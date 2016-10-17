package com.hike.digitalgymnastic.entitiy;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by computer on 2015/6/15.
 */
public class HomeSportData implements Serializable{

	/**
	 * dailyList :
	 * [{"totalTime":0,"sportType":4,"sportName":"动感单车","totalCalories"
	 * :0},{"totalTime"
	 * :0,"sportType":7,"sportName":"椭圆机","totalCalories":0},{"totalTime"
	 * :0,"sportType"
	 * :11,"sportName":"坐式背肌","totalCalories":0},{"totalTime":0,"sportType"
	 * :12,"sportName":"飞鸟","totalCalories":0}] goalCalories : 500 advice :
	 * 你今天还差500kcal达到目标 totalTime : 345 totalCalories : 0 onceList :
	 * [{"sportType":4,"sportName":"动感单车","beginTime":"09:55","endTime":"10:00",
	 * "burnCalories"
	 * :0},{"sportType":7,"sportName":"椭圆机","beginTime":"10:59","endTime"
	 * :"11:00"
	 * ,"burnCalories":0},{"sportType":11,"sportName":"坐式背肌","beginTime":
	 * "13:59",
	 * "endTime":"14:00","burnCalories":0},{"sportType":12,"sportName":"飞鸟"
	 * ,"beginTime":"14:59","endTime":"15:00","burnCalories":0}]
	 */
	private java.util.List<DailySportData> dailyList;
	private String goalCalories;
	private String advice;
	private String totalTime;
	private String totalCalories;
	private java.util.List<OnceSportData> onceList;

	private String weight;
	private String weightDate;
	private String totalSleep;

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getWeightDate() {
		return weightDate;
	}

	public void setWeightDate(String weightDate) {
		this.weightDate = weightDate;
	}

	public String getTotalSleep() {
		return totalSleep;
	}

	public void setTotalSleep(String totalSleep) {
		this.totalSleep = totalSleep;
	}

	public String getSleepDate() {
		return sleepDate;
	}

	public void setSleepDate(String sleepDate) {
		this.sleepDate = sleepDate;
	}

	private String sleepDate;

	public java.util.List<DailySportData> getDailyList() {
		
		Collections.sort(dailyList, new Comparator<DailySportData>() {

			@Override
			public int compare(DailySportData lhs, DailySportData rhs) {
				if(lhs.getTotalCalories()<rhs.getTotalCalories())
					return 1;
				else if(lhs.getTotalCalories()>rhs.getTotalCalories())
					return -1;
				else
					return 0;
			}
		});
		return dailyList;
	}

	public void setDailyList(java.util.List<DailySportData> dailyList) {
		this.dailyList = dailyList;
	}

	public java.util.List<OnceSportData> getOnceList() {
		return onceList;
	}

	public void setOnceList(java.util.List<OnceSportData> onceList) {
		this.onceList = onceList;
	}


	public void setAdvice(String advice) {
		this.advice = advice;
	}



	public void setGoalCalories(String goalCalories) {
		this.goalCalories = goalCalories;
	}

	public String getGoalCalories() {
		return goalCalories;
	}

	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}


	public String getTotalTime() {
		return totalTime;
	}

	public void setTotalCalories(String totalCalories) {
		this.totalCalories = totalCalories;
	}

	public String getTotalCalories() {
		return totalCalories;
	}

	public String getAdvice() {
		return advice;
	}

	public class DailyListEntity {
		/**
		 * totalTime : 0 sportType : 4 sportName : 动感单车 totalCalories : 0
		 */
		private String totalTime;
		private String sportType;
		private String sportName;
		private String totalCalories;

		public void setTotalTime(String totalTime) {
			this.totalTime = totalTime;
		}

		public void setSportType(String sportType) {
			this.sportType = sportType;
		}

		public void setSportName(String sportName) {
			this.sportName = sportName;
		}

		public void setTotalCalories(String totalCalories) {
			this.totalCalories = totalCalories;
		}

		public String getTotalTime() {
			return totalTime;
		}

		public String getSportType() {
			return sportType;
		}

		public String getSportName() {
			return sportName;
		}

		public String getTotalCalories() {
			return totalCalories;
		}
	}

	public class OnceListEntity {
		/**
		 * sportType : 4 sportName : 动感单车 beginTime : 09:55 endTime : 10:00
		 * burnCalories : 0
		 */
		private String sportType;
		private String sportName;
		private String beginTime;
		private String endTime;
		private String burnCalories;

		public void setSportType(String sportType) {
			this.sportType = sportType;
		}

		public void setSportName(String sportName) {
			this.sportName = sportName;
		}

		public void setBeginTime(String beginTime) {
			this.beginTime = beginTime;
		}

		public void setEndTime(String endTime) {
			this.endTime = endTime;
		}

		public void setBurnCalories(String burnCalories) {
			this.burnCalories = burnCalories;
		}

		public String getSportType() {
			return sportType;
		}

		public String getSportName() {
			return sportName;
		}

		public String getBeginTime() {
			return beginTime;
		}

		public String getEndTime() {
			return endTime;
		}

		public String getBurnCalories() {
			return burnCalories;
		}
	}
}
