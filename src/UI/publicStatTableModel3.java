package UI;

import java.util.List;
import javax.swing.table.AbstractTableModel;

import Core.publicStat;

public class publicStatTableModel3 extends AbstractTableModel {
    
    private static final int Total_Col = 0;
    
    private String[] columnNames = {"Total"};
    private List<publicStat> statsList;
    
    public publicStatTableModel3(List<publicStat> statsList) {
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
            case Total_Col:
            	return tempPublicStat.get_Total();
            default:
            	return tempPublicStat.get_Total();
    	}
    }

    @Override
    public Class getColumnClass(int c) {
    	Object value=this.getValueAt(0, c);
    	return (value==null?Object.class:value.getClass());
    }
    
}
