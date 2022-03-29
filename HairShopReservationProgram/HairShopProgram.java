import java.time.LocalDateTime;	//날짜값을 표현하기 위한 time의 내장클래스 사용
import java.time.format.DateTimeFormatter;	//날짜를 원하는 형식대로 출력하기 위해 사용
import java.util.List;	   //List로 여러 Customer를 관리
import java.util.ArrayList;	//Customer를 List로 관리하기 위해 ArrayList 사용

abstract class Reservation { // 예약 추상클래스

	protected Customer customer; // 고객객체 타입의 고객객체
	protected LocalDateTime date; // LocalDateTime타입의 날짜변수

	public Reservation(Customer customer, LocalDateTime date) { // 고객 객체, 날짜
		this.customer = customer;
		this.date = date;
	}

	abstract public void reserve(); // 사전에 어떤 예약을 했는지 나타내는 추상 메소드

	abstract int compute(int price); // 할인적용과 기장추가의 계산 후 금액을 리턴하는 추상 메소드

	int addPriceByHairLength() {	//기장추가 메소드
		if ("어깨위".equals(customer.getHairLength())) {	//머리길이가 어깨위면 +10000원
			return 10000;			
		} else if ("어깨아래".equals(customer.getHairLength())) {	//머리길이가 어깨아래면 +20000원
			return 20000;
		}
		return 0;	//머리길이가 턱선위면 기장추가 없음
	}
	void printCustomerInfo() { // 예약정보내용을 나타내는 메소드
		System.out.println("예약자명: " + customer.getName()); // 예약자명 출력
		System.out.println("예약자 성별: " + customer.getGender()); // 예약자성별출력
		System.out.println("예약자 머리길이: " + customer.getHairLength()); // 예약자머리길이출력
		System.out.println("예약시간: " + date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))); // 예약시간 출력
	}
}

class ImmediateReservation extends Reservation {	//예약 추상클래스를 상속받은 현장예약클래스
	
	public ImmediateReservation(Customer customer, LocalDateTime date) { //고객객체, 날짜
		super(customer, date);	//생성자 상속
	}

	public void reserve() {	//어떤예약했는지 나타냄-메소드 오버라이딩
		System.out.println("[현장예약]");  
		printCustomerInfo();  //메소드 상속하여 예약정보메소드 호출
	}

	int compute(int price) {  //메소드 오버라이딩 
		return price + addPriceByHairLength();	  //0퍼센트 할인 후 기장추가 적용(호출)한 금액
	}

}

class NaverReservation extends Reservation { // 예약 추상클래스를 상속받은 네이버예약클래스

	public NaverReservation(Customer customer, LocalDateTime date) { // 고객객체, 날짜
		super(customer, date); // 생성자 상속
	}

	public void reserve() { // 어떤예약했는지 나타냄-메소드 오버라이딩
		System.out.println("[네이버예약]");
		printCustomerInfo(); // 메소드 상속하여 예약정보메소드 호출

	}

	int compute(int price) { // 메소드 오버라이딩
		return (int) (price * 0.8) + addPriceByHairLength();  //20퍼센트 할인 후 기장추가 적용(호출)한 금액
	}

}

interface Perm {  //펌을 정의한 인터페이스
	int PLAIN_PRICE = 60000;	//일반펌 가격 상수
	int DESIGN_PRICE = 60000;	//디자인펌 가격 상수
	int SETTING_PRICE = 70000;	//세팅펌 가격 상수

	void plainPerm();	//일반펌 추상메소드

	void settingPerm();	//세팅펌 추상메소드

	void designPerm();	//디자인펌 추상메소드
}
interface Color {  //염색을 정의한 인터페이스
	int RED_PRICE = 40000;  //레드계열 염색 가격 상수
	int ASH_PRICE = 60000;  //애쉬계열 염색 가격 상수

	void redLine();  //레드계열 추상메소드

	void ashLine();  //애쉬계열 추상메소드
}
interface Cut {  //커트를 정의한 인터페이스

