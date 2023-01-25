package net.jin.repository;

import java.util.List;

import net.jin.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CodeDetailRepository extends JpaRepository<CodeDetail, CodeDetailId> {

	@Query("SELECT max(cd.sortSeq) FROM CodeDetail cd WHERE cd.groupCode = ?1")
	public List<Object[]> getMaxSortSeq(String groupCode);

	@Query("SELECT cd FROM CodeDetail cd WHERE cd.groupCode = ?1")
	public List<CodeDetail> getCodeList(String groupCode);
}
