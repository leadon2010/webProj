package dept;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@WebServlet("/GetDepartmentsServlet")
public class GetDepartmentsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GetDepartmentsServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		DeptDAO dao = new DeptDAO();
		List<Departments> list = dao.getDeptList();
		JSONArray ary = new JSONArray();
		for (Departments dept : list) {
			JSONObject obj = new JSONObject();
			obj.put("departmentId", dept.getDepartmentId());
			obj.put("departmentDesc", dept.getDepartmentName());
			obj.put("managerId", dept.getManagerId());
			obj.put("locationId", dept.getLocationId());
			ary.add(obj);
		}
		PrintWriter out = response.getWriter();
		out.print(ary.toString());

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
