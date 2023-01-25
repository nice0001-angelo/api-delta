package net.jin.service;

import java.util.List;

import net.jin.domain.*;

public interface CoinService {

	public void charge(ChargeCoin chargeCoin) throws Exception;

	public List<ChargeCoin> list(Long userNo) throws Exception;

	public List<PayCoin> listPayHistory(Long userNo) throws Exception;

}
