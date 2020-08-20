package com.member.action;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.model.MemberDAOImpl;
import com.member.model.MemberDTO;

/**
 * Servlet implementation class MemberUpdate
 */
@WebServlet("/member/update.me")
public class MemberUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberUpdate() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		MemberDTO dto = new MemberDTO();
		dto.setAdmin(Integer.parseInt(request.getParameter("admin")));
		dto.setUserEmail(request.getParameter("userEmail"));
		dto.setUserName(request.getParameter("userName"));
		dto.setUserTel(Integer.parseInt(request.getParameter("userTel")));
		
		MemberDAOImpl dao = MemberDAOImpl.getinstance();
		int flag = dao.memberUpdate(dto);
		
		if(flag==1) {
			HttpSession session = request.getSession();
			session.invalidate();
			response.sendRedirect("login.me");
		}

	}

}
