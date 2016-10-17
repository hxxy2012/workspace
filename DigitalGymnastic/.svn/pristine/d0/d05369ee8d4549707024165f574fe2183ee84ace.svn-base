package com.hike.digitalgymnastic.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.hike.digitalgymnastic.entitiy.HomeSleepData;
import com.hike.digitalgymnastic.entitiy.SectionSleepData;
import com.hiko.enterprisedigital.R;

import java.util.List;


//睡眠首页下半部分，睡眠情况视图
public class HomeSleepStateView extends View {
	HomeSleepData homeSleepData;
	List<SectionSleepData> dataList;
	private Paint titlePaint;// 绘制文本的画笔
	private Paint paint;// 矩形画笔 横线的样式信息

	// 横向的虚线
	private Bitmap bitmap_horiLine;
	// 夜晚图标
	private Bitmap bitmap_xingxing;

	// 运动页面初始化数据
	public void initData(HomeSleepData homeSleepData) {

		this.homeSleepData = homeSleepData;
		this.dataList = homeSleepData.getDataList();
		if (dataList != null && dataList.size() > 0) {
			rects=new Rect[dataList.size()];
			texts=new boolean[dataList.size()];
		}
		postInvalidate();
	}

	public HomeSleepStateView(Context context) {
		super(context);
		init();
	}

	public HomeSleepStateView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public void reDrawWithData(int flag) {

	}

	void init() {
		titlePaint = new Paint();
		titlePaint.setAntiAlias(true);// 抗锯齿效果
		titlePaint.setStyle(Paint.Style.FILL);
		titlePaint.setTextSize(sp2px(textSize));// 字体大小
		titlePaint.setTextAlign(Align.CENTER);
		titlePaint.setColor(getResources().getColor(R.color.text_bodypage_color));// 字体颜色
		
		paint = new Paint();
		bitmap_horiLine = BitmapFactory.decodeResource(getResources(),
				R.mipmap.history_chart_iv_horizontalline);
		bitmap_xingxing = BitmapFactory.decodeResource(getResources(),
				R.mipmap.icon_sleep_moom);
	}

	// 竖直方向的间隔数量
	int stepCount = 7;

	// 水平左右留出的的空间
	int hor_padding = 20;

	// 左侧划分的个数
	int leftCount = 6;
	// 字体大小单位sp
	int textSize = 12;
	int stepTextSize = 12;
	// 顶部线宽度
	// int topLineWidth = 15;
	int topMargin = 20;
	int bottmoMargin = 20;
	// 竖直方向虚线宽度
	int virtual_veriLine_width;
	// 竖直方向两条虚线之间距离
	int hPerWidth;
	// 夜晚标识图标显示的dp数
	int iconSize = 10;

	// 睡眠总时间
	String totalTime = "10小时45分";

	// // 睡眠开始时间
	// String startTime="10:34";
	// // 睡眠结束时间
	// String endTime="6:23";

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int width = getWidth();
		int height = getHeight();
		int virtual_horiLine_height = bitmap_horiLine.getHeight();
		int vert_space_height = height / stepCount - virtual_horiLine_height;

		titlePaint.setTextSize(sp2px(textSize));// 字体大小
		Rect textBounds = new Rect();
		titlePaint.getTextBounds("00.00", 0, 5, textBounds);

		// 绘制左上角的星星图标
		Rect rect_icon = new Rect();
		rect_icon.left = dp2px(hor_padding);
		rect_icon.right = dp2px(hor_padding) + dp2px(iconSize);
		rect_icon.top = (vert_space_height - dp2px(iconSize)) / 2;
		rect_icon.bottom = (vert_space_height + dp2px(iconSize)) / 2;
		canvas.drawBitmap(bitmap_xingxing, null, rect_icon, paint);
		
		
		// 绘制左上角星星图标右边的文字
		
