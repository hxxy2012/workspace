package com.hike.digitalgymnastic.request;

import com.hike.digitalgymnastic.response.UploadPictureResponse;
import com.hike.digitalgymnastic.utils.Utils;
import com.lidroid.xutils.http.RequestParams;

/*
 * @auth changqi
 * @description 
 * @GET + POST
 */
public class UploadPictureRequest extends BaseRequest<UploadPictureResponse> {
	@Override
	public Class getResponseClass() {
		
		return UploadPictureResponse.class;
	}
	
	@Override
	public RequestParams getRequestParams() {
		return Utils.getRequestParams(this);
	}

}
