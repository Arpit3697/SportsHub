import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class InventoryServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		MySqlDataStoreUtilities msdsu = new MySqlDataStoreUtilities();
		HashMap<String, ArrayList<Inventory>> inventory = msdsu.getInventory();
		HashMap<String, ArrayList<Inventory>> onSaleInventory = msdsu.getOnSaleInventory();
		// HashMap<String, ArrayList<Inventory>> onRebateInventory = msdsu.getOnRebateInventory();

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

			out.print(utility.printHtmlUserHeader(_dirPath + "Header.html", currentUser, _dirPath+"/scripts/inventory.js"));
		} else {
			out.print(utility.printHtml(_dirPath + "Header.html"));
		}

	    out.print(getInventoryTable(inventory));
		out.print(getBarChart());
		out.println(getProductOnSale(onSaleInventory));
		// out.println(getRebateInventory(onRebateInventory));
		out.println(getContentFooter());

		if (session != null) {
			out.print(utility.printHtmlUserLeftNav(_dirPath + "LeftNav.html", currentUser));
		} else {
			out.print(utility.printHtml(_dirPath + "LeftNav.html"));
		}
		out.print(utility.printHtml(_dirPath + "Footer.html"));

	}

	

	private String getBarChart() {
		String _Content = "";
		_Content += "" + "<center><h2>Bar Chart</h2></center>";
		_Content += "<div id='barchart_div' style='border:1px solid #CCC'></div>";

		return _Content;
	}



	// private String getRebateInventory(HashMap<String, ArrayList<Inventory>> inventory) {
	// 	String _Content = "";
	// 	_Content += "" + "<center><h2>On Rebate Inventory</h2></center>";
	// 	for (Map.Entry<String, ArrayList<Inventory>> data : inventory.entrySet()) {
	// 		String frame = data.getKey();
	// 		ArrayList<Inventory> inv = data.getValue();
	// 		if (frame.equalsIgnoreCase("ERROR")) {
	// 			_Content += "<center>No Data Available.</center>";
	// 		} else {
	// 			_Content += ""
	// 					+ "<table id='example3' class='hover' cellspacing='0' width='100%'>"
	// 					+ "<thead>"
	// 					+ "<tr>"
	// 					+ "<th>"
	// 					+ "ID"
	// 					+ "</th>"
	// 					+ "<th>"
	// 					+ "Model"
	// 					+ "</th>"
	// 					+ "<th>"
	// 					+ "Price"
	// 					+ "</th>"
	// 					+ "<th>"
	// 					+ "QTY"
	// 					+ "</th>"
	// 					+ "<th>"
	// 					+ "Rebate"
	// 					+ "</th>"
	// 					+ "</tr>"
	// 					+ "</thead>"
	// 					+ "";
				
	// 			/*_Content += ""
	// 					+ "<tfoot>"
	// 					+ "<tr>"
	// 					+ "<th>"
	// 					+ "ID"
	// 					+ "</th>"
	// 					+ "<th>"
	// 					+ "Model"
	// 					+ "</th>"
	// 					+ "<th>"
	// 					+ "Price"
	// 					+ "</th>"
	// 					+ "<th>"
	// 					+ "QTY"
	// 					+ "</th>"
	// 					+ "<th>"
	// 					+ "Rebate"
	// 					+ "</th>"
	// 					+ "</tr>"
	// 					+ "</tfoot>"
	// 					+ "";*/
				
	// 			_Content +="<tbody>";
	// 			for(Inventory i: inv) {
	// 				_Content += ""
	// 						+ "<tr>"
	// 						+ "<td>"
	// 						+ i.getId()
	// 						+ "</td>"
	// 						+ "<td>"
	// 						+ i.getModel()
	// 						+ "</td>"
	// 						+ "<td>$"
	// 						+ i.getPrice()
	// 						+ "</td>"
	// 						+ "<td>"
	// 						+ i.getQuantity()
	// 						+ "</td>"
	// 						+ "<td>"
	// 						+ i.getRebate()
	// 						+ "</td>"
	// 						+ "</tr>";
	// 			}
	// 			_Content +="</tbody>"
	// 					+ "</table>";

	// 		}
	// 	}

	// 	return  _Content;

	// }

	private String getProductOnSale(HashMap<String, ArrayList<Inventory>> inventory) {
		String _Content = "";
		_Content += "" + "<center><h2>On Sale Inventory</h2></center>";
		for (Map.Entry<String, ArrayList<Inventory>> data : inventory.entrySet()) {
			String frame = data.getKey();
			ArrayList<Inventory> inv = data.getValue();
			if (frame.equalsIgnoreCase("ERROR")) {
				_Content += "<center>No Data Available.</center>";
			} else {
				_Content += ""
						+ "<table id='example2' class='hover' cellspacing='0' width='100%'>"
						+ "<thead>"
						+ "<tr>"
						+ "<th>"
						+ "Event ID"
						+ "</th>"
						+ "<th>"
						+ "Event Name"
						+ "</th>"
						+ "<th>"
						+ "Quantity"
						+ "</th>"
						+ "<th>"
						+ "Discount"
						+ "</th>"
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
						+ "Price"
						+ "</th>"
						+ "<th>"
						+ "QTY"
						+ "</th>"
						+ "</tr>"
						+ "</tfoot>"
						+ "";
				
*/				_Content +="<tbody>";
				for(Inventory i: inv) {
					_Content += ""
							+ "<tr>"
							+ "<td>"
							+ i.geteventid()
							+ "</td>"
							+ "<td>"
							+ i.geteventname()
							+ "</td>"
							+ "<td>"
							+ i.getquantity()
							+ "</td>"
							+ "<td>"
							+ i.getdiscount()
							+ "</td>"
							+ "</tr>";
				}
				_Content +="</tbody>"
						+ "</table>";

			}
		}

		return  _Content;
	}

	private String getContentFooter() {

		return "</section>" + "</div>";
	}

	private String getInventoryTable(HashMap<String, ArrayList<Inventory>> inventory) {
		String _Heading = "" + "<div id='body'>" + "<section id='content'>" + "";
		String _Content = "";
		_Content += "" + "<center><h2>Inventory</h2></center>";
		for (Map.Entry<String, ArrayList<Inventory>> data : inventory.entrySet()) {
			String frame = data.getKey();
			ArrayList<Inventory> inv = data.getValue();
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
						+ "Quantity"
						+ "</th>"
						+ "<th>"
						+ "Discount"
						+ "</th>"
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
						+ "Price"
						+ "</th>"
						+ "<th>"
						+ "QTY"
						+ "</th>"
						+ "</tr>"
						+ "</tfoot>"
						+ "";*/
				
				_Content +="<tbody>";
				for(Inventory i: inv) {
					_Content += ""
							+ "<tr>"
							+ "<td>"
							+ i.geteventid()
							+ "</td>"
							+ "<td>"
							+ i.geteventname()
							+ "</td>"
							+ "<td>"
							+ i.getquantity()
							+ "</td>"
							+ "<td>"
							+ i.getdiscount()
							+ "</td>"
							+ "</tr>";
				}
				_Content +="</tbody>"
						+ "</table>";

			}
		}

		return _Heading + _Content;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
