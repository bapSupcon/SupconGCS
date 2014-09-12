/**
 * Copyright (C) 2011 ZHEJIANG SUPCON TECHNOLOGY CO.,LTD. 
 * All rights reserved.
 */
package com.supcon.gcs.pool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.derby.jdbc.ClientDataSource;
import org.apache.derby.jdbc.EmbeddedDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.supcon.gcs.jdbc.BAPDataSource;

/**
 * 
 * 
 * @author Administrator
 * @version 1.0
 */
public class PoolDataSource {
	public static Logger logger = LoggerFactory.getLogger(PoolDataSource.class);
	private static final String DRIVER_CLASS = "org.h2.Driver";
	private static final String URL = "jdbc:h2:DB/gcsDB;";
	private static final String USAER = "sa";
	private static final String PASSWORD = "1";
	
	private static PoolDataSource instance;
	private BAPDataSource bapDataSource;
	private static BasicDataSource basicDatabase = null;
	private PoolDataSource() {
		basicDatabase = new BasicDataSource();
		basicDatabase.setDriverClassName(DRIVER_CLASS);
		basicDatabase.setUrl(URL);
		basicDatabase.setUsername(USAER);
		basicDatabase.setPassword(PASSWORD);
		basicDatabase.setMaxActive(10);
		basicDatabase.setMaxIdle(5);
		bapDataSource = new BAPDataSource(basicDatabase);
	}

	public static PoolDataSource getInstance() {
		if (null == instance) {
			synchronized (PoolDataSource.class) {
				if (null == instance) {
					try {
						instance = new PoolDataSource();
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
		}
		return instance;
	}

	public Connection getConnection() {
		Connection connection = null;
		try {
			connection = bapDataSource.getConnection();
			connection.setAutoCommit(false);
			logger.info("connect to h2 database success");
		} catch (SQLException e) {
			logger.error("connect to h2 database failed");
			logger.error(e.getMessage(), e);
		}
		return connection;
	}

	public ResultSet getResultSet(Connection connection, String sql) throws SQLException {
		PreparedStatement preparedStatement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		return preparedStatement.executeQuery();
	}
	
	public static void close() throws SQLException{
		basicDatabase.close();
	}
}
