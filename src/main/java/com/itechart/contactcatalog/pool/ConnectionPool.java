package com.itechart.contactcatalog.pool;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itechart.contactcatalog.exception.ConnectionPoolException;

public class ConnectionPool {
	private static final String DATASOURCE_NAME = "jdbc/manual";
	private static DataSource dataSource;
	private static ConnectionPool instance = null;
	private static Logger logger = LoggerFactory.getLogger(ConnectionPool.class);

	private ConnectionPool(){
		
	}
	
	public static synchronized ConnectionPool getInstance() throws ConnectionPoolException{
		if (instance == null) {
			try {
				instance = new ConnectionPool();
				Context initContext = new InitialContext();
				Context envContext = (Context) initContext.lookup("java:/comp/env");
				dataSource = (DataSource) envContext.lookup(DATASOURCE_NAME);
				if(dataSource == null) {
					logger.error("Couldn't find datasource");
	                throw new ConnectionPoolException("Couldn't find datasource");
	            }
			} catch (NamingException e) {
				logger.error("Exception in getInstance: {}", e);
				throw new ConnectionPoolException("Error in connection pool "+ e);
			}
        }
        return instance;
    }
	
	public Connection getConnection() throws ConnectionPoolException{
		try {
			Connection connection = dataSource.getConnection();
			if(connection == null) {
				logger.error("Couldn't get connection");
                throw new ConnectionPoolException("Couldn't get connection");
            }
			return connection;
		} catch (SQLException e) {
			logger.error("Exception in getConnection: {}", e);
			throw new ConnectionPoolException("Try to make your request later");
		}
	}
	
	public void returnConnection(Connection connection) {
		 if(connection!=null){
			 try {
				connection.close();
			} catch (SQLException e) {
				logger.error("Exception in returnConnection: {}", e);
			}
		 }
    }
}
