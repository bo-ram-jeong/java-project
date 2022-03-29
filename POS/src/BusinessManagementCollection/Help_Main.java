package BusinessManagementCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

class Help_Main extends JFrame {
	JTabbedPane jtp;
	Container ct = getContentPane();

	Help_Main() {
		setTitle("도움말");
		ct.setLayout(null);
		ct.setBackground(new Color(255, 227, 236));
		jtp = new JTabbedPane();
		Button1 b1 = new Button1();
		jtp.add("로그인", b1);
		Button2 b2 = new Button2();
		jtp.add("코인룸 관리", b2);
		Button3 b3 = new Button3();
		jtp.add("곡/시간/음료", b3);
		Button4 b4 = new Button4();
		jtp.add("결제", b4);
		jtp.setBounds(0, 0, 430, 310);
		ct.add(jtp);
	}

}

class Button1 extends JPanel {
	Button1() {
		setBackground(new Color(255, 255, 255));
		setLayout(new GridLayout(4, 1));
		JLabel l0, l1, l2, l3;
		l0 = new JLabel("로그인/회원가입");
		add(l0);
		l1 = new JLabel("전화번호: 전화번호는 ID/PW찾기에 이용됩니다.");
		add(l1);
		l2 = new JLabel("2차Password: 4자리 숫자를 설정해주세요");
		add(l2);
		l3 = new JLabel("회원가입: 회원이 아니시라면 회원가입을 해주세요.");
		add(l3);
	}
}

class Button2 extends JPanel {
	Button2() {
		setBackground(new Color(255, 255, 255));
		setLayout(new GridLayout(5, 1));
		JLabel l0, l1, l2, l3, l4;
		l0 = new JLabel("코인룸 관리");
		add(l0);
		l1 = new JLabel("*코인룸 버튼을 클릭하여 코인룸의 곡, 시간 등의 관리를 할 수 있습니다.");
		add(l1);
		l2 = new JLabel("*코인룸이 빈방이면 방번호, 최대인원수와 함께 빈방임을 알려주며,");
		add(l2);
		l3 = new JLabel(" 사용중이면 해당 버튼에 사용중인 이용타입과 결제금액이 나타납니다.");
		add(l3);
		l4 = new JLabel("*결제내역 버튼을 눌러 결제내역을 조회할 수 있습니다.");
		add(l4);
	}
}

class Button3 extends JPanel {
	Button3() {
		setBackground(new Color(255, 255, 255));
		setLayout(new GridLayout(8, 1));
		JLabel l0, l1, l2, l3, l4, l5, l6, l7;
		l0 = new JLabel("곡/시간/음료");
		add(l0);
		l1 = new JLabel("*서비스탭에서 숫자 버튼을 이용해 곡 수와 시간을 입력하여 확인 버튼을");
		add(l1);
		l2 = new JLabel("누르면 결제금액이 나타납니다 결제 후, 결제완료를 누르면 ");
		add(l2);
		l3 = new JLabel("코인곡(또는 시간)과 결제금액을 코인룸에서 확인하실 수 있습니다");
		add(l3);
		l4 = new JLabel("*SendMessage버튼 클릭 시, 해당 방이나 전체방에게 알림이나");
		add(l4);
		l5 = new JLabel("요청사항을 메세지로 전송할 수 있습니다.");
		add(l5);
		l6 = new JLabel("*음료탭에서 판매할 음료를 장바구니처럼 담을 수 있고 음료의 종류와");
		add(l6);
		l7 = new JLabel("가짓수를 설정하여 그 리스트를 결제할 수 있습니다.");
		add(l7);
	}
}

class Button4 extends JPanel {
	Button4() {
		setBackground(new Color(255, 255, 255));
		setLayout(new GridLayout(6, 1));
		JLabel l0, l1, l2, l3, l4, l5;
		l0 = new JLabel("결제");
		add(l0);
		l1 = new JLabel("*곡/시간/음료 등을 판매할 때, 현금 또는 카드로 결제가 가능합니다.");
		add(l1);
		l2 = new JLabel("*현금 계산의 경우, 결제금액을 입력한후 거스름돈을 확인할 수 있습니다");
		add(l2);
		l3 = new JLabel("*카드 계산의 경우, 카드번호와 유효기간을 입력해야 합니다.");
		add(l3);
		l4 = new JLabel("포인트 사용/적립 버튼을 이용하여 포인트를 사용하여 결제금액을");
		add(l4);
		l5 = new JLabel("차감시키거나, 결제금액의 10%로를 적립시켜 사용할 수 있습니다.");
		add(l5);
	}
}
