<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>회원가입 Page</h1>
	
	<form action="/mem/register" method="post" enctype="multipart/form-data">
	id:<br>
	<input type="text" name="id" placeholder="id..."><br>
	pwd:<br>
	<input type="password" name="pwd" placeholder="pwd..."><br>
	email:<br>
	<input type="text" name="email" placeholder="email..."><br>
	phone:<br>
	<input type="text" name="phone" placeholder="phone...">
	file: <br>
	<input type="file" name="imageFile" accept="image/jpeg, image/gif, image/png"><br>
	<button type="submit">join</button>
	</form>	
	
	
	
</body>
</html>