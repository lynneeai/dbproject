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
import DAO.AuthorDAO;
  
public class SearchApp extends JFrame {

    private JPanel contentPane;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    private JTextField searchTextField;
    private Choice methodChoice;
    private Choice searchChoice;
    private JButton advancedOption;
    private JLabel placeHolder;
    private JButton btnSearch;
    private JScrollPane scrollPane;
    private JTable table;

    private AuthorDAO AuthorDAO;
	
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SearchApp frame = new SearchApp();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public SearchApp() {
        // create the DAO
        try {
            AuthorDAO = new AuthorDAO();
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE); 
        }
		
        setTitle("Book Search App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 180);
        initialPage();
        //detailSearch();
    }
    
    public void initialPage() {
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 18, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
		
        panel1 = new JPanel();
        FlowLayout flowLayout1 = (FlowLayout) panel1.getLayout();
        flowLayout1.setAlignment(FlowLayout.LEFT);
        contentPane.add(panel1, BorderLayout.NORTH); 
        panel1.setLayout(new GridLayout(1,1));
        
        JLabel lblSearchItem = new JLabel("Book Search ", SwingConstants.CENTER);
        panel1.add(lblSearchItem);
        
        panel2 = new JPanel();
        FlowLayout flowLayout2 = (FlowLayout) panel2.getLayout();
        flowLayout2.setAlignment(FlowLayout.LEFT);
        panel2.setBorder(new EmptyBorder(32, 5, 33, 5));
        contentPane.add(panel2, BorderLayout.CENTER); 
        panel2.setLayout(new GridLayout(1,3));
        
        methodChoice = new Choice();
        methodChoice.addItem("Exact Search"); 
        methodChoice.addItem("Approximate Search"); 
        panel2.add(methodChoice);
        
        searchTextField = new JTextField();
        panel2.add(searchTextField);
        searchTextField.setColumns(10);
        
        searchChoice = new Choice();
        searchChoice.addItem("Authors");  
        searchChoice.addItem("Books"); 
        searchChoice.addItem("Publishers"); 
        searchChoice.addItem("Awards"); 
        searchChoice.addItem("Languages"); 
        panel2.add(searchChoice);
        
        panel3 = new JPanel();
        FlowLayout flowLayout3 = (FlowLayout) panel3.getLayout();
        flowLayout3.setAlignment(FlowLayout.LEFT);
        contentPane.add(panel3, BorderLayout.SOUTH); 
        panel3.setLayout(new GridLayout(1,3));
        
        advancedOption = new JButton("Advanced Search Option");
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
                detailSearch();
            }
        });
        
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchResult();
            }
        });
    }
    
    public void searchResult() {
        setTitle("Book Search App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        panel1.removeAll();
        panel2.removeAll();
        panel3.removeAll();
        
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 0, 18, 0));
        contentPane.setLayout(gridbag);
        setContentPane(contentPane);
		
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
        
        methodChoice = new Choice();
        methodChoice.addItem("Exact Search"); 
        methodChoice.addItem("Approximate Search"); 
        panel2.add(methodChoice);
        
        searchTextField = new JTextField();
        panel2.add(searchTextField);
        searchTextField.setColumns(10);
        
        searchChoice = new Choice();
        searchChoice.addItem("Authors"); 
        searchChoice.addItem("Books");  
        searchChoice.addItem("Publishers"); 
        searchChoice.addItem("Awards"); 
        searchChoice.addItem("Languages"); 
        panel2.add(searchChoice);
        
        panel3 = new JPanel();
        FlowLayout flowLayout3 = (FlowLayout) panel3.getLayout();
        flowLayout3.setAlignment(FlowLayout.LEFT);
        c.gridx = 0;
        c.gridy = 2;
        c.gridheight = 1;
        contentPane.add(panel3,c); 
        panel3.setLayout(new GridLayout(1,3));
        
        advancedOption = new JButton("Advanced Search Option");
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
                detailSearch();
            }
        });
        
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                table.setModel(new DefaultTableModel());
                try {
                    String Name = searchTextField.getText();
                    List<Author> authors = null;
                    
                    if (Name != null && Name.trim().length() > 0) {
                        authors = AuthorDAO.searchAuthors(Name);
                    } else {
                    authors = AuthorDAO.getfiveAuthors();
                    }					
                    AuthorTableModel model = new AuthorTableModel(authors);	               
                    table.setModel(model);
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(SearchApp.this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE); 
                }			
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
        panel4.add(scrollPane, BorderLayout.PAGE_END);
		
        table = new JTable();
        scrollPane.setViewportView(table);
    }
    
    public void detailSearch() {
    	
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setBounds(100, 100, 800, 600);
        
        panel1.removeAll();
        panel2.removeAll();
        panel3.removeAll();
        contentPane.removeAll();
        
        contentPane.setBorder(new EmptyBorder(10, 10, 18, 10));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
		
        // Title
        JPanel panelTitle = new JPanel();
        FlowLayout flowLayout1 = (FlowLayout) panelTitle.getLayout();
        flowLayout1.setAlignment(FlowLayout.LEFT);
        //contentPane.add(panelTitle, BorderLayout.NORTH); 
        panelTitle.setLayout(new GridLayout(1,1));
        
        JLabel lblSearchItem = new JLabel("Advanced Book Search ", SwingConstants.CENTER);
        panelTitle.add(lblSearchItem);
        
        
        // Fields
        JPanel panelFields = new JPanel();
        panelFields.setLayout(new GridBagLayout());
        GridBagConstraints grid = new GridBagConstraints();
        
        JLabel authorName = new JLabel("Author Name");
        JLabel bookTitle = new JLabel("Book Title");
        JLabel publisher = new JLabel("Publisher");
        JLabel startDate = new JLabel("Start Date");
        JLabel endDate = new JLabel("End Date");
        JLabel language = new JLabel("Language");
        JLabel bookSeries = new JLabel("Book Series");
        JLabel type = new JLabel("Type");
        JLabel transparent1 = new JLabel("");
        JLabel transparent2 = new JLabel("");
        JLabel transparent3 = new JLabel("");
       
        
        JTextField text1 = new JTextField();
        JTextField text2 = new JTextField();
        JTextField text3 = new JTextField();
        JTextField text4 = new JTextField();
        JTextField text5 = new JTextField();
        text1.setColumns(20);
        text2.setColumns(20);
        text3.setColumns(20);
        text4.setColumns(20);
        text5.setColumns(20);
        
        
        Choice languageChoice = new Choice();
        String[] avail_Languages = {"English", "French", "Chinese", "Spanish", "Arabic", "German", "Other"};
        for (int i = 0; i < avail_Languages.length; i++) {
        	languageChoice.addItem(avail_Languages[i]);
        }
        languageChoice.setPreferredSize(new Dimension(255, 25));
        
        
        Choice bookType = new Choice();
        String[] avail_Type = {"Anthology", "Back Cover Art", "Collection", "Cover Art", "Interior Art", "Editor", "Essay", "Interview", "Novel", "Non Fiction", "Omnibus", "Poem", "Review", "Serial", "Short Fiction", "Chapbook"};
        for (int i = 0; i < avail_Type.length; i++) {
        	bookType.addItem(avail_Type[i]);
        }
        bookType.setPreferredSize(new Dimension(255, 25));
        
        Choice startYear = new Choice();
        startYear.setPreferredSize(new Dimension(255, 25));

        Choice endYear = new Choice();
        endYear.setPreferredSize(new Dimension(255, 25));
        
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
            
        
        JCheckBox awardedBook = new JCheckBox("Is this book awarded?");
        
        JButton search = new JButton("Search");
        
        
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
        panelFields.add(text1, grid);
        
        
        grid.gridx = 0;
        grid.gridy = 2;
        panelFields.add(bookTitle, grid);
        grid.gridx = 1;
        grid.gridy = 2;
        panelFields.add(text2, grid);
        
        grid.gridx = 0;
        grid.gridy = 3;
        panelFields.add(publisher, grid);
        grid.gridx = 1;
        grid.gridy = 3;
        panelFields.add(text3, grid);
        
        grid.gridx = 0;
        grid.gridy = 4;
        panelFields.add(bookSeries, grid);
        grid.gridx = 1;
        grid.gridy = 4;
        panelFields.add(text5, grid);
        
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
    