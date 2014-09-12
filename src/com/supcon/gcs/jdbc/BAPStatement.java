/**
 * Copyright (C) 2011 ZHEJIANG SUPCON TECHNOLOGY CO.,LTD. 
 * All rights reserved.
 */
package com.supcon.gcs.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;


/**
 * 
 * 
 * @author yaowei
 * @version $Id$
 */
public class BAPStatement implements Statement {
	
	private Statement wrapper;
	private BAPConnection connection;
	
	public BAPStatement(BAPConnection connection, Statement statement) {
		this.connection = connection;
		this.wrapper = statement;
	}
	
	public Statement getWrapper() {
		return wrapper;
	}
	
	protected long getConnectionId() {
		return this.connection.getId();
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
	 * @see java.sql.Statement#executeQuery(java.lang.String)
	 */
	@Override
	public ResultSet executeQuery(String sql) throws SQLException {
		long start = System.currentTimeMillis();
		try {
			return getWrapper().executeQuery(sql);
		} finally {
			BAPLogger.log(getConnectionId(), start, BAPLogger.Category.Statement, sql);
		}		
	}

	/* (non-Javadoc)
	 * @see java.sql.Statement#executeUpdate(java.lang.String)
	 */
	@Override
	public int executeUpdate(String sql) throws SQLException {
		long start = System.currentTimeMillis();
		try {
			return getWrapper().executeUpdate(sql);
		} finally {
			BAPLogger.log(getConnectionId(), start, BAPLogger.Category.Statement, sql);
		}
	}

	/* (non-Javadoc)
	 * @see java.sql.Statement#close()
	 */
	@Override
	public void close() throws SQLException {
		getWrapper().close();
	}

	/* (non-Javadoc)
	 * @see java.sql.Statement#getMaxFieldSize()
	 */
	@Override
	public int getMaxFieldSize() throws SQLException {
		return getWrapper().getMaxFieldSize();
	}

	/* (non-Javadoc)
	 * @see java.sql.Statement#setMaxFieldSize(int)
	 */
	@Override
	public void setMaxFieldSize(int max) throws SQLException {
		getWrapper().setMaxFieldSize(max);
	}

	/* (non-Javadoc)
	 * @see java.sql.Statement#getMaxRows()
	 */
	@Override
	public int getMaxRows() throws SQLException {
		return getWrapper().getMaxRows();
	}

	/* (non-Javadoc)
	 * @see java.sql.Statement#setMaxRows(int)
	 */
	@Override
	public void setMaxRows(int max) throws SQLException {
		getWrapper().setMaxRows(max);
	}

	/* (non-Javadoc)
	 * @see java.sql.Statement#setEscapeProcessing(boolean)
	 */
	@Override
	public void setEscapeProcessing(boolean enable) throws SQLException {
		getWrapper().setEscapeProcessing(enable);
	}

	/* (non-Javadoc)
	 * @see java.sql.Statement#getQueryTimeout()
	 */
	@Override
	public int getQueryTimeout() throws SQLException {
		return getWrapper().getQueryTimeout();
	}

	/* (non-Javadoc)
	 * @see java.sql.Statement#setQueryTimeout(int)
	 */
	@Override
	public void setQueryTimeout(int seconds) throws SQLException {
		getWrapper().setQueryTimeout(seconds);
	}

	/* (non-Javadoc)
	 * @see java.sql.Statement#cancel()
	 */
	@Override
	public void cancel() throws SQLException {
		getWrapper().cancel();
	}

	/* (non-Javadoc)
	 * @see java.sql.Statement#getWarnings()
	 */
	@Override
	public SQLWarning getWarnings() throws SQLException {
		return getWrapper().getWarnings();
	}

	/* (non-Javadoc)
	 * @see java.sql.Statement#clearWarnings()
	 */
	@Override
	public void clearWarnings() throws SQLException {
		getWrapper().clearWarnings();
	}

	/* (non-Javadoc)
	 * @see java.sql.Statement#setCursorName(java.lang.String)
	 */
	@Override
	public void setCursorName(String name) throws SQLException {
		getWrapper().setCursorName(name);
	}

	/* (non-Javadoc)
	 * @see java.sql.Statement#execute(java.lang.String)
	 */
	@Override
	public boolean execute(String sql) throws SQLException {
		long start = System.currentTimeMillis();
		try {
			return getWrapper().execute(sql);
		} finally {
			BAPLogger.log(getConnectionId(), start, BAPLogger.Category.Statement, sql);
		}
	}

	/* (non-Javadoc)
	 * @see java.sql.Statement#getResultSet()
	 */
	@Override
	public ResultSet getResultSet() throws SQLException {
		return getWrapper().getResultSet();
	}

	/* (non-Javadoc)
	 * @see java.sql.Statement#getUpdateCount()
	 */
	@Override
	public int getUpdateCount() throws SQLException {
		return getWrapper().getUpdateCount();
	}

	/* (non-Javadoc)
	 * @see java.sql.Statement#getMoreResults()
	 */
	@Override
	public boolean getMoreResults() throws SQLException {
		return getWrapper().getMoreResults();
	}

	/* (non-Javadoc)
	 * @see java.sql.Statement#setFetchDirection(int)
	 */
	@Override
	public void setFetchDirection(int direction) throws SQLException {
		getWrapper().setFetchDirection(direction);
	}

	/* (non-Javadoc)
	 * @see java.sql.Statement#getFetchDirection()
	 */
	@Override
	public int getFetchDirection() throws SQLException {
		return getWrapper().getFetchDirection();
	}

	/* (non-Javadoc)
	 * @see java.sql.Statement#setFetchSize(int)
	 */
	@Override
	public void setFetchSize(int rows) throws SQLException {
		getWrapper().setFetchSize(rows);
	}

	/* (non-Javadoc)
	 * @see java.sql.Statement#getFetchSize()
	 */
	@Override
	public int getFetchSize() throws SQLException {
		return getWrapper().getFetchSize();
	}

	/* (non-Javadoc)
	 * @see java.sql.Statement#getResultSetConcurrency()
	 */
	@Override
	public int getResultSetConcurrency() throws SQLException {
		return getWrapper().getResultSetConcurrency();
	}

	/* (non-Javadoc)
	 * @see java.sql.Statement#getResultSetType()
	 */
	@Override
	public int getResultSetType() throws SQLException {
		return getWrapper().getResultSetType();
	}

	/* (non-Javadoc)
	 * @see java.sql.Statement#addBatch(java.lang.String)
	 */
	@Override
	public void addBatch(String sql) throws SQLException {
		long start = System.currentTimeMillis();
		try {
			getWrapper().addBatch(sql);
		} finally {
			BAPLogger.log(getConnectionId(), start, BAPLogger.Category.Batch, sql);
		}
	}

	/* (non-Javadoc)
	 * @see java.sql.Statement#clearBatch()
	 */
	@Override
	public void clearBatch() throws SQLException {
		getWrapper().clearBatch();
	}

	/* (non-Javadoc)
	 * @see java.sql.Statement#executeBatch()
	 */
	@Override
	public int[] executeBatch() throws SQLException {
		long start = System.currentTimeMillis();
		try {
			return getWrapper().executeBatch();
		} finally {
			BAPLogger.log(getConnectionId(), start, BAPLogger.Category.ExecuteBatch, null);
		}
	}

	/* (non-Javadoc)
	 * @see java.sql.Statement#getConnection()
	 */
	@Override
	public Connection getConnection() throws SQLException {
		return this.connection;
	}

	/* (non-Javadoc)
	 * @see java.sql.Statement#getMoreResults(int)
	 */
	@Override
	public boolean getMoreResults(int current) throws SQLException {
		return getWrapper().getMoreResults();
	}

	/* (non-Javadoc)
	 * @see java.sql.Statement#getGeneratedKeys()
	 */
	@Override
	public ResultSet getGeneratedKeys() throws SQLException {
		return getWrapper().getGeneratedKeys();
	}

	/* (non-Javadoc)
	 * @see java.sql.Statement#executeUpdate(java.lang.String, int)
	 */
	@Override
	public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
		long start = System.currentTimeMillis();
		try {
			return getWrapper().executeUpdate(sql, autoGeneratedKeys);
		} finally {
			BAPLogger.log(getConnectionId(), start, BAPLogger.Category.Statement, sql);
		}
	}

