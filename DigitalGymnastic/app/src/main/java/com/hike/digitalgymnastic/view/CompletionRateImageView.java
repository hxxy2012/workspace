package com.hike.digitalgymnastic.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import com.hike.digitalgymnastic.utils.Constants;
import com.hike.digitalgymnastic.utils.Utils;
import com.hiko.enterprisedigital.R;

import java.util.Timer;
import java.util.TimerTask;

public class CompletionRateImageView extends View {
	Context context;
	float density;

	public CompletionRateImageView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		this.context = context;
		initParams();
	}

	public CompletionRateImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
		initParams();
	}

	public CompletionRateImageView(Context context) {
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
	int default_circle_color=R.color.default_circle_color;
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

	int currentIDIndex = 0;// ��ǰ��ʾ�Ķ���ͼƬ������
	int currentDrawingIndex = 0;// ����ռ�ȴ�0��ʼ����Բ������ĵ�

	int dot_radius = 6;
	Bitmap bitmap;
	Bitmap newBitmap;
	Bitmap bitmap_circle_big;
	int currentRate;// ��ǰ��ɱ���
	int dialogState = Constants.progress_init_state;// Ĭ��Ϊ0����ʾ��1��ʾ����Ч����2��ʾ����Ч��

	// int padding;

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
			
			if (currentDrawingIndex < currentRate){
				postInvalidate();
				currentDrawingIndex++;
			}
				
			else {

			}
		}

	}

	public void initParams() {
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
				+ (circle_big_width / 2 - bmp.getWidth() / 2) / 2+1;
		dot_radius = (circle_big_width / 2 - bmp.getWidth() / 2) / 2 / 2;

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		Paint paint = new Paint();
		paint.setAntiAlias(true);
		
		if(bitmap!=null)
			bitmap.recycle();
		if(newBitmap!=null)
			newBitmap.recycle();
		
		if (dialogState == Constants.progress_init_state) {

		}
		if (dialogState == Constants.progress_showing_state) {
			bitmap = BitmapFactory.decodeResource(getResources(),
					drawableIds[currentIDIndex]);
			// bitmap = Utils.bitmapToScale(bitmap, viewWidth, viewHeight);
			
			try{
				newBitmap = Utils.bitmapToScale(bitmap, rate);
				canvas.drawBitmap(newBitmap, 0, 0, paint);
				bitmap.recycle();
			}catch(Exception e){
				e.printStackTrace();
				bitmap = Utils.bitmapToScale(bitmap, rate);
				canvas.drawBitmap(bitmap, 0, 0, paint);
			}
		}
		if (dialogState == Constants.progress_finish_state && currentRate > 0) {
			// bitmap = BitmapFactory.decodeResource(getResources(),
			// drawableIds[currentRate]);
			// bitmap = Utils.bitmapToScale(bitmap,rate);
			// canvas.drawBitmap(bitmap, 0, 0, paint);
		}
		if(bitmap_circle_big.isRecycled()){
			bitmap_circle_big = BitmapFactory.decodeResource(getResources(),
					circle_big_ID);
			bitmap_circle_big = Utils.bitmapToScale(bitmap_circle_big, rate);
		}
		//绘制背景圆环
		canvas.drawBitmap(bitmap_circle_big,
				+(progress_width - circle_big_width) / 2,
				+(progress_width - circle_big_width) / 2, paint);
		//绘制背景小圆点
		drawDefaultCircle(canvas);
		if (dialogState == Constants.progress_finish_state && currentRate > 0) {
			for (int i = 0; i < currentDrawingIndex; i++) {
				paint.setColor(getResources().getColor(colorIds[i]));
				int cx = progress_radius
						+ (int) (circle_little_radius * Math.cos(Math
								.toRadians(-90 + (i+1) * 10)));
				int cy = progress_radius
						+ (int) (circle_little_radius * Math.sin(Math
								.toRadians(-90 + (i+1) * 10)));

				if (i+1 == currentRate) {
					canvas.drawCircle(cx, cy, (int) (dot_radius * 1.5), paint);
				} else {
					canvas.drawCircle(cx, cy, dot_radius, paint);
				}

			}

		}
	}

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

	// 显示对话框
	public void showProgress() {
		dialogState = Constants.progress_showing_state;// ��ʾ����Ч��
		currentDrawingIndex = 0;
		currentRate = 0;
		currentIDIndex = 0;
		timer=new Timer();
		task = new MyTimerTask();
		timer.schedule(task, 0, 30);
	}
	
	public boolean isShowingDailog(){
		if(dialogState == Constants.progress_showing_state)
			return true;
		else
			return false;
	}
	// 关闭对话框
	public void dismissProgress() {

		dialogState = Constants.progress_init_state;// �رն�����
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

	// 更新数据
	public void updateLastData(double current, double target) {
		currentRate = (int) (current / (target / 36));
		if (currentRate >= 36)
			currentRate = 36;
		dialogState = Constants.progress_finish_state;// �رն�����
		postInvalidate();
		timer = new Timer();
		drawTask = new DrawTask();
		timer.schedule(drawTask, 400, 30);
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
		if (dialogState == Constants.progress_init_state
				|| dialogState == Constants.progress_finish_state) {// Ĭ��״̬���߽���״̬�������
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
					if(listener!=null)
						listener.onTouch();
				}
				break;

			default:
				break;
			}

		}
		return true;
	}

	public interface OnTouchListener {
		public void onTouch();
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
}
