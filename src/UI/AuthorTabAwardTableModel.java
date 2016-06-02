package UI;

import java.util.List;
import javax.swing.table.AbstractTableModel;

import Core.AuthorTab;

public class AuthorTabAwardTableModel extends AbstractTableModel {
    private static final int AWARD_CAT_COL = 0;
    private static final int NAME_COL = 1;
    
    private String[] columnNames = {"Award Category", "Author Name"};
    private List<AuthorTab> AuthorTabs;
    
    public AuthorTabAwardTableModel(List<AuthorTab> authorTabs) {
    	this.AuthorTabs = authorTabs;
    }
        
    @Override
    public int getColumnCount() {
    	return columnNames.length;
    }

    @Override
    public int getRowCount() {
    	return AuthorTabs.size();
    }
        
    @Override
    public String getColumnName(int col) {
    	return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {

    	AuthorTab tempAuthorTab = AuthorTabs.get(row);
    	
    	
    	switch (col) {
            case AWARD_CAT_COL:
            	return tempAuthorTab.get_Award_Cat();
            case NAME_COL:
            	return tempAuthorTab.get_Author_Name();
            default:
            	return tempAuthorTab.get_Author_Name();
    	}
    }

    @Override
    public Class getColumnClass(int c) {
    	Object value=this.getValueAt(0, c);
    	return (value==null?Object.class:value.getClass());
    }
}
