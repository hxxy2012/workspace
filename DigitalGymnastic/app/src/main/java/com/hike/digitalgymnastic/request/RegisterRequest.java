package com.hike.digitalgymnastic.request;

import com.hike.digitalgymnastic.response.RegisterResponse;
import com.hike.digitalgymnastic.utils.Utils;
import com.lidroid.xutils.http.RequestParams;

/*
 * @auth changqi
 * @description 
 * @GET + POST
 */
public class RegisterRequest extends BaseRequest<RegisterResponse> {
	public String phone;// String	是	客户电话
	public String password;// String	是	客户密码
	public String verifyCode;//String	是	验证码
	
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
	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	
	
	
	@Override
	public Class getResponseClass() {
		
		return RegisterResponse.class;
	}
	
	@Override
	public RequestParams getRequestParams() {
		return Utils.getRequestParams(this);
	}

}
