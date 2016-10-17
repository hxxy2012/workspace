package com.hike.digitalgymnastic.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.os.Environment;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.HeaderViewListAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.hike.digitalgymnastic.adapter.TimeLineAdapter;
import com.lidroid.xutils.http.RequestParams;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Utils {
	public static final double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
	public static final int padding = 30;
	private static final int quality = 70;
	public static final float scale = (float) 0.93;

	public static String md5(String string) {

		byte[] hash;

		try {

			hash = MessageDigest.getInstance("MD5").digest(
					string.getBytes("UTF-8"));

		} catch (NoSuchAlgorithmException e) {

			throw new RuntimeException("Huh, MD5 should be supported?", e);

		} catch (UnsupportedEncodingException e) {

			throw new RuntimeException("Huh, UTF-8 should be supported?", e);

		}

		StringBuilder hex = new StringBuilder(hash.length * 2);

		for (byte b : hash) {

			if ((b & 0xFF) < 0x10)
				hex.append("0");

			hex.append(Integer.toHexString(b & 0xFF));

		}

		return hex.toString();

	}

	/**
	 * 获取版本�?
	 * 
	 * @return 当前应用的版本号
	 */
	public static String getVersion(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(),
					0);
			return info.versionName;
		} catch (Exception e) {
			e.printStackTrace();
			return "1.0";
		}
	}

	public static synchronized Bitmap createViewBitmap(View v) {
		Bitmap bitmap = Bitmap.createBitmap(v.getMeasuredWidth(),
				v.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		v.draw(canvas);
		return bitmap;
	}
	/**
	 * 判断两个日期是否是同一天
	 * @param pNeedTimeString
	 * @return
	 */
	public  static boolean isYesterday(String pNeedTimeString) {

		if (pNeedTimeString == null || pNeedTimeString.equals(""))
			return false;
		// 转换为标准时间
		SimpleDateFormat _Formatter = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date _nowDate = null;
		java.util.Date _needTime = null;
		try {
			String _now = getStringDate();
			_nowDate = _Formatter.parse(_now);
			_needTime = _Formatter.parse(pNeedTimeString);
		} catch (Exception e) {
		}
		long day = (_nowDate.getTime() - _needTime.getTime()) / (24 * 60 * 60 * 1000);
		if (Math.abs(day)== 1){
			return true;
		}else {
			return false;
		}

	}
	private static String getStringDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		return dateString;
	}
	public static String getStandardDate(String timeStr) {

		StringBuffer sb = new StringBuffer();
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dt2 = null;
		try {
			dt2 = sdf.parse(timeStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//继续转换得到秒数的long型
//		long lTime = dt2.getTime() / 1000;
		long t=dt2.getTime() / 1000;  ;
//		long t = Long.parseLong(timeStr);
		long time = System.currentTimeMillis() - (t * 1000);
		long mill = (long) Math.ceil(time / 1000);// 秒前

		long minute = (long) Math.ceil(time / 60 / 1000.0f);// 分钟前

		long hour = (long) Math.ceil(time / 60 / 60 / 1000.0f);// 小时

		long day = (long) Math.ceil(time / 24 / 60 / 60 / 1000.0f);// 天前

		if (day - 1 > 0) {
			sb.append(day + "天");
		} else if (hour - 1 > 0) {
			if (hour >= 24) {
				sb.append("1天");
			} else {
				sb.append(hour + "小时");
			}
		} else if (minute - 1 > 0) {
			if (minute == 60) {
				sb.append("1小时");
			} else {
				sb.append(minute + "分钟");
			}
		} else if (mill - 1 > 0) {
			if (mill == 60) {
				sb.append("1分钟");
			} else {
				sb.append(mill + "秒");
			}
		} else {
			sb.append("刚刚");
		}
		if (!sb.toString().equals("刚刚")) {
			sb.append("前");
		}
		return sb.toString();
	}

	/**
	 * 截取scrollview的屏
	 * 
	 * @param scrollView
	 * @return
	 */
	public static synchronized Bitmap getBitmapByView(ScrollView scrollView, int padding) {
		int h = 0;
		Bitmap bitmap = null;
		// 获取scrollview实际高度，最底部按钮不转画成图像
		for (int i = 0; i < scrollView.getChildCount(); i++) {
			h += scrollView.getChildAt(i).getHeight();
			// scrollView.getChildAt(i).setBackgroundColor(
			// Color.parseColor("#ffffff"));
		}
		LinearLayout ll = (LinearLayout) scrollView.getChildAt(0);
		if (ll.getChildCount() > 0) {
			h -= ll.getChildAt(ll.getChildCount() - 1).getHeight();
		}
		// 创建对应大小的bitmap
		bitmap = Bitmap.createBitmap(scrollView.getWidth(), h - padding,
				Bitmap.Config.RGB_565);
		 Canvas canvas = new Canvas(bitmap);
//		 canvas.drawColor(Color.parseColor("#323a5d"));
		scrollView.draw(canvas);
		return bitmap;
	}
	/**
	 * 截取scrollview的屏
	 *
	 * @param scrollView
	 * @return
	 */
	public static synchronized Bitmap getBitmapByView(ScrollView scrollView,int  bg_color, int padding) {

		int h = 0;
		Bitmap bitmap = null;
		// 获取scrollview实际高度，最底部按钮不转画成图像
		for (int i = 0; i < scrollView.getChildCount(); i++) {
			h += scrollView.getChildAt(i).getHeight();
			// scrollView.getChildAt(i).setBackgroundColor(
			// Color.parseColor("#ffffff"));
		}
		LinearLayout ll = (LinearLayout) scrollView.getChildAt(0);
		if (ll.getChildCount() > 0) {
			h -= ll.getChildAt(ll.getChildCount() - 1).getHeight();
		}
		// 创建对应大小的bitmap
		bitmap = Bitmap.createBitmap(scrollView.getWidth(), (h - padding),
				Bitmap.Config.RGB_565);

		Canvas canvas = new Canvas(bitmap);
		canvas.drawColor(bg_color);
		scrollView.buildDrawingCache(true);
		scrollView.draw(canvas);
//		if (bitmap != null && !bitmap.isRecycled()) { bitmap.recycle(); bitmap = null; }

		return bitmap;
	}
	public static Bitmap duplicateBitmap(Bitmap bmpSrc)
	{
		if (null == bmpSrc)
		{ return null; }

		int bmpSrcWidth = bmpSrc.getWidth();
		int bmpSrcHeight = bmpSrc.getHeight();

		Bitmap bmpDest = Bitmap.createBitmap(bmpSrcWidth, bmpSrcHeight, Bitmap.Config.ARGB_8888);
		if (null != bmpDest) {
			Canvas canvas = new Canvas(bmpDest);
			final Rect rect = new Rect(0, 0, bmpSrcWidth, bmpSrcHeight);
			canvas.drawBitmap(bmpSrc, rect, rect, null);
		}

		return bmpDest;
	}
	public static Bitmap decodeSampledBitmapFromFile(String pathName, int reqWidth, int reqHeight) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(pathName, options);

		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		options.inJustDecodeBounds = false;
		Bitmap src = BitmapFactory.decodeFile(pathName, options);
//		return createScaleBitmap(src, reqWidth, reqHeight, options.inSampleSize);
		return src;
	}
	/**
	 * @description 通过传入的bitmap，进行压缩，得到符合标准的bitmap
	 *
	 * @param src
	 * @param dstWidth
	 * @param dstHeight
	 * @return
	 */
	private static Bitmap createScaleBitmap(Bitmap src, int dstWidth, int dstHeight, int inSampleSize) {
		//如果inSampleSize是2的倍数，也就说这个src已经是我们想要的缩略图了，直接返回即可。
		if (inSampleSize % 2 == 0) {
			return src;
		}
		// 如果是放大图片，filter决定是否平滑，如果是缩小图片，filter无影响，我们这里是缩小图片，所以直接设置为false
		Bitmap dst = Bitmap.createScaledBitmap(src, dstWidth, dstHeight, false);
		if (src != dst) { // 如果没有缩放，那么不回收
			src.recycle(); // 释放Bitmap的native像素数组
		}
		return dst;
	}

	/**
	 * 截取ListView的屏�?
	 * 
	 * @param listView
	 * @return
	 */
	public static synchronized Bitmap getBitmapByView(ListView listView, int padding) {
		int totalHeight = 0;
		Bitmap bitmap = null;
		HeaderViewListAdapter adp=(HeaderViewListAdapter) listView.getAdapter();
		TimeLineAdapter listAdapter=(TimeLineAdapter) adp.getWrappedAdapter();
		// 获取scrollview实际高度，最底部按钮不转画成图像
//		for (int i = 0; i < adapter.getCount(); i++) {
//			if(listView.getv==R.id.sport_footer_view)
//				h += listView.getChildAt(i).getHeight()*2/3;
//			else
//				h += listView.getChildAt(i).getHeight();
//		}
		View listItem = listAdapter.getView(0, null, listView);
		listItem.measure(0, 0);
		totalHeight = listItem.getMeasuredHeight() * listAdapter.getCount();

		 totalHeight+=(listView.getDividerHeight() * (listAdapter.getCount() - 1));
		// 创建对应大小的bitmap
		bitmap = Bitmap.createBitmap(listView.getWidth(), totalHeight- padding,
				Bitmap.Config.RGB_565);
		final Canvas canvas = new Canvas(bitmap);
		listView.draw(canvas);
		return bitmap;
	}
	/**
	 * @category 截取动�?�创建View的视图的屏幕
	 * @param view
	 * @param padding
	 * @return
	 */
	public static synchronized Bitmap getBitmapByViewCache(View view, int padding) {
		Bitmap bitmap = null;
		view.setDrawingCacheEnabled(true);
		    // this is the important code :)  
		    // Without it the view will have a dimension of 0,0 and the bitmap will be null          
		view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), 
		                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight()); 
	    bitmap = Bitmap.createBitmap(view.getDrawingCache());
		return bitmap;
	}
	/**
	 * @category 截取普�?�View的视图的屏幕
	 * @param view
	 * @param padding
	 * @return
	 */
	public static synchronized Bitmap getBitmapByView(View view, int padding) {
		int h = 0;
		Bitmap bitmap = null;
		// 创建对应大小的bitmap
		bitmap = Bitmap.createBitmap(view.getWidth(),
				view.getHeight() - padding, Bitmap.Config.RGB_565);
		final Canvas canvas = new Canvas(bitmap);
		view.draw(canvas);
		return bitmap;
	}
	
	/**
	 * 获取运动分享的图�?
	 * 
	 * @param bg_color
	 * @return
	 */
	public static synchronized Bitmap getBitmapSportList(int bg_color,List<Bitmap> bmpList) {
		int totalHeight = 0;
		int totalWidth = 0;
		Bitmap bitmap;
		Bitmap bmp;

		// 获取scrollview实际高度，最底部按钮不转画成图像
		for (int i = 0; i < bmpList.size(); i++) {// 循环�?有的view,累加获得总的高度
			if (bmpList.get(i) != null) {
				if (bmpList.get(i).getWidth() > totalWidth)
					totalWidth = bmpList.get(i).getWidth();
				totalHeight += bmpList.get(i).getHeight();
			}
		}

		// 创建对应大小的bitmap
		bitmap = Bitmap.createBitmap(totalWidth, totalHeight,
				Bitmap.Config.RGB_565);
		final Canvas canvas = new Canvas(bitmap);
		canvas.drawColor(bg_color);

		int cur_height = 0;
		for (int i = 0; i < bmpList.size(); i++) {
			bmp = bmpList.get(i);
			if (bmp != null) {
				canvas.drawBitmap(bmp, 0, cur_height, null);
				cur_height += bmp.getHeight();
				bmp.recycle();
			}
		}
		bmpList.clear();
		System.gc();
		return bitmap;
	}
	/**
	 * 获取运动分享的图�?
	 *
	 * @param
	 *
	 *
	 * @return
	 */
	public static synchronized Bitmap getBitmapSportList(Bitmap bg_bitmap,List<Bitmap> bmpList) {
		int totalHeight = 0;
		int totalWidth = 0;
		Bitmap bitmap;
		Bitmap bmp;

		// 获取scrollview实际高度，最底部按钮不转画成图像
		for (int i = 0; i < bmpList.size(); i++) {
		// 循环所有有的view,累加获得总的高度
			if (bmpList.get(i) != null) {
				if (bmpList.get(i).getWidth() > totalWidth)
					totalWidth = bmpList.get(i).getWidth();
				totalHeight += bmpList.get(i).getHeight();
			}
		}

		// 创建对应大小的bitmap
		bitmap = Bitmap.createBitmap(totalWidth, totalHeight,
				Bitmap.Config.RGB_565);
		final Canvas canvas = new Canvas(bitmap);
		RectF rectf=new RectF(0,0,totalWidth,totalHeight);
		canvas.drawBitmap(bg_bitmap,null,rectf,new Paint());
		int cur_height = 0;
		for (int i = 0; i < bmpList.size(); i++) {
			bmp = bmpList.get(i);
			if (bmp != null) {
				canvas.drawBitmap(bmp, 0, cur_height, null);
				cur_height += bmp.getHeight();
				bmp.recycle();
			}
		}
		bmpList.clear();
		System.gc();

		return bitmap;
	}
	/**
	 * 截取RelativeLayout的屏�?
	 * 
	 * @param relativeLayout
	 * @return
	 */
	public static synchronized Bitmap getBitmapByView(RelativeLayout relativeLayout,
			int padding) {
		int h = 0;
		Bitmap bitmap = null;

		// 创建对应大小的bitmap
		bitmap = Bitmap.createBitmap(relativeLayout.getWidth(),
				relativeLayout.getHeight() - padding, Bitmap.Config.RGB_565);
		final Canvas canvas = new Canvas(bitmap);
		relativeLayout.draw(canvas);
		return bitmap;
	}

	/**
	 * 压缩图片
	 * 
	 * @param image
	 * @return
	 */
	public static Bitmap compressImage(Bitmap image) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// 质量压缩方法，这100表示不压缩，把压缩后的数据存放到baos
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		int options = 100;
		// 循环判断如果压缩后图片是否大�?100kb,大于继续压缩
		while (baos.toByteArray().length / 1024 > 100) {
			// 重置baos
			baos.reset();
			// 这里压缩options%，把压缩后的数据存放到baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);
			// 每次都减10
			options -= 10;
		}
		// 把压缩后的数据baos存放到ByteArrayInputStream�?
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		// 把ByteArrayInputStream数据生成图片
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
		return bitmap;
	}

	/**
	 * 保存到sdcard
	 * 
	 * @param b
	 * @return
	 */
	public static String savePic(Bitmap b, String fileName) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss",
				Locale.US);
		File outfile;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			outfile = new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/techfit/share" + "/" + fileName);
		}else{
			outfile = new File(Contants.FILES + "/" + fileName);
		}
		// 如果文件不存在，则创建一个新文件
		if (!outfile.exists()) {
			try {
				outfile.mkdirs();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String fname = null;
		// if(!TextUtils.isEmpty(fileName))
		// fname = outfile + "/" + fileName + ".jpg";
		// else
		if (TextUtils.equals("picHead", fileName))
			fname = outfile + "/" + fileName + ".jpg";
		else
			fname = outfile + "/" + sdf.format(new Date()) + ".jpg";
		File file = new File(fname);
		if (file.exists())
			file.delete();
		
		FileOutputStream fos = null;
		try {
			file.createNewFile();
			fos = new FileOutputStream(fname);
			if (null != fos) {
				b.compress(Bitmap.CompressFormat.PNG, 100, fos);
				fos.flush();
				fos.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
		}
		return fname;
	}

	/**
	 * 保存到sdcard
	 * 
	 * @param b
	 * @return
	 */
	public static String saveHeadPic(Bitmap b) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss",
				Locale.US);
		File outfile = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/techfit/headimg");
		// 如果文件不存在，则创建一个新文件
		if (!outfile.exists()) {
			try {
				outfile.mkdirs();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String fname = null;
		fname = outfile + "/head.jpg";
		File file = new File(fname);
		if (file.exists())
			file.delete();
		FileOutputStream fos = null;
		try {
			file.createNewFile();
			fos = new FileOutputStream(fname);
			if (null != fos) {
				b.compress(Bitmap.CompressFormat.PNG, 90, fos);
				fos.flush();
				fos.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fname;
	}

	public static void setListViewHeightBasedOnChildren(ListView listView) {
		Adapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		// for (int i = 0; i < listAdapter.getCount(); i++) {
		// View listItem = listAdapter.getView(i, null, listView);
		// // View listItem=listView.getChildAt(i);
		// listItem.measure(0, 0);
		// totalHeight += listItem.getMeasuredHeight();
		// }
		if (listAdapter.getCount() > 0) {
			View listItem = listAdapter.getView(0, null, listView);
			listItem.measure(0, 0);
			totalHeight = listItem.getMeasuredHeight() * listAdapter.getCount();

			ViewGroup.LayoutParams params = listView.getLayoutParams();
			params.height = totalHeight
					+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
			listView.setLayoutParams(params);
		} else {
			ViewGroup.LayoutParams params = listView.getLayoutParams();
			params.height = totalHeight;
			listView.setLayoutParams(params);
		}
	}

	public static Bitmap bitmapToScale(Bitmap bitmap, int parentWidth,
			int parentHeight) {
		Bitmap icon = null;
		// ԭʼͼƬ
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Bitmap newIcon = null;
		// ��С�ı���
		if (width >= (parentWidth) * scale || height >= (parentWidth) * scale) {
			Matrix matrix = new Matrix();
			matrix.postScale((float) (parentWidth) * scale / width,
					(float) (parentWidth) * scale / height);
			newIcon = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix,
					true);
			bitmap.recycle();
		}
		return newIcon;
	}

	public static Bitmap bitmapToScale(Bitmap bitmap, float rate) {
		if (rate == 1) {
			return bitmap;
		}
		Bitmap icon = null;
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Bitmap newIcon = null;
		Matrix matrix = new Matrix();
		matrix.postScale(rate, rate);
		newIcon = Bitmap
				.createBitmap(bitmap, 0, 0, width, height, matrix, true);
		bitmap.recycle();
		return newIcon;
	}

	public static Bitmap bitmapToScale(Bitmap bitmap, int tartgetWidth,
			int targetHeight, float degrees) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float ratex = (float) tartgetWidth / width;
		float ratey = (float) targetHeight / height;
		Bitmap newIcon = null;
		Matrix matrix = new Matrix();
		matrix.postScale(ratex, ratey);
		matrix.postRotate(degrees);
		newIcon = Bitmap
				.createBitmap(bitmap, 0, 0, width, height, matrix, true);
		bitmap.recycle();
		return newIcon;
	}

	public static Bitmap zhizhenToScale(Bitmap bitmap, float degrees) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Bitmap newIcon = null;
		Matrix matrix = new Matrix();
		matrix.postRotate(degrees);
		newIcon = Bitmap
				.createBitmap(bitmap, 0, 0, width, height, matrix, true);
		bitmap.recycle();
		return newIcon;
	}

	public static Bitmap zhizhenorgToScale(Bitmap bitmap, float degrees) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Bitmap newIcon = null;
		Matrix matrix = new Matrix();
		matrix.postRotate(degrees);
		newIcon = Bitmap
				.createBitmap(bitmap, 0, 0, width, height, matrix, true);
		return newIcon;
	}

	public static Bitmap zhizhenToScale(Bitmap bitmap, int tartgetWidth,
			int targetHeight) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float ratex = (float) tartgetWidth / width;
		float ratey = (float) targetHeight / height;
		Bitmap newIcon = null;
		Matrix matrix = new Matrix();
		matrix.postScale(ratex, ratey);
		newIcon = Bitmap
				.createBitmap(bitmap, 0, 0, width, height, matrix, true);
		bitmap.recycle();
		return newIcon;
	}

	public static Bitmap Bitmapsuofang(Bitmap bitmap) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 1;
		bitmap = BitmapFactory.decodeStream(Utils.Bitmap2InputStream(bitmap),
				null, options);
		return bitmap;
	}

	public static InputStream Byte2InputStream(byte[] b) {
		ByteArrayInputStream bais = new ByteArrayInputStream(b);
		return bais;
	}

	public static byte[] InputStream2Bytes(InputStream is) {
		String str = "";
		byte[] readByte = new byte[1024];
		int readCount = -1;
		try {
			while ((readCount = is.read(readByte, 0, 1024)) != -1) {
				str += new String(readByte).trim();
			}
			return str.getBytes();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	//
	public static void bitmap2JPG(Bitmap mBitmap, File f) {
		if (!f.getParentFile().exists())
			f.getParentFile().mkdirs();
		if (f.exists()) {
			f.delete();
		}

		FileOutputStream fOut = null;
		try {
			f.createNewFile();
			fOut = new FileOutputStream(f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
		try {
			fOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static InputStream Bitmap2InputStream(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, quality, baos);
		InputStream is = new ByteArrayInputStream(baos.toByteArray());
		return is;
	}

	public static InputStream Bitmap2InputStream(Bitmap bm, int quality) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, quality, baos);
		InputStream is = new ByteArrayInputStream(baos.toByteArray());
		return is;
	}

	public static Bitmap InputStream2Bitmap(InputStream is) {
		return BitmapFactory.decodeStream(is);
	}

	public static Bitmap ImageFile2Bitmap(File bmFile) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		String path = bmFile.getAbsolutePath();
		BitmapFactory.decodeFile(path, opts);

		int inSampleSize = (int) Math.ceil(Math.sqrt(opts.outWidth
				* opts.outHeight / (160 * 160)));
		// int inSampleSize=4;
		opts.inJustDecodeBounds = false;
		opts.inSampleSize = inSampleSize;
		Bitmap comBm = BitmapFactory.decodeFile(path, opts);
		Bitmap resizeBmp = ThumbnailUtils.extractThumbnail(comBm, 320, 320);
		return resizeBmp;
	}

	public static InputStream Drawable2InputStream(Drawable d) {
		Bitmap bitmap = drawable2Bitmap(d);
		return Bitmap2InputStream(bitmap);
	}

	public static Drawable InputStream2Drawable(InputStream is) {
		Bitmap bitmap = InputStream2Bitmap(is);
		return bitmap2Drawable(bitmap);
	}

	public static byte[] Drawable2Bytes(Drawable d) {
		Bitmap bitmap = drawable2Bitmap(d);
		return Bitmap2Bytes(bitmap);
	}

	public static Drawable Bytes2Drawable(byte[] b) {
		Bitmap bitmap = Bytes2Bitmap(b);
		return bitmap2Drawable(bitmap);
	}

	public static byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, quality, baos);
		return baos.toByteArray();
	}

	public static Bitmap Bytes2Bitmap(byte[] b) {
		if (b.length != 0) {
			return BitmapFactory.decodeByteArray(b, 0, b.length);
		}
		return null;
	}

	public static Bitmap drawable2Bitmap(Drawable drawable) {
		// BitmapDrawable bd = (BitmapDrawable) drawable;
		// Bitmap bitmap = bd.getBitmap();
		Bitmap bitmap = Bitmap
				.createBitmap(
						drawable.getIntrinsicWidth(),
						drawable.getIntrinsicHeight(),
						drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
								: Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight());
		drawable.draw(canvas);

		bitmap = ThumbnailUtils.extractThumbnail(bitmap, 320, 320);

		return bitmap;
	}

	public static Drawable bitmap2Drawable(Bitmap bitmap) {
		BitmapDrawable bd = new BitmapDrawable(bitmap);
		Drawable d = (Drawable) bd;
		return d;
	}

	public static String file2String(String filePath) {

		return Base64.encodeToString(getBytes(filePath), Base64.NO_WRAP);
	}

	public static byte[] getBytes(String filePath) {
		byte[] buffer = null;
		try {
			File file = new File(filePath);
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}

	public static void getFile(byte[] bfile, String filePath, String fileName) {
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		File file = null;
		try {
			File dir = new File(filePath);
			if (!dir.exists() && dir.isDirectory()) {
				dir.mkdirs();
			}
			file = new File(filePath + fileName);
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(bfile);
			bos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	public static void inputstreamtofile(InputStream ins, String filePath,
			String fileName) {
		OutputStream os = null;
		File file = null;
		try {
			File dir = new File(filePath);
			if (!dir.exists() && dir.isDirectory()) {
				dir.mkdirs();
			}
			file = new File(filePath, fileName);
			if (file.exists())
				file.delete();
			try {
				file.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			try {
				while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
					os.write(buffer, 0, bytesRead);
				}
				os.close();
				ins.close();
				os.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}

	public static Bitmap getSmallBitmap(String filePath) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);

		options.inSampleSize = calculateInSampleSize(options, 480, 480);

		options.inJustDecodeBounds = false;

		return BitmapFactory.decodeFile(filePath, options);
	}

	public static String bitmapToString(String filePath) {

		Bitmap bm = getSmallBitmap(filePath);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, quality, baos);
		byte[] b = baos.toByteArray();
		return Base64.encodeToString(b, Base64.NO_WRAP);
	}

	public static void decoderBase64File(String base64Code, String savePath)
			throws Exception {
		byte[] buffer = Base64.decode(base64Code, Base64.DEFAULT);
		FileOutputStream out = new FileOutputStream(savePath);
		out.write(buffer);
		out.close();
	}

	public Bitmap stringtoBitmap(String string) {
		Bitmap bitmap = null;
		try {
			byte[] bitmapArray;
			bitmapArray = Base64.decode(string, Base64.DEFAULT);
			bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
					bitmapArray.length);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bitmap;
	}

	public static ByteArrayOutputStream compress(String str) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		if (str == null || str.length() == 0) {
			return out;
		}
		GZIPOutputStream gzip = new GZIPOutputStream(out);
		gzip.write(str.getBytes("UTF-8")); // UTF-8
		gzip.flush();
		gzip.close();
		return out;
	}


	public static String uncompress(InputStream is) {
		ByteArrayOutputStream out = null;
		String result = "";
		try {
			if (is == null) {
				return "";
			}
			out = new ByteArrayOutputStream();
			// ByteArrayInputStream in = new ByteArrayInputStream(
			// str.getBytes("UTF-8"));//ISO-8859-1
			GZIPInputStream gunzip = new GZIPInputStream(is);
			byte[] buffer = new byte[256];
			int n;
			while ((n = gunzip.read(buffer)) >= 0) {
				out.write(buffer, 0, n);
			}
			out.flush();
			out.close();
			result = out.toString("UTF-8");
		} catch (IOException e) {
			// return "";
		}
		if (out == null)
			return "";

		return result;
	}


	public static Bitmap getImage(String path) throws Exception {
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("GET");
		conn.connect();
		if (conn.getResponseCode() == 200) {
			InputStream inStream = conn.getInputStream();
			return BitmapFactory.decodeStream(inStream);
		}
		return null;
	}

	public static int getSeconds(Date startdate, Date enddate) {
		long time = enddate.getTime() - startdate.getTime();
		int totalS = new Long(time / 1000).intValue();
		return totalS;
	}

	public static String Md5(String sourceStr) {
		String result = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(sourceStr.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			result = buf.toString();
			System.out.println("result: " + result);

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block e.printStackTrace();
		}
		return result.toUpperCase();
	}

	// // GCJ-02תBD-09
	// public static LatLng bd_encrypt(AMapLocation ll) {
	// double x = ll.getLongitude(), y = ll.getLatitude();
	// double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
	// double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
	// return new LatLng(z * Math.sin(theta) + 0.006, z * Math.cos(theta)
	// + 0.0065);
	//
	// }
	//
	// public static void bd_encrypt(double gg_lat, double gg_lon, double
	// bd_lat,
	// double bd_lon) {
	// double x = gg_lon, y = gg_lat;
	// double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
	// double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
	// bd_lon = z * Math.cos(theta) + 0.0065;
	// bd_lat = z * Math.sin(theta) + 0.006;
	// }
	//
	// // BD-09תGCJ-02
	// public static LatLng bd_decrypt(LatLng ll) {
	// double x = ll.longitude - 0.0065, y = ll.latitude - 0.006;
	// double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
	// double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
	// return new LatLng(z * Math.sin(theta), z * Math.cos(theta));
	// }
	//
	// public static void bd_decrypt(double bd_lat, double bd_lon, double
	// gg_lat,
	// double gg_lon) {
	// double x = bd_lon - 0.0065, y = bd_lat - 0.006;
	// double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
	// double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
	// gg_lon = z * Math.cos(theta);
	// gg_lat = z * Math.sin(theta);
	// }

	public static int getHeight(View view)
	{
		int w = MeasureSpec.makeMeasureSpec(0,
				MeasureSpec.UNSPECIFIED);
		int h = MeasureSpec.makeMeasureSpec(0,
				MeasureSpec.UNSPECIFIED);
		view.measure(w, h);
		return view.getMeasuredHeight();
	}

	public static int getWidth(View view)
	{
		int w = MeasureSpec.makeMeasureSpec(0,
				MeasureSpec.UNSPECIFIED);
		int h = MeasureSpec.makeMeasureSpec(0,
				MeasureSpec.UNSPECIFIED);
		view.measure(w, h);
		return view.getMeasuredWidth();
	}

	public static void showMessage(Context context, String msg) {
		Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	public static Bitmap getPicFromBytes(byte[] bytes,
			BitmapFactory.Options opts) {
		if (bytes != null)
			if (opts != null)
				return BitmapFactory.decodeByteArray(bytes, 0, bytes.length,
						opts);
			else
				return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
		return null;
	}

	public static byte[] readStream(InputStream inStream) throws Exception {
		byte[] buffer = new byte[1024];
		int len = -1;
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();
		outStream.close();
		inStream.close();
		return data;

	}

	public static void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	public static RequestParams getRequestParams(Object object) {
		RequestParams params = new RequestParams();

		if (object != null) {
			// if (object!=null ) ----begin
			Class<?> clz = object.getClass();
			// Field[] fields = clz.getDeclaredFields();
			Field[] fields = clz.getFields();

			for (Field field : fields) {// --for() begin
				System.out.println(field.getGenericType());
				if (field.getGenericType().toString()
						.equals("class java.lang.String")) {
					Method m;
					try {
						m = (Method) object.getClass().getMethod(
								"get" + getMethodName(field.getName()));
						String val = (String) m.invoke(object);// 调用getter方法获取属
						if (val != null) {
							params.addQueryStringParameter(field.getName(), val);
						} else {
							params.addQueryStringParameter(field.getName(), "");
						}
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}// for() --end
		}// if (object!=null ) ----end
		return params;
	}

	private static String getMethodName(String fildeName) throws Exception {
		byte[] items = fildeName.getBytes();
		items[0] = (byte) ((char) items[0] - 'a' + 'A');
		return new String(items);
	}

	public static void closeInputMetherUI(Activity activity) {
		activity.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		View view = activity.getWindow().peekDecorView();
		if (view != null) {
			InputMethodManager inputmanger = (InputMethodManager) activity
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}

	public static Bitmap getHttpBitmap(String url) {
		URL myFileURL;
		Bitmap bitmap = null;
		try {
			myFileURL = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) myFileURL
					.openConnection();
			conn.setConnectTimeout(6000);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			// conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			is.close();
		}catch (Exception e) {
			e.printStackTrace();
		}

		return bitmap;

	}

	public static void BitmapMohu(Context context, Bitmap bkg, View view) {
		float scaleFactor = 8;
		float radius = 2;
		Bitmap overlay = Bitmap.createBitmap(35, 30, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(overlay);
		canvas.translate(-view.getLeft() / scaleFactor, -view.getTop()
				/ scaleFactor);
		canvas.scale(1 / scaleFactor, 1 / scaleFactor);
		Paint paint = new Paint();
		paint.setFlags(Paint.FILTER_BITMAP_FLAG);
		canvas.drawBitmap(bkg, 0, 0, paint);
		RenderScript rs = RenderScript.create(context);
		Allocation overlayAlloc = Allocation.createFromBitmap(rs, overlay);
		ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs,
				overlayAlloc.getElement());
		blur.setInput(overlayAlloc);
		blur.setRadius(radius);
		blur.forEach(overlayAlloc);
		overlayAlloc.copyTo(overlay);
		view.setBackground(new BitmapDrawable(context.getResources(), overlay));
		rs.destroy();
	}

	public static Date dateAdd(int days) {
		Calendar canlendar = Calendar.getInstance();
		canlendar.add(Calendar.DATE, days);
		return canlendar.getTime();
	}

	private Drawable loadImageFromNetwork(String imageUrl) {
		Drawable drawable = null;
		try {
			drawable = Drawable.createFromStream(
					new URL(imageUrl).openStream(), "image.jpg");
		} catch (IOException e) {
			Log.d("test", e.getMessage());
		}
		if (drawable == null) {
			Log.d("test", "null?drawable");
		} else {
			Log.d("test", "not?null?drawable");
		}
		return drawable;
	}





	public static synchronized int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	/*
         *
         * @param angle
         *
         * @param bitmap
         *
         * @return Bitmap
         */
	public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		System.out.println("angle2=" + angle);
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return resizedBitmap;
	}


	public static Bitmap imageFile2Bitmap(File bmFile) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		String path = bmFile.getAbsolutePath();
		BitmapFactory.decodeFile(path, opts);

		int inSampleSize = (int) Math
				.ceil(Math.sqrt(opts.outWidth * opts.outHeight / (Constants.imgWidth * Constants.imgHeight)));
		opts.inJustDecodeBounds = false;
		opts.inSampleSize = inSampleSize;
		Bitmap comBm = BitmapFactory.decodeFile(path, opts);
		int degree = readPictureDegree(bmFile.getAbsolutePath());
		Bitmap newbitmap = rotaingImageView(degree, comBm);
		Bitmap resizeBmp = ThumbnailUtils.extractThumbnail(newbitmap, comBm.getWidth(), comBm.getHeight());
		return resizeBmp;
		// return comBm;
	}

//	public static void toolBarManager(Context context,int res){
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//			Utils.setTranslucentStatus(true, context);
//		}
//		SystemBarTintManager tintManager = new SystemBarTintManager((Activity)context);
//
//		tintManager.setStatusBarTintEnabled(true);
//
//		// 使用颜色资源
//
//		tintManager.setStatusBarTintResource(res);
//	}
	@TargetApi(19)
	public static void setTranslucentStatus(boolean on,Context context) {
//		Window win = getWindow();
		Window win =((Activity)context).getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}

	public static boolean isToLong(String str) {
		String hanziEx = "[\\u4e00-\\u9fa5]";
		String zifuEx = "[a-zA-Z0-9]";

		int hanziCount = 0;
		int zifuCount = 0;
		String c;
		for (int i = 0; i < str.length(); i++) {
			c = String.valueOf(str.charAt(i));
			if (c.matches(hanziEx)) {
				hanziCount++;
			} else if (c.matches(zifuEx)) {
				zifuCount++;
			}
		}
		int length = hanziCount * 2 + zifuCount;
		if (hanziCount > 6)
			return true;
		else if (zifuCount > 12)
			return true;
		else if (length > 12)
			return true;

		return false;
	}

	// 特殊字符过滤
	public static boolean check(String str) {

		String regEx = "[\\u4e00-\\u9fa5a-zA-Z0-9]{1,12}";
		return str.matches(regEx);
	}
	/**
	 * 保存到sdcard
	 *
	 * @param b
	 * @return
	 */
	public static String savePartPic(Bitmap b,String path, String fileName) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss",
				Locale.US);
		File outfile = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath() + path + "/" + fileName);
		// 如果文件不存在，则创建一个新文件
		if (!outfile.exists()) {
			try {
				outfile.mkdirs();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String fname = null;
		// if(!TextUtils.isEmpty(fileName))
		// fname = outfile + "/" + fileName + ".jpg";
		// else
		if (TextUtils.equals("picHead", fileName))
			fname = outfile + "/" + fileName + ".jpg";
		else
			fname = outfile + "/" + sdf.format(new Date()) + ".jpg";
		File file = new File(fname);
		if (file.exists())
			file.delete();

		FileOutputStream fos = null;
		try {
			file.createNewFile();
			fos = new FileOutputStream(fname);
			if (null != fos) {
				b.compress(Bitmap.CompressFormat.PNG, 90, fos);
				fos.flush();
				fos.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fname;
	}
	public static String disposeAdress(String address){
		String[] names = address.split(":");
		String deviceName = "";
		for(int i=0;i<names.length;i++){
			deviceName+=names[i];
		}
		return deviceName;
	}
	public static String DateToStr(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = format.format(date);
		return str;
	}
	public static  List getmWeekList(String pSrc){
		List _WeekList = new ArrayList();
		String[] arr = pSrc.split("-");
		for (int i = 0; i < arr.length; i++) {
			if ("1".equals(arr[i])) {
				_WeekList.add(true);
			}else {
				_WeekList.add(false);
			}
		}
		return  _WeekList;
	}
	public static  String getmWeekListString(List<Boolean> pList){
		StringBuilder _WeekListString = new StringBuilder();
		for (int i = 0; i < pList.size(); i++) {
			boolean _check = pList.get(i);
			if (_WeekListString.length()>0){
				_WeekListString.append("-");
			}
			if (_check){
				_WeekListString.append(1);
			}else {
				_WeekListString.append(0);
			}
		}
		return  _WeekListString.toString();
	}
}
