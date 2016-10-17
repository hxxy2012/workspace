package com.hike.digitalgymnastic.entitiy;

import java.io.Serializable;

public class TuoYuanJi implements Serializable{
	Integer juli; // 距离(米)
	Double sudu; // /速度(公里/小时)
	Integer shijian; // 时间(秒)
	Double kaluli; // 卡路里(千卡)

	public Integer getJuli() {
		return juli;
	}

	public void setJuli(Integer juli) {
		this.juli = juli;
	}

	public Double getSudu() {
		return sudu;
	}

	public void setSudu(Double sudu) {
		this.sudu = sudu;
	}

	public Integer getShijian() {
		return shijian;
	}

	public void setShijian(Integer shijian) {
		this.shijian = shijian;
	}

	public Double getKaluli() {
		return kaluli;
	}

	public void setKaluli(Double kaluli) {
		this.kaluli = kaluli;
	}

}
