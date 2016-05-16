package DAO;

import java.util.*;
import java.sql.*;
import java.io.*;

public class PublisherInput {
    private String Selection;
    private String Year;
    
    public PublisherInput() {
    	this.Selection = null;
    	this.Year = null;
    }
    
    public void set_Selection(String selection) {
    	this.Selection = selection;
    }
    
    public void set_Year(String year) {
    	this.Year = year;
    }
    
    public String get_Selection() {
    	return Selection;
    }
    
    public String get_Year() {
    	return Year;
    }
 
}
