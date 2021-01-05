<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>분실물 게시판</title>
		<link rel="stylesheet"
			href="${pageContext.request.contextPath}/resources/css/bus_board.css">
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
		<table width="750px" border="1" align="center">
			<caption>공지사항</caption>
				<tr>
					<th width="60%" align="center">제목</th>
					<th align="center">작성일</th>
					<th align="center">조회수</th>
				</tr>
			<c:forEach var="vo" items="${ list }">
				<c:if test="${ vo.depth == 0 }">
				<tr>
					<td>
						
						<c:if test="${ vo.del_info == 1 }">
							삭제된 글입니다
						</c:if>
						
							<c:if test="${ vo.del_info != 1 }">
								<a href="free_view_list.do?idx=${ vo.idx }&ref=${vo.ref}&page=${empty param.page ? 1 : param.page}">${ vo.subject }</a>
							</c:if>
					</td>
					<td align="center">${ vo.regdate }</td>
					<td align="center">${ vo.readhit }</td>
				</tr>
				</c:if>
			</c:forEach>
			
			<tr>
				<td colspan="3" align="center">
					${ pageMenu }
				</td>
			</tr>
			
			<!-- 게시글이 한 개도 없는 경우 -->
			<c:if test="${ empty list }">
				<tr>
					<td colspan="3" align="center">
						현재 등록된 게시물이 없습니다.
					</td>
				</tr>
			</c:if>
			
			<tr>
				<td colspan="3"><input type="button" value="글쓰기" onclick="location.href='free_insert_form.do'"></td>
			</tr>
		</table>
		</div>
	</div>
	</body>
</html>