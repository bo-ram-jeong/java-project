import java.time.LocalDateTime;	//날짜값을 표현하기 위해 time의 내장클래스 사용
import java.time.format.DateTimeFormatter;	//날짜를 원하는 형식대로 출력하기 위해 사용
import java.util.List;	   //List로 여러 Buyer관리
import java.util.ArrayList;	//Buyer를 List로 관리하기 위해 ArrayList 사용

abstract class Ticketing {	//티켓팅 추상클래스
	protected Buyer buyer; // 구매자객체 타입의 구매자객체(캡슐화)
	protected LocalDateTime date; // LocalDateTime타입의 날짜변수(캡슐화)

	public Ticketing(Buyer buyer, LocalDateTime date) { // 구매자 객체, 날짜
		this.buyer = buyer;
		this.date = date;
	}

	abstract public void ticketing(); // 어떤 방식으로 예매를 할건지 나타내는 추상메소드
	
	abstract int discount(int price); // 할인 적용 후 금액을 리턴하는 추상메소드
	
	void printCustomerInfo() { // 예매정보내용을 나타내는 메소드
		System.out.println("구매자명: " + buyer.getName()); // 구매자명 출력
		System.out.println("구매할 게임 테마: " + buyer.getGameTitle()); // 구매할 게임 테마 제목을 출력
		System.out.println("인원수: " + buyer.getTotalNumber()+" 명"); // 몇명참여하는지 총인원수 출력
		System.out.println("예약날짜: " + date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))); // 예약날짜 출력
	}
}
class ImmediateTicketing extends Ticketing{	//티켓팅 추상클래스를 상속받은 현장구매 클래스
	public ImmediateTicketing(Buyer buyer, LocalDateTime date) { // 구매자객체, 날짜
		super(buyer, date); // 생성자 상속
	}

	public void ticketing() { // 예매방식이 현장예매인 것을 나타내는 메소드(메소드 오버라이딩, 다형성 구현)
		System.out.println("[현장 예매]");
		printCustomerInfo(); //티켓팅 추상클래스에서 메소드 상속받아 예매정보메소드 호출
		waitingTime();	//대기시간 내용을 보여주는 메소드를 호출
	}

	int discount(int price) { // 티켓팅 추상클래스를 상속받은 할인 추상메소드를 오버라이딩(메소드 오버라이딩, 다형성 구현)
		int totalNumber=buyer.getTotalNumber();
		if(totalNumber>=5) {	//총 인원수가 5명이상이면 인당 -3000원으로 할인하여 총 지불할 금액에 적용
			price -= 3000*totalNumber;
		}
		else if(totalNumber>=3){	//총 인원수가 3명이상이면 인당 -2000원으로 할인하여 총 지불할 금액에 적용
			price -= 2000*totalNumber;
		}
		return price;	//총 인원수가 2명이하면 인당 할인적용없이 지불금액 리턴
	}
	
	void waitingTime() {	//온라인 예매가 아닌 현장예매는 대기간이 소요된다는 알림 메소드
		System.out.println("현장예매는 대기시간 1시간 소요됩니다.");
	}
}
class OnlineTicketing extends Ticketing{	//티켓팅 추상클래스를 상속받은 온라인티켓팅 클래스
	public OnlineTicketing(Buyer buyer, LocalDateTime date) { // 구매자객체, 날짜
		super(buyer, date); // 생성자 상속
	}

	public void ticketing() { // 예매방식이 온라인예매인 것을 나타내는 메소드(메소드 오버라이딩, 다형성 구현)
		System.out.println("[온라인 예매]");
		printCustomerInfo(); // 티켓팅 추상클래스에서 메소드 상속받아 예매정보메소드 호출

	}

	int discount(int price) { // 티켓팅 추상클래스를 상속받은 할인 추상메소드를 오버라이딩(메소드 오버라이딩, 다형성 구현)
		int totalNumber=buyer.getTotalNumber();	//protected로 선언된 총 인원수 멤버변수를 사용할 수 있도록 변수에 넣음
		if(totalNumber>=5) {	//총 인원수가 5명이상이면 인당 -3000원으로 할인하여 총 지불할 금액에 적용
			price -= 3000*totalNumber;
		}
		else if(totalNumber>=3){	//총 인원수가 3명이상이면 인당 -2000원으로 할인하여 총 지불할 금액에 적용
			price -= 2000*totalNumber;
		}
		return price;	//총 인원수가 2명이하면 인당 할인적용없이 지불금액 리턴
	}
}
interface IndustrialSpy {	//방탈출 게임 중 '산업스파이'를 정의한 인터페이스
	int INDUSTRIALSPY_PRICE = 18000;  //'산업스파이' 게임의 인당 가격 상수

	void industrialSpy();  //'산업스파이' 게임의 추상메소드
}
interface ExplorerSecret {	//방탈출 게임 중 '탐험가의 비밀'을 정의한 인터페이스
	int EXPLORERSECRET_PRICE = 20000;  //'탐험가의 비밀' 게임의 인당 가격 상수

	void explorerSecret();  //'탐험가의 비밀' 게임의 추상메소드
}
interface EscapeFromPrison {	//방탈출 게임 중 '감옥에서 탈출하기'를 정의한 인터페이스
	int PRISON_PRICE = 21000;  //'감옥에서 탈출하기' 게임의 인당 가격 상수

