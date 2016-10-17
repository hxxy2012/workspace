package com.hike.digitalgymnastic.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.hiko.enterprisedigital.R;
//固件升级视图
public class FirewallUpdateView extends View {
	public final static int state_ing=0;
	public final static int state_ed=1;
	public final static int state_failed=-1;
	private int state=state_ing;

	Context context;
	/**
	 * 当前值
	 */
	int  value;

	/**
	 * 最大值
	 */
	int max;

	/**
	 * 当前角度
	 */
	float angle;
	/**
	 * 升级失败
	 */
	Bitmap bitmap_failed;
	/**
	 * 升级成功
	 */
	Bitmap bitmap_success;


	float textSize;
	public FirewallUpdateView(Context context) {
		super(context);
		this.context = context;
		init();
	}

	public FirewallUpdateView(Context context, AttributeSet attrs) {
		super(context, attrs, 0);
		this.context = context;
		init();
	}

	int ringWidth = dp2px(2);
	int ringWidthOut= dp2px(1);
	int resouceID=R.color.aver_cal_color;


	private void init(){
		bitmap_success= BitmapFactory.decodeResource(getResources(),R.mipmap.icon_success);
		bitmap_failed= BitmapFactory.decodeResource(getResources(),R.mipmap.icon_failed);
	}
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub

		super.onDraw(canvas);
		int radiusX = getWidth() / 2;
		int radiusY = getHeight() / 2;
		Paint paint = new Paint();
		paint.setAntiAlias(true);



		if(state==state_ing){

			paint.setColor(Color.WHITE);
			// 设置进度是实心还是空心
			paint.setStyle(Paint.Style.STROKE);

			RectF oval = new RectF(ringWidthOut, ringWidthOut, radiusX * 2
					- ringWidthOut, radiusY * 2 - ringWidthOut);

			canvas.drawArc(oval, -90, (float) 360, false, paint); // 根据进度画圆弧

			paint.setColor(Color.parseColor("#049983"));
			RectF oval1 = new RectF(ringWidth+ringWidthOut/2, ringWidth+ringWidthOut/2 , radiusX * 2
					- (ringWidth+ringWidthOut/2), radiusY * 2 - (ringWidth+ringWidthOut/2));
			paint.setStrokeWidth(ringWidth); // 设置圆环的宽度
			canvas.drawArc(oval1, -90, (float) angle, false, paint); // 根据进度画圆弧

			paint.setStyle(Paint.Style.FILL);
			paint.setTextSize(textSize);// 字体大小
			paint.setTextAlign(Align.CENTER);
			paint.setColor(Color.WHITE);
			Rect rect=new Rect();
			String percent=String.valueOf(100*value/max+"%");
			paint.getTextBounds(percent, 0, percent.length(), rect);
			canvas.drawText(percent, radiusX, radiusY+rect.height()/2, paint);
		}else if(state==state_ed){
			int size_bitmap = getWidth() / 2;
			Rect rect = new Rect();
			rect.left = getWidth() / 2 - size_bitmap / 2;
			rect.right = getWidth() / 2 + size_bitmap / 2;
			rect.bottom = getHeight() / 2+size_bitmap*bitmap_success.getHeight()/bitmap_success.getWidth();
			rect.top = rect.bottom - size_bitmap*bitmap_success.getHeight()/bitmap_success.getWidth();
			canvas.drawBitmap(bitmap_success, null, rect, paint);
		}else{
			int size_bitmap = getWidth() / 2;
			Rect rect = new Rect();
			rect.left = getWidth() / 2 - size_bitmap / 2;
			rect.right = getWidth() / 2 + size_bitmap / 2;
			rect.bottom = getHeight() / 2+size_bitmap*bitmap_success.getHeight()/bitmap_success.getWidth();
			rect.top = rect.bottom - size_bitmap*bitmap_failed.getHeight()/bitmap_failed.getWidth();
			canvas.drawBitmap(bitmap_failed, null, rect, paint);
		}

	}
	public void setColor(int resouceID){
		this.resouceID=resouceID;
	}
	public void setText(int value,int max) {
		state=state_ing;
		this.value = value;
		this.max=max;
		this.angle=(float) ((value*360)/max);
		invalidate();
	}
	public void showFinished(){
		state=state_ed;
		invalidate();
	}
	public void showFailed(){
		state=state_failed;
		invalidate();
	}
	private int dp2px(int value) {
		float v = getContext().getResources().getDisplayMetrics().density;
		return (int) (v * value + 0.5f);
	}

	private int sp2px(int value) {
		float v = getContext().getResources().getDisplayMetrics().scaledDensity;
		return (int) (v * value + 0.5f);
	}

	/**
	 * @see android.view.View#measure(int, int)
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		widthMeasureSpec = measureWidth(widthMeasureSpec);
		heightMeasureSpec = measureHeight(heightMeasureSpec);
		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
		textSize=widthMeasureSpec/4f;
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
			result = (int) getHeight() + getPaddingTop() + getPaddingBottom();
			if (specMode == MeasureSpec.AT_MOST) {
				// Respect AT_MOST value if that was what is called for by
				// measureSpec
				result = Math.min(result, specSize);
			}
		}
		return result;
	}


}
