package com.hike.digitalgymnastic.entitiy;

import java.io.Serializable;

//JiroulvData(肌肉率数据):
public class JiroulvData implements Serializable{
	private JiroulvStandard	standard;//肌肉率标准	
	private short grade;//肌肉率评级(-1:偏;0:标准;1:偏高;2:高)	
	private double value;//肌肉率值(%)
	public JiroulvStandard getStandard() {
		return standard;
	}
	public void setStandard(JiroulvStandard standard) {
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