	void escapeFromPrison();  //'감옥에서 탈출하기' 게임의 추상메소드
}
class Buyer implements IndustrialSpy, ExplorerSecret, EscapeFromPrison{	//'산업스파이', '탐험가의 비밀', '감옥에서 탈출하기' 인터페이스에서 다중상속받아 정의한 구매자클래스
	private String name; // 구매자명(캡슐화)
	private String gameTitle; // 게임 테마 제목(캡슐화)
	private int totalNumber;	//총 인원수(캡슐화)
	
	protected int totalPrice = 0; // 지불할 총 금액(캡슐화)

	public Buyer(String name, String gameTitle, int totalNumber) { // 생성자 객체 생성시 구매자명, 게임 테마 제목, 참여하는 총 인원수
		this.name = name;
		this.gameTitle = gameTitle;
		this.totalNumber = totalNumber;
	}

	public String getName() { // private으로 선언해준 멤버변수를 사용할 수 있도록 리턴해주는 메소드
		return name;
	}

	public String getGameTitle() { // private으로 선언해준 멤버변수를 사용할 수 있도록 리턴해주는 메소드
		return gameTitle;
	}
	public int getTotalNumber() { // protected로 선언해준 멤버변수를 사용할 수 있도록 리턴해주는 메소드
		return totalNumber;
	}
	
	public int getTotalPrice() { // protected로 선언해준 멤버변수를 사용할 수 있도록 리턴해주는 메소드
		return totalPrice;
	}
	
	public void industrialSpy() { // '산업스파이' 인터페이스를 상속받아서 메소드 오버라이딩
		totalPrice += INDUSTRIALSPY_PRICE*totalNumber;
		System.out.println("\n"+name + "님이 "+gameTitle+" 테마를 선택하였습니다.");
	}

	public void explorerSecret() { // '탐험가의비밀' 인터페이스를 상속받아서 메소드 오버라이딩
		totalPrice += EXPLORERSECRET_PRICE*totalNumber; // 레드계열 염색금액을 총 지불할금액에 합산
		System.out.println("\n"+name + "님이 "+gameTitle+" 테마를 선택하였습니다.");
	}

	public void escapeFromPrison() { // '감옥에서 탈출하기' 인터페이스를 상속받아서 메소드 오버라이딩
		totalPrice += PRISON_PRICE*totalNumber; // 인당 지불금액 X 인원수를 총 지불할금액에 합산
		System.out.println("\n"+name + "님이 "+gameTitle+" 테마를 선택하였습니다.");
	}
}


class EscapeRoomGameProgram {	//main함수를 가지고 있는 방탈출게임프로그램 클래스

	public static void main(String[] args) {
		List<Buyer> list = new ArrayList<Buyer>();
		list.add(new Buyer("이하얀", "'산업스파이'", 2)); // 구매자 이하얀, 테마 '산업스파이' 를 선택, 게임에 참여할 총 인원은 2명
		list.add(new Buyer("윤동현", "'탐험가의 비밀'", 5)); // 구매자 윤동현, 테마 '탐험가의 비밀' 을 선택, 게임에 참여할 총 인원은 5명
		
		Buyer buyer1 = list.get(0); // 인덱스 0번째 있는 Buyer객체를 꺼내옴
		Ticketing ticketing = new OnlineTicketing(buyer1, LocalDateTime.of(2020, 5, 30, 20, 25));
		// 티켓팅 클래스타입의 객체 ticketing에 온라인예매 클래스의 객체를 할당(객체 형 변환)
		// 구매자가 2020년 5월 30일 20:25분에 예약
		
		ticketing.ticketing(); // 이하얀 구매자의 예약정보를 나타냄.
		buyer1.industrialSpy(); // 산업스파이 인터페이스를 상속받은 구매자 클래스의 산업스파이 메소드
		
		int totalPrice = ticketing.discount(buyer1.getTotalPrice()); // 할인 후 금액을 변수 totalPrice1에 넣음
		System.out.println(buyer1.getName() + "님이 지불할 금액은 " + totalPrice + " 원 입니다.\n"); // 총 지불할 금액 출력
		
		Buyer buyer2 = list.get(1); // 인덱스 1번째 있는 Buyer객체를 꺼내옴
		Ticketing ticketing2 = new ImmediateTicketing(buyer2, LocalDateTime.of(2020, 4, 24, 12, 40));
		// 티켓팅 클래스타입의 객체 ticketing2에 현장예매 클래스의 객체를 할당(객체 형 변환)
		// 구매자가 2020년 4월 24일 12:40분에 예약
		
		ticketing2.ticketing(); // 윤동현 구매자의 예매정보를 나타냄.
		buyer2.explorerSecret(); // 탐험가의비밀 인터페이스를 상속받은 구매자 클래스의 탐험가의비밀 메소드
		
		int totalPrice2 = ticketing2.discount(buyer2.getTotalPrice()); // 할인 후 금액을 변수 totalPrice2에 넣음
		System.out.println(buyer2.getName() + "님이 지불할 금액은 " + totalPrice2 + " 원 입니다.\n"); // 총 지불할 금액 출력
	}
}