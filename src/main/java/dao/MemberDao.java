package dao;

import java.sql.*;

import vo.Member;

public class MemberDao {
	// 회원가입
	// 회원수정update
	// 회원탈퇴delete
	// 회원정보select
	
	// 로그인 -> member를 리턴하는게 제일 좋지만 member안에 아무것도 없다..
	public String selectMemberByIdPw(Member member) {
		String memberId = null; // 위 (Member member)값이랑 같은데 존재하지 않으면 null리턴
		// Pw는 받아오지 않음
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT member_id memberId FROM member WHERE member_id=? AND member_pw=PASSWORD(?)";
		try {
		    Class.forName("org.mariadb.jdbc.Driver");
		    conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook","root","java1234");
		    stmt = conn.prepareStatement(sql);
		    stmt.setString(1, member.getMemberId());
		    stmt.setString(2, member.getMemberPw());
		    rs = stmt.executeQuery();
		    if(rs.next()) {
		        memberId = rs.getString("memberId");
		    }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	   
		return memberId; // 위 (Member member)값이랑 같은데 존재하면 memberId값 리턴
	}
}
