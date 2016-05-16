package DAO;

import java.util.*;
import java.sql.*;

import Core.AuthorTab;
import Core.Publisher;
import DAO.PublisherInput;

public class PublisherDAO {
	private Connection myConn = connectDB.getConnection();
    private Statement myStmt = null;
    private ResultSet myRs = null;
    

    private Publisher convertRowToPublisher_Price(ResultSet myRs) throws SQLException {
    	String name = myRs.getString("PUBLISHER_NAME");
    	String price = myRs.getString("AVG_PRICE");
    	
    	return new Publisher(name, price, 0);
    }
    
    private Publisher convertRowToPublisher_Publ(ResultSet myRs) throws SQLException {
    	String name = myRs.getString("PUBLISHER_NAME");
    	String num = myRs.getString("NUM_PUBL");
    	
    	return new Publisher(name, num, 1);
    }
    
    
    private Publisher convertRowToPublisher_Author(ResultSet myRs) throws SQLException {
    	String year = myRs.getString("YEAR");
    	String num = myRs.getString("Average Number of Authors");
    	
    	return new Publisher(year, num, 2);
    }
    
    
    
    public List<Publisher> searchPublisher_Price(PublisherInput publisherInput) {
    	String query = "";
    	List<Publisher> publisherList = new ArrayList<>();
    	
    	query = "SELECT P2.PUBLISHER_NAME, ROUND(AVG(P1.PRICE), 4) AS AVG_PRICE " +
    			"FROM PUBLICATIONS P1, PUBLISHERS P2 " +
    			"WHERE P1.PUBLISHER_ID = P2.PUBLISHER_ID AND P1.PUBL_TYPE = 'NOVEL' AND PRICE_CURRENCY = '$' " +
    			"GROUP BY P2.PUBLISHER_ID, P2.PUBLISHER_NAME";
    	
    	System.out.println(query);
    	
    	try {
            myStmt = myConn.createStatement();		
            myRs = myStmt.executeQuery(query);
            
            while (myRs.next()) {
            	Publisher tempPublisher = convertRowToPublisher_Price(myRs);
            	publisherList.add(tempPublisher);
            }
            
		}
		catch (SQLException e) {
            System.out.println("SQL exception occured " + e);
        }
    	
    	return publisherList;
    }
    
    public List<Publisher> searchPublisher_Publ(PublisherInput publisherInput) {
    	String query = "";
    	List<Publisher> publisherList = new ArrayList<>();
    	
    	query = "SELECT PUBLISHER_NAME, NUM_PUBL " +
    			"FROM (SELECT P.PUBLISHER_ID, COUNT(DISTINCT PUBL_ID) AS NUM_PUBL " +
    				  "FROM PUBLISHERS P , PUBLICATIONS P2 " +
    				  "WHERE extract(year from P2.PUBL_DATE) = " + publisherInput.get_Year() + " " +
    				  		"AND P.PUBLISHER_ID = P2.PUBLISHER_ID " +
    				  "GROUP BY P.PUBLISHER_ID " +
    				  "ORDER BY NUM_PUBL DESC) N, PUBLISHERS P " +
				"WHERE N.PUBLISHER_ID = P.PUBLISHER_ID AND ROWNUM <= 3";
    	
    	System.out.println(query);
    	
    	try {
            myStmt = myConn.createStatement();		
            myRs = myStmt.executeQuery(query);
            
            while (myRs.next()) {
            	Publisher tempPublisher = convertRowToPublisher_Publ(myRs);
            	publisherList.add(tempPublisher);
            }
            
		}
		catch (SQLException e) {
            System.out.println("SQL exception occured " + e);
        }
    	
    	return publisherList;
    }
    
    public List<Publisher> searchPublisher_Author(PublisherInput publisherInput) {
    	String query = "";
    	List<Publisher> publisherList = new ArrayList<>();
    	
    	query = "SELECT DISTINCT EXTRACT(YEAR FROM PUBL_DATE) AS YEAR, " +
    							"ROUND(AVG(COUNT(AUTHOR_ID)) over(PARTITION BY EXTRACT(YEAR FROM PUBL_DATE)), 4) \"Average Number of Authors\"" +
    			"FROM (SELECT PUBLICATIONS.PUBL_ID, PUBL_DATE, PUBLICATIONS.PUBLISHER_ID " +
    				  "FROM PUBLICATIONS " +
    				  "INNER JOIN PUBLISHERS ON PUBLICATIONS.PUBLISHER_ID = PUBLISHERS.PUBLISHER_ID " +
    				  "WHERE EXTRACT(YEAR FROM PUBL_DATE) IS NOT NULL) Y " +
    			"INNER JOIN WRITTEN_PUBL_AUT ON WRITTEN_PUBL_AUT.PUBL_ID = Y.PUBL_ID " +
    			"GROUP BY EXTRACT(YEAR FROM PUBL_DATE), PUBLISHER_ID " +
    			"ORDER BY EXTRACT(YEAR FROM PUBL_DATE) DESC";
    	
    	System.out.println(query);
    	
    	try {
            myStmt = myConn.createStatement();		
            myRs = myStmt.executeQuery(query);
            
            while (myRs.next()) {
            	Publisher tempPublisher = convertRowToPublisher_Author(myRs);
            	publisherList.add(tempPublisher);
            }
            
		}
		catch (SQLException e) {
            System.out.println("SQL exception occured " + e);
        }
    	
    	return publisherList;
    }
    
}