package UI;

import java.util.List;
import javax.swing.table.AbstractTableModel;

import Core.AuthorTab;

public class AuthorTabRatioTableModel extends AbstractTableModel {
    private static final int Name_COL = 0;
    private static final int Ratio_COL = 1;
    
    private String[] columnNames = {"Author Name", "Page/Dollar Ratio"};
    private List<AuthorTab> AuthorTabs;
    
    public AuthorTabRatioTableModel(List<AuthorTab> authorTabs) {
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
            case Name_COL:
            	return tempAuthorTab.get_Author_Name();
            case Ratio_COL:
            	return tempAuthorTab.get_Page_Dollar();
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
