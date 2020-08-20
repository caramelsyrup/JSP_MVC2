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
	
	// ��ü ���� �޼ҵ�
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
	
	// ���� �ݱ� �޼ҵ�
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
	
	// ȸ�� ���� �޼ҵ�
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
	
	// ��� ��� ���� �޼ҵ�
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
	
	// ��� ���� ���� �޼ҵ�
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
	
	// ��� ���� �� ���� �޼ҵ�
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
	
	// ��� ���� �޼ҵ�
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
	
	// id�ߺ� üũ �޼ҵ�
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
			// id�� ������ ���� ��ȸ�� �Ǳ� ������, no�� ��ȯ�Ͽ� ���̵� ������ �������Ѵ�.
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
	
	// �α��� �Ǵ� �޼ҵ�
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
				if(rs.getString("userPwd").equals(userPwd)) {	// String�� �񱳴� equals
					int flag = rs.getInt("admin");	// DB�� 0�Ǵ� 1�� ������ �Ǿ� ����.
					return flag;
				}else {
					return 2;	// userID�� ������ userPwd ����ġ.
				}
			}else {
				return -1;	// rs��  ���� . userID�� ����.
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(con, st, rs);
		}
		return -2;	// DB����
	}
	
	// ���Ե� ��� ���� ���� �޼ҵ�
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
