package com.hike.digitalgymnastic.entitiy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//TizhongData(体重数据):
public class PeriodNeizangzhifangData {
	NeizangzhifangStandard	standard	;//	内脏脂肪指数标准	
	Map<String, Integer> dataMap	;//	内脏脂肪指数数据映射	
	public void setDataMap(Map<String, Integer> dataMap) {
		this.dataMap = dataMap;
	}
	String minDate	;//	最小值日期	
	String maxDate	;//	最大值日期	
	Integer change	;//	内脏脂肪指数变化	
	Short grade		;//内脏脂肪指数评级(-1:皮下型;0:均衡型;1:警戒;2:肥胖;3:非常肥胖)	
	String advice	;//	系统建议
	public NeizangzhifangStandard getStandard() {
		return standard;
	}
	public void setStandard(NeizangzhifangStandard standard) {
		this.standard = standard;
	}
	public Map<String, Integer> getDataMap() {
		 if(dataMap!=null){
	           Map<String,Integer> myMap = new LinkedHashMap<String, Integer>();
	           
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
	public Integer getChange() {
		return change;
	}
	public void setChange(Integer change) {
		this.change = change;
	}
	public Short getGrade() {
		return grade;
	}
	public void setGrade(Short grade) {
		this.grade = grade;
	}
	public String getAdvice() {
		return advice;
	}
	public void setAdvice(String advice) {
		this.advice = advice;
	}
	

	
	
}
