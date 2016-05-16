package Core;

public class Publisher {
	private String Publisher_Name;
	private String Year;
	private String Avg_Price;
	private String Avg_Author_Num;
	private String Publ_Num;
	
	public Publisher() {
		Publisher_Name = null;
	}
	
	public Publisher(String data1, String data2, int choice) {
		if (choice == 0) {
			Publisher_Name = data1;
			Avg_Price = data2;
		} else if (choice == 1) {
			Publisher_Name = data1;
			Publ_Num = data2;
		} else if (choice == 2) {
			Year = data1;
			Avg_Author_Num = data2;
		}
	}
	
	public void set_Year(String year) {
		this.Year = year;
	}
	
	public void set_Avg_Price(String price) {
		this.Avg_Price = price;
	}
	
	public void set_Avg_Author_Num(String num) {
		this.Avg_Author_Num = num;
	}
	
	public void set_Publ_Num(String num) {
		this.Publ_Num = num;
	}
	
	public String get_Publisher_Name() {
		return Publisher_Name;
	}
	
	public String get_Year() {
		return Year;
	}
	
	public String get_Avg_Price() {
		return Avg_Price;
	}
	
	public String get_Avg_Author_Num() {
		return Avg_Author_Num;
	}
	
	public String get_Publ_Num() {
		return Publ_Num;
	}
	
}