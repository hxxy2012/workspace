package com.hike.digitalgymnastic.entitiy;

import android.os.Parcel;
import android.os.Parcelable;

public class Customer implements Parcelable{
	public String id;//		客户标识	
	public String status;	//Short	客户状态(1:正常;2:冻结;3:删除)	
	public String name;//	String	客户名称	
	public String phone;//	String	客户电话	
	public String avatar;//	String	客户头像	
	public String gender;//	Short	客户性别(0:未知;1:男性;2:女性)	
	public String year;	//Integer	客户生年	
	public String height;//	Integer	客户身高(厘米)	
	public String weight;//	Double	客户体重(千克)	
	public String goalCalories;//	Double	目标卡路里(千卡)	
	public String source;//	Short	客户来源(1:宅不住;2:数字健身)	
	public String loginToken;//	String	登录令牌	
	public String bandId;	//String	手环标识	
	public String description;//	String	客户描述
	
	
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
public static final Parcelable.Creator<Customer> CREATOR =new Creator<Customer>() {
		
		@Override
		public Customer[] newArray(int size) {
			
			return new Customer[size];
		}
		
		@Override
		public Customer createFromParcel(Parcel source) {
			Customer customer=new Customer();
			customer.id=source.readString();
			customer.status=source.readString();
			customer.name=source.readString();
			customer.phone=source.readString();
			customer.avatar=source.readString();
			customer.gender=source.readString();
			customer.year=source.readString();
			customer.height=source.readString();
			customer.weight=source.readString();
			customer.goalCalories=source.readString();
			customer.source=source.readString();
			customer.loginToken=source.readString();
			customer.bandId=source.readString();
			customer.description=source.readString();
			return customer;
		}
	};
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(status);
		dest.writeString(name);
		dest.writeString(phone);
		dest.writeString(avatar);
		dest.writeString(gender);
		dest.writeString(year);
		dest.writeString(height);
		dest.writeString(weight);
		dest.writeString(goalCalories);
		dest.writeString(source);
		dest.writeString(loginToken);
		dest.writeString(bandId);
		dest.writeString(description);
	}
	
	
}
