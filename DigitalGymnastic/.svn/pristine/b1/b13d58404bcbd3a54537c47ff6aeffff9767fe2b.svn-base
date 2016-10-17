package com.hike.digitalgymnastic.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.hike.digitalgymnastic.http.HttpConnectUtils;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

/**
 * http网络工具类
 * 
 * @author yuhj
 * @date 2011-4-9
 * 
 */
public class HttpUtil {

	private static BitmapUtils bitmapUtils;
	private static FileOutputStream partfos;
	private static File partfile;

	public static byte[] doPost(String reqAddress, String content)
			throws Exception {
		HttpURLConnection conn = null;
		ByteArrayOutputStream baos = null;
		byte[] data = null;
		try {
			URL url = new URL(reqAddress);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(10000);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "binary/octet-stream");
			OutputStream outputStream = conn.getOutputStream();
			outputStream.write(content.getBytes("utf-8"));
			outputStream.flush();
			outputStream.close();

			InputStream gins = (InputStream) conn.getInputStream();
			baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[4096];
			int len = -1;
			while ((len = gins.read(buffer)) != -1) {
				baos.write(buffer, 0, len);
			}
			data = baos.toByteArray();
		} catch (Exception e) {
			try {
				if (conn != null) {
					conn.disconnect();
				}

				if (baos != null) {
					baos.close();
				}
			} catch (Exception e1) {
				conn = null;
				baos = null;
				e.printStackTrace();
			}
		} finally {
			try {
				if (conn != null) {
					conn.disconnect();
				}

				if (baos != null) {
					baos.close();
				}
			} catch (Exception e) {
				conn = null;

				baos = null;
				e.printStackTrace();
			}
		}
		return data;
	}

	public static byte[] http(String url,String phone) {
		
		
		
		String postdata="phone="+phone+"&osType=2&osVersion=2.0&appVersion=2.0&deviceModel=090&deviceId=9090";
		URL u = null;
		HttpURLConnection con = null;
		InputStream inputStream = null;
		ByteArrayOutputStream baos=null;
		byte[] data = null;
		// 尝试发送请求
		try {
			u = new URL(url);
			con = (HttpURLConnection) u.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setUseCaches(false);
			con.setRequestProperty("Content-Type", "binary/octet-stream");
			OutputStream outStream = con.getOutputStream();
			outStream.write(postdata.getBytes());
			outStream.flush();
			outStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		// 读取返回内容
		try {
			// 读取返回内容
			inputStream = con.getInputStream();
			baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[4096];
			int len = -1;
			while ((len = inputStream.read(buffer)) != -1) {
				baos.write(buffer, 0, len);
			}
			data = baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (con != null) {
				con.disconnect();
			}
			if(baos!=null)
				try {
					baos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return data;
	}
		
	public static byte[] getImage(Context context,String baseUrl,String phone){
		String postdata="phone="+phone+"&osType=2&osVersion=2.0&appVersion=2.0&deviceModel=090&deviceId=9090";
		List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();  
		//和GET方式一样，先将参数放入List
		params = new LinkedList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("phone", phone));
		Utiles.Add(context, params);
		byte[] data=null;
		try {
			//将URL与参数拼接  
			              
			HttpClient httpClient = new DefaultHttpClient();  
		    HttpPost postMethod = new HttpPost(baseUrl);
		    postMethod.setEntity(new UrlEncodedFormEntity(params, "utf-8")); //将参数填入POST Entity中
						
		    HttpResponse response = httpClient.execute(postMethod); //执行POST方法
		    if(response.getStatusLine().getStatusCode()==200){
		    	
		    	// 读取返回内容
				try {
					// 读取返回内容
					InputStream	inputStream =response.getEntity().getContent();
					ByteArrayOutputStream	baos = new ByteArrayOutputStream();
					byte[] buffer = new byte[4096];
					int len = -1;
					while ((len = inputStream.read(buffer)) != -1) {
						baos.write(buffer, 0, len);
					}
					data = baos.toByteArray();
				} catch (Exception e) {
					e.printStackTrace();
				}
		    }
		    
						
		} catch (UnsupportedEncodingException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		} catch (ClientProtocolException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		return data;
	}
	public static Bitmap getSportGifImage(Context context,String baseUrl,int index,String sdimppath,int[] sings){
		Bitmap bitmap=null;

		try {
			//将URL与参数拼接

			HttpClient httpClient = new DefaultHttpClient();
			HttpGet postMethod = new HttpGet(baseUrl);
//			postMethod.setEntity(new UrlEncodedFormEntity(params, "utf-8")); //将参数填入POST Entity中

			HttpResponse response = httpClient.execute(postMethod); //执行POST方法
			if(response.getStatusLine().getStatusCode()==200){

				// 读取返回内容
				try {
					// 读取返回内容
					InputStream	inputStream =response.getEntity().getContent();
					bitmap = BitmapFactory.decodeStream(inputStream);
					File file =new File(sdimppath);
					file.createNewFile();
					FileOutputStream fos=new FileOutputStream(file);

					bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
//					byte[] buffer = new byte[4096];
//					int len = -1;
//					while ((len = inputStream.read(buffer)) != -1) {
//						fos.write(buffer, 0, len);
//					}
//					fos.flush();
					sings[index]=1;
//					data = baos.toByteArray();
				} catch (Exception e) {
					e.printStackTrace();

					deleteFiles(sdimppath);
				}
			}


		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bitmap;
	}

	private static void deleteFiles(String sdimppath) {
		File file = new File(sdimppath);
		File parent = file.getParentFile();

		File[] listfiles = parent.listFiles();
		if(listfiles!=null){
			for (int i=0;i<listfiles.length;i++){
				parent.listFiles()[0].delete();
			}
		}

//		while(parent.listFiles().length==0){
//			parent.listFiles()[0].delete();
//		}
	}

	/**
	 * 初始化图片加载器
	 */
	public static void initBitmapUtils(Context context,String imageCachePath) {
		bitmapUtils = new BitmapUtils(context,imageCachePath);
		try {
		partfile =new File(imageCachePath);
			partfile.createNewFile();

			partfos=new FileOutputStream(partfile);

		} catch (Exception e) {
			e.printStackTrace();
		}
		bitmapUtils.configDefaultShowOriginal(false);// 显示原始图片,不压缩,
		// 尽量不要使用,图片太大时容易OOM。
		bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
		bitmapUtils.configDefaultBitmapMaxSize(BitmapCommonUtils
				.getScreenSize(context));
		bitmapUtils.configThreadPoolSize(5);

	}
	public static void displayIcon(final ImageView ivwb,String uri) {
		String url = HttpConnectUtils.image_ip + uri;

		bitmapUtils.display(ivwb, url,
				new BitmapLoadCallBack<View>() {

					@Override
					public void onLoadCompleted(View container, String uri,
												Bitmap bitmap, BitmapDisplayConfig config,
												BitmapLoadFrom from) {
						ivwb.setImageBitmap(bitmap);
						bitmap.compress(Bitmap.CompressFormat.PNG, 100, partfos);

					}

					@Override
					public void onLoadFailed(View container, String uri,
											 Drawable drawable) {
						partfile.delete();
					}
				});

	}
}
