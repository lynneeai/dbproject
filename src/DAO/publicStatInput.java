package DAO;

public class publicStatInput {
    
    private String Year;
    private Boolean showAll;
    
    public publicStatInput(String Year, Boolean showAll) {
	this.Year = Year;
	this.showAll = showAll;
    }
    
    public void set_Year(String Year) {
	this.Year = Year;
    }
    
    public void set_showAll(Boolean showAll) {
	this.showAll = showAll;
    }
	
    public String get_Year() {
	return this.Year;
    }
	
    public Boolean get_showAll() {
	return this.showAll;
    }

}
