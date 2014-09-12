/**
 * Copyright (C) 2011 ZHEJIANG SUPCON TECHNOLOGY CO.,LTD. 
 * All rights reserved.
 */
package com.supcon.gcs.actions;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;
import com.supcon.gcs.entities.User;
import com.supcon.gcs.service.UserService;
import com.supcon.gcs.service.impl.UserServiceImpl;

/**
 * 
 * 用户操作Action
 * 
 * @author Administrator
 * @version $Id$
 */
public class UserAction extends ActionSupport implements ServletRequestAware {

	public static Logger logger = LoggerFactory.getLogger(UserAction.class);
	private static final long serialVersionUID = -5035563678580271995L;
	private HttpServletRequest request;
	private User user;
	private String userName;
	private String password;
	private String openId;
	private UserService userService = new UserServiceImpl();
	private Map<String, Object> responseMap = new HashMap<String, Object>();

	@Action(value = "binding", results = { @Result( location = "/result.jsp") })
	public String bindingUser() {
		if (null != user) {
			//user.setOpenId(openId);
			try {
				userService.bindingUser(user);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				responseMap.put("dealResult", "绑定失败");
				return ERROR;
			}
			responseMap.put("dealResult", "绑定成功");
		}
		return SUCCESS;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Map<String, Object> getResponseMap() {
		return responseMap;
	}

	public void setResponseMap(Map<String, Object> responseMap) {
		this.responseMap = responseMap;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

}
