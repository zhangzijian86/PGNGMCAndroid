package com.pg.ngmc.pgngmcandroid.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.pg.ngmc.pgngmcandroid.R;
import com.pg.ngmc.pgngmcandroid.bean.PGNGMC_APP_User;
import com.pg.ngmc.pgngmcandroid.bean.PGNGMC_User;
import com.pg.ngmc.pgngmcandroid.json.JsonUtil;
import com.pg.ngmc.pgngmcandroid.tools.Operaton;

import java.util.List;

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
        pgngmc_app_user = (PGNGMC_APP_User) getApplication();
        if(getValue("USER_Mobile")==null||getValue("USER_Mobile").equals("")){
            pgngmc_app_user.setUSER_Mobile("");
            mHandler.postDelayed(toMainActivity, 3000);
        }else{
            pgngmc_app_user.setUSER_Mobile(getValue("USER_Mobile"));
            new UserRegisterAsyncTask().execute(new String[]{getValue("USER_Mobile")});
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

    /**
     * dis：AsyncTask参数类型：
     * 第一个参数标书传入到异步任务中并进行操作，通常是网络的路径
     * 第二个参数表示进度的刻度
     * 第三个参数表示返回的结果类型
     * */
    private class UserRegisterAsyncTask extends AsyncTask<String, String, String> {
        //任务执行之前的操作
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }
        //完成耗时操作
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            try{
                Operaton operaton=new Operaton();
                String result=operaton.UpData("Register", params[0]);
                return result;
            }catch(Exception e){
                e.printStackTrace();
                return "";
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            // TODO Auto-generated method stub
            super.onProgressUpdate(values);

        }

        //数据处理完毕后更新UI操作
        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            if(result!=null&&!result.equals("no")){
                //Toast.makeText(getApplicationContext(), "验证成功！", Toast.LENGTH_SHORT).show();
                Log.d("=====LoginActivity=====", "===getUSER_Mobile=000=" + result);
                JsonUtil jsonUtil=new JsonUtil();
                List<PGNGMC_User> list1=(List<PGNGMC_User>) jsonUtil.StringFromJsonUser(result);
                PGNGMC_User user=list1.get(0);
                Log.d("=====LoginActivity=====", "===getUSER_Mobile=111=" + user.getUSER_Mobile());
                pgngmc_app_user.setUSER_Mobile(user.getUSER_Mobile());
                pgngmc_app_user.setUSER_ID(user.getUSER_ID());
                pgngmc_app_user.setUSER_ISDN(user.getUSER_ISDN());
                pgngmc_app_user.setUSER_RegisterDate(user.getUSER_RegisterDate());
                pgngmc_app_user.setUSER_Status(user.getUSER_Status());
                pgngmc_app_user.setUSER_DepositStatus(user.getUSER_DepositStatus());
                pgngmc_app_user.setUSER_DepositNumber(user.getUSER_DepositNumber());
            }else{
                pgngmc_app_user.setUSER_Mobile("");
            }
            mHandler.postDelayed(toMainActivity, 3000);
        }
    }
}