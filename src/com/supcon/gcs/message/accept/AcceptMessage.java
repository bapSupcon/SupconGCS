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
public class AcceptMessage extends BaseMessage {
	/**
	 * 事件类型 CLICK /VIEW /SCAN
	 */
	private String Event;
	/**
	 * 事件KEY值，与自定义菜单接口中KEY值对应
	 */
	private String EventKey;

	/**
	 * 图片链接
	 */
	private String PicUrl;
	
	/**
	 * 消息标题
	 */
	private String Title;
	/**
	 * 消息描述
	 */
	private String Description;
	/**
	 * 链接地址
	 */
	private String Url;
	

	/**
	 * 消息内容
	 */
	private String Content;

	
	public String getEvent() {
		return Event;
	}

	public void setEvent(String event) {
		Event = event;
	}

	public String getEventKey() {
		return EventKey;
	}

	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
}
