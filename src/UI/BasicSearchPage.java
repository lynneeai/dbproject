package UI;

import java.awt.*; 
import java.awt.event.*; 

import java.util.*;
import java.awt.font.TextAttribute;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

import Core.Book;
import DAO.AuthorDAO;
import DAO.UserInput;
import DAO.BasicSearchInput;
import DAO.BookDAO;
import DAO.BasicBookDAO;

public class BasicSearchPage extends JPanel {
	
	private JPanel contentPane;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    private JTextField searchTextField;
    private Choice methodChoice = new Choice();
    private Choice searchChoice = new Choice();
    private JButton advancedOption;
    private JLabel placeHolder;
    private JButton btnSearch;
    private JScrollPane scrollPane;
    private JTable table = new JTable();

    private AuthorDAO AuthorDAO;
    private BookDAO BookDAO;
    private BasicBookDAO BasicBookDAO;
    private UserInput UserInput;
    private BasicSearchInput BasicSearchInput;
	
	public void basicSearch() {
		contentPane = new JPanel();
        contentPane.setSize(100, 100);
        contentPane.setBorder(new EmptyBorder(5, 5, 18, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        Main.mainFrame.add(contentPane);
        Main.mainFrame.revalidate();
        Main.mainFrame.repaint();
        
        JPanel page = new JPanel();
        page.setLayout(new GridBagLayout());
        GridBagConstraints grid = new GridBagConstraints();
        
        JLabel transparent = new JLabel("       ");
        
        JTextField searchTextField = new JTextField();
        searchTextField.setColumns(30);
        searchTextField.setForeground(Color.gray);
        searchTextField.setText("What are you look for ?");
        
        Choice searchChoice = new Choice();
        searchChoice.setPreferredSize(new Dimension(180, 25));
        searchChoice.addItem("Author");  
        searchChoice.addItem("Book"); 
        
        JRadioButton exactSearch = new JRadioButton("Exact Search");
        JRadioButton approxSearch = new JRadioButton("Approximate Search");
        ButtonGroup bg = new ButtonGroup();
        bg.add(exactSearch);
        bg.add(approxSearch);
        
        JButton advancedOption = new JButton("Advanced Book Search");
        JButton btnSearch = new JButton("Search");
        
        advancedOption.setBorder(null);
        advancedOption.setOpaque(true);
        advancedOption.setForeground(Color.blue);
        advancedOption.setFont(new Font("Plain", Font.PLAIN, 13));
        Font font = advancedOption.getFont();
        Map attributes = font.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        advancedOption.setFont(font.deriveFont(attributes));       
        advancedOption.setFocusPainted(true);
        advancedOption.setBorderPainted(true);
        
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = searchTextField.getText();
                int searchChoiceContent = searchChoice.getSelectedIndex();
                int methodChoiceContent = methodChoice.getSelectedIndex();
                page.removeAll();
                contentPane.removeAll();
                Main.mainFrame.remove(contentPane);
                Main.mainFrame.setBounds(100, 100, 800, 1000);
                searchResult(name, searchChoiceContent, methodChoiceContent);
            }
        });
        
        grid.gridwidth = 2;
        grid.ipady = 5;
        grid.gridx = 0;
        grid.gridy = 0;
        page.add(searchTextField, grid);
        
        grid.gridwidth = 1;
        grid.gridx = 1;
        grid.gridy = 0;
        page.add(transparent, grid);
        
        grid.ipady = 0;
        grid.gridx = 2;
        grid.gridy = 0;
        page.add(searchChoice, grid);
        
        grid.gridx = 0;
        grid.gridy = 1;
        page.add(exactSearch, grid);
        
        grid.gridx = 1;
        grid.gridy = 1;
        page.add(approxSearch, grid);
        
        grid.ipady = 5;
        grid.gridx = 0;
        grid.gridy = 2;
        page.add(advancedOption, grid);
        
        grid.ipady = 0;
        grid.gridx = 2;
        grid.gridy = 2;
        page.add(btnSearch, grid);
        
        
        
