package vo;

public class Member {
	private String memberId;
	private String memberPw;
	private String createDate;
	@Override
	public String toString() {
		return "Member [memberId=" + memberId + ", memberPw=" + memberPw + ", createDate=" + createDate + "]";
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemverId(String memberId) {
		this.memberId = memberId;
	}
	public String getMemberPw() {
		return memberPw;
	}
	public void setMemberPw(String memberPw) {
		this.memberPw = memberPw;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	// 1회용 vo는 맵 -> 맵은 키를 바꿀수있음
}
