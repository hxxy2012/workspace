package com.hike.digitalgymnastic.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class BrainService extends Service {
	private static final String TAG = BrainService.class.getSimpleName();

	private BrainBind m_binder;
	
	
	
	@Override
	public void onCreate() {
		m_binder = new BrainBind(this);
		Log.d(TAG, "*服务创建完成*");
	}



	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}



	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return m_binder;
	}

}
