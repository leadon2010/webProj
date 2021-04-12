package ac.server.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ac.server.dto.LoginDto;
import ac.server.dto.ProfDto;
import ac.server.service.ProfService;

public class ProfInsertController implements Controller {
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Parameter 추출
		String id = request.getParameter("id");
		String prof_name = request.getParameter("prof_name");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");

		String pw = request.getParameter("pw");

		// 유효성 체크
		if (id.isEmpty() || prof_name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
			request.setAttribute("error", "모든 항목을 빠짐없이 입력해주시기 바랍니다!");
			// HttpUtil.forward(request, response, "/memberInsert.jsp");
			return "/prof_Insert.jsp";
		}

		// VO객체에 데이타 바인딩
		ProfDto prof = new ProfDto();
		prof.setId(id);
		prof.setProf_name(prof_name);
		prof.setEmail(email);
		prof.setPhone(phone);

		LoginDto login = new LoginDto();
		login.setId(id);
		login.setPw(pw);

		// Service 객체의 메서드 호출
		ProfService service = ProfService.getInstance();
		try {
			service.ProfInsert(prof, login);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Output View 페이지로 이동
		request.setAttribute("id", id);
		// HttpUtil.forward(request, response, "/result/memberInsertOutput.jsp");
		return "redirect:ProfList.do";
	}
}
