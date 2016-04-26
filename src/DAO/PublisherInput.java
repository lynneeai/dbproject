package DAO;

import java.util.*;
import java.sql.*;
import java.io.*;

public class PublisherInput {
    private String Publisher_Name;
    private Boolean averageAll;
    private Boolean minimumAll;
    private Boolean averageSpecific;
    private Boolean minimumSpecific;
    
    public PublisherInput() {
	this.Publisher_Name = null;
	this.averageAll = false;
	this.minimumAll = false;
        this.averageSpecific = false;
	this.minimumSpecific = false;
    }
    
    public void set_Publisher_Name(String name) {
		this.Publisher_Name = name;
    }
    
    public void set_averageAll(Boolean selected) {
		this.averageAll = selected;
    }
    
    public void set_minimumAll(Boolean selected) {
		this.minimumAll = selected;
    }
    
    public void set_averageSpecific(Boolean selected) {
		this.averageSpecific = selected;
    }
    
    public void set_minimumSpecific(Boolean selected) {
		this.minimumSpecific = selected;
    }
    
    public String get_Publisher_Name() {
	return this.Publisher_Name;
    }
    
    public Boolean set_averageAll() {
        return this.averageAll;
    }
    
    public Boolean get_minimumAll() {
        return this.minimumAll;
    }
    
    public Boolean get_averageSpecific() {
	return this.averageSpecific;
    }
    
    public Boolean get_minimumSpecific() {
	return this.minimumSpecific;
    }

}
