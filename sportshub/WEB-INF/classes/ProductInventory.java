import java.io.Serializable;
import java.util.ArrayList;

public class ProductInventory implements Serializable{
	private int eventid;
	private String eventname;
	private String listingid;
	private int quantity;
	private String sectionname;
	private double price;


	public ProductInventory(){
		
	}
	public ProductInventory(int eventid, String listingid,int quantity,String sectionname, double price) {
		this.eventid = eventid;
		this.listingid = listingid;
		this.quantity = quantity;
		this.sectionname = sectionname;
		this.price = price;
	}

	public ProductInventory(int eventid, String eventname ,String listingid,int quantity,String sectionname, double price) {
		this.eventid = eventid;
		this.eventname=eventname;
		this.listingid = listingid;
		this.quantity = quantity;
		this.sectionname = sectionname;
		this.price = price;
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
	
	public String getlistingid() {
		return listingid;
	}

	public void setlistingid(String listingid) {
		this.listingid = listingid;
	}

	public int getquantity() {
		return quantity;
	}

	public void setquantity(int quantity) {
		this.quantity = quantity;
	}

	public String getsectionname() {
		return sectionname;
	}

	public void setsectionname(String sectionname) {
		this.sectionname = sectionname;
	}	

	public double getprice() {
		return price;
	}

	public void setprice(double price) {
		this.price = price;
	}


	public void updateProductInventory(ProductInventory updateProductInventory) {
		this.quantity = updateProductInventory.quantity;
		this.price = updateProductInventory.price;
	}

}
