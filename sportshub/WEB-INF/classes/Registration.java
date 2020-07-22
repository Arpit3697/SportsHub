import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;


public class Registration extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session =  request.getSession(false);
		User currentUser = null;
		if(session !=null) {
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

		String content = getLoginHTML();
		out.print(content);
		if(session !=null) {
			out.print(utility.printHtmlUserLeftNav(_dirPath + "LeftNav.html", currentUser));
		}else {
			out.print(utility.printHtml(_dirPath + "LeftNav.html"));
		}
		out.print(utility.printHtml(_dirPath + "Footer.html"));
	}

	private String getLoginHTML() {
		return "<div id='body'>"
				+ "<section id='content'>"
				+ "<div class='row'>"
				+ "<div class='col-md-3'></div>"
				+ "<div class='col-md-6'>"
				+ "<div class='container'>"
				+ "<form class='form-signin' method='get' action='/sportshub/RegisterUser'>"
				+ "<center><h2 class='form-signin-heading'>Registration Form</h2></center>"
				+ ""
				+ ""
				+ "<label for='fname' class='sr-only'>First Name</label>"
				 + "<input name='fname' type='text' id='fname' class='form-control'placeholder='First Name' required='' autofocus=''>"
				 + ""
				 + ""
				 + "<label for='lname' class='sr-only'>Last Name</label>"
				 + "<input name='lname' type='text' id='lname' class='form-control'placeholder='Last Name' required=''>"
				 + ""
				 + ""
				 + "<label for='inputEmail' class='sr-only'>Email address</label>"
				 + "<input name='email' type='email' id='inputEmail' class='form-control'placeholder='Email address' required=''>"
				 + ""
				 + ""
				 + "<label for='mno' class='sr-only'>Mobile Number</label>"
				 + "<input name='mno' type='text' id='mno' class='form-control'placeholder='Mobile Number' required='' >"
				 + ""
				 + ""
				 + "<label for='uid' class='sr-only'>User Id</label>"
				 + "<input name='uid' type='email' id='uid' class='form-control'placeholder='User Id' required=''>"
				 + ""
				 + ""
				 + "<label for='inputPassword' class='sr-only'>Password</label>"
				 + "<input name='pwd' type='password' id='inputPassword' class='form-control' placeholder='Password' required=''>"
				 + ""
				 + ""
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
				 + ""
				 + ""
				 + "<button class='btn btn-lg btn-primary btn-block' type='submit'>Register</button>"
				 + "</form>"
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
