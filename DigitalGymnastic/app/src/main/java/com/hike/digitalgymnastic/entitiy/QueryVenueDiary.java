package com.hike.digitalgymnastic.entitiy;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huwei on 2016/2/22.
 */
public class QueryVenueDiary implements Serializable {
    int total;// 关注日记总数
    List<VenueDiary> diaryList;// 关注日记列表

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<VenueDiary> getDiaryList() {
        return diaryList;
    }

    public void setDiaryList(List<VenueDiary> diaryList) {
        this.diaryList = diaryList;
    }

}