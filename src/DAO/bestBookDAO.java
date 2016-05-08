package DAO;

import java.util.*;
import java.sql.*;

import Core.bestBook;

public class bestBookDAO {
    
    private Connection myConn = connectDB.getConnection();
    private Statement myStmt = null;
    private ResultSet myRs = null;
    private bestBookInput bestBookInput = null;
    
    public bestBookDAO(bestBookInput bestBookInput) {
        this.bestBookInput = bestBookInput;
    }
    
    
    private bestBook convertRowToBook(ResultSet myRs) throws SQLException {
        
        String query = this.bestBookInput.get_Query();
        String input = this.bestBookInput.get_TextInput();
        bestBook tempBestBook = new bestBook();
        
        String Name; String Number; String Currency; String AvgPublPrice; 
        String AuthorName; String TitleName; String NumReviews;
	
        switch(query)  {
            case "Most Reviewed Books":
            case "Most Awarded Books": 
            case "Most Awarded Series": 
            case "Most Translated Book Types":
            case "Most extensive web presence awarded as ":
            case "Publication series with most book awarded as ": 
            
                Name = myRs.getString("Name");
                Number = myRs.getString("Total");
            
                tempBestBook.set_Name(Name);
                tempBestBook.set_Number(Number);
                break;
          
            case "Most Popular Books":
            
                Name = myRs.getString("Name");
                Currency = myRs.getString("Currency");
                AvgPublPrice = myRs.getString("AvgPublPrice");
            
                tempBestBook.set_Name(Name);
                tempBestBook.set_Currency(Currency);
                tempBestBook.set_AvgPublPrice(AvgPublPrice);
                break;

            case "  Most reviewed book of author ":
            
                TitleName = myRs.getString("TitleName");
                NumReviews = myRs.getString("NumReviews");
            
                tempBestBook.set_AuthorName(input);
                tempBestBook.set_TitleName(TitleName);
                tempBestBook.set_NumReviews(NumReviews);
                break;
            
	}
              
	return tempBestBook;
        
    }
    
    public List<bestBook> mostBook() {
        
        String query = this.bestBookInput.get_Query();   
	String mostQuery;		
	List<bestBook> bestBookList = new ArrayList<>();	
        
        switch(query) {
            case "Most Reviewed Books":
                mostQuery = "";    
                break;
            case"Most Awarded Books":
                mostQuery = "";
                break;
            case "Most Awarded Series":
                mostQuery = "SELECT TITLE_SERIES_TITLE as Name, NUM_AWARDS as TOTAL " +
                            "FROM ( SELECT TITLE_SERIES_ID, COUNT (AWARD_ID) AS NUM_AWARDS " +
                            "FROM WON_TITLE_AWD W, TITLES " +
                            "WHERE TITLES.TITLE_ID = W.TITLE_ID " +
                            "GROUP BY TITLE_SERIES_ID " +
                            "ORDER BY NUM_AWARDS DESC) A , TITLE_SERIES T " +
                            "WHERE A.TITLE_SERIES_ID = T.TITLE_SERIES_ID AND ROWNUM <= 10 ";
                break;
            case "Most Translated Book Types":
                mostQuery = "SELECT * FROM ( SELECT TITLE_TYPE as Name, COUNT(TITLE_ID) AS Total " +
                            "FROM (SELECT TITLE_TYPE, TITLE_ID, TRANS_PARENT, LANGUAGE_ID " +
                            "FROM titles WHERE NOT TRANS_PARENT = 0) " + 
                            "GROUP BY TITLE_TYPE " +  "ORDER BY TOTAL DESC) " +
                            "WHERE ROWNUM <= 10";
                break;
            case "Most Popular Books":
                mostQuery = "";
                break;
            default: 
                mostQuery = "";
                break;
        }
		
	System.out.println(mostQuery);
		
	try {
            myStmt = myConn.createStatement();		
            myRs = myStmt.executeQuery(mostQuery);
            
            while (myRs.next()) {
		bestBook bestBook = convertRowToBook(myRs);
		bestBookList.add(bestBook);
            }	
            
	}
	catch (SQLException e) {
            System.out.println("SQL exception occured " + e);
        } 
		
        return bestBookList;       
    
    }
    
