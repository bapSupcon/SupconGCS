/**
 * Copyright (C) 2011 ZHEJIANG SUPCON TECHNOLOGY CO.,LTD. 
 * All rights reserved.
 */
package com.supcon.gcs.message.accept;

/**
 * 接收消息基类（普通用户 -> 公众帐号）
 * 
 * @author Administrator
 * @version 1.0
 */
public class BaseMessage {
	/**
	 * 开发者微信号
	 */
	private String ToUserName;
	/**
	 * 发送方帐号（openId）
	 */
	private String FromUserName;
	/**
	 * 创建消息时间  时间戳
	 */
	private Long CreateTime;
	/**
	 * 消息类型（text/image/location/link）
	 */
	private String MsgType;
	/**
	 * 消息id，64位整型
	 */
	private Long MsgId;
	
	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public Long getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(Long createTime) {
		CreateTime = createTime;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
	public Long getMsgId() {
		return MsgId;
	}
	public void setMsgId(Long msgId) {
		MsgId = msgId;
	}
}
