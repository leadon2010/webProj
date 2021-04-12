package dataTable;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import employee.SEmpDAO;
import employee.SEmployee;

/**
 * Servlet implementation class EmpServlet
 */
@WebServlet("/EmpServ")
public class EmpServ extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EmpServ() {
		super();
	}

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		response.getWriter().append("Served at: ").append(request.getContextPath());

		SEmpDAO dao = new SEmpDAO();
		List<SEmployee> list = dao.getEmplsList();
		JSONArray ary = null;
		JSONArray orig = new JSONArray();
		JSONObject obj = new JSONObject();
		for (SEmployee emp : list) {
			System.out.println(emp.getFirstName());
			ary = new JSONArray();
			ary.add(emp.getFirstName());
			ary.add(emp.getLastName());
			ary.add(emp.getJobId());
			ary.add(emp.getEmail());
			ary.add(emp.getHireDate());
			ary.add(emp.getSalary());
			orig.add(ary);
		}
		obj.put("data", orig);
		response.getWriter().print(obj.toString());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
