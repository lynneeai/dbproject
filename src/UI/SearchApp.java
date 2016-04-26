package UI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.*; 
import java.awt.event.*; 
import java.applet.Applet; 

import  javax.swing.*;
import javax.swing.table.*;
import javax.swing.JFrame;
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

import javax.swing.BorderFactory;
import javax.swing.border.Border;

import Core.Author;
import Core.Book;
import DAO.AuthorDAO;
import DAO.UserInput;
import DAO.BasicSearchInput;
import DAO.BookDAO;
import DAO.BasicBookDAO;

  
public class SearchApp extends JPanel {

    private JPanel contentPane;
    //public static SearchApp frame = new SearchApp();
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
        
        JLabel lblSearchItem = new JLabel("Basic Book Search", SwingConstants.CENTER);
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
        searchTextField.setText("Please Enter Exact Name of");
        
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
        
        searchChoice.addItem("Authors");  
        searchChoice.addItem("Books"); 
        panel2.add(searchChoice);
        
        panel3 = new JPanel();
        FlowLayout flowLayout3 = (FlowLayout) panel3.getLayout();
        flowLayout3.setAlignment(FlowLayout.LEFT);
        contentPane.add(panel3, BorderLayout.SOUTH); 
        panel3.setLayout(new GridLayout(1,3));
        
        advancedOption = new JButton("Advanced Book Search");
        advancedOption.setHorizontalAlignment(SwingConstants.LEFT);

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
        BasicSearchInput.set_Publisher_Name(null);
        BasicSearchInput.set_Award(null);
        BasicSearchInput.set_Language(null);
                                                   
