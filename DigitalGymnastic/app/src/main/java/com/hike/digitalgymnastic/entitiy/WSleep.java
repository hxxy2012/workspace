package com.hike.digitalgymnastic.entitiy;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.NotNull;
import com.lidroid.xutils.db.annotation.Table;
import com.lidroid.xutils.db.annotation.Unique;

import java.io.Serializable;

@Table(name="WSleep")
public class WSleep implements Serializable {
	@Id(column="id")
	@NotNull
	@Unique
	int id;
	
	public long time;
	
	@Column(column="time")
	public String timeString;  //数据库保存yyyy-MM-dd HH:mm:ss格式的串
	@Column(column="type")
	public int type;
	@Column(column="turnOver")
	public int turnOver;
	@Column(column="reserved")
	public int reserved;
	

}
