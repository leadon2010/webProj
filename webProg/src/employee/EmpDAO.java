package employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.DbCon;

public class EmpDAO {
	PreparedStatement pstmt = null;
	Connection conn = null;

	public Map<String, Integer> getEmployeePerDept() {
		conn = DbCon.connect();
		String sql = "SELECT d.department_name,COUNT(*) AS cnt FROM   employees   e,departments d "
				+ "WHERE  e.department_id = d.department_id GROUP  BY d.department_name";
		Map<String, Integer> map = new HashMap<>();
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				map.put(rs.getString("department_name"), rs.getInt("cnt"));
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
		return map;
	}

	public Map<String, String> getJobCode() {
		conn = DbCon.connect();
		Map<String, String> map = new HashMap<>();
		String sql = "select * from jobs";
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				map.put(rs.getString("job_id"), rs.getString("job_title"));
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

		return map;
	}

	public void addEmployee(Employee emp) {
		conn = DbCon.connect();
		String sql = "insert into emp (employee_id, first_name, salary, email) values (employees_seq.nextval,?,?,?) ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, emp.getFirstName());
			pstmt.setInt(2, emp.getSalary());
			pstmt.setString(3, emp.getEmail());
			int r = pstmt.executeUpdate();
			System.out.println(r + "건 입력됨.");
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

	public void updateEmployee(Employee emp) {
		conn = DbCon.connect();
		String sql = "update emp set salary = ? where employee_id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, emp.getSalary());
			pstmt.setString(2, emp.getEmployeeId());
			int r = pstmt.executeUpdate();
			System.out.println(r + " 건이 변경되었습니다.");

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

	public void insertEmployee(Employee emp) {
		conn = DbCon.connect();
		String sql = "insert into employees(employee_id, last_name, email, hire_date, job_id, first_name)"
				+ " values(employees_seq.nextval, ?,?,?,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, emp.getLastName());
			pstmt.setString(2, emp.getEmail());
			pstmt.setString(3, emp.getHireDate());
			pstmt.setString(4, emp.getJobId());
			pstmt.setString(5, emp.getFirstName());

			int r = pstmt.executeUpdate();
			System.out.println(r + " 건이 입력되었습니다.");

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

	public List<Map<String, Object>> getData() {
		conn = DbCon.connect();
		String str = "select * from ajaxsample";
		Map<String, Object> map;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			pstmt = conn.prepareStatement(str);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				map = new HashMap<String, Object>();
				map.put("fullName", rs.getString("full_name"));
				map.put("position", rs.getString("position"));
				map.put("office", rs.getString("office"));
				map.put("extn", rs.getString("extn"));
				map.put("startDate", rs.getString("start_date"));
				map.put("salary", rs.getString("salary"));
				list.add(map);
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

	public List<Map<String, Object>> getSampleData() {
		conn = DbCon.connect();
		Map<String, Object> map = new HashMap<>();
		List<Map<String, Object>> list = new ArrayList<>();
		String str = "select * from ajaxsample";
		try {
			pstmt = conn.prepareStatement(str);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getString("full_name"));
				map = new HashMap<>();
				map.put("fullName", rs.getString("full_name"));
				map.put("position", rs.getString("position"));
				map.put("office", rs.getString("office"));
				map.put("extn", rs.getString("extn"));
				map.put("startDate", rs.getString("start_date"));
				map.put("salary", rs.getString("salary"));
				list.add(map);
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

	public void insertSample(String a, String b, String c, String d, String e, String f) {
		conn = DbCon.connect();
		String str = "insert into ajaxsample values(?,?,?,?,?,?)";
		try {
			int r = 0;
			pstmt = conn.prepareStatement(str);
			pstmt.setString(++r, a);
			pstmt.setString(++r, b);
			pstmt.setString(++r, c);
			pstmt.setString(++r, d);
			pstmt.setString(++r, e);
			pstmt.setString(++r, f);

			int cnt = pstmt.executeUpdate();
			System.out.println(cnt + " row inserted.");
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	public List<String> getEmailList() {
		conn = DbCon.connect();
		String str = "select email from employees";
		List<String> list = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(str);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(rs.getString("email"));
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

	public List<String> getNamesList() {
		conn = DbCon.connect();
		List<String> nameList = new ArrayList<>();
		String sql = "select first_name from employees where rownum < 20";
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				nameList.add(rs.getString("first_name"));
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
		return nameList;
	}

	public String getUserInfo(String id, String pw) {
		conn = DbCon.connect();
		String sql = "select * from member where userid = ? and userpw = ?";
		String returnInfo = "";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				returnInfo += rs.getString("username");
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
		System.out.println(returnInfo);
		if (returnInfo.equals(""))
			returnInfo = "no";
		return returnInfo;

	}

	public String delEmployee(String id) {
		conn = DbCon.connect();
		int r = 0;
		try {
			pstmt = conn.prepareStatement("delete from emp where employee_id = " + id);
			r = pstmt.executeUpdate();
			System.out.println(r + " 건이 삭제되었습니다.");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (r > 0)
			return "success";
		else
			return null;
	}

	public List<Employee> getEmplsList() {
		conn = DbCon.connect();
		List<Employee> list = new ArrayList<>();
		Employee emp;
		String sql = "select * from emp order by employee_id desc";
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				emp = new Employee();
				emp.setFirstName(rs.getString("first_name"));
				emp.setLastName(rs.getString("last_name"));
				emp.setEmployeeId(rs.getString("employee_id"));
				emp.setSalary(rs.getInt("salary"));
				emp.setHireDate(rs.getString("hire_date"));
				list.add(emp);
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

	public List<Employee> getEmpList(String name) {
		conn = DbCon.connect();
		Employee emp;
		List<Employee> list = new ArrayList<>();

		String sql = "select first_name, last_name, salary from employees where first_name like '%'||'" + name
				+ "'||'%'";
		try {
			pstmt = conn.prepareStatement(sql);
			System.out.println(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				emp = new Employee();
				emp.setFirstName(rs.getString("first_name"));
				emp.setLastName(rs.getString("last_name"));
				emp.setSalary(rs.getInt("salary"));
				list.add(emp);
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

	public List<Map<String, Object>> getEmpPerDept() {
		conn = DbCon.connect();
		Map<String, Object> map;
		List<Map<String, Object>> list = new ArrayList<>();
		String sql = "SELECT d.department_name ,COUNT(*) cnt FROM employees e JOIN departments d ON e.department_id = d.department_id "
				+ "WHERE  e.department_id IS NOT NULL GROUP  BY d.department_name";
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				map = new HashMap<String, Object>();
				map.put(rs.getString("department_name"), rs.getInt("cnt"));
				list.add(map);
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
}
