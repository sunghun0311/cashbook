import java.util.ArrayList;

public class HashTag {
	public static void main(String[] args) {
		String memo = "안녕하세요 #구디 아카데미 #자바 과정입니다."; // 글자를  띄어쓰기로 분리(공백토큰문자) 분리해서 #붙으면 따로 저장
		// ISSUE
		// 1) #앞에 공백이 없을때 -> "안녕하세요#구디 아카데미 #자바#자바 과정입니다."
		// 2) ##문자가 있을때 -> "안녕하세요 ##구디 아카데이 #자바 과정입니
		
		memo = memo.replace("#", " #"); // 1)해결인데 -> 3)나옴 공백문자가 hashtag 등록된다.
		
		String[] arr = memo.split("");
		
		ArrayList<String> hashtag = new ArrayList<>();
		for(String s : arr) {
			// System.out.println(s);
			if(s.startsWith("#")) {
			// 2)문제해결 #이라는 문자가 있으면 공백으로 만들어버려서 출력할때 #을 없앰
			// hashtag.add(s.substring(1)); 1번째버그 수정
			// hashtag.add(s.replace("#", "")); 2번째버그 수정
				String temp = s.replace("#", ""); // 3번째버그 수정
				if(!temp.equals("")) {
					hashtag.add(temp);
				}
				
			}
					
		}
		
		for(String s : hashtag) {
			System.out.println(s);
		}

	}

}
