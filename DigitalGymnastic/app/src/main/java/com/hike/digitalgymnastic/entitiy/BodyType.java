package com.hike.digitalgymnastic.entitiy;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.NotNull;
import com.lidroid.xutils.db.annotation.Table;
import com.lidroid.xutils.db.annotation.Unique;

@Table(name="BodyType")
public class BodyType {
	@Id(column="id")
	@NotNull
	@Unique
	int id;
	
	@Column(column="type")
	@Unique
	public int type;  //体测类型
	
	@Column(column="name")
	public String name;  //体测类型名称
	
	@Column(column="standard1")//体测标准值（共5个，保存到一个表中）
	public String standard1;  
	@Column(column="standard2")
	public String standard2;  
	@Column(column="standard3")
	public String standard3;  
	@Column(column="standard4")
	public String standard4; 
	@Column(column="standard5")
	public String standard5; 
}