		Rect rect_icon_text = new Rect();
		titlePaint.getTextBounds("0000", 0, 4, rect_icon_text);
		int rect_icon_text_x = dp2px(hor_padding) + dp2px(iconSize)
				+ rect_icon_text.width();
		int rect_icon_text_Y = vert_space_height / 2 + rect_icon_text.height()
				/ 2;
		canvas.drawText("睡眠详图", rect_icon_text_x, rect_icon_text_Y, titlePaint);

		
		// 绘制第一条白线
		titlePaint.setColor(getResources().getColor(R.color.text_bodypage_color));// 字体颜色
		paint.setAntiAlias(true);// 抗锯齿效果
		paint.setStyle(Paint.Style.FILL);
		paint.setTextSize(sp2px(stepTextSize));// 字体大小
		paint.setStrokeWidth(virtual_horiLine_height);
		paint.setColor(getResources().getColor(R.color.white));// 字体颜色
		int startX = 0;
		int startY = vert_space_height;
		int stopX = width;
		int stopY = vert_space_height;
		canvas.drawLine(startX, startY, stopX, stopY, paint);

		// 绘制白线下面的虚线
		for (int i = 1; i < 6; i++) {
			rect_icon = new Rect();
			rect_icon.left = 0;
			rect_icon.right = width;
			rect_icon.top = vert_space_height * (i + 1)
					+ virtual_horiLine_height * i;
			rect_icon.bottom = vert_space_height * (i + 1)
					+ virtual_horiLine_height * (i + 1);
			canvas.drawBitmap(bitmap_horiLine, null, rect_icon, paint);
		}

		// 绘制对底部的白线
		paint.setAntiAlias(true);// 抗锯齿效果
		paint.setStyle(Paint.Style.FILL);
		paint.setTextSize(sp2px(stepTextSize));// 字体大小
		paint.setStrokeWidth(virtual_horiLine_height);
		paint.setColor(getResources().getColor(R.color.white));// 字体颜色
		startX = 0;
		startY = height - virtual_horiLine_height;
		stopX = width;
		stopY = height;
		canvas.drawLine(startX, startY, stopX, stopY, paint);

