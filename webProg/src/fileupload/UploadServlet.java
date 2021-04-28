package fileupload;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@WebServlet("/uploadServlet")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UploadServlet() {
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
			out.println(key + " : " + val + "<br>");
		}

		String saveDir = "c:/tmp";
		int size = 5 * 1024 * 1024;

		MultipartRequest multi = new MultipartRequest(request, saveDir, size, "UTF-8", new DefaultFileRenamePolicy());

		out.println(new String(multi.getParameter("name").getBytes("UTF-8"), "UTF-8") + "<br>");

		for (Enumeration em = multi.getFileNames(); em.hasMoreElements();) {
			String strName = (String) em.nextElement();
			String fileName = multi.getFilesystemName(strName);
			System.out.println("strName: " + strName + ", fileName: " + fileName);
			out.println(new String(fileName.getBytes("UTF-8"), "UTF-8") + "<br>");
		}

	}

}
