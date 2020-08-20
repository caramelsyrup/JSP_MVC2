package org.addMy.config;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MybatisManager {
	// 파일을 읽도록 유도, db 연결을 위해서
	// mybatis.jar 필요함. 모든 mybatis 애플리케이션은 SqlSessionFactory의 인스턴스를 사용.
	// sqlMapper로 인스턴스 선언.
	public static SqlSessionFactory sqlMapper;
	// static는  먼저 실행 되기에, 먼저 정의.
	static {
		// jdbc 이름, 경로, 계정, 비번 정보가 있는 xml 파일 경로를 저장
		String resource="org/addMy/config/Configuration.xml";
		// 파일을 읽을 수 있도록 변수 설정.
		Reader reader;
		try {
			// resource객체를 통해서 reader 객체에 저장.
			reader = Resources.getResourceAsReader(resource);
			// reader를 읽어서 SqlSessionFactoryBuilder통해 jdbc관련 정보들을 실행.
			// SqlSessionFactory의 인스턴스를 만들 수 있다. 여기서는 sqlMapper로 이미 선인.
			sqlMapper = new SqlSessionFactoryBuilder().build(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static SqlSessionFactory getSqlMapper() {
		return sqlMapper;
	}
}
