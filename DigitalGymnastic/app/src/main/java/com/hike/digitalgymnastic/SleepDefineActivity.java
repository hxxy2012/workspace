package com.hike.digitalgymnastic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.hike.digitalgymnastic.entitiy.DailySleepData;
import com.hike.digitalgymnastic.entitiy.PeriodSleepData;
import com.hike.digitalgymnastic.entitiy.PeriodSportData;
import com.hike.digitalgymnastic.request.BaseDao;
import com.hike.digitalgymnastic.utils.Utils;
import com.hike.digitalgymnastic.view.HistogramSleepView;
import com.hike.digitalgymnastic.view.HistogramSleepView.OnTouchWeekListener;
import com.hike.digitalgymnastic.view.HistorySleepProgressView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.hiko.enterprisedigital.R;
/*
 * @Auth changqi
 * 
 */

@ContentView(R.layout.activity_sleep_define)
public class SleepDefineActivity extends BaseActivity {
	@ViewInject(R.id.btn_left)
	ImageView btn_left;
	@ViewInject(R.id.btn_right)
	ImageView btn_right;
	@ViewInject(R.id.title)
	TextView tv_title;
	@ViewInject(R.id.ll_btn_left)
	private LinearLayout ll_btn_left;
	@ViewInject(R.id.ll_btn_right)
	private LinearLayout ll_btn_right;
	


	@OnClick(value={R.id.btn_left,R.id.ll_btn_left})
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_left:
			finish();
			break;
		case R.id.ll_btn_left:
			finish();
			break;
			
		default:
			break;
		}
	}



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		init();
	}



	private void init() {
		// TODO Auto-generated method stub
		tv_title.setText("睡眠释义");
	}
}
