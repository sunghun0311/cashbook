package controller;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.HashtagDao;

@WebServlet("/TagController")
public class TagController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HashtagDao hashtagDao = new HashtagDao(); // Dao를 호출? 선언
		List<Map<String,Object>> list = hashtagDao.selectTagRankList(); // Dao에 저장된 값들(내용)을 list에 저장
		request.setAttribute("list", list);// list값에 저장된 값을 request에 저장 후 넘겨줌
		request.getRequestDispatcher("/WEB-INF/view/TagList.jsp").forward(request, response); // 공유 서블릿과 jsp 저장할때
		// jsp파일이 출력하는 페이지 -> 저장된 내용을 jsp(view)에 보내줌
	}

}

