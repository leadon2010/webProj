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

/**
 * Servlet implementation class GetDepartmentsServlet
 */
@WebServlet("/GetDepartmentsServlet")
public class GetDepartmentsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetDepartmentsServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
