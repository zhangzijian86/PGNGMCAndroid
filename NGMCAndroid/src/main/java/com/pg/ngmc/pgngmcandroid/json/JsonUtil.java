package com.pg.ngmc.pgngmcandroid.json;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pg.ngmc.pgngmcandroid.bean.PGNGMC_Bike;
import com.pg.ngmc.pgngmcandroid.bean.PGNGMC_User;


public class JsonUtil {

	public List<PGNGMC_Bike> StringFromJsonBike (String jsondata)
	{
		Type listType = new TypeToken<List<PGNGMC_Bike>>(){}.getType();
		Gson gson=new Gson();
		ArrayList<PGNGMC_Bike> list=gson.fromJson(jsondata, listType);
		return list;

	}
	public List<PGNGMC_User> StringFromJsonUser (String jsondata)
	{
		Type listType = new TypeToken<List<PGNGMC_User>>(){}.getType();
		Gson gson=new Gson();
		ArrayList<PGNGMC_User> list=gson.fromJson(jsondata, listType);
		return list;
	}

//	public List<Pgdr_price> StringFromJsonPrice(String jsondata)
//	{
//		Type listType = new TypeToken<List<Pgdr_price>>(){}.getType();
//		Gson gson=new Gson();
//		ArrayList<Pgdr_price> list=gson.fromJson(jsondata, listType);
//		return list;
//
//	}
}
