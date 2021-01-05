package dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import vo.LossVO;

public class LossDAO {
	SqlSession sqlSession;
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	//loss 전체 게시물 조회
	public List<LossVO> loss_list() {
		List<LossVO> list = sqlSession.selectList("l.loss_list");
		return list;
	}
	
	//loss 페이지별 게시물 조회
	public List<LossVO> loss_list(Map<String, Integer> map) {
		List<LossVO> list = sqlSession.selectList("l.loss_list_condition", map);
		return list;
	}
	
	//loss 하나의 뷰
	public LossVO loss_view(int idx) {
		LossVO vo = sqlSession.selectOne("l.loss_view", idx);
		return vo;
	}
	
	//loss 뷰 리스트
	public List<LossVO> loss_view_list(int ref) {
		List<LossVO> list = sqlSession.selectList("l.loss_view_list", ref);
		return list;
	}
	
	//loss 입력
	public int loss_insert(LossVO vo) {
		int res = sqlSession.insert("l.loss_insert", vo);
		return res;
	}
	
	//loss 수정
	public int loss_modify(LossVO vo) {
		int res = sqlSession.update("l.loss_modify", vo);
		return res;
	}
	
	//loss 댓글 입력
	public int loss_reply(LossVO vo) {
		int res = sqlSession.insert("l.loss_reply", vo);
		return res;
	}

	//loss 댓글관련 나머지 스텝 조절
	public int reply_step_update(LossVO vo) {
		int res = sqlSession.update("l.loss_reply_step_update", vo);
		return res;
	}
	
	//loss 조회수 조절
	public int loss_update_readhit(int idx) {
		int res = sqlSession.update("l.loss_update_readhit", idx);
		return res;
	}

	//loss 삭제기능
	public int loss_del(LossVO vo) {
		int res = sqlSession.update("l.loss_del", vo);
		return res;
	}
	
	//전체 게시물 수 구하기
	public int getRowTotal() {
		int count = sqlSession.selectOne("l.loss_count");
		return count;
	}
}