        if (searchChoice.getItem(searchChoiceContent).equals("Authors")) {
            BasicSearchInput.set_Author_Name(Name);
        }
        else if (searchChoice.getItem(searchChoiceContent).equals("Books")) {
            BasicSearchInput.set_Publication_Name(Name);
        }
        else if (searchChoice.getItem(searchChoiceContent).equals("Publishers")) {
            BasicSearchInput.set_Publisher_Name(Name);
        }
        else if (searchChoice.getItem(searchChoiceContent).equals("Awards")) {
            BasicSearchInput.set_Award(Name);
        }
        else if (searchChoice.getItem(searchChoiceContent).equals("Languages")) {
            BasicSearchInput.set_Language(Name);
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
        //setContentPane(contentPane);
        
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
        
        if(!content.equals("")) {
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
        
        //
        advancedOption = new JButton("Advanced Book Search");
        advancedOption.setBorder(null);
        advancedOption.setOpaque(true);
        advancedOption.setFocusPainted(true);
        advancedOption.setBorderPainted(true);
        advancedOption.setHorizontalAlignment(SwingConstants.LEFT);
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
                table.setModel(new DefaultTableModel());
                setBounds(100, 100, 800, 1000);
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
    
    public void detailSearch() {
    	
        Main.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setBounds(100, 100, 800, 1000);
        
        panel1.removeAll();
        panel2.removeAll();
        panel3.removeAll();
        contentPane.removeAll();
        
        contentPane.setBorder(new EmptyBorder(10, 10, 18, 10));
        contentPane.setLayout(new BorderLayout(0, 0));
        Main.mainFrame.add(contentPane);
		
        // Title
        JPanel panelTitle = new JPanel();
        FlowLayout flowLayout1 = (FlowLayout) panelTitle.getLayout();
        flowLayout1.setAlignment(FlowLayout.LEFT);
        //contentPane.add(panelTitle, BorderLayout.NORTH); 
        panelTitle.setLayout(new GridLayout(1,1));
        
        JLabel lblSearchItem = new JLabel("Advanced Book Search", SwingConstants.CENTER);
        panelTitle.add(lblSearchItem);
        
        // Result
        JPanel panelResult = new JPanel();
        
        JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane1.setPreferredSize(new Dimension(775, 390));
        panelResult.add(scrollPane1);
        
        JTable tableTemp = new JTable();
        scrollPane1.setViewportView(tableTemp);
        
        contentPane.add(panelResult, BorderLayout.SOUTH);
        
        
        // Fields
        
        UserInput = new UserInput();
        
        JPanel panelFields = new JPanel();
        panelFields.setLayout(new GridBagLayout());
        GridBagConstraints grid = new GridBagConstraints();
        
        JLabel authorName = new JLabel("Author Name");
        JLabel bookTitle = new JLabel("Book Title");
        JLabel publisher = new JLabel("Publisher");
        JLabel startDate = new JLabel("Date from");
        JLabel endDate = new JLabel("Date till");
        JLabel language = new JLabel("Language");
        JLabel bookSeries = new JLabel("Book Series");
        JLabel type = new JLabel("Type");
        JLabel transparent1 = new JLabel("");
        JLabel transparent2 = new JLabel("");
        JLabel transparent3 = new JLabel("");
       
        
        JTextField authorNameText = new JTextField("Please Enter Exact Author Name");
        JTextField bookTitleText = new JTextField("Please Enter Exact Book Title");
        JTextField publisherNameText = new JTextField("Please Enter Exact Publisher Name");
        JTextField bookSeriesText = new JTextField("Please Enter Exact Book Series Name");
         
        authorNameText.setColumns(20);
        authorNameText.setEditable(true);
        authorNameText.setForeground(Color.gray);
        authorNameText.addFocusListener(new FocusListener() {
        	public void focusGained(FocusEvent e) {
        		String content = authorNameText.getText();
        		if (content.trim().equals("Please Enter Exact Author Name")) {
        			authorNameText.setText("");
        			authorNameText.setForeground(Color.black);
        		}
        	}
        	public void focusLost(FocusEvent e) {
        		String content = authorNameText.getText();
        		if (content.trim().equals("")) {
        			authorNameText.setForeground(Color.gray);
        			authorNameText.setText("Please Enter Exact Author Name");
        			UserInput.set_Author_Name(null);
        		} else {
        			UserInput.set_Author_Name(content);
        		}
        	}
        });
        
        bookTitleText.setColumns(20);
        bookTitleText.setEditable(true);
        bookTitleText.setForeground(Color.gray);
        bookTitleText.addFocusListener(new FocusListener() {
        	public void focusGained(FocusEvent e) {
        		String content = bookTitleText.getText();
        		if (content.trim().equals("Please Enter Exact Book Title")) {
        			bookTitleText.setText("");
        			bookTitleText.setForeground(Color.black);
        		}
        	}
        	public void focusLost(FocusEvent e) {
        		String content = bookTitleText.getText();
        		if (content.trim().equals("")) {
        			bookTitleText.setForeground(Color.gray);
        			bookTitleText.setText("Please Enter Exact Book Title");
        			UserInput.set_Title_Name(null);
        			UserInput.set_Publication_Name(null);
        		} else {
        			UserInput.set_Title_Name(content);
        			UserInput.set_Publication_Name(content);
        		}
        			
        	}
        });
        
        publisherNameText.setColumns(20);
        publisherNameText.setEditable(true);
        publisherNameText.setForeground(Color.gray);
        publisherNameText.addFocusListener(new FocusListener() {
        	public void focusGained(FocusEvent e) {
        		String content = publisherNameText.getText();
        		if (content.trim().equals("Please Enter Exact Publisher Name")) {
        			publisherNameText.setText("");
        			publisherNameText.setForeground(Color.black);
        		}
        	}
        	public void focusLost(FocusEvent e) {
        		String content = publisherNameText.getText();
        		if (content.trim().equals("")) {
        			publisherNameText.setForeground(Color.gray);
        			publisherNameText.setText("Please Enter Exact Publisher Name");
        			UserInput.set_Publisher_Name(null);
        		} else {
        			UserInput.set_Publisher_Name(content);
        		}
        	}
        });
        
        bookSeriesText.setColumns(20);
        bookSeriesText.setEditable(true);
        bookSeriesText.setForeground(Color.gray);
        bookSeriesText.addFocusListener(new FocusListener() {
        	public void focusGained(FocusEvent e) {
        		String content = bookSeriesText.getText();
        		if (content.trim().equals("Please Enter Exact Book Series Name")) {
        			bookSeriesText.setText("");
        			bookSeriesText.setForeground(Color.black);
        		}
        	}
        	public void focusLost(FocusEvent e) {
        		String content = bookSeriesText.getText();
        		if (content.trim().equals("")) {
        			bookSeriesText.setForeground(Color.gray);
        			bookSeriesText.setText("Please Enter Exact Book Series Name");
        			UserInput.set_Title_Series_Name(null);
            		UserInput.set_Publication_Series_Name(null);
        		} else {
        			UserInput.set_Title_Series_Name(content);
        			UserInput.set_Publication_Series_Name(content);
        		}
        	}
        });
        
        
        Choice languageChoice = new Choice();
        Choice bookType = new Choice();
        Choice startYear = new Choice();
        Choice endYear = new Choice();
        
        String[] avail_Languages = {"", "English", "French", "Chinese", "Spanish", "Arabic", "German", "Other"};
        for (int i = 0; i < avail_Languages.length; i++) {
        	languageChoice.addItem(avail_Languages[i]);
        }
        languageChoice.setPreferredSize(new Dimension(255, 25));
        languageChoice.addFocusListener(new FocusListener() {
        	public void focusGained(FocusEvent e) {}
        	public void focusLost(FocusEvent e) {
        		String language = languageChoice.getSelectedItem();
        		if (language != "") {
        			UserInput.set_Language(language);
        		} else {
        			UserInput.set_Language(null);
        		}
        	}
        });
        
        
        String[] avail_Type = {"", "ANTHOLOGY", "BACKCOVERART", "COLLECTION", "COVERART", "INTERIORART", "EDITOR", "ESSAY", "INTERVIEW", "NOVEL", "NONFICTION", "OMNIBUS", "POEM", "REVIEW", "SERIAL", "SHORTFICTION", "CHAPBOOK", "MAGAZINE", "FANZINE"};
        for (int i = 0; i < avail_Type.length; i++) {
        	bookType.addItem(avail_Type[i]);
        }
        bookType.setPreferredSize(new Dimension(255, 25));
        bookType.addFocusListener(new FocusListener() {
        	public void focusGained(FocusEvent e) {}
        	public void focusLost(FocusEvent e) {
        		String type = bookType.getSelectedItem();
        		if (type != "") {
        			UserInput.set_Title_Type(type);
        			UserInput.set_Publication_Type(type);
        		} else {
        			UserInput.set_Title_Type(null);
        			UserInput.set_Publication_Type(null);
        		}
        	}
        });
        
        
        startYear.addItem("");
        endYear.addItem("");
        for (int i = 2016; i >= 1; i--) {
        	if (i >= 1000) {
        		startYear.addItem(Integer.toString(i));
        		endYear.addItem(Integer.toString(i));
        	}
        	if ((i >= 100) && (i <= 999)) {
        		startYear.addItem("0" + Integer.toString(i));
        		endYear.addItem("0" + Integer.toString(i));
        	}
        	if ((i >= 10) && (i <= 99)) {
        		startYear.addItem("00" + Integer.toString(i));
        		endYear.addItem("00" + Integer.toString(i));
        	}
        	if ((i >= 1) && (i <= 9)) {
        		startYear.addItem("000" + Integer.toString(i));
        		endYear.addItem("000" + Integer.toString(i));
        	}
        }
        
        startYear.setPreferredSize(new Dimension(255, 25));
        startYear.addFocusListener(new FocusListener() {
        	public void focusGained(FocusEvent e) {}
        	public void focusLost(FocusEvent e) {
        		String start_Year = startYear.getSelectedItem();
        		if (start_Year != "") {
        			UserInput.set_Start_Date(start_Year + "-01-01");
        		} else {
        			UserInput.set_Start_Date(null);
        		}
        	}
        });

        endYear.setPreferredSize(new Dimension(255, 25));
        endYear.addFocusListener(new FocusListener() {
        	public void focusGained(FocusEvent e) {}
        	public void focusLost(FocusEvent e) {
        		String end_Year = endYear.getSelectedItem();
        		if (end_Year != "") {
        			UserInput.set_End_Date(end_Year + "-01-01");
        		} else {
        			UserInput.set_End_Date(null);
        		}
        	}
        });
        
        
        JCheckBox awardedBook = new JCheckBox("Show only awarded books");
        awardedBook.addFocusListener(new FocusListener() {
        	public void focusGained(FocusEvent e) {}
        	public void focusLost(FocusEvent e) {
        		UserInput.set_Awarded(awardedBook.isSelected());
        	}
        });
        
        JButton search = new JButton("Search");
        search.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		System.out.println("");
        		System.out.println("***********NEW SEARCH************");
                System.out.println("Author Name = " + UserInput.get_Author_Name());
        		System.out.println("Book Title = " + UserInput.get_Publication_Name());
        		System.out.println("Publisher = " + UserInput.get_Publisher_Name());
        		System.out.println("Book Series Name = " + UserInput.get_Title_Series_Name());
        		System.out.println("Language = " + UserInput.get_Language());
        		System.out.println("Book Type = " + UserInput.get_Title_Type());
        		System.out.println("Start Date = " + UserInput.get_Start_Date());
        		System.out.println("End Date = " + UserInput.get_End_Date());
        		System.out.println("Awarded Book = " + UserInput.get_Awarded());
    
        		BookDAO = new BookDAO();
        		tableTemp.setModel(new DefaultTableModel());
                try {
                    List<Book> books = BookDAO.searchBook(UserInput);					
                    BookTableModel model = new BookTableModel(books);	               
                    tableTemp.setModel(model);
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(SearchApp.this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE); 
                }
        	}
        });
        
        
        grid.gridx = 0;
        grid.gridy = 0;
        grid.ipady = 40;
        panelFields.add(transparent3, grid);
        grid.ipady = 0;
        
