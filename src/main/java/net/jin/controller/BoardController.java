package net.jin.controller;

import java.util.List;

import net.jin.common.security.domain.*;
import net.jin.domain.*;
import net.jin.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/boards")
public class BoardController {

	private final BoardService service;

	@PreAuthorize("hasRole('MEMBER')")
	@PostMapping
	public ResponseEntity<Board> register(@Validated @RequestBody Board board,
			@AuthenticationPrincipal CustomUser customUser) throws Exception {
		String userId = customUser.getUserId();

		log.info("register userId = " + userId);

		board.setWriter(userId);

		service.register(board);

		log.info("register board.getBoardNo() = " + board.getBoardNo());

		Board createdBoard = service.read(board.getBoardNo());

		return new ResponseEntity<Board>(createdBoard, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<Board>> list() throws Exception {
		log.info("list");

		return new ResponseEntity<List<Board>>(service.list(), HttpStatus.OK);
	}

	@GetMapping("/{boardNo}")
	public ResponseEntity<Board> read(@PathVariable("boardNo") Long boardNo) throws Exception {
		log.info("read");

		Board board = service.read(boardNo);

		return new ResponseEntity<Board>(board, HttpStatus.OK);
	}

	@PreAuthorize("(hasRole('MEMBER') and principal.username == #writer) or hasRole('ADMIN')")
	@DeleteMapping("/{boardNo}")
	public ResponseEntity<Void> remove(@PathVariable("boardNo") Long boardNo, @RequestParam("writer") String writer)
			throws Exception {
		log.info("remove");

		log.info("remove writer " + writer);

		service.remove(boardNo);

		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@PreAuthorize("hasRole('MEMBER') and principal.username == #board.writer")
	@PutMapping("/{boardNo}")
	public ResponseEntity<Board> modify(@PathVariable("boardNo") Long boardNo, @Validated @RequestBody Board board)
			throws Exception {
		log.info("modify");

		board.setBoardNo(boardNo);
		service.modify(board);

		return new ResponseEntity<Board>(board, HttpStatus.OK);
	}

}
