package com.pg.ngmc.pgngmcandroid.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.pg.ngmc.pgngmcandroid.R;
import com.pg.ngmc.pgngmcandroid.bean.PGNGMC_APP_User;

/**
 * Created by zzj on 16-7-22.
 */
public class SplashActivity extends AppCompatActivity {
    private Handler mHandler;
    private PGNGMC_APP_User pgngmc_app_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mHandler = new Handler();
        mHandler.postDelayed(toMainActivity, 3000);
        pgngmc_app_user = (PGNGMC_APP_User) getApplication();
        if(getValue("USER_Mobile")==null||getValue("USER_Mobile").equals("")){
            pgngmc_app_user.setUSER_Mobile("");
        }else{
            pgngmc_app_user.setUSER_Mobile(getValue("USER_Mobile"));
        }
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
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            //startActivity(new Intent(SplashActivity.this, MapDemo.class));
            finish();
        }
    };

    private String getValue(String name){
        SharedPreferences sp=getSharedPreferences("paramater", Context.MODE_PRIVATE);
        //若没有数据，返回默认值""
        String value=sp.getString(name, "");
        return value;
    }
}