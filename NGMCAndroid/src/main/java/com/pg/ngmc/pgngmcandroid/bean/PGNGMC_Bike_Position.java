package com.pg.ngmc.pgngmcandroid.bean;

public class PGNGMC_Bike_Position {
	
	private String POSITION_ID;// '位置ID'	
	private String POSITION_Code;// '位置编码'
	private String POSITION_X;// '位置X'
	private String POSITION_Y;// '位置Y'
	private String POSITION_RecordDate;// '记录日期'
	private String POSITION_BIKE_ID;// '自行车ID'
	
	//select POSITION_ID,POSITION_Code,POSITION_X,POSITION_Y,POSITION_RecordDate,POSITION_BIKE_ID
	//from PG_BIKE_POSITION;
	
	public String getPOSITION_ID() {
		return POSITION_ID;
	}
	public void setPOSITION_ID(String pOSITION_ID) {
		POSITION_ID = pOSITION_ID;
	}
	public String getPOSITION_Code() {
		return POSITION_Code;
	}
	public void setPOSITION_Code(String pOSITION_Code) {
		POSITION_Code = pOSITION_Code;
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
	public String getPOSITION_RecordDate() {
		return POSITION_RecordDate;
	}
	public void setPOSITION_RecordDate(String pOSITION_RecordDate) {
		POSITION_RecordDate = pOSITION_RecordDate;
	}
	public String getPOSITION_BIKE_ID() {
		return POSITION_BIKE_ID;
	}
	public void setPOSITION_BIKE_ID(String pOSITION_BIKE_ID) {
		POSITION_BIKE_ID = pOSITION_BIKE_ID;
	}	
}
