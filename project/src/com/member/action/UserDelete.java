package com.member.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.member.model.MemberDAOImpl;
import com.member.model.MemberDTO;

/**
 * Servlet implementation class UserDelete
 */
@WebServlet("/member/userDelete.me")
public class UserDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserDelete() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String userid = request.getParameter("userId");
		MemberDAOImpl dao = MemberDAOImpl.getinstance();
		dao.memberDelete(userid);	// 삭제
		int count = dao.memberCount();
		ArrayList<MemberDTO> arr = dao.memberList();	// 삭제후 전체목록
		//JSON 형태로 값을 가져감.
		JSONObject mainObj = new JSONObject();
		JSONArray jarr = new JSONArray();
		for(MemberDTO dto : arr) {
			String mode = dto.getAdmin()==1?"관리자":"일반회원";
			JSONObject obj = new JSONObject();
			obj.put("mode", mode);
			obj.put("useremail", dto.getUserEmail());
			obj.put("userid", dto.getUserID());
			obj.put("username", dto.getUserName());
			obj.put("usertel", dto.getUserTel());
			jarr.add(obj);
		}
		// 갯수 데이터 저장을 위한 객체 생성.
		JSONObject countObj = new JSONObject();
		//객체에 count변수 담기
		countObj.put("count", count);
		
		// mainObj 객체에 목록과 갯수 객체들을 각각 담는다.
		mainObj.put("root", jarr);
		mainObj.put("rootCount",countObj);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(mainObj.toString());	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