        Border title = BorderFactory.createTitledBorder("Two Million Book Search");
        page.setBorder(title);
        contentPane.add(page, BorderLayout.CENTER);
	}
	
	
	 public void displayResult(String Name, int searchChoiceContent, int methodChoiceContent) { 
	        
	        BasicSearchInput = new BasicSearchInput();
	        
	        BasicSearchInput.set_Author_Name(null);
	        BasicSearchInput.set_Publication_Name(null);
	                                                   
	        if (searchChoice.getItem(searchChoiceContent).equals("Author")) {
	            BasicSearchInput.set_Author_Name(Name);
	        }
	        else if (searchChoice.getItem(searchChoiceContent).equals("Book")) {
	            BasicSearchInput.set_Publication_Name(Name);
	        }
	                
	        System.out.println("");
	        System.out.println("***********NEW SEARCH************");
	        System.out.println("Basic Search Input = " + Name);
	    
	        BasicBookDAO = new BasicBookDAO();
	        try {
	            if (Name != null && Name.trim().length() > 0) {
	                if (methodChoice.getItem(methodChoiceContent).equals("Approximate Search")) {
	                    List<Book> books = BasicBookDAO.fuzzySearchBook(BasicSearchInput);
	                    BookTableModel model = new BookTableModel(books);	               
	                    table.setModel(model);
	                }
	                else {
	                    List<Book> books = BasicBookDAO.searchBook(BasicSearchInput);
	                    BookTableModel model = new BookTableModel(books);	               
	                    table.setModel(model);
	                }                     					
	            }
	        } catch (Exception exc) {
	            JOptionPane.showMessageDialog(BasicSearchPage.this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE); 
	        }            			
	    }
	
	 public void searchResult(String content, int searchChoiceContent1, int methodChoiceContent1) {
		 	contentPane = new JPanel();
	        contentPane.setSize(100, 100);
	        contentPane.setBorder(new EmptyBorder(5, 5, 18, 5));
	        contentPane.setLayout(new BorderLayout(0, 0));
	        Main.mainFrame.add(contentPane);
	        Main.mainFrame.revalidate();
	        Main.mainFrame.repaint();
	        
	        JPanel page = new JPanel();
	        page.setLayout(new GridBagLayout());
	        GridBagConstraints grid = new GridBagConstraints();
	        
	        JLabel transparent = new JLabel("       ");
	        
	        JTextField searchTextField = new JTextField();
	        searchTextField.setColumns(30);
	        searchTextField.setForeground(Color.gray);
	        searchTextField.setText("What are you look for ?");
	        
	        Choice searchChoice = new Choice();
	        searchChoice.setPreferredSize(new Dimension(180, 25));
	        searchChoice.addItem("Author");  
	        searchChoice.addItem("Book"); 
	        
	        JRadioButton exactSearch = new JRadioButton("Exact Search");
	        JRadioButton approxSearch = new JRadioButton("Approximate Search");
	        ButtonGroup bg = new ButtonGroup();
	        bg.add(exactSearch);
	        bg.add(approxSearch);
	        
	        JButton advancedOption = new JButton("Advanced Book Search");
	        JButton btnSearch = new JButton("Search");
	        
	        advancedOption.setBorder(null);
	        advancedOption.setOpaque(true);
	        advancedOption.setForeground(Color.blue);
	        advancedOption.setFont(new Font("Plain", Font.PLAIN, 13));
	        Font font = advancedOption.getFont();
	        Map attributes = font.getAttributes();
	        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
	        advancedOption.setFont(font.deriveFont(attributes));       
	        advancedOption.setFocusPainted(true);
	        advancedOption.setBorderPainted(true);
	        
	        grid.gridwidth = 2;
	        grid.ipady = 5;
	        grid.gridx = 0;
	        grid.gridy = 0;
	        page.add(searchTextField, grid);
	        
	        grid.gridwidth = 1;
	        grid.ipady = 0;
	        grid.gridx = 1;
	        grid.gridy = 0;
	        page.add(transparent, grid);
	        
	        grid.gridx = 2;
	        grid.gridy = 0;
	        page.add(searchChoice, grid);
	        
	        grid.gridx = 0;
	        grid.gridy = 1;
	        page.add(exactSearch, grid);
	        
	        grid.gridx = 1;
	        grid.gridy = 1;
	        page.add(approxSearch, grid);
	        
	        grid.ipady = 5;
	        grid.gridx = 0;
	        grid.gridy = 2;
	        page.add(advancedOption, grid);
	        
	        grid.ipady = 0;
	        grid.gridx = 2;
	        grid.gridy = 2;
	        page.add(btnSearch, grid);
	        
	        Border title = BorderFactory.createTitledBorder("Two Million Book Search");
	        page.setBorder(title);
	        
		 
	        
	        JPanel resultPanel = new JPanel();
	        scrollPane = new JScrollPane();
	        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	        scrollPane.setPreferredSize(new Dimension(775, 610));
	        
	        resultPanel.add(scrollPane);
		 
	        contentPane.add(page, BorderLayout.NORTH);
	        contentPane.add(resultPanel, BorderLayout.SOUTH);
	 }
	
	
	
	
	
	
	
	
	
	
}