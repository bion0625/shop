package dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import vo.BnRecordVO;
import vo.StRecordVO;

public class BusDAO {
	SqlSession sqlSession;

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public List<StRecordVO> st_record_list(){
		List<StRecordVO> list = sqlSession.selectList("b.st_record_list");
		return list;
	}

	public List<BnRecordVO> bn_record_list(){
		List<BnRecordVO> list = sqlSession.selectList("b.bn_record_list");
		return list;
	}
	
	public int st_insert_search(StRecordVO vo) {
		int res = sqlSession.insert("b.st_record_insert", vo);
		return res;
	}
	public int bn_insert_search(BnRecordVO vo) {
		int res = sqlSession.insert("b.bn_record_insert", vo);
		return res;
	}
	public int st_record_del(int idx) {
		int res = sqlSession.delete("b.st_record_del", idx);
		return res;
	}
	
	
}
