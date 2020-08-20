package com.guest.action;

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

import com.guest.model.GuestDAO;
import com.guest.model.GuestDTO;

/**
 * Servlet implementation class GuestViewAction
 */
@WebServlet("/guestbook/view.gb")
public class GuestViewAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GuestViewAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		int num = Integer.parseInt(request.getParameter("num"));
		
		GuestDAO dao = GuestDAO.getinstance();
		GuestDTO guest = dao.guestView(num);
		
		// 자바 object를 JSON으로 바꿔준다.
			JSONObject obj = new JSONObject();
			obj.put("num",guest.getNum());
			obj.put("writter", guest.getWritter());
			obj.put("content", guest.getContent());
			obj.put("grade", guest.getGrade());
			obj.put("created", guest.getCreated());
			obj.put("ipaddr", guest.getIpaddr());
			
		// JSON 객체에 담은 것을 화면에 출력 시켜주어야 이 정보를 자바스크립트에서 활용 가능.	
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(obj.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
