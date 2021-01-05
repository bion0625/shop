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
import dao.LossDAO;
import vo.LossVO;

@Controller
public class BusLossController {

	@Autowired
	HttpServletRequest request;
	
	@Autowired
	HttpSession session;
	
	LossDAO loss_dao;
	public void setLoss_dao(LossDAO loss_dao) {
		this.loss_dao = loss_dao;
	}
	
	@RequestMapping("/loss_list.do")
	public String loss_list(Model model, Integer page) {
		
		int nowPage = 1;
		if(page != null) {
			nowPage = page;
		}
		
		int start = (nowPage - 1) * Common.loss.BLOCKLIST + 1;
		int end = start + Common.loss.BLOCKLIST - 1;
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("start", start);
		map.put("end", end);
		
		List<LossVO> list = loss_dao.loss_list(map);
		
		int row_total = loss_dao.getRowTotal();
		
		String pageMenu = Paging.getPaging(
				"loss_list.do", nowPage, row_total, 
				Common.loss.BLOCKLIST, 
				Common.loss.BLOCKPAGE);
		
		model.addAttribute("list", list);
		model.addAttribute("pageMenu", pageMenu);
		
		request.getSession().removeAttribute("show");
		
		return common.Common.loss.VIEW_PATH + "loss_list.jsp";
	}
	
	@RequestMapping("/loss_view_list.do") // 클릭 시 글보여주는 메서드 
	public String loss_view_list(int idx, int ref, Model model) {
		List<LossVO> list = loss_dao.loss_view_list(ref);
		//System.out.println("test:" + vo.getPwd());
		
		session = request.getSession();
		String show = (String)session.getAttribute("show");
		
		if(show == null) {
			loss_dao.loss_update_readhit(idx);
			session.setAttribute("show", "a");
		}
		
		model.addAttribute("list", list);
		return common.Common.loss.VIEW_PATH + "loss_view_list.jsp";
	}
	
	@RequestMapping("/loss_insert_form.do") // 답변 누를시 전환되는 화면
	public String loss_insert_form() { 
		return common.Common.loss.VIEW_PATH + "loss_insert_form.jsp";
	}
	
	@RequestMapping("/loss_insert.do") // 답변에 입력된 값을 저장
	public String loss_insert(LossVO vo) {
		String ip = request.getRemoteAddr();
		vo.setIp(ip);
		loss_dao.loss_insert(vo);
		return "loss_list.do";
	}

	@RequestMapping("/loss_modify_form.do") // 수정누를시 전환되는 화면
	public String loss_modify_form(int idx, Model model) {
		LossVO vo = loss_dao.loss_view(idx);
		model.addAttribute("vo", vo);
		return common.Common.loss.VIEW_PATH + "loss_modify_form.jsp";
	}

	@RequestMapping("/loss_modify.do") //수정된 값 저장
	public String loss_modify(LossVO baseVo, Model model) {
		String ip = request.getRemoteAddr();
		baseVo.setIp(ip);
		int res = loss_dao.loss_modify(baseVo);
		LossVO vo = loss_dao.loss_view(baseVo.getIdx());
		return "loss_view_list.do?+idx="+vo.getIdx()+"&ref="+vo.getRef();
	}
	
	@RequestMapping("/loss_reply_form.do")
	public String loss_reply_form(int idx, Model model) {
		model.addAttribute("idx", idx);
		return common.Common.loss.VIEW_PATH + "loss_reply_form.jsp";
	}
	
	@RequestMapping("/loss_reply.do")
	public String loss_reply(LossVO vo) {
		String ip = request.getRemoteAddr();
		vo.setIp(ip);
		
		LossVO baseVo = loss_dao.loss_view(vo.getIdx());
		
		loss_dao.reply_step_update(baseVo);
		
		vo.setRef(baseVo.getRef());
		vo.setStep(baseVo.getStep()+1);
		vo.setDepth(baseVo.getDepth()+1);
		
		loss_dao.loss_reply(vo);
		
		return "loss_view_list.do?+idx="+vo.getIdx()+"&ref="+vo.getRef();
	}

	@RequestMapping("/loss_del.do") // 글삭제
	public String loss_del(int idx, Model model) {
		LossVO vo = loss_dao.loss_view(idx);
		loss_dao.loss_del(vo);
		return "loss_list.do";
	}
}
