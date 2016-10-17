package com.hike.digitalgymnastic.entitiy;

import java.io.Serializable;

//FubufeipanglvStandard(腹部肥胖率标准):
public class FubufeipanglvStandard implements Serializable{
	
	private double standardFloor;//标准腹部肥胖率下限(%)	
	private double  standardCeil;//标准腹部肥胖率上限(%)
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
