interface GasStation {
	public void gas(int f, int pay);	
}
interface Run{
	public void drive(int speed, int distance);
}
interface Quit{	//하차
	public void stop(int a, int c, int distance);
}
interface Finish{
	public void deposit();
	public void park(String g);
}	
class Bus implements GasStation,Run,Quit,Finish {
	String name, number, garage;
	int year,fuel,delinquency; 	    //차고지, 남은연료, 사납금

	public Bus(String n, String num, int y, String g){
		name=n; number=num; year=y; garage=g;
	}
	public void gas(int f, int pay){
		fuel=f;
		delinquency-=pay;
		if(fuel>0){
			System.out.println(number+" "+name+"가 "+f+"리터 만큼의 연료를 넣어 연료잔고는 "+fuel+"리터이고 "+pay+"원의 요금을 지불한다.");
		}
		else
			System.out.println("연료가 없습니다. 주유하세요.");
	}
	public void drive(int speed, int distance){
		int consume=0;	//연료 소모
		if(speed<=60){
			consume=1*distance/10000;
			fuel-=consume;
			if(fuel>0){
				System.out.println(number+" "+name+"가 "+speed+"속도로 "+distance+"m를 주행하여 "+consume+"리터의 연료 소모를 한다. 연료 잔고는 "+fuel);
			}
			else
				System.out.println("연료가 부족합니다. 주유하세요.");
		}
		else if(speed<=80){
			consume=(int)(1.5*distance/10000);
			fuel-=consume;
			if(fuel>0){
				System.out.println(number+" "+name+"가 "+speed+"속도로 "+distance+"m를 과속주행하여 "+consume+"리터의 연료 소모를 한다. 연료 잔고는 "+fuel);
			}
			else
				System.out.println("연료가 부족합니다. 주유하세요.");
		}
		else{
			consume=2*distance/10000;
			fuel-=consume;
			if(fuel>0){
				System.out.println(number+" "+name+"가 "+speed+"속도로 "+distance+"m를 과속주행하여 "+consume+"리터의 연료 소모를 한다. 연료 잔고는 "+fuel);
			}
			else
				System.out.println("연료가 부족합니다. 주유하세요.");
		}
	}
	public void stop(int a, int c, int distance){
		int sum=0;	//요금총합
		if(distance<=10000){
			sum+=a*1200+c*800;
			delinquency+=sum;
		}
		else{
			sum+=(a*1200+c*800)*1.5;
			delinquency+=sum;
		}
		System.out.println(number+" "+name+"에 어른"+a+"명, 학생"+c+"명의 요금"+sum+"원 받아서 사납금 총액은 "+delinquency+"원");
	}
	public void deposit(){
		System.out.println(number+" "+name+"가 서울 교통에 "+delinquency+"원의 사납급을 입금한다.");
	}
	public void park(String g){
		System.out.println(number+" "+name+"가 "+g+"에 주차한다.");
	}
}

class Taxi extends Bus implements Quit {
	public Taxi(String n, String num, int y, String g){
		super(n, num, y, g);
	}
	
	public void stop(int a, int c, int distance){
		int sum=0;	//요금총합
		if(distance<=2000){
			sum+=3000;
			delinquency+=sum;
		}
		else{
			sum+=3000+(distance-2000)*142/100;
			delinquency+=sum;
		}
		System.out.println(number+" "+name+"가 요금"+sum+"원 받아서 사납급 총액은 "+delinquency+"원");
	}
	public void deposit(){
		System.out.println(number+" "+name+"가 ok교통에 "+delinquency+"원의 사납급을 입금한다.");
	}
}


public class CreateVehicles{
	public static void main(String args[]){
		Bus b120 =new Bus(" 120번 버스", "12사3456", 2019, "서울교통");
		b120.gas(10,30000);
		b120.drive(50,10000);
		b120.stop(20,10,10000);
		b120.drive(80,20000);
		b120.stop(10,5,20000);
		b120.deposit();
		b120.park("본사 차고지");

		Taxi t=new Taxi("회사택시", "56가1234", 2018, "ok교통");
		t.gas(5,15000);
		t.drive(60,10000);
		t.stop(1,0,10000);
		t.drive(80,20000);
		t.stop(2,1,20000);
		t.deposit();
		t.park("서울 주차장");		
	}
}