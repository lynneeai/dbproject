package DAO;

import java.util.*;
import java.sql.*;

import Core.AuthorTab;
import DAO.AuthorTabInput;

public class AuthorTabDAO {
	private Connection myConn = connectDB.getConnection();
    private Statement myStmt = null;
    private ResultSet myRs = null;
    
    private AuthorTab convertRowToAuthorTab(ResultSet myRs) throws SQLException {
    	String authorName = myRs.getString("AUTHOR_NAME");
    	
    	return new AuthorTab(authorName);
    }
    
    private AuthorTab convertRowToAuthorTab_Birthdate(ResultSet myRs) throws SQLException {
    	String authorName = myRs.getString("AUTHOR_NAME");
    	String birthdate = myRs.getString("BIRTHDATE");
    	
    	return new AuthorTab(authorName, birthdate, "birthdate");
    }
    
    private AuthorTab convertRowToAuthorTab_Num(ResultSet myRs) throws SQLException {
    	String authorName = myRs.getString("AUTHOR_NAME");
    	String num = myRs.getString("NUM");
    	
    	return new AuthorTab(authorName, num, "number");
    }
    
    private AuthorTab convertRowToAuthorTab_Ratio(ResultSet myRs) throws SQLException {
    	String authorName = myRs.getString("AUTHOR_NAME");
    	String ratio = myRs.getString("PAGE_DOLLAR_RATIO");
    	
    	return new AuthorTab(authorName, ratio, "page_dollar");
    }
    
    private AuthorTab convertRowToAuthorTab_Language(ResultSet myRS) throws SQLException {
    	String language = myRs.getString("LANGUAGE_NAME");
    	String name = myRs.getString("AUTHOR_NAME");
    	
    	return new AuthorTab(language, name);
    }
    
    public List<AuthorTab> searchAllAuthor(AuthorTabInput authorTabInput) {
    	String query = "";
    	List<AuthorTab> authorTabList = new ArrayList<>();
    	if (authorTabInput.get_Selection() == null) {
    		query = "SELECT DISTINCT A.AUTHOR_NAME FROM AUTHORS A WHERE ROWNUM <= 50";
    	} else {
    		if (authorTabInput.get_Selection() == "In Language") {
    			query = "SELECT DISTINCT A.AUTHOR_NAME " +
    					"FROM AUTHORS A, LANGUAGES L " +
    					"WHERE A.LANGUAGE_ID = L.LANGUAGE_ID AND " +
    						  "L.LANGUAGE_NAME = '" + authorTabInput.get_Constraint() + "' AND " +
    						  "ROWNUM <= 50";
    		} else if (authorTabInput.get_Selection() == "With Tag") {
    			query = "SELECT DISTINCT A.AUTHOR_NAME " +
    					"FROM AUTHORS A, TITLES T, PUBLICATIONS P, WRITTEN_PUBL_AUT PA, PUBLISHED_PUBL_TITLE PT, TAGS, TAGGED_TITLE TT " +
    					"WHERE A.AUTHOR_ID = PA.AUTHOR_ID AND " +
    						  "PA.PUBL_ID = P.PUBL_ID AND " +
    						  "P.PUBL_ID = PT.PUBL_ID AND " +
    						  "PT.TITLE_ID = T.TITLE_ID AND " +
    						  "T.TITLE_ID = TT.TITLE_ID AND " +
    						  "TT.TAG_ID = TAGS.TAG_ID AND " +
    						  "TAGS.TAG_NAME LIKE '%" + authorTabInput.get_Constraint() + "%' AND " +
    						  "ROWNUM <= 50";
    		} else if (authorTabInput.get_Selection() == "Published In") {
    			query = "SELECT DISTINCT A.AUTHOR_NAME " + 
    					"FROM AUTHORS A, PUBLICATIONS P, WRITTEN_PUBL_AUT W " +
    					"WHERE A.AUTHOR_ID = W.AUTHOR_ID AND P.PUBL_ID = W.PUBL_ID AND " +
    						"extract(year from PUBL_DATE) = " + authorTabInput.get_Constraint() + " AND " +
    						"ROWNUM <= 50";
    		} 
    	}
    	
    	System.out.println(query);
    	
    	try {
            myStmt = myConn.createStatement();		
            myRs = myStmt.executeQuery(query);
            
            while (myRs.next()) {
            	AuthorTab tempAuthorTab = convertRowToAuthorTab(myRs);
            	authorTabList.add(tempAuthorTab);
            }
            
		}
		catch (SQLException e) {
            System.out.println("SQL exception occured " + e);
        }
    	
    	return authorTabList;
    }
    
    public List<AuthorTab> searchLivingAuthor(AuthorTabInput authorTabInput) {
    	String query = "";
    	List<AuthorTab> authorTabList = new ArrayList<>();
    	
    	query = "SELECT DISTINCT AUTHOR_NAME, BIRTHDATE " +
    			"FROM AUTHORS " +
    			"INNER JOIN (SELECT A.TITLE_ID, B.AUTHOR_ID " +
    						"FROM (SELECT TITLE_ID, TITLE, TITLE_TYPE FROM TITLES " +
    							  "WHERE TITLE_TYPE = '" + authorTabInput.get_Constraint() + "') A " +
    						"INNER JOIN (SELECT DISTINCT TITLE_ID, AUTHOR_ID " +
    									"FROM PUBLISHED_PUBL_TITLE " +
    									"INNER JOIN WRITTEN_PUBL_AUT " +
    									"ON WRITTEN_PUBL_AUT.PUBL_ID = PUBLISHED_PUBL_TITLE.PUBL_ID) B " +
    						"ON A.TITLE_ID = B.TITLE_ID) C " +
    			"ON AUTHORS.AUTHOR_ID = C.AUTHOR_ID " +
    			"WHERE NOT BIRTHDATE IS NULL AND DEATHDATE IS NULL " +
    			"ORDER BY BIRTHDATE DESC";

    	System.out.println(query);
    	
    	try {
            myStmt = myConn.createStatement();		
            myRs = myStmt.executeQuery(query);
            
            
            while (myRs.next()) {
            	AuthorTab tempAuthorTab = convertRowToAuthorTab_Birthdate(myRs);
            	authorTabList.add(tempAuthorTab);
            }
            
		}
		catch (SQLException e) {
            System.out.println("SQL exception occured " + e);
        }
    	
    	return authorTabList;
    }
    
