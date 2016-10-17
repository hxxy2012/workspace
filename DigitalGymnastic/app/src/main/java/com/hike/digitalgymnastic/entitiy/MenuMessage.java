package com.hike.digitalgymnastic.entitiy;

public class MenuMessage {
	
	Long customerId; // 客户标识
	String customerName; // 客户名称
	Long noticeId; // 公告标识
	Short type; // 公告类型
	Short status; // 公告状态
	String title; // 公告标题
	String content; // 公告内容
	String action; // 公告动作
	Long sendedCount; // 发送数量
	Long readedCount; // 阅读数量
	Short readedStatus; // 阅读状态(0:未读;1:已读)
	String description; // 公告描述
	String createdTime; // 创建时间
	String readedTime; // 阅读时间

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Long getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(Long noticeId) {
		this.noticeId = noticeId;
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Long getSendedCount() {
		return sendedCount;
	}

	public void setSendedCount(Long sendedCount) {
		this.sendedCount = sendedCount;
	}

	public Long getReadedCount() {
		return readedCount;
	}

	public void setReadedCount(Long readedCount) {
		this.readedCount = readedCount;
	}

	public Short getReadedStatus() {
		return readedStatus;
	}

	public void setReadedStatus(Short readedStatus) {
		this.readedStatus = readedStatus;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getReadedTime() {
		return readedTime;
	}

	public void setReadedTime(String readedTime) {
		this.readedTime = readedTime;
	}

}
