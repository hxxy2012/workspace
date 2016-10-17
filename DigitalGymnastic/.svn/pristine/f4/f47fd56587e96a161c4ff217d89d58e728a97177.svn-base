package com.hike.digitalgymnastic.entitiy;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.NotNull;
import com.lidroid.xutils.db.annotation.Table;
import com.lidroid.xutils.db.annotation.Unique;

@Table(name="BodyHistory")
public class BodyHistory {
	@Id(column="id")
	@NotNull
	@Unique
	int id;
	@Column(column="type")
	public int type;  //体测类型
	@Column(column="name")
	public String date;  //体测时间
	@Column(column="value")//体测值
	public String value;  
	
	
}
