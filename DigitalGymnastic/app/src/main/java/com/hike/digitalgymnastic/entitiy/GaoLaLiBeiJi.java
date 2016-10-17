package com.hike.digitalgymnastic.entitiy;

import java.io.Serializable;

public class GaoLaLiBeiJi implements Serializable{
	Integer shijian; // 时间(秒)
	Integer cishu; // 次数(次)
	Integer zhongliang; // 重量(磅)
	Double juli; // 距离(米)
	Double kaluli; // 卡路里(千卡)

	public Integer getShijian() {
		return shijian;
	}

	public void setShijian(Integer shijian) {
		this.shijian = shijian;
	}

	public Integer getCishu() {
		return cishu;
	}

	public void setCishu(Integer cishu) {
		this.cishu = cishu;
	}

	public Integer getZhongliang() {
		return zhongliang;
	}

	public void setZhongliang(Integer zhongliang) {
		this.zhongliang = zhongliang;
	}

	public Double getJuli() {
		return juli;
	}

	public void setJuli(Double juli) {
		this.juli = juli;
	}

	public Double getKaluli() {
		return kaluli;
	}

	public void setKaluli(Double kaluli) {
		this.kaluli = kaluli;
	}
}
