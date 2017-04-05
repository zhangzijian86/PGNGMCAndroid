package com.pg.ngmc.pgngmcandroid.tools;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.client.methods.HttpPost;

import android.util.Log;

public class ConnNet 
{
//	private static final String URLVAR="http://10.110.5.38:8080/LoginRegister/";
//	private static final String URLVAR="http://192.168.1.109:8080/WebRoot/";
//	private static final String URLVAR="http://192.8.50.202:8080/WebRoot/";
	private static final String URLVAR="http://192.168.1.110:8080/WebRoot/";
//	private static final String URLVAR="http://192.168.1.110:8080/WebRoot/";
	//将路径定义为一个常量，修改的时候也好更改
	//通过url获取网络连接  connection 
	public HttpURLConnection getConn(String urlpath)
	{
		String finalurl=URLVAR+urlpath;
		HttpURLConnection connection = null;
		try {
			URL url=new URL(finalurl);
			connection=(HttpURLConnection) url.openConnection();
			connection.setDoInput(true);  //允许输入流
            connection.setDoOutput(true); //允许输出流
            connection.setUseCaches(false);  //不允许使用缓存
            connection.setRequestMethod("POST");  //请求方式
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return connection;

	}
	public HttpPost gethttPost(String uripath) 
	{	
		HttpPost httpPost=new HttpPost(URLVAR+uripath);	
	
		System.out.println(URLVAR+uripath);
		return httpPost;
	}

}
