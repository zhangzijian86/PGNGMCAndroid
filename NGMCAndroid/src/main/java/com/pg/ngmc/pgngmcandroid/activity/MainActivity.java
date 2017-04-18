package com.pg.ngmc.pgngmcandroid.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.pg.ngmc.pgngmcandroid.R;
import com.pg.ngmc.pgngmcandroid.view.CategoryTabStrip;
import com.pg.ngmc.pgngmcandroid.view.NewsFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {
    private CategoryTabStrip tabs;
    private ViewPager pager;
    private MyPagerAdapter adapter;

    private TranslateAnimation mHiddenAction;
    private TranslateAnimation mShowAction;

    private LinearLayout zuoce;
    private RelativeLayout head;

    private ImageView top_head;
    private RelativeLayout zuocetouming;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                if(zuoce.getVisibility()!=View.VISIBLE){
                    zuoce.startAnimation(mShowAction);
                    zuoce.setVisibility(View.VISIBLE);
                    head.startAnimation(mHiddenAction);
                    head.setVisibility(View.INVISIBLE);
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