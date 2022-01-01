package com.example.vandraby.activities.authorization.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vandraby.R;
import com.example.vandraby.activities.registration.RegistrationActivity;
import com.example.vandraby.activities.splashscreen.SplashActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class AuthorizationActivity extends AppCompatActivity {

    private final static String LOGGER_TAG = "LOG_VANDRA_AUTH";

    private static final int RC_SIGN_IN = 9001;

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mAuth = FirebaseAuth.getInstance();

        SignInButton buttonSignInWithGoogle = findViewById(R.id.button_sign_in_with_google);
        buttonSignInWithGoogle.setOnClickListener(v -> {
            Intent intent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(intent, RC_SIGN_IN);
        });

        TextView buttonRegistration = findViewById(R.id.button_registration);
        buttonRegistration.setOnClickListener(v -> {
            Log.e(LOGGER_TAG, "open registration activity");
            Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();
        startSplashActivity(user);

        mGoogleSignInClient.signOut();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.e(LOGGER_TAG, "getSignedInAccountFromIntent failed: " + e.getMessage());
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = mAuth.getCurrentUser();
                startSplashActivity(user);
            } else {
                Log.e(LOGGER_TAG, "signInWithCredential failed: " + task.getResult().toString());
            }
        });
    }

    private void startSplashActivity(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
