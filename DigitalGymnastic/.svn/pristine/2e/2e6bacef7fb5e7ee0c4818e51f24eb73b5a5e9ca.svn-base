package com.hike.digitalgymnastic.view;

import android.app.Activity;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hike.digitalgymnastic.utils.Constants;
import com.hike.digitalgymnastic.utils.Utils;
import com.hiko.enterprisedigital.R;

public class DiverRuleWeightLayout extends LinearLayout implements NotifationDp{
	Activity context;
	FrameLayout fl_kedu;
	HorizontalScrollView hsv_year;
	DiverRuleWeightScrollView hsv_kedu;
	LinearLayout ll_hsv_year;
	LinearLayout ll_hsv_kedu;
	TextView tv_selected_year;
	LayoutInflater inflater;
	float nDensity;
	int screenWidth;
	int screenHeight;
	int validWidth;
	float startYear = Constants.defaultStartWeigth;
	float endYear = Constants.defaultEndWeigth;
	Float defaultYear ;
	int defaultX;
	int x10;
	public double currentWeight;
	
	public float getStartYear() {
		return startYear;
	}

	public void setStartYear(float startYear) {
		this.startYear = startYear;
	}

	public float getEndYear() {
		return endYear;
	}

	public void setEndYear(float endYear) {
		this.endYear = endYear;
	}

	public Float getDefaultYear() {
		return defaultYear;
	}

	public void setDefaultYear(Float defaultYear) {
		this.defaultYear = defaultYear;
	}

	public DiverRuleWeightLayout(Activity context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.context = context;
//		init();
	}

	public DiverRuleWeightLayout(Activity context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
//		init();
	}

	public DiverRuleWeightLayout(Activity context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
//		init();
	}

	public void init() {
		// TODO Auto-generated method stub
		inflater = context.getLayoutInflater();
		inflate(context, R.layout.diverruleweight, this);
		fl_kedu = (FrameLayout) findViewById(R.id.fl_kedu);
		tv_selected_year = (TextView) findViewById(R.id.tv_selected_year);
		hsv_year = (HorizontalScrollView) findViewById(R.id.hsv_year);
		hsv_kedu = (DiverRuleWeightScrollView) findViewById(R.id.hsv_kedu);
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

		validWidth = (int) (screenWidth - getResources().getDimensionPixelSize(
				R.dimen.x9) * 2);
		setUpView();

	}

	private void setUpView() {
		// TODO Auto-generated method stub
		View view = inflate(context, R.layout.ruleitembig, null);
		TextView tv = null;
		 x10 = Utils.getWidth(view);
		int x98 = x10 * 3;
		hsv_kedu.setKeduWidth(x10);
		hsv_kedu.setStartyear(startYear);

		LinearLayout.LayoutParams kedu_params = new LinearLayout.LayoutParams(
				x10, LayoutParams.WRAP_CONTENT);
		LinearLayout.LayoutParams year_params = new LayoutParams(x98,
				LayoutParams.WRAP_CONTENT);
		year_params.gravity = Gravity.CENTER;

		// ǰ����ಿ�ֿ�ʼ
		if (validWidth / x10 % 2 == 0) {
			validWidth += x10;
		}
		int extraCount = validWidth / x10 / 2;

		LinearLayout.LayoutParams year_extra_params = new LayoutParams(
				extraCount * x10, LayoutParams.WRAP_CONTENT);
		for (int i = 0; i < extraCount; i++) {
			view = inflate(context, R.layout.ruleitembig, null);
			view.setVisibility(View.INVISIBLE);
			ll_hsv_kedu.addView(view);

			
		}
		for (int i = 0; i < extraCount; i++) {
			if (i == extraCount - 1) {
				if (String.valueOf(startYear).endsWith("0.0")||String.valueOf(startYear).endsWith("5.0")) {
					view = inflate(context, R.layout.yearitem, null);
					view.setLayoutParams(year_params);
					((TextView) view).setGravity(Gravity.CENTER);
					((TextView) view).setText(String.valueOf((int)(startYear)));
					ll_hsv_year.addView(view);
					break;
				}
			}
			view = new View(context);
			year_extra_params = new LayoutParams(x10,
					LayoutParams.WRAP_CONTENT);
			view.setLayoutParams(year_extra_params);
			view.setVisibility(View.INVISIBLE);
			ll_hsv_year.addView(view);
		}

		// ǰ����ಿ�ֽ���
		for (float i = startYear; i < endYear + 0.5; i += 0.5) {
			
			//���̶�
			if (i / 0.5 % 20 == 0 ) {
				view = inflate(context, R.layout.ruleitembig, null);
				ll_hsv_kedu.addView(view);

			} else if(i / 0.5 % 10 == 0){
				view = inflate(context, R.layout.ruleitemmiddle, null);
				ll_hsv_kedu.addView(view);
			}else {
				view = inflate(context, R.layout.ruleitemsmall, null);
				ll_hsv_kedu.addView(view);
			}
			//���̶Ƚ���
			//��ֵ
			if (String.valueOf(i).endsWith("9.5")||String.valueOf(i).endsWith("4.5")) {
				view = inflate(context, R.layout.yearitem, null);
				view.setLayoutParams(year_params);
				((TextView) view).setGravity(Gravity.CENTER);
				((TextView) view).setText(String.valueOf((int)(i + 0.5)));
				ll_hsv_year.addView(view);

			}else if (!String.valueOf(i).endsWith("9.5")
					&& !String.valueOf(i).endsWith("0.0")
					&& !String.valueOf(i).endsWith("0.5")
					&&!String.valueOf(i).endsWith("4.5")
					&&!String.valueOf(i).endsWith("5.0")
					&&!String.valueOf(i).endsWith("5.5")
					
					) {
				view = new View(context);
				kedu_params = new LayoutParams(x10,
						LayoutParams.WRAP_CONTENT);
				view.setLayoutParams(kedu_params);
				ll_hsv_year.addView(view);
			}else if ((String.valueOf(i).endsWith("0.5")||String.valueOf(i).endsWith("5.5")) && i == startYear) {
				view = new View(context);
				kedu_params = new LayoutParams(x10,
						LayoutParams.WRAP_CONTENT);
				view.setLayoutParams(kedu_params);
				ll_hsv_year.addView(view);
			}
			
			//��ֵ����
		}
		// ������ಿ�ֿ�ʼ
		for (int i = 0; i < extraCount; i++) {
			view = inflate(context, R.layout.ruleitembig, null);
			view.setVisibility(View.INVISIBLE);
			ll_hsv_kedu.addView(view);
			view = new View(context);
			year_extra_params = new LayoutParams(x10,
					LayoutParams.WRAP_CONTENT);
			view.setVisibility(View.INVISIBLE);
			view.setLayoutParams(year_extra_params);
			ll_hsv_year.addView(view);

		}
		// ������ಿ�ֽ���
		// ��ʼ���������

		LinearLayout.LayoutParams fl_params = (LayoutParams) fl_kedu
				.getLayoutParams();
		fl_params.width = validWidth - validWidth % x10;
		fl_kedu.setLayoutParams(fl_params);
		
	}

	public void initKeduScroll() {
		
		defaultX = (int)((defaultYear - startYear)/0.5) * x10 ;
//		hsv_kedu.setScrollX(defaultX);
		hsv_kedu.post(new Runnable() {
			@Override
			public void run() {
				hsv_kedu.scrollTo(defaultX, 0);
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
		
	}

	@Override
	public void changeValue(float value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeValue(double value) {
		// TODO Auto-generated method stub
		this.currentWeight=value;
	}
}
