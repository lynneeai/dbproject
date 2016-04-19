package Core;

public class Publication {
	private int PUBL_ID;
	private String PUBL_TITLE;
	private String PUBL_DATE;
	private int PUBLISHER_ID;
	private String PUBL_PAGES;
	private String PACKAGING_TYPE;
	private String PUBL_TYPE;
	private String ISBN;
	private String COVER_IMAGE;
	private String PRICE;
	private int PUBL_SERIES_ID;
	private String PUBL_SERIES_NUM;
	private String NOTE;
	
	public Publication(int publication_id) {
		this.PUBL_ID = publication_id;
	}
}