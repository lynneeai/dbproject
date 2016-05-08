package DAO;

public class bestBookInput {
    
    private String query;
    private String textInput;
	
	
    public bestBookInput() {
	query = null;
	textInput = null;
    }
	
    public void set_Query(String query) {
	this.query = query;
    }
	
    public void set_TextInput(String textInput) {
	this.textInput = textInput;
    }
	
    public String get_Query() {
	return query;
    }
	
    public String get_TextInput() {
	return textInput;
    }
    
}
