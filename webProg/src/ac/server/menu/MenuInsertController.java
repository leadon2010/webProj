package ac.server.menu;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ac.server.controller.Controller;
import ac.server.dto.MenuDto;
import ac.server.service.menuService;



public class MenuInsertController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int menu_num = Integer.parseInt(request.getParameter("menunum"));
		String menu_title = request.getParameter("menutitle");
		System.out.println(menu_num + menu_title);
		
		
		MenuDto menu = new MenuDto();
		menu.setMenu_num(menu_num);
		menu.setMenu_title(menu_title);
		menuService service = null;
		
		try {
		service = menuService.getInstance().MenuInsert(menu);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.setContentType("text/html; charset=utf-8");
		return "redirect:/yedam/MenuSelect.do";
	}
}