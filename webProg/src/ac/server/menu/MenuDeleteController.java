package ac.server.menu;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ac.server.controller.Controller;
import ac.server.dto.MenuDto;
import ac.server.service.menuService;



public class MenuDeleteController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int menu_num = Integer.parseInt(request.getParameter("menu_num"));
		
		PrintWriter out = response.getWriter();
		
		MenuDto menu = new MenuDto();
		
		try {
			menuService service = menuService.getInstance().MenuDelete(menu_num);;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "redirect:/yedam/MenuSelect.do";
	}
}