    public List<AuthorTab> searchMostPublAuthor(AuthorTabInput authorTabInput) {
    	String query = "";
    	List<AuthorTab> authorTabList = new ArrayList<>();
    	if (authorTabInput.get_Selection() == null) {
    		query = "SELECT AUTHOR_NAME, NUM " +
    				"FROM (SELECT Aut.AUTHOR_ID, aut.AUTHOR_NAME, COUNT(DISTINCT PUBL_ID) AS NUM " +
    					  "FROM AUTHORS Aut, WRITTEN_PUBL_AUT W " +
    					  "WHERE Aut.AUTHOR_ID = W.AUTHOR_ID AND AUT.AUTHOR_NAME IS NOT NULL " +
    					  "GROUP BY Aut.AUTHOR_ID, AUT.AUTHOR_NAME " +
    					  "ORDER BY NUM DESC) " +
    			    "WHERE ROWNUM <= 10";
    	} else {
    		if (authorTabInput.get_Selection() == "In Language") {
    			query = "SELECT AUTHOR_NAME, NUM " +
    					"FROM (SELECT Aut.AUTHOR_ID, aut.AUTHOR_NAME, COUNT(DISTINCT P.PUBL_ID) AS NUM " +
    						  "FROM AUTHORS Aut, WRITTEN_PUBL_AUT W, PUBLISHED_PUBL_TITLE PT, TITLES T, LANGUAGES L, PUBLICATIONS P " +
    						  "WHERE Aut.AUTHOR_ID = W.AUTHOR_ID AND AUT.AUTHOR_NAME IS NOT NULL AND " +
    						  		 "P.PUBL_ID = W.PUBL_ID AND " +
    						  		 "T.TITLE_ID = PT.TITLE_ID AND P.PUBL_ID = PT.PUBL_ID AND " +
    						  		 "T.LANGUAGE_ID = L.LANGUAGE_ID  AND " +
    						  		 "L.LANGUAGE_NAME = '" + authorTabInput.get_Constraint() + "' " +
    						  "GROUP BY Aut.AUTHOR_ID, AUT.AUTHOR_NAME " +
    						  "ORDER BY NUM DESC) " +
    					"WHERE ROWNUM <= 10";
    		} else if (authorTabInput.get_Selection() == "With Tag") {
    			query = "SELECT AUTHOR_NAME, NUM " +
    					"FROM (SELECT Aut.AUTHOR_ID, aut.AUTHOR_NAME, COUNT(DISTINCT PUBL_ID) AS NUM " +
    						  "FROM AUTHORS Aut, WRITTEN_PUBL_AUT W, TAGS, TAGGED_TITLE " +
    						  "WHERE Aut.AUTHOR_ID = W.AUTHOR_ID AND AUT.AUTHOR_NAME IS NOT NULL AND " +
    						  		 "TAGS.TAG_ID = TAGGED_TITLE.TAG_ID AND " +
    						  		 "TAG_NAME LIKE '%" + authorTabInput.get_Constraint() + "%' " +
    						  "GROUP BY Aut.AUTHOR_ID, AUT.AUTHOR_NAME " +
    						  "ORDER BY NUM DESC) " +
    					"WHERE ROWNUM <= 10";
    		} else if (authorTabInput.get_Selection() == "Published In") {
    			query = "SELECT AUTHOR_NAME, NUM " +
    					"FROM (SELECT Aut.AUTHOR_ID, aut.AUTHOR_NAME, COUNT(DISTINCT P.PUBL_ID) AS NUM " +
    						  "FROM AUTHORS Aut, WRITTEN_PUBL_AUT W, PUBLICATIONS P " +
    						  "WHERE Aut.AUTHOR_ID = W.AUTHOR_ID AND AUT.AUTHOR_NAME IS NOT NULL AND " +
    						  		 "P.PUBL_ID = W.PUBL_ID AND " +
    						  		 "extract(year from PUBL_DATE) = " + authorTabInput.get_Constraint() + " " +
    						  "GROUP BY Aut.AUTHOR_ID, AUT.AUTHOR_NAME " +
    						  "ORDER BY NUM DESC) " +
    						  "WHERE ROWNUM <= 10";
    		}
    	}
    	
    	System.out.println(query);
    	
    	try {
            myStmt = myConn.createStatement();		
            myRs = myStmt.executeQuery(query);
            
            while (myRs.next()) {
            	AuthorTab tempAuthorTab = convertRowToAuthorTab_Num(myRs);
            	authorTabList.add(tempAuthorTab);
            }
            
		}
		catch (SQLException e) {
            System.out.println("SQL exception occured " + e);
        }
    	
    	return authorTabList;
    }
    
