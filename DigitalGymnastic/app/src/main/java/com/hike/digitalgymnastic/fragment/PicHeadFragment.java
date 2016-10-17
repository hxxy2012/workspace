package com.hike.digitalgymnastic.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.hike.digitalgymnastic.MainFragment;
import com.hike.digitalgymnastic.PicturePickerActivity;
import com.hike.digitalgymnastic.entitiy.Customer;
import com.hike.digitalgymnastic.http.HttpConnectUtils;
import com.hike.digitalgymnastic.request.BaseDao;
import com.hike.digitalgymnastic.utils.Constants;
import com.hike.digitalgymnastic.utils.Contants;
import com.hike.digitalgymnastic.utils.LocalDataUtils;
import com.hike.digitalgymnastic.utils.PreferencesUtils;
import com.hike.digitalgymnastic.utils.Utils;
import com.hike.digitalgymnastic.view.ImageHelper;
import com.hiko.enterprisedigital.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.io.ByteArrayOutputStream;
import java.io.File;

//头像昵称页面
public class PicHeadFragment extends BaseFragment {
    //	@ViewInject(R.id.btn_next_five)
//	private Button btn;
    @ViewInject(R.id.iv_camera)
    private ImageView iv_camera;
    @ViewInject(R.id.et_name)
    private EditText et_name;

    private Customer customer;
    private View v;
    private String imgpath;
    private String name;

    private BaseDao dao;
    private Bitmap myBitmap;
    private ByteArrayOutputStream baos;
    String[] array = new String[]{"管理员", "admin", "官方", "工作人员"};

    @OnClick(value = {R.id.iv_camera})
    public void onClick(View v) {
        switch (v.getId()) {
//		case R.id.btn_next_five:
////			name = et_name.getText().toString().trim();
//			name = et_name.getText().toString();
//			if (TextUtils.isEmpty(name)) {
//				Utils.showMessage(activity, "请填写你的昵称！");
//			} else if(name.contains(" ")){
//				Utils.showMessage(activity, "昵称中包含非法字符！");
//			}else if (!check(name)) {
//				Utils.showMessage(activity, "昵称中仅支持中英文及数字！");
//			} else if (is2Long(name)) {
//				Utils.showMessage(activity, "你输入的昵称过长！");
//			} else if (name.contains(array[0]) || name.contains(array[1])
//					|| name.contains(array[2]) || name.contains(array[3])) {
//				Utils.showMessage(activity, "你输入的昵称中包含敏感词！");
//			} else {
//				if (imgpath == null || imgpath.length() == 0){
////					Utils.showMessage(activity, "请上传头像");
//					((MainFragment) activity).enterTargetActivity();
//				}else{
//					 imgpath = ((MainFragment) activity).getCustomer()
//							.getAvatar();
//					((MainFragment) activity).setPichead(imgpath, name);// 数据返回给主Activity
//
//					((MainFragment) activity).enterTargetActivity();
//				}
//			}
//			break;
            case R.id.iv_camera:
                showPicturePickerDialog();
                break;
            default:
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.inflater = inflater;
//		Utils.toolBarManager((MainFragment) activity, R.color.app_bg_login);
        this.v = inflater.inflate(R.layout.personalinfo_five, container, false);
        ViewUtils.inject(this, v);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Init();
    }
    String goalCalories;
    String description;
    private void Init() {
//		if (activity instanceof MainFragment) {
//			btn.setVisibility(View.VISIBLE);
//		}
        dao = new BaseDao(this, activity);
        customer = ((MainFragment) activity).getCustomer();
        if (!TextUtils.isEmpty(customer.name))
            et_name.setText(customer.name);
        else
            et_name.setText("");
        /* 将默认头像转为圆形 */
//		int gender = PreferencesUtils.getInt((MainFragment) activity, "gender_personal");
        int gender = ((MainFragment) activity).getGender();
        int year = ((MainFragment) activity).getYear();
        int weight = (int) ((MainFragment) activity).getWeight();
        int height = ((MainFragment) activity).getHeight();
        System.out.println("gender_2" + gender);
        System.out.println("year_2" + year);
        System.out.println("weight_2" + weight);
        System.out.println("height_2" + height);
        Bitmap bmp;
        Resources res = getResources();
        if (gender == 1) {
            bmp = BitmapFactory.decodeResource(res,
                    R.mipmap.boy_head);
        } else {
            bmp = BitmapFactory.decodeResource(res,
                    R.mipmap.girl_head);
        }
        iv_camera.setImageBitmap(bmp);
//		Bitmap bmp = BitmapFactory.decodeResource(res,
//				R.mipmap.boy_head);
//		iv_camera.setImageBitmap(ImageHelper.toRoundBitmap(bmp));

        if (!TextUtils.isEmpty(customer.avatar)) {// 此处启动线程获取头像
            new Thread(new Runnable() {

                @Override
                public void run() {
                    String url = HttpConnectUtils.image_ip + customer.avatar;
                    Bitmap bitmap = Utils.getHttpBitmap(url);
                    Message msg = new Message();
                    msg.obj = bitmap;
                    handler.sendMessage(msg);
                }
            }).start();

        }

        et_name.addTextChangedListener(watcher);
    }

    TextWatcher watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub

        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
            // TODO Auto-generated method stub

        }

        @Override
        public void afterTextChanged(Editable editable) {
            // TODO Auto-generated method stub
            String name = editable.toString();
            if (name.contains(" ")) {
                name = name.trim();
                et_name.setText(name.trim());
            }
            PreferencesUtils.putString((MainFragment) activity, Contants.NICKNAME, name);
//			else if(is2Long(name)){
//				et_name.setText(name.substring(0, name.length()-1));
//			}
        }
    };
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            if (msg.obj != null) {
                try {
                    Bitmap bmp = (Bitmap) msg.obj;
                    iv_camera.setImageBitmap(ImageHelper.toRoundBitmap(bmp));
                } catch (Exception e) {
                }
            }

        }

    };

    private void showPicturePickerDialog() {
        Intent intent = null;
        intent = new Intent(activity, PicturePickerActivity.class);
        startActivityForResult(intent, Constants.requestCode_pickpicture);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (data != null) {
            String imagePath = data.getStringExtra("imagePath");
            if (imagePath != null) {

                uploadImage(imagePath);
            }
        }
    }

    // 上传图片
    private void uploadImage(final String imagePath) {
        showProgress(true);
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                File file = new File(imagePath);
                myBitmap = Utils.imageFile2Bitmap(file);
                String filePath = Utils.savePic(myBitmap, "picHead");
                dao.UploadImage(filePath);
            }
        }).start();

    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);
        this.imgpath = dao.getImageurl().getImageUrl();
        customer.setAvatar(imgpath);
        ((MainFragment) activity).getCustomer().setAvatar(imgpath);
        LocalDataUtils.saveCustomer(activity, customer);
        name = "昵称";
        PreferencesUtils.putString((MainFragment) activity, Contants.NICKNAME, name);
        try {
            if (myBitmap != null) {
                iv_camera.setImageBitmap(ImageHelper.toRoundBitmap(myBitmap));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            if (myBitmap != null && !myBitmap.isRecycled()) {
                myBitmap.recycle();
                myBitmap = null;
            }
        } else {
            Init();
        }
    }
}