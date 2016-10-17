package com.hike.digitalgymnastic.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hike.digitalgymnastic.entitiy.Customer;
import com.hiko.enterprisedigital.R;

public class UISliderView extends LinearLayout implements View.OnTouchListener {
	LinearLayout ll_silder;
	ImageView iv_line_white;
	ImageView iv_kedu_line;
	TextView tv_kedu_value;
	ImageView iv_people;
	public int kcalValue = 500;
	Customer customer;

	int startX;
	int startY;
	int y_iv_line_white;
	int up_padding;
	int down_padding;

	public UISliderView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		inflate(context, R.layout.uisliderview, this);

		init();
	}

	public UISliderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		inflate(context, R.layout.uisliderview, this);
		init();
	}

	public UISliderView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		inflate(context, R.layout.uisliderview, this);
		init();
	}

	@Override
	protected void onAttachedToWindow() {
		// TODO Auto-generated method stub
		super.onAttachedToWindow();

		// 重写该方法用于显示最后选择的客户度

	}

	private void init() {

		ll_silder = (LinearLayout) findViewById(R.id.ll_silder);
		iv_line_white = (ImageView) findViewById(R.id.iv_line_white);
		iv_kedu_line = (ImageView) findViewById(R.id.iv_kedu_line);
		tv_kedu_value = (TextView) findViewById(R.id.tv_kedu_value);
		iv_people = (ImageView) findViewById(R.id.iv_people);
		ll_silder.setOnTouchListener(this);

	}

	public void initParams(Customer customer) {
		this.customer = customer;

		if (!TextUtils.isEmpty(customer.getGoalCalories())) {
			kcalValue = Integer.parseInt(customer.getGoalCalories());
		}
//		initView();
	}

	private void initView() {
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {

				handler.sendEmptyMessage(0);

			}
		}, 500);
	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			int iv_kedu_line_height = iv_kedu_line.getHeight();
			int iv_iv_line_white = iv_line_white.getHeight();
			int[] xy_ll_silder = new int[2];
			int[] xy_iv_line_white = new int[2];
			int[] xy_iv_kedu_line = new int[2];
			ll_silder.getLocationOnScreen(xy_ll_silder);
			iv_line_white.getLocationOnScreen(xy_iv_line_white);
			iv_kedu_line.getLocationOnScreen(xy_iv_kedu_line);
			up_padding = xy_iv_line_white[1] - xy_ll_silder[1];
			down_padding = xy_ll_silder[1] + ll_silder.getHeight()
					- xy_iv_line_white[1];
			int y_0 = xy_iv_kedu_line[1] + iv_kedu_line_height;
			float eachPixHeight = (float) iv_kedu_line_height / 1000;
			int h = ll_silder.getHeight();
			int t_new = (int) (y_0 - kcalValue * eachPixHeight - up_padding);
			int b_new = (int) (y_0 - kcalValue * eachPixHeight + down_padding);
			int l_new = ll_silder.getLeft();
			int r_new = ll_silder.getRight();
			ll_silder.layout(l_new, t_new, r_new, b_new);
			tv_kedu_value.setText(String.valueOf(kcalValue) + "kcal");

		}

	};

	@Override
	public boolean onTouch(View arg0, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			this.startX = (int) event.getRawX();
			this.startY = (int) event.getRawY();
			int[] xy_iv_line_white = new int[2];
			int[] xy_iv_kedu_line = new int[2];
			int[] xy_ll_silder = new int[2];
			iv_line_white.getLocationOnScreen(xy_iv_line_white);
			iv_kedu_line.getLocationOnScreen(xy_iv_kedu_line);
			ll_silder.getLocationOnScreen(xy_ll_silder);
			y_iv_line_white = xy_iv_kedu_line[1];
			up_padding = xy_iv_line_white[1] - xy_ll_silder[1];
			down_padding = xy_ll_silder[1] + ll_silder.getHeight()
					- xy_iv_line_white[1];
			break;
		case MotionEvent.ACTION_MOVE:
			int newX = (int) event.getRawX();
			int newY = (int) event.getRawY();

			int dx = newX - this.startX;
			int dy = newY - this.startY;
			if (dy > 30 || dy < -30)
				break;
			int l = ll_silder.getLeft();
			int r = ll_silder.getRight();
			int t = ll_silder.getTop();
			int b = ll_silder.getBottom();

			int newt = t + dy;
			int newb = b + dy;

			xy_iv_line_white = new int[2];

			iv_line_white.getLocationOnScreen(xy_iv_line_white);
			// 向下滑动
			if (dy > 0
					&& y_iv_line_white + iv_kedu_line.getHeight() <= xy_iv_line_white[1]) {
				newb = y_iv_line_white + iv_kedu_line.getHeight()
						+ down_padding;
				newt = y_iv_line_white + iv_kedu_line.getHeight() - up_padding;
				kcalValue = 0;
			} else if (dy < 0 && y_iv_line_white >= xy_iv_line_white[1]) {
				newb = y_iv_line_white + down_padding;
				newt = y_iv_line_white - up_padding;
				kcalValue = 1000;
			} else if (dy < 0 && newY <= y_iv_line_white) {
				newb = y_iv_line_white + down_padding;
				newt = y_iv_line_white - up_padding;
				kcalValue = 1000;
			} else if (dy > 0
					&& newY >= y_iv_line_white + iv_kedu_line.getHeight()) {
				newb = y_iv_line_white + iv_kedu_line.getHeight()
						+ down_padding;
				newt = y_iv_line_white + iv_kedu_line.getHeight() - up_padding;
				kcalValue = 0;
			} else {
				kcalValue = (y_iv_line_white + iv_kedu_line.getHeight() - xy_iv_line_white[1])
						* 1000 / iv_kedu_line.getHeight();
				ll_silder.layout(l, newt, r, newb);
				this.startX = (int) event.getRawX();
				this.startY = (int) event.getRawY();
			}
			tv_kedu_value.setText(String.valueOf(kcalValue) + "kcal");
			break;
		case MotionEvent.ACTION_UP:
			this.startX = (int) event.getRawX();
			this.startY = (int) event.getRawY();

			break;
		}
		return true;
	}

}
