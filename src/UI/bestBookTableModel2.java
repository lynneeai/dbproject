package UI;

import java.util.List;
import javax.swing.table.AbstractTableModel;

import Core.bestBook;

public class bestBookTableModel2 extends AbstractTableModel {
    
    private static final int AuthorName_Col = 0;
    private static final int TitleName_Col = 1;
    private static final int NumReviews_Col = 2;
    
    private String[] columnNames = {"Author Name", "Title Name", "Number of Reviews"};
    private List<bestBook> statsList;
    
    public bestBookTableModel2(List<bestBook> statsList) {
    	this.statsList = statsList;
    }
        
    @Override
    public int getColumnCount() {
    	return columnNames.length;
    }

    @Override
    public int getRowCount() {
    	return statsList.size();
    }
        
    @Override
    public String getColumnName(int col) {
    	return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {

    	bestBook tempBestBook = statsList.get(row);
    	
    	
    	switch (col) {
            case AuthorName_Col:
        	return tempBestBook.get_AuthorName();
            case TitleName_Col:
            	return tempBestBook.get_TitleName();
            case NumReviews_Col:
            	return tempBestBook.get_NumReviews();
            default:
            	return tempBestBook.get_AuthorName();
    	}
    }

    @Override
    public Class getColumnClass(int c) {
    	Object value=this.getValueAt(0, c);
    	return (value==null?Object.class:value.getClass());
    }
    
}
