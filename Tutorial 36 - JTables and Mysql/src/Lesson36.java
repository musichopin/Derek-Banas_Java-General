import java.awt.BorderLayout;
import java.awt.Font;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.table.DefaultTableCellRenderer; // render info in each cell of the table
import javax.swing.table.DefaultTableModel; // define methods etc inside jtable
import javax.swing.table.TableColumn; // make specific changes to columns

// *we are trying to find 200 best players with minimum cost per run
// this tutorial wud normally be in MVC design pattern*

public class Lesson36{
//	*Static olan main metod içerisinde this kw 
//	kullanılamayacağından jframe extend edilmiyor
//	ve frame e ait instance variable oluşturuluyor*
	
	// Used to hold the column data for each player
//	(Holds row values for the table)
//	md object array
	static Object[][] databaseInfo;
	
	// holds the column titles for the JTable
//	TTRC: theoretical teams run created, CPR: cost per run
//	jtable için
    static Object[] columns = {"Year", "PlayerID", "Name", "TTRC", "Team", "Salary", "CPR", "POS"};
//    *databaseInfo ile columns, DefaultTableModel'a pass ediliyor*
    
    // A ResultSet contains a table of data filled 
    // with the results of the query.
//    (lesson 34'de de vardı)
    static ResultSet rows;
//    *any mention of rows is a mention of db to do sth*
    
    // ResultSetMetaData contains information on
    // the data returned by the query (e.g. no of columns etc)
    
    static ResultSetMetaData metaData; // !not used in this tutorial
    
    // DefaultTableModel defines the methods JTable will use
    // DTM is used so we can manipulate the data in the JTable.﻿
    // I'm overriding the getColumnClass (with anonymous inner class)
//    *DefaultTableModel allows us to specifically edit our jtable*
//    *with DefaultTableModel whenever the class values are passed from the db 
//    into our table we check to see what data type they are 
//    (which then allows us to do sorting and other neat things for numerics).
//    we pass the values and column names into the DTM constructor*
    static DefaultTableModel dTableModel = new DefaultTableModel(databaseInfo, columns){
//    	*getColumnClass method is passed a column number and what it does is it exchanges 
//    	and passes back what type of data type that column is*
        public Class getColumnClass(int column) {
//        	*if we didn't override the getColumnClass method it would 
//        	treat every thing we would pull from the database as a string*
            Class returnValue;
            
            // Verifying that the column exists (index > 0 && index < number of columns
            
            if ((column >= 0) && (column < getColumnCount())) {
              returnValue = getValueAt(0, column).getClass();
//         ***returns the class type for the column***
            } else {
              // if we are looking for a column that doesn't exist	
            	
              returnValue = Object.class; // returns by default
            }
            return returnValue;
          }
        };
	
