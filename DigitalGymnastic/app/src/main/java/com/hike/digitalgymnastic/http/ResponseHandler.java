package com.hike.digitalgymnastic.http;

import com.hike.digitalgymnastic.response.BaseResponse;
import com.lidroid.xutils.exception.HttpException;

public interface ResponseHandler {
	public void handleResponse(BaseResponse response);
	public void handleError(HttpException error, String msg);
}
