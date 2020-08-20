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
	//디비셋팅
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
	// 연결 닫기
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
	
	// 추가
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
	
	// 전체 보기
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
	
	// 수 세기
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
	
	// 검색기능 추가된 페이징.
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
	
	// 전체보기 페이징
	public ArrayList<GuestDTO> guestList(int startRow, int endRow) {
		Connection con = null;
		Statement st = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<GuestDTO> arr = new ArrayList<GuestDTO>();
		
		try {
			con = getConnection();

			StringBuilder sb = new StringBuilder();
			// 1. 모든 것을 조회 해라. 2가 충족된 조건 하의, 그리고 4에서 행번호가 시작번호보다 크거나 같은 조건 하에서
			sb.append("select * from");
			// 2. guestbook의 모든 데이터로 부터 행번호(rownum)과 abc의 모든 데이터를 조회하는데, 4의  행번호가 끝번호보다 작거나 같은 조건을 충족하는 경우
			sb.append(" (select abc.*, rownum rn from");
			// 3. guestbook의 모든 데이터를 조회하는데, 이것을  abc로 명명
			sb.append(" (select * from guestbook order by num desc) abc");
			// 4. 행번호가 끝번호보다는 작거나 같아야한다. 그리고 행번호가 시작번호보다는 크거나 같아야한다.
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
	
	
	// 상세보기
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
	
	//삭제 기능.
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
	
	// 로그인 판단
	public int guestLoginCheck(String id, String pw) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		int flag =-1;	// 초기, id,pw 모두 값이 없음.
		
		try {
			con = getConnection();
			st = con.createStatement();
			sql = "SELECT userpwd FROM member WHERE userid = '"+id+"'";
			rs = st.executeQuery(sql);
			
			if(rs.next()) {	// id는 존재함.
				if(rs.getString("userpwd").equals(pw)) {	// pw도 존재하는가?
					 return 1;	// 회원
				}else {
					 return 0; // 비번 안맞음
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
