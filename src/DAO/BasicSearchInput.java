package DAO;

import java.util.*;
import java.sql.*;
import java.io.*;

/**
 *
 * @author mengdong
 */
public class BasicSearchInput {
        
        private String Author_Name;
	private String Publication_Name;
        
        public BasicSearchInput() {
            this.Author_Name = null;
            this.Publication_Name = null;
	}
	
	public void set_Author_Name(String name) {
		this.Author_Name = name;
	}
	
	public void set_Publication_Name(String name) {
		this.Publication_Name = name;
	}
	
	public String get_Author_Name() {
		return this.Author_Name;
	}
	
	public String get_Publication_Name() {
		return this.Publication_Name;
	}
}
