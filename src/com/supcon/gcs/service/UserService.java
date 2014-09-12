/**
 * Copyright (C) 2011 ZHEJIANG SUPCON TECHNOLOGY CO.,LTD. 
 * All rights reserved.
 */
package com.supcon.gcs.service;

import com.supcon.gcs.entities.User;

/**
 * 
 * 用户操作接口
 * 
 * @author Administrator
 * @version 1.0
 */
public interface UserService {
	/**
	 * 绑定用户信息
	 * 
	 * @param user
	 */
	void bindingUser(User user);

	/**
	 * 解绑用户
	 * 
	 * @param openId
	 */
	void unbindingUser(String openId);

	/**
	 * 判断用户是否绑定
	 * 
	 * @param openId
	 * @return
	 */
	boolean checkBinding(String openId);
}
