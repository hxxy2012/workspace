package com.hike.digitalgymnastic;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.OrientationEventListener;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hike.digitalgymnastic.utils.Constants;
import com.hike.digitalgymnastic.utils.ImageUtil;
import com.hike.digitalgymnastic.utils.PhotoPicker;
import com.hike.digitalgymnastic.utils.ReqeustCode;
import com.hike.digitalgymnastic.utils.Utils;
import com.hiko.enterprisedigital.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by huwei on 2016/3/10.
 */

@ContentView(R.layout.activity_photoalbum)
public class PhotoAlbumActivity extends BaseActivity implements ReqeustCode, View.OnClickListener, SurfaceHolder.Callback {
    @ViewInject(R.id.surface_shows)
    SurfaceView surface_shows;
    @ViewInject(R.id.ib_takepic)
    ImageView ib_takepic;
    @ViewInject(R.id.ib_switch)
    ImageView ib_switch;
    @ViewInject(R.id.ib_close)
    ImageView ib_close;
    @ViewInject(R.id.iv_flashmode)
    ImageView iv_flashmode;
    @ViewInject(R.id.iv_album_show)
    ImageView iv_album_show;

    SurfaceHolder holder;
    LinearLayout.LayoutParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//没有标题
        ViewUtils.inject(this);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//拍照过程屏幕一直处于高亮
        //设置手机屏幕朝向，一共有7种
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
    }

    int screenWidth, screenHeight;

    private void init() {
        holder = surface_shows.getHolder();
        holder.addCallback(this); // 回调接口
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
//        openCamera();


        WindowManager windowManager = this.getWindowManager();

        screenWidth = windowManager.getDefaultDisplay().getWidth();
        screenHeight = windowManager.getDefaultDisplay().getHeight();


        params = new LinearLayout.LayoutParams(screenWidth, screenWidth);
        surface_shows.setLayoutParams(params);

        if (getIntent() != null) {
            this.fromToggle = getIntent().getBooleanExtra(Constants.fromToggle, false);
        }
    }

    /**
     * 根据当前照相机状态(前置或后置)，打开对应相机
     */
    boolean mIsFrontCamera = false;

    private boolean openCamera() {
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }

        if (mIsFrontCamera) {
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            for (int i = 0; i < Camera.getNumberOfCameras(); i++) {
                Camera.getCameraInfo(i, cameraInfo);
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    try {
                        mCamera = Camera.open(i);
                    } catch (Exception e) {
                        mCamera = null;
                        return false;
                    }
                }
            }
        } else {
            try {
                mCamera = Camera.open();
                mCamera.setPreviewDisplay(holder);
                mCamera.startPreview();
            } catch (Exception e) {
                mCamera = null;
                return false;
            }

        }
        return true;
    }


    private Camera mCamera;
    /* 图像数据处理还未完成时的回调函数 */
    private Camera.ShutterCallback mShutter = new Camera.ShutterCallback() {
        @Override
        public void onShutter() {
            // 一般显示进度条
            showProgress(PhotoAlbumActivity.this, true);
        }
    };
    File captureFile;
    BufferedOutputStream bos;
    int degree;
    /* 图像数据处理完成后的回调函数 */
    private Camera.PictureCallback mJpeg = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length);
            String filePath = Utils.savePic(bm, "picHead");
            Intent i = new Intent(PhotoAlbumActivity.this, DiaryPublishActivity.class);
            i.putExtra("imagePath", filePath);
            startActivity(i);
            finish();
        }
    };


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.push_down, R.anim.push_up);
    }

    boolean fromToggle = false;
    /**
     * 当前闪光灯类型，默认为关闭
     */
    private String mFlashMode = Camera.Parameters.FLASH_MODE_AUTO;

    @OnClick(value = {R.id.iv_album_show, R.id.iv_flashmode, R.id.ib_switch, R.id.ib_takepic, R.id.ib_close})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_album_show:
                PhotoPicker.launchGallery(this, FROM_GALLERY);
                break;
            case R.id.iv_flashmode:
                //当前模式为自动模式
                setFlashMode(mFlashMode);
                break;
            case R.id.ib_switch:
                switchCamera();
                break;
            case R.id.ib_takepic:
                mCamera.autoFocus(new Camera.AutoFocusCallback() {//自动对焦
                    @Override
                    public void onAutoFocus(boolean success, Camera camera) {
                        if (success) {
                            camera.takePicture(mShutter, null, mJpeg);//将拍摄到的照片给自定义的对象
                        }
                    }
                });
                break;
            case R.id.ib_close:
                finish();
                break;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // SurfaceView创建时，建立Camera和SurfaceView的联系
        openCamera();
        updateCameraOrientation();
        setCameraParameters();