	int CUT_PRICE = 18000;  //일반커트 가격 상수

	void cut();  //커트 추상메소드

}
interface Clinic {	//클리닉을 정의한 인터페이스
	int CLINIC_PRICE = 30000;	//클리닉 가격 상수

	void treatment();	//클리닉 추상메소드
}

class Customer implements Perm, Color, Cut, Clinic { // 펌,염색,커트 인터페이스들로부터 상속받은 고객클래스
	private String name; // 고객이름
	private String gender; // 고객성별
	private String hairLength; // 고객머리길이

	protected int totalPrice = 0; // 지불할 총 금액

	public Customer(String name, String gender, String hairLength) { // 생성자 객체 생성시 고객이름, 고객성별, 고객머리길이
		this.name = name;
		this.gender = gender;
		this.hairLength = hairLength;
	}

	public String getName() { // private으로 선언해준 멤버변수를 사용할 수 있도록 리턴해주는 메소드
		return name;
	}

	public String getGender() { // private으로 선언해준 멤버변수를 사용할 수 있도록 리턴해주는 메소드
		return gender;
	}

	public String getHairLength() { // private으로 선언해준 멤버변수를 사용할 수 있도록 리턴해주는 메소드
		return hairLength;
	}

	public int getTotalPrice() { // protected로 선언해준 멤버변수를 사용할 수 있도록 리턴해주는 메소드
		return totalPrice;
	}

	public void cut() {	//커트인터페이스 상속해서 메소드 오버라이딩
		totalPrice += CUT_PRICE; // 커트금액을 총 지불할금액에 합산
		System.out.println(name + "님이 커트를 시작합니다.");
	}

	public void redLine() {	//염색인터페이스 상속해서 메소드 오버라이딩
		totalPrice += RED_PRICE; // 레드계열 염색금액을 총 지불할금액에 합산
		System.out.println(name + "님이 레드계열 염색을 시작합니다.");
	}

	public void ashLine() {	//염색인터페이스 상속해서 메소드 오버라이딩
		totalPrice += ASH_PRICE; // 애쉬계열 염색금액을 총 지불할금액에 합산
		System.out.println(name + "님이 애쉬계열 염색을 시작합니다.");
	}

	public void plainPerm() {	//펌인터페이스 상속해서 일반펌 메소드 오버라이딩
		totalPrice += PLAIN_PRICE; // 일반펌 금액을 총 지불할금액에 합산
		System.out.println(name + "님이 일반펌을 시작합니다.");
	}

	public void settingPerm() {	//펌인터페이스 상속해서 세팅펌 메소드 오버라이딩
		totalPrice += SETTING_PRICE; // 세팅펌 금액을 총 지불할금액에 합산
		System.out.println(name + "님이 세팅펌을 시작합니다.");
	}

	public void designPerm() {	//펌인터페이스 상속해서 디자인펌 메소드 오버라이딩
		totalPrice += DESIGN_PRICE; // 디자인펌 금액을 총 지불할금액에 합산
		System.out.println(name + "님이 디자인펌을 시작합니다.");
	}

	public void treatment() {	//클리닉인터페이스 상속해서 트리트먼트 메소드 오버라이딩
		totalPrice += CLINIC_PRICE; // 트리트먼트 금액을 총 지불할금액에 합산
		System.out.println(name + "님이 트리트먼트를 시작합니다.");
	}

}
class Man extends Customer {	//고객클래스를 상속받은 남성클래스

	public Man(String name, String hairLength) {	//남성고객 이름, 남성고객 머리길이
		super(name, "남성", hairLength); //생성자 상속
	}

	public void cut() {	//고객클래스 상속해서 메소드 오버라이딩
		this.totalPrice += (CUT_PRICE - 3000); // 남성고객은 여성고객보다 짧은 머리를 감안해 일반 커트금액에서 3000원 할인하여 총 지불할금액에 합산
		System.out.println(getName() + "님이 커트를 시작합니다.");
	}

}
class Woman extends Customer{ //고객클래스를 상속받은 여성클래스

