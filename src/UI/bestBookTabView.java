package UI;

import Core.bestBook;
import DAO.bestBookDAO;
import DAO.bestBookInput;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

public class bestBookTabView extends JPanel{
    private JTable table  = new JTable();
    
    public bestBookTabView() {
        this.getBestBooks();
    }

    public class bestBookValContainer {
        private String query;
        private String text;
        private Boolean selected;
        private Boolean rePlusAw = false;
            
        public bestBookValContainer() {
            this.query = null;
            this.text = null;
            this.selected = false;
        }
            
        public void setQuery(String query) {
            this.query = query;
        }
                
        public void setText(String text) {
            this.text = text;
        }
            
        public String getQuery() {
            return this.query;
        }
                
        public String getText() {
            return this.text;
        } 
    }
        
    private void getBestBooks() {
    
        bestBookValContainer bestBookValContainer = new bestBookValContainer();
        	
        JPanel fieldPanel = new JPanel();
        JPanel resultPanel = new JPanel();
        
        // resultPanel
    	
        JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane1.setPreferredSize(new Dimension(710, 375));
        
        ImageIcon backgroundIcon = new ImageIcon("src/background.jpg");
        Image backgroundImg = backgroundIcon.getImage();
        Image newBackgroundImg = backgroundImg.getScaledInstance(700, 370,  java.awt.Image.SCALE_SMOOTH );
        backgroundIcon = new ImageIcon(newBackgroundImg);
        JLabel backgroundPic = new JLabel(backgroundIcon);
        scrollPane1.getViewport().add(backgroundPic, scrollPane1);
        
        //scrollPane1.setViewportView(table);
        resultPanel.add(scrollPane1);
        	
        // fieldPanel
        fieldPanel.setLayout(new GridBagLayout());
        GridBagConstraints grid = new GridBagConstraints();
            
        Border title = BorderFactory.createTitledBorder("Best Books");
        fieldPanel.setBorder(title);
        	
        JLabel transparent = new JLabel("   ");
        
        JRadioButton searchBook = new JRadioButton("Search for Books");
        JComboBox books = new JComboBox();
        books.setPreferredSize( new Dimension( 270, 24 ) );
        String[] which_Book = {"Most reviewed books", "Most awarded books", "Most reviewed and awarded books", "Most awarded series", "Most translated type of all languages"};
        for (int i = 0; i < which_Book.length; i++) {
            books.addItem(which_Book[i]);
        }
        books.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                JComboBox comboBox = (JComboBox) event.getSource();
            	Object selected = comboBox.getSelectedItem();
            	String Books = selected.toString();
            		
            	if (Books.equals("Most reviewed books")) {
                    bestBookValContainer.setQuery("Most Reviewed Books");               
                }
                else if (Books.equals("Most awarded books")) {
                    bestBookValContainer.setQuery("Most Awarded Books");                   
                }
                else if(Books.equals("Most reviewed and awarded books")) {
                    bestBookValContainer.setQuery("Most Popular Books");
                }
                else if(Books.equals("Most awarded series")) {
                    bestBookValContainer.setQuery("Most Awarded Series");
                }
                else {
                    bestBookValContainer.setQuery("Most translated type of all languages");
                }
            }
        });
        
        JRadioButton translated = new JRadioButton("  Most Translated Book Types");
        JRadioButton reviewedAuthor = new JRadioButton("  Most reviewed book of author ");
        JRadioButton webPresence = new JRadioButton("  Most extensive web presence awarded as ");
        JRadioButton seriesAward = new JRadioButton("  Publication series with most book awarded as ");
        
        ButtonGroup bg = new ButtonGroup();
        bg.add(searchBook);
        bg.add(translated);
        bg.add(reviewedAuthor);
        bg.add(webPresence);
        bg.add(seriesAward);
        searchBook.setSelected(true); 
        
            
        JTextField translatedT = new JTextField("Please Enter a Language");
        translatedT.setPreferredSize( new Dimension( 270, 24 ) );
        translatedT.setForeground(Color.gray);
        translatedT.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if (!translated.isSelected()) {
                    translatedT.setEditable(false);
 
                }
                else {
                    translatedT.setEditable(true);
                    String content = translatedT.getText();
                    if (content.trim().equals("Please Enter a Language")) {
                        translatedT.setText("");
                        translatedT.setForeground(Color.black);
                    }
                }
                
            }
            public void focusLost(FocusEvent e) {
                String content = translatedT.getText();
                if (content.trim().equals("")) {
                    translatedT.setForeground(Color.gray);
                    translatedT.setText("Please Enter a Language");
                } 
                else {
                    bestBookValContainer.setText(translatedT.getText());
                }
            }
        });
        

	
        JTextField webPresenceT = new JTextField("Please Enter an Award Name");
        webPresenceT.setPreferredSize( new Dimension( 270, 24 ) );
        webPresenceT.setForeground(Color.gray);
        webPresenceT.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if (!webPresence.isSelected()) {
                    webPresenceT.setEditable(false);
 
                }
                else {
                    webPresenceT.setEditable(true);
                    String content = webPresenceT.getText();
                    System.out.println(content.trim());
                    if (content.trim().equals("Please Enter an Award Name")) {
                        webPresenceT.setText("");
                        webPresenceT.setForeground(Color.black);
                    }
                }
            }
            public void focusLost(FocusEvent e) {
                String content = webPresenceT.getText();
                if (content.trim().equals("")) {
                    webPresenceT.setForeground(Color.gray);
                    webPresenceT.setText("Please Enter an Award Name");
                } 
                else {
                    bestBookValContainer.setText(webPresenceT.getText());
                }
            }
        });
	
        JTextField reviewedAuthorT = new JTextField("Please Enter an Author Name");
        reviewedAuthorT.setPreferredSize( new Dimension( 270, 24 ) );
        reviewedAuthorT.setForeground(Color.gray);
        reviewedAuthorT.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if (!reviewedAuthor.isSelected()) {
                    reviewedAuthorT.setEditable(false);
 
                }
                else {
                    reviewedAuthorT.setEditable(true);
                    String content = reviewedAuthorT.getText();
                    if (content.trim().equals("Please Enter an Author Name")) {
                        reviewedAuthorT.setText("");
                        reviewedAuthorT.setForeground(Color.black);
                    }
                }
            }
            public void focusLost(FocusEvent e) {
                String content = reviewedAuthorT.getText();
                if (content.trim().equals("")) {
                    reviewedAuthorT.setForeground(Color.gray);
                    reviewedAuthorT.setText("Please Enter an Author Name");
                } 
                else {
                    bestBookValContainer.setText(reviewedAuthorT.getText());
                }
            }
        });
	
        JTextField seriesAwardT = new JTextField("Please Enter an Award Type");
        seriesAwardT.setPreferredSize( new Dimension( 270, 24 ) );
        seriesAwardT.setForeground(Color.gray);
        seriesAwardT.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if (!seriesAward.isSelected()) {
                    seriesAwardT.setEditable(false);
 
                }
                else {
                    seriesAwardT.setEditable(true);
                    String content = seriesAwardT.getText();
                    if (content.trim().equals("Please Enter an Award Type")) {
                        seriesAwardT.setText("");
                        seriesAwardT.setForeground(Color.black);
                    }
                }
            }
            public void focusLost(FocusEvent e) {
                String content = seriesAwardT.getText();
                if (content.trim().equals("")) {
                    seriesAwardT.setForeground(Color.gray);
                    seriesAwardT.setText("Please Enter an Award Type");
                }    
                else {
                    bestBookValContainer.setText(seriesAwardT.getText());
                }
            }
        });
        
        translated.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if(translated.isSelected()) {
                    bestBookValContainer.setQuery("Most Translated Book Types");
                }
                webPresenceT.setForeground(Color.gray);
                webPresenceT.setText("Please Enter an Award Name"); 
                reviewedAuthorT.setForeground(Color.gray);
                reviewedAuthorT.setText("Please Enter an Author Name");
                seriesAwardT.setForeground(Color.gray);
                seriesAwardT.setText("Please Enter an Award Type");
            }
        });
        
        reviewedAuthor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if(reviewedAuthor.isSelected()) {
                    bestBookValContainer.setQuery("  Most reviewed book of author ");
                }
                translatedT.setForeground(Color.gray);
                translatedT.setText("Please Enter a Language");
                webPresenceT.setForeground(Color.gray);
                webPresenceT.setText("Please Enter an Award Name"); 
                seriesAwardT.setForeground(Color.gray);
                seriesAwardT.setText("Please Enter an Award Type");
            }
        });
        
        webPresence.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if(webPresence.isSelected()) {
                    bestBookValContainer.setQuery("Most extensive web presence awarded as ");
                }
                translatedT.setForeground(Color.gray);
                translatedT.setText("Please Enter a Language");
                reviewedAuthorT.setForeground(Color.gray);
                reviewedAuthorT.setText("Please Enter an Author Name");
                seriesAwardT.setForeground(Color.gray);
                seriesAwardT.setText("Please Enter an Award Type");
            }
        });
        
        seriesAward.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if(seriesAward.isSelected()) {
                    bestBookValContainer.setQuery("Publication series with most book awarded as ");
                }               
                translatedT.setForeground(Color.gray);
                translatedT.setText("Please Enter a Language");
                reviewedAuthorT.setForeground(Color.gray);
                reviewedAuthorT.setText("Please Enter an Author Name");
                webPresenceT.setForeground(Color.gray);
                webPresenceT.setText("Please Enter an Award Name"); 
            }
        });
        
        searchBook.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {              
                translatedT.setForeground(Color.gray);
                translatedT.setText("Please Enter a Language");
                reviewedAuthorT.setForeground(Color.gray);
                reviewedAuthorT.setText("Please Enter an Author Name");
                webPresenceT.setForeground(Color.gray);
                webPresenceT.setText("Please Enter an Award Name"); 
                seriesAwardT.setForeground(Color.gray);
                seriesAwardT.setText("Please Enter an Award Type");
            }
        });
        	
        ImageIcon searchIcon = new ImageIcon("src/search-button.png");
        Image searchImg = searchIcon.getImage();
        Image newSearchImg = searchImg.getScaledInstance(70, 30,  java.awt.Image.SCALE_SMOOTH );
        searchIcon = new ImageIcon(newSearchImg);
        JButton search = new JButton(searchIcon);
        search.setOpaque(false);
        search.setContentAreaFilled(false);
        search.setBorderPainted(false);
        search.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	backgroundPic.setVisible(false);
            	scrollPane1.setViewportView(table);
                table.setModel(new DefaultTableModel());   
                String query = bestBookValContainer.getQuery();
                String text = bestBookValContainer.getText();
                displayBestBook(query, text);    	
                System.out.println(query);
            }
        });
        	
        	
        grid.anchor = GridBagConstraints.WEST;
        grid.ipady = 5;
        grid.ipadx = 5;
        grid.gridx = 0;
        grid.gridy = 0;
        fieldPanel.add(searchBook, grid);
            
        grid.gridx = 0;
        grid.gridy = 1;
        fieldPanel.add(translated, grid);
	
        grid.gridx = 0;
        grid.gridy = 2;
        fieldPanel.add(reviewedAuthor, grid);
	
        grid.gridx = 0;
        grid.gridy = 3;
        fieldPanel.add(webPresence, grid);
	
        grid.gridx = 0;
        grid.gridy = 4;
        fieldPanel.add(seriesAward, grid);
        	
        grid.gridx = 1;
        grid.gridy = 0;
        fieldPanel.add(transparent, grid);
        	
        grid.gridx = 2;
        grid.gridy = 0;
        fieldPanel.add(books, grid);        	
    
        grid.gridx = 2;
        grid.gridy = 1;
        fieldPanel.add(translatedT, grid);
	
        grid.gridx = 2;
        grid.gridy = 2;
        fieldPanel.add(reviewedAuthorT, grid);
	
        grid.gridx = 2;
        grid.gridy = 3;
        fieldPanel.add(webPresenceT, grid);
	
        grid.gridx = 2;
        grid.gridy = 4;
        fieldPanel.add(seriesAwardT, grid);
	
        grid.ipady = 0;
        grid.anchor = GridBagConstraints.CENTER;
        grid.gridx = 2;
        grid.gridy = 5;
        grid.gridheight = 2;
        fieldPanel.add(search, grid);
        	
        
        	
        add(fieldPanel, BorderLayout.NORTH);
        add(resultPanel, BorderLayout.CENTER);
        
    }

    public void displayBestBook(String query, String text) { 
        
        bestBookInput bestBookInput = new bestBookInput();
    
        switch(query) {
            case "Most Reviewed Books":
            case "Most Awarded Books":
            case "Most Awarded Series":
            case "Most Popular Books":
            case "Most translated type of all languages":
                bestBookInput.set_Query(query);
                break;
            default:
                bestBookInput.set_Query(query);
                bestBookInput.set_TextInput(text);
            }       
	
        bestBookDAO bestBookDAO = new bestBookDAO(bestBookInput);                                          
                
        System.out.println("");
        System.out.println("***********NEW SEARCH************");
        
        List<bestBook> statsList; bestBookTableModel1 model1;
        bestBookTableModel2 model2; bestBookTableModel2 model3;
    
        try {
            switch(query)  {
                case "Most Awarded Series": 
                case "Most Reviewed Books":
                case "Most Awarded Books": 
                case "Most Popular Books": 
                case "Most translated type of all languages":
                    System.out.println(query);
                    statsList = bestBookDAO.mostBook();
                    model1 = new bestBookTableModel1(statsList);	               
                    table.setModel(model1);
                    break;
			
                case "Most Translated Book Types":
                case "Most extensive web presence awarded as ":
                case "Publication series with most book awarded as ": 
            
                    statsList = bestBookDAO.mostInputBook();
                    model1 = new bestBookTableModel1(statsList);	               
                    table.setModel(model1);
                    break;

                case "  Most reviewed book of author ":
            
                    statsList = bestBookDAO.mostInputBook();
                    model2 = new bestBookTableModel2(statsList);	               
                    table.setModel(model2);
                    break;
            
            }                   					
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(bestBookTabView.this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE); 
        }            			
    }    
}
