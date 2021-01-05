package dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import vo.FreeVO;

public class FreeDAO {
	SqlSession sqlSession;
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	//free 전체 게시물 조회
	public List<FreeVO> free_list() {
		List<FreeVO> list = sqlSession.selectList("f.free_list");
		return list;
	}
	
	//free 페이지별 게시물 조회
	public List<FreeVO> free_list(Map<String, Integer> map) {
		List<FreeVO> list = sqlSession.selectList("f.free_list_condition", map);
		return list;
	}
	
	//free 하나의 뷰
	public FreeVO free_view(int idx) {
		FreeVO vo = sqlSession.selectOne("f.free_view", idx);
		return vo;
	}
	
	//free 뷰 리스트
	public List<FreeVO> free_view_list(int ref) {
		List<FreeVO> list = sqlSession.selectList("f.free_view_list", ref);
		return list;
	}
	
	//free 입력
	public int free_insert(FreeVO vo) {
		int res = sqlSession.insert("f.free_insert", vo);
		return res;
	}
	
	//free 수정
	public int free_modify(FreeVO vo) {
		int res = sqlSession.update("f.free_modify", vo);
		return res;
	}
	
	//free 댓글 입력
	public int free_reply(FreeVO vo) {
		int res = sqlSession.insert("f.free_reply", vo);
		return res;
	}

	//free 댓글관련 나머지 스텝 조절
	public int reply_step_update(FreeVO vo) {
		int res = sqlSession.update("f.free_reply_step_update", vo);
		return res;
	}
	
	//free 조회수 조절
	public int free_update_readhit(int idx) {
		int res = sqlSession.update("f.free_update_readhit", idx);
		return res;
	}

	//free 삭제기능
	public int free_del(FreeVO vo) {
		int res = sqlSession.update("f.free_del", vo);
		return res;
	}
	
	//전체 게시물 수 구하기
	public int getRowTotal() {
		int count = sqlSession.selectOne("f.free_count");
		return count;
	}
}
