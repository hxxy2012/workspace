package com.hike.digitalgymnastic.entitiy;

import java.io.Serializable;
import java.util.List;

//获取运动分享图片
public class ShareImageData implements Serializable {
    int imageNumber	;	//图片个数
    List<ShareImage> imageList;	//图片列表

    public List<ShareImage> getImageList() {
        return imageList;
    }

    public void setImageList(List<ShareImage> imageList) {
        this.imageList = imageList;
    }

    public int getImageNumber() {
        return imageNumber;
    }

    public void setImageNumber(int imageNumber) {
        this.imageNumber = imageNumber;
    }
}
