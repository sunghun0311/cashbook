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
	// dao에서 전달받은 controller는 request.setAttribute메서드를 통해서 전달해주는데
	// 이 때 String, Object형 이므로 형 변환을 해줘야한다.
%>
	<h1>tag rank</h1>
	<div> 수입/지출별 검색</div>
	<div> 날짜별 검색</div>
	<!-- 상세보기까지 -->
	<table border="1">
		<tr>
			<th>rank</th>
			<th>tag</th>
			<th>count</th>
		</tr>
		<%
			for(Map<String, Object> map : list) {
		%>
				<tr>
					<td><%=map.get("rank")%></td>
					<td><a href="<%=request.getContextPath()%>/HashtagOneController?tag=<%=map.get("tag")%>"># <%=map.get("tag")%></a></td>
					<td><%=map.get("cnt")%></td>
				</tr>
		<%			
			}
		%>
	</table>
</body>
</html>
