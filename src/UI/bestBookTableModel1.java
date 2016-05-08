package UI;

import java.util.List;
import javax.swing.table.AbstractTableModel;

import Core.bestBook;

public class bestBookTableModel1 extends AbstractTableModel {
    
    private static final int Name_Col = 0;
    private static final int Total_Col = 1;
    
    private String[] columnNames = {"Name", "Total"};
    private List<bestBook> statsList;
    
    public bestBookTableModel1(List<bestBook> statsList) {
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
            case Total_Col:
            	return tempBestBook.get_Total();
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