	public Woman(String name, String hairLength) { //여성고객 이름, 여성고객 머리길이
		super(name, "여성", hairLength);	//생성자 상속
	}

}


class HairShopProgram { // 미용실 관리 프로그램

	public static void main(String[] args) {

		List<Customer> list = new ArrayList<Customer>();
		list.add(new Woman("정보람", "어깨아래")); // 머리길이가 어깨아래 정도인 정보람이라는 여성 고객이 존재
		list.add(new Man("김철수", "턱선위"));	// 머리길이가 턱선위 정도인 김철수라는 남성 고객이 존재
		list.add(new Woman("박영희", "어깨위"));  // 머리길이가 어깨위 정도인 박영희라는 여성 고객이 존재

		Customer customer1 = list.get(0);	//인덱스 0번째 있는 Customer객체를 꺼내옴 
		Reservation reservation = new NaverReservation(customer1, LocalDateTime.of(2020, 6, 15, 18, 0)); 
		// 예약 클래스타입의 객체 reservation에 네이버예약 클래스의 객체를 할당 
		// 고객이 2020년 6월 15일 18:00에 예약

		reservation.reserve(); // 정보람 고객의 예약정보를 나타냄.
		customer1.cut(); // 커트인터페이스를 상속받은 고객클래스의 머리자름메소드
		customer1.redLine(); // 염색인터페이스를 상속받은 고객클래스의 레드계열 염색메소드

		int finalPrice = reservation.compute(customer1.getTotalPrice());	//할인적용과 기장추가의 계산 후 금액을 변수 finalPrice에 넣음
		System.out.println(customer1.getName() + "님의 총 계산금액은 " + finalPrice + " 원 입니다.\n"); // 총 지불할 금액 출력

		Customer customer2 = list.get(1); //인덱스 1번째 있는 Customer객체를 꺼내옴
		Reservation reservation2 = new ImmediateReservation(customer2, LocalDateTime.of(2020, 12, 22, 10, 0)); 
		// 예약 클래스타입의 객체 reservation2에 현장예약 클래스의 객체를 할당 
		// 고객이 2020년 12월 22일 10:00에 예약

		reservation2.reserve(); // 김철수 고객의 예약정보를 나타냄.
		customer2.designPerm(); // 펌인터페이스를 상속받은 고객클래스의 디자인펌메소드
		customer2.cut(); // 커트인터페이스를 상속받은 고객클래스의 커트메소드
		customer2.treatment(); // 클리닉인터페이스를 상속받은 고객클래스의 트리트먼트메소드
		
		int finalPrice2 = reservation2.compute(customer2.getTotalPrice());	//할인적용과 기장추가의 계산 후 금액을 변수 finalPrice에 넣음
		System.out.println(customer2.getName() + "님의 총 계산금액은 " + finalPrice2 + " 원 입니다.\n"); // 총 지불할 금액 출력

		Customer customer3 = list.get(2);  //인덱스 2번째 있는 Customer객체를 꺼내옴
		Reservation reservation3 = new NaverReservation(customer3, LocalDateTime.of(2020, 1, 7, 16, 0)); 
		// 예약 클래스타입의 객체 reservation3에 현장예약 클래스의 객체를 할당 
		// 고객이 2020년 1월 7일 16:00에 예약

		reservation3.reserve(); // 박영희 고객의 예약정보를 나타냄.
		customer3.settingPerm(); // 펌인터페이스를 상속받은 고객클래스의 세팅펌메소드
		customer3.ashLine(); // 염색인터페이스를 상속받은 고객클래스의 애쉬계열 염색메소드

		int finalPrice3 = reservation3.compute(customer3.getTotalPrice());	//할인적용과 기장추가의 계산 후 금액을 변수 finalPrice에 넣음
		System.out.println(customer3.getName() + "님의 총 계산금액은 " + finalPrice3 + " 원 입니다."); // 총 지불할 금액 출력
	}
}