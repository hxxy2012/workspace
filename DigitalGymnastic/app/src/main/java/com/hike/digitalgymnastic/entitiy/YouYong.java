package com.hike.digitalgymnastic.entitiy;

import java.io.Serializable;

public class YouYong implements Serializable{
	Integer juli;    //距离(米)   
	Integer shijian;    //总时长(秒)   
	Double pingjunsudu;  //平均速度(公里/小时)   
	Double fengzhisudu;   //峰值速度(公里/小时)   
	public Integer getJuli() {
		return juli;
	}
	public void setJuli(Integer juli) {
		this.juli = juli;
	}
	public Integer getShijian() {
		return shijian;
	}
	public void setShijian(Integer shijian) {
		this.shijian = shijian;
	}
	public Double getPingjunsudu() {
		return pingjunsudu;
	}
	public void setPingjunsudu(Double pingjunsudu) {
		this.pingjunsudu = pingjunsudu;
	}
	public Double getFengzhisudu() {
		return fengzhisudu;
	}
	public void setFengzhisudu(Double fengzhisudu) {
		this.fengzhisudu = fengzhisudu;
	}
	public Double getKaluli() {
		return kaluli;
	}
	public void setKaluli(Double kaluli) {
		this.kaluli = kaluli;
	}
	Double kaluli;  //卡路里(千卡)  

}
