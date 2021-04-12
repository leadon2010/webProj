package google;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import employee.EmpDAO;

@WebServlet("/GetEmployeePerDeptServ")
public class GetEmployeePerDeptServ extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GetEmployeePerDeptServ() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String json = "[";
		EmpDAO dao = new EmpDAO();
		Map<String, Integer> map = dao.getEmployeePerDept();
		Set<Map.Entry<String, Integer>> set = map.entrySet();
		int cnt = 0;
		int dataLenght = set.size();
		for (Map.Entry<String, Integer> ent : set) {
			json += "{\"" + ent.getKey() + "\":\"" + ent.getValue() + "\"}";
			cnt++;
			if(cnt != dataLenght) {
				json += ",";
			}
		}
		json += "]";
		response.getWriter().append(json);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
