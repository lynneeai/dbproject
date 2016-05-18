package UI;

import Core.publicStat;
import DAO.publicStatDAO;
import DAO.publicStatInput;
import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.font.TextAttribute;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author mengdong
 */
public class publicStatTabView extends JPanel {
    
    private JTable table  = new JTable();
    
    public publicStatTabView() {
        this.getPublicStat();
    }
    
    public class publicStatValContainer {
        private String query;
        private String choice;
        private Boolean selected;
            
        public publicStatValContainer() {
            this.query = null;
            this.choice = null;
            this.selected = false;
        }
            
        public void setQuery(String query) {
            this.query = query;
        }
                
        public void setChoice(String choice) {
            this.choice = choice;
        }
            
        public void setSelected(Boolean selected) {
            this.selected = selected;
        }
            
        public String getQuery() {
            return this.query;
        }
                
        public String getChoice() {
            return this.choice;
        } 
            
        public Boolean getSelected() {
            return this.selected;
        } 
    }
	
        
    private void getPublicStat() {
            
        publicStatValContainer publicStatValContainer = new publicStatValContainer();
        	
        JPanel fieldPanel = new JPanel();
        JPanel resultPanel = new JPanel();
        
        // resultPanel
    	
        JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane1.setPreferredSize(new Dimension(710, 445));
        
        ImageIcon backgroundIcon = new ImageIcon("src/background.jpg");
        Image backgroundImg = backgroundIcon.getImage();
        Image newBackgroundImg = backgroundImg.getScaledInstance(700, 440,  java.awt.Image.SCALE_SMOOTH );
        backgroundIcon = new ImageIcon(newBackgroundImg);
        JLabel backgroundPic = new JLabel(backgroundIcon);
        scrollPane1.getViewport().add(backgroundPic, scrollPane1);
        
        resultPanel.add(scrollPane1);
        //scrollPane1.setViewportView(table);
        	
        // fieldPanel
        fieldPanel.setLayout(new GridBagLayout());
        GridBagConstraints grid = new GridBagConstraints();
            
        Border title = BorderFactory.createTitledBorder("Publication Statistics");
        fieldPanel.setBorder(title);
            
        JRadioButton avgSeries= new JRadioButton("Average Number of Publications per Series");
        JRadioButton avgTitle= new JRadioButton("Average Price of Title with Most Publications");
           
        avgSeries.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (avgSeries.isSelected() && publicStatValContainer.getSelected() == false) {
                    publicStatValContainer.setQuery(avgSeries.getText());
                    publicStatValContainer.setSelected(true);
                }   		
                else if (!avgSeries.isSelected() && publicStatValContainer.getSelected() == true) {
                    publicStatValContainer.setSelected(false);
                }	
            }
        });
            
        avgTitle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (avgTitle.isSelected() && publicStatValContainer.getSelected() == false) {
                    publicStatValContainer.setQuery(avgTitle.getText());
                    publicStatValContainer.setSelected(true);
                }   		
                else if (!avgTitle.isSelected() && publicStatValContainer.getSelected() == true) {
                    publicStatValContainer.setSelected(false);
                }	
            }
        });
            
        JLabel bookNum = new JLabel("Total Books Published In Year");            
        Choice year = new Choice();
        year.setPreferredSize(new Dimension(195, 20));
        year.add("");
        for (int i = 2016; i >= 1; i--) {
            if (i >= 1000) {
                year.addItem(Integer.toString(i));
            }
            if ((i >= 100) && (i <= 999)) {
            	year.addItem("0" + Integer.toString(i));
            }
            if ((i >= 10) && (i <= 99)) {
            	year.addItem("00" + Integer.toString(i));
            }
            if ((i >= 1) && (i <= 9)) {
            	year.addItem("000" + Integer.toString(i));
            }
        }
            
        year.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {}
            public void focusLost(FocusEvent e) {
                publicStatValContainer.setQuery(bookNum.getText());
                publicStatValContainer.setChoice(year.getSelectedItem());
            }
        });
            
        JLabel comicSize = new JLabel("Number of Comics with Size");
        Choice size = new Choice();
        size.setPreferredSize(new Dimension(200, 20));
        size.add("");
        size.addItem("Small (<50 pages)");
        size.addItem("Medium (<100 pages)");
        size.addItem("Large (>100 pages)");		
            
        size.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {}
            public void focusLost(FocusEvent e) {
                publicStatValContainer.setQuery(comicSize.getText());
                publicStatValContainer.setChoice(size.getSelectedItem());
            }
        });
            
        JButton stats = new JButton("Statistics On Publications Per Year");
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
                String query = publicStatValContainer.getQuery();
                String choice = publicStatValContainer.getChoice();
                displayPublicStat(query, choice);    	
                System.out.println(query);   			
            }
        });
            
        stats.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { 
            	backgroundPic.setVisible(false);
            	scrollPane1.setViewportView(table);
                publicStatValContainer.setQuery(stats.getText());
                table.setModel(new DefaultTableModel()); 
                String query = publicStatValContainer.getQuery();
                String choice = publicStatValContainer.getChoice();
                displayPublicStat(query, choice);    	
                System.out.println(query);     			
            }
        });
            
        stats.setBorder(null);
        stats.setOpaque(true);
        stats.setForeground(Color.blue);
        stats.setFont(new Font("Plain", Font.PLAIN, 13));
        Font font = stats.getFont();
        Map attributes = font.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        stats.setFont(font.deriveFont(attributes));       
        stats.setFocusPainted(true);
        stats.setBorderPainted(true);
            
        grid.ipadx = 5;
        grid.ipady = 5;
        grid.gridx = 0;
        grid.gridy = 0;
        fieldPanel.add(avgSeries, grid);
			
        grid.ipadx = 5;
        grid.ipady = 5;
        grid.gridx = 1;
        grid.gridy = 0;
        fieldPanel.add(avgTitle, grid);			
		
        grid.anchor = GridBagConstraints.EAST;
        grid.ipadx = 5;
        grid.ipady = 5;
        grid.gridx = 0;
        grid.gridy = 1;
        fieldPanel.add(bookNum, grid);
		
        grid.anchor = GridBagConstraints.CENTER;
        grid.gridx = 1;
        grid.gridy = 1;
        fieldPanel.add(year, grid);
		
        grid.anchor = GridBagConstraints.EAST;
        grid.gridx = 0;
        grid.gridy = 2;
        fieldPanel.add(comicSize, grid);
            
        grid.anchor = GridBagConstraints.CENTER;
        grid.ipadx = 0;
        grid.ipady = 0;
        grid.gridx = 1;
        grid.gridy = 2;
        fieldPanel.add(size, grid);			
            
        grid.anchor = GridBagConstraints.EAST;
        grid.gridx = 0;
        grid.gridy = 3;
        fieldPanel.add(stats, grid);
            
        grid.anchor = GridBagConstraints.CENTER;
        grid.gridwidth = 1;
        grid.ipadx = 0;
        grid.ipady = 0;
        grid.gridx = 1;
        grid.gridy = 3;
        grid.gridheight = 2;
        fieldPanel.add(search, grid);
            
        
            
        add(fieldPanel, BorderLayout.NORTH);
        add(resultPanel, BorderLayout.CENTER);
            
    }
        
    public void displayPublicStat(String query, String choice) { 
        
        publicStatInput publicStatInput = new publicStatInput();
        switch(query) {
            case "Average Number of Publications per Series":
            case "Average Price of Title with Most Publications":
            case "Statistics On Publications Per Year":
		publicStatInput.set_Query(query);
                break;
            default:
                publicStatInput.set_Query(query);
                publicStatInput.set_Choice(choice);
        }
			
        publicStatDAO publicStatDAO = new publicStatDAO(publicStatInput);
            
        System.out.println("");
        System.out.println("***********NEW SEARCH************");
            
        List<publicStat> statsList; publicStatTableModel1 model1;
	publicStatTableModel2 model2; publicStatTableModel3 model3;
    
        try {
            switch(query)  {
                case "Average Number of Publications per Series": 
                    statsList = publicStatDAO.showStat();
                    model3 = new publicStatTableModel3(statsList);	               
                    table.setModel(model3);
                    break;
                case "Average Price of Title with Most Publications":
                    statsList = publicStatDAO.showStat();
                    model2 = new publicStatTableModel2(statsList);	               
                    table.setModel(model2);
                    break;
                case "Statistics On Publications Per Year": 
                    statsList = publicStatDAO.showStat();
                    model1 = new publicStatTableModel1(statsList);	               
                    table.setModel(model1);
                    break;
                case "Total Books Published In Year": 
                    statsList = publicStatDAO.showInputStat();
                    model1 = new publicStatTableModel1(statsList);	               
                    table.setModel(model1);
                    break;
                case "Number of Comics with Size": 
                    statsList = publicStatDAO.showInputStat();
                    model3 = new publicStatTableModel3(statsList);	               
                    table.setModel(model3);
                    break;
                }                   					
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(publicStatTabView.this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE); 
        }                      			
    }
}
