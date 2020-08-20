package com.board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BoardDAO {
	// 디비 세팅
	private static BoardDAO instance = new BoardDAO();
	
	public static BoardDAO getinstance() {
		return instance;
	}
	
	private Connection getConnection()throws Exception{
		Context initCtx = new InitialContext();
		Context envCtx = (Context) initCtx.lookup("java:comp/env");
		DataSource ds = (DataSource) envCtx.lookup("jdbc/member");
		return ds.getConnection();
	}
	// 닫기 메소드
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
	
	public void closeConnection(Connection con, Statement st) {
		try {
			if(st != null) st.close();
			if(con != null) con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	//추가
	public void boardInsert(BoardVO board) {	// 새글과 답글 구분
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		//부모글. db에서 받아 옴.
		int num = board.getNum();
		int ref = board.getRef();
		int re_step = board.getRe_step();
		int re_level = board.getRe_level();
		
		int number = 0;
		
		try {
			con = getConnection();
			pstmt = con.prepareStatement("SELECT MAX(num) FROM board");
			rs = pstmt.executeQuery();
			
			if(rs.next()) {	// 기존데이터가 있을때 ref를 최대값+1로 결정. number를 ref에 저장시킬것임.
				number = rs.getInt(1)+1;
			}else {	// 기존데이터가 없을때 ref를 1로 결정
				number = 1;
			}
			if(num!=0) {	// 답글
				sql = "UPDATE board SET re_step=re_step+1 WHERE ref=? and re_step>? ";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, ref);
				pstmt.setInt(2, re_step);
				pstmt.executeUpdate();
				re_step = re_step +1;
				re_level = re_level+1;
				
			}else {	// 새글
				ref = number;
				re_step=0;
				re_level=0;
			}
			
			// 현재 페이지에서 아는 내용만 추가되도록 한다.
			// num, writer,subject,email,content,passwd,ip,readcount,ref,re_step,re_level
			sql = "INSERT INTO board(num,reg_date,writer,subject,email,content,passwd,ip,readcount,ref,re_step,re_level) "
					+ "VALUES(board_seq.nextval,SYSDATE,?,?,?,?,?,?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, board.getWriter());
			pstmt.setString(2, board.getSubject());
			pstmt.setString(3, board.getEmail());
			pstmt.setString(4, board.getContent());
			pstmt.setString(5, board.getPasswd());
			pstmt.setString(6, board.getIp());
			pstmt.setInt(7, board.getreadcount());
			pstmt.setInt(8, ref);
			pstmt.setInt(9, re_step);
			pstmt.setInt(10, re_level);
			pstmt.executeUpdate();
			con.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			closeConnection(con, pstmt);
		}
	}
	
	//전체보기
	public ArrayList<BoardVO>boardList(int startRow,int endRow){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<BoardVO> arr = new ArrayList<BoardVO>();
		try {
			con = getConnection();
			String sql = "SELECT * FROM (select rownum rn,aa.*from(select*from board order by ref desc,re_step asc)aa) where rn<=? and rn >=?";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, endRow);
			pstmt.setInt(2, startRow);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardVO board = new BoardVO();
				board.setNum(rs.getInt("num"));
				board.setSubject(rs.getString("subject"));
				board.setWriter(rs.getString("writer"));
				board.setReg_date(rs.getString("reg_date"));
				board.setreadcount(rs.getInt("readcount"));
				board.setIp(rs.getString("ip"));
				arr.add(board);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(con, pstmt, rs);
		}
		return arr;
	}
	
	//상세보기
	public BoardVO boardView(int num) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		BoardVO board = null;
		
		try {
			con = getConnection();
			st = con.createStatement();
			st.executeUpdate("UPDATE board SET readcount=readcount+1 where num="+num);
			
			String sql="SELECT * FROM board WHERE num="+num;
			rs = st.executeQuery(sql);
			
			if(rs.next()) {
				board = new BoardVO();
				board.setNum(rs.getInt("num"));
				board.setWriter(rs.getString("writer"));
				board.setContent(rs.getString("content"));
				board.setEmail(rs.getString("email"));
				board.setSubject(rs.getString("subject"));
				board.setIp(rs.getString("ip"));
				board.setPasswd(rs.getString("passwd"));
				board.setRe_level(rs.getInt("re_level"));
				board.setRe_step(rs.getInt("re_step"));
				board.setreadcount(rs.getInt("readcount"));
				board.setRef(rs.getInt("ref"));
				board.setReg_date(rs.getString("reg_date"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(con, st, rs);
		}
		return board;
	}
	
	//수정
	public int boardUpdate(BoardVO board) {
		int flag = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql ="";
		try {
			con = getConnection();
			sql ="SELECT passwd FROM board WHERE num="+board.getNum();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getString("passwd").equals(board.getPasswd())) {
					sql = "UPDATE board SET subject=?, email=?, content=?, reg_date= SYSDATE WHERE num = ?";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, board.getSubject());
					pstmt.setString(2, board.getEmail());
					pstmt.setString(3, board.getContent());
					pstmt.setInt(4, board.getNum());
					flag = pstmt.executeUpdate();
					con.commit();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(con, pstmt);
		}
		return flag;
	}
	
	//삭제
	public int boardDelete(int num) {
		Connection con = null;
		Statement st = null;

		int flag = 0;
		
		try {
			con = getConnection();
			String sql = "DELETE FROM board WHERE num ="+num;
			st = con.createStatement();
			flag = st.executeUpdate(sql);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(con, st);
		}
		return flag;
	}

	//개수
	public int boardCount() {
		int count =0;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			con = getConnection();
			String sql = "SELECT COUNT(*) FROM board";
			st = con.createStatement();
			rs = st.executeQuery(sql);
			if(rs.next()) {
				count = rs.getInt(1);
			}
			return count;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(con, st, rs);
		}
		return count;
	}
	
	// 검색기능
	public ArrayList<BoardVO> boardList(String field, String word,int startRow,int endRow) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<BoardVO> arr = new ArrayList<BoardVO>();
		
		try {
			con = getConnection();
			String sql = "SELECT * FROM (select rownum rn,aa.*from(select*from board WHERE "+field+" like '%"+word+"%' order by ref desc,re_step asc)aa) where rn<=? and rn >=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, endRow);
			pstmt.setInt(2, startRow);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardVO board = new BoardVO();
				board.setNum(rs.getInt("num"));
				board.setSubject(rs.getString("subject"));
				board.setWriter(rs.getString("writer"));
				board.setReg_date(rs.getString("reg_date"));
				board.setreadcount(rs.getInt("readcount"));
				board.setIp(rs.getString("ip"));
				arr.add(board);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(con, pstmt, rs);
		}
		return arr;
	}
	
	// 검색 개수
	public int boardCount(String field, String word) {
		int count =0;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			con = getConnection();
			String sql = "SELECT COUNT(*) FROM board WHERE "+field+" like '%"+word+"%'";
			st = con.createStatement();
			rs = st.executeQuery(sql);
			if(rs.next()) {
				count = rs.getInt(1);
			}
			return count;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(con, st, rs);
		}
		return count;
	}
	
	// 댓글쓰기 추가 메소드
	public void commentInsert(commentboardVO cvo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = getConnection();
			String sql = "INSERT INTO commentboard(cnum,userid,regdate,msg,bnum) VALUES (commentboard_seq.nextval,?,sysdate,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, cvo.getUserid());
			pstmt.setString(2, cvo.getMsg());
			pstmt.setInt(3, cvo.getBnum());
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(con, pstmt);
		}
	}
	
	// 댓글 보여지기 메소드, 댓글은 원글에서 따오기 때문에 원글의num이 반드시 필요함.
	public ArrayList<commentboardVO> commentList(int num){
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		ArrayList<commentboardVO> arr = new ArrayList<commentboardVO>();
		try {
			con = getConnection();
			String sql = "SELECT * FROM commentboard WHERE bnum ="+num+" ORDER BY cnum DESC";
			st = con.createStatement();
			rs = st.executeQuery(sql);
			
			while(rs.next()) {
				commentboardVO cvo = new commentboardVO();
				cvo.setBnum(rs.getInt("bnum"));
				cvo.setCnum(rs.getInt("cnum"));
				cvo.setMsg(rs.getString("msg"));
				cvo.setRegdate(rs.getString("regdate"));
				cvo.setUserid(rs.getString("userid"));
				arr.add(cvo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(con, st, rs);
		}
		return arr;
	}
	
	
}
