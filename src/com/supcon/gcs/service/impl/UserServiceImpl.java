/**
 * Copyright (C) 2011 ZHEJIANG SUPCON TECHNOLOGY CO.,LTD. 
 * All rights reserved.
 */
package com.supcon.gcs.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.supcon.gcs.entities.User;
import com.supcon.gcs.pool.PoolDataSource;
import com.supcon.gcs.service.UserService;

/**
 * 
 * 
 * @author Administrator
 * @version $Id$
 */
public class UserServiceImpl implements UserService {
	public static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	private PoolDataSource dataSource;

	@Override
	public void bindingUser(User user) {
		dataSource = PoolDataSource.getInstance();
		Connection connection = dataSource.getConnection();
		String sql = "insert into base_user (username,password,openId) values (?,?,?)";
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, user.getUserName());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getOpenId());
			ps.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			try {
				if (null != connection && !connection.isClosed()) {
					connection.rollback();
				}
			} catch (SQLException e1) {
				logger.error(e1.getMessage(), e1);
			}
		} finally {
			try {
				if (null != ps) {
					ps.close();
				}
				if (null != connection && !connection.isClosed()) {
					connection.close();
				}
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			}
		}

	}

	@Override
	public void unbindingUser(String openId) {
		dataSource = PoolDataSource.getInstance();
		Connection connection = dataSource.getConnection();
		String sql = "delete from base_user where openId=?";
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, openId);
			ps.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			try {
				if (null != connection && !connection.isClosed()) {
					connection.rollback();
				}
			} catch (SQLException e1) {
				logger.error(e1.getMessage(), e1);
			}
		} finally {
			try {
				if (null != ps) {
					ps.close();
				}
				if (null != connection && !connection.isClosed()) {
					connection.close();
				}
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	@Override
	public boolean checkBinding(String openId) {
		dataSource = PoolDataSource.getInstance();
		Connection connection = dataSource.getConnection();
		String sql = "select count(*) from base_user where openId=?";
		PreparedStatement ps = null;
		int count = 0;
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, openId);
			ResultSet rs = ps.executeQuery();
			if(null != rs){
				rs.first();
				count = rs.getInt(0);
			}
			rs.close();
			if(count > 0){
				return true;
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if (null != ps) {
					ps.close();
				}
				if (null != connection && !connection.isClosed()) {
					connection.close();
				}
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return false;
	}

}
