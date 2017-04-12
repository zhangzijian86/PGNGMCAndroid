package com.pg.ngmc.pgngmcandroid.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.pg.ngmc.pgngmcandroid.R;
import com.pg.ngmc.pgngmcandroid.bean.PGNGMC_Bike;
import com.pg.ngmc.pgngmcandroid.json.JsonUtil;
import com.pg.ngmc.pgngmcandroid.tools.LoadingProgressDialog;
import com.pg.ngmc.pgngmcandroid.tools.Operaton;

import java.util.List;

public class MainActivity1111 extends Activity {
    private static final String ClassName = "MainActivity";
    private LocationClient mLocationClient;
    private BDLocationListener mBDLocationListener;
    private BaiduMap mBaiduMap;
    private MapView mapView;
    private double start1 = 39.958271;
    private double start2 = 116.42652;
    private List<PGNGMC_Bike> list;
    private int count = 0;

    private LoadingProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(ClassName, "=onCreate=11==");
        SDKInitializer.initialize(getApplicationContext());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_mapdemo);

        mapView = (MapView) findViewById(R.id.mapView);

        mBaiduMap = mapView.getMap();
        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        //开启交通图
        mBaiduMap.setTrafficEnabled(true);

        Log.d(ClassName, "=onCreate=22==");
        // 声明LocationClient类
        mLocationClient = new LocationClient(getApplicationContext());
        mBDLocationListener = new MyBDLocationListener();
        // 注册监听
        mLocationClient.registerLocationListener(mBDLocationListener);

        dialog=new LoadingProgressDialog(this,"正在加载...");

        getLocation(mapView);

    }

    /**
     * 获得所在位置经纬度及详细地址
     */
    public void getLocation(View view) {
        // 声明定位参数
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式 高精度
        option.setCoorType("bd09ll");// 设置返回定位结果是百度经纬度 默认gcj02
        option.setScanSpan(3000);// 设置发起定位请求的时间间隔 单位ms
        option.setIsNeedAddress(true);// 设置定位结果包含地址信息
        option.setNeedDeviceDirect(true);// 设置定位结果包含手机机头 的方向
        // 设置定位参数
        mLocationClient.setLocOption(option);
        // 启动定位
        mLocationClient.start();

    }

    private class MyBDLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // 非空判断
            if (location != null) {

                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                String address = location.getAddrStr();

                Log.d(ClassName,"===latitude=="+latitude+"===longitude=="+longitude);

                MyLocationData locData = new MyLocationData.Builder()
                        .accuracy(40)
                                // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(90.0f)
                        .latitude(latitude)
                        .longitude(longitude).build();
                float f = mBaiduMap.getMaxZoomLevel();//19.0 最小比例尺
                //float m = mBaiduMap.getMinZoomLevel();//3.0 最大比例尺
                mBaiduMap.setMyLocationData(locData);
                mBaiduMap.setMyLocationEnabled(true);
                LatLng ll = new LatLng(latitude, longitude);
                //MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll,f);
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(ll, f - 2.9f);//设置缩放比例
                mBaiduMap.animateMapStatus(u);

                // 开发者可根据自己实际的业务需求，利用标注覆盖物，在地图指定的位置上添加标注信息。具体实现方法如下：
                //定义Maker坐标点
//                LatLng point = new LatLng(latitude, longitude);
//
//
//                //构建Marker图标
//                BitmapDescriptor bitmap = BitmapDescriptorFactory
//                        .fromResource(R.mipmap.tubiao);
//                //构建MarkerOption，用于在地图上添加Marker
//                OverlayOptions option = new MarkerOptions()
//                        .position(point)
//                        .icon(bitmap);
//                //在地图上添加Marker，并显示
//                mBaiduMap.addOverlay(option);
                if(count==0){
                    new GetBikeAsyncTask().execute(new String[]{String.valueOf(latitude), String.valueOf(longitude)});
                }
                count ++;
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        // 取消监听函数
        if (mLocationClient != null) {
            mLocationClient.unRegisterLocationListener(mBDLocationListener);
        }
        mapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mapView.onPause();
    }

    /**
     * dis：AsyncTask参数类型：
     * 第一个参数标书传入到异步任务中并进行操作，通常是网络的路径
     * 第二个参数表示进度的刻度
     * 第三个参数表示返回的结果类型
     * */
    private class GetBikeAsyncTask extends AsyncTask<String, String, String> {
        //任务执行之前的操作
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            dialog.show();
        }
        //完成耗时操作
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            try{
                Operaton operaton=new Operaton();
                String result=operaton.GetBikeByPosition("GetBikeByPosition", params[0], params[1]);
                JsonUtil jsonUtil=new JsonUtil();
                list=(List<PGNGMC_Bike>) jsonUtil.StringFromJsonBike(result);
                if(list!=null&&list.size()>0){
                    return "true";
                }
            }catch(Exception e){
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
            Log.d(ClassName, "===result=0=" + result);
            if(result.equals("true")){
                for(int i = 0;i < list.size(); i ++){
                    PGNGMC_Bike PGB = list.get(i);
                    LatLng point = new LatLng(Double.valueOf(PGB.getPOSITION_Y()), Double.valueOf(PGB.getPOSITION_X()));
                    BitmapDescriptor bitmap = BitmapDescriptorFactory
                            .fromResource(R.mipmap.tubiao);
                    OverlayOptions option = new MarkerOptions()
                            .position(point)
                            .icon(bitmap);
                    mBaiduMap.addOverlay(option);
                }
            }
            dialog.dismiss();
        }
    }
}