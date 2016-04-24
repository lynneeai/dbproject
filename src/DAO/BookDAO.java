package DAO;

import java.util.*;
import java.sql.*;
import java.io.*;

import Core.Book;
import DAO.connectDB;

public class BookDAO {
	
	private Connection myConn = connectDB.getConnection();
	private Statement myStmt = null;
	private ResultSet myRs = null;
	
	
	private Book convertRowToBook(ResultSet myRs) throws SQLException {	
		
		String bookName = myRs.getString("PUBL_TITLE");
		String publishedDate = myRs.getString("PUBL_DATE");
		String isbn = myRs.getString("ISBN");
		String price = myRs.getString("PRICE");
			
		Book tempBook = new Book(bookName, publishedDate, isbn, price);
		
		tempBook.set_Author(null);
		tempBook.set_Language(null);
			
		return tempBook;
	}
	
	public List<Book> searchBook(UserInput input) {
		String select = "SELECT DISTINCT PUBLICATIONS.PUBL_TITLE, PUBLICATIONS.PUBL_DATE, PUBLICATIONS.ISBN, PUBLICATIONS.PRICE ";
		String from = "FROM PUBLICATIONS ";
		String where = "WHERE ";
		
		List<Book> bookList = new ArrayList<>();
		
		boolean firstConstraint = true;
		boolean titleIncluded = false;
		
		if (input.get_Publication_Name() != null) {
			if (!firstConstraint) {
				where = where + " AND ";
			}
			where = where + "PUBLICATIONS.PUBL_TITLE='" + input.get_Publication_Name() + "'";
			firstConstraint = false;
		}
		
		if (input.get_Author_Name() != null) {
			if (!firstConstraint) {
				where = where + " AND ";
			}
			from = from + ", AUTHORS, WRITTEN_PUBL_AUT ";
			where = where + "AUTHORS.AUTHOR_NAME='" + input.get_Author_Name() + "'" + " AND "
					      + "WRITTEN_PUBL_AUT.AUTHOR_ID=AUTHORS.AUTHOR_ID" + " AND "
					      + "PUBLICATIONS.PUBL_ID=WRITTEN_PUBL_AUT.PUBL_ID";
			firstConstraint = false;
		}
		
		if (input.get_Publisher_Name() != null) {
			if (!firstConstraint) {
				where = where + " AND ";
			}
			from = from + ", PUBLISHERS ";
			where = where + "PUBLISHERS.PUBLISHER_NAME='" + input.get_Publisher_Name() + "'" + " AND "
						  + "PUBLICATIONS.PUBLISHER_ID=PUBLISHERS.PUBLISHER_ID";
			firstConstraint = false;
		}
		
		if (input.get_Publication_Series_Name() != null) {
			if (!firstConstraint) {
				where = where + " AND ";
			}
			from = from + ", PUBL_SERIES ";
			where = where + "PUBL_SERIES.PUBL_SERIES_NAME='" + input.get_Publication_Series_Name() + "'" + " AND "
						  + "PUBLICATIONS.PUBL_SERIES_ID=PUBL_SERIES.PUBL_SERIES_ID";
			firstConstraint = false;
		}
		
		if (input.get_Language() != null) {
			if (!firstConstraint) {
				where = where + " AND ";
			}
			if (!titleIncluded) {
				from = from + ", TITLES, PUBLISHED_PUBL_TITLE ";
				titleIncluded = true;
			}
			from = from + ", LANGUAGES ";
			where = where + "LANGUAGES.LANGUAGE_NAME='" + input.get_Language() + "'" + " AND "
						  + "TITLES.LANGUAGE_ID=LANGUAGES.LANGUAGE_ID" + " AND "
						  + "PUBLISHED_PUBL_TITLE.TITLE_ID=TITLES.TITLE_ID" + " AND "
						  + "PUBLICATIONS.PUBL_ID=PUBLISHED_PUBL_TITLE.PUBL_ID";
			firstConstraint = false;
		}
		
		if (input.get_Publication_Type() != null) {
			if (!firstConstraint) {
				where = where + " AND ";
			}
			if (!titleIncluded) {
				from = from + ", TITLES, PUBLISHED_PUBL_TITLE ";
				titleIncluded = true;
			}
			where = where + "TITLES.TITLE_TYPE='" + input.get_Publication_Type() + "'" + " AND "
						  + "PUBLISHED_PUBL_TITLE.TITLE_ID=TITLES.TITLE_ID" + " AND "
						  + "PUBLICATIONS.PUBL_ID=PUBLISHED_PUBL_TITLE.PUBL_ID" + " AND "
						  + "ROWNUM<=50";
			firstConstraint = false;
		}
		
		if (input.get_Start_Date() != null) {
			if (!firstConstraint) {
				where = where + " AND ";
			}
			where = where + "PUBLICATIONS.PUBL_DATE>='" + input.get_Start_Date() + "'" + " AND "
						  + "ROWNUM<=50";
			firstConstraint = false;
		}
		
		if (input.get_End_Date() != null) {
			if (!firstConstraint) {
				where = where + " AND ";
			}
			where = where + "PUBLICATIONS.PUBL_DATE<='" + input.get_End_Date() + "'" + " AND "
						  + "ROWNUM<=50";
			firstConstraint = false;
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