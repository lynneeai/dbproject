package UI;

import Core.AuthorTab;
import DAO.AuthorTabDAO;
import DAO.AuthorTabInput;
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
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

public class authorTabView extends JPanel{
    private JTable table  = new JTable();
    
    public authorTabView() {
        this.getAllAuthors();
    }
    
    private void getAllAuthors() {
            JPanel fieldPanel = new JPanel();
            JPanel resultPanel = new JPanel();
            
            AuthorTabInput authorTabInput = new AuthorTabInput();
            
            // resultPanel
            
            JScrollPane scrollPane1 = new JScrollPane();
            scrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane1.setPreferredSize(new Dimension(710, 460));
            
            ImageIcon backgroundIcon = new ImageIcon("src/background.jpg");
            Image backgroundImg = backgroundIcon.getImage();
            Image newBackgroundImg = backgroundImg.getScaledInstance(700, 455,  java.awt.Image.SCALE_SMOOTH );
            backgroundIcon = new ImageIcon(newBackgroundImg);
            JLabel backgroundPic = new JLabel(backgroundIcon);
            scrollPane1.getViewport().add(backgroundPic, scrollPane1);
            
            //scrollPane1.setViewportView(table);
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
            					if (authorTabInput.get_Selection() == null) {
            						AuthorTabLanguageTableModel model = new AuthorTabLanguageTableModel(authorTabList);
            						table.setModel(model);
            					} else {
            						AuthorTabNumberTableModel model = new AuthorTabNumberTableModel(authorTabList);
            						table.setModel(model);
            					}
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
            grid.gridy = 2;
            grid.ipady = 0;
            grid.gridheight = 2;
            fieldPanel.add(search, grid);
            
            
            
            
            add(fieldPanel, BorderLayout.NORTH);
            add(resultPanel, BorderLayout.SOUTH);
            
            
        }
}
