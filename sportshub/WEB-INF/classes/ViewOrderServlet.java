import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ViewOrderServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session =  request.getSession(false);
		User currentUser = null;
		if(session !=null) {
			currentUser = (User) session.getAttribute("user");
		}


		response.setContentType("text/html");
		String _dirPath = getServletContext().getRealPath("/");

		String orderDisplay = request.getParameter("order");
		String orderId = request.getParameter("orderid");

		PrintWriter out = response.getWriter();
		Utilities utility = new Utilities(out);
		if (session != null) {

			out.print(utility.printHtmlUserHeader(_dirPath + "Header.html", currentUser));
		} else {
			out.print(utility.printHtml(_dirPath + "Header.html"));
		}

		String content = getHTMLContent();
		String contentFooter = getHTMLFooter();
		out.print(content);
		if (orderDisplay != null) {
			if (orderId != null) {


//				ArrayList<Order> allOrder = SerializeDataStoreManager.readOrderInfo();
//				Order ord = Order.filterByOrderID(allOrder, orderId, currentUser);
				MySqlDataStoreUtilities msdsq = new MySqlDataStoreUtilities();
				ArrayList<String> orderinfo = msdsq.readOrderInfo(orderId,currentUser);
				// Order ord = msdsq.readOrderInfo(orderId,currentUser);
				//for (String i : orderinfo) {
				//	if (i.getOrderId != null) {	
					out.print(getOrderView(orderinfo));
				//	} 
				//	else {
					//out.println("<center><h6>No Such Order Available.</h6></center>");
				//	}
				}	
			}
			out.print("");
		
		out.print(contentFooter);
		if(session !=null) {
			out.print(utility.printHtmlUserLeftNav(_dirPath + "LeftNav.html", currentUser));
		}else {
			out.print(utility.printHtml(_dirPath + "LeftNav.html"));
		}
		out.print(utility.printHtml(_dirPath + "Footer.html"));

	}
	private String getOrderView(ArrayList<String> orderinfo) {

		String _Header = "<div class='row'>"
				// + "<form action='/sportshub/CancelOrderServlet' method='post'>"
				+ "<div class='box'>"
				+ "<div class='box-header'>"
				+ "<h3 class='box-title'>Order Details</h3>"
				// + "<input type='hidden' name='cancelOrder' value ='" + ord.getOrderId() + "'>"
				// + "<button class='btn btn-danger pull-right' type='Submit'>Cancel Order</button>"
				+ "</div>"
				+ "<div class='box-body no-padding'>"
				+ "<table class='table table-striped center-table'>"
				+ "<tr>"
				// + "<th style='width: 10px'>#</th>"
				+ "<th>OrderID</th>"
				+ "<th>Eventname</th>"
				+ "<th>Quantity</th>"
				// + "<th>Price</th>"
				+ "</tr>";

		//HashMap<Product,Integer> cart = ord.getCart().displayCart();
		String _Content = "";
		//double total = 0.0;
		//SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		// for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
		// 	Product p = entry.getKey();
		// 	int Quantity = entry.getValue();
		// 	double subtotal = Quantity * p.getPrice();
		// 	total +=subtotal;

			//for (String i : orderinfo) {

				_Content +="<tr>"
						+ "<td>" + "1" + "</td>"
						+ "<td>"+"MONTERREY FLASH AT MESQUITE OUTLAWS"+"</td>"
						+ "<td>"+"1"+"</td>";
						//+ "<td>"
						//+ new Date(ord.getDeliveryDate()).toString()
						//+ "</td>"
						//+ "<td>$"
						//+ subtotal
						//+ "</td>";
		//}
		String _total = ""
				+ "</tr>";


		String _Footer = "</table>"
				+ "</div>"
				+ "</div>";
				//+ "</form>";
		return _Header + _Content + _total + _Footer;
	}
	private String getHTMLFooter() {

		return  "</section>"
				+ "</div>";
	}
	private String getHTMLContent() {
		return "<div id='body'>"
				+ "<section id='content'>"
				+ "<div class='row'>"
				+ "<div class='col-md-3'></div>"
				+ "<div class='col-md-6'>"
				+ "<div class='container'>"
				+ "<form class='form-signin' method='post' action='/sportshub/ViewOrderServlet?order=display'>"
				+ "<center><h2 class='form-signin-heading'>View Order</h2></center>"
				+ ""
				+ ""
				+ "<label for='orderid' class='sr-only'>Order ID</label>"
				 + "<input name='orderid' type='text' id='orderid' class='form-control'placeholder='Order ID' required='' autofocus=''>"
				 + ""
				 + ""
				+ "<button class='btn btn-lg btn-primary btn-block' type='submit'>View Order</button>"
				 + "</form>"
				 + "</div>"
				+ "</div>"
				+ "<div class='col-md-3'></div>"
				+ "</div>"

				;
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
