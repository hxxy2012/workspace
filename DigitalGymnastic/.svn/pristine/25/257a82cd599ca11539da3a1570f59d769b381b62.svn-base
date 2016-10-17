package com.hike.digitalgymnastic.utils;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.hike.digitalgymnastic.tools.UtilLog;
import com.lidroid.xutils.http.RequestParams;

import org.apache.http.message.BasicNameValuePair;

import java.util.List;

public class Utiles {

	private static String TAG ="utiles";

	/**
	 * 每次请求提交的信息
	 * 
	 * @param params
	 */
	@SuppressWarnings("static-access")
	public static void Add(Context context,RequestParams params) {
		Build bd = new Build();
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		WifiManager wifimanager=(WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		params.addBodyParameter("osType", "2");
		params.addBodyParameter("osVersion", String.valueOf(android.os.Build.VERSION.SDK_INT) );
		params.addBodyParameter("appVersion",Utils.getVersion(context));
		params.addBodyParameter("deviceModel", bd.MODEL);
		if(!TextUtils.isEmpty(tm.getDeviceId()))
			params.addBodyParameter("deviceId", tm.getDeviceId());
		else if(wifimanager.getConnectionInfo()!=null
				&&!TextUtils.isEmpty(wifimanager.getConnectionInfo().getMacAddress())){
			params.addBodyParameter("deviceId",wifimanager.getConnectionInfo().getMacAddress());
		}else{
			params.addBodyParameter("deviceId", android.os.Build.SERIAL);
		}
		params.addBodyParameter("sign", "");
		String _token = LocalDataUtils.readCustomer(context).getLoginToken();
		params.addBodyParameter("token", _token);
		UtilLog.e(TAG, _token);
		params.addBodyParameter("timestamp", String.valueOf(System.currentTimeMillis()));
	}
//	static BeanRequestBase mReqeustBean = null;
//	public static BeanRequestBase newRequestBaseBean() {
//		if (mReqeustBean == null){
//			Build bd = new Build();
//			TelephonyManager tm = (TelephonyManager) HikoDigitalgyApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
//			WifiManager wifimanager=(WifiManager) HikoDigitalgyApplication.getInstance().getSystemService(Context.WIFI_SERVICE);
//
//
//			String _deivceID = "";
//			if(!TextUtils.isEmpty(tm.getDeviceId()))
//				_deivceID =  tm.getDeviceId();
//			else if(wifimanager.getConnectionInfo()!=null
//					&&!TextUtils.isEmpty(wifimanager.getConnectionInfo().getMacAddress())){
//				_deivceID = wifimanager.getConnectionInfo().getMacAddress();
//			}else{
//				_deivceID =  android.os.Build.SERIAL;
//			}
//			mReqeustBean = new BeanRequestBase("2",String.valueOf(android.os.Build.VERSION.SDK_INT),Utils.getVersion(HikoDigitalgyApplication.getInstance())
//					, bd.MODEL,_deivceID,"","","");
//		}
//		return mReqeustBean;
//	}
	/**
	 * 每次请求提交的信息
	 * 
	 * @param params
	 */
	@SuppressWarnings("static-access")
	public static void Add(Context context,List<BasicNameValuePair> params) {
		Build bd = new Build();
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);		
		params.add(new BasicNameValuePair("osType", "2"));
		params.add(new BasicNameValuePair("osVersion", Utils.getVersion(context)));
		params.add(new BasicNameValuePair("appVersion", String.valueOf(android.os.Build.VERSION.SDK_INT)));
		params.add(new BasicNameValuePair("deviceModel",  bd.MODEL));
		params.add(new BasicNameValuePair("deviceId", tm.getDeviceId()));
	}
}
