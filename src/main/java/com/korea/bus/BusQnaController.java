package com.korea.bus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import common.Common;
import common.Paging;
import dao.QnaDAO;
import vo.QnaVO;

@Controller
public class BusQnaController {

	@Autowired
	HttpServletRequest request;
	
	@Autowired
	HttpSession session;
	
	QnaDAO qna_dao;
	public void setqna_dao(QnaDAO qna_dao) {
		this.qna_dao = qna_dao;
	}
	
	@RequestMapping("/qna_list.do")
	public String qna_list(Model model, Integer page) {
		
		int nowPage = 1;
		if(page != null) {
			nowPage = page;
		}
		
		int start = (nowPage - 1) * Common.qna.BLOCKLIST + 1;
		int end = start + Common.qna.BLOCKLIST - 1;
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("start", start);
		map.put("end", end);
		
		List<QnaVO> list = qna_dao.qna_list(map);
		
		int row_total = qna_dao.getRowTotal();
		
		String pageMenu = Paging.getPaging(
				"qna_list.do", nowPage, row_total, 
				Common.qna.BLOCKLIST, 
				Common.qna.BLOCKPAGE);
		
		model.addAttribute("list", list);
		model.addAttribute("pageMenu", pageMenu);
		
		request.getSession().removeAttribute("show");
		
		return common.Common.qna.VIEW_PATH + "qna_list.jsp";
	}
	
	@RequestMapping("/qna_view_list.do") // 클릭 시 글보여주는 메서드 
	public String qna_view_list(int idx, int ref, Model model) {
		List<QnaVO> list = qna_dao.qna_view_list(ref);
		//System.out.println("test:" + vo.getPwd());
		
		session = request.getSession();
		String show = (String)session.getAttribute("show");
		
		if(show == null) {
			qna_dao.qna_update_readhit(idx);
			session.setAttribute("show", "a");
		}
		
		model.addAttribute("list", list);
		return common.Common.qna.VIEW_PATH + "qna_view_list.jsp";
	}
	
	@RequestMapping("/qna_insert_form.do") // 답변 누를시 전환되는 화면
	public String qna_insert_form() { 
		return common.Common.qna.VIEW_PATH + "qna_insert_form.jsp";
	}
	
	@RequestMapping("/qna_insert.do") // 답변에 입력된 값을 저장
	public String qna_insert(QnaVO vo) {
		String ip = request.getRemoteAddr();
		vo.setIp(ip);
		qna_dao.qna_insert(vo);
		return "qna_list.do";
	}

	@RequestMapping("/qna_modify_form.do") // 수정누를시 전환되는 화면
	public String qna_modify_form(int idx, Model model) {
		QnaVO vo = qna_dao.qna_view(idx);
		model.addAttribute("vo", vo);
		return common.Common.qna.VIEW_PATH + "qna_modify_form.jsp";
	}

	@RequestMapping("/qna_modify.do") //수정된 값 저장
	public String qna_modify(QnaVO baseVo, Model model) {
		String ip = request.getRemoteAddr();
		baseVo.setIp(ip);
		int res = qna_dao.qna_modify(baseVo);
		QnaVO vo = qna_dao.qna_view(baseVo.getIdx());
		return "qna_view_list.do?+idx="+vo.getIdx()+"&ref="+vo.getRef();
	}
	
	@RequestMapping("/qna_reply_form.do")
	public String qna_reply_form(int idx, Model model) {
		model.addAttribute("idx", idx);
		return common.Common.qna.VIEW_PATH + "qna_reply_form.jsp";
	}
	
	@RequestMapping("/qna_reply.do")
	public String qna_reply(QnaVO vo) {
		String ip = request.getRemoteAddr();
		vo.setIp(ip);
		
		QnaVO baseVo = qna_dao.qna_view(vo.getIdx());
		
		qna_dao.reply_step_update(baseVo);
		
		vo.setRef(baseVo.getRef());
		vo.setStep(baseVo.getStep()+1);
		vo.setDepth(baseVo.getDepth()+1);
		
		qna_dao.qna_reply(vo);
		
		return "qna_view_list.do?+idx="+vo.getIdx()+"&ref="+vo.getRef();
	}

	@RequestMapping("/qna_del.do") // 글삭제
	public String qna_del(int idx, Model model) {
		QnaVO vo = qna_dao.qna_view(idx);
		qna_dao.qna_del(vo);
		return "qna_list.do";
	}
}
