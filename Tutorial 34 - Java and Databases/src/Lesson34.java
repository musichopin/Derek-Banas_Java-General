// The API for accessing and processing data stored in a database

import java.sql.*;

public class Lesson34 {
    public static void main(String[] args) {
    	
    	// A connection object is used to provide access to a database
    	
    	Connection conn = null;
    	
        try {
            // The driver allows you to query the database with Java
        	// forName dynamically loads the class for you
           
            Class.forName("com.mysql.jdbc.Driver");
            
            // DriverManager is used to handle a set of JDBC drivers
            // getConnection establishes a connection to the database
            // You must also pass the user id and password for the database
            
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/customer?autoReconnect=true&useSSL=false","root","");

            // Statement objects execute a SQL query
            // createStatement returns a Statement object
            
            Statement sqlState = conn.createStatement();
            
            // This is the query I'm sending to the database
//            queries are questions asked to the databases
            String selectStuff = "Select first_name from customer";
            
            // A ResultSet contains a table of data representing the
            // results of the query. It can not be changed and can 
            // only be read in one direction
            
            ResultSet rows = sqlState.executeQuery(selectStuff);
            
            // next is used to iterate through the results of a query
            
            while(rows.next()){
            	System.out.println(rows.getString("first_name"));
//            	column/field name is first_name
            }
        }
        
        catch (SQLException ex) {
            
        	// String describing the error
        	
            System.out.println("SQLException: " + ex.getMessage());
            
            // Vendor specific error code
            
            System.out.println("VendorError: " + ex.getErrorCode());
        } 
        
        catch (ClassNotFoundException e) {
			// Executes if the driver can't be found
			e.printStackTrace();
		} 
        
        finally {
        	
        	try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        	
        }
        
    }
    
}

/*
Derek
Sally
Paul
Mark
*/