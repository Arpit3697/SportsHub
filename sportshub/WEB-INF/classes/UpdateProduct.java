import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class UpdateProduct extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session =  request.getSession(false);
		User currentUser = null;
		if(session !=null) {
			currentUser = (User) session.getAttribute("user");
		}
		String src = request.getParameter("Referer");
		response.setContentType("text/html");
		String _dirPath = getServletContext().getRealPath("/");
		String findProduct = request.getParameter("findProduct");
		String updateProduct = request.getParameter("UpdateProduct");
		String eventid = request.getParameter("eventid");
		String deleteProduct = request.getParameter("DeleteProduct");
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
		if (findProduct != null) {
			if (eventid != null) {
//				Product  p =  SaxParserReader.findProductByeventid(Integer.parseInt(eventid));
				Product  p =  MySqlDataStoreUtilities.findProductByeventid(Integer.parseInt(eventid));
				if (p != null) {
					out.print(getProductView(p));
				} else {
					out.println("<center><h6>Mysql is not running or No Such Product Available.</h6></center>");
				}
			}
			out.print("");
		} else if (updateProduct != null) {
			updateProduct(request, response, src);
			//response.sendRedirect("/sportshub/");
		} else if (deleteProduct != null) {
			int id = Integer.parseInt(deleteProduct);
			//SaxParserReader.deleteProduct(id);
			MySqlDataStoreUtilities msdsq = new MySqlDataStoreUtilities();
			int resultcode= msdsq.deleteProduct(id);
			if (resultcode == 1) {
				out.println("<center><h6>Product Catalog Updated.</h6></center>");
			}else {
				out.println("<center><h6>Mysql is not running.Error occured in the transaction. Please try again later.</h6></center>");
			}

		}
		out.print(contentFooter);
		if(session !=null) {
			out.print(utility.printHtmlUserLeftNav(_dirPath + "LeftNav.html", currentUser));
		}else {
			out.print(utility.printHtml(_dirPath + "LeftNav.html"));
		}
		out.print(utility.printHtml(_dirPath + "Footer.html"));
	}

	private void updateProduct(HttpServletRequest request, HttpServletResponse response, String src) throws IOException {
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

		//SaxParserReader.updateProduct(p);
		MySqlDataStoreUtilities msdsq = new MySqlDataStoreUtilities();
		int resultcode = msdsq.updateProduct(p);
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		Utilities utility = new Utilities(out);
		if(resultcode ==1) {
				out.println("<center><h6>Product details is updated.</h6></center>");
		}else {
				out.println("<center><h6>Mysql is not running. Product details is not updated.</h6></center>");
		}
	}

	private String getProductView(Product p) {
		return "<div class='row'>"
				+ "<div class='col-md-3'></div>"
				+ "<div class='col-md-6'>"
				+ "<div class='container text-center'>"
				+ "<form class='form-signin' method='post' action='/sportshub/UpdateProduct?UpdateProduct=true' >"
				+ "<center><h2 class='form-signin-heading'>Update Product</h2></center>"
				+ ""
				+ ""
				+ "<label for='eventid' class='sr-only'>Event Id</label>"
				+ "<input type='hidden' name='eventid' value='" + p.geteventid() + "'>"
				 + "<input name='' value='"+p.geteventid()+"' disabled type='text' id='eventid' class='form-control' placeholder='eventid' required='' autofocus=''>"
				 + ""
				 + ""
				 + "<label for='eventname' class='sr-only'>Event Name</label>"
				 + "<input name='eventname' value='"+p.geteventname()+"' type='text' id='eventname' class='form-control'placeholder='eventname' required=''>"
				 + ""			 
				 + ""
				 //+ "<label for='price' class='sr-only'>Price</label>"
				 //+ "<input name='price' type='text' id='price' class='form-control'placeholder='Price' required=''>"
				 + ""
				 + ""
				 + "<label for='img' class='sr-only'>Image</label>"
				 + "Image:&nbsp <input name='img' value='"+p.getImage()+"' type='file' id='mno' class='form-control' required='' >"
				 + ""
				 + ""
				 + "<label for='sportscategory' class='sr-only'>Sports Category</label>"
				 + "<input name='sportscategory' value='"+p.getsportscategory()+"' type='text' id='sportscategory' class='form-control'placeholder='Sports Category' required=''>"
				 + ""
				 + ""
				 + "<label for='eventdate' class='sr-only'> Event Date</label>"
				 + "<input name='eventdate' value='"+p.geteventdate()+"'type='text' id='eventdate' class='form-control'placeholder='eventdate' required=''>"
				 + ""
				 + ""
				 + "<label for='eventplace' class='sr-only'> Event Place</label>"
				 + "<input name='eventplace' value='"+p.geteventplace()+"'type='text' id='eventplace' class='form-control'placeholder='eventplace' required=''>"
				 + ""
				 + ""
				 + "<label for='eventcity' class='sr-only'> Event City</label>"
				 + "<input name='eventcity' value='"+p.geteventcity()+"'type='text' id='eventcity' class='form-control'placeholder='eventcity' required=''>"
				 + ""
				 + ""
				 + "<label for='eventstate' class='sr-only'> Event State</label>"
				 + "<input name='eventstate' value='"+p.geteventstate()+"'type='text' id='eventstate' class='form-control'placeholder='eventstate' required=''>"
				 + ""
				 + ""
				 + "<label for='eventzipcode' class='sr-only'> Event Zip Code</label>"
				 + "<input name='eventzipcode' value='"+p.geteventzipcode()+"'type='text' id='eventzipcode' class='form-control'placeholder='eventzipcode' required=''>"
				 + ""				 
				 + ""
				 + "<label for='discount' class='sr-only'>Discount</label>"
				 + "<input name='discount' value='"+p.getDiscount()+"'type='text' id='discount' class='form-control'placeholder='Discount' required=''>"

				 + "<div class='form-group'>"
				 + "<label for='role' class='sr-only'>Role</label>"
				 + "<div class='col-xs-5 selectContainer'>"
				 // + "<select class='form-control' name='type' id='type'>"

				 // + "<option value=''>Choose a type</option>"
				 // + (p.getType().equals("TV")?"<option value='TV' selected='selected'>TV</option>":"<option value='TV'>TV</option>")
				 // + (p.getType().equals("SoundSystems")?"<option value='SoundSystems' selected='selected'>SoundSystems</option>":"<option value='SoundSystems'>SoundSystems</option>")
				 // + (p.getType().equals("Phones")?"<option value='Phones' selected='selected'>Phones</option>":"<option value='Phones'>Phones</option>")
				 // + (p.getType().equals("Laptops")?"<option value='Laptops' selected='selected'>Laptops</option>":"<option value='Laptops'>Laptops</option>")
				 // + (p.getType().equals("VoiceAssistant")?"<option value='VoiceAssistant' selected='selected'>VoiceAssistant</option>":"<option value='VoiceAssistant'>VoiceAssistant</option>")
				 // + (p.getType().equals("FitnessWatches")?"<option value='FitnessWatches' selected='selected'>FitnessWatches</option>":"<option value='FitnessWatches'>FitnessWatches</option>")
				 // + (p.getType().equals("SmartWatches")?"<option value='SmartWatches' selected='selected'>SmartWatches</option>":"<option value='SmartWatches'>SmartWatches</option>")
				 // + (p.getType().equals("Headphones")?"<option value='Headphones' selected='selected'>Headphones</option>":"<option value='Headphones'>Headphones</option>")
				 // + (p.getType().equals("WirelessPlan")?"<option value='WirelessPlan' selected='selected'>WirelessPlan</option>":"<option value='WirelessPlan'>WirelessPlan</option>")
				 // + "</select>"
				 + "</div>"
				 + "</div>"
				+ ""
				 + ""
				 + "<button class='btn btn-lg btn-outline-dark' type='submit'>Update Product</button>"
				 + "<a class='btn btn-lg btn-outline-dark' role='button' href='/sportshub/UpdateProduct?DeleteProduct="+p.geteventid()+"' >Delete Product</a>"
				 + "</form>"
				 + "</div>"
				+ "</div>"
				+ "<div class='col-md-3'></div>"
				+ "</div>";
	}

	private String getHTMLContent() {
		return "<div id='body'>"
				+ "<section id='content'>"
				+ "<div class='row'>"
				+ "<div class='col-md-3'></div>"
				+ "<div class='col-md-6'>"
				+ "<div class='container text-center'>"
				+ "<form class='form-signin' method='post' action='/sportshub/UpdateProduct?findProduct=true'>"
				+ "<center><h2 class='form-signin-heading'>View Product</h2></center>"
				+ ""
				+ ""
				+ "<label for='eventid' class='sr-only'>Product ID</label>"
				 + "<input name='eventid' type='text' id='eventid' class='form-control'placeholder='Event ID' required='' autofocus=''>"
				 + ""
				 + ""
				+ "<button class='btn btn-lg btn-outline-dark' type='submit'>View</button>"
				 + "</form>"
				 + "</div>"
				+ "</div>"
				+ "<div class='col-md-3'></div>"
				+ "</div>"

				;
	}

	private String getHTMLFooter() {

		return  "</section>"
				+ "</div>";
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
