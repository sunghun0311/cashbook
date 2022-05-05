<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	List<Map<String, Object>> list = (List<Map<String, Object>>)request.getAttribute("list");
	// controller에서 list에 저장한 Map데이터 값들을 request.getAttribute를 통해 가져옴.
%>
	<h1>해쉬태그 상세보기</h1>
	<a href="<%=request.getContextPath()%>/TagController" class="btn btn-outline-info">tags</a><!-- controller로 돌아가기 -->
	<table border="1">
		<tr>
			<th>해시태그</th>
			<th>날짜</th>
			<th>수입/지출</th>
			<th>메모</th>
		</tr>
		<%
			for(Map<String, Object> map : list) { // list의 값들을 순서대로 반복
		%>
		<tr>
			<td><%=map.get("tag")%></td>
			<td><%=map.get("cashDate")%></td>
			<td><%=map.get("kind")%></td>
			<td><%=map.get("memo")%></td>
		</tr>
		<%
			}
		%>
	</table>
</body>
</html>