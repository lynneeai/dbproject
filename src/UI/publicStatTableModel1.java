package UI;

import java.util.List;
import javax.swing.table.AbstractTableModel;

import Core.publicStat;

public class publicStatTableModel1 extends AbstractTableModel {
    private static final int Year_COL = 0;
    private static final int Total_COL = 1;
    
    private String[] columnNames = {"Year", "Total"};
    private List<publicStat> statsList;
    
    public publicStatTableModel1(List<publicStat> statsList) {
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

    	publicStat tempPublicStat = statsList.get(row);
    	
    	
    	switch (col) {
            case Year_COL:
        	return tempPublicStat.get_Year();
            case Total_COL:
            	return tempPublicStat.get_Total();
            default:
            	return tempPublicStat.get_Year();
    	}
    }

    @Override
    public Class getColumnClass(int c) {
    	Object value=this.getValueAt(0, c);
    	return (value==null?Object.class:value.getClass());
    }
}
