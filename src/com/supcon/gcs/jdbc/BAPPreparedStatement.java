/**
 * Copyright (C) 2011 ZHEJIANG SUPCON TECHNOLOGY CO.,LTD. 
 * All rights reserved.
 */
package com.supcon.gcs.jdbc;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * 
 * @author yaowei
 * @version $Id$
 */
public class BAPPreparedStatement extends BAPStatement implements PreparedStatement {

	private PreparedStatement wrapper;
	private Object[] values = new Object[VALUE_GROW_MAX + 1];
	private String preparedQuery;

	private String timestampFormat = "yyyy-MM-dd HH:mm:ss.SSS";
	private String dateFormat = "yyyy-MM-dd";
	private String timeFormat = "HH:mm:ss";

	public BAPPreparedStatement(BAPConnection connection, PreparedStatement preparedStatement, String sql) {
		super(connection, preparedStatement);
		this.wrapper = preparedStatement;
		this.preparedQuery = sql;
	}

	public String getSql() {
		return preparedQuery;
	}

	public PreparedStatement getWrapper() {
		return wrapper;
	}

	private static final int VALUE_GROW_MAX = 32;

	protected void setParamterAsString(int parameterIndex, Object parameter) {
		int length = this.values.length;
		if (parameterIndex >= length) {
			Object[] temp = new Object[parameterIndex + VALUE_GROW_MAX];
			System.arraycopy(this.values, 0, temp, 0, length);
			this.values = temp;
		}

		if (parameter instanceof java.sql.Date) {
			this.values[parameterIndex] = new SimpleDateFormat(this.dateFormat).format((java.sql.Date) parameter) + "@D";
		} else if (parameter instanceof java.sql.Time) {
			this.values[parameterIndex] = new SimpleDateFormat(this.timeFormat).format((java.sql.Time) parameter) + "@T";
		} else if (parameter instanceof java.sql.Timestamp) {
			this.values[parameterIndex] = new SimpleDateFormat(this.timestampFormat).format((java.sql.Timestamp) parameter) + "@TS";
		} else if (parameter instanceof java.util.Date) {
			this.values[parameterIndex] = new SimpleDateFormat(this.timestampFormat).format((java.util.Date) parameter) + "@d";
		} else if (parameter instanceof byte[]) {
			StringBuilder builder = new StringBuilder();
			for (byte b : (byte[]) parameter) {
				int val = ((int) b) & 0xff;
				if (val < 16)
					builder.append('0');
				builder.append(Integer.toHexString(b));
			}
			this.values[parameterIndex] = builder.toString() + "@bs";
		} else if (parameter instanceof String) {
			this.values[parameterIndex] = "'" + parameter + "'";
		} else {
			if (null == parameter) {
				this.values[parameterIndex] = "null";
			} else {
				this.values[parameterIndex] = parameter.toString();
			}
		}
	}

	@Override
	public ResultSet executeQuery() throws SQLException {
		long start = System.currentTimeMillis();
		try {
			ResultSet rs = getWrapper().executeQuery();

			return rs;
		} finally {
			BAPLogger.log(getConnectionId(), start, BAPLogger.Category.Statement, preparedQuery, this.values);
		}

	}

	@Override
	public int executeUpdate() throws SQLException {
		long start = System.currentTimeMillis();
		try {
			return getWrapper().executeUpdate();
		} finally {
			BAPLogger.log(getConnectionId(), start, BAPLogger.Category.Statement, preparedQuery, this.values);
		}
	}

	@Override
	public boolean execute() throws SQLException {
		long start = System.currentTimeMillis();
		try {
			return getWrapper().execute();
		} finally {
			BAPLogger.log(getConnectionId(), start, BAPLogger.Category.Statement, preparedQuery, this.values);
		}
	}

	@Override
	public void addBatch() throws SQLException {
		long start = System.currentTimeMillis();
		try {
			getWrapper().addBatch();
		} finally {
			BAPLogger.log(getConnectionId(), start, BAPLogger.Category.Batch, preparedQuery, this.values);
		}
	}

	@Override
	public int[] executeBatch() throws SQLException {
		long start = System.currentTimeMillis();
		try {
			return getWrapper().executeBatch();
		} finally {
			BAPLogger.log(getConnectionId(), start, BAPLogger.Category.ExecuteBatch, this.preparedQuery, this.values);
		}
	}

