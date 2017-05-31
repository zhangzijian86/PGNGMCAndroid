package com.pg.ngmc.pgngmcandroid.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pg.ngmc.pgngmcandroid.R;
import com.pg.ngmc.pgngmcandroid.bean.PGNGMC_APP_User;
import com.pg.ngmc.pgngmcandroid.bean.PGNGMC_User;
import com.pg.ngmc.pgngmcandroid.json.JsonUtil;
import com.pg.ngmc.pgngmcandroid.tools.LoadingProgressDialog;
import com.pg.ngmc.pgngmcandroid.tools.Operaton;

import java.util.List;
import java.util.regex.Pattern;

public class ISDNActivity extends Activity {
	private Button quedingisdn;
	private EditText isdn;
	private PGNGMC_APP_User pgngmc_app_user;
	private LoadingProgressDialog dialog;

	public Pattern idNumPattern = Pattern.compile(
			"^[1-9][0-7]\\d{4}((19\\d{2}(0[13-9]|1[012])(0[1-9]|[12]\\d|30))|(19\\d{2}(0[13578]|1[02])31)|(19\\d{2}02(0[1-9]|1\\d|2[0-8]))|(19([13579][26]|[2468][048]|0[48])0229))\\d{3}(\\d|X|x)?$");

	@Override
	 protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
		 setContentView(R.layout.activity_isdn);

		 pgngmc_app_user = (PGNGMC_APP_User) getApplication();

		 isdn = (EditText) findViewById(R.id.isdn);
		 quedingisdn  = (Button) findViewById(R.id.quedingisdn);

		 quedingisdn.setOnClickListener(new OnClickListener() {
			 @Override
			 public void onClick(View view) {
				 String isdnstr = isdn.getText().toString().trim();
				 boolean flag = idNumPattern.matcher(isdnstr).matches();
				 if(flag){
					 new UserUpdateISDNMaAsyncTask().execute(
							 new String[]{isdnstr,pgngmc_app_user.getUSER_Mobile()}
					 );
				 }else{
					 Toast.makeText(getApplicationContext(), "请输入正确身份证号码！", Toast.LENGTH_SHORT).show();
				 }
			 }
		 });

		 isdn.addTextChangedListener(textWatcher);

		 dialog=new	 LoadingProgressDialog(this,"正在加载...");
	 }

	private TextWatcher textWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
								  int count) {
			if(isdn.getText().length()>=15){
				quedingisdn.setEnabled(true);
			}else{
				quedingisdn.setEnabled(false);
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
	private class UserUpdateISDNMaAsyncTask extends AsyncTask<String, String, String>{
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
				result=operaton.UpdateISDN("UpdateISDN", params[0], params[1]);
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
			if(!result.equals("no")){
				Toast.makeText(getApplicationContext(), "验证成功！", Toast.LENGTH_SHORT).show();
				Log.d("=====ISDNActivity=====", "===getUSER_Mobile=000=" + result);
				pgngmc_app_user.setUSER_ISDN(isdn.getText().toString().trim());
				ISDNActivity.this.finish();
			}else if("".equals(result)){
				Toast.makeText(getApplicationContext(), "验证失败，请重新验证！", Toast.LENGTH_SHORT).show();
			}
			dialog.dismiss();//dialog关闭，数据处理完毕
		}
	}
}
