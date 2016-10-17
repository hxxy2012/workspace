package com.hike.digitalgymnastic.entitiy;

import java.io.Serializable;

/**
 *
 *
 */
public class Message implements Serializable {
	long messageId;// 消息标识
	long diaryId;// 日记标识
	long sourceId;// 来源客户标识
	String sourceName;// 来源客户名称
	String sourceAvatar;// 来源客户头像
	short relation;// 关注关系(0:未关注;1:已关注;2:相互关注)
	short type;// 消息类型(1:系统公告;2.系统通知;3:关注通知;4:评论通知;5:称赞通知;6:回复通知)
	short status;// 消息状态(1:未读;2:已读;3:删除)
	String title;// 消息标题
	String content;// 消息内容
	String action;// 消息动作
	String createdTime;// 创建时间(YYYY-MM-DD HH:MM:SS)
	String expiredTime;// 过期时间(YYYY-MM-DD HH:MM:SS)

	public long getMessageId() {
		return messageId;
	}

	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}

	public long getDiaryId() {
		return diaryId;
	}

	public void setDiaryId(long diaryId) {
		this.diaryId = diaryId;
	}

	public long getSourceId() {
		return sourceId;
	}

	public void setSourceId(long sourceId) {
		this.sourceId = sourceId;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getSourceAvatar() {
		return sourceAvatar;
	}

	public void setSourceAvatar(String sourceAvatar) {
		this.sourceAvatar = sourceAvatar;
	}

	public short getRelation() {
		return relation;
	}

	public void setRelation(short relation) {
		this.relation = relation;
	}

	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
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

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(String expiredTime) {
		this.expiredTime = expiredTime;
	}

}
