/**
 * Copyright (C) 2011 ZHEJIANG SUPCON TECHNOLOGY CO.,LTD. 
 * All rights reserved.
 */
package com.supcon.gcs.entities;

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
import com.supcon.gcs.servlet.CheckServlet;
import com.supcon.gcs.utils.Constant;

/**
 * 
 * 
 * @author Administrator
 * @version $Id$
 */
public class TokenThread implements Runnable {

	public static Logger logger = LoggerFactory.getLogger(TokenThread.class);

	@Override
	public void run() {

		while (true) {
			try {
				GetToken();
				if (null != Constant.ACCESS_TOKEN && Constant.ACCESS_TOKEN.length() > 0) {
					logger.info("ACCESS_TOKEN 获取成功，有效时长{}s token:{}", Constant.EXPIRES_IN, Constant.ACCESS_TOKEN);
					long sleepTime = Constant.EXPIRES_IN > 200 ? (Constant.EXPIRES_IN - 200 ) : (Constant.EXPIRES_IN - 50 );
					Thread.sleep(sleepTime * 1000);
				} else {
					Thread.sleep(60 * 1000);
				}
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
		}

	}
	
	
	
	public static void GetToken() {
		String url = Constant.GET_TOKEN_URL + "&appid=" + Constant.APPID + "&secret=" + Constant.APP_SECRET;
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		JsonParser jsonParser = new JsonParser();
		String access_token = null;
		Integer expires_in = null;
		try {
			HttpResponse response = client.execute(httpGet);
			String responseContent = null; // 响应内容
			HttpEntity entity = response.getEntity();
			responseContent = EntityUtils.toString(entity, "UTF-8");
			JsonObject json = jsonParser.parse(responseContent).getAsJsonObject();
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				if (json.get("errcode") != null) {

				} else {
					access_token = json.get("access_token").getAsString();
					expires_in = json.get("expires_in").getAsInt();
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			client.getConnectionManager().shutdown();
		}
		if (null != access_token) {
			Constant.ACCESS_TOKEN = access_token;
			Constant.EXPIRES_IN = expires_in;
		}
	}
}
