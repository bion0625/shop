<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>버스 노선 검색 메인</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/bus_main2.css">
<script
	src="${pageContext.request.contextPath}/resources/js/httpRequest.js"></script>
<script type="text/javascript">
	var check = 0;
	
	function modal_open() { 
		document.getElementById("modal").style.display = "block";
	}
	function modal_close() {
		/* 검색창 닫으면 값초기화 */
		document.getElementById("text_st").value="";
		document.getElementById("text_n").value = "";
		
		document.getElementById("modal").style.display = "none";
	}
	
	function st_View() {/* 정류소 검색창 클릭시 검색기록 출력*/
		document.getElementById("user_record").style.display="block";
	}
	function st_noView() {
		document.getElementById("user_record").style.display="none";
	}
	
	function bn_View() {/* 버스번호 검색창 클릭시 검색기록 출력*/
		document.getElementById("busNum_record").style.display="block";
	} 
	function bn_noView() {
		document.getElementById("busNum_record").style.display="none";
	}
	
	/* 마우스를 눌렀을때 텍스트 넣기 */
	function st_mouse_down(idx) {
		var st_name = document.getElementById("st_name"+idx).innerText;
		document.getElementById("text_st").value = st_name;
	}
	/* 마우스를 눌렀을때 텍스트 넣기 */
	function bn_mouse_down(idx) {
		var bn_num = document.getElementById("bn_num"+idx).innerText;
		document.getElementById("text_n").value = bn_num;
	}
	
	
	
	function st_check() { // 정류소있는지 체크
		var text_st = document.getElementById("text_st").value.trim(); // trim안먹힘 확인해봐야함
		
		var url = "station_check.do";
		var param = "station="+text_st;
		sendRequest(url,param,st_check_resultFn,"get");
	}
	function st_check_resultFn() { // 있으면 hidden으로 되어있는 input에 정류장id저장한다.
		if(xhr.readyState == 4 && xhr.status==200){
			var text_st = document.getElementById("text_st");
			var data = xhr.responseText;
			var json = eval(data);
			
			if(json[0].res == 'no'){
				alert("없는 정류장입니다.");
				text_st.focus();
				return ;
			}
			alert("조회 가능 정류장입니다.");
			document.getElementById("station_id2").value = json[0].res; 
			check=1;
		}
	}
	
	function search_send(f) { // 넘어간 컨트롤러에서는 station_id만 받음
		var busStop_name = f.busStop_name.value;
		
		if(busStop_name == ""){
			alert("정류소를 입력해주세요");
			return ;
		}
		
		if(check == 0 ){ //유효성검사를 안했으면
			alert("정류소를 검색해주세요.");
			return ;
		}
		
		
		
		f.action = "bus_search_list.do";
		f.method = "get";
		f.submit();
		
		check = 0;
	}
	function st_record_del(idx) {
		if(idx == null){
			alert("오류입니다. 관리자문의");
			return ;
		}
		
		var url = "st_record_del.do";
		var param = "idx="+idx;
		sendRequest(url,param,del_resultFn,"post");
	}
	function del_resultFn() {
		if(xhr.readyState == 4 && xhr.status == 200){
			var data = xhr.responseText;
			var json = eval(data);
			
			if(json[0].res == 'no'){
				alert("삭제실패, 관리자에게 문의하세요");
				return ;
			}
			alert("삭제되었습니다.");
			return ;
		}
	}
	
	
	
	
	
	//지도부분 -----------------------------------------------------------------
	var map;
    var markers = [];
   function initMap() { // 지도를 표시하게해줌
       map = new google.maps.Map(document.getElementById('bus_view'), {
          center: {lat: 37.509502638066365, lng: 126.88897908338905}, //초창기 경로
          zoom: 14
       });

       map.addListener('click', function(e) { //클릭시 명령어
          deleteMarkers();

          var marker = new google.maps.Marker({
             position: e.latLng,
             map: map
          });

          markers.push(marker);

          document.getElementById('lat').value = e.latLng.lat();
          document.getElementById('lng').value = e.latLng.lng();

          //geocodeLatLng(geocoder, map, infowindow);
       });
    }
   
  		 function deleteMarkers() {
	      clearMarkers();
	      markers = [];
	    }

	    function clearMarkers() {
	      setMapOnAll(null);
	    }

	    function setMapOnAll(map) { //더블 클릭시 마커 선택 하게 해줌
	       for (var i = 0; i < markers.length; i++) {
	          markers[i].setMap(map);
	       }
	    }
</script>
<script async defer
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBnbo9n4dfkqvasA9jO4xdgoDeuBywYgeM&callback=initMap">
</script>
</head>