    public List<AuthorTab> searchOldestAuthor(AuthorTabInput authorTabInput) {
    	String query = "";
    	List<AuthorTab> authorTabList = new ArrayList<>();
    	if (authorTabInput.get_Selection() == null) {
    		query = "SELECT A.AUTHOR_NAME, A.BIRTHDATE " +
    				"FROM AUTHORS A " +
    				"WHERE A.BIRTHDATE IN (SELECT MIN(BIRTHDATE) " +
    				                      "FROM AUTHORS A)";
    	} else {
    		if (authorTabInput.get_Selection() == "In Language") {
    			query = "SELECT A.AUTHOR_NAME, A.BIRTHDATE " +
    				    "FROM AUTHORS A " +
        			    "WHERE A.BIRTHDATE IN (SELECT MIN(BIRTHDATE) " +
        				                      "FROM AUTHORS A, PUBLISHED_PUBL_TITLE PT, " +
        				                       	   "TITLES T, LANGUAGES L, PUBLICATIONS P, " +
        				                     	   "WRITTEN_PUBL_AUT W " +
        				                      "WHERE A.AUTHOR_ID = W.AUTHOR_ID AND P.PUBL_ID = W.PUBL_ID AND " +
        				                      	    "T.TITLE_ID = PT.TITLE_ID AND P.PUBL_ID = PT.PUBL_ID AND " +
        				                     	    "T.LANGUAGE_ID = L.LANGUAGE_ID  AND " +
        				                     	    "L.LANGUAGE_NAME = '" + authorTabInput.get_Constraint() + "')";
    		} else if (authorTabInput.get_Selection() == "With Tag") {
    			query = "SELECT A.AUTHOR_NAME, A.BIRTHDATE " +
    					"FROM AUTHORS A " +
    					"WHERE A.BIRTHDATE IN (SELECT MIN(BIRTHDATE) " +
    										  "FROM AUTHORS A, WRITTEN_PUBL_AUT PA, PUBLICATIONS P, PUBLISHED_PUBL_TITLE PT, TITLES T, TAGS, TAGGED_TITLE TT " +
    										  "WHERE A.AUTHOR_ID = PA.AUTHOR_ID AND P.PUBL_ID = PA.PUBL_ID AND " +
    										  		"PT.PUBL_ID = P.PUBL_ID AND T.TITLE_ID = PT.TITLE_ID AND " +
    										  		"TT.TITLE_ID = T.TITLE_ID AND TT.TAG_ID = TAGS.TAG_ID AND " +
    										  		"TAGS.TAG_NAME LIKE '%" + authorTabInput.get_Constraint() + "%')";
    		} else if (authorTabInput.get_Selection() == "Published In") {
    			query = "SELECT A.AUTHOR_NAME, A.BIRTHDATE " +
    				    "FROM AUTHORS A " +
        			    "WHERE A.BIRTHDATE IN (SELECT MIN(BIRTHDATE) " +
        				                      "FROM AUTHORS A, PUBLICATIONS P, WRITTEN_PUBL_AUT W " +
        				                      "WHERE A.AUTHOR_ID = W.AUTHOR_ID AND P.PUBL_ID = W.PUBL_ID AND " +
        				                      	   	"extract(year from PUBL_DATE) = " + authorTabInput.get_Constraint() + ")";
    		}
    	}
    	
    	System.out.println(query);
    	
    	try {
            myStmt = myConn.createStatement();		
            myRs = myStmt.executeQuery(query);
            
            
            while (myRs.next()) {
            	AuthorTab tempAuthorTab = convertRowToAuthorTab_Birthdate(myRs);
            	authorTabList.add(tempAuthorTab);
            }
            
		}
		catch (SQLException e) {
            System.out.println("SQL exception occured " + e);
        }
    	
    	return authorTabList;
    }
    
