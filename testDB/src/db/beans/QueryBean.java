package db.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class QueryBean {
	// 연결 객체
	Connection conn;
	// 질의 객체
	Statement stmt;
	// 결과 객체
	ResultSet rs;
	// 생성자는 객체 모두 null로 초기화
	public QueryBean() {
		conn = null;
		stmt = null;
		rs = null;
	}
	// 연결메소드
	public void getConnection() {
		// DB에 연결하는 메소드 실행.
		try {
			conn = DBConnection.getConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// sql문장을 적을 객체.
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// 연결 종료
	public void closeConnection() {
		if(stmt!=null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(conn!=null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	public ArrayList getUserInfo() throws Exception {
		// 스트링 버퍼를 이용. 문장의 가변길이에 대응.
		StringBuffer sb = new StringBuffer();
		// 객체에 해당 문자열을 추가
		sb.append("SELECT ");
		sb.append("U_ID,U_NAME,U_PHONE,U_GRADE,WRITE_TIME ");
		sb.append(" FROM ");
		sb.append(" USER_INFO_SAMPLE ");
		sb.append(" ORDER BY ");
		sb.append(" WRITE_TIME DESC");
		// rs에 스트링버퍼객체를 스트링으로 바꾸고 쿼리실행.
		rs = stmt.executeQuery(sb.toString());
		
		ArrayList res = new ArrayList();
		while(rs.next()) {
			res.add(rs.getString("U_ID"));
			res.add(rs.getString(2));
			res.add(rs.getString(3));
			res.add(rs.getString(4));
			res.add(rs.getString(5));
		}
		System.out.println(sb.toString());
		return res;
	}
	
	public int setUserInfo(String id,String name,String phone,String grade) {
		int result =0;
		StringBuffer sb = new StringBuffer();
		PreparedStatement pstmt = null;
		
		sb.append("INSERT INTO ");
		sb.append("	USER_INFO_SAMPLE (U_ID,U_NAME,U_PHONE,U_GRADE,WRITE_TIME)	");
		sb.append("	VALUES	");
		sb.append("	('"+id+"', '"+name+"', '"+phone+"', '"+grade+"', sysdate)");
		
		System.out.println(sb.toString());
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
					if(pstmt!=null) {
						pstmt.close();
					}
				} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
			}
		return result;
	}
	
	public int deleteUserInfo(String id) {
		
		int result=0;
		PreparedStatement pstmt= null;
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(" DELETE ");
		sb.append("		FROM ");
		sb.append("			USER_INFO_SAMPLE");
		sb.append("		WHERE ");
		sb.append("			U_ID = ? ");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.clearParameters();
			pstmt.setString(1, id);
			
			result = pstmt.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				if(pstmt!=null) {
					pstmt.close();
				}
			}catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return result;
	}
	
	public int updateUserInfo(String id,String name,String phone,String grade) {
		int result =0;
		StringBuffer sb = new StringBuffer();
		PreparedStatement pstmt = null;
		
		sb.append("UPDATE USER_INFO_SAMPLE ");
		sb.append(" SET ");
		sb.append(" U_NAME='"+name+"', U_PHONE='"+phone+"', U_GRADE='"+grade+"', WRITE_TIME = sysdate");
		sb.append(" WHERE U_ID ='"+id+"'");
		
		System.out.println(sb.toString());
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
					if(pstmt!=null) {
						pstmt.close();
					}
				} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
			}
		return result;
	}
	
	public ArrayList searchUserInfo(String id) {
		
		ArrayList res = new ArrayList();
		StringBuffer sb = new StringBuffer();
		
		sb.append(" SELECT ");
		sb.append("	U_ID,U_NAME,U_PHONE,U_GRADE,WRITE_TIME ");
		sb.append("	FROM ");
		sb.append("	USER_INFO_SAMPLE ");
		sb.append("	WHERE ");
		sb.append("	U_ID LIKE '%"+id+"%' ");
		
			try {
				rs = stmt.executeQuery(sb.toString());
				
				while(rs.next()) {
					res.add(rs.getString(1));
					res.add(rs.getString(2));
					res.add(rs.getString(3));
					res.add(rs.getString(4));
					res.add(rs.getString(5));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				closeConnection();
			}
			
		System.out.println(sb.toString());
		return res;
	}
	
}
