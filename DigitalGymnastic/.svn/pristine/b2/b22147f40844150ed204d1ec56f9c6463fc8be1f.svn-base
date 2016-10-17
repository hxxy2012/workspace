package com.hike.digitalgymnastic.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.hike.digitalgymnastic.utils.Utils;
import com.hiko.enterprisedigital.R;

//目标设定圆环
public class TargetSetImageView extends View {

	public static final int init_value_status = 0;
	public static final int select_value_status = 1;
	public static final float radiusRate = (float) 3.14159265;
	public double degree = 0;
	public int status = select_value_status;
	Context context;
	/**
	 * 当前进度
	 */
	float progress = 0;
	/**
	 * 最大进度
	 */
	int max = 1000;

	/**
	 * 圆环百分比画笔颜色
	 */
	int colorId = R.color.white;
	/**
	 * 整个大圆ID
	 */
	int circle_big_ID = R.mipmap.circle_big;
	/**
	 * 内圆ID
	 */
	int circle_trans_ID = R.mipmap.circle_trans;
	/**
	 * 图片缩放百分比
	 */
	float rate = (float) 1.0;

	/**
	 * 外部的加载进度条
	 */
	int progress_width;

	/**
	 * 外部的加载进度条半径
	 */
	int progress_radius;

	/**
	 * 外圆半径
	 */
	int circle_big_radius;
	/**
	 * 内圆半径
	 */
	int circle_inner_radius;
	/**
	 * 外部的加载进度条图片
	 */
	Bitmap bitmap;
	/**
	 * 外圆图片
	 */
	Bitmap bitmap_circle_big;

	/**
	 * 中心x轴坐标
	 */
	int radiusX;
	/**
	 * 中心Y轴坐标
	 */
	int radiusY;

	int ringWidth;
	NotifyDataListener notifyDataListener;
	
	
	
	public NotifyDataListener getNotifyDataListener() {
		return notifyDataListener;
	}

	public void setNotifyDataListener(NotifyDataListener notifyDataListener) {
		this.notifyDataListener = notifyDataListener;
	}

	public TargetSetImageView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		this.context = context;

		initParams();
	}

	public TargetSetImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
		initParams();
	}

	public TargetSetImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		initParams();
	}

	public void initParams() {

		bitmap = BitmapFactory
				.decodeResource(getResources(), R.mipmap.main00);
		bitmap = Utils.bitmapToScale(bitmap, rate);
		bitmap_circle_big = BitmapFactory.decodeResource(getResources(),
				circle_big_ID);
		bitmap_circle_big = Utils.bitmapToScale(bitmap_circle_big, rate);

		Bitmap bmp = BitmapFactory.decodeResource(getResources(),
				circle_trans_ID);
		bmp = Utils.bitmapToScale(bmp, rate);

		progress_width = bitmap.getWidth();
		progress_radius = progress_width / 2;
		circle_big_radius = bitmap_circle_big.getWidth() / 2;
		circle_inner_radius = bmp.getWidth() / 2;
		ringWidth = circle_big_radius - circle_inner_radius;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		canvas.drawBitmap(bitmap_circle_big, +progress_radius
				- circle_big_radius, +progress_radius - circle_big_radius,
				paint);
		if (status == select_value_status) {
			paint.setColor(Color.rgb(0 ,187 , 163));

			// 设置进度是实心还是空心
			paint.setStrokeWidth(ringWidth); // 设置圆环的宽度
			

			
			RectF oval1 = new RectF(radiusX - circle_big_radius+ringWidth/2, radiusY
					- circle_big_radius+ringWidth/2, radiusX + circle_big_radius-ringWidth/2,
					radiusY + circle_big_radius-ringWidth/2);
			paint.setStyle(Paint.Style.STROKE);
			canvas.drawArc(oval1, -90, (float) degree, false, paint); // 根据进度画圆弧
		} else {

		}
	}

	public void showProgress() {
		status = init_value_status;
	}

	public void dismissProgress() {

	}

	private boolean isInsideProgress(float x, float y) {
		boolean isInside = false;
		int distance = (int) Math.sqrt((x - radiusX) * (x - radiusX)
				+ (y - radiusY) * (y - radiusY));
		if (distance >= circle_inner_radius - circle_inner_radius / 2
				&& distance <= circle_big_radius + circle_inner_radius / 2)
			return true;
		else
			return false;
	}

