package com.hike.digitalgymnastic.request;


import com.lidroid.xutils.http.RequestParams;

public interface BaseInterface {
	public Class getResponseClass();
	public RequestParams getRequestParams();
}
