import java.time.LocalDateTime;	//날짜값을 표현하기 위한 time의 내장클래스 사용
import java.time.format.DateTimeFormatter;	//날짜를 원하는 형식대로 출력하기 위해 사용
import java.util.List;	   //List로 여러 User관리
import java.util.ArrayList;	//User를 List로 관리하기 위해 ArrayList 사용

abstract class BikeRent {
	protected User user; // 고객객체 타입의 고객객체
	protected LocalDateTime date; // LocalDateTime타입의 날짜변수

	public BikeRent(User user, LocalDateTime date) { // 고객 객체, 날짜
		this.user = user;
		this.date = date;
	}

	abstract public void reserve(); // 사전에 어떤 예약을 했는지 나타내는 메소드

	public void returnBike(int usedTime) {	  //자전거 반납할 때 초과금액이 있는지 확인하는 메소드
		int exceededFee;	//초과금액
		int diff = usedTime - user.getBorrowTime();	//실제로 사용한시간과 빌린다고 한 시간의 차이
		if (diff <= 10) {	//초과시간이 10분 이하일 때 
			exceededFee = 0;	//초과금액 0원
		} else if (diff <= 30) {	//초과시간이 30분 이하일 때 
			exceededFee = 1000;	//초과금액 1000원
		} else if (diff <= 60) {	//초과시간이 60분 이하일 때
			exceededFee = 2000;	 //초과금액 2000원
		} else {	//초과시간이 60분을 초과할 때
			exceededFee = 5000;	//초과금액 5000원
		}

		if (exceededFee == 0) {	//초과금액이 0원일 때 반납처리함
			System.out.println("[반납]\n이용한 시간: "+usedTime+"분 \n반납되었습니다. \n");
		} else {	//초과금액이 0원이 아닐 때 초과금액에 대한 지불요청함
			System.out.println("[반납]\n이용한 시간: "+usedTime+"분 \n초과요금이 " + exceededFee + "원 발생하였으니 지불 부탁드립니다. \n");
		}
	}

	abstract int discount(int price); // 할인 후 금액을 리턴하는 메소드

	void printCustomerInfo() { // 대여정보내용을 나타내는 메소드
		System.out.println("이용자명: " + user.getName()); // 이용자명 출력
		System.out.println("이용자 핸드폰번호: " + user.getPhoneNumber()); // 이용자 핸드폰 번호 출력
		System.out.println("빌릴시간: " + user.getBorrowTime() + " 분"); // 빌릴시간 출력
		System.out.println("대여자전거: " + user.getRentBike()); // 대여할 자전거 출력
		System.out.println("이용날짜: " + date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))); // 이용날짜 출력
	}
}
class ImmediateRent extends BikeRent {	//자전거 대여 추상클래스를 상속받은 현장 대여 클래스
	public ImmediateRent(User user, LocalDateTime date) { // 고객객체, 날짜
		super(user, date); // 생성자 상속
	}

	public void reserve() { // 어떤예약했는지 나타냄-메소드 오버라이딩
		System.out.println("[현장 대여자]");
		printCustomerInfo(); // 메소드 상속하여 대여정보메소드 호출

	}

	int discount(int price) { // 메소드 오버라이딩
		return price;	//할인 없이 금액 리턴
	}
}
class CallRent extends BikeRent{	//자전거 대여 추상클래스를 상속받은 전화 예약 클래스
	public CallRent(User user, LocalDateTime date) { // 고객객체, 날짜
		super(user, date); // 생성자 상속
	}

	public void reserve() { // 어떻게 대여 했는지 나타냄-메소드 오버라이딩
		System.out.println("[전화 대여자]");
		printCustomerInfo(); // 메소드 상속하여 대여정보메소드 호출

	}

	int discount(int price) { //메소드 오버라이딩
		return (int) (price * 0.9);	// 10퍼센트 할인 후 금액을 리턴
	}
}
interface BeginnerBike {
	int BEGINNER_PRICE_PER_10MIN = 1000;  //초급자전거 가격 상수

	void beginnerBike();  //초급자전거 추상메소드
}
interface MiddleBike {
	int MIDDLE_PRICE_PER_10MIN = 2000; //중급자전거 가격 상수

	void middleBike();  //중급자전거 추상메소드
}
interface AdvancedBike {
	int ADVANCED_PRICE_PER_10MIN = 4000;  //고급자전거 가격 상수

	void advancedBike();  //고급자전거 추상메소드
}
class User implements BeginnerBike, MiddleBike, AdvancedBike { // 초급,중급,고급 자전거 인터페이스들로부터 상속받은 이용자클래스
	private String name; // 이용자명
	private String phoneNumber; // 이용자 핸드폰번호
	private int borrowTime; // 빌릴시간
	private String rentBike; //대여할 자전거

