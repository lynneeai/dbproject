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
        	
            // fieldPanel
            
            fieldPanel.setLayout(new GridBagLayout());
            GridBagConstraints grid = new GridBagConstraints();
            
            Border title = BorderFactory.createTitledBorder("Authors");
            fieldPanel.setBorder(title);
            
            JTextField tag = new JTextField("Please Enter Exact Tag");
            JTextField awarded = new JTextField("Please Enter Award Category");
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
            
            
            Choice language = new Choice();
            Choice type = new Choice();
            Choice year = new Choice();
            Choice authors = new Choice();
            
           
            
            language.setPreferredSize(new Dimension(192, 20));
            type.setPreferredSize(new Dimension(192, 20));
            year.setPreferredSize(new Dimension(192, 20));
            authors.setPreferredSize(new Dimension(192, 20));
            
            ActionListener selectionListener = new ActionListener() {
            	public void actionPerformed(ActionEvent actionEvent) {
            		AbstractButton selected = (AbstractButton) actionEvent.getSource();
            		String Selection = selected.getText();
            		String Language = language.getSelectedItem();
            		String Type = type.getSelectedItem();
            		String Year = year.getSelectedItem();
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
            			if (!Awarded.trim().equals("") && !Awarded.trim().equals("Please Enter Award Category")) {
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
            
            language.addFocusListener(new FocusListener() {
            	public void focusGained(FocusEvent e) {}
            	public void focusLost(FocusEvent e) {
            		String Language = language.getSelectedItem();
            		if ((authorTabInput.get_Selection() == "In Language") && (Language != "")) {
            			authorTabInput.set_Constraint(Language);
            			//System.out.println(authorTabInput.get_Constraint());
            		}
            	}
            });
            
            type.addFocusListener(new FocusListener() {
            	public void focusGained(FocusEvent e) {}
            	public void focusLost(FocusEvent e) {
            		String Type = type.getSelectedItem();
            		if ((authorTabInput.get_Selection() == "Book Type") && (Type != "")) {
            			authorTabInput.set_Constraint(Type);
            			//System.out.println(authorTabInput.get_Constraint());
            		}
            	}
            });
            
            year.addFocusListener(new FocusListener() {
            	public void focusGained(FocusEvent e) {}
            	public void focusLost(FocusEvent e) {
            		String Year = year.getSelectedItem();
            		if ((authorTabInput.get_Selection() == "Published In") && (Year != "")) {
            			authorTabInput.set_Constraint(Year);
            			//System.out.println(authorTabInput.get_Constraint());
            		}
            	}
            });
            
            authorTabInput.set_Which_Author("All Authors");
            authors.addFocusListener(new FocusListener() {
            	public void focusGained(FocusEvent e) {}
            	public void focusLost(FocusEvent e) {
            		String Authors = authors.getSelectedItem();
            		authorTabInput.set_Which_Author(Authors);
            		//System.out.println(authorTabInput.get_Which_Author());
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
            
            String[] which_Author = {"All Authors", "Best Author", "Living Authors", "Most-Published Author", "Oldest Author", "Youngest Author", "Most-Written Author", "Most-Awarded Author", "Most-Awarded After Death Author", "Edited Most Review Author", "Largest Page/Dollar Ratio Author"};
            for (int i = 0; i < which_Author.length; i++) {
            	authors.add(which_Author[i]);
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
            					
            				} else if (authorTabInput.get_Which_Author() == "Best Author") {
            					List<AuthorTab> authorTabList = authorTabDAO.searchBestAuthor(authorTabInput);
            					AuthorTabTableModel model = new AuthorTabTableModel(authorTabList);
            					table.setModel(model);
            					
            				} else if (authorTabInput.get_Which_Author() == "Living Author") {
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
            					
            				} else if (authorTabInput.get_Which_Author() == "Edited Most Review Author") {
            					List<AuthorTab> authorTabList = authorTabDAO.searchMostReviewAuthor(authorTabInput);
            					AuthorTabNumberTableModel model = new AuthorTabNumberTableModel(authorTabList);
            					table.setModel(model);
            					
            				} else if (authorTabInput.get_Which_Author() == "Largest Page/Dollar Ratio Author") {
            					List<AuthorTab> authorTabList = authorTabDAO.searchPageDollarAuthor(authorTabInput);
            					AuthorTabRatioTableModel model = new AuthorTabRatioTableModel(authorTabList);
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
            
            grid.gridx = 0;
            grid.gridy = 4;
            fieldPanel.add(mostAwarded, grid);
            grid.gridx = 1;
            grid.gridy = 4;
            fieldPanel.add(awarded, grid);
            
            
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
            scrollPane1.setPreferredSize(new Dimension(710, 430));
            scrollPane1.setViewportView(table);
            resultPanel.add(scrollPane1);
            
            add(fieldPanel, BorderLayout.NORTH);
            add(resultPanel, BorderLayout.CENTER);
            
            
        }
        
        public class bestBookValContainer {
            private String query;
            private String text;
            private Boolean selected;
            
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
            
            public String getQuery() {
                return this.query;
            }
                
            public String getText() {
                return this.text;
            } 
            
            public Boolean getSelected() {
                return this.selected;
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
            JRadioButton translated = new JRadioButton("Most Translated Book Types");
            JRadioButton popular = new JRadioButton("Most Popular Books");
	
            reviewed.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (reviewed.isSelected() && bestBookValContainer.getSelected() == false) {
                        bestBookValContainer.setQuery(reviewed.getText());
                        bestBookValContainer.setSelected(true);
                    }   		
                    else if (!reviewed.isSelected() && bestBookValContainer.getSelected() == true) {
			bestBookValContainer.setSelected(false);
                    }	
                }
            });
	
            awarded.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (awarded.isSelected() && bestBookValContainer.getSelected() == false) {
                        bestBookValContainer.setQuery(awarded.getText());
                        bestBookValContainer.setSelected(true);
                    }   		
                    else if (!awarded.isSelected() && bestBookValContainer.getSelected() == true) {
			bestBookValContainer.setSelected(false);
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
	
            translated.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (translated.isSelected() && bestBookValContainer.getSelected() == false) {
			bestBookValContainer.setQuery(translated.getText());
                        bestBookValContainer.setSelected(true);
                    }   		
                    else if (!translated.isSelected() && bestBookValContainer.getSelected() == true) {
			bestBookValContainer.setSelected(false);
                    }	
                }
            });
	
            popular.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (popular.isSelected() && bestBookValContainer.getSelected() == false) {
			bestBookValContainer.setQuery(popular.getText());
                        bestBookValContainer.setSelected(true);
                    }   		
                    else if (!popular.isSelected() && bestBookValContainer.getSelected() == true) {
			bestBookValContainer.setSelected(false);
                    }	
                }
            });
	
            JLabel webPresence = new JLabel("Most extensive web presence awarded as ");
            JTextField webPresenceT = new JTextField("Please Enter an Award Type  ");
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
                        webPresenceT.setText("Please Enter an Award Type  ");
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
	
            JLabel seriesAward = new JLabel("Publication series with most book awarded as ");
            JTextField seriesAwardT = new JTextField("Please Enter an Award Type  ");
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
                        seriesAwardT.setText("Please Enter an Award Type  ");
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
            fieldPanel.add(awarded, grid);
	
            grid.gridx = 0;
            grid.gridy = 2;
            fieldPanel.add(webPresence, grid);
	
            grid.gridx = 0;
            grid.gridy = 3;
            fieldPanel.add(reviewedAuthor, grid);
	
            grid.gridx = 0;
            grid.gridy = 4;
            fieldPanel.add(seriesAward, grid);
	
            grid.gridx = 0;
            grid.gridy = 5;
            fieldPanel.add(popular, grid);
        	
            grid.gridx = 1;
            grid.gridy = 0;
            fieldPanel.add(transparent, grid);
        	
            grid.gridx = 2;
            grid.gridy = 0;
            fieldPanel.add(translated, grid);        	
    
            grid.gridx = 2;
            grid.gridy = 1;
            fieldPanel.add(awardedSeries, grid);
	
            grid.gridx = 2;
            grid.gridy = 2;
            fieldPanel.add(webPresenceT, grid);
	
            grid.gridx = 2;
            grid.gridy = 3;
            fieldPanel.add(reviewedAuthorT, grid);
	
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
            case "Most Translated Book Types": 
            case "Most Popular Books":
                bestBookInput.set_Query(query);
            default:
		bestBookInput.set_Query(query);
		bestBookInput.set_TextInput(text);
	}       
	
	bestBookDAO bestBookDAO = new bestBookDAO(bestBookInput);                                          
                
        System.out.println("");
        System.out.println("***********NEW SEARCH************");
        
        List<bestBook> statsList; bestBookTableModel1 model1;
        bestBookTableModel2 model2; bestBookTableModel3 model3;
    
        try {
            switch(query)  {
		case "Most Reviewed Books":
		case "Most Awarded Books": 
		case "Most Awarded Series": 
		case "Most Translated Book Types":
                    
                    statsList = bestBookDAO.mostBook();
	            model1 = new bestBookTableModel1(statsList);	               
                    table.setModel(model1);
                    break;
			
			
		case "Most extensive web presence awarded as ":
		case "Publication series with most book awarded as ": 
            
                    statsList = bestBookDAO.mostInputBook();
	            model1 = new bestBookTableModel1(statsList);	               
                    table.setModel(model1);
                    break;
          
		case "Most Popular Books":
            
	   	    statsList = bestBookDAO.mostBook();
	   	    model2 = new bestBookTableModel2(statsList);	               
	   	    table.setModel(model2);
                    break;

		case "  Most reviewed book of author ":
            
                    statsList = bestBookDAO.mostInputBook();
                    model3 = new bestBookTableModel3(statsList);	               
                    table.setModel(model3);
                    break;
            
            }                   					
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(TabContent.this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE); 
        }            			
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
            
            search.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    table.setModel(new DefaultTableModel());             
                    int yearSelected = year.getSelectedIndex();
                    String Year = year.getItem(yearSelected);
                    displayPublicStat(Year, false);    			
                }
            });
            
            stats.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    table.setModel(new DefaultTableModel());             
                    displayPublicStat(null, true);    			
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
            scrollPane1.setViewportView(table);
            
            add(fieldPanel, BorderLayout.NORTH);
        	add(resultPanel, BorderLayout.CENTER);
            
        }
        
        public void displayPublicStat(String Year, Boolean showAll) { 
        
            publicStatInput publicStatInput = new publicStatInput(Year, showAll);
            publicStatDAO publicStatDAO = new publicStatDAO(Year);
        
            publicStatInput.set_Year(null);
            publicStatInput.set_showAll(null);
                                                   
            if (!showAll) {
                publicStatInput.set_Year(Year);
            }
            publicStatInput.set_showAll(showAll);
                
            System.out.println("");
            System.out.println("***********NEW SEARCH************");
    
            try {
                if (Year != null) {
                    List<publicStat> statsList = publicStatDAO.searchStat(publicStatInput);
                    publicStatTableModel model = new publicStatTableModel(statsList);	               
                    table.setModel(model);
                }
                else {
                    List<publicStat> statsList = publicStatDAO.showAllStat(publicStatInput);
                    publicStatTableModel model = new publicStatTableModel(statsList);	               
                    table.setModel(model);
                }                     					
            } catch (Exception exc) {
                JOptionPane.showMessageDialog(TabContent.this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE); 
            }            			
        }
        
        private void getAllComics() {
        	
            JPanel fieldPanel = new JPanel();
            JPanel resultPanel = new JPanel();
        	
            // fieldPanel
            fieldPanel.setLayout(new GridBagLayout());
            GridBagConstraints grid = new GridBagConstraints();
            
            Border title = BorderFactory.createTitledBorder("Comics");
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
