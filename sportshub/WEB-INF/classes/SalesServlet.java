import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SalesServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		MySqlDataStoreUtilities msdsu = new MySqlDataStoreUtilities();
		HashMap<String, ArrayList<Sales>> allSales = msdsu.getAllSale();

		// HashMap<String, ArrayList<DailySale>> dailySales = msdsu.getDailySale();

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
			out.print(utility.printHtmlUserHeader(_dirPath + "Header.html", currentUser, _dirPath+"/scripts/sales.js"));
		} else {
			out.print(utility.printHtml(_dirPath + "Header.html"));
		}
		out.print(getSaleReport(allSales));
		out.print(getSalesChart());
		//out.print(getDailySale(dailySales));
		out.println(getContentFooter());

		if (session != null) {
			out.print(utility.printHtmlUserLeftNav(_dirPath + "LeftNav.html", currentUser));
		} else {
			out.print(utility.printHtml(_dirPath + "LeftNav.html"));
		}
		out.print(utility.printHtml(_dirPath + "Footer.html"));

	}

	private String getSalesChart() {
		String _Content = "";
		_Content += "" + "<center><h2>Bar Chart</h2></center>";
		_Content += "<div id='barchart_div' style='border:1px solid #CCC'></div>";

		return _Content;
	}

// 	private String getDailySale(HashMap<String, ArrayList<DailySale>> sales) {
// 		String _Content = "";
// 		_Content += "" + "<center><h2>Daily Sales</h2></center>";
// 		for (Map.Entry<String, ArrayList<DailySale>> data : sales.entrySet()) {
// 			String frame = data.getKey();
// 			ArrayList<DailySale> inv = data.getValue();
// 			if (frame.equalsIgnoreCase("ERROR")) {
// 				_Content += "<center>No Data Available.</center>";
// 			} else {
// 				_Content += ""
// 						+ "<table id='example2' class='hover' cellspacing='0' width='100%'>"
// 						+ "<thead>"
// 						+ "<tr>"
// 						+ "<th>"
// 						+ "Date"
// 						+ "</th>"
// 						+ "<th>"
// 						+ "No. Item Sold"
// 						+ "</th>"
// 						+ "<th>"
// 						+ "Total Sale"
// 						+ "</th>"
// 						+ "</tr>"
// 						+ "</thead>"
// 						+ "";
				
// /*				_Content += ""
// 						+ "<tfoot>"
// 						+ "<tr>"
// 						+ "<th>"
// 						+ "Date"
// 						+ "</th>"
// 						+ "<th>"
// 						+ "No. Item Sold"
// 						+ "</th>"
// 						+ "<th>"
// 						+ "Total Sale"
// 						+ "</th>"
// 						+ "</tr>"
// 						+ "</tfoot>"
// 						+ "";
// 				*/
// 				_Content +="<tbody>";
// 				DecimalFormat df = new DecimalFormat(".###");
// 				for(DailySale i: inv) {
// 					_Content += ""
// 							+ "<tr>"
// 							+ "<td>"
// 							+ i.getDate()
// 							+ "</td>"
// 							+ "<td>"
// 							+ i.getQuantity()
// 							+ "</td>"
// 							+ "<td>$"
// 							+ df.format(i.getPrice())
// 							+ "</td>"
// 							+ "</tr>";
// 				}
// 				_Content +="</tbody>"
// 						+ "</table>";

// 			}
// 		}

// 		return _Content;
// 	}

	private String getSaleReport(HashMap<String, ArrayList<Sales>> sales) {
		String _Heading = "" + "<div id='body'>" + "<section id='content'>" + "";
		String _Content = "";
		_Content += "" + "<center><h2>Sales</h2></center>";
		for (Map.Entry<String, ArrayList<Sales>> data : sales.entrySet()) {
			String frame = data.getKey();
			ArrayList<Sales> inv = data.getValue();
			if (frame.equalsIgnoreCase("ERROR")) {
				_Content += "<center>No Data Available.</center>";
			} else {
				_Content += ""
						+ "<table id='example' class='hover' cellspacing='0' width='100%'>"
						+ "<thead>"
						+ "<tr>"
						+ "<th>"
						+ "Event ID"
						+ "</th>"
						+ "<th>"
						+ "Event Name"
						+ "</th>"
						+ "<th>"
						+ "Total Ticket Sold"
						+ "</th>"
						// + "<th>"
						// + "QTY"
						// + "</th>"
						// + "<th>"
						// + "Total Sale"
						// + "</th>"
						+ "</tr>"
						+ "</thead>"
						+ "";
				
/*				_Content += ""
						+ "<tfoot>"
						+ "<tr>"
						+ "<th>"
						+ "ID"
						+ "</th>"
						+ "<th>"
						+ "Model"
						+ "</th>"
						+ "<th>"
						+ "QTY"
						+ "</th>"
						+ "<th>"
						+ "Total Sale"
						+ "</th>"
						+ "</tr>"
						+ "</tfoot>"
						+ "";*/
				
				_Content +="<tbody>";
				//DecimalFormat df = new DecimalFormat(".###");
				for(Sales i: inv) {
					_Content += ""
							+ "<tr>"
							+ "<td>"
							+ i.geteventid()
							+ "</td>"
							+ "<td>"
							+ i.geteventname()
							+ "</td>"
							// + "<td>$"
							// + df.format(i.getPrice())
							// + "</td>"
							+ "<td>"
							+ i.getquantity()
							+ "</td>"
							// + "<td>$"
							// + df.format(i.getTotalSale())
							// + "</td>"
							+ "</tr>";
				}
				_Content +="</tbody>"
						+ "</table>";

			}
		}

		return _Heading + _Content;
	}

	private String getContentFooter() {

		return "</section>" + "</div>";
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
