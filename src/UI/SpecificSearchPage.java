package UI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.*; 
import java.awt.event.*; 
import java.applet.Applet; 

import javax.swing.*;
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
import javax.swing.JTabbedPane;
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

public class SpecificSearchPage {
    
    private JPanel contentPane;
    private final JTabbedPane pane = new JTabbedPane();
	
    public void specificSearch() {
        contentPane = SearchApp.contentPane;
        contentPane.setBorder(new EmptyBorder(10, 10, 18, 10));
        contentPane.setLayout(new BorderLayout(0, 0));
        
        String[] tabContents = {"All Publishers", "All authors", "Most Popular Books", "Books Published", "All Comics"};
        
        for (int i = 0; i < 5; i++) {
            pane.add(tabContents[i], new TabContent(i));        
        }
        contentPane.add(pane);
    }
    
    private static class TabContent extends JPanel {

        private TabContent(int i) {
            setOpaque(true);
            if(i == 0) {
                getAllPublishers();
            }
            else if (i == 1) {
                getAllAuthors();
            }
            else if (i == 2) {
                getMostPopulars();
            }
            else if (i == 3 ) {
                getBooksPublished();
            }
            else {
                getAllComics();
            }
        }
        
        private void getAllPublishers() {
            add(new JLabel("Tab content " + "Publishers"));
        }
        
        private void getAllAuthors() {
            
        }
        
        private void getMostPopulars() {
            
        }
        
        private void getBooksPublished() {
            
        }
        
        private void getAllComics() {
            
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(320, 140);
        }
    }
}
