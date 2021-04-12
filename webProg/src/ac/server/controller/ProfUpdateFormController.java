package ac.server.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ac.server.dto.LoginDto;
import ac.server.dto.ProfDto;
import ac.server.service.ProfService;

public class ProfUpdateFormController implements Controller {
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Parameter 추출
		String id = request.getParameter("id");
		// 서비스(db처리)
		ProfService service = ProfService.getInstance();
		ArrayList<ProfDto> prof = service.ProfSearch(id);
		// DeptBeans beans = DeptService.getInstance().getDept(department_id);
		// 결과를 저장
		request.setAttribute("prof", prof.get(0));
		// forward될 페이지명을 리턴
		return "member/prof_update.jsp";
	}
}
