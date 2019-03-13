package com.mcda5510.dao;


/**
 * Original source code from 
 * http://www.vogella.com/tutorials/MySQLJava/article.html
 * 
**/

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.regex.*;

import com.mcda5510.entity.Transaction;
import com.sun.org.apache.xalan.internal.xsltc.compiler.Pattern;

public class MySQLAccess {



	public Collection<Transaction> getAllTransactions(Connection connection) {
		Statement statement = null;
		ResultSet resultSet = null;
		Collection<Transaction> results = new ArrayList<Transaction>();
		// Result set get the result of the SQL query
		try {
			// Statements allow to issue SQL queries to the database
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from m_rankireddi.TRANSACTION");
			results = createTrxns(resultSet);

			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			statement = null;
			resultSet = null;
		}
		return results;

	}

	private Collection<Transaction> createTrxns(ResultSet resultSet) throws SQLException {
		Collection<Transaction> results = new ArrayList<Transaction>();

		// ResultSet is initially before the first data set
		while (resultSet.next()) {
			// It is possible to get the columns via name
			// also possible to get the columns via the column number
			// which starts at 1
			// e.g. resultSet.getSTring(2);
			Transaction trxn = new Transaction();
			trxn.setID(resultSet.getInt(1));
			trxn.setNameOnCard(resultSet.getString(2));
			trxn.setCardNumber(resultSet.getString(3));
			trxn.setUnitPrice(resultSet.getFloat(4));
			trxn.setQuantity(resultSet.getInt(5));
			trxn.setTotalPrice(resultSet.getFloat(6));
			trxn.setExpDate(resultSet.getString(7));
			trxn.setCreatedOn(resultSet.getDate(8));
			trxn.setCreatedBy(resultSet.getString(9));
			
			
			results.add(trxn);

			// TODO
			/*
			 *  String ID = resultSet.getString("ID");
			 *  String ExpDate = resultSet.getString("ExpDate"); 
			 *  String UnitPrice = resultSet.getString("UnitPrice"); 
			 *  Integer qty = resultSet.getInt("Quantity"); 
			 *  String totalPrice = resultSet.getString("TotalPrice"); 
			 *  Date createdOn = resultSet.getDate("CreatedOn"); 
			 *  String createdBy = resultSet.getString("CreatedBy");
			 */

		}

		return results;

	}

	public boolean createTransaction(Connection connection,Transaction trxn) throws SQLException {
		// DO the update SQL here
		Statement statement = null;
		boolean flag= false;
		try {
		
			//statement = connection.createStatement();
		    String query = "INSERT INTO TRANSACTION(ID, NameOnCard, CARDNUMBER, UNITPRICE, QUANTITY, TOTALPRICE, EXPDATE, CREATEDON, CREATEDBY)"
					        + " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		    
		    
					      // create the mysql insert preparedstatement
					      PreparedStatement preparedStmt;
						
							preparedStmt = connection.prepareStatement(query);
					
							// TODO Auto-generated catch block
							
					      preparedStmt.setInt(1, trxn.getID());
					      preparedStmt.setString (2, trxn.getNameOnCard());
					      preparedStmt.setString (3, trxn.getCardNumber());
					      preparedStmt.setFloat(4, trxn.getUnitPrice());
					      preparedStmt.setInt(5, trxn.getQuantity());
					      preparedStmt.setFloat(6, trxn.getTotalPrice());
					      preparedStmt.setString(7, trxn.getExpDate());
					      preparedStmt.setDate(8, trxn.getCreatedOn());
					      preparedStmt.setString(9, trxn.getCreatedBy());
					      
					      int i = preparedStmt.executeUpdate();
					      
					      if(i>=1) {
					    	  flag=true;
					    	  System.out.println("Record inserted successfully with ID : " + trxn.getID() );
					    	  
					    	 
					    	  					      }
					      else {
					    	  System.out.println("Unsuccessful insert !");
					      }
		}
		catch(Exception e ) {
			e.printStackTrace();
		}
		
		
		return flag;

		
	}
	
