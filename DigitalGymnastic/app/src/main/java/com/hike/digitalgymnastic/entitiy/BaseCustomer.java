package com.hike.digitalgymnastic.entitiy;

public class BaseCustomer {

	public String id;// 客户标识
	public String status; // Short 客户状态(1:正常;2:冻结;3:删除)
	public String name;// String 客户名称
	public String phone;// String 客户电话
	public String avatar;// String 客户头像
	public String gender;// Short 客户性别(0:未知;1:男性;2:女性)
	public String year; // Integer 客户生年
	public String height;// Integer 客户身高(厘米)
	public String weight;// Double 客户体重(千克)
	public String goalCalories;// Double 目标卡路里(千卡)
	public String source;// Short 客户来源(1:宅不住;2:数字健身)
	public String loginToken;// String 登录令牌
	public String bandId; // String 手环标识
	public String description;// String 客户描述

	public String persongender;// 个人信息注册使用客户性别1:男性;2:女性)

	public String getPersongender() {
		return persongender;
	}

	public void setPersongender(String persongender) {
		this.persongender = persongender;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getGoalCalories() {
		return goalCalories;
	}

	public void setGoalCalories(String goalCalories) {
		this.goalCalories = goalCalories;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getLoginToken() {
		return loginToken;
	}

	public void setLoginToken(String loginToken) {
		this.loginToken = loginToken;
	}

	public String getBandId() {
		return bandId;
	}

	public void setBandId(String bandId) {
		this.bandId = bandId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}