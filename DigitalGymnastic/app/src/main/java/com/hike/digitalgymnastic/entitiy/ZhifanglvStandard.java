package com.hike.digitalgymnastic.entitiy;

import java.io.Serializable;

//ZhifanglvStandard(身体脂肪率标准):
public class ZhifanglvStandard implements Serializable{
	private double standardFloor;//标准身体脂肪率下限(%)	
	private double mildFatFloor;//轻度肥胖身体脂肪率下限(%)	
	private double fatFloor;//肥胖身体脂肪率下限(%)
	public double getStandardFloor() {
		return standardFloor;
	}
	public void setStandardFloor(double standardFloor) {
		this.standardFloor = standardFloor;
	}
	public double getMildFatFloor() {
		return mildFatFloor;
	}
	public void setMildFatFloor(double mildFatFloor) {
		this.mildFatFloor = mildFatFloor;
	}
	public double getFatFloor() {
		return fatFloor;
	}
	public void setFatFloor(double fatFloor) {
		this.fatFloor = fatFloor;
	}
	
}
