import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class StartupServlet extends HttpServlet {
	public static Cart cart;
	public void init(ServletConfig config) throws ServletException {
/*SaxParserReader spr = new SaxParserReader(
				 "C:/apache-tomcat-7.0.34/webapps/AS1"
				+ "/webdata/ProductCatalogNew.xml");
*///		spr.parseDocument();
		
		MySqlDataStoreUtilities msdsu = new MySqlDataStoreUtilities();
		msdsu.prepareCatalog();
		cart = new Cart();
	}
}