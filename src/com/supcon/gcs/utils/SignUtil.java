/**
 * Copyright (C) 2011 ZHEJIANG SUPCON TECHNOLOGY CO.,LTD. 
 * All rights reserved.
 */
package com.supcon.gcs.utils;

import java.security.MessageDigest;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.supcon.gcs.servlet.CheckServlet;

/**
 * 
 * 
 * @author Administrator
 * @version 1.0
 */
public class SignUtil {
	public static Logger logger = LoggerFactory.getLogger(CheckServlet.class);

	public static boolean checkSignature(String signature, String timestamp, String nonce) {
		if (null != signature && null != timestamp && null != nonce) {
			logger.info("判断消息是否来自微信:{signature:'" + signature + "',timestamp:'" + timestamp + "',nonce:'" + nonce + "'}");

			String[] arr = new String[] { Constant.TOKEN, timestamp, nonce };
			// 参数排序
			Arrays.sort(arr);
			StringBuilder content = new StringBuilder();
			for (int i = 0; i < arr.length; i++) {
				content.append(arr[i]);
			}
			MessageDigest md = null;
			String tmpStr = null;
			try {
				md = MessageDigest.getInstance("SHA-1");
				// 加密
				byte[] digest = md.digest(content.toString().getBytes());
				tmpStr = byteToString(digest);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}

			return (null != tmpStr) ? (tmpStr.equals(signature.toUpperCase())) : false;
		} else {
			logger.error("判断消息是否来自微信:  验证信息为空");
		}
		return false;
	}

	/**
	 * 将字节数组转换为十六进制字符串
	 * 
	 * @param bytes
	 * @return
	 */
	public static String byteToString(byte[] bytes) {
		String strDigit = "";
		for (int i = 0; i < bytes.length; i++) {
			strDigit += byteToHex(bytes[i]);
		}
		return strDigit;
	}

	/**
	 * 将字节转换为十六进制字符串
	 * 
	 * @param mBtye
	 * @return
	 */
	public static String byteToHex(byte mBtye) {
		char[] digit = { '0','1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = digit[(mBtye >>> 4) & 0X0F];
		tempArr[1] = digit[mBtye & 0X0F];

		return new String(tempArr);
	}
	
//	
//	public static void main(String[] args) {
//		String signature = "0989df8eef4779844a1af1d0bc07c19a1b79e3d6";
//		String timestamp = "1410491804";
//		String nonce = "1823080663";
//		String token = "supcongcs";
//		System.out.println(checkSignature(signature, timestamp, nonce));
//	}
}
