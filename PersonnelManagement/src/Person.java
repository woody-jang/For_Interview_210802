import java.io.Serializable;

public class Person implements Serializable {
	private static final long serialVersionUID = 1L;

	static String[] departmentList = { "인사팀", "개발팀", "디자인팀", "영업팀", "생산팀", "자재팀" };
	static String[] positionList = { "사원", "대리", "과장", "차장", "부장", "이사", "상무", "전무", "대표" };
	private String name;
	private String birth; // 생년월일 8자리
	private String joinDate; // 입사일 8자리
	private int department; // 0 : 인사팀, 1 : 개발팀, 2 : 디자인팀, 3 : 영업팀, 4 : 생산팀, 5 : 자재팀
	private int position; // 0 : 사원, 1 : 대리, 2 : 과장, 3 : 차장, 4 : 부장, 5 : 이사, 6 : 상무, 7 : 전무, 8 : 대표
	private String idNumber; // 사번 : 입사일6자리 - 부서코드 - 001 순서대로

	public Person(String name, String birth, String joinDate, int department, int position) {
		this.name = name;
		this.birth = birth;
		this.joinDate = joinDate;
		this.department = department;
		this.position = position;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}

	public int getDepartment() {
		return department;
	}

	public void setDepartment(int department) {
		this.department = department;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

}
