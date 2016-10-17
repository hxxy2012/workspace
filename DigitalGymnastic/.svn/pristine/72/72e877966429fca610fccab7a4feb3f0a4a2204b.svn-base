package com.hike.digitalgymnastic;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hike.digitalgymnastic.tools.UtilLog;
import com.hike.digitalgymnastic.utils.Constants;
import com.hiko.enterprisedigital.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;

/*
 * changqi
 */
@ContentView(R.layout.activity_devicelist)
public class DeviceListActivity extends BaseActivity {
	
	@ViewInject(R.id.btn_research)
	Button btn_research;
	@ViewInject(R.id.ll_help)
	LinearLayout ll_help;
	@ViewInject(R.id.device_list)
	ListView device_list;

	@OnClick(value = { R.id.btn_research, R.id.ll_help })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_research:
			application.unBinder();
			Intent intent=new Intent(DeviceListActivity.this,DeviceScanActivity.class);
			intent.putExtra("isFromRegister",isFromRegister);
			startActivity(intent);
			finish();
			break;
		case R.id.ll_help:
			intent=new Intent(DeviceListActivity.this,DeviceScanHelpActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}

	ArrayList<BluetoothDevice> deviceList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		ViewUtils.inject(this);
		
		init();

		

	}

	boolean isFromRegister;
	private void init() {
		deviceList=getIntent().getParcelableArrayListExtra(Constants.deviceList);
		if(deviceList!=null){
			DeviceAdapter adapter=new DeviceAdapter();
			device_list.setAdapter(adapter);
		}
		isFromRegister=getIntent().getBooleanExtra(Constants.isRegister, false);
		
	}

	class DeviceAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return deviceList.size();
		}

		@Override
		public Object getItem(int index) {
			// TODO Auto-generated method stub
			return deviceList.get(index);
		}

		@Override
		public long getItemId(int id) {
			// TODO Auto-generated method stub
			return id;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			Holder holder=null;
			if(convertView==null){
				holder=new Holder();
				convertView=getLayoutInflater().inflate(R.layout.item_devicelist, null);
				holder.tv_device_name=(TextView) convertView.findViewById(R.id.tv_device_name);
				holder.iv_connect=(ImageView) convertView.findViewById(R.id.iv_connect);
				convertView.setTag(holder);
			}else{
				holder=(Holder) convertView.getTag();
			}
			final BluetoothDevice device=(BluetoothDevice) getItem(position);
			if (UtilLog.isDebug){
				holder.tv_device_name.setText(device.getName()+"  "+device.getAddress());
			}else{
				holder.tv_device_name.setText(device.getName());
			}

			holder.iv_connect.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					Intent intent=new Intent(DeviceListActivity.this,DeviceConnectActivity.class);
					intent.putExtra(Constants.deviceName,device);
					startActivity(intent);
				}
			});
			convertView.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					Intent intent=new Intent(DeviceListActivity.this,DeviceConnectActivity.class);
					intent.putExtra(Constants.deviceName,device);
					intent.putExtra(Constants.isRegister, isFromRegister);
					startActivity(intent);
				}
			});
			return convertView;
		}
		class Holder{
			public TextView tv_device_name;
			public ImageView iv_connect;
		}
	}
	

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
			application.unBinder();
			if(isFromRegister)
				return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		deviceList.clear();
	}
}