    public List<AuthorTab> searchYoungestAuthor(AuthorTabInput authorTabInput) {
    	String query = "";
    	List<AuthorTab> authorTabList = new ArrayList<>();
    	if (authorTabInput.get_Selection() == null) {
    		query = "SELECT A.AUTHOR_NAME, A.BIRTHDATE " +
    				"FROM AUTHORS A " +
    				"WHERE A.BIRTHDATE IN (SELECT MAX(BIRTHDATE) " +
    				                      "FROM AUTHORS A)";
    	} else {
    		if (authorTabInput.get_Selection() == "In Language") {
    			query = "SELECT A.AUTHOR_NAME, A.BIRTHDATE " +
    				    "FROM AUTHORS A " +
        			    "WHERE A.BIRTHDATE IN (SELECT MAX(BIRTHDATE) " +
        				                      "FROM AUTHORS A, PUBLISHED_PUBL_TITLE PT, " +
        				                       	   "TITLES T, LANGUAGES L, PUBLICATIONS P, " +
        				                     	   "WRITTEN_PUBL_AUT W " +
        				                      "WHERE A.AUTHOR_ID = W.AUTHOR_ID AND P.PUBL_ID = W.PUBL_ID AND " +
        				                      	    "T.TITLE_ID = PT.TITLE_ID AND P.PUBL_ID = PT.PUBL_ID AND " +
        				                     	    "T.LANGUAGE_ID = L.LANGUAGE_ID  AND " +
        				                     	    "L.LANGUAGE_NAME = '" + authorTabInput.get_Constraint() + "')";
    		} else if (authorTabInput.get_Selection() == "With Tag") {
    			query = "SELECT A.AUTHOR_NAME, A.BIRTHDATE " +
    					"FROM AUTHORS A " +
    					"WHERE A.BIRTHDATE IN (SELECT MAX(BIRTHDATE) " +
    										  "FROM AUTHORS A, WRITTEN_PUBL_AUT PA, PUBLICATIONS P, PUBLISHED_PUBL_TITLE PT, TITLES T, TAGS, TAGGED_TITLE TT " +
    										  "WHERE A.AUTHOR_ID = PA.AUTHOR_ID AND P.PUBL_ID = PA.PUBL_ID AND " +
    										  		"PT.PUBL_ID = P.PUBL_ID AND T.TITLE_ID = PT.TITLE_ID AND " +
    										  		"TT.TITLE_ID = T.TITLE_ID AND TT.TAG_ID = TAGS.TAG_ID AND " +
    										  		"TAGS.TAG_NAME LIKE '%" + authorTabInput.get_Constraint() + "%')";
    		} else if (authorTabInput.get_Selection() == "Published In") {
    			query = "SELECT A.AUTHOR_NAME, A.BIRTHDATE " +
    				    "FROM AUTHORS A " +
        			    "WHERE A.BIRTHDATE IN (SELECT MAX(BIRTHDATE) " +
        				                      "FROM AUTHORS A, PUBLICATIONS P, WRITTEN_PUBL_AUT W " +
        				                      "WHERE A.AUTHOR_ID = W.AUTHOR_ID AND P.PUBL_ID = W.PUBL_ID AND " +
        				                      	   	"extract(year from PUBL_DATE) = " + authorTabInput.get_Constraint() + ")";
    		} 
    	}
    	
    	System.out.println(query);
    	
    	try {
            myStmt = myConn.createStatement();		
            myRs = myStmt.executeQuery(query);
            
            while (myRs.next()) {
            	AuthorTab tempAuthorTab = convertRowToAuthorTab_Birthdate(myRs);
            	authorTabList.add(tempAuthorTab);
            }	
            
		}
		catch (SQLException e) {
            System.out.println("SQL exception occured " + e);
        }
    	
    	return authorTabList;
    }
    
    
    public List<AuthorTab> searchMostWrittenAuthor(AuthorTabInput authorTabInput) {
    	String query = "";
    	List<AuthorTab> authorTabList = new ArrayList<>();
    	if (authorTabInput.get_Selection() == null) {
    		query = "SELECT AUTHOR_NAME, NUM " +
    				"FROM (SELECT A.AUTHOR_NAME, COUNT(PT.TITLE_ID) AS NUM " +
    					  "FROM AUTHORS A,  WRITTEN_PUBL_AUT W, PUBLISHED_PUBL_TITLE PT " + 
    					  "WHERE PT.PUBL_ID = W.PUBL_ID AND W.AUTHOR_ID = A.AUTHOR_ID AND " +
    					  		"A.AUTHOR_NAME IS NOT NULL " +
    					  "GROUP BY A.AUTHOR_ID, A.AUTHOR_NAME " +
    					  "ORDER BY NUM DESC) " +
    				"WHERE ROWNUM <= 1";
    	} else {
    		if (authorTabInput.get_Selection() == "In Language") {
    			query = "SELECT AUTHOR_NAME, NUM " +
        				"FROM (SELECT A.AUTHOR_NAME, COUNT(PT.TITLE_ID) AS NUM " +
        					  "FROM AUTHORS A,  WRITTEN_PUBL_AUT W, PUBLISHED_PUBL_TITLE PT, LANGUAGES L " + 
        					  "WHERE PT.PUBL_ID = W.PUBL_ID AND W.AUTHOR_ID = A.AUTHOR_ID AND " +
        					  		"A.AUTHOR_NAME IS NOT NULL AND " +
        					  		"A.LANGUAGE_ID = L.LANGUAGE_ID AND " + 
        					  		"L.LANGUAGE_NAME = '" + authorTabInput.get_Constraint() + "' " +
        					  "GROUP BY A.AUTHOR_ID, A.AUTHOR_NAME " +
        					  "ORDER BY NUM DESC) " +
        				"WHERE ROWNUM <= 1";
    		} else if (authorTabInput.get_Selection() == "With Tag") {
    			query = "SELECT AUTHOR_NAME, NUM " +
        				"FROM (SELECT A.AUTHOR_NAME, COUNT(PT.TITLE_ID) AS NUM " +
        					  "FROM TAGS, TAGGED_TITLE, AUTHORS A,  WRITTEN_PUBL_AUT W, PUBLISHED_PUBL_TITLE PT " + 
        					  "WHERE TAGS.TAG_ID = TAGGED_TITLE.TAG_ID AND " +
        					  "A.AUTHOR_NAME IS NOT NULL AND " +
        					  "TAG_NAME LIKE '%" + authorTabInput.get_Constraint() + "%' " +
        					  "AND tagged_title.title_ID = PT.TITLE_ID " +
        					  "AND PT.PUBL_ID = W.PUBL_ID AND W.AUTHOR_ID = A.AUTHOR_ID " +
        					  "GROUP BY A.AUTHOR_ID, A.AUTHOR_NAME " +
        					  "ORDER BY NUM DESC) " +
        				"WHERE ROWNUM <= 1";
    					
    		} else if (authorTabInput.get_Selection() == "Published In") {
    			query = "SELECT AUTHOR_NAME, NUM " +
        				"FROM (SELECT A.AUTHOR_NAME, COUNT(PT.TITLE_ID) AS NUM " +
        					  "FROM AUTHORS A,  WRITTEN_PUBL_AUT W, PUBLISHED_PUBL_TITLE PT, PUBLICATIONS P " + 
        					  "WHERE PT.PUBL_ID = W.PUBL_ID AND W.AUTHOR_ID = A.AUTHOR_ID AND " +
        					  		"A.AUTHOR_NAME IS NOT NULL AND " +
        					  		"P.PUBL_ID = PT.PUBL_ID AND " +
        					  		"EXTRACT(YEAR FROM P.PUBL_DATE) = " + authorTabInput.get_Constraint() + " " +
        					  "GROUP BY A.AUTHOR_ID, A.AUTHOR_NAME " +
        					  "ORDER BY NUM DESC) " +
        				"WHERE ROWNUM <= 1";
    			
    		}
    	}
    
    	System.out.println(query);
    	
    	try {
            myStmt = myConn.createStatement();		
            myRs = myStmt.executeQuery(query);
            
            while (myRs.next()) {
           		AuthorTab tempAuthorTab = convertRowToAuthorTab_Num(myRs);
           		authorTabList.add(tempAuthorTab);
           	}	
            	
            
		}
		catch (SQLException e) {
            System.out.println("SQL exception occured " + e);
        }
    	
    	return authorTabList;
    
    }
    
