package com.hike.digitalgymnastic.entitiy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//获取日期段身体水分数据
public class PeriodShuifenData {
	
	ShuifenStandard standard;//身体水分标准	
	Map<String, Double> dataMap	;//	身体水分数据映射	
	String minDate;//		最小值日期	
	String maxDate;//		最大值日期	
	double change;//		身体水分变化(%)	
	short grade;//	身体水分评级(-1:偏低;0:标准;1:偏高)	
	String advice;//		系统建议
	public ShuifenStandard getStandard() {
		return standard;
	}
	public void setStandard(ShuifenStandard standard) {
		this.standard = standard;
	}
	public Map<String, Double> getDataMap() {
		  if(dataMap!=null){
	           Map<String,Double> myMap = new LinkedHashMap<String, Double>();
	           
	           List<String> keyList = new ArrayList<String>();
	           Iterator<String> it =dataMap.keySet().iterator();
	           while(it.hasNext()){
	               keyList.add(it.next());
	           }
	           Collections.sort(keyList,new DataComparator());
	          for(String key:keyList){
	               myMap.put(key, dataMap.get(key));
	           }
	          dataMap=  myMap;
		  }
		
		
		return dataMap;
	}
	public void setDataMap(Map<String, Double> dataMap) {
		this.dataMap = dataMap;
	}
	public String getMinDate() {
		return minDate;
	}
	public void setMinDate(String minDate) {
		this.minDate = minDate;
	}
	public String getMaxDate() {
		return maxDate;
	}
	public void setMaxDate(String maxDate) {
		this.maxDate = maxDate;
	}
	public double getChange() {
		return change;
	}
	public void setChange(double change) {
		this.change = change;
	}
	public short getGrade() {
		return grade;
	}
	public void setGrade(short grade) {
		this.grade = grade;
	}
	public String getAdvice() {
		return advice;
	}
	public void setAdvice(String advice) {
		this.advice = advice;
	}
	
}
