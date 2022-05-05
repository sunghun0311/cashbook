
package controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CashBookDao;
import vo.*;



// Dao를 불러옴
@WebServlet("/CashBookListByMonthController")
public class CashBookListByMonthController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		  HttpSession session = request.getSession();
	      String sessionMemberId = (String)session.getAttribute("sessionMemberId");
	      if(sessionMemberId == null) {
	         // 로그인 되지 않은 경우
	         response.sendRedirect(request.getContextPath()+"/LoginController"); // 로그인을 하지않으면 cashbooklist가 아닌 로그인컨트롤러로 감. -> 로그인하지않으면 못들어옴(막아버림)
	         return;
	      }

		// 위 코드는 로그인이 되지 않았을때 다시 LoginController로 돌아가서 로그인을 할 수있게 하는 코드이다.
		// 밑 코드는 로그인이 되었을 때 실행되는 코드이다.
		
		// 1) 월별 가계부 리스트 요청 분석
		// 이번달 년도와 이번달 달 구하는 메서드
		Calendar now = Calendar.getInstance(); // 오늘날짜 2022.04.19
		int y = now.get(Calendar.YEAR);
		int m = now.get(Calendar.MONTH) + 1; // MONTH(월)는 0 - 1월, 1 - 2월, ... 11 - 12월 그래서 + 1    // string과 날짜 계산...
		//current page처럼 안넘어오면 0으로 처리하겠다(현재 페이지로?)
		
		// int today = now.get(Calendar.DATE); // 오늘 날짜(DAY) 오늘 DAY -> 19일
		
		int startBlank = 0; // 일 0, 월 1, ... , 토 6 -> 1일의 요일을 이용하여 구한다.
		
		// 시작시 필요한 공백 <TD>의 갯수를 구하는 알고리즘 -> startBlank
		
		// 오늘날짜가 아닐 수도 있으니 밑 코드에 맞는 날짜부터 우선적으로 구해야함.
		if(request.getParameter("y") != null) {
			y = Integer.parseInt(request.getParameter("y"));			
		}
		if(request.getParameter("m") != null) {
			m = Integer.parseInt(request.getParameter("m"));
				
		}
		if(m==0) { // 나오는 값이 0월이면 전 년도
			m = 12;
			y = y-1;
		}
		if(m==13) { // 나오는 값이 13월이면 다음 년도
			m = 1;
			y = y+1; 
		}
		
		System.out.println(y+" <-- y");
		System.out.println(m+" <-- m");
		
		
		/*
		  1) startBlank
		  2) endDay
		  3) endBlank
		  4) totalBlank 위 3개 다 더함
		  
		 
		 */
		
		// 시작시 필요한 공백 <td>-----------------------------------
		// 시작시 필요한 공백 <TD>의 갯수를 구하는 알고리즘 -> startBlank
		// 오늘날짜에서 1일로 변경하여 구한다. -> 이번달의 1일을 알아야하니까
		// firstDay는 오늘날짜를 먼저 구하여 날짜만 1일로 변경해서 구하자
		Calendar firstDay = Calendar.getInstance(); // 오늘날짜 2022.04.19
		
		// 어떤 날이 올지 모르기에 날짜 구하고 코드가 들어가야함.
		firstDay.set(Calendar.YEAR, y);
		firstDay.set(Calendar.MONTH, m-1); // 자바 달력API는 1월을 0으로, 2월을 1로, ... 12월을 11로 설정되어있다.
		firstDay.set(Calendar.DATE, 1); // 오늘날짜 2022.04.01
		int dayOfWeek = firstDay.get(Calendar.DAY_OF_WEEK);
		// dayOfWeek메서드는 일 1, 월 2, ..., 토 7
		// 그래서
		// startBlank는 	   일 0, 월 1, ..., 토 6이었으면 좋겠다.
		// 1)
		startBlank = dayOfWeek - 1;	// 처음 공백 // 위의 요구사항에 맞게 하기위해서 - 1 해줌
		// 마지막 날짜는 자바 달력API를 이용하여 구하자
		// 2)
		int endDay = firstDay.getActualMaximum(Calendar.DATE); // firstDay달의 제일 큰 숫자 -> 마지막 날짜
		// startBlank와 endDay를 합의 결과에 endBlank를 더해서 !7의 배수가 되도록!
		// 3) 
		int endBlank = 0; // 0이니까 else로 바로??
		if((startBlank+endDay)%7 != 0) { // 나누어 지지않으니까 나머지를 빼기 // 나머지를 0으로 해서(%7 == 0) 7의 배수로 할수도 있다.
	
			// 7에서 startBlank+endDay의 7로 나눈 나머지값을 빼면
			endBlank = 7-((startBlank+endDay)%7);
			
		}
		// 4)
		int totalTd = startBlank + endDay + endBlank;
		
		// 2) 모델값(월별 가계부 리스트)을 반환하는 비지니스로직(모델) 호출
		CashBookDao cashBookDao = new CashBookDao();
		List<Map<String, Object>> list = cashBookDao.selectCashbookListByMonth(y, m, memberId);
		
		/*
		 달력출력에 필요한 모델값(1), 2), 3), 4)) + 데이터베이스에서 반환된 모델값(list, 출력년도(y), 출력월(m)) + 오늘날짜(today)
		  
		 */
		
		// 모델값 CashBookListByMonth로 넘기기 (request.으로 8개를 따로묶어서 넘기거나, Map으로 깔끔하게 넘기기)
		request.setAttribute("startBlank", startBlank);
		request.setAttribute("endDay", endDay);
		request.setAttribute("endBlank", endBlank);
		request.setAttribute("totalTd", totalTd);
		
		request.setAttribute("list", list);
		request.setAttribute("y", y);
		request.setAttribute("m", m); // jsp파일을 넘길 수 있음 
		request.setAttribute("memberId, memberId");
		
		// request.setAttribute("today", today);
		// 값을 넘겼으니 View(Dao)가서 출력
		// m(dao) v(jsp) c(controller)
		
		// 3) 뷰 포워딩
		request.getRequestDispatcher("/WEB-INF/view/CashBookListByMonth.jsp").forward(request,response);
	}

}
