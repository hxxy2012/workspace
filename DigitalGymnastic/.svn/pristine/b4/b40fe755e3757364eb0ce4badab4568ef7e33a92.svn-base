package com.hike.digitalgymnastic.db;

import java.sql.Date;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.NotNull;
import com.lidroid.xutils.db.annotation.Table;
import com.lidroid.xutils.db.annotation.Unique;
/*
 * 体测历史数据表
 */
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
