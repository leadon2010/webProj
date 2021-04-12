package ac.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ac.server.dto.MenuDto;

public class MenuDao {

	private static MenuDao Menudao = new MenuDao();

	public static MenuDao getInstance() {
		return Menudao;
	}		
		//게시판 전체 조회
			public ArrayList<MenuDto> MenuList(Connection conn) throws SQLException{

				ArrayList<MenuDto> list = new ArrayList<MenuDto>();

				PreparedStatement pstmt = null;
				ResultSet rs = null;

				MenuDto Menu = null;

					pstmt = conn.prepareStatement("select menu_num, menu_title from menu order by menu_num");
					rs = pstmt.executeQuery();
					while (rs.next()) {
						Menu = new MenuDto();
						Menu.setMenu_num(rs.getInt(1));
						Menu.setMenu_title(rs.getString(2));
						list.add(Menu);
					}
				return list;
			}
		public void MenuInsert(Connection conn, MenuDto dto) throws SQLException {
			PreparedStatement pstmt = null;
			pstmt = conn.prepareStatement("insert into menu(menu_num, menu_title) values (?,?)");
			pstmt.setInt(1, dto.getMenu_num());
			pstmt.setString(2, dto.getMenu_title());
			pstmt.executeUpdate();
		}
		
		public void MenuUpdate(Connection conn, MenuDto dto) throws SQLException {
			PreparedStatement pstmt = null;
			pstmt = conn.prepareStatement("Update menu set menu_title=? where menu_num=?");
			pstmt.setString(1, dto.getMenu_title());
			pstmt.setInt(2, dto.getMenu_num());
			pstmt.executeUpdate();
		}
		
		public void MenuDelete(Connection conn, int id) throws SQLException{
			PreparedStatement pstmt = null;
			pstmt = conn.prepareStatement("delete from menu where menu_num=?");
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		}
}
