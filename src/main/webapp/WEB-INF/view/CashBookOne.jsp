<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "vo.CashBook" %>
<%@ page import = "java.util.*" %>
<%
CashBook cashbook = new CashBook(); // cashbook으로 변수 -> 모든값이 안에 변수에 저장
	cashbook = (CashBook)request.getAttribute("cashbookOne"); // controller에서 받아옴
	// request.getAttribute에 저장한 값을 불러옴(받음)
	// type이 Object이므로 형변환을 시켜줘야한다.
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div>
		<h1>가계부 상세정보</h1>
		<table border="1">
			<thead>
				<th>cashbookNo</th>
				<th>cashDate</th>
				<th>kind</th>
				<th>cash</th>
				<th>memo</th>
				<th>updateDate</th>
				<th>createDate</th>
			</thead>	
			<tbody>
				<tr>
					<td><%=cashbook.getCashbookNo() %></td>
					<td><%=cashbook.getCashDate() %></td>
					<td><%=cashbook.getKind() %></td>
					<td><%=cashbook.getCash() %></td>
					<td><%=cashbook.getMemo() %></td>
					<td><%=cashbook.getUpdateDate() %></td>
					<td><%=cashbook.getCreateDate() %></td>
				</tr>
			</tbody>					
		</table>	
		<div>
			<a href="<%=request.getContextPath()%>/DeleteCashbookController?cashbookNo=<%=cashbook.getCashbookNo()%>">삭제</a>
			<a href="<%=request.getContextPath()%>/UpdateCashbookController">수정</a>
		</div>
	</div>
</body>
</html>