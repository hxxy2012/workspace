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
//年龄
public class DiverRuleAgeLayout extends LinearLayout implements NotifationDp {
	Activity context;
	FrameLayout fl_kedu;
	ScrollView hsv_year;
	DiverRuleAgeScrollView hsv_kedu;
	LinearLayout ll_hsv_year;
	LinearLayout ll_hsv_kedu;
	TextView tv_selected_year;
	LayoutInflater inflater;
	float nDensity;
	int screenWidth;
	int screenHeight;
	int validWidth;
	int startYear =Constants.defaultStartYear;
	int endYear  =Constants.defaultEndYear;
	int x10 ;
	public int currentAge;
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
	public DiverRuleAgeLayout(Activity context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.context = context;
//		init();
	}

	public DiverRuleAgeLayout(Activity context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
//		init();
	}

	public DiverRuleAgeLayout(Activity context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
//		init();
	}
	ScrollView hsv_year_right;
	LinearLayout  ll_hsv_year_right;
	public  void init() {
		// TODO Auto-generated method stub
		inflater = context.getLayoutInflater();
		inflate(context, R.layout.diverruleage, this);
		fl_kedu=(FrameLayout) findViewById(R.id.fl_kedu);
		tv_selected_year=(TextView) findViewById(R.id.tv_selected_year);
		hsv_year = (ScrollView) findViewById(R.id.hsv_year);
		hsv_kedu = (DiverRuleAgeScrollView) findViewById(R.id.hsv_kedu);
		hsv_year_right=(ScrollView) findViewById(R.id.hsv_year_right);
		
		
		
		hsv_kedu.setYearScrollView(hsv_year,hsv_year_right);
		
		hsv_kedu.setSelectedView(tv_selected_year);
		hsv_kedu.setNotifationDp(this);
		
		ll_hsv_year = (LinearLayout) findViewById(R.id.ll_hsv_year);
		ll_hsv_kedu = (LinearLayout) findViewById(R.id.ll_hsv_kedu);
		ll_hsv_year_right= (LinearLayout) findViewById(R.id.ll_hsv_year_right);

		hsv_year.setOnTouchListener(hsv_year_ontouch_listener);
		hsv_year_right.setOnTouchListener(hsv_year_right_ontouch_listener);
		hsv_kedu.setOnTouchListener(hsv_kedu_ontouch_listener);
		DisplayMetrics mDisplayMetrics = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay()
				.getMetrics(mDisplayMetrics);
		nDensity = (float) 1 / mDisplayMetrics.density;
		screenWidth = context.getWindowManager().getDefaultDisplay().getWidth();
		screenHeight = context.getWindowManager().getDefaultDisplay()
				.getHeight();
		
		validWidth = (int) (screenWidth-getResources().getDimensionPixelSize(R.dimen.x9)*2);
		setUpView();

	}

	
	
