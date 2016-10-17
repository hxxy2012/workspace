package com.hike.digitalgymnastic.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DrawFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.hiko.enterprisedigital.R;

import java.text.DecimalFormat;
import java.util.Arrays;

/**
 * Created by computer on 2015/12/21.
 */
public class WeightRingView extends View {

    /**
     * 整个View的宽高
     * */
    private int mTotalHeight, mTotalWidth;

    /**
     * 心跳线的总宽度 -- 圆环的宽度
     * */
    private int mHeartBeatWidth;

    /**
     * 圆环半径 根据view的宽度计算
     * */
    private int mRadius = 200;

    /**
     * 圆环的中心点 -- 画圆环和旋转画布时需要使用
     * */
    private int x, y;

    /**
     * 圆环使用
     * */
    private Paint mRingPaint;

    /**
     * 圆环动画使用 -- 与mRingPaint唯一不同得方在于颜色
     * */
    private Paint mRingAnimPaint;

    /**
     * 圆环大小 矩形
     * */
    private RectF mRectf;

    private Context mContext;

    /**
     * 圆环 宽度
     * */
    private final int mHeartPaintWidth = dp2px(10);

    /**
     * 圆环动画开始时 画弧的偏移量
     * */
    private int mAnimAngle = -1;

    /**
     * 心跳线 Y轴坐标
     * */
    private float[] mOriginalYPositon;

    /**
     * 心跳线 Y轴坐标 -- 默认坐标
     * */
    private float [] mDefaultYPostion;
    // y = Asin(w*x)+Y
    /**
     * sin函数 周期因子
     * */
    private float mPeriodFraction = 0;

    /**
     * 期初的偏移量
     * */
    private final int OFFSET_Y = 0;

    /**
     * canvas抗锯齿开启需要
     * */
    private DrawFilter mDrawFilter;

    /**
     * 正弦曲线偏移量
     * */
    private volatile int mOffset=0;

    /**
     * 振幅
     * */
    private float AmplitudeA = 200;// 振幅

    /**
     * 心跳线条速度
     * */
    private final int SPEED = 5;
    int MARGIN40 = dp2px(40);
    int MARGIN50 = dp2px(50);
    int MARGIN20 = dp2px(20);
    int MARGIN10 = dp2px(10);
    /**
     * 将SPEED转换为实际速度
     * */
    private int mOffsetSpeed;

    /**
     * 绘制心率线Paint
     * */
    private Paint mHeartBeatPaint;

    /**
     * 绘制心率线path的Paint -- 优化
     * */
    private Paint mHeartBeatPathPaint;

    Path path = new Path();

    private Bitmap ring_3x;

    private int bgwidth;

    private RectF rectf;

    private String kg="kg";
    private int middleTextSize = sp2px(30);
    private int bottomTextSize = sp2px(18);
    private int circleTextSize = sp2px(10);
    float mm;
    private int MAXANGLE = 45;
    private int MINANGLE = -225;
    private float currentValue=35;
    public float getCurrentValue() {
        return currentValue;
    }


    public void setCurrentValue(float currentValue) {
        this.currentValue = currentValue;
    }

    int[] kedus = {20,30,40,50,60,70,80,90,100,110,120,130,140,150,160};

    int maxvalue = kedus[14];
    int minvalue = kedus[0];
    private float currentAngle;

    private float lastValue;

    private float everyAngle;

    private int indextotal;

