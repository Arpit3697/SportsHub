import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.text.DecimalFormat; 

public class ProductDisplayServlet extends HttpServlet {
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session =  request.getSession(false);
	
		
		User currentUser = null;
		if(session !=null) {
			currentUser = (User) session.getAttribute("user");
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

		String sportscategory = request.getParameter("sportscategory");

		String eventcity = request.getParameter("eventcity");
		// String productnamedropdown = request.getParameter("productnamedropdown");

		if(eventcity==null) {
			out.print(printProductCatalog(sportscategory,request));
		}else if (sportscategory==null) {
			out.print(printProductCatalog2(eventcity,request));			
		}
		else{
			out.print(printProductCatalog3(sportscategory,eventcity,request));	
		}


//its a temporary solution, for permanent try chnages in utilities.java
		if(session !=null) {
			out.print(utility.printHtmlUserLeftNav(_dirPath + "LeftNav.html", currentUser));
		}else {
			if(sportscategory == null){
			 out.print(utility.printHtml(_dirPath + "LeftNav.html"));
			}
			else{
			out.print(LeftNav(sportscategory));
			}
		}
		out.print(utility.printHtml(_dirPath + "Footer.html"));
	}

	private String LeftNav(String sportscategory){
		String _head = ""
			+"<head>"
			+"<style>"
			+".sidebar h4 {"
			+    "padding-bottom: 0;"
			+    "font-size: 18px;"
			+    "color: #fff;"
			+    "text-transform: uppercase;"
			+    "font-weight: bold;"
			+    "padding: 7px 7px;"
			+    "background-color: darkblue !important;"
			+    "text-align: center;"
			+    "border-radius: 15px;"
			+"}"
			+"a {"
			+    "color: #212529;"
			+    "background-color: transparent;"
			+"}"
			+".sidebar ul {"
			+	"width: 250px;"
			+"}"
			+".sidebar li ul li a:active {"
			+     "background-color: #007bff;" 
			+     "border-radius: 20px;"
			+"}"
			+".sidebar li ul li a:hover {"
			+     "background-color: lightblue;" 
			+     "border-radius: 20px;" 
			+"}"
			+"</style>"
			+"</head>";
		String _content = ""
				+"<aside class='sidebar'>"
				+	"<ul>"
				+	"<x></x>"
				+	"<y></y>"
				+	"<li>"
				+		"<h4>Filter "+sportscategory +" Events By Cities</h4>"
				+		"<ul>"
				+			"<li><a href='/sportshub/ProductDisplayServlet?sportscategory="+sportscategory+"&&eventcity=Chicago'>Chicago</a></li>"
				+			"<li><a href='/sportshub/ProductDisplayServlet?sportscategory="+sportscategory+"&&eventcity=New York'>New York</a></li>"
				+			"<li><a href='/sportshub/ProductDisplayServlet?sportscategory="+sportscategory+"&&eventcity=Boston'>Boston</a></li>"
				+			"<li><a href='/sportshub/ProductDisplayServlet?sportscategory="+sportscategory+"&&eventcity=Pittsburgh'>Pittsburgh</a></li>"
				+			"<li><a href='/sportshub/ProductDisplayServlet?sportscategory="+sportscategory+"&&eventcity=Gainesville'>Gainesville</a></li>"
				+		"</ul>"
				+	"</li>"
				+"</ul>"
				+ "</aside>"
				+ "<div class='clear'>"
				+"</div>";

				return _head+_content;
	}

