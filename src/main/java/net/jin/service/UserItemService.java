package net.jin.service;

import java.util.List;

import net.jin.domain.*;

public interface UserItemService {

	public void register(Member member, Item item) throws Exception;

	public UserItem read(Long userItemNo) throws Exception;

	public List<UserItem> list(Long userNo) throws Exception;

}
