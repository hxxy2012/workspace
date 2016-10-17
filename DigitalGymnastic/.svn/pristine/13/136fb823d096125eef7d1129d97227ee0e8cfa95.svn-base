package com.hike.digitalgymnastic.request;

import com.hike.digitalgymnastic.response.LoginResponse;
import com.hike.digitalgymnastic.utils.Utils;
import com.lidroid.xutils.http.RequestParams;


/*
 * @auth changqi
 * @description 登陆请求类
 * @GET + POST
 */
public class LoginRequest extends BaseRequest<LoginResponse>{
	
	public String  phone;//
	public String  password;//
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public Class getResponseClass() {
		
		return LoginResponse.class;
	}
	@Override
	public RequestParams getRequestParams() {
		return Utils.getRequestParams(this);
	}
}
