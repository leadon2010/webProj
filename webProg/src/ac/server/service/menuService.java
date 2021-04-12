package ac.server.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import ac.server.dao.MenuDao;
import ac.server.dbconnection.DbConnection;
import ac.server.dto.MenuDto;

/**
 * Servlet implementation class menuservice
 */
@WebServlet("/menuservice")
public class menuService extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public MenuDao dao = MenuDao.getInstance();
    
	private static menuService service = new menuService();
	
	public static menuService getInstance() {
		return service;
	}
	
	public ArrayList<MenuDto> selectAll() throws Exception {
		Connection conn = DbConnection.getConnection();
		ArrayList<MenuDto> list = null;
		try {
			list = dao.MenuList(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnection.close(conn);
		}

		return list;
	}
	
	public menuService MenuInsert(MenuDto dto) throws Exception {
		Connection conn = DbConnection.getConnection();
		try {
			conn.setAutoCommit(false);
			dao.MenuInsert(conn, dto);					
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbConnection.close(conn);
		}
		return null;
	}

	public menuService MenuUpdate(MenuDto dto) throws Exception {
		Connection conn = DbConnection.getConnection();
		try {
			conn.setAutoCommit(false);
			dao.MenuUpdate(conn, dto);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbConnection.close(conn);
		}
		return null;
	}

	public menuService MenuDelete(int id) throws Exception {
		Connection conn = DbConnection.getConnection();
		try {
			dao.MenuDelete(conn, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbConnection.close(conn);
		}
		return null;
	}

}
