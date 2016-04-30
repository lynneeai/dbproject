package Core;

public class publicStat {
    private String Year;
    private String Count;
    
    public publicStat(String Year, String Count) {
	this.Year = Year;
	this.Count = Count;
    }
    
    public void set_Year(String Year) {
	this.Year = Year;
    }
    
    public void set_Count(String Count) {
	this.Count = Count;
    }
	
    public String get_Year() {
	return this.Year;
    }
	
    public String get_Count() {
	return this.Count;
    }

}
