package dao;
import java.util.*;
import javax.servlet.RequestDispatcher;
import vo.*;
import java.sql.*;
// 모델(Dao)
public class CashBookDao {
	public void insertCashbook(CashBook cashbook, List<String> hashtag, String memberId) { // Insert지만 result가 필요
		Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    try {
	         Class.forName("org.mariadb.jdbc.Driver");
	         conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook","root","java1234");
	         conn.setAutoCommit(false); // 자동커밋을 해제
	         
	         String sql = "INSERT INTO cashbook(cash_date,kind,cash,memo,update_date,create_date,member_id)"
	        		 + " VALUES(?,?,?,?,NOW(),NOW(),?)";
	         
	         // INSERT쿼리 실행 후 자동 증가 키값 받아오기 JDBC API
	         // insert + select -> 입력(insert)하자마자 방금 생성된 행의 키값 select(누가 보기도전에)
	         // ex) select 방금입력한 cashbook_no from cashbook;
	         stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
	         stmt.setString(1, cashbook.getCashDate());
	         stmt.setString(2, cashbook.getKind());
	         stmt.setInt(3, cashbook.getCash());
	         stmt.setString(4, cashbook.getMemo());
	         stmt.executeUpdate(); // insert만 실행하는 거
	         rs = stmt.getGeneratedKeys(); // select를 실행 // select 방금입력한 cashbook_no from cashbook 
	         // -> cashNo에 방금입력한 1번이 들어감
	         int cashbookNo = 0;
	         if(rs.next()) {
	        	 cashbookNo = rs.getInt(1);
	         }
	         
	         // hashtag를 저장하는 코드
	         PreparedStatement stmt2 = null; // Insert만
	         for(String h : hashtag) {  	 
	        	  String sql2 = "INSERT INTO hashtag(cashbook_no, tag, create_date) VALUES(?, ?, NOW())";       
	        	  stmt2 = conn.prepareStatement(sql2);
	        	  stmt2.setInt(1,  cashbookNo);	        		  
	        	  stmt2.setString(2, h);
	        	  stmt2.execute();
	         }
   
	         conn.commit();
	    } catch (Exception e) {
	    	try {
	    		conn.rollback();
	    	} catch (SQLException e1) {
	    		e1.printStackTrace();
	    	} 
	    	e.printStackTrace();
	    } finally {
	         try {
	        	 conn.close();          
	         } catch (SQLException e) {
	            e.printStackTrace();
	         }   
	    }
	}
	
	public List<Map<String, Object>> selectCashbookListByMonth(int y, int m, String memberId) { // y=year, m=month // <cashbook>으로 받을필요가없어서 Map타입으로
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		/*
			SELECT
				cashbook_no cashbookNo
				,DAY(cash_date) day
				
				,kind
				,cash // 뽑을것들
				,LEFT(memo, 5) memo
			From cashbook
			WHERE YEAR(cash_date) = ? AND MONTH(cash_date) = ?
			ORDER BY DAY(cash_date) ASC, kind ASC
		 */
	      Connection conn = null;
	      PreparedStatement stmt = null;
	      ResultSet rs = null;
	      String sql = "SELECT"
	            + "          cashbook_no cashbookNo"
	            + "          ,DAY(cash_date) day"
	            + "          ,kind"
	            + "          ,cash"
	            + "          ,LEFT(memo, 5) memo"		
	            + "       FROM cashbook"
	            + "       WHERE YEAR(cash_date) = ? AND MONTH(cash_date) = ? AND member_id=?"
	            + "       ORDER BY DAY(cash_date) ASC, kind ASC";
	      try {
	         Class.forName("org.mariadb.jdbc.Driver");
	         conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook","root","java1234");
	         stmt = conn.prepareStatement(sql);
	         stmt.setInt(1, y);
	         stmt.setInt(2, m);
	         stmt.setString(3, memberId);
	         rs = stmt.executeQuery();
	         while(rs.next()) {
	            Map<String, Object> map = new HashMap<String, Object>();
	            map.put("cashbookNo", rs.getInt("cashbookNo"));
	            map.put("memberId", rs.getString("memberId"));
	            map.put("day", rs.getInt("day"));
	            map.put("kind", rs.getString("kind"));
	            map.put("cash", rs.getInt("cash"));
	            map.put("memo", rs.getString("memo"));
	            list.add(map);
	         }
	      } catch (Exception e) {
	         e.printStackTrace();
	      } finally {
	         try {
	        	 rs.close();
	        	 stmt.close();
	        	 conn.close();
	         } catch (SQLException e) {
	            e.printStackTrace();
	         }
	      }
	      return list;
	   }
	public CashBook selectCashBookOne(int cashbookNo) {
		  CashBook cashbook = null; // return할 Cashbook을 변수(cashbook)에 저장, 초기화
		  Connection conn = null;
	      PreparedStatement stmt = null;
	      ResultSet rs = null;
	      
	      try {
	    	  Class.forName("org.mariadb.jdbc.Driver"); // 로그인 정보를 가지고 있는 DBUtil을 만들어 사용가능
	    	  conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/cashbook","root","java1234");
	    	  String sql = "SELECT cashbook_no cashbookNo, cash_date cashDate, kind, cash, memo, create_date createDate, update_date updateDate From cashbook WHERE cashbook_no = ?";
	    	  stmt = conn.prepareStatement(sql);
	    	  stmt.setInt(1, cashbookNo); // sql(쿼리문)에 있는 1번째 물음표는 cashbookNo이다.
	    	  rs = stmt.executeQuery();
	    	  
	    	  // 쿼리를 실행
	    	  if(rs.next()) {
	    		  cashbook = new CashBook(); // cashbook에 새로운 객체생성한걸 저장
	    		  cashbook.setCashbookNo(rs.getInt("cashbookNo")); // * 쿼리에 없으면 넘어가긴 넘어가도 DB에서 값을 안받아와서 null로 넘어감. cashbook_no cashbook_no,  cashbook_no cashbookNo cashbookNo 이렇게 하면됨.
	    		  cashbook.setCashDate(rs.getString("cashDate"));
	    		  cashbook.setKind(rs.getString("kind"));
	    		  cashbook.setCash(rs.getInt("cash"));
	    		  cashbook.setMemo(rs.getString("memo"));
	    		  cashbook.setUpdateDate(rs.getString("updateDate"));
	    		  cashbook.setCreateDate(rs.getString("createDate"));	    		  
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
		
		
		return cashbook;
			}
	}