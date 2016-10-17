package com.hike.digitalgymnastic.view;

import android.app.Activity;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hike.digitalgymnastic.utils.Constants;
import com.hike.digitalgymnastic.utils.Utils;
import com.hiko.enterprisedigital.R;

//身高
public class DiverRuleHeightLayout extends LinearLayout implements NotifationDp {
	Activity context;
	FrameLayout fl_kedu;
	ScrollView hsv_year;
	DiverRuleHeightScrollView hsv_kedu;
	LinearLayout ll_hsv_year;
	LinearLayout ll_hsv_kedu;
	TextView tv_selected_year;
	LayoutInflater inflater;
	float nDensity;
	int screenWidth;
	int screenHeight;
	int validWidth;
	int startYear = Constants.defaultEndHeight;
	int endYear = Constants.defaultStartHeight;
	int x10;
	public int currentHeight;

	public int getDefaultYear() {
		return defaultYear;
	}

	public void setDefaultYear(int defaultYear) {
		this.defaultYear = defaultYear;
	}

	public int getStartYear() {
		return startYear;
	}

	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}

	public int getEndYear() {
		return endYear;
	}

	public void setEndYear(int endYear) {
		this.endYear = endYear;
	}

	int defaultYear;
	int defaultX;

	public DiverRuleHeightLayout(Activity context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.context = context;
		// init();
	}

	public DiverRuleHeightLayout(Activity context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
		// init();
	}

	public DiverRuleHeightLayout(Activity context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		// init();
	}

	public void init() {
		// TODO Auto-generated method stub
		inflater = context.getLayoutInflater();
		inflate(context, R.layout.diverruleheight, this);
		fl_kedu = (FrameLayout) findViewById(R.id.fl_kedu);
		tv_selected_year = (TextView) findViewById(R.id.tv_selected_year);
		hsv_year = (ScrollView) findViewById(R.id.hsv_year);
		hsv_kedu = (DiverRuleHeightScrollView) findViewById(R.id.hsv_kedu);
		hsv_kedu.setYearScrollView(hsv_year);
		hsv_kedu.setSelectedView(tv_selected_year);
		hsv_kedu.setNotifationDp(this);

		ll_hsv_year = (LinearLayout) findViewById(R.id.ll_hsv_year);
		ll_hsv_kedu = (LinearLayout) findViewById(R.id.ll_hsv_kedu);

		hsv_year.setOnTouchListener(hsv_year_ontouch_listener);
		hsv_kedu.setOnTouchListener(hsv_kedu_ontouch_listener);
		DisplayMetrics mDisplayMetrics = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay()
				.getMetrics(mDisplayMetrics);
		nDensity = (float) 1 / mDisplayMetrics.density;
		screenWidth = context.getWindowManager().getDefaultDisplay().getWidth();
		screenHeight = context.getWindowManager().getDefaultDisplay()
				.getHeight();

		// validWidth = (int)
		// (screenWidth-getResources().getDimensionPixelSize(R.dimen.x9)*2);
		validWidth = getResources().getDimensionPixelSize(R.dimen.y187);
		setUpView();

	}

	private void setUpView() {
		// TODO Auto-generated method stub
		ll_hsv_kedu.measure(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		int heigth = ll_hsv_kedu.getMeasuredHeight();
		View view_year = null;
		View view = inflate(context, R.layout.ruleitembig_horizontal, null);
		int perHeight = Utils.getHeight(view);
		x10 = (int) (perHeight * 1.0 / 10);
		// int x98 = x10 * 3;
		hsv_kedu.setKeduWidth(x10);
		hsv_kedu.setStartyear(startYear);
		int count = startYear / 10 - endYear / 10;
		count += 4;
		ImageView iv_kedu;
		TextView tv = null;

		LinearLayout.LayoutParams params_rl = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, perHeight);
		LayoutParams params_half = new LayoutParams(LayoutParams.WRAP_CONTENT,
				perHeight / 2);

		LayoutParams error_params = new LayoutParams(LayoutParams.WRAP_CONTENT,
				x10);

		for (int i = 0; i < count; i++) {
			view = inflate(context, R.layout.ruleitembig_horizontal, null);

			iv_kedu = (ImageView) view.findViewById(R.id.iv_kedu);
			tv = new TextView(context);
			tv.setTextColor(getResources().getColor(R.color.white));
			if (i == 0 || i == count - 1) {

				tv.setLayoutParams(params_half);
				ll_hsv_year.addView(tv);

				iv_kedu.setImageResource(R.mipmap.item_height_blank);
			} else if (i == 1) {
				view_year = inflate(context,
						R.layout.ruleitembig_horizontal_year, null);
				view_year.setLayoutParams(params_rl);
				ll_hsv_year.addView(view_year);
				iv_kedu.setImageResource(R.mipmap.item_height_1);
			} else if (i == count - 2) {
				view_year = inflate(context,
						R.layout.ruleitembig_horizontal_year, null);
				view_year.setLayoutParams(params_rl);
				TextView tv_year = (TextView) view_year
						.findViewById(R.id.tv_year);
				tv_year.setText(String.valueOf((startYear / 10 - i + 2) * 10));
				ll_hsv_year.addView(view_year);
				iv_kedu.setImageResource(R.mipmap.item_height_2);
			} else {
				view_year = inflate(context,
						R.layout.ruleitembig_horizontal_year, null);
				view_year.setLayoutParams(params_rl);
				TextView tv_year = (TextView) view_year
						.findViewById(R.id.tv_year);
				tv_year.setText(String.valueOf((startYear / 10 - i + 2) * 10));
				ll_hsv_year.addView(view_year);
				iv_kedu.setImageResource(R.mipmap.item_height_0);
			}
			if (i == 0 || i == count - 1) {
				tv = new TextView(context);
				tv.setLayoutParams(error_params);
				ll_hsv_kedu.addView(tv);

				tv = new TextView(context);
				tv.setLayoutParams(error_params);
				ll_hsv_year.addView(tv);
			}
			ll_hsv_kedu.addView(view);
		}
		view_year = inflate(context, R.layout.ruleitembig_horizontal_year, null);
		view_year.setLayoutParams(params_rl);
		TextView tv_year = (TextView) view_year.findViewById(R.id.tv_year);
		ll_hsv_year.addView(view_year);

		Log.d("MyLog", ll_hsv_year.getHeight() + "");
		Log.d("MyLog", ll_hsv_kedu.getHeight() + "");
	}

	public void initKeduScroll() {
		defaultX = (startYear - defaultYear) * x10;
		hsv_kedu.post(new Runnable() {
			@Override
			public void run() {
				hsv_kedu.scrollTo(0, defaultX);
			}
		});

	}

	OnTouchListener hsv_year_ontouch_listener = new OnTouchListener() {

		@Override
		public boolean onTouch(View view, MotionEvent envetn) {

			return true;
		}
	};
	OnTouchListener hsv_kedu_ontouch_listener = new OnTouchListener() {

		@Override
		public boolean onTouch(View view, MotionEvent envetn) {
			return false;
		}
	};

	@Override
	public void changeValue(int value) {
		// TODO Auto-generated method stub
		this.currentHeight = value;
		if (currentHeight > Constants.defaultEndHeight)
			currentHeight = Constants.defaultEndHeight;
		if (currentHeight < Constants.defaultStartHeight)
			currentHeight = Constants.defaultStartHeight;
		tv_selected_year.setText(String.valueOf(currentHeight));
	}

	@Override
	public void changeValue(float value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void changeValue(double value) {
		// TODO Auto-generated method stub

	}
}
