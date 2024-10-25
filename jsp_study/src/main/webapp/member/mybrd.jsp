<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3>내가 쓴 글</h3>

<table>
		<thead>
		<tr>
		<th>no.</th>
		<th>title</th>
		<th>writer</th>
		<th>regdate</th>
		<th>readCount</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${boardList}" var="bvo">
			<tr>
			<td>${bvo.bno }</td>
			<td><a href="/brd/detail?bno=${bvo.bno }">
			<img alt="" src="/_fileUpload/_th_${bvo.imageFile }">
			${bvo.title }</a></td>
			<td>${bvo.writer }</td>
			<td>${bvo.regdate }</td> 
			<td>${bvo.readCount }</td>
			</tr>
		</c:forEach>
		
		</tbody>
	
	</table>

	<a href="/index.jsp"><button>main</button></a>

</body>
</html>