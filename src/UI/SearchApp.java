package UI;

import java.awt.BorderLayout;
import java.awt.*; 
import java.awt.event.*; 

import java.util.*;
import java.awt.font.TextAttribute;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;
import java.util.List;

import Core.Book;
import DAO.AuthorDAO;
import DAO.UserInput;
import DAO.BasicSearchInput;
import DAO.BookDAO;
import DAO.BasicBookDAO;

  
public class SearchApp extends JPanel {

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
	
    
    public void initialPage() {
        contentPane = new JPanel();
        contentPane.setSize(100, 100);
        contentPane.setBorder(new EmptyBorder(5, 5, 18, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        Main.mainFrame.add(contentPane);
        Main.mainFrame.revalidate();
        Main.mainFrame.repaint();
		
        panel1 = new JPanel();
        panel1.setSize(100, 30);
        FlowLayout flowLayout1 = (FlowLayout) panel1.getLayout();
        flowLayout1.setAlignment(FlowLayout.LEFT);
        contentPane.add(panel1, BorderLayout.NORTH); 
        panel1.setLayout(new GridLayout(1,1));
        
        JLabel lblSearchItem = new JLabel("Two Million Book Search", SwingConstants.CENTER);
        panel1.add(lblSearchItem);
        
        panel2 = new JPanel();
        panel2.setSize(100, 30);
        FlowLayout flowLayout2 = (FlowLayout) panel2.getLayout();
        flowLayout2.setAlignment(FlowLayout.LEFT);
        panel2.setBorder(new EmptyBorder(32, 5, 33, 5));
        contentPane.add(panel2, BorderLayout.CENTER); 
        panel2.setLayout(new GridLayout(1,3));

        methodChoice.addItem("Exact Search"); 
        methodChoice.addItem("Approximate Search"); 
        panel2.add(methodChoice);
        
        searchTextField = new JTextField();
        panel2.add(searchTextField);
        searchTextField.setColumns(10);
        searchTextField.setForeground(Color.gray);
        searchTextField.setText("What are you look for ?");
        
        searchTextField.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
        	String content = searchTextField.getText();
                if (content.trim().equals("What are you look for ?")) {
                    searchTextField.setText("");
                    searchTextField.setForeground(Color.black);
                }
            }
            public void focusLost(FocusEvent e) {
        	String content = searchTextField.getText();
        	if (content.trim().equals("")) {
                    searchTextField.setForeground(Color.gray);
                    searchTextField.setText("What are you look for ?");
        	} 
            }
        });
        
        searchChoice.addItem("Author");  
        searchChoice.addItem("Book"); 
        panel2.add(searchChoice);
        
        panel3 = new JPanel();
        FlowLayout flowLayout3 = (FlowLayout) panel3.getLayout();
        flowLayout3.setAlignment(FlowLayout.LEFT);
        contentPane.add(panel3, BorderLayout.SOUTH); 
        panel3.setLayout(new GridLayout(1,3));
        
        advancedOption = new JButton("Advanced Book Search");
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
        advancedOption.setHorizontalAlignment(SwingConstants.CENTER);
        panel3.add(advancedOption);
        
        //A transparent label as placeholder 
        placeHolder = new JLabel("Book Search ");
        placeHolder.setFont(new Font("Serif", Font.BOLD, 10));
        placeHolder.setForeground(new Color(0, 255, 0, 0));
        panel3.add(placeHolder);
        
        btnSearch = new JButton("Search");;
        btnSearch.setHorizontalAlignment(SwingConstants.LEFT);
        panel3.add(btnSearch);
        
