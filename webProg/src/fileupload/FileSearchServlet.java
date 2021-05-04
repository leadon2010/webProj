package fileupload;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

@WebServlet("/fileSearchServlet")
public class FileSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FileSearchServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		String num = request.getParameter("num");

		num = num == null ? "0" : num;

		FileDAO dao = new FileDAO();
		FileVO vo = dao.getSearch(Integer.parseInt(num));

		JSONObject obj = new JSONObject();
		obj.put("num", vo.getNum());
		obj.put("author", vo.getAuthor());
		obj.put("title", vo.getTitle());
		obj.put("day", vo.getDay());
		obj.put("fileName", vo.getFile());

		response.getWriter().print(obj);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
