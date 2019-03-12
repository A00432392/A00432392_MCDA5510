package com.mcda5510.connect;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLJDBCConnection implements DBConnection{

	public Connection setupConnection()  {
		final String USERNAME = "m_rankireddi";
		final String PASSWORD = "A00432392";
		final String CONN_STRING ="jdbc:mysql://dev.cs.smu.ca/m_rankireddi";
		Connection connection = null;
		try {
			// This will load the MySQL driver, each DB has its own driver
			// Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.mysql.cj.jdbc.Driver");
			// Setup the connection with the DB

			connection = DriverManager.getConnection(CONN_STRING,USERNAME,PASSWORD);
			System.out.println("Connected !");
		} catch (Exception e) {
			System.out.println("Error setting up connection");
			e.printStackTrace();
		} finally {
			
			}
		
		return connection;
	}		
	
	
}
