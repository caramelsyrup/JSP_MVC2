package db.beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
	public static Connection getConnection() throws Exception {
		System.out.println("DB연결시도");
		Class.forName("oracle.jdbc.driver.OracleDriver");
		return DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","scott","1234");
		
	}
	
	public static Connection geConnection(String ip, int port, String db, String user,String pw) throws Exception {
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		return DriverManager.getConnection("jdbc:oracle:thin:@"+ip+":"+port+":"+db,user,pw);
		
	}
}