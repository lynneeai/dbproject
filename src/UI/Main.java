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

public class Main {
	
	public static JFrame mainFrame = new JFrame();
	//public static JPanel mainPanel = new JPanel();
	
	public Main() {
            mainFrame.setVisible(true);
            mainFrame.setBounds(100, 100, 800, 180);
	    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    SearchApp searchApp = new SearchApp();
	    searchApp.initialPage();
	}
	
	public static void main(String[] args) {
		new Main();
	}
	
	
}