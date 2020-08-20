package org.addMy.config;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MybatisManager {
	// ������ �е��� ����, db ������ ���ؼ�
	// mybatis.jar �ʿ���. ��� mybatis ���ø����̼��� SqlSessionFactory�� �ν��Ͻ��� ���.
	// sqlMapper�� �ν��Ͻ� ����.
	public static SqlSessionFactory sqlMapper;
	// static��  ���� ���� �Ǳ⿡, ���� ����.
	static {
		// jdbc �̸�, ���, ����, ��� ������ �ִ� xml ���� ��θ� ����
		String resource="org/addMy/config/Configuration.xml";
		// ������ ���� �� �ֵ��� ���� ����.
		Reader reader;
		try {
			// resource��ü�� ���ؼ� reader ��ü�� ����.
			reader = Resources.getResourceAsReader(resource);
			// reader�� �о SqlSessionFactoryBuilder���� jdbc���� �������� ����.
			// SqlSessionFactory�� �ν��Ͻ��� ���� �� �ִ�. ���⼭�� sqlMapper�� �̹� ����.
			sqlMapper = new SqlSessionFactoryBuilder().build(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static SqlSessionFactory getSqlMapper() {
		return sqlMapper;
	}
}
