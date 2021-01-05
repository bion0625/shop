<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>로그인</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/login_view1.css">
	</head>
	
	<body>
		<div id="main_box">
			<h1>로그인</h1>
			<div id="user_info">
					<p><input class="text_style" id="user_id" placeholder="아이디 입력"></p>
					<p><input type="password" class="text_style" id="user_pwd" placeholder="비밀번호 입력"></p>	
			</div>
			
			<a href="#" onclick="location.href='sign_up.do'">회원가입</a>
			
			<div id="sign_btn">
				<input class="bottom_btn" type="button" value ="로그인"  >
				<input class="bottom_btn" type="button" value ="취소" onclick="location.href='main_home.do'" >
			</div>
		</div>
	</body>
</html>