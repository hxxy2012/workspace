package com.hike.digitalgymnastic.view;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.MediaController;
import android.widget.MediaController.MediaPlayerControl;

public class CustomerVideoView extends SurfaceView implements
		MediaPlayerControl {
	private static String TAG = "MyLog";
	private boolean pause;
	private boolean seekBackward;
	private boolean seekForward;
	private Uri videoUri;
	private MediaPlayer mediaPlayer;
	private Context context;
	private OnPreparedListener onPreparedListener;
	private int videoWidth;
	private int videoHeight;
	private MediaController mediaController;
	protected SurfaceHolder surfaceHolder;
	private SurfaceHolder.Callback surfaceHolderCallback = new SurfaceHolder.Callback() {
		public void surfaceChanged(SurfaceHolder holder, int format, int w,
				int h) {
		}

		public void surfaceCreated(SurfaceHolder holder) {
			surfaceHolder = holder;
			if (mediaPlayer == null) {
				mediaPlayer=new MediaPlayer();
			}
			mediaPlayer.setDisplay(surfaceHolder);
//			if (mediaPlayer != null) {
//				mediaPlayer.setDisplay(surfaceHolder);
//				resume();
//			} else {
//				openVideo();
//			}
		}

		public void surfaceDestroyed(SurfaceHolder holder) {
			surfaceHolder = null;
			if (mediaController != null) {
				mediaController.hide();
			}
			release(true);
		}
	};

	public  void release(boolean cleartargetstate) {
		if (mediaPlayer != null) {
			mediaPlayer.reset();
			mediaPlayer.release();
			mediaPlayer = null;
		}
	}

	public void resume() {
		if (surfaceHolder == null) {
			return;
		}
		if (mediaPlayer != null) {
			return;
		}
		openVideo();
	}

	public CustomerVideoView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		this.initVideoView();
	}

	public CustomerVideoView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		this.initVideoView();
	}

	public CustomerVideoView(Context context) {
		super(context);
		this.context = context;
		this.initVideoView();
	}

	@Override
	public boolean canPause() {
		return this.pause;
	}

	@Override
	public boolean canSeekBackward() {
		return this.seekBackward;
	}

	@Override
	public boolean canSeekForward() {
		return this.seekForward;
	}

	@Override
	public int getBufferPercentage() {
		return 0;
	}

	@Override
	public int getCurrentPosition() {
		return mediaPlayer != null ? mediaPlayer.getCurrentPosition() : 0;
	}

	@Override
	public int getDuration() {
		return mediaPlayer != null ? mediaPlayer.getDuration() : 0;
	}

	@Override
	public boolean isPlaying() {
		return false;
	}

	@Override
	public void pause() {
		if(mediaPlayer!=null)
			mediaPlayer.pause();
	}
	
	
	@Override
	public void seekTo(int mSec) {
		if(mediaPlayer!=null)
			mediaPlayer.seekTo(mSec);
	}

	@Override
	public void start() {
	}

	public void setVideoURI(Uri uri) {
		this.videoUri = uri;
//		try {
//			AssetManager  asset = context.getAssets();
//			   afd = asset.openFd(fileName);
//		}catch(Exception e){
//			e.printStackTrace();
//		}
	}
	AssetFileDescriptor afd;
	AssetManager  asset;
	String fileName="video/one.mp4";
	public void openVideo() {
		if(this.videoUri!=null){
			if(mediaPlayer==null)
				this.mediaPlayer = new MediaPlayer();
			mediaPlayer.reset();
			try {
				this.mediaPlayer.setDataSource(this.context, this.videoUri);
//				this.mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			
			this.mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			this.mediaPlayer.setOnPreparedListener(onPreparedListener);
			this.mediaPlayer.prepareAsync();
			attachMediaController();
			requestLayout();
			invalidate();
		}
	}

	private void attachMediaController() {
		if (mediaPlayer != null && mediaController != null) {
			mediaController.setMediaPlayer(this);
			View anchorView = this.getParent() instanceof View ? (View) this
					.getParent() : this;
			mediaController.setAnchorView(anchorView);
			mediaController.setEnabled(true);
		}
	}

	public void setMediaController(MediaController controller) {
		if (mediaController != null) {
			mediaController.hide();
		}
		mediaController = controller;
		attachMediaController();
	}

	public void setOnPreparedListener(OnPreparedListener onPreparedListener) {
		this.onPreparedListener = onPreparedListener;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = getDefaultSize(videoWidth, widthMeasureSpec);
		int height = getDefaultSize(videoHeight, heightMeasureSpec);
		if (videoWidth > 0 && videoHeight > 0) {
			if (videoWidth * height > width * videoHeight) {
				height = width * videoHeight / videoWidth;
			} else if (videoWidth * height < width * videoHeight) {
				width = height * videoWidth / videoHeight;
			}
		}
		Log.i(TAG, "setting size: " + width + "x" + height);
//		if(width>0&&height>0)
			setMeasuredDimension(width, height);
	}

	private void initVideoView() {
		videoWidth = 0;
		videoHeight = 0;
		mediaPlayer=new MediaPlayer();
		getHolder().addCallback(surfaceHolderCallback);
		getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		onPreparedListener=new OnPreparedListener() {
			
			@Override
			public void onPrepared(MediaPlayer mp) {
				Log.d(TAG, "onPrepared=======");
				play();
			}
			
		};
		setFocusable(true);
		setFocusableInTouchMode(true);
		requestFocus();
	}
	public void play(){
		if(mediaPlayer!=null)
		mediaPlayer.start();
	}
	@Override
	public int getAudioSessionId() {
		return 0;
	}
}