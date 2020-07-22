import java.util.HashMap;
import java.util.Map;

public class AjaxUtility {

	public StringBuffer readdata(String searchId) {
		StringBuffer sb = new StringBuffer();
		MySqlDataStoreUtilities msdsu = new MySqlDataStoreUtilities();

		HashMap<Integer, String> products = msdsu.getAjaxData(searchId);

		for (Map.Entry<Integer, String> product : products.entrySet()) {
			int id = product.getKey();
			String name = product.getValue();
			sb.append("<product>");
			sb.append("<id>" + id + "</id>");
			sb.append("<productName>" + name + "</productName>");
			sb.append("</product>");
		}

		return sb;
	}

}
