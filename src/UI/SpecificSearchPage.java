package UI;

import java.awt.BorderLayout;
import java.awt.Image;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
            if (i == 0) {              
                publisherTabView publisherTab = new publisherTabView();
                pane.add(tabContents[i],publisherTab);
            }
            else if (i == 1) {
                authorTabView authorTab = new authorTabView();
                pane.add(tabContents[i],authorTab);
            }
            else if (i == 2) {
                bestBookTabView bestBookTab = new bestBookTabView();
                pane.add(tabContents[i],bestBookTab);
            }
            else {
                publicStatTabView publicStatTab = new publicStatTabView();
                pane.add(tabContents[i],publicStatTab);
            }       
        }
        
        JPanel backPanel = new JPanel();
        ImageIcon backIcon = new ImageIcon("src/back-button.png");
        Image backImg = backIcon.getImage();
        Image newBackImg = backImg.getScaledInstance(70, 30,  java.awt.Image.SCALE_SMOOTH );
        backIcon = new ImageIcon(newBackImg);
        JButton back = new JButton(backIcon);
        back.setOpaque(false);
        back.setContentAreaFilled(false);
        back.setBorderPainted(false);
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
}
