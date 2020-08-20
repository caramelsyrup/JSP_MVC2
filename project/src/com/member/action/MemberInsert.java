package com.member.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.member.model.MemberDAOImpl;
import com.member.model.MemberDTO;

/**
 * Servlet implementation class MemberInsert
 */
@WebServlet("/member/insert.me")
public class MemberInsert extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberInsert() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("join.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		MemberDAOImpl dao = MemberDAOImpl.getinstance();
		MemberDTO member = new MemberDTO();
		member.setAdmin(Integer.parseInt(request.getParameter("admin")));
		member.setUserEmail(request.getParameter("email"));
		member.setUserID(request.getParameter("userId"));
		member.setUserName(request.getParameter("name"));
		member.setUserPwd(request.getParameter("pwd"));
		member.setUserTel(Integer.parseInt(request.getParameter("phone")));
		dao.memberInsert(member);
		
		response.sendRedirect("login.me");
	}

}
