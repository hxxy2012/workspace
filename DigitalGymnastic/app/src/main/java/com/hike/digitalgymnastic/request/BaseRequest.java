package com.hike.digitalgymnastic.request;

import com.hike.digitalgymnastic.response.BaseResponse;
import com.lidroid.xutils.http.RequestParams;

 public class BaseRequest<T extends BaseResponse> implements BaseInterface{
	 public    String osType;//
	      public	 String osVersion;
		 public	 String appVersion;
		 public String deviceModel;
		 public	 String deviceId;
		 public	 String sign;
		 public	 String token;
		 public	 String timestamp;
	
		public String getOsType() {
			return osType;
		}
		public void setOsType(String osType) {
			this.osType = osType;
		}
		public String getOsVersion() {
			return osVersion;
		}
		public void setOsVersion(String osVersion) {
			this.osVersion = osVersion;
		}
		public String getAppVersion() {
			return appVersion;
		}
		public void setAppVersion(String appVersion) {
			this.appVersion = appVersion;
		}
		public String getDeviceModel() {
			return deviceModel;
		}
		public void setDeviceModel(String deviceModel) {
			this.deviceModel = deviceModel;
		}
		public String getDeviceId() {
			return deviceId;
		}
		public void setDeviceId(String deviceId) {
			this.deviceId = deviceId;
		}
		public String getSign() {
			return sign;
		}
		public void setSign(String sign) {
			this.sign = sign;
		}
		public String getToken() {
			return token;
		}
		public void setToken(String token) {
			this.token = token;
		}
		public String getTimestamp() {
			return timestamp;
		}
		public void setTimestamp(String timestamp) {
			this.timestamp = timestamp;
		}
		@Override
		public Class getResponseClass() {
			return BaseResponse.class;
			
		}
		@Override
		public RequestParams getRequestParams() {
		// TODO Auto-generated method stub
		return null;
		}
		
	
}
