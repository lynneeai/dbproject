package Core;

public class bestBook {
    private String Name;
    private String Total;
    private String AuthorName;
    private String TitleName;
    private String NumReviews;
    
    
    public bestBook() {
	this.Name = null;
	this.Total = null;
        this.AuthorName = null;
        this.TitleName = null;
        this.NumReviews = null;        
    }
    
    public void set_Name(String Name) {
	this.Name = Name;
    }
    
    public void set_Number(String Total) {
	this.Total = Total;
    }
    
    public void set_AuthorName(String AuthorName) {
	this.AuthorName = AuthorName;
    }
    
    public void set_TitleName(String TitleName) {
	this.TitleName = TitleName;
    }
    
    public void set_NumReviews(String NumReviews) {
	this.NumReviews = NumReviews;
    }
	
    public String get_Name() {
	return this.Name;
    }
    
    public String get_Total() {
	return this.Total;
    }
    
    public String get_AuthorName() {
	return this.AuthorName;
    }
    
    public String get_TitleName() {
	return this.TitleName;
    }
    
    public String get_NumReviews() {
	return this.NumReviews;
    }
}
