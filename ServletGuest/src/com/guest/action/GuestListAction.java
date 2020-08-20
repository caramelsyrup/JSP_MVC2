package com.guest.action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.guest.model.GuestDAO;
import com.guest.model.GuestDTO;
import com.guest.model.PageUtil;

/**
 * Servlet implementation class GuestListAction
 */
@WebServlet("/guestbook/list.gb")
public class GuestListAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GuestListAction() {
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
		// �������� �����Ͽ�, �����ϱ� ���� ���� ����.
		String pageNum = request.getParameter("pageNum")==null? "1":request.getParameter("pageNum");
		String field = request.getParameter("field")==null? "":request.getParameter("field");
		String word = request.getParameter("word")==null? "":request.getParameter("word");
		int currentPage = Integer.parseInt(pageNum);
		int pageSize = 5;
		int startRow=(currentPage-1)*pageSize+1;
		int endRow=currentPage*pageSize;
		
		int count = dao.guestCount(field,word);
		
		int totalpage = (count/pageSize)+(count%pageSize==0?0:1);
		int pageBlock = 3;
		int startPage = ((currentPage-1)/pageBlock)*pageBlock+1;
		int endPage = startPage+pageBlock-1;
		if(endPage>totalpage) endPage=totalpage;
		// �������� ���ؼ� �� ���� �������� �� setAttribute�� �ϸ� ���� �۾�. �ϳ��� ��ü�� ��°� ȿ����.
		PageUtil pu = new PageUtil();
		pu.setCurrentPage(currentPage);
		pu.setEndPage(endPage);
		pu.setPageBlock(pageBlock);
		pu.setStartPage(startPage);
		pu.setTotalpage(totalpage);
		pu.setField(field);
		pu.setWord(word);
		
		ArrayList<GuestDTO> arr = null;
		if(word.equals("")) {
			arr = dao.guestList(startRow, endRow);
		}else {
			arr = dao.guestList(field, word, startRow, endRow);
		}
		
		int rowNo = count-((currentPage-1)*pageSize);	// �� �������� ���۹�ȣ. DB�� ����� ��ȣ�� �ƴϴ�.
		
		request.setAttribute("rowNo", rowNo);
		request.setAttribute("pu", pu);
		request.setAttribute("list", arr);
		request.setAttribute("count", count);
		
		RequestDispatcher rd = request.getRequestDispatcher("listResult.jsp");
		rd.forward(request, response);
	}

}
