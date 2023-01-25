package net.jin.service;

import java.util.ArrayList;
import java.util.List;

import net.jin.domain.*;
import net.jin.repository.*;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PdsServiceImpl implements PdsService {

	private final PdsRepository repository;

	private final PdsFileRepository pdsFileRepository;

	public void register(Pds pds) throws Exception {
		Pds pdsEntity = new Pds();

		pdsEntity.setItemName(pds.getItemName());
		pdsEntity.setDescription(pds.getDescription());

		String[] files = pds.getFiles();

		if (files == null) {
			return;
		}

		for (String fileName : files) {
			PdsFile pdsFile = new PdsFile();
			pdsFile.setFullName(fileName);

			pdsEntity.addItemFile(pdsFile);
		}

		repository.save(pdsEntity);

		pds.setItemId(pdsEntity.getItemId());
	}

	public Pds read(Long itemId) throws Exception {
		Pds pdsEntity = repository.getOne(itemId);
		Integer viewCnt = pdsEntity.getViewCnt();

		if (viewCnt == null) {
			viewCnt = 0;
		}

		pdsEntity.setViewCnt(viewCnt + 1);

		repository.save(pdsEntity);

		return repository.getOne(itemId);
	}

	public void modify(Pds pds) throws Exception {
		Pds pdsEntity = repository.getOne(pds.getItemId());

		pdsEntity.setItemName(pds.getItemName());
		pdsEntity.setDescription(pds.getDescription());

		String[] files = pds.getFiles();

		if (files != null) {
			pdsEntity.clearItemFile();

			for (String fileName : files) {
				PdsFile pdsFile = new PdsFile();
				pdsFile.setFullName(fileName);

				pdsEntity.addItemFile(pdsFile);
			}
		}
		repository.save(pdsEntity);
	}

	public void remove(Long itemId) throws Exception {
		repository.deleteById(itemId);
	}

	public List<Pds> list() throws Exception {
		return repository.findAll(Sort.by(Direction.DESC, "itemId"));
	}

	public List<String> getAttach(Long itemId) throws Exception {
		Pds pdsEntity = repository.getOne(itemId);

		List<PdsFile> pdsFiles = pdsEntity.getPdsFiles();

		List<String> attachList = new ArrayList<String>();
		for (PdsFile pdsFile : pdsFiles) {
			attachList.add(pdsFile.getFullName());
		}

		return attachList;
	}

	public void updateAttachDownCnt(String fullName) throws Exception {
		List<PdsFile> pdsFileList = pdsFileRepository.findByFullName(fullName);

		if (pdsFileList.size() > 0) {
			PdsFile pdsFileEntity = pdsFileList.get(0);

			Integer downCnt = pdsFileEntity.getDownCnt();
			if (downCnt == null) {
				downCnt = 0;
			}
			pdsFileEntity.setDownCnt(downCnt + 1);

			pdsFileRepository.save(pdsFileEntity);
		}
	}

}
