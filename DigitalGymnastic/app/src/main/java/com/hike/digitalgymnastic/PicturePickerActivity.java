package com.hike.digitalgymnastic;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.hike.digitalgymnastic.utils.FileUtil;
import com.hike.digitalgymnastic.utils.ImageUtil;
import com.hike.digitalgymnastic.utils.PhotoPicker;
import com.hike.digitalgymnastic.utils.ReqeustCode;
import com.hike.digitalgymnastic.utils.Utils;
import com.hiko.enterprisedigital.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.io.File;
/*
 * @auth changqi
 * @descirption 照片选择页面
 */

@ContentView(R.layout.dialog_pickpicture)
public class PicturePickerActivity extends BaseActivity implements ReqeustCode{
	private Intent intent;
	File captureFile;
	@ViewInject(R.id.btn_takepic_camera)
	ImageView btn_takepic_camera;
	@ViewInject(R.id.btn_takepic_album)
	ImageView btn_takepic_album;
	private int degree;

	@OnClick(value={R.id.btn_takepic_camera,R.id.btn_takepic_album})
	public void onClick(View view){
		switch (view.getId()) {
		case R.id.btn_takepic_camera:
			 try {  
	                //拍照我们用Action为MediaStore.ACTION_IMAGE_CAPTURE，  
	                //有些人使用其他的Action但我发现在有些机子中会出问题，所以优先选择这个  
	                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);  
	                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);  
	                //文件夹
//	                 path = Environment.getExternalStorageDirectory().toString()+"/hiko/persion/image";
//	                File path1 = new File(path);
//	                if(!path1.exists()){
//	                 path1.mkdirs();
//	                }
//	                File file = new File(path1,System.currentTimeMillis()+".jpg");
//	                Uri mOutPutFileUri = Uri.fromFile(file);
//	                intent.putExtra(MediaStore.EXTRA_OUTPUT, mOutPutFileUri);
//	                startActivityForResult(intent, Constants.requestCode_pickpicture);  
	                captureFile = FileUtil.getCaptureFile(PicturePickerActivity.this);

				  PhotoPicker.launchCamera(PicturePickerActivity.this, FROM_CAPTURE, captureFile);
	            } catch (Exception e) {  
	                e.printStackTrace();  
	                Utils.showMessage(PicturePickerActivity.this, "无法使用拍照功能");
	            }  
			break;
		case R.id.btn_takepic_album:
			try {  
                //选择照片的时候也一样，我们用Action为Intent.ACTION_GET_CONTENT，  
                //有些人使用其他的Action但我发现在有些机子中会出问题，所以优先选择这个  
//                Intent intent = new Intent();  
//                intent.setType("image/*");  
//                intent.setAction(Intent.ACTION_GET_CONTENT);  
//                startActivityForResult(intent, 2);  
                PhotoPicker.launchGallery(PicturePickerActivity.this, FROM_GALLERY);
            } catch (ActivityNotFoundException e) {  
            	Utils.showMessage(PicturePickerActivity.this, "无法查看图片浏览器");
            }  
			break;
		
		default:
			break;
		}
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		ViewUtils.inject(this);
		init();		
	}
	private void init() {
		intent=getIntent();
	}
	@Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
//        if (resultCode != RESULT_OK) {  
//            return;  
//        }
//        Intent intentResult=new Intent();
//        //选择完或者拍完照后会在这里处理，然后我们继续使用setResult返回Intent以便可以传递数据和调用  
//        if (data.getExtras() != null) { 
//        	Bundle bundle=data.getExtras();
//        	Uri uri=data.getData(); 
//        	bundle.putString("imagePath",path);
//        	intentResult.putExtras(bundle);  
//        }
//        if (data.getData()!= null)  
//        	intentResult.setData(data.getData());  
//        setResult(RESULT_OK, intentResult);  
//        finish();


        if (resultCode == RESULT_OK) {

			if (requestCode == FROM_GALLERY) {
				
				if (data != null) {
					String path = PhotoPicker.getPhotoPathByLocalUri(this, data);
					onGalleryComplete(path);
				}
			} else if (requestCode == FROM_CAPTURE) {
				onCaptureComplete(captureFile);
				
			} else if (requestCode == FROM_CROP) {
				if (data != null) {
					Bitmap bitmap = data.getParcelableExtra("data");
					onCropComplete(bitmap);
				}
			}

		}
    }  
		
	 // 实现onTouchEvent触屏函数但点击屏幕时销毁本Activity  
    @Override  
    public boolean onTouchEvent(MotionEvent event) {  
        finish();  
        return true;  
    }  
    
    protected void onGalleryComplete(String filepath) {
    	
    	String	path=ImageUtil.getTargetImage(this, filepath, null, 1024, false, 0);
    	Intent result=new Intent();
		result.putExtra("imagePath", path);
		setResult(Activity.RESULT_OK, result);
		finish();
	}

	protected void onCropComplete(Bitmap bitmap) {

	}

	protected void onCaptureComplete(File captureFile) {
		//String path=ImageUtil.getTargetImage(this, captureFile.getPath(), null, 1024, false, 0);
		try{
			if(captureFile==null){
				degree=0;
			}else{
				degree=Utils.readPictureDegree(captureFile.getAbsolutePath());

			}
			Intent result=new Intent();
			result.putExtra("imagePath", captureFile.getAbsolutePath());
			result.putExtra("degree",degree);
			setResult(Activity.RESULT_OK, result);
			finish();
		}catch (Exception e){
			finish();
		}

	}

}
