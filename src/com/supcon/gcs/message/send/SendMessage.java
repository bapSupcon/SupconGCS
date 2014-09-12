/**
 * Copyright (C) 2011 ZHEJIANG SUPCON TECHNOLOGY CO.,LTD. 
 * All rights reserved.
 */
package com.supcon.gcs.message.send;

/**
 * 发送消息基类（公众帐号 -> 普通用户）
 * 
 * @author Administrator
 * @version 1.0
 */
public class SendMessage extends BaseMessage {
	/**
	 * 消息内容
	 */
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
}
