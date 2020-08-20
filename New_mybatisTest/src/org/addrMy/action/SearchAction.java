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
 * Servlet implementation class SearchAction
 */
@WebServlet("/address/searchAction.amy")
public class SearchAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String field = request.getParameter("field");
		String word = request.getParameter("word");
		
		// map�� ���� ���� �Ű��������� ���� �� �ֵ��� ��.
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("field", field);
		map.put("word", word);
		SqlSessionFactory sqlMapper = MybatisManager.getSqlMapper();
		SqlSession sqlsession = sqlMapper.openSession(ExecutorType.REUSE);
		try {
		// selectList�� ��Ʈ��,��ü ���� �ۿ� �ȵǱ⿡, ���� �������� ���� �� �ִ� map�� ����.
		List<AddressVO> sarr =  sqlsession.selectList("searchData",map);
		// sql�� ��� ���� ���� �ؽ����� ����
		HashMap<String, Object> hm = new HashMap<String, Object>();
		// hm ��ü�� ������� ����.
		hm.put("sarr", sarr);

		// ���� ����
		int count = sqlsession.selectOne("countsearchData",map);
		hm.put("count",count);
		
		// Gson ��ü�� ����,
		Gson gson = new Gson();
		// obj�� hm�� gson�� �̿��ؼ� JSON���� �׵� ����.
		String obj = gson.toJson(hm);
		
		// ���
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
