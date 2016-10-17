package com.hike.digitalgymnastic.entitiy;

import java.io.Serializable;

//DanbaizhiStandard(蛋白质比重标准):
public class DanbaizhiStandard implements Serializable{
	
	private double standardFloor;//标准蛋白质下限(%)
	private double  standardCeil;//标准蛋白质上限(%)
	public double getStandardFloor() {
		return standardFloor;
	}
	public void setStandardFloor(double standardFloor) {
		this.standardFloor = standardFloor;
	}
	public double getStandardCeil() {
		return standardCeil;
	}
	public void setStandardCeil(double standardCeil) {
		this.standardCeil = standardCeil;
	}
	
}
