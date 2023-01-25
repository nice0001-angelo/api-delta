package net.jin.repository;

import java.util.List;

import net.jin.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PayCoinRepository extends JpaRepository<PayCoin, Long> {

	@Query("SELECT a.historyNo, a.userNo, a.itemId, b.itemName, a.amount, a.regDate "
			+ "FROM PayCoin a INNER JOIN Item b ON a.itemId = b.itemId " + "WHERE a.historyNo > 0 AND a.userNo = ?1 "
			+ "ORDER BY a.historyNo DESC")
	public List<Object[]> listPayHistory(Long userNo);

}
