package com.hike.digitalgymnastic.view;

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
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.hike.digitalgymnastic.entitiy.DailyTotalSportData;
import com.hike.digitalgymnastic.utils.Utils;
import com.hiko.enterprisedigital.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//历史记录柱状图
public class HistogramView extends View {
	List<DailyTotalSportData> dataList;
	long animTime=1500;
	public List<DailyTotalSportData> getDataList() {
		return dataList;
	}

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
	private Bitmap bitmap_normal;
	// 动画显示的点击位图
	private Bitmap bitmap_checked;
	// 顶部的横线
//	private Bitmap history_chart_top_line;
	// 坐标轴左侧的数标
	private int[] ySteps;
	// 坐标轴底部的星期数
	private String[] xWeeks;
	private int flag;// 是否使用动画
	private HistogramAnimation ani;

	private float perPixKcal;
	float leftMarginRate=1.5f;

	public int[] getProgress() {
		return progress;
	}
	NinePatch np_normal;
	NinePatch np_checked;
	// 运动页面初始化数据
	public void initData(List<DailyTotalSportData> dataList, int[] ySteps) {

		// 如果数据集合个数不够7个，用null补全
		if (dataList != null && dataList.size() == 7)
			this.dataList = dataList;
		else {
			this.dataList = new ArrayList<DailyTotalSportData>();
			for (int k = 0; k < 7 - dataList.size(); k++) {
				this.dataList.add(null);
			}
			this.dataList.addAll(dataList);
		}
		this.ySteps = ySteps;
		xWeeks = new String[] { "", "", "", "", "", "", "" };
		progress = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		SimpleDateFormat format = new SimpleDateFormat("MM.dd");
		SimpleDateFormat format_back = new SimpleDateFormat("yyyy-MM-dd");
		int i = 0;
		// for (DailyTotalSportData dtsd : dataList) {
		for (i = this.dataList.size() - 1; i >= 0; i--) {
			DailyTotalSportData dtsd = this.dataList.get(i);
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
					progress[i] = (int) (dtsd.getTotalCalories()+0.5);
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
		ani.setDuration(animTime);
		xLinePaint = new Paint();
		hLinePaint = new Paint();
		titlePaint = new Paint();
		paint = new Paint();
		// 给画笔设置颜色
		xLinePaint.setColor(Color.WHITE);
		hLinePaint.setColor(Color.LTGRAY);
		titlePaint.setColor(Color.BLACK);
		// 加载画图
		bitmap_normal = BitmapFactory.decodeResource(getResources(),
				R.mipmap.icon_history_normal);
		bitmap_checked = BitmapFactory.decodeResource(getResources(),
				R.mipmap.icon_history_hong);
		np_normal=new NinePatch(bitmap_normal,bitmap_normal.getNinePatchChunk(),null);
		np_checked=new NinePatch(bitmap_checked,bitmap_checked.getNinePatchChunk(),null);

		bitmap_horiLine = BitmapFactory.decodeResource(getResources(),
				R.mipmap.history_chart_iv_horizontalline);
		bitmap_vertLine = BitmapFactory.decodeResource(getResources(),
				R.mipmap.history_chart_iv_verticalline);
//		history_chart_top_line = BitmapFactory.decodeResource(getResources(),
//				R.mipmap.history_chart_top_line);
		bitmap_history_chart_left_aimline = BitmapFactory.decodeResource(
				getResources(), R.mipmap.history_chart_iv_aimline1);
		bitmap_history_chart_right_aimline = BitmapFactory.decodeResource(
				getResources(), R.mipmap.history_chart_iv_aimline2);
	}

	// 设置柱状图要显示的数据
	public void setProgress(int[] progress) {
		this.progress = progress;
	}

	public HistogramView(Context context) {
		super(context);
		// init();
	}

	public HistogramView(Context context, double goalCalories) {
		super(context);
		this.goalCalories = (int) goalCalories;
		// init();
	}

	public HistogramView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// init();
	}

