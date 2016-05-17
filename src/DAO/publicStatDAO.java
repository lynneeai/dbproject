package DAO;

import java.util.*;
import java.sql.*;

import Core.publicStat;

public class publicStatDAO {
    private Connection myConn = connectDB.getConnection();
    private Statement myStmt = null;
    private ResultSet myRs = null;
    private publicStatInput publicStatInput = null;
    
    public publicStatDAO(publicStatInput publicStatInput) {
        this.publicStatInput = publicStatInput;
    }
    
    private publicStat convertRowToBook(ResultSet myRs) throws SQLException {	
	
        String query = this.publicStatInput.get_Query();
        String choice = this.publicStatInput.get_Choice();
        publicStat tempPublicStat = new publicStat();
        
        String Year; String Total;String Name; 
        String Currency; String AvgPublPrice; 
        
         switch(query)  {
            case "Average Number of Publications per Series": 
            case "Number of Comics with Size":

                Total = myRs.getString("Total");
                
                tempPublicStat.set_Total(Total);
                break;

            case "Average Price of Title with Most Publications":
                
                Name = myRs.getString("Name");
                Currency = myRs.getString("Currency");
                AvgPublPrice = myRs.getString("AvgPublPrice");
                
                tempPublicStat.set_Name(Name);
                tempPublicStat.set_Currency(Currency);
                tempPublicStat.set_AvgPublPrice(AvgPublPrice);
                break;
                
                
            case "Statistics On Publications Per Year": 
            case "Total Books Published In Year": 
            
                Total = myRs.getString("Total");
                Year = myRs.getString("Year");
                
                tempPublicStat.set_Total(Total);
                tempPublicStat.set_Year(Year);
                break;
            
	}
        
	return tempPublicStat;
        
    }
    
    public List<publicStat> showStat() {
	
        String query = this.publicStatInput.get_Query();   
	String showQuery;		
	List<publicStat> publicStatList = new ArrayList<>();
	
         switch(query) {
            case "Average Number of Publications per Series":
                showQuery = "SELECT AVG(COUNTS) as TOTAL " +
                            "FROM ( SELECT COUNT(PUBL_ID) AS COUNTS " +
                            "FROM PUBLICATIONS " +
                            "GROUP BY PUBL_SERIES_ID)";
                break;
            case "Average Price of Title with Most Publications":
                showQuery = "";
                break;
             case "Statistics On Publications Per Year": 
                showQuery = "SELECT extract(year from PUBL_DATE) AS YEAR, COUNT (Publ_ID) AS TOTAL " +
                            "FROM PUBLICATIONS " +
                            "WHERE extract(year from PUBL_DATE) IS NOT NULL " +
                            "GROUP BY extract(year from PUBL_DATE) " +
                            "ORDER BY extract(year from PUBL_DATE) DESC" ;
                break;
             default: 
                showQuery = "";
                break;
         }	
		
	System.out.println(showQuery);

		
	try {
            myStmt = myConn.createStatement();		
            myRs = myStmt.executeQuery(showQuery);
            
            while (myRs.next()) {
		publicStat publicStat = convertRowToBook(myRs);
		publicStatList.add(publicStat);
            }	
            
	}
	catch (SQLException e) {
            System.out.println("SQL exception occured " + e);
        } 
		
        return publicStatList;       
    
    }
    
    public List<publicStat> showInputStat() {
	String query = this.publicStatInput.get_Query();  
        String choice = this.publicStatInput.get_Choice();  
	String showInputQuery;		
	List<publicStat> publicStatList = new ArrayList<>();	
        
        switch(query) {
            case "Total Books Published In Year":
                showInputQuery = "SELECT extract(year from PUBL_DATE) AS YEAR, COUNT (PUBL_ID) AS TOTAL " +
                                 "FROM PUBLICATIONS " +
                                 "WHERE extract(year from PUBL_DATE) = " + choice + " " +
                                 "GROUP BY extract(year from PUBL_DATE)";
                break;
            case "Number of Comics with Size":
                if (choice.equals("Small (<50 pages)")) {
                    choice = "PUBL_PAGES < 50";
                }
                else if (choice.equals("Medium (<100 pages)")) {
                    choice = "PUBL_PAGES < 100";
                }
                else {
                    choice = "PUBL_PAGES >= 100";
                }
                showInputQuery = "SELECT COUNT(DISTINCT P.Publ_ID) as Total " +
                        "FROM PUBLICATIONS P, TITLES T, PUBLISHED_PUBL_TITLE PT " +
                        "WHERE PT.TITLE_ID = T.TITLE_ID AND PT.PUBL_ID = P.PUBL_ID " +
                        "AND T.TITLE_GRAPHIC = 'Yes' AND " + choice;
                break;
             default: 
                showInputQuery = "";
                break;
         }
						
	System.out.println(showInputQuery);
		
	try {
            myStmt = myConn.createStatement();		
            myRs = myStmt.executeQuery(showInputQuery);
            
            while (myRs.next()) {
		publicStat publicStat = convertRowToBook(myRs);
		publicStatList.add(publicStat);
            }	
            
	}
	catch (SQLException e) {
            System.out.println("SQL exception occured " + e);
        } 

	return publicStatList;           
    }
}