    public List<AuthorTab> searchAwardedAuthor(AuthorTabInput authorTabInput) {
    	String query = "";
    	List<AuthorTab> authorTabList = new ArrayList<>();
    	
    	query = "SELECT AUTHOR_NAME, NUM " +
				"FROM AUTHORS " +
				"INNER JOIN (SELECT COUNT(A.TITLE_ID) AS NUM, AUTHOR_ID " +
							"FROM (SELECT Z.AWARD_ID, AWARD_CAT_ID, TITLE_ID " +
								  "FROM WON_TITLE_AWD " +
								  "INNER JOIN (SELECT * " +
								              "FROM (SELECT AWARD_ID, Y.AWARD_CAT_ID, Y.AWARD_CAT_NAME " +
								              		"FROM AWARDS " +
								              		"INNER JOIN (SELECT AWARD_CAT_ID, AWARD_CAT_NAME " +
								              					"FROM AWARD_CATS) Y " +
								              		"ON Y.AWARD_CAT_ID = AWARDS.AWARD_CAT_ID) " +
								              "WHERE AWARD_CAT_NAME = '" + authorTabInput.get_Constraint() + "') Z " +
								  "ON Z.AWARD_ID = WON_TITLE_AWD.AWARD_ID) A " +

							"INNER JOIN (SELECT DISTINCT TITLE_ID, AUTHOR_ID " +
										"FROM PUBLISHED_PUBL_TITLE " +
										"INNER JOIN WRITTEN_PUBL_AUT " +
										"ON WRITTEN_PUBL_AUT.PUBL_ID = PUBLISHED_PUBL_TITLE.PUBL_ID) B " +
							"ON A.TITLE_ID = B.TITLE_ID " +
							"GROUP BY AUTHOR_ID " +
							"ORDER BY NUM DESC) C " +
				"ON C.AUTHOR_ID = AUTHORS.AUTHOR_ID " +
				"WHERE ROWNUM <= 3";
    	
    	System.out.println(query);
    	
    	try {
            myStmt = myConn.createStatement();		
            myRs = myStmt.executeQuery(query);
            
            while (myRs.next()) {
           		AuthorTab tempAuthorTab = convertRowToAuthorTab_Num(myRs);
           		authorTabList.add(tempAuthorTab);
           	}	
            	
            
		}
		catch (SQLException e) {
            System.out.println("SQL exception occured " + e);
        }
    	
    	return authorTabList;
    }
    
    public List<AuthorTab> searchAwardedDeadAuthor(AuthorTabInput authorTabInput) {
    	String query = "";
    	List<AuthorTab> authorTabList = new ArrayList<>();
    	if (authorTabInput.get_Selection() == null) {
    		query = "SELECT AUTHOR_NAME , NUM " +
    				"FROM (SELECT A.AUTHOR_NAME, COUNT(WA.AWARD_ID) AS NUM " +
    					  "FROM AUTHORS A, PUBLICATIONS P, WRITTEN_PUBL_AUT WP, TITLES T, PUBLISHED_PUBL_TITLE PT, WON_TITLE_AWD WA, AWARDS AW " +
    					  "WHERE WA.TITLE_ID = T.TITLE_ID AND PT.TITLE_ID = T.TITLE_ID " +
    					  "AND  PT.PUBL_ID = P.PUBL_ID AND WA.AWARD_ID = AW.AWARD_ID " +
    					  "AND P.PUBL_ID = WP.PUBL_ID AND WP.AUTHOR_ID = A.AUTHOR_ID " +
    					  "AND A.DEATHDATE < AW.AWARD_DATE " +
    					  "GROUP BY A.AUTHOR_NAME " +
    					  "ORDER BY NUM DESC) " +
    				"WHERE ROWNUM = 1";
    	} else {
    		if (authorTabInput.get_Selection() == "In Language") {
    			query = "SELECT AUTHOR_NAME , NUM " +
    					"FROM (SELECT A.AUTHOR_NAME, COUNT(WA.AWARD_ID) AS NUM " +
    						  "FROM AUTHORS A, PUBLICATIONS P, WRITTEN_PUBL_AUT WP, TITLES T, PUBLISHED_PUBL_TITLE PT, WON_TITLE_AWD WA, AWARDS AW, LANGUAGES L " +
    						  "WHERE WA.TITLE_ID = T.TITLE_ID AND PT.TITLE_ID = T.TITLE_ID " +
    						  "AND  PT.PUBL_ID = P.PUBL_ID AND WA.AWARD_ID = AW.AWARD_ID " +
    						  "AND P.PUBL_ID = WP.PUBL_ID AND WP.AUTHOR_ID = A.AUTHOR_ID " +
    						  "AND A.DEATHDATE < AW.AWARD_DATE " +
    						  "AND L.LANGUAGE_ID = A.LANGUAGE_ID " +
    						  "AND L.LANGUAGE_NAME = '" + authorTabInput.get_Constraint() + "' " +
    						  "GROUP BY A.AUTHOR_NAME " +
    						  "ORDER BY NUM DESC) " + 
    					"WHERE ROWNUM = 1";	
    		} else if (authorTabInput.get_Selection() == "Published In") {
    			query = "SELECT AUTHOR_NAME , NUM " +
    					"FROM (SELECT A.AUTHOR_NAME, COUNT(WA.AWARD_ID) AS NUM " +
    						  "FROM AUTHORS A, PUBLICATIONS P, WRITTEN_PUBL_AUT WP, TITLES T, PUBLISHED_PUBL_TITLE PT, WON_TITLE_AWD WA, AWARDS AW " +
    						  "WHERE WA.TITLE_ID = T.TITLE_ID AND PT.TITLE_ID = T.TITLE_ID " +
    						  "AND  PT.PUBL_ID = P.PUBL_ID AND WA.AWARD_ID = AW.AWARD_ID " +
    						  "AND P.PUBL_ID = WP.PUBL_ID AND WP.AUTHOR_ID = A.AUTHOR_ID " +
    						  "AND A.DEATHDATE < AW.AWARD_DATE " +
    						  "AND extract(year from P.PUBL_DATE) = " + authorTabInput.get_Constraint() + " " +
    						  "GROUP BY A.AUTHOR_NAME " +
    						  "ORDER BY NUM DESC) " +
    					"WHERE ROWNUM = 1";
    		} else if (authorTabInput.get_Selection() == "With Tag") {
    			query = "SELECT AUTHOR_NAME , NUM " +
    					"FROM (SELECT A.AUTHOR_NAME, COUNT(WA.AWARD_ID) AS NUM " +
    						  "FROM AUTHORS A, PUBLICATIONS P, WRITTEN_PUBL_AUT WP, TITLES T, PUBLISHED_PUBL_TITLE PT, WON_TITLE_AWD WA, AWARDS AW, TAGS, TAGGED_TITLE TT " +
    						  "WHERE WA.TITLE_ID = T.TITLE_ID AND PT.TITLE_ID = T.TITLE_ID " +
    						  "AND  PT.PUBL_ID = P.PUBL_ID AND WA.AWARD_ID = AW.AWARD_ID " +
    						  "AND P.PUBL_ID = WP.PUBL_ID AND WP.AUTHOR_ID = A.AUTHOR_ID " +
    						  "AND A.DEATHDATE < AW.AWARD_DATE AND T.TITLE_ID = TT.TITLE_ID " +
    						  "AND TAGS.TAG_ID = TT.TAG_ID " +
    						  "AND TAGS.TAG_NAME LIKE '%" + authorTabInput.get_Constraint() + "%' " +
    						  "GROUP BY A.AUTHOR_NAME " +
    						  "ORDER BY NUM DESC) " +
    					"WHERE ROWNUM = 1";	
    		}
    	}
    	
    	System.out.println(query);
    	
    	try {
            myStmt = myConn.createStatement();		
            myRs = myStmt.executeQuery(query);
            
            while (myRs.next()) {
           		AuthorTab tempAuthorTab = convertRowToAuthorTab_Num(myRs);
           		authorTabList.add(tempAuthorTab);
           	}	
            	
            
		}
		catch (SQLException e) {
            System.out.println("SQL exception occured " + e);
        }
    	
    	return authorTabList;
    }
    
