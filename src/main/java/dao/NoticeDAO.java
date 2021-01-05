package dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import vo.NoticeVO;

public class NoticeDAO {
	SqlSession sqlSession;
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	//notice 전체 게시물 조회
	public List<NoticeVO> notice_list() {
		List<NoticeVO> list = sqlSession.selectList("n.notice_list");
		return list;
	}
	
	//notice 페이지별 게시물 조회
	public List<NoticeVO> notice_list(Map<String, Integer> map) {
		List<NoticeVO> list = sqlSession.selectList("n.notice_list_condition", map);
		return list;
	}
	
	//notice 하나의 뷰
	public NoticeVO notice_view(int idx) {
		NoticeVO vo = sqlSession.selectOne("n.notice_view", idx);
		return vo;
	}
	
	//notice 뷰 리스트
	public List<NoticeVO> notice_view_list(int ref) {
		List<NoticeVO> list = sqlSession.selectList("n.notice_view_list", ref);
		return list;
	}
	
	//notice 입력
	public int notice_insert(NoticeVO vo) {
		int res = sqlSession.insert("n.notice_insert", vo);
		return res;
	}
	
	//notice 수정
	public int notice_modify(NoticeVO vo) {
		int res = sqlSession.update("n.notice_modify", vo);
		return res;
	}
	
	//notice 댓글 입력
	public int notice_reply(NoticeVO vo) {
		int res = sqlSession.insert("n.notice_reply", vo);
		return res;
	}

	//notice 댓글관련 나머지 스텝 조절
	public int reply_step_update(NoticeVO vo) {
		int res = sqlSession.update("n.notice_reply_step_update", vo);
		return res;
	}
	
	//notice 조회수 조절
	public int notice_update_readhit(int idx) {
		int res = sqlSession.update("n.notice_update_readhit", idx);
		return res;
	}

	//notice 삭제기능
	public int notice_del(NoticeVO vo) {
		int res = sqlSession.update("n.notice_del", vo);
		return res;
	}
	
	//전체 게시물 수 구하기
	public int getRowTotal() {
		int count = sqlSession.selectOne("n.notice_count");
		return count;
	}
}
