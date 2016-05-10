package DAO;

public class publicStatInput {
    
    private String query;
    private String choice;
	
	
    public publicStatInput() {
	query = null;
	choice = null;
    }
	
    public void set_Query(String query) {
	this.query = query;
    }
	
    public void set_Choice(String textInput) {
	this.choice = textInput;
    }
	
    public String get_Query() {
	return query;
    }
	
    public String get_Choice() {
	return choice;
    }

}
