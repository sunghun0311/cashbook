package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
// a태그로 받아서 get, Action할거니 post도
// 포워딩? 서블릿?
// parameter / attribute
// 계산은 모델(Dao)에서

import dao.CashBookDao;
import vo.CashBook;

// 포워드 덕환님의 결과님을 받아서 주는거고
// 인클루드 한번에 준다?

@WebServlet("/InsertCashBookController")
public class InsertCashBookController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String y = request.getParameter("y");
		String m = request.getParameter("m");
		String d = request.getParameter("d");
		String cashDate = y+"-"+m+"-"+d; // 지역변수라서 밑 주소에서 쓰질 못해 request.setAttribute안에 넘어줌
		request.setAttribute("cashDate", cashDate);		
		request.getRequestDispatcher("/WEB-INF/view/InsertCashBookForm.jsp").forward(request, response);
	}

	// Form에서 넘겨받기 (중요*메모값 분리 해쉬태그)
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String cashDate = request.getParameter("cashDate");
		String kind = request.getParameter("kind");
		int cash = Integer.parseInt(request.getParameter("cash"));
		String memo = request.getParameter("memo");
		
		System.out.println(cashDate + "<-- cashDate InsertCashBookController.doPost()");
		System.out.println(kind + "<-- kind InsertCashBookController.doPost()");
		System.out.println(cash + "<-- cash InsertCashBookController.doPost()");
		System.out.println(memo + "<-- memo InsertCashBookController.doPost()");
		
		CashBook cashbook = new CashBook();
		cashbook.setCashDate(cashDate);
		cashbook.setKind(kind);
		cashbook.setCash(cash);
		cashbook.setMemo(memo);
		
		
		List<String> hashtag = new ArrayList<>(); // hashtag 묶기
		String memo2 = memo.replace("#", " #");
		String[] arr = memo2.split(" ");// 바뀐메모를 공백문자로 자르기
		for(String s : arr) {
			if(s.startsWith("#")) {
				String temp = s.replace("#", "");
				if(temp.length()>0) {
					hashtag.add(temp);
				}
			}
		}
		System.out.println(hashtag.size() + "<-hashtag.size InsertCashBookController.doPost()");
		for(String h : hashtag) {
			System.out.println(h + "<-hashtag InsertCashBookController.doPost()");
		}
		
		CashBookDao cashbookDao = new CashBookDao();
		cashbookDao.insertCashbook(cashbook, hashtag);
		
		response.sendRedirect(request.getContentType()+"/CashBookListByMonthController");
	}

}
