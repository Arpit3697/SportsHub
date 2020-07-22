import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class RegisterUser extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session =  request.getSession(false);
		User currentUser = null;
		if(session !=null) {
			currentUser = (User) session.getAttribute("user");
		}

		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String email = request.getParameter("email");
		String mno = request.getParameter("mno");
		String uid = request.getParameter("uid");
		String pwd = request.getParameter("pwd");
		int role = Integer.parseInt(request.getParameter("role"));

		User user = new User(uid, pwd, fname, lname, email, mno, role);
	//	SerializeDataStoreManager.writeUserInfo(user);
		MySqlDataStoreUtilities msdsq = new MySqlDataStoreUtilities();
		int resultcode = msdsq.writeUserInfo(user);

		response.setContentType("text/html");
		String _dirPath = getServletContext().getRealPath("/");

		PrintWriter out = response.getWriter();
		Utilities utility = new Utilities(out);
		if (session != null) {

			out.print(utility.printHtmlUserHeader(_dirPath + "Header.html", currentUser));
		} else {
			out.print(utility.printHtml(_dirPath + "Header.html"));
		}
		if(resultcode >0 ) {
		out.print(
				"<div id='body'>" + "<section id='content'>" + "<h1>User Registered....</h1>" + "</section>" + "</div>");
		}else {
			out.print(
					"<div id='body'>" + "<section id='content'>" + "<h1>Mysql is not running. Registration unsuccessful.</h1>" + "</section>" + "</div>");
		}
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
