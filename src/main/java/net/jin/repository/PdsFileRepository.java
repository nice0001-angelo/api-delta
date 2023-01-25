package net.jin.repository;

import java.util.List;

import net.jin.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PdsFileRepository extends JpaRepository<PdsFile, Long> {

	public List<PdsFile> findByFullName(String fullName);

}
