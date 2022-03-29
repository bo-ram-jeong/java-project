import java.util.*;
class PrintCalendar {
	public static void main(String args[]) {
		System.out.print("�޷��� ����ϰ� ���� ������ ���� �Է��Ͻÿ�(��: 2020 5) -> ");
		Scanner s=new Scanner(System.in);
		int y, m;
		y=s.nextInt(); 	m=s.nextInt();
	  	
		System.out.println("\t\t\t"+y+"��   "+m+"��");
		System.out.println("��\t��\tȭ\t��\t��\t��\t��");

		Calendar cal=Calendar.getInstance();
		cal.set(Calendar.YEAR, y );
		cal.set(Calendar.MONTH, m-1 );

		int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

		cal.set(Calendar.DAY_OF_MONTH, firstDay);				
		int tabCount=cal.get(Calendar.DAY_OF_WEEK)-1;	
		for(int i=0; i<tabCount; i++){
			System.out.print("\t");
		}

		for(int i=firstDay; i<=lastDay; i++){
			System.out.print(i+"\t");
			cal.set(Calendar.DAY_OF_MONTH, i);
			if(cal.get(Calendar.DAY_OF_WEEK)==7){
				System.out.print("\n");
			}
		}  		
   	}
}
