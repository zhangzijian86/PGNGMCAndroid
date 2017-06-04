package com.pg.ngmc.pgngmcandroid.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.pg.ngmc.pgngmcandroid.R;
import com.pg.ngmc.pgngmcandroid.bean.PGNGMC_APP_User;
import com.pg.ngmc.pgngmcandroid.tools.LoadingProgressDialog;
import com.pg.ngmc.pgngmcandroid.tools.Operaton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RecordTimeActivity extends Activity {

	private PGNGMC_APP_User pgngmc_app_user;
	private LoadingProgressDialog dialog;
	private String  bikecode;
	private String  finishFlag;

	private TextView timetv;

	private Date dateRT;
	private String dateString = "00:00:00";

	private int sumFlag;

	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");


	@Override
	 protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
		 setContentView(R.layout.activity_recordtime);

		finishFlag = "0";
		sumFlag = 0;

		Intent intent = getIntent(); //用于激活它的意图对象
		bikecode = intent.getStringExtra("bikecode");

		pgngmc_app_user = (PGNGMC_APP_User) getApplication();

		dialog=new	 LoadingProgressDialog(this,"正在加载...");

		timetv = (TextView)findViewById(R.id.timetv);

		dateRT = toDate(dateString);

		new StartOrderAsyncTask().execute(
				new String[]{pgngmc_app_user.getUSER_ID(),bikecode}
		);
		handler1.postDelayed(new Runnable() {
			@Override
			public void run() {
				handler1.sendEmptyMessage(1);
			}
		}, 500);
	}

	private  Date toDate(String datastr){
		Date datetmp = null;
		try {
			datetmp = sdf.parse(dateString);
		}
		catch (ParseException e)
		{
			System.out.println(e.getMessage());
		}
		return  datetmp;
	}

	final Handler handler1 = new Handler(){
		public void handleMessage(Message msg){
			super.handleMessage(msg);
			switch (msg.what) {
				case 1:
					Log.d("==RecordTimeActivity==", "====1111==");
					if(!sdf.format(dateRT).startsWith("23:59")&&finishFlag.equals("0")){
						handler1.sendEmptyMessageDelayed(1, 1000);
						String   str   =   sdf.format(dateRT);
						Calendar c = Calendar.getInstance();
						c.setTime(dateRT);
						c.add(Calendar.SECOND, +1);
						dateRT = c.getTime();
						timetv.setText(str);
						sumFlag ++;
						if(sumFlag==2){
							sumFlag = 0;
							new ReGetBikeStatusAsyncTask().execute(new String[]{bikecode});
						}
					}else{

					}
					break;
				case 2:
					finishFlag = "1";
					Log.d("==RecordTimeActivity==", "====xingchengjieshu==");
					break;
			}
		}
	};

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

	/**
	 * dis：AsyncTask参数类型：
	 * 第一个参数标书传入到异步任务中并进行操作，通常是网络的路径
	 * 第二个参数表示进度的刻度
	 * 第三个参数表示返回的结果类型
	 * */
	private class ReGetBikeStatusAsyncTask extends AsyncTask<String, String, String> {
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
				String result=operaton.GetBikeStatus("GetBikeStatus", params[0]);
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
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Log.d("==RecordTimeActivity==", "====result==1==" + result);
			result = result.replace("\"","");
			if(result!=null&&!result.equals("false")){
				Log.d("==RecordTimeActivity==", "====result==2==" + result);
				if(result.equals("lock")){
					Log.d("==RecordTimeActivity==", "====lock=");
					handler1.sendEmptyMessage(2);
				}
			}else{
				Log.d("==RecordTimeActivity==", "====else=");
			}
		}
	}
}