    public List<bestBook> mostInputBook() {
        
        String query = this.bestBookInput.get_Query();  
        String textInput = this.bestBookInput.get_TextInput();  
	String mostInputQuery;		
	List<bestBook> bestBookList = new ArrayList<>();	
        
        switch(query) {
            case "Most extensive web presence awarded as ":
                mostInputQuery = "";    
                break;
            case "  Most reviewed book of author ":
                mostInputQuery = "SELECT T.TITLE as TitleName, R.NUM_REVIEWS as NumReviews " +
                                 "FROM TITLES T,( " +
                                 "SELECT TITLE_ID, COUNT (DISTINCT REVIEW_ID) AS NUM_REVIEWS " +
                                 "FROM TITLE_REVIEWS WHERE TITLE_ID IN ( " +
                                 "SELECT T.TITLE_ID " +
                                 "FROM AUTHORS A,  WRITTEN_PUBL_AUT WP, PUBLICATIONS P, PUBLISHED_PUBL_TITLE PT, TITLES T " +
                                 "WHERE A.AUTHOR_NAME = '" + textInput + "' " +
                                 "AND A.AUTHOR_ID = WP.AUTHOR_ID AND P.PUBL_ID = WP.PUBL_ID " +
                                 "AND PT.PUBL_ID = P.PUBL_ID AND T.TITLE_ID = PT.TITLE_ID) " +
                                 "GROUP BY TITLE_ID ORDER BY NUM_REVIEWS DESC ) R " +
                                 "WHERE T.TITLE_ID = R.TITLE_ID AND ROWNUM = 1";
                break;
            case "Publication series with most book awarded as ":
                mostInputQuery = "SELECT PUBL_SERIES_NAME as Name, NUM_TITLE_AWARDED as Total FROM ( " + 
                                 "SELECT * FROM ( " +
                                 "SELECT TITLE_SERIES_ID, PUBL_SERIES_ID, PUBL_SERIES_NAME " +
                                 "FROM TITLE_SERIES " +
                                 "INNER JOIN PUBL_SERIES " +
                                 "ON PUBL_SERIES.PUBL_SERIES_NAME = TITLE_SERIES.TITLE_SERIES_TITLE) A " +
                                 "INNER JOIN ( " +
                                 "SELECT COUNT(TITLE_ID) AS NUM_TITLE_AWARDED, TITLE_SERIES_ID " +
                                 "FROM ( " +
                                 "SELECT AWARD_ID, Y.TITLE_ID, TITLE_SERIES_ID FROM ( " +
                                 "SELECT X.AWARD_ID, AWARD_TITLE, AWARD_TYPE_NAME, TITLE_ID FROM ( " +
                                 "SELECT AWARDS.AWARD_ID, AWARDS.AWARD_TITLE, AWARDS.AWARD_TYPE_ID, AWARD_TYPE_NAME " +
                                 "FROM AWARDS INNER JOIN AWARD_TYPES " +
                                 "ON AWARDS.AWARD_TYPE_ID = AWARD_TYPES.AWARD_TYPE_ID " +
                                 "WHERE AWARD_TYPE_NAME = '" + textInput + "') X " +
                                 "INNER JOIN WON_TITLE_AWD ON WON_TITLE_AWD.AWARD_ID = X.AWARD_ID) Y " +
                                 "INNER JOIN (SELECT TITLE_ID, TITLE_SERIES_ID FROM TITLES " +
                                 "WHERE NOT TITLE_SERIES_ID IS NULL) Z " +
                                 "ON Y.TITLE_ID = Z.TITLE_ID ) " +
                                 "GROUP BY TITLE_SERIES_ID ) B " +
                                 "ON A.TITLE_SERIES_ID = B.TITLE_SERIES_ID " +
                                 "ORDER BY NUM_TITLE_AWARDED DESC ) " +
                                 "WHERE ROWNUM <= 1";
                break;
            default: 
                mostInputQuery = "";
                break;
        }
		
	System.out.println(mostInputQuery);
		
	try {
            myStmt = myConn.createStatement();		
            myRs = myStmt.executeQuery(mostInputQuery);
            
            while (myRs.next()) {
		bestBook bestBook = convertRowToBook(myRs);
		bestBookList.add(bestBook);
            }	
            
	}
	catch (SQLException e) {
            System.out.println("SQL exception occured " + e);
        } 
		
        return bestBookList;       
    
    }   
    
}