	/* (non-Javadoc)
	 * @see java.sql.Statement#executeUpdate(java.lang.String, int[])
	 */
	@Override
	public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
		long start = System.currentTimeMillis();
		try {
			return getWrapper().executeUpdate(sql, columnIndexes);
		} finally {
			BAPLogger.log(getConnectionId(), start, BAPLogger.Category.Statement, sql);
		}
	}

	/* (non-Javadoc)
	 * @see java.sql.Statement#executeUpdate(java.lang.String, java.lang.String[])
	 */
	@Override
	public int executeUpdate(String sql, String[] columnNames) throws SQLException {
		long start = System.currentTimeMillis();
		try {
			return getWrapper().executeUpdate(sql, columnNames);
		} finally {
			BAPLogger.log(getConnectionId(), start, BAPLogger.Category.Statement, sql);
		}
	}

	/* (non-Javadoc)
	 * @see java.sql.Statement#execute(java.lang.String, int)
	 */
	@Override
	public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
		long start = System.currentTimeMillis();
		try {
			return getWrapper().execute(sql, autoGeneratedKeys);
		} finally {
			BAPLogger.log(getConnectionId(), start, BAPLogger.Category.Statement, sql);
		}
	}

	/* (non-Javadoc)
	 * @see java.sql.Statement#execute(java.lang.String, int[])
	 */
	@Override
	public boolean execute(String sql, int[] columnIndexes) throws SQLException {
		long start = System.currentTimeMillis();
		try {
			return getWrapper().execute(sql, columnIndexes);
		} finally {
			BAPLogger.log(getConnectionId(), start, BAPLogger.Category.Statement, sql);
		}
	}

	/* (non-Javadoc)
	 * @see java.sql.Statement#execute(java.lang.String, java.lang.String[])
	 */
	@Override
	public boolean execute(String sql, String[] columnNames) throws SQLException {
		long start = System.currentTimeMillis();
		try {
			return getWrapper().execute(sql, columnNames);
		} finally {
			BAPLogger.log(getConnectionId(), start, BAPLogger.Category.Statement, sql);
		}
	}

	/* (non-Javadoc)
	 * @see java.sql.Statement#getResultSetHoldability()
	 */
	@Override
	public int getResultSetHoldability() throws SQLException {
		return getWrapper().getResultSetHoldability();
	}

	/* (non-Javadoc)
	 * @see java.sql.Statement#isClosed()
	 */
	@Override
	public boolean isClosed() throws SQLException {
		return getWrapper().isClosed();
	}

	/* (non-Javadoc)
	 * @see java.sql.Statement#setPoolable(boolean)
	 */
	@Override
	public void setPoolable(boolean poolable) throws SQLException {
		getWrapper().setPoolable(poolable);
	}

	/* (non-Javadoc)
	 * @see java.sql.Statement#isPoolable()
	 */
	@Override
	public boolean isPoolable() throws SQLException {
		return getWrapper().isPoolable();
	}

	@Override
	public void closeOnCompletion() throws SQLException {
		getWrapper().closeOnCompletion();
	}

	@Override
	public boolean isCloseOnCompletion() throws SQLException {
		return getWrapper().isCloseOnCompletion();
	}

}
