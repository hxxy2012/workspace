package com.hike.digitalgymnastic.view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.hiko.enterprisedigital.R;


public class CircleImage extends ImageView {
	Context context;
	public CircleImage(Context context) {
        super(context);
        this.context=context;
    }
  
    public CircleImage(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        this.context=context;
    }
  
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
          
            Path clipPath = new Path(); 
            int w = this.getWidth(); 
            int h = this.getHeight(); 
            clipPath.addRoundRect(new RectF(0, 0, w, h), 10.0f, 10.0f, Path.Direction.CW); 
            canvas.clipPath(clipPath); 
            super.onDraw(canvas);
    }
    public void setImageDrawable(Drawable drawable, int pixels) {
        // TODO Auto-generated method stub
    	int x133=(int) context.getResources().getDimensionPixelSize(R.dimen.x133);
         Bitmap bitmap =toRoundCorner(drawableToBitmap(drawable,x133), pixels); 
         Drawable drawable1 = new BitmapDrawable(bitmap);  
        super.setImageDrawable(drawable1);     
    }
      
  
    public void setImageBitmap(Drawable drawable, int pixels,boolean isNeedSacle,double size) {    
        // TODO Auto-generated method stub
    	int x133=(int) context.getResources().getDimensionPixelSize(R.dimen.x133);
    	if(isNeedSacle){
    		x133=(int) (x133*size);
    	}
    	
        super.setImageBitmap(toRoundCorner(drawableToBitmap(drawable,x133), pixels));
    }
  
    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
    	int strokeWidth=4;
    	int radius=bitmap.getWidth()<bitmap.getHeight()?bitmap.getWidth():bitmap.getHeight();
        //创建一个和原始图片一样大小位图
    	Bitmap output = Bitmap.createBitmap(radius,
   			 radius, Config.ARGB_8888);
        //创建带有位图bitmap的画布
        Canvas canvas = new Canvas(output);
        final int circleColor=0xffffffff;//白色
        //创建一个和原始图片一样大小的矩形
        
        final Rect rect1 = new Rect(0+strokeWidth, 0+strokeWidth, radius-strokeWidth, radius-strokeWidth);
        final Rect rect = new Rect(0, 0, radius, radius);
        final RectF rectF1 = new RectF(rect1);
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;
         // 去锯齿
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(circleColor);
         //画一个和原始图片一样大小的圆角矩形
        canvas.drawRoundRect(rectF1, roundPx, roundPx, paint);
          //设置相交模式
         paint.setXfermode(new PorterDuffXfermode(Mode.SRC_ATOP));
        final int color = 0xff424242;
        paint.setColor(color);
        //把图片画到90度矩形去
        canvas.drawBitmap(bitmap, rect, rectF1, paint);
        
        
       final Paint pt = new Paint();
       //设置相交模式
       pt.setAntiAlias(true);
       pt.setColor(circleColor);
       pt.setXfermode(new PorterDuffXfermode(Mode.DST_ATOP));
        canvas.drawRoundRect(rectF, roundPx, roundPx, pt);
        return output;
    }
  
      
    public static Bitmap drawableToBitmap(Drawable drawable,int x133){
           
    	 int width = drawable.getIntrinsicWidth();
         int height = drawable.getIntrinsicHeight();
         
         int maxsize=width>height?width:height;
         int n=1;
         if(maxsize/x133>=2){
        	 n=maxsize/x133;
         }else
        	 n=1;
        
         
         //创建一个和原始图片一样大小位图
         Bitmap bitmap = Bitmap.createBitmap(x133, x133,
         drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
         : Bitmap.Config.RGB_565);//创建一个指定高、宽的可变的Bitmap图像
        //创建带有位图bitmap的画布
         Canvas canvas = new Canvas(bitmap);
         drawable.setBounds(0,0,x133,x133);
         drawable.draw(canvas);
         return bitmap;
         }
           


}
