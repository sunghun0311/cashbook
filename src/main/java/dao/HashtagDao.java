package dao;

import java.util.*;
import java.sql.*;

public class HashtagDao {
	   public List<Map<String,Object>> selectTagRankList() {
		      List<Map<String,Object>> list = new ArrayList<>();
		      Connection conn = null;
		      PreparedStatement stmt = null;
		      ResultSet rs = null;
		      try {
		         /*
		             SELECT t.tag, t.cnt, RANK() over(ORDER BY t.cnt DESC) ranking
		            FROM 
		            (SELECT tag, COUNT(*) cnt
		            FROM hashtag
		            GROUP BY tag) t
		          */
		         Class.forName("org.mariadb.jdbc.Driver");
		         conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook","root","java1234");
		         String sql = "SELECT t.tag, t.cnt, RANK() over(ORDER BY t.cnt DESC) rank"
		               + "            FROM"
		               + "            (SELECT tag, COUNT(*) cnt"
		               + "            FROM hashtag"
		               + "            GROUP BY tag) t";
		         stmt = conn.prepareStatement(sql);
		         rs = stmt.executeQuery();
		         while(rs.next()) {
		            Map<String, Object> map = new HashMap<>();
		            map.put("tag", rs.getString("tag"));
		            map.put("cnt", rs.getInt("t.cnt"));
		            map.put("rank", rs.getInt("rank"));
		            list.add(map);
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
		      return list;
		   }

	public List<Map<String, Object>> hashtagOne(String tag) { // 받아오는값이 tag -> 작업(가공)시작
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(); // 새로운 객체를 선언, Map에 저장되어있는 값(데이터)을 list에 저장
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		// 쿼리문 작성
		String sql = "SELECT h.tag tag, c.cash_date cashDate, c.kind kind, c.cash cash, c.memo memo"
				+ " 	FROM cashbook c INNER JOIN hashtag h"
				+ "		ON c.cashbook_no=h.cashbook_no"
				+ "		WHERE h.tag=?"
				+ " 	ORDER BY c.cash_date DESC";
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook","root","java1234");
			stmt = conn.prepareStatement(sql); // 쿼리문 실행
			stmt.setString(1, tag); // 물음표에 들어갈 값
			rs = stmt.executeQuery(); // 데이터베이스에서 데이터를 가져와서 rs에 저장(select문에만 가능) 
			// rs(ResultSet)는 executeQuery를 통해 쿼리를 실행할시 rs타입으로 반환을 해주어 저장이 가능 
	         while(rs.next()) { // 반복문 실행(다음 rs값 문자열로 return)
	             Map<String, Object> map = new HashMap<>(); // Map에 들어있는 데이터들을
	             map.put("tag", rs.getString("tag"));
	             map.put("cashDate", rs.getString("cashDate"));
	             map.put("kind", rs.getString("kind"));
	             map.put("memo", rs.getString("memo"));
	             list.add(map); // map에 위 데이터를(반복된 데이터들) 추가
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
		
		return list; // Dao에서 처리한 데이터 값들을 list로 저장 후 반환하여 다시 Controller로 반환(돌려 보내줌).
	}

	   
	   
	}