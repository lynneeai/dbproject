package DAO;

import java.util.*;
import java.sql.*;

import Core.publicStat;

public class publicStatDAO {
    private Connection myConn = connectDB.getConnection();
    private Statement myStmt = null;
    private ResultSet myRs = null;
    private String Year;
    
    public publicStatDAO(String Year) {
        this.Year = Year;
    }
    
    private publicStat convertRowToBook(ResultSet myRs) throws SQLException {	
	
        try {
            this.Year = myRs.getString("Year");
        }
        catch (SQLException e) {
        } 
	
	String Count = myRs.getString("Count");
        
        publicStat tempPublicStat = new publicStat(this.Year, Count);
              
	return tempPublicStat;
        
    }
    
    public List<publicStat> searchStat(publicStatInput input) {
	String select = "SELECT COUNT (Publ_ID) as Count ";
	String from = "FROM PUBLICATIONS ";
	String where = "WHERE extract(year from PUBL_DATE) = ";
		
	List<publicStat> statList = new ArrayList<>();
		
	if (input.get_Year() != null) {
            where = where + input.get_Year() + " ";
	}		
		
	System.out.println(select);
	System.out.println(from);
	System.out.println(where);
		
	try {
            myStmt = myConn.createStatement();		
            myRs = myStmt.executeQuery(select + from + where);
            
            while (myRs.next()) {
		publicStat tempPublicStat = convertRowToBook(myRs);
		statList.add(tempPublicStat);
            }	
            
	}
	catch (SQLException e) {
            System.out.println("SQL exception occured " + e);
        } 
		
        return statList;       
    
    }
    
    public List<publicStat> showAllStat(publicStatInput input) {
	String select = "SELECT extract(year from PUBL_DATE) as Year, COUNT (Publ_ID) as Count ";
	String from = "FROM PUBLICATIONS ";
        String group = "GROUP BY extract(year from PUBL_DATE) ";
	String order = "ORDER BY extract(year from PUBL_DATE) DESC ";
		
	List<publicStat> statList = new ArrayList<>();		
		
	System.out.println(select);
	System.out.println(from);
	System.out.println(group);
        System.out.println(order);
		
	try {
            myStmt = myConn.createStatement();		
            myRs = myStmt.executeQuery(select + from + group + order);
            
            while (myRs.next()) {
		publicStat tempPublicStat = convertRowToBook(myRs);
		statList.add(tempPublicStat);
            }	
            
	}
	catch (SQLException e) {
            System.out.println("SQL exception occured " + e);
        } 

	return statList;           
    }
}
