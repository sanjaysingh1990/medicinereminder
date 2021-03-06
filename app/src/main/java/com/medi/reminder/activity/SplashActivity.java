package com.medi.reminder.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.ViewById;
import com.medi.reminder.R;
import com.medi.reminder.login.PhoneAuthActivity;


public class SplashActivity extends AppCompatActivity {

    private static final int SPLASHTIME = 3000; //3 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, PhoneAuthActivity.class));
                finish();
            }
        }, SPLASHTIME);
    }

}
