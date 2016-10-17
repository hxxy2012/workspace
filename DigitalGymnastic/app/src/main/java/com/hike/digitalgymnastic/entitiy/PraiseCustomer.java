package com.hike.digitalgymnastic.entitiy;

import java.io.Serializable;

public class PraiseCustomer implements Serializable{
	long customerId	;//	客户标识	
	String name		;//客户名称	
	String avatar		;//客户头像	
	String time		;//称赞时间(YYYY-MM-DD HH:MM:SS)
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
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

}