	public static void main(String[] args){
		
		JFrame frame = new JFrame();
		
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
    	// A connection object is used to provide access to a database
	    Connection conn = null;
    	
        try {
            // The driver allows you to query the database with Java
        	// forName dynamically loads the class for you
           
            Class.forName("com.mysql.jdbc.Driver");
            
            // DriverManager is used to handle a set of JDBC drivers
            // getConnection establishes a connection to the database
            // You must also pass the userid and password for the database
            
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lahman591?autoReconnect=true&useSSL=false","root","");
            
            // Statement objects executes a SQL query
            // createStatement returns a Statement object
            
            Statement sqlState = conn.createStatement();
            
            // This is the query I'm sending to the database
           
            String selectStuff = "select b.yearID, b.playerID, " +
            		"CONCAT(m.nameFirst, ' ', m.nameLast) AS Name, " + 
            		"((b.H+b.BB)+(2.4*(b.AB+b.BB)))*(t.TB+(3*(b.AB+b.BB)))/(9*(b.AB+b.BB))-(.9*(b.AB+b.BB)) AS TTRC, " +
            		"b.teamID AS Team, s.salary AS Salary, " +
            		"CAST( s.salary/(((b.H+b.BB)+(2.4*(b.AB+b.BB)))*(t.TB+(3*(b.AB+b.BB)))/(9*(b.AB+b.BB))-(.9*(b.AB+b.BB))) as decimal(10,2)) AS CPR, " +
            		"f.POS AS POS FROM Batting b, Master m, Salaries s, TOTBYR t, Fielding f " +
            		"WHERE b.playerID = m.playerID AND t.playerID = m.playerID " +
            		"AND t.yearID = 2010 AND b.yearID = t.yearID AND s.playerID = b.playerID " + 
            		"AND s.yearID = b.yearID AND b.AB > 50 AND b.playerID = f.playerID " +
            		"AND b.playerID = t.playerID GROUP BY b.playerID ORDER BY TTRC DESC LIMIT 200;";
            		
            /* Have to cut out salary because it isn't in database for 2011 
            String selectStuff = "select b.yearID, b.playerID, " +
            		"CONCAT(m.nameFirst, ' ', m.nameLast) AS NAME, " + 
            		"((b.H+b.BB)+(2.4*(b.AB+b.BB)))*(t.TB+(3*(b.AB+b.BB)))/(9*(b.AB+b.BB))-(.9*(b.AB+b.BB)) AS TTRC, " +
            		"b.teamID AS Team, 0 AS Salary, " +
            		"0 AS CPR, f.POS AS POS " +
            		"FROM Batting b, Master m, Fielding f, TOTBYR as t " +
            		"WHERE b.playerID = m.playerID AND t.playerID = m.playerID " +
            		"AND t.yearID = 2011 AND b.yearID = t.yearID " + 
            		"AND b.AB > 100 AND f.playerID = b.playerID " +
            		"GROUP BY b.playerID ORDER BY TTRC DESC LIMIT 200;";
            */
            
            // A ResultSet contains a table of data representing the
            // results of the query. It can not be changed and can 
            // only be read in one direction
            
            rows = sqlState.executeQuery(selectStuff);
            
            /* 
            int numOfCol;
            
            // *metaData, retrieves the number, types and properties of the Query Results*
            
            metaData = rows.getMetaData();
            
            // Returns the number of columns
            
            numOfCol = metaData.getColumnCount();
            
            // One way to get the column titles
            
            columns = new String[numOfCol]; // declare and instantiate array
            
            for(int i=1; i<=numOfCol; i++) // iteration starts from 1 at jtable
            {
            
            	// Returns the column name
            
            	columns[i] = metaData.getColumnName(i); // initialize
            	System.out.println(columns[i]);
            }
            */
            
            // *Temporarily holds the row results in order to pass to the 
//            addRow method of dTableModel which we overriden before*
            Object[] tempRow; // create array
            
         // next is used to iterate through the results of a query
            
            while(rows.next()){ // loops row by row
            	
            	// *Gets the column values based on class type expected
//            	if table consisted of string our job would be easier
//            	instantiates and initializes the array*
            	tempRow = new Object[]{rows.getInt(1), rows.getString(2), rows.getString(3),
            		rows.getDouble(4), rows.getString(5), rows.getInt(6), rows.getDouble(7),
            		rows.getString(8)};
//            	*for jtable index starts at 1 (with an integer in this example)*
            	
            	// *Adds the row of data to the end of the model*
            	
            	dTableModel.addRow(tempRow);
            	
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
        
        
        // Create a JTable component using the custom DefaultTableModel
//	    adds DefaultTableModel to the JTable
        JTable table = new JTable(dTableModel);
        
        // Increase the font size for the cells in the table
        
        table.setFont(new Font("Serif", Font.PLAIN, 20));
        
        // Increase the size of the cells to allow for bigger fonts
        
        table.setRowHeight(table.getRowHeight()+10);
	    
	    // Allows the user to sort the data
	    
	    table.setAutoCreateRowSorter(true);

        /* If you want to right justify column
         * 
	    TableColumn tc = table.getColumn("TTRC");
	    RightTableCellRenderer rightRenderer = new RightTableCellRenderer();
	    tc.setCellRenderer(rightRenderer);
	    */
	    
	    // Disable auto resizing
//	    otherwise we wudnt adjust the column size
	    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

	    // Set the width for the columns
//	    *not every column resized*
	    TableColumn col1 = table.getColumnModel().getColumn(0);
	    // we can use 0 index now
	    col1.setPreferredWidth(100);
	    
	    TableColumn col2 = table.getColumnModel().getColumn(1);
	    col2.setPreferredWidth(190);
	    
	    TableColumn col3 = table.getColumnModel().getColumn(2);
	    col3.setPreferredWidth(260);
	    
	    TableColumn col5 = table.getColumnModel().getColumn(5);
	    col5.setPreferredWidth(200);
	    
	    TableColumn col6 = table.getColumnModel().getColumn(6);
	    col6.setPreferredWidth(200);
	    
	    // Change justification of column to Center
//	    *we can also access columns by their field name besides index*
	    TableColumn tc = table.getColumn("Team");
	    CenterTableCellRenderer centerRenderer = new CenterTableCellRenderer();
	    tc.setCellRenderer(centerRenderer);
	    
//	    *we are overriding the previously created ref vars*
	    tc = table.getColumn("POS");
	    centerRenderer = new CenterTableCellRenderer();
	    tc.setCellRenderer(centerRenderer);
	    
//	    adding table to the scroll pane
	    JScrollPane scrollPane = new JScrollPane(table);
	    frame.add(scrollPane, BorderLayout.CENTER);
	    frame.setSize(800, 500);
	    frame.setVisible(true);
		
	} // END of Main
	
} // END of Class

// How to change justification to the right

class RightTableCellRenderer extends DefaultTableCellRenderer {
	  public RightTableCellRenderer() {
	    setHorizontalAlignment(JLabel.RIGHT);  
	  }   
	    
}   

// Change justification to the center
//*creates the method to handle table centering (not event handling)*
class CenterTableCellRenderer extends DefaultTableCellRenderer {   
	  public CenterTableCellRenderer() {  
	    setHorizontalAlignment(JLabel.CENTER);  
	  }   
	    
}   
// The API for accessing and processing data stored in a database