	public boolean deleteTransaction(Connection connection,Transaction trxn) throws SQLException {
		// DO the delete SQL here
		Statement statement = null;
		boolean flag= false;
		
		try {
		System.out.println("Please input the ID #");
		Scanner in = new Scanner(System.in);
		  
		  int inp = in.nextInt();
		  
		  trxn.setID(inp);
		
			//statement = connection.createStatement();
		    String query = "DELETE FROM TRANSACTION WHERE ID=?";
		    
		    
					      // create the mysql insert preparedstatement
					      PreparedStatement preparedStmt;
						
							preparedStmt = connection.prepareStatement(query);
					
							// TODO Auto-generated catch block
							
					      preparedStmt.setInt(1, trxn.getID());
					      
					      
					      int i = preparedStmt.executeUpdate();
					      
					      if(i>=1) {
					    	  flag=true;
					    	  System.out.println("Record deleted successfully with ID : " + trxn.getID() );
					    	  					      }
					      else {
					    	  System.out.println("Unsuccessful delete !");
					      }
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return flag;
		
	}
	
	public boolean validate(Connection connection, Transaction trxn) throws SQLException,Exception
	{ 
		boolean flag1=false;
		boolean flag2=false;
		boolean flag3=false;
		boolean flag=false;
		boolean flag5= true;
		boolean flag6= true;
		String regex = "[\\;\\:\\!\\@\\#\\$\\%\\^\\*\\+\\?\\<\\>]+";
		java.util.regex.Pattern pat = java.util.regex.Pattern.compile(regex);
		Matcher nameOnCard_Match = pat.matcher(trxn.getNameOnCard());
        Matcher cardNumber_Match = pat.matcher(trxn.getCardNumber().toString());
        
        
        if (nameOnCard_Match.find()) {
               flag5=false;
               System.out.println("Please check your name on the card again.");
               throw new Exception("Invalid name given, Please remove special characters.");
        }
        if (cardNumber_Match.find() || !(trxn.getCardNumber().toString().matches("[0-9]+")) ) {
               flag6 = false;
               System.out.println("Please check your cardnumber again.");
               throw new Exception("Invalid cardnumber. Card number has characters other than numbers.");
        }
        
        

		
		String year[] = new String[10]; 
		ResultSet result=null;
		String cardname=null;
		result = retrieve(connection);
		int validlength =0;
		String z= String.valueOf(trxn.getCardNumber());
		z = z.substring(0, 2);
		int k = Integer.parseInt(z);
		//System.out.println(k);
		
		year = trxn.getExpDate().split("/");
		
		//for(String l : year) {
			//System.out.println(l);
		//}
		
		int firstpart = Integer.parseInt(year[0]);
		int secondpart = Integer.parseInt(year[1]);
		
		
		ArrayList<Integer> yearlist = new ArrayList<Integer>(); 
		ArrayList<String> card = new ArrayList<String>();
		ArrayList<Integer> prefix = new ArrayList<Integer>();
		ArrayList<Integer> length  = new ArrayList<Integer>();
		try {
			while (result.next()) {
			    card.add(result.getString(1));
			    prefix.add(result.getInt(2));
			    length.add(result.getInt(3));		    
			}
			int count =0;
			
			for(int i : prefix)
			{
				if (i==k) {
					//System.out.println("prefix:"+i +", "+"input:"+k);
					int m=prefix.indexOf(k);
					//System.out.println("Index of length is"+m);
					validlength = length.get(m);
					cardname=card.get(m);
					count++;
					
				}
			}
					 
			if(count==0) {
				
				String errormsg = "Please enter a valid credit card number.";
				flag1 = false;
				throw  new Exception(errormsg);
				
			}	else flag1=true;
			
			if(count==1 && trxn.getCardNumber().length()!=validlength) {
				
				String errormsg ="Please check the length of the card number proplerly.";
				flag2 = false;
				throw new Exception(errormsg);
				
			}
			else flag2 =true;
			
			if((firstpart>=1 && firstpart<=12) && (secondpart>=2016 && secondpart<=2031))
			{
				flag3=true;
			}
			else {
				flag3=false;
				System.out.println("Enter valid expiry year");
			}
				
				
			if(flag1 && flag2 && flag3 && flag5 && flag6 ) {
				flag = true;
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return flag;
	}
	
	
	private ResultSet retrieve(Connection connection)
	{
		ResultSet result=null;
		Statement statement2 = null;
		try {
			statement2 = connection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			result = statement2.executeQuery("select * from m_rankireddi.CARD");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

	
	public boolean updateTransaction(Connection connection,Transaction trxn) throws SQLException {
		// DO the update SQL here
		Statement statement = null;
		boolean flag= false;
		 int input;		
		 System.out.println("Please input the ID #");
		  Scanner in = new Scanner(System.in);
		  
		  input = in.nextInt();
		  
		  trxn.setID(input);
		
			//statement = connection.createStatement();
		    String query = "update TRANSACTION set NAMEONCARD=?, CARDNUMBER=?, UNITPRICE=?, QUANTITY=?, TOTALPRICE=?, EXPDATE=?, CREATEDON=?, CREATEDBY=? where ID = ?";
		    
		    
					      // create the mysql insert preparedstatement
					      PreparedStatement preparedStmt;
						
							preparedStmt = connection.prepareStatement(query);
					
							// TODO Auto-generated catch block
							
					      
					      preparedStmt.setString (1, trxn.getNameOnCard());
					      preparedStmt.setString (2, trxn.getCardNumber());
					      preparedStmt.setFloat(3, trxn.getUnitPrice());
					      preparedStmt.setInt(4, trxn.getQuantity());
					      preparedStmt.setFloat(5, trxn.getTotalPrice());
					      preparedStmt.setString(6, trxn.getExpDate());
					      preparedStmt.setDate(7, trxn.getCreatedOn());
					      preparedStmt.setString(8, trxn.getCreatedBy());
					      preparedStmt.setInt(9, trxn.getID());
					      
					      int i = preparedStmt.executeUpdate();
					      
					      if(i>=1) {
					    	  flag=true;
					    	  System.out.println("Record updated successfully !");
					    	  					      }
					      else {
					    	  System.out.println("Unsuccessful update !");
					      }
			
		
		
		return flag;
	}

	

	

	
}