	private String printProductCatalog3(String sportscategory,String eventcity, HttpServletRequest request) {
		String _Heading = "" + "<div id='body'>" + "<section id='content'>" + "<center><h2 class='form-signin-heading'>"
				+ eventcity.toUpperCase() + "</h2></center>" + "";

//		HashMap<String, ArrayList<Product>> product = SaxParserReader.filterByeventcity(sportscategory, eventcity);
		HashMap<String, ArrayList<Product>> product = MySqlDataStoreUtilities.filterByeventcitysportscategory(sportscategory,eventcity);

		String _Product = "";
		for (Map.Entry<String, ArrayList<Product>> entry : product.entrySet()) {
			ArrayList<Product> prod = entry.getValue();
			for (Product p : prod) {

				_Product += "<div class='row row-space'>" 
						+ "<div class='col-xs-3 img-block'>" + ""
						+ "<a href='#'><img src='" + p.getImage() + "' alt='" 
						+ p.getsportscategory()
						+ "class='img-thumbnail'></a>" + "" 
						+ "</div>" 

						+ "<div class='col-xs-6 divide-block'>"
						+ "<h4 class=''>" + "<a href='/sportshub/ViewProductServlet?eventid="+p.geteventid()+"'>" + p.geteventname() + "</a>" + "</h4>"

						+ "<label class='control-label'>Date: </label>" + " <label class='info-label '>"
						+ p.geteventdate() + "</label>" 						
						+ "<x class='space'></x> |"

						+ "<label class='control-label'>Place: </label>"
						+ " <label class='info-label '>" + p.geteventplace() + "</label>" 
						+ "<x class='space'></x> |"

						+ "<label class='control-label'>Discount: </label>" + " <label class='info-label'>"
						+ p.getDiscount() + "% " + "</label> <br>" 

						+ "<label class='control-label'>Address: </label>"
						+ " <label class='info-label '>" 
						+ p.geteventcity() + ","
						+ p.geteventstate() + ","
						+ p.geteventzipcode()
						+ "</label>"
						+ "</div>";

						DecimalFormat df = new DecimalFormat("#"); 
						String eventid = df.format(p.geteventid()); 	

						MySqlDataStoreUtilities msdsq = new MySqlDataStoreUtilities();
						ArrayList<ProductInventory> prodinv = msdsq.getproductInventory(p.geteventid());

						int size = prodinv.size();

						if(size>0)
						{
								_Product+="<div class='col-xs-3 space-price-block'>" 
							// + "<h3 class='price space'>$" + p.getPrice() + "</h3>"
							+"<table>"+"<tr>"+"<td>"
							+ "<form action='/sportshub/AddToCart' name='requestedForm' value='AddToCart' method='post'>";

							_Product+=""
								 +"<select name='productnamedropdown'>"+ "<option value="+""+">"+""+"</option>";
							
							for (ProductInventory pi : prodinv) {
								String dprice = df.format(pi.getprice());
								String desc = pi.getsectionname()+"--Price:$"+dprice;
								_Product+="<option value="+pi.getlistingid()+">"+desc+"</option>";
							}

							// String listing = request.getParameter("productnamedropdown");
							_Product+=""
							+ "</td>" + "</tr>"
							+ "<tr>" + "<td>"
							+ "<input type='hidden' name='requestedForm' value='AddToCart' >"
							+ "<button class='btn btn-outline-dark' name='cartProduct' value='" + p.geteventid()
							+ "'   type='submit'>Add to Cart</button>" 
							+ "</td>" + "</tr>" 
							+ "</table>" 
							+ "</form>" 
							+ "</div>" + ""
							
							+ "</div>" 
							+ "<hr class='draw-line'>";

						}
						else
						{
							_Product+="<div class='col-xs-3 space-price-block'>" 
							// + "<h3 class='price space'>$" + p.getPrice() + "</h3>"
							+"<table>"
							+"<tr>"+"<td>"
							+ "<label class='info-label red'>" + "Sold out."+ "</label>" 
							+"</td>"+"</tr>"
							+ "<tr>" + "<td>"
							+ "<button class='btn btn-outline-danger' name='cartProduct'>Add to Cart</button>" 
							+ "</td>" + "</tr>" 
							+ "</table>" 
							+ "</form>" 
							+ "</div>" + ""
							
							+ "</div>" 
							+ "<hr class='draw-line'>";
						}
			}
		}

		String _Footer = "</section>" + "</div>";

		return _Heading + _Product + _Footer;
	}
	private String printProductCatalog2(String eventcity,HttpServletRequest request) {
		String _Heading = "" + "<div id='body'>" + "<section id='content'>" + "<center><h2 class='form-signin-heading'>"
				+ eventcity.toUpperCase() + "</h2></center>" + "";

//		HashMap<String, ArrayList<Product>> product = SaxParserReader.filterByeventcity(sportscategory, eventcity);
		HashMap<String, ArrayList<Product>> product = MySqlDataStoreUtilities.filterByeventcity(eventcity);

		String _Product = "";
		for (Map.Entry<String, ArrayList<Product>> entry : product.entrySet()) {
			ArrayList<Product> prod = entry.getValue();
			for (Product p : prod) {

				_Product += "<div class='row row-space'>" 
						+ "<div class='col-xs-3 img-block'>" + ""
						+ "<a href='#'><img src='" + p.getImage() + "' alt='" 
						+ p.getsportscategory()
						+ "class='img-thumbnail'></a>" + "" 
						+ "</div>" 

						+ "<div class='col-xs-6 divide-block'>"
						+ "<h4 class=''>" + "<a href='/sportshub/ViewProductServlet?eventid="+p.geteventid()+"'>" + p.geteventname() + "</a>" + "</h4>"

						+ "<label class='control-label'>Date: </label>" + " <label class='info-label '>"
						+ p.geteventdate() + "</label>" 						
						+ "<x class='space'></x> |"

						+ "<label class='control-label'>Place: </label>"
						+ " <label class='info-label '>" + p.geteventplace() + "</label>" 
						+ "<x class='space'></x> |"

						+ "<label class='control-label'>Discount: </label>" + " <label class='info-label'>"
						+ p.getDiscount() + "% " + "</label> <br>" 

						+ "<label class='control-label'>Address: </label>"
						+ " <label class='info-label '>" 
						+ p.geteventcity() + ","
						+ p.geteventstate() + ","
						+ p.geteventzipcode()
						+ "</label>"
						+ "</div>";

						DecimalFormat df = new DecimalFormat("#"); 
						String eventid = df.format(p.geteventid()); 	

						MySqlDataStoreUtilities msdsq = new MySqlDataStoreUtilities();
						ArrayList<ProductInventory> prodinv = msdsq.getproductInventory(p.geteventid());

						int size = prodinv.size();

						if(size>0)
						{
								_Product+="<div class='col-xs-3 space-price-block'>" 
							// + "<h3 class='price space'>$" + p.getPrice() + "</h3>"
							+"<table>"+"<tr>"+"<td>"
							+ "<form action='/sportshub/AddToCart' name='requestedForm' value='AddToCart' method='post'>";

							_Product+=""
								 +"<select name='productnamedropdown'>"+ "<option value="+""+">"+""+"</option>";
							
							for (ProductInventory pi : prodinv) {
								String dprice = df.format(pi.getprice());
								String desc = pi.getsectionname()+"--Price:$"+dprice;
								_Product+="<option value="+pi.getlistingid()+">"+desc+"</option>";
							}

							// String listing = request.getParameter("productnamedropdown");
							_Product+=""
							+ "</td>" + "</tr>"
							+ "<tr>" + "<td>"
							+ "<input type='hidden' name='requestedForm' value='AddToCart' >"
							+ "<button class='btn btn-outline-dark' name='cartProduct' value='" + p.geteventid()
							+ "'   type='submit'>Add to Cart</button>" 
							+ "</td>" + "</tr>" 
							+ "</table>" 
							+ "</form>" 
							+ "</div>" + ""
							
							+ "</div>" 
							+ "<hr class='draw-line'>";

						}
						else
						{
							_Product+="<div class='col-xs-3 space-price-block'>" 
							// + "<h3 class='price space'>$" + p.getPrice() + "</h3>"
							+"<table>"
							+"<tr>"+"<td>"
							+ "<label class='info-label red'>" + "Sold out."+ "</label>" 
							+"</td>"+"</tr>"
							+ "<tr>" + "<td>"
							+ "<button class='btn btn-outline-danger' name='cartProduct'>Add to Cart</button>" 
							+ "</td>" + "</tr>" 
							+ "</table>" 
							+ "</form>" 
							+ "</div>" + ""
							
							+ "</div>" 
							+ "<hr class='draw-line'>";
						}
			}
		}

		String _Footer = "</section>" + "</div>";

		return _Heading + _Product + _Footer;
	}

