/**
 * Copyright (C) 2011 ZHEJIANG SUPCON TECHNOLOGY CO.,LTD. 
 * All rights reserved.
 */
package com.supcon.gcs.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 
 * @author yaowei
 * @version $Id$
 */
public class BAPLogger {

	private static final Logger LOGGER = LoggerFactory.getLogger("bap.sql.logging");

	public enum Category {
		Statement, Batch, ExecuteBatch, Commit, Rollback
	};

	/**
	 * 只用于单元测试时检查sql格式。
	 */
	private static String lastEntry = null;

	public static String getLastEntry() {
		return lastEntry;
	}

	/**
	 * 
	 * @param connectionId
	 * @param startTime
	 * @param category
	 * @param sql
	 * @param args
	 * @see #log(long, long, long, Category, String, Object...)
	 */
	public static void log(long connectionId, long startTime, Category category, String sql, Object... args) {
		log(connectionId, startTime, System.currentTimeMillis(), category, sql, args);
	}

	public static void error(String message, Throwable e) {
		LOGGER.error(message, e);
	}

	/**
	 * 输出日志的结构： ${connectionId}|${category}|${startTime}|${elapsed}|${sql}
	 * 
	 * @param connectionId
	 * @param startTime
	 * @param endTime
	 * @param category
	 * @param sql
	 * @param args
	 */
	public static void log(long connectionId, long startTime, long endTime, Category category, String sql, Object... args) {
		StringBuilder builder = new StringBuilder();
		builder.append(connectionId).append('|').append(category).append('|').append(startTime).append('|').append(endTime - startTime);

		if (null != sql && sql.length() > 0) {
			builder.append('|');

			if (null != args && args.length > 0) {
				int parameterIndex = 0;
				boolean isCommit = false;
				final char[] chars = sql.toCharArray();
				for (int i = 0; i < chars.length; i++) {
					char ch = chars[i];
					if ('/' == ch && '*' == chars[i + 1]) { // 开始 /*
						isCommit = true;
					}
					if (isCommit && ('*' == ch && '/' == chars[i + 1])) { // 结束 */
						isCommit = false;
					}

					if ('?' == ch && !isCommit) { // 只替换不在 /* */ 里的问号
						parameterIndex++;
						builder.append(ch).append(':').append(args[parameterIndex]);
					} else {
						builder.append(ch);
					}
				}
			} else {
				builder.append(sql);
			}
		}
		lastEntry = builder.toString();
		LOGGER.info(builder.toString());
	}
}
