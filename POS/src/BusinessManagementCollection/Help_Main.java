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
		setTitle("����");
		ct.setLayout(null);
		ct.setBackground(new Color(255, 227, 236));
		jtp = new JTabbedPane();
		Button1 b1 = new Button1();
		jtp.add("�α���", b1);
		Button2 b2 = new Button2();
		jtp.add("���η� ����", b2);
		Button3 b3 = new Button3();
		jtp.add("��/�ð�/����", b3);
		Button4 b4 = new Button4();
		jtp.add("����", b4);
		jtp.setBounds(0, 0, 430, 310);
		ct.add(jtp);
	}

}

class Button1 extends JPanel {
	Button1() {
		setBackground(new Color(255, 255, 255));
		setLayout(new GridLayout(4, 1));
		JLabel l0, l1, l2, l3;
		l0 = new JLabel("�α���/ȸ������");
		add(l0);
		l1 = new JLabel("��ȭ��ȣ: ��ȭ��ȣ�� ID/PWã�⿡ �̿�˴ϴ�.");
		add(l1);
		l2 = new JLabel("2��Password: 4�ڸ� ���ڸ� �������ּ���");
		add(l2);
		l3 = new JLabel("ȸ������: ȸ���� �ƴϽö�� ȸ�������� ���ּ���.");
		add(l3);
	}
}

class Button2 extends JPanel {
	Button2() {
		setBackground(new Color(255, 255, 255));
		setLayout(new GridLayout(5, 1));
		JLabel l0, l1, l2, l3, l4;
		l0 = new JLabel("���η� ����");
		add(l0);
		l1 = new JLabel("*���η� ��ư�� Ŭ���Ͽ� ���η��� ��, �ð� ���� ������ �� �� �ֽ��ϴ�.");
		add(l1);
		l2 = new JLabel("*���η��� ����̸� ���ȣ, �ִ��ο����� �Բ� ������� �˷��ָ�,");
		add(l2);
		l3 = new JLabel(" ������̸� �ش� ��ư�� ������� �̿�Ÿ�԰� �����ݾ��� ��Ÿ���ϴ�.");
		add(l3);
		l4 = new JLabel("*�������� ��ư�� ���� ���������� ��ȸ�� �� �ֽ��ϴ�.");
		add(l4);
	}
}

class Button3 extends JPanel {
	Button3() {
		setBackground(new Color(255, 255, 255));
		setLayout(new GridLayout(8, 1));
		JLabel l0, l1, l2, l3, l4, l5, l6, l7;
		l0 = new JLabel("��/�ð�/����");
		add(l0);
		l1 = new JLabel("*�����ǿ��� ���� ��ư�� �̿��� �� ���� �ð��� �Է��Ͽ� Ȯ�� ��ư��");
		add(l1);
		l2 = new JLabel("������ �����ݾ��� ��Ÿ���ϴ� ���� ��, �����ϷḦ ������ ");
		add(l2);
		l3 = new JLabel("���ΰ�(�Ǵ� �ð�)�� �����ݾ��� ���η뿡�� Ȯ���Ͻ� �� �ֽ��ϴ�");
		add(l3);
		l4 = new JLabel("*SendMessage��ư Ŭ�� ��, �ش� ���̳� ��ü�濡�� �˸��̳�");
		add(l4);
		l5 = new JLabel("��û������ �޼����� ������ �� �ֽ��ϴ�.");
		add(l5);
		l6 = new JLabel("*�����ǿ��� �Ǹ��� ���Ḧ ��ٱ���ó�� ���� �� �ְ� ������ ������");
		add(l6);
		l7 = new JLabel("�������� �����Ͽ� �� ����Ʈ�� ������ �� �ֽ��ϴ�.");
		add(l7);
	}
}

class Button4 extends JPanel {
	Button4() {
		setBackground(new Color(255, 255, 255));
		setLayout(new GridLayout(6, 1));
		JLabel l0, l1, l2, l3, l4, l5;
		l0 = new JLabel("����");
		add(l0);
		l1 = new JLabel("*��/�ð�/���� ���� �Ǹ��� ��, ���� �Ǵ� ī��� ������ �����մϴ�.");
		add(l1);
		l2 = new JLabel("*���� ����� ���, �����ݾ��� �Է����� �Ž������� Ȯ���� �� �ֽ��ϴ�");
		add(l2);
		l3 = new JLabel("*ī�� ����� ���, ī���ȣ�� ��ȿ�Ⱓ�� �Է��ؾ� �մϴ�.");
		add(l3);
		l4 = new JLabel("����Ʈ ���/���� ��ư�� �̿��Ͽ� ����Ʈ�� ����Ͽ� �����ݾ���");
		add(l4);
		l5 = new JLabel("������Ű�ų�, �����ݾ��� 10%�θ� �������� ����� �� �ֽ��ϴ�.");
		add(l5);
	}
}
