/**
 * Copyright (C) 2011 ZHEJIANG SUPCON TECHNOLOGY CO.,LTD. 
 * All rights reserved.
 */
package com.supcon.gcs.actions;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;
import com.sun.org.apache.bcel.internal.generic.GETSTATIC;

/**
 * 
 * 
 * @author Administrator
 * @version $Id$
 */
public class LoginAction extends ActionSupport implements ServletRequestAware{
	private static final long serialVersionUID = 649853266924341212L;
	public static Logger logger = LoggerFactory.getLogger(LoginAction.class);
	private HttpServletRequest request;
	private String openId;

	@Action(value="login", results = { @Result(name = SUCCESS, location = "/login.jsp") })
	public String login(){
		String filePath = request.getServletContext().getRealPath("/");
		logger.info(filePath);
		return SUCCESS;
	}
	
	@Action(value="index", results = { @Result(name = SUCCESS, location = "/index.jsp") })
	public String index(){
		return SUCCESS;
	}
	
	
	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
}
