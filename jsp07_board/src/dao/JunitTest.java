package dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import dto.Board;
import dto.Page;

class JunitTest {
	BoardDAO bdao = new BoardDAOImpl();
	@Test
	void testMBConn() {
		MBConn.getSession();
	}
	
	@Test
	void testInsert() {
		Board board = new Board();
		board.setEmail("park@gmail.com");
		board.setSubject("테스트제목");
		board.setContent("테스트내용");
		board.setIp("192.168.0.100");
		int cnt = bdao.insert(board);
		System.out.println(cnt + "건 추가");
	}
	
	@Test
	void testUpate() {
		Board board = new Board();
		board.setBnum(2);
		board.setEmail("park@gmail.com");
		board.setSubject("수정제목");
		board.setContent("수정내용");
		board.setIp("192.168.0.200");
		int cnt = bdao.update(board);
		System.out.println(cnt + "건 수정");
	}
	
	@Test
	void testDelete() {
		int cnt = bdao.delete(2);
		System.out.println(cnt + "건 삭제");
	}
	@Test
	void testSelectOne() {
		Board board = bdao.selectOne(3);
		System.out.println(board);
		//board가 null과 같지 않으면 성공
		assertNotNull(board);
	}
	
	@Test
	void testSelectList() {
		Page page = new Page();
		page.setFindkey("email");
		page.setFindvalue("park");
		
		List<Board> blist = bdao.selectList(page);
		System.out.println(blist);

	}
	
	
	
	
}
