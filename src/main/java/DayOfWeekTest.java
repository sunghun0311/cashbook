import java.util.Calendar;

public class DayOfWeekTest {

	public static void main(String[] args) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DATE, 3);
		System.out.println(c.get(Calendar.DAY_OF_WEEK));
		// API 반환하는 요일에 대한 숫자
		// 일 1, 원 2, 화 2, 금 6, 토 7
		
		// startBlank
		// 일 0, 월 1, 화 2, ..., 토 6

	}

}
