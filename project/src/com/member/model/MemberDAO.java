package com.member.model;

import java.util.ArrayList;


public interface MemberDAO {
		//추가
		public void memberInsert(MemberDTO vo);
		//전체보기
		public ArrayList<MemberDTO> memberList();
		//수정
		public int memberUpdate(MemberDTO vo);
		//상세보기
		public MemberDTO memberDetail(String userID);
		//삭제
		public void memberDelete(String userID);
		//아이디 중복 체크
		public String idCheck(String userID);
		// 로그인 판단
		public int loginCheck(String userID, String userPwd );
		
		public int memberCount();

}