		if (homeSleepData != null) {
			// 绘制左上角的星星图标最右侧的文字
			titlePaint.setColor(getResources().getColor(R.color.white));// 字体颜色
			rect_icon_text = new Rect();
			titlePaint.getTextBounds("0000000", 0, 7, rect_icon_text);
			rect_icon_text_x = width - dp2px(hor_padding)
					- rect_icon_text.width() / 2;
			rect_icon_text_Y = vert_space_height / 2 + rect_icon_text.height()
					/ 2;
			int hour = (int) (homeSleepData.getTotalTime() / 3600);
			int min = (int) ((homeSleepData.getTotalTime() / 60) % 60);
			canvas.drawText(hour + "小时" + min + "分", rect_icon_text_x,
					rect_icon_text_Y, titlePaint);

			// 绘制开始睡眠时间
			titlePaint.setColor(getResources().getColor(R.color.text_bodypage_color));// 字体颜色
			rect_icon_text = new Rect();
			titlePaint.getTextBounds("00000", 0, 5, rect_icon_text);
			rect_icon_text_x = dp2px(hor_padding) + rect_icon_text.width() / 2;
			rect_icon_text_Y = vert_space_height + virtual_horiLine_height
					+ vert_space_height / 2 + rect_icon_text.height() / 2;
			;
			canvas.drawText(homeSleepData.getBeginTime(), rect_icon_text_x,
					rect_icon_text_Y, titlePaint);

			// 绘制结束睡眠时间
			rect_icon_text = new Rect();
			titlePaint.getTextBounds("00000", 0, 5, rect_icon_text);
			rect_icon_text_x = width - dp2px(hor_padding)
					- rect_icon_text.width() / 2;
			rect_icon_text_Y = vert_space_height + virtual_horiLine_height
					+ vert_space_height / 2 + rect_icon_text.height() / 2;
			;
			canvas.drawText(homeSleepData.getEndTime(), rect_icon_text_x,
					rect_icon_text_Y, titlePaint);
			
			// 绘制点击后顶部的时间提示效果
			for(int j=0;j<texts.length;j++){
				if(texts[j]){
					SectionSleepData data=dataList.get(j);
					String type="";
					if(data.getStatus() == 3)
						type="深度睡眠";
					if(data.getStatus() == 2)
						type="轻度睡眠";
					String value=type+" "+data.getBeginTime()+"-"+data.getEndTime()+" "+"时长"+data.getDuration()/60+"分钟";
					canvas.drawText(value, width/2,
							rect_icon_text_Y, titlePaint);
				}
			}
			
			// 根据时间段数据绘制时间段数据显示部分
			int leftX = dp2px(hor_padding) + rect_icon_text.width() / 2;
			int rigthX = width - dp2px(hor_padding) - rect_icon_text.width()
					/ 2;
			int totalWidth = width - dp2px(hor_padding) * 2
					- rect_icon_text.width();
//			float eachMillWidth = (float) totalWidth
//					/ homeSleepData.getTotalTime();
			float eachMillWidth = (float) totalWidth
					/ getDistance(homeSleepData.getBeginTime(),homeSleepData.getEndTime());
			String startTime = homeSleepData.getBeginTime();
			String endTime = homeSleepData.getEndTime();

			if (dataList != null && dataList.size() > 0) {
				int i = 0;
				int curLeftX = leftX;
				
				for (SectionSleepData data : dataList) {
					curLeftX=leftX+(int) (getDistance(startTime,data.getBeginTime())*eachMillWidth);
					Rect rect = new Rect();
					rect.left = (int) curLeftX;
					rect.right = (int) (curLeftX + data.getDuration()
							* eachMillWidth);
					rect.top = vert_space_height * 2 + virtual_horiLine_height
							* 3;
					rect.bottom = height - virtual_horiLine_height;
//					curLeftX += data.getDuration() * eachMillWidth;
					rects[i]=rect;
					
					// 睡眠状态(1:清醒;2:浅层;3:深层)
					// if(data.getStatus()==1){
					// paint.setColor(getResources().getColor(R.color.white));
					// }else
					if(texts[i]){
						paint.setColor(Color.rgb(216, 96, 143));
						if (data.getStatus() == 2) {
							rect.top=(rect.bottom+rect.top)/2;
						}
						canvas.drawRect(rect, paint);
					}else if (data.getStatus() == 2) {
						paint.setColor(Color.rgb(113, 98, 177));
						rect.top=(rect.bottom+rect.top)/2;
						canvas.drawRect(rect, paint);
					} else if (data.getStatus() == 3) {
						paint.setColor(Color.rgb(70, 58, 117));
						canvas.drawRect(rect, paint);
					}
					i++;
				}
			}

		}

	}

	private long getDistance(String startTime, String currentTime) {
		String[] hms = startTime.split(":", -1);
		String[] hmc = currentTime.split(":", -1);
		long startMill;
		long currentMill;
		if (Integer.parseInt(hms[0]) > 18 && Integer.parseInt(hmc[0]) >= 0
				&& Integer.parseInt(hmc[0]) <= 18) {
			startMill = Integer.parseInt(hms[0]) * 60 * 60
					+ Integer.parseInt(hms[1]) * 60;
			currentMill = (Integer.parseInt(hmc[0]) + 24) * 60 * 60
					+ Integer.parseInt(hmc[1]) * 60;
		} else {
			startMill = Integer.parseInt(hms[0]) * 60 * 60
					+ Integer.parseInt(hms[1]) * 60;
			currentMill = Integer.parseInt(hmc[0]) * 60 * 60
					+ Integer.parseInt(hmc[1]) * 60;
		}

		
		return currentMill - startMill;

	}

	private int dp2px(int value) {
		float v = getContext().getResources().getDisplayMetrics().density;
		return (int) (v * value + 0.5f);
	}

	private int sp2px(int value) {
		float v = getContext().getResources().getDisplayMetrics().scaledDensity;
		return (int) (v * value + 0.5f);
	}

	float lastX;
	Rect[] rects;
	boolean texts[];
	boolean enabled=true;
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			lastX = event.getX();
			break;
		case MotionEvent.ACTION_UP:

			if (Math.abs(lastX - event.getX()) < 10) {
				
				int x = (int) event.getX();
				if (dataList != null && dataList.size() > 0) 
				for (int i = 0; i < dataList.size(); i++) {
					if (x > rects[i].left
							&& x < rects[i].right ) {
							if(enabled){
								enabled=false;
								texts[i] = true;
								for (int j = 0; j < texts.length; j++) {
									if (i != j) {
										texts[j] = false;
									}
								}
								if (Looper.getMainLooper() == Looper.myLooper()) {
									invalidate();
								} else {
									postInvalidate();
								}
								reset();
							}
					}
				}
			}
			break;
		case MotionEvent.ACTION_MOVE:
			
			break;

		default:
			break;
		}

		return true;
	}
	private void reset(){
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				enabled=true;
			}
		}, 500);
	}
}