package com.hike.digitalgymnastic.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.hiko.enterprisedigital.R;

import java.util.Timer;
import java.util.TimerTask;
public class DeviceSearchImageView extends View {
	public final static int status_search=0;//搜索手环
	public final static int status_research=1;//重新搜索
	public final static int status_search_ing=2;//手环搜索中
	public final static int status_search_ed=3;//搜索完成

	Context context;

	private  int state=status_search;
	/**
	 * view宽度
	 */
	int width;
	/**
	 * view高度
	 */
	int height;

	/**
	 * 搜索状态位图
	 */
	Bitmap bitmap_progress;
	/**
	 * 位图当前旋转角度
	 */
	int currentAngle;

	int stokeWidth=dp2px(3);
	Paint paint;



	public void init() {

		paint=new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setTextAlign(Paint.Align.CENTER);

		bitmap_progress=BitmapFactory.decodeResource(getResources(), R.mipmap.icon_searching);
//		bitmap_progress=Bitmap.createScaledBitmap(bitmap_progress,w);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		drawCircleBg(canvas);
		switch (state){
		case status_search:
			drawSearchView(canvas);
		break;
		case status_research:
			drawResearchView(canvas);
			break;
		case status_search_ing:
			drawSearchingView(canvas);
			break;
			case status_search_ed:
				drawSearchedView(canvas);
				break;

		default:
			break;
	}

	}


	float rate=1.0f;
	boolean isSacalSmall=true;
	/**
	 * 绘制圆底背景
	 */
	private void drawCircleBg(Canvas canvas){
		if(rate==1.0f){
			isSacalSmall=true;
		}else if(rate==0.8f)
		{
			isSacalSmall=false;
		}
		if(isSacalSmall)
			rate=1.0f-currentAngle*0.2f/(-360);
		else
		    rate=0.8f+currentAngle*0.2f/(-360);
		int radius= (int) (width*0.85/2*rate);
		int radiux=width/2;
		int radiuy=height/2;
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.WHITE);
		canvas.drawCircle(radiux, radiuy, radius, paint);
	}
	/**
	 * 绘制搜索手环状态
	 */