//	private OnTouchListener listener;
//
//	public void setOnTouchListener(OnTouchListener listener) {
//		this.listener = listener;
//	}

	float lastX = 0;
	float lastY = 0;
	float lastDegree;
	float totalDegree;
	boolean isValid = false;
	boolean isAddedAction = false;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub

		if (status == select_value_status) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				isValid = isInsideProgress(event.getX(), event.getY());
				if (isValid) {
					lastX = event.getX();
					lastY = event.getY();
					if (lastY < radiusY && lastX > radiusX) {// 第一象限
						double degree = Math.toDegrees(Math
								.atan((lastY - radiusY) / (lastX - radiusX)));
						lastDegree = (float) (90 - (Math.abs(degree)));
					} else if (lastY > radiusY && lastX > radiusX) {// 第四象限
						double degree = Math.toDegrees(Math
								.atan((lastY - radiusY) / (lastX - radiusX)));
						lastDegree = (float) (90 + (Math.abs(degree)));
					} else if (lastY > radiusY && lastX < radiusX) {// 第三象限
						double degree = Math.toDegrees(Math
								.atan((lastY - radiusY) / (lastX - radiusX)));
						lastDegree = (float) (270 - (Math.abs(degree)));
					} else if (lastY < radiusY && lastX < radiusX) {// 第二象限
						double degree = Math.toDegrees(Math
								.atan((lastY - radiusY) / (lastX - radiusX)));
						lastDegree = (float) (270 + (Math.abs(degree)));
					}
				}
				break;
			case MotionEvent.ACTION_UP:

				break;
			case MotionEvent.ACTION_MOVE:
				float newX = event.getX();
				float newY = event.getY();
				if (!isValid) {// 如果之前是无效状态，判断当前的坐标是否有效
					isValid = isInsideProgress(newX, newY);
					if (isValid) {// 有效记录下当前的坐标
						lastX = newX;
						lastY = newY;
					}
					break;
				} else {// 进入该处说明之前的移动坐标有效，判断当前的坐标是否有效
					isValid = isInsideProgress(newX, newY);
					if (isValid) {

						// 如果当前的坐标有效
						float currentDegree = 0;
						if (newY < radiusY && newX > radiusX) {// 第一象限
							double dg = Math.toDegrees(Math
									.atan((newY - radiusY) / (newX - radiusX)));
							currentDegree = (float) (90 - (Math.abs(dg)));

						
							if(degree>=0&&degree<=110){
								if(lastDegree>90&&currentDegree>0){//说明刚刚过度到第一象限
									degree = currentDegree;
								}else if(lastDegree>0&&currentDegree>0&&lastDegree<90&&currentDegree<90){
									degree += currentDegree - lastDegree;
									if(degree<0)
										degree=0;
								}
							}
							
							
							lastDegree = currentDegree;
						} else if (newY > radiusY && newX > radiusX) {// 第四象限
							double dg = Math.toDegrees(Math
									.atan((newY - radiusY) / (newX - radiusX)));
							currentDegree = (float) (90 + (Math.abs(dg)));
							
							if(degree>=70&&degree<=200){
								if(lastDegree>90&&currentDegree>90&&lastDegree<180&&currentDegree<180){
									degree += currentDegree - lastDegree;
								}
							}
							lastDegree = currentDegree;
						} else if (newY > radiusY && newX < radiusX) {// 第三象限
							double dg = Math.toDegrees(Math
									.atan((newY - radiusY) / (newX - radiusX)));
							currentDegree = (float) (270 - (Math.abs(dg)));
							if(degree>=160&&degree<=290){
								if(lastDegree>180&&currentDegree>180&&lastDegree<270&&currentDegree<270){
									degree += currentDegree - lastDegree;
								}
							}
							lastDegree = currentDegree;
						} else if (newY < radiusY && newX < radiusX) {// 第二象限
							double dg = Math.toDegrees(Math
									.atan((newY - radiusY) / (newX - radiusX)));
							currentDegree = (float) (270 + (Math.abs(dg)));


							if(degree>=250&&degree<=360){
								if(lastDegree>270&&currentDegree>270&&lastDegree<360&&currentDegree<360){
									degree += currentDegree - lastDegree;
									if(degree>360)
										degree=360;
								}
							}
							lastDegree = currentDegree;
						}

						notifyDataListener.notifyData((int)((degree*1000)/360));
						postInvalidate();
						lastX = newX;
						lastY = newY;
					} else {
						break;
					}
				}
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
		radiusX = result / 2;
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
		radiusY = result / 2;
		return result;
	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);

			if (msg.obj != null) {
				float distance = (Float) msg.obj;
				postInvalidate();
				if (progress + distance > 0) {
					// degree=(float)
					// Math.toRadians(((progress+distance)*360)/(2*circle_inner_radius*radiusRate));
					postInvalidate();
				}
			}

		}

	};
	
	public interface NotifyDataListener{
		public void notifyData(int value);
	}

	public void init(double parseDouble) {
		// TODO Auto-generated method stub
		degree=parseDouble*360.0/1000;
		invalidate();
		if(notifyDataListener!=null)
			notifyDataListener.notifyData((int)((degree*1000)/360));
	}
}
