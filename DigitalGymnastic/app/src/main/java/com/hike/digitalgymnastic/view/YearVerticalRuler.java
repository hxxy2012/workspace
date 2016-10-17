package com.hike.digitalgymnastic.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;

import com.hiko.enterprisedigital.R;


@SuppressLint("ClickableViewAccessibility")
public class YearVerticalRuler extends View {

    public interface OnValueChangeListener {
        public void onValueChange(float value);
    }

    public static final int MOD_TYPE_HALF = 2;
    public static final int MOD_TYPE_ONE = 10;

    private static final int ITEM_HALF_DIVIDER = 40;
    private static final int ITEM_ONE_DIVIDER = 9;

    private int ITEM_MAX_HEIGHT = 0;
    private int ITEM_MIN_HEIGHT = 0;
    private int MARGIN_WIDTH = 0;
    private static final int TEXT_SIZE = 12;

    private float mDensity;
    //    private int mValue = 50, mMaxValue = 100, mModType = MOD_TYPE_HALF, mLineDivider = ITEM_HALF_DIVIDER;
    private int mValue = 50, mMaxValue = 500, mModType = MOD_TYPE_ONE,
            mLineDivider = ITEM_ONE_DIVIDER;

    private int mLastY, mMove;
    private int mWidth, mHeight, mMinValue = 0;

    private int mMinVelocity;
    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;

    private OnValueChangeListener mListener;

    @SuppressWarnings("deprecation")
    public YearVerticalRuler(Context context, AttributeSet attrs) {
        super(context, attrs);

        mScroller = new Scroller(getContext());
        mDensity = getContext().getResources().getDisplayMetrics().density;

        mMinVelocity = ViewConfiguration.get(getContext()).getScaledMinimumFlingVelocity();

        int w = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        int h = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        this.measure(w, h);
        int height = this.getMeasuredHeight();
        int width = this.getMeasuredWidth();

        ITEM_MAX_HEIGHT = dp2px(60);
        ITEM_MIN_HEIGHT = dp2px(50);
        MARGIN_WIDTH = dp2px(15);
        // setBackgroundResource(R.drawable.bg_wheel);
//        setBackgroundDrawable(createBackground());
    }

//    private GradientDrawable createBackground() {
//        float strokeWidth = 1 * mDensity; // 边框宽度
//        float roundRadius = 1 * mDensity; // 圆角半径
//        int strokeColor = Color.parseColor("#FF666666");// 边框颜色
//        // int fillColor = Color.parseColor("#DFDFE0");// 内部填充颜色
//
//        setPadding((int) strokeWidth, (int) strokeWidth, (int) strokeWidth, 0);
//
//        int colors[] = {0xEEF5F5F5, 0xF5F5F5, 0xEEF5F5F5};// 分别为开始颜色，中间夜色，结束颜色
//        GradientDrawable bgDrawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors);// 创建drawable
//        // bgDrawable.setColor(fillColor);
//        bgDrawable.setCornerRadius(roundRadius);
//        bgDrawable.setStroke((int) strokeWidth, strokeColor);
//        // setBackgroundDrawable(gd);
//        return bgDrawable;
//    }

    /**
     * 考虑可扩展，但是时间紧迫，只可以支持两种类型效果图中两种类型
     *
     * @param defaultValue 初始值
     * @param maxValue     最大值
     * @param model        刻度盘精度：
     *                     MOD_TYPE_HALF
     *                     MOD_TYPE_ONE
     */
    public void initViewParam(int minValue, int defaultValue, int maxValue, int model) {
        switch (model) {
            case MOD_TYPE_HALF:
                mModType = MOD_TYPE_HALF;
                mLineDivider = ITEM_HALF_DIVIDER;

                mValue = defaultValue * 2;
                mMaxValue = maxValue * 2;
                break;
            case MOD_TYPE_ONE:
                mModType = MOD_TYPE_ONE;
                mLineDivider = ITEM_ONE_DIVIDER;
                mMinValue = minValue;
                mValue = defaultValue;
                mMaxValue = maxValue;
                break;

            default:
                break;
        }
        invalidate();

        mLastY = 0;
        mMove = 0;
        notifyValueChange();
    }

    /**
     * 设置用于接收结果的监听器
     *
     * @param listener
     */
    public void setValueChangeListener(OnValueChangeListener listener) {
        mListener = listener;
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        return super.onCreateDrawableState(extraSpace);
    }

    /**
     * 获取当前刻度值
     *
     * @return
     */
    public float getValue() {
        return mValue;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        mWidth = getWidth();
        mHeight = getHeight();
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawScaleLine(canvas);
        drawWheel(canvas);
        drawMiddleLine(canvas);
    }

    private void drawWheel(Canvas canvas) {
        Drawable wheel = getResources().getDrawable(R.mipmap.ruler_big_bg);
        wheel.setBounds(0, 0, getWidth(), getHeight());
        wheel.draw(canvas);
    }

