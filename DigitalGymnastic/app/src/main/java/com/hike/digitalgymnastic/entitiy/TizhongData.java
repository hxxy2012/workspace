package com.hike.digitalgymnastic.entitiy;

import java.io.Serializable;

//TizhongData(体重数据):
public class TizhongData implements Serializable{
	private	TizhongStandard standard;//体重标准
	private short grade;//体重评级(-1:偏低;0:标准;1:偏高)
	private double value;//体重值(千克)
	public TizhongStandard getStandard() {
		return standard;
	}
	public void setStandard(TizhongStandard standard) {
		this.standard = standard;
	}
	public short getGrade() {
		return grade;
	}
	public void setGrade(short grade) {
		this.grade = grade;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	
}
