package multiFile.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet("/fileprocess")
public class FileUpLoad extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FileUpLoad() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		boolean isMultypart = ServletFileUpload.isMultipartContent(request);
		DiskFileItemFactory factory = new DiskFileItemFactory();

		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");

		try {
			List<FileItem> items = upload.parseRequest(request);
			Iterator<FileItem> iter = items.iterator();

			while (iter.hasNext()) {
				FileItem item = iter.next();
				String fileName = null;
				if (!item.isFormField()) {
					fileName = item.getName();
					out.println(fileName);
				}

				ServletContext cxt = getServletContext();
				String dir = cxt.getRealPath("/");
				File uploadedFile = new File(dir + "/uploadfile/" + fileName);
				item.write(uploadedFile);
			}
		} catch (Exception e) {
			e.printStackTrace(out);
		}
		out.println("<a href=\"fileio.jsp\">파일 업로드 폼으로</a>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
