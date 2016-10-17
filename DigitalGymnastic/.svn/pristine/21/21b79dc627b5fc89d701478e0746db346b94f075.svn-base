package com.hike.digitalgymnastic.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.hike.digitalgymnastic.utils.Utils;
import com.hiko.enterprisedigital.R;

import java.lang.ref.SoftReference;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class CircleWeight extends View {

	public CircleWeight(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init();
	}

	public CircleWeight(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	public CircleWeight(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	Paint paint;
	Bitmap bitmap;
	Bitmap bitmapOverlay;
	public Map<String, SoftReference<Bitmap>> bitmapMap = new HashMap<String, SoftReference<Bitmap>>();
	String key = "weightCircleBMP";
	String keyOverlay = "bitmapOverlay";
	public static SoftReference<Bitmap> srf;
	float angle = 15;
	int textSize=25;
	int suffixSize=15;
	private void init() {
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);
		paint.setTextSize(sp2px(textSize));
		// bitmap=BitmapFactory.decodeResource(getResources(),
		// R.mipmap.icon_weight_circle);
		// bitmap=Utils.bitmapToScale(bitmap, getWidth(), getHeight());
		// srf=new SoftReference<Bitmap>(bitmap);
		// bitmapMap.put(key, srf);
	}

	double weight_init;

	public void initWeight(double weight_init) {
		this.weight_init = weight_init;
		currentValue=weight_init;
//		 currentCount=(int) (weight_init/0.1);
		 degree=-(float) ((weight_init/0.1)*0.24);
		 currentDegree=-(float) ((weight_init/0.1)*0.24);
		 currentCount = (int) (weight_init/0.1);
		 
		 
		 postInvalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (getWidth() > 0 && status == free) {
			status = drawing;
			if (bitmapMap.get(key) != null && bitmapMap.get(key).get() != null) {
				bitmap = bitmapMap.get(key).get();
			} else {
				bitmap = BitmapFactory.decodeResource(getResources(),
						R.mipmap.icon_weight_circle);
				bitmap = Utils
						.bitmapToScale(bitmap, getWidth(), getHeight(), 0);
				srf = new SoftReference<Bitmap>(bitmap);
				bitmapMap.put(key, srf);
			}

			if (bitmapMap.get(keyOverlay) != null
					&& bitmapMap.get(keyOverlay).get() != null) {
				bitmapOverlay = bitmapMap.get(keyOverlay).get();
			} else {
				bitmapOverlay = BitmapFactory.decodeResource(getResources(),
						R.mipmap.icon_weight_circle_overlay);
				bitmapOverlay = Utils.bitmapToScale(bitmapOverlay, getWidth(),
						getHeight(), 0);
				srf = new SoftReference<Bitmap>(bitmapOverlay);
				bitmapMap.put(keyOverlay, srf);
			}
			radiusX = getWidth() / 2;
			radiusY = getHeight() / 2;

			// 创建操作图片用的matrix对象
			Matrix matrix = new Matrix();
			// 旋转图片 动作

			matrix.setRotate(currentDegree, bitmap.getWidth() / 2,
					bitmap.getHeight() / 2);

			Bitmap bm = Utils.zhizhenorgToScale(bitmap, currentDegree);
			Rect rect = new Rect();
			rect.left = getWidth() / 2 - bm.getWidth() / 2;
			rect.right = getWidth() / 2 + bm.getWidth() / 2;
			rect.top = getHeight() / 2 - bm.getHeight() / 2;
			rect.bottom = getHeight() / 2 + bm.getHeight() / 2;

			canvas.drawBitmap(bm, null, rect, paint);
//			canvas.drawBitmap(bitmapOverlay, null, rect, paint);
			 matrix = new Matrix();
			 matrix.setRotate(0, getWidth() / 2, getHeight() / 2);
			 canvas.drawBitmap(bitmapOverlay, matrix, paint);
			
			 
			 paint.setTextSize(sp2px(textSize));
			currentValue=currentCount * 0.1;
			if(currentValue==150.0)
				currentValue=0;
			DecimalFormat df = new DecimalFormat("####.#");
			currentValue=Double.parseDouble(df.format(currentValue));
			String value = String.valueOf(currentValue);
			rect = new Rect();
			paint.getTextBounds(value, 0, value.length(), rect);
			
			
			
			paint.setStrokeWidth(2);
			canvas.drawLine(radiusX, -10, radiusX,
					radiusY*2/3 , paint);
			canvas.drawText(value, radiusX - rect.width() / 2, radiusY*2/3+rect.height()+dp2px(10), paint);
			
			value="kg";
			paint.setTextSize(sp2px(suffixSize));
			Rect sufxRect = new Rect();
			paint.getTextBounds(value, 0, value.length(), sufxRect);
			canvas.drawText(value, radiusX +rect.width() / 2+sufxRect.width()/2, radiusY*2/3+rect.height()+dp2px(10), paint);

			status = free;
		}
	}

	private boolean isInsideProgress(float x, float y) {
		int distance = (int) Math.sqrt((x - radiusX) * (x - radiusX)
				+ (y - radiusY) * (y - radiusY));
		int leftX = radiusX
				+(int) (radiusX * Math.cos(startAngle * Math.PI / 180));
		int rigthX = radiusX
				+ (int) (radiusX * Math.cos(endAngle * Math.PI / 180));
		double arg = (endAngle - startAngle) / 2;
		int bottomY = (int) radiusY/2;
		int topY = 0;

		if (x >= leftX && x <= rigthX && y >= topY && y <= bottomY) {
			return true;
		} else {
			return false;
		}
	}

	double startAngle = -154;
	double endAngle = -26;
	int radiusX;
	int radiusY;
	float lastX = 0;
	float lastY = 0;
	float lastDegree;
	float totalDegree;
	boolean isValid = false;
	boolean isAddedAction = false;
	private static final int free = 0;
	private static final int drawing = 1;
	int status = free;
	float degree;
	float currentDegree;
	int currentCount = 0;// 划过的刻度个数
	public double currentValue;
	int totalCount=1500;
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		float eachAngle;
		if (status == free) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				isValid = isInsideProgress(event.getX(), event.getY());
				if (isValid) {
					lastX = event.getX();
					lastY = event.getY();

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
						eachAngle = (float) ((endAngle - startAngle)
								* (newX - lastX) / (4*radiusX));// 向右滑动eachAngle>0，顺时针，向左滑eachAngle<0，逆时针

						degree += eachAngle;
						if(degree>=360){
							degree=0;
						}else if(degree<=-360){
							degree=0;
						}
						if (currentCount == (int) (degree / 0.24)) {

						} else {
							currentCount = (int) (degree / 0.24);
							currentDegree = (float) ((int) (degree / 0.24) * 0.24);
							if (degree < 0) {
								currentCount = -(int) (degree / 0.24);
							} else {
								currentCount = totalCount - (int) (degree / 0.24);
							}
						}

						Log.d("MyLog", "ondraw()====degree==" + degree
								+ "    currentCount===" + currentCount);
						postInvalidate();

						lastX = newX;
						lastY = newY;
					}
					break;
				}
			default:
				break;
			}

		}
		return true;
	}

	/**
	 * @see android.view.View#measure(int, int)
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		widthMeasureSpec = measureWidth(widthMeasureSpec);
		heightMeasureSpec = measureHeight(heightMeasureSpec);
		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
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
			result = (int) getWidth() + getPaddingLeft() + getPaddingRight();
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
			result = (int) getHeight() + getPaddingTop() + getPaddingBottom();
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
