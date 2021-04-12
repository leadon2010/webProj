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



public class MenuSelectController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<MenuDto> menulist = null;
		try {
			menulist = menuService.getInstance().selectAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.setContentType("text/html; charset=utf-8");
		request.setAttribute("menu", menulist);
		return "/menu/menu.jsp";
	}
}