import java.util.*;
class PrintToday {
	public static void main(String args[]) {
	  	Date today = new Date();
     		System.out.println("������ "+(today.getYear()+1900)+"�� ");
     		System.out.println((today.getMonth()+1)+"�� ");
     		System.out.println(today.getDate()+"�� ");
     		System.out.println(today.getDay()+" �����Դϴ�. ");
   	}
}
