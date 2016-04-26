package DAO;

import java.util.*;
import java.sql.*;

import Core.Book;

public class BasicBookDAO {
    private Connection myConn = connectDB.getConnection();
    private Statement myStmt = null;
    private ResultSet myRs = null;
    
    private Book convertRowToBook(ResultSet myRs) throws SQLException {	
		
	String bookName = myRs.getString("PUBL_TITLE");
	String publishedDate = myRs.getString("PUBL_DATE");
	String isbn = myRs.getString("ISBN");
	String price = myRs.getString("PRICE");
        
        Book tempBook = new Book(bookName, publishedDate, isbn, price);
              
	return tempBook;
        
    }
    
    public List<Book> searchBook(BasicSearchInput input) {
	String select = "SELECT DISTINCT PUBLICATIONS.PUBL_TITLE, PUBLICATIONS.PUBL_DATE, PUBLICATIONS.ISBN, PUBLICATIONS.PRICE ";
	String from = "FROM PUBLICATIONS ";
	String where = "WHERE ";
		
	List<Book> bookList = new ArrayList<>();
		
	if (input.get_Publication_Name() != null) {
            where = where + "PUBLICATIONS.PUBL_TITLE='" + input.get_Publication_Name() + "'";
	}
        else if (input.get_Author_Name() != null) {
            from = from + ", AUTHORS, WRITTEN_PUBL_AUT ";
            where = where + "AUTHORS.AUTHOR_NAME='" + input.get_Author_Name() + "'" + " AND "
                                    + "WRITTEN_PUBL_AUT.AUTHOR_ID=AUTHORS.AUTHOR_ID" + " AND "
                                    + "PUBLICATIONS.PUBL_ID=WRITTEN_PUBL_AUT.PUBL_ID";
	}		
		
	System.out.println(select);
	System.out.println(from);
	System.out.println(where);
		
	try {
            myStmt = myConn.createStatement();		
            myRs = myStmt.executeQuery(select + from + where);
            
            while (myRs.next()) {
		Book tempBook = convertRowToBook(myRs);
		bookList.add(tempBook);
            }	
            
	}
	catch (SQLException e) {
            System.out.println("SQL exception occured " + e);
        } 

		
    return bookList;       
    
    }
    
    public List<Book> fuzzySearchBook(BasicSearchInput input) {
	String select = "SELECT DISTINCT PUBLICATIONS.PUBL_TITLE, PUBLICATIONS.PUBL_DATE, PUBLICATIONS.ISBN, PUBLICATIONS.PRICE ";
	String from = "FROM PUBLICATIONS ";
	String where = "WHERE ";
		
	List<Book> bookList = new ArrayList<>();
		
	if (input.get_Publication_Name() != null) {
            where = where + "PUBLICATIONS.PUBL_TITLE LIKE '%" + input.get_Publication_Name() + "%'";
	}
        else if (input.get_Author_Name() != null) {
            from = from + ", AUTHORS, WRITTEN_PUBL_AUT ";
            where = where + "AUTHORS.AUTHOR_NAME LIKE '%" + input.get_Author_Name() + "%'" + " AND "
                                    + "WRITTEN_PUBL_AUT.AUTHOR_ID=AUTHORS.AUTHOR_ID" + " AND "
                                    + "PUBLICATIONS.PUBL_ID=WRITTEN_PUBL_AUT.PUBL_ID";
	}		
		
	System.out.println(select);
	System.out.println(from);
	System.out.println(where);
		
	try {
            myStmt = myConn.createStatement();		
            myRs = myStmt.executeQuery(select + from + where);
            
            while (myRs.next()) {
		Book tempBook = convertRowToBook(myRs);
		bookList.add(tempBook);
            }	
            
	}
	catch (SQLException e) {
            System.out.println("SQL exception occured " + e);
        } 

		
    return bookList;       
    
    }
	
}










