package net.jin.controller;

import java.util.List;

import net.jin.domain.*;
import net.jin.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/codegroups")
public class CodeGroupController {

	private final CodeGroupService service;

	@GetMapping("/{groupCode}")
	public ResponseEntity<CodeGroup> read(@PathVariable("groupCode") String groupCode) throws Exception {
		CodeGroup codeGroup = service.read(groupCode);

		return new ResponseEntity<CodeGroup>(codeGroup, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<CodeGroup>> list() throws Exception {
		log.info("list");

		return new ResponseEntity<List<CodeGroup>>(service.list(), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<CodeGroup> register(@Validated @RequestBody CodeGroup codeGroup) throws Exception {
		log.info("register");

		service.register(codeGroup);

		log.info("register codeGroup.getCodeGroupNo() = " + codeGroup.getGroupCode());

		return new ResponseEntity<CodeGroup>(codeGroup, HttpStatus.OK);
	}

	@DeleteMapping("/{groupCode}")
	public ResponseEntity<Void> remove(@PathVariable("groupCode") String groupCode) throws Exception {
		service.remove(groupCode);

		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@PutMapping("/{groupCode}")
	public ResponseEntity<CodeGroup> modify(@PathVariable("groupCode") String groupCode,
			@Validated @RequestBody CodeGroup codeGroup) throws Exception {
		codeGroup.setGroupCode(groupCode);
		service.modify(codeGroup);

		return new ResponseEntity<CodeGroup>(codeGroup, HttpStatus.OK);
	}

}
