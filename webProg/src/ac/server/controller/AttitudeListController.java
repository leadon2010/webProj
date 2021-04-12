package ac.server.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ac.server.dto.AttitudeDto;
import ac.server.service.AttitudeService;

public class AttitudeListController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AttitudeService service = AttitudeService.getInstance();
		List<Map<String, Object>> attitList = null;
		try {
			attitList = service.attitudeList();
			request.setAttribute("list", attitList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 결과페이지 리턴
		return "/member/attit.jsp";
	}
}
