package net.jin.service;

import java.util.List;

import net.jin.domain.*;

public interface MemberService {

	public void register(Member member) throws Exception;

	public Member read(Long userNo) throws Exception;

	public void modify(Member member) throws Exception;

	public void remove(Long userNo) throws Exception;

	public List<Member> list() throws Exception;

	public long countAll() throws Exception;

	public void setupAdmin(Member member) throws Exception;

	public long getCoin(Long userNo) throws Exception;

}
