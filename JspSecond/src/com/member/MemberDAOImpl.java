package com.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class MemberDAOImpl implements MemberDAO {
	private static MemberDAOImpl instance = new MemberDAOImpl();
	
	public static MemberDAOImpl getinstance() {
		return instance;
	}
	
	private Connection getConnection()throws Exception{
		Context initCtx = new InitialContext();
		Context envCtx = (Context) initCtx.lookup("java:comp/env");
		DataSource ds = (DataSource) envCtx.lookup("jdbc/member");
		return ds.getConnection();
	}
	
	// 연결 닫기 메소드
	public void closeConnection(Connection con,PreparedStatement pstmt) {
			try {
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	public void closeConnection(Connection con,Statement st,ResultSet rs) {
		try {
			if(rs!=null) rs.close();
			if(st != null) st.close();
			if(con != null) con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
}
	
	@Override	// 신규회원가입
	public void memberInsert(MemberVO vo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = getConnection();
			String sql = "INSERT INTO member VALUES (?,?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getUserID());
			pstmt.setString(2, vo.getUserName());
			pstmt.setString(3, vo.getUserPwd());
			pstmt.setString(4, vo.getUserEmail());
			pstmt.setString(5, vo.getUserTel());
			pstmt.setInt(6, vo.getAdmin());
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(con, pstmt);
		}
	}

	@Override	// 전체보기
	public ArrayList<MemberVO> memberList() {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		ArrayList<MemberVO> array = new ArrayList<MemberVO>();
		
		try {
			con = getConnection();
			String sql = "SELECT * FROM member";
			st = con.createStatement();
			rs = st.executeQuery(sql);
			
			while(rs.next()) {
				MemberVO vo = new MemberVO();
				vo.setUserID(rs.getString("userid"));
				vo.setUserName(rs.getString("username"));
				vo.setUserPwd(rs.getString("userpwd"));
				vo.setUserEmail(rs.getString("userEmail"));
				vo.setUserTel(rs.getString("userTel"));
				vo.setAdmin(rs.getInt("admin"));
				array.add(vo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return array;
	}

	@Override	// 멤버 정보 변경.
	public int memberUpdate(MemberVO vo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		 try {
			con = getConnection();
			String sql = "UPDATE member SET username=?,useremail=?,usertel=?,admin=? WHERE userid=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getUserName());
			pstmt.setString(2, vo.getUserEmail());
			pstmt.setString(3, vo.getUserTel());
			pstmt.setInt(4, vo.getAdmin());
			pstmt.setString(5, vo.getUserID());
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(con, pstmt);
		}
		return 0;
		
	}

	@Override	// 멤버 정보 조회
	public MemberVO memberDetail(String userID) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		MemberVO vo = null;
		try {
			con = getConnection();
			String sql = "SELECT * FROM member WHERE userID = '"+userID+"'";
			st = con.createStatement();
			rs = st.executeQuery(sql);
		
			while(rs.next()) {
				vo = new MemberVO();
				vo.setUserID(rs.getString("userid"));
				vo.setUserName(rs.getString("username"));
				vo.setUserPwd(rs.getString("userpwd"));
				vo.setUserEmail(rs.getString("useremail"));
				vo.setUserTel(rs.getString("usertel"));
				vo.setAdmin(rs.getInt("admin"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vo;
	}

	@Override	// 삭제기능
	public void memberDelete(String userID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = getConnection();
			String sql = "DELETE FROM member WHERE userID ='"+userID+"'";
			pstmt = con.prepareStatement(sql);
			pstmt.executeUpdate(sql);
			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(con, pstmt);
		}
	}

	@Override	// 회원가입, 아이디 중복 검사
	public String idCheck(String userID) {
		Connection con =null;
		Statement st = null;
		ResultSet rs = null;
		String flag = "yes";
		
		try {
			con = getConnection();
			String sql = "SELECT * FROM member WHERE userID ='"+userID+"'";
			st = con.createStatement();
			rs = st.executeQuery(sql);
			// id가 있으면 값이 조회가 되기 때문에, no를 반환하여 아이디를 못쓰게 만들어야한다.
			if(rs.next()) {
				return flag = "no";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(con, st, rs);
		}
		return flag;
	}

	
	@Override // 로그인 아이디 비밀번호 판단.
	public int loginCheck(String userID, String userPwd) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			con = getConnection();
			String sql = "SELECT userPwd, admin FROM member WHERE userID = '"+userID+"'";
			st = con.createStatement();
			rs = st.executeQuery(sql);
			
			if(rs.next()) {
				if(rs.getString("userPwd").equals(userPwd)) {	// String의 비교는 equals
					int flag = rs.getInt("admin");
					return flag;
				}else {
					return 2;	// userID는 있으나 userPwd가 없음.
				}
			}else {
				return -1;	// rs가  없음 . userID가 없음.
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(con, st, rs);
		}
		return -2;
	}

	@Override	// 회원수 찾기 메소드
	public int memberCount() {
		Connection con = null;
		Statement st	= null;
		ResultSet rs	= null;
		int count	=	0;
		try {
			con = getConnection();
			String sql = "SELECT COUNT(USERID) FROM member";
			st = con.createStatement();
			rs = st.executeQuery(sql);
			if(rs.next())
			count = rs.getInt(1);
			return count;
			
		} catch (Exception e) {
			e.printStackTrace();
		}	finally {
			closeConnection(con, st, rs);
		}
		return count;
	}
	
	

}
