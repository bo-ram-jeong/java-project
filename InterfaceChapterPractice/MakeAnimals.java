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
		System.out.println(name+"가 벽을 갉아먹다");
	}
	public void eat(String str) { 
		System.out.println(name+"가 "+str+"을 먹다"); 
	} 
	public void sleep(int time) { 
		System.out.println(name+"는 "+time+"시간을 잔다"); 
	}
 	public void bear(int num) { 
		System.out.println(name+"가 "+num+"마리의 새끼를 낳다");
	}
}
class Bats extends Rats implements Birds {
	public Bats(String name) { 
             	super(name);
	} 
     	public void hangdown() {
		System.out.println(name+"가 천장에 매달리다"); 
	}
	public void fly() { 
		System.out.println(name+"가 날다");
   	}          
} 
class Flights implements Birds {     
    	String name;
    	public Flights (String n) {
        		name = n;       
	}
    	public void takeoff() {
 		System.out.println(name+"비행기가 이륙하다");      
	}
   	public void land() {
 		System.out.println(name+"비행기가 착륙하다");     
	}
    	public void fly() {
 		System.out.println(name+"비행기가 비행하다");  
	}
    	public void eat(String str) { 
		System.out.println(name+"에 "+str+"을 주유한다");  
	} 
 	public void sleep(int time) { 
  		System.out.println(name+"가 "+time+"시간동안 주차되다"); 
	}     
}

class MakeAnimals { 
	public static void main(String args[]) { 
        		Rats  mickey = new Rats("생쥐미키");        
        		mickey.eat("빵");           mickey.sleep(6);
       	 	mickey.bear(10);           mickey.gnaw();

        		Bats  little = new Bats("박쥐");
        		little.eat("곤충");          little.sleep(5);
        		little.bear(3);               little.gnaw();
        		little.hangdown();        little.fly();

        		Flights boing = new Flights("보잉707");
        		boing.takeoff();           boing.fly();
        		boing.land();
	} 
}

