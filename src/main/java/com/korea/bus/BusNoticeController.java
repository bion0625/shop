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
import dao.NoticeDAO;
import vo.NoticeVO;

@Controller
public class BusNoticeController {

	@Autowired
	HttpServletRequest request;
	
	@Autowired
	HttpSession session;
	
	NoticeDAO notice_dao;
	public void setNotice_dao(NoticeDAO notice_dao) {
		this.notice_dao = notice_dao;
	}
	
	@RequestMapping("/notice_list.do")
	public String notice_list(Model model, Integer page) {
		
		int nowPage = 1;
		if(page != null) {
			nowPage = page;
		}
		
		int start = (nowPage - 1) * Common.notice.BLOCKLIST + 1;
		int end = start + Common.notice.BLOCKLIST - 1;
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("start", start);
		map.put("end", end);
		
		List<NoticeVO> list = notice_dao.notice_list(map);
		
		int row_total = notice_dao.getRowTotal();
		
		String pageMenu = Paging.getPaging(
				"notice_list.do", nowPage, row_total, 
				Common.notice.BLOCKLIST, 
				Common.notice.BLOCKPAGE);
		
		model.addAttribute("list", list);
		model.addAttribute("pageMenu", pageMenu);
		
		request.getSession().removeAttribute("show");
		
		return common.Common.notice.VIEW_PATH + "notice_list.jsp";
	}
	
	@RequestMapping("/notice_view_list.do") // 클릭 시 글보여주는 메서드 
	public String notice_view_list(int idx, int ref, Model model) {
		List<NoticeVO> list = notice_dao.notice_view_list(ref);
		//System.out.println("test:" + vo.getPwd());
		
		session = request.getSession();
		String show = (String)session.getAttribute("show");
		
		if(show == null) {
			notice_dao.notice_update_readhit(idx);
			session.setAttribute("show", "a");
		}
		
		model.addAttribute("list", list);
		return common.Common.notice.VIEW_PATH + "notice_view_list.jsp";
	}
	
	@RequestMapping("/notice_insert_form.do") // 답변 누를시 전환되는 화면
	public String notice_insert_form() { 
		return common.Common.notice.VIEW_PATH + "notice_insert_form.jsp";
	}
	
	@RequestMapping("/notice_insert.do") // 답변에 입력된 값을 저장
	public String notice_insert(NoticeVO vo) {
		String ip = request.getRemoteAddr();
		vo.setIp(ip);
		notice_dao.notice_insert(vo);
		return "notice_list.do";
	}

	@RequestMapping("/notice_modify_form.do") // 수정누를시 전환되는 화면
	public String notice_modify_form(int idx, Model model) {
		NoticeVO vo = notice_dao.notice_view(idx);
		model.addAttribute("vo", vo);
		return common.Common.notice.VIEW_PATH + "notice_modify_form.jsp";
	}

	@RequestMapping("/notice_modify.do") //수정된 값 저장
	public String notice_modify(NoticeVO baseVo, Model model) {
		String ip = request.getRemoteAddr();
		baseVo.setIp(ip);
		int res = notice_dao.notice_modify(baseVo);
		NoticeVO vo = notice_dao.notice_view(baseVo.getIdx());
		return "notice_view_list.do?+idx="+vo.getIdx()+"&ref="+vo.getRef();
	}
	
	@RequestMapping("/notice_reply_form.do")
	public String notice_reply_form(int idx, Model model) {
		model.addAttribute("idx", idx);
		return common.Common.notice.VIEW_PATH + "notice_reply_form.jsp";
	}
	
	@RequestMapping("/notice_reply.do")
	public String notice_reply(NoticeVO vo) {
		String ip = request.getRemoteAddr();
		vo.setIp(ip);
		
		NoticeVO baseVo = notice_dao.notice_view(vo.getIdx());
		
		notice_dao.reply_step_update(baseVo);
		
		vo.setRef(baseVo.getRef());
		vo.setStep(baseVo.getStep()+1);
		vo.setDepth(baseVo.getDepth()+1);
		
		notice_dao.notice_reply(vo);
		
		return "notice_view_list.do?+idx="+vo.getIdx()+"&ref="+vo.getRef();
	}

	@RequestMapping("/notice_del.do") // 글삭제
	public String notice_del(int idx, Model model) {
		NoticeVO vo = notice_dao.notice_view(idx);
		notice_dao.notice_del(vo);
		return "notice_list.do";
	}
}
