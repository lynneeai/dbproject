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
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
            
        public void setSelected(Boolean selected) {
            this.selected = selected;
        }
            
        public void setRePlusAw(Boolean rePlusAw) {
            this.rePlusAw = rePlusAw;
        }
            
        public String getQuery() {
            return this.query;
        }
                
        public String getText() {
            return this.text;
        } 
            
        public Boolean getSelected() {
            return this.selected;
        } 
            
        public Boolean getRePlusAw() {
            return this.rePlusAw;
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
            
        JRadioButton reviewed = new JRadioButton("Most Reviewed Books");
        JRadioButton awarded = new JRadioButton("Most Awarded Books");
        JRadioButton awardedSeries= new JRadioButton("Most Awarded Series");
	
        reviewed.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(bestBookValContainer.getSelected() == true && bestBookValContainer.getQuery().equals("Most Awarded Books")) {
                    if(reviewed.isSelected()) {
                        bestBookValContainer.setQuery("Most Popular Books");
                        bestBookValContainer.setSelected(true);
                        bestBookValContainer.setRePlusAw(true);
                    }
                }
                if ((reviewed.isSelected() && !awarded.isSelected() && bestBookValContainer.getSelected() == false) || 
                    (reviewed.isSelected() && !awarded.isSelected() && bestBookValContainer.getRePlusAw() == true))
                {
                    bestBookValContainer.setQuery(reviewed.getText());
                    bestBookValContainer.setSelected(true);
                    bestBookValContainer.setRePlusAw(false);
                }   
                else if ((awarded.isSelected() && !reviewed.isSelected() && bestBookValContainer.getSelected() == false) ||
                         (awarded.isSelected() && !reviewed.isSelected() && bestBookValContainer.getRePlusAw() == true))
                {
                    bestBookValContainer.setQuery(awarded.getText());
                    bestBookValContainer.setSelected(true);
                    bestBookValContainer.setRePlusAw(false);
                } 
                else if (!reviewed.isSelected() && !awarded.isSelected() && bestBookValContainer.getSelected() == true) {
                    bestBookValContainer.setSelected(false);
                    bestBookValContainer.setRePlusAw(false);
                }	
            }
        });
	
        awarded.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(bestBookValContainer.getSelected() == true && bestBookValContainer.getQuery().equals("Most Reviewed Books")) {
                    if (awarded.isSelected()) {
                        bestBookValContainer.setQuery("Most Popular Books");
                        bestBookValContainer.setSelected(true);
                        bestBookValContainer.setRePlusAw(true);
                        }
                }
                if ((awarded.isSelected() && !reviewed.isSelected() && bestBookValContainer.getSelected() == false) ||
                    (awarded.isSelected() && !reviewed.isSelected() && bestBookValContainer.getRePlusAw() == true))
                {
                    bestBookValContainer.setQuery(awarded.getText());
                    bestBookValContainer.setSelected(true);
                    bestBookValContainer.setRePlusAw(false);
                } 
                else if ((reviewed.isSelected() && !awarded.isSelected() && bestBookValContainer.getSelected() == false) || 
                         (reviewed.isSelected() && !awarded.isSelected() && bestBookValContainer.getRePlusAw() == true))
                {
                    bestBookValContainer.setQuery(reviewed.getText());
                    bestBookValContainer.setSelected(true);
                    bestBookValContainer.setRePlusAw(false);
                } 
                else if (!awarded.isSelected() && reviewed.isSelected() && bestBookValContainer.getSelected() == true) {
                    bestBookValContainer.setSelected(false);
                    bestBookValContainer.setRePlusAw(false);
                }	
            }
        });
	
        awardedSeries.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (awardedSeries.isSelected() && bestBookValContainer.getSelected() == false) {
                    bestBookValContainer.setQuery(awardedSeries.getText());
                    bestBookValContainer.setSelected(true);
                }   		
                else if (!awardedSeries.isSelected() && bestBookValContainer.getSelected() == true) {
                    bestBookValContainer.setSelected(false);
                }	
            }
        });
            
        JLabel translated = new JLabel("  Most Translated Book Types");
        JTextField translatedT = new JTextField("Please Enter a Language");
        translatedT.setPreferredSize( new Dimension( 190, 24 ) );
        translatedT.setForeground(Color.gray);
        translatedT.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                String content = translatedT.getText();
                if (content.trim().equals("Please Enter a Language") && bestBookValContainer.getSelected() == false) {
                    translatedT.setEditable(true);
                    translatedT.setText("");
                    translatedT.setForeground(Color.black);
                }
                else {
                    if(content.trim().equals("Please Enter a Language")) {
                        translatedT.setEditable(false);
                    }
                }
            }
            public void focusLost(FocusEvent e) {
                String content = translatedT.getText();
                if (content.trim().equals("")) {
                    bestBookValContainer.setSelected(false);
                    translatedT.setForeground(Color.gray);
                    translatedT.setText("Please Enter a Language");
                } 
                else {
                    bestBookValContainer.setQuery("Most Translated Book Types");
                    bestBookValContainer.setText(translatedT.getText());
                    bestBookValContainer.setSelected(true);
                }
            }
        });
	
        JLabel webPresence = new JLabel("  Most extensive web presence awarded as ");
        JTextField webPresenceT = new JTextField("Please Enter an Award Type");
        webPresenceT.setPreferredSize( new Dimension( 190, 24 ) );
        webPresenceT.setForeground(Color.gray);
        webPresenceT.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                String content = webPresenceT.getText();
                if (content.trim().equals("Please Enter an Award Type") && bestBookValContainer.getSelected() == false) {
                    webPresenceT.setEditable(true);
                    webPresenceT.setText("");
                    webPresenceT.setForeground(Color.black);
                }
                else {
                    if(content.trim().equals("Please Enter an Award Type")) {
                        webPresenceT.setEditable(false);
                    }
                }
            }
            public void focusLost(FocusEvent e) {
                String content = webPresenceT.getText();
                if (content.trim().equals("")) {
                    bestBookValContainer.setSelected(false);
                    webPresenceT.setForeground(Color.gray);
                    webPresenceT.setText("Please Enter an Award Type");
                } 
                else {
                    bestBookValContainer.setQuery("Most extensive web presence awarded as ");
                    bestBookValContainer.setText(webPresenceT.getText());
                    bestBookValContainer.setSelected(true);
                }
            }
        });
	
        JLabel reviewedAuthor = new JLabel("  Most reviewed book of author ");
        JTextField reviewedAuthorT = new JTextField("Please Enter an Author Name");
        reviewedAuthorT.setPreferredSize( new Dimension( 190, 24 ) );
        reviewedAuthorT.setForeground(Color.gray);
        reviewedAuthorT.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                String content = reviewedAuthorT.getText();
                if (content.trim().equals("Please Enter an Author Name") && bestBookValContainer.getSelected() == false) {
                    reviewedAuthorT.setEditable(true);
                    reviewedAuthorT.setText("");
                    reviewedAuthorT.setForeground(Color.black);
                }
                else {
                    if(content.trim().equals("Please Enter an Author Name")) {
                        reviewedAuthorT.setEditable(false);
                    }
                }
            }
            public void focusLost(FocusEvent e) {
                String content = reviewedAuthorT.getText();
                if (content.trim().equals("")) {
                    bestBookValContainer.setSelected(false);
                    reviewedAuthorT.setForeground(Color.gray);
                    reviewedAuthorT.setText("Please Enter an Author Name");
                } 
                else {
                    bestBookValContainer.setQuery("  Most reviewed book of author ");
                    bestBookValContainer.setText(reviewedAuthorT.getText());
                    bestBookValContainer.setSelected(true);
                }
            }
        });
	
        JLabel seriesAward = new JLabel("  Publication series with most book awarded as ");
        JTextField seriesAwardT = new JTextField("Please Enter an Award Type");
        seriesAwardT.setPreferredSize( new Dimension( 190, 24 ) );
        seriesAwardT.setForeground(Color.gray);
        seriesAwardT.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                String content = seriesAwardT.getText();
                if (content.trim().equals("Please Enter an Award Type") && bestBookValContainer.getSelected() == false) {
                    seriesAwardT.setEditable(true);
                    seriesAwardT.setText("");
                    seriesAwardT.setForeground(Color.black);
                }
                else {
                    if(content.trim().equals("Please Enter an Award Type")) {
                       seriesAwardT.setEditable(false);
                    }
                }
            }
            public void focusLost(FocusEvent e) {
                String content = seriesAwardT.getText();
                if (content.trim().equals("")) {
                    bestBookValContainer.setSelected(false);
                    seriesAwardT.setForeground(Color.gray);
                    seriesAwardT.setText("Please Enter an Award Type");
                }    
                else {
                    bestBookValContainer.setQuery("Publication series with most book awarded as ");
                    bestBookValContainer.setText(seriesAwardT.getText());
                    bestBookValContainer.setSelected(true);
                }
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
        fieldPanel.add(reviewed, grid);
            
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
	
        grid.gridx = 0;
        grid.gridy = 5;
        fieldPanel.add(awardedSeries, grid);
        	
        grid.gridx = 1;
        grid.gridy = 0;
        fieldPanel.add(transparent, grid);
        	
        grid.gridx = 2;
        grid.gridy = 0;
        fieldPanel.add(awarded, grid);        	
    
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
