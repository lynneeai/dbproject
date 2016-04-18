package UI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

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
    private JTextField lastNameTextField;
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
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
		
        JPanel panel = new JPanel();
        FlowLayout flowLayout = (FlowLayout) panel.getLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);
        contentPane.add(panel, BorderLayout.NORTH);
		
        JLabel lblEnterLastName = new JLabel("Enter Name of Author");
        panel.add(lblEnterLastName);
		
        lastNameTextField = new JTextField();
        panel.add(lastNameTextField);
        lastNameTextField.setColumns(10);
		
        btnSearch = new JButton("Search");
        btnSearch.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            // Get last name from the text field

            // Call DAO and get employees for the last name

            // If last name is empty, then get all employees

            // Print out employees				
				
            try {
                String Name = lastNameTextField.getText();

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
                for (Employee temp : employees) {
                    System.out.println(temp);
		}
		*/
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(SearchApp.this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE); 
                }			
            }
        });
        
        panel.add(btnSearch);
        scrollPane = new JScrollPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);
		
        table = new JTable();
        scrollPane.setViewportView(table);
    }
}
