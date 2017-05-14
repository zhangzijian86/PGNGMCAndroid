package com.pg.ngmc.pgngmcandroid.activity;

import android.content.Intent;
import android.os.Bundle;
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

import com.pg.ngmc.pgngmcandroid.R;
import com.pg.ngmc.pgngmcandroid.bean.PGNGMC_APP_User;
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

    private ImageView top_head;
    private ImageView top_more;
    private RelativeLayout zuocetouming;

    private final static int SCANNIN_GREQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        saomakaisuo = (ImageView)findViewById(R.id.saomakaisuo);
        saomakaisuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Log.d("=====MainActivity=====", "====saomakaisuo====");
                if (pgngmc_app_user.getUSER_Mobile().equals("")) {
                    Log.d("=====MainActivity=====", "====saomakaisuo===false=");
                } else {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, MipcaActivityCapture.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
                }
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

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCANNIN_GREQUEST_CODE:
                if(resultCode == RESULT_OK){
                    Bundle bundle = data.getExtras();
                    Log.d("=====MainActivity=====", "====onActivityResult====" + bundle.getString("result"));
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
}