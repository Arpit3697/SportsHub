
public class Sales {
	int eventid;
	String eventname;
	int quantity;

	public Sales(int eventid, String eventname, int quantity) {
		this.eventid = eventid;
		this.eventname = eventname;
		this.quantity = quantity;
	}

	// public Sales(int id, String model, double price, double totalsale, int quantity, String type) {
	// 	this.id = id;
	// 	this.model = model;
	// 	this.price = price;
	// 	this.totalsale =totalsale;
	// 	this.quantity = quantity;
	// 	this.type = type;
	// // }
	// public double getTotalSale(
	// 	) {
	// 	return price * (double) quantity;
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

	public int getquantity() {
		return quantity;
	}

	public void setquantity(int quantity) {
		this.quantity = quantity;
	}
	// public int getId() {
	// 	return id;
	// }

	// public void setId(int id) {
	// 	this.id = id;
	// }

	// public String getModel() {
	// 	return model;
	// }

	// public void setModel(String model) {
	// 	this.model = model;
	// }

	// public double getPrice() {
	// 	return price;
	// }

	// public void setPrice(double price) {
	// 	this.price = price;
	// }

	// public int getQuantity() {
	// 	return quantity;
	// }

	// public void setQuantity(int quantity) {
	// 	this.quantity = quantity;
	// }

	// public String getType() {
	// 	return type;
	// }

	// public void setType(String type) {
	// 	this.type = type;
	// }

}
