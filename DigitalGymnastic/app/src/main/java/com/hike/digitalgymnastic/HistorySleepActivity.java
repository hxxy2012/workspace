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

import com.hike.digitalgymnastic.entitiy.Customer;
import com.hike.digitalgymnastic.entitiy.DailySleepData;
import com.hike.digitalgymnastic.entitiy.PeriodSleepData;
import com.hike.digitalgymnastic.entitiy.PeriodSportData;
import com.hike.digitalgymnastic.request.BaseDao;
import com.hike.digitalgymnastic.utils.LocalDataUtils;
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

@ContentView(R.layout.activity_history_sleep)
public class HistorySleepActivity extends BluetoothActivity implements
		HistogramSleepView.OnTouchWeekListener {
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

	@ViewInject(R.id.iv_xingxing)
	ImageView iv_xingxing;
	@ViewInject(R.id.viewFlipper)
	ViewFlipper viewFlipper;

	// @ViewInject(R.id.iv_top)
	// ImageView iv_top;
	@ViewInject(R.id.ll_blank_alert)
	LinearLayout ll_blank_alert;
	@ViewInject(R.id.rl_handler)
	RelativeLayout rl_handler;
	// @ViewInject(R.id.rl_bottom_tongji)
	// RelativeLayout rl_bottom_tongji;
	@ViewInject(R.id.ll_bottom)
	LinearLayout ll_bottom;

	@ViewInject(R.id.tv_sleep_time)
	TextView tv_sleep_time;
	@ViewInject(R.id.tv_sleep_totaltime)
	TextView tv_sleep_totaltime;

	@ViewInject(R.id.tv_deep_sleep_time)
	TextView tv_deep_sleep_time;
	@ViewInject(R.id.tv_light_sleep_time)
	TextView tv_light_sleep_time;
	@ViewInject(R.id.tv_sleep_begintime)
	TextView tv_sleep_begintime;
	@ViewInject(R.id.tv_sleep_endtime)
	TextView tv_sleep_endtime;

	@ViewInject(R.id.hspv1)
	HistorySleepProgressView hspv1;
	@ViewInject(R.id.hspv2)
	HistorySleepProgressView hspv2;

	private BaseDao dao;
	private LayoutInflater test_inflater;

	@OnClick(value = { R.id.btn_left, R.id.ll_btn_left })
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

	Animation leftInAnimation;
	Animation leftOutAnimation;
	Animation rightInAnimation;
	Animation rightOutAnimation;
	Customer customer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		init();
		setData();
	}

	private void setData() {
		// 动画效果
		leftInAnimation = AnimationUtils.loadAnimation(this,
				R.anim.next_view_enter);
		leftOutAnimation = AnimationUtils.loadAnimation(this,
				R.anim.next_right_out);
		rightInAnimation = AnimationUtils.loadAnimation(this,
				R.anim.next_right_in);
		rightOutAnimation = AnimationUtils.loadAnimation(this,
				R.anim.next_view_out);
		buildView(null);
		showProgress(true);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String startDate = format.format(new Date());
		// dao.GetPeriodSleepData(format.format(dateAdd(-56)),
		// format.format(dateAdd(-1)));
		boolean isLasted = dao.GetPeriodSleepDataFromDB(
				format.format(dateAdd(-56)), format.format(dateAdd(-1)));
		if (isLasted) {
			PeriodSleepData periodSleepData = dao.getPeriodSleepData();
			buildView(periodSleepData);
		}
	}

	public static Date dateAdd(int days) {
		// 日期处理模块 (将日期加上某些天或减去天数)返回字符串
		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, days); // 日期减 如果不够减会将月变动
		return canlendar.getTime();
	}

	private void init() {
		customer = LocalDataUtils.readCustomer(this);
		dao = new BaseDao(this, this);
		tv_title.setText(getString(R.string.history_title_str));
		// tv_date.setText("2015-06-22");
		// tv_date.setVisibility(View.GONE);
		btn_left.setImageResource(R.mipmap.toolbar_back);
		// iv_top.setVisibility(View.GONE);
		// ll_blank_alert.setVisibility(View.GONE);
		rl_handler.setVisibility(View.GONE);
		// rl_bottom_tongji.setVisibility(View.GONE);
	}

	private OnClickListener MyListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			switch (v.getId()) {
			case R.id.btn_left:
				Back();
				break;
			default:
				break;
			}
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Back();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	// 返回事件
	private void Back() {
		finish();

	}

	private void showPreviousView() {
		viewFlipper.setInAnimation(leftInAnimation);
		viewFlipper.setOutAnimation(rightOutAnimation);
		viewFlipper.showPrevious();// 向左滑动
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				HistogramSleepView hs = (HistogramSleepView) viewFlipper
						.getCurrentView();
				hs.start(2);
				hs = (HistogramSleepView) viewFlipper.getChildAt(viewFlipper
						.getDisplayedChild() + 1);
				hs.clear();
			}
		}, 500);
	}

	private void showNextView() {
		viewFlipper.setInAnimation(rightInAnimation);
		viewFlipper.setOutAnimation(leftOutAnimation);
		viewFlipper.showNext();// 向右滑动
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {

				HistogramSleepView hs = (HistogramSleepView) viewFlipper
						.getCurrentView();
				hs.start(2);
				hs = (HistogramSleepView) viewFlipper.getChildAt(viewFlipper
						.getDisplayedChild() - 1);
				hs.clear();
			}
		}, 500);

	}

	@Override
	public void onWeekTouch(Object obj1, Object obj2) {
		// TODO Auto-generated method stub
		if (obj2 != null && obj2 instanceof DailySleepData) {
			DailySleepData osd = (DailySleepData) obj2;
			updateText(osd);
		}
		if (obj1 != null && obj1 instanceof List) {
			updateWeekAvera(obj1);
		}

	}

	boolean isToucheAble = true;

	@Override
	public void onWeekFiller(int direction) {
		// TODO Auto-generated method stub
		switch (direction) {
		case -1:
			if (viewFlipper.getDisplayedChild() != 0) {
				if (isToucheAble) {
					showPreviousView();
					isToucheAble = false;
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							isToucheAble = true;
						}
					}, 800);
				}
			}

			break;
		case 1:
			if (viewFlipper.getDisplayedChild() != viewFlipper.getChildCount() - 1) {
				if (isToucheAble) {
					showNextView();
					isToucheAble = false;
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							isToucheAble = true;
						}
					}, 800);
				}
			}

			break;

		default:
			break;
		}

	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
		}

	};

	@Override
	public void onRequestFaild(String errorNo, String errorMessage) {
		// TODO Auto-generated method stub
		super.onRequestFaild(errorNo, errorMessage);
	}

	@Override
	public void showProgress(boolean show) {
		// TODO Auto-generated method stub
		super.showProgress(show);
	}

	@Override
	public void showProgressWithText(boolean show, String message) {
		// TODO Auto-generated method stub
		super.showProgressWithText(show, message);
	}

	@Override
	public void onRequestSuccess(int requestCode) {
		// TODO Auto-generated method stub
		super.onRequestSuccess(requestCode);
		PeriodSleepData periodSleepData = dao.getPeriodSleepData();
		buildView(periodSleepData);

	}

	@SuppressLint("ResourceAsColor")
	private void updateWeekAvera(Object list) {
		List<DailySleepData> dailySleepDataList = (List<DailySleepData>) list;
		int dayCount = 0;
		long totalWeekSleepTime = 0;
		long deepWeekSleepTime = 0;
		long lightWeekSleepTime = 0;
		for (int i = 0; i < dailySleepDataList.size(); i++) {
			if (dailySleepDataList.get(i) != null) {
				DailySleepData dsd = (DailySleepData) dailySleepDataList.get(i);

				totalWeekSleepTime += dsd.getTotalTime();
				deepWeekSleepTime += dsd.getDeepTime();
				lightWeekSleepTime += dsd.getLightTime();
				if (dsd.getTotalTime() > 0) {
					dayCount++;
				}
			}
		}
		hspv2.setColor(R.color.time_color_normal);
		if (dayCount > 0) {
			int hour = (int) (totalWeekSleepTime / dayCount / 3600);
			int min = (int) (totalWeekSleepTime / dayCount / 60 % 60);
			String value = hour + "小时" + min + "分";

			SimpleDateFormat format = new SimpleDateFormat("yyyy");

			int age = Integer.parseInt(format.format(new Date()))
					- Integer.parseInt(customer.getYear()) + 1;
			float totalTime_statand;
			float deepTime_statand;
			if (age <= 3) {
				totalTime_statand = 10 * 3600;
				deepTime_statand = 5 * 3600;
			} else if (age >= 4 && age <= 18) {
				totalTime_statand = 9 * 3600;
				deepTime_statand = (float) 4.5 * 3600;
			} else if (age >= 19 && age <= 45) {
				totalTime_statand = 8 * 3600;
				deepTime_statand = 4 * 3600;
			} else {
				totalTime_statand = 7 * 3600;
				deepTime_statand = (float) 3.5 * 3600;
			}

			hspv1.init(true, value, (int) (totalWeekSleepTime / dayCount),
					(int) (10 * 3600), totalTime_statand);

			hour = (int) (deepWeekSleepTime / dayCount / 3600);
			min = (int) (deepWeekSleepTime / dayCount / 60 % 60);
			value = hour + "小时" + min + "分";
			hspv2.init(false, value, (int) (deepWeekSleepTime / dayCount),
					(int) (5 * 3600), deepTime_statand);
		} else {
			String value = "00小时00分";
			hspv1.init(true, value, 0, 10 * 3600, 0);
			hspv2.init(false, value, 0, 10 * 3600, 0);
		}
	}

	private void updateText(DailySleepData dailySleepData) {
		if (dailySleepData != null) {
			long totalTime = dailySleepData.getTotalTime();
			long deepTime = dailySleepData.getDeepTime();
			long lightTime = dailySleepData.getLightTime();

			int hour = (int) (totalTime / 3600);
			int min = (int) (totalTime % 3600 / 60);
			String hourString = String.valueOf(hour);
			String minString = String.valueOf(min);
			if (hour == 0)
				hourString = "00";
			if (min == 0)
				minString = "00";
			String value = hourString + "小时" + minString + "分钟";
			tv_sleep_totaltime.setText(value);

			hour = (int) (deepTime / 3600);
			min = (int) (deepTime % 3600 / 60);
			hourString = String.valueOf(hour);
			minString = String.valueOf(min);
			if (hour == 0)
				hourString = "00";
			if (min == 0)
				minString = "00";
			value = hourString + "小时" + minString + "分钟";

			tv_deep_sleep_time.setText(value);

			hour = (int) (lightTime / 3600);
			min = (int) (lightTime % 3600 / 60);
			hourString = String.valueOf(hour);
			minString = String.valueOf(min);
			if (hour == 0)
				hourString = "00";
			if (min == 0)
				minString = "00";
			value = hourString + "小时" + minString + "分钟";
			tv_light_sleep_time.setText(value);

			value = dailySleepData.getBeginTime();
			tv_sleep_begintime.setText(value);
			value = dailySleepData.getEndTime();
			tv_sleep_endtime.setText(value);

			SimpleDateFormat format = new SimpleDateFormat("yyyy");

			int age = Integer.parseInt(format.format(new Date()))
					- Integer.parseInt(customer.getYear()) + 1;
			float totalTime_statand;
			float deepTime_statand;
			if (age <= 3) {
				totalTime_statand = 10 * 3600;
				deepTime_statand = 5 * 3600;
			} else if (age >= 4 && age <= 18) {
				totalTime_statand = 9 * 3600;
				deepTime_statand = (float) 4.5 * 3600;
			} else if (age >= 19 && age <= 45) {
				totalTime_statand = 8 * 3600;
				deepTime_statand = 4 * 3600;
			} else {
				totalTime_statand = 7 * 3600;
				deepTime_statand = (float) 3.5 * 3600;
			}

			if (totalTime >= totalTime_statand && deepTime >= deepTime_statand) {// 优质
				iv_xingxing.setImageResource(R.mipmap.icon_sleep_youzhi_big);
			} else if (totalTime < totalTime_statand
					&& deepTime >= deepTime_statand) {// 良好
				iv_xingxing
						.setImageResource(R.mipmap.icon_sleep_lianghao_big);
			} else if (totalTime >= totalTime_statand
					&& deepTime < deepTime_statand) {// 一般
				iv_xingxing.setImageResource(R.mipmap.icon_sleep_yiban_big);
			} else {// 较差
				iv_xingxing.setImageResource(R.mipmap.icon_sleep_jiaocha_big);
			}
		}
	}

	private void buildView(PeriodSleepData periodSleepData) {
		showProgress(false);
		if (periodSleepData != null) {

			ll_bottom.setVisibility(View.VISIBLE);
			rl_handler.setVisibility(View.VISIBLE);
			// rl_bottom_tongji.setVisibility(View.VISIBLE);
			updateText(periodSleepData.getDataList().get(0));

			// TODO Auto-generated method stub
			List<List<DailySleepData>> dailyListCollection = new ArrayList<List<DailySleepData>>();

			if (periodSleepData != null) {
				viewFlipper.removeAllViews();
				List<DailySleepData> dailySleepDataList = periodSleepData
						.getDataList();
				int viewSize = 0;
				int mode = 0;
				if (dailySleepDataList.size() % 7 == 0) {
					viewSize = dailySleepDataList.size() / 7;
					mode = dailySleepDataList.size() % 7;
				} else {
					viewSize = dailySleepDataList.size() / 7 + 1;

				}
				for (int i = 0; i < viewSize; i++) {
					if (i == viewSize - 1) {
						if (mode == 0)
							dailyListCollection.add(dailySleepDataList.subList(
									0, dailySleepDataList.size() - i * 7));
						else
							dailyListCollection.add(dailySleepDataList.subList(
									0, mode));
					} else
						dailyListCollection.add(dailySleepDataList.subList(
								dailySleepDataList.size() - (i + 1) * 7,
								dailySleepDataList.size() - i * 7));
				}
				if (dailyListCollection.size() > 0) {
					for (int i = dailyListCollection.size() - 1; i >= 0; i--) {
						List<DailySleepData> list = dailyListCollection.get(i);
						HistogramSleepView hs = new HistogramSleepView(this,
								standedValue);
						hs.setOnTouchWeekListener(this);
						int[] steps = getSteps(list);

						hs.initData(list, steps);

						// hs.initData(list, null);
						hs.setOnTouchWeekListener(this);
						viewFlipper.addView(hs);
					}
					viewFlipper
							.setDisplayedChild(dailyListCollection.size() - 1);
					HistogramSleepView hs = (HistogramSleepView) viewFlipper
							.getChildAt(dailyListCollection.size() - 1);
					hs.start(2);
				}
			}
		} else {
			viewFlipper.removeAllViews();
			ll_bottom.setVisibility(View.VISIBLE);
			rl_handler.setVisibility(View.VISIBLE);
			// rl_bottom_tongji.setVisibility(View.VISIBLE);
			hspv1.init(true, null, 0, 10 * 3600, 0);
			hspv2.init(false, null, 0, 10 * 3600, 0);
			HistogramSleepView hs = new HistogramSleepView(this, standedValue);
			viewFlipper.addView(hs);
			hs.init();
			hs.start(2);

		}
	}

	double standedValue = (double) 7.5 * 3600;

	private int[] getSteps(List<DailySleepData> list) {
		int[] steps = new int[6];
		int max = 0;
		for (DailySleepData data : list) {
			if (data.getTotalTime() > max)
				max = (int) data.getTotalTime();
		}

		if (max > 12.5 * 3600) {
			max += 4.5 * 3600;
			for (int i = 0; i < steps.length; i++) {
				steps[i] = max * (steps.length - 1 - i) / 6;
			}
		} else {
			steps = new int[] { 45000, 36000, 27000, 18000, 9000, 0 };
		}

		return steps;
	}
}
