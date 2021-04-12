package fullCalendar;

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

@WebServlet("/GetScheduleServlet")
public class GetScheduleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GetScheduleServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		CalendarDAO dao = new CalendarDAO();
		List<DataTable> list = dao.getSchedules();
		JSONArray ary = new JSONArray();

		for (DataTable data : list) {
			JSONObject obj = new JSONObject();
			obj.put("title", data.getTitle());
			obj.put("start", data.getStartDate());
			obj.put("end", data.getEndDate());
			ary.add(obj);
		}
		out.print(ary.toString());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
