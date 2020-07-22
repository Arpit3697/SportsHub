import java.io.Serializable;
import java.util.ArrayList;

public class Product implements Serializable{
	private int eventid;
	private String eventname;
	private String image;
	private String sportscategory;
	private String eventdate;
	private String eventplace;
	private String eventcity;
	private String eventstate;
	private String eventzipcode;
	private double discount;
	// private ArrayList<Integer> accessories=null;

	 public Product() {
	// 	this.accessories = new ArrayList<Integer>();
	 }

	public Product(int eventid, String eventname, String image, String sportscategory,String eventdate,String eventplace,
			String eventcity, String eventstate, String eventzipcode, double discount) {
		// , ArrayList<Integer> accessories
		this.eventid = eventid;
		this.eventname = eventname;
		this.image = image;
		this.sportscategory = sportscategory;
		this.eventdate = eventdate;
		this.eventplace = eventplace;
		this.eventcity = eventcity;
		this.eventstate = eventstate;
		this.eventzipcode = eventzipcode;
		this.discount = discount;
		// this.accessories = accessories;
	}

	public String geteventname() {
		return eventname;
	}

	public void seteventname(String eventname) {
		this.eventname = eventname;
	}

	public int geteventid() {
		return eventid;
	}

	public void seteventid(int eventid) {
		this.eventid = eventid;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
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

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	// public ArrayList<Integer> getAccessories() {
	// 	return accessories;
	// }

	// public void setAccessories(ArrayList<Integer> accessories) {
	// 	this.accessories = accessories;
	// }

	public void updateProduct(Product updateProduct) {
		this.eventname = updateProduct.eventname;
		this.image = updateProduct.image;
		this.sportscategory = updateProduct.sportscategory;
		this.eventdate = updateProduct.eventdate;
		this.eventplace = updateProduct.eventplace;
		this.eventcity = updateProduct.eventcity;
		this.eventstate = updateProduct.eventstate;
		this.eventzipcode = updateProduct.eventzipcode;	
		this.discount = updateProduct.discount;
	}

}
