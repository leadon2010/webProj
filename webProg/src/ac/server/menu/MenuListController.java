package ac.server.menu;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ac.server.controller.Controller;
import ac.server.dao.BoardDao;
import ac.server.dao.PostDao;
import ac.server.dbconnection.DbConnection;
import ac.server.dto.BoardDto;
import ac.server.dto.MenuDto;
import ac.server.dto.PostDto;
import ac.server.service.menuService;

public class MenuListController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			ArrayList<MenuDto> menulist = menuService.getInstance().selectAll();
			request.getSession().setAttribute("menulist", menulist);
			String menu_num = request.getParameter("menu_num");

			if (menu_num == null || menu_num.equals("")) {
				menu_num = String.valueOf((menulist.get(0).getMenu_num()));
			}
			String menuName = "";

			for (int i = 0; i < menulist.size(); i++) {
				if (menulist.get(i).getMenu_num() == Integer.parseInt(menu_num)) {
					menuName = menulist.get(i).getMenu_title();
				}
			}
			request.setAttribute("menuname", menuName);
			
			Connection conn = DbConnection.getConnection();

			ArrayList<BoardDto> boardlist = BoardDao.getInstance().BoardList(conn, Integer.parseInt(menu_num));
			request.setAttribute("boardlist", boardlist);
			String board_num = request.getParameter("boardlist");

			if ((board_num == null || board_num.equals("") ) && boardlist.size()>0) {
				board_num = String.valueOf((boardlist.get(0).getBoard_num()));
			}
			if(board_num != null) {
			ArrayList<PostDto> postlist = PostDao.getInstance().PostList(conn, Integer.parseInt(board_num));
			request.setAttribute("postlist", postlist);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/board/board.jsp";
	}
}