	protected int totalPrice = 0; // 지불할 총 금액

	public User(String name, String phoneNumber, int borrowTime, String rentBike) { // 생성자 객체 생성시 이용자명, 이용자핸드폰번호, 빌릴시간, 대여할 자전거
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.borrowTime=borrowTime;
		this.rentBike=rentBike;
	}

	public String getName() { // private으로 선언해준 멤버변수를 사용할 수 있도록 리턴해주는 메소드
		return name;
	}

	public String getPhoneNumber() { // private으로 선언해준 멤버변수를 사용할 수 있도록 리턴해주는 메소드
		return phoneNumber;
	}
	
	public int getBorrowTime() { // private으로 선언해준 멤버변수를 사용할 수 있도록 리턴해주는 메소드
		return borrowTime;
	}
	
	public String getRentBike() { // private으로 선언해준 멤버변수를 사용할 수 있도록 리턴해주는 메소드
		return rentBike;
	}
	
	public int getTotalPrice() { // protected로 선언해준 멤버변수를 사용할 수 있도록 리턴해주는 메소드
		return totalPrice;
	}

	public void beginnerBike() { // 초급자전거 인터페이스에서 상속받아서 메소드 오버라이딩
		totalPrice += (BEGINNER_PRICE_PER_10MIN * borrowTime / 10); // 빌릴시간에 10분당 가격을 적용하여 그 금액을 총 지불할금액에 합산
		System.out.println(name + "님이 초급자전거를 빌렸습니다.");
	}

	public void middleBike() { // 중급자전거 인터페이스에서 상속받아서 메소드 오버라이딩
		totalPrice += (MIDDLE_PRICE_PER_10MIN * borrowTime / 10); // 빌릴시간에 10분당 가격을 적용하여 그 금액을 총 지불할금액에 합산
		System.out.println(name + "님이 중급자전거를 빌렸습니다.");
	}

	public void advancedBike() { // 고급자전거 인터페이스에서 상속받아서 메소드 오버라이딩
		totalPrice += (ADVANCED_PRICE_PER_10MIN * borrowTime / 10); // 빌릴시간에 10분당 가격을 적용하여 그 금액을 총 지불할금액에 합산
		System.out.println(name + "님이 고급자전거를 빌렸습니다.");	
	}
}


class BikeRentProgram {	// 메인함수를 가진 자전거 대여점 프로그램

	public static void main(String[] args) { 
		List<User> list = new ArrayList<User>();
		list.add(new User("김미나", "010.7865.5580", 90, "고급자전거")); // 전화번호가 010.7855.5580인 김미나 이용자가 고급자전거를 90분 대여함
		list.add(new User("하현우", "010.3421.0987", 150, "초급자전거")); // 전화번호가 010.3421.0987인 하현우 이용자가 초급자전거를 150분 대여함

		User user1 = list.get(0); // 인덱스 0번째 있는 User객체를 꺼내옴
		BikeRent bikeRent = new ImmediateRent(user1, LocalDateTime.of(2020, 3, 14, 14, 0));
		// 자전거대여 클래스타입의 객체 bikeRent에 현장대여 클래스의 객체를 할당
		// 고객이 2020년 6월 15일 18:00에 예약

		bikeRent.reserve(); // 김미나 이용자의 예약정보를 나타냄.
		user1.advancedBike();   // 고급자전거 인터페이스를 상속받은 이용자클래스의 고급자전거 메소드

		int payPrice = bikeRent.discount(user1.getTotalPrice()); // 할인 후 금액을 변수 payPrice에 넣음
		System.out.println(user1.getName() + "님이 지불할 금액은 " + payPrice + " 원 입니다.\n"); // 총 지불할 금액 출력

		bikeRent.returnBike(100);	//김미나 이용자가 100분 사용하고 반납

		User user2 = list.get(1); // 인덱스 1번째 있는 User객체를 꺼내옴
		BikeRent bikeRent2 = new CallRent(user2, LocalDateTime.of(2020, 12, 1, 11, 0));
		// 자전거대여 클래스타입의 객체 bikeRent2에 전화대여 클래스의 객체를 할당
		// 이용자가 2020년 12월 1일 11:00에 예약

		bikeRent2.reserve(); // 하현우 이용자의 예약정보를 나타냄
		user2.beginnerBike(); // 초급자전거 인터페이스를 상속받은 이용자클래스의 초급자전거 메소드

		int payPrice2 = bikeRent2.discount(user2.getTotalPrice()); // 할인 후 금액을 변수 payPrice에 넣음
		System.out.println(user2.getName() + "님이 지불할 금액은 " + payPrice2 + " 원 입니다.\n"); // 총 지불할 금액 출력

		bikeRent2.returnBike(210);	//하현우 이용자가 210분 사용하고 반납
	}
}