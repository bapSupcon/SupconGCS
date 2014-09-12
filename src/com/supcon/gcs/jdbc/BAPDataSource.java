/**
 * Copyright (C) 2011 ZHEJIANG SUPCON TECHNOLOGY CO.,LTD. 
 * All rights reserved.
 */
package com.supcon.gcs.jdbc;

import java.io.PrintWriter;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;


/**
 * 
 * 
 * @author yaowei
 * @version $Id$
 */
public class BAPDataSource implements DataSource, Serializable{
	
	private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger("bap.sql.logging");
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DataSource wrapper;
	
	public BAPDataSource() {
	}
	
	public BAPDataSource(DataSource dataSource) {
		setWrapperDataSource(dataSource);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getWrapper().getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(getWrapper()));
	}
	
	public DataSource getWrapper(){
		return this.wrapper;
	}
	
	public void setWrapperDataSource(DataSource dataSource) {
		this.wrapper = dataSource;
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return getWrapper().getLogWriter();
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		getWrapper().setLogWriter(out);
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		getWrapper().setLoginTimeout(seconds);
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return getWrapper().getLoginTimeout();
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return getWrapper().unwrap(iface);
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return getWrapper().isWrapperFor(iface);
	}

	@Override
	public Connection getConnection() throws SQLException {
		BAPConnection connection = new BAPConnection(getWrapper().getConnection());
		LOGGER.trace("connection {} created.", connection.toString());
		return connection;
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		BAPConnection connection = new BAPConnection(getWrapper().getConnection(username, password));
		LOGGER.trace("connection {} created.", connection.toString());
		return connection;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return getWrapper().getParentLogger();
	}

}
