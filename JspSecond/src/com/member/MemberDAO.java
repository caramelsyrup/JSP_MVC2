package com.member;

import java.util.ArrayList;

public interface MemberDAO {
	//�߰�
	public void memberInsert(MemberVO vo);
	//��ü����
	public ArrayList<MemberVO> memberList();
	//����
	public int memberUpdate(MemberVO vo);
	//�󼼺���
	public MemberVO memberDetail(String userID);
	//����
	public void memberDelete(String userID);
	//���̵� �ߺ� üũ
	public String idCheck(String userID);
	// �α��� �Ǵ�
	public int loginCheck(String userID, String userPwd );
	
	public int memberCount();
}
