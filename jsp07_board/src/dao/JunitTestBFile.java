package dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import dto.BoardFile;

class JunitTestBFile {
	
	BoardFileDAO bfdao = new BoardFileDAOImpl();
	
	@Test
	void testInsert() {
		BoardFile boardFile = new BoardFile();
		boardFile.setBnum(5);
		boardFile.setFilename("b.png");
		int cnt = bfdao.insert(boardFile);
		System.out.println(cnt+"건 추가");
	}

	@Test
	void testUpdate() {
		BoardFile boardFile = new BoardFile();
		boardFile.setFnum(6);
		boardFile.setFilename("c.png");
		int cnt = bfdao.update(boardFile);
		System.out.println(cnt+"건 수정");
	}

	@Test
	void testDelete() {
		int cnt = bfdao.delete(6);
		System.out.println(cnt+"건 삭제");
	}

	@Test
	void testSelectOne() {
		BoardFile boardFile = bfdao.selectOne(7);
		System.out.println(boardFile);
	}

	@Test
	void testSelectList() {
		List<BoardFile> bflist = bfdao.selectList(5);
		System.out.println(bflist);
	}

}