    public List<AuthorTab> searchMostReviewAuthor(AuthorTabInput authorTabInput) {
    	String query = "";
    	List<AuthorTab> authorTabList = new ArrayList<>();
    	if (authorTabInput.get_Selection() == null) {
    		query = "SELECT AUTHOR_NAME, NUM " +
    				"FROM (SELECT A.AUTHOR_NAME AS AUTHOR_NAME, COUNT(T.TITLE_ID) AS NUM " +
    					  "FROM TITLES T " +
    					  "LEFT OUTER JOIN PUBLISHED_PUBL_TITLE PT ON T.TITLE_ID = PT.TITLE_ID " +
    					  "LEFT OUTER JOIN WRITTEN_PUBL_AUT PA ON PT.PUBL_ID = PA.PUBL_ID " +
    					  "LEFT OUTER JOIN AUTHORS A ON PA.AUTHOR_ID = A.AUTHOR_ID " +
    					  "WHERE T.TITLE_TYPE = 'REVIEW' " +
    					  "GROUP BY A.AUTHOR_NAME " +
    					  "ORDER BY NUM DESC) " +
    				"WHERE ROWNUM = 1";
    	} else {
    		if (authorTabInput.get_Selection() == "In Language") {
    			query = "SELECT AUTHOR_NAME, NUM " +
    					"FROM (SELECT A.AUTHOR_NAME AS AUTHOR_NAME, COUNT(T.TITLE_ID) AS NUM " +
    						  "FROM TITLES T " +
    						  "LEFT OUTER JOIN PUBLISHED_PUBL_TITLE PT ON T.TITLE_ID = PT.TITLE_ID " +
    						  "LEFT OUTER JOIN WRITTEN_PUBL_AUT PA ON PT.PUBL_ID = PA.PUBL_ID " +
    						  "LEFT OUTER JOIN AUTHORS A ON PA.AUTHOR_ID = A.AUTHOR_ID " +
    						  "LEFT OUTER JOIN LANGUAGES L ON L.LANGUAGE_ID = A.LANGUAGE_ID " +
    						  "WHERE T.TITLE_TYPE = 'REVIEW' AND " +
    						  		 "L.LANGUAGE_NAME = '" + authorTabInput.get_Constraint() + "' " +
    						  "GROUP BY A.AUTHOR_NAME " +
    						  "ORDER BY NUM DESC) " +
    					"WHERE ROWNUM = 1";	
    		} else if (authorTabInput.get_Selection() == "Published In") {
    			query = "SELECT AUTHOR_NAME, NUM " +
    					"FROM (SELECT A.AUTHOR_NAME AS AUTHOR_NAME, COUNT(T.TITLE_ID) AS NUM " +
    						  "FROM TITLES T " +
    						  "LEFT OUTER JOIN PUBLISHED_PUBL_TITLE PT ON T.TITLE_ID = PT.TITLE_ID " +
    						  "LEFT OUTER JOIN WRITTEN_PUBL_AUT PA ON PT.PUBL_ID = PA.PUBL_ID " +
    						  "LEFT OUTER JOIN AUTHORS A ON PA.AUTHOR_ID = A.AUTHOR_ID " +
    						  "LEFT OUTER JOIN PUBLICATIONS P ON P.PUBL_ID = PT.PUBL_ID " +
    						  "WHERE T.TITLE_TYPE = 'REVIEW' AND " +
    						  	    "extract(year from P.PUBL_DATE) = " + authorTabInput.get_Constraint() + " " +
    						  "GROUP BY A.AUTHOR_NAME " +
    						  "ORDER BY NUM DESC) " +
    					"WHERE ROWNUM = 1";
    		} else if (authorTabInput.get_Selection() == "With Tag") {
    			query = "SELECT AUTHOR_NAME, NUM " +
    					"FROM (SELECT A.AUTHOR_NAME AS AUTHOR_NAME, COUNT(T.TITLE_ID) AS NUM " +
    						  "FROM TITLES T " +
    						  "LEFT OUTER JOIN PUBLISHED_PUBL_TITLE PT ON T.TITLE_ID = PT.TITLE_ID " +
    						  "LEFT OUTER JOIN WRITTEN_PUBL_AUT PA ON PT.PUBL_ID = PA.PUBL_ID " +
    						  "LEFT OUTER JOIN AUTHORS A ON PA.AUTHOR_ID = A.AUTHOR_ID " +
    						  "LEFT OUTER JOIN TAGGED_TITLE TT ON TT.TITLE_ID = T.TITLE_ID " +
    						  "LEFT OUTER JOIN TAGS TG ON TG.TAG_ID = TT.TAG_ID " +
    						  "WHERE T.TITLE_TYPE = 'REVIEW' AND " +
    						  	    "TG.TAG_NAME LIKE '%" + authorTabInput.get_Constraint() + "%' " +
    						  "GROUP BY A.AUTHOR_NAME " +
    						  "ORDER BY NUM DESC) " +
    					"WHERE ROWNUM = 1";
    		}
    	}
    	
    	System.out.println(query);
    	
    	try {
            myStmt = myConn.createStatement();		
            myRs = myStmt.executeQuery(query);
            
            while (myRs.next()) {
           		AuthorTab tempAuthorTab = convertRowToAuthorTab_Num(myRs);
           		authorTabList.add(tempAuthorTab);
           	}	
            	
            
		}
		catch (SQLException e) {
            System.out.println("SQL exception occured " + e);
        }
    	
    	return authorTabList;
    }
    
