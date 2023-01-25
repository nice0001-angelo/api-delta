package net.jin.common.controller;

import java.util.List;

import net.jin.dto.CodeLabelValue;
import net.jin.service.CodeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/codes")
public class CodeController {

	private final CodeService codeService;

	@GetMapping("/codeGroup")
	public ResponseEntity<List<CodeLabelValue>> codeGroupList() throws Exception {
		log.info("codeGroupList");

		return new ResponseEntity<List<CodeLabelValue>>(codeService.getCodeGroupList(), HttpStatus.OK);
	}

	@GetMapping("/job")
	public ResponseEntity<List<CodeLabelValue>> jobList() throws Exception {
		log.info("jobList");

		String classCode = "A01";
		List<CodeLabelValue> jobList = codeService.getCodeList(classCode);

		return new ResponseEntity<List<CodeLabelValue>>(jobList, HttpStatus.OK);
	}

}
