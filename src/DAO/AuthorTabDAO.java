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
    
    public List<AuthorTab> searchAllAuthor(AuthorTabInput authorTabInput) {
    	String query = "";
    	List<AuthorTab> authorTabList = new ArrayList<>();
    	if (authorTabInput.get_Selection() == null) {
    		query = "SELECT DISTINCT A.AUTHOR_NAME FROM AUTHORS A";
    	} else {
    		if (authorTabInput.get_Selection() == "In Language") {
    			query = "SELECT DISTINCT A.AUTHOR_NAME " + 
    					"FROM AUTHORS A, PUBLISHED_PUBL_TITLE PT, TITLES T, " + 
    						 "LANGUAGES L, PUBLICATIONS P, WRITTEN_PUBL_AUT W " +
    					"WHERE A.AUTHOR_ID = W.AUTHOR_ID AND P.PUBL_ID = W.PUBL_ID AND " +
    						 "T.TITLE_ID = PT.TITLE_ID AND P.PUBL_ID = PT.PUBL_ID AND " +
    						 "T.LANGUAGE_ID = L.LANGUAGE_ID AND " +
    						 "L.LANGUAGE_NAME = '" + authorTabInput.get_Constraint() + "'";
    		} else if (authorTabInput.get_Selection() == "With Tag") {
    			query = "SELECT DISTINCT A.AUTHOR_NAME " + 
    					"FROM AUTHORS A, TAGS, TAGGED_TITLE " +
    					"WHERE TAGS.TAG_ID = TAGGED_TITLE.TAG_ID AND " +
    						  "TAG_NAME = '" + authorTabInput.get_Constraint() + "'";
    		} else if (authorTabInput.get_Selection() == "Published In") {
    			query = "SELECT DISTINCT A.AUTHOR_NAME " + 
    					"FROM AUTHORS A, PUBLICATIONS P, WRITTEN_PUBL_AUT W " +
    					"WHERE A.AUTHOR_ID = W.AUTHOR_ID AND P.PUBL_ID = W.PUBL_ID AND " +
    						"extract(year from PUBL_DATE) = " + authorTabInput.get_Constraint();
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
    
    public List<AuthorTab> searchMostPublAuthor(AuthorTabInput authorTabInput) {
    	String query = "";
    	List<AuthorTab> authorTabList = new ArrayList<>();
    	if (authorTabInput.get_Selection() == null) {
    		query = "SELECT DISTINCT AUTHOR_NAME " +
    				"FROM (SELECT A.AUTHOR_NAME, COUNT(DISTINCT PUBL_ID) AS NUM_PUBL " +
    				      "FROM AUTHORS A, WRITTEN_PUBL_AUT W " +
    				      "WHERE A.AUTHOR_ID = W.AUTHOR_ID " +
    				      "GROUP BY A.AUTHOR_NAME " +
    				      "ORDER BY num_publ DESC) " +
    				"WHERE ROWNUM <= 10";
    	} else {
    		if (authorTabInput.get_Selection() == "In Language") {
    			String query1 = "SELECT DISTINCT AUTHOR_NAME " +
        						"FROM (SELECT A.AUTHOR_NAME, COUNT(DISTINCT PUBL_ID) AS NUM_PUBL " +
        							  "FROM AUTHORS A, WRITTEN_PUBL_AUT W, " +
        							  	   "PUBLISHED_PUBL_TITLE PT, TITLES T, LANGUAGES L, PUBLICATIONS P, WRITTEN_PUBL_AUT W " +
        							  "WHERE A.AUTHOR_ID = W.AUTHOR_ID AND ";
    			String query2 = "A.AUTHOR_ID = W.AUTHOR_ID AND P.PUBL_ID = W.PUBL_ID AND " +
        						"T.TITLE_ID = PT.TITLE_ID AND P.PUBL_ID = PT.PUBL_ID AND " +
        						"T.LANGUAGE_ID = L.LANGUAGE_ID  AND L.LANGUAGE_NAME = '" + authorTabInput.get_Constraint() + "' ";
    			String query3 = "GROUP BY A.AUTHOR_NAME " +
    							"ORDER BY num_publ DESC) " +
    							"WHERE ROWNUM <= 10";
    			query = query1 + query2 + query3;
    		} else if (authorTabInput.get_Selection() == "With Tag") {
    			String query1 = "SELECT DISTINCT AUTHOR_NAME " +
								"FROM (SELECT A.AUTHOR_NAME, COUNT(DISTINCT PUBL_ID) AS NUM_PUBL " +
									  "FROM AUTHORS A, WRITTEN_PUBL_AUT W, " +
									  "TAGS, TAGGED_TITLE " +
								"WHERE A.AUTHOR_ID = W.AUTHOR_ID AND ";
    			String query2 = "TAGS.TAG_ID = TAGGED_TITLE.TAG_ID AND " +
								"TAG_NAME = '" + authorTabInput.get_Constraint() + "' ";
    			String query3 = "GROUP BY A.AUTHOR_NAME " +
    							"ORDER BY num_publ DESC) " +
    							"WHERE ROWNUM <= 10";
    			query = query1 + query2 + query3;
    		} else if (authorTabInput.get_Selection() == "Published In") {
    			String query1 = "SELECT DISTINCT AUTHOR_NAME " +
								"FROM (SELECT A.AUTHOR_NAME, COUNT(DISTINCT PUBL_ID) AS NUM_PUBL " +
									  "FROM AUTHORS A, WRITTEN_PUBL_AUT W, " +
									  "PUBLICATIONS P, WRITTEN_PUBL_AUT W " +
								"WHERE A.AUTHOR_ID = W.AUTHOR_ID AND ";
    			String query2 = "A.AUTHOR_ID = W.AUTHOR_ID AND P.PUBL_ID = W.PUBL_ID AND " +
								"extract(year from PUBL_DATE) = " + authorTabInput.get_Constraint() + " ";
    			String query3 = "GROUP BY A.AUTHOR_NAME " +
								"ORDER BY num_publ DESC) " +
								"WHERE ROWNUM <= 10";
    			query = query1 + query2 + query3;
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
    
    public List<AuthorTab> searchOldestAuthor(AuthorTabInput authorTabInput) {
    	String query = "";
    	List<AuthorTab> authorTabList = new ArrayList<>();
    	if (authorTabInput.get_Selection() == null) {
    		query = "SELECT A.AUTHOR_NAME " +
    				"FROM AUTHORS A " +
    				"WHERE A.BIRTHDATE IN (SELECT MIN(BIRTHDATE) " +
    				                      "FROM AUTHORS A)";
    	} else {
    		if (authorTabInput.get_Selection() == "In Language") {
    			query = "SELECT A.AUTHOR_NAME " +
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
    			query = "SELECT A.AUTHOR_NAME " +
    				    "FROM AUTHORS A " +
        			    "WHERE A.BIRTHDATE IN (SELECT MIN(BIRTHDATE) " +
        				                      "FROM AUTHORS A, PUBLISHED_PUBL_TITLE PT, " +
        				                      	   "TAGS, TAGGED_TITLE " +
        				                      "WHERE TAGS.TAG_ID = TAGGED_TITLE.TAG_ID AND " +
        				                      	   	"TAG_NAME = '" + authorTabInput.get_Constraint() + "')";
    		} else if (authorTabInput.get_Selection() == "Published In") {
    			query = "SELECT A.AUTHOR_NAME " +
    				    "FROM AUTHORS A " +
        			    "WHERE A.BIRTHDATE IN (SELECT MIN(BIRTHDATE) " +
        				                      "FROM AUTHORS A, PUBLISHED_PUBL_TITLE PT, " +
        				                      	   "PUBLICATIONS P, WRITTEN_PUBL_AUT W " +
        				                      "WHERE A.AUTHOR_ID = W.AUTHOR_ID AND P.PUBL_ID = W.PUBL_ID AND " +
        				                      	   	"extract(year from PUBL_DATE) = " + authorTabInput.get_Constraint() + ")";
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
    
    public List<AuthorTab> searchYoungestAuthor(AuthorTabInput authorTabInput) {
    	String query = "";
    	List<AuthorTab> authorTabList = new ArrayList<>();
    	if (authorTabInput.get_Selection() == null) {
    		query = "SELECT A.AUTHOR_NAME " +
    				"FROM AUTHORS A " +
    				"WHERE A.BIRTHDATE IN (SELECT MAX(BIRTHDATE) " +
    				                      "FROM AUTHORS A)";
    	} else {
    		if (authorTabInput.get_Selection() == "In Language") {
    			query = "SELECT A.AUTHOR_NAME " +
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
    			query = "SELECT A.AUTHOR_NAME " +
    				    "FROM AUTHORS A " +
        			    "WHERE A.BIRTHDATE IN (SELECT MAX(BIRTHDATE) " +
        				                      "FROM AUTHORS A, PUBLISHED_PUBL_TITLE PT, " +
        				                      	   "TAGS, TAGGED_TITLE " +
        				                      "WHERE TAGS.TAG_ID = TAGGED_TITLE.TAG_ID AND " +
        				                      	   	"TAG_NAME = '" + authorTabInput.get_Constraint() + "')";
    		} else if (authorTabInput.get_Selection() == "Published In") {
    			query = "SELECT A.AUTHOR_NAME " +
    				    "FROM AUTHORS A " +
        			    "WHERE A.BIRTHDATE IN (SELECT MAX(BIRTHDATE) " +
        				                      "FROM AUTHORS A, PUBLISHED_PUBL_TITLE PT, " +
        				                      	   "PUBLICATIONS P, WRITTEN_PUBL_AUT W " +
        				                      "WHERE A.AUTHOR_ID = W.AUTHOR_ID AND P.PUBL_ID = W.PUBL_ID AND " +
        				                      	   	"extract(year from PUBL_DATE) = " + authorTabInput.get_Constraint() + ")";
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
    
    
    public List<AuthorTab> searchMostWrittenAuthor(AuthorTabInput authorTabInput) {
    	String query = "";
    	List<AuthorTab> authorTabList = new ArrayList<>();
    	if (authorTabInput.get_Selection() == null) {
    		query = "SELECT AUTHOR_NAME " +
    				"FROM (SELECT A.AUTHOR_NAME, COUNT(T.TITLE_ID) AS NUM_TITLE " +
    					  "FROM AUTHORS A, PUBLICATIONS P, WRITTEN_PUBL_AUT W, TITLES T, " +
    					  	   "PUBLISHED_PUBL_TITLE PT " +
    					  "WHERE  PT.PUBL_ID = P.PUBL_ID AND PT.TITLE_ID = T.TITLE_ID AND " +
    					  	   	 "P.PUBL_ID = W.PUBL_ID AND W.AUTHOR_ID = A.AUTHOR_ID " +
    			     	  "GROUP BY A.AUTHOR_ID, A.AUTHOR_NAME) " +
    				"GROUP BY AUTHOR_NAME";
    	} else {
    		if (authorTabInput.get_Selection() == "In Language") {
    			String query1 = "SELECT AUTHOR_NAME " +
        						"FROM (SELECT A.AUTHOR_NAME, COUNT(T.TITLE_ID) AS NUM_TITLE " +
        							  "FROM AUTHORS A, PUBLICATIONS P, WRITTEN_PUBL_AUT W, TITLES T, " +
        							  "PUBLISHED_PUBL_TITLE PT, LANGUAGES L ";
    			String query2 = "WHERE PT.PUBL_ID = P.PUBL_ID AND PT.TITLE_ID = T.TITLE_ID AND " +
    					  	   	 	  "P.PUBL_ID = W.PUBL_ID AND W.AUTHOR_ID = A.AUTHOR_ID AND " +
    					  	   	 	  "A.AUTHOR_ID = W.AUTHOR_ID AND P.PUBL_ID = W.PUBL_ID AND " +
    					  	   	 	  "T.TITLE_ID = PT.TITLE_ID AND P.PUBL_ID = PT.PUBL_ID AND " +
    					  	   	 	  "T.LANGUAGE_ID = L.LANGUAGE_ID AND " +
    					  	   	 	  "L.LANGUAGE_NAME = '" + authorTabInput.get_Constraint() + "' " +
    					  	   	 	  "GROUP BY A.AUTHOR_ID, A.AUTHOR_NAME) " + 
     							"GROUP BY AUTHOR_NAME";
    			query = query1 + query2;
    		} else if (authorTabInput.get_Selection() == "With Tag") {
    			String query1 = "SELECT AUTHOR_NAME " +
						"FROM (SELECT A.AUTHOR_NAME, COUNT(T.TITLE_ID) AS NUM_TITLE " +
						  	  "FROM AUTHORS A, PUBLICATIONS P, WRITTEN_PUBL_AUT W, TITLES T, " +
						  	  	   "PUBLISHED_PUBL_TITLE PT, TAGS, TAGGED_TITLE ";
    			String query2 = "WHERE PT.PUBL_ID = P.PUBL_ID AND PT.TITLE_ID = T.TITLE_ID AND " +
    								  "P.PUBL_ID = W.PUBL_ID AND W.AUTHOR_ID = A.AUTHOR_ID AND " +
    								  "TAGS.TAG_ID = TAGGED_TITLE.TAG_ID AND " +
    								  "TAG_NAME = '" + authorTabInput.get_Constraint() + "' " +
    							"GROUP BY A.AUTHOR_ID, A.AUTHOR_NAME) " + 
    							"GROUP BY AUTHOR_NAME";
    			query = query1 + query2;
    					
    		} else if (authorTabInput.get_Selection() == "Published In") {
    			String query1 = "SELECT AUTHOR_NAME " +
						"FROM (SELECT A.AUTHOR_NAME, COUNT(T.TITLE_ID) AS NUM_TITLE " +
							  "FROM AUTHORS A, PUBLICATIONS P, WRITTEN_PUBL_AUT W, TITLES T, " +
							  	   "PUBLISHED_PUBL_TITLE PT ";
    			String query2 = "WHERE PT.PUBL_ID = P.PUBL_ID AND PT.TITLE_ID = T.TITLE_ID AND " +
									  "P.PUBL_ID = W.PUBL_ID AND W.AUTHOR_ID = A.AUTHOR_ID AND " +
									  "A.AUTHOR_ID = W.AUTHOR_ID AND P.PUBL_ID = W.PUBL_ID AND " + 
									  "extract(year from PUBL_DATE) = " + authorTabInput.get_Constraint() + " " +
								"GROUP BY A.AUTHOR_ID, A.AUTHOR_NAME) " + 
    							"GROUP BY AUTHOR_NAME";
    			query = query1 + query2;
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
    
    
}