	private String printProductCatalog(String sportscategory,HttpServletRequest request) {

		String _Heading = "" + "<div id='body'>" + "<section id='content'>" + "<center><h2 class='form-signin-heading'>"
				+ sportscategory.toUpperCase() + "</h2></center>" + "";

//		HashMap<String, ArrayList<Product>> product = SaxParserReader.filterBysportscategory(sportscategory);
		HashMap<String, ArrayList<Product>> product = MySqlDataStoreUtilities.filterBysportscategory(sportscategory);

		String _Product = "";
		for (Map.Entry<String, ArrayList<Product>> entry : product.entrySet()) {
			ArrayList<Product> prod = entry.getValue();
			for (Product p : prod) {

				_Product += "<div class='row row-space'>" 
						+ "<div class='col-xs-3 img-block'>" + ""
						+ "<a href='#'><img src='" + p.getImage() + "' alt='" 
						+ p.getsportscategory()
						+ "class='img-thumbnail'></a>" + "" 
						+ "</div>" 

						+ "<div class='col-xs-6 divide-block'>"
						+ "<h4 class=''>" + "<a href='/sportshub/ViewProductServlet?eventid="+p.geteventid()+"'>" + p.geteventname() + "</a>" + "</h4>"

						+ "<label class='control-label'>Date: </label>" + " <label class='info-label '>"
						+ p.geteventdate() + "</label>" 						
						+ "<x class='space'></x> |"

						+ "<label class='control-label'>Place: </label>"
						+ " <label class='info-label '>" + p.geteventplace() + "</label>" 
						+ "<x class='space'></x> |"

						+ "<label class='control-label'>Discount: </label>" + " <label class='info-label'>"
						+ p.getDiscount() + "% " + "</label> <br>" 

						+ "<label class='control-label'>Address: </label>"
						+ " <label class='info-label '>" 
						+ p.geteventcity() + ","
						+ p.geteventstate() + ","
						+ p.geteventzipcode()
						+ "</label>"
						+ "</div>";

						DecimalFormat df = new DecimalFormat("#"); 
						String eventid = df.format(p.geteventid()); 	

						MySqlDataStoreUtilities msdsq = new MySqlDataStoreUtilities();
						ArrayList<ProductInventory> prodinv = msdsq.getproductInventory(p.geteventid());

						int size = prodinv.size();

					// 	+ "<div class='col-xs-1 space-price-block '>" 
					// // + "<h3 class='price space'>$"+ p.getPrice() + "</h3>"
					// + "<form action='/sportshub/AddToCart' name='requestedForm' value='AddToCart' method='post'>"
					// + "<input type='hidden' name='requestedForm' value='AddToCart' >"
					// + "<button class='btn btn-outline-dark' name='cartProduct' value='" + p.geteventid()
					// + "'   type='submit'>Add to Cart</button>" + "</form>" + "</div>" + "" + "</div>"
					// + "<hr class='draw-line'>"

						if(size>0)
						{
								_Product+="<div class='col-xs-3 space-price-block'>" 
							// + "<h3 class='price space'>$" + p.getPrice() + "</h3>"
							+"<table>"+"<tr>"+"<td>"
							+ "<form action='/sportshub/AddToCart' name='requestedForm' value='AddToCart' method='post'>";

							_Product+=""
								 +"<select name='productnamedropdown'>"+ "<option value="+""+">"+""+"</option>";
							
							for (ProductInventory pi : prodinv) {
								String dprice = df.format(pi.getprice());
								String desc = pi.getsectionname()+"--Price:$"+dprice;
								_Product+="<option value="+pi.getlistingid()+">"+desc+"</option>";
							}

							// String listing = request.getParameter("productnamedropdown");
							_Product+=""
							+ "</td>" + "</tr>"
							+ "<tr>" + "<td>"
							+ "<input type='hidden' name='requestedForm' value='AddToCart' >"
							+ "<button class='btn btn-outline-dark' name='cartProduct' value='" + p.geteventid()
							+ "'   type='submit'>Add to Cart</button>" 
							+ "</td>" + "</tr>" 
							+ "</table>" 
							+ "</form>" 
							+ "</div>" + ""
							
							+ "</div>" 
							+ "<hr class='draw-line'>";

						}
						else
						{
							_Product+="<div class='col-xs-3 space-price-block'>" 
							// + "<h3 class='price space'>$" + p.getPrice() + "</h3>"
							+"<table>"
							+"<tr>"+"<td>"
							+ "<label class='info-label red'>" + "Sold out."+ "</label>" 
							+"</td>"+"</tr>"
							+ "<tr>" + "<td>"
							+ "<button class='btn btn-outline-danger' name='cartProduct'>Add to Cart</button>" 
							+ "</td>" + "</tr>" 
							+ "</table>" 
							+ "</form>" 
							+ "</div>" + ""
							
							+ "</div>" 
							+ "<hr class='draw-line'>";
						}
			}
		}

		String _Footer = "</section>" + "</div>";

		return _Heading + _Product + _Footer;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
