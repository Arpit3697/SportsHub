import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Home extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session =  request.getSession(false);
		User currentUser = null;
		Cart cart =null;

		if(session !=null) {
			currentUser = (User) session.getAttribute("user");
			cart = (Cart) session.getAttribute("cart");
			if(cart!=null){
				StartupServlet.cart =cart;
			}
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
		out.print(utility.printHtml(_dirPath + "Content.html"));

		if(session !=null) {
			out.print(utility.printHtmlUserLeftNav(_dirPath + "LeftNav.html", currentUser));
		}else {
			out.print(utility.printHtml(_dirPath + "LeftNav.html"));
		}
		out.print(utility.printHtml(_dirPath + "Footer.html"));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
