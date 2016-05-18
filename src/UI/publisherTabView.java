package UI;

import Core.Publisher;
import DAO.PublisherDAO;
import DAO.PublisherInput;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;

public class publisherTabView extends JPanel {
    
    private final JTable table  = new JTable();
    
    public publisherTabView() {
        this.getAllPublishers();
    }
    
    public void getAllPublishers() {
            
        JPanel fieldPanel = new JPanel();
        JPanel resultPanel = new JPanel();
            
        PublisherInput publisherInput = new PublisherInput();
            
        // resultPanel
            
        JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane1.setPreferredSize(new Dimension(710, 465));
        scrollPane1.setViewportView(table);
        resultPanel.add(scrollPane1);
            
        // fieldPanel
            
        fieldPanel.setLayout(new GridBagLayout());
        GridBagConstraints grid = new GridBagConstraints();
            
        Border title = BorderFactory.createTitledBorder("Publishers");
        fieldPanel.setBorder(title);
            
        JComboBox year = new JComboBox();
        year.setPreferredSize(new Dimension(200, 20));
        year.addItem("");
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
            
        year.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
            	String Year = year.getSelectedItem().toString();
            	if ((publisherInput.get_Selection() == "Most Number Of Publications In Year") && Year != "") {
                    publisherInput.set_Year(Year);
            	} else {
                    publisherInput.set_Year(null);
            	}
            }
        });
            
        JRadioButton publNum = new JRadioButton("Most Number Of Publications In Year");
        JRadioButton authorNum = new JRadioButton("Average Number Of Authors Per Year");
        JRadioButton avgPrice = new JRadioButton("Average Price of Novels");
        ButtonGroup bg = new ButtonGroup();
        bg.add(publNum);
        bg.add(authorNum);
        bg.add(avgPrice);
            
        ActionListener selectionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
            	AbstractButton selected = (AbstractButton) actionEvent.getSource();
            	String Selection = selected.getText();
            	String Year = year.getSelectedItem().toString();
            	publisherInput.set_Selection(Selection);
            		
            	if (Selection == "Most Number Of Publications In Year") {
                    if (Year != "") {
            		publisherInput.set_Year(Year);
                    }
                }
                else {
            		publisherInput.set_Year(null);
                }
            }
        };
            
        publNum.addActionListener(selectionListener);
        authorNum.addActionListener(selectionListener);
        avgPrice.addActionListener(selectionListener);      
            
        ImageIcon searchIcon = new ImageIcon("src/search-button.png");
        Image searchImg = searchIcon.getImage();
        Image newSearchImg = searchImg.getScaledInstance(70, 30,  java.awt.Image.SCALE_SMOOTH );
        searchIcon = new ImageIcon(newSearchImg);
        JButton search = new JButton(searchIcon);
        search.setOpaque(false);
        search.setContentAreaFilled(false);
        search.setBorderPainted(false); 
        search.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("");
                System.out.println("************New Search***********");
                System.out.println("Selection: " + publisherInput.get_Selection());
                System.out.println("Year: " + publisherInput.get_Year());
            		
                String Selection = publisherInput.get_Selection();
                String Year = publisherInput.get_Year();
                PublisherDAO publisherDAO = new PublisherDAO();
            		
                try {                          
                    if (Selection == null) {
                        throw new Exception("Selection not made!");
                    } else if ((Selection == "Most Number Of Publications In Year") && Year == null) {
                        throw new Exception("Year not selected!");
                    } else if (Selection == "Most Number Of Publications In Year") {
                        List<Publisher> publisherList = publisherDAO.searchPublisher_Publ(publisherInput);
                        PublisherPublTableModel model = new PublisherPublTableModel(publisherList);
                        table.setModel(model);		
                    } else if (Selection == "Average Number Of Authors Per Year") {
                        List<Publisher> publisherList = publisherDAO.searchPublisher_Author(publisherInput);
                        PublisherAuthorTableModel model = new PublisherAuthorTableModel(publisherList);
                        table.setModel(model);		
                    } else if (Selection == "Average Price of Novels") {
                        List<Publisher> publisherList = publisherDAO.searchPublisher_Price(publisherInput);
                        PublisherPriceTableModel model = new PublisherPriceTableModel(publisherList);
                        table.setModel(model);
                    }
                }
                catch (Exception e2) {
                    System.out.println("Error: " + e2);
                }
            }
        });            
            
        grid.anchor = GridBagConstraints.WEST;
        grid.ipady = 5;
        grid.gridx = 0;
        grid.gridy = 0;
        fieldPanel.add(publNum, grid);
        grid.gridx = 1;
        grid.gridy = 0;
        fieldPanel.add(year, grid);
            
        grid.gridx = 0;
        grid.gridy = 1;
        fieldPanel.add(authorNum, grid);
            
        grid.gridx = 0;
        grid.gridy = 2;
        fieldPanel.add(avgPrice, grid);
            
        grid.anchor = GridBagConstraints.EAST;
        grid.ipady = 0;
        grid.gridx = 1;
        grid.gridy = 2;
        grid.gridheight = 2;
        fieldPanel.add(search, grid);
            
        add(fieldPanel, BorderLayout.NORTH);
        add(resultPanel, BorderLayout.SOUTH);
    }
}
