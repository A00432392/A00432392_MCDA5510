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
	public static Logger error_logger;
	public static Logger output_logger;


	public static Connection getInstance() {
		if (single_instance == null) {
			MySQLJDBCConnection dbConnection = new MySQLJDBCConnection();
			single_instance = dbConnection.setupConnection();
		}

		return single_instance;
	}

	public static void logInit() {
		Handler error_consoleHandler = null;
		Handler output_consoleHandler = null;

		Handler error_fileHandler = null;
		Handler output_fileHandler = null;
		Formatter simpleFormatter = null;
		
		 output_logger = Logger.getLogger(Assignment2.class.getName());
		 error_logger = Logger.getLogger(Assignment2.class.getName());
		
		error_consoleHandler = new ConsoleHandler();
		try {
			output_fileHandler = new FileHandler("C:\\Users\\Meghashyam\\Documents\\GitHub\\A00432392_MCDA5510\\Assignment2\\Output\\output.log", true);
			error_fileHandler = new FileHandler("C:\\Users\\Meghashyam\\Documents\\GitHub\\A00432392_MCDA5510\\Assignment2\\Output\\error.log", true);
		} catch (SecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		
		//output_logger.addHandler(output_consoleHandler);
		output_logger.addHandler(output_fileHandler);
		
		error_logger.addHandler(error_consoleHandler);
		error_logger.addHandler(error_fileHandler);
		
		error_logger.setLevel(Level.ALL);
		output_logger.setLevel(Level.ALL);
		
		simpleFormatter = new SimpleFormatter();
		
		// Setting formatter to the handler
		error_fileHandler.setFormatter(simpleFormatter);
		output_fileHandler.setFormatter(simpleFormatter);

		
	}
	
public static void create_transaction(Boolean a, Transaction transaction)
{    if(a) {
	 String msg= "Transaction is success, ID is "+transaction.getID();
	 output_logger.info(msg);
	 System.out.println(msg);
			}
}

public static void update_transaction(Boolean a, Transaction transaction)
{    if(a) {
	 String msg= "Transaction " +transaction.getID()+" is successfully updated";
	 output_logger.info(msg);
	 System.out.println(msg);
			}
}

public static void delete_transaction(Boolean a, Transaction transaction)
{    if(a) {
	 String msg= "Transaction " +transaction.getID()+" is successfully deleted";
	 output_logger.info(msg);
	 System.out.println(msg);
			}
}


	public static void main(String[] args) {
		MySQLAccess dao = new MySQLAccess();
		
	//Static assignment of variables	
		int ID=500;
		String nameOnCard="MeghashyamR";
		
		String cardNumber="5112987654334569";
		
		float unitPrice=20;
		
		int quantity=20;
		
		float totalPrice=0;
		
		totalPrice=unitPrice*(float)quantity;
		
		String expDate="12/2018";
		
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
			
			
           //Create Transaction (Insert)
			 boolean Create_flag = dao.createTransaction(connection, trxn);  
			 create_transaction(Create_flag, trxn);
			 
			  
			 
	//Update Transaction - Uncomment below  to execute 
			 //boolean Update_flag= dao.updateTransaction(connection, trxn); 
			 //update_transaction(Update_flag, trxn);
			 
			  
	// Delete transaction (Delete) - Uncomment below to execute
			 boolean Delete_flag = dao.deleteTransaction(connection, trxn);  
			 delete_transaction(Delete_flag, trxn);
				
				
						
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
			error_logger.severe(e.getMessage());
		}
	}

	

}
