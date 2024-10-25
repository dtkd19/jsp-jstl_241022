package domain;

public class MemberVO {
	private String id;
	private String pwd;
	private String email;
	private String phone;
	private String regdate;
	private String lastlogin;
	private String imageFile;
	
	
	public MemberVO() {}

	// 관리자입장 / 사용자입장
	// 회원등급 : admin(사이트관리자), manager(각 파트의 최고 관리자), user(사용자)
	// 회원가입 / 수정
	public MemberVO(String id, String pwd, String email, String phone, String imageFile) {
		this.id = id;
		this.pwd = pwd;
		this.email = email;
		this.phone = phone;
		this.imageFile = imageFile;
	}
	
	// 로그인
	public MemberVO(String id, String pwd) {
		this.id = id;
		this.pwd = pwd;
	}
		
	// 회원리스트 : 관리자만 접근가능 전체
	
	public MemberVO(String id, String pwd, String email, String phone, String regdate, String lastlogin, String imageFile) {
		this.id = id;
		this.pwd = pwd;
		this.email = email;
		this.phone = phone;
		this.regdate = regdate;
		this.lastlogin = lastlogin;
		this.imageFile = imageFile;
		
	}
		
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getPwd() {
		return pwd;
	}


	public void setPwd(String pwd) {
		this.pwd = pwd;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getRegdate() {
		return regdate;
	}


	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}


	public String getLastlogin() {
		return lastlogin;
	}


	public void setLastlogin(String lastlogin) {
		this.lastlogin = lastlogin;
	}

	
	public String getImageFile() {
		return imageFile;
	}

	public void setImageFile(String imageFile) {
		this.imageFile = imageFile;
	}

	@Override
	public String toString() {
		return "MemberVO [id=" + id + ", pwd=" + pwd + ", email=" + email + ", phone=" + phone + ", regdate=" + regdate
				+ ", lastlogin=" + lastlogin + ", imageFile=" + imageFile + "]";
	}


	
	
	
	
	
	
}
