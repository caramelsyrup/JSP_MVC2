package org.addrMy.action;

import java.io.IOException;
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

/**
 * Servlet implementation class InsertAction
 */
@WebServlet("/address/insertAction.amy")
public class InsertAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertAction() {
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
		AddressVO avo = new AddressVO();
		avo.setAddress(request.getParameter("address"));
		avo.setName(request.getParameter("name"));
		avo.setTel(request.getParameter("tel"));
		avo.setZipcode(request.getParameter("zipcode"));
		
		// mybatis를 통해서 SaqlMapper를 불러와서 sqlMapper에 저장.
		SqlSessionFactory sqlMapper= MybatisManager.getSqlMapper();
		// 저장된 sqlMapper에서 
		SqlSession sqlSession = sqlMapper.openSession(ExecutorType.REUSE);
		try {
			sqlSession.insert("insertData",avo);
			sqlSession.commit();
		} finally {
			sqlSession.clearCache();
			sqlSession.close();
		}
		response.sendRedirect("listAction.amy");
	}

}
