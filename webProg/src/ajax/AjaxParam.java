package ajax;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Servlet implementation class AjaxParam
 */
@WebServlet("/AjaxParam")
public class AjaxParam extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AjaxParam() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		Map<String, String[]> map = request.getParameterMap();
//		String res = getMap(map, request, response).toString();
		String res = getArgs(map);
		System.out.println(res);

		response.getWriter().println(res);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public String getArgs(Map<String, String[]> args) {
		String result = "[{";
		Set<Map.Entry<String, String[]>> set = args.entrySet();
		Iterator<Map.Entry<String, String[]>> iter = set.iterator();
		while (iter.hasNext()) {
			Map.Entry<String, String[]> ent = iter.next();
			result += "\"" + ent.getKey() + "\":[";
			for (int i = 0; i < ent.getValue().length; i++) {
				result += "\"" + ent.getValue()[i] + "\"" + (i == ent.getValue().length - 1 ? "" : ",");
			}
			result += "]";
			if (iter.hasNext()) {
				result += ",";
			}
		}
		result += "}]";

		return result;
	}

	@SuppressWarnings("unchecked")
	public JSONArray getMap(Map<String, String[]> map, HttpServletRequest req, HttpServletResponse res) {
		JSONObject obj = new JSONObject();
		JSONArray ary = new JSONArray();

		map.forEach((k, v) -> {
			String[] args = req.getParameterValues(k);
			JSONArray inner = new JSONArray();
			for (int i = 0; i < args.length; i++) {
				inner.add(args[i]);
			}
			obj.put(k, inner);
		});
		ary.add(obj);
		System.out.println(ary);

//		map.forEach(new BiConsumer<String, String[]>() {
//
//			@Override
//			public void accept(String t, String[] u) {
//				String[] args = req.getParameterValues(t);
//				JSONArray inner = new JSONArray();
//				for (String arg : args) {
//					inner.add(arg);
//				}
//				obj.put(t, inner);
//			}
//		});
//		ary.add(obj);

		return ary;
	}

}
