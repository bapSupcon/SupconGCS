/**
 * Copyright (C) 2011 ZHEJIANG SUPCON TECHNOLOGY CO.,LTD. 
 * All rights reserved.
 */
package com.supcon.gcs.jdbc;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 
 * @author yaowei
 * @version 1.0
 */
public class BAPConnection implements Connection {
	
	private Connection wrapper;
	
	protected static final AtomicLong counter = new AtomicLong();
	
	private long id;
	
	private static final Logger LOGGER = LoggerFactory.getLogger("bap.sql.logging");
	
	public BAPConnection() {
		id = counter.getAndIncrement();
	}
	
	public BAPConnection(Connection connection) {
		this();
		this.wrapper = connection;
	}
	
	public Connection getWrapper() {
		return this.wrapper;
	}
	
	public String toString() {
		return getWrapper().getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(getWrapper())) + "@" + getId();
	}
	
	public long getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see java.sql.Wrapper#unwrap(java.lang.Class)
	 */
	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return getWrapper().unwrap(iface);
	}

	/* (non-Javadoc)
	 * @see java.sql.Wrapper#isWrapperFor(java.lang.Class)
	 */
	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return getWrapper().isWrapperFor(iface);
	}

	/* (non-Javadoc)
	 * @see java.sql.Connection#createStatement()
	 */
	@Override
	public Statement createStatement() throws SQLException {
		return new BAPStatement(this, getWrapper().createStatement());
	}

	/* (non-Javadoc)
	 * @see java.sql.Connection#prepareStatement(java.lang.String)
	 */
	@Override
	public PreparedStatement prepareStatement(String sql) throws SQLException {
		return new BAPPreparedStatement(this, getWrapper().prepareStatement(sql), sql);
	}

	/* (non-Javadoc)
	 * @see java.sql.Connection#prepareCall(java.lang.String)
	 */
	@Override
	public CallableStatement prepareCall(String sql) throws SQLException {
		return new BAPCallableStatement(this, getWrapper().prepareCall(sql), sql);
	}

	/* (non-Javadoc)
	 * @see java.sql.Connection#nativeSQL(java.lang.String)
	 */
	@Override
	public String nativeSQL(String sql) throws SQLException {
		return getWrapper().nativeSQL(sql);
	}

	/* (non-Javadoc)
	 * @see java.sql.Connection#setAutoCommit(boolean)
	 */
	@Override
	public void setAutoCommit(boolean autoCommit) throws SQLException {
		LOGGER.trace("connection {} set autoCommit to {}.", toString(), autoCommit);
		getWrapper().setAutoCommit(autoCommit);
	}

	/* (non-Javadoc)
	 * @see java.sql.Connection#getAutoCommit()
	 */
	@Override
	public boolean getAutoCommit() throws SQLException {
		boolean autocommit = getWrapper().getAutoCommit();
		LOGGER.trace("{} getAutoCommit return {}.", toString(), autocommit);
		return autocommit;
	}

	/* (non-Javadoc)
	 * @see java.sql.Connection#commit()
	 */
	@Override
	public void commit() throws SQLException {
		LOGGER.trace("connection {} commit.", toString());
		LOGGER.debug("===提交开始...");
		getWrapper().commit();
		LOGGER.debug("===提交结束...");
	}

	/* (non-Javadoc)
	 * @see java.sql.Connection#rollback()
	 */
	@Override
	public void rollback() throws SQLException {
		LOGGER.trace("connection {} rollback.", toString());
		getWrapper().rollback();
	}

	/* (non-Javadoc)
	 * @see java.sql.Connection#close()
	 */
	@Override
	public void close() throws SQLException {
		LOGGER.trace("connection {} close.", toString());
		getWrapper().close();
	}

	/* (non-Javadoc)
	 * @see java.sql.Connection#isClosed()
	 */
	@Override
	public boolean isClosed() throws SQLException {
		return getWrapper().isClosed();
	}

	/* (non-Javadoc)
	 * @see java.sql.Connection#getMetaData()
	 */
	@Override
	public DatabaseMetaData getMetaData() throws SQLException {
		return getWrapper().getMetaData();
	}

	/* (non-Javadoc)
	 * @see java.sql.Connection#setReadOnly(boolean)
	 */
	@Override
	public void setReadOnly(boolean readOnly) throws SQLException {
		getWrapper().setReadOnly(readOnly);
	}

	/* (non-Javadoc)
	 * @see java.sql.Connection#isReadOnly()
	 */
	@Override
	public boolean isReadOnly() throws SQLException {
		return getWrapper().isReadOnly();
	}

	/* (non-Javadoc)
	 * @see java.sql.Connection#setCatalog(java.lang.String)
	 */
	@Override
	public void setCatalog(String catalog) throws SQLException {
		getWrapper().setCatalog(catalog);
	}

	/* (non-Javadoc)
	 * @see java.sql.Connection#getCatalog()
	 */
	@Override
	public String getCatalog() throws SQLException {
		return getWrapper().getCatalog();
	}

	/* (non-Javadoc)
	 * @see java.sql.Connection#setTransactionIsolation(int)
	 */
	@Override
	public void setTransactionIsolation(int level) throws SQLException {
		getWrapper().setTransactionIsolation(level);
	}

	/* (non-Javadoc)
	 * @see java.sql.Connection#getTransactionIsolation()
	 */
	@Override
	public int getTransactionIsolation() throws SQLException {
		return getWrapper().getTransactionIsolation();
	}

	/* (non-Javadoc)
	 * @see java.sql.Connection#getWarnings()
	 */
	@Override
	public SQLWarning getWarnings() throws SQLException {
		return getWrapper().getWarnings();
	}

	/* (non-Javadoc)
	 * @see java.sql.Connection#clearWarnings()
	 */
	@Override
	public void clearWarnings() throws SQLException {
		getWrapper().clearWarnings();
	}

	/* (non-Javadoc)
	 * @see java.sql.Connection#createStatement(int, int)
	 */
	@Override
	public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
		return new BAPStatement(this, getWrapper().createStatement(resultSetType, resultSetConcurrency));
	}

	/* (non-Javadoc)
	 * @see java.sql.Connection#prepareStatement(java.lang.String, int, int)
	 */
	@Override
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
		return new BAPPreparedStatement(this, getWrapper().prepareStatement(sql, resultSetType, resultSetConcurrency), sql);
	}

	/* (non-Javadoc)
	 * @see java.sql.Connection#prepareCall(java.lang.String, int, int)
	 */
	@Override
	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
		return new BAPCallableStatement(this, getWrapper().prepareCall(sql, resultSetType, resultSetConcurrency), sql);
	}

	/* (non-Javadoc)
	 * @see java.sql.Connection#getTypeMap()
	 */
	@Override
	public Map<String, Class<?>> getTypeMap() throws SQLException {
		return getWrapper().getTypeMap();
	}

	/* (non-Javadoc)
	 * @see java.sql.Connection#setTypeMap(java.util.Map)
	 */
	@Override
	public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
		getWrapper().setTypeMap(map);
	}

	/* (non-Javadoc)
	 * @see java.sql.Connection#setHoldability(int)
	 */
	@Override
	public void setHoldability(int holdability) throws SQLException {
		getWrapper().setHoldability(holdability);
	}

	/* (non-Javadoc)
	 * @see java.sql.Connection#getHoldability()
	 */
	@Override
	public int getHoldability() throws SQLException {
		return getWrapper().getHoldability();
	}

	/* (non-Javadoc)
	 * @see java.sql.Connection#setSavepoint()
	 */
	@Override
	public Savepoint setSavepoint() throws SQLException {
		LOGGER.trace("connection {} save point.", toString());
		return getWrapper().setSavepoint();
	}

	/* (non-Javadoc)
	 * @see java.sql.Connection#setSavepoint(java.lang.String)
	 */
	@Override
	public Savepoint setSavepoint(String name) throws SQLException {
		LOGGER.trace("connection {} save point to {}.", toString(), name);
		return getWrapper().setSavepoint(name);
	}

	/* (non-Javadoc)
	 * @see java.sql.Connection#rollback(java.sql.Savepoint)
	 */
	@Override
	public void rollback(Savepoint savepoint) throws SQLException {
		LOGGER.trace("connection {} rollback to {}.", toString(), savepoint);
		getWrapper().rollback(savepoint);
	}

	/* (non-Javadoc)
	 * @see java.sql.Connection#releaseSavepoint(java.sql.Savepoint)
	 */
	@Override
	public void releaseSavepoint(Savepoint savepoint) throws SQLException {
		LOGGER.trace("connection {} release Savepoint to {}.", toString(), savepoint);
		getWrapper().releaseSavepoint(savepoint);
	}

	/* (non-Javadoc)
	 * @see java.sql.Connection#createStatement(int, int, int)
	 */
	@Override
	public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
		return new BAPStatement(this, getWrapper().createStatement(resultSetType, resultSetConcurrency, resultSetHoldability));
	}

	/* (non-Javadoc)
	 * @see java.sql.Connection#prepareStatement(java.lang.String, int, int, int)
	 */
	@Override
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		return new BAPPreparedStatement(this, getWrapper().prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability), sql);
	}

	/* (non-Javadoc)
	 * @see java.sql.Connection#prepareCall(java.lang.String, int, int, int)
	 */
	@Override
	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		return new BAPCallableStatement(this, getWrapper().prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability), sql);
	}

	/* (non-Javadoc)
	 * @see java.sql.Connection#prepareStatement(java.lang.String, int)
	 */
	@Override
	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
		return new BAPPreparedStatement(this, getWrapper().prepareStatement(sql, autoGeneratedKeys), sql);
	}

	/* (non-Javadoc)
	 * @see java.sql.Connection#prepareStatement(java.lang.String, int[])
	 */
	@Override
	public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
		return new BAPPreparedStatement(this, getWrapper().prepareStatement(sql, columnIndexes), sql);
	}

	/* (non-Javadoc)
	 * @see java.sql.Connection#prepareStatement(java.lang.String, java.lang.String[])
	 */
	@Override
	public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
		return new BAPPreparedStatement(this, getWrapper().prepareStatement(sql, columnNames), sql);
	}

	/* (non-Javadoc)
	 * @see java.sql.Connection#createClob()
	 */
	@Override
	public Clob createClob() throws SQLException {
		return getWrapper().createClob();
	}

	/* (non-Javadoc)
	 * @see java.sql.Connection#createBlob()
	 */
	@Override
	public Blob createBlob() throws SQLException {
		return getWrapper().createBlob();
	}

	/* (non-Javadoc)
	 * @see java.sql.Connection#createNClob()
	 */
	@Override
	public NClob createNClob() throws SQLException {
		return getWrapper().createNClob();
	}

	/* (non-Javadoc)
	 * @see java.sql.Connection#createSQLXML()
	 */
	@Override
	public SQLXML createSQLXML() throws SQLException {
		return getWrapper().createSQLXML();
	}

	/* (non-Javadoc)
	 * @see java.sql.Connection#isValid(int)
	 */
	@Override
	public boolean isValid(int timeout) throws SQLException {
		return getWrapper().isValid(timeout);
	}

	/* (non-Javadoc)
	 * @see java.sql.Connection#setClientInfo(java.lang.String, java.lang.String)
	 */
	@Override
	public void setClientInfo(String name, String value) throws SQLClientInfoException {
		getWrapper().setClientInfo(name, value);
	}

	/* (non-Javadoc)
	 * @see java.sql.Connection#setClientInfo(java.util.Properties)
	 */
	@Override
	public void setClientInfo(Properties properties) throws SQLClientInfoException {
		getWrapper().setClientInfo(properties);
	}

	/* (non-Javadoc)
	 * @see java.sql.Connection#getClientInfo(java.lang.String)
	 */
	@Override
	public String getClientInfo(String name) throws SQLException {
		return getWrapper().getClientInfo(name);
	}

	/* (non-Javadoc)
	 * @see java.sql.Connection#getClientInfo()
	 */
	@Override
	public Properties getClientInfo() throws SQLException {
		return getWrapper().getClientInfo();
	}

	/* (non-Javadoc)
	 * @see java.sql.Connection#createArrayOf(java.lang.String, java.lang.Object[])
	 */
	@Override
	public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
		return getWrapper().createArrayOf(typeName, elements);
	}

	/* (non-Javadoc)
	 * @see java.sql.Connection#createStruct(java.lang.String, java.lang.Object[])
	 */
	@Override
	public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
		return getWrapper().createStruct(typeName, attributes);
	}

	@Override
	public void setSchema(String schema) throws SQLException {
		getWrapper().setSchema(schema);
	}

	@Override
	public String getSchema() throws SQLException {
		return getWrapper().getSchema();
	}

	@Override
	public void abort(Executor executor) throws SQLException {
		getWrapper().abort(executor);
	}

	@Override
	public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
		getWrapper().setNetworkTimeout(executor, milliseconds);
	}

	@Override
	public int getNetworkTimeout() throws SQLException {
		return getWrapper().getNetworkTimeout();
	}

}
