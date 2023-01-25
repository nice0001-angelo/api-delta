package net.jin.service;

import java.util.List;

import net.jin.domain.*;

public interface ItemService {

	public void register(Item item) throws Exception;

	public Item read(Long itemId) throws Exception;

	public void modify(Item item) throws Exception;

	public void remove(Long itemId) throws Exception;

	public List<Item> list() throws Exception;

	public String getPreview(Long itemId) throws Exception;

	public String getPicture(Long itemId) throws Exception;

}
