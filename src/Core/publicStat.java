package Core;

public class publicStat {
    private String Year;
    private String Total;
    private String Name;
    private String Currency;
    private String AvgPublPrice;
    
    public publicStat() {
	this.Year = null;
	this.Total = null;
        this.Name = null;
        this.Currency = null;
        this.AvgPublPrice = null;
    }
    
    public void set_Year(String Year) {
	this.Year = Year;
    }
    
    public void set_Total(String Total) {
	this.Total = Total;
    }
    
    public void set_Name(String Name) {
	this.Name = Name;
    }
    
    public void set_Currency(String Currency) {
	this.Total = Total;
    }
    
    public void set_AvgPublPrice(String AvgPublPrice) {
	this.AvgPublPrice = AvgPublPrice;
    }
	
    public String get_Year() {
	return this.Year;
    }
	
    public String get_Total() {
	return this.Total;
    }
    
    public String get_Name() {
	return this.Name;
    }
    
    public String get_Currency() {
	return this.Currency;
    }
    
    public String get_AvgPublPrice() {
	return this.AvgPublPrice;
    }

}
