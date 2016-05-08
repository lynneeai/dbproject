package UI;

import java.util.List;
import javax.swing.table.AbstractTableModel;

import Core.bestBook;

public class bestBookTableModel2 extends AbstractTableModel {
    
    private static final int Name_Col = 0;
    private static final int Currency_Col = 1;
    private static final int AvgPublPrice_Col = 2;
    
    private String[] columnNames = {"Name", "Currency", "Average Publication Price"};
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
            case Name_Col:
        	return tempBestBook.get_Name();
            case Currency_Col:
            	return tempBestBook.get_Currency();
            case AvgPublPrice_Col:
            	return tempBestBook.get_AvgPublPrice();
            default:
            	return tempBestBook.get_Name();
    	}
    }

    @Override
    public Class getColumnClass(int c) {
    	Object value=this.getValueAt(0, c);
    	return (value==null?Object.class:value.getClass());
    }
    
}
