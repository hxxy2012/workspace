package com.hike.digitalgymnastic.entitiy;

import java.io.Serializable;

public class Paobu implements Serializable{
	Integer juli; // 距离(米)
	Integer podu; // 坡度(度)
	Integer shijian; // 总时长(秒)
	Double sudu; // 平均速度(公里/小时)
	Double shishisudu; // 实时速度(公里/小时)
	Double kaluli; // 卡路里(千卡)

	public Integer getJuli() {
		return juli;
	}

	public void setJuli(Integer juli) {
		this.juli = juli;
	}

	public Integer getPodu() {
		return podu;
	}

	public void setPodu(Integer podu) {
		this.podu = podu;
	}

	public Integer getShijian() {
		return shijian;
	}

	public void setShijian(Integer shijian) {
		this.shijian = shijian;
	}

	public Double getSudu() {
		return sudu;
	}

	public void setSudu(Double sudu) {
		this.sudu = sudu;
	}

	public Double getShishisudu() {
		return shishisudu;
	}

	public void setShishisudu(Double shishisudu) {
		this.shishisudu = shishisudu;
	}

	public Double getKaluli() {
		return kaluli;
	}

	public void setKaluli(Double kaluli) {
		this.kaluli = kaluli;
	}

}
