package Core;

public class Book {
	
	private String BOOK_NAME;
	private String AUTHOR;
	private String PUBLISHED_DATE;
	private String LANGUAGE;
	private String ISBN;
	private String PRICE;
	
	public Book() {}
	
	public Book(String bookName, String publishedDate, String isbn, String price) {
		this.BOOK_NAME = bookName;
		this.PUBLISHED_DATE = publishedDate;
		this.ISBN = isbn;
		this.PRICE = price;
	}
	
	public void set_Author(String name) {
		this.AUTHOR = name;
	}
	public void set_Language(String language) {
		this.LANGUAGE = language;
	}
	
	public String get_BOOK_NAME() {
		return this.BOOK_NAME;
	}
	
	public String get_AUTHOR() {
		return this.AUTHOR;
	}
	
	public String get_PUBLISHED_DATE() {
		return this.PUBLISHED_DATE;
	}
	
	public String get_LANGUAGE() {
		return this.LANGUAGE;
	}
	
	public String get_ISBN() {
		return this.ISBN;
	}
	
	public String get_PRICE() {
		return this.PRICE;
	}
	
}