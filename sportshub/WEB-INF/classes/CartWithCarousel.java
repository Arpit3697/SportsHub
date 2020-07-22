import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class CartWithCarousel extends HttpServlet {
  boolean userLoggedIn = false;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String passedeventid = request.getParameter("eventid");

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
		out.print(getCarousel(passedeventid));

		out.print(content_footer());

		if(session !=null) {
			out.print(utility.printHtmlUserLeftNav(_dirPath + "LeftNav.html", currentUser));
		}else {
			out.print(utility.printHtml(_dirPath + "LeftNav.html"));
		}
		out.print(utility.printHtml(_dirPath + "Footer.html"));
	}

   private String getCarousel(String eventid) {

		MySqlDataStoreUtilities msdsu = new MySqlDataStoreUtilities();

// //		HashMap<Integer,ArrayList<Accessory>> accessories = SaxParserReader.findAccessoryByeventid(eventid);
 		//HashMap<String,ArrayList<Product>> products = msdsu.getsimilarevents(eventid);
		ArrayList<Product> products = msdsu.getsimilarevents();
		// for (User user : users) {
		// 	if (user.getUserId().equalsIgnoreCase(uname) && user.getPassword().equals(pwd) && user.getRole() == role) {
		// 		isUserFound = true;
		// 		currentUser = user;
		// 		break;cl
		// 	}
		// }		
		String _Heading = ""
				+ "<div class='container'>"
				+ "<div class='col-md-12'>"
				+ "<div id='carouselExampleIndicators' class='carousel slide' data-ride='carousel'>"
				+ "<div class='carousel-inner'>"
				+ "";
		String _Content = "";

 		for (Product p : products) {
 			int count = products.size();
// 			ArrayList<Product> p = entry.getValue();
 			boolean firstFlag = true;

 			for (int i = 0; i < count; i +=3) {
 				if(i>count)
 					break;

 				_Content +=""
 						+(i == 0 ? "<div class='carousel-item active'>" : "<div class='carousel-item'>")
 						+ "<div class='row center-row'>";

 				for (int j = i; j < (i + 3); j++) {

					if (j < count) {
						_Content += ""
								+ "<div class='col-xs-4'>"
								+ "<table>"
								+ "<tr>"
								+ "<td>"
								+ "<img class='img-thumbnail accessory-img-block' src='"+p.getImage()+"' alt='IMG'>"
								+ "</td>"
								+ "</tr>"
								+ "<tr>"
								+ "<td>"
								+ "<label class='control-label'>Sports Category: </label>"
								+ "<label class='info-label '>" + p.getsportscategory() + "</label>"
								+ "</td>"
								+ "</tr>"
								+ "<tr>"
								+ "<td>"
								+ "<label class='control-label price'>Price: </label>"
								+ "<label class='info-label '> " + p.geteventname() + " </label>"
								+ "</td>"
								+ "</tr>"
								+ "<tr>"
								+ "<td>"
								+ "<form action='/sportshub/AddToCart' name='requestedForm' value='AddToCart' method='post'>"
								+ "<input type='hidden' name='requestedForm' value='AddToCart' >"
								+ "<button class='btn btn-outline-dark' name='cartProduct' value='"+p.geteventid()+"'   type='submit'>Add to Cart</button>"
								+ "</form>"
								+ "</td>"
								+ "</tr>"
								+ "</table>"
								+ "</div>"
						;
					} else {
						break;
					}
				}
				_Content +=""
						+ "</div>"
						+ "</div>";

			}
		}

		_Content += ""
				+ "<a class='carousel-control-prev' href='#carouselExampleIndicators'role='button' data-slide='prev'> "
				+ "<span class='fa fa-3x fa-chevron-left' aria-hidden='true'></span> "
				+ "<span class='sr-only'>Previous</span>"
				+ "</a> "
				+ "<a class='carousel-control-next' href='#carouselExampleIndicators' 	role='button' data-slide='next'> "
				+ "<span class='fa fa-3x fa-chevron-right' aria-hidden='true'></span> "
				+ "<span class='sr-only'>Next</span>"
				+ "</a>"
				+ "</div></div>"
				+ "</div>";


		return _Heading + _Content;
	}

  private String content_footer(){
    String _Footer =   "</section>"
                     + "</div>";
    return _Footer;
  }

	private String printCart() {
		String _Header = "<div id='body'>"
				+ "<section id='content'>"
				+ (userLoggedIn ? "<form action='/sportshub/PlaceOrderServlet' method='post'>":"<form action='/sportshub/LoginServlet' method='post'>")
				+ "<div class='box'>"
				+ "<div class='box-header'>"
				+ "<h3 class='box-title'>Cart Details</h3>"
				+ "	<button class='btn btn-success pull-right' type='Submit'>Place Order</button>"
				+ "</div>"
				+ "<div class='box-body no-padding'>"
				+ "<table class='table table-striped'>"
				+ "<tr>"
				+ "<th style='width: 10px' color='black'>#</th>"
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
					+ "<td><a href='/sportshub/RemoveFromCart?requestedForm=RemoveFromCart&&cartProduct="+p.geteventid()+"' class='btn btn-outline-dark' role='button'>Remove</a></td>";
			count++;

		}
    String _Footer ="</table>"
				+ "</div>"
				+ "</div>"
				+ "</form>";
		return _Header + _Content + _Footer;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
