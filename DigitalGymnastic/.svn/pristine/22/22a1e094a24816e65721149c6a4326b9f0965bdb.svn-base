package com.hike.digitalgymnastic.http;


import com.lidroid.xutils.http.ResponseInfo;

/**
 * Activity 访问网络接口 MVC模式
 *
 * @author beanu
 */
public interface INetResult {

    /**
     * 访问网络成功后更新UI
     *
     * @param requestCode 网络请求顺序号，第一个请求，NetRequestOrderNum=0,处理第一条请求的结果。如果等于1,
     *                    表示处理此页面的第二条请求
     */
    public void onRequestSuccess(int requestCode);
    public void onResponseReceived(int requestCode);
    public void onRequestFaild(String errorNo, String errorMessage);
    public void onRequestSuccess(ResponseInfo responseInfo);
    public void onNoConnect();
}