    public int getFontHeight(Paint paint) {

        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int) Math.ceil(fm.descent - fm.ascent);
    }

    /**
     * 从中间往两边开始画刻度线
     *
     * @param canvas
     */
    private void drawScaleLine(Canvas canvas) {
        canvas.save();

        Paint linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth((int)0.25*mDensity);
        linePaint.setColor(getResources().getColor(R.color.umeng_socialize_text_time));

        TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(getResources().getColor(R.color.umeng_socialize_text_time));
        linePaint.setStrokeWidth(1*mDensity);
        textPaint.setTextSize(TEXT_SIZE * mDensity);

        int height = mHeight, drawCount = 0;
        float yPosition = 0, textHeight = getFontHeight(textPaint), textWidth = 0;
        for (int i = 0; drawCount <= 4 * height; i++) {
            int numSize = String.valueOf(mValue + i).length();
            System.out.println("numSize==" + mValue);
            yPosition = (height / 2 - mMove) - i * mLineDivider * mDensity;
            if (yPosition + getPaddingBottom() < mHeight) {
//
                if ((mValue + i) % mModType == 0 || (mValue + i) % (mModType / 2) == 0) {
                    if (mValue + i > mMaxValue+2) {

                    } else {
                        canvas.drawLine(mWidth / 2 - ITEM_MAX_HEIGHT, yPosition, mWidth / 2 + ITEM_MAX_HEIGHT, yPosition, linePaint);
                    }

                    if (mValue + i <= mMaxValue) {
                        switch (mModType) {
                            case MOD_TYPE_HALF:
                                canvas.drawText(String.valueOf((mValue + i) / 2), mWidth / 2 + ITEM_MAX_HEIGHT + MARGIN_WIDTH, countLeftStart(mValue + i, yPosition, textHeight), textPaint);
                                break;
                            case MOD_TYPE_ONE:
                                if ((mValue + i) % mModType == 0) {
                                    String str = String.valueOf(mValue + i);
                                    textWidth = textPaint.measureText(str, 0, str.length());
                                    canvas.drawText(String.valueOf(mValue + i), mWidth / 2 - ITEM_MAX_HEIGHT - MARGIN_WIDTH - textWidth, yPosition + (textHeight / 4), textPaint);
                                    canvas.drawText(String.valueOf(mValue + i), mWidth / 2 + ITEM_MAX_HEIGHT + MARGIN_WIDTH, yPosition + (textHeight / 4), textPaint);
                                }
                                break;

                            default:
                                break;
                        }
                    }
                } else {
                    if (mValue + i > mMaxValue+2) {

                    } else {
                        canvas.drawLine(mWidth / 2 - ITEM_MIN_HEIGHT, yPosition, mWidth / 2 + ITEM_MIN_HEIGHT, yPosition, linePaint);
                    }
                }
            }

            yPosition = (height / 2 - mMove) + i * mLineDivider * mDensity;

            if (yPosition > getPaddingTop()) {
                if ((mValue - i) % mModType == 0 || (mValue - i) % (mModType / 2) == 0) {
                    if (mValue - i < mMinValue - 2) {

                    } else {
                        canvas.drawLine(mWidth / 2 - ITEM_MAX_HEIGHT, yPosition, mWidth / 2 + ITEM_MAX_HEIGHT, yPosition, linePaint);
                    }
                    if (mValue - i >= mMinValue) {

                        switch (mModType) {
                            case MOD_TYPE_HALF:
                                canvas.drawText(String.valueOf((mValue - i) / 2), mWidth / 2 + ITEM_MAX_HEIGHT + MARGIN_WIDTH, countLeftStart(mValue - i, yPosition, textHeight), textPaint);
                                break;
                            case MOD_TYPE_ONE:
                                if ((mValue - i) % mModType == 0) {
                                    String str = String.valueOf(mValue + i);
                                    textWidth = textPaint.measureText(str, 0, str.length());
                                    canvas.drawText(String.valueOf(mValue - i), mWidth / 2 - ITEM_MAX_HEIGHT - MARGIN_WIDTH - textWidth, yPosition + (textHeight / 4), textPaint);
                                    canvas.drawText(String.valueOf(mValue - i), mWidth / 2 + ITEM_MAX_HEIGHT + MARGIN_WIDTH, yPosition + (textHeight / 4), textPaint);
                                }
                                break;

                            default:
                                break;
                        }
                    }
                } else {
                    if (mValue - i < mMinValue - 2) {

                    } else {
                        canvas.drawLine(mWidth / 2 - ITEM_MIN_HEIGHT, yPosition, mWidth / 2 + ITEM_MIN_HEIGHT, yPosition, linePaint);
                    }
                }
            }

            drawCount += 2 * mLineDivider * mDensity;
        }

        canvas.restore();
    }

    /**
     * 计算没有数字显示位置的辅助方法
     *
     * @param value
     * @param yPosition
     * @param textWidth
     * @return
     */
    private float countLeftStart(int value, float yPosition, float textWidth) {
        float xp = 0f;
        if (value < 20) {
            xp = yPosition + (textWidth * 1 / 2);
        } else {
            xp = yPosition + (textWidth * 1 / 2);
        }
        return xp;
    }

    private int dp2px(float value) {
        float v = getContext().getResources().getDisplayMetrics().density;
        return (int) (v * value + 0.5f);
    }

    private int sp2px(int value) {
        float v = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (v * value + 0.5f);
    }

    /**
     * 画中间的红色指示线
     *
     * @param canvas
     */
    private void drawMiddleLine(Canvas canvas) {
        int gap = 12, indexWidth = 2, indexTitleWidth = 24, indexTitleHight = 10, shadow = 6;
        String color = "#66999999";

        canvas.save();

        Paint redPaint = new Paint();
        redPaint.setStrokeWidth((int)(1.5*mDensity));
        redPaint.setColor(getResources().getColor(R.color.light_green));
        canvas.drawLine(mWidth / 2 - ITEM_MAX_HEIGHT, mHeight / 2, ITEM_MAX_HEIGHT + mWidth / 2, mHeight / 2, redPaint);


        Bitmap arrow_right = BitmapFactory.decodeResource(getResources(), R.mipmap.arrow_right);
        Bitmap arrow_left = BitmapFactory.decodeResource(getResources(), R.mipmap.arrow_left);
        RectF rectf = new RectF();
        rectf.left = mWidth / 2 - ITEM_MAX_HEIGHT - MARGIN_WIDTH * 3 - arrow_right.getWidth();
        rectf.right = mWidth / 2 - ITEM_MAX_HEIGHT - MARGIN_WIDTH * 3;
        rectf.top = mHeight / 2 - arrow_right.getHeight() / 2;
        rectf.bottom = mHeight / 2 + arrow_right.getHeight() / 2;
        canvas.drawBitmap(arrow_right, null, rectf, redPaint);
        rectf.left = mWidth / 2 + ITEM_MAX_HEIGHT + MARGIN_WIDTH * 3;
        rectf.right = mWidth / 2 + ITEM_MAX_HEIGHT + MARGIN_WIDTH * 3 + arrow_left.getWidth();
        rectf.top = mHeight / 2 - arrow_left.getHeight() / 2;
        rectf.bottom = mHeight / 2 + arrow_left.getHeight() / 2;
        canvas.drawBitmap(arrow_left, null, rectf, redPaint);
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int yPosition = (int) event.getY();

        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);

        switch (action) {
            case MotionEvent.ACTION_DOWN:

                mScroller.forceFinished(true);

                mLastY = yPosition;
                mMove = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                mMove += (mLastY - yPosition);
                changeMoveAndValue();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                countMoveEnd();
                countVelocityTracker(event);
                return false;
            // break;
            default:
                break;
        }

        mLastY = yPosition;
        return true;
    }

    private void countVelocityTracker(MotionEvent event) {
        mVelocityTracker.computeCurrentVelocity(1000);
        float yVelocity = mVelocityTracker.getYVelocity();
        if (Math.abs(yVelocity) > mMinVelocity) {
            mScroller.fling(0, 0, 0, (int) yVelocity, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 0);
        }
    }

    private void changeMoveAndValue() {
        int tValue = (int) (mMove / (mLineDivider * mDensity));
        if (Math.abs(tValue) > 0) {
            mValue -= tValue;
            mMove -= tValue * mLineDivider * mDensity;
            if (mValue < mMinValue || mValue > mMaxValue) {
                mValue = mValue <= mMinValue ? mMinValue : mMaxValue;
                mMove = 0;
                mScroller.forceFinished(true);
            } else {
                notifyValueChange();
                postInvalidate();
            }

        }

    }

    private void countMoveEnd() {
        int roundMove = Math.round(mMove / (mLineDivider * mDensity));
        mValue = mValue + roundMove;
        mValue = mValue <= mMinValue ? mMinValue : mValue;
        mValue = mValue > mMaxValue ? mMaxValue : mValue;

        mLastY = 0;
        mMove = 0;

        notifyValueChange();
        postInvalidate();
    }

    private void notifyValueChange() {
        if (null != mListener) {
            if (mModType == MOD_TYPE_ONE) {
                mListener.onValueChange(mValue);
            }
            if (mModType == MOD_TYPE_HALF) {
                mListener.onValueChange(mValue / 2f);
            }
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            if (mScroller.getCurrY() == mScroller.getFinalY()) { // over
                countMoveEnd();
            } else {
                int yPosition = mScroller.getCurrY();
                mMove += (mLastY - yPosition);
                changeMoveAndValue();
                mLastY = yPosition;
            }
        }
    }

    public int getCurrentValue() {
        return mValue;
    }

}
