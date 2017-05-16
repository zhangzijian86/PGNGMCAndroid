package com.pg.ngmc.pgngmcandroid.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;

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
import com.pg.ngmc.pgngmcandroid.tools.ConnNet;
import com.pg.ngmc.pgngmcandroid.tools.LoadingProgressDialog;
import com.pg.ngmc.pgngmcandroid.tools.Operaton;

import java.util.List;

public class NewsFragment extends Fragment {

	private static final String ARG_POSITION = "position";
	private int position;

	private static final String ClassName = "NewsFragment";
	private LocationClient mLocationClient;
	private BDLocationListener mBDLocationListener;
	private BaiduMap mBaiduMap;
	private MapView mapView;
	private WebView webView;
	private double start1 = 39.958271;
	private double start2 = 116.42652;
	private List<PGNGMC_Bike> list;
	private int count = 0;
	private LoadingProgressDialog dialog;

	private ConnNet connNet;

	@Override
	public void onDestroy() {
		super.onDestroy();
		//在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		// 取消监听函数
		if (mLocationClient != null) {
			mLocationClient.unRegisterLocationListener(mBDLocationListener);
		}
		if(mapView!=null){
			mapView.onDestroy();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		count = 0;
		//在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
	}

	public static NewsFragment newInstance(int position) {
		NewsFragment f = new NewsFragment();
		Bundle b = new Bundle();
		b.putInt(ARG_POSITION, position);
		f.setArguments(b);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		position = getArguments().getInt(ARG_POSITION);

		Log.d(ClassName, "=onCreate=11==");
		connNet=new ConnNet();
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		setContentView(R.layout.activity_mapdemo);
		dialog=new LoadingProgressDialog(getActivity(),"正在加载...");

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
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		//
		if(position==0){
			SDKInitializer.initialize(getActivity().getApplicationContext());
			View v = inflater.inflate(R.layout.activity_mapdemo, container, false);
			mapView = (MapView) v.findViewById(R.id.mapView);
			mapView.showScaleControl(false);
			mapView.showZoomControls(false);
			mBaiduMap = mapView.getMap();
			//普通地图
			mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
			//开启交通图
			mBaiduMap.setTrafficEnabled(true);

			Log.d(ClassName, "=onCreate=22==");
			// 声明LocationClient类
			mLocationClient = new LocationClient(getActivity().getApplicationContext());
			mBDLocationListener = new MyBDLocationListener();
			// 注册监听
			mLocationClient.registerLocationListener(mBDLocationListener);
			getLocation(mapView);
			return v;
		}else {//if(position==1)
			View v = inflater.inflate(R.layout.activity_webview, container, false);
			webView = (WebView) v.findViewById(R.id.webView);
			webView.loadUrl(connNet.getWebViewURLVAR()+"1.html");
			return v;
		}
//		else{
//			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//			FrameLayout fl = new FrameLayout(getActivity());
//			fl.setLayoutParams(params);
//
//			final int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources()
//					.getDisplayMetrics());
//
//
//			TextView v = new TextView(getActivity());
//			params.setMargins(margin, margin, margin, margin);
//			v.setLayoutParams(params);
//			v.setLayoutParams(params);
//			v.setGravity(Gravity.CENTER);
//			v.setText("PAGE " + (position + 1));
//
//			fl.addView(v);
//			return fl;
//		}
	}
}