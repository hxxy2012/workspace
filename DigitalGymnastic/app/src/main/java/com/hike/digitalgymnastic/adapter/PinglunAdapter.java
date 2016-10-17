package com.hike.digitalgymnastic.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hike.digitalgymnastic.entitiy.DiaryComment;
import com.hike.digitalgymnastic.http.HttpConnectUtils;
import com.hike.digitalgymnastic.view.CircleImageView;
import com.hike.digitalgymnastic.view.ImageHelper;
import com.hiko.enterprisedigital.R;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.bitmap.callback.DefaultBitmapLoadCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by huwei on 2016/2/25.
 */
public class PinglunAdapter extends MyBaseAdapter<DiaryComment> {

    List list;
    SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    AdapterItemClickListener adapterItemClickListener;

    public AdapterItemClickListener getAdapterItemClickListener() {
        return adapterItemClickListener;
    }

    public void setAdapterItemClickListener(AdapterItemClickListener adapterItemClickListener) {
        this.adapterItemClickListener = adapterItemClickListener;
    }

    public PinglunAdapter(List<DiaryComment> list, Context context) {
        super(list, context);
        this.list = list;
        initBitmapUtils();
    }

    private void initBitmapUtils() {
        if (bitmapUtils == null) {
            bitmapUtils = new BitmapUtils(context);
        }
        bitmapUtils.configDefaultLoadFailedImage(R.mipmap.boy_head);
        bitmapUtils.configDefaultShowOriginal(false);// 显示原始图片,不压缩,
        // 尽量不要使用,图片太大时容易OOM。
        bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
        bitmapUtils.configDefaultBitmapMaxSize(BitmapCommonUtils.getScreenSize(context));
        bitmapUtils.configDefaultLoadingImage(R.mipmap.boy_head);
        // bitmapUtils.configDefaultCacheExpiry(2*60*1000);
        bitmapUtils.configThreadPoolSize(4);

    }

    @Override
    public View getConvertView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        final DiaryComment diaryComment = (DiaryComment) getItem(position);
        if (convertView == null) {
            holder = new Holder();
            convertView = inflater.inflate(R.layout.activity_pinglunlist_item, null);
            ViewUtils.inject(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        try {
            holder.tv_name.setText(diaryComment.getName());
            if (diaryComment.getReplyId() != 0 && diaryComment.getCustomerId() != diaryComment.getReplyId()) {
                String before = "回复 ";
                String replyName = diaryComment.getReplyName();
                String value = before + replyName + " :" + diaryComment.getContent();
                SpannableStringBuilder spannable = new SpannableStringBuilder(value);
                spannable.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.register_xieyi_textcolor))
                        , before.length(), before.length() + replyName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置前景色为白色
                holder.tv_reply.setText(spannable);
            } else {
                holder.tv_reply.setText(diaryComment.getContent());
            }
//            bitmapUtils.display(holder.iv_customer, HttpConnectUtils.image_ip + diaryComment.getAvatar());
            display(holder.iv_customer, HttpConnectUtils.image_ip+diaryComment.getAvatar());
            String time = format.format(format1.parse(diaryComment.getTime()));
            holder.tv_time.setText(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //头像添加点击事件
        holder.iv_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapterItemClickListener != null) {
                    adapterItemClickListener.onPictureClick(diaryComment);
                }
            }
        });
        //昵称添加点击事件
        holder.tv_name.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (adapterItemClickListener != null) {
                    adapterItemClickListener.onNickClick(diaryComment);
                }
            }
        });
        return convertView;
    }

    class Holder {
        @ViewInject(R.id.iv_customer)
        CircleImageView iv_customer;
        @ViewInject(R.id.tv_name)
        TextView tv_name;
        @ViewInject(R.id.tv_time)
        TextView tv_time;
        @ViewInject(R.id.tv_reply)
        TextView tv_reply;
    }


    public BitmapDisplayConfig bigPicDisplayConfig;
    public BitmapLoadCallBack<ImageView> bitmapLoadCallBack;
    BitmapUtils bitmapUtils;

    public BitmapUtils getBitmapUtils() {
        return bitmapUtils;
    }

    private void display(ImageView container, String uri) {

        DefaultBitmapLoadCallBack bitmapLoadCallBack = new DefaultBitmapLoadCallBack<ImageView>() {
            @Override
            public void onLoadStarted(ImageView container, String uri, BitmapDisplayConfig config) {
                super.onLoadStarted(container, uri, config);
            }

            @Override
            public void onLoadCompleted(ImageView container, String uri, Bitmap bitmap, BitmapDisplayConfig config,
                                        BitmapLoadFrom from) {
                super.onLoadCompleted(container, uri, bitmap, config, from);
                container.setImageBitmap(ImageHelper.toRoundBitmap(bitmap));
            }
        };
//        uri = uri + Constants.suffix;
        bitmapUtils.display(container, uri, bitmapLoadCallBack);

    }

}
