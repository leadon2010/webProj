package ac.server.dao;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateUtil {
	/*
	 * 자바 util 데이트를 sql 데이트로 변환 -1-
	 * 자바 sql 데이트를 util 데이트로 변환 -2-
	 */
	public static java.sql.Date toSqlDate(java.util.Date utilDate) {
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		return sqlDate;
	}	
	public static java.util.Date toUtilDate(java.sql.Date sqlDate){
		java.util.Date utilDate = new java.util.Date(sqlDate.getTime());
		return utilDate;
	}
	// 문자열을 Date 타입으로 변환
	public static Date StringToDate(String str) {
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
		Date d=null;
		try {
			d = dt.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return d;
	}	
	//오늘날짜를 String으로 변환 yyyy-mm-dd 
	public static String tDateFormat() {
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
     	  Date currentTime = new Date();
     	  String str = sdf.format(currentTime);
     	  return str;
	}
	
}
