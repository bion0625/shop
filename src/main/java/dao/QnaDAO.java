package dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import vo.QnaVO;

public class QnaDAO {
	SqlSession sqlSession;
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	//qna 전체 게시물 조회
	public List<QnaVO> qna_list() {
		List<QnaVO> list = sqlSession.selectList("l.qna_list");
		return list;
	}
	
	//qna 페이지별 게시물 조회
	public List<QnaVO> qna_list(Map<String, Integer> map) {
		List<QnaVO> list = sqlSession.selectList("l.qna_list_condition", map);
		return list;
	}
	
	//qna 하나의 뷰
	public QnaVO qna_view(int idx) {
		QnaVO vo = sqlSession.selectOne("l.qna_view", idx);
		return vo;
	}
	
	//qna 뷰 리스트
	public List<QnaVO> qna_view_list(int ref) {
		List<QnaVO> list = sqlSession.selectList("l.qna_view_list", ref);
		return list;
	}
	
	//qna 입력
	public int qna_insert(QnaVO vo) {
		int res = sqlSession.insert("l.qna_insert", vo);
		return res;
	}
	
	//qna 수정
	public int qna_modify(QnaVO vo) {
		int res = sqlSession.update("l.qna_modify", vo);
		return res;
	}
	
	//qna 댓글 입력
	public int qna_reply(QnaVO vo) {
		int res = sqlSession.insert("l.qna_reply", vo);
		return res;
	}

	//qna 댓글관련 나머지 스텝 조절
	public int reply_step_update(QnaVO vo) {
		int res = sqlSession.update("l.qna_reply_step_update", vo);
		return res;
	}
	
	//qna 조회수 조절
	public int qna_update_readhit(int idx) {
		int res = sqlSession.update("l.qna_update_readhit", idx);
		return res;
	}

	//qna 삭제기능
	public int qna_del(QnaVO vo) {
		int res = sqlSession.update("l.qna_del", vo);
		return res;
	}
	
	//전체 게시물 수 구하기
	public int getRowTotal() {
		int count = sqlSession.selectOne("l.qna_count");
		return count;
	}
}
