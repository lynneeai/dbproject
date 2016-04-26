package UI;

import java.util.List;
import javax.swing.table.AbstractTableModel;

import Core.Book;

public class BookTableModel extends AbstractTableModel {
	
    private static final int PUBLICATION_NAME_COL = 0;
    private static final int AUTHOR_NAME_COL = 1;
    private static final int PUBLICATION_DATE_COL = 2;
    private static final int LANGUAGE_COL = 3;
    private static final int ISBN_COL = 4;
    private static final int PRICE_COL = 5;

    private String[] columnNames = {"Book Title", "Author", "Publication Date", "Language", "ISBN", "Price"};
    private List<Book> books;
    
    public BookTableModel(List<Book> theBooks) {
    	books = theBooks;
    }
        
    @Override
    public int getColumnCount() {
    	return columnNames.length;
    }

    @Override
    public int getRowCount() {
    	return books.size();
    }
        
    @Override
    public String getColumnName(int col) {
    	return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {

    	Book tempBook = books.get(row);
    	
    	
    	switch (col) {
            case PUBLICATION_NAME_COL:
        	return tempBook.get_BOOK_NAME();
            case AUTHOR_NAME_COL:
            	return tempBook.get_AUTHOR();
            case PUBLICATION_DATE_COL:
            	return tempBook.get_PUBLISHED_DATE();
            case LANGUAGE_COL:
            	return tempBook.get_LANGUAGE();
            case ISBN_COL:
            	return tempBook.get_ISBN();
            case PRICE_COL:
            	return tempBook.get_PRICE();
            default:
            	return tempBook.get_BOOK_NAME();
    	}
    }

    @Override
    public Class getColumnClass(int c) {
    	Object value=this.getValueAt(0, c);
    	return (value==null?Object.class:value.getClass());
    }
	
}