package com.lj.common;

import java.sql.Types;
import org.hibernate.Hibernate;

/**
 * hibernate 关系数据库持久化Java的惯用
 * 持久化与数据库类型的转化
 * config.properties---hibernate.dialect=com.lj.common.MySQL5Dialect
 * @author lscm
 *
 */
public class MySQL5Dialect extends org.hibernate.dialect.MySQL5Dialect {
	public MySQL5Dialect() {
		super();
		// register additional hibernate types for default use in scalar
		// sqlquery type auto detection
		registerHibernateType(Types.LONGVARCHAR, Hibernate.TEXT.getName());
	}
}