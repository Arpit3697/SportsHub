import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class CartServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String src = request.getHeader("Referer");

		HttpSession session =  request.getSession(false);
		Cart cart =StartupServlet.cart;

		String requestedForm = request.getParameter("requestedForm");
		//Cart cart = StartupServlet.cart;
		if (requestedForm.equalsIgnoreCase("AddToCart")) {
			String addToCarteventid = request.getParameter("cartProduct");
			int eventid = Integer.parseInt(addToCarteventid);
//			Product p = SaxParserReader.fetchProductById(eventid);
			Product p = MySqlDataStoreUtilities.fetchProductByeventid(eventid);
			if (p != null) {
				cart.addToCart(p);

				if(session !=null) {
					session.setAttribute("cart",cart);
					cart = (Cart) session.getAttribute("cart");
				}
				response.sendRedirect("/sportshub/CartWithCarousel?eventid="+eventid);
			}else{
				response.sendRedirect(src);
		}
		} else if (requestedForm.equalsIgnoreCase("RemoveFromCart")) {
			String removeFromCarteventid = request.getParameter("cartProduct");
			int eventid = Integer.parseInt(removeFromCarteventid);
//			Product p = SaxParserReader.fetchProductById(eventid);
			Product p = MySqlDataStoreUtilities.fetchProductByeventid(eventid);
			if (p != null) {
				cart.removeFromCart(p);
				if(session !=null) {
					session.setAttribute("cart",cart);
					cart = (Cart) session.getAttribute("cart");
				}
			}
			response.sendRedirect(src);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
