package com.mcda5510;

import java.sql.Connection;
import java.util.Calendar;
import java.util.Collection;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.io.IOException;
import java.sql.*;

import com.mcda5510.connect.ConnectionFactory;
import com.mcda5510.connect.MySQLJDBCConnection;
import com.mcda5510.dao.MySQLAccess;
import com.mcda5510.entity.Transaction;

public class Assignment2 {

	public static Connection single_instance;
	public static Logger logger;


	public static Connection getInstance() {
		if (single_instance == null) {
			MySQLJDBCConnection dbConnection = new MySQLJDBCConnection();
			single_instance = dbConnection.setupConnection();
		}

		return single_instance;
	}

	public static void logInit() {
		Handler consoleHandler = null;

		Handler fileHandler = null;
		Formatter simpleFormatter = null;
		
		
		 logger = Logger.getLogger(Assignment2.class.getName());
		
		consoleHandler = new ConsoleHandler();
		try {
			fileHandler = new FileHandler("C:\\Users\\Meghashyam\\Documents\\GitHub\\A00432392_MCDA5510\\Assignment2\\Output\\result.log");
		} catch (SecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		logger.addHandler(consoleHandler);
		logger.addHandler(fileHandler);
		
		logger.setLevel(Level.ALL);
		simpleFormatter = new SimpleFormatter();
		
		// Setting formatter to the handler
		fileHandler.setFormatter(simpleFormatter);

		
	}
	

	public static void main(String[] args) {
		MySQLAccess dao = new MySQLAccess();
		
		
		int ID=32;
		String nameOnCard="Meghashyam";
		
		String cardNumber="5112345678901000";
		
		float unitPrice=20;
		
		int quantity=20;
		
		float totalPrice=0;
		
		totalPrice=unitPrice*(float)quantity;
		
		String expDate="12/2019";
		
		java.sql.Date createdOn = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		
		String createdBy="Meghashyam";
		logInit();
		
		try {
			 Connection connection = getInstance();
			 
			 Transaction trxn = new Transaction();
			 
			 trxn.setCardNumber(cardNumber);
			 trxn.setNameOnCard(nameOnCard);
			 trxn.setExpDate(expDate);
			 trxn.setQuantity(quantity);
			 trxn.setUnitPrice(unitPrice);
			 trxn.setCreatedBy(createdBy);
			 trxn.setCreatedOn(createdOn);
			 trxn.setID(ID);
			 
			ConnectionFactory factory = new ConnectionFactory();
			connection = factory.getConnection("mySQLJDBC");

			
			boolean flag= dao.validate(connection, trxn);
			
			if(flag) {
						
			 boolean Create_flag = dao.createTransaction(connection, trxn);   // Create Transaction (Insert)
			 
			 if(Create_flag) {
				 String msg= "Transaction is success, ID is ";
				 String Id= String.valueOf(trxn.getID());
				 logger.info(msg+Id);
				 System.out.println(msg+Id);
			 }
			//dao.updateTransaction(connection, trxn); // Update transaction
			//dao.deleteTransaction(connection, trxn);  // Delete transaction (Delete)
			 
						
			}
			else {
				System.out.println("Validation failed");
				
			}

			if (connection != null) {
				connection.close();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.severe(e.getMessage());
		}
	}

}
