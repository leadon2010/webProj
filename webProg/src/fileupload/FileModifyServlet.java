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

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@WebServlet("/fileModifyServlet")
public class FileModifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FileModifyServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();

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
			String num = multi.getParameter("num");

			Enumeration enu = multi.getFileNames();
			while (enu.hasMoreElements()) {
				String name = (String) enu.nextElement();
				String fileName = multi.getFilesystemName(name);
				System.out.println("name: " + name + ", fileName: " + fileName);
				fileN = fileName;

			}

			FileDAO dao = new FileDAO();
			FileVO arg = new FileVO();
			arg.setNum(Integer.parseInt(num));
			arg.setTitle(title);
			arg.setFile(fileN);
			arg.setAuthor(author);

			dao.modifyFile(arg);

		}
	}

}