private void drawSearchView(Canvas canvas){
	//共沪指40个弧形，间距与弧长相等，共计平分80个角度,每个角度为360/80=4.5f；
	//环的宽度为1px
	paint.setStyle(Paint.Style.STROKE);
	paint.setColor(Color.parseColor("#E9E9E9"));
	paint.setStrokeWidth(stokeWidth);
	canvas.drawCircle(width / 2, height / 2, width / 2 - stokeWidth, paint);
	paint.setColor(context.getResources().getColor(R.color.device_unbind_btncolor));
	paint.setStyle(Paint.Style.FILL);
	canvas.drawText(context.getString(R.string.device_connect), width / 2, height / 2, paint);

}

	/**
	 * 绘制搜索手环状态
	 */
	private void drawResearchView(Canvas canvas){
	//共沪指40个弧形，间距与弧长相等，共计平分80个角度,每个角度为360/80=4.5f；
		//环的宽度为1px
		float sweepAngle=4.5f;
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(stokeWidth);
		paint.setColor(Color.parseColor("#00bea3"));
		RectF rectf=new RectF(0.025f*width+stokeWidth,0.025f*height+stokeWidth,width-stokeWidth-0.025f*width,height -stokeWidth-0.025f*height);
		for(int i=0;i<40;i++){
			canvas.drawArc(rectf,i*4.5f*2,  sweepAngle,false,  paint);
		}

		paint.setColor(context.getResources().getColor(R.color.device_unbind_btncolor));
		paint.setStyle(Paint.Style.FILL);
		canvas.drawText("重新搜索",width/2,height/2,paint);
	}


	/**
	 * 绘制手环搜索中状态
	 */
	private void drawSearchingView(Canvas canvas){
		paint.setColor(context.getResources().getColor(R.color.device_unbind_btncolor));
		canvas.drawText("搜索中", width / 2, height / 2, paint);
		Matrix matrix =new Matrix();
		int newWidth= (int) (width * 0.95f / bitmap_progress.getWidth()*bitmap_progress.getWidth());
		int newHeight= (int) (width * 0.95f / bitmap_progress.getHeight()* bitmap_progress.getHeight());

		matrix.preScale(width * 0.95f / bitmap_progress.getWidth(), height * 0.95f / bitmap_progress.getHeight());
		matrix.preTranslate((width -newWidth)/2, (width -newWidth)/2);
		matrix.preRotate(currentAngle, bitmap_progress.getWidth() / 2, bitmap_progress.getHeight() / 2);  //要旋转的角度

		canvas.drawBitmap(bitmap_progress, matrix, paint);

	}

	/**
	 * 绘制手环完成状态
	 */
	private void drawSearchedView(Canvas canvas){
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(Color.parseColor("#E9E9E9"));
		paint.setStrokeWidth(stokeWidth);
		canvas.drawCircle(width / 2, height / 2, width / 2 - stokeWidth, paint);
		paint.setColor(Color.GRAY);
		paint.setStyle(Paint.Style.FILL);
		canvas.drawText("搜索完成", width / 2, height / 2, paint);

	}
	Timer timer=new Timer();
	TimerTask timerTask=new MyTimerTask() {
		@Override
		public void run() {
			currentAngle-=5;
			if(currentAngle<-360)
				currentAngle=0;
			postInvalidate();
		}
	};

	class MyTimerTask  extends  TimerTask{

		@Override
		public void run() {
			currentAngle-=5;
			if(currentAngle<-360)
				currentAngle=0;
			postInvalidate();
		}
	}

	/**
	 * 搜索失败显示重新搜索
	 */
	public  void   initSearch(){
		Log.d("MyLog","initSearch==");
		try {
			currentAngle=0;
			rate=1.0f;
			isSacalSmall=true;
			state=status_search;
			timer.cancel();
			timerTask.cancel();
			invalidate();
		}catch (Exception e){

		}
	}

	/**
	 * 开始搜索
	 */
	public  void   startSearch(){
		Log.d("MyLog","startSearch==");
		try {
			currentAngle=0;
			rate=1.0f;
			isSacalSmall=true;
			state=status_search_ing;
			timer=new Timer();
			timerTask=new MyTimerTask();
			timer.schedule(timerTask,0,30);
		}catch (Exception e){

		}
	}

	/**
	 * 搜索失败显示重新搜索
	 */
	public  void   stopSearch(){
		try {
			currentAngle=0;
			 rate=1.0f;
			 isSacalSmall=true;
			state=status_research;
			timer.cancel();
			timerTask.cancel();
			invalidate();
		}catch (Exception e){

		}
	}
	/**
	 * 搜索完成
	 */
	public  void   finishSearch(){
		Log.d("MyLog","finishSearch==");
		try {
			currentAngle=0;
			rate=1.0f;
			isSacalSmall=true;
			state=status_search_ed;
			timer.cancel();
			timerTask.cancel();
			invalidate();
		}catch (Exception e){

		}
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

			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				lastX = event.getX();
				lastY = event.getY();
				break;
			case MotionEvent.ACTION_UP:
				if(state==status_research||state==status_search){
					if (Math.abs(lastX - event.getX()) > 10
							|| Math.abs(lastY - event.getY()) > 10) {
						return false;
					} else {
						if(listener!=null)
							listener.onTouch();
					}
				}
				break;

			default:
				break;
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
		width=	MeasureSpec.getSize(widthMeasureSpec);
		height=MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width,height);
		paint.setTextSize((float) (height*0.85/10));
        
    }



	/*构造方法*/
	public DeviceSearchImageView(Context context, AttributeSet attrs,
								 int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		this.context=context;

		init();
	}

	public DeviceSearchImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context=context;
		init();
	}

	public DeviceSearchImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context=context;
		init();
	}




//工具类
	private int dp2px(int value) {
		float v = getContext().getResources().getDisplayMetrics().density;
		return (int) (v * value + 0.5f);
	}

	private int sp2px(int value) {
		float v = getContext().getResources().getDisplayMetrics().scaledDensity;
		return (int) (v * value + 0.5f);
	}
}
