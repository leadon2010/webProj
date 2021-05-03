package fileupload;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@WebServlet("/fileListServlet")
public class FileListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FileListServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");

		FileDAO dao = new FileDAO();
		List<FileVO> list = dao.selectAll();
		JSONArray ary = new JSONArray();

		for (FileVO vo : list) {
			JSONObject obj = new JSONObject();
			obj.put("num", vo.getNum());
			obj.put("author", vo.getAuthor());
			obj.put("title", vo.getTitle());
			obj.put("day", vo.getDay());
			obj.put("fileName", vo.getFile());
			ary.add(obj);
		}

		response.getWriter().print(ary);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