    public List<AuthorTab> searchPageDollarAuthor(AuthorTabInput authorTabInput) {
    	String query = "";
    	List<AuthorTab> authorTabList = new ArrayList<>();
    	if (authorTabInput.get_Selection() == null) {
    		query = "SELECT AUTHOR_NAME, PAGE_DOLLAR_RATIO " +
    				"FROM (SELECT A.AUTHOR_NAME AS AUTHOR_NAME, SUM(P.PUBL_PAGES)/SUM(P.PRICE) AS PAGE_DOLLAR_RATIO " +
    					  "FROM AUTHORS A " +
    					  "LEFT JOIN WRITTEN_PUBL_AUT PA ON PA.AUTHOR_ID = A.AUTHOR_ID " +
    					  "LEFT JOIN PUBLICATIONS P ON P.PUBL_ID = PA.PUBL_ID " +
    					  "WHERE P.PRICE_CURRENCY = '$' AND P.PRICE != 0 AND P.PUBL_PAGES IS NOT NULL " +
    					  "GROUP BY A.AUTHOR_NAME " +
    					  "ORDER BY PAGE_DOLLAR_RATIO DESC) " +
    				"WHERE ROWNUM <= 10";
    	} else {
    		if (authorTabInput.get_Selection() == "In Language") {
    			query = "SELECT AUTHOR_NAME, PAGE_DOLLAR_RATIO " +
    					"FROM (SELECT A.AUTHOR_NAME AS AUTHOR_NAME, SUM(P.PUBL_PAGES)/SUM(P.PRICE) AS PAGE_DOLLAR_RATIO " +
    						  "FROM AUTHORS A " +
    						  "LEFT JOIN WRITTEN_PUBL_AUT PA ON PA.AUTHOR_ID = A.AUTHOR_ID " +
    						  "LEFT JOIN PUBLICATIONS P ON P.PUBL_ID = PA.PUBL_ID " +
    						  "LEFT JOIN LANGUAGES L ON L.LANGUAGE_ID = A.LANGUAGE_ID " +
    						  "WHERE P.PRICE_CURRENCY = '$' AND P.PRICE != 0 AND P.PUBL_PAGES IS NOT NULL AND " +
    						  	 	"L.LANGUAGE_NAME = '" + authorTabInput.get_Constraint() + "' " +
    						  "GROUP BY A.AUTHOR_NAME " +
    						  "ORDER BY PAGE_DOLLAR_RATIO DESC) " +
    					"WHERE ROWNUM <= 10";
    		} else if (authorTabInput.get_Selection() == "Published In") {
    			query = "SELECT AUTHOR_NAME, PAGE_DOLLAR_RATIO " +
    					"FROM (SELECT A.AUTHOR_NAME AS AUTHOR_NAME, SUM(P.PUBL_PAGES)/SUM(P.PRICE) AS PAGE_DOLLAR_RATIO " +
    						  "FROM AUTHORS A " +
    						  "LEFT JOIN WRITTEN_PUBL_AUT PA ON PA.AUTHOR_ID = A.AUTHOR_ID " +
    						  "LEFT JOIN PUBLICATIONS P ON P.PUBL_ID = PA.PUBL_ID " +
    						  "WHERE P.PRICE_CURRENCY = '$' AND P.PRICE != 0 AND P.PUBL_PAGES IS NOT NULL AND " +
    						  	    "extract(year from P.PUBL_DATE) = " + authorTabInput.get_Constraint() + " " +
    						  "GROUP BY A.AUTHOR_NAME " +
    						  "ORDER BY PAGE_DOLLAR_RATIO DESC) " +
    					"WHERE ROWNUM <= 10";
    		} else if (authorTabInput.get_Selection() == "With Tag") {
    			query = "SELECT AUTHOR_NAME, PAGE_DOLLAR_RATIO " +
    					"FROM (SELECT A.AUTHOR_NAME AS AUTHOR_NAME, SUM(P.PUBL_PAGES)/SUM(P.PRICE) AS PAGE_DOLLAR_RATIO " +
    					"FROM AUTHORS A " +
    					"LEFT JOIN WRITTEN_PUBL_AUT PA ON PA.AUTHOR_ID = A.AUTHOR_ID " +
    					"LEFT JOIN PUBLICATIONS P ON P.PUBL_ID = PA.PUBL_ID " +
    					"LEFT JOIN PUBLISHED_PUBL_TITLE PT ON PT.PUBL_ID = P.PUBL_ID " +
    					"LEFT JOIN TITLES T ON T.TITLE_ID = PT.TITLE_ID " +
    					"LEFT JOIN TAGGED_TITLE TT ON TT.TITLE_ID = T.TITLE_ID " +
    					"LEFT JOIN TAGS TG ON TG.TAG_ID = TT.TAG_ID " +
    					"WHERE P.PRICE_CURRENCY = '$' AND P.PRICE != 0 AND P.PUBL_PAGES IS NOT NULL AND " +
    						  "TG.TAG_NAME LIKE '%" + authorTabInput.get_Constraint() + "%' " +
    					"GROUP BY A.AUTHOR_NAME " +
    					"ORDER BY PAGE_DOLLAR_RATIO DESC) " +
    					"WHERE ROWNUM <= 10";
    		}
    	}
    	
    	System.out.println(query);
    	
    	try {
            myStmt = myConn.createStatement();		
            myRs = myStmt.executeQuery(query);
            
            while (myRs.next()) {
           		AuthorTab tempAuthorTab = convertRowToAuthorTab_Ratio(myRs);
           		authorTabList.add(tempAuthorTab);
           	}	
            	
            
		}
		catch (SQLException e) {
            System.out.println("SQL exception occured " + e);
        }
    	
    	return authorTabList;
    }
    
