import java.io.Serializable;
import java.util.*;

public class Cart implements Serializable {
	private String sessionID;
	private String orderID;
	private ArrayList<Product> cart;



	public Cart(ArrayList<Product> cart) {
		this.cart = cart;
	}

	public Cart() {
		this.cart = new ArrayList<Product>();
	}

	public Cart(String sessionID, String orderID) {
		this.sessionID = sessionID;
		this.orderID = orderID;
		this.cart = new ArrayList<Product>();
	}

	public int size() {
		return cart.size();
	}

	public void addToCart(Product p) {
		cart.add(p);
	}

	public void removeFromCart(Product p) {
		ArrayList<Product> tempCart = new ArrayList<Product>();
		for (Product product : cart) {
			if (product.geteventid() != p.geteventid()) {
				tempCart.add(product);
			}
		}
		cart = tempCart;
	}

	public void clearCart() {
		this.cart = null;
		this.cart = new ArrayList<Product>();
	}

	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public ArrayList<Product> getCart() {
		return cart;
	}

	public void setCart(ArrayList<Product> cart) {
		this.cart = cart;
	}

	public HashMap<Product, Integer> displayCart() {
		HashMap<Product, Integer> temp = new HashMap<Product, Integer>();
		for (Product p : cart) {
			if (temp.containsKey(p)) {
				temp.put(p, temp.get(p) + 1);
			} else {
				temp.put(p, 1);
			}
		}
		return temp;
	}

	public Cart updateCart(HashMap<Product, Integer> hashCart) {
		Cart tempCart = new Cart();
		for (Map.Entry<Product, Integer> entry : hashCart.entrySet()) {
			Product p = entry.getKey();
			for (int i = 0; i < entry.getValue(); i++) {
				tempCart.getCart().add(p);
			}
		}
		return tempCart;
	}

}
