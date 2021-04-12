package ac.server.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ac.server.edu.EndProcessController;
import ac.server.edu.ProcessController;
import ac.server.edu.RecruitController;
import ac.server.login.LoginController;
import ac.server.login.LogoutController;
import ac.server.main.ServerMainController;
import ac.server.menu.MenuInsertController;
import ac.server.menu.MenuListController;
import ac.server.menu.MenuSelectController;
import ac.server.menu.MenuUpdateController;
import ac.server.post.PostDeleteController;
import ac.server.post.PostInsertController;
import ac.server.post.PostListController;
import ac.server.post.PostUpdateController;
import ac.server.post.PostUpdateFormController;
import ac.server.admission.AdmissionController;
import ac.server.admission.AllowAdmissionController;
import ac.server.admission.AllowConfirmController;
import ac.server.admission.AllowController;
import ac.server.category.CourseUpdateController;
import ac.server.category.CourseUpdateformController;
import ac.server.category.CoursekindinsertController;
import ac.server.category.ManagerInsController;
import ac.server.category.ManagerInsinsertController;
import ac.server.category.ManagerSearchController;
import ac.server.category.TraKindselectController;
import ac.server.category.TraininginsertController;
import ac.server.category.TrakinddeleteController;
import ac.server.category.TrakindupdateController;

@WebServlet("*.do")
public class FrontController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	String charset = null;
	HashMap<String, Controller> list = null;

	@Override
	public void init(ServletConfig sc) throws ServletException {
		list = new HashMap<String, Controller>();
		list.put("/MainNotice.do", new ServerMainController());// 메인페이지

		list.put("/RecruitSelect.do", new RecruitController());// 교육관리 - 모집중과정 페이지
		list.put("/ProcessSelect.do", new ProcessController());// 교육관리 - 진행중과정 페이지
		list.put("/EndProcessSelect.do", new EndProcessController());// 교육관리 - 종료(수료) 페이지

		list.put("/TrakindSelect.do", new TraKindselectController());// 교육과정관리 - 훈련분야구분 페이지
		list.put("/TrainingInsert.do", new TraininginsertController());// 교육과정관리 - 훈련분야구분 - 훈련분류등록
		list.put("/CourseKindInsert.do", new CoursekindinsertController());// 교육과정관리 - 훈련분야구분 - 교육분류등록
		list.put("/TrakindUpdate.do", new TrakindupdateController());// 교육과정관리 - 훈련분야구분 - 훈련/교육분류등록
		list.put("/TrainingDelete.do", new TrakinddeleteController());// 교육과정관리 - 훈련분야구분 - 훈련/교육분류삭제
		list.put("/ManagetIns.do", new ManagerInsController());// 교육과정관리 - 교육과정입력 페이지
		list.put("/ManagerInsInsert.do", new ManagerInsinsertController());//
		list.put("/ManagerSearch.do", new ManagerSearchController());// 교육과정관리 - 교육과정관리 페이지
		list.put("/CourseUpdate.do", new CourseUpdateController());
		list.put("/CourseUpdateform.do", new CourseUpdateformController());

		list.put("/StudentList.do", new StudentListController());
		list.put("/ProfList.do", new ProfListController());
		list.put("/StudentDelete.do", new StudentDeleteController());
		list.put("/ProfDelete.do", new ProfDeleteController());
		list.put("/StudentInsert.do", new StudentInsertController());
		list.put("/ProfInsert.do", new ProfInsertController());
		list.put("/LoginCheck.do", new LoginController());
		list.put("/Login.do", new LoginController());
		list.put("/Logout.do", new LogoutController());
		list.put("/StudentUpdate.do", new StudentUpdateController());
		list.put("/StudentUpdateform.do", new StudentUpdateFormController());

		list.put("/PostList.do", new PostListController());
		list.put("/PostInsert.do", new PostInsertController());
		list.put("/PostInsertTemp.do", new PostInsertTempController());
		list.put("/PostDelete.do", new PostDeleteController());
		list.put("/PostUpdateform.do", new PostUpdateFormController());
		list.put("/PostUpdate.do", new PostUpdateController());
		list.put("/PostSelect.do", new PostSelectController());
		list.put("/MenuList.do", new MenuListController());
		list.put("/Admission.do", new AdmissionController());
		list.put("/Allow.do", new AllowController());
		list.put("/AllowCofirm.do", new AllowConfirmController());
		list.put("/AllowAdmission.do", new AllowAdmissionController());
		list.put("/MenuInsert.do", new MenuInsertController());
		list.put("/MenuUpdate.do", new MenuUpdateController());
		list.put("/MenuSelect.do", new MenuSelectController());
		list.put("/StudentSearch.do", new StudentSearchController());
		list.put("/ProfSearch.do", new ProfSearchController());
		list.put("/AttitudeList.do", new AttitudeListController());
		list.put("/ProfUpdate.do", new ProfUpdateController());
		list.put("/ProfUpdateform.do", new ProfUpdateFormController());
	}

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		// uri : /dev/memberList.do
		String url = request.getRequestURI();
		// /dev
		String contextPath = request.getContextPath();
		// = > /memberList.do
		String path = url.substring(contextPath.length());
		System.out.println(path);

		// 컨트롤러 찾기
		Controller subController = list.get(path); // ex) MemberListController
		System.out.println(subController);
		// 컨트롤러 실행 ,다형성(실행결과가 다 다름), 참조하는 자식객체의 메서드를 호출
		// subController.execute(request, response);
		String view = null;
		try {
			view = subController.execute(request, response);
			System.out.println(view);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (view != null) {
			if (view.startsWith("redirect:")) {
				response.sendRedirect(view.substring(9));
			} else if (view.startsWith("ajax:")) {
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.print(view.substring(5));
			} else {
				HttpUtil.forward(request, response, view);
			}
		}
	}
}