package com.guest.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class GuestDAO {
	//������
	private static GuestDAO instance = new GuestDAO();
	public static GuestDAO getinstance() {
		return instance;
	}
	
	private Connection getConnection()throws Exception{
		Context initCtx = new InitialContext();
		Context envCtx = (Context) initCtx.lookup("java:comp/env");
		DataSource ds = (DataSource) envCtx.lookup("jdbc/guest");
		return ds.getConnection();
	}
	// ���� �ݱ�
	public void closeConnection(Connection con, PreparedStatement pstmt) {
			try {
				if(pstmt!=null)
					pstmt.close();
				if(con!=null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	public void closeConnection(Connection con, Statement st, ResultSet rs) {
		try {
			if(rs!=null)
				rs.close();
			if(st!=null)
				st.close();
			if(con!=null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// �߰�
	public void guestInsert(GuestDTO guest) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = getConnection();
			String sql = "INSERT INTO guestbook VALUES (guestbook_seq.nextval,?,?,?,sysdate,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, guest.getWritter());
			pstmt.setString(2, guest.getContent());
			pstmt.setString(3, guest.getGrade());
			pstmt.setString(4, guest.getIpaddr());
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(con, pstmt);
		}
	}
	
	// ��ü ����
	public ArrayList<GuestDTO>guestList(){
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		ArrayList<GuestDTO>arr = new ArrayList<GuestDTO>();
		try {
			con = getConnection();
			String sql = "SELECT * FROM guestbook ORDER BY num DESC";
			st = con.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				GuestDTO guest = new GuestDTO();
				guest.setNum(rs.getInt("num"));
				guest.setWritter(rs.getString("writter"));
				guest.setContent(rs.getString("content"));
				guest.setGrade(rs.getString("grade"));
				guest.setCreated(rs.getString("created"));
				guest.setIpaddr(rs.getString("ipaddr"));
				arr.add(guest);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(con, st, rs);
		}
		return arr;
	}
	
	// �� ����
	public int guestCount(String field, String word) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		int count=0;
		String sql ="";
		try {
			con = getConnection();
			st = con.createStatement();
			if(word.equals("")) {
				sql = "SELECT COUNT(num) FROM guestbook";
			}else {
				sql = "SELECT COUNT(num) FROM guestbook WHERE "+field+" LIKE '%"+word+"%'";
			}
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
	
	// �˻���� �߰��� ����¡.
	public ArrayList<GuestDTO> guestList(String field, String word, int startRow, int endRow) {
		Connection con = null;
		Statement st = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<GuestDTO> arr = new ArrayList<GuestDTO>();
		
		try {
			con = getConnection();

			StringBuilder sb = new StringBuilder();
			sb.append("select * from");
			sb.append(" (select aa.*, rownum rn from");
			sb.append(" (select * from guestbook where "+field+" like '%"+word+"%'");
			sb.append("order by num desc) aa");
			sb.append(" where rownum<=? ) where rn>=?");
			
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setInt(1, endRow);
			pstmt.setInt(2, startRow);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				GuestDTO guest = new GuestDTO();
				guest.setNum(rs.getInt("num"));
				guest.setWritter(rs.getString("writter"));
				guest.setContent(rs.getString("content"));
				guest.setGrade(rs.getString("grade"));
				guest.setCreated(rs.getString("created"));
				guest.setIpaddr(rs.getString("ipaddr"));
				arr.add(guest);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(con, st, rs);
		}
		return arr;
	}
	
	// ��ü���� ����¡
	public ArrayList<GuestDTO> guestList(int startRow, int endRow) {
		Connection con = null;
		Statement st = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<GuestDTO> arr = new ArrayList<GuestDTO>();
		
		try {
			con = getConnection();

			StringBuilder sb = new StringBuilder();
			// 1. ��� ���� ��ȸ �ض�. 2�� ������ ���� ����, �׸��� 4���� ���ȣ�� ���۹�ȣ���� ũ�ų� ���� ���� �Ͽ���
			sb.append("select * from");
			// 2. guestbook�� ��� �����ͷ� ���� ���ȣ(rownum)�� abc�� ��� �����͸� ��ȸ�ϴµ�, 4��  ���ȣ�� ����ȣ���� �۰ų� ���� ������ �����ϴ� ���
			sb.append(" (select abc.*, rownum rn from");
			// 3. guestbook�� ��� �����͸� ��ȸ�ϴµ�, �̰���  abc�� ���
			sb.append(" (select * from guestbook order by num desc) abc");
			// 4. ���ȣ�� ����ȣ���ٴ� �۰ų� ���ƾ��Ѵ�. �׸��� ���ȣ�� ���۹�ȣ���ٴ� ũ�ų� ���ƾ��Ѵ�.
			sb.append(" where rownum<=? ) where rn>=?");
			
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setInt(1, endRow);
			pstmt.setInt(2, startRow);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				GuestDTO guest = new GuestDTO();
				guest.setNum(rs.getInt("num"));
				guest.setWritter(rs.getString("writter"));
				guest.setContent(rs.getString("content"));
				guest.setGrade(rs.getString("grade"));
				guest.setCreated(rs.getString("created"));
				guest.setIpaddr(rs.getString("ipaddr"));
				arr.add(guest);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(con, st, rs);
		}
		return arr;
	}
	
	
	// �󼼺���
	public GuestDTO guestView(int num){
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		GuestDTO guest = null;
		
		try {
			con = getConnection();
			String sql = "SELECT * FROM guestbook WHERE num ="+num;
			st = con.createStatement();
			rs = st.executeQuery(sql);
			
			if(rs.next()) {
				guest =  new GuestDTO();
				guest.setNum(num);
				guest.setWritter(rs.getString("writter"));
				guest.setContent(rs.getString("content"));
				guest.setGrade(rs.getString("grade"));
				guest.setCreated(rs.getString("created"));
				guest.setIpaddr(rs.getString("ipaddr"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(con, st, rs);
		}
		return guest;
	}
	
	//���� ���.
	public void guestDelete(int num) {
		Connection con = null;
		Statement st = null;
		
		try {
			con = getConnection();
			st = con.createStatement();
			String sql = "DELETE FROM guestbook WHERE num = "+num;
			st.executeQuery(sql);
			con.commit();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection(con, st, null);
		}
	}
	
	// �α��� �Ǵ�
	public int guestLoginCheck(String id, String pw) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		int flag =-1;	// �ʱ�, id,pw ��� ���� ����.
		
		try {
			con = getConnection();
			st = con.createStatement();
			sql = "SELECT userpwd FROM member WHERE userid = '"+id+"'";
			rs = st.executeQuery(sql);
			
			if(rs.next()) {	// id�� ������.
				if(rs.getString("userpwd").equals(pw)) {	// pw�� �����ϴ°�?
					 return 1;	// ȸ��
				}else {
					 return 0; // ��� �ȸ���
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(con, st, rs);
		}
		return flag;
	}
	
	
}
