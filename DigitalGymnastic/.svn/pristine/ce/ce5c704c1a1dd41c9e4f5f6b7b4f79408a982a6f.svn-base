package com.hike.digitalgymnastic.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;

import com.hiko.enterprisedigital.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * changqi
 * 2015-12-18
 */
public class DashCircleProgressWatch extends View {

    /**
     * 整个View的宽高
     */
    private int mTotalHeight, mTotalWidth;

    /**
     * 心跳线的总宽度 -- 圆环的宽度
     */
    private int mHeartBeatWidth;

    /**
     * 圆环半径 根据view的宽度计算
     */
    private int mRadius = 200;

    /**
     * 圆环的中心点 -- 画圆环和旋转画布时需要使用
     */
    private int x, y;

    /**
     * 圆环使用
     */
    private Paint mRingPaint;

    /**
     * 圆环的颜色值
     */
    private int mRingPaintColor = Color.RED;

    /**
     * 圆环动画使用 -- 与mRingPaint唯一不同得方在于颜色
     */
    private Paint mRingAnimPaint;
    /**
     * 圆环的颜色值
     */
    private int mRingColor = Color.WHITE;


    /**
     * 圆环大小 矩形
     */
    private RectF mRectf;

    private Context mContext;

    /**
     * 圆环 宽度
     */
    private int mHeartPaintWidth = 20;

    /**
     * 圆环动画开始时 画弧的偏移量
     */
    private int mAnimAngle = -1;

    /**
     * 心跳线 Y轴坐标
     */
    private float[] mOriginalYPositon;

    /**
     * 心跳线 Y轴坐标 -- 默认坐标
     */
    private float[] mDefaultYPostion;
    // y = Asin(w*x)+Y
    /**
     * sin函数 周期因子
     */
    private float mPeriodFraction = 0;

    /**
     * 期初的偏移量
     */
    private final int OFFSET_Y = 0;


    /**
     * 振幅
     */
    private float AmplitudeA = 200;// 振幅


    /**
     * 电量的图标画笔
     */
    Paint paint;

    /**
     * 电量的字体大小
     */
    int powerRateTextSize;
    /**
     * 电量字体的颜色
     */
    int powerRateColor;
    /**
     * 电量图标的颜色
     */
    int powerColor;
    /**
     * 低电量图标的颜色
     */
    int powerLowerColor;

    /**
     * 同步数据字体颜色
     */
    int synColor;


    /**
     * 同步成功icon
     */
    Bitmap bitmap_success;
    /**
     * 同步失败icon
     */
    Bitmap bitmap_failed;

    /**
     * 同步动画位图当前绘制的索引
     */
    private int index;
    /**
     * 同步动画位图集合
     */
    private List<Bitmap> bitmapList = new ArrayList<Bitmap>();
    Path path = new Path();


    private void init(AttributeSet attrs) {
        TypedArray attributes = mContext.obtainStyledAttributes(attrs,
                R.styleable.DashCircleProgress);
        initAttr(attributes);
        initPainter();
        initValueAnimator();
    }

    private void initAttr(TypedArray attributes) {
        if (attributes != null) {

            mHeartPaintWidth = attributes.getDimensionPixelSize(R.styleable.DashCircleProgress_mHeartPaintWidth, 20);
            mRingColor = attributes.getColor(R.styleable.DashCircleProgress_mRingColor, Color.parseColor("#999999"));
            mRingPaintColor = attributes.getColor(R.styleable.DashCircleProgress_mRingPaintColor,Color.RED);
            max = attributes.getInt(R.styleable.DashCircleProgress_max, 1000);
            min = attributes.getInt(R.styleable.DashCircleProgress_min, 0);
            duration = attributes.getInt(R.styleable.DashCircleProgress_duration, 500);
            attributes.recycle();
        }

    }

    private void initPainter() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mRingPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        if (!isInEditMode()) {
            // 造成错误的代码段
            mRingPaint.setColor(mRingColor);
        }
        mRingPaint.setStrokeWidth(mHeartPaintWidth);
        mRingPaint.setAlpha(76);
        mRingPaint.setStyle(Style.STROKE);


