package UI;

import java.util.List;
import javax.swing.table.AbstractTableModel;

import Core.Author;

public class BasicSearchTableModel extends AbstractTableModel  {
    private static final int AUTHOR_ID_COL = 0;
    private static final int AUTHOR_NAME_COL = 1;
    private static final int BIRTHPLACE_COL = 2;

    private String[] columnNames = { "AUTHOR_ID", "AUTHOR_NAME", "BIRTHPLACE"};
    private List<Author> authors;
    
    public BasicSearchTableModel(List<Author> theAuthors) {
	authors = theAuthors;
    }
    
    @Override
    public int getColumnCount() {
	return columnNames.length;
    }

    @Override
    public int getRowCount() {
	return authors.size();
    }
    
    @Override
    public String getColumnName(int col) {
	return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {

	Author tempAuthor = authors.get(row);

	switch (col) {
            case AUTHOR_ID_COL:
		return tempAuthor.getId();
            case AUTHOR_NAME_COL:
		return tempAuthor.getAuthorName();
            case BIRTHPLACE_COL:
		return tempAuthor.getBirthplace();
            default:
		return tempAuthor.getAuthorName();
	}
    }

    @Override
    public Class getColumnClass(int c) {
	return getValueAt(0, c).getClass();
    }
}
