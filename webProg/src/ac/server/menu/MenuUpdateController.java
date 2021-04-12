package ac.server.menu;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ac.server.controller.Controller;
import ac.server.dto.MenuDto;
import ac.server.service.menuService;



public class MenuUpdateController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int menu_num = Integer.parseInt(request.getParameter("menunum"));
		String menu_title = request.getParameter("menutitle");
		
		PrintWriter out = response.getWriter();
		
		MenuDto menu = new MenuDto();
		
		try {
			menuService service = menuService.getInstance().MenuUpdate(menu);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		response.setContentType("text/html; charset=utf-8");
		out.print("<script>alert('메뉴 삽입 완료되었습니다! ' );");
		out.print("</script>");	
		return "/menu/menu.jsp";
	}
}