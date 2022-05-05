package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LogoutController
 */
@WebServlet("/LogoutController")
public class LogoutController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().invalidate(); // session 갱신 메서드 : 기존 session을 지우고 새로운  sessioon공간을 부여(생성)
		// session을 지우는것보다 갱신시킴
		/*
		HttpSession session = request.getSession();
		session.invalidate();
		*/
		response.sendRedirect(request.getContextPath()+"/LoginController");
	}



}
