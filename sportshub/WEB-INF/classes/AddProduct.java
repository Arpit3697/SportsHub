import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AddProduct extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session =  request.getSession(false);
		User currentUser = null;
		if(session !=null) {
			currentUser = (User) session.getAttribute("user");
		}


		response.setContentType("text/html");
		String addProdcut = request.getParameter("addProduct");
		if (addProdcut != null) {
			addProduct(request, response);
		}
		String _dirPath = getServletContext().getRealPath("/");

		PrintWriter out = response.getWriter();
		Utilities utility = new Utilities(out);
		if (session != null) {
			out.print(utility.printHtmlUserHeader(_dirPath + "Header.html", currentUser));
		} else {
			out.print(utility.printHtml(_dirPath + "Header.html"));
		}
		String content = getLoginHTML();
		out.print(content);
		if(session !=null) {
			out.print(utility.printHtmlUserLeftNav(_dirPath + "LeftNav.html", currentUser));
		}else {
			out.print(utility.printHtml(_dirPath + "LeftNav.html"));
		}
		out.print(utility.printHtml(_dirPath + "Footer.html"));
	}

	private void addProduct(HttpServletRequest request, HttpServletResponse response) throws IOException{

		int eventid = Integer.parseInt(request.getParameter("eventid").trim());
		String eventname = request.getParameter("eventname");
		//double price = Double.parseDouble(request.getParameter("price"));
		String img = request.getParameter("img");
		String sportscategory = request.getParameter("sportscategory");
		String eventdate = request.getParameter("eventdate");
		String eventplace = request.getParameter("eventplace");
		String eventcity = request.getParameter("eventcity");
		String eventstate = request.getParameter("eventstate");
		String eventzipcode = request.getParameter("eventzipcode");
		double discount = Double.parseDouble(request.getParameter("discount"));
		// int quantity = Integer.parseInt(request.getParameter("quantity"));
		// String type = request.getParameter("type");

		Product p = new Product();
		p.seteventid(eventid);
		p.seteventname(eventname);
		//p.setPrice(price);
		p.setImage("imagesNew/"+img);
		p.setsportscategory(sportscategory);
		p.seteventdate(eventdate);
		p.seteventplace(eventplace);
		p.seteventcity(eventcity);
		p.seteventstate(eventstate);
		p.seteventzipcode(eventzipcode);
		p.setDiscount(discount);
		// p.setQuantity(quantity);
		// p.setType(type);

//		SaxParserReader.addProduct(p);
		MySqlDataStoreUtilities msdsu = new MySqlDataStoreUtilities();
		int resultcode = msdsu.addProduct(p);
	}

	private String getLoginHTML() {
		return "<div id='body'>"
				+ "<section id='content'>"
				+ "<div class='row'>"
				+ "<div class='col-md-3'></div>"
				+ "<div class='col-md-6'>"
				+ "<div class='container text-center'>"
				+ "<form class='form-signin' method='post' action='/sportshub/AddProduct?addProduct=true'>"
				+ "<center><h2 class='form-signin-heading'>Add Product</h2></center>"
				+ ""
				+ ""
				+ "<label for='eventid' class='sr-only'>Event Id</label>"
				 + "<input name='eventid' type='text' id='eventid' class='form-control'placeholder='eventid' required='' autofocus=''>"
				 + ""
				 + ""
				 + "<label for='eventname' class='sr-only'>Event Name</label>"
				 + "<input name='eventname' type='text' id='eventname' class='form-control'placeholder='eventname' required=''>"
				 + ""
				 + ""
				 //+ "<label for='price' class='sr-only'>Price</label>"
				 //+ "<input name='price' type='text' id='price' class='form-control'placeholder='Price' required=''>"
				 + ""
				 + ""
				 + "<label for='img' class='sr-only'>Image</label>"
				 + "Image:&nbsp <input name='img' type='file' id='mno' class='form-control' required='' >"
				 + ""
				 + ""
				 + "<label for='sportscategory' class='sr-only'>Sports Category</label>"
				 + "<input name='sportscategory' type='text' id='sportscategory' class='form-control'placeholder='Sports Category' required=''>"
				 + ""
				 + ""
				 + "<label for='eventdate' class='sr-only'> Event Date</label>"
				 + "<input name='eventdate' type='text' id='eventdate' class='form-control'placeholder='eventdate' required=''>"
				 + ""
				 + ""
				 + "<label for='eventplace' class='sr-only'> Event Place</label>"
				 + "<input name='eventplace' type='text' id='eventplace' class='form-control'placeholder='eventplace' required=''>"
				 + ""
				 + ""
				 + "<label for='eventcity' class='sr-only'> Event City</label>"
				 + "<input name='eventcity' type='text' id='eventcity' class='form-control'placeholder='eventcity' required=''>"
				 + ""
				 + ""
				 + "<label for='eventstate' class='sr-only'> Event State</label>"
				 + "<input name='eventstate' type='text' id='eventstate' class='form-control'placeholder='eventstate' required=''>"
				 + ""
				 + ""
				 + "<label for='eventzipcode' class='sr-only'> Event Zip Code</label>"
				 + "<input name='eventzipcode' type='text' id='eventzipcode' class='form-control'placeholder='eventzipcode' required=''>"
				 + ""				 
				 + ""
				 + "<label for='discount' class='sr-only'>Discount</label>"
				 + "<input name='discount' type='text' id='discount' class='form-control'placeholder='Discount' required=''>"
				 + ""
				 + ""
				 //+ "<label for='quantity' class='sr-only'>Quantity</label>"
				 //+ "<input name='quantity' type='text' id='quantity' class='form-control'placeholder='Quantity' required=''>"
				 + ""
				 + ""
				 // + "<div class='form-group'>"
				 // + "<label for='role' class='sr-only'>Role</label>"
				  + "<div class='col-xs-5 selectContainer'>"
				 // + "<select class='form-control' name='type' id='type'>"
				 // + "<option value=''>Choose a type</option>"
				 // + "<option value='TV'>TV</option>"
				 // + "<option value='SoundSystems'>SoundSystems</option>"
				 // + "<option value='Phones'>Phones</option>"
				 // + "<option value='Laptops'>Laptops</option>"
				 // + "<option value='VoiceAssistant'>VoiceAssistant</option>"
				 // + "<option value='FitnessWatches'>FitnessWatches</option>"
				 // + "<option value='SmartWatches'>SmartWatches</option>"
				 // + "<option value='Headphones'>Headphones</option>"
				 // + "<option value='WirelessPlan'>WirelessPlan</option>"
				 // + "</select>"
				  + "</div>"
				 // + "</div>"
				 + ""
				 + ""
				 + "<button class='btn btn-lg btn-outline-dark' type='submit'>Add Product</button>"
				 + "</form>"
				 + "</div>"
				+ "</div>"
				+ "<div class='col-md-3'></div>"
				+ "</div>"
				+ "</section>"
				+ "</div>";
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
