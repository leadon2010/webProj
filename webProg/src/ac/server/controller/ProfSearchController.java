package ac.server.controller;

import java.util.ArrayList;

import javax.servlet.http.*;

import ac.server.dto.ProfDto;
import ac.server.service.ProfService;

public class ProfSearchController implements Controller {
	public String execute(HttpServletRequest request, HttpServletResponse response) {

		// Parameter 추출
		String id = request.getParameter("id");
		String path = null;
		path = "/member/prof.jsp";

		// 유효성 체크
		if (id.isEmpty()) {
			request.setAttribute("error", "ID를 입력해주시기 바랍니다!");
			// HttpUtil.forward(request, response, path);
			return path;
		}

		// Service 객체의 메서드 호출
		ProfService service = ProfService.getInstance();
		ArrayList<ProfDto> prof;
		try {
			prof = service.ProfSearch(id);
			if (prof == null)
				request.setAttribute("result", "검색된 정보가 없습니다!");
			request.setAttribute("list", prof);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Output View 페이지로 이동
		return path;
	}
}
