package net.jin.common.service;

import java.util.List;

import net.jin.common.domain.AccessLog;
import net.jin.common.repository.AccessLogRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AccessLogServiceImpl implements AccessLogService {

	private final AccessLogRepository repository;

	public void register(AccessLog accessLog) throws Exception {
		repository.save(accessLog);
	}

	public List<AccessLog> list() throws Exception {
		return repository.findAll(Sort.by(Direction.DESC, "logNo"));
	}

}
