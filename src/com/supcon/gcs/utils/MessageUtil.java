/**
 * Copyright (C) 2011 ZHEJIANG SUPCON TECHNOLOGY CO.,LTD. 
 * All rights reserved.
 */
package com.supcon.gcs.utils;

import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.supcon.gcs.message.send.SendMessage;
import com.supcon.gcs.service.impl.UserServiceImpl;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * 
 * 
 * @author Administrator
 * @version $Id$
 */
public class MessageUtil {
	public static Logger logger = LoggerFactory.getLogger(MessageUtil.class);
	/**
	 * 返回消息类型：文本
	 */
	public static final String SEND_MESSAGE_TYPE_TEXT = "text";

	/**
	 * 返回消息类型：音乐
	 */
	public static final String SEND_MESSAGE_TYPE_MUSIC = "music";

	/**
	 * 返回消息类型：图文
	 */
	public static final String SEND_MESSAGE_TYPE_NEWS = "news";

	/**
	 * 请求消息类型：文本
	 */
	public static final String ACCEPT_MESSAGE_TYPE_TEXT = "text";

	/**
	 * 请求消息类型：图片
	 */
	public static final String ACCEPT_MESSAGE_TYPE_IMAGE = "image";

	/**
	 * 请求消息类型：链接
	 */
	public static final String ACCEPT_MESSAGE_TYPE_LINK = "link";

	/**
	 * 请求消息类型：地理位置
	 */
	public static final String ACCEPT_MESSAGE_TYPE_LOCATION = "location";

	/**
	 * 请求消息类型：音频
	 */
	public static final String ACCEPT_MESSAGE_TYPE_VOICE = "voice";

	/**
	 * 请求消息类型：推送
	 */
	public static final String ACCEPT_MESSAGE_TYPE_EVENT = "event";

	/**
	 * 事件类型：subscribe(订阅)
	 */
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

	/**
	 * 事件类型：unsubscribe(取消订阅)
	 */
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

	/**
	 * 事件类型：CLICK(自定义菜单点击事件)
	 */
	public static final String EVENT_TYPE_CLICK = "CLICK";
	
	
	/**
	 * 解析微信发来的请求（XML）
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
		// 将解析结果存储在HashMap中
		Map<String, String> map = new HashMap<String, String>();

		// 从request中取得输入流
		InputStream inputStream = request.getInputStream();
		// 读取输入流
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		// 得到xml根元素
		Element root = document.getRootElement();
		// 得到根元素的所有子节点
		List<Element> elementList = root.elements();

		// 遍历所有子节点
		for (Element e : elementList)
			map.put(e.getName(), e.getText());

		// 释放资源
		inputStream.close();
		inputStream = null;

		return map;
	}
	
	/**
	 * 文本消息对象转换成xml
	 * 
	 * @param textMessage 文本消息对象
	 * @return xml
	 * @throws Exception 
	 */
	public static String sendMessageToXml(SendMessage sendMessage) throws Exception {
		return XMLUtil.format(sendMessage);
	}
	
	
}
