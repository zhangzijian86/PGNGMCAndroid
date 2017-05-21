package com.pg.ngmc.pgngmcandroid.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.pg.ngmc.pgngmcandroid.R;
import com.pg.ngmc.pgngmcandroid.bean.PGNGMC_APP_User;
import com.pg.ngmc.pgngmcandroid.bean.PGNGMC_Bike;
import com.pg.ngmc.pgngmcandroid.json.JsonUtil;
import com.pg.ngmc.pgngmcandroid.tools.Operaton;
import com.pg.ngmc.pgngmcandroid.view.CategoryTabStrip;
import com.pg.ngmc.pgngmcandroid.view.NewsFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private PGNGMC_APP_User pgngmc_app_user;

    private CategoryTabStrip tabs;
    private ViewPager pager;
    private MyPagerAdapter adapter;

    private TranslateAnimation mHiddenAction;
    private TranslateAnimation mShowAction;

    private LinearLayout zuoce;
    private ImageView saomakaisuo;

    private RelativeLayout head;
    private RelativeLayout jindu;

    private ImageView top_head;
    private ImageView top_more;
    private RelativeLayout zuocetouming;

    private String resultCodeStr;

    int len = 0;

    private SeekBar seekBar;

    private final static int SCANNIN_GREQUEST_CODE = 1;

    @Override
    protected void onResume() {
        super.onResume();
        len = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        resultCodeStr = "";

        pgngmc_app_user = (PGNGMC_APP_User) getApplication();

        mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,-1.0f ,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAction.setDuration(500);

        mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, -1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f);
        mHiddenAction.setDuration(500);

        zuoce  = (LinearLayout)findViewById(R.id.zuoce);
        head  = (RelativeLayout)findViewById(R.id.head);
        jindu  = (RelativeLayout)findViewById(R.id.jindu);

        saomakaisuo = (ImageView)findViewById(R.id.saomakaisuo);
        saomakaisuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Log.d("=====MainActivity=====", "====saomakaisuo====");
