import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.LinkedHashMap;

public class MySqlDataStoreUtilities {
	Connection con = null;
	String DRIVERS = "com.mysql.jdbc.Driver";
	String URL = "jdbc:mysql://localhost:3306/";
	String db = "sportshub";
	String USER = "root";
	String PASSWORD = "root";
	PreparedStatement ps = null;
	public static HashMap<String, ArrayList<Product>> productData;
	private static ArrayList<Product> products;

	public MySqlDataStoreUtilities() {
	}

	public void prepareCatalog() {
		getConnection();
		productData = new HashMap<String, ArrayList<Product>>();
		products = new ArrayList<Product>();

		prepareProductCatalog();
		// prepareAccessoryList();
		closeConnection();

	}

	private int getLastOrderID() {
		int result = -1;
		try {
			getConnection();
			String SQL = "select max(orderid) from customerorders";
			ps = con.prepareStatement(SQL);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				result = rs.getInt(1);
			}
			rs.close();
			ps.close();
			closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public LinkedHashMap<String, Integer> getMostSelled() {
		LinkedHashMap<String, Integer> p = new LinkedHashMap();
		getConnection();
		try {
			//String SQL = "SELECT count(orderquantity), zip, ordername FROM customerorders group by zip,ordername order by count(orderquantity) DESC";
			String SQL = "Select eventname, sum(orderquantity) AS TotalItemSold from eventcatalog,customerorder where eventcatalog.eventid=customerorder.eventid group by eventname order by sum(orderquantity) desc limit 5";
			ps = con.prepareStatement(SQL);
			ResultSet rs = ps.executeQuery();
			int flag = 0;
			while (rs.next()) {
				String model = rs.getString(1);
				int count = rs.getInt(2);
				//String zip = rs.getString(2);
				//int id = rs.getInt(3);
				//Product pr = findProductById(id);
				p.put(model, count);
				flag++;
				if (flag > 6)
					break;
			}
			;
		} catch (Exception e) {
			e.printStackTrace();
		}
		closeConnection();
		return p;
	}

	public LinkedHashMap<String, Integer> getTopZip() {
		LinkedHashMap<String, Integer> p = new LinkedHashMap();
		getConnection();
		try {
			String SQL = "SELECT zip, SUM(orderquantity) AS TotalItemsSold FROM customerorder group by zip order by SUM(orderquantity) DESC limit 5";
			ps = con.prepareStatement(SQL);
			ResultSet rs = ps.executeQuery();
			int flag = 0;
			while (rs.next()) {
				String zip = rs.getString(1);
				int count = rs.getInt(2);
				//String zip = rs.getString(2);
				//int id = rs.getInt(3);
				//Product pr = findProductById(id);
				p.put(zip, count);
				flag++;
				if (flag > 6)
					break;
			}
			;
		} catch (Exception e) {
			e.printStackTrace();
		}
		closeConnection();
		return p;
	}

	public ArrayList<String> getAllProductNames(){
		ArrayList<String> productnameslist = new ArrayList<String>();
		getConnection();
		try {
			String SQL = "SELECT eventname FROM eventcatalog";
			ps = con.prepareStatement(SQL);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				productnameslist.add(rs.getString("eventname"));
			}
			rs.close();
			ps.close();
		} 
		catch (SQLException e) 
		{
		}
		closeConnection();
		return productnameslist;
	}
		public ArrayList<ProductInventory> getproductInventory(int eventid) {
		ArrayList<ProductInventory> prodinv = new ArrayList<ProductInventory>();
		try {
			getConnection();
			ResultSet rs = null;
			String SQL = "select * from eventinventory where eventid="+eventid;
			ps = con.prepareStatement(SQL);
			rs = ps.executeQuery();
			ProductInventory pi = null;
			while (rs.next()) {
				pi = null;
				pi = new ProductInventory();
				pi.seteventid(rs.getInt(1));
				pi.seteventname(rs.getString(2));
				pi.setlistingid(rs.getString(3));
				pi.setquantity(rs.getInt(4));
				pi.setsectionname(rs.getString(5));
				pi.setprice(rs.getDouble(6));
				prodinv.add(pi);
			}
			rs.close();
			ps.close();
			closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return prodinv;
	}
	// public int updateOrder(String updateOrderId, int productId, int quantity) {
	// 	int result = 0;
	// 	try {
	// 		getConnection();
	// 		String SQL = "update customerorders  set orderquantity=? where orderid=? and ordername=?";
	// 		ps = con.prepareStatement(SQL);
	// 		ps.setInt(1, quantity);
	// 		ps.setInt(2, Integer.parseInt(updateOrderId));
	// 		ps.setInt(3, productId);
	// 		result = ps.executeUpdate();
	// 		ps.close();
	// 		closeConnection();
	// 		return result;
	// 	} catch (Exception e) {
	// 		e.printStackTrace();
	// 	}
	// 	return result;
	// }

	// public int cancelOrder(String cancelOrderId) {
	// 	int result = -1;
	// 	int orderId = Integer.parseInt(cancelOrderId);
	// 	String SQL = "delete from customerorders  where orderid=?";
	// 	getConnection();
	// 	try {
	// 		ps = con.prepareStatement(SQL);
	// 		ps.setInt(1, orderId);
	// 		result = ps.executeUpdate();
	// 		ps.close();
	// 		closeConnection();
	// 		// System.out.println("Cancel Result: " + result);
	// 		return result;
	// 	} catch (SQLException e) {
	// 		e.printStackTrace();
	// 	}

	// 	return result;
	// }

	public ArrayList<String> readOrderInfo(String oId, User currentUser) {
		ArrayList<String> orderinfo = new ArrayList<String>();
		int orderId = Integer.parseInt(oId);
		getConnection();
		try {
		String SQL = "select orderId,eventname,orderquantity from customerorder as c,eventcatalog as e where c.eventid = e.eventid and c.username=? and c.orderId = ?";
		ps = con.prepareStatement(SQL);
		ps.setInt(1, orderId);
		ps.setString(2, currentUser.getUserId());
		ResultSet rs = ps.executeQuery();
		while(rs.next())
		{
			//String string1 = new String(rs.getInt("orderID"),rs.getString("eventname"),rs.getInt("orderquantity"));
        	//orderinfo.add(string1);
				// orderinfo.add(rs.getInt("orderID"));
				// orderinfo.add(rs.getString("eventname"));
				// orderinfo.add(rs.getInt("orderquantity"));
		}
		rs.close();
		ps.close();
		} 
		catch (SQLException e) 
		{
		}
		closeConnection();
		return orderinfo;
}
	// 	int orderId = Integer.parseInt(oId);
	// 	// Order order = null;
	// 	// String SQL = "select * from " + " customerorder as c Join eventcatalog as p "
	// 	// 		+ " on c.listingid = p.eventid " + " where c.orderId=? and c.username=?";

	// 	try {
	// 		getConnection();
	// 		ps = con.prepareStatement(SQL);
	// 		ps.setInt(1, orderId);
	// 		ps.setString(2, currentUser.getUserId());
	// 		ResultSet rs = ps.executeQuery();
	// 		Cart cart = new Cart();
	// 		User user = new User();
	// 		// Product p = null;
	// 		// Accessory a = null;
	// 		if (rs != null) {
	// 			// order = new Order();
	// 			while (rs.next()) {
	// 				order.setOrderId(rs.getInt(1) + "");
	// 				user.setUserId(rs.getString(2));
	// 				order.setUser(user);
	// 				int quantity = rs.getInt(4);
	// 				order.setAddress(rs.getString(6));
	// 				order.setCc(rs.getString(7));

	// 				Timestamp t = rs.getTimestamp(8);
	// 				order.setDeliveryDate(t.getTime());
	// 				order.setZip(rs.getString(9));
	// 				int productid = rs.getInt(10);
	// 				String model = rs.getString(11);
	// 				double price = rs.getDouble(12);
	// 				String image = rs.getString(13);
	// 				String manufacturer = rs.getString(14);
	// 				String condition = rs.getString(15);
	// 				double discount = rs.getDouble(16);
	// 				int qty = rs.getInt(17);
	// 				String type = rs.getString(18);
	// 				if (!type.equalsIgnoreCase("accessory")) {
	// 					p = null;
	// 					p = new Product();
	// 					p.setProductId(productid);
	// 					p.setModel(model);
	// 					p.setPrice(price);
	// 					p.setImage(image);
	// 					p.setManufacturer(manufacturer);
	// 					p.setCondition(condition);
	// 					p.setDiscount(discount);
	// 					p.setQuantity(quantity);
	// 					p.setType(type);

	// 					for (int i = 0; i < quantity; i++) {
	// 						cart.addToCart(p);
	// 					}

	// 				}
	// 			}

	// 		}
	// 		order.setCart(cart);
	// 		rs.close();
	// 		ps.close();
	// 		closeConnection();
	// 	} catch (SQLException e) {
	// 		e.printStackTrace();
	// 	}

	// 	return order;
	// }

	// public Order readOrderInfo(String oId) {
	// 	int orderId = Integer.parseInt(oId);
	// 	Order order = new Order();
	// 	String SQL = "select * from " + "customerorders as c Join productcatalog as p "
	// 			+ " on c.ordername = p.productid " + " where c.orderId=?";

	// 	try {
	// 		getConnection();
	// 		ps = con.prepareStatement(SQL);
	// 		ps.setInt(1, orderId);
	// 		ResultSet rs = ps.executeQuery();
	// 		Cart cart = new Cart();
	// 		User user = new User();
	// 		Product p = null;
	// 		Accessory a = null;
	// 		while (rs.next()) {
	// 			order.setOrderId(rs.getInt(1) + "");
	// 			user.setUserId(rs.getString(2));
	// 			order.setUser(user);
	// 			int quantity = rs.getInt(4);
	// 			order.setAddress(rs.getString(6));
	// 			order.setCc(rs.getString(7));

	// 			Timestamp t = rs.getTimestamp(8);
	// 			order.setDeliveryDate(t.getTime());
	// 			order.setZip(rs.getString(9));
	// 			int productid = rs.getInt(10);
	// 			String model = rs.getString(11);
	// 			double price = rs.getDouble(12);
	// 			String image = rs.getString(13);
	// 			String manufacturer = rs.getString(14);
	// 			String condition = rs.getString(15);
	// 			double discount = rs.getDouble(16);
	// 			int qty = rs.getInt(17);
	// 			String type = rs.getString(18);
	// 			if (!type.equalsIgnoreCase("accessory")) {
	// 				p = null;
	// 				p = new Product();
	// 				p.setProductId(productid);
	// 				p.setModel(model);
	// 				p.setPrice(price);
	// 				p.setImage(image);
	// 				p.setManufacturer(manufacturer);
	// 				p.setCondition(condition);
	// 				p.setDiscount(discount);
	// 				p.setQuantity(quantity);
	// 				p.setType(type);

	// 				for (int i = 0; i < quantity; i++) {
	// 					cart.addToCart(p);
	// 				}

	// 			} else {
	// 				a = null;
	// 				a = new Accessory();
	// 				a.setProductId(productid);
	// 				a.setModel(model);
	// 				a.setPrice(price);
	// 				a.setImage(image);
	// 				a.setManufacturer(manufacturer);
	// 				a.setType(type);
	// 				for (int i = 0; i < quantity; i++) {
	// 					cart.addToCart(a);
	// 				}
	// 			}

	// 		}
	// 		order.setCart(cart);
	// 		rs.close();
	// 		ps.close();
	// 		closeConnection();
	// 	} catch (SQLException e) {
	// 		e.printStackTrace();
	// 	}

	// 	return order;
	// }

	public int writeOrderInfo(Order o) {
		int index = getLastOrderID();
		try {
			getConnection();
			con.setAutoCommit(false);
			if (index >= 0) {
				index++;
				String SQL = "insert into customerorders values(?,?,?,?,?,?,?,?,?)";
				//String SQL = "call sportshub.writeorder(?, ?, ?, ?, ?, ?, ?, ?, ?)";
				ps = con.prepareStatement(SQL);
				ps.setInt(1, index);
				ps.setString(2, o.getUser().getUserId());
				ps.setInt(5, 0);
				ps.setString(6, o.getAddress());
				ps.setString(7, o.getCc());
				ps.setTimestamp(8, new Timestamp(o.getDeliveryDate()));
				ps.setString(9, o.getZip());
				HashMap<Product, Integer> cart = o.getCart().displayCart();
				for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
					int pid = entry.getKey().geteventid();
					int qty = entry.getValue();
					ps.setInt(3, pid);
					ps.setInt(4, qty);
					int result = ps.executeUpdate();
					if (result <= 0) {
						con.rollback();
					} else {
						for (Product p : MySqlDataStoreUtilities.products) {
							// if (p.geteventid() == pid) {
							// 	p.setQuantity(p.getQuantity() - qty);
							// }
						}
					}
				}
				con.commit();
				ps.close();
				closeConnection();
				return index++;
			} else {
				return -1;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int writeUserInfo(User user) {
		try {
			getConnection();
			String SQL = "insert into registration values(?,?,?,?,?,?,?)";
			ps = con.prepareStatement(SQL);
			ps.setString(1, user.getUserId());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getFname());
			ps.setString(4, user.getLname());
			ps.setString(5, user.getEmail());
			ps.setString(6, user.getMno());
			ps.setInt(7, user.getRole());
			int result = ps.executeUpdate();
			ps.close();
			closeConnection();
			if (result > 0) {
				return 1;
			} else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public ArrayList<User> readUserInfo() {
		ArrayList<User> users = new ArrayList<User>();
		try {
			getConnection();
			ResultSet rs = null;
			String SQL = "select * from registration";
			ps = con.prepareStatement(SQL);
			rs = ps.executeQuery();
			User u = null;
			while (rs.next()) {
				u = null;
				u = new User();
				u.setUserId(rs.getString(1));
				u.setPassword(rs.getString(2));
				u.setFname(rs.getString(3));
				u.setLname(rs.getString(4));
				u.setEmail(rs.getString(5));
				u.setMno(rs.getString(6));
				u.setRole(rs.getInt(7));
				users.add(u);
			}
			rs.close();
			ps.close();
			closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return users;
	}

	public ArrayList<Product> getsimilarevents(){
		ArrayList<Product> products = new ArrayList<Product>();
		try {
			getConnection();
			ResultSet rs = null;
			String SQL = "select * from eventcatalog where sportscategory='Football' limit 2";
			ps = con.prepareStatement(SQL);
			rs = ps.executeQuery();
			Product p = null;
			while (rs.next()) {
				p = null;
				p = new Product();
				p.seteventid(rs.getInt(1));
					p.seteventname(rs.getString(2));
					p.setImage(rs.getString(3));
					p.setsportscategory(rs.getString(4));
					p.seteventdate(rs.getString(5));
					p.seteventplace(rs.getString(6));
					p.seteventcity(rs.getString(7));
					p.seteventstate(rs.getString(8));
					p.seteventzipcode(rs.getString(9));
					p.setDiscount(rs.getDouble(10));
					products.add(p);
			}
			rs.close();
			ps.close();
			closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return products;
	}
	public static HashMap<String, ArrayList<Product>> retriveAllProducts() {
		final String key = "allProducts";
		productData.put(key, products);
		return productData;
	}

	// private void prepareAccessoryList() {
	// 	ResultSet rs = null;
	// 	try {
	// 		for (Product p : products) {
	// 			if (!p.getType().equalsIgnoreCase("accessory")) {
	// 				String SQL = "select a_id from product_accessory where p_id= ?";

	// 				ps = con.prepareStatement(SQL);
	// 				ps.setInt(1, p.getProductId());
	// 				rs = ps.executeQuery();
	// 				while (rs.next()) {
	// 					int id = rs.getInt(1);
	// 					p.getAccessories().add(id);
	// 				}
	// 			}
	// 			if (rs != null)
	// 				rs.close();
	// 			ps.close();
	// 		}
	// 	} catch (SQLException e) {
	// 		e.printStackTrace();
	// 	}

	// }

	private void prepareProductCatalog() {
		String SQL = "select * from eventcatalog order by eventdate desc";
		try {
			ps = con.prepareStatement(SQL);
			ResultSet rs = ps.executeQuery();
			Product p = null;
			System.out.println("working");
			// Accessory a = null;
			while (rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				String image = rs.getString(3);
				String sports_category = rs.getString(4);
				String event_date = rs.getString(5);
				String event_place = rs.getString(6);
				String event_city = rs.getString(7);
				String event_state = rs.getString(8);
				String event_zipcode = rs.getString(9);
				double discount = rs.getDouble(10);

				// if (type.equalsIgnoreCase("accessory")) {
				// 	a = null;
				// 	a = new Accessory();
				// 	a.setProductId(id);
				// 	a.setModel(model);
				// 	a.setPrice(price);
				// 	a.setImage(image);
				// 	a.setManufacturer(manufacturer);
				// 	a.setType(type);
				// 	products.add(a);
				// } else {
					p = null;
					p = new Product();
					p.seteventid(id);
					p.seteventname(name);
					p.setImage(image);
					p.setsportscategory(sports_category);
					p.seteventdate(event_date);
					p.seteventplace(event_place);
					p.seteventcity(event_city);
					p.seteventstate(event_state);
					p.seteventzipcode(event_zipcode);
					p.setDiscount(discount);
					products.add(p);
				// }

			}
			rs.close();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void getConnection() {
		try {								
			Class.forName(DRIVERS).newInstance();
			con = DriverManager.getConnection(URL + db, USER, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void closeConnection() {
		try {
			con.close();
			con = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// public void firstTimeAddProduct(HashMap<String, ArrayList<Product>> hashMap) {
	// 	getConnection();
	// 	ArrayList<Product> products = null;
	// 	for (Map.Entry<String, ArrayList<Product>> entry : hashMap.entrySet()) {
	// 		products = entry.getValue();
	// 	}
	// 	// System.out.println("Size: " + products.size());
	// 	if (products.size() != 0) {
	// 		String SQL = "Insert into productcatalog values(?,?,?,?,?,?,?,?,?)";
	// 		try {
	// 			PreparedStatement ps = con.prepareStatement(SQL);
	// 			int result;
	// 			int count = 0;
	// 			int size = products.size();
	// 			for (Product p : products) {
	// 				if (!p.getType().equalsIgnoreCase("accessory")) {
	// 					ps.setInt(1, p.getProductId());
	// 					ps.setString(2, p.getModel());
	// 					ps.setDouble(3, p.getPrice());
	// 					ps.setString(4, p.getImage());
	// 					ps.setString(5, p.getManufacturer());
	// 					ps.setString(6, p.getCondition());
	// 					ps.setDouble(7, p.getDiscount());
	// 					ps.setInt(8, p.getQuantity());
	// 					ps.setString(9, p.getType());
	// 				} else {
	// 					ps.setInt(1, p.getProductId());
	// 					ps.setString(2, p.getModel());
	// 					ps.setDouble(3, p.getPrice());
	// 					ps.setString(4, p.getImage());
	// 					ps.setString(5, p.getManufacturer());
	// 					ps.setString(6, null);
	// 					ps.setDouble(7, 0.0);
	// 					ps.setInt(8, 0);
	// 					ps.setString(9, p.getType());
	// 				}
	// 				result = ps.executeUpdate();
	// 			}
	// 			ps.close();

	// 			String SQL1 = "Insert into product_accessory values(?,?)";
	// 			PreparedStatement ps1 = con.prepareStatement(SQL1);
	// 			for (Product p : products) {
	// 				if (p instanceof Product) {
	// 					if (p.getAccessories().size() > 0) {
	// 						ArrayList<Integer> accessories = p.getAccessories();
	// 						// System.out.println(p.getProductId() + " " + accessories.size());
	// 						for (Integer i : accessories) {
	// 							ps1.setInt(1, p.getProductId());
	// 							ps1.setInt(2, i);
	// 							result = ps1.executeUpdate();
	// 						}
	// 					}
	// 				}
	// 			}
	// 			ps1.close();

	// 		} catch (SQLException e) {
	// 			e.printStackTrace();
	// 		}
	// 	}
	// 	closeConnection();
	// }

	// modify this
	public int addProduct(Product p) {
		try {
			if (p != null) {
				getConnection();
				String SQL = "insert into eventcatalog values (?,?,?,?,?,?,?,?,?,?)";
				ps = con.prepareStatement(SQL);
				ps.setInt(1, p.geteventid());
				ps.setString(2, p.geteventname());
				ps.setString(3, p.getImage());
				ps.setString(4, p.getsportscategory());
				ps.setString(5, p.geteventdate());
				ps.setString(6, p.geteventplace());
				ps.setString(7, p.geteventcity());
				ps.setString(8, p.geteventstate());
				ps.setString(9, p.geteventzipcode());
				ps.setDouble(10, p.getDiscount());
				int result = ps.executeUpdate();
				if (result > 0) {
					MySqlDataStoreUtilities.products.add(p);
					ps.close();
					closeConnection();
					return 1;
				} else {
					return 0;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;

	}

	// modify this
	public int updateProduct(Product p) {
		try {
			if (p != null) {
				getConnection();
				String SQL = "update eventcatalog set eventname=?, image=?,  sportscategory=?,eventdate=?, eventplace=?, eventcity=?, eventstate=?, eventzipcode=?, discount=?  where eventid=?";
				ps = con.prepareStatement(SQL);
				
				ps.setString(1, p.geteventname());
				ps.setString(2, p.getImage());
				ps.setString(3, p.getsportscategory());
				ps.setString(4, p.geteventdate());
				ps.setString(5, p.geteventplace());
				ps.setString(6, p.geteventcity());
				ps.setString(7, p.geteventstate());
				ps.setString(8, p.geteventzipcode());
				ps.setDouble(9, p.getDiscount());
				ps.setInt(10, p.geteventid());
				int result = ps.executeUpdate();
				ps.close();
				closeConnection();
				if (result > 0) {
					for (Product p1 : MySqlDataStoreUtilities.products) {
						if (p1.geteventid() == p.geteventid()) {
							p1.updateProduct(p);
							break;
						}
					}
					return 1;
				} else {
					return 0;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	// modify this
	public int deleteProduct(int id) {
		try {
			getConnection();
			String SQL = "Delete from productcatalog where eventid=?";
			ps = con.prepareStatement(SQL);
			ps.setInt(1, id);
			int result = ps.executeUpdate();
			ps.close();
			closeConnection();
			if (result > 0) {
				ArrayList<Product> updatedCatalog = new ArrayList<Product>();
				for (Product p : products) {
					if (p.geteventid() != id) {
						updatedCatalog.add(p);
					}
				}
				MySqlDataStoreUtilities.products = updatedCatalog;
				return 1;
			} else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static HashMap<String, ArrayList<Product>> filterBysportscategory(String sportscategory) {
		HashMap<String, ArrayList<Product>> resultData = new HashMap<String, ArrayList<Product>>();
		ArrayList<Product> filterProduct = new ArrayList<Product>();
		for (Product p : products) {
			if (p.getsportscategory().equalsIgnoreCase(sportscategory)) {
				filterProduct.add(p);
			}
		}
		resultData.put(sportscategory, filterProduct);

		return resultData;
	}

	public static Product fetchProductByeventid(int eventid) {
		Product resultProdut = null;
		for (Product p : products) {
			if (p.geteventid() == eventid) {
				resultProdut = p;
				break;
			}
		}
		return resultProdut;
	}
	public static HashMap<String, ArrayList<Product>> filterByeventcitysportscategory(String sportscategory, String eventcity) {
		HashMap<String, ArrayList<Product>> resultData = new HashMap<String, ArrayList<Product>>();
		ArrayList<Product> filterProduct = new ArrayList<Product>();
		for (Product p : products) {
			if (p.geteventcity().equalsIgnoreCase(eventcity) && p.getsportscategory().equalsIgnoreCase(sportscategory)) {
				filterProduct.add(p);
			}
		}
		resultData.put(eventcity, filterProduct);
		return resultData;
	}
	public static HashMap<String, ArrayList<Product>> filterByeventcity(String sportscategory, String eventcity) {
		HashMap<String, ArrayList<Product>> resultData = new HashMap<String, ArrayList<Product>>();
		ArrayList<Product> filterProduct = new ArrayList<Product>();
		for (Product p : products) {
			if (p.geteventcity().equalsIgnoreCase(eventcity) && p.getsportscategory().equalsIgnoreCase(sportscategory)) {
				filterProduct.add(p);
			}
		}
		resultData.put(eventcity, filterProduct);
		return resultData;
	}

	public static HashMap<String, ArrayList<Product>> filterByeventcity(String eventcity) {
		HashMap<String, ArrayList<Product>> resultData = new HashMap<String, ArrayList<Product>>();
		ArrayList<Product> filterProduct = new ArrayList<Product>();
		for (Product p : products) {
			if (p.geteventcity().equalsIgnoreCase(eventcity)) {
				filterProduct.add(p);
			}
		}
		resultData.put(eventcity, filterProduct);
		return resultData;
	}

	public static HashMap<String, Product> filterByeventid(String eventid) {
		HashMap<String, Product> result = new HashMap<String, Product>();
		Product p = MySqlDataStoreUtilities.findProductByeventid(Integer.parseInt(eventid));
		result.put(p.getsportscategory(), p);
		return result;
	}

	public static Product findProductByeventid(int parseInt) {
		for (Product p : products) {
			if (p.geteventid() == parseInt) {
				return p;
			}
		}
		return null;
	}

	// public static HashMap<Integer, ArrayList<Accessory>> findAccessoryByProductID(String productId) {
	// 	HashMap<Integer, ArrayList<Accessory>> accessoryList = new HashMap<Integer, ArrayList<Accessory>>();

	// 	Product p = MySqlDataStoreUtilities.findProductById(Integer.parseInt(productId));
	// 	if (p != null) {
	// 		ArrayList<Integer> accessoryListIds = p.getAccessories();

	// 		HashMap<String, ArrayList<Product>> allAccessories = MySqlDataStoreUtilities.filterByType("accessory");
	// 		ArrayList<Accessory> filterAccessory = new ArrayList<Accessory>();

	// 		for (Map.Entry<String, ArrayList<Product>> entry : allAccessories.entrySet()) {
	// 			for (Product p1 : entry.getValue()) {
	// 				if (p1 instanceof Accessory && accessoryListIds.contains(p1.getProductId())) {
	// 					filterAccessory.add((Accessory) p1);
	// 				}
	// 			}
	// 		}
	// 		accessoryList.put(filterAccessory.size(), filterAccessory);
	// 	}
	// 	return accessoryList;
	// }

	public HashMap<String, ArrayList<Inventory>> getInventory() {
		HashMap<String, ArrayList<Inventory>> result = new HashMap<String, ArrayList<Inventory>>();
		ArrayList<Inventory> inv = new ArrayList<Inventory>();
		try {
			getConnection();
			String SQL = "select e.eventid, e.eventname as eventname, sum(ei.quantity) as Quantity, discount from eventcatalog as e Join eventinventory as ei on e.eventid=ei.eventid group by e.eventid order by discount desc";
			ps = con.prepareStatement(SQL);
			ResultSet rs = ps.executeQuery();
			Inventory i = null;
			while (rs.next()) {
				i = null;
				i = new Inventory(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getDouble(4));
				inv.add(i);
			}
			rs.close();
			ps.close();
			closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (inv.size() == 0) {
			result.put("ERROR", null);
		} else {
			result.put("ALL Product", inv);
		}
		return result;
	}

	// public HashMap<String, ArrayList<Inventory>> filterInventoryByType(HashMap<String, ArrayList<Inventory>> data,
	// 		String type) {
	// 	HashMap<String, ArrayList<Inventory>> result = new HashMap<String, ArrayList<Inventory>>();
	// 	ArrayList<Inventory> filter = new ArrayList<Inventory>();
	// 	for (Map.Entry<String, ArrayList<Inventory>> d : data.entrySet()) {
	// 		ArrayList<Inventory> inv = d.getValue();
	// 		if (inv != null && inv.size() > 0) {
	// 			for (Inventory i : inv) {
	// 				if (type.equalsIgnoreCase(i.getType())) {
	// 					filter.add(i);
	// 				}
	// 			}
	// 		}
	// 	}
	// 	if (filter.size() > 0) {
	// 		result.put(type, filter);
	// 	} else {
	// 		result.put("ERROR", filter);
	// 	}
	// 	return result;
	// }

	// public HashMap<String, ArrayList<Product>> getsimilarevents(String eventid) {
	// 	HashMap<String, ArrayList<Product>> result = new HashMap<String, ArrayList<Product>>();
	// 	ArrayList<Product> inv = new ArrayList<Product>();
	// 	int eventid = Integer.parseInt(eventid);
	// 	try {
	// 		getConnection();
	// 		String SQL = "select * from eventcatalog limit 5";
	// 		ps = con.prepareStatement(SQL);
	// 		ResultSet rs = ps.executeQuery();
	// 		Inventory i = null;
	// 		while (rs.next()) {
	// 			p = null;
	// 				p = new Product();
	// 				p.seteventid(id);
	// 				p.seteventname(name);
	// 				p.setImage(image);
	// 				p.setsportscategory(sports_category);
	// 				p.seteventdate(event_date);
	// 				p.seteventplace(event_place);
	// 				p.seteventcity(event_city);
	// 				p.seteventstate(event_state);
	// 				p.seteventzipcode(event_zipcode);
	// 				p.setDiscount(discount);
	// 				products.add(p);
	// 			// i = new Product(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getDouble(4));
	// 			inv.add(p);
	// 		}
	// 		rs.close();
	// 		ps.close();
	// 		closeConnection();
	// 	} catch (Exception e) {
	// 		e.printStackTrace();
	// 	}

	// 	if (inv.size() == 0) {
	// 		result.put("ERROR", null);
	// 	} else {
	// 		result.put("OnSale", inv);
	// 	}
	// 	return result;
	// }

	public HashMap<String, ArrayList<Inventory>> getOnSaleInventory() {
		HashMap<String, ArrayList<Inventory>> result = new HashMap<String, ArrayList<Inventory>>();
		ArrayList<Inventory> inv = new ArrayList<Inventory>();
		try {
			getConnection();
			String SQL = "select e.eventid, e.eventname, sum(ei.quantity) as Quantity, discount from eventcatalog as e Join eventinventory as ei on e.eventid=ei.eventid where discount > 0 group by e.eventid";
			ps = con.prepareStatement(SQL);
			ResultSet rs = ps.executeQuery();
			Inventory i = null;
			while (rs.next()) {
				i = null;
				i = new Inventory(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getDouble(4));
				inv.add(i);
			}
			rs.close();
			ps.close();
			closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (inv.size() == 0) {
			result.put("ERROR", null);
		} else {
			result.put("OnSale", inv);
		}
		return result;
	}

	// public HashMap<String, ArrayList<Inventory>> getOnRebateInventory() {
	// 	HashMap<String, ArrayList<Inventory>> result = new HashMap<String, ArrayList<Inventory>>();
	// 	ArrayList<Inventory> inv = new ArrayList<Inventory>();
	// 	try {
	// 		getConnection();
	// 		String SQL = "select productid, model, price, quantity, ptype, discount  from productcatalog where quantity >0 && discount >0";
	// 		ps = con.prepareStatement(SQL);
	// 		ResultSet rs = ps.executeQuery();
	// 		Inventory i = null;
	// 		while (rs.next()) {
	// 			i = null;
	// 			i = new Inventory(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getInt(4), rs.getString(5),
	// 					rs.getDouble(6));
	// 			inv.add(i);
	// 		}
	// 		rs.close();
	// 		ps.close();
	// 		closeConnection();
	// 	} catch (Exception e) {
	// 		e.printStackTrace();
	// 	}

	// 	if (inv.size() == 0) {
	// 		result.put("ERROR", null);
	// 	} else {
	// 		result.put("OnRebate", inv);
	// 	}
	// 	return result;
	// }

	public HashMap<String, ArrayList<Sales>> getAllSale() {
		HashMap<String, ArrayList<Sales>> result = new HashMap<String, ArrayList<Sales>>();
		ArrayList<Sales> sales = new ArrayList<Sales>();
		try {
			getConnection();
			String SQL = "select c.eventid,e.eventname,sum(c.orderquantity) as totalsale  from customerorder as c Join eventcatalog as e on c.eventid = e.eventid group by eventid";
			ps = con.prepareStatement(SQL);
			ResultSet rs = ps.executeQuery();
			// System.out.println(rs);
			Sales s = null;
			while (rs.next()) {
				s = null;
				s = new Sales(rs.getInt(1), rs.getString(2), rs.getInt(3));
				sales.add(s);
			}
			rs.close();
			ps.close();
			closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (sales.size() == 0) {
			result.put("ERROR", null);
		} else {
			
			result.put("All Sale", sales);
		}
		return result;
	}

	// public HashMap<String, ArrayList<Sales>> filterSalesByType(HashMap<String, ArrayList<Sales>> data, String type) {
	// 	HashMap<String, ArrayList<Sales>> result = new HashMap<String, ArrayList<Sales>>();
	// 	ArrayList<Sales> filter = new ArrayList<Sales>();
	// 	for (Map.Entry<String, ArrayList<Sales>> d : data.entrySet()) {
	// 		ArrayList<Sales> inv = d.getValue();
	// 		if (inv != null && inv.size() > 0) {
	// 			for (Sales i : inv) {
	// 				if (type.equalsIgnoreCase(i.getType())) {
	// 					filter.add(i);
	// 				}
	// 			}
	// 		}
	// 	}
	// 	if (filter.size() > 0) {
	// 		result.put(type, filter);
	// 	} else {
	// 		result.put("ERROR", filter);
	// 	}
	// 	return result;
	// }

	// public HashMap<String, ArrayList<DailySale>> getDailySale() {
	// 	HashMap<String, ArrayList<DailySale>> result = new HashMap<String, ArrayList<DailySale>>();
	// 	ArrayList<DailySale> sale = new ArrayList<DailySale>();
	// 	try {
	// 		getConnection();
	// 		String SQL = "select DATE(DATE_SUB(c.deliverydate, Interval 15 day)),sum(c.orderquantity) , sum(c.orderquantity * p.price) from customerorders as c Join productcatalog as p on c.ordername = p.productid   group by DATE(DATE_SUB(c.deliverydate, Interval 15 day)) order by DATE(DATE_SUB(c.deliverydate, Interval 15 day)) ASC";
	// 		ps = con.prepareStatement(SQL);
	// 		ResultSet rs = ps.executeQuery();
	// 		DailySale ds = null;
	// 		while (rs.next()) {
	// 			ds = null;
	// 			ds = new DailySale(rs.getString(1), rs.getInt(2), rs.getDouble(3));
	// 			sale.add(ds);
	// 		}
	// 		rs.close();
	// 		ps.close();
	// 		closeConnection();
	// 	} catch (Exception e) {
	// 		e.printStackTrace();
	// 	}

	// 	if (sale.size() > 0) {
	// 		result.put("Daily Sale", sale);
	// 	} else {
	// 		result.put("ERROR", sale);
	// 	}
	// 	return result;
	// }	

	public HashMap<Integer, String> getAjaxData(String searchId) {
		HashMap<Integer, String> data = new HashMap<Integer, String>();
		try {
			getConnection();
			String sql = "select eventid, eventname from eventcatalog where eventname like '%" + searchId
					+ "%'";
			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				data.put(id, name);
			}
			rs.close();
			ps.close();
			closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
}
