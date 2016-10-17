package com.hiko.enterprisedigital;

import java.io.File;
import java.io.FileNotFoundException;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hike.digitalgymnastic.HikoDigitalgyApplication;
import com.hike.digitalgymnastic.http.HttpConnectUtils;
import com.hike.digitalgymnastic.http.INetResult;
import com.hike.digitalgymnastic.request.BaseDao;
import com.hike.digitalgymnastic.utils.NetworkUtil;
import com.hike.digitalgymnastic.utils.Utils;
import com.hike.digitalgymnastic.view.ProgressHUD;
import com.hiko.enterprisedigital.wxapi.WXEntryActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

/*
 * 社会分享页
 * changqi
 */
@ContentView(R.layout.activity_socialshare)
// public class SocialShareActivity extends BaseActivity {
public class SocialShareActivity extends WXEntryActivity {
	@ViewInject(R.id.btn_share_weixin_friend)
	ImageView btn_share_weixin_friend;
	@ViewInject(R.id.btn_share_weixin_space)
	ImageView btn_share_weixin_space;
	@ViewInject(R.id.btn_share_wlweibo)
	ImageView btn_share_wlweibo;
	@ViewInject(R.id.btn_share_qq_friend)
	ImageView btn_share_qq_friend;
	@ViewInject(R.id.btn_share_qq_space)
	ImageView btn_share_qq_space;
	@ViewInject(R.id.btn_share_local)
	ImageView btn_share_local;

	@ViewInject(R.id.root)
	RelativeLayout root;

	@OnClick(value = { R.id.btn_share_weixin_friend,
			R.id.btn_share_weixin_space, R.id.btn_share_wlweibo,
			R.id.btn_share_qq_friend, R.id.btn_share_qq_space,
			R.id.btn_share_local, R.id.root })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.root:

			finish();
			break;
		case R.id.btn_share_weixin_friend:
			if (NetworkUtil.isNetwork(SocialShareActivity.this))
				share_weixin_friend();
			else
				Utils.showMessage(SocialShareActivity.this, "网络不给力哦");
			break;
		case R.id.btn_share_weixin_space:
			if (NetworkUtil.isNetwork(SocialShareActivity.this))
				share_weixin_circle();
			else
				Utils.showMessage(SocialShareActivity.this, "网络不给力哦");

			break;
		case R.id.btn_share_wlweibo:

			if (NetworkUtil.isNetwork(SocialShareActivity.this))
				share_sina();
			else
				Utils.showMessage(SocialShareActivity.this, "网络不给力哦");
			break;
		case R.id.btn_share_qq_friend:
			if (NetworkUtil.isNetwork(SocialShareActivity.this))
				share_qq_friend();
			else
				Utils.showMessage(SocialShareActivity.this, "网络不给力哦");

			break;
		case R.id.btn_share_qq_space:

			if (NetworkUtil.isNetwork(SocialShareActivity.this))
//				share_qq_zone();
				uploadImage();
			else
				Utils.showMessage(SocialShareActivity.this, "网络不给力哦");

