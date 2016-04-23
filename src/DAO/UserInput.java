package DAO;

import java.util.*;
import java.sql.*;
import java.io.*;

public class UserInput {
	
	private String Author_Name;
	private String Title_Name;
	private String Publication_Name;
	private String Publisher_Name;
	private String Title_Series_Name;
	private String Publication_Series_Name;
	private String Language;
	private String Title_Type;
	private String Publication_Type;
	private String Start_Date;
	private String End_Date;
	private boolean Awarded;
	
	public UserInput() {
		this.Author_Name = null;
		this.Title_Name = null;
		this.Publication_Name = null;
		this.Publisher_Name = null;
		this.Title_Series_Name = null;
		this.Publication_Series_Name = null;
		this.Language = null;
		this.Title_Type = null;
		this.Publication_Type = null;
		this.Start_Date = null;
		this.End_Date = null;
		this.Awarded = false;
	}
	
	public void set_Author_Name(String name) {
		this.Author_Name = name;
	}
	
	public void set_Title_Name(String name) {
		this.Title_Name = name;
	}
	
	public void set_Publication_Name(String name) {
		this.Publication_Name = name;
	}
	
	public void set_Publisher_Name(String name) {
		this.Publisher_Name = name;
	}
	
	public void set_Title_Series_Name(String name) {
		this.Title_Series_Name = name;
	}
	
	public void set_Publication_Series_Name(String name) {
		this.Publication_Series_Name = name;
	}
	
	public void set_Language(String language) {
		this.Language = language;
	}
	
	public void set_Title_Type(String type) {
		this.Title_Type = type;
	}
	
	public void set_Publication_Type(String type) {
		this.Publication_Type = type;
	}
	
	public void set_Start_Date(String date) {
		this.Start_Date = date;
	}
	
	public void set_End_Date(String date) {
		this.End_Date = date;
	}
	
	public void set_Awarded(boolean selection) {
		this.Awarded = selection;
	}
	
	public String get_Author_Name() {
		return this.Author_Name;
	}
	
	public String get_Title_Name() {
		return this.Title_Name;
	}
	
	public String get_Publication_Name() {
		return this.Publication_Name;
	}
	
	public String get_Publisher_Name() {
		return this.Publisher_Name;
	}
	
	public String get_Title_Series_Name() {
		return this.Title_Series_Name;
	}
	
	public String get_Publication_Series_Name() {
		return this.Publication_Series_Name;
	}
	
	public String get_Language() {
		return this.Language;
	}
	
	public String get_Title_Type() {
		return this.Title_Type;
	}
	
	public String get_Publication_Type() {
		return this.Publication_Type;
	}
	
	public String get_Start_Date() {
		return this.Start_Date;
	}
	
	public String get_End_Date() {
		return this.End_Date;
	}
	
	public boolean get_Awarded() {
		return this.Awarded;
	}
	
}