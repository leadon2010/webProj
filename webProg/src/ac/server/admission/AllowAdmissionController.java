package ac.server.admission;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ac.server.controller.Controller;
import ac.server.dto.CourseDto;
import ac.server.dto.StudentDto;
import ac.server.service.StudentService;
import net.sf.json.JSONObject;

public class AllowAdmissionController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		StudentDto student = new StudentDto();
		try {
			String admission = request.getParameter("adm");
			String id = request.getParameter("id");
			
			student.setAdmission(admission);
			student.setId(id);
			student = StudentService.getInstance().AllowUpdate(student);
			
			} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "ajax:"+JSONObject.fromObject(student);
	}

}
