package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;


public class SpecificSearchPage {
    
    private JPanel specificSearchPanel;
    private final JTabbedPane pane = new JTabbedPane();
	
    public void specificSearch() {
        specificSearchPanel = new JPanel();
        specificSearchPanel.setBorder(new EmptyBorder(10, 10, 18, 10));
        specificSearchPanel.setLayout(new BorderLayout(0, 0));
        
        
        Main.mainFrame.add(specificSearchPanel);
        Main.mainFrame.revalidate();
        Main.mainFrame.repaint();
        
        
        String[] tabContents = {"All Publishers", "All authors", "Most Popular Books", "Books Published", "All Comics"};
        
        for (int i = 0; i < 5; i++) {
            pane.add(tabContents[i], new TabContent(i));        
        }
        
        JPanel backPanel = new JPanel();
        JButton back = new JButton("back");
        back.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		backPanel.removeAll();
        		pane.removeAll();
        		specificSearchPanel.removeAll();
        		Main.mainFrame.remove(specificSearchPanel);
        		DetailSearchPage detailSearchPage = new DetailSearchPage();
        		detailSearchPage.detailSearch();
        		
        	}
        });
        backPanel.add(back);
        specificSearchPanel.add(pane, BorderLayout.CENTER);
        specificSearchPanel.add(backPanel, BorderLayout.SOUTH);

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
            
            Border title = BorderFactory.createTitledBorder("Advanced Book Search");
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
            scrollPane.setPreferredSize(new Dimension(710, 570));
            add(scrollPane);
            
        }
        
        private void getAllAuthors() {
            
        }
        
        private void getMostPopulars() {
            
        }
        
        private void getBooksPublished() {
            
        }
        
        private void getAllComics() {
            
        }
    }
}