        mRingAnimPaint = new Paint(mRingPaint);
        mRingAnimPaint.setColor(mRingPaintColor);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Style.STROKE);
        powerRateColor = getResources().getColor(R.color.device_rel_text_normal);
        powerColor = Color.parseColor("#00bea3");
        powerLowerColor = Color.parseColor("#d7685c");
        synColor = Color.parseColor("#00bea3");
        paint.setColor(powerRateColor);
        paint.setTextAlign(Paint.Align.CENTER);


        bitmap_success = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_success);
        bitmap_failed = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_failed);
                 ;
    String name="icon_progress_";
        int id;
        for(int i=1;i<=10;i++){
            id= getResources().getIdentifier(name+i,"mipmap",mContext.getPackageName());
            bitmapList.add(BitmapFactory.decodeResource(getResources(), id));
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTotalHeight = h;
        mTotalWidth = w;
        mHeartBeatWidth = w - mHeartPaintWidth * 2 - 40; //内圆宽度
        AmplitudeA = (mTotalHeight - 2 * mHeartPaintWidth) / 4;
        mOriginalYPositon = new float[mHeartBeatWidth];//正弦曲线 Y坐标
        mDefaultYPostion = new float[mHeartBeatWidth];
        Arrays.fill(mOriginalYPositon, 0);
        Arrays.fill(mDefaultYPostion, -1);
        // 周期定位总宽度的1/4
        mPeriodFraction = (float) (Math.PI * 2 / mHeartBeatWidth * 3);
        for (int i = mHeartBeatWidth / 3 * 2; i < mHeartBeatWidth; i++) {
            mOriginalYPositon[i] = (float) (AmplitudeA * Math.sin(mPeriodFraction * i) + OFFSET_Y);
        }
        x = w / 2;
        y = h / 2;
        mRadius = w / 2 - mHeartPaintWidth / 2; //因为制定了Paint的宽度，因此计算半径需要减去这个
        mRectf = new RectF(x - mRadius, y - mRadius, x + mRadius, y + mRadius);

        powerRateTextSize = mTotalWidth / 8;
    }


    int startAngle = 135;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < 360; i += 3) {
            canvas.drawArc(mRectf, i, 1, false, mRingPaint);
        }
        switch (state) {
            case state_powerLow://显示低电量状态
                mRingAnimPaint.setColor(powerLowerColor);
                drawAnimal(canvas);
                drawPowerRate(canvas);
                break;
            case state_powerRate://显示电量状态
                mRingAnimPaint.setColor(powerColor);
                drawAnimal(canvas);
                drawPowerRate(canvas);
                break;
            case state_syn_init://显示同步数据初始状态
                drawSynInit(canvas);
                break;
            case state_syn_ing://显示同步数据中状态
                mRingAnimPaint.setColor(powerColor);
                drawAnimal(canvas);
                drawSynIng(canvas);
                break;
            case state_syn_ed://显示同步数据完成
                drawSynFinshed(canvas);
                break;
            case state_syn_failed://显示同步数据失败
                drawSynFailed(canvas);
                break;
            default:
                break;
        }


    }


    //绘制动画效果
    private void drawAnimal(Canvas canvas) {
        if (mAnimAngle != -1) {
            for (int i = startAngle; i < mAnimAngle + startAngle; i += 3) {
                canvas.drawArc(mRectf, i, 1, false, mRingAnimPaint);
            }
        }
    }

    //	绘制正常电量
    private void drawPowerRate(Canvas canvas) {
        int rate = (int) (100 * value / max);
        String rateValue = rate + "%";
        Rect rect = new Rect();
        paint.setTextSize(powerRateTextSize);
        paint.setStyle(Style.FILL);
        paint.setColor(powerRateColor);
        paint.getTextBounds(rateValue, 0, rateValue.length(), rect);
        canvas.drawText(rateValue, getWidth() / 2, getHeight() / 2, paint);

        //绘制底部的电量图标,图标总长度设定为powerRateTextSize字体的2个字大小
        if (rate <= 5)
            paint.setColor(powerLowerColor);
        else
            paint.setColor(powerColor);

        paint.setStyle(Style.STROKE);
        paint.setStrokeWidth(3);
        RectF rectf = new RectF();
        rectf.left = getWidth() / 2 - powerRateTextSize / 2;
        rectf.right = getWidth() / 2 + powerRateTextSize / 2;
        rectf.top = getHeight() / 2 + powerRateTextSize / 2;
        rectf.bottom = getHeight() / 2 + powerRateTextSize;
        canvas.drawRoundRect(rectf, 5, 5, paint);


        //实际电量powerRateTextSize-10长度
        paint.setStyle(Style.FILL);
        rectf = new RectF();
        rectf.left = getWidth() / 2 - powerRateTextSize / 2 + 5;
        rectf.right = getWidth() / 2 - powerRateTextSize / 2 + 5 + (powerRateTextSize - 10) * (float) value / max;
        rectf.top = getHeight() / 2 + powerRateTextSize / 2 + 5;
        rectf.bottom = getHeight() / 2 + powerRateTextSize - 5;
        canvas.drawRoundRect(rectf, 5, 5, paint);

        rect = new Rect();
        rect.left = getWidth() / 2 + powerRateTextSize / 2;
        rect.right = getWidth() / 2 + powerRateTextSize / 2 + 5;
        rect.top = getHeight() / 2 + powerRateTextSize / 2 + 7;
        rect.bottom = getHeight() / 2 + powerRateTextSize - 7;
        canvas.drawRect(rect, paint);
    }

    //数据同步初始化
    private void drawSynInit(Canvas canvas) {
        paint.setColor(synColor);
        paint.setStyle(Style.FILL);
        paint.setTextSize(powerRateTextSize*0.7f);
        String value = "轻触同步数据";
        Rect rect = new Rect();
        paint.getTextBounds(value, 0, value.length(), rect);
        canvas.drawText(value, getWidth() / 2, getHeight() / 2 + rect.height() / 2, paint);
    }

    //数据同步中
    private void drawSynIng(Canvas canvas) {
        for (int i = startAngle; i < -360 * rate / 100 + startAngle; i -= 3) {
            canvas.drawArc(mRectf, i, 1, false, mRingAnimPaint);
        }
        Rect rect = null;
        int size_bitmap = getWidth() / 5;
        int padding = getWidth() / 15;
        if (index < bitmapList.size()) {
            Bitmap bmp = bitmapList.get(index);

            rect = new Rect();
            rect.left = getWidth() / 2 - size_bitmap / 2;
            rect.right = getWidth() / 2 + size_bitmap / 2;
            rect.bottom = getHeight() / 2-dp2px(5);
            rect.top = rect.bottom - size_bitmap*bmp.getHeight()/bmp.getWidth();
            canvas.drawBitmap(bmp, null, rect, paint);
        }


        paint.setColor(synColor);
        paint.setStyle(Style.FILL);
        paint.setTextSize(powerRateTextSize*0.7f);
        String value = "数据同步中...";
        rect = new Rect();
        paint.getTextBounds(value, 0, value.length(), rect);
        canvas.drawText(value, getWidth() / 2, getHeight() / 2 + rect.height(), paint);

//        paint.setTextSize((float) (powerRateTextSize * 0.8f));
//        value = rate + "%";
//        Rect rect1 = new Rect();
//        paint.getTextBounds(value, 0, value.length(), rect1);
//        canvas.drawText(value, getWidth() / 2, getHeight() / 2 + rect1.height() + rect.height() + padding, paint);

    }


    //数据同步完成
    private void drawSynFinshed(Canvas canvas) {

        int size_bitmap = getWidth() / 5;
        Rect rect = new Rect();
        rect.left = getWidth() / 2 - size_bitmap / 2;
        rect.right = getWidth() / 2 + size_bitmap / 2;
        rect.bottom = getHeight() / 2-dp2px(5);
        rect.top = rect.bottom - size_bitmap*bitmap_success.getHeight()/bitmap_success.getWidth();
        canvas.drawBitmap(bitmap_success, null, rect, paint);

        paint.setColor(synColor);
        paint.setStyle(Style.FILL);
        paint.setTextSize(powerRateTextSize*0.7f);
        String value = "数据同步完成";
        rect = new Rect();
        paint.getTextBounds(value, 0, value.length(), rect);
        canvas.drawText(value, getWidth() / 2, getHeight() / 2 + rect.height(), paint);
    }

    //数据同步失败
    private void drawSynFailed(Canvas canvas) {

        int size_bitmap = getWidth() / 5;
        Rect rect = new Rect();
        rect.left = getWidth() / 2 - size_bitmap / 2;
        rect.right = getWidth() / 2 + size_bitmap / 2;
        rect.bottom = getHeight() / 2-dp2px(5);
        rect.top = rect.bottom - size_bitmap*bitmap_failed.getHeight()/bitmap_failed.getWidth();
        canvas.drawBitmap(bitmap_failed, null, rect, paint);

        paint.setColor(synColor);
        paint.setStyle(Style.FILL);
        paint.setTextSize(powerRateTextSize*0.7f);
        String value = "数据同步失败";
        rect = new Rect();
        paint.getTextBounds(value, 0, value.length(), rect);
        canvas.drawText(value, getWidth() / 2, getHeight() / 2 + rect.height(), paint);
    }

    ValueAnimator valueAnimator = new ValueAnimator();


    /*---------------------------------构造函数-----------------------------------*/
    public DashCircleProgressWatch(Context context) {
        super(context);
        mContext = context;
        init(null);
    }

    public DashCircleProgressWatch(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(attrs);
    }

    public DashCircleProgressWatch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(attrs);
    }


