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
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;


/**
 * 
 * 
 * @author yaowei
 * @version $Id$
 */
public class BAPCallableStatement extends BAPPreparedStatement implements CallableStatement {
	

	protected CallableStatement wrapper;
    protected String callableQuery;
    

    public BAPCallableStatement(BAPConnection connection, CallableStatement preparedStatement, String sql) {
		super(connection, preparedStatement, sql);
		this.wrapper = preparedStatement;
		this.callableQuery = sql;
	}

    public CallableStatement getWrapper() {
		return wrapper;
	}

	@Override
	public void registerOutParameter(int parameterIndex, int sqlType) throws SQLException {
		getWrapper().registerOutParameter(parameterIndex, sqlType);
	}

	@Override
	public void registerOutParameter(int parameterIndex, int sqlType, int scale) throws SQLException {
		getWrapper().registerOutParameter(parameterIndex, sqlType, scale);
	}

	@Override
	public boolean wasNull() throws SQLException {
		return getWrapper().wasNull();
	}

	@Override
	public String getString(int parameterIndex) throws SQLException {
		return getWrapper().getString(parameterIndex);
	}

	@Override
	public boolean getBoolean(int parameterIndex) throws SQLException {
		return getWrapper().getBoolean(parameterIndex);
	}

	@Override
	public byte getByte(int parameterIndex) throws SQLException {
		return getWrapper().getByte(parameterIndex);
	}

	@Override
	public short getShort(int parameterIndex) throws SQLException {
		return getWrapper().getShort(parameterIndex);
	}

	@Override
	public int getInt(int parameterIndex) throws SQLException {
		return getWrapper().getInt(parameterIndex);
	}

	@Override
	public long getLong(int parameterIndex) throws SQLException {
		return getWrapper().getLong(parameterIndex);
	}

	@Override
	public float getFloat(int parameterIndex) throws SQLException {
		return getWrapper().getFloat(parameterIndex);
	}

	@Override
	public double getDouble(int parameterIndex) throws SQLException {
		return getWrapper().getDouble(parameterIndex);
	}

	@Override
	public BigDecimal getBigDecimal(int parameterIndex, int scale) throws SQLException {
		return getWrapper().getBigDecimal(parameterIndex, scale);
	}

	@Override
	public byte[] getBytes(int parameterIndex) throws SQLException {
		return getWrapper().getBytes(parameterIndex);
	}

	@Override
	public Date getDate(int parameterIndex) throws SQLException {
		return getWrapper().getDate(parameterIndex);
	}

	@Override
	public Time getTime(int parameterIndex) throws SQLException {
		return getWrapper().getTime(parameterIndex);
	}

	@Override
	public Timestamp getTimestamp(int parameterIndex) throws SQLException {
		return getWrapper().getTimestamp(parameterIndex);
	}

	@Override
	public Object getObject(int parameterIndex) throws SQLException {
		return getWrapper().getObject(parameterIndex);
	}

	@Override
	public BigDecimal getBigDecimal(int parameterIndex) throws SQLException {
		return getWrapper().getBigDecimal(parameterIndex);
	}

	@Override
	public Object getObject(int parameterIndex, Map<String, Class<?>> map) throws SQLException {
		return getWrapper().getObject(parameterIndex, map);
	}

	@Override
	public Ref getRef(int parameterIndex) throws SQLException {
		return getWrapper().getRef(parameterIndex);
	}

	@Override
	public Blob getBlob(int parameterIndex) throws SQLException {
		return getWrapper().getBlob(parameterIndex);
	}

	@Override
	public Clob getClob(int parameterIndex) throws SQLException {
		return getWrapper().getClob(parameterIndex);
	}

	@Override
	public Array getArray(int parameterIndex) throws SQLException {
		return getWrapper().getArray(parameterIndex);
	}

	@Override
	public Date getDate(int parameterIndex, Calendar cal) throws SQLException {
		return getWrapper().getDate(parameterIndex, cal);
	}

	@Override
	public Time getTime(int parameterIndex, Calendar cal) throws SQLException {
		return getWrapper().getTime(parameterIndex, cal);
	}

	@Override
	public Timestamp getTimestamp(int parameterIndex, Calendar cal) throws SQLException {
		return getWrapper().getTimestamp(parameterIndex, cal);
	}

	@Override
	public void registerOutParameter(int parameterIndex, int sqlType, String typeName) throws SQLException {
		getWrapper().registerOutParameter(parameterIndex, sqlType, typeName);
	}

