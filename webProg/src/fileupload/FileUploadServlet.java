package fileupload;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.simple.JSONObject;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@WebServlet("/fileUploadServlet")
public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FileUploadServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		Enumeration<String> en = request.getHeaderNames();
		while (en.hasMoreElements()) {
			String key = en.nextElement();
			String val = request.getHeader(key);
			System.out.println(key + " : " + val + "\n");
		}

		// ....
		ServletContext context = getServletContext(); // 어플리케이션에 대한 정보를 ServletContext 객체가 갖게 됨.
		String saveDir = context.getRealPath("Upload"); // "c:/tmp";
		int size = 9 * 1024 * 1024;
		MultipartRequest multi = new MultipartRequest(request, saveDir, size, "UTF-8", new DefaultFileRenamePolicy());
		boolean isMulti = ServletFileUpload.isMultipartContent(request);// boolean타입. ?????? multipart타입인지 체크후...
		if (isMulti) {
//			String num = multi.getParameter("num");
			String author = multi.getParameter("author");
			String title = multi.getParameter("title");
			String fileN = multi.getParameter("fileName");

			Enumeration enu = multi.getFileNames();
			while (enu.hasMoreElements()) {
				String name = (String) enu.nextElement();
				String fileName = multi.getFilesystemName(name);
				System.out.println("name: " + name + ", fileName: " + fileName);
				fileN = fileName;

			}

			FileDAO dao = new FileDAO();
			FileVO vo = dao.getInsertKeyVal(author, title, fileN);

			JSONObject obj = new JSONObject();
			obj.put("num", vo.getNum());
			obj.put("author", vo.getAuthor());
			obj.put("title", vo.getTitle());
			obj.put("day", vo.getDay());
			obj.put("fileName", vo.getFile());

//			out.println(new String(fileName.getBytes("UTF-8"), "UTF-8") + "<br>");
			out.println(obj);
		}
	}

}
