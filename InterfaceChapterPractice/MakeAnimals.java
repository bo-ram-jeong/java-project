interface Animals{
	public void eat(String str);
	public void sleep(int time);
}
interface Mammal extends Animals{
	public void bear(int num);
}
interface Birds extends Animals{
	public void fly();
}
class Rats implements Mammal{
	String name;
	public Rats(String str){
		name=str;
	}
	public void gnaw(){
		System.out.println(name+"�� ���� ���ƸԴ�");
	}
	public void eat(String str) { 
		System.out.println(name+"�� "+str+"�� �Դ�"); 
	} 
	public void sleep(int time) { 
		System.out.println(name+"�� "+time+"�ð��� �ܴ�"); 
	}
 	public void bear(int num) { 
		System.out.println(name+"�� "+num+"������ ������ ����");
	}
}
class Bats extends Rats implements Birds {
	public Bats(String name) { 
             	super(name);
	} 
     	public void hangdown() {
		System.out.println(name+"�� õ�忡 �Ŵ޸���"); 
	}
	public void fly() { 
		System.out.println(name+"�� ����");
   	}          
} 
class Flights implements Birds {     
    	String name;
    	public Flights (String n) {
        		name = n;       
	}
    	public void takeoff() {
 		System.out.println(name+"����Ⱑ �̷��ϴ�");      
	}
   	public void land() {
 		System.out.println(name+"����Ⱑ �����ϴ�");     
	}
    	public void fly() {
 		System.out.println(name+"����Ⱑ �����ϴ�");  
	}
    	public void eat(String str) { 
		System.out.println(name+"�� "+str+"�� �����Ѵ�");  
	} 
 	public void sleep(int time) { 
  		System.out.println(name+"�� "+time+"�ð����� �����Ǵ�"); 
	}     
}

class MakeAnimals { 
	public static void main(String args[]) { 
        		Rats  mickey = new Rats("�����Ű");        
        		mickey.eat("��");           mickey.sleep(6);
       	 	mickey.bear(10);           mickey.gnaw();

        		Bats  little = new Bats("����");
        		little.eat("����");          little.sleep(5);
        		little.bear(3);               little.gnaw();
        		little.hangdown();        little.fly();

        		Flights boing = new Flights("����707");
        		boing.takeoff();           boing.fly();
        		boing.land();
	} 
}

