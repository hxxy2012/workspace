package com.hike.digitalgymnastic.entitiy;

import java.io.Serializable;

public class YouYangWuDao implements Serializable{
	Integer shijian; // 时间(秒)
	Integer cishu; // 次数(次)
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

	public Double getKaluli() {
		return kaluli;
	}

	public void setKaluli(Double kaluli) {
		this.kaluli = kaluli;
	}

}
