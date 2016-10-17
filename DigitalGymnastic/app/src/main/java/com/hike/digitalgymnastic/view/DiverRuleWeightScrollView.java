package com.hike.digitalgymnastic.view;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hike.digitalgymnastic.utils.Constants;
//体重布局类
public class DiverRuleWeightScrollView extends HorizontalScrollView {
	public double currentWeight;
	
	NotifationDp notifationDp;

	public NotifationDp getNotifationDp() {
		return notifationDp;
	}

	public void setNotifationDp(NotifationDp notifationDp) {
		this.notifationDp = notifationDp;
	}

	View yearView;
	TextView selectedView;
	int x10;
	float startyear;
	float scrollIndex;
	int scrollIndexInt;
	String suffix="kg";
	

	public float getStartyear() {
		return startyear;
	}

	public void setStartyear(float startyear) {
		this.startyear = startyear;
	}

	public DiverRuleWeightScrollView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init();
	}

	public DiverRuleWeightScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	public DiverRuleWeightScrollView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	private void init() {
		// x10=(int) getResources().getDimension(R.dimen.x10);
		setOnTouchListener(listener);
		
		//滚动停止监听
				scrollerTask = new Runnable() {

					public void run() {

						int newY = getScrollX();
						if (initialX - newY == 0) { // stopped
							final int remainder = initialX % x10;
							
							if (remainder == 0) {

							} else {
								if (remainder > x10 / 2) {
									DiverRuleWeightScrollView.this.post(new Runnable() {
										@Override
										public void run() {
											DiverRuleWeightScrollView.this.smoothScrollTo(
													initialX - remainder + x10,
													0);
										}
									});
								} else {
									DiverRuleWeightScrollView.this.post(new Runnable() {
										@Override
										public void run() {
											DiverRuleWeightScrollView.this.smoothScrollTo(
													initialX - remainder, 0);
										}
									});
								}

							}

						} else {
							initialX = getScrollX();
							DiverRuleWeightScrollView.this.postDelayed(scrollerTask,
									newCheck);
						}
					}
				};
	}

	Runnable scrollerTask;
	int initialX;
	int newCheck = 50;

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_UP) {

			startScrollerTask();
		}
		return super.onTouchEvent(ev);
	}

	public void startScrollerTask() {

		initialX = getScrollX();
		this.postDelayed(scrollerTask, newCheck);
	}
	boolean isScrolling=false;
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		if (yearView != null) {
			yearView.scrollTo(l, t);
		}
		if (x10 != 0) {
			scrollIndex =(float)l / x10;
			
			if(scrollIndexInt!=l / x10){
				isScrolling=false;
				scrollIndexInt=l / x10;
			}
			
			if (l >= oldl) {
				if(!isScrolling){
					isScrolling=false;
					if(scrollIndex-l / x10>0.01&&scrollIndex-l / x10<=0.50){
						isScrolling=true;
//						smoothScrollBy((int)((0.5-(scrollIndex-l / x10))*x10), 0);
						if((l / x10)*0.5+ startyear>=Constants.defaultStartWeigth
								&&(l / x10)*0.5+ startyear<=Constants.defaultEndWeigth)
						selectedView.setText(String.valueOf((l / x10)*0.5
								+ startyear));
					}else{
//						isScrolling=false;
						isScrolling=true;
//						smoothScrollBy((int)((0.5-(scrollIndex-l / x10))*x10-x10), 0);
						if((l / x10)*0.5+ startyear>=Constants.defaultStartWeigth
								&&(l / x10)*0.5+ startyear<=Constants.defaultEndWeigth)
						selectedView.setText(String.valueOf((l / x10)*0.5
								+ startyear));
					}
				}
//				else{
//					isScrolling=false;
//				}
			} else {
				if(!isScrolling){
					if(scrollIndex-l / x10<0.99&&scrollIndex-l / x10>=0.50){
						isScrolling=true;
//						smoothScrollBy(-(int)((0.5-(1-(scrollIndex-l / x10)))*x10), 0);
						if((l / x10)*0.5+ startyear>=Constants.defaultStartWeigth
								&&(l / x10)*0.5+ startyear<=Constants.defaultEndWeigth)
						selectedView.setText(String.valueOf((l / x10)*0.5
								+ startyear));
						
					}else{
//						isScrolling=false;
						isScrolling=true;
//						smoothScrollBy(-(int)((0.5-(1-(scrollIndex-l / x10)))*x10)+x10, 0);
						if((l / x10)*0.5+ startyear>=Constants.defaultStartWeigth
								&&(l / x10)*0.5+ startyear<=Constants.defaultEndWeigth)
						selectedView.setText(String.valueOf((l / x10)*0.5
								+ startyear));
						
					}
				}
//				else{
//					isScrolling=false;
//				}
			}
			currentWeight=(l / x10)*0.5+ startyear;
			notifationDp.changeValue(currentWeight);
		}

	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
	}

	public void setYearScrollView(View view) {
		yearView = view;
	}

	public void setSelectedView(TextView selectedView) {
		this.selectedView = selectedView;
	}
	
	
	public void setKeduWidth(int x10) {
		this.x10 = x10;
	}

	// crollView scrollContent ;
	// scrollContent.setOnTouchListener(

	OnTouchListener listener = new OnTouchListener() {
		private int lastX = 0;
		private int touchEventId = -9983761;
		Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				View scroller = (View) msg.obj;
				if (msg.what == touchEventId) {
					if (lastX == scroller.getScrollX()) {
						handleStop(scroller);
					} else {
						handler.sendMessageDelayed(
								handler.obtainMessage(touchEventId, scroller),
								5);
						lastX = scroller.getScrollX();
					}
				}
			}
		};

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_UP) {
				handler.sendMessageDelayed(
						handler.obtainMessage(touchEventId, v), 5);
			}
			return false;
		}

		// 这里写真正的事件
		private void handleStop(Object view) {
			ScrollView scroller = (ScrollView) view;
			System.out.println(scroller.getScrollX());
			System.out.println(scroller.getWidth());

			smoothScrollBy((int)((1-(scrollIndex-scrollIndexInt))*x10), 0);
//			if (scroller.getScrollX() % x10 != 0) {
//				scroller.scrollTo(x10
//						* (scroller.getScrollX() / x10 + 1),
//						scroller.getScrollY());
//			}

		}

	};
}
