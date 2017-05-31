package com.pg.ngmc.pgngmcandroid.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.pg.ngmc.pgngmcandroid.R;
import com.pg.ngmc.pgngmcandroid.bean.PGNGMC_APP_User;
import com.pg.ngmc.pgngmcandroid.tools.LoadingProgressDialog;
import com.pg.ngmc.pgngmcandroid.tools.Operaton;

public class RecordTimeActivity extends Activity {

	private PGNGMC_APP_User pgngmc_app_user;
	private LoadingProgressDialog dialog;
	private String  bikecode;



	@Override
	 protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
		 setContentView(R.layout.activity_recordtime);

		Intent intent = getIntent(); //用于激活它的意图对象
		bikecode = intent.getStringExtra("bikecode");

		pgngmc_app_user = (PGNGMC_APP_User) getApplication();

		dialog=new	 LoadingProgressDialog(this,"正在加载...");

		new StartOrderAsyncTask().execute(
				new String[]{pgngmc_app_user.getUSER_ID(),bikecode}
		);
	 }

	/**
	 * dis：AsyncTask参数类型：
	 * 第一个参数标书传入到异步任务中并进行操作，通常是网络的路径
	 * 第二个参数表示进度的刻度
	 * 第三个参数表示返回的结果类型
	 * */
	private class StartOrderAsyncTask extends AsyncTask<String, String, String>{
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
				String result = "";
				result=operaton.StartOrder("StartOrder", params[0],params[1]);
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
				Toast.makeText(getApplicationContext(), "计时开始！", Toast.LENGTH_SHORT).show();
			}else if("".equals(result)){
				Toast.makeText(getApplicationContext(), "车辆开启失败！", Toast.LENGTH_SHORT).show();
				RecordTimeActivity.this.finish();
			}
		}
	}
}