	@Override
	public void registerOutParameter(String parameterName, int sqlType) throws SQLException {
		getWrapper().registerOutParameter(parameterName, sqlType);
	}

	@Override
	public void registerOutParameter(String parameterName, int sqlType, int scale) throws SQLException {
		getWrapper().registerOutParameter(parameterName, sqlType, scale);
	}

	@Override
	public void registerOutParameter(String parameterName, int sqlType, String typeName) throws SQLException {
		getWrapper().registerOutParameter(parameterName, sqlType, typeName);
	}

	@Override
	public URL getURL(int parameterIndex) throws SQLException {
		return getWrapper().getURL(parameterIndex);
	}

	@Override
	public void setURL(String parameterName, URL val) throws SQLException {
		// TODO Auto-generated method stub
		getWrapper().setURL(parameterName, val);
	}

	@Override
	public void setNull(String parameterName, int sqlType) throws SQLException {
		// TODO Auto-generated method stub
		getWrapper().setNull(parameterName, sqlType);
	}

	@Override
	public void setBoolean(String parameterName, boolean x) throws SQLException {
		// TODO Auto-generated method stub
		getWrapper().setBoolean(parameterName, x);
	}

	@Override
	public void setByte(String parameterName, byte x) throws SQLException {
		// TODO Auto-generated method stub
		getWrapper().setByte(parameterName, x);
	}

	@Override
	public void setShort(String parameterName, short x) throws SQLException {
		// TODO Auto-generated method stub
		getWrapper().setShort(parameterName, x);
	}

	@Override
	public void setInt(String parameterName, int x) throws SQLException {
		// TODO Auto-generated method stub
		getWrapper().setInt(parameterName, x);
	}

	@Override
	public void setLong(String parameterName, long x) throws SQLException {
		// TODO Auto-generated method stub
		getWrapper().setLong(parameterName, x);
	}

	@Override
	public void setFloat(String parameterName, float x) throws SQLException {
		// TODO Auto-generated method stub
		getWrapper().setFloat(parameterName, x);
	}

	@Override
	public void setDouble(String parameterName, double x) throws SQLException {
		// TODO Auto-generated method stub
		getWrapper().setDouble(parameterName, x);
	}

	@Override
	public void setBigDecimal(String parameterName, BigDecimal x) throws SQLException {
		// TODO Auto-generated method stub
		getWrapper().setBigDecimal(parameterName, x);
	}

	@Override
	public void setString(String parameterName, String x) throws SQLException {
		// TODO Auto-generated method stub
		getWrapper().setString(parameterName, x);
	}

	@Override
	public void setBytes(String parameterName, byte[] x) throws SQLException {
		// TODO Auto-generated method stub
		getWrapper().setBytes(parameterName, x);
	}

	@Override
	public void setDate(String parameterName, Date x) throws SQLException {
		// TODO Auto-generated method stub
		getWrapper().setDate(parameterName, x);
	}

	@Override
	public void setTime(String parameterName, Time x) throws SQLException {
		// TODO Auto-generated method stub
		getWrapper().setTime(parameterName, x);
	}

	@Override
	public void setTimestamp(String parameterName, Timestamp x) throws SQLException {
		// TODO Auto-generated method stub
		getWrapper().setTimestamp(parameterName, x);
	}

	@Override
	public void setAsciiStream(String parameterName, InputStream x, int length) throws SQLException {
		// TODO Auto-generated method stub
		getWrapper().setAsciiStream(parameterName, x, length);
	}

	@Override
	public void setBinaryStream(String parameterName, InputStream x, int length) throws SQLException {
		// TODO Auto-generated method stub
		getWrapper().setBinaryStream(parameterName, x, length);
	}

	@Override
	public void setObject(String parameterName, Object x, int targetSqlType, int scale) throws SQLException {
		// TODO Auto-generated method stub
		getWrapper().setObject(parameterName, x, targetSqlType, scale);
	}

	@Override
	public void setObject(String parameterName, Object x, int targetSqlType) throws SQLException {
		// TODO Auto-generated method stub
		getWrapper().setObject(parameterName, x, targetSqlType);
	}

	@Override
	public void setObject(String parameterName, Object x) throws SQLException {
		// TODO Auto-generated method stub
		getWrapper().setObject(parameterName, x);
	}

	@Override
	public void setCharacterStream(String parameterName, Reader reader, int length) throws SQLException {
		// TODO Auto-generated method stub
		getWrapper().setCharacterStream(parameterName, reader, length);
	}

