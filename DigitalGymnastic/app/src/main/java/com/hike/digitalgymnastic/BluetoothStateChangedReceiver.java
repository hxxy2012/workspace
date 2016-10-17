package com.hike.digitalgymnastic;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.hike.digitalgymnastic.tools.UtilLog;
public class BluetoothStateChangedReceiver extends BroadcastReceiver {

	private String TAG = "BluetoothStateChangedReceiver" ;

	@Override
	public void onReceive(Context context, Intent intent) {
		UtilLog.e(TAG, "onReceive");
		HikoDigitalgyApplication application = (HikoDigitalgyApplication) context
				.getApplicationContext();
		if (BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED.equals(intent
				.getAction())) {
			UtilLog.e(TAG, "BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED");

		}
		if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(intent.getAction())) {
			UtilLog.e(TAG,"BluetoothDevice.ACTION_ACL_DISCONNECTED");
//			application.setWatchConnected(false);

		}
		if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(intent.getAction())) {
			UtilLog.e(TAG, "BluetoothDevice.ACTION_ACL_CONNECTED");
		}
		if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(intent.getAction())) {
//			Log.v("MyLog", "BluetoothAdapter.ACTION_STATE_CHANGED");
		}

	}

}
