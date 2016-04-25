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
	private String Publisher_Name;
	private String Language;
	private String Award;
        
        public BasicSearchInput() {
            this.Author_Name = null;
            this.Publication_Name = null;
            this.Publisher_Name = null;
            this.Language = null;
            this.Award = null;
	}
	
	public void set_Author_Name(String name) {
		this.Author_Name = name;
	}
	
	public void set_Publication_Name(String name) {
		this.Publication_Name = name;
	}
	
	public void set_Publisher_Name(String name) {
		this.Publisher_Name = name;
	}
	
	public void set_Language(String language) {
		this.Language = language;
	}
	
	public void set_Award(String award) {
		this.Award = award;
	}
	
	public String get_Author_Name() {
		return this.Author_Name;
	}
	
	public String get_Publication_Name() {
		return this.Publication_Name;
	}
	
	public String get_Publisher_Name() {
		return this.Publisher_Name;
	}
	
	public String get_Language() {
		return this.Language;
	}
	
	public String get_Award() {
		return this.Award;
	}
    
}