    private Bitmap arrow_3x;
    private void init() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mRingPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        if (!isInEditMode()) {
            // 造成错误的代码段
            mRingPaint.setColor(mContext.getResources().getColor(R.color.grey_cacaca));
        }
        mRingPaint.setStrokeWidth(mHeartPaintWidth);
        mRingPaint.setStyle(Paint.Style.STROKE);
        mRingAnimPaint = new Paint(mRingPaint);
        mRingAnimPaint.setColor(mContext.getResources().getColor(R.color.grey_838383));
      arrow_3x = BitmapFactory.decodeResource(getResources(), R.mipmap.arrow_ring);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTotalHeight = h;
        mTotalWidth = w;
        mHeartBeatWidth = w - mHeartPaintWidth*2-MARGIN40; //内圆宽度
        mFirstFrameOffset  =mHeartBeatWidth-1;
        AmplitudeA = (mTotalHeight-2*mHeartPaintWidth)/4;
        mOriginalYPositon = new float[mHeartBeatWidth];//正弦曲线 Y坐标
        mDefaultYPostion = new float[mHeartBeatWidth];
        Arrays.fill(mOriginalYPositon, 0);
        Arrays.fill(mDefaultYPostion, -1);
        // 周期定位总宽度的1/4
        mPeriodFraction = (float) (Math.PI * 2 / mHeartBeatWidth * 3);
        for (int i =  mHeartBeatWidth/3*2; i < mHeartBeatWidth; i++) {
            mOriginalYPositon[i] = (float) (AmplitudeA * Math.sin(mPeriodFraction * i) + OFFSET_Y);
        }
        x = w / 2;
        y = h / 2;
        mRadius = w / 2 - mHeartPaintWidth / 2-dp2px(50); //因为制定了Paint的宽度，因此计算半径需要减去这个
        mRectf = new RectF(x - mRadius, y - mRadius, x + mRadius, y + mRadius);
    }

    private void resetPath(){
        path.reset();
        path.moveTo(mHeartPaintWidth+MARGIN20, mTotalHeight/2-mOriginalYPositon[mOffset]);
        int interval = mHeartBeatWidth - mOffset;
        for(int i=mOffset+1,j=mHeartPaintWidth+MARGIN20;i<mHeartBeatWidth;i++,j++){
            path.lineTo(j, mTotalHeight/2-mOriginalYPositon[i]);
        }
        for(int i=0,j=interval+mHeartPaintWidth+MARGIN20;i<mOffset;i++,j++){
            path.lineTo(j, mTotalHeight/2-mOriginalYPositon[i]);
        }

    }

    private void resetPath1(){
        path.reset();
        path.moveTo(mHeartPaintWidth+MARGIN20, mTotalHeight/2-mOriginalYPositon[mOffset]);
        int interval = mHeartBeatWidth - mOffset;
        //先找到全0的部分
        int index = -1;
        for(int i=mOffset+1;i<mHeartBeatWidth;i++){
            if(mOriginalYPositon[i]==0){
                index = i;
            }else{
                break;
            }
        }
        if(index!=-1){
            path.lineTo(mHeartPaintWidth+MARGIN20+(index-mOffset+1), mTotalHeight/2);
            for(int i=index+1,j=mHeartPaintWidth+MARGIN20+(index-mOffset+2);i<mHeartBeatWidth;i++,j++){
                path.lineTo(j, mTotalHeight/2-mOriginalYPositon[i]);
            }
        }else{
            for(int i=mOffset+1,j=mHeartPaintWidth+MARGIN20;i<mHeartBeatWidth;i++,j++)
                path.lineTo(j, mTotalHeight/2-mOriginalYPositon[i]);
        }
        //查找最后全为0的index
        index = -1;
        for(int i =0;i<mOffset;i++){
            if(mOriginalYPositon[i]==0)
                index = i;
            else
                break;
        }
        if(index !=-1){
            //修正视觉偏移量
            path.lineTo(mHeartPaintWidth+MARGIN20+(mHeartBeatWidth-mOffset), mTotalHeight/2);
            path.lineTo(interval+mHeartPaintWidth+MARGIN20+index, mTotalHeight/2);
            for(int i=index+1,j=interval+mHeartPaintWidth+MARGIN20+index+1;i<mOffset;i++,j++){
                path.lineTo(j, mTotalHeight/2-mOriginalYPositon[i]);
            }
        }else{
            for(int i=0,j=interval+mHeartPaintWidth+MARGIN20;i<mOffset;i++,j++)
                path.lineTo(j, mTotalHeight/2-mOriginalYPositon[i]);
        }

    }
