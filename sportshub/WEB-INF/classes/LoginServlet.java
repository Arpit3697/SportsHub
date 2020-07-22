import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LoginServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String _dirPath = getServletContext().getRealPath("/");
		PrintWriter out = response.getWriter();
		Utilities utility = new Utilities(out);
		out.print(utility.printHtml(_dirPath + "Header.html"));
		String content = getLoginHTML();
		out.print(content);
		out.print(utility.printHtml(_dirPath + "LeftNav.html"));
		out.print(utility.printHtml(_dirPath + "Footer.html"));
	}

	private String getLoginHTML() {
		return "<div id='body'>"
					+ "<section id='content'>"
					+ "<div class='row'>"
					+ "<div class='col-md-3'></div>"
					+ "<div class='col-md-6'>"
					+ "<div class='container'>"
					+ "<form class='form-signin' method='get' action='/sportshub/ValidateUser'>"
					+ "<center><h2 class='form-signin-heading'>Please Sign in</h2></center>"
				 	+ "<label for='inputEmail' class='sr-only'>Email address</label>"
				 	+ "<input name='uname' type='email' id='inputEmail' class='form-control'placeholder='Email address' required='' autofocus=''>"
				 	+ "<label for='inputPassword' class='sr-only'>Password</label>"
				 	+ "<input name='pwd' type='password' id='inputPassword' class='form-control' placeholder='Password' required=''>"
				 	+ "<div class='form-group'>"
				 	+ "<label for='role' class='sr-only'>Role</label>"
				 	+ "<div class='col-xs-5 selectContainer'>"
				 	+ "<select class='form-control' name='role' id='role'>"
				 	+ "<option value=''>Choose a role</option>"
				 	+ "<option value='0'>Customer</option>"
				 	+ "<option value='1'>Store Manager</option>"
				 // + "<option value='2'>Salesman</option>"
				 + "</select>"
				 + "</div>"
				 + "</div>"
				 + "<button class='btn btn-lg btn-primary btn-block' type='submit'>Sign in</button>"
				 + "</form>"
				 + "<center><h5>New User? <a href='/sportshub/Registration'>Register Here</a></h5></center>"
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
