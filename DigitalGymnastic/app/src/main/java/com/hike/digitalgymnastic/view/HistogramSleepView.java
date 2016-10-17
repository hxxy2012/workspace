package com.hike.digitalgymnastic.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.hiko.enterprisedigital.R;
import com.hike.digitalgymnastic.entitiy.DailySleepData;
import com.hike.digitalgymnastic.utils.Utils;

//睡眠历史记录柱状图
public class HistogramSleepView extends View {
	List<DailySleepData> dataList;
	int goalCalories;
	private OnTouchWeekListener onTouchWeekListener;

	public void setOnTouchWeekListener(OnTouchWeekListener onTouchWeekListener) {
		this.onTouchWeekListener = onTouchWeekListener;
	}

	List<Object> list;// 保存数据集合
	private Paint xLinePaint;// 坐标轴 轴线 画笔：
	private Paint hLinePaint;// 坐标轴水平内部 虚线画笔
	private Paint titlePaint;// 绘制文本的画笔
	private Paint paint;// 矩形画笔 柱状图的样式信息
	private int[] progress;
	private int[] aniProgress;// 实现动画的值
	private final int TRUE = 1;// 在柱状图上显示数字
	private int[] text;// 设置点击事件，显示哪一条柱状的信息

	// 每日目标线的左侧数值背景图
	private Bitmap bitmap_history_chart_left_aimline;
	// 每日目标线的右侧图标
	private Bitmap bitmap_history_chart_right_aimline;
	// 横向的虚线
	private Bitmap bitmap_horiLine;
	// 纵向的虚线
	private Bitmap bitmap_vertLine;
	// 动画显示的位图
	private Bitmap bitmap_deep;
	private NinePatch np_bitmap_deep;
	private Bitmap bitmap_total;
	private NinePatch np_bitmap_total;
	// 动画显示的点击位图

//	private Bitmap bitmap_checked;
	// 顶部的横线
	private Bitmap history_chart_top_line;
	// 坐标轴左侧的数标
	private int[] ySteps;
	// 坐标轴底部的星期数
	private String[] xWeeks;
	private int flag;// 是否使用动画
	private HistogramAnimation ani;

	private float perPixKcal;

	public int[] getProgress() {
		return progress;
	}

