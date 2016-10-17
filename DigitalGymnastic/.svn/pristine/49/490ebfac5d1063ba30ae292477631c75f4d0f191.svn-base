package com.hike.digitalgymnastic.entitiy;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.NotNull;
import com.lidroid.xutils.db.annotation.Table;
import com.lidroid.xutils.db.annotation.Unique;

import java.io.Serializable;
import java.util.List;

@Table(name = "VenueDiary")
public class VenueDiary implements Serializable {
    @Id(column = "id")
    @NotNull
    @Unique
    int id;
    @Column(column = "diaryId")
    long diaryId; // 日记标识
    @Column(column = "customerId")
    long customerId;// 客户标识
    @Column(column = "name")
    String name; // 客户名称
    @Column(column = "name")
    String avatar;// 客户头像
    @Column(column = "type")
    short type; // 日记类型(1:运动;2:饮食;3:感受)
    @Column(column = "idea")
    String idea; // 日记想法
    @Column(column = "photo")
    String photo; // 日记照片
    @Column(column = "dietType")
    short dietType; // 饮食类型(1:早餐;2:午餐;3:晚餐;4:加餐)
    @Column(column = "dietContent")
    String dietContent; // 饮食内容
    @Column(column = "venueId")
    String venueId; // 饮食内容
    @Column(column = "sportItem")
    String sportItem; // 运动项目
    @Column(column = "sportDuration")
    int sportDuration; // 运动时长(分)
    @Column(column = "praiseCount")
    int praiseCount; // 称赞数量
    @Column(column = "commentCount")
    int commentCount; // 评论数量
    @Column(column = "isPraised")
    boolean isPraised;// 是否称赞
    @Column(column = "createdTime")
    String createdTime; // 创建时间(YYYY-MM-DD HH:MM:SS)
    List<DiaryComment> commentList; // 评论列表(只显示前3条)

    public long getDiaryId() {
        return diaryId;
    }

    public void setDiaryId(long diaryId) {
        this.diaryId = diaryId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public String getIdea() {
        return idea;
    }

    public String getVenueId() {
        return venueId;
    }

    public void setVenueId(String venueId) {
        this.venueId = venueId;
    }

    public void setIdea(String idea) {
        this.idea = idea;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public short getDietType() {
        return dietType;
    }

    public void setDietType(short dietType) {
        this.dietType = dietType;
    }

    public String getDietContent() {
        return dietContent;
    }

    public void setDietContent(String dietContent) {
        this.dietContent = dietContent;
    }

    public String getSportItem() {
        return sportItem;
    }

    public void setSportItem(String sportItem) {
        this.sportItem = sportItem;
    }

    public int getSportDuration() {
        return sportDuration;
    }

    public void setSportDuration(int sportDuration) {
        this.sportDuration = sportDuration;
    }

    public int getPraiseCount() {
        return praiseCount;
    }

    public void setPraiseCount(int praiseCount) {
        this.praiseCount = praiseCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public boolean isPraised() {
        return isPraised;
    }

    public void setPraised(boolean isPraised) {
        this.isPraised = isPraised;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public List<DiaryComment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<DiaryComment> commentList) {
        this.commentList = commentList;
    }

    // public final Parcelable.Creator<VenueDiary> CREATOR = new
    // Creator<VenueDiary>() {
    //
    // @Override
    // public VenueDiary createFromParcel(Parcel source) {
    // // TODO Auto-generated method stub
    // VenueDiary diary = new VenueDiary();
    // diary.diaryId = source.readLong();
    // diary.customerId = source.readLong();
    // diary.name = source.readString();
    // diary.avatar = source.readString();
    // diary.type = (short) source.readInt();
    // diary.idea = source.readString();
    // diary.photo = source.readString();
    // diary.dietType = (short) source.readInt();
    // diary.dietContent = source.readString();
    // diary.sportItem = source.readString();
    // diary.sportDuration = source.readInt();
    // diary.praiseCount = source.readInt();
    // diary.commentCount = source.readInt();
    // // 布尔型
    // diary.isPraised = source.readByte() != 0;
    // diary.createdTime = source.readString();
    // diary.commentList = new ArrayList<DiaryComment>();
    // source.readList(diary.commentList, getClass().getClassLoader());
    // return diary;
    // }
    //
    // @Override
    // public VenueDiary[] newArray(int size) {
    // return new VenueDiary[size];
    // }
    //
    // };
    //
    // @Override
    // public int describeContents() {
    // return 0;
    // }
    //
    // @Override
    // public void writeToParcel(Parcel dest, int flags) {
    // dest.writeLong(diaryId);
    // dest.writeLong(customerId);
    // dest.writeString(name);
    // dest.writeString(avatar);
    // dest.writeInt(type);
    // dest.writeString(idea);
    // dest.writeString(photo);
    // dest.writeInt(dietType);
    // dest.writeString(dietContent);
    // dest.writeString(sportItem);
    // dest.writeInt(sportDuration);
    // dest.writeInt(praiseCount);
    // dest.writeInt(commentCount);
    // dest.writeByte((byte) (isPraised ? 1 : 0));
    // dest.writeString(createdTime);
    // dest.writeList(commentList);
    // }

}
