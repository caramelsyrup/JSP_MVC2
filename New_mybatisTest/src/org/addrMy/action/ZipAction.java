package org.addrMy.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.addMy.config.MybatisManager;
import org.addrMy.model.ZipcodeVO;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.google.gson.Gson;

/**
 * Servlet implementation class ZipAction
 */
@WebServlet("/address/zipAction.amy")
public class ZipAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ZipAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 주소입력창에서 우편번호 검색 버튼 누르면 오는 부분
		RequestDispatcher rd = request.getRequestDispatcher("zipCheck.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 우편번호 검색되는 새 창에서 데이터 받아서 처리. 자바 스크립트 함수에서 지정한 경로이기에 여기로 접속
		request.setCharacterEncoding("utf-8");
		String dong = request.getParameter("dong");
		
		SqlSessionFactory sqlMapper = MybatisManager.getSqlMapper();
		SqlSession sqlsession = sqlMapper.openSession(ExecutorType.REUSE);
		
		List<ZipcodeVO> zip = sqlsession.selectList("zipData",dong);
		
		// 1. RequestDispatcher 나 response.sendRedirect 를 이용해서 다른 페이지 이동, 이전페이지는 못감.
		
		// 2. json을 이용해서 값을 저장하고, 다시 이전 페이지로 돌아가는 방법 (1. json simple 라이브러리를 이용함),(2.gson 라이브러리 이용)
		// 해쉬맵 객체를 만든다.
		HashMap<String, Object> hm = new HashMap<>();
		// 해쉬맵에 넣는다. sql문의 결과를
		hm.put("zipcode", zip);
		// Gson 객체를 만든다.
		Gson gson = new Gson();
		// Gson에 해쉬맵 객체를 넣는다.
		String obj = gson.toJson(hm);
		
		//  타입을 지정하고
		response.setContentType("text/html;charset=utf-8");
		// out객체를 만들어서 출력 되도록한다.
		PrintWriter out =  response.getWriter();
		// Gson객체가 담긴 obj를 string화 시켜서 출력.
		out.println(obj.toString());
		
		
		
		
		
	}

}