    public List<AuthorTab> searchMostTranslatedNovelAuthor(AuthorTabInput authorTabInput) {
    	String query = "";
    	List<AuthorTab> authorTabList = new ArrayList<>();
    	if (authorTabInput.get_Selection() == null) {
    		query = "WITH TEMP_AUTHORS AS (SELECT COUNT(TEMP_TITLES.TITLE_ID) AS COUNTS, A.AUTHOR_NAME, A.LANGUAGE_ID " +
                     	           	      "FROM (SELECT DISTINCT T1.TITLE_ID, T1.TITLE_TYPE " +
                            		     	    "FROM TITLES T1 " +
                            		     	    "INNER JOIN TITLES T2 ON T1.TITLE_ID = T2.TRANS_PARENT) TEMP_TITLES " +
                            		      "LEFT OUTER JOIN PUBLISHED_PUBL_TITLE PT ON TEMP_TITLES.TITLE_ID = PT.TITLE_ID " +
                      	                  "LEFT OUTER JOIN WRITTEN_PUBL_AUT PA ON PT.PUBL_ID = PA.PUBL_ID " +
                      	          	      "LEFT OUTER JOIN AUTHORS A ON PA.AUTHOR_ID = A.AUTHOR_ID " +
                      	                  "WHERE TEMP_TITLES.TITLE_TYPE = 'NOVEL' " +
                     	                  "GROUP BY A.AUTHOR_NAME, A.LANGUAGE_ID) " +
                    "SELECT LANGUAGE_NAME, AUTHOR_NAME " +
                    "FROM (SELECT LANGUAGE_ID, AUTHOR_NAME, RANK() OVER (PARTITION BY LANGUAGE_ID ORDER BY COUNTS DESC) AS RANK1 " +
                    	  "FROM TEMP_AUTHORS) TEMP_RESULT " +
                    "LEFT JOIN LANGUAGES ON LANGUAGES.LANGUAGE_ID = TEMP_RESULT.LANGUAGE_ID " +
                    "WHERE RANK1 <= 3";
    	} else {
    		query = "SELECT * " +
        			"FROM (SELECT TEMP_AUTHORS.AUTHOR_NAME AS AUTHOR_NAME, COUNT(TITLE_ID) AS NUM " +
        				  "FROM LANGUAGES, (SELECT TEMP_TITLES.TITLE_ID, A.AUTHOR_NAME, A.LANGUAGE_ID " +
    									   "FROM (SELECT DISTINCT T1.TITLE_ID, T1.TITLE_TYPE " +
    									   		 "FROM TITLES T1 " +
    									   		 "INNER JOIN TITLES T2 ON T1.TITLE_ID = T2.TRANS_PARENT) TEMP_TITLES " +
    									   "LEFT OUTER JOIN PUBLISHED_PUBL_TITLE PT ON TEMP_TITLES.TITLE_ID = PT.TITLE_ID " +
    									   "LEFT OUTER JOIN WRITTEN_PUBL_AUT PA ON PT.PUBL_ID = PA.PUBL_ID " +
    									   "LEFT OUTER JOIN AUTHORS A ON PA.AUTHOR_ID = A.AUTHOR_ID " +
    									   "WHERE TEMP_TITLES.TITLE_TYPE = 'NOVEL') TEMP_AUTHORS " +
    					  "WHERE LANGUAGES.LANGUAGE_NAME = '" + authorTabInput.get_Constraint() + "' AND " + 
    							"LANGUAGES.LANGUAGE_ID = TEMP_AUTHORS.LANGUAGE_ID " +
    					  "GROUP BY TEMP_AUTHORS.AUTHOR_NAME " +
    					  "ORDER BY NUM DESC) " + 
    				"WHERE ROWNUM <= 3";
    	}
    	
    	
    	System.out.println(query);
    	
    	try {
            myStmt = myConn.createStatement();		
            myRs = myStmt.executeQuery(query);
            
            while (myRs.next()) {
            	if (authorTabInput.get_Selection() == null) {
            		AuthorTab tempAuthorTab = convertRowToAuthorTab_Language(myRs);
            		authorTabList.add(tempAuthorTab);         		
            	} else {
            		AuthorTab tempAuthorTab = convertRowToAuthorTab_Num(myRs);
            		authorTabList.add(tempAuthorTab);
            	}	
           	}	
            	
            
		}
		catch (SQLException e) {
            System.out.println("SQL exception occured " + e);
        }
    	
    	return authorTabList;
    }
    
}