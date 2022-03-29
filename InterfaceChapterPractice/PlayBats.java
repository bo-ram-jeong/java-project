interface Birds {            
	void fly();          
} 
class Rats { 
     public void gnaw() { 
	System.out.println("벽을 갉아 구멍을 내다"); 
	} 
} 
class Bats extends Rats implements Birds {   
	public void hangdown() { 
		System.out.println("천장에 매달리다"); 
	} 
	public void fly() { 
		System.out.println("날개를 퍼덕거리다");
	} 
} 
class Flights implements Birds {  

	public void takeoff() {
 		System.out.println("비행기가 이륙하다");     
	}
 	public void land() {
 		System.out.println("비행기가 착륙하다");    
	}
 	public void fly() {
 		System.out.println("비행기가 비행하다");
 	}
}

public class PlayBats { 
      public static void main(String args[]) { 
         Bats  little = new Bats();
         little.gnaw();
         little.hangdown();
         little.fly();

         Flights boing = new Flights();
         boing.takeoff();
         boing.fly();
         boing.land(); 
      } 
}