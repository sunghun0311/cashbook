package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CashBookDao;
import vo.CashBook;
// 전달하는 역할?
@WebServlet("/CashBookOneController")
public class CashBookOneController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Dao호출
		CashBookDao cashbookDao = new CashBookDao();
		// view에서 요청값 받아오기
		int cashbookNo = Integer.parseInt(request.getParameter("cashbookNo"));
		System.out.println(cashbookNo + "<- CashBookOneController");
		
		// cashbook에 selectCashBookOne(Dao)값을 저장
		CashBook cashbook = cashbookDao.selectCashBookOne(cashbookNo);
		request.setAttribute("cashbook", cashbook); // request.getAttribute에 담아서 jsp파일로 보냄(보냄)
		
		// 뷰 포워딩
		request.getRequestDispatcher("/WEB-INF/view/CashBookOne.jsp").forward(request, response);
	}

}
