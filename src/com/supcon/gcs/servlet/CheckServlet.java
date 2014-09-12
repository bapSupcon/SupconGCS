/**
 * Copyright (C) 2011 ZHEJIANG SUPCON TECHNOLOGY CO.,LTD. 
 * All rights reserved.
 */
package com.supcon.gcs.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.supcon.gcs.actions.LoginAction;
import com.supcon.gcs.message.accept.AcceptMessage;
import com.supcon.gcs.message.send.SendMessage;
import com.supcon.gcs.service.UserService;
import com.supcon.gcs.service.impl.UserServiceImpl;
import com.supcon.gcs.utils.Constant;
import com.supcon.gcs.utils.MessageUtil;
import com.supcon.gcs.utils.SignUtil;
import com.supcon.gcs.utils.XMLUtil;

/**
 * 
 * 
 * @author Administrator
 * @version 1.0
 */
public class CheckServlet extends HttpServlet {

	public static Logger logger = LoggerFactory.getLogger(CheckServlet.class);
	private static final long serialVersionUID = -6668087886577968483L;
	private UserService userService = new UserServiceImpl();

	/**
	 * 确认请求来自微信服务器
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 微信加密签名
		String signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echostr = request.getParameter("echostr");

		PrintWriter out = response.getWriter();

		// 校验signature，成功则返回echostr
		if (SignUtil.checkSignature(signature, timestamp, nonce)) {
			out.print(echostr);
			logger.info("判断消息是否来自微信:验证通过");
		}
		out.close();
		out = null;
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();
		InputStream stream = request.getInputStream();
		try {
			AcceptMessage acceptMessage = (AcceptMessage) XMLUtil.parseObject(stream, AcceptMessage.class);
			logger.info("接收消息并转换成功");
			if (null != acceptMessage) {
				String MsgType = acceptMessage.getMsgType();
				String ToUserName = acceptMessage.getToUserName();// 开发者微信号
				String FromUserName = acceptMessage.getFromUserName();// 发送方OpenId
				Long CreateTime = acceptMessage.getCreateTime();
				String EventKey = acceptMessage.getEventKey();
				boolean hasBinded = userService.checkBinding(FromUserName);
				SendMessage sendMessage = new SendMessage();
				String content = "";
				if (hasBinded) {
					if (MsgType.equals(MessageUtil.ACCEPT_MESSAGE_TYPE_EVENT)) {// 事件推送
						String event = acceptMessage.getEvent();
						if (event.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {// 关注事件
							content = "感谢关注SupconGCS";
						} else if (event.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {// 取消关注事件
							content = "感谢使用SupconGCS";
						} else if (event.equals(MessageUtil.EVENT_TYPE_CLICK)) {// 自定义按钮点击事件
							if (null != EventKey) {
								if (EventKey.equals("V1")) {// 公司介绍
									content = "<a href=\"" + Constant.DEMAIN_NAME + "/index.action?openId=" + FromUserName + "\">点击查看公司信息</a>";
								} else if (EventKey.equals("V2")) {// 控制
									content = "<a href=\"" + Constant.DEMAIN_NAME + "/control.action?openId=" + FromUserName + "\">点击控制设备</a>";
								} else if (EventKey.equals("V3")) {// 诊断
									content = "<a href=\"" + Constant.DEMAIN_NAME + "/diagnoseInfo.action?openId=" + FromUserName + "\">点击进行诊断</a>";
								} else {
									content = "openId为:" + FromUserName;
								}
							}
						}
					} else if (MsgType.equals(MessageUtil.ACCEPT_MESSAGE_TYPE_TEXT)) {
						String acceptContent = acceptMessage.getContent();
						if (null != acceptContent) {
							if (acceptContent.equalsIgnoreCase("BD")) {
								content = "您已绑定用户帐号，不能重复绑定";
							} else if (acceptContent.equalsIgnoreCase("INTRO")) {
								content = "<a href=\"" + Constant.DEMAIN_NAME + "/index.action?openId=" + FromUserName + "\">点击查看公司信息</a>";
							} else if (acceptContent.equalsIgnoreCase("CONTROL")) {
								content = "<a href=\"" + Constant.DEMAIN_NAME + "/control.action?openId=" + FromUserName + "\">点击控制设备</a>";
							} else if (acceptContent.equalsIgnoreCase("DG")) {
								content = "<a href=\"" + Constant.DEMAIN_NAME + "/diagnoseInfo.action?openId=" + FromUserName + "\">点击进行诊断</a>";
							}
						}
					}
				} else {
					if (MsgType.equals(MessageUtil.ACCEPT_MESSAGE_TYPE_EVENT)) {// 事件推送
						String event = acceptMessage.getEvent();
						if (event.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {// 关注事件
							content = "感谢关注SupconGCS,请先<a href=\"" + Constant.DEMAIN_NAME + "/login.action?openId=" + FromUserName + "\">绑定帐号</a>";
						} else if (!event.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
							content = "您还未绑定帐号，请先<a href=\"" + Constant.DEMAIN_NAME + "/login.action?openId=" + FromUserName + "\">绑定帐号</a>";
						}
					} else {
						content = "您还未绑定帐号，请先<a href=\"" + Constant.DEMAIN_NAME + "/SupconGCS/login.action?openId=" + FromUserName + "\">绑定帐号</a>";
					}
				}
				if (null != content && content.length() > 0) {
					sendMessage.setContent(content);
					sendMessage.setToUserName(FromUserName);
					sendMessage.setFromUserName(ToUserName);
					sendMessage.setCreateTime(new Date().getTime());
					sendMessage.setMsgType(MessageUtil.SEND_MESSAGE_TYPE_TEXT);
					String xml = XMLUtil.format(sendMessage);
					logger.info("发送信息xml:" + xml);
					writer.print(xml);
				}

			}
		} catch (Exception e) {
			logger.error("接收消息异常...");
			logger.error(e.getMessage(), e);
		} finally {
			writer.close();
		}
	}

	@Override
	public void destroy() {

	}
}
