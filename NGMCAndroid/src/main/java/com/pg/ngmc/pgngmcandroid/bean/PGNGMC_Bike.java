package com.pg.ngmc.pgngmcandroid.bean;

public class PGNGMC_Bike {
	
	 private String BIKE_ID;// '自行车ID'
	 private String BIKE_Code;// '自行车编码'
	 private String BIKE_EnableDate;//  '启用日期'
	 private String BIKE_Company;//  '公司（生产）'
	 private String BIKE_Status;//  '自行车状态(0,正常 1,保修 2,维修 -1,报废)'
	 private String BIKE_Type;//  '自行车状态(0,山地 1,普通 )'	
	 private String POSITION_X;// '位置X' longitude
	 private String POSITION_Y;// '位置Y' latitude
	 
	 //select BIKE_ID,BIKE_Code,BIKE_EnableDate,BIKE_Company,BIKE_Status,BIKE_Type from PG_BIKE;
	
	public String getBIKE_ID() {
		return BIKE_ID;
	}
	public void setBIKE_ID(String bIKE_ID) {
		BIKE_ID = bIKE_ID;
	}
	public String getBIKE_Code() {
		return BIKE_Code;
	}
	public void setBIKE_Code(String bIKE_Code) {
		BIKE_Code = bIKE_Code;
	}
	public String getBIKE_EnableDate() {
		return BIKE_EnableDate;
	}
	public void setBIKE_EnableDate(String bIKE_EnableDate) {
		BIKE_EnableDate = bIKE_EnableDate;
	}
	public String getBIKE_Company() {
		return BIKE_Company;
	}
	public void setBIKE_Company(String bIKE_Company) {
		BIKE_Company = bIKE_Company;
	}
	public String getBIKE_Status() {
		return BIKE_Status;
	}
	public void setBIKE_Status(String bIKE_Status) {
		BIKE_Status = bIKE_Status;
	}
	public String getBIKE_Type() {
		return BIKE_Type;
	}
	public void setBIKE_Type(String bIKE_Type) {
		BIKE_Type = bIKE_Type;
	}
	public String getPOSITION_X() {
		return POSITION_X;
	}
	public void setPOSITION_X(String pOSITION_X) {
		POSITION_X = pOSITION_X;
	}
	public String getPOSITION_Y() {
		return POSITION_Y;
	}
	public void setPOSITION_Y(String pOSITION_Y) {
		POSITION_Y = pOSITION_Y;
	}
}
