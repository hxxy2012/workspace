package com.hike.digitalgymnastic.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.hiko.enterprisedigital.R;
/*
 * ���໭�̶ȳ��޷�ʵ�ֻ���Ч������ˣ�����
 */
public class HorizontalDividingRule extends View {

	int screenWidth;
	int screenHeight;

	float lastX;
	float lastY;
	boolean isMoveLeft = false;
	int startYear = 1900;
	int startIndex = 59;

	int y150;// �����ܵĸ߶�
	int description_height;// ���������߶�
	int year_height;// ��ݸ߶�
	int line_height;// ���߸߶�
	int line_height_extra;// ���߸߳�����
	int padding;// �ָ�
	Bitmap bitmap;

	int viewWidth;
	int eachWidth;
	int totalLineConut = 48;
	int currentSelectYear = 1983;

	public HorizontalDividingRule(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		init(context);

	}

	public HorizontalDividingRule(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public HorizontalDividingRule(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}

	private void init(Context context) {
		// TODO Auto-generated method stub
		y150 = (int) getResources().getDimensionPixelSize(R.dimen.y150);
		description_height = y150 / 5;

		year_height = y150 / 15;
		line_height_extra = y150 / 12;
		line_height = y150 / 2 + line_height_extra;

		padding = y150 / 6;
		bitmap = BitmapFactory.decodeResource(getResources(),
				R.mipmap.arrow_up);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		viewWidth = getRight() - getLeft();
		eachWidth = viewWidth / totalLineConut;
		Paint paint = new Paint();

		paint.setAntiAlias(true);
		// ���������ֿ�ʼ
		paint.setColor(getResources().getColor(R.color.white));
		paint.setTextSize(description_height);

		canvas.drawText("" + currentSelectYear,
				viewWidth / 2 - paint.measureText("" + startYear) / 2,
				getTop() + 40, paint);
		// ���������ֽ���

		// ���̶ȳ߲���
		for (int i = startIndex; i < startIndex + totalLineConut; i++) {
			if (i % 10 == 0) {
				paint.setColor(getResources().getColor(R.color.white));
				paint.setTextSize(year_height);
				canvas.drawText("" + (startYear + i), (i - startIndex)
						* eachWidth - paint.measureText("" + startYear) / 2, getTop() + 40
						+ description_height + padding, paint);

				paint.setColor(getResources().getColor(R.color.darkgray));
				// ��ݺ͸��߼���趨Ϊ�������ĸ߶�
				canvas.drawLine((i - startIndex) * eachWidth, getTop() + 40
						+ description_height + padding + year_height
						+ year_height, (i - startIndex) * eachWidth, getTop() + 40
						+ description_height + padding + year_height
						+ year_height + line_height, paint);
			} else {
				paint.setColor(getResources().getColor(R.color.darkgray));
				canvas.drawLine((i - startIndex) * eachWidth, getTop() + 40
						+ description_height + padding + year_height
						+ year_height + line_height_extra, (i - startIndex)
						* eachWidth, 20 + description_height + padding
						+ year_height + year_height + line_height, paint);
			}

		}
		// ���̶ȳ߲��ֽ���

		// ���м��߲���
		// ��ݺ͸��߼���趨Ϊ�������ĸ߶�

		canvas.drawLine(eachWidth * totalLineConut / 2, getTop() + 40 + description_height
				+ padding + year_height + year_height, eachWidth
				* totalLineConut / 2, getTop() + 40 + description_height + padding
				+ year_height + year_height + line_height, paint);

		//���ײ�ͼƬ����
		canvas.drawBitmap(bitmap, eachWidth * totalLineConut / 2 - bitmap.getWidth() / 2, getTop() + 40
				+ description_height + padding + year_height + year_height
				+ line_height, paint);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			lastX = event.getX();
			lastY = event.getY();
			break;
		case MotionEvent.ACTION_UP:
			lastX = 0;
			lastY = 0;
			break;
		case MotionEvent.ACTION_MOVE:
			if (lastX > 0 && lastY > 0) {
				if (lastX > event.getX()) {

					// ������ƶ�
					if (lastX - event.getX() > eachWidth) {
						startIndex += 1;
						postInvalidate();
						lastX = event.getX();
					}
				} else if (lastX < event.getX()) {
					// ����
					if (event.getX() - lastX > eachWidth) {
						startIndex -= 1;
						postInvalidate();
						lastX = event.getX();
					}
				} else {

				}

			}
			break;

		default:
			break;
		}
		return true;
	}
}
