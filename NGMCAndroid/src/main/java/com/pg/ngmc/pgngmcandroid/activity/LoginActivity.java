package com.pg.ngmc.pgngmcandroid.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pg.ngmc.pgngmcandroid.R;
import com.pg.ngmc.pgngmcandroid.bean.PGNGMC_APP_User;
import com.pg.ngmc.pgngmcandroid.bean.PGNGMC_User;
import com.pg.ngmc.pgngmcandroid.json.JsonUtil;
import com.pg.ngmc.pgngmcandroid.tools.LoadingProgressDialog;
import com.pg.ngmc.pgngmcandroid.tools.Operaton;

import java.util.List;

public class LoginActivity extends Activity {
	private Button queding;
	private Button yanzhengmabt;
	private EditText shoujihaoma;
	private EditText yanzhengma;
	private PGNGMC_APP_User pgngmc_app_user;
	private LoadingProgressDialog dialog;
	private String yanzhengmaReturn;
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
		 setContentView(R.layout.activity_login);

		 pgngmc_app_user = (PGNGMC_APP_User) getApplication();

		 shoujihaoma = (EditText) findViewById(R.id.shoujihaoma);
		 yanzhengma = (EditText) findViewById(R.id.yanzhengma);
		 queding  = (Button) findViewById(R.id.queding);
		 yanzhengmabt  = (Button) findViewById(R.id.yanzhengmabt);

		 yanzhengmabt.setOnClickListener(new OnClickListener() {
			 @Override
			 public void onClick(View view) {
				 String phoneNumber = shoujihaoma.getText().toString().trim();
				 new UserRegisterYanZhengMaAsyncTask().execute(new String[]{phoneNumber});
			 }
		 });

		 queding.setOnClickListener(new OnClickListener() {
			  @Override
			  public void onClick(View view) {
				  String phoneNumber = shoujihaoma.getText().toString().trim();
				  String yanZhengMa = yanzhengma.getText().toString().trim();
				  if(yanzhengmaReturn.equals(yanZhengMa)){
					  new UserRegisterAsyncTask().execute(new String[]{phoneNumber});
				  }
			  }
		  });


			 shoujihaoma.addTextChangedListener(textWatcher);
		 yanzhengma.addTextChangedListener(textWatcher);

			 dialog=new

			 LoadingProgressDialog(this,"正在加载...");
	 }

		 private TextWatcher textWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
								  int count) {
			if(shoujihaoma.getText().length()>=11){
				yanzhengmabt.setEnabled(true);
			}else{
				yanzhengmabt.setEnabled(false);
			}
			if(shoujihaoma.getText().length()>=11&&yanzhengma.getText().length()>=6){
				queding.setEnabled(true);
			}else{
				queding.setEnabled(false);
			}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
									  int after) {

		}

		@Override
		public void afterTextChanged(Editable s) {

		}
	};

	/**
	 * dis：AsyncTask参数类型：
	 * 第一个参数标书传入到异步任务中并进行操作，通常是网络的路径
	 * 第二个参数表示进度的刻度
	 * 第三个参数表示返回的结果类型
	 * */
	private class UserRegisterYanZhengMaAsyncTask extends AsyncTask<String, String, String>{
		//任务执行之前的操作
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog.show();//显示dialog，数据正在处理....
		}
		//完成耗时操作
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try{
				Operaton operaton=new Operaton();
				String result = "";
				result=operaton.checkPhoneNumber("GetYZM", params[0]);
				return result;
			}catch(Exception e){
				e.printStackTrace();
				return "false";
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

			if(result!=null){
				if(result.startsWith("no")){
					Toast.makeText(getApplicationContext(), "请求成功，等待短信验证码发送！", Toast.LENGTH_SHORT).show();
					yanzhengmaReturn = result.substring(2, result.length());
					Log.d("=yanzhengmaReturn==", yanzhengmaReturn);
				}
				else if("false".equals(result)){
					Toast.makeText(getApplicationContext(), "验证码请求失败，请重新请求！", Toast.LENGTH_SHORT).show();
				}
			}else{
				Toast.makeText(getApplicationContext(), "验证码请求失败，请重新请求！", Toast.LENGTH_SHORT).show();
			}
			dialog.dismiss();//dialog关闭，数据处理完毕
		}
	}

	/**
	 * dis：AsyncTask参数类型：
	 * 第一个参数标书传入到异步任务中并进行操作，通常是网络的路径
	 * 第二个参数表示进度的刻度
	 * 第三个参数表示返回的结果类型
	 * */
	private class UserRegisterAsyncTask extends AsyncTask<String, String, String>{
		//任务执行之前的操作
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog.show();//显示dialog，数据正在处理....
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
			if(!result.equals("no")){
				Toast.makeText(getApplicationContext(), "验证成功！", Toast.LENGTH_SHORT).show();
				Log.d("=====LoginActivity=====", "===getUSER_Mobile=000="+result);
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
				LoginActivity.this.finish();
			}else if("".equals(result)){
				Toast.makeText(getApplicationContext(), "验证失败，请重新验证！", Toast.LENGTH_SHORT).show();
			}
			dialog.dismiss();//dialog关闭，数据处理完毕
		}
	}
}
