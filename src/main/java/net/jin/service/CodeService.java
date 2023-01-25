package net.jin.service;

import java.util.List;

import net.jin.dto.*;

public interface CodeService {

	public List<CodeLabelValue> getCodeGroupList() throws Exception;

	public List<CodeLabelValue> getCodeList(String groupCode) throws Exception;

}
