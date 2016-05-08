package Core;

public class AuthorTab {
	private String Author_Name;
	private String Birthdate;
	private String Total_Number;
	private String Page_Dollar;
	
	public AuthorTab() {
		Author_Name = null;
	}
	
	public AuthorTab(String name) {
		this.Author_Name = name;
	}
	
	public AuthorTab(String name, String data, String choice) {
		Author_Name = name;
		if (choice == "birthdate") {
			Birthdate = data;
		} else if (choice == "number") {
			Total_Number = data;
		} else if (choice == "page_dollar") {
			Page_Dollar = data;
		}
	}
	
	
	public void set_Author_Name(String name) {
		this.Author_Name = name;
	}
	
	public void set_Birthdate(String date) {
		this.Birthdate = date;
	}
	
	public void set_Total_Number(String number) {
		this.Total_Number = number;
	}
	
	public void set_Page_Dollar(String ratio) {
		this.Page_Dollar = ratio;
	}
	
	public String get_Author_Name() {
		return Author_Name;
	}
	
	public String get_Birthdate() {
		return Birthdate;
	}
	
	public String get_Total_Number() {
		return Total_Number;
	}
	
	public String get_Page_Dollar() {
		return Page_Dollar;
	}
}