package Core;

public class AuthorTab {
	private String Author_Name;
	
	public AuthorTab() {
		Author_Name = null;
	}
	
	public AuthorTab(String name) {
		this.Author_Name = name;
	}
	
	public void set_Author_Name(String name) {
		this.Author_Name = name;
	}
	
	public String get_Author_Name() {
		return Author_Name;
	}
}