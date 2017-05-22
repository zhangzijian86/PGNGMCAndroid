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
	private Button btdenglu;
	private Button btzhuce;
	private EditText passwdedit;
	private EditText namededit;
	private TextView nametext;
	private TextView passwdtext;
	private PGNGMC_APP_User pgngmc_app_user;
	private String usermobile;
	private String loginPass;
	private LoadingProgressDialog dialog;
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
		 setContentView(R.layout.activity_login);

		 pgngmc_app_user = (PGNGMC_APP_User) getApplication();

		 dialog=new LoadingProgressDialog(this,"正在加载...");
	 }
	 	 
 	/**
 	 * dis：AsyncTask参数类型：
 	 * 第一个参数标书传入到异步任务中并进行操作，通常是网络的路径
 	 * 第二个参数表示进度的刻度
 	 * 第三个参数表示返回的结果类型
 	 * */
 	private class UserLoginAsyncTask extends AsyncTask<String, String, String>{
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
 				String result="";//operaton.login("Login", params[0], params[1]);
// 				if(!result.equals("false")){
// 	 				JsonUtil jsonUtil=new JsonUtil();
// 					List<PGNGMC_User> list1=(List<PGNGMC_User>) jsonUtil.StringFromJson(result);
// 					Pgdr_user user=list1.get(0);
// 					if(user.isUser_return()){
// 						puser.setUser_id(user.getUser_id());
// 						puser.setUser_name(user.getUser_name());
// 						puser.setUser_password(user.getUser_password());
// 						puser.setUser_mobile(user.getUser_mobile());
// 						puser.setUser_address(user.getUser_address());
// 						puser.setUser_email(user.getUser_email());
// 						puser.setUser_status("1");
// 						puser.setUser_type(user.getUser_type());
// 						puser.setUser_photo(user.getUser_photo());
// 						puser.setUser_return(true);
// 						Log.d("====com.pg.pg.login.LoginActivity====", "getUser_id()"+puser.getUser_id());
// 						Log.d("====com.pg.pg.login.LoginActivity====", "getUser_mobile()"+puser.getUser_mobile());
// 						Log.d("====com.pg.pg.login.LoginActivity====", "getUser_password()"+puser.getUser_password());
// 						return "success";
// 					}else{
// 						return "false";
// 					}
// 				}else{
// 					return "false";
// 				}
 			}catch(Exception e) {
				e.printStackTrace();
				return "false";
			}
 			return "false";
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
 			dialog.dismiss();//dialog关闭，数据处理完毕
 		}
 	}
}
