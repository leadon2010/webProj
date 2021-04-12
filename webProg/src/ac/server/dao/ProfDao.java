package ac.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ac.server.dto.ProfDto;
import ac.server.dto.StudentDto;

public class ProfDao {

	private static ProfDao dao = new ProfDao();

	private ProfDao() {
	}

	// 싱글톤
	public static ProfDao getInstance() {
		return dao;
	}

	public void ProfInsert(Connection conn, ProfDto prof) throws SQLException {
		PreparedStatement pstmt = null;
		pstmt = conn.prepareStatement("insert into prof(id,prof_name,email,phone) values(?,?,?,?)");
		pstmt.setString(1, prof.getId());
		pstmt.setString(2, prof.getProf_name());
		pstmt.setString(3, prof.getEmail());
		pstmt.setString(4, prof.getPhone());
		pstmt.executeUpdate();
	}

	public ArrayList<ProfDto> ProfSearch(Connection conn, String id) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<ProfDto> list = new ArrayList<>();
		ProfDto prof= null;

		pstmt = conn.prepareStatement("select * from prof where id=?");
		pstmt.setString(1, id);
		rs = pstmt.executeQuery();
		if (rs.next()) {
			prof= new ProfDto();
			prof.setId(rs.getString("id"));
			prof.setProf_name(rs.getString("prof_name"));
			prof.setEmail(rs.getString("email"));
			prof.setPhone(rs.getString("phone"));
			list.add(prof);
		}
		return list;
	}

	public void ProfUpdate(Connection conn, ProfDto prof) throws SQLException {
		PreparedStatement pstmt = null;

		pstmt = conn.prepareStatement("update prof set prof_name =?,email=?,phone=? where id=?");
		pstmt.setString(4, prof.getId());
		pstmt.setString(1, prof.getProf_name());
		pstmt.setString(2, prof.getEmail());
		pstmt.setString(3, prof.getPhone());
		pstmt.executeUpdate();
	}

	public void ProfDelete(Connection conn, String id) throws SQLException {
		PreparedStatement pstmt = null;

		pstmt = conn.prepareStatement("delete from prof where id=?");
		pstmt.setString(1, id);
		pstmt.executeUpdate();

	}

	public ArrayList<ProfDto> profList(Connection conn) throws SQLException{

		ArrayList<ProfDto> list = new ArrayList<ProfDto>();

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		ProfDto prof = null;

			pstmt = conn.prepareStatement("select * from prof");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				prof = new ProfDto();
				prof.setId(rs.getString("id"));
				prof.setProf_name(rs.getString("prof_name"));
				prof.setEmail(rs.getString("email"));
				prof.setPhone(rs.getString("phone"));
				list.add(prof);
			}

		return list;
	}
}