<body>
	<div id="main_box">

		<div id="head">
			<h1>
				<a href="main_home.do" id="home" style="color: black;">HOME${ a }</a>
			</h1>

			<ul id="my_bar">
				<li><a href="#" id="log"
					onclick="location.href='login_view.do'">로그인</a></li>
				<li><a href="#" id="mypag">마이페이지</a></li>
			</ul>
		</div>


		<ul id="community_bar">
			<li><a href="#">소개</a>
				<ul>
					<li><a href="#">만든사람들</a></li>
					<li><a href="#">제작사유</a></li>
				</ul></li>

			<li><a href="#">버스정보</a></li>

			<li><a href="#">커뮤니티</a>
				<ul>
					<li><a href="notice_list.do">공지사항</a></li>
					<li><a href="free_list.do">자유게시판</a></li>
				</ul></li>

			<li><a href="#">고객센터</a>
				<ul>
					<li><a href="loss_list.do">분실물센터</a></li>
					<li><a href="qna_list.do">Q &amp; A</a></li>
				</ul></li>
		</ul>

		<div id="bus_content">
			<div id="bus_view"></div>
			<div id="bus_search_btn"> 
				<input id="busStapName" placeholder="정류장코드 / 버스번호 검색"
			 			onclick="modal_open();" readonly>
					<!--  readonly: 값 입력 못하게 막음 -->
				<input id="search" value="검색" type="button" onclick="modal_open();">
			</div>

			<div id="bus_info">
				<ul id="bus_list">
					<li>
						<p>
							정류소 : ${bus_list[0].station }
							<c:if test="${empty bus_list }">현재등록된 정류소가 없습니다.</c:if>
						</p>
						<ul>
							<c:forEach var="i" items="${bus_list }">
								<li><a href="#" onclick="">${i.bus_name}/</a> <a href="#"
									onclick="">${i.nowBus}/</a> <a href="#" onclick="">${i.nextBus}/</a>
								</li>
							</c:forEach>
						</ul>
					</li>
				</ul>
			</div>
		</div>


		<!-- 검색버튼 누를시 나오는 검색창  -->

		<div id="modal_main">
			<div id="modal">
				<form>
					<div id="modal_content">
						<h1>버스 노선 검색</h1>

						<!-- 정류소 검색 //////////////////////////////////////////////////////////////////////////-->
						<div id="station">
							<ul id="input_station">
								<li><input name="busStop_name" id="text_st"
									onfocus="st_View();" onblur="st_noView();"
									placeholder="검색할 정류소 이름을 입력하세요."> <!-- placeholder : 안내문구, onfocus:텍스트가 클릭됬을때 , onblur: 포커스가 나갔을때-->
									<input type="hidden" id="station_id2" name="station_id">
									<ul id="user_record">
										<!-- 최근 검색어 -->
										<c:forEach var="i" begin="0" end="2" step="1">
											<li><a href="#" id="st_name${st_record_list[i].idx}"
												onMouseDown="st_mouse_down('${st_record_list[i].idx}');">${st_record_list[i].busStop_name}</a>
												<input type="image"
												src="${pageContext.request.contextPath}/resources/image/delete_btn.jpg"
												class="st_del_btn" id="st_del_btn${st_record_list[i].idx }"
												onclick="st_record_del(${st_record_list[i].idx});"></li>
										</c:forEach>
									</ul></li>
								<li><input id="sc_btn_station" class="search_button"
									onclick="st_check();" type="button" value="검색"></li>
							</ul>

						</div>
						<!-- ----------------------------------------------------------------------------------- -->

						<!-- 버스번호 검색----------------------------------------------------------------------- -->
						<div id="bus_num">
							<ul id="input_busNum">
								<li><input name="bus_num" id="text_n" onfocus="bn_View();"
									onblur="bn_noView();" placeholder="버스번호를 입력해주세요.">
									<ul id="busNum_record">
										<!-- 최근 검색어 -->
										<c:forEach var="i" items="${ bn_record_list }">
											<li><a href="#" id="bn_num${i.idx}"
												onMouseDown="bn_mouse_down('${i.idx}');">${ i.bus_num }</a>
												<input type="image"
												src="${pageContext.request.contextPath}/resources/image/delete_btn.jpg"
												class="bn_del_btn" id="bn_del_btn${i.idx }"
												onclick="bn_record_del(${i.idx});"></li>
										</c:forEach>
									</ul></li>
								<li><input id="sc_btn_busNum" class="search_button"
									type="button" value="검색"></li>
							</ul>
						</div>
						<!-- ----------------------------------------------------------------------------------- -->


						<!-- 모달창 닫기 버튼 -->
						<div id="option_btn">
							<input class="op_btn" type="button" value="취소"
								onclick="modal_close();">
							<!-- 메인창으로 돌아가기 -->
							<input class="op_btn" type="button" value="확인"
								onclick="search_send(this.form);">
						</div>
					</div>
				</form>

				<div class="modal_layer"></div>
			</div>
		</div>
	</div>



</body>
</html>