	private void setUpView() {
		// TODO Auto-generated method stub
		ll_hsv_kedu.measure(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		int heigth = ll_hsv_kedu.getMeasuredHeight();
		View view_year=null;
		View view = inflate(context, R.layout.ruleitembig_horizontal, null);
		int perHeight = Utils.getHeight(view);
		x10 = (int) (perHeight * 1.0 / 10);
//		int x98 = x10 * 3;
		hsv_kedu.setKeduWidth(x10);
		hsv_kedu.setStartyear(endYear);
		int count = endYear / 10 - startYear / 10;
		count += 4;
		ImageView iv_kedu;
		TextView tv = null;
		
		LinearLayout.LayoutParams params_rl=new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,perHeight);
		LayoutParams params_half = new LayoutParams(LayoutParams.WRAP_CONTENT,
				perHeight / 2);
		
		
		LayoutParams  error_params=new LayoutParams(LayoutParams.WRAP_CONTENT,
				x10);
		
		for (int i = 0; i < count; i++) {
			view = inflate(context, R.layout.ruleitembig_horizontal, null);

			iv_kedu = (ImageView) view.findViewById(R.id.iv_kedu);
			tv = new TextView(context);
			tv.setTextColor(getResources().getColor(R.color.umeng_socialize_text_share_content));
			if (i == 0|| i == count - 1) {
				view_year=inflate(context, R.layout.ruleitembig_horizontal_year, null);
				view_year.setLayoutParams(params_rl);
				ll_hsv_year.addView(view_year);
				
				view_year=inflate(context, R.layout.ruleitembig_horizontal_year, null);
				view_year.setLayoutParams(params_rl);
				ll_hsv_year_right.addView(view_year);
				
				tv = new TextView(context);
				tv.setLayoutParams(params_half);
				ll_hsv_kedu.addView(tv);
			} else if (i == 1) {
				view_year=inflate(context, R.layout.ruleitembig_horizontal_year, null);
				view_year.setLayoutParams(params_rl);
				ll_hsv_year.addView(view_year);
				
				view_year=inflate(context, R.layout.ruleitembig_horizontal_year, null);
				view_year.setLayoutParams(params_rl);
				ll_hsv_year_right.addView(view_year);
				
				iv_kedu.setImageResource(R.mipmap.item_height_blank);
				ll_hsv_kedu.addView(view);
			}
//			else if(i == count - 2){
//				view_year=inflate(context, R.layout.ruleitembig_horizontal_year, null);
//				view_year.setLayoutParams(params_rl);
//				ll_hsv_year.addView(view_year);
//				iv_kedu.setImageResource(R.mipmap.item_height_blank);
//				ll_hsv_kedu.addView(view);
//			}
			else {
				view_year=inflate(context, R.layout.ruleitembig_horizontal_year, null);
				view_year.setLayoutParams(params_rl);
				TextView tv_year=(TextView) view_year.findViewById(R.id.tv_year);
				tv_year.setText(String.valueOf((endYear / 10 - i + 2) * 10));
				ll_hsv_year.addView(view_year);
				
				view_year=inflate(context, R.layout.ruleitembig_horizontal_year, null);
				view_year.setLayoutParams(params_rl);
				 tv_year=(TextView) view_year.findViewById(R.id.tv_year);
				tv_year.setText(String.valueOf((endYear / 10 - i + 2) * 10));
				ll_hsv_year_right.addView(view_year);
				
				iv_kedu.setImageResource(R.mipmap.item_height_0);
				ll_hsv_kedu.addView(view);
			}

			
			if(i==0||i==count - 1){
				tv=new TextView(context);
				tv.setLayoutParams(error_params);
				ll_hsv_kedu.addView(tv);
				
				tv=new TextView(context);
				tv.setLayoutParams(error_params);
				ll_hsv_year.addView(tv);
				
				tv=new TextView(context);
				tv.setLayoutParams(error_params);
				ll_hsv_year_right.addView(tv);
			}
			
		}
		view_year=inflate(context, R.layout.ruleitembig_horizontal_year, null);
		view_year.setLayoutParams(params_rl);
		TextView tv_year=(TextView) view_year.findViewById(R.id.tv_year);
		ll_hsv_year.addView(view_year);
		
		view_year=inflate(context, R.layout.ruleitembig_horizontal_year, null);
		view_year.setLayoutParams(params_rl);
		 tv_year=(TextView) view_year.findViewById(R.id.tv_year);
		ll_hsv_year.addView(view_year);
		
		view_year=inflate(context, R.layout.ruleitembig_horizontal_year, null);
		view_year.setLayoutParams(params_rl);
		 tv_year=(TextView) view_year.findViewById(R.id.tv_year);
		 ll_hsv_year_right.addView(view_year);
		
		view_year=inflate(context, R.layout.ruleitembig_horizontal_year, null);
		view_year.setLayoutParams(params_rl);
		 tv_year=(TextView) view_year.findViewById(R.id.tv_year);
		 ll_hsv_year_right.addView(view_year);
		
		view_year=inflate(context, R.layout.ruleitembig_horizontal_year, null);
		view_year.setLayoutParams(params_rl);
		 tv_year=(TextView) view_year.findViewById(R.id.tv_year);
		ll_hsv_kedu.addView(view_year);
		
		
		
		
		
		Log.d("MyLog", ll_hsv_year.getHeight()+"");
		Log.d("MyLog", ll_hsv_kedu.getHeight()+"");
	}

	public void initKeduScroll(){
		defaultX=(endYear-defaultYear)*x10;
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
	OnTouchListener hsv_year_right_ontouch_listener = new OnTouchListener() {

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
		this.currentAge=value;
		if(currentAge>Constants.defaultEndYear)
			currentAge=Constants.defaultEndYear;
		if(currentAge<Constants.defaultStartYear)
			currentAge=Constants.defaultStartYear;
			
		tv_selected_year.setText(String.valueOf(currentAge));
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
