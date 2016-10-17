package com.hike.digitalgymnastic.entitiy;

import java.io.Serializable;

//JiroulvStandard(肌肉率标准):
public class JiroulvStandard implements Serializable{
	private double standardFloor;//标准肌肉率下限(%)	
	private double excellentFloor;//优秀肌肉率下限(%)	
	
	public double getExcellentFloor() {
		return excellentFloor;
	}
	public void setExcellentFloor(double excellentFloor) {
		this.excellentFloor = excellentFloor;
	}
	public double getStandardFloor() {
		return standardFloor;
	}
	public void setStandardFloor(double standardFloor) {
		this.standardFloor = standardFloor;
	}
	
	
}
