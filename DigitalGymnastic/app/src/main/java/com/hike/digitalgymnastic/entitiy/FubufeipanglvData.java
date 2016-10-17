package com.hike.digitalgymnastic.entitiy;

import java.io.Serializable;

//FubufeipanglvData(腹部肥胖率数据):
public class FubufeipanglvData implements Serializable{
	private FubufeipanglvStandard standard;//腹部肥胖率标准
	private short grade;//腹部肥胖率评级(-1:偏低;0:标准;1:偏高)	
	private double value;//腹部肥胖率值(%)
	public FubufeipanglvStandard getStandard() {
		return standard;
	}
	public void setStandard(FubufeipanglvStandard standard) {
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