			break;
		case R.id.btn_share_local:
			share_local();
			break;
		default:
			break;
		}
	}

	HikoDigitalgyApplication application;
	String filePath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		ViewUtils.inject(this);
		init();
		initShareSetting();
	}

	UMSocialService mController;
	String title;
	private void initShareSetting() {

		// TODO Auto-generated method stub
		String appID = "wx967daebe835fbeac";
		String appSecret = "5fa9e68ca3970e87a1f83e563c8dcbce";
		// 添加微信平台
		UMWXHandler wxHandler = new UMWXHandler(this,
				getString(R.string.wx_appid), appSecret);
		wxHandler.addToSocialSDK();
		// 支持微信朋友圈
		UMWXHandler wxCircleHandler = new UMWXHandler(this,
				getString(R.string.wx_appid), appSecret);
		wxCircleHandler.setToCircle(true);
		wxCircleHandler.addToSocialSDK();

		// qq好友：参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this, "1104196320",
				"fNqdv6qeiztm9fZO");
		qqSsoHandler.addToSocialSDK();

		// qq空间分享参数1为当前Activity， 参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(this,
				"1104196320", "fNqdv6qeiztm9fZO");
		qZoneSsoHandler.addToSocialSDK();

		// 首先在您的Activity中添加如下成员变量
		mController = UMServiceFactory.getUMSocialService("com.umeng.share");

		// 设置新浪SSO handler
		mController.getConfig().setSsoHandler(new SinaSsoHandler());
		mController.getConfig().closeToast();

	}

	private void init() {
		title = getString(R.string.app_name);
		application = (HikoDigitalgyApplication) getApplication();
		filePath = getIntent().getStringExtra("filePath");

	}

	public void finish() {
		super.finish();

	};

	// 微信好友
	private void share_weixin_friend() {

		// 设置微信好友分享纯文本内容
		// WeiXinShareContent weixinContent = new WeiXinShareContent();
		// //设置分享文字
		// weixinContent.setShareContent("来自友盟社会化组件（SDK）让移动应用快速整合社交分享功能，微信");
		// //设置title
		// weixinContent.setTitle("友盟社会化分享组件-微信");
		// //设置分享内容跳转URL
		// weixinContent.setTargetUrl("你的URL链接");
		// mController.setShareMedia(weixinContent);

		// 设置微信好友分享纯图片内容
		WeiXinShareContent weixinContent = new WeiXinShareContent();
		// 设置title
		weixinContent.setTitle("title");
		// 设置分享内容跳转URL
		weixinContent.setTargetUrl("");
		// 设置分享图片
		weixinContent.setShareImage(new UMImage(this, filePath));
		mController.setShareMedia(weixinContent);

		// 参数1为Context类型对象， 参数2为要分享到的目标平台， 参数3为分享操作的回调接口
		mController.postShare(this, SHARE_MEDIA.WEIXIN, new SnsPostListener() {
			@Override
			public void onStart() {
				// Toast.makeText(SocialShareActivity.this, "开始分享.",
				// Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onComplete(SHARE_MEDIA platform, int eCode,
					SocializeEntity entity) {
				// TODO Auto-generated method stub
				if (eCode == 200) {
					Toast.makeText(SocialShareActivity.this, "分享成功.",
							Toast.LENGTH_SHORT).show();

				} else {
					String eMsg = "";
					if (eCode == -101) {
						eMsg = "没有授权";
					} else if (eCode == 40000) {

					} else {
						Toast.makeText(SocialShareActivity.this,
								"分享失败[" + eCode + "] " + eMsg,
								Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
	}

	// 微信朋友圈
	private void share_weixin_circle() {
		// 设置微信朋友圈分享纯文本内容
		// CircleShareContent circleMedia = new CircleShareContent();
		// circleMedia.setShareContent("来自友盟社会化组件（SDK）让移动应用快速整合社交分享功能，朋友圈");
		// //设置朋友圈title
		// circleMedia.setTitle("友盟社会化分享组件-朋友圈");
		// circleMedia.setTargetUrl("你的URL链接");
		// mController.setShareMedia(circleMedia);

		// 设置微信朋友圈分享纯图片内容
		CircleShareContent circleMedia = new CircleShareContent();
		circleMedia.setShareImage(new UMImage(this, filePath));
		circleMedia.setTargetUrl("");
		mController.setShareMedia(circleMedia);

		// 参数1为Context类型对象， 参数2为要分享到的目标平台， 参数3为分享操作的回调接口
		mController.postShare(this, SHARE_MEDIA.WEIXIN_CIRCLE,
				new SnsPostListener() {
					@Override
					public void onStart() {
						// Toast.makeText(SocialShareActivity.this, "开始分享.",
						// Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onComplete(SHARE_MEDIA platform, int eCode,
							SocializeEntity entity) {
						// TODO Auto-generated method stub
						if (eCode == 200) {
							Toast.makeText(SocialShareActivity.this, "分享成功.",
									Toast.LENGTH_SHORT).show();
						} else {
							String eMsg = "";
							if (eCode == -101) {
								eMsg = "没有授权";
							} else if (eCode == 40000) {

							} else {
								Toast.makeText(SocialShareActivity.this,
										"分享失败[" + eCode + "] " + eMsg,
										Toast.LENGTH_SHORT).show();
							}
						}
					}
				});
	}

	// qq好友
	private void share_qq_friend() {
		QQShareContent qqShareContent = new QQShareContent();
		// 设置分享文字
		// qqShareContent.setShareContent(title);
		// 设置分享title
		// qqShareContent.setTitle(title);
		// 设置分享图片
		qqShareContent.setShareImage(new UMImage(this, filePath));
		// 设置点击分享内容的跳转链接
		// qqShareContent.setTargetUrl("http://baidu.com");
		mController.setShareMedia(qqShareContent);

		// 参数1为Context类型对象， 参数2为要分享到的目标平台， 参数3为分享操作的回调接口
		mController.postShare(this, SHARE_MEDIA.QQ, new SnsPostListener() {
			@Override
			public void onStart() {
				// Toast.makeText(SocialShareActivity.this, "开始分享.",
				// Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onComplete(SHARE_MEDIA platform, int eCode,
					SocializeEntity entity) {
				// TODO Auto-generated method stub
				if (eCode == 200) {
					Toast.makeText(SocialShareActivity.this, "分享成功.",
							Toast.LENGTH_SHORT).show();

				} else {
					String eMsg = "";
					if (eCode == -101) {
						eMsg = "没有授权";
					} else if (eCode == 40000) {

					} else if (eCode == 40002) {
						share_qq_friend();
					}
				}
			}
		});

	}

	// qqzone分享,不支持纯图片分享
	private void share_qq_zone(String url) {
		QZoneShareContent qzone = new QZoneShareContent();
		// 设置分享文字
		qzone.setShareContent(title);

		// 设置分享内容的标题
		qzone.setTitle(title);
		// 设置分享图片
		qzone.setShareImage(new UMImage(this, filePath));
		// 设置点击消息的跳转URL
		qzone.setTargetUrl(url);
		mController.setShareMedia(qzone);

		// 参数1为Context类型对象， 参数2为要分享到的目标平台， 参数3为分享操作的回调接口
		mController.postShare(this, SHARE_MEDIA.QZONE, new SnsPostListener() {
			@Override
			public void onStart() {
				// Toast.makeText(SocialShareActivity.this, "开始分享.",
				// Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onComplete(SHARE_MEDIA platform, int eCode,
					SocializeEntity entity) {
				// TODO Auto-generated method stub
				if (eCode == 200) {
					Toast.makeText(SocialShareActivity.this, "分享成功.",
							Toast.LENGTH_SHORT).show();

				} else {
					String eMsg = "";
					if (eCode == -101) {
						eMsg = "没有授权";
					} else if (eCode == 40000) {

					} else {
						Toast.makeText(SocialShareActivity.this,
								"分享失败[" + eCode + "] " + eMsg,
								Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
	}

	// 新浪微博分享
	private void share_sina() {
		// 设置分享内容
		mController.setShareContent(getString(R.string.app_name));
		// 设置分享图片, 参数2为图片的url地址
		mController.setShareMedia(new UMImage(this, filePath));
		// 参数1为Context类型对象， 参数2为要分享到的目标平台， 参数3为分享操作的回调接口
		mController.postShare(this, SHARE_MEDIA.SINA, new SnsPostListener() {
			@Override
			public void onStart() {
			}

			@Override
			public void onComplete(SHARE_MEDIA platform, int eCode,
					SocializeEntity entity) {
				// TODO Auto-generated method stub
				if (eCode == 200) {
					Toast.makeText(SocialShareActivity.this, "分享成功.",
							Toast.LENGTH_SHORT).show();

				} else {
					String eMsg = "";
					if (eCode == -101) {
						eMsg = "没有授权";
					} else if (eCode == 40000) {

					} else {
						Toast.makeText(SocialShareActivity.this,
								"分享失败[" + eCode + "] " + eMsg,
								Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:

				File file = (File) msg.obj;
				// 这个广播的目的就是更新图库，发了这个广播进入相册就可以找到你保存的图片了！，记得要传你更新的file哦
				Intent intent = new Intent(
						Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
				Uri uri = Uri.fromFile(file);
				intent.setData(uri);
				sendBroadcast(intent);
				Utils.showMessage(getApplicationContext(), "保存成功！");
				showProgress(false);
				break;
			case -1:
				Utils.showMessage(getApplicationContext(), "保存失败！");
				break;
			default:
				break;
			}

		};
	};

	private void share_local() {
		showProgress(true);
		new Thread(new Runnable() {
			@Override
			public void run() {
				Message msg = new Message();
				try {
					File file = new File(filePath);
					MediaStore.Images.Media.insertImage(getContentResolver(),
							filePath, file.getName(), null);
					msg.what = 0;
					msg.obj = file;
					handler.sendMessage(msg);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					msg.what = -1;
					handler.sendMessage(msg);
				}
			}
		}).start();

	}

	ProgressHUD mProgressHUD;
	BaseDao dao = new BaseDao(new INetResult() {
		@Override
		public void onResponseReceived(int requestCode) {
			// TODO Auto-generated method stub
			showProgress(false);
		}

		@Override
		public void onRequestSuccess(ResponseInfo responseInfo) {
			// TODO Auto-generated method stub
			showProgress(false);
		}

		@Override
		public void onRequestSuccess(int requestCode) {
			// TODO Auto-generated method stub
			showProgress(false);
			share_qq_zone(HttpConnectUtils.image_ip+dao.getImageurl().getImageUrl());
		}

		@Override
		public void onRequestFaild(String errorNo, String errorMessage) {
			showProgress(false);
			if (errorNo.equals("0"))
				errorMessage = "网络不给力哦";
			Utils.showMessage(getApplicationContext(), errorMessage);
		}

		@Override
		public void onNoConnect() {
			showProgress(false);
			Utils.showMessage(getApplicationContext(),
					getString(R.string.no_connect_str));
		}

	}, this);

	// 上传图片
	private void uploadImage() {
		showProgress(true);
		new Thread(new Runnable() {
			@Override
			public void run() {
				dao.UploadImage(filePath);
			}
		}).start();

	}

	public void showProgress(boolean show) {
		showProgressWithText(show, "加载中...");
	}

	public void showProgressWithText(boolean show, String message) {
		if (show) {
			mProgressHUD = ProgressHUD.show(this, message, true, true, null);
		} else {
			if (mProgressHUD != null) {
				mProgressHUD.dismiss();
			}
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		/**使用SSO授权必须添加如下代码 */
	    UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode) ;
	    if(ssoHandler != null){
	       ssoHandler.authorizeCallBack(requestCode, resultCode, data);
	    }
	}
}