	// 睡眠历史初始化数据
	public void initData(List<DailySleepData> dataList, int[] ySteps) {

		// 如果数据集合个数不够7个，用null补全
		if (dataList != null && dataList.size() == 7)
			this.dataList = dataList;
		else {
			this.dataList = new ArrayList<DailySleepData>();
			for (int k = 0; k < 7 - dataList.size(); k++) {
				this.dataList.add(null);
			}
			this.dataList.addAll(dataList);
		}
		this.ySteps = ySteps;
		// this.ySteps = new int[] { 45000, 36000, 27000, 18000, 9000, 0 };

		xWeeks = new String[] { "", "", "", "", "", "", "" };
		progress = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		SimpleDateFormat format = new SimpleDateFormat("MM.dd");
		SimpleDateFormat format_back = new SimpleDateFormat("yyyy-MM-dd");
		int i = 0;

		for (i = 0; i < this.dataList.size(); i++) {
			DailySleepData dtsd = this.dataList.get(i);
			Date date;
			try {
				if (dtsd != null) {
					if (format_back.format(new Date()).equals(
							dtsd.getStatDate())) {
						xWeeks[i] = "今天";
					} else {
						date = format_back.parse(dtsd.getStatDate());
						xWeeks[i] = format.format(date);
					}
					progress[i] = (int) dtsd.getTotalTime();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		text = new int[xWeeks.length];
		aniProgress = new int[xWeeks.length];
		for (i = 0; i < xWeeks.length; i++) {
			text[i] = 0;
			aniProgress[i] = 0;
		}
		leftCount = this.ySteps.length;

		ani = new HistogramAnimation();
		ani.setDuration(1500);
		xLinePaint = new Paint();
		hLinePaint = new Paint();
		titlePaint = new Paint();
		paint = new Paint();
		// 给画笔设置颜色
		xLinePaint.setColor(Color.WHITE);
		hLinePaint.setColor(Color.LTGRAY);
		titlePaint.setColor(Color.BLACK);
		// 加载画图
		bitmap_deep = BitmapFactory.decodeResource(getResources(),
				R.mipmap.icon_history_sleep_deep);
		np_bitmap_deep=new NinePatch(bitmap_deep,bitmap_deep.getNinePatchChunk(),null);
		bitmap_total = BitmapFactory.decodeResource(getResources(),
				R.mipmap.icon_history_sleep_total);
		np_bitmap_total=new NinePatch(bitmap_total,bitmap_total.getNinePatchChunk(),null);

		bitmap_horiLine = BitmapFactory.decodeResource(getResources(),
				R.mipmap.history_chart_iv_horizontalline);
		bitmap_vertLine = BitmapFactory.decodeResource(getResources(),
				R.mipmap.history_chart_iv_verticalline);
		history_chart_top_line = BitmapFactory.decodeResource(getResources(),
				R.mipmap.history_chart_top_line);
		bitmap_history_chart_left_aimline = BitmapFactory.decodeResource(
				getResources(), R.mipmap.history_chart_iv_aimline1);
		bitmap_history_chart_right_aimline = BitmapFactory.decodeResource(
				getResources(), R.mipmap.history_chart_iv_aimline2);
	}

	// 设置柱状图要显示的数据
	public void setProgress(int[] progress) {
		this.progress = progress;
	}

	public HistogramSleepView(Context context) {
		super(context);
		// init();
	}

	public HistogramSleepView(Context context, double goalCalories) {
		super(context);
		this.goalCalories = (int) goalCalories;
		// init();
	}

	public HistogramSleepView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// init();
	}

	public void init() {
		this.goalCalories = (int) goalCalories;
		// ySteps= getYSteps(1000,goalCalories);
		this.ySteps = new int[] { 45000, 36000, 27000, 18000, 9000, 0 };
		SimpleDateFormat format = new SimpleDateFormat("MM.dd");

		xWeeks = new String[] { format.format(Utils.dateAdd(-7)),
				format.format(Utils.dateAdd(-6)),
				format.format(Utils.dateAdd(-5)),
				format.format(Utils.dateAdd(-4)),
				format.format(Utils.dateAdd(-3)),
				format.format(Utils.dateAdd(-2)),
				format.format(Utils.dateAdd(-1)) };
		text = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		text = new int[xWeeks.length];
		aniProgress = new int[xWeeks.length];
		for (int i = 0; i < xWeeks.length; i++) {
			text[i] = 0;
			aniProgress[i] = 0;
		}
		leftCount = this.ySteps.length;

		ani = new HistogramAnimation();
		ani.setDuration(1500);
		xLinePaint = new Paint();
		hLinePaint = new Paint();
		titlePaint = new Paint();
		paint = new Paint();
		// 给画笔设置颜色
		xLinePaint.setColor(Color.WHITE);
		hLinePaint.setColor(Color.LTGRAY);
		titlePaint.setColor(Color.BLACK);
		// 加载画图
		bitmap_deep = BitmapFactory.decodeResource(getResources(),
				R.mipmap.icon_history_sleep_deep);
		np_bitmap_deep=new NinePatch(bitmap_deep,bitmap_deep.getNinePatchChunk(),null);
		bitmap_total = BitmapFactory.decodeResource(getResources(),
				R.mipmap.icon_history_sleep_total);
		np_bitmap_total=new NinePatch(bitmap_total,bitmap_total.getNinePatchChunk(),null);
		bitmap_horiLine = BitmapFactory.decodeResource(getResources(),
				R.mipmap.history_chart_iv_horizontalline);
		bitmap_vertLine = BitmapFactory.decodeResource(getResources(),
				R.mipmap.history_chart_iv_verticalline);
		history_chart_top_line = BitmapFactory.decodeResource(getResources(),
				R.mipmap.history_chart_top_line);
		bitmap_history_chart_left_aimline = BitmapFactory.decodeResource(
				getResources(), R.mipmap.history_chart_iv_aimline1);
		bitmap_history_chart_right_aimline = BitmapFactory.decodeResource(
				getResources(), R.mipmap.history_chart_iv_aimline2);
		invalidate();
	}

	public void start(int flag) {
		this.flag = flag;
		text[text.length - 1] = 1;
		this.startAnimation(ani);
		if (onTouchWeekListener != null) {
			if (dataList != null && dataList.size() > 0
					&& dataList.get(dataList.size() - 1) != null)
				onTouchWeekListener.onWeekTouch(dataList,
						dataList.get(dataList.size() - 1));

		}
	}

	boolean isNeedClear = false;

	public void clear() {
		isNeedClear = true;
		for (int i = 0; i < xWeeks.length; i++) {
			text[i] = 0;
		}
		invalidate();

	}

	// 水平左右留出的的空间
	int hor_padding = 0;
	// 上部和底部留出的空间
	int ver_padding = 50;

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

	void drawBackground(Canvas canvas, int paddingBottom) {
		int width = getWidth();
		// int height = getHeight()-dp2px(20);
		int height = getHeight();
		Paint paintBg = new Paint();
		paintBg.setAntiAlias(true);
		paintBg.setStyle(Paint.Style.FILL);
		paintBg.setColor(getResources().getColor(R.color.bg_chart));
		Rect rect = new Rect(0, 0, getWidth(), getHeight() - paddingBottom);
		// canvas.drawColor(getResources().getColor(R.color.bg_chart));
		canvas.drawRect(rect, paintBg);
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		int width = getWidth();
		int height = getHeight();
		int topLineHeight = history_chart_top_line.getHeight();
		int virtual_horiLine_height = bitmap_horiLine.getHeight();
		virtual_veriLine_width = bitmap_vertLine.getWidth();
		int xAxisLength = width;
		int columCount = xWeeks.length;
		int step = xAxisLength / (columCount + 1);

		titlePaint.setAntiAlias(true);// 抗锯齿效果
		titlePaint.setStyle(Paint.Style.FILL);
		titlePaint.setTextSize(sp2px(textSize));// 字体大小
		titlePaint.setTextAlign(Align.CENTER);
		titlePaint.setColor(Color.GRAY);// 字体颜色
		Rect textBounds = new Rect();
		titlePaint.getTextBounds("00.00", 0, 5, textBounds);

		int week_topPadding = topLineHeight + dp2px(topMargin);
		int line_topPadding = topLineHeight + dp2px(topMargin)
				+ dp2px(bottmoMargin) - textBounds.height();
		hPerWidth = (width - columCount * virtual_veriLine_width)
				/ (columCount + 1);

//		drawBackground(canvas, line_topPadding);
		// 绘制顶部的线条
		Rect rect_top = new Rect();// 柱状图的形状
		// rect_top.left = 0;
		// rect_top.right = width;
		// rect_top.top = 0;
		// rect_top.bottom = history_chart_top_line.getHeight();
		// canvas.drawBitmap(history_chart_top_line, null, rect_top, paint);

		// 设置顶部的日期
		titlePaint.setAntiAlias(true);// 抗锯齿效果
		titlePaint.setStyle(Paint.Style.FILL);
		titlePaint.setTextSize(sp2px(textSize));// 字体大小
		titlePaint.setTextAlign(Align.CENTER);
		titlePaint.setColor(Color.WHITE);// 字体颜色
		titlePaint.setAlpha(153);
		textBounds = new Rect();
		titlePaint.getTextBounds("00.00", 0, 5, textBounds);
		for (int i = 0; i < columCount; i++) {
			if (this.text[i] == TRUE) {
				titlePaint.setAlpha(255);
			} else {
				titlePaint.setAlpha(153);
				titlePaint
						.setColor(getResources().getColor(R.color.week_color));// 字体颜色
			}
			canvas.drawText(xWeeks[i], hPerWidth * (i + 1)
					+ virtual_veriLine_width * i, getHeight()
					- (line_topPadding - textBounds.height()) / 2, titlePaint);
		}

		int leftHeight = height - line_topPadding * 2;// 左侧外周的 需要划分的高度：
		int hPerHeight = (leftHeight - (ySteps.length + 1)
				* virtual_horiLine_height)
				/ leftCount;// 分成6部分
		perPixKcal = ((float) (ySteps[0] - ySteps[ySteps.length - 1]))
				/ (hPerHeight * (ySteps.length - 1) + virtual_horiLine_height
						* ySteps.length);

		paint.setColor(getResources().getColor(R.color.week_color));// 字体颜色
		// // 设置表格部分--横向
		for (int i = 0; i < ySteps.length + 1; i++) {
			Rect rect = new Rect();// 柱状图的形状
			rect.left = 0;
			rect.right = width;
			rect.top = line_topPadding + i * hPerHeight + i
					* virtual_horiLine_height;
			rect.bottom = line_topPadding + i * hPerHeight
					+ virtual_horiLine_height + i * virtual_horiLine_height;
			if (i == 0) {
				rect_top = new Rect();// 柱状图的形状
				rect_top.left = 0;
				rect_top.right = width;
				rect_top.top = line_topPadding + i * hPerHeight + i
						* virtual_horiLine_height;
				rect_top.bottom = line_topPadding + i * hPerHeight + i
						* virtual_horiLine_height
						+ history_chart_top_line.getHeight();
				// canvas.drawBitmap(history_chart_top_line, null, rect_top,
				// paint);
//				canvas.drawLine(0, rect_top.top, width, rect_top.bottom, paint);
			} else if (i == ySteps.length) {

				rect_top = new Rect();// 柱状图的形状
				rect_top.left = 0;
				rect_top.right = width;
				// rect_top.top = height-history_chart_top_line.getHeight();
				// rect_top.bottom = height
				rect_top.top = height - history_chart_top_line.getHeight()
						- line_topPadding;
				rect_top.bottom = height - line_topPadding;
				// canvas.drawBitmap(history_chart_top_line, null, rect_top,
				// paint);
				canvas.drawLine(0, rect_top.top, width, rect_top.bottom, paint);
			} else {
				// canvas.drawBitmap(bitmap_horiLine, null, rect, paint);
			}
		}
		// // 设置表格部分--纵向
		//
		// for (int i = 0; i < columCount; i++) {
		// Rect rect = new Rect();// 柱状图的形状
		// rect.left = (i + 1) * hPerWidth + i * virtual_veriLine_width;
		// rect.right = (i + 1) * hPerWidth + (i + 1) * virtual_veriLine_width;
		// rect.top = line_topPadding;
		// rect.bottom = height - history_chart_top_line.getHeight() * 6;
		// canvas.drawBitmap(bitmap_vertLine, null, rect, paint);
		// }

		// 绘制左边的标尺
		/*
		 * paint.setAntiAlias(true);// 抗锯齿效果 paint.setStyle(Paint.Style.FILL);
		 * paint.setTextAlign(Align.RIGHT);
		 * paint.setTextSize(sp2px(stepTextSize));// 字体大小
		 * paint.setColor(getResources().getColor(R.color.his_left_color));//
		 * 字体颜色 textBounds = new Rect(); paint.getTextBounds("00000", 0, 5,
		 * textBounds); for (int i = 0; i < ySteps.length; i++) { int x =
		 * textBounds.width(); int y = line_topPadding + (i + 1) * hPerHeight +
		 * virtual_horiLine_height + (i + 1) virtual_horiLine_height - dp2px(3);
		 * canvas.drawText(String.valueOf(ySteps[i]), x, y, paint); }
		 */
		if (goalCalories > 0) {
			// 计算目标值的Y轴坐标
			int rh1 = (int) (leftHeight - (leftHeight - hPerHeight - virtual_horiLine_height)
					* ((goalCalories - ySteps[ySteps.length - 1]) / ((float) ySteps[0] - ySteps[ySteps.length - 1])));
			int goalCaloriesY = rh1 + line_topPadding;
			// int goalCaloriesY = line_topPadding+leftHeight/2;
			paint.setAntiAlias(true);// 抗锯齿效果

			paint.setStyle(Paint.Style.FILL);
			paint.setTextSize(sp2px(textSize));// 字体大小
			paint.setColor(Color.WHITE);
			paint.setTextAlign(Align.CENTER);
			textBounds = new Rect();
			paint.getTextBounds("00000", 0, 5, textBounds);
			paint.setTextSize(sp2px(textSize - 2));// 字体大小
			// 画目标值横线
			// canvas.drawLine(0, goalCaloriesY, width - dp2px(20),
			// goalCaloriesY,
			// paint);
			// canvas.drawLine(0, goalCaloriesY, width, goalCaloriesY,
			// paint);
			// 绘制目标线顶部的文字
			paint.setColor(getResources().getColor(R.color.week_color));// 字体颜色
			Rect rect = new Rect();
			paint.getTextBounds("00", 0, 2, rect);
			canvas.drawText("标准", rect.width() / 2 + dp2px(5), goalCaloriesY
					- dp2px(4), paint);
			rect = new Rect();
			paint.getTextBounds("0000", 0, 2, rect);
			canvas.drawText("7.5h", rect.width() / 2 + dp2px(5), goalCaloriesY
					+ rect.height() + dp2px(4), paint);

			// 测量目标值背景图的坐标
			// Rect rect = new Rect();// 画目标消耗左边的背景图
			// rect.left = 0;
			// rect.right = textBounds.width();
			// rect.top = goalCaloriesY - textBounds.height() / 2 - dp2px(1);
			// rect.bottom = goalCaloriesY + textBounds.height() / 2 + dp2px(1);
			// canvas.drawBitmap(bitmap_history_chart_left_aimline, null, rect,
			// paint);

			// 测量字体的位置坐标
			// rect = new Rect();// 画字体
			// rect.left = 0;
			// rect.right = textBounds.width();
			// rect.top = goalCaloriesY - textBounds.height() / 2;
			// rect.bottom = goalCaloriesY + textBounds.height() / 2;
			// canvas.drawText(String.valueOf(goalCalories),
			// textBounds.width() / 2, goalCaloriesY + textBounds.height()
			// / 2 - dp2px(1), paint);

			// 测量目标值右侧背景图的坐标
			// rect = new Rect();// 画目标消耗左边的背景图
			// rect.left = width - dp2px(20);
			// rect.right = width;
			// rect.top = goalCaloriesY - dp2px(10);
			// rect.bottom = goalCaloriesY + dp2px(10);
			// canvas.drawBitmap(bitmap_history_chart_right_aimline, null, rect,
			// paint);
		}
		// 绘制矩形
		if (aniProgress != null && aniProgress.length > 0 && !isNeedClear) {
			isNeedClear = false;
			for (int i = 0; i < aniProgress.length; i++) {// 循环遍历将7条柱状图形画出来
				int value = aniProgress[i];
				paint.setAntiAlias(true);// 抗锯齿效果
				paint.setStyle(Paint.Style.FILL);
				paint.setTextSize(sp2px(textSize));// 字体大小
				paint.setColor(Color.WHITE);// 字体颜色
				paint.setAlpha(255);
				textBounds = new Rect();
				paint.getTextBounds("0", 0, 1, textBounds);

				Rect rect = new Rect();// 柱状图的形状
				rect.left = (i + 1) * hPerWidth + i * virtual_veriLine_width
						- textBounds.width();
				rect.right = (i + 1) * hPerWidth + (i + 1)
						* virtual_veriLine_width + textBounds.width();
				int rh = (int) (leftHeight - (leftHeight - hPerHeight - virtual_horiLine_height)
						* ((value - ySteps[ySteps.length - 1]) / ((float) ySteps[0] - ySteps[ySteps.length - 1])));
				rect.top = rh + line_topPadding - virtual_horiLine_height-dp2px(8);
				// if (rect.top <= line_topPadding + textBounds.height()
				// + dp2px(2))
				// rect.top = line_topPadding + textBounds.height() + dp2px(2);
				if (rect.top <= height- hPerHeight * 6 - history_chart_top_line.getHeight()* 6)
					rect.top = height- hPerHeight * 6- history_chart_top_line.getHeight() * 6;
				rect.bottom = height - history_chart_top_line.getHeight()
						- line_topPadding-dp2px(8);

				if (dataList != null && dataList.get(i) != null
						&& dataList.get(i).getTotalTime() > 0)
					// canvas.drawBitmap(bitmap_normal, null, rect, paint);

					// if(dataList!=null&&dataList.get(i)!=null){
					// DailySleepData dsd=dataList.get(i);
					// rect.top=(int)
					// (rect.bottom-(rect.bottom-rect.top)*(float)dsd.getDeepTime()/dsd.getTotalTime());
					// paint.setColor(Color.rgb(79, 70, 122));//设置底部的深色矩形颜色
					// canvas.drawRect(rect, paint);
					// }

					// 是否显示柱状图上方的数字
					if (dataList != null && dataList.get(i) != null) {
						int top = rect.top;
						paint.setColor(Color.rgb(119, 109, 172));// 设置底部的总长度矩形颜色
//						canvas.drawRect(rect, paint);
						np_bitmap_total.draw(canvas, rect, paint);



						DailySleepData dsd = dataList.get(i);
						rect.top = (int) (rect.bottom - (rect.bottom - rect.top)
								* (float) dsd.getDeepTime()
								/ dsd.getTotalTime());
						paint.setColor(Color.rgb(79, 70, 122));// 设置底部的深色矩形颜色
//						canvas.drawRect(rect, paint);
						np_bitmap_deep.draw(canvas,rect,paint);
						if (this.text[i] == TRUE) {
							paint.setTextAlign(Align.CENTER);
							int hour = value / 3600;
							int min = value % 3600 / 60;
							String hourStr = String.valueOf(hour);
							String minStr = String.valueOf(min);
							String str = hourStr + "h" + minStr + "min";
							if (hour == 0)
								str = minStr + "min";
							// canvas.drawText(str , (i + 1) * hPerWidth + i
							// * virtual_veriLine_width, rect.top - dp2px(2),
							// paint);
							paint.setColor(Color.WHITE);// 字体颜色
							paint.setAlpha(204);
							canvas.drawText(str, (i + 1) * hPerWidth + i
									* virtual_veriLine_width, top - dp2px(4),
									paint);
							rect.top = top;
//							paint.setAlpha(33);
//							canvas.drawRect(rect, paint);
							if (onTouchWeekListener != null) {
								if (dataList != null && dataList.get(i) != null) {
									onTouchWeekListener.onWeekTouch(null,
											dataList.get(i));
								}
							}
						} else if (xWeeks[i].equals("今天")) {
//							canvas.drawBitmap(bitmap_checked, null, rect, paint);
						}
					}
			}
		}
	}

	// 画中间的横向标尺
	private void drawMiddleLine() {
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

	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			lastX = event.getX();
			break;
		case MotionEvent.ACTION_UP:

			if (Math.abs(lastX - event.getX()) < 10) {
				Paint paint = new Paint();
				paint.setStyle(Paint.Style.FILL);
				paint.setTextSize(sp2px(textSize));// 字体大小
				paint.setColor(Color.parseColor("#6DCAEC"));// 字体颜色
				Rect textBounds = new Rect();
				paint.getTextBounds("00", 0, 2, textBounds);
				int x = (int) event.getX();

				for (int i = 0; i < xWeeks.length; i++) {
					if (x > (i + 1) * hPerWidth + i * virtual_veriLine_width
							- textBounds.width()
							&& x < (i + 1) * hPerWidth + (i + 1)
									* virtual_veriLine_width
									+ textBounds.width()) {
						if (dataList != null && dataList.get(i) != null)
							if (enabled) {
								enabled = false;
								text[i] = 1;
								for (int j = 0; j < xWeeks.length; j++) {
									if (i != j) {
										text[j] = 0;
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
			if (lastX - event.getX() > 20 && lastX != -1) {
				if (onTouchWeekListener != null)
					onTouchWeekListener.onWeekFiller(1);
				lastX = event.getX();
				lastX = -1;
			} else if (event.getX() - lastX > 20 && lastX != -1) {
				if (onTouchWeekListener != null)
					onTouchWeekListener.onWeekFiller(-1);
				lastX = event.getX();
				lastX = -1;
			}
			break;

		default:
			break;
		}

		return true;
	}

	boolean enabled = true;

	private void reset() {
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				enabled = true;
			}
		}, 500);
	}

	private class HistogramAnimation extends Animation {
		protected void applyTransformation(float interpolatedTime,
				Transformation t) {
			super.applyTransformation(interpolatedTime, t);
			if (progress != null) {
				if (interpolatedTime < 1.0f && flag == 2) {
					for (int i = 0; i < aniProgress.length; i++) {
						aniProgress[i] = (int) (progress[i] * interpolatedTime);
					}
				} else {
					for (int i = 0; i < aniProgress.length; i++) {
						aniProgress[i] = progress[i];
					}
				}
			}
			isNeedClear = false;
			invalidate();
		}
	}

	public interface OnTouchWeekListener {
		public void onWeekTouch(Object obj1, Object obj2);

		// -1为showpre，1为shownext
		public void onWeekFiller(int direction);
	}
}