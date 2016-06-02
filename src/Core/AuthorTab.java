package Core;

public class AuthorTab {
	private String Author_Name;
	private String Birthdate;
	private String Total_Number;
	private String Page_Dollar;
	private String Language;
	private String Award_Cat;
	
	public AuthorTab() {
		Author_Name = null;
	}
	
	public AuthorTab(String name) {
		this.Author_Name = name;
	}
	
	public AuthorTab(String one, String two, String choice) {
		if (choice == "birthdate") {
			Author_Name = one;
			Birthdate = two;
		} else if (choice == "number") {
			Author_Name = one;
			Total_Number = two;
		} else if (choice == "page_dollar") {
			Author_Name = one;
			Page_Dollar = two;
		} else if (choice == "award") {
			Award_Cat = one;
			Author_Name = two;
		}
	}
	
	public AuthorTab(String language, String name) {
		Author_Name = name;
		Language = language;
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
	
	public void set_Language(String language) {
		this.Language = language;
	}
	
	public void set_Award_Cat(String award) {
		this.Award_Cat = award;
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
	
	public String get_Language() {
		return Language;
	}
	
	public String get_Award_Cat() {
		return Award_Cat;
	}
}