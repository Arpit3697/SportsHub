import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class CartDisplayServlet extends HttpServlet {
	boolean userLoggedIn = false;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session =  request.getSession(false);
		User currentUser = null;
		if(session !=null) {
			currentUser = (User) session.getAttribute("user");
			userLoggedIn = true;
		}else {
			userLoggedIn = false;
		}

		response.setContentType("text/html");
		String _dirPath = getServletContext().getRealPath("/");

		PrintWriter out = response.getWriter();
		Utilities utility = new Utilities(out);
		if (session != null) {

			out.print(utility.printHtmlUserHeader(_dirPath + "Header.html", currentUser));
		} else {
			out.print(utility.printHtml(_dirPath + "Header.html"));
		}

		out.print(printCart());

		if(session !=null) {
			out.print(utility.printHtmlUserLeftNav(_dirPath + "LeftNav.html", currentUser));
		}else {
			out.print(utility.printHtml(_dirPath + "LeftNav.html"));
		}
		out.print(utility.printHtml(_dirPath + "Footer.html"));
	}

	private String printCart() {
		String _Header = "<div id='body'>"
				+ "<section id='content'>"
				+ (userLoggedIn ? "<form action='/sportshub/PlaceOrderServlet' method='post'>":"<form action='/sportshub/LoginServlet' method='post'>")
				+ "<div class='box'>"
				+ "<div class='box-header'>"
				+ "<h3 class='box-title'>Cart Details</h3>"
				+ "<button class='btn btn-success pull-right' type='Submit'>Place Order</button>"
				+ "</div>"
				+ "<div class='box-body no-padding'>"
				+ "<table class='table table-striped'>"
				+ "<tr>"
				+ "<th style='width: 10px'>#</th>"
				+ "<th>Product</th>"
				+ "<th>Quantity</th>"
				+ "<th>Buy Warranty</th>"
				+ "<th>Remove</th>"
				+ "</tr>";
		HashMap<Product,Integer> cart = StartupServlet.cart.displayCart();
		String _Content = "";
		int count = 1;
		for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
			Product p = entry.getKey();
			int Quantity = entry.getValue();
			_Content +="<tr>"
					+ "<td>"+count+".</td>"
					+ "<td>"+p.geteventname()+"</td>"
					+ "<td>"+Quantity+"</td>"
					+ "<td><input type='checkbox' name='warranty' value='"+p.geteventid()+"'></td>"
					+ "<td><a href='/sportshub/RemoveFromCart?requestedForm=RemoveFromCart&&cartProduct="+p.geteventid()+"' class='btn btn-danger' role='button'>Remove</a></td>";
			count++;

		}
		String _Footer = "</table>"
				+ "</div>"
				+ "</div>"
				+ "</form>"
				+ "</section>"
				+ "</div>";
		return _Header + _Content + _Footer;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
