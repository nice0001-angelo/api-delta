package net.jin.controller;

import java.util.List;
import java.util.Locale;

import net.jin.common.security.domain.*;
import net.jin.domain.*;
import net.jin.service.*;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
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
@RequestMapping("/users")
public class MemberController {

	private final MemberService service;

	private final PasswordEncoder passwordEncoder;

	private final MessageSource messageSource;

	@PostMapping
	public ResponseEntity<Member> register(@Validated @RequestBody Member member) throws Exception {
		log.info("member.getUserName() = " + member.getUserName());

		String inputPassword = member.getUserPw();
		member.setUserPw(passwordEncoder.encode(inputPassword));

		service.register(member);

		log.info("register member.getUserNo() = " + member.getUserNo());

		return new ResponseEntity<Member>(member, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public ResponseEntity<List<Member>> list() throws Exception {
		return new ResponseEntity<List<Member>>(service.list(), HttpStatus.OK);
	}

	@GetMapping("/{userNo}")
	public ResponseEntity<Member> read(@PathVariable("userNo") Long userNo) throws Exception {
		Member member = service.read(userNo);

		return new ResponseEntity<Member>(member, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userNo}")
	public ResponseEntity<Void> remove(@PathVariable("userNo") Long userNo) throws Exception {
		service.remove(userNo);

		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@PreAuthorize("hasAnyRole('ADMIN','MEMBER')")
	@PutMapping("/{userNo}")
	public ResponseEntity<Member> modify(@PathVariable("userNo") Long userNo, @Validated @RequestBody Member member)
			throws Exception {
		log.info("modify : member.getUserName() = " + member.getUserName());
		log.info("modify : userNo = " + userNo);

		member.setUserNo(userNo);
		service.modify(member);

		return new ResponseEntity<Member>(member, HttpStatus.OK);
	}

	@PostMapping(value = "/setup", produces = "text/plain;charset=UTF-8")
	public ResponseEntity<String> setupAdmin(@Validated @RequestBody Member member) throws Exception {
		log.info("setupAdmin : member.getUserName() = " + member.getUserName());
		log.info("setupAdmin : service.countAll() = " + service.countAll());

		if (service.countAll() == 0) {
			String inputPassword = member.getUserPw();
			member.setUserPw(passwordEncoder.encode(inputPassword));

			member.setJob("00");

			service.setupAdmin(member);

			return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		}

		String message = messageSource.getMessage("common.cannotSetupAdmin", null, Locale.KOREAN);

		return new ResponseEntity<String>(message, HttpStatus.BAD_REQUEST);
	}

	@PreAuthorize("hasAnyRole('ADMIN','MEMBER')")
	@GetMapping("/myinfo")
	public ResponseEntity<Member> getMyInfo(@AuthenticationPrincipal CustomUser customUser) throws Exception {
		Long userNo = customUser.getUserNo();
		log.info("register userNo = " + userNo);

		Member member = service.read(userNo);

		member.setUserPw("");

		return new ResponseEntity<Member>(member, HttpStatus.OK);
	}

}
