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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/coins")
public class CoinController {

	private final CoinService service;

	private final MessageSource messageSource;

	@PreAuthorize("hasRole('MEMBER')")
	@PostMapping(value = "/charge/{amount}", produces = "text/plain;charset=UTF-8")
	public ResponseEntity<String> charge(@PathVariable("amount") int amount,
			@AuthenticationPrincipal CustomUser customUser) throws Exception {
		Long userNo = customUser.getUserNo();

		ChargeCoin chargeCoin = new ChargeCoin();

		chargeCoin.setUserNo(userNo);
		chargeCoin.setAmount(amount);

		service.charge(chargeCoin);

		String message = messageSource.getMessage("coin.chargingComplete", null, Locale.KOREAN);

		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('MEMBER')")
	@GetMapping
	public ResponseEntity<List<ChargeCoin>> list(@AuthenticationPrincipal CustomUser customUser) throws Exception {
		Long userNo = customUser.getUserNo();

		return new ResponseEntity<List<ChargeCoin>>(service.list(userNo), HttpStatus.OK);
	}

	@GetMapping("/pay")
	@PreAuthorize("hasRole('MEMBER')")
	public ResponseEntity<List<PayCoin>> listPayHistory(@AuthenticationPrincipal CustomUser customUser)
			throws Exception {
		Long userNo = customUser.getUserNo();

		return new ResponseEntity<List<PayCoin>>(service.listPayHistory(userNo), HttpStatus.OK);
	}

}
