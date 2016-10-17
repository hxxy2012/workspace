package com.hike.digitalgymnastic.entitiy;

import java.io.Serializable;

/**
 * Created by computer on 2015/6/15.
 */
public class HomeBodyData implements Serializable{
	private String time;// 体测时间
	private int qualified;// 合格项数
	private int unqualified;// 不合格项数
	private int shentinianling;// 身体年龄(岁)
	private double jichudaixie;// 基础代谢率(千卡/天)
	private TizhongData tizhongData;// 体重数据
	private BmiData bmiData;// 体质指数数据
	private ZhifanglvData zhifanglvData;// 身体脂肪率数据
	private NeizangzhifangData neizangzhifangData;// 内脏脂肪指数数据
	private FubufeipanglvData fubufeipanglvData;// 腹部肥胖率数据
	private JiroulvData jiroulvData;// 肌肉率数据
	private DanbaizhiData danbaizhiData;// 蛋白质比重数据
	private ShuifenData shuifenData;// 身体水分数据
	private GuliangData guliangData;// 骨量数据
	private double xibaozhongliang;// 细胞重量(千克)
	private double kuangwuzhi;// 矿物质(千克)
	private String advice;// 系统建议
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getQualified() {
		return qualified;
	}
	public void setQualified(int qualified) {
		this.qualified = qualified;
	}
	public int getUnqualified() {
		return unqualified;
	}
	public void setUnqualified(int unqualified) {
		this.unqualified = unqualified;
	}
	public int getShentinianling() {
		return shentinianling;
	}
	public void setShentinianling(int shentinianling) {
		this.shentinianling = shentinianling;
	}
	public double getJichudaixie() {
		return jichudaixie;
	}
	public void setJichudaixie(double jichudaixie) {
		this.jichudaixie = jichudaixie;
	}
	public TizhongData getTizhongData() {
		return tizhongData;
	}
	public void setTizhongData(TizhongData tizhongData) {
		this.tizhongData = tizhongData;
	}
	public BmiData getBmiData() {
		return bmiData;
	}
	public void setBmiData(BmiData bmiData) {
		this.bmiData = bmiData;
	}
	public ZhifanglvData getZhifanglvData() {
		return zhifanglvData;
	}
	public void setZhifanglvData(ZhifanglvData zhifanglvData) {
		this.zhifanglvData = zhifanglvData;
	}
	public NeizangzhifangData getNeizangzhifangData() {
		return neizangzhifangData;
	}
	public void setNeizangzhifangData(NeizangzhifangData neizangzhifangData) {
		this.neizangzhifangData = neizangzhifangData;
	}
	public FubufeipanglvData getFubufeipanglvData() {
		return fubufeipanglvData;
	}
	public void setFubufeipanglvData(FubufeipanglvData fubufeipanglvData) {
		this.fubufeipanglvData = fubufeipanglvData;
	}
	public JiroulvData getJiroulvData() {
		return jiroulvData;
	}
	public void setJiroulvData(JiroulvData jiroulvData) {
		this.jiroulvData = jiroulvData;
	}
	public DanbaizhiData getDanbaizhiData() {
		return danbaizhiData;
	}
	public void setDanbaizhiData(DanbaizhiData danbaizhiData) {
		this.danbaizhiData = danbaizhiData;
	}
	public ShuifenData getShuifenData() {
		return shuifenData;
	}
	public void setShuifenData(ShuifenData shuifenData) {
		this.shuifenData = shuifenData;
	}
	public GuliangData getGuliangData() {
		return guliangData;
	}
	public void setGuliangData(GuliangData guliangData) {
		this.guliangData = guliangData;
	}
	public double getXibaozhongliang() {
		return xibaozhongliang;
	}
	public void setXibaozhongliang(double xibaozhongliang) {
		this.xibaozhongliang = xibaozhongliang;
	}
	public double getKuangwuzhi() {
		return kuangwuzhi;
	}
	public void setKuangwuzhi(double kuangwuzhi) {
		this.kuangwuzhi = kuangwuzhi;
	}
	public String getAdvice() {
		return advice;
	}
	public void setAdvice(String advice) {
		this.advice = advice;
	}

}
