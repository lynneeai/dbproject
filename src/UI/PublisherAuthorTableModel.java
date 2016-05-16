package UI;

import java.util.List;
import javax.swing.table.AbstractTableModel;

import Core.Publisher;

public class PublisherAuthorTableModel extends AbstractTableModel {
    private static final int YEAR_COL = 0;
    private static final int NUM_COL = 1;
    
    private String[] columnNames = {"Year", "Average Number Of Authors"};
    private List<Publisher> Publishers;
    
    public PublisherAuthorTableModel(List<Publisher> publishers) {
    	this.Publishers = publishers;
    }
        
    @Override
    public int getColumnCount() {
    	return columnNames.length;
    }

    @Override
    public int getRowCount() {
    	return Publishers.size();
    }
        
    @Override
    public String getColumnName(int col) {
    	return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {

    	Publisher tempPublisher = Publishers.get(row);
    	
    	
    	switch (col) {
            case YEAR_COL:
            	return tempPublisher.get_Year();
           case NUM_COL:
            	return tempPublisher.get_Avg_Author_Num();
            default:
            	return tempPublisher.get_Publisher_Name();
    	}
    }

    @Override
    public Class getColumnClass(int c) {
    	Object value=this.getValueAt(0, c);
    	return (value==null?Object.class:value.getClass());
    }
}
