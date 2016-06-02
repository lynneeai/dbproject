package UI;

import java.awt.EventQueue;
import javax.swing.JFrame;
import java.lang.Object;
import java.awt.Toolkit;
import java.awt.Dimension;



public class Main {
	
	public static JFrame mainFrame = new JFrame();
	
	public Main() {
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	   mainFrame.setLocation(dim.width/2 - mainFrame.getSize().width/2, dim.height/2 - mainFrame.getSize().height/2 - 100);
	    
        mainFrame.setVisible(true);
        mainFrame.setBounds(100, 100, 800, 180);
	    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    SearchApp searchApp = new SearchApp();
	    searchApp.initialPage();
	    
	    //BasicSearchPage basicSearchPage = new BasicSearchPage();
	    //basicSearchPage.basicSearch();
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Main();
				} catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    });
	}
	
	
}