	@Override
	public void setNull(int parameterIndex, int sqlType) throws SQLException {
		setParamterAsString(parameterIndex, null);
		getWrapper().setNull(parameterIndex, sqlType);
	}

	@Override
	public void setBoolean(int parameterIndex, boolean x) throws SQLException {
		setParamterAsString(parameterIndex, new Boolean(x));
		getWrapper().setBoolean(parameterIndex, x);
	}

	@Override
	public void setByte(int parameterIndex, byte x) throws SQLException {
		setParamterAsString(parameterIndex, new Byte(x));
		getWrapper().setByte(parameterIndex, x);
	}

	@Override
	public void setShort(int parameterIndex, short x) throws SQLException {
		setParamterAsString(parameterIndex, new Short(x));
		getWrapper().setShort(parameterIndex, x);
	}

	@Override
	public void setInt(int parameterIndex, int x) throws SQLException {
		setParamterAsString(parameterIndex, new Integer(x));
		getWrapper().setInt(parameterIndex, x);
	}

	@Override
	public void setLong(int parameterIndex, long x) throws SQLException {
		setParamterAsString(parameterIndex, new Long(x));
		getWrapper().setLong(parameterIndex, x);
	}

	@Override
	public void setFloat(int parameterIndex, float x) throws SQLException {
		setParamterAsString(parameterIndex, new Float(x));
		getWrapper().setFloat(parameterIndex, x);
	}

	@Override
	public void setDouble(int parameterIndex, double x) throws SQLException {
		setParamterAsString(parameterIndex, new Double(x));
		getWrapper().setDouble(parameterIndex, x);
	}

