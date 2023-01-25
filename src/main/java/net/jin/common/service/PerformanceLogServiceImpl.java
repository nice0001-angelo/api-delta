package net.jin.common.service;

import java.util.List;

import net.jin.common.domain.PerformanceLog;
import net.jin.common.repository.PerformanceLogRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PerformanceLogServiceImpl implements PerformanceLogService {

	private final PerformanceLogRepository repository;

	public void register(PerformanceLog performanceLog) throws Exception {
		repository.save(performanceLog);
	}

	public List<PerformanceLog> list() throws Exception {
		return repository.findAll(Sort.by(Direction.DESC, "logNo"));
	}

}
