package UI;

import java.awt.EventQueue;
import javax.swing.JFrame;


public class Main {
	
	public static JFrame mainFrame = new JFrame();
	
	public Main() {
        mainFrame.setVisible(true);
        mainFrame.setBounds(100, 100, 800, 180);
	    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    SearchApp searchApp = new SearchApp();
	    searchApp.initialPage();
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