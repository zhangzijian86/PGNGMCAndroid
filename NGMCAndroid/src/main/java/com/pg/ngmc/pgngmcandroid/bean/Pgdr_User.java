package com.pg.ngmc.pgngmcandroid.bean;

public class Pgdr_User {
	
	private String user_id;//USER_ID
	private String user_name;//USER_NAME
	private String user_password;//USER_PASSWORD
	private String user_mobile;//USER_MOBILE
	private String user_address;//USER_ADDRESS
	private String user_email;//USER_EMAIL
	private String user_status;//USER_STATUS 用户状态 -1 删除 0 禁用  1 正常   
	private String user_type;//USER_TYPE 用户类型 0 普通用户 1 在申请状态用户 2小贩
	private String user_photo;//USER_PHOTO
	private boolean user_return;//返回值
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_password() {
		return user_password;
	}
	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}
	public String getUser_mobile() {
		return user_mobile;
	}
	public void setUser_mobile(String user_mobile) {
		this.user_mobile = user_mobile;
	}
	public String getUser_address() {
		return user_address;
	}
	public void setUser_address(String user_address) {
		this.user_address = user_address;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getUser_status() {
		return user_status;
	}
	public void setUser_status(String user_status) {
		this.user_status = user_status;
	}
	public String getUser_type() {
		return user_type;
	}
	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}
	public String getUser_photo() {
		return user_photo;
	}
	public void setUser_photo(String user_photo) {
		this.user_photo = user_photo;
	}	
	public boolean isUser_return() {
		return user_return;
	}
	public void setUser_return(boolean user_return) {
		this.user_return = user_return;
	}
}