//        setCameraParameters2();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // SurfaceView销毁时，取消Camera预览
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
        holder = null;
        surface_shows = null;
    }

    @SuppressLint("NewApi")
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        // 释放相机
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }

    }

    private Camera.Size getBestSupportedSize(List<Camera.Size> sizes) {
        // 取能适用的最大的SIZE
        Camera.Size largestSize = sizes.get(0);
        int largestArea = sizes.get(0).height * sizes.get(0).width;
        for (Camera.Size s : sizes) {
            int area = s.width * s.height;
            if (area > largestArea) {
                largestArea = area;
                largestSize = s;
            }
        }
        return largestSize;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == FROM_GALLERY) {
                //系统图片
                if (data != null) {
                    String path = PhotoPicker.getPhotoPathByLocalUri(this, data);
                    String filepath = ImageUtil.getTargetImage(this, path, null, 1024, false, 0);
                    startIntent(filepath, DiaryPublishActivity.class);
                }
            }

        }
    }

    public void startIntent(String path, Class class1) {
        Intent intent = new Intent(this, class1);
        intent.putExtra("imagePath", path);
        startActivity(intent);
        finish();
    }


    /**
     * 转换前置和后置照相机
     */
    public void switchCamera() {
        mIsFrontCamera = !mIsFrontCamera;
        openCamera();
        if (mCamera != null) {
            updateCameraOrientation();
            try {
                mCamera.setPreviewDisplay(holder);
                mCamera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 设置照相机参数
     */
    private void setCameraParameters() {
        Camera.Parameters parameters = mCamera.getParameters();
        // 选择合适的预览尺寸
        List<Camera.Size> sizeList = parameters.getSupportedPreviewSizes();
//        if (sizeList.size() > 0) {
//            Camera.Size cameraSize = sizeList.get(0);
//            //预览图片大小
//            parameters.setPreviewSize(cameraSize.width, cameraSize.height);
//        }

//        设置生成的图片大小
        sizeList = parameters.getSupportedPictureSizes();
        if (sizeList.size() > 0) {
            Camera.Size cameraSize = sizeList.get(0);
            for (Camera.Size size : sizeList) {
                //小于100W像素
                if (size.width * size.height < 100 * 10000) {
                    cameraSize = size;
                    break;
                }
            }
            parameters.setPictureSize(cameraSize.width, cameraSize.height);
        }
        //设置图片格式
        parameters.setPictureFormat(ImageFormat.JPEG);
        parameters.setJpegQuality(100);
//        parameters.setJpegThumbnailQuality(100);
        //自动聚焦模式
        List<String> focusModes = parameters.getSupportedFocusModes();
        if (focusModes.contains("continuous-video")) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
        }
        mCamera.setParameters(parameters);
        //设置闪光灯模式。此处主要是用于在相机摧毁后又重建，保持之前的状态
        setFlashMode(mFlashMode);
        //开启屏幕朝向监听
        startOrientationChangeListener();
    }

    /**
     * 设置闪光灯类型
     *
     * @param flashMode
     */
    public void setFlashMode(String flashMode) {
        if (mCamera == null) return;
        mFlashMode = flashMode;
        Camera.Parameters parameters = mCamera.getParameters();
        if (mFlashMode.equals(Camera.Parameters.FLASH_MODE_AUTO)) {
            iv_flashmode.setImageResource(R.mipmap.light_close);
            mFlashMode = Camera.Parameters.FLASH_MODE_OFF;
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        } else if (mFlashMode.equals(Camera.Parameters.FLASH_MODE_OFF)) {
            iv_flashmode.setImageResource(R.mipmap.light_open);
            mFlashMode = Camera.Parameters.FLASH_MODE_ON;
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
        } else if (mFlashMode.equals(Camera.Parameters.FLASH_MODE_ON)) {
            iv_flashmode.setImageResource(R.mipmap.light_auto);
            mFlashMode = Camera.Parameters.FLASH_MODE_AUTO;
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
        }
        mCamera.setParameters(parameters);
    }

    /**
     * 启动屏幕朝向改变监听函数 用于在屏幕横竖屏切换时改变保存的图片的方向
     */
    private void startOrientationChangeListener() {
        OrientationEventListener mOrEventListener = new OrientationEventListener(this) {
            @Override
            public void onOrientationChanged(int rotation) {

                if (((rotation >= 0) && (rotation <= 45)) || (rotation > 315)) {
                    rotation = 0;
                } else if ((rotation > 45) && (rotation <= 135)) {
                    rotation = 90;
                } else if ((rotation > 135) && (rotation <= 225)) {
                    rotation = 180;
                } else if ((rotation > 225) && (rotation <= 315)) {
                    rotation = 270;
                } else {
                    rotation = 0;
                }
                if (rotation == mOrientation)
                    return;
                mOrientation = rotation;
                updateCameraOrientation();
            }
        };
        mOrEventListener.enable();
    }

    /**
     * 根据当前朝向修改保存图片的旋转角度
     */

    private int mOrientation = 0;

    private void updateCameraOrientation() {
        if (mCamera != null) {
            Camera.Parameters parameters = mCamera.getParameters();
            //rotation参数为 0、90、180、270。水平方向为0。
            int rotation = 90 + mOrientation == 360 ? 0 : 90 + mOrientation;
            //前置摄像头需要对垂直方向做变换，否则照片是颠倒的
            if (mIsFrontCamera) {
                if (rotation == 90) rotation = 270;
                else if (rotation == 270) rotation = 90;
            }
            parameters.setRotation(rotation);//生成的图片转90°
            //预览图片旋转90°
            mCamera.setDisplayOrientation(90);//预览转90°
            mCamera.setParameters(parameters);
        }
    }

}
