package UI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.*; 
import java.awt.event.*; 
import java.applet.Applet; 

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
    private JPanel panel;
    private JPanel interPanel;
    private JTextField NameTextField;
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
		
        setTitle("Author Search App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 1000);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
		
        panel = new JPanel();
        FlowLayout flowLayout = (FlowLayout) panel.getLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);
        contentPane.add(panel, BorderLayout.NORTH); 
        GridLayout experimentLayout1 = new GridLayout(2,2);
        panel.setLayout(experimentLayout1);
        
        JLabel lblSearchItem = new JLabel("What do you want to search? ");
        panel.add(lblSearchItem);
        Choice choice =new Choice(); 
        choice.addItem("Authors"); 
        choice.addItem("Titles"); 
        choice.addItem("Publications"); 
        choice.addItem("Publishers"); 
        choice.addItem("Awards"); 
        choice.addItem("Languages"); 
        choice.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent ie) {               
                if (choice.getSelectedItem() == "Authors") {
                    interPanel.removeAll();
                    init_authorGUI();
                }
                else if (choice.getSelectedItem() == "Titles") {
                    interPanel.removeAll();
                    init_titleGUI();
                }
                else if (choice.getSelectedItem() == "Publications") {
                    interPanel.removeAll();
                    init_publicationGUI();
                }
                else if (choice.getSelectedItem() == "Publishers") {
                    interPanel.removeAll();
                    init_publisherGUI();
                }
                else if (choice.getSelectedItem() == "Awards") {
                    interPanel.removeAll();
                    init_awardGUI();
                }
                else if (choice.getSelectedItem() == "Languages") {
                    interPanel.removeAll();
                    init_languageGUI();
                }
            }      
        }); 
        panel.add(choice);
        
        GridLayout experimentLayout2 = new GridLayout(0,3);
        interPanel = new JPanel();
        FlowLayout interflowLayout = (FlowLayout) interPanel.getLayout();
        panel.add(interPanel, BorderLayout.LINE_START);
        interPanel.setLayout(experimentLayout2);
        
        init_authorGUI();
        
        scrollPane = new JScrollPane();
        contentPane.add(scrollPane, BorderLayout.PAGE_END);
		
        table = new JTable();
        scrollPane.setViewportView(table);
    }
    
    public void init_authorGUI() {
        JLabel lblEnterName= new JLabel();
        interPanel.add(lblEnterName);
        
        NameTextField = new JTextField();
        interPanel.add(NameTextField);
        NameTextField.setColumns(10);
        
        btnSearch = new JButton("Search");
        interPanel.add(btnSearch);
        lblEnterName.setText("Name of Author");		
        
        btnSearch.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            // Get name from the text field

            // Call DAO and get authors for the name

            // If name is empty, then get all authors

            // Print out first five authors				
				
            try {
                String Name = NameTextField.getText();

                List<Author> authors = null;

                if (Name != null && Name.trim().length() > 0) {
                    authors = AuthorDAO.searchAuthors(Name);
                } else {
                    authors = AuthorDAO.getfiveAuthors();
                }
					
                // create the model and update the "table"
		AuthorTableModel model = new AuthorTableModel(authors);	
                
		table.setModel(model);
					
		/*
                for (Author temp : authors) {
                    System.out.println(temp);
		}
		*/
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(SearchApp.this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE); 
                }			
            }
        });     
        
    } 
    
    public void init_titleGUI() {
        JLabel lblEnterName= new JLabel();
        interPanel.add(lblEnterName);
        
        NameTextField = new JTextField();
        interPanel.add(NameTextField);
        NameTextField.setColumns(10);
        
        btnSearch = new JButton("Search");
        interPanel.add(btnSearch);
        lblEnterName.setText("Name of Title");

        btnSearch.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            // Get name from the text field

            // Call DAO and get authors for the name

            // If name is empty, then get all authors

            // Print out first five authors				
				
            try {
                String Name = NameTextField.getText();

                List<Author> authors = null;

                if (Name != null && Name.trim().length() > 0) {
                    authors = AuthorDAO.searchAuthors(Name);
                } else {
                    authors = AuthorDAO.getfiveAuthors();
                }
					
                // create the model and update the "table"
		AuthorTableModel model = new AuthorTableModel(authors);	
                
		table.setModel(model);
					
		/*
                for (Author temp : authors) {
                    System.out.println(temp);
		}
		*/
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(SearchApp.this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE); 
                }			
            }
        });           
    } 
    
    public void init_publicationGUI() {
        JLabel lblEnterName= new JLabel();
        interPanel.add(lblEnterName);
        
        NameTextField = new JTextField();
        interPanel.add(NameTextField);
        NameTextField.setColumns(10);
        
        btnSearch = new JButton("Search");
        interPanel.add(btnSearch);
        lblEnterName.setText("Name of Publication");

        btnSearch.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            // Get name from the text field

            // Call DAO and get authors for the name

            // If name is empty, then get all authors

            // Print out first five authors				
				
            try {
                String Name = NameTextField.getText();

                List<Author> authors = null;

                if (Name != null && Name.trim().length() > 0) {
                    authors = AuthorDAO.searchAuthors(Name);
                } else {
                    authors = AuthorDAO.getfiveAuthors();
                }
					
                // create the model and update the "table"
		AuthorTableModel model = new AuthorTableModel(authors);	
                
		table.setModel(model);
					
		/*
                for (Author temp : authors) {
                    System.out.println(temp);
		}
		*/
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(SearchApp.this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE); 
                }			
            }
        });           
    } 
    
    public void init_publisherGUI() {
        JLabel lblEnterName= new JLabel();
        interPanel.add(lblEnterName);
        
        NameTextField = new JTextField();
        interPanel.add(NameTextField);
        NameTextField.setColumns(10);
        
        btnSearch = new JButton("Search");
        interPanel.add(btnSearch);
        lblEnterName.setText("Name of Publisher");

        btnSearch.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            // Get name from the text field

            // Call DAO and get authors for the name

            // If name is empty, then get all authors

            // Print out first five authors				
				
            try {
                String Name = NameTextField.getText();

                List<Author> authors = null;

                if (Name != null && Name.trim().length() > 0) {
                    authors = AuthorDAO.searchAuthors(Name);
                } else {
                    authors = AuthorDAO.getfiveAuthors();
                }
					
                // create the model and update the "table"
		AuthorTableModel model = new AuthorTableModel(authors);	
                
		table.setModel(model);
					
		/*
                for (Author temp : authors) {
                    System.out.println(temp);
		}
		*/
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(SearchApp.this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE); 
                }			
            }
        });            
    } 
    
    public void init_awardGUI() {
        JLabel lblEnterName= new JLabel();
        interPanel.add(lblEnterName);
        
        NameTextField = new JTextField();
        interPanel.add(NameTextField);
        NameTextField.setColumns(10);
        
        btnSearch = new JButton("Search");
        interPanel.add(btnSearch);
        lblEnterName.setText("Name of Award");

        btnSearch.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            // Get name from the text field

            // Call DAO and get authors for the name

            // If name is empty, then get all authors

            // Print out first five authors				
				
            try {
                String Name = NameTextField.getText();

                List<Author> authors = null;

                if (Name != null && Name.trim().length() > 0) {
                    authors = AuthorDAO.searchAuthors(Name);
                } else {
                    authors = AuthorDAO.getfiveAuthors();
                }
					
                // create the model and update the "table"
		AuthorTableModel model = new AuthorTableModel(authors);	
                
		table.setModel(model);
					
		/*
                for (Author temp : authors) {
                    System.out.println(temp);
		}
		*/
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(SearchApp.this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE); 
                }			
            }
        });            
    } 
    
    public void init_languageGUI() {
        JLabel lblEnterName= new JLabel();
        interPanel.add(lblEnterName);
        
        NameTextField = new JTextField();
        interPanel.add(NameTextField);
        NameTextField.setColumns(10);
        
        btnSearch = new JButton("Search");
        interPanel.add(btnSearch);
        lblEnterName.setText("Name of Language");

        btnSearch.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            // Get name from the text field

            // Call DAO and get authors for the name

            // If name is empty, then get all authors

            // Print out first five authors				
				
            try {
                String Name = NameTextField.getText();

                List<Author> authors = null;

                if (Name != null && Name.trim().length() > 0) {
                    authors = AuthorDAO.searchAuthors(Name);
                } else {
                    authors = AuthorDAO.getfiveAuthors();
                }
					
                // create the model and update the "table"
		AuthorTableModel model = new AuthorTableModel(authors);	
                
		table.setModel(model);
					
		/*
                for (Author temp : authors) {
                    System.out.println(temp);
		}
		*/
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(SearchApp.this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE); 
                }			
            }
        });           
    } 
        
 }
        
        		
        