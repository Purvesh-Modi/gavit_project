package com.project.myapplication;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class SplashActivity extends AppCompatActivity {

    ProgressBar progressBar;
    int         mProgressBarStatus = 0;
    Handler     handler            = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        progressBar = findViewById(R.id.splash_progressBar);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mProgressBarStatus < 100) {
                    mProgressBarStatus++;
                    SystemClock.sleep(50);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(mProgressBarStatus);
                        }
                    });
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    }
                });
            }
        }).start();
    }

    @Override
    public void onBackPressed() {}
}
