package com.example.vandraby.activities.authorization.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vandraby.R;
import com.example.vandraby.activities.main.ui.MainActivity;
import com.example.vandraby.model.User;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class AuthorizationActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static final String LOGGER_TAG = "VANDRA_LOGGER";
    private static final int RC_SIGN_IN = 9001;

    SharedPreferences settings;
    private static final String SETTINGS_NAME = "userCredentials";
    private static final String LOGIN_FIELD_NAME = "login";
    private static final String PASSWORD_FIELD_NAME = "password";
    private static final String AUTO_LOGIN_FLAG_NAME = "auto-login";

    EditText editLoginInput;
    EditText editPasswordInput;

    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        // Init views
        editLoginInput = findViewById(R.id.edit_login);
        editPasswordInput = findViewById(R.id.edit_password);
        // Fill views with the credentials from the  previous session
        settings = getSharedPreferences(SETTINGS_NAME, 0);
        editLoginInput.setText(settings.getString(LOGIN_FIELD_NAME, ""));
        editPasswordInput.setText(settings.getString(PASSWORD_FIELD_NAME, ""));

        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        SignInButton buttonSignInWithGoogle = findViewById(R.id.button_sign_in_with_google);
        buttonSignInWithGoogle.setOnClickListener(v -> {
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
            startActivityForResult(signInIntent, RC_SIGN_IN);
        });

        Button buttonLogin = findViewById(R.id.button_login);
        buttonLogin.setOnClickListener(v -> {
            // TODO::
        });

        /*

        // Try to log in if auto-login is on
        if (settings.getBoolean(AUTO_LOGIN_FLAG_NAME, false)) {
            loginWithCredentials();
        }
         */
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        assert data != null;

        if (requestCode == RC_SIGN_IN) {

            GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            assert googleSignInResult != null;

            if (googleSignInResult.isSuccess()) {
                GoogleSignInAccount account = googleSignInResult.getSignInAccount();
                assert account != null;

                Toast.makeText(this, "Hello, " + account.getDisplayName(), Toast.LENGTH_LONG).show();

                firebaseAuthWithGoogle(account);

                //openApplicationActivity();
            } else {
                Toast.makeText(this, "Authorization failed", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "onConnectionFailed, " + connectionResult, Toast.LENGTH_LONG).show();
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                openApplicationActivity();
            } else {

            }
        });
    }

    private void openApplicationActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            openApplicationActivity();
        }
    }
}