	@Override
	public void setDate(String parameterName, Date x, Calendar cal) throws SQLException {
		// TODO Auto-generated method stub
		getWrapper().setDate(parameterName, x, cal);
	}

	@Override
	public void setTime(String parameterName, Time x, Calendar cal) throws SQLException {
		// TODO Auto-generated method stub
		getWrapper().setTime(parameterName, x, cal);
	}

	@Override
	public void setTimestamp(String parameterName, Timestamp x, Calendar cal) throws SQLException {
		// TODO Auto-generated method stub
		getWrapper().setTimestamp(parameterName, x, cal);
	}

	@Override
	public void setNull(String parameterName, int sqlType, String typeName) throws SQLException {
		// TODO Auto-generated method stub
		getWrapper().setNull(parameterName, sqlType, typeName);
	}

	@Override
	public String getString(String parameterName) throws SQLException {
		return getWrapper().getString(parameterName);
	}

	@Override
	public boolean getBoolean(String parameterName) throws SQLException {
		return getWrapper().getBoolean(parameterName);
	}

	@Override
	public byte getByte(String parameterName) throws SQLException {
		return getWrapper().getByte(parameterName);
	}

	@Override
	public short getShort(String parameterName) throws SQLException {
		return getWrapper().getShort(parameterName);
	}

	@Override
	public int getInt(String parameterName) throws SQLException {
		return getWrapper().getInt(parameterName);
	}

	@Override
	public long getLong(String parameterName) throws SQLException {
		return getWrapper().getLong(parameterName);
	}

	@Override
	public float getFloat(String parameterName) throws SQLException {
		return getWrapper().getFloat(parameterName);
	}

	@Override
	public double getDouble(String parameterName) throws SQLException {
		return getWrapper().getDouble(parameterName);
	}

	@Override
	public byte[] getBytes(String parameterName) throws SQLException {
		return getWrapper().getBytes(parameterName);
	}

	@Override
	public Date getDate(String parameterName) throws SQLException {
		return getWrapper().getDate(parameterName);
	}

	@Override
	public Time getTime(String parameterName) throws SQLException {
		return getWrapper().getTime(parameterName);
	}

	@Override
	public Timestamp getTimestamp(String parameterName) throws SQLException {
		return getWrapper().getTimestamp(parameterName);
	}

	@Override
	public Object getObject(String parameterName) throws SQLException {
		return getWrapper().getObject(parameterName);
	}

	@Override
	public BigDecimal getBigDecimal(String parameterName) throws SQLException {
		return getWrapper().getBigDecimal(parameterName);
	}

	@Override
	public Object getObject(String parameterName, Map<String, Class<?>> map) throws SQLException {
		return getWrapper().getObject(parameterName, map);
	}

	@Override
	public Ref getRef(String parameterName) throws SQLException {
		return getWrapper().getRef(parameterName);
	}

	@Override
	public Blob getBlob(String parameterName) throws SQLException {
		return getWrapper().getBlob(parameterName);
	}

	@Override
	public Clob getClob(String parameterName) throws SQLException {
		return getWrapper().getClob(parameterName);
	}

	@Override
	public Array getArray(String parameterName) throws SQLException {
		return getWrapper().getArray(parameterName);
	}

	@Override
	public Date getDate(String parameterName, Calendar cal) throws SQLException {
		return getWrapper().getDate(parameterName, cal);
	}

	@Override
	public Time getTime(String parameterName, Calendar cal) throws SQLException {
		return getWrapper().getTime(parameterName, cal);
	}

	@Override
	public Timestamp getTimestamp(String parameterName, Calendar cal) throws SQLException {
		return getWrapper().getTimestamp(parameterName, cal);
	}

	@Override
	public URL getURL(String parameterName) throws SQLException {
		return getWrapper().getURL(parameterName);
	}

	@Override
	public RowId getRowId(int parameterIndex) throws SQLException {
		return getWrapper().getRowId(parameterIndex);
	}

	@Override
	public RowId getRowId(String parameterName) throws SQLException {
		return getWrapper().getRowId(parameterName);
	}

	@Override
	public void setRowId(String parameterName, RowId x) throws SQLException {
		getWrapper().setRowId(parameterName, x);
	}

	@Override
	public void setNString(String parameterName, String value) throws SQLException {
		getWrapper().setNString(parameterName, value);		
	}

	@Override
	public void setNCharacterStream(String parameterName, Reader value, long length) throws SQLException {
		getWrapper().setNCharacterStream(parameterName, value, length);
	}

