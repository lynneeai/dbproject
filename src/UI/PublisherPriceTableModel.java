package UI;

import java.util.List;
import javax.swing.table.AbstractTableModel;

import Core.Publisher;

public class PublisherPriceTableModel extends AbstractTableModel {
    private static final int NAME_COL = 0;
    private static final int PRICE_COL = 1;
    
    private String[] columnNames = {"Publisher Name", "Average Price"};
    private List<Publisher> Publishers;
    
    public PublisherPriceTableModel(List<Publisher> publishers) {
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
            case NAME_COL:
            	return tempPublisher.get_Publisher_Name();
            case PRICE_COL:
            	return tempPublisher.get_Avg_Price();
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
