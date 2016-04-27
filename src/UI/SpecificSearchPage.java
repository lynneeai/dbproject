package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.font.TextAttribute;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.Choice;


public class SpecificSearchPage {
    
    private JPanel statsPanel;
    private final JTabbedPane pane = new JTabbedPane();
	
    public void specificSearch() {
        statsPanel = new JPanel();
        statsPanel.setBorder(new EmptyBorder(10, 10, 18, 10));
        statsPanel.setLayout(new BorderLayout(0, 0));
        
        
        Main.mainFrame.add(statsPanel);
        Main.mainFrame.revalidate();
        Main.mainFrame.repaint();
        
        
        String[] tabContents = {"Publishers", "Authors", "Best Books", "Publication Statistics", "Comics"};
        
        for (int i = 0; i < 5; i++) {
            pane.add(tabContents[i], new TabContent(i));        
        }
        
        JPanel backPanel = new JPanel();
        JButton back = new JButton("back");
        back.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		backPanel.removeAll();
        		pane.removeAll();
        		statsPanel.removeAll();
        		Main.mainFrame.remove(statsPanel);
        		DetailSearchPage detailSearchPage = new DetailSearchPage();
        		detailSearchPage.detailSearch();
        		
        	}
        });
        backPanel.add(back);
        statsPanel.add(pane, BorderLayout.CENTER);
        statsPanel.add(backPanel, BorderLayout.SOUTH);

        //Main.mainFrame.pack();
        Main.mainFrame.setVisible(true);
    }
    
    private static class TabContent extends JPanel {

        private TabContent(int i) {
            setOpaque(true);
            if (i == 0) {
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
            
            JPanel panel1 = new JPanel();
            panel1.setSize(100, 30);
            FlowLayout flowLayout1 = (FlowLayout) panel1.getLayout();
            flowLayout1.setAlignment(FlowLayout.LEFT);
            add(panel1); 
            panel1.setLayout(new GridLayout(3,2));
            
            Border title = BorderFactory.createTitledBorder("Publishers");
            panel1.setBorder(title);
            
            JCheckBox minPricePublisher = new JCheckBox("Show minimum price of the publisher");
            minPricePublisher.addFocusListener(new FocusListener() {
        	public void focusGained(FocusEvent e) {}
        	public void focusLost(FocusEvent e) {
        		//UserInput.set_Awarded(awardedBook.isSelected());
        	}
            });
            panel1.add(minPricePublisher);
            
            JCheckBox avgPricePublisher = new JCheckBox("Show average price of the publisher");
            avgPricePublisher.addFocusListener(new FocusListener() {
        	public void focusGained(FocusEvent e) {}
        	public void focusLost(FocusEvent e) {
        		//UserInput.set_Awarded(awardedBook.isSelected());
        	}
            });
            panel1.add(avgPricePublisher);
            
            JCheckBox minAllPublisher = new JCheckBox("Show minimum price of all publishers");
            minAllPublisher.addFocusListener(new FocusListener() {
        	public void focusGained(FocusEvent e) {}
        	public void focusLost(FocusEvent e) {
        		//UserInput.set_Awarded(awardedBook.isSelected());
        	}
            });
            panel1.add(minAllPublisher);
            
            JCheckBox avgAllPublisher = new JCheckBox("Show minimum price of all publishers");
            avgAllPublisher.addFocusListener(new FocusListener() {
        	public void focusGained(FocusEvent e) {}
        	public void focusLost(FocusEvent e) {
        		//UserInput.set_Awarded(awardedBook.isSelected());
        	}
            });
            panel1.add(avgAllPublisher);
            
            JTextField publisherTextField = new JTextField();
            panel1.add(publisherTextField);
            publisherTextField.setColumns(10);
            publisherTextField.setForeground(Color.gray);
            publisherTextField.setText("Publisher name for specific search");
        
            publisherTextField.addFocusListener(new FocusListener() {
                public void focusGained(FocusEvent e) {
                    String content = publisherTextField.getText();
                    if (content.trim().equals("Publisher name for specific search")) {
                        publisherTextField.setText("");
                        publisherTextField.setForeground(Color.black);
                    }
                }
                public void focusLost(FocusEvent e) {
                    String content = publisherTextField.getText();
                    if (content.trim().equals("")) {
                        publisherTextField.setForeground(Color.gray);
                        publisherTextField.setText("Publisher name for specific search");
                    } 
                }
            });
            
            JButton btnSearch = new JButton("Search");;
            btnSearch.setHorizontalAlignment(SwingConstants.LEFT);
            panel1.add(btnSearch);
            
            btnSearch.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String name = publisherTextField.getText();
                    //publisherResult(name);
                 }
            });
            
            JScrollPane scrollPane = new JScrollPane();
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setPreferredSize(new Dimension(710, 510));
            add(scrollPane);
            
        }
        
        private void getAllAuthors() {
            JPanel fieldPanel = new JPanel();
            JPanel resultPanel = new JPanel();
        	
            // fieldPanel
            
            fieldPanel.setLayout(new GridBagLayout());
            GridBagConstraints grid = new GridBagConstraints();
            
            Border title = BorderFactory.createTitledBorder("Authors");
            fieldPanel.setBorder(title);
            
            
            JRadioButton inLanguage = new JRadioButton("In Language");
            JRadioButton withTag = new JRadioButton("With Tag");
            JRadioButton bookType = new JRadioButton("Book Type");
            JRadioButton publishedYear = new JRadioButton("Published In");
            ButtonGroup bg = new ButtonGroup();
            bg.add(inLanguage);
            bg.add(withTag);
            bg.add(bookType);
            bg.add(publishedYear);
            
            
            
            Choice language = new Choice();
            Choice type = new Choice();
            Choice year = new Choice();
            Choice authors = new Choice();
            
            language.setPreferredSize(new Dimension(180, 20));
            type.setPreferredSize(new Dimension(180, 20));
            year.setPreferredSize(new Dimension(180, 20));
            authors.setPreferredSize(new Dimension(180, 20));
            
            String[] avail_Languages = {"", "English", "French", "Chinese"};
            for (int i = 0; i < avail_Languages.length; i++) {
            	language.add(avail_Languages[i]);
            }
            
            String[] avail_Type = {"", "ANTHOLOGY", "BACKCOVERART", "COLLECTION", "COVERART", "INTERIORART", "EDITOR", "ESSAY", "INTERVIEW", "NOVEL", "NONFICTION", "OMNIBUS", "POEM", "REVIEW", "SERIAL", "SHORTFICTION", "CHAPBOOK", "MAGAZINE", "FANZINE"};
            for (int i = 0; i < avail_Type.length; i++) {
            	type.add(avail_Type[i]);
            }
            
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
            
            String[] which_Author = {"All Authors", "Best Author", "Published-Most Author", "Oldest Author", "Youngest Author", "Written-Most Author"};
            for (int i = 0; i < which_Author.length; i++) {
            	authors.add(which_Author[i]);
            }
            
            
            JTextField tag = new JTextField();
            tag.setColumns(14);
            tag.setEditable(true);
            
            
            JLabel see = new JLabel("Search For Authors");
            JLabel transparent = new JLabel("     ");
            
            
            JButton search = new JButton("Search");
            
            
            grid.anchor = GridBagConstraints.WEST;
            grid.ipady = 5;
            grid.gridx = 0;
            grid.gridy = 0;
            fieldPanel.add(inLanguage, grid);
            grid.gridx = 1;
            grid.gridy = 0;
            fieldPanel.add(language, grid);
            
            grid.gridx = 0;
            grid.gridy = 1;
            fieldPanel.add(publishedYear, grid);
            grid.gridx = 1;
            grid.gridy = 1;
            fieldPanel.add(year, grid);
            
            grid.gridx = 0;
            grid.gridy = 2;
            fieldPanel.add(bookType, grid);
            grid.gridx = 1;
            grid.gridy = 2;
            fieldPanel.add(type, grid);
            
            grid.gridx = 0;
            grid.gridy = 3;
            fieldPanel.add(withTag, grid);
            grid.gridx = 1;
            grid.gridy = 3;
            grid.ipady = 0;
            fieldPanel.add(tag, grid);
            
            
            grid.ipady = 5;
            grid.anchor = GridBagConstraints.CENTER;
            grid.gridx = 2;
            grid.gridy = 0;
            fieldPanel.add(transparent, grid);
            
            grid.gridx = 3;
            grid.gridy = 0;
            fieldPanel.add(see, grid);
            grid.gridx = 4;
            grid.gridy = 0;
            fieldPanel.add(authors, grid);
            
            grid.gridx = 4;
            grid.gridy = 3;
            grid.ipady = 0;
            fieldPanel.add(search, grid);
            
            
            // resultPanel
            
            JScrollPane scrollPane1 = new JScrollPane();
            scrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane1.setPreferredSize(new Dimension(710, 485));
            resultPanel.add(scrollPane1);
            
            add(fieldPanel, BorderLayout.NORTH);
            add(resultPanel, BorderLayout.CENTER);
            
            
        }
        
        private void getMostPopulars() {
        	
        	JPanel fieldPanel = new JPanel();
        	JPanel resultPanel = new JPanel();
        	
        	// fieldPanel
        	fieldPanel.setLayout(new GridBagLayout());
            GridBagConstraints grid = new GridBagConstraints();
            
            Border title = BorderFactory.createTitledBorder("Best Books");
            fieldPanel.setBorder(title);
        	
            JLabel transparent = new JLabel("   ");
            
        	JCheckBox reviewed = new JCheckBox("Most Reviewed Books");
        	JCheckBox awarded = new JCheckBox("Most Awarded Books");
        	JCheckBox translated = new JCheckBox("Most Translated Books");
        	
        	JButton search = new JButton("Search");
        	
        	
        	grid.anchor = GridBagConstraints.WEST;
        	grid.ipady = 5;
        	grid.ipadx = 5;
        	grid.gridx = 0;
        	grid.gridy = 0;
        	fieldPanel.add(reviewed, grid);
        	
        	grid.gridx = 0;
        	grid.gridy = 1;
        	fieldPanel.add(awarded, grid);
        	
        	grid.gridx = 1;
        	grid.gridy = 0;
        	fieldPanel.add(transparent, grid);
        	
        	grid.gridx = 2;
        	grid.gridy = 0;
        	fieldPanel.add(translated, grid);
        	
        	grid.anchor = GridBagConstraints.CENTER;
        	grid.ipady = 0;
        	grid.gridx = 2;
        	grid.gridy = 1;
        	fieldPanel.add(search, grid);
        	
        	// resultPanel
        	
        	JScrollPane scrollPane1 = new JScrollPane();
            scrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane1.setPreferredSize(new Dimension(710, 535));
            resultPanel.add(scrollPane1);
        	
        	add(fieldPanel, BorderLayout.NORTH);
        	add(resultPanel, BorderLayout.CENTER);
            
        }
        
        private void getBooksPublished() {
        	
        	JPanel fieldPanel = new JPanel();
        	JPanel resultPanel = new JPanel();
        	
        	// fieldPanel
        	fieldPanel.setLayout(new GridBagLayout());
            GridBagConstraints grid = new GridBagConstraints();
            
            Border title = BorderFactory.createTitledBorder("Publication Statistics");
            fieldPanel.setBorder(title);
            
            JLabel bookNum = new JLabel("Total Books Published In Year");
            
            
            Choice year = new Choice();
            year.setPreferredSize(new Dimension(200, 20));
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
            
            JButton stats = new JButton("Statistics On Publications Per Year");
            JButton search = new JButton("Search");
            
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
            fieldPanel.add(bookNum, grid);
            
            grid.gridx = 1;
            grid.gridy = 0;
            fieldPanel.add(year, grid);
            
            grid.anchor = GridBagConstraints.WEST;
            grid.gridwidth = 2;
            grid.gridx = 0;
            grid.gridy = 1;
            fieldPanel.add(stats, grid);
            
            grid.anchor = GridBagConstraints.CENTER;
            grid.gridwidth = 1;
            grid.ipadx = 0;
            grid.ipady = 0;
            grid.gridx = 1;
            grid.gridy = 1;
            fieldPanel.add(search, grid);
            
            // resultPanel
        	
        	JScrollPane scrollPane1 = new JScrollPane();
            scrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane1.setPreferredSize(new Dimension(710, 540));
            resultPanel.add(scrollPane1);
            
            add(fieldPanel, BorderLayout.NORTH);
        	add(resultPanel, BorderLayout.CENTER);
            
        }
        
        private void getAllComics() {
        	
        	JPanel fieldPanel = new JPanel();
        	JPanel resultPanel = new JPanel();
        	
        	// fieldPanel
        	fieldPanel.setLayout(new GridBagLayout());
            GridBagConstraints grid = new GridBagConstraints();
            
            Border title = BorderFactory.createTitledBorder("Publication Statistics");
            fieldPanel.setBorder(title);
            
            JLabel transparent = new JLabel("      ");
            
            JRadioButton small = new JRadioButton("small comics (<50 pages)");
            JRadioButton medium = new JRadioButton("medium size comics (< 100 pages)");
            JRadioButton large = new JRadioButton("long comics (> 100 pages)");
            ButtonGroup bg = new ButtonGroup();
            bg.add(small);
            bg.add(medium);
            bg.add(large);
            
            JButton search = new JButton("Search");
            
            grid.anchor = GridBagConstraints.WEST;
            grid.ipady = 5;
            grid.gridx = 0;
            grid.gridy = 0;
            fieldPanel.add(small, grid);
            
            grid.gridx = 0;
            grid.gridy = 1;
            fieldPanel.add(medium, grid);
            
            grid.gridx = 0;
            grid.gridy = 2;
            fieldPanel.add(large, grid);
            
            
            grid.anchor = GridBagConstraints.CENTER;
            grid.ipady = 0;
            grid.gridx = 1;
            grid.gridy = 0;
            fieldPanel.add(transparent, grid);
            
            grid.gridx = 2;
            grid.gridy = 2;
            fieldPanel.add(search, grid);
            
            // resultPanel
        	
        	JScrollPane scrollPane1 = new JScrollPane();
            scrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane1.setPreferredSize(new Dimension(710, 510));
            resultPanel.add(scrollPane1);
            
            add(fieldPanel, BorderLayout.NORTH);
        	add(resultPanel, BorderLayout.CENTER);
            
        }
    }
}
