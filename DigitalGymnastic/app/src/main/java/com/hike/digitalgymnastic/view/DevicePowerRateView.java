package com.hike.digitalgymnastic.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import com.hike.digitalgymnastic.utils.Utils;
import com.hiko.enterprisedigital.R;

import java.util.Timer;
import java.util.TimerTask;

public class DevicePowerRateView extends View {

	private static final int state_power_rate = 0;// 电量百分比模式
	private static final int state_connect_device = -1;// 连接手环模式
	private static final int state_sync_init = 1;// 轻触同步数据
	private static final int state_sync_ing = 2;// 数据同步中
	private static final int state_sync_finished = 3;// 数据同步完成

	Context context;
	float density;

	public DevicePowerRateView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		initParams();
	}

	public DevicePowerRateView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
		initParams();
	}

	public DevicePowerRateView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		initParams();
	}

	int[] drawableIds = { R.mipmap.main00, R.mipmap.main01,
			R.mipmap.main02, R.mipmap.main03, R.mipmap.main04,
			R.mipmap.main05, R.mipmap.main06, R.mipmap.main07,
			R.mipmap.main08, R.mipmap.main09, R.mipmap.main10,
			R.mipmap.main11, R.mipmap.main12, R.mipmap.main13,
			R.mipmap.main14, R.mipmap.main15, R.mipmap.main16,
			R.mipmap.main17, R.mipmap.main18, R.mipmap.main19,
			R.mipmap.main20, R.mipmap.main21, R.mipmap.main22,
			R.mipmap.main23, R.mipmap.main24, R.mipmap.main25,
			R.mipmap.main26, R.mipmap.main27, R.mipmap.main28,
			R.mipmap.main29, R.mipmap.main30, R.mipmap.main31,
			R.mipmap.main32, R.mipmap.main33, R.mipmap.main34,
			R.mipmap.main35, };

	int[] colorIds = { R.color.rigth_circle_one, R.color.rigth_circle_two,
			R.color.rigth_circle_three, R.color.rigth_circle_four,
			R.color.rigth_circle_five, R.color.rigth_circle_six,
			R.color.rigth_circle_seven, R.color.rigth_circle_aight,
			R.color.rigth_circle_nine, R.color.rigth_circle_ten,
			R.color.rigth_circle_eleven, R.color.rigth_circle_twelve,
			R.color.rigth_circle_thirteen, R.color.rigth_circle_fourteen,
			R.color.rigth_circle_fifteen, R.color.rigth_circle_sixteen,
			R.color.rigth_circle_seventeen, R.color.rigth_circle_eighteen,
			R.color.left_circle_one, R.color.left_circle_two,
			R.color.left_circle_three, R.color.left_circle_four,
			R.color.left_circle_five, R.color.left_circle_six,
			R.color.left_circle_seven, R.color.left_circle_aight,
			R.color.left_circle_nine, R.color.left_circle_ten,
			R.color.left_circle_eleven, R.color.left_circle_twelve,
			R.color.left_circle_thirteen, R.color.left_circle_fourteen,
			R.color.left_circle_fifteen, R.color.left_circle_sixteen,
			R.color.left_circle_seventeen, R.color.left_circle_eighteen };

	private float rate = (float) 1.0;
	private int viewWidth;//
	private int viewHeight;
	// int progress_radius;
	int circle_big_ID = R.mipmap.circle_big;
	int circle_trans_ID = R.mipmap.circle_trans;

	int progress_width;
	int circle_big_width;

	int progress_radius;
	int circle_big_radius;
	int circle_little_radius;

	int currentIDIndex = 0;
	int currentDrawingIndex = 0;

	int dot_radius = 6;
	Bitmap bitmap;
	Bitmap bitmap_circle_big;
	int currentRate;
	int dialogState = state_power_rate;

	// int padding;

	// 连接手环
	int watchConnectColor = Color.WHITE;
	Paint watchConnectPaint;
	int watchConnectTextSize = (int) getResources().getDimension(R.dimen.x20);

	// 电池电量
	int watchPowerColor = Color.WHITE;
	int watchPower100Color = Color.WHITE;
	Paint watchPowerPaint;
	int watchPowerTextSize = (int) getResources().getDimension(R.dimen.x20);
	int watchPower100TextSize = (int) getResources().getDimension(R.dimen.x25);

	// 数据同步
	int syncRate = 0;

	String little_touch_refresh;
	Timer timer = new Timer();
	TimerTask task;
	TimerTask drawTask;

	class MyTimerTask extends TimerTask {

		@Override
		public void run() {
			postInvalidate();
			if (currentIDIndex < 35)
				currentIDIndex++;
			else
				currentIDIndex = 0;

		}

	}

	class DrawTask extends TimerTask {

		@Override
		public void run() {
			postInvalidate();
			if (currentDrawingIndex < currentRate)
				currentDrawingIndex++;
			else {

			}
		}

	}

	public void initParams() {

		watchConnectPaint = new Paint();
		watchConnectPaint.setAntiAlias(true);
		watchConnectPaint.setColor(watchConnectColor);
		watchConnectPaint.setTextAlign(Align.CENTER);
		watchConnectPaint.setTextSize(watchConnectTextSize);

		watchPowerPaint = new Paint();
		watchPowerPaint.setAntiAlias(true);
		watchPowerPaint.setColor(watchPowerColor);
		watchPowerPaint.setTextAlign(Align.CENTER);
		watchPowerPaint.setTextSize(watchPowerTextSize);

		DisplayMetrics dm = new DisplayMetrics();
		dm = getResources().getDisplayMetrics();
		density = dm.density; // 屏幕密度（像素比例：0.75/1.0/1.5/2.0）

		little_touch_refresh = getResources().getString(
				R.string.little_touch_refresh);
		viewWidth = (int) this.getResources().getDimensionPixelSize(
				R.dimen.x196);
		viewHeight = (int) this.getResources().getDimensionPixelSize(
				R.dimen.x196);
		bitmap = BitmapFactory.decodeResource(getResources(),
				drawableIds[currentIDIndex]);
		bitmap = Utils.bitmapToScale(bitmap, rate);
		// bitmap = Utils.bitmapToScale(bitmap, viewWidth, viewHeight);

		bitmap_circle_big = BitmapFactory.decodeResource(getResources(),
				circle_big_ID);
		bitmap_circle_big = Utils.bitmapToScale(bitmap_circle_big, rate);
		// bitmap_circle_big = Utils.bitmapToScale(bitmap_circle_big,
		// bitmap.getWidth(), bitmap.getHeight());
		Bitmap bmp = BitmapFactory.decodeResource(getResources(),
				circle_trans_ID);
		bmp = Utils.bitmapToScale(bmp, rate);

		progress_width = bitmap.getWidth();
		progress_radius = progress_width / 2;
		circle_big_width = bitmap_circle_big.getWidth();
		circle_big_radius = circle_big_width / 2;
		// circle_little_radius = (circle_big_width - 50) / 2;//
		circle_little_radius = bmp.getWidth() / 2
				+ (circle_big_width / 2 - bmp.getWidth() / 2) / 2;
		dot_radius = (circle_big_width / 2 - bmp.getWidth() / 2) / 2 / 2;

		powerIconLowBitmap = BitmapFactory.decodeResource(getResources(),
				powerIconLowID);
		powerIconHighBitmap = BitmapFactory.decodeResource(getResources(),
				powerIconHighID);
	}

	int powerIconLowID = R.mipmap.icon_power_low;
	int powerIconHighID = R.mipmap.icon_power_high;
	Bitmap powerIconLowBitmap;
	Bitmap powerIconHighBitmap;

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		if (dialogState == state_power_rate) {// 电量百分比模式

			drawPower_rate(canvas);
		}
		if (dialogState == state_connect_device) {// 连接手环模式

			drawWatchConnect(canvas);
		}
		if (dialogState == state_sync_init) {// 轻触同步数据
			drawSyncInit(canvas);
		}
		if (dialogState == state_sync_ing) {// 数据同步中
			drawSyncIng(canvas);
		}
		if (dialogState == state_sync_finished) {// 数据同步完成
			drawSyncFinished(canvas);

		}
		canvas.drawBitmap(bitmap_circle_big,
				+(progress_width - circle_big_width) / 2,
				+(progress_width - circle_big_width) / 2, watchConnectPaint);
	}
	int default_circle_color=R.color.default_circle_color;
	private void drawDefaultCircle(Canvas canvas) {
		// TODO Auto-generated method stub
		Paint paint=new Paint();
		paint.setAntiAlias(true);
		paint.setAlpha(15);//透明度
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(getResources().getColor(default_circle_color));
		for (int i = 0; i < 36; i++) {
			int cx = progress_radius
					+ (int) (circle_little_radius * Math.cos(Math
							.toRadians(-90 + i * 10)));
			int cy = progress_radius
					+ (int) (circle_little_radius * Math.sin(Math
							.toRadians(-90 + i * 10)));
			canvas.drawCircle(cx, cy, dot_radius, paint);
		}
	}
	// 绘制电量状态
	private void drawPower_rate(Canvas canvas) {
		int width = getWidth();
		int height = getHeight();
		int radiusX = width / 2;
		int radiusY = height / 2;
		// 绘制电池电量
		String value = "电池电量";
		Rect rect = new Rect();
		watchPowerPaint.setColor(getResources().getColor(R.color.umeng_socialize_list_item_textcolor));
		watchPowerPaint.setTextSize(watchPowerTextSize);
		watchPowerPaint.getTextBounds(value, 0, value.length(), rect);
		canvas.drawText(value, radiusX+rect.height()/2, radiusY - rect.height()*2/3,
				watchConnectPaint);

		// 绘制左侧的图标
		Rect iconRect = new Rect();
		iconRect.left = radiusX+rect.height()/2 - rect.width() / 2- rect.height()-dp2px(1) ;
		iconRect.right = radiusX+rect.height()/2 - rect.width() / 2-dp2px(1) ;
		iconRect.top = radiusY - rect.height()*2/3 - rect.height() + dp2px(1);
		iconRect.bottom = radiusY - rect.height()*2/3 + dp2px(1);

		if (currentPower <= 5)// 低电量,显示红色标识
		{
			canvas.drawBitmap(powerIconLowBitmap, null, iconRect,
					watchPowerPaint);
		} else {// 低电量,显示蓝色标识
			canvas.drawBitmap(powerIconHighBitmap, null, iconRect,
					watchPowerPaint);
		}
		drawDefaultCircle(canvas);
		// 绘制百分比
		value = String.valueOf(currentPower + "%");
		watchPowerPaint.setColor(getResources().getColor(R.color.umeng_socialize_list_item_textcolor));
		watchPowerPaint.setTextSize(watchPower100TextSize);
		watchPowerPaint.getTextBounds(value, 0, value.length(), rect);
		canvas.drawText(value, radiusX, radiusY + rect.height(),
				watchPowerPaint);

		if (currentRate > 0) {
			for (int i = 0; i < currentRate; i++) {
				watchPowerPaint.setColor(getResources().getColor(colorIds[i]));
				int cx = progress_radius
						+ (int) (circle_little_radius * Math.cos(Math
								.toRadians(-90 + i * 10)));
				int cy = progress_radius
						+ (int) (circle_little_radius * Math.sin(Math
								.toRadians(-90 + i * 10)));

				if (i == currentRate) {
					canvas.drawCircle(cx, cy, (int) (dot_radius * 1.5),
							watchPowerPaint);
				} else {
					canvas.drawCircle(cx, cy, dot_radius, watchPowerPaint);
				}

			}
		}
	}

	// 绘制连接手环状态
	private void drawWatchConnect(Canvas canvas) {
		int width = getWidth();
		int height = getHeight();
		int radiusX = width / 2;
		int radiusY = height / 2;
		String value = "连接手环";
		Rect rect = new Rect();
		watchConnectPaint.setColor(getResources().getColor(R.color.umeng_socialize_list_item_textcolor));
		watchConnectPaint.setTextSize(watchConnectTextSize);
		watchConnectPaint.getTextBounds(value, 0, value.length(), rect);
		canvas.drawText(value, radiusX, radiusY
				+ rect.height() / 2, watchConnectPaint);
		drawDefaultCircle(canvas);
		for (int i = 0; i < 36; i++) {
			watchConnectPaint.setColor(getResources().getColor(colorIds[i]));
			int cx = progress_radius
					+ (int) (circle_little_radius * Math.cos(Math.toRadians(-90
							+ i * 10)));
			int cy = progress_radius
					+ (int) (circle_little_radius * Math.sin(Math.toRadians(-90
							+ i * 10)));

			if (i == currentRate) {
				canvas.drawCircle(cx, cy, (int) (dot_radius * 1.5),
						watchConnectPaint);
			} else {
				canvas.drawCircle(cx, cy, dot_radius, watchConnectPaint);
			}

		}
	}

	// 绘制轻触同步数据初始状态
	private void drawSyncInit(Canvas canvas) {
		int width = getWidth();
		int height = getHeight();
		int radiusX = width / 2;
		int radiusY = height / 2;
		drawDefaultCircle(canvas);
		String value = "轻触同步数据";
		Rect rect = new Rect();
		watchConnectPaint.getTextBounds(value, 0, value.length(), rect);
		canvas.drawText(value, radiusX, radiusY
				+rect.height() / 2, watchConnectPaint);
		
		
	}

	// 绘制轻触同步数据进行中状态
	private void drawSyncIng(Canvas canvas) {
		int width = getWidth();
		int height = getHeight();
		int radiusX = width / 2;
		int radiusY = height / 2;
		String value = "数据同步中";
		Rect rect = new Rect();
		watchConnectPaint.getTextBounds(value, 0, value.length(), rect);
		canvas.drawText(value, radiusX, radiusY
				- rect.height() / 2, watchConnectPaint);
		value = String.valueOf(syncRate + "%");
		canvas.drawText(value, radiusX, radiusY
				+ rect.height()*2, watchConnectPaint);
		//
		bitmap = BitmapFactory.decodeResource(getResources(),
				drawableIds[currentIDIndex]);
		// bitmap = Utils.bitmapToScale(bitmap, viewWidth, viewHeight);
		bitmap = Utils.bitmapToScale(bitmap, rate);
		canvas.drawBitmap(bitmap, 0, 0, watchConnectPaint);
		
		for (int i = 0; i < currentRate; i++) {
			watchConnectPaint.setColor(getResources().getColor(colorIds[i]));
			int cx = progress_radius
					+ (int) (circle_little_radius * Math.cos(Math.toRadians(-90
							+ i * 10)));
			int cy = progress_radius
					+ (int) (circle_little_radius * Math.sin(Math.toRadians(-90
							+ i * 10)));

			if (i == currentRate) {
				canvas.drawCircle(cx, cy, (int) (dot_radius * 1.5),
						watchConnectPaint);
			} else {
				canvas.drawCircle(cx, cy, dot_radius, watchConnectPaint);
			}

		}
		
	}

	// 绘制轻触同步数据完成状态
	private void drawSyncFinished(Canvas canvas) {
		int width = getWidth();
		int height = getHeight();
		int radiusX = width / 2;
		int radiusY = height / 2;
		String value = "数据同步完成";
		Rect rect = new Rect();
		watchConnectPaint.getTextBounds(value, 0, value.length(), rect);
		canvas.drawText(value, radiusX, radiusY
				+rect.height() / 2, watchConnectPaint);
		for (int i = 0; i < currentRate; i++) {
			watchConnectPaint.setColor(getResources().getColor(colorIds[i]));
			int cx = progress_radius
					+ (int) (circle_little_radius * Math.cos(Math.toRadians(-90
							+ i * 10)));
			int cy = progress_radius
					+ (int) (circle_little_radius * Math.sin(Math.toRadians(-90
							+ i * 10)));

			if (i == currentRate) {
				canvas.drawCircle(cx, cy, (int) (dot_radius * 1.5),
						watchConnectPaint);
			} else {
				canvas.drawCircle(cx, cy, dot_radius, watchConnectPaint);
			}

		}
	}

	// 显示数据同步状态
	public void showSyncState() {
		dialogState = state_sync_init;
		postInvalidate();
	}

	// 显示轻触连接状态
	public void showConnectState() {
		dialogState = state_connect_device;
		postInvalidate();
	}

	// 显示电量状态
	public void showPowerState() {
		dialogState = state_power_rate;
		postInvalidate();
	}

	// 显示数据同步中对话框
	public void showSyncProgress() {
		dialogState = state_sync_ing;//
		currentDrawingIndex = 0;
		currentRate = 0;
		currentIDIndex = 0;
		task = new MyTimerTask();
		timer.schedule(task, 0, 30);
	}

	// 关闭数据同步对话框
	public void dismissSyncProgress() {

		dialogState = state_sync_finished;//
		if (task != null) {
			task.cancel();
		}
		if (drawTask != null) {
			drawTask.cancel();
		}
		if (timer != null) {
			timer.purge();
			timer.cancel();
		}
		postInvalidate();
	}

	int currentPower;

	// 更新电量数据
	public void updateLastData(int current, int target) {
		currentPower = current;
		currentRate = (int) (current / ((float)target / 36));
		if (currentRate >= 36)
			currentRate = 36;
		dialogState = state_power_rate;//
		currentDrawingIndex = 0;
		postInvalidate();
		timer = new Timer();
		drawTask = new DrawTask();
		timer.schedule(drawTask, 400, 30);
	}

	// 更新数据同步中状态
	public void updateSyncLastData(int current, int target) {
		currentRate = (int) (current / (target / 36));
		if (currentRate >= 36)
			currentRate = 36;
//		dialogState = state_sync_ing;//
//		currentDrawingIndex = 0;
//		postInvalidate();
//		task = new MyTimerTask();
//		timer.schedule(task, 0, 30);
		
	}
	// 更新数据完成状态
	public void updateSyncLastDataFinshed(int current, int target) {
		currentRate = (int) (current / (target / 36));
		if (currentRate >= 36)
			currentRate = 36;
		dialogState = state_sync_finished;//
		postInvalidate();

	}
	float lastX;
	float lastY;
	private OnTouchListener listener;

	public void setOnTouchListener(OnTouchListener listener) {
		this.listener = listener;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (dialogState == state_connect_device
				|| dialogState == state_sync_init) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				lastX = event.getX();
				lastY = event.getY();
				break;
			case MotionEvent.ACTION_UP:
				if (Math.abs(lastX - event.getX()) > 10
						|| Math.abs(lastY - event.getY()) > 10) {
					return false;
				} else {

					if (listener != null) {
						if (dialogState == state_connect_device)
							listener.onTouchConnect2Device();
//						if (dialogState == state_sync_init)
//							listener.onTouchSyncData();
					}
				}
				break;

			default:
				break;
			}

		}
		return true;
	}

	public interface OnTouchListener {
		public void onTouchConnect2Device();// 连接手环
	}

	/**
	 * @see android.view.View#measure(int, int)
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		widthMeasureSpec = measureWidth(widthMeasureSpec);
		heightMeasureSpec = measureHeight(heightMeasureSpec);
		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
		if (widthMeasureSpec < bitmap.getWidth() && rate == 1.0) {
			rate = (float) widthMeasureSpec / bitmap.getWidth();
			initParams();
		}

	}

	/**
	 * Determines the width of this view
	 * 
	 * @param measureSpec
	 *            A measureSpec packed into an int
	 * @return The width of the view, honoring constraints from measureSpec
	 */
	private int measureWidth(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.EXACTLY) {
			// We were told how big to be
			result = specSize;
		} else {
			// Measure the text
			result = (int) bitmap.getWidth() + getPaddingLeft()
					+ getPaddingRight();
			if (specMode == MeasureSpec.AT_MOST) {
				// Respect AT_MOST value if that was what is called for by
				// measureSpec
				result = Math.min(result, specSize);
			}
		}

		return result;
	}

	/**
	 * Determines the height of this view
	 * 
	 * @param measureSpec
	 *            A measureSpec packed into an int
	 * @return The height of the view, honoring constraints from measureSpec
	 */
	private int measureHeight(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.EXACTLY) {
			// We were told how big to be
			result = specSize;
		} else {
			// Measure the text (beware: ascent is a negative number)
			result = (int) bitmap.getHeight() + getPaddingTop()
					+ getPaddingBottom();
			if (specMode == MeasureSpec.AT_MOST) {
				// Respect AT_MOST value if that was what is called for by
				// measureSpec
				result = Math.min(result, specSize);
			}
		}
		return result;
	}

	private int dp2px(int value) {
		float v = getContext().getResources().getDisplayMetrics().density;
		return (int) (v * value + 0.5f);
	}

	private int sp2px(int value) {
		float v = getContext().getResources().getDisplayMetrics().scaledDensity;
		return (int) (v * value + 0.5f);
	}
}
