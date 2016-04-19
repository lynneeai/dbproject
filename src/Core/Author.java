package Core;

public class Author {
    private int AUTHOR_ID;
    private String AUTHOR_NAME;
    private String LEGAL_NAME;
    private String LAST_NAME;
    private String PSEUDO;
    private String BIRTHPLACE;
    private String BIRTHDATE;
    private String DEATHDATE;
    private String EMAIL;
    private String IMAGE;
    private int LANGUAGE_ID;
    private String NOTE;
	
    public Author(int AUTHOR_ID, String AUTHOR_NAME, String BIRTHPLACE) {
    	this.AUTHOR_ID = AUTHOR_ID;
    	this.AUTHOR_NAME = AUTHOR_NAME;	
        this.BIRTHPLACE = BIRTHPLACE;

    }

    public int getId() {
    	return AUTHOR_ID;
    }

    public String getAuthorName() {
    	return AUTHOR_NAME;
    }

    public String getBirthplace() {
    	return BIRTHPLACE;
    }
	
}
