package net.jin.service;

import java.util.List;

import net.jin.domain.*;
import net.jin.repository.*;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

	private final BoardRepository repository;

	public void register(Board board) throws Exception {
		repository.save(board);
	}

	public Board read(Long boardNo) throws Exception {
		return repository.getOne(boardNo);
	}

	public void modify(Board board) throws Exception {
		Board boardEntity = repository.getOne(board.getBoardNo());

		boardEntity.setTitle(board.getTitle());
		boardEntity.setContent(board.getContent());

		repository.save(boardEntity);
	}

	public void remove(Long boardNo) throws Exception {
		repository.deleteById(boardNo);
	}

	public List<Board> list() throws Exception {
		return repository.findAll(Sort.by(Direction.DESC, "boardNo"));
	}

}
