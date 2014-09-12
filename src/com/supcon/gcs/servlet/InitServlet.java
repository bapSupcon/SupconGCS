/**
 * Copyright (C) 2011 ZHEJIANG SUPCON TECHNOLOGY CO.,LTD. 
 * All rights reserved.
 */
package com.supcon.gcs.servlet;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.supcon.gcs.entities.Device;
import com.supcon.gcs.pool.PoolDataSource;
import com.supcon.gcs.utils.XMLUtil;

/**
 * 
 * 
 * @author Administrator
 * @version 1.0
 */
public class InitServlet extends HttpServlet {
	public static Logger logger = LoggerFactory.getLogger(InitServlet.class);
	private static final long serialVersionUID = -1352764096430574289L;
	private static final String HOST = "localhost";
	private static final Integer PORT = 1527;

	@Override
	public void init() throws ServletException {
		PoolDataSource dataSource = PoolDataSource.getInstance();
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			String sql = "create table base_user (username varchar(200) UNIQUE, password varchar(200),openId varchar(1000))";
			Statement statement = connection.createStatement();
			statement.executeUpdate(sql);
			connection.commit();
			statement.close();
		} catch (Exception e) {
			try {
				if (null != connection && !connection.isClosed()) {
					connection.rollback();
					connection.close();
				}
			} catch (SQLException e1) {
				logger.error(e1.getMessage(), e1);
			}
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}

}
