<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>상세</title>
		<link rel="stylesheet"
			href="${pageContext.request.contextPath}/resources/css/bus_board.css">
		<script type="text/javascript">
			function modify(m) {
				var c_pwd = document.getElementById("c_pwd"+m).value.trim();
				
				var pwd = document.getElementById("pwd"+m).value;
				if(pwd == c_pwd){
					location.href='qna_modify_form.do?idx='+m;
					return;
				}
					alert("비밀번호가 틀립니다");
			}
			function del(d) {
				var c_pwd = document.getElementById("c_pwd"+d).value;
				var pwd = document.getElementById("pwd"+d).value;
				if(pwd != c_pwd){
					alert("비밀번호가 틀립니다");
					return;
				}
				location.href='qna_del.do?idx='+d;
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
					<li>고객센터</li>
					<li><a href="loss_list.do">분실물센터</a></li>
					<li><a href="qna_list.do">Q&A</a></li>
				</ul>
			</div>
		
		<table border="1" align="center" width="750px" style="border-collapse: collapse;">
		<caption>::상세::</caption>
		<c:forEach var="vo" items="${ list }" >
		<c:if test="${ vo.step eq 0 }">
			<tr style="background: black; color: white;">
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
			<tr style="background: #aaa;">
				<th width="120" height="25">비밀번호</th>
				<td width="250"><input type="password" id="c_pwd${ vo.idx }"><input type="hidden" id="pwd${ vo.idx }" value="${ vo.pwd }"></td>
			</tr>
			<tr>
				<td align="left">
					<input value="목록으로" type="button" onclick="location.href='loss_list.do?page=${param.page}'">
				</td>
				<td align="right"v>
					<input value="수정" type="button" onclick="modify(${vo.idx});">
					<input value="삭제" type="button" onclick="del(${vo.idx});">
					<input value="댓글쓰기" type="button" onclick="location.href='qna_reply_form.do?idx=${vo.idx}'">
				</td>
			</tr>
		</c:if>
		<c:if test="${ vo.step > 0 }">
			<tr style="background: black; color: white;">
				<th width="120"> 
					<c:if test="${ vo.del_info == 1 }">
							삭제된 댓글입니다
					</c:if>
					<c:if test="${ vo.del_info != 1 }">
					ㄴRe : ${ vo.subject }
				</th>
					</c:if>
				<td width="250" style="padding: 10px;">
				<c:if test="${ vo.del_info == 1 }">
				</c:if>
				<c:if test="${ vo.del_info != 1 }">
				<pre>${ vo.content }</pre>
				</c:if>
				</td>
			</tr>
			<c:if test="${ vo.del_info == 1 }">
			</c:if>
			<c:if test="${ vo.del_info != 1 }">
			<tr style="background: #aaa;">
				<th width="120" height="25">비밀번호</th>
				<td width="250"><input type="password" id="c_pwd${ vo.idx }"><input type="hidden" id="pwd${ vo.idx }" value="${ vo.pwd }"></td>
			</tr>
			<tr align="right">
				<td colspan="2">
					<input value="수정" type="button" onclick="modify(${vo.idx});">
					<input value="삭제" type="button" onclick="del(${vo.idx});">
				</td>
			</tr>

			</c:if>
		</c:if>
		</c:forEach>
		</table>
		</div>
		</div>
	</body>
</html>