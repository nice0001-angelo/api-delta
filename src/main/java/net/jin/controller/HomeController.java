package net.jin.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

//	@GetMapping("/")
//	public String home(Locale locale, Model model) {
//		LocalDateTime now = LocalDateTime.now();
//
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일 (E) a h시 m분 s초");
//		String formattedNow = now.format(formatter);
//
//		model.addAttribute("serverTime", formattedNow);
//
//		return "home";
//	}

	@GetMapping("/")
	public String home(Locale locale, Model model) {
		LocalDateTime now = LocalDateTime.now();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일 (E) a h시 m분 s초");
		String formattedNow = now.format(formatter);

		model.addAttribute("serverTime", formattedNow);

		return "index";
	}

	@GetMapping("/index")
	public String index(Locale locale, Model model) {
		LocalDateTime now = LocalDateTime.now();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일 (E) a h시 m분 s초");
		String formattedNow = now.format(formatter);

		model.addAttribute("serverTime", formattedNow);

		return "index";
	}

	@GetMapping("/about")
	public String about(Locale locale, Model model) {
		LocalDateTime now = LocalDateTime.now();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일 (E) a h시 m분 s초");
		String formattedNow = now.format(formatter);

		model.addAttribute("serverTime", formattedNow);

		return "about";
	}

	@GetMapping("/computer")
	public String computer(Locale locale, Model model) {
		LocalDateTime now = LocalDateTime.now();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일 (E) a h시 m분 s초");
		String formattedNow = now.format(formatter);

		model.addAttribute("serverTime", formattedNow);

		return "computer";
	}

	@GetMapping("/laptop")
	public String laptop(Locale locale, Model model) {
		LocalDateTime now = LocalDateTime.now();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일 (E) a h시 m분 s초");
		String formattedNow = now.format(formatter);

		model.addAttribute("serverTime", formattedNow);

		return "laptop";
	}

	@GetMapping("/contact")
	public String contact(Locale locale, Model model) {
		LocalDateTime now = LocalDateTime.now();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일 (E) a h시 m분 s초");
		String formattedNow = now.format(formatter);

		model.addAttribute("serverTime", formattedNow);

		return "contact";
	}

	@GetMapping("/product")
	public String product(Locale locale, Model model) {
		LocalDateTime now = LocalDateTime.now();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일 (E) a h시 m분 s초");
		String formattedNow = now.format(formatter);

		model.addAttribute("serverTime", formattedNow);

		return "product";
	}
}
