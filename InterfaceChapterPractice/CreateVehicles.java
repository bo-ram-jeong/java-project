interface GasStation {
	public void gas(int f, int pay);	
}
interface Run{
	public void drive(int speed, int distance);
}
interface Quit{	//����
	public void stop(int a, int c, int distance);
}
interface Finish{
	public void deposit();
	public void park(String g);
}	
class Bus implements GasStation,Run,Quit,Finish {
	String name, number, garage;
	int year,fuel,delinquency; 	    //������, ��������, �糳��

	public Bus(String n, String num, int y, String g){
		name=n; number=num; year=y; garage=g;
	}
	public void gas(int f, int pay){
		fuel=f;
		delinquency-=pay;
		if(fuel>0){
			System.out.println(number+" "+name+"�� "+f+"���� ��ŭ�� ���Ḧ �־� �����ܰ�� "+fuel+"�����̰� "+pay+"���� ����� �����Ѵ�.");
		}
		else
			System.out.println("���ᰡ �����ϴ�. �����ϼ���.");
	}
	public void drive(int speed, int distance){
		int consume=0;	//���� �Ҹ�
		if(speed<=60){
			consume=1*distance/10000;
			fuel-=consume;
			if(fuel>0){
				System.out.println(number+" "+name+"�� "+speed+"�ӵ��� "+distance+"m�� �����Ͽ� "+consume+"������ ���� �Ҹ� �Ѵ�. ���� �ܰ�� "+fuel);
			}
			else
				System.out.println("���ᰡ �����մϴ�. �����ϼ���.");
		}
		else if(speed<=80){
			consume=(int)(1.5*distance/10000);
			fuel-=consume;
			if(fuel>0){
				System.out.println(number+" "+name+"�� "+speed+"�ӵ��� "+distance+"m�� ���������Ͽ� "+consume+"������ ���� �Ҹ� �Ѵ�. ���� �ܰ�� "+fuel);
			}
			else
				System.out.println("���ᰡ �����մϴ�. �����ϼ���.");
		}
		else{
			consume=2*distance/10000;
			fuel-=consume;
			if(fuel>0){
				System.out.println(number+" "+name+"�� "+speed+"�ӵ��� "+distance+"m�� ���������Ͽ� "+consume+"������ ���� �Ҹ� �Ѵ�. ���� �ܰ�� "+fuel);
			}
			else
				System.out.println("���ᰡ �����մϴ�. �����ϼ���.");
		}
	}
	public void stop(int a, int c, int distance){
		int sum=0;	//�������
		if(distance<=10000){
			sum+=a*1200+c*800;
			delinquency+=sum;
		}
		else{
			sum+=(a*1200+c*800)*1.5;
			delinquency+=sum;
		}
		System.out.println(number+" "+name+"�� �"+a+"��, �л�"+c+"���� ���"+sum+"�� �޾Ƽ� �糳�� �Ѿ��� "+delinquency+"��");
	}
	public void deposit(){
		System.out.println(number+" "+name+"�� ���� ���뿡 "+delinquency+"���� �糳���� �Ա��Ѵ�.");
	}
	public void park(String g){
		System.out.println(number+" "+name+"�� "+g+"�� �����Ѵ�.");
	}
}

class Taxi extends Bus implements Quit {
	public Taxi(String n, String num, int y, String g){
		super(n, num, y, g);
	}
	
	public void stop(int a, int c, int distance){
		int sum=0;	//�������
		if(distance<=2000){
			sum+=3000;
			delinquency+=sum;
		}
		else{
			sum+=3000+(distance-2000)*142/100;
			delinquency+=sum;
		}
		System.out.println(number+" "+name+"�� ���"+sum+"�� �޾Ƽ� �糳�� �Ѿ��� "+delinquency+"��");
	}
	public void deposit(){
		System.out.println(number+" "+name+"�� ok���뿡 "+delinquency+"���� �糳���� �Ա��Ѵ�.");
	}
}


public class CreateVehicles{
	public static void main(String args[]){
		Bus b120 =new Bus(" 120�� ����", "12��3456", 2019, "���ﱳ��");
		b120.gas(10,30000);
		b120.drive(50,10000);
		b120.stop(20,10,10000);
		b120.drive(80,20000);
		b120.stop(10,5,20000);
		b120.deposit();
		b120.park("���� ������");

		Taxi t=new Taxi("ȸ���ý�", "56��1234", 2018, "ok����");
		t.gas(5,15000);
		t.drive(60,10000);
		t.stop(1,0,10000);
		t.drive(80,20000);
		t.stop(2,1,20000);
		t.deposit();
		t.park("���� ������");		
	}
}