	@Override
	public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
		setParamterAsString(parameterIndex, x);
		getWrapper().setBigDecimal(parameterIndex, x);
	}

	@Override
	public void setString(int parameterIndex, String x) throws SQLException {
		setParamterAsString(parameterIndex, x);
		getWrapper().setString(parameterIndex, x);
	}

	@Override
	public void setBytes(int parameterIndex, byte[] x) throws SQLException {
		setParamterAsString(parameterIndex, x);
		getWrapper().setBytes(parameterIndex, x);
	}

	@Override
	public void setDate(int parameterIndex, Date x) throws SQLException {
		setParamterAsString(parameterIndex, x);
		getWrapper().setDate(parameterIndex, x);
	}

	@Override
	public void setTime(int parameterIndex, Time x) throws SQLException {
		setParamterAsString(parameterIndex, x);
		getWrapper().setTime(parameterIndex, x);
	}

	@Override
	public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
		setParamterAsString(parameterIndex, x);
		getWrapper().setTimestamp(parameterIndex, x);
	}

	@Override
	public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
		setParamterAsString(parameterIndex, x);
		getWrapper().setAsciiStream(parameterIndex, x, length);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.PreparedStatement#setUnicodeStream(int, java.io.InputStream, int)
	 * 
	 * @deprecated
	 */
	@Deprecated
	@Override
	public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
		setParamterAsString(parameterIndex, x);
		getWrapper().setUnicodeStream(parameterIndex, x, length);
	}

	@Override
	public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
		setParamterAsString(parameterIndex, x);
		getWrapper().setBinaryStream(parameterIndex, x, length);
	}

	@Override
	public void clearParameters() throws SQLException {
		getWrapper().clearParameters();
	}

	@Override
	public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
		setParamterAsString(parameterIndex, x);
		getWrapper().setObject(parameterIndex, x, targetSqlType);
	}

	@Override
	public void setObject(int parameterIndex, Object x) throws SQLException {
		setParamterAsString(parameterIndex, x);
		getWrapper().setObject(parameterIndex, x);
	}

	@Override
	public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
		getWrapper().setCharacterStream(parameterIndex, reader, length);
	}

	@Override
	public void setRef(int parameterIndex, Ref x) throws SQLException {
		setParamterAsString(parameterIndex, x);
		getWrapper().setRef(parameterIndex, x);
	}

	@Override
	public void setBlob(int parameterIndex, Blob x) throws SQLException {
		setParamterAsString(parameterIndex, x);
		getWrapper().setBlob(parameterIndex, x);
	}

	@Override
	public void setClob(int parameterIndex, Clob x) throws SQLException {
		setParamterAsString(parameterIndex, x);
		getWrapper().setClob(parameterIndex, x);
	}

	@Override
	public void setArray(int parameterIndex, Array x) throws SQLException {
		setParamterAsString(parameterIndex, x);
		getWrapper().setArray(parameterIndex, x);
	}

	@Override
	public ResultSetMetaData getMetaData() throws SQLException {
		return getWrapper().getMetaData();
	}

	@Override
	public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
		setParamterAsString(parameterIndex, x);
		getWrapper().setDate(parameterIndex, x, cal);
	}

	@Override
	public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
		setParamterAsString(parameterIndex, x);
		getWrapper().setTime(parameterIndex, x, cal);
	}

	@Override
	public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
		setParamterAsString(parameterIndex, x);
		getWrapper().setTimestamp(parameterIndex, x, cal);
	}

	@Override
	public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {
		setParamterAsString(parameterIndex, null);
		getWrapper().setNull(parameterIndex, sqlType, typeName);
	}

	@Override
	public void setURL(int parameterIndex, URL x) throws SQLException {
		setParamterAsString(parameterIndex, x);
		getWrapper().setURL(parameterIndex, x);
	}

	@Override
	public ParameterMetaData getParameterMetaData() throws SQLException {
		return getWrapper().getParameterMetaData();
	}

	@Override
	public void setRowId(int parameterIndex, RowId x) throws SQLException {
		setParamterAsString(parameterIndex, x);
		getWrapper().setRowId(parameterIndex, x);
	}

	@Override
	public void setNString(int parameterIndex, String value) throws SQLException {
		setParamterAsString(parameterIndex, value);
		getWrapper().setNString(parameterIndex, value);
	}

	@Override
	public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {
		setParamterAsString(parameterIndex, value);
		getWrapper().setNCharacterStream(parameterIndex, value, length);
	}

	@Override
	public void setNClob(int parameterIndex, NClob value) throws SQLException {
		setParamterAsString(parameterIndex, value);
		getWrapper().setNClob(parameterIndex, value);
	}

	@Override
	public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {
		setParamterAsString(parameterIndex, reader);
		getWrapper().setClob(parameterIndex, reader, length);
	}

	@Override
	public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {
		setParamterAsString(parameterIndex, inputStream);
		getWrapper().setBlob(parameterIndex, inputStream, length);
	}

	@Override
	public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
		setParamterAsString(parameterIndex, reader);
		getWrapper().setNClob(parameterIndex, reader, length);
	}

	@Override
	public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
		setParamterAsString(parameterIndex, xmlObject);
		getWrapper().setSQLXML(parameterIndex, xmlObject);
	}

	@Override
	public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws SQLException {
		setParamterAsString(parameterIndex, x);
		getWrapper().setObject(parameterIndex, x, targetSqlType, scaleOrLength);
	}

	@Override
	public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {
		setParamterAsString(parameterIndex, x);
		getWrapper().setAsciiStream(parameterIndex, x, length);
	}

	@Override
	public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {
		setParamterAsString(parameterIndex, x);
		getWrapper().setBinaryStream(parameterIndex, x, length);
	}

	@Override
	public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
		setParamterAsString(parameterIndex, reader);
		getWrapper().setCharacterStream(parameterIndex, reader, length);
	}

	@Override
	public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
		setParamterAsString(parameterIndex, x);
		getWrapper().setAsciiStream(parameterIndex, x);
	}

	@Override
	public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
		setParamterAsString(parameterIndex, x);
		getWrapper().setBinaryStream(parameterIndex, x);
	}

	@Override
	public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
		setParamterAsString(parameterIndex, reader);
		getWrapper().setCharacterStream(parameterIndex, reader);
	}

	@Override
	public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {
		setParamterAsString(parameterIndex, value);
		getWrapper().setNCharacterStream(parameterIndex, value);
	}

	@Override
	public void setClob(int parameterIndex, Reader reader) throws SQLException {
		setParamterAsString(parameterIndex, reader);
		getWrapper().setClob(parameterIndex, reader);
	}

	@Override
	public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {
		setParamterAsString(parameterIndex, inputStream);
		getWrapper().setBlob(parameterIndex, inputStream);
	}

	@Override
	public void setNClob(int parameterIndex, Reader reader) throws SQLException {
		setParamterAsString(parameterIndex, reader);
		getWrapper().setNClob(parameterIndex, reader);
	}
}
