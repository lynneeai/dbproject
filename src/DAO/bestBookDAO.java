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
        
        String Name; String Number; String TitleName; String NumReviews;
	
        switch(query)  {
            case "Most Awarded Series": 
            case "Most Reviewed Books":
            case "Most Awarded Books": 
            case "Most Popular Books": 
            case "Most Translated Book Types":
            case "Most translated type of all languages":
            case "Most extensive web presence awarded as ":
            case "Publication series with most book awarded as ": 

                Name = myRs.getString("Name");
                Number = myRs.getString("Total");
            
                tempBestBook.set_Name(Name);
                tempBestBook.set_Number(Number);
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
                mostQuery = "SELECT TITLE AS NAME, SUM_AR AS TOTAL FROM ( " +
                            "SELECT TITLE_ID, SUM_AR FROM ( " +
                            "SELECT REV.TITLE_ID,  SUM(NUMB_REVIEWS) AS SUM_AR " +
                            "FROM (SELECT T.TITLE_ID, COUNT (REVIEW_ID) AS NUMB_REVIEWS " +
                            "FROM TITLE_REVIEWS " +
                            "RIGHT JOIN TITLES T ON T.TITLE_ID = TITLE_REVIEWS.TITLE_ID " +
                            "GROUP BY T.TITLE_ID ORDER BY NUMB_REVIEWS DESC) REV " +
                            "GROUP BY REV.TITLE_ID) ORDER BY SUM_AR DESC) HH, TITLES T " +
                            "WHERE T.TITLE_ID = HH.TITLE_ID AND ROWNUM <= 3 ";  
                break;
            case "Most Awarded Books":
                mostQuery = "SELECT TITLE AS NAME, SUM_AR AS TOTAL FROM ( " +
                            "SELECT TITLE_ID, SUM_AR FROM ( " +
                            "SELECT AWD.TITLE_ID,  SUM(NUM_AWARDS) AS SUM_AR " +
                            "FROM (SELECT T.TITLE_ID, COUNT (AWARD_ID) AS NUM_AWARDS " +
                            "FROM WON_TITLE_AWD " +
                            "RIGHT JOIN TITLES T ON T.TITLE_ID = WON_TITLE_AWD.TITLE_ID " +
                            "GROUP BY T.TITLE_ID ORDER BY NUM_AWARDS DESC) AWD " +
                            "GROUP BY AWD.TITLE_ID) ORDER BY SUM_AR DESC) HH, TITLES T " +
                            "WHERE T.TITLE_ID = HH.TITLE_ID AND ROWNUM <= 3 ";
                break;
            case "Most Popular Books":
                mostQuery = "SELECT TITLE as NAME, SUM_AR AS TOTAL FROM ( " +
                            "SELECT TITLE_ID, SUM_AR FROM ( " +
                            "SELECT AWD.TITLE_ID,  SUM(NUM_AWARDS+NUMB_REVIEWS) AS SUM_AR " +
                            "FROM (SELECT T.TITLE_ID, COUNT (AWARD_ID) AS NUM_AWARDS " +
                            "FROM WON_TITLE_AWD RIGHT JOIN TITLES T ON T.TITLE_ID = WON_TITLE_AWD.TITLE_ID " +
                            "GROUP BY T.TITLE_ID ORDER BY NUM_AWARDS DESC) AWD, " +
                            "(SELECT T.TITLE_ID, COUNT (REVIEW_ID) AS NUMB_REVIEWS " +
                            "FROM TITLE_REVIEWS RIGHT JOIN TITLES T ON T.TITLE_ID = TITLE_REVIEWS.TITLE_ID " +
                            "GROUP BY T.TITLE_ID ORDER BY NUMB_REVIEWS DESC) REV " +
                            "WHERE AWD.TITLE_ID = REV.TITLE_ID GROUP BY AWD.TITLE_ID) " +
                            "ORDER BY SUM_AR DESC) HH, TITLES T " +
                            "WHERE T.TITLE_ID = HH.TITLE_ID AND ROWNUM <= 3 ";
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
            case "Most translated type of all languages":
                mostQuery = "WITH ALL_RESULT AS ( " +
                            "SELECT TITLE_TYPE, LANGUAGE_NAME, COUNT(TITLE_ID) AS TOTAL " +
                            "FROM ( SELECT TITLES.TITLE_TYPE, TITLES.TITLE_ID, LANGUAGE_NAME " +
                            "FROM TITLES, LANGUAGES " +
                            "WHERE NOT TITLES.TRANS_PARENT = 0 AND LANGUAGES.LANGUAGE_ID = TITLES.LANGUAGE_ID) " +
                            "GROUP BY TITLE_TYPE, LANGUAGE_NAME ORDER BY TOTAL DESC) " +
                            "SELECT LANGUAGE_NAME as Name, TITLE_TYPE as Total FROM ( " +
                            "SELECT TITLE_TYPE, TOTAL,LANGUAGE_NAME, " +
                            "ROW_NUMBER() OVER (PARTITION BY LANGUAGE_NAME ORDER BY TOTAL DESC) AS ROWNUMBER " +
                            "FROM ALL_RESULT) WHERE ROWNUMBER <= 3 ";
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
            case "Most Translated Book Types":
                mostInputQuery = "SELECT * FROM ( SELECT TITLE_TYPE as Name, COUNT(TITLE_ID) AS Total " +
                            "FROM (SELECT TITLES.TITLE_TYPE, TITLES.TITLE_ID, TITLES.TRANS_PARENT " +
                            "FROM TITLES, LANGUAGES WHERE NOT TITLES.TRANS_PARENT = 0 AND LANGUAGES.LANGUAGE_NAME = '" + textInput + "' " + 
                            "AND LANGUAGES.LANGUAGE_ID = TITLES.LANGUAGE_ID) " +
                            "GROUP BY TITLE_TYPE " +  "ORDER BY TOTAL DESC) " +
                            "WHERE ROWNUM <= 10";
                break;
            case "Most extensive web presence awarded as ":
                mostInputQuery = "WITH TEMP_PUBL1 AS (SELECT P.PUBL_ID, P.PUBLISHER_ID, P.PUBL_SERIES_ID " +
                                 "FROM PUBLICATIONS P " +
                                 "LEFT JOIN PUBLISHED_PUBL_TITLE PT ON PT.PUBL_ID = P.PUBL_ID " +
                                 "LEFT JOIN TITLES T ON T.TITLE_ID = PT.TITLE_ID " +
                                 "LEFT JOIN WON_TITLE_AWD TA ON TA.TITLE_ID = T.TITLE_ID " +
                                 "LEFT JOIN AWARDS AW ON AW.AWARD_ID = TA.AWARD_ID " +
                                 "WHERE AW.AWARD_TITLE LIKE '%" + textInput + "%') " +
                                 "SELECT P.PUBL_TITLE AS NAME, TEMP_RESULT.NUM AS TOTAL FROM ( " +
                                 "SELECT TEMP_AUTHORS.PUBL_ID AS PUBL_ID, (NUM1 + NUM2 + NUM3 + NUM4 + NUM5) AS NUM " +
                                 "FROM (SELECT TEMP_PUBL1.PUBL_ID, COUNT(URL) AS NUM1 FROM TEMP_PUBL1 " +
                                 "LEFT JOIN WRITTEN_PUBL_AUT PA ON PA.PUBL_ID = TEMP_PUBL1.PUBL_ID " +
                                 "LEFT JOIN AUTHORS A ON A.AUTHOR_ID = PA.AUTHOR_ID " +
                                 "LEFT JOIN AUTHOR_WEBPAGES W ON W.AUTHOR_ID = A.AUTHOR_ID " +
                                 "GROUP BY TEMP_PUBL1.PUBL_ID ORDER BY NUM1 DESC) TEMP_AUTHORS, " +
                                 "(SELECT TEMP_PUBL1.PUBL_ID, COUNT(URL) AS NUM2 FROM TEMP_PUBL1 " +
                                 "LEFT JOIN PUBLISHED_PUBL_TITLE PT ON PT.PUBL_ID = TEMP_PUBL1.PUBL_ID " +
                                 "LEFT JOIN TITLES T ON T.TITLE_ID = PT.TITLE_ID " +
                                 "LEFT JOIN TITLE_WEBPAGES W ON W.TITLE_ID = T.TITLE_ID " +
                                 "GROUP BY TEMP_PUBL1.PUBL_ID ORDER BY NUM2 DESC) TEMP_TITLES, " +
                                 "(SELECT TEMP_PUBL1.PUBL_ID, COUNT(URL) AS NUM3 FROM TEMP_PUBL1 " +
                                 "LEFT JOIN PUBLISHER_WEBPAGES W ON W.PUBLISHER_ID = TEMP_PUBL1.PUBLISHER_ID " +
                                 "GROUP BY TEMP_PUBL1.PUBL_ID ORDER BY NUM3 DESC) TEMP_PUBLISHERS," +
                                 "(SELECT TEMP_PUBL1.PUBL_ID, COUNT(URL) AS NUM4 FROM TEMP_PUBL1 " +
                                 "LEFT JOIN PUBL_SERIES_WEBPAGES W ON W.PUBL_SERIES_ID = TEMP_PUBL1.PUBL_SERIES_ID " +
                                 "GROUP BY TEMP_PUBL1.PUBL_ID ORDER BY NUM4 DESC) TEMP_PUBL_SERIES, "+
                                 "(SELECT TEMP_PUBL1.PUBL_ID, COUNT(URL) AS NUM5 FROM TEMP_PUBL1 " +
                                 "LEFT JOIN PUBLISHED_PUBL_TITLE PT ON PT.PUBL_ID = TEMP_PUBL1.PUBL_ID " +
                                 "LEFT JOIN TITLES T ON T.TITLE_ID = PT.TITLE_ID " +
                                 "LEFT JOIN TITLE_SERIES_WEBPAGES W ON W.TITLE_SERIES_ID = T.TITLE_SERIES_ID " +
                                 "GROUP BY TEMP_PUBL1.PUBL_ID ORDER BY NUM5 DESC) TEMP_TITLE_SERIES " +
                                 "WHERE TEMP_TITLES.PUBL_ID = TEMP_AUTHORS.PUBL_ID AND " +
                                 "TEMP_PUBLISHERS.PUBL_ID = TEMP_TITLES.PUBL_ID AND " +
                                 "TEMP_PUBL_SERIES.PUBL_ID = TEMP_PUBLISHERS.PUBL_ID AND " +
                                 "TEMP_TITLE_SERIES.PUBL_ID = TEMP_PUBL_SERIES.PUBL_ID " +
                                 "ORDER BY NUM DESC) TEMP_RESULT " +
                                 "LEFT JOIN PUBLICATIONS P ON P.PUBL_ID = TEMP_RESULT.PUBL_ID " +
                                 "WHERE ROWNUM <= 10";    
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
