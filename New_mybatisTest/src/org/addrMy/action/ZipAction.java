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
		// �ּ��Է�â���� �����ȣ �˻� ��ư ������ ���� �κ�
		RequestDispatcher rd = request.getRequestDispatcher("zipCheck.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// �����ȣ �˻��Ǵ� �� â���� ������ �޾Ƽ� ó��. �ڹ� ��ũ��Ʈ �Լ����� ������ ����̱⿡ ����� ����
		request.setCharacterEncoding("utf-8");
		String dong = request.getParameter("dong");
		
		SqlSessionFactory sqlMapper = MybatisManager.getSqlMapper();
		SqlSession sqlsession = sqlMapper.openSession(ExecutorType.REUSE);
		
		List<ZipcodeVO> zip = sqlsession.selectList("zipData",dong);
		
		// 1. RequestDispatcher �� response.sendRedirect �� �̿��ؼ� �ٸ� ������ �̵�, ������������ ����.
		
		// 2. json�� �̿��ؼ� ���� �����ϰ�, �ٽ� ���� �������� ���ư��� ��� (1. json simple ���̺귯���� �̿���),(2.gson ���̺귯�� �̿�)
		// �ؽ��� ��ü�� �����.
		HashMap<String, Object> hm = new HashMap<>();
		// �ؽ��ʿ� �ִ´�. sql���� �����
		hm.put("zipcode", zip);
		// Gson ��ü�� �����.
		Gson gson = new Gson();
		// Gson�� �ؽ��� ��ü�� �ִ´�.
		String obj = gson.toJson(hm);
		
		//  Ÿ���� �����ϰ�
		response.setContentType("text/html;charset=utf-8");
		// out��ü�� ���� ��� �ǵ����Ѵ�.
		PrintWriter out =  response.getWriter();
		// Gson��ü�� ��� obj�� stringȭ ���Ѽ� ���.
		out.println(obj.toString());
		
		
		
		
		
	}

}
