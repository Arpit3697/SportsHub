import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

public class Order implements Serializable {
	private String orderId;
	private String name;
	private String address;
	private String zip;
	private String mno;
	private String cc;
	private Cart cart;
	private User user;
	private long deliveryDate;

//	private static AtomicLong orderIdCounter = new AtomicLong();

	public Order() {

	}

	public Order(String orderId, String name, String address, String zip, String mno, String cc, Cart cart, User user,
		long deliveryDate) {
	this.orderId = orderId;
	this.name = name;
	this.address = address;
	this.zip = zip;
	this.mno = mno;
	this.cc = cc;
	this.cart = cart;
	this.user = user;
	this.deliveryDate = deliveryDate;
}

	public Order(String orderId, String name, String address, String mno, String cc, Cart cart, User user,
			long deliveryDate) {
		this.orderId = orderId;
		this.name = name;
		this.address = address;
		this.mno = mno;
		this.cc = cc;
		this.cart = cart;
		this.user = user;
		this.deliveryDate = deliveryDate;
	}

	public Order(String orderId, String name, String address, String mno, String cc, Cart cart) {
		this.orderId = orderId;
		this.name = name;
		this.address = address;
		this.mno = mno;
		this.cc = cc;
		this.cart = cart;
	}

	public Order(String orderId, String name, String address, String mno, String cc, Cart cart, User user) {
		this.orderId = orderId;
		this.name = name;
		this.address = address;
		this.mno = mno;
		this.cc = cc;
		this.cart = cart;
		this.user = user;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMno() {
		return mno;
	}

	public void setMno(String mno) {
		this.mno = mno;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public long getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(long deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

//	public static String generateUniqueOrderID() {
//		return String.valueOf(orderIdCounter.getAndIncrement() + 1);
//	}

	public static long getDeliveryDate(long timestamp) {
		return (timestamp + (15 * 24 * 60 * 60 * 1000));
	}

	public static Order filterByOrderID(ArrayList<Order> allOrder, String oId) {
		ArrayList<Order> all = allOrder;
		Order o = null;
		if (all.size() > 0) {
			for (Order or : all) {
				if (or.getOrderId().equals(oId + "")) {
					o = or;
					break;
				}
			}
		}
		return o;
	}

	public static Order filterByOrderID(ArrayList<Order> allOrder, String oId, User user) {
		ArrayList<Order> all = allOrder;
		Order o = null;
		if (all.size() > 0) {
			for (Order or : all) {
				if (or.getOrderId().equals(oId + "") && or.getUser().getUserId().equals(user.getUserId())) {
					o = or;
					break;
				}
			}
		}
		return o;
	}
}
