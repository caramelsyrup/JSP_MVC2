package com.guest.action;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.guest.model.GuestDAO;
import com.guest.model.GuestDTO;

/**
 * Servlet implementation class GuestListAction
 */
@WebServlet("/guestbook/create.gb")
public class GuestInsertAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GuestInsertAction() {
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
		GuestDAO dao = GuestDAO.getinstance();
		GuestDTO guest = new GuestDTO();
		guest.setWritter(request.getParameter("writter"));
		guest.setContent(request.getParameter("content"));
		guest.setGrade(request.getParameter("grade"));
		guest.setIpaddr(request.getRemoteAddr());
		dao.guestInsert(guest);
		
		response.sendRedirect("list.gb");
		
	}

}
