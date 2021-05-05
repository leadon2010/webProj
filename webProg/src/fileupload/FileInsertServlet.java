package fileupload;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

@WebServlet("/fileInsertServlet")
public class FileInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FileInsertServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String author = request.getParameter("author");
		String title = request.getParameter("title");
		String file = request.getParameter("file");

		System.out.println(author + "," + title + "," + file);

		FileDAO dao = new FileDAO();
		FileVO vo = dao.getInsertKeyVal(author, title, file);

		JSONObject obj = new JSONObject();
		obj.put("num", vo.getNum());
		obj.put("author", vo.getAuthor());
		obj.put("title", vo.getTitle());
		obj.put("day", vo.getDay());
		obj.put("fileName", vo.getFile());

		response.getWriter().print(obj);

	}

}
