package com.btpns.rtw.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.btpns.rtw.Wisma;

public class DbUtil {
	public boolean isConnect(String host,String port,String dbName,String userName,String password) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = null;
			conn = DriverManager.getConnection("jdbc:mysql://" +
					host + ":" + port + 
					"/" + dbName,userName, password);
			conn.close();
			
			return true;
		}catch (Exception ex) {
			return false;
		}
	}
	public List<Wisma> getWisma(String host,String port,String dbName,String userName
			,String password,String search,List<String> schemaSelected) throws Exception {
		 List<Wisma> wismas = new ArrayList<Wisma>();
		 
	 Class.forName("com.mysql.jdbc.Driver");
		Connection conn = null;
		conn = DriverManager.getConnection("jdbc:mysql://" +
				host + ":" + port + 
				"/" + dbName,userName, password);
	 Statement stmt = null;
	    String query =
	        "select schema_name,a.office_code,b.display_name,file_date from consolidation.data_restore_history a " +
	        "left join prospera.office b on a.office_code = b.office_code where status='NEW' " +
	        "AND (a.office_code like '%" + search.trim() + "%' or b.display_name LIKE '%" + search.trim() + "%') " +
	        "order by office_code;";

	    try {
	        stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        while (rs.next()) {
	        	String schemaName = rs.getString("schema_name");
	           wismas.add(new Wisma(schemaName,rs.getString("office_code")
	        		   ,  rs.getString("display_name"),  rs.getDate("file_date")
	        		   ,(schemaSelected.contains(schemaName))?true:false));
	        }
	    } catch (Exception e) {
	    	throw new SQLException("Can't connect mysql, please check your setting database !!!");
	    } finally {
	        if (stmt != null)
	        	stmt.close();
	        if (conn != null)
	        	conn.close();
	    }
		 
		 return wismas;
		
	}
}
