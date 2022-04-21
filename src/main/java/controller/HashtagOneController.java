package controller;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.HashtagDao;


@WebServlet("/HashtagOneController")
public class HashtagOneController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String tag = request.getParameter("tag"); // -> 어떻게 클라이언트한데 값을 받아오지? -> 해쉬태그를 누르면 그 값을 받아옴
		System.out.println(tag+ "<-TagController tag");
		
		HashtagDao hashtagDao = new HashtagDao();
		List<Map<String, Object>> list = hashtagDao.hashtagOne(tag);
		request.setAttribute("list", list);
		request.getRequestDispatcher("/WEB-INF/view/HashtagOne.jsp").forward(request, response);
	
	}
}
