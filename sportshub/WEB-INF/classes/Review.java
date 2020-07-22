
public class Review {
	int eventid;
	String eventname;
	String sportscategory;
	String eventdate;
	String eventplace;
	String eventcity;
	String eventstate;
	String eventzipcode;
	String manufacturerRebate;
	boolean productOnSale;
	String retailername;
	String retailerzip;
	String retailerCity;
	String retailerState;
	String username;
	String age;
	String gender;
	String occupation;
	int rating;
	String review;
	long time;

	public Review(int eventid, String eventname, String sportscategory, String eventdate, String eventplace, String eventcity, String eventstate, 
		String eventzipcode, String manufacturerRebate, boolean productOnSale,
			String retailername, String retailerzip, String retailerCity, String retailerState, String username,
			String age, String gender, String occupation, int rating, String review, long time) {
		super();
		this.eventid = eventid;
		this.eventname = eventname;
		this.sportscategory = sportscategory;
		this.eventdate = eventdate;
		this.eventplace = eventplace;
		this.eventcity = eventcity;
		this.eventstate = eventstate;
		this.eventzipcode = eventzipcode;
		this.manufacturerRebate = manufacturerRebate;
		this.productOnSale = productOnSale;
		this.retailername = retailername;
		this.retailerzip = retailerzip;
		this.retailerCity = retailerCity;
		this.retailerState = retailerState;
		this.username = username;
		this.age = age;
		this.gender = gender;
		this.occupation = occupation;
		this.rating = rating;
		this.review = review;
		this.time = time;
	}

	@Override
	public String toString() {
		return "Review [eventid=" + eventid + ", productModelName=" + eventname 
				+ ", sportscategory=" + sportscategory
				+ ", eventdate=" + eventdate
				+ ", eventplace=" + eventplace
				+ ", eventcity=" + eventcity
				+ ", eventstate=" + eventstate
				+ ", eventzipcode=" + eventzipcode
				+ ", manufacturerRebate=" + manufacturerRebate + ", productOnSale=" + productOnSale + ", retailername="
				+ retailername + ", retailerzip=" + retailerzip + ", retailerCity=" + retailerCity + ", retailerState="
				+ retailerState + ", username=" + username + ", age=" + age + ", gender=" + gender + ", occupation="
				+ occupation + ", rating=" + rating + ", review=" + review + ", time=" + time + "]";
	}

	public int geteventid() {
		return eventid;
	}

	public void seteventid(int eventid) {
		this.eventid = eventid;
	}

	public String geteventname() {
		return eventname;
	}

	public void seteventname(String eventname) {
		this.eventname = eventname;
	}

	public String getsportscategory() {
		return sportscategory;
	}

	public void setsportscategory(String sportscategory) {
		this.sportscategory = sportscategory;
	}

	public String geteventdate() {
		return eventdate;
	}

	public void seteventdate(String eventdate) {
		this.eventdate = eventdate;
	}

	public String geteventplace() {
		return eventplace;
	}

	public void seteventplace(String eventplace) {
		this.eventplace = eventplace;
	}

	public String geteventcity() {
		return eventcity;
	}

	public void seteventcity(String eventcity) {
		this.eventcity = eventcity;
	}

	public String geteventstate() {
		return eventstate;
	}

	public void seteventstate(String eventstate) {
		this.eventstate = eventstate;
	}

	public String geteventzipcode() {
		return eventzipcode;
	}

	public void seteventzipcode(String eventzipcode) {
		this.eventzipcode = eventzipcode;
	}	

	public String getManufacturerRebate() {
		return manufacturerRebate;
	}

	public void setManufacturerRebate(String manufacturerRebate) {
		this.manufacturerRebate = manufacturerRebate;
	}

	public boolean isProductOnSale() {
		return productOnSale;
	}

	public void setProductOnSale(boolean productOnSale) {
		this.productOnSale = productOnSale;
	}

	public String getRetailername() {
		return retailername;
	}

	public void setRetailername(String retailername) {
		this.retailername = retailername;
	}

	public String getRetailerzip() {
		return retailerzip;
	}

	public void setRetailerzip(String retailerzip) {
		this.retailerzip = retailerzip;
	}

	public String getRetailerCity() {
		return retailerCity;
	}

	public void setRetailerCity(String retailerCity) {
		this.retailerCity = retailerCity;
	}

	public String getRetailerState() {
		return retailerState;
	}

	public void setRetailerState(String retailerState) {
		this.retailerState = retailerState;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

}
