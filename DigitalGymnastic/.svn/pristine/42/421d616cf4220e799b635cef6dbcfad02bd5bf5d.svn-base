package com.hike.digitalgymnastic.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hike.digitalgymnastic.entitiy.VenueDiary;
import com.hike.digitalgymnastic.http.HttpConnectUtils;
import com.hike.digitalgymnastic.request.BaseDao;
import com.hike.digitalgymnastic.view.ZanButton;
import com.hiko.enterprisedigital.R;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by huwei on 2016/2/19.
 */
public class PersonalDiaryAdapter extends MyBaseAdapter<VenueDiary> {
    List<VenueDiary> list;
    Context context;
    BitmapUtils bm;
    BaseDao dao;

    public PersonalDiaryAdapter(List<VenueDiary> list, Context context, BitmapUtils bm, BaseDao dao) {
        super(list, context);
        this.list = list;
        this.context = context;
        this.bm = bm;
        this.dao = dao;
    }

    @Override
    public View getConvertView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_pd, null);
            holder = new ViewHolder();
            ViewUtils.inject(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final VenueDiary diary = list.get(position);
        fillZan(holder, diary);
        fillCommentInfo(holder, diary);
        fillOwnerInfo(holder, diary);
        fillDiaryRelInfo(holder, diary);

        holder.tv_zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean praised = diary.isPraised();
                int praiseCount = diary.getPraiseCount();
                if (praised) {
                    praiseCount--;
                    diary.setPraiseCount(praiseCount);
                    diary.setPraised(false);
                    dao.unpraiseDiary(null, diary.getDiaryId());
                    holder.tv_zan.setZanCount(0);
                    if (praiseCount == 0) {
                        holder.count_zan.setVisibility(View.GONE);
                    } else {
                        holder.count_zan.setVisibility(View.VISIBLE);
                        holder.count_zan.setText(String.valueOf(praiseCount));
                    }
                } else {
                    // 自己没点赞
                    praiseCount++;
                    diary.setPraiseCount(praiseCount);
                    diary.setPraised(true);
                    dao.praiseDiary(null, diary.getDiaryId());
                    holder.tv_zan.setZanCount(praiseCount);
                    holder.count_zan.setVisibility(View.VISIBLE);
                    holder.count_zan.setText(String.valueOf(praiseCount));
                }
            }
        });
        holder.rl_zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean praised = diary.isPraised();
                int praiseCount = diary.getPraiseCount();
                if (praised) {
                    praiseCount--;
                    diary.setPraiseCount(praiseCount);
                    diary.setPraised(false);
                    dao.unpraiseDiary(null, diary.getDiaryId());
                    holder.tv_zan.setZanCount(0);
                    if (praiseCount == 0) {
                        holder.count_zan.setVisibility(View.GONE);
                    } else {
                        holder.count_zan.setVisibility(View.VISIBLE);
                        holder.count_zan.setText(String.valueOf(praiseCount));
                    }
                } else {
                    // 自己没点赞
                    praiseCount++;
                    diary.setPraiseCount(praiseCount);
                    diary.setPraised(true);
                    dao.praiseDiary(null, diary.getDiaryId());
                    holder.tv_zan.setZanCount(praiseCount);
                    holder.count_zan.setVisibility(View.VISIBLE);
                    holder.count_zan.setText(String.valueOf(praiseCount));
                }

            }
        });

        return convertView;
    }


    /**
     * @param holder
     * @param diary
     * @category 填充场馆日记的详细信息
     */
    private void fillDiaryRelInfo(ViewHolder holder, VenueDiary diary) {
        String idea = diary.getIdea();// 描述信息
        String photo = diary.getPhoto();
        if (!TextUtils.isEmpty(photo) && TextUtils.isEmpty(idea)) {
            // 有图片，没有运动说说
            holder.rl_two.setVisibility(View.VISIBLE);
            bm.display(holder.iv_diarypic, HttpConnectUtils.image_ip + photo);
            holder.tv_shuoshuo.setVisibility(View.GONE);
        } else if (!TextUtils.isEmpty(photo) && !TextUtils.isEmpty(idea)) {
            // 两者都有
            holder.rl_two.setVisibility(View.VISIBLE);
            bm.display(holder.iv_diarypic, HttpConnectUtils.image_ip + photo);
            holder.tv_shuoshuo.setVisibility(View.VISIBLE);
            holder.tv_shuoshuo.setText(idea);
        }
    }

    /**
     * @param holder
     * @param diary
     * @category 填充评论信息
     */
    private void fillCommentInfo(ViewHolder holder, final VenueDiary diary) {
        int commentCount = diary.getCommentCount();
        if (commentCount > 0) {
            holder.count_comment.setVisibility(View.VISIBLE);
            holder.count_comment.setText(String.valueOf(commentCount));
        } else {
            holder.count_comment.setVisibility(View.GONE);
        }

    }

    private void fillZan(final ViewHolder holder, final VenueDiary diary) {
        // 获得点赞数
        final int praiseCount = diary.getPraiseCount();
        boolean praised = diary.isPraised();
        if (praised) {// 自己称赞
            if (praiseCount > 0) {
                holder.tv_zan.setZanCount(praiseCount);
                holder.count_zan.setVisibility(View.VISIBLE);
                holder.count_zan.setText(String.valueOf(praiseCount));
            }
        } else {
            if (praiseCount > 0) {
                holder.tv_zan.setZanCount(0);
                holder.count_zan.setVisibility(View.VISIBLE);
                holder.count_zan.setText(String.valueOf(praiseCount));
            } else {
                holder.tv_zan.setZanCount(0);
                holder.count_zan.setVisibility(View.GONE);
            }

        }
    }


    /**
     * @param diaryDetail
     * @category 填充个人头像和名称以及发布的时间
     */
    private void fillOwnerInfo(ViewHolder holder, VenueDiary diaryDetail) {
        String createdTime = diaryDetail.getCreatedTime();
        String date = createdTime.substring(0, 10);
        String time = createdTime.substring(10, createdTime.length());
        if (!TextUtils.isEmpty(diaryDetail.getName())) {
            holder.tv_date.setText(date);
        }
        if (!TextUtils.isEmpty(diaryDetail.getCreatedTime())) {
            holder.tv_publishtime.setText(time);
        }
    }

    class ViewHolder {
        @ViewInject(R.id.tv_zan)
        ZanButton tv_zan;
        @ViewInject(R.id.ll_item_bottom)
        LinearLayout ll_item_bottom;
        //ImageView
        @ViewInject(R.id.iv_comment)
        ImageView iv_comment;
        @ViewInject(R.id.iv_diarypic)
        ImageView iv_diarypic;
        //TextView
        @ViewInject(R.id.tv_publishtime)
        TextView tv_publishtime;
        @ViewInject(R.id.tv_date)
        TextView tv_date;
        @ViewInject(R.id.count_comment)
        TextView count_comment;
        @ViewInject(R.id.tv_shuoshuo)
        TextView tv_shuoshuo;
        @ViewInject(R.id.count_zan)
        TextView count_zan;
        //Relative
        @ViewInject(R.id.rl_comment)
        RelativeLayout rl_comment;
        @ViewInject(R.id.rl_two)
        RelativeLayout rl_two;
        @ViewInject(R.id.rl_zan)
        RelativeLayout rl_zan;
    }
}