//                if (pgngmc_app_user.getUSER_Mobile().equals("")) {
//                    Log.d("=====MainActivity=====", "====saomakaisuo===false=");
//                } else {
                    resultCodeStr = "";
                    len=0;
                    seekBar.setProgress(len);
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, MipcaActivityCapture.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
//                }
            }
        });

        zuoce.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        top_head  = (ImageView)findViewById(R.id.top_head);
        top_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (pgngmc_app_user.getUSER_Mobile().equals("")) {
                    Log.d("=====MainActivity=====", "====top_head===false=");
                } else {
                    if (zuoce.getVisibility() != View.VISIBLE) {
                        zuoce.startAnimation(mShowAction);
                        zuoce.setVisibility(View.VISIBLE);
                        head.startAnimation(mHiddenAction);
                        head.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });

        top_more = (ImageView)findViewById(R.id.top_more);
        top_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (pgngmc_app_user.getUSER_Mobile().equals("")) {
                    Log.d("=====MainActivity=====", "====top_more===false=");
                } else {

                }
            }
        });

        zuocetouming = (RelativeLayout)findViewById(R.id.zuocetouming);
        zuocetouming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                zuoce.startAnimation(mHiddenAction);
                zuoce.setVisibility(View.INVISIBLE);
                head.startAnimation(mShowAction);
                head.setVisibility(View.VISIBLE);
            }
        });

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int widthPixels= dm.widthPixels;

        tabs = (CategoryTabStrip) findViewById(R.id.category_strip);
        tabs.setWidthPixels(widthPixels);

        pager = (ViewPager) findViewById(R.id.view_pager);
        adapter = new MyPagerAdapter(getSupportFragmentManager());

        pager.setAdapter(adapter);

        tabs.setViewPager(pager);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax(100);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.d("=====handler=====", "====msg="+msg.what);
            switch (msg.what)
            {
                case 4://故障
                    Log.d("=====handler=====", "====故障=");
                    jindu.setVisibility(View.INVISIBLE);
                    resultCodeStr = "";
                    seekBar.setProgress(100);
                    Toast.makeText(getApplicationContext(), "车辆故障请使用其他车辆！", Toast.LENGTH_SHORT).show();
                    break;
                case 3://维修
                    Log.d("=====handler=====", "====维修=");
                    jindu.setVisibility(View.INVISIBLE);
                    resultCodeStr = "";
                    seekBar.setProgress(100);
                    Toast.makeText(getApplicationContext(), "车辆维修中请使用其他车辆！", Toast.LENGTH_SHORT).show();
                    break;
                case 2://报废
                    Log.d("=====handler=====", "====报废=");
                    jindu.setVisibility(View.INVISIBLE);
                    resultCodeStr = "";
                    seekBar.setProgress(100);
                    Toast.makeText(getApplicationContext(), "车辆报废请使用其他车辆！", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    if (seekBar.getProgress()<100)
                    {
                        len += 15;
                        handler.sendEmptyMessageDelayed(1, 1000);
                        new GetBikeStatusAsyncTask().execute(new String[]{resultCodeStr});
                        seekBar.setProgress(len);
                    }else{
                        if(len>100){
                            jindu.setVisibility(View.INVISIBLE);
                            resultCodeStr = "";
                            Toast.makeText(getApplicationContext(), "开锁失败请重试！", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case 0://开锁成功
                    Log.d("=====handler=====", "====成功=");
                    jindu.setVisibility(View.INVISIBLE);
                    resultCodeStr = "";
                    seekBar.setProgress(100);
                    Toast.makeText(getApplicationContext(), "开锁成功！", Toast.LENGTH_SHORT).show();
                    break;
            }

        }

    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCANNIN_GREQUEST_CODE:
                if(resultCode == RESULT_OK){
                    Bundle bundle = data.getExtras();
                    Log.d("=====MainActivity=====", "====onActivityResult====" + bundle.getString("result"));
                    resultCodeStr =  bundle.getString("result");
                    jindu.setVisibility(View.VISIBLE);
                    //jindu.setVisibility(View.INVISIBLE);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            handler.sendEmptyMessage(1);
                        }
                    }, 500);
                }
                break;
        }
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        private final List<String> catalogs = new ArrayList<String>();

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            catalogs.add(getString(R.string.biketitle));
            catalogs.add(getString(R.string.activitytitle));
            catalogs.add(getString(R.string.tickettitle));
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return catalogs.get(position);
        }

        @Override
        public int getCount() {
            return catalogs.size();
        }

        @Override
        public Fragment getItem(int position) {

            if(position==0){
                return NewsFragment.newInstance(position);
            }else{
                return NewsFragment.newInstance(position);
            }

        }
    }

    /**
     * dis：AsyncTask参数类型：
     * 第一个参数标书传入到异步任务中并进行操作，通常是网络的路径
     * 第二个参数表示进度的刻度
     * 第三个参数表示返回的结果类型
     * */
    private class GetBikeStatusAsyncTask extends AsyncTask<String, String, String> {
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
            Log.d("=====onPostExecute=====", "====result==1==" + result);
            result = result.replace("\"","");
            if(result!=null&&!result.equals("false")){
                Log.d("=====onPostExecute=====", "====result==2=="+result);
                if(result.equals("unlock")){//开锁成功
                    handler.sendEmptyMessage(0);
                    Log.d("=====onPostExecute=====", "====成功=");
                }
                if(result.equals("lock")){
                    Log.d("=====onPostExecute=====", "====lock=");
                }
                if(result.equals("report")){//故障
                    Log.d("=====onPostExecute=====", "====故障=");
                    handler.sendEmptyMessage(4);
                }
                if(result.equals("repair")){//维修
                    Log.d("=====onPostExecute=====", "====维修=");
                    handler.sendEmptyMessage(3);
                }
                if(result.equals("Scrap")){//报废
                    Log.d("=====onPostExecute=====", "====报废=");
                    handler.sendEmptyMessage(2);
                }
            }else{
                Log.d("=====onPostExecute=====", "====else=");
            }
        }
    }
}