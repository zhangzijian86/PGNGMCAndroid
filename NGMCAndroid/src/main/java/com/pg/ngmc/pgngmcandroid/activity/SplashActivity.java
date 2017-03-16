package com.pg.ngmc.pgngmcandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import com.pg.ngmc.pgngmcandroid.R;

/**
 * Created by zzj on 16-7-22.
 */
public class SplashActivity extends AppCompatActivity {
    private Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mHandler = new Handler();
        mHandler.postDelayed(toMainActivity, 3000);

    }

    Runnable toLoginActivity = new Runnable() {
        @Override
        public void run() {
//            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
//            finish();
        }
    };

    Runnable toMainActivity = new Runnable() {
        @Override
        public void run() {
            //startActivity(new Intent(SplashActivity.this, MainActivity.class));
            startActivity(new Intent(SplashActivity.this, MapDemo.class));
            finish();
        }
    };
}