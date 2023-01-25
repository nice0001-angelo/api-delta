package net.jin.common.service;

import java.util.List;

import net.jin.common.domain.AccessLog;

public interface AccessLogService {

	public void register(AccessLog accessLog) throws Exception;

	public List<AccessLog> list() throws Exception;

}
