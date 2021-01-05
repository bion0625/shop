<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>회원가입</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/sign_up.css">
			
	</head>
	
	<body>
		<div id="main_box">
			<h1 id="sign_title">회원가입</h1>
			
			<div id="user_info">
				<div id="input_id">
					<p class="input_title">아이디</p>
					<input class="text_input" name="id" >
				</div>	
				<div id="input_pwd">
					<p class="input_title" >비밀번호</p>
					<input type="password" class="text_input" name="pwd">
				</div>	
				<div id="input_c_pwd">
					<p class="input_title">비밀번호 확인</p>
					<input type="password" class="text_input" name="c_pwd">
				</div>
				<div id="input_name">
					<p class="input_title">이름</p>
					<input class="text_input" name="name">
				</div>	
				<div id="input_tel">
					<p class="input_title">전화번호</p>
					<input class="text_input" name="tel" >
				</div>
				
				<div id="sign_btn">
					<input class="btm_btn" type="button" value="회원가입" >
					<input class="btm_btn" type="button" value="취소" onclick="location.href='login_view.do'">
				</div>
			</div>
			
		</div>
	</body>
</html>