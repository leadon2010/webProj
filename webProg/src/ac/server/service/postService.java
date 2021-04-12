package ac.server.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import ac.server.dao.PostDao;
import ac.server.dbconnection.DbConnection;
import ac.server.dto.PostDto;


public class postService {

public PostDao dao = PostDao.getInstance();
	
	private static postService service = new postService();
	
	public static postService getInstance() {
		return service;
	}
	
	public ArrayList<PostDto> selectAll(int num) throws Exception {
		Connection conn = DbConnection.getConnection();
		ArrayList<PostDto> list = null;
		try {
			list = dao.PostList(conn,num);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnection.close(conn);
		}

		return list;
	}
	public void PostInsert(PostDto dto) throws Exception {
		Connection conn = DbConnection.getConnection();
		try {
			conn.setAutoCommit(false);
			dao.PostInsert(conn, dto);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbConnection.close(conn);
		}
	}
	
	public PostDto PostSelectOne(int id) throws Exception {
		Connection conn = DbConnection.getConnection();
		PostDto dto = null;
		try {
			dto = dao.PostSelect(conn, id);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnection.close(conn);
		}
		return dto;
	}
	public void PostUpdate(PostDto dto) throws Exception {
		Connection conn = DbConnection.getConnection();
		try {
			dao.PostUpdate(conn, dto);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbConnection.close(conn);
		}
	}
	public void PostDelete(int id) throws Exception {
		Connection conn = DbConnection.getConnection();
		try {
			dao.PostDelete(conn, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbConnection.close(conn);
		}
	}
	
	public ArrayList<PostDto> selectList() throws Exception {
		Connection conn = DbConnection.getConnection();
		ArrayList<PostDto> list = null;
		try {
			list = dao.PostListMain(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnection.close(conn);
		}

		return list;
	}
}