	public void init(double goalCalories) {
		this.goalCalories = (int) goalCalories;
		ySteps = getYSteps(1000, goalCalories);
		SimpleDateFormat format = new SimpleDateFormat("MM.dd");

		xWeeks = new String[] { format.format(Utils.dateAdd(-7)),
				format.format(Utils.dateAdd(-6)),
				format.format(Utils.dateAdd(-5)),
				format.format(Utils.dateAdd(-4)),
				format.format(Utils.dateAdd(-3)),
				format.format(Utils.dateAdd(-2)),
				format.format(Utils.dateAdd(-1)) };
		text = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		aniProgress = new int[xWeeks.length];
		for (int i = 0; i < xWeeks.length; i++) {
			text[i] = 0;
			aniProgress[i] = 0;
		}
		leftCount = this.ySteps.length;

		ani = new HistogramAnimation();
		ani.setDuration(animTime);
		xLinePaint = new Paint();
		hLinePaint = new Paint();
		titlePaint = new Paint();
		paint = new Paint();
		// 给画笔设置颜色
		xLinePaint.setColor(Color.WHITE);
		hLinePaint.setColor(Color.LTGRAY);
		titlePaint.setColor(Color.BLACK);
		// 加载画图
		bitmap_normal = BitmapFactory.decodeResource(getResources(),
				R.mipmap.icon_history_normal);
		bitmap_checked = BitmapFactory.decodeResource(getResources(),
				R.mipmap.icon_history_hong);
		np_normal=new NinePatch(bitmap_normal,bitmap_normal.getNinePatchChunk(),null);
		np_checked=new NinePatch(bitmap_checked,bitmap_checked.getNinePatchChunk(),null);

		bitmap_horiLine = BitmapFactory.decodeResource(getResources(),
				R.mipmap.history_chart_iv_horizontalline);
		bitmap_vertLine = BitmapFactory.decodeResource(getResources(),
				R.mipmap.history_chart_iv_verticalline);
//		history_chart_top_line = BitmapFactory.decodeResource(getResources(),
//				R.mipmap.history_chart_top_line);
		bitmap_history_chart_left_aimline = BitmapFactory.decodeResource(
				getResources(), R.mipmap.history_chart_iv_aimline1);
		bitmap_history_chart_right_aimline = BitmapFactory.decodeResource(
				getResources(), R.mipmap.history_chart_iv_aimline2);
	}