//    private void drawWheel(Canvas canvas) {
//        Drawable wheel = getResources().getDrawable(R.drawable.ring_3x);
//        wheel.setBounds(0, 0, getWidth(), getHeight());
//        wheel.draw(canvas);
//    }

    private void drawArrow(Canvas canvas){ //======
        ring_3x = BitmapFactory.decodeResource(getResources(), R.mipmap.ring_3x);
        bgwidth =mRadius+dp2px(26);
        mm = (float)bgwidth/(float)ring_3x.getWidth()*2;
        System.out.println("getHeight=="+ring_3x.getHeight()*mm+"=mTotalHeight=="+mTotalHeight+"==getWidth=="+ring_3x.getWidth()+"==bgwidth=="+bgwidth);
        rectf = new RectF();
        rectf.left = mTotalWidth/2 - bgwidth;
        rectf.right = mTotalWidth/2 + bgwidth;
        rectf.top = mTotalHeight/2 - bgwidth;
        rectf.bottom = mTotalHeight/2+ ring_3x.getHeight()*mm - bgwidth;
        canvas.drawBitmap(ring_3x, null, rectf, mRingPaint);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        everyAngle = (float)(MAXANGLE-MINANGLE)/((float)140);
        mDrawFilter=new PaintFlagsDrawFilter(0,Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);
        canvas.setDrawFilter(mDrawFilter);
        drawArrow(canvas);
        TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(sp2px(circleTextSize));
//		drawWheel(canvas);




        if(startAngle==-1 && endAngle==-1){
            currentAngle = (float)(MINANGLE+(float)(lastValue-minvalue)*everyAngle);
            if(currentAngle<=MINANGLE){
                currentAngle=MINANGLE;
            }else if(currentAngle>=MAXANGLE){
                currentAngle=MAXANGLE;
            }
        }else if(moveAngle==0 && startAngle==0){
            lastValue = currentValue;
            if(lastValue<minvalue){
                lastValue=minvalue;
            }else if(lastValue>maxvalue){
                lastValue=maxvalue;
            }
            currentAngle = (float)(MINANGLE+(float)(lastValue-minvalue)*everyAngle);
            if(currentAngle<=MINANGLE){
                currentAngle=MINANGLE;
            }else if(currentAngle>=MAXANGLE){
                currentAngle=MAXANGLE;
            }
        }
        else if(moveAngle==0 || endAngle==-1){
            lastValue = (float) ((startAngle-MINANGLE)/2+minvalue);
            if(lastValue<minvalue){
                lastValue=minvalue;
            }else if(lastValue>maxvalue){
                lastValue=maxvalue;
            }
            System.out.println("lastValue="+lastValue);
            currentAngle = (float)(MINANGLE+(float)(lastValue-minvalue)*everyAngle);
            if(currentAngle<=MINANGLE){
                currentAngle=MINANGLE;
            }else if(currentAngle>=MAXANGLE){
                currentAngle=MAXANGLE;
            }
        }else{
            lastValue = (float) ((endAngle-MINANGLE)/2+minvalue);
            System.out.println("lastValue="+lastValue);
            if(lastValue<minvalue){
                lastValue=minvalue;
            }else if(lastValue>maxvalue){
                lastValue=maxvalue;
            }
            currentAngle = (int)(MINANGLE+(float)(lastValue-minvalue)*everyAngle);
            if(currentAngle<=MINANGLE){
                currentAngle=MINANGLE;
            }else if(currentAngle>=MAXANGLE){
                currentAngle=MAXANGLE;
            }
            System.out.println("currentAngle=="+currentAngle+"=="+moveAngle+"==lastValue=="+lastValue);
        }
        DecimalFormat format = new DecimalFormat("0.0");

        currentValue = Float.parseFloat(format.format(lastValue));


        textPaint.setTextSize(middleTextSize);
        textPaint.setColor(getResources().getColor(R.color.green_62cdba));
        Rect rect =new Rect();
        float textwidth = getTextViewLength(textPaint, currentValue+"");
        textPaint.setTextSize(bottomTextSize);
        float textwidth2 = getTextViewLength(textPaint, kg);
        textPaint.setTextSize(middleTextSize);
        canvas.drawText(currentValue+"",x-textwidth2/2-textwidth/2,y, textPaint);

        textPaint.setTextSize(bottomTextSize);
        canvas.drawText(kg,x+textwidth/2-textwidth2/2,y, textPaint);
        textPaint.setColor(Color.GRAY);
        canvas.drawText(kedus[kedus.length-1]+kg, (int)(x-dp2px(20)-textwidth2+(mRadius)*Math.cos(Math.toRadians(MAXANGLE+10))),(int) (y+(mRadius)*Math.sin(Math.toRadians(MAXANGLE+10))), textPaint);
        canvas.drawText(kedus[0]+kg, (int)(x-dp2px(10)+(mRadius)*Math.cos(Math.toRadians(MINANGLE-10))),(int) (y+(mRadius)*Math.sin(Math.toRadians(MINANGLE-10))), textPaint);
//		RectF rectf_arrow = new RectF();
//		rectf_arrow.left = mTotalWidth/2 - bgwidth;
//		rectf_arrow.right = mTotalWidth/2 + bgwidth;
//		rectf_arrow.top = mTotalHeight/2 - bgwidth;
//		rectf_arrow.bottom = mTotalHeight/2+ ring_3x.getHeight()*mm - bgwidth;
        textPaint.setTextSize(sp2px(8));
        Matrix matrix=new Matrix();
        int arrow_width = bgwidth;

        //以图片中心作为旋转中心，旋转180°
        matrix.postRotate(currentAngle+90, arrow_3x.getWidth() / 2, arrow_3x.getHeight() / 2);
        matrix.postTranslate((int)(x-dp2px(13)+arrow_width*Math.cos(Math.toRadians(currentAngle))), y-dp2px(13)+(int)(arrow_width*Math.sin(Math.toRadians(currentAngle))));//移到·100,100

        //设置抗锯齿,防止过多的失真
        mRingAnimPaint.setAntiAlias(true);

        //============
        canvas.drawBitmap(arrow_3x, matrix, mRingAnimPaint);


        //在canvas上抗锯齿
        //由于drawArc默认从x轴开始画，因此需要将画布旋转或者绘制角度旋转，2种方案
        //int level = canvas.save();
        //canvas.rotate(-90, x, y);// 旋转的时候一定要指明中心
        int index = 0;

        textPaint.setTextSize(circleTextSize);
        for (float i = MINANGLE; i <= MAXANGLE; i += everyAngle) {
            canvas.drawArc(mRectf, i, 1, false, mRingPaint);
            indextotal++;

            if(indextotal%10==0 && i>=MINANGLE && i<-90 && index<kedus.length-1){
                index++;
                canvas.drawText(kedus[index]+"", (int)(x-dp2px(5)+(mRadius-dp2px(15))*Math.cos(Math.toRadians(i))),(int) (y+dp2px(5)+(mRadius-dp2px(15))*Math.sin(Math.toRadians(i))), textPaint);
                System.out.println("i=="+i);
            }
            else if(indextotal%10==0  && i>=-90&& i<MAXANGLE && index<kedus.length-2){
                index++;
                canvas.drawText(kedus[index]+"", (int)(x-dp2px(10)+(mRadius-dp2px(15))*Math.cos(Math.toRadians(i))),(int) (y+dp2px(5)+(mRadius-dp2px(15))*Math.sin(Math.toRadians(i+2))), textPaint);
                System.out.println("i=="+i);
            }
        }
//		if (mAnimAngle != -1) {
        // 如果开启了动画
        for (float i = MINANGLE; i < currentAngle; i += everyAngle) {

            canvas.drawArc(mRectf, i, 1, false, mRingAnimPaint);

        }


    }



    // 计算出该TextView中文字的长度(像素)
    public float getTextViewLength(Paint paint,String text){

        // 得到使用该paint写上text的时候,像素为多少
        float textLength = paint.measureText(text);
        return textLength;
    }

	/*---------------------------------动画-----------------------------------------*/


    private int mFirstFrameOffset = 0;

    private volatile boolean StopHeartBeatAnmiFlag = false;

    private float mStartX;

    private float mStartY;

    private float moveAngle=0;

    private float oldValue;

    private double startAngle=0;

    private double endAngle =-1;



    /**
     * 开启圆环动画
     * */
    private void startRingAnim() {
        mAnimAngle = 0;
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (mAnimAngle < (MAXANGLE-MINANGLE)) {

                    mAnimAngle++;
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    postInvalidate();
                }
                mAnimAngle = -1;// 停止动画
//				stopAnim();
            }
        }).start();
    }


    public void stopAnim(){
        StopHeartBeatAnmiFlag = true;
//		StartHeartBeatAnmiFlag = false;

    }

    public void startAnim(){
        startRingAnim();
//		startHeartBeatAnmi();
    }

	/*---------------------------------动画  end------------------------------------*/

    /*---------------------------------构造函数-----------------------------------*/
    public WeightRingView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public WeightRingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public WeightRingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }
    private int dp2px(int value) {
        float v = getContext().getResources().getDisplayMetrics().density;
        return (int) (v * value + 0.5f);
    }

    private int sp2px(int value) {
        float v = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (v * value + 0.5f);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
//		int xPosition = (int) event.getX();
        oldValue = currentValue;
        float mLastX;
        float mLastY;
        float mMove;
        switch (action) {
            case MotionEvent.ACTION_DOWN:

                mStartX = (float) event.getX();
                mStartY = (float) event.getY();

                endAngle=-1;
                float xx = mStartX-x+MARGIN10;
                float yy = mStartY-y+MARGIN10;

                if(xx*xx+yy*yy<(bgwidth/2*bgwidth/2) || (xx*xx+yy*yy)>((bgwidth+MARGIN50)*(bgwidth+MARGIN50))){
//			startAngle=MINANGLE;
                    startAngle=-1;
                }else{
                    if(xx>0 && yy>0){
                        startAngle = Math.toDegrees(Math.atan((float)yy/(float)xx));
                        if(Math.atan((float)yy/(float)xx)>1.25){
                            startAngle=-1;
                        }else{

                        }
                    }else if(xx>0 && yy<0){
                        startAngle = Math.toDegrees(Math.atan((float)yy/(float)xx));

                    }else if(xx<0 && yy<0){

                        startAngle = -(90-Math.toDegrees(Math.atan((float)yy/(float)xx)))-90;


                    }else if(xx<0 && yy>0){
                        startAngle = Math.toDegrees(Math.atan((float)yy/(float)xx))-180;
                        if(Math.atan((float)yy/(float)xx)<-1.25){
                            startAngle=-1;
                        }else{

                        }
                    }
                    if(startAngle==-1){

                    }else{
                        postInvalidate();
                    }
                }
                System.out.println("endAngledown=="+endAngle+"==startAngle=="+startAngle+"=moveAngle="+moveAngle);

                moveAngle = 0;
                break;
            case MotionEvent.ACTION_MOVE:
//			mMove += (mLastX - xPosition);
//			changeMoveAndValue();
                mLastX = (float) event.getX();
                mLastY = (float) event.getY();
                float xx1 = mStartX-x+MARGIN10;
                float yy1 = mStartY-y+MARGIN10;
                float moveX = mLastX-mStartX;
                float moveY = mLastY-mStartY;

                if(xx1*xx1+yy1*yy1<(bgwidth/2*bgwidth/2) || (xx1*xx1+yy1*yy1)>((bgwidth+MARGIN50)*(bgwidth+MARGIN50))){
//				startAngle=MINANGLE;
                    startAngle=-1;
                }else{
                    if(moveX!=0 || moveY!=0){
                        float xx2 = mLastX-x+MARGIN10;
                        float yy2 = mLastY-y+MARGIN10;
                        if(xx2>0 && yy2>0){
                            endAngle = Math.toDegrees(Math.atan((float)yy2/(float)xx2));
                            if(Math.atan((float)yy2/(float)xx2)>1.25){
                                startAngle=-1;
                                endAngle=-1;
                            }else{

                            }
                        }else if(xx2>0 && yy2<0){
                            endAngle = Math.toDegrees(Math.atan((float)yy2/(float)xx2));

                        }else if(xx2<0 && yy2<0){
                            endAngle = -(90-Math.toDegrees(Math.atan((float)yy2/(float)xx2)))-90;

                        }else if(xx2<0 && yy2>0){
                            endAngle = Math.toDegrees(Math.atan((float)yy2/(float)xx2))-180;
                            if(Math.atan((float)yy2/(float)xx2)<-1.25){
                                startAngle=-1;
                                endAngle=-1;
                            }else{

                            }
                        }
                        //				double endAngle =  Math.toDegrees(Math.atan((float)yy2/(float)xx2));
                        moveAngle = (float)(endAngle-startAngle);
                        System.out.println("endAnglemove=="+endAngle+"==startAngle=="+startAngle+"=moveAngle="+moveAngle);
                        if(startAngle==-1 && endAngle==-1){

                        }else{
                            postInvalidate();
                        }
                    }

                }
                System.out.println("endAnglemove=="+endAngle+"==startAngle=="+startAngle+"=moveAngle="+moveAngle);
//			System.out.println("endAngle=="+endAngle+"==startAngle=="+startAngle+"=moveAngle="+moveAngle);


                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_CANCEL:

                break;
            // break;
            default:
                break;
        }

        return true;
    }
//
//	@Override
//	protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {
//
//
//	}
}