        grid.gridx = 0;
        grid.gridy = 1;
        panelFields.add(authorName, grid);
        grid.gridx = 1;
        grid.gridy = 1;
        panelFields.add(authorNameText, grid);
        
        
        grid.gridx = 0;
        grid.gridy = 2;
        panelFields.add(bookTitle, grid);
        grid.gridx = 1;
        grid.gridy = 2;
        panelFields.add(bookTitleText, grid);
        
        grid.gridx = 0;
        grid.gridy = 3;
        panelFields.add(publisher, grid);
        grid.gridx = 1;
        grid.gridy = 3;
        panelFields.add(publisherNameText, grid);
        
        grid.gridx = 0;
        grid.gridy = 4;
        panelFields.add(bookSeries, grid);
        grid.gridx = 1;
        grid.gridy = 4;
        panelFields.add(bookSeriesText, grid);
        
        grid.gridx = 2;
        grid.gridy = 1;
        grid.ipadx = 20;
        panelFields.add(transparent2, grid);
        grid.ipadx = 0;
        
        grid.gridx = 3;
        grid.gridy = 1;
        panelFields.add(language, grid);
        grid.gridx = 4;
        grid.gridy = 1;
        panelFields.add(languageChoice, grid);
        
        grid.gridx = 3;
        grid.gridy = 2;
        panelFields.add(type, grid);
        grid.gridx = 4;
        grid.gridy = 2;
        panelFields.add(bookType, grid);
        
        
        grid.gridx = 3;
        grid.gridy = 3;
        panelFields.add(startDate, grid);
        grid.gridx = 4;
        grid.gridy = 3;
        panelFields.add(startYear, grid);
        
        grid.gridx = 3;
        grid.gridy = 4;
        panelFields.add(endDate, grid);
        grid.gridx = 4;
        grid.gridy = 4;
        panelFields.add(endYear, grid);
        
        grid.gridx = 4;
        grid.gridy = 5;
        grid.ipady = 30;
        panelFields.add(transparent1, grid);
        
        grid.gridx = 4;
        grid.gridy = 6;
        grid.ipady = 30;
        panelFields.add(awardedBook, grid);
        
        grid.gridx = 4;
        grid.gridy = 7;
        grid.ipady = 0;
        panelFields.add(search, grid);
        
        
        Border title = BorderFactory.createTitledBorder("Advanced Book Search");
        panelFields.setBorder(title);
        
        contentPane.add(panelFields, BorderLayout.NORTH);  
        
        
        
    }
    
}  