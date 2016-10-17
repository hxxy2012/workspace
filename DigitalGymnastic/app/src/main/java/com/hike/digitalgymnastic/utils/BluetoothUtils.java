package com.hike.digitalgymnastic.utils;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;

import com.hike.digitalgymnastic.HikoDigitalgyApplication;

import java.util.List;

public class BluetoothUtils {

	/**
	 * 获取系统是否和此mac地址的设备保持连接
	 * @param pMac
	 * @return
	 * @throws Exception
     */
	@SuppressLint("NewApi")
	public static BluetoothDevice getBlueToothDeviceFromConnected(String pMac)
			throws Exception {
		List<BluetoothDevice> _listBluetoothDevices = HikoDigitalgyApplication.getInstance().mBluetoothManager
				.getConnectedDevices(BluetoothProfile.GATT_SERVER);
		for (int i = 0; i < _listBluetoothDevices.size(); i++) {
			BluetoothDevice _BluetoothDevice = _listBluetoothDevices.get(i);
			String _mac = _BluetoothDevice.getAddress();
			if (pMac.equalsIgnoreCase(_mac)) {
				return _BluetoothDevice;
			}
		}

		return null;
	}
	public static boolean checkDeviceConnectStatus() throws Exception {
		BluetoothDevice _device = getBlueToothDeviceFromConnected(HikoDigitalgyApplication.getInstance().bindMAC);
		if (_device==null){
			return false;
		}else{
			return  true;
		}
	}





	

	

}
