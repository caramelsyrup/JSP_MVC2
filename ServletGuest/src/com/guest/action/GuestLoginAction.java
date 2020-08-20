package com.guest.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.guest.model.GuestDAO;

/**
 * Servlet implementation class GuestLoginAction
 */
@WebServlet("/guestbook/login.gb")
public class GuestLoginAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GuestLoginAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String path="";
		
		GuestDAO dao = GuestDAO.getinstance();
		int flag = dao.guestLoginCheck(id, pw);
		
		if(flag==1) {	// �α��μ���
			HttpSession session = request.getSession();
			session.setAttribute("login", id);
			path="insert.jsp";
		}else if(flag==0) {	// ��� ����
			request.setAttribute("errMsg", "��й�ȣ�� Ȯ���ϼ���.");
			path="login.jsp";
		}else if(flag==-1) {	// ȸ�� �ƴ�
			request.setAttribute("errMsg", "ȸ���� �ƴմϴ�.");
			path="login.jsp";
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}

}
