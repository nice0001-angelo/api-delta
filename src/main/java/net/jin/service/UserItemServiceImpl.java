package net.jin.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import net.jin.domain.*;
import net.jin.repository.*;
import net.jin.exception.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserItemServiceImpl implements UserItemService {

	private final UserItemRepository userItemRepository;

	private final PayCoinRepository payCoinRepository;

	private final MemberRepository memberRepository;

	@Transactional
	public void register(Member member, Item item) throws Exception {
		Long userNo = member.getUserNo();

		Long itemId = item.getItemId();
		int price = item.getPrice();

		UserItem userItem = new UserItem();
		userItem.setUserNo(userNo);
		userItem.setItemId(itemId);

		PayCoin payCoin = new PayCoin();
		payCoin.setUserNo(userNo);
		payCoin.setItemId(itemId);
		payCoin.setAmount(price);

		Member memberEntity = memberRepository.getOne(userNo);

		Long coin = memberEntity.getCoin();
		long amount = payCoin.getAmount();

		if (coin < price) {
			throw new NotEnoughCoinException("There is Not Enough Coin.");
		}

		memberEntity.setCoin(coin - amount);

		memberRepository.save(memberEntity);

		payCoinRepository.save(payCoin);

		userItemRepository.save(userItem);
	}

	public UserItem read(Long userItemNo) throws Exception {
		List<Object[]> valueArrays = userItemRepository.readUserItem(userItemNo);

		Object[] valueArray = valueArrays.get(0);

		UserItem userItem = new UserItem();

		userItem.setUserItemNo((Long) valueArray[0]);
		userItem.setUserNo((Long) valueArray[1]);
		userItem.setItemId((Long) valueArray[2]);
		userItem.setRegDate((LocalDateTime) valueArray[3]);
		userItem.setItemName((String) valueArray[4]);
		userItem.setPrice((Integer) valueArray[5]);
		userItem.setDescription((String) valueArray[6]);
		userItem.setPictureUrl((String) valueArray[7]);

		return userItem;
	}

	public List<UserItem> list(Long userNo) throws Exception {
		List<Object[]> valueArrays = userItemRepository.listUserItem(userNo);

		List<UserItem> userItemList = new ArrayList<UserItem>();
		for (Object[] valueArray : valueArrays) {
			UserItem userItem = new UserItem();

			userItem.setUserItemNo((Long) valueArray[0]);
			userItem.setUserNo((Long) valueArray[1]);
			userItem.setItemId((Long) valueArray[2]);
			userItem.setRegDate((LocalDateTime) valueArray[3]);
			userItem.setItemName((String) valueArray[4]);
			userItem.setPrice((Integer) valueArray[5]);
			userItem.setDescription((String) valueArray[6]);
			userItem.setPictureUrl((String) valueArray[7]);

			userItemList.add(userItem);
		}

		return userItemList;
	}

}
