<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script type="text/javascript">
	 var count = 0;
	function send2(f) {
		var ddd = f.ddd.value;
		count += 1;
		alert(count);
	}
</script>

</head>
<body>
	<form>
		<div>
			<input name="ddd" value="ddd"><br>
			<input name="eee" value="eee"><br>
			<input name="fff" value="fff"><br>
			<input type="button" value="가자" onclick="send(this.form)"><br>
		</div>			
		<hr>
		<form>
			<div>
				<input name="aaa" value="aaa"><br>
				<input name="bbb" value="bbb"><br>
				<input name="ccc" value="ccc"><br>
				<input type="button" value="가자" onclick="send2(this.form)"><br>
			</div>
		</form>
		
	</form>
</body>
</html>