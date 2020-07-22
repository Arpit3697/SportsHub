import java.nio.file.*;
import java.io.*;
import javax.servlet.http.*;

public class Utilities extends HttpServlet {
	private PrintWriter out = null;

	public Utilities(PrintWriter out) {
		this.out = out;
	}

	public String printHtmlUserLeftNav(String file, User currentUser) {

		String result = HTMLtoString(file);
		if (result == null || result.length() == 0)
			result = "File: <b>" + file + "</b> Not available.<br/>";

		String lookupString = "<x></x>";
		String replaceBy = "";

		if(currentUser.getRole()==0) {
			replaceBy = ""
					+ "<li>"
					+ "<h4>User Control</h4>"
					+ "<ul>"
					+ "<li><a href='/sportshub/ViewOrderServlet\'>View Order</a></li>"
					+ "</ul>"
					+ "</li>";
		} else if (currentUser.getRole() == 1) {
			replaceBy = ""
					+ "<li>"
					+ "<h4>User Control</h4>"
					+ "<ul>"
					+ "<li><a href='/sportshub/AddProduct'>Add Product</a></li>"
					+ "<li><a href='/sportshub/UpdateProduct'>Update/Delete Product</a></li>"
					+ "<li><a href='/sportshub/InventoryServlet'>Inventory Report</a></li>"
					+ "<li><a href='/sportshub/SalesServlet'>Sales Report</a></li>"
					+ "</ul>"
					+ "</li>";

		} else if (currentUser.getRole() == 2) {
			replaceBy = ""
					+ "<li>"
					+ "<h4>User Control</h4>"
					+ "<ul>"
					+ "<li><a href='/sportshub/Registration\'>Register Customer</a></li>"
					+ "<li><a href='/sportshub/UpdateOrder\'>View Order</a></li>"
					// + "<li><a href='/sportshub/LogoutServlet'>Logout</a></li>"
					+ "</ul>"
					+ "</li>";

		}

		result = HTMLStringReplace(result, lookupString, replaceBy);

		// String lookupString2 = "<y></y>";
		// String replaceBy2 = "";

		// if(currentUser.getRole()==1) {
		// 	replaceBy2=""
		// 			+"<li>"
		// 			+"<h4>Data Analytics</h4>"
		// 			+"<ul>"
		// 			+"<li><a href='/sportshub/DataAnalyticsServlet\'>Data Analytics on Review</a></li>"
		// 			+"</ul>"
		// 			+"</li>";
		// }
		// result = HTMLStringReplace(result, lookupString2, replaceBy2);

		return result;
	}

	public String printHtmlUserHeader(String file, User currentUser) {

		String result = HTMLtoString(file);
		if (result == null || result.length() == 0)
			result = "File: <b>" + file + "</b> Not available.<br/>";

		String lookupString = "Cart";
		String replaceBy = "Cart<sup>(" + StartupServlet.cart.size() + ")</sup>";
		result = HTMLStringReplace(result, lookupString, replaceBy);

		lookupString = "Hi There! Welcome.";
		replaceBy = "Hi " + currentUser.getFname() + "! Welcome.";
		result = HTMLStringReplace(result, lookupString, replaceBy);

		lookupString = "/sportshub/LoginServlet";
		replaceBy = "/sportshub/LogoutServlet";
		result = HTMLStringReplace(result, lookupString, replaceBy);

		lookupString = "Sign in";
		replaceBy = "Sign out";
		result = HTMLStringReplace(result, lookupString, replaceBy);		

		return result;
	}

	public String printHtmlUserHeader(String file, User currentUser, String chart) {
		String result = HTMLtoString(file);
		if (result == null || result.length() == 0)
			result = "File: <b>" + file + "</b> Not available.<br/>";

		String lookupString = "Cart";
		String replaceBy = "Cart<sup>(" + StartupServlet.cart.size() + ")</sup>";
		result = HTMLStringReplace(result, lookupString, replaceBy);

		lookupString = "Hi There! Welcome.";
		replaceBy = "Hi " + currentUser.getFname() + "! Welcome.";
		result = HTMLStringReplace(result, lookupString, replaceBy);

		lookupString = "/sportshub/LoginServlet";
		replaceBy = "/sportshub/LogoutServlet";
		result = HTMLStringReplace(result, lookupString, replaceBy);

		lookupString = "Sign in";
		replaceBy = "Sign out";
		result = HTMLStringReplace(result, lookupString, replaceBy);
		
		String temp = printHtml(chart);
		lookupString = "</head>";
		replaceBy = "<script type='text/javascript' src='https://www.gstatic.com/charts/loader.js'></script>"
				+ "<script type='text/javascript'>"
				+ temp
				+"</script>"
				+ "</head>";
		result = HTMLStringReplace(result, lookupString, replaceBy);
		
		
		return result;

	}
	public String printHtml(String file) {

		String result = HTMLtoString(file);
		if (result == null || result.length() == 0)
			result = "File: <b>" + file + "</b> Not available.<br/>";

		// if (file.contains("Header.html")) {
		// 	String lookupString = "Cart";
		// 	String replaceBy = "Cart<sup>(" + StartupServlet.cart.size() + ")</sup>";
		// 	result = HTMLStringReplace(result, lookupString, replaceBy);
		// }

		return result;
	}

	private String HTMLStringReplace(String result, String lookupString, String replaceBy) {
		String temp = result.replace(lookupString, replaceBy);
		return temp;
	}

	private String HTMLtoString(String file) {
		StringBuilder content = new StringBuilder();
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(file));
			String str;
			while ((str = in.readLine()) != null)
				content.append(str);
			in.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content.toString();

	}

}