        advancedOption.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	panel1.removeAll();
                panel2.removeAll();
                panel3.removeAll();
                contentPane.removeAll();
                Main.mainFrame.remove(contentPane);
                Main.mainFrame.setBounds(100, 100, 800, 1000);
            	DetailSearchPage detailSearchPage = new DetailSearchPage();
            	detailSearchPage.detailSearch();
            	
            }
        });
        
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = searchTextField.getText();
                int searchChoiceContent = searchChoice.getSelectedIndex();
                int methodChoiceContent = methodChoice.getSelectedIndex();
                panel1.removeAll();
                panel2.removeAll();
                panel3.removeAll();
                contentPane.removeAll();
                Main.mainFrame.remove(contentPane);
                Main.mainFrame.setBounds(100, 100, 800, 1000);
                searchResult(name, searchChoiceContent, methodChoiceContent);
            }
        });
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
            JOptionPane.showMessageDialog(SearchApp.this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE); 
        }            			
    }
    
    public void searchResult(String content, int searchChoiceContent1, int methodChoiceContent1) {
        
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 0, 18, 0));
        contentPane.setLayout(gridbag);
        
        Main.mainFrame.add(contentPane);
        Main.mainFrame.revalidate();
        Main.mainFrame.repaint();
		
        panel1 = new JPanel();
        FlowLayout flowLayout1 = (FlowLayout) panel1.getLayout();
        flowLayout1.setAlignment(FlowLayout.LEFT);
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 1;
        contentPane.add(panel1,c); 
        panel1.setLayout(new GridLayout(1,1));
        
        JLabel lblSearchItem = new JLabel("Book Search ", SwingConstants.CENTER);
        panel1.add(lblSearchItem);
        
        panel2 = new JPanel();
        FlowLayout flowLayout2 = (FlowLayout) panel2.getLayout();
        flowLayout2.setAlignment(FlowLayout.LEFT);
        panel2.setBorder(new EmptyBorder(25, 5, 25, 5));
        c.gridx = 0;
        c.gridy = 1;
        c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        contentPane.add(panel2,c); 
        panel2.setLayout(new GridLayout(1,3));
        
        panel2.add(methodChoice);
        
        searchTextField = new JTextField();
        panel2.add(searchTextField);
        searchTextField.setColumns(10);
        searchTextField.setForeground(Color.gray);   
        
        searchTextField.setEditable(true);
        searchTextField.setForeground(Color.gray);
        searchTextField.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
        	String content = searchTextField.getText();
                if (content.trim().equals("Please Enter Exact Name of")) {
                    searchTextField.setText("");
                    searchTextField.setForeground(Color.black);
                }
            }
            public void focusLost(FocusEvent e) {
        	String content = searchTextField.getText();
        	if (content.trim().equals("")) {
                    searchTextField.setForeground(Color.gray);
                    searchTextField.setText("Please Enter Exact Name of");
        	} 
            }
        });
        
        if(!content.equals("What are you look for ?")) {
            displayResult(content, searchChoiceContent1, methodChoiceContent1);
            searchTextField.setForeground(Color.black);
            searchTextField.setText(content);
        }
        else {
            searchTextField.setText("Please Enter Exact Name of");
        }   

        panel2.add(searchChoice);
        
        panel3 = new JPanel();
        FlowLayout flowLayout3 = (FlowLayout) panel3.getLayout();
        flowLayout3.setAlignment(FlowLayout.LEFT);
        c.gridx = 0;
        c.gridy = 2;
        c.gridheight = 1;
        contentPane.add(panel3,c); 
        panel3.setLayout(new GridLayout(1,3));
        
        advancedOption = new JButton("Advanced Book Search");
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
        advancedOption.setHorizontalAlignment(SwingConstants.CENTER);
        panel3.add(advancedOption);
        
        placeHolder = new JLabel("Book Search ");
        placeHolder.setFont(new Font("Serif", Font.BOLD, 10));
        placeHolder.setForeground(new Color(0, 255, 0, 0));
        panel3.add(placeHolder);
        
        btnSearch = new JButton("Search");
        btnSearch.setHorizontalAlignment(SwingConstants.LEFT);
        panel3.add(btnSearch);
        
        advancedOption.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panel1.removeAll();
                panel2.removeAll();
                panel3.removeAll();
                panel4.removeAll();
                contentPane.removeAll();
                Main.mainFrame.remove(contentPane);
                table.setModel(new DefaultTableModel());
                Main.mainFrame.setBounds(100, 100, 800, 1000);
            	DetailSearchPage detailSearchPage = new DetailSearchPage();
            	detailSearchPage.detailSearch();
            }
        });
        
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                table.setModel(new DefaultTableModel());             
                String Name = searchTextField.getText();
                int searchChoiceContent2 = searchChoice.getSelectedIndex();
                int methodChoiceContent2 = methodChoice.getSelectedIndex();
                displayResult(Name, searchChoiceContent2, methodChoiceContent2);    			
            }
        });
        
        panel4 = new JPanel();
        FlowLayout flowLayout4 = (FlowLayout) panel4.getLayout();
        flowLayout4.setAlignment(FlowLayout.LEFT);
        c.gridx = 0;
        c.gridy = 3;
        c.gridheight = 3;
        c.anchor = GridBagConstraints.PAGE_END; //bottom of space
        c.weighty = 0.5;
        contentPane.add(panel4,c); 
        panel4.setLayout(new GridLayout(1,1));
        
        scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(775, 590));
        panel4.add(scrollPane);
        
        scrollPane.setViewportView(table);
    }
}  