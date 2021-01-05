package com.korea.bus;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import busapi.BusApi;
import dao.BusDAO;
import vo.BnRecordVO;
import vo.StRecordVO;
import vo.StationInfoVO;

@Controller
public class BusController {
	
	
	BusDAO bus_dao;
	
	public BusController(BusDAO bus_dao) {
		this.bus_dao = bus_dao;
	}

	@RequestMapping(value={"/","/main_home.do"})
	public String home(Model model,HttpServletRequest request) {
		
//		System.out.println("들어왔다");
		Map<String , ?> redirectMap = RequestContextUtils.getInputFlashMap(request); // redirect의 값을 map으로 전달받음 
//		System.out.println("실행1");
		List<StationInfoVO> bus_list = null;
		if(redirectMap != null) {
			bus_list = (List<StationInfoVO>) redirectMap.get("bus_list"); // 키값이 bus_list라는 객체의 값을 가져온다.
		}
//		System.out.println("실행2");

		//redirect로 넘어온 api list
//		System.out.println("정류장:"+bus_list.get(0).getStation());
		model.addAttribute("bus_list", bus_list);
//		System.out.println("모델링성공");

		//사용자 정류소,버스이름 검색기록 출력
		List<StRecordVO> st_record_list = bus_dao.st_record_list();
		List<BnRecordVO> bn_record_list = bus_dao.bn_record_list();
		
		model.addAttribute("st_record_list", st_record_list);
		model.addAttribute("bn_record_list", bn_record_list);
		//-------------------------------------------------------------------
//		System.out.println("바딩인 다성공");
		return common.Common.path.VIEW_PATH +"main_home.jsp";
	}

	@RequestMapping("/login_view.do")
	public String login_view() {// 로그인창
		return common.Common.path.VIEW_PATH +"login_view.jsp";
	}
	@RequestMapping("/sign_up.do")
	public String sign_up() {// 회원가입창
		return common.Common.path.VIEW_PATH +"sign_up.jsp";
	}
	
	@RequestMapping("/station_check.do")
	@ResponseBody
	public String st_check(String station) { // 검색을 눌렀을때 있는 정류소인지 체크 있으면 id로 반환한다.
		BusApi bus_api = new BusApi();
		String result = bus_api.busNameCheck(station);
		// 정류소가 있으면 해당 정류소의 id를 반환하고 없으면 no를 반환
		
		String resultStr = String.format("[{'res':'%s'}]", result);
		return resultStr;
	}
	
	@RequestMapping(value = "/bus_search_list.do")
	public String bus_search_list(Model model,RedirectAttributes redirect, String station_id,String busStop_name) { //정류소 id를 받아서 현재 정류소의 도착하는 버스목록 출력
		//회원 기록 db에 정류소 이름 추가 ---------------
		StRecordVO vo = new StRecordVO();
		vo.setBusStop_name(busStop_name);
		vo.setId("AA");
		bus_dao.st_insert_search(vo);
		//------------------------------
		
		//api 정보 가져온후 main_home에 정보를 넘김------------------------------------
//		System.out.println("정류소 아이디:"+station_id); // 잘넘어옴
		BusApi bus_api = new BusApi();
		List<StationInfoVO> list = bus_api.busInfo(station_id);
//		System.out.println("컨트롤러에서 실행된 정류장명:"+list.get(0).getStation()); // 잘넘어옴
		redirect.addFlashAttribute("bus_list", list); //문제점 : F5누르면 값이 사라짐// redirect가 넘기는 맵핑으로 넘김  오류나옴 / flash로 하니까 해결됨 : list로는 넘기지못하는거같음
//		System.out.println("실행됬어");
		//---------------------------------------------------------------------
		
		
		return "redirect:main_home.do"; 
	}
	@RequestMapping("/st_record_del.do")
	@ResponseBody
	public String st_record_del(int idx) {
		int res = bus_dao.st_record_del(idx);
		String result = "no";
		String resultStr = String.format("[{'res':'%s'}]", result);
		
		if(res==0) {
			result = "yes";
			resultStr = String.format("[{'res':'%s'}]", result);
		}
		
		return "resultStr";
	}
	
	
}
