package org.addrMy.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.addMy.config.MybatisManager;
import org.addrMy.model.AddressVO;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.google.gson.Gson;

/**
 * Servlet implementation class DeleteAjaxAction
 */
@WebServlet("/address/deleteAjaxAction.amy")
public class DeleteAjaxAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteAjaxAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		int num = Integer.parseInt(request.getParameter("num"));
		SqlSessionFactory sqlMapper = MybatisManager.getSqlMapper();
		SqlSession sqlsession = sqlMapper.openSession(ExecutorType.REUSE);
		
		try {
			// ������ ���� ����.
		sqlsession.delete("deleteAjaxData",num);
		sqlsession.commit();
		// ��ü ����Ʈ�� �̴´�.
		List<AddressVO> arr = sqlsession.selectList("listData");
		// ����Ʈ ��ü�� ���� map��ü ����.
		HashMap<String, Object> map = new HashMap<String, Object>();
		// ����Ʈ ��ü�� ��´�. map��ü��
		map.put("list", arr);
		
		// ������ ����.
		int count = sqlsession.selectOne("countData");
		map.put("count", count);
		
		//gson ��ü ����.
		Gson gson = new Gson();
		// obj�� map��ü�� gson�� �̿��Ͽ� Json���·� �ٲ�.
		String obj = gson.toJson(map);
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(obj.toString());
		
		}finally {
			sqlsession.clearCache();
			sqlsession.close();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}