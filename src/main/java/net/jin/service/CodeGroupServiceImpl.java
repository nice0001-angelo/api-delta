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
public class CodeGroupServiceImpl implements CodeGroupService {

	private final CodeGroupRepository repository;

	public void register(CodeGroup codeGroup) throws Exception {
		repository.save(codeGroup);
	}

	public CodeGroup read(String groupCode) throws Exception {
		return repository.getOne(groupCode);
	}

	public void modify(CodeGroup codeGroup) throws Exception {
		CodeGroup codeGroupEntity = repository.getOne(codeGroup.getGroupCode());

		codeGroupEntity.setGroupName(codeGroup.getGroupName());

		repository.save(codeGroupEntity);
	}

	public void remove(String groupCode) throws Exception {
		repository.deleteById(groupCode);
	}

	public List<CodeGroup> list() throws Exception {
		return repository.findAll(Sort.by(Direction.DESC, "groupCode"));
	}

}
