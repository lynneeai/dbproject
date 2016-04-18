package DAO;

import java.util.*;
import java.sql.*;
import java.io.*;

import Core.Author;
import DAO.connectDB;

public class AuthorDAO {
    
    private Connection myConn = connectDB.getConnection();
    
    private Author convertRowToAuthor(ResultSet myRs) throws SQLException {	
	int AUTHOR_ID = myRs.getInt("AUTHOR_ID");
	String AUTHOR_NAME = myRs.getString("AUTHOR_NAME");
	String BIRTHPLACE = myRs.getString("BIRTHPLACE");
		
	Author tempAuthor = new Author(AUTHOR_ID, AUTHOR_NAME, BIRTHPLACE);
		
	return tempAuthor;
    }
    
    public List<Author> getfiveAuthors() throws Exception {
	List<Author> list = new ArrayList<>();
		
	Statement myStmt = null;
	ResultSet myRs = null;
		
	try {
            myStmt = myConn.createStatement();
            myRs = myStmt.executeQuery("SELECT * FROM AUTHORS WHERE ROWNUM <= 5");
			
            while (myRs.next()) {
                Author tempAuthor = convertRowToAuthor(myRs);
                 System.out.println(tempAuthor.getBirthplace());
                list.add(tempAuthor);
            }		
	}
	catch(SQLException e){
            System.out.println("SQL exception occured " + e);
        }    
        
        return list;
    }
    
    public List<Author> searchAuthors(String AUTHOR_NAME) throws Exception {
	List<Author> list = new ArrayList<>();

	PreparedStatement myStmt = null;
	ResultSet myRs = null;

	try {
            AUTHOR_NAME += "%";
            myStmt = myConn.prepareStatement("select * from AUTHORS where AUTHOR_NAME like ?");		
            myStmt.setString(1, AUTHOR_NAME);
            myRs = myStmt.executeQuery();
			
            while (myRs.next()) {
		Author tempAuthor = convertRowToAuthor(myRs);
		list.add(tempAuthor);
            }
	}
	catch(SQLException e){
            System.out.println("SQL exception occured " + e);
        } 
        return list;
    }
    
    /*public static void main(String[] args) throws Exception {		
	AuthorDAO dao = new AuthorDAO();
	System.out.println(dao.searchAuthors("Robert Asprin"));

	//System.out.println(dao.getfiveAuthors());
    }*/
}
