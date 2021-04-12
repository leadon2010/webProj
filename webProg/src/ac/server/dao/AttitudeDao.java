package ac.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import ac.server.dto.AttitudeDto;
import ac.server.dto.StudentDto;

public class AttitudeDao {
	private static AttitudeDao dao = new AttitudeDao();

	private AttitudeDao() {
	}

	// 싱글톤
	public static AttitudeDao getInstance() {
		return dao;
	}

	public void AttitudeInsert(Connection conn, AttitudeDto attit) throws SQLException {
		PreparedStatement pstmt = null;
		pstmt = conn.prepareStatement("insert into attitude(id,name,day) values(?,?,?)");
		pstmt.setString(1, attit.getId());
		pstmt.setString(2, attit.getName());
		pstmt.setString(3, attit.getDay());
		pstmt.executeUpdate();
	}

	public AttitudeDto attitudeSearch(Connection conn, String id) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		AttitudeDto attit= null;

		pstmt = conn.prepareStatement("select * from attitude where id=?");
		pstmt.setString(1, id);
		rs = pstmt.executeQuery();
		if (rs.next()) {
			attit = new AttitudeDto();
			attit.setId(rs.getString("id"));
			attit.setDay(rs.getString("day"));
			attit.setName(rs.getString("name"));
		}
		return attit;
	}

	public void AttitudeDelete(Connection conn, String id) throws SQLException {
		PreparedStatement pstmt = null;

		pstmt = conn.prepareStatement("delete from attitude where id=?");
		pstmt.setString(1, id);
		pstmt.executeUpdate();

	}

	public List<Map<String,Object>> attitudeList(Connection conn){

		ArrayList<Map<String,Object>> list = new ArrayList<Map<String,Object>>();

		PreparedStatement pstmt = null;
		ResultSet rs = null;
try {
			pstmt = conn.prepareStatement(	"select attitude.id, name, \r\n" + 
					"					  sum(case to_char(day,'dd') when '01' then 1 else 0 end) as d1, \r\n" + 
					"					  sum(case to_char(day,'dd') when '02' then 1 else 0 end) as d2, \r\n" + 
					"					  sum(case to_char(day,'dd') when '03' then 1 else 0 end) as d3, \r\n" + 
					"					  sum(case to_char(day,'dd') when '04' then 1 else 0 end) as d4, \r\n" + 
					"					  sum(case to_char(day,'dd') when '05' then 1 else 0 end) as d5, \r\n" + 
					"					  sum(case to_char(day,'dd') when '06' then 1 else 0 end) as d6, \r\n" + 
					"					  sum(case to_char(day,'dd') when '07' then 1 else 0 end) as d7, \r\n" + 
					"					  sum(case to_char(day,'dd') when '08' then 1 else 0 end) as d8, \r\n" + 
					"					  sum(case to_char(day,'dd') when '09' then 1 else 0 end) as d9, \r\n" + 
					"					  sum(case to_char(day,'dd') when '10' then 1 else 0 end) as d10, \r\n" + 
					"					  sum(case to_char(day,'dd') when '11' then 1 else 0 end) as d11, \r\n" + 
					"					  sum(case to_char(day,'dd') when '12' then 1 else 0 end) as d12, \r\n" + 
					"					  sum(case to_char(day,'dd') when '13' then 1 else 0 end) as d13, \r\n" + 
					"					  sum(case to_char(day,'dd') when '14' then 1 else 0 end) as d14, \r\n" + 
					"					  sum(case to_char(day,'dd') when '15' then 1 else 0 end) as d15, \r\n" + 
					"					  sum(case to_char(day,'dd') when '16' then 1 else 0 end) as d16, \r\n" + 
					"					  sum(case to_char(day,'dd') when '17' then 1 else 0 end) as d17, \r\n" + 
					"					  sum(case to_char(day,'dd') when '18' then 1 else 0 end) as d18, \r\n" + 
					"					  sum(case to_char(day,'dd') when '19' then 1 else 0 end) as d19, \r\n" + 
					"					  sum(case to_char(day,'dd') when '20' then 1 else 0 end) as d20, \r\n" + 
					"					  sum(case to_char(day,'dd') when '21' then 1 else 0 end) as d21, \r\n" + 
					"					  sum(case to_char(day,'dd') when '22' then 1 else 0 end) as d22, \r\n" + 
					"					  sum(case to_char(day,'dd') when '23' then 1 else 0 end) as d23, \r\n" + 
					"					  sum(case to_char(day,'dd') when '24' then 1 else 0 end) as d24, \r\n" + 
					"					  sum(case to_char(day,'dd') when '25' then 1 else 0 end) as d25, \r\n" + 
					"					  sum(case to_char(day,'dd') when '26' then 1 else 0 end) as d26, \r\n" + 
					"					  sum(case to_char(day,'dd') when '27' then 1 else 0 end) as d27, \r\n" + 
					"					  sum(case to_char(day,'dd') when '28' then 1 else 0 end) as d28, \r\n" + 
					"					  sum(case to_char(day,'dd') when '29' then 1 else 0 end) as d29, \r\n" + 
					"					  sum(case to_char(day,'dd') when '30' then 1 else 0 end) as d30, \r\n" + 
					"					  sum(case to_char(day,'dd') when '31' then 1 else 0 end) as d31 \r\n" + 
					"					from attitude join student  on (student.id = attitude.id)\r\n" + 
					"					where attitude.id in(select s.id \r\n" + 
					"					            from course c left join student s    \r\n" + 
					"					            on(s.course_code = c.course_code)    \r\n" + 
					"					           where c.training_list between 100 and 199    \r\n" + 
					"					             and to_char( start_date, 'yyyymmdd' ) < to_char( sysdate, 'yyyymmdd') and 	to_char( end_date, 'yyyymmdd' ) > to_char( sysdate, 'yyyymmdd')\r\n" + 
					"                                 \r\n" + 
					"                                 ) \r\n" + 
					"					   and to_char(Day,'yyyymm')='201904'  \r\n" + 
					"					   group by attitude.id, name \r\n" + 
					"					   order by attitude.id");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Map<String,Object> attit = new HashMap<String,Object>();
				attit.put("id",rs.getString("id"));
				attit.put("D1",rs.getString("D1"));
				attit.put("D2",rs.getString("D2"));
				attit.put("D3",rs.getString("D3"));
				attit.put("D4",rs.getString("D4"));
				attit.put("D5",rs.getString("D5"));
				attit.put("D6",rs.getString("D6"));
				attit.put("D7",rs.getString("D7"));
				attit.put("D8",rs.getString("D8"));
				attit.put("D9",rs.getString("D9"));
				attit.put("D10",rs.getString("D10"));
				attit.put("D11",rs.getString("D11"));
				attit.put("D12",rs.getString("D12"));
				attit.put("D13",rs.getString("D13"));
				attit.put("D14",rs.getString("D14"));
				attit.put("D15",rs.getString("D15"));
				attit.put("D16",rs.getString("D16"));
				attit.put("D17",rs.getString("D17"));
				attit.put("D18",rs.getString("D18"));
				attit.put("D19",rs.getString("D19"));
				attit.put("D20",rs.getString("D20"));
				attit.put("D21",rs.getString("D21"));
				attit.put("D22",rs.getString("D22"));
				attit.put("D23",rs.getString("D23"));
				attit.put("D24",rs.getString("D24"));
				attit.put("D25",rs.getString("D25"));
				attit.put("D26",rs.getString("D26"));
				attit.put("D27",rs.getString("D27"));
				attit.put("D28",rs.getString("D28"));
				attit.put("D29",rs.getString("D29"));
				attit.put("D30",rs.getString("D30"));
				attit.put("D31",rs.getString("D31"));
				attit.put("name",rs.getString("name"));
				list.add(attit);
			}
}catch(Exception e) {
	e.printStackTrace();
}
		return list;
	}
}
