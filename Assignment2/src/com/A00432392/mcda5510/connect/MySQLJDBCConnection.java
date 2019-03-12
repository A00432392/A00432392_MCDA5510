package com.dpenny.mcda5510.connect;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class MySQLJDBCConnection{

private static final String USERNAME = "m_rankireddi";
private static final String PASSWORD = "A00432392";
private static final String CONN_STRING ="jdbc:mysql://dev.cs.smu.ca/m_rankireddi?autoReconnect=true&useSSL=false";
	
	public static void main(String[] args) {
		
		try 
		{
			Connection conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);

			System.out.println("Connected !");
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		}
		
	}