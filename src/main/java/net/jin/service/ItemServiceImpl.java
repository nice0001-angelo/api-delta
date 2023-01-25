package net.jin.service;

import java.util.List;

import net.jin.domain.*;
import net.jin.repository.*;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ItemServiceImpl implements ItemService {

	private final ItemRepository repository;

	public void register(Item item) throws Exception {
		repository.save(item);
	}

	public Item read(Long itemId) throws Exception {
		return repository.getOne(itemId);
	}

	public void modify(Item item) throws Exception {
		Item itemEntity = repository.getOne(item.getItemId());

		itemEntity.setItemName(item.getItemName());
		itemEntity.setPrice(item.getPrice());
		itemEntity.setDescription(item.getDescription());
		itemEntity.setPictureUrl(item.getPictureUrl());
		itemEntity.setPreviewUrl(item.getPreviewUrl());

		repository.save(itemEntity);
	}

	public void remove(Long itemId) throws Exception {
		repository.deleteById(itemId);
	}

	public List<Item> list() throws Exception {
		return repository.findAll(Sort.by(Direction.DESC, "itemId"));
	}

	public String getPreview(Long itemId) throws Exception {
		Item item = repository.getOne(itemId);
		return item.getPreviewUrl();
	}

	public String getPicture(Long itemId) throws Exception {
		Item item = repository.getOne(itemId);
		return item.getPictureUrl();
	}

}
