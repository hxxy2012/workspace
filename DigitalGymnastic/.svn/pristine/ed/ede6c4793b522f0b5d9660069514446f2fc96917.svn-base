package com.hike.digitalgymnastic.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.google.gson.Gson;
import com.hike.digitalgymnastic.entitiy.Customer;
import com.hike.digitalgymnastic.request.BaseRequest;
import com.hike.digitalgymnastic.response.BaseResponse;
import com.hike.digitalgymnastic.response.GetVerifyCodeResponse;
import com.hike.digitalgymnastic.response.LoginResponse;
import com.hike.digitalgymnastic.response.RegisterResponse;
import com.hike.digitalgymnastic.response.UploadPictureResponse;
import com.lidroid.xutils.http.RequestParams;

public class JSONUtils {

	public static void parseJson() {

	}
	
	public static void toParams(Map  map,RequestParams params){
		 StringBuilder postContent = new StringBuilder();
         String myEncoding = HTTP.UTF_8;
         Set<Entry<String, String>> entries = map.entrySet();
 		boolean hasParam = false;

 		for (Entry<String, String> entry : entries) {
 			String name = entry.getKey();
 			String value = entry.getValue();
 			 try {
 				params.addQueryStringParameter(URLEncoder.encode(name, myEncoding)
 						,URLEncoder.encode(value, myEncoding));
 			
 			} catch (UnsupportedEncodingException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
 		}
	}
	/**
	 * 
	 * 
	 * @param param
	 *          map转
	 * @return 其形式如?username="aaa"&password="bbb"
	 * 调用buildQuery(param, "utf-8")实现
	 * */
	public static String ParserParams(Map<String, String> param) {
		String re = "";
		try {
			re += buildQuery(param, "utf-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.i("return", re);
		return re;
	}

	/**
	 *   map转?username="aaa"&password="bbb"
	 * 
	 * */
	private static String buildQuery(Map<String, String> params, String charset)
			throws IOException {
		if (params == null || params.isEmpty()) {
			return null;
		}
		StringBuilder query = new StringBuilder();
		Set<Entry<String, String>> entries = params.entrySet();
		boolean hasParam = false;

		for (Entry<String, String> entry : entries) {
			String name = entry.getKey();
			String value = entry.getValue();
			if (hasParam) {
				query.append("&");
			} else {
				hasParam = true;
			}

			if(value != null)
				query.append(name).append("=")
					.append(URLEncoder.encode(value, charset));
		}
		return query.toString();
	}
	/**
	 * 将实体类转换成json字符串
	 * 
	 * @param <T>
	 * @param o
	 *            实体类型
	 * @return
	 */
	public static String getJsonStringByEntity(Object o) {
		String strJson = "";
		Gson gson = new Gson();
		strJson = gson.toJson(o);
		
		return strJson;
	}
	/**
	 * 将json字符串转换成实体类
	 * 
	 * @param <T>
	 * @param o
	 *            实体类型
	 * @return
	 */
//	public static Object getBean(String jo,Class mClass) {
//		Gson gson = new Gson();
//		
//		return ;
//	}
	public static <T> T getBean(String jsonString, Class<T> cls) {
        T t = null;
        try {
            Gson gson = new Gson();
            t = gson.fromJson(jsonString, cls);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return t;
    }
//	public static <T> List<T> getPersons(String jsonString, Class<T> cls) {
//        List<T> list = new ArrayList<T>();
//        try {
//            Gson gson = new Gson();
//            list = gson.fromJson(jsonString, new TypeToken<List<cls>>() {
//            }.getType());
//        } catch (Exception e) {
//        }
//        return list;
//    }
	/**
	 * 将实体类的List转换成json字符串
	 * 
	 * @param <T>
	 * @param list
	 *            需要转换的实体类List
	 * @return
	 * @throws JSONException
	 */
	public static <E> String getJsonStringByList(List<E> list) {
		StringBuilder strJson = new StringBuilder("[");
		Gson gson = new Gson();
		for (int i = 0; i < list.size(); i++) {
			if (i != list.size() - 1) {
				strJson.append(gson.toJson(list.get(i)) + ",");
			} else {
				strJson.append(gson.toJson(list.get(i)));
			}
		}
		strJson = strJson.append("]");
		return strJson.toString();
	}

	public static BaseResponse parseJson(String result, BaseRequest<BaseResponse> req) {
		// TODO Auto-generated method stub
		Class object = req.getResponseClass();
		BaseResponse res = null;
		JSONObject json;
		try {
			json = new JSONObject(result);
			if (object.equals(GetVerifyCodeResponse.class)) {
				 res = new GetVerifyCodeResponse();
				 
				try {
					res.code = json.getInt("code");
					
					if (res.code == Constants.isSuccess) {
						res.data = json.getString("data");
						JSONObject jo=new JSONObject(res.data);
						((GetVerifyCodeResponse)res).setVerifyCode(jo.getString("verifyCode"));
					}else{
						res.message = json.getString("message");
					}
				} catch (JSONException e) {
					e.printStackTrace();
					res.code=Constants.isError;
					res.message="json exception";
				}

				return res;
			}
			if (object.equals(RegisterResponse.class)) {
				 res = new RegisterResponse();
				try {
					res.code = json.getInt("code");
					
					if (res.code == Constants.isSuccess) {
						res.data = json.getString("data");
						JSONObject jo=new JSONObject(res.data);
						String s=jo.getJSONObject("customer").toString();
						Customer customer=(Customer)getBean(s,Customer.class);
						((RegisterResponse)res).setCustomer(customer);
					}else{
						res.message = json.getString("message");
					}
				} catch (JSONException e) {
					e.printStackTrace();
					res.code=Constants.isError;
					res.message="json exception";
				}

				return res;
			}
			
			if (object.equals(LoginResponse.class)) {
				 res = new LoginResponse();
				try {
					res.code = json.getInt("code");
					
					if (res.code == Constants.isSuccess) {
						res.data = json.getString("data");
						JSONObject jo=new JSONObject(res.data);
						String s=jo.getJSONObject("customer").toString();
						Customer customer=(Customer)getBean(s,Customer.class);
						((LoginResponse)res).setCustomer(customer);
					}else{
						res.message = json.getString("message");
					}
				} catch (JSONException e) {
					e.printStackTrace();
					res.code=Constants.isError;
					res.message="json exception";
				}

				return res;
			}
			
			if (object.equals(UploadPictureResponse.class)) {
				 res = new UploadPictureResponse();
				try {
					res.code = json.getInt("code");
					
					if (res.code == Constants.isSuccess) {
						res.data = json.getString("data");
						res.message = "上传成功！";
						JSONObject jo=new JSONObject(res.data);
						((UploadPictureResponse)res).imageUrl=jo.getString("imageUrl");
						
					}else{
						res.message = json.getString("message");
					}
				} catch (JSONException e) {
					e.printStackTrace();
					res.code=Constants.isError;
					res.message="json exception";
				}

				return res;
			}
			
		} catch (JSONException e1) {

			e1.printStackTrace();
			res.code=Constants.isError;
			res.message="json exception";
		}
		return res;
	}

}
