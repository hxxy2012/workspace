package com.hike.digitalgymnastic;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;

import com.hike.digitalgymnastic.tools.AnimUtil;
import com.hike.digitalgymnastic.view.CustomerVideoView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.hiko.enterprisedigital.R;
/*
 * changqi
 */
@ContentView(R.layout.activity_guider)
public class GuiderActivity extends Activity {

	@ViewInject(R.id.viewPager)
	private ViewPager mViewPager;
	@ViewInject(R.id.btn_register)
	Button btn_register;
	@ViewInject(R.id.btn_login)
	Button btn_login;
	@ViewInject(R.id.page0)
	ImageView iv_page0;
	@ViewInject(R.id.page1)
	ImageView iv_page1;
	@ViewInject(R.id.page2)
	ImageView iv_page2;

	@ViewInject(R.id.ll_videoView_one)
	LinearLayout ll_videoView_one;
	@ViewInject(R.id.ll_videoView_two)
	LinearLayout ll_videoView_two;
	@ViewInject(R.id.ll_videoView_three)
	LinearLayout ll_videoView_three;

	@ViewInject(R.id.videoView_one)
	CustomerVideoView videoView_one;
	@ViewInject(R.id.videoView_two)
	CustomerVideoView videoView_two;
	@ViewInject(R.id.videoView_three)
	CustomerVideoView videoView_three;

	@OnClick(value = { R.id.btn_register, R.id.btn_login })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_register:
			Intent intent = new Intent(GuiderActivity.this,
					RegisterActivity.class);
			startActivity(intent);
			AnimUtil.intentPushUp(GuiderActivity.this);
			finish();
			break;
		case R.id.btn_login:
			intent = new Intent(GuiderActivity.this, LoginActivity.class);
			startActivity(intent);
			AnimUtil.intentPushUp(GuiderActivity.this);
			finish();
			break;
		default:
			break;
		}
	}

	private static final int ALL = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		closeAllActivity();
		initViews();
		mViewPager.setAdapter(new MyAdapter());
		mViewPager.setOnPageChangeListener(listener);
		mViewPager.setCurrentItem(0);
		mViewPager.setOffscreenPageLimit(2);// 预加载
		initMediaPlayer();

		checkSdkVersion();

	}

	HikoDigitalgyApplication application;

//	private void initService() {
//		// TODO Auto-generated method stub
//		if (android.os.Build.VERSION.SDK_INT >= 18) {
//			application = (HikoDigitalgyApplication) getApplication();
//			application.connectService();
//		}
//	}

	private void closeAllActivity() {
		try {
			Set<String> set = HikoDigitalgyApplication.map.keySet();
			String[] nameArray = new String[100];
			set.toArray(nameArray);

			for (String activityName : nameArray) {
				Activity ay = HikoDigitalgyApplication.map.get(activityName);
				if (ay != null
						&& !TextUtils
								.equals(activityName, getClass().getName())) {
					ay.finish();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	int screenWidth;
	int screenHeight;
	int one = 1;
	int two = 2;
	int three = 3;

	Uri uri_one;
	Uri uri_two;
	Uri uri_three;
	List<View> list = new ArrayList<View>();

	private void initMediaPlayer() {
		// TODO Auto-generated method stub

		screenWidth = getWindowManager().getDefaultDisplay().getWidth();
		screenHeight = getWindowManager().getDefaultDisplay().getHeight();

		// Create media controller，组件可以控制视频的播放，暂停，回复，seek等操作，不需要你实现
		MediaController mMediaControllerOne = new MediaController(this);
		videoView_one.setMediaController(mMediaControllerOne);
		MediaController mMediaControllerTwo = new MediaController(this);
		videoView_two.setMediaController(mMediaControllerTwo);
		MediaController mMediaControllerThree = new MediaController(this);
		videoView_three.setMediaController(mMediaControllerThree);
		initUrl();

	}

	private void checkSdkVersion() {
		if (android.os.Build.VERSION.SDK_INT < 18) {
			new AlertDialog.Builder(this).setMessage("当前系统版本过低，应用部分功能无法正常使用！")
					.setNegativeButton("确定", null).show();
		}
	}

	private void initUrl() {
		uri_one = Uri.parse("android.resource://" + getPackageName() + "/"
				+ R.raw.one);
		uri_two = Uri.parse("android.resource://" + getPackageName() + "/"
				+ R.raw.two);
		uri_three = Uri.parse("android.resource://" + getPackageName() + "/"
				+ R.raw.three);

		videoView_one.setVideoURI(uri_one);
		videoView_two.setVideoURI(uri_two);
		videoView_three.setVideoURI(uri_three);

	}

	public void onStart() {
		// Play Video
		super.onStart();
		// play(mViewPager.getCurrentItem());
		play(0);
		play(1);
		play(2);
	}

	private void initViews() {
		iv_page0.setImageDrawable(getResources().getDrawable(
				R.mipmap.page_indicator_focused));
	}

	class MyAdapter extends PagerAdapter {
		@Override
		public int getCount() {
			return mViewPager.getChildCount();
		}

		@Override
		public boolean isViewFromObject(View view, Object o) {
			return view == o;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View v = container.getChildAt(position);
			return v;
		}
	}

	void play(int order) {
		switch (order) {
		case 0:
			videoView_one.openVideo();
			break;
		case 1:
			videoView_two.openVideo();
			break;
		case 2:
			videoView_three.openVideo();
			break;
		default:
			break;
		}
	}

	ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
		@Override
		public void onPageScrolled(int i, float v, int i2) {
			switch (i) {
			case 0:
				iv_page0.setImageDrawable(getResources().getDrawable(
						R.mipmap.page_indicator_focused));
				iv_page1.setImageDrawable(getResources().getDrawable(
						R.mipmap.page_indicator_unfocused));

				break;
			case 1:
				iv_page0.setImageDrawable(getResources().getDrawable(
						R.mipmap.page_indicator_unfocused));
				iv_page1.setImageDrawable(getResources().getDrawable(
						R.mipmap.page_indicator_focused));
				iv_page2.setImageDrawable(getResources().getDrawable(
						R.mipmap.page_indicator_unfocused));
				break;
			case 2:
				iv_page1.setImageDrawable(getResources().getDrawable(
						R.mipmap.page_indicator_unfocused));
				iv_page2.setImageDrawable(getResources().getDrawable(
						R.mipmap.page_indicator_focused));
				break;
			default:
				break;
			}
		}

		@Override
		public void onPageSelected(int i) {
			// play(i);
			switch (i) {
			case 0:
				videoView_one.seekTo(0);
				videoView_one.play();
				break;
			case 1:
				videoView_two.seekTo(0);
				videoView_two.play();
				break;
			case 2:
				videoView_three.seekTo(0);
				videoView_three.play();
				break;
			default:
				break;
			}

		}

		@Override
		public void onPageScrollStateChanged(int i) {
		}
	};

	@Override
	public void finish() {
		try {
			if (videoView_one != null) {
				videoView_one.release(true);
			}
			if (videoView_two != null) {
				videoView_two.release(true);
			}
			if (videoView_three != null) {
				videoView_three.release(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.gc();
		super.finish();
	};

}
