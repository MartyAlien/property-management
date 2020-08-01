package com.program.druidJDBCutil;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSourceFactory;

public class DruidJDBC {
	private static DataSource dataSource;
	static {
		Properties properties=new Properties();
		try {
			DruidJDBC.class.getClassLoader();
			properties.load(ClassLoader.getSystemResourceAsStream("cnf/druidJDBC.properties"));
			dataSource = DruidDataSourceFactory.createDataSource(properties);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static DataSource getDataSource() {
		return dataSource;
		
	}
	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}
}