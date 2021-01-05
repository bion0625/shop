<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>상세</title>
		<link rel="stylesheet"
			href="${pageContext.request.contextPath}/resources/css/bus_board.css">
		<script type="text/javascript">
			function modify() {
				var c_pwd = document.getElementById("c_pwd").value.trim();
				
				var pwd = '${vo.pwd}';
				if(pwd == c_pwd){
					location.href='free_modify_form.do?idx=${vo.idx}';
					return;
				}
					alert("비밀번호가 틀립니다");
			}
			function del() {
				var c_pwd = document.getElementById("c_pwd").value;
				var pwd = '${vo.pwd}';
				if(pwd != c_pwd){
					alert("비밀번호가 틀립니다");
					return;
				}
				location.href='free_del.do?idx=${vo.idx}';
			}
		</script>
	</head>
	<body>
	<div id="main_box">
		
		<div id="head">
				<h1>
					<a href="main_home.do" id="home">HOME</a>
				</h1>
	
				<ul id="my_bar">
					<li><a href="#" id="log" onclick="location.href='login_view.do'">로그인</a></li>
					<li><a href="#" id="mypag">마이페이지</a></li>
				</ul>
			</div>
			
			
			<ul id="community_bar">
				<li><a href="#">소개</a>
					<ul>
						<li><a href="#">만든사람들</a></li>
						<li><a href="#">제작사유</a></li>
					</ul>
				</li>
	
				<li><a href="#">버스정보</a></li>
	
				<li>
					<a href="#">커뮤니티</a>
					<ul>
						<li><a href="notice_list.do">공지사항</a></li>
						<li><a href="free_list.do">자유게시판</a></li>
					</ul>
				</li>
	
				<li>
					<a href="#">고객센터</a>
					<ul>
						<li><a href="loss_list.do">분실물센터</a></li>
						<li><a href="qna_list.do">Q &amp; A</a></li>
					</ul>
				</li>
			</ul> 
			<div id="list">
			<div>
				<ul>
					<li>커뮤니티</li>
					<li><a href="notice_list.do">공지사항</a></li>
					<li><a href="free_list.do">자유 게시판</a></li>
				</ul>
			</div>
		<table border="1" align="center" width="750px">
			<caption>::상세::</caption>
			<tr>
				<th width="120" height="25">제목</th>
				<td width="250">${ vo.subject }</td>
			</tr>
			<tr>
				<th width="120" height="25">작성일</th>
				<td width="250">${ vo.regdate }</td>
			</tr>
			<tr>
				<th width="120" height="25">ip</th>
				<td width="250">${ vo.ip }</td>
			</tr>
			<tr>
				<th width="120" height="250">내용</th>
				<td width="250"><pre>${ vo.content }</pre></td>
			</tr>
			<tr>
				<th width="120" height="25">비밀번호</th>
				<td width="250"><input type="password" id="c_pwd"></td>
			</tr>
			<tr>
				<td colspan="3"><input value="목록" type="button" onclick="location.href='free_list.do?page=${param.page}'">
				<input value="수정" type="button" onclick="modify();">
				<input value="삭제" type="button" onclick="del();">
				<input value="답변" type="button" onclick="location.href='free_reply_form.do?idx=${vo.idx}'"></td>
			</tr>
		</table>
		</div>
		</div>
	</body>
</html>