package UI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.*; 
import java.awt.event.*; 
import java.applet.Applet; 

import  javax.swing.*;
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

import Core.Author;
import DAO.AuthorDAO;
  
public class SearchApp extends JFrame {

    private JPanel contentPane;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JTextField searchTextField;
    private Choice methodChoice;
    private Choice searchChoice;
    private JButton advancedOption;
    private JLabel placeHolder;
    private JButton btnSearch;

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
        searchChoice.addItem("Titles"); 
        searchChoice.addItem("Publications"); 
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
        if (advancedOption.getModel().isPressed()) {
        	detailSearch();
        }
        
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
                System.out.println("pressed");
                detailSearch();
            }
        });    
    }
    
    public void detailSearch() {
    	
    	setBounds(100, 100, 800, 600);
    	
    	contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 18, 10));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
		
        // Title
        JPanel panelTitle = new JPanel();
        FlowLayout flowLayout1 = (FlowLayout) panelTitle.getLayout();
        flowLayout1.setAlignment(FlowLayout.LEFT);
        contentPane.add(panelTitle, BorderLayout.NORTH); 
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
        JLabel date = new JLabel("Date");
        JLabel language = new JLabel("Language");
        
        JTextField text1 = new JTextField();
        JTextField text2 = new JTextField();
        JTextField text3 = new JTextField();
        text1.setColumns(20);
        text2.setColumns(20);
        text3.setColumns(20);
        
        Choice languageChoice = new Choice();
        languageChoice.addItem("English");
        languageChoice.addItem("French");
        languageChoice.setPreferredSize(new Dimension(255, 25));
        
        Choice year = new Choice();
        Choice month = new Choice();
        Choice day = new Choice();
        
        
        grid.gridx = 0;
        grid.gridy = 0;
        panelFields.add(authorName, grid);
        grid.gridx = 1;
        grid.gridy = 0;
        panelFields.add(text1, grid);
        
        grid.gridx = 0;
        grid.gridy = 1;
        panelFields.add(bookTitle, grid);
        grid.gridx = 1;
        grid.gridy = 1;
        panelFields.add(text2, grid);
        
        grid.gridx = 0;
        grid.gridy = 2;
        panelFields.add(publisher, grid);
        grid.gridx = 1;
        grid.gridy = 2;
        panelFields.add(text3, grid);
        
        grid.gridx = 0;
        grid.gridy = 3;
        panelFields.add(language, grid);
        grid.gridx = 1;
        grid.gridy = 3;
        panelFields.add(languageChoice, grid);
        
        
      
        contentPane.add(panelFields, BorderLayout.WEST);
        
        
    }
    
    
}  
    