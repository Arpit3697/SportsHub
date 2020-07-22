import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LogoutServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session  = request.getSession(false);
		session.setAttribute("user", null);
		session.setAttribute("cart",null);
		session.invalidate();
		session = null;
		StartupServlet.cart = null;
		StartupServlet.cart = new Cart();
		response.sendRedirect("/sportshub");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
