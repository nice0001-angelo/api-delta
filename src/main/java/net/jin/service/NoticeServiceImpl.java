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
public class NoticeServiceImpl implements NoticeService {

	private final NoticeRepository repository;

	public void register(Notice notice) throws Exception {
		repository.save(notice);
	}

	public Notice read(Long noticeNo) throws Exception {
		return repository.getOne(noticeNo);
	}

	public void modify(Notice notice) throws Exception {
		Notice noticeEntity = repository.getOne(notice.getNoticeNo());

		noticeEntity.setTitle(notice.getTitle());
		noticeEntity.setContent(notice.getContent());

		repository.save(noticeEntity);
	}

	public void remove(Long noticeNo) throws Exception {
		repository.deleteById(noticeNo);
	}

	public List<Notice> list() throws Exception {
		return repository.findAll(Sort.by(Direction.DESC, "noticeNo"));
	}

}
