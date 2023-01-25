package net.jin.common.service;

import java.util.List;

import net.jin.common.domain.PerformanceLog;

public interface PerformanceLogService {

	public void register(PerformanceLog performanceLog) throws Exception;

	public List<PerformanceLog> list() throws Exception;

}
