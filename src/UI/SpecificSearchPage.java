package UI;

import Core.publicStat;
import Core.bestBook;
import Core.AuthorTab;
import DAO.publicStatDAO;
import DAO.publicStatInput;
import DAO.bestBookInput;
import DAO.bestBookDAO;
import DAO.AuthorTabDAO;
import DAO.AuthorTabInput;

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
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.AbstractButton;
import javax.swing.JComboBox;


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
        
        for (int i = 0; i < 4; i++) {
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
    
    private class TabContent extends JPanel {

        private JTable table = new JTable();
        private TabContent(int i) {
            setOpaque(true);
            if (i == 0) {
                getAllPublishers();
            }
            else if (i == 1) {
                getAllAuthors();
            }
            else if (i == 2) {
                getBestBooks();
            }
            else {
                getBooksPublished();
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
            
            JCheckBox minAllPublisher = new JCheckBox("Show minimum price of their books");
            minAllPublisher.addFocusListener(new FocusListener() {
        	public void focusGained(FocusEvent e) {}
        	public void focusLost(FocusEvent e) {
        		//UserInput.set_Awarded(awardedBook.isSelected());
        	}
            });
            panel1.add(minAllPublisher);
            
            JCheckBox avgAllPublisher = new JCheckBox("Show average price of their books");
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
            publisherTextField.setText("Publisher (Leave empty to show all)");
        
            publisherTextField.addFocusListener(new FocusListener() {
                public void focusGained(FocusEvent e) {
                    String content = publisherTextField.getText();
                    if (content.trim().equals("Publisher'('Leave empty to show all')")) {
                        publisherTextField.setText("");
                        publisherTextField.setForeground(Color.black);
                    }
                }
                public void focusLost(FocusEvent e) {
                    String content = publisherTextField.getText();
                    if (content.trim().equals("")) {
                        publisherTextField.setForeground(Color.gray);
                        publisherTextField.setText("Publisher'('Leave empty to show all')");
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
            
            AuthorTabInput authorTabInput = new AuthorTabInput();
            
            // resultPanel
            
            JScrollPane scrollPane1 = new JScrollPane();
            scrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane1.setPreferredSize(new Dimension(710, 475));
            scrollPane1.setViewportView(table);
            resultPanel.add(scrollPane1);
        	
            // fieldPanel
            
            fieldPanel.setLayout(new GridBagLayout());
            GridBagConstraints grid = new GridBagConstraints();
            
            Border title = BorderFactory.createTitledBorder("Authors");
            fieldPanel.setBorder(title);
            
            JTextField tag = new JTextField("Please Enter Exact Tag");
            JTextField awarded = new JTextField("Enter Award Category");
            tag.setColumns(15);
            tag.setEditable(true);
            tag.setForeground(Color.gray);
            
            awarded.setColumns(15);
            awarded.setEditable(true);
            awarded.setForeground(Color.gray);
            
            
            JRadioButton inLanguage = new JRadioButton("In Language");
            JRadioButton withTag = new JRadioButton("With Tag");
            JRadioButton bookType = new JRadioButton("Book Type");
            JRadioButton publishedYear = new JRadioButton("Published In");
            JRadioButton mostAwarded = new JRadioButton("Awarded In");
            ButtonGroup bg = new ButtonGroup();
            bg.add(inLanguage);
            bg.add(withTag);
            bg.add(bookType);
            bg.add(publishedYear);
            bg.add(mostAwarded);
            
            
            JComboBox language = new JComboBox();
            JComboBox type = new JComboBox();
            JComboBox year = new JComboBox();
            JComboBox authors = new JComboBox();
            
           
            
            language.setPreferredSize(new Dimension(192, 20));
            type.setPreferredSize(new Dimension(192, 20));
            year.setPreferredSize(new Dimension(192, 20));
            //authors.setPreferredSize(new Dimension(270, 20));
            
            ActionListener selectionListener = new ActionListener() {
            	public void actionPerformed(ActionEvent actionEvent) {
            		AbstractButton selected = (AbstractButton) actionEvent.getSource();
            		String Selection = selected.getText();
            		String Language = language.getSelectedItem().toString();
            		String Type = type.getSelectedItem().toString();
            		String Year = year.getSelectedItem().toString();
            		String Tag = tag.getText();
            		String Awarded = awarded.getText();
            		authorTabInput.set_Selection(Selection);
            		if (Selection == "In Language") {
            			if (Language != "") {
            				authorTabInput.set_Constraint(Language);
            			} else {
            				authorTabInput.set_Constraint(null);
            			}
            		}
            		if (Selection == "Book Type") {
            			if (Type != "") {
            				authorTabInput.set_Constraint(Type);
            			} else {
            				authorTabInput.set_Constraint(null);
            			}
            		}
            		if (Selection == "Published In") {
            			if (Year != "") {
            				authorTabInput.set_Constraint(Year);
            			} else {
            				authorTabInput.set_Constraint(null);
            			}
            		}
            		if (Selection == "With Tag") {
            			if (!Tag.trim().equals("") && !Tag.trim().equals("Please Enter Exact Tag")) {
            				authorTabInput.set_Constraint(Tag);
            			} else {
            				authorTabInput.set_Constraint(null);
            			}
            		}
            		if (Selection == "Awarded In") {
            			if (!Awarded.trim().equals("") && !Awarded.trim().equals("Enter Award Category")) {
            				authorTabInput.set_Constraint(Awarded);
            			} else {
            				authorTabInput.set_Constraint(null);
            			}
            		}
            		
            		//System.out.println(authorTabInput.get_Selection());
            	}
            };
            

            inLanguage.addActionListener(selectionListener);
            withTag.addActionListener(selectionListener);
            bookType.addActionListener(selectionListener);
            publishedYear.addActionListener(selectionListener);
            mostAwarded.addActionListener(selectionListener);
            
            language.addActionListener(new ActionListener() {
            	public void actionPerformed(ActionEvent event) {
            		String Language = language.getSelectedItem().toString();
            		if ((authorTabInput.get_Selection() == "In Language") && (Language != "")) {
            			authorTabInput.set_Constraint(Language);
            			//System.out.println(authorTabInput.get_Constraint());
            		}
            	}
            });
            
            
            type.addActionListener(new ActionListener() {
            	public void actionPerformed(ActionEvent event) {
            		String Type = type.getSelectedItem().toString();
            		if ((authorTabInput.get_Selection() == "Book Type") && (Type != "")) {
            			authorTabInput.set_Constraint(Type);
            			//System.out.println(authorTabInput.get_Constraint());
            		}
            	}
            });
            
            year.addActionListener(new ActionListener() {
            	public void actionPerformed(ActionEvent event) {
            		String Year = year.getSelectedItem().toString();
            		if ((authorTabInput.get_Selection() == "Published In") && (Year != "")) {
            			authorTabInput.set_Constraint(Year);
            			//System.out.println(authorTabInput.get_Constraint());
            		}
            	}
            });
            
            
            authorTabInput.set_Which_Author("All Authors");
            authors.addActionListener(new ActionListener() {
            	public void actionPerformed(ActionEvent event) {
            		JComboBox comboBox = (JComboBox) event.getSource();
            		Object selected = comboBox.getSelectedItem();
            		String Authors = selected.toString();
            		authorTabInput.set_Which_Author(Authors);
            		
            		if (Authors.equals("All Authors")) {
            			inLanguage.setVisible(true);
                        language.setVisible(true);
                        language.setSelectedIndex(0);
                        
                        publishedYear.setVisible(true);
                        year.setVisible(true);
                        year.setSelectedIndex(0);
                        
                        withTag.setVisible(true);
                        tag.setVisible(true);
                        tag.setText("Please Enter Exact Tag");
                        tag.setForeground(Color.gray);
                        
                        bookType.setVisible(false);
                        type.setVisible(false);
                        type.setSelectedIndex(0);
                        
                        mostAwarded.setVisible(false);
                        awarded.setVisible(false);
                        awarded.setText("Enter Award Category");
                        awarded.setForeground(Color.gray);
                        
                        bg.clearSelection();
                        authorTabInput.set_Constraint(null);
                        authorTabInput.set_Selection(null);
                        
                        table.setModel(new DefaultTableModel());
                        
            		} else if (Authors.equals("Living Authors")) {
            			inLanguage.setVisible(false);
                        language.setVisible(false);
                        language.setSelectedIndex(0);
                        
                        publishedYear.setVisible(false);
                        year.setVisible(false);
                        year.setSelectedIndex(0);
                        
                        withTag.setVisible(false);
                        tag.setVisible(false);
                        tag.setText("Please Enter Exact Tag");
                        tag.setForeground(Color.gray);
                        
                        bookType.setVisible(true);
                        type.setVisible(true);	
                        type.setSelectedIndex(0);
                        
                        mostAwarded.setVisible(false);
                        awarded.setVisible(false);
                        awarded.setText("Enter Award Category");
                        awarded.setForeground(Color.gray);
                        
                        bg.clearSelection();
                        bookType.setSelected(true);
                        authorTabInput.set_Constraint(null);
                        authorTabInput.set_Selection("Book Type");
                        
                        table.setModel(new DefaultTableModel());
                        
            		} else if (Authors.equals("Most-Published Author")) {
            			inLanguage.setVisible(true);
                        language.setVisible(true);
                        language.setSelectedIndex(0);
                        
                        publishedYear.setVisible(true);
                        year.setVisible(true);
                        year.setSelectedIndex(0);
                        
                        withTag.setVisible(true);
                        tag.setVisible(true);
                        tag.setText("Please Enter Exact Tag");
                        tag.setForeground(Color.gray);
                        
                        bookType.setVisible(false);
                        type.setVisible(false);
                        type.setSelectedIndex(0);
                        
                        mostAwarded.setVisible(false);
                        awarded.setVisible(false);
                        awarded.setText("Enter Award Category");
                        awarded.setForeground(Color.gray);
                        
                        bg.clearSelection();
                        authorTabInput.set_Constraint(null);
                        authorTabInput.set_Selection(null);
                        
                        table.setModel(new DefaultTableModel());
                        
            		} else if (Authors.equals("Oldest Author")) {
            			inLanguage.setVisible(true);
                        language.setVisible(true);
                        language.setSelectedIndex(0);
                        
                        publishedYear.setVisible(true);
                        year.setVisible(true);
                        year.setSelectedIndex(0);
                        
                        withTag.setVisible(true);
                        tag.setVisible(true);
                        tag.setText("Please Enter Exact Tag");
                        tag.setForeground(Color.gray);
                        
                        bookType.setVisible(false);
                        type.setVisible(false);	
                        type.setSelectedIndex(0);
                        
                        mostAwarded.setVisible(false);
                        awarded.setVisible(false);
                        awarded.setText("Enter Award Category");
                        awarded.setForeground(Color.gray);
                        
                        bg.clearSelection();
                        authorTabInput.set_Constraint(null);
                        authorTabInput.set_Selection(null);
                        
                        table.setModel(new DefaultTableModel());
                        
            		} else if (Authors.equals("Youngest Author")) {
            			inLanguage.setVisible(true);
                        language.setVisible(true);
                        language.setSelectedIndex(0);
                        
                        publishedYear.setVisible(true);
                        year.setVisible(true);
                        year.setSelectedIndex(0);
                        
                        withTag.setVisible(true);
                        tag.setVisible(true);
                        tag.setText("Please Enter Exact Tag");
                        tag.setForeground(Color.gray);
                        
                        bookType.setVisible(false);
                        type.setVisible(false);	
                        type.setSelectedIndex(0);
                        
                        mostAwarded.setVisible(false);
                        awarded.setVisible(false);
                        awarded.setText("Enter Award Category");
                        awarded.setForeground(Color.gray);
                        
                        bg.clearSelection();
                        authorTabInput.set_Constraint(null);
                        authorTabInput.set_Selection(null);
                        
                        table.setModel(new DefaultTableModel());
                        
            		} else if (Authors.equals("Most-Written Author")) {
            			inLanguage.setVisible(true);
                        language.setVisible(true);
                        language.setSelectedIndex(0);
                        
                        publishedYear.setVisible(true);
                        year.setVisible(true);
                        year.setSelectedIndex(0);
                        
                        withTag.setVisible(true);
                        tag.setVisible(true);
                        tag.setText("Please Enter Exact Tag");
                        tag.setForeground(Color.gray);
                        
                        bookType.setVisible(false);
                        type.setVisible(false);	
                        type.setSelectedIndex(0);
                        
                        mostAwarded.setVisible(false);
                        awarded.setVisible(false);
                        awarded.setText("Enter Award Category");
                        awarded.setForeground(Color.gray);
                        
                        bg.clearSelection();
                        authorTabInput.set_Constraint(null);
                        authorTabInput.set_Selection(null);
                        
                        table.setModel(new DefaultTableModel());
                        
            		} else if (Authors.equals("Most-Awarded Author")) {
            			inLanguage.setVisible(false);
                        language.setVisible(false);
                        language.setSelectedIndex(0);
                        
                        publishedYear.setVisible(false);
                        year.setVisible(false);
                        year.setSelectedIndex(0);
                        
                        withTag.setVisible(false);
                        tag.setVisible(false);
                        tag.setText("Please Enter Exact Tag");
                        tag.setForeground(Color.gray);
                        
                        bookType.setVisible(false);
                        type.setVisible(false);	
                        type.setSelectedIndex(0);
                        
                        mostAwarded.setVisible(true);
                        awarded.setVisible(true);
                        awarded.setText("Enter Award Category");
                        awarded.setForeground(Color.gray);
                        
                        bg.clearSelection();
                        mostAwarded.setSelected(true);
                        authorTabInput.set_Constraint(null);
                        authorTabInput.set_Selection("Awarded In");
                        
                        table.setModel(new DefaultTableModel());
                        
            		} else if (Authors.equals("Most-Awarded After Death Author")) {
            			inLanguage.setVisible(true);
                        language.setVisible(true);
                        language.setSelectedIndex(0);
                        
                        publishedYear.setVisible(true);
                        year.setVisible(true);
                        year.setSelectedIndex(0);
                        
                        withTag.setVisible(true);
                        tag.setVisible(true);
                        tag.setText("Please Enter Exact Tag");
                        tag.setForeground(Color.gray);
                        
                        bookType.setVisible(false);
                        type.setVisible(false);	
                        type.setSelectedIndex(0);
                        
                        mostAwarded.setVisible(false);
                        awarded.setVisible(false);
                        awarded.setText("Enter Award Category");
                        awarded.setForeground(Color.gray);
                        
                        bg.clearSelection();
                        authorTabInput.set_Constraint(null);
                        authorTabInput.set_Selection(null);
                        
                        table.setModel(new DefaultTableModel());
                        
            		} else if (Authors.equals("Reviewed Most Titles Author")) {
            			inLanguage.setVisible(true);
                        language.setVisible(true);
                        language.setSelectedIndex(0);
                        
                        publishedYear.setVisible(true);
                        year.setVisible(true);
                        year.setSelectedIndex(0);
                        
                        withTag.setVisible(true);
                        tag.setVisible(true);
                        tag.setText("Please Enter Exact Tag");
                        tag.setForeground(Color.gray);
                        
                        bookType.setVisible(false);
                        type.setVisible(false);	
                        type.setSelectedIndex(0);
                        
                        mostAwarded.setVisible(false);
                        awarded.setVisible(false);
                        awarded.setText("Enter Award Category");
                        awarded.setForeground(Color.gray);
                        
                        bg.clearSelection();
                        authorTabInput.set_Constraint(null);
                        authorTabInput.set_Selection(null);
                        
                        table.setModel(new DefaultTableModel());
                        
            		} else if (Authors.equals("Largest Page/Dollar Ratio Authors")) {
            			inLanguage.setVisible(true);
                        language.setVisible(true);
                        language.setSelectedIndex(0);
                        
                        publishedYear.setVisible(true);
                        year.setVisible(true);
                        year.setSelectedIndex(0);
                        
                        withTag.setVisible(true);
                        tag.setVisible(true);
                        tag.setText("Please Enter Exact Tag");
                        tag.setForeground(Color.gray);
                        
                        bookType.setVisible(false);
                        type.setVisible(false);	
                        type.setSelectedIndex(0);
                        
                        mostAwarded.setVisible(false);
                        awarded.setVisible(false);
                        awarded.setText("Enter Award Category");
                        awarded.setForeground(Color.gray);
                        
                        bg.clearSelection();
                        authorTabInput.set_Constraint(null);
                        authorTabInput.set_Selection(null);
                        
                        table.setModel(new DefaultTableModel());
                        
            		} else if (Authors.equals("Most Translated Novel Authors")) {
            			inLanguage.setVisible(true);
                        language.setVisible(true);
                        language.setSelectedIndex(0);
                        
                        publishedYear.setVisible(false);
                        year.setVisible(false);
                        year.setSelectedIndex(0);
                        
                        withTag.setVisible(false);
                        tag.setVisible(false);
                        tag.setText("Please Enter Exact Tag");
                        tag.setForeground(Color.gray);
                        
                        bookType.setVisible(false);
                        type.setVisible(false);	
                        type.setSelectedIndex(0);
                        
                        mostAwarded.setVisible(false);
                        awarded.setVisible(false);
                        awarded.setText("Enter Award Category");
                        awarded.setForeground(Color.gray);
                        
                        bg.clearSelection();
                        authorTabInput.set_Constraint(null);
                        authorTabInput.set_Selection(null);
                        
                        table.setModel(new DefaultTableModel());
                        
            		}
            		
            	}
            });
             
            
            tag.addFocusListener(new FocusListener() {
            	public void focusGained(FocusEvent e) {
            		String Tag = tag.getText();
            		if (Tag.trim().equals("Please Enter Exact Tag")) {
            			tag.setText("");
            			tag.setForeground(Color.black);
            		}
            	}
            	public void focusLost(FocusEvent e) {
            		String Tag = tag.getText();
            		if (Tag.trim().equals("")) {
            			tag.setForeground(Color.gray);
            			tag.setText("Please Enter Exact Tag");
            			authorTabInput.set_Constraint(null);
            		} else {
            			if (authorTabInput.get_Selection() == "With Tag") {
            				authorTabInput.set_Constraint(Tag);
            			}
            		}
            	}
            });
            
            awarded.addFocusListener(new FocusListener() {
            	public void focusGained(FocusEvent e) {
            		String Awarded = awarded.getText();
            		if (Awarded.trim().equals("Enter Award Category")) {
            			awarded.setText("");
            			awarded.setForeground(Color.black);
            		}
            	}
            	public void focusLost(FocusEvent e) {
            		String Awarded = awarded.getText();
            		if (Awarded.trim().equals("")) {
            			awarded.setForeground(Color.gray);
            			awarded.setText("Enter Award Category");
            			authorTabInput.set_Constraint(null);
            		} else {
            			if (authorTabInput.get_Selection() == "Awarded In") {
            				authorTabInput.set_Constraint(Awarded);
            			}
            		}
            	}
            });
            
            
            String[] avail_Languages = {"", "English", "French", "Chinese"};
            for (int i = 0; i < avail_Languages.length; i++) {
            	language.addItem(avail_Languages[i]);
            }
            
            String[] avail_Type = {"", "ANTHOLOGY", "BACKCOVERART", "COLLECTION", "COVERART", "INTERIORART", "EDITOR", "ESSAY", "INTERVIEW", "NOVEL", "NONFICTION", "OMNIBUS", "POEM", "REVIEW", "SERIAL", "SHORTFICTION", "CHAPBOOK", "MAGAZINE", "FANZINE"};
            for (int i = 0; i < avail_Type.length; i++) {
            	type.addItem(avail_Type[i]);
            }
            
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
            
            String[] which_Author = {"All Authors", "Living Authors", "Most-Published Author", "Oldest Author", "Youngest Author", "Most-Written Author", "Most-Awarded Author", "Most-Awarded After Death Author", "Reviewed Most Titles Author", "Largest Page/Dollar Ratio Authors", "Most Translated Novel Authors"};
            for (int i = 0; i < which_Author.length; i++) {
            	authors.addItem(which_Author[i]);
            }
            
            
            
            
            
            JLabel see = new JLabel("Search For Authors");
            JLabel transparent = new JLabel("     ");
            
            
            JButton search = new JButton("Search");
            
            
            search.addActionListener(new ActionListener() {
            	public void actionPerformed(ActionEvent e) {
            		table.setModel(new DefaultTableModel());
            		System.out.println("");
            		System.out.println("***********New Author Tab Search********");
            		System.out.println(authorTabInput.get_Which_Author());
            		System.out.println(authorTabInput.get_Selection());
            		System.out.println(authorTabInput.get_Constraint());
            		
            		AuthorTabDAO authorTabDAO = new AuthorTabDAO();
            		
            		try {
            			if ((authorTabInput.get_Selection() != null) && (authorTabInput.get_Constraint() == null)) {
            				throw new Exception("Constraint Not Specified");
            			} else {
            				if (authorTabInput.get_Which_Author() == "All Authors") {
            					List<AuthorTab> authorTabList = authorTabDAO.searchAllAuthor(authorTabInput);
            					AuthorTabTableModel model = new AuthorTabTableModel(authorTabList);
            					table.setModel(model);
            					
            				} else if (authorTabInput.get_Which_Author() == "Living Authors") {
            					List<AuthorTab> authorTabList = authorTabDAO.searchLivingAuthor(authorTabInput);
            					AuthorTabBirthdateTableModel model = new AuthorTabBirthdateTableModel(authorTabList);
            					table.setModel(model);
            					
            				} else if (authorTabInput.get_Which_Author() == "Most-Published Author") {
            					List<AuthorTab> authorTabList = authorTabDAO.searchMostPublAuthor(authorTabInput);
            					AuthorTabNumberTableModel model = new AuthorTabNumberTableModel(authorTabList);
            					table.setModel(model);
            					
            				} else if (authorTabInput.get_Which_Author() == "Oldest Author") {
            					List<AuthorTab> authorTabList = authorTabDAO.searchOldestAuthor(authorTabInput);
            					AuthorTabBirthdateTableModel model = new AuthorTabBirthdateTableModel(authorTabList);
            					table.setModel(model);
            					
            				} else if (authorTabInput.get_Which_Author() == "Youngest Author") {
            					List<AuthorTab> authorTabList = authorTabDAO.searchYoungestAuthor(authorTabInput);
            					AuthorTabBirthdateTableModel model = new AuthorTabBirthdateTableModel(authorTabList);
            					table.setModel(model);
            					
            				} else if (authorTabInput.get_Which_Author() == "Most-Written Author") {
            					List<AuthorTab> authorTabList = authorTabDAO.searchMostWrittenAuthor(authorTabInput);
            					AuthorTabNumberTableModel model = new AuthorTabNumberTableModel(authorTabList);
            					table.setModel(model);
            					
            				}  else if (authorTabInput.get_Which_Author() == "Most-Awarded Author") {
            					List<AuthorTab> authorTabList = authorTabDAO.searchAwardedAuthor(authorTabInput);
            					AuthorTabNumberTableModel model = new AuthorTabNumberTableModel(authorTabList);
            					table.setModel(model);
            					
            					
            				}else if (authorTabInput.get_Which_Author() == "Most-Awarded After Death Author") {
            					List<AuthorTab> authorTabList = authorTabDAO.searchAwardedDeadAuthor(authorTabInput);
            					AuthorTabNumberTableModel model = new AuthorTabNumberTableModel(authorTabList);
            					table.setModel(model);
            					
            					
            				} else if (authorTabInput.get_Which_Author() == "Reviewed Most Titles Author") {
            					List<AuthorTab> authorTabList = authorTabDAO.searchMostReviewAuthor(authorTabInput);
            					AuthorTabNumberTableModel model = new AuthorTabNumberTableModel(authorTabList);
            					table.setModel(model);
            					
            				} else if (authorTabInput.get_Which_Author() == "Largest Page/Dollar Ratio Authors") {
            					List<AuthorTab> authorTabList = authorTabDAO.searchPageDollarAuthor(authorTabInput);
            					AuthorTabRatioTableModel model = new AuthorTabRatioTableModel(authorTabList);
            					table.setModel(model);
            				} else if (authorTabInput.get_Which_Author() == "Most Translated Novel Authors") {
            					List<AuthorTab> authorTabList = authorTabDAO.searchMostTranslatedNovelAuthor(authorTabInput);
            					AuthorTabNumberTableModel model = new AuthorTabNumberTableModel(authorTabList);
            					table.setModel(model);
            				}
            				
            				
            			}
            		}
            		catch (Exception ex) {
            			System.out.println(ex.getMessage());
            		}
            	}
            });
            
            
            grid.anchor = GridBagConstraints.WEST;
            grid.ipady = 5;
            grid.gridx = 0;
            grid.gridy = 0;
            fieldPanel.add(inLanguage, grid);
            grid.gridx = 1;
            grid.gridy = 0;
            fieldPanel.add(language, grid);
            
            inLanguage.setVisible(true);
            language.setVisible(true);
            
            grid.gridx = 0;
            grid.gridy = 1;
            fieldPanel.add(publishedYear, grid);
            grid.gridx = 1;
            grid.gridy = 1;
            fieldPanel.add(year, grid);
            
            publishedYear.setVisible(true);
            year.setVisible(true);
            
            grid.gridx = 0;
            grid.gridy = 2;
            fieldPanel.add(withTag, grid);
            grid.gridx = 1;
            grid.gridy = 2;
            fieldPanel.add(tag, grid);
            
            withTag.setVisible(true);
            tag.setVisible(true);
            
            grid.gridx = 0;
            grid.gridy = 0;
            fieldPanel.add(bookType, grid);
            grid.gridx = 1;
            grid.gridy = 0;
            fieldPanel.add(type, grid);
            
            bookType.setVisible(false);
            type.setVisible(false);
            
            
            grid.gridx = 0;
            grid.gridy = 0;
            fieldPanel.add(mostAwarded, grid);
            grid.gridx = 1;
            grid.gridy = 0;
            fieldPanel.add(awarded, grid);
            
            mostAwarded.setVisible(false);
            awarded.setVisible(false);
            
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
            
            
            
            
            add(fieldPanel, BorderLayout.NORTH);
            add(resultPanel, BorderLayout.SOUTH);
            
            
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
        	
            JButton search = new JButton("Search");
            search.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
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
            fieldPanel.add(search, grid);
        	
            // resultPanel
        	
            JScrollPane scrollPane1 = new JScrollPane();
            scrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane1.setPreferredSize(new Dimension(710, 535));
            scrollPane1.setViewportView(table);
            resultPanel.add(scrollPane1);
        	
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
                JOptionPane.showMessageDialog(TabContent.this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE); 
            }            			
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
	
        
        private void getBooksPublished() {
            
            publicStatValContainer publicStatValContainer = new publicStatValContainer();
        	
            JPanel fieldPanel = new JPanel();
            JPanel resultPanel = new JPanel();
        	
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
            	public void focusGained(FocusEvent e) {
                }
            	public void focusLost(FocusEvent e) {
                    publicStatValContainer.setQuery(comicSize.getText());
                    publicStatValContainer.setChoice(size.getSelectedItem());
            	}
            });
            
            JButton stats = new JButton("Statistics On Publications Per Year");
            JButton search = new JButton("Search");
            
            search.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    table.setModel(new DefaultTableModel());   
                    String query = publicStatValContainer.getQuery();
                    String choice = publicStatValContainer.getChoice();
                    displayPublicStat(query, choice);    	
                    System.out.println(query);   			
                }
            });
            
            stats.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) { 
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
            fieldPanel.add(search, grid);
            
            // resultPanel
        	
            JScrollPane scrollPane1 = new JScrollPane();
            scrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane1.setPreferredSize(new Dimension(710, 540));
            resultPanel.add(scrollPane1);
            scrollPane1.setViewportView(table);
            
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
                JOptionPane.showMessageDialog(TabContent.this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE); 
            }                      			
        }
    } 
}
