package ac.server.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ac.server.dto.ProfDto;
import ac.server.service.ProfService;

public class ProfListController implements Controller {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ProfService service = ProfService.getInstance();
		ArrayList<ProfDto> profList = null;
		try {
			profList = service.ProfList();
			request.setAttribute("list", profList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 결과페이지 리턴
		return "/member/prof.jsp";
	}
}