//    /**
//     * 设置需要显示的数值
//     */
//    public void setValue(float value) {
//        this.value = value;
//        if (value <= max || value >= min) {
//            animateValue();
//        }
//    }

    /**
     * 启动电量动画效果
     */
    private void animateValue() {
        if (valueAnimator != null) {
            valueAnimator.setFloatValues(min, value);
            valueAnimator.setDuration(duration);
            valueAnimator.start();
        }
    }


//    /**
//     * 启动同步中动画效果
//     */
//    private void animateSyningValue() {
//        if (valueAnimator != null) {
//            valueAnimator.setFloatValues(last, value);
//            valueAnimator.setDuration(duration);
//            valueAnimator.start();
//        }
//    }

    /**
     * 初始化动画
     */

    private void initValueAnimator() {
        valueAnimator = new ValueAnimator();
        valueAnimator.setInterpolator(interpolator);
        valueAnimator.addUpdateListener(new ValueAnimatorListenerImp());
    }

    public void setInterpolator(Interpolator interpolator) {
        this.interpolator = interpolator;
        if (valueAnimator != null) {
            valueAnimator.setInterpolator(interpolator);
        }
    }

    float min, max = 1000,  value;
    long duration = 500;
    Interpolator interpolator;


    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public static final int state_powerLow = -1;//显示低电量状态
    public static final int state_powerRate = 0;//显示电量状态
    public static final int state_syn_init = 1;//显示同步数据初始状态
    public static final int state_syn_ing = 2;//显示同步数据中状态
    public static final int state_syn_ed = 3;//显示同步数据完成
    public static final int state_syn_failed = 4;//显示同步数据失败
    private int state = state_powerRate;

    //设置电量信息，显示动画效果
    public void setPowerRate(int value, int max) {
        this.max = max;
        this.value = value;
        if (value <= 5)
            state = state_powerLow;
        else
            state = state_powerRate;
        if (value == 0) {
            invalidate();
            return;
        }

        if (value <= max || value >= min) {
            animateValue();
        }
    }

    //显示数据同步初始状态
    public void showSynDataInit() {
        state = state_syn_init;
        timer.cancel();
        timer=new Timer();
        invalidate();
    }
    int rate;
    Timer timer=new Timer();
    TimerTask timerTask=new TimerTask() {
        @Override
        public void run() {

        }
    };
    //显示数据同步中状态
    public void showSynDataIng(int rate) {
        state = state_syn_ing;
        this.rate = rate;
        mAnimAngle= 360*rate/100;
        index=0;
        timer.cancel();
        timer=new Timer();
        timerTask=new TimerTask() {
            @Override
            public void run() {
                postInvalidate();
                index++;
                if(index>=bitmapList.size())
                    index=0;
            }
        };

        timer.schedule(timerTask,0,150);
    }

    //显示数据同步完成状态
    public void showSynDataEd() {
        state = state_syn_ed;
        timer.cancel();
        timer=new Timer();
        invalidate();
    }

    //显示数据同步失败状态
    public void showSynDataFailed() {
        state = state_syn_failed;
        timer.cancel();
        timer=new Timer();
        invalidate();
    }

    private class ValueAnimatorListenerImp implements ValueAnimator.AnimatorUpdateListener {
        @Override
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            Float value = (Float) valueAnimator.getAnimatedValue();
            if (valueChangeListener != null) {
                valueChangeListener.onValueChange(value);
            }
            mAnimAngle = (int) (value * 360 / max);
            if (mAnimAngle > 360) {
                mAnimAngle = 360;
            }
            postInvalidate();


        }
    }


    float lastX;
    float lastY;
    private OnTouchSyncListener listener;

    public void setOnTouchListener(OnTouchSyncListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = event.getX();
                lastY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                if (state == state_syn_init) {
                    if (Math.abs(lastX - event.getX()) > 10
                            || Math.abs(lastY - event.getY()) > 10) {
                        return false;
                    } else {
                        if (listener != null)
                            listener.onTouchSyncData();
                    }
                }
                break;

            default:
                break;
        }

        return true;
    }

    public interface OnTouchSyncListener {
        public void onTouchSyncData();// 同步数据
    }


    //数值变化回调
    private OnValueChangeListener valueChangeListener;

    public void setValueChangeListener(OnValueChangeListener valueChangeListener) {
        this.valueChangeListener = valueChangeListener;
    }

    public interface OnValueChangeListener {
        void onValueChange(float value);
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
