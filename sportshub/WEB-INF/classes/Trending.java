
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Trending extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		MongoDBDataStoreUtilities mdsu = new MongoDBDataStoreUtilities();

		LinkedHashMap<String, Float> mostLiked = mdsu.getMostLiked();

		MySqlDataStoreUtilities msdsq = new MySqlDataStoreUtilities();

		//LinkedHashMap<String, Integer> mostReviewed = mdsu.getMostReviewed();
		LinkedHashMap<String,Integer> mostSold = msdsq.getMostSelled();
		LinkedHashMap<String, Integer> zipCode = msdsq.getTopZip();		
		

		HttpSession session = request.getSession(false);
		User currentUser = null;
		if (session != null) {
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

		// out.println(getHTMLContent(mostLiked,"60616" ,mostReviewed));
		out.println(getHTMLContent(mostLiked, zipCode, mostSold));
		out.println(getContentFooter());
		
		
		

		if (session != null) {
			out.print(utility.printHtmlUserLeftNav(_dirPath + "LeftNav.html", currentUser));
		} else {
			out.print(utility.printHtml(_dirPath + "LeftNav.html"));
		}
		out.print(utility.printHtml(_dirPath + "Footer.html"));

	}
	private String getContentFooter() {

		return "</section>" + "</div>";
	}
	private String getHTMLContent(LinkedHashMap<String, Float> mostLiked, LinkedHashMap<String, Integer> zipCode,
			LinkedHashMap<String, Integer> mostSold) {
		String _Heading = "" + "<div id='body'>" + "<section id='content'>" + "";
		String _content ="";

		_content =""
				+ "<center><h2>Top Zip Code</h2></center>"
				+ "<table>"
				+ "<tr>"
				+ "<th>"
				+ "Zip Code"
				+ "</th>"
				+ "<th>"
				+ "No of Ticket Sold."
				+ "</th>"
				+ "</tr>";
		for(Map.Entry<String, Integer> data :zipCode.entrySet()) {
			String k = data.getKey();
			int v = data.getValue();
			_content += "<tr>" + "<td>" + k + "</td>" + "<td>" + v + "</td>" + "</tr>";					
		}
		_content+= "</table>";
			_content +=""
				+ "<center><h2>Most Liked</h2></center>"
				+ "<table>"
				+ "<tr>"
				+ "<th>"
				+ "Event Name"
				+ "</th>"
				+ "<th>"
				+ "Rating"
				+ "</th>"
				+ "</tr>";
		for(Map.Entry<String, Float> data :mostLiked.entrySet()) {
			String k = data.getKey();
			float v = data.getValue();
			_content += "<tr>" + "<td>" + k + "</td>" + "<td>" + v + "</td>" + "</tr>";					
		}
		_content+= "</table>";

		
		
/*		_content += "<center><h2>Most Reviewed</h2></center>"
				+ "<table>"
				+ "<tr>"
				+ "<th>"
				+ "Product Name"
				+ "</th>"
				+ "<th>"
				+ "No. of Reviews"
				+ "</th>"
				+ "</tr>";
		for (Map.Entry<String, Integer> data : mostReviewed.entrySet()) {
			String k = data.getKey();
			int v = data.getValue();
			_content += "<tr>" + "<td>" + k + "</td>" + "<td>" + v + "</td>" + "</tr>";
		}*/

		_content += "<center><h2>Most Sold</h2></center>"
				+ "<table>"
				+ "<tr>"
				+ "<th>"
				+ "Product Name"
				+ "</th>"
				+ "<th>"
				+ "No. of Items Sold"
				+ "</th>"
				+ "</tr>";
		for (Map.Entry<String, Integer> data : mostSold.entrySet()) {
			String k = data.getKey();
			int v = data.getValue();
			_content += "<tr>" + "<td>" + k + "</td>" + "<td>" + v + "</td>" + "</tr>";

		// for (Map.Entry<String, Integer> data : mostReviewed.entrySet()) {
		// 	String k = data.getKey();
		// 	int v = data.getValue();
		// 	_content += "<tr>" + "<td>" + k + "</td>" + "<td>" + v + "</td>" + "</tr>";
		}		
		_content+= "</table>";

		return _Heading + _content;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
