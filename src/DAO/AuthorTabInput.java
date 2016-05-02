package DAO;

import java.util.*;
import java.sql.*;
import java.io.*;

public class AuthorTabInput {
	private String Which_Author;
	private String Selection;
	private String Constraint;
	
	
	public AuthorTabInput() {
		Which_Author = null;
		Selection = null;
		Constraint = null;
	}
	
	public void set_Which_Author(String author) {
		this.Which_Author = author;
	}
	
	public void set_Selection(String selection) {
		this.Selection = selection;
	}
	
	public void set_Constraint(String constraint) {
		this.Constraint = constraint;
	}
	
	public String get_Which_Author() {
		return Which_Author;
	}
	
	public String get_Selection() {
		return Selection;
	}
	
	public String get_Constraint() {
		return Constraint;
	}
	
	
}