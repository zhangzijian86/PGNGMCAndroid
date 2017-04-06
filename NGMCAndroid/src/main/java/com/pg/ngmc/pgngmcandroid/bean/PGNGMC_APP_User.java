package com.pg.ngmc.pgngmcandroid.bean;

import android.app.Application;

public class PGNGMC_APP_User extends Application {

	private String USER_ID;
	private String USER_Code;
	private String USER_Name;
	private String USER_ISDN;
	private String USER_Mobile;
	private String USER_RegisterDate;
	private String USER_Status;              //用户状态(0,注册 1,身份证 -1,删除)
	private String USER_DepositStatus;       //用户押金状态(0,未交 1,已交)
	private String USER_DepositNumber;       //押金数量

	public String getUSER_ID() {
		return USER_ID;
	}
	public void setUSER_ID(String uSER_ID) {
		USER_ID = uSER_ID;
	}
	public String getUSER_Code() {
		return USER_Code;
	}
	public void setUSER_Code(String uSER_Code) {
		USER_Code = uSER_Code;
	}
	public String getUSER_Name() {
		return USER_Name;
	}
	public void setUSER_Name(String uSER_Name) {
		USER_Name = uSER_Name;
	}
	public String getUSER_ISDN() {
		return USER_ISDN;
	}
	public void setUSER_ISDN(String uSER_ISDN) {
		USER_ISDN = uSER_ISDN;
	}
	public String getUSER_Mobile() {
		return USER_Mobile;
	}
	public void setUSER_Mobile(String uSER_Mobile) {
		USER_Mobile = uSER_Mobile;
	}
	public String getUSER_RegisterDate() {
		return USER_RegisterDate;
	}
	public void setUSER_RegisterDate(String uSER_RegisterDate) {
		USER_RegisterDate = uSER_RegisterDate;
	}
	public String getUSER_Status() {
		return USER_Status;
	}
	public void setUSER_Status(String uSER_Status) {
		USER_Status = uSER_Status;
	}
	public String getUSER_DepositStatus() {
		return USER_DepositStatus;
	}
	public void setUSER_DepositStatus(String uSER_DepositStatus) {
		USER_DepositStatus = uSER_DepositStatus;
	}
	public String getUSER_DepositNumber() {
		return USER_DepositNumber;
	}
	public void setUSER_DepositNumber(String uSER_DepositNumber) {
		USER_DepositNumber = uSER_DepositNumber;
	}
}
