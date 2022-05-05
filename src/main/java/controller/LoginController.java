package controller;

import java.io.IOException;
import java.lang.reflect.Member;
import java.sql.*;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MemberDao;
import vo.*;

@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	
	// 로그인 폼으로 간다.
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 로그인 되어있는 멤버면 리다이렉트
		
		// 다시 로그인 할 수 없게(로그인폼으로 갈수없게) 검사
		HttpSession session = request.getSession();
		String sessionMemberId = (String)session.getAttribute("sessionMemberId"); // 로그인이 되어있으면 null이 아님
		   if(sessionMemberId != null) {
		         // 이미 로그인이 되어있는 상태
		         response.sendRedirect(request.getContextPath()+"/CashBookListByMonthController");
		         return;
		   }
		 // 로그인 되어있는 멤버라면 리다이렉트
		 request.getRequestDispatcher("/WEB-INF/view/Login.jsp").forward(request, response);
	}

	// 로그인 액션으로 간다.
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 로그인 되어있는 멤버면 리다이렉트 
		String memberId = request.getParameter("memberId");
		String memberPw = request.getParameter("memberPw");
		Member member = new Member();
		member.setMemberId(memberId);
		member.setMemberPw(memberPw);
		
		// 모델 호출
		MemberDao memberDao = new MemberDao();
		String returnMemberId = memberDao.selectMemberByIdPw(member);
		if(returnMemberId == null) {
			// 로그인 실패 -> 로그인 컨트롤러를 get방식으로 다시호출 -> 로그인 실패시 로그인 폼을 재요청
			System.out.println("로그인 실패 <-- LoginController.doPost()");
			response.sendRedirect(request.getContextPath()+"/LoginController?=에러");
			return;
		}
		// 로그인 성공
		HttpSession session = request.getSession(); // 현재 연결한 클라이언트(브라우저)에 대한 세션값을 받아옴 -> 그 세션을 저장함
		session.setAttribute("sessionMemberId", returnMemberId);
		response.sendRedirect(request.getContextPath()+"/CashBookListByMonthController");
	}

}

// 맵 List, request.저장소 -> include, forward(두개의 메서드를 호출(서블릿, jsp)), 톰캣
// 세션 : 계속유지하는게 아니고 사용자가 사용할때만 사용(요청할때마다 세션아이디를 주기적으로 비교) -> 정해진 시간이 지났는데 재요청을 안하면 리셋
// C와 M을 같이 사용하고 싶을 때 안에 내용을 저장하는 변수가 request.Attribute이다.