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
		dao.memberDelete(userid);	// ����
		int count = dao.memberCount();
		ArrayList<MemberDTO> arr = dao.memberList();	// ������ ��ü���
		//JSON ���·� ���� ������.
		JSONObject mainObj = new JSONObject();
		JSONArray jarr = new JSONArray();
		for(MemberDTO dto : arr) {
			String mode = dto.getAdmin()==1?"������":"�Ϲ�ȸ��";
			JSONObject obj = new JSONObject();
			obj.put("mode", mode);
			obj.put("useremail", dto.getUserEmail());
			obj.put("userid", dto.getUserID());
			obj.put("username", dto.getUserName());
			obj.put("usertel", dto.getUserTel());
			jarr.add(obj);
		}
		// ���� ������ ������ ���� ��ü ����.
		JSONObject countObj = new JSONObject();
		//��ü�� count���� ���
		countObj.put("count", count);
		
		// mainObj ��ü�� ��ϰ� ���� ��ü���� ���� ��´�.
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
