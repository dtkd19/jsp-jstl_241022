<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3>User info modify</h3>

	<a href="/index.jsp"><button>main</button></a>
	<form action="/mem/info" method="post"  enctype="multipart/form-data">
		image:<br> <img alt="" src="/_fileUpload/${ses.imageFile }"> <br>
		id:<br> <input type="text" name="id" placeholder="id..." value = ${mvo.id } readonly="readonly"><br>
		pwd:<br> <input type="password" name="pwd" placeholder="pwd..." value = ${mvo.pwd }><br>
		email:<br> <input type="text" name="email" placeholder="email..." value = ${mvo.email }><br>
		phone:<br> <input type="text" name="phone" placeholder="phone..." value = ${mvo.phone }><br>
		<input type="hidden" name="imageFile" value="${mvo.imageFile }">
		<input type="file" name="newFile" accept="image/jpeg, image/gif, image/png"> <br> <br>
		<button type="submit">modify</button>
		<a href="/mem/delete?id=${mvo.id }"><button type="button">delete</button></a>
	</form>


	<script type="text/javascript">
		const msg_modify = `<c:out value= "${msg_modify}" />`;
		if(msg_modify == '-1'){
			alert("유저정보수정 실패.");
		}
	</script>

</body>
</html>