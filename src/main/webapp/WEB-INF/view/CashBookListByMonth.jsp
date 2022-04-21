<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.util.*" %>
<!DOCTYPE html>
<html>
</head>
<meta charset="UTF-8">
<!-- 부트스트랩 -->
   <!-- Latest compiled and minified CSS -->
   <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
   <!-- jQuery library -->
   <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.slim.min.js"></script>
   <!-- Popper JS -->
   <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
   <!-- Latest compiled JavaScript -->
   <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body class="container">
   <%
   		// CashBookListByMonthController에 요청값 받아오기
		List<Map<String, Object>> list = (List<Map<String, Object>>)request.getAttribute("list"); // list는 현재 출력못하고, size로 확인
		int y = (Integer)request.getAttribute("y"); // 
		int m = (Integer)request.getAttribute("m");
		
		// int today = (Integer)request.getAttribute("today");
		int startBlank = (Integer)request.getAttribute("startBlank");
		int endDay = (Integer)request.getAttribute("endDay");
		int endBlank = (Integer)request.getAttribute("endBlank");
		int totalTd = (Integer)request.getAttribute("totalTd");
		
		
		// 이제는 출력한 곳도 포함해서 디버깅하기! -> 어디서 출력한건지 위치값(안내문구)
		System.out.println(list.size() + " <- list.size() CashBookListByMonth.jsp"); // list는 현재 출력못하고, size로 확인
		System.out.println(y + " <- y CashBookListByMonth.jsp");
		System.out.println(m + " <- m CashBookListByMonth.jsp");
		
		// System.out.println(today + " <- today CashBookListByMonth.jsp");
		System.out.println(startBlank + " <- startBlank CashBookListByMonth.jsp");
		System.out.println(endDay + " <- endDay CashBookListByMonth.jsp");
		System.out.println(endBlank + " <- endBlank CashBookListByMonth.jsp");
		System.out.println(totalTd + " <- totalTd CashBookListByMonth.jsp");
	%>
	<div>
		<a href="<%=request.getContextPath() %>/TagController">tags</a>
	</div>
	<h2><%=y%>년 <%=m%>월</h2>
	<div>
		<a href="<%=request.getContextPath() %>/CashBookListByMonthController?y=<%=y%>&m=<%=m-1%>">이전달</a>
		<a href="<%=request.getContextPath() %>/CashBookListByMonthController?y=<%=y%>&m=<%=m+1%>">다음달</a>
	</div>
	<!-- 
		1) 이번달 1일의 요일 firstDayYoil
		2) 요일 -> startBlank -> 일 0, 월 1, 화 2, ... 토 6 // 요일을 통해서 몇카이 비어있는지 구함
		3) 이번달 마지막 날짜 endDay
		4) endtotalBlank -> totalBlank
		5) td의 갯수 1 ~ totalBlank
					+
		6) 가계부 list
		7) 오늘 날짜
	 -->
	 <table class="table table-bordered table-striped">
	   	 <thead>
			<tr>
				<th>일</th>
				<th>월</th>
				<th>화</th>
				<th>수</th>
				<th>목</th>
				<th>금</th>
				<th>토</th>
			</tr>
		</thead>
		<tbody>
	<!-- 달력 형상 만들기, 하나하나 천천히 업데이트 -->
		<tr>
			<%
				for(int i=1; i<=totalTd; i+=1) {
					if((i-startBlank) > 0 && (i-startBlank) <= endDay) {
						String c ="";
						if(i%7==0) {
							c = "text-primary";
						} else if(i%7==1) {
							c = "text-danger";
						}								
			%>
						<td class="<%=c%>">
                     	   	<%=i-startBlank%>
                      	   	<a href="<%=request.getContextPath()%>/InsertCashBookController?y=<%=y%>&m=<%=m%>&d=<%=i-startBlank%>" class="btn btn-light">입력</a>
                     	   	<div><!-- a태그는 get방식, form으로 넘김 -->                     	   	
                     	   		<%
                     	   			// 해당 날짜의 cashbook 목록 출력
                     	   			for(Map map : list) {	
                     	   				if((Integer)map.get("day") == (i-startBlank)) {
                     	   		%>
                     	   					<div>
                     	   						<a href="<%=request.getContextPath()%>/CashBookOneController?cashbookNo=<%=map.get("cashbookNo")%>">
		                     	   					[<%=map.get("kind")%>] 
		                     	   					<%=map.get("cash")%>원
		                     	   					<%=map.get("memo")%>...
		                     	   				</a>	
                     	   					</div>
                     	   		<%			
                     	   				}
                     	   			}
                     	   		%>
                     	   	</div>
                     </td>
            <%
                  } else {
            %>
                     <td>&nbsp;</td>
            <%         
                  }
                  if(i<totalTd && i%7==0) {
            %>

				<!-- 날짜값을 가져와서 날짜를 비교하고 값을 입혀야함 -->
						</tr><tr><!-- 새로운 행을 추가시키기 위해서 -->
			<%					
					}
				}
			%>
		</tr>
		</tbody>
		<!--  어떤 데이터가 들어가있는지 보는 코드
		
			 for(Map map : list) {			
					
					map.get("cashbookNo")
					map.get("day")
					map.get("kind")
					map.get("cash") 
					map.get("memo") 							
		
			}
		-->
	</table>
</body>
</html>