
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
// import com.mysql.fabric.xmlrpc.base.Array;

public class InventoryChart extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		MySqlDataStoreUtilities msdsu = new MySqlDataStoreUtilities();

		HashMap<String, ArrayList<Inventory>> inventory = msdsu.getInventory();

		Gson gson = new Gson();
		String type = request.getParameter("type");
	//	inventory = msdsu.filterInventoryByType(inventory, type);
		response.setContentType("application/json");

		PrintWriter out = response.getWriter();
		for (Map.Entry<String, ArrayList<Inventory>> data : inventory.entrySet()) {
			if (!data.getKey().equalsIgnoreCase("ERROR")) {
				out.print(gson.toJson(data.getValue()));

			}
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
