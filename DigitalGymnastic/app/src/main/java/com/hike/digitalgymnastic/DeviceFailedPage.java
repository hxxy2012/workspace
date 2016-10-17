package com.hike.digitalgymnastic;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.hiko.enterprisedigital.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
/*
 * changqi
 */
@ContentView(R.layout.activity_deviceconfailed)
public class DeviceFailedPage extends BaseActivity {

	@ViewInject(R.id.btn_research)
	Button btn_research;

	@OnClick(value = { R.id.btn_research })
	public void onclick(View view) {
		switch (view.getId()) {
		case R.id.btn_research:
			jump2DeviceSearchPage();
			break;

		default:
			break;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		ViewUtils.inject(this);
	}

	private void jump2DeviceSearchPage() {
		// TODO Auto-generated method stub
		application.unBinder();
		Intent intent = new Intent(DeviceFailedPage.this,
				DeviceScanActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK){
			application.unBinder();
		}
		return super.onKeyDown(keyCode, event);
	}
}
