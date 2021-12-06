package dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import dto.BoardFile;

public class BoardFileDAOImpl implements BoardFileDAO{

	@Override
	public int insert(BoardFile boardFile) {
		try (SqlSession session = MBConn.getSession()){
			return session.insert("BoardFileMapper.insert", boardFile);
		}
	}

	@Override
	public int update(BoardFile boardFile) {
		try (SqlSession session = MBConn.getSession()){
			return session.update("BoardFileMapper.update", boardFile);
		}
	}

	//fnum를 기준으로 삭제(한건삭제)
	@Override
	public int delete(int fnum) {
		try (SqlSession session = MBConn.getSession()){
			return session.delete("BoardFileMapper.delete", fnum);
		}
	}
	
	//bnum를 기준으로 삭제(여러건삭제)
	@Override
	public int delete_bnum(int bnum) {
		try (SqlSession session = MBConn.getSession()){
			return session.delete("BoardFileMapper.delete_bnum", bnum);
		}
	}
	
	@Override
	public BoardFile selectOne(int fnum) {
		try (SqlSession session = MBConn.getSession()){
			return session.selectOne("BoardFileMapper.selectOne", fnum);
		}
	}

	@Override
	public List<BoardFile> selectList(int bnum) {
		try (SqlSession session = MBConn.getSession()){
			return session.selectList("BoardFileMapper.selectList", bnum);
		}
	}


}
