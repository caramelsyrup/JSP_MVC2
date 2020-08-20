package com.member.model;

import java.util.ArrayList;


public interface MemberDAO {
		//�߰�
		public void memberInsert(MemberDTO vo);
		//��ü����
		public ArrayList<MemberDTO> memberList();
		//����
		public int memberUpdate(MemberDTO vo);
		//�󼼺���
		public MemberDTO memberDetail(String userID);
		//����
		public void memberDelete(String userID);
		//���̵� �ߺ� üũ
		public String idCheck(String userID);
		// �α��� �Ǵ�
		public int loginCheck(String userID, String userPwd );
		
		public int memberCount();

}
