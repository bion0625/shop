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
import dao.FreeDAO;
import vo.FreeVO;

@Controller
public class BusFreeController {

	@Autowired
	HttpServletRequest request;
	
	@Autowired
	HttpSession session;
	
	FreeDAO free_dao;
	public void setFree_dao(FreeDAO free_dao) {
		this.free_dao = free_dao;
	}
	
	@RequestMapping("/free_list.do")
	public String free_list(Model model, Integer page) {
		
		int nowPage = 1;
		if(page != null) {
			nowPage = page;
		}
		
		int start = (nowPage - 1) * Common.free.BLOCKLIST + 1;
		int end = start + Common.free.BLOCKLIST - 1;
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("start", start);
		map.put("end", end);
		
		List<FreeVO> list = free_dao.free_list(map);
		
		int row_total = free_dao.getRowTotal();
		
		String pageMenu = Paging.getPaging(
				"free_list.do", nowPage, row_total, 
				Common.free.BLOCKLIST, 
				Common.free.BLOCKPAGE);
		
		model.addAttribute("list", list);
		model.addAttribute("pageMenu", pageMenu);
		
		request.getSession().removeAttribute("show");
		
		return common.Common.free.VIEW_PATH + "free_list.jsp";
	}
	
	@RequestMapping("/free_view_list.do") // 클릭 시 글보여주는 메서드 
	public String free_view_list(int idx, int ref, Model model) {
		List<FreeVO> list = free_dao.free_view_list(ref);
		//System.out.println("test:" + vo.getPwd());
		
		session = request.getSession();
		String show = (String)session.getAttribute("show");
		
		if(show == null) {
			free_dao.free_update_readhit(idx);
			session.setAttribute("show", "a");
		}
		
		model.addAttribute("list", list);
		return common.Common.free.VIEW_PATH + "free_view_list.jsp";
	}
	
	@RequestMapping("/free_insert_form.do") // 답변 누를시 전환되는 화면
	public String free_insert_form() { 
		return common.Common.free.VIEW_PATH + "free_insert_form.jsp";
	}
	
	@RequestMapping("/free_insert.do") // 답변에 입력된 값을 저장
	public String free_insert(FreeVO vo) {
		String ip = request.getRemoteAddr();
		vo.setIp(ip);
		free_dao.free_insert(vo);
		return "free_list.do";
	}

	@RequestMapping("/free_modify_form.do") // 수정누를시 전환되는 화면
	public String free_modify_form(int idx, Model model) {
		FreeVO vo = free_dao.free_view(idx);
		model.addAttribute("vo", vo);
		return common.Common.free.VIEW_PATH + "free_modify_form.jsp";
	}

	@RequestMapping("/free_modify.do") //수정된 값 저장
	public String free_modify(FreeVO baseVo, Model model) {
		String ip = request.getRemoteAddr();
		baseVo.setIp(ip);
		int res = free_dao.free_modify(baseVo);
		FreeVO vo = free_dao.free_view(baseVo.getIdx());
		return "free_view_list.do?+idx="+vo.getIdx()+"&ref="+vo.getRef();
	}
	
	@RequestMapping("/free_reply_form.do")
	public String free_reply_form(int idx, Model model) {
		model.addAttribute("idx", idx);
		return common.Common.free.VIEW_PATH + "free_reply_form.jsp";
	}
	
	@RequestMapping("/free_reply.do")
	public String free_reply(FreeVO vo) {
		String ip = request.getRemoteAddr();
		vo.setIp(ip);
		
		FreeVO baseVo = free_dao.free_view(vo.getIdx());
		
		free_dao.reply_step_update(baseVo);
		
		vo.setRef(baseVo.getRef());
		vo.setStep(baseVo.getStep()+1);
		vo.setDepth(baseVo.getDepth()+1);
		
		free_dao.free_reply(vo);
		
		return "free_view_list.do?+idx="+vo.getIdx()+"&ref="+vo.getRef();
	}

	@RequestMapping("/free_del.do") // 글삭제
	public String free_del(int idx, Model model) {
		FreeVO vo = free_dao.free_view(idx);
		free_dao.free_del(vo);
		return "free_list.do";
	}
}
