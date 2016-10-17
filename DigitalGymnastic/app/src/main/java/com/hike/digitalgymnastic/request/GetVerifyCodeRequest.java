package com.hike.digitalgymnastic.request;

import com.hike.digitalgymnastic.response.GetVerifyCodeResponse;
import com.hike.digitalgymnastic.utils.Utils;
import com.lidroid.xutils.http.RequestParams;

/*
 * @auth changqi
 * @description 获取验证码
 * @GET + POST
 */
public class GetVerifyCodeRequest extends BaseRequest<GetVerifyCodeResponse>  {
	public 	String phone;// 是	客户电话
	public	String type;// 是	短信类型(1:注册用户;2:找回密码)
	
	
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	
	@Override
	public Class getResponseClass() {
		
		return GetVerifyCodeResponse.class;
	}
	@Override
	public RequestParams getRequestParams() {
		return Utils.getRequestParams(this);
	}

}
