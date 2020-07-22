
public class Inventory {
	int eventid;
	String eventname;
	int quantity;
	double discount;

	public Inventory(int eventid, String eventname, int quantity, double discount) {
		this.eventid = eventid;
		this.eventname = eventname;
		this.quantity = quantity;
		this.discount = discount;
	}

	// public Inventory(int id, String model, double price, int quantity, String type, double rebate) {
	// 	this.id = id;
	// 	this.model = model;
	// 	this.price = price;
	// 	this.quantity = quantity;
	// 	this.type = type;
	// 	this.rebate = rebate;
	// }

	
	// public double getRebate() {
	// 	return rebate;
	// }

	// public void setRebate(double rebate) {
	// 	this.rebate = rebate;
	// }
	
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

	// public double getPrice() {
	// 	return price;
	// }

	// public void setPrice(double price) {
	// 	this.price = price;
	// }

	public int getquantity() {
		return quantity;
	}

	public void setquantity(int quantity) {
		this.quantity = quantity;
	}

	// public String getType() {
	// 	return type;
	// }

	// public void setType(String type) {
	// 	this.type = type;
	// }
	public double getdiscount() {
		return discount;
	}

	public void setdiscount(double discount) {
		this.discount = discount;
	}

}
