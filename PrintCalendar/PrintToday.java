import java.util.*;
class PrintToday {
	public static void main(String args[]) {
	  	Date today = new Date();
     		System.out.println("오늘은 "+(today.getYear()+1900)+"년 ");
     		System.out.println((today.getMonth()+1)+"월 ");
     		System.out.println(today.getDate()+"일 ");
     		System.out.println(today.getDay()+" 요일입니다. ");
   	}
}
