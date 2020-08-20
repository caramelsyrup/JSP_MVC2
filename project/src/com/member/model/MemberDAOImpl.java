package com.member.model;

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
	
	// 객체 생성 메소드
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
	
	// 회원 가입 메소드
	@Override
	public void memberInsert(MemberDTO vo) {
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
			pstmt.setInt(5, vo.getUserTel());
			pstmt.setInt(6, vo.getAdmin());
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(con, pstmt);
		}
	}
	
	// 멤버 목록 보기 메소드
	@Override
	public ArrayList<MemberDTO> memberList() {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		ArrayList<MemberDTO> arr = new ArrayList<MemberDTO>();
		
		try {
			con = getConnection();
			String sql = "SELECT * FROM member ORDER BY admin DESC";
			st = con.createStatement();
			rs = st.executeQuery(sql);
			
			while(rs.next()) {
				MemberDTO dto  = new MemberDTO();
				dto.setAdmin(rs.getInt("admin"));
				dto.setUserEmail(rs.getString("useremail"));
				dto.setUserID(rs.getString("userid"));
				dto.setUserName(rs.getString("username"));
				dto.setUserPwd(rs.getString("userpwd"));
				dto.setUserTel(rs.getInt("usertel"));
				arr.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(con, st, rs);
		}
		return arr;
	}
	
	// 멤버 정보 수정 메소드
	@Override
	public int memberUpdate(MemberDTO vo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int flag =0;
		 try {
			con = getConnection();
			String sql = "UPDATE member SET username=?,useremail=?,usertel=?,admin=? WHERE userid=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getUserName());
			pstmt.setString(2, vo.getUserEmail());
			pstmt.setInt(3, vo.getUserTel());
			pstmt.setInt(4, vo.getAdmin());
			pstmt.setString(5, vo.getUserID());
			flag = pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(con, pstmt);
		}
		return flag;		
	}
	
	// 멤버 정보 상세 보기 메소드
	@Override
	public MemberDTO memberDetail(String userID) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		MemberDTO dto = null;
		try {
			con = getConnection();
			String sql = "SELECT * FROM member WHERE userid = '"+userID+"'";
			st = con.createStatement();
			rs = st.executeQuery(sql);

			if(rs.next()) {
				dto = new MemberDTO();
				dto.setAdmin(rs.getInt("admin"));
				dto.setUserEmail(rs.getString("useremail"));
				dto.setUserID(rs.getString("userid"));
				dto.setUserName(rs.getString("username"));
				dto.setUserPwd(rs.getString("userpwd"));
				dto.setUserTel(rs.getInt("usertel"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}
	
	// 멤버 삭제 메소드
	@Override
	public void memberDelete(String userID) {
		Connection con = null;
		Statement st = null;
		
		try {
			con = getConnection();
			String sql = "DELETE FROM member WHERE userid = '"+userID+"'";
			st = con.createStatement();
			st.executeUpdate(sql);
			con.commit();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	finally {
			closeConnection(con, st, null);
		}
		
	}
	
	// id중복 체크 메소드
	@Override
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
	
	// 로그인 판단 메소드
	@Override
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
					int flag = rs.getInt("admin");	// DB에 0또는 1로 저장이 되어 있음.
					return flag;
				}else {
					return 2;	// userID는 있으나 userPwd 불일치.
				}
			}else {
				return -1;	// rs가  없음 . userID가 없음.
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(con, st, rs);
		}
		return -2;	// DB오류
	}
	
	// 가입된 멤버 개수 세기 메소드
	@Override
	public int memberCount() {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		int count = 0;
		try {
			con = getConnection();
			String sql = "SELECT COUNT(userid) FROM member";
			st = con.createStatement();
			rs = st.executeQuery(sql);
			if(rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(con, st, rs);
		}
		return count;
	}
	

}
