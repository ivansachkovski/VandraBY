package com.example.vandraby.activities.splashscreen;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vandraby.R;
import com.example.vandraby.activities.main.MainActivity;
import com.example.vandraby.model.DataModel;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        DataModel.getInstance().initialize();

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (DataModel.getInstance().isReady()) {
                    timer.cancel();

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };

        timer.schedule(timerTask, 2000, 1000);
    }

}
