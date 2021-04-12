package product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.DbCon;

public class ProductDAO {
	Connection conn = DbCon.connect();
	Connection conn1 = DbCon.connect();
	ResultSet rs = null;
	PreparedStatement pstmt;
	PreparedStatement pstmt1;

	public int addLikeit(int prod_id) {
		String sql = "update products set likeit=likeit+1 where product_id=?";
		String gsql = "select nvl(likeit,0) likeit from products where product_id=?";
		int likeit = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, prod_id);
			int r = pstmt.executeUpdate();
			System.out.println(r + " 좋아요 추가.");

			pstmt1 = conn1.prepareStatement(gsql);
			pstmt1.setInt(1, prod_id);
			rs = pstmt1.executeQuery();
			if (rs.next()) {
				likeit = rs.getInt("likeit");
				System.out.println("systout : " + likeit);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return likeit;
	}

	// 4.update
	public void updateProd(Product prod) {
		String sql = "update products set product_name=?, product_price=?, product_img=?, product_cont=? where product_id=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, prod.getProductName());
			pstmt.setInt(2, prod.getProductPrice());
			pstmt.setString(3, prod.getProductImg());
			pstmt.setString(4, prod.getProductCont());
			pstmt.setInt(5, prod.getProductId());
			int r = pstmt.executeUpdate();
			System.out.println(r + " updated.");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	// 3.insert
	public void insertProd(Product prod) {
		String sql = "insert into products (product_id, product_name, product_price, product_cont, product_img, likeit) "
				+ " values((select nvl(max(product_id),0)+1 from products), ?, ?, ?, ?, 0) ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, prod.getProductName());
			pstmt.setInt(2, prod.getProductPrice());
			pstmt.setString(3, prod.getProductCont());
			pstmt.setString(4, prod.getProductImg());
			int r = pstmt.executeUpdate();
			System.out.println(r + " inserted.");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	// 2.getOne
	public Product getProdcutOne(int prod_id) {
		Product prod = null;

		String sql = "select product_id, product_name, product_price, product_cont, product_img, likeit from products where product_id=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, prod_id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				prod = new Product();
				prod.setProductId(rs.getInt("product_id"));
				prod.setProductName(rs.getString("product_name"));
				prod.setProductPrice(rs.getInt("product_price"));
				prod.setProductCont(rs.getString("product_cont"));
				prod.setProductImg(rs.getString("product_img"));
				prod.setLikeit(rs.getInt("likeit"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return prod;
	}

	// 1.list
	public List<Product> getProductList() {

		Product prod = null;
		List<Product> list = new ArrayList<>();

		String sql = "select product_id, product_name, product_price, product_cont, product_img, likeit from products order by 1 desc";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				prod = new Product();
				prod.setProductId(rs.getInt("product_id"));
				prod.setProductName(rs.getString("product_name"));
				prod.setProductPrice(rs.getInt("product_price"));
				prod.setProductCont(rs.getString("product_cont"));
				prod.setProductImg(rs.getString("product_img"));
				prod.setLikeit(rs.getInt("likeit"));
				list.add(prod);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}

}// end of class