	@Override
	public void setNClob(String parameterName, NClob value) throws SQLException {
		getWrapper().setNClob(parameterName, value);
	}

	@Override
	public void setClob(String parameterName, Reader reader, long length) throws SQLException {
		getWrapper().setClob(parameterName, reader, length);
	}

	@Override
	public void setBlob(String parameterName, InputStream inputStream, long length) throws SQLException {
		getWrapper().setBlob(parameterName, inputStream, length);		
	}

	@Override
	public void setNClob(String parameterName, Reader reader, long length) throws SQLException {
		getWrapper().setNClob(parameterName, reader, length);
	}

	@Override
	public NClob getNClob(int parameterIndex) throws SQLException {
		return getWrapper().getNClob(parameterIndex);
	}

	@Override
	public NClob getNClob(String parameterName) throws SQLException {
		return getWrapper().getNClob(parameterName);
	}

	@Override
	public void setSQLXML(String parameterName, SQLXML xmlObject) throws SQLException {
		getWrapper().setSQLXML(parameterName, xmlObject);
	}

	@Override
	public SQLXML getSQLXML(int parameterIndex) throws SQLException {
		return getWrapper().getSQLXML(parameterIndex);
	}

	@Override
	public SQLXML getSQLXML(String parameterName) throws SQLException {
		return getWrapper().getSQLXML(parameterName);
	}

	@Override
	public String getNString(int parameterIndex) throws SQLException {
		return getWrapper().getNString(parameterIndex);
	}

	@Override
	public String getNString(String parameterName) throws SQLException {
		return getWrapper().getNString(parameterName);
	}

	@Override
	public Reader getNCharacterStream(int parameterIndex) throws SQLException {
		return getWrapper().getNCharacterStream(parameterIndex);
	}

	@Override
	public Reader getNCharacterStream(String parameterName) throws SQLException {
		return getWrapper().getNCharacterStream(parameterName);
	}

	@Override
	public Reader getCharacterStream(int parameterIndex) throws SQLException {
		return getWrapper().getCharacterStream(parameterIndex);
	}

	@Override
	public Reader getCharacterStream(String parameterName) throws SQLException {
		return getWrapper().getCharacterStream(parameterName);
	}

	@Override
	public void setBlob(String parameterName, Blob x) throws SQLException {
		getWrapper().setBlob(parameterName, x);		
	}

	@Override
	public void setClob(String parameterName, Clob x) throws SQLException {
		getWrapper().setClob(parameterName, x);		
	}

	@Override
	public void setAsciiStream(String parameterName, InputStream x, long length) throws SQLException {
		getWrapper().setAsciiStream(parameterName, x, length);		
	}

	@Override
	public void setBinaryStream(String parameterName, InputStream x, long length) throws SQLException {
		getWrapper().setBinaryStream(parameterName, x, length);
	}

	@Override
	public void setCharacterStream(String parameterName, Reader reader, long length) throws SQLException {
		getWrapper().setCharacterStream(parameterName, reader, length);		
	}

	@Override
	public void setAsciiStream(String parameterName, InputStream x) throws SQLException {
		getWrapper().setAsciiStream(parameterName, x);		
	}

	@Override
	public void setBinaryStream(String parameterName, InputStream x) throws SQLException {
		getWrapper().setBinaryStream(parameterName, x);		
	}

	@Override
	public void setCharacterStream(String parameterName, Reader reader) throws SQLException {
		getWrapper().setCharacterStream(parameterName, reader);		
	}

	@Override
	public void setNCharacterStream(String parameterName, Reader value) throws SQLException {
		getWrapper().setNCharacterStream(parameterName, value);		
	}

	@Override
	public void setClob(String parameterName, Reader reader) throws SQLException {
		getWrapper().setClob(parameterName, reader);		
	}

	@Override
	public void setBlob(String parameterName, InputStream inputStream) throws SQLException {
		getWrapper().setBlob(parameterName, inputStream);		
	}

	@Override
	public void setNClob(String parameterName, Reader reader) throws SQLException {
		getWrapper().setNClob(parameterName, reader);		
	}

	@Override
	public <T> T getObject(int parameterIndex, Class<T> type) throws SQLException {
		return getWrapper().getObject(parameterIndex, type);
	}

	@Override
	public <T> T getObject(String parameterName, Class<T> type) throws SQLException {
		return getWrapper().getObject(parameterName, type);
	}
	
}
