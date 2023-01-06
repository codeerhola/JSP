package kr.or.ddit.vo;

import java.security.Principal;


//멤버vo 안건드림 
public class MemberVOWrapper implements Principal{
	
	private MemberVO realMember; // 어댑티

	//어댑터는 기본생성자를 안갖음
	public MemberVOWrapper(MemberVO realMember) {
		super();
		this.realMember = realMember;
	}
	
	public MemberVO getRealMember() {
		return realMember;
	}

	//식별자임 
	@Override
	public String getName() {
		return realMember.getMemId();
	}

}
