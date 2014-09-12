/**
 * Copyright (C) 2011 ZHEJIANG SUPCON TECHNOLOGY CO.,LTD. 
 * All rights reserved.
 */
package com.supcon.gcs.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.supcon.gcs.actions.UserAction;
import com.supcon.gcs.entities.TokenThread;
import com.supcon.gcs.utils.Constant;

/**
 * 
 * 获取Access_token
 * 
 * @author Administrator
 * @version $Id$
 */
public class TokenServlet extends HttpServlet {
	public static Logger logger = LoggerFactory.getLogger(TokenServlet.class);
	private static final long serialVersionUID = 5964111134318675916L;

	@Override
	public void init() throws ServletException {
		//暂时注释  正式发布后开启
		new Thread(new TokenThread()).start();
	}
}