	public void start(int flag) {
		this.flag = flag;
		text[text.length-1]=1;
		this.startAnimation(ani);
		if (onTouchWeekListener != null) {
			if (dataList != null && dataList.size() > 0
					&& dataList.get(dataList.size() - 1) != null)
				onTouchWeekListener
						.onWeekTouch(dataList.get(dataList.size() - 1));

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
	int stepTextSize = 11;
	// 顶部线宽度
	// int topLineWidth = 15;
	int topMargin = 10;
	int bottmoMargin = 3;
	// 竖直方向虚线宽度
	int virtual_veriLine_width;
	// 竖直方向两条虚线之间距离
	int hPerWidth;

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		drawPage(canvas);

	}
	void drawBackground(Canvas canvas){
		int width = getWidth();
		// int height = getHeight()-dp2px(20);
		int height = getHeight();
		Paint paintBg=new Paint();
		paintBg.setAntiAlias(true);
		paintBg.setColor(getResources().getColor(R.color.bg_chart));
		canvas.drawColor(getResources().getColor(R.color.bg_chart));
		
	}
	private void drawPage(Canvas canvas) {
		// TODO Auto-generated method stub
		
//		drawBackground(canvas);
		int width = getWidth();
		int height = getHeight();
//		int topLineHeight = history_chart_top_line.getHeight();
		int virtual_horiLine_height = bitmap_horiLine.getHeight();
		virtual_veriLine_width = bitmap_vertLine.getWidth();
		int xAxisLength = width;
		int columCount = xWeeks.length;
		int step = xAxisLength / (columCount + 1);

		titlePaint.setAntiAlias(true);// 抗锯齿效果
		titlePaint.setStyle(Paint.Style.FILL);
		titlePaint.setTextSize(sp2px(textSize)*2);// 字体大小
		titlePaint.setTextAlign(Align.CENTER);
		titlePaint.setColor(Color.WHITE);// 字体颜色
		Rect textBounds = new Rect();
		titlePaint.getTextBounds("00.00", 0, 5, textBounds);

		
//		int line_topPadding = topLineHeight + dp2px(topMargin)*2 - textBounds.height();
		int line_topPadding = 1 + dp2px(topMargin);//左侧setp的顶部的位置，注意并不是文本的坐标，而是等分后的第一行的顶部坐标
		int leftHeight = height - (1 + dp2px(topMargin)+textBounds.height()+1+dp2px(bottmoMargin));// 左侧外周的 需要划分的高度：
		int week_topPadding = line_topPadding+leftHeight+1+dp2px(bottmoMargin)+textBounds.height()*2/3;// 日期的上部间距
		hPerWidth =(int) ((width - columCount * virtual_veriLine_width)
				/ (columCount + leftMarginRate));

		// 设置底部的日期
		titlePaint.setTextSize(sp2px(textSize));// 字体大小
		for (int i = 0; i < columCount; i++) {
			if (this.text[i] == TRUE) {
				titlePaint.setAlpha(255);
			} else {
				titlePaint.setAlpha(204);//80%不透明度
			}
			canvas.drawText(xWeeks[i], (int)(hPerWidth * (i + leftMarginRate)
					+ virtual_veriLine_width * i), week_topPadding, titlePaint);
		}
		// 设置表格部分--横向

	
		int hPerHeight = (leftHeight - (ySteps.length + 1)
				* virtual_horiLine_height)
				/ leftCount;// 分成6部分
		perPixKcal = ((float) (ySteps[0] - ySteps[ySteps.length - 1]))
				/ (hPerHeight * (ySteps.length - 1) + virtual_horiLine_height
				* ySteps.length);
		paint.setColor(Color.WHITE);
		paint.setAlpha(40);//10%不透明度
		canvas.drawLine(0, 0, width, virtual_horiLine_height, paint);
		canvas.drawLine(0,  leftHeight+ line_topPadding+dp2px(bottmoMargin), width,
				leftHeight +line_topPadding+dp2px(bottmoMargin), paint);

//		for (int i = 0; i < ySteps.length + 1; i++) {
//			Rect rect = new Rect();// 柱状图的形状
//			rect.left = 0;
//			rect.right = width;
//			rect.top =  i * hPerHeight + i
//					* virtual_horiLine_height;
//			rect.bottom = i * hPerHeight
//					+ virtual_horiLine_height + i * virtual_horiLine_height;
//			if (i == 0||i == ySteps.length) {
//				rect_top = new Rect();// 柱状图的形状
//				rect_top.left = 0;
//				rect_top.right = width;
//				rect_top.top = i * hPerHeight + i
//						* virtual_horiLine_height;
//				rect_top.bottom = i * hPerHeight + i
//						* virtual_horiLine_height
//						+ history_chart_top_line.getHeight();
////				canvas.drawBitmap(history_chart_top_line, null, rect_top, paint);
//				canvas.drawLine(rect_top.left,rect_top.top,rect_top.right,rect_top.bottom,paint);
//			}
//			else {
////				canvas.drawBitmap(bitmap_horiLine, null, rect, paint);
//			}
//		}
		// 设置表格部分--纵向

//		for (int i = 0; i < columCount; i++) {
//			Rect rect = new Rect();// 柱状图的形状
//			rect.left = (i + 1) * hPerWidth + i * virtual_veriLine_width;
//			rect.right = (i + 1) * hPerWidth + (i + 1) * virtual_veriLine_width;
//			rect.top = 0;
//			rect.bottom = height -  virtual_horiLine_height-line_topPadding;
//			canvas.drawBitmap(bitmap_vertLine, null, rect, paint);
//		}

		// 绘制左边的标尺
		paint.setAntiAlias(true);// 抗锯齿效果
		paint.setStyle(Paint.Style.FILL);
		paint.setTextAlign(Align.CENTER);
		paint.setTextSize(sp2px(stepTextSize));// 字体大小
		paint.setColor(Color.WHITE);// 字体颜色
		paint.setAlpha(127);//50%不透明度
		textBounds = new Rect();
		paint.getTextBounds("00000", 0, 5, textBounds);
		for (int i = 0; i < ySteps.length; i++) {
			int x = textBounds.width() / 2 + dp2px(2);
//			int y = line_topPadding+i * hPerHeight
//					+ hPerHeight/2 + textBounds.height()/2;
			int y = line_topPadding+(i+1) * hPerHeight;
			canvas.drawText(String.valueOf(ySteps[i]), x, y, paint);
		}
		if (goalCalories > 0) {
			// 计算目标值的Y轴坐标
			int rh1 = (int) (leftHeight - (leftHeight - hPerHeight - virtual_horiLine_height)
					* ((goalCalories - ySteps[ySteps.length - 1]) / ((float) ySteps[0] - ySteps[ySteps.length - 1])));
			int goalCaloriesY = rh1  - virtual_horiLine_height;
			paint.setAntiAlias(true);// 抗锯齿效果

			paint.setStyle(Paint.Style.FILL);
			paint.setTextSize(sp2px(textSize));// 字体大小
			paint.setColor(Color.WHITE);// 字体颜色
			paint.setAlpha(76);//30%不透明度
			paint.setTextAlign(Align.CENTER);
			textBounds = new Rect();
			paint.getTextBounds("00000", 0, 5, textBounds);
			paint.setTextSize(sp2px(textSize - 2));// 字体大小
			// 画目标值横线
			canvas.drawLine(0, goalCaloriesY, width, goalCaloriesY,
					paint);
			// 测量目标值背景图的坐标
			Rect rect = new Rect();// 画目标消耗左边的背景图
			rect.left = 0;
			rect.right = textBounds.width();
			rect.top = goalCaloriesY - textBounds.height() / 2 - dp2px(1);
			rect.bottom = goalCaloriesY + textBounds.height() / 2 + dp2px(1);
			canvas.drawBitmap(bitmap_history_chart_left_aimline, null, rect,
					paint);

			// 测量字体的位置坐标
			rect = new Rect();// 画字体
			rect.left = 0;
			rect.right = textBounds.width();
			rect.top = goalCaloriesY - textBounds.height() / 2;
			rect.bottom = goalCaloriesY + textBounds.height() / 2;
			// canvas.drawBitmap(bitmap_history_chart_right_aimline, null, rect,
			// paint);
			paint.setColor(Color.parseColor("#d83b5c"));// 字体颜色
			paint.setTypeface(Typeface.DEFAULT_BOLD);
			canvas.drawText(String.valueOf(goalCalories),
					textBounds.width() / 2, goalCaloriesY + textBounds.height()
							/ 2 - dp2px(1), paint);

			// 测量目标值右侧背景图的坐标
//			rect = new Rect();// 画目标消耗左边的背景图
//			rect.left = width - dp2px(16);
//			rect.right = width;
//			rect.top = goalCaloriesY - dp2px(8);
//			rect.bottom = goalCaloriesY + dp2px(8);
//			canvas.drawBitmap(bitmap_history_chart_right_aimline, null, rect,
//					paint);
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
				textBounds = new Rect();
				paint.getTextBounds("00", 0, 1, textBounds);

				Rect rect = new Rect();// 柱状图的形状
//				rect.left = (int)((i + leftMarginRate) * hPerWidth + i * virtual_veriLine_width
//						- textBounds.width());
//				rect.right = (int)((i + leftMarginRate) * hPerWidth + (i + 1)
//						* virtual_veriLine_width + textBounds.width());
				rect.left = (int)((i + leftMarginRate) * hPerWidth + i * virtual_veriLine_width
						- 10);
				rect.right = (int)((i + leftMarginRate) * hPerWidth + (i + 1)
						* virtual_veriLine_width + 10);
				rect.bottom = (ySteps.length * hPerHeight + line_topPadding);
				int rh = (int) (((ySteps.length-1) * hPerHeight )
						* ((value - ySteps[ySteps.length - 1]) / ((float) ySteps[0] - ySteps[ySteps.length - 1])));
				rect.top = rect.bottom  - rh;
				
				if (rect.top <= line_topPadding )
					rect.top = line_topPadding;

//				rect.bottom = height -line_topPadding- history_chart_top_line.getHeight();

				if (dataList != null && dataList.get(i) != null
						&& dataList.get(i).getTotalCalories() > 0)
					np_normal.draw(canvas,rect,paint);
//					canvas.drawBitmap(bitmap_normal, null, rect, paint);
				// 是否显示柱状图上方的数字
				if (dataList != null && dataList.get(i) != null) {
					if (this.text[i] == TRUE) {
						paint.setTextAlign(Align.CENTER);
						canvas.drawText(value + "kcal", (int) ((i + leftMarginRate) * hPerWidth + i
										* virtual_veriLine_width), rect.top - dp2px(5),
								paint);
//						canvas.drawBitmap(bitmap_checked, null, rect, paint);
						np_checked.draw(canvas,rect,paint);

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
			if (enabled) {
				if (Math.abs(lastX - event.getX()) < 10) {
					Paint paint = new Paint();
					paint.setStyle(Paint.Style.FILL);
					paint.setTextSize(sp2px(textSize));// 字体大小
					paint.setColor(Color.parseColor("#6DCAEC"));// 字体颜色
					Rect textBounds = new Rect();
					paint.getTextBounds("0000", 0, 4, textBounds);
					int x = (int) event.getX();

					for (int i = 0; i < xWeeks.length; i++) {
						if (x > (i + 1) * hPerWidth + i
								* virtual_veriLine_width - textBounds.width()
								&& x < (i + 1) * hPerWidth + (i + 1)
										* virtual_veriLine_width
										+ textBounds.width()) {
							if (dataList != null
									&& dataList.get(i)!=null) {

								enabled = false;
								text[i] = 1;
								for (int j = 0; j < xWeeks.length; j++) {
									if (i != j) {
										text[j] = 0;
									}
								}
								if (onTouchWeekListener != null) {
									if (dataList != null
											&& dataList.get(i) != null)
										onTouchWeekListener
												.onWeekTouch(dataList.get(i));

								}
								if (Looper.getMainLooper() == Looper.myLooper()) {
									invalidate();
								} else {
									postInvalidate();
								}
								reset();
							}
							break;
						}
					}
				}
			}
			break;
		case MotionEvent.ACTION_MOVE:
			
				if (lastX - event.getX() > 20 && lastX != -1) {
					if (onTouchWeekListener != null&&isFillabled) {
						isFillabled = false;
						onTouchWeekListener.onWeekFiller(1);
					}
					lastX = -1;
				} else if (event.getX() - lastX > 20 && lastX != -1) {
					if (onTouchWeekListener != null&&isFillabled) {
						isFillabled = false;
						onTouchWeekListener.onWeekFiller(-1);
					}
					lastX = -1;
				}
				resetFillabled();
			break;

		default:
			break;
		}

		return true;
	}

	boolean enabled = true;
	boolean isFillabled = true;

	private void resetFillabled() {
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				isFillabled = true;
			}
		}, 1000);
	}

	private void reset() {
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				enabled = true;
			}
		}, 200);
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
		public void onWeekTouch(Object obj);

		// -1为showpre，1为shownext
		public void onWeekFiller(int direction);
	}

	private int[] getYSteps(int maxValue, double targetCal) {
		int[] ySteps = new int[6];
		int height = maxValue / (ySteps.length);
		maxValue+=height;
		
		if (targetCal >= maxValue) {
			maxValue = (int) (targetCal * 2);
		}
		int extra = 0;
		 height = maxValue / (ySteps.length);
		if (height >= 0 && height < 100) {
			extra = 10;
			height = height / 10 * 10 + extra;
		} else if (height >= 100 && height < 1000) {
			extra = 100;
			height = height / 100 * 100 + extra;
		} else if (height >= 1000 && height < 10000) {
			extra = 1000;
			height = height / 1000 * 1000 + extra;
		} else if (height >= 10000 && height < 100000) {
			extra = 10000;
			height = height / 10000 * 10000 + extra;
		} else {
			extra = 100000;
			height = height / 100000 * 100000 + extra;
		}

		for (int i = 0; i < ySteps.length; i++) {
			ySteps[i] = height * (ySteps.length-1 - i);
		}
		return ySteps;
	}
}