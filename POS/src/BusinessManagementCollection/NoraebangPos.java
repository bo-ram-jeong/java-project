package BusinessManagementCollection;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

class NoraebangPos extends JFrame implements ActionListener // 로그인화면 시작
{
	Container cp;
	JTextField id;
	JPasswordField passwd;

	NoraebangPos() { // 생성자
		cp = getContentPane();
		cp.setLayout(null);
		cp.setBackground(new Color(255, 227, 236));

		ImageIcon titleimage = new ImageIcon("src/BusinessManagementCollection/titleimage.jpg");
		JLabel title = new JLabel(titleimage);
		title.setBounds(0, 0, 340, 420);
		cp.add(title);

		JPanel info = new JPanel();
		info.setLayout(new GridLayout(2, 2));
		JLabel log_label = new JLabel("아이디");
		JLabel pwd_label = new JLabel("패스워드");
		log_label.setOpaque(true);
		pwd_label.setOpaque(true);
		log_label.setBackground(new Color(255, 227, 236));
		pwd_label.setBackground(new Color(255, 227, 236));
		id = new JTextField(8);
		passwd = new JPasswordField(8);
		info.add(log_label);
		info.add(id);
		info.add(pwd_label);
		info.add(passwd);
		JButton login_btn = new JButton("로그인");
		login_btn.setBackground(new Color(255, 255, 255));
		JButton search_btn = new JButton("ID/PW 찾기");
		search_btn.setBackground(new Color(255, 255, 255));
		JButton regit_btn = new JButton("회원가입");
		regit_btn.setBackground(new Color(255, 255, 255));
		JLabel logo = new JLabel("(주)KjuK");
		login_btn.addActionListener(this);
		search_btn.addActionListener(this);
		regit_btn.addActionListener(this);
		info.setBounds(360, 150, 150, 60);
		login_btn.setBounds(520, 150, 100, 60);
		search_btn.setBounds(410, 230, 100, 20);
		regit_btn.setBounds(520, 230, 100, 20);
		logo.setBounds(580, 400, 100, 20);
		cp.add(logo);
		cp.add(info);
		cp.add(login_btn);
		cp.add(search_btn);
		cp.add(regit_btn);

	}

	public void actionPerformed(ActionEvent ae) {
		String s = ae.getActionCommand();
		String t_id = "", t_pwd = "";
		t_id = id.getText();
		t_pwd = passwd.getText();
		if (s == "로그인") {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.err.println("드라이버 로드에 실패했습니다.");
			}
			try {
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/coinroom?serverTimezone=UTC",
						"root", "java2020");
				Statement dbst = con.createStatement();
				String strSql;
				strSql = "SELECT*FROM user_info WHERE id='" + t_id + "'and passwd='" + t_pwd + "';";
				ResultSet result = dbst.executeQuery(strSql);
				boolean ok = false;
				while (result.next())
					ok = true;
				if (ok) {
					MainMenu win = new MainMenu("메인메뉴", t_id);
					win.setSize(600, 550);
					win.setLocation(680, 280);
					win.show();
					win.setResizable(false);
					win.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					dispose();
				} else {
					MessageDialog md = new MessageDialog(this, "로그인 확인", true, "ID, PW를 확인하세요");
					md.show();
				}
				dbst.close();
				con.close();
			} catch (SQLException e) {
				System.out.println("SQLException :" + e.getMessage());
			}
		} else if (s == "회원가입") {
			MakeRegit win = new MakeRegit("회원가입");
			win.setSize(360, 300);
			win.setLocation(750, 350);
			win.show();
			win.setResizable(false);
			win.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		} else if (s == "ID/PW 찾기") {
			MakeSearch win = new MakeSearch("ID/PW 찾기");
			win.setSize(280, 250);
			win.setLocation(750, 350);
			win.show();
			win.setResizable(false);
			win.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}

	}

	public static void main(String[] args) {
		NoraebangPos win = new NoraebangPos();
		win.setSize(650, 460);
		win.setLocation(600, 300);
		win.setTitle("다불러 노래방 POS프로그램");
		win.setVisible(true);
		win.setResizable(false);
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
} // 로그인화면 마지막

//메세지다이알로그
class MessageDialog extends JDialog implements ActionListener {
	   JButton ok;
	   MessageDialog(JFrame parent, String title, boolean mode, String msg) {
	      super(parent, title, mode);
	      JPanel pc = new JPanel();
	      JLabel label = new JLabel(msg);
	      pc.add(label);
	      add(pc, BorderLayout.CENTER);
	      JPanel ps = new JPanel();
	      ok = new JButton("OK");
	      ok.addActionListener(this);
	      ps.add(ok);
	      add(ps, BorderLayout.SOUTH);
	      pack();
	   }
	   public void actionPerformed(ActionEvent ae) {
	      dispose();
	   }
	}

class MainMenu extends JFrame implements ActionListener // 메인메뉴 시작
{
	String t_id;
	LineBorder tb = new LineBorder(Color.BLACK, 1);
	Font f1 = new Font("맑은 고딕", Font.PLAIN | Font.BOLD, 16);
	Font f2 = new Font("맑은 고딕", Font.PLAIN | Font.BOLD, 12);

	MainMenu(String title, String s_id) {
		t_id = s_id;
		setTitle(title);
		Container ct = getContentPane();
		ct.setLayout(null);
		ct.setBackground(new Color(255, 227, 236));
		JButton b1 = new JButton("코인룸 관리");
		b1.setFont(f1);
		JButton b2 = new JButton("재고관리");
		b2.setFont(f1);
		JButton b3 = new JButton("근태관리");
		b3.setFont(f1);
		JButton b4 = new JButton("회원관리");
		b4.setFont(f1);
		JButton b5 = new JButton("관리자설정");
		b5.setFont(f1);
		JButton b6 = new JButton("도움말");
		b6.setFont(f2);
		JButton b7 = new JButton("로그아웃");
		b7.setFont(f2);
		b1.setBackground(new Color(255, 255, 255));
		b2.setBackground(new Color(255, 255, 255));
		b3.setBackground(new Color(255, 255, 255));
		b4.setBackground(new Color(255, 255, 255));
		b5.setBackground(new Color(255, 255, 255));
		b6.setBackground(new Color(255, 255, 255));
		b7.setBackground(new Color(255, 255, 255));
		b1.setBounds(145, 40, 310, 120);
		b2.setBounds(145, 180, 150, 120);
		b3.setBounds(305, 180, 150, 120);
		b4.setBounds(145, 320, 150, 120);
		b5.setBounds(305, 320, 150, 120);
		b6.setBounds(390, 460, 80, 40);
		b7.setBounds(480, 460, 90, 40);
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		b5.addActionListener(this);
		b6.addActionListener(this);
		b7.addActionListener(this);

		ct.add(b1);
		ct.add(b2);
		ct.add(b3);
		ct.add(b4);
		ct.add(b5);
		ct.add(b6);
		ct.add(b7);
	}

	public void actionPerformed(ActionEvent ae) {
		String s = ae.getActionCommand();
		switch (s) {
		case "코인룸 관리":
			BusinessManagement bm = new BusinessManagement(t_id);
			bm.setTitle("코인 룸 관리");
			bm.setSize(1200, 800);
			bm.setLocation(400, 150);
			bm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			bm.setVisible(true);
			dispose();
			break;
		case "근태관리":
			TimeCheck tc = new TimeCheck(t_id);
			tc.setSize(1100, 750);
			tc.setLocation(400, 100);
			tc.setTitle("근태관리");
			tc.setVisible(true);
			tc.setResizable(false);
			tc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			dispose();
			break;
		case "재고관리":
			InventoryControl ic = new InventoryControl(t_id);
			ic.setTitle("재고관리");
			ic.setSize(1000, 640);
			ic.setLocation(450, 150);
			ic.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			ic.setVisible(true);
			ic.setResizable(false);
			dispose();
			break;
		case "회원관리":
			MemberInfo mi = new MemberInfo(t_id);
			mi.setSize(1100, 750);
			mi.setLocation(400, 100);
			mi.setTitle("회원관리");
			mi.setVisible(true);
			mi.setResizable(false);
			mi.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			dispose();
			break;
		case "관리자설정":
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.err.println("드라이버 로드에 실패했습니다.");
			}
			try {
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/coinroom?serverTimezone=UTC",
						"root", "java2020");
				Statement dbst = con.createStatement();
				String strSql;
				strSql = "SELECT*FROM user_info WHERE id='" + t_id + "';";
				ResultSet result = dbst.executeQuery(strSql);
				String ok = "";
				while (result.next())
					ok = result.getString("second_pwd");
				if (ok.equals("")) {
					MakeSecondPW win = new MakeSecondPW(t_id);
					win.setSize(280, 100);
					win.setLocation(800, 300);
					win.setTitle("비밀번호 설정");
					win.setVisible(true);
					win.setResizable(false);
					win.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					break;
				} else {
					SecondPW win = new SecondPW(t_id);
					win.setSize(280, 100);
					win.setLocation(800, 300);
					win.setTitle("2차 비밀번호");
					win.setVisible(true);
					win.setResizable(false);
					win.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					dispose();
					break;
				}
			} catch (SQLException e) {
				System.out.println("SQLException :" + e.getMessage());
			}
			break;
		case "도움말":
			Help_Main hm = new Help_Main();
			hm.setSize(450, 340);
			hm.setVisible(true);
			hm.setLocation(800, 340);
			hm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			break;
		case "로그아웃":
			NoraebangPos np = new NoraebangPos();
			np.setSize(650, 460);
			np.setLocation(700, 300);
			np.setTitle("다불러 노래방 POS프로그램");
			np.setVisible(true);
			np.setResizable(false);
			np.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			dispose();
			break;
		}
	}
} // 메인메뉴 마지막

class MakeSecondPW extends JFrame implements ActionListener // 2차비밀번호설정 시작
{
	String t_id;
	JPasswordField passwd;

	MakeSecondPW(String s_id) {
		t_id = s_id;
		Container ct = getContentPane();
		ct.setLayout(null);
		passwd = new JPasswordField(4);
		JButton btn = new JButton("설정");
		btn.addActionListener(this);
		passwd.setBounds(50, 20, 100, 20);
		btn.setBounds(170, 15, 60, 30);
		ct.add(btn);
		ct.add(passwd);
	}

	public void actionPerformed(ActionEvent ae) {
		String t_passwd = passwd.getText();
		if (passwd.getText().length() < 4) {
			MessageDialog md = new MessageDialog(this, "2차PW 확인", true, "4자리 비밀번호로 설정하세요");
			md.show();
		} else {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.err.println("드라이버 로드에 실패했습니다.");
			}
			try {
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/coinroom?serverTimezone=UTC",
						"root", "java2020");
				Statement dbst = con.createStatement();
				String strSql;
				strSql = "UPDATE user_info SET second_pwd='" + t_passwd + "' WHERE id ='" + t_id + "';";
				dbst.executeUpdate(strSql);
				MessageDialog md = new MessageDialog(this, "2차PW 확인", true, "설정 되었습니다");
				md.show();
				dbst.close();
				con.close();
			} catch (SQLException e) {
				System.out.println("SQLException :" + e.getMessage());
			}
			dispose();
		}
	}
}// 2차비밀번호설정 마지막

class SecondPW extends JFrame implements ActionListener // 2차비밀번호입력 시작
{
	String t_id;
	JPasswordField passwd;

	SecondPW(String s_id) {
		t_id = s_id;
		Container ct = getContentPane();
		ct.setLayout(null);
		passwd = new JPasswordField(4);
		JButton btn = new JButton("확인");
		btn.addActionListener(this);
		passwd.setBounds(50, 20, 100, 20);
		btn.setBounds(170, 15, 60, 30);
		ct.add(btn);
		ct.add(passwd);
	}

	public void actionPerformed(ActionEvent ae) {
		String t_passwd = passwd.getText();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println("드라이버 로드에 실패했습니다.");
		}
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/coinroom?serverTimezone=UTC",
					"root", "java2020");
			Statement dbst = con.createStatement();
			String strSql;
			strSql = "SELECT*FROM user_info WHERE second_pwd='" + t_passwd + "';";
			boolean ok = false;
			ResultSet result = dbst.executeQuery(strSql);
			while (result.next())
				ok = true;
			if (ok) {
				dispose();
				UserSet win = new UserSet(t_id);
				win.setSize(1100, 750);
				win.setLocation(400, 100);
				win.setTitle("관리자설정");
				win.setVisible(true);
				win.setResizable(false);
				win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			} else {
				MessageDialog md = new MessageDialog(this, " 접속실패", true, "비밀번호를 확인해주세요 ");
				md.show();
				MainMenu win = new MainMenu("메인메뉴", t_id);
				win.setSize(600, 550);
				win.setLocation(680, 280);
				win.show();
				win.setResizable(false);
				win.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				dispose();
			}
			dbst.close();
			con.close();
		} catch (SQLException a) {
			System.out.println("SQLException :" + a.getMessage());
		}
	}
}// 2차비밀번호입력 마지막

class MakeSearch extends JFrame implements ActionListener // ID,PW찾기 시작
{

	JLabel l1;
	JTextField id, name, mid_num, last_num;
	JPasswordField passwd;
	JComboBox tel;
	String code[] = { "010", "070", "031", "032" };
	JRadioButton gr[] = new JRadioButton[2];
	JButton b1, b2;

	MakeSearch(String title) {
		setTitle(title);
		Container ct = getContentPane();
		ct.setLayout(new FlowLayout());

		String grName[] = { "ID찾기", "PW찾기" };
		ButtonGroup grade = new ButtonGroup();
		for (int i = 0; i < gr.length; i++) {
			gr[i] = new JRadioButton(grName[i]);
			grade.add(gr[i]);
			gr[i].addActionListener(this);
		}
		gr[0].setSelected(true);
		JPanel rg = new JPanel();
		rg.add(gr[0]);
		rg.add(gr[1]);
		ct.add(rg, BorderLayout.NORTH);

		JPanel search = new JPanel();
		search.setLayout(new GridLayout(3, 1));

		JPanel p1 = new JPanel();
		p1.setLayout(new FlowLayout(FlowLayout.LEFT));
		l1 = new JLabel("ID                   :");
		id = new JTextField(8);
		p1.add(l1);
		p1.add(id);
		l1.setVisible(false);
		id.setVisible(false);

		JPanel p2 = new JPanel();
		p2.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel l2 = new JLabel("이름              :");
		name = new JTextField(8);
		p2.add(l2);
		p2.add(name);

		JPanel p3 = new JPanel();
		p3.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel l3 = new JLabel("전화번호     :");
		mid_num = new JTextField(4);
		tel = new JComboBox(code);
		last_num = new JTextField(4);
		p3.add(l3);
		p3.add(tel);
		p3.add(mid_num);
		p3.add(last_num);

		search.add(p1);
		search.add(p2);
		search.add(p3);
		ct.add(search, BorderLayout.CENTER);

		JPanel bottom = new JPanel();
		b1 = new JButton("찾기");
		b1.addActionListener(this);
		b2 = new JButton("취소");
		b2.addActionListener(this);
		bottom.add(b1);
		bottom.add(b2);
		ct.add(bottom, BorderLayout.SOUTH);

	}

	public void actionPerformed(ActionEvent ae) {
		String s = ae.getActionCommand();
		String t_name = name.getText(), t_tel = (String) tel.getSelectedItem() + mid_num.getText() + last_num.getText(),
				t_id = "";
		if (gr[0].isSelected()) {
			l1.setVisible(false);
			id.setVisible(false);
		} else {
			l1.setVisible(true);
			id.setVisible(true);
		}

		if (s == "찾기") {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.err.println("드라이버 로드에 실패했습니다.");
			}
			try {
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/coinroom?serverTimezone=UTC",
						"root", "java2020");
				Statement dbst = con.createStatement();
				String strSql;
				if (gr[0].isSelected()) {
					strSql = "SELECT*FROM user_info WHERE name='" + t_name + "'and tel_number='" + t_tel + "';";
					String msg;
					Boolean ok = true;
					ResultSet result = dbst.executeQuery(strSql);
					while (result.next()) {
						ok = false;
						msg = result.getString("id");
						MessageDialog md = new MessageDialog(this, "아이디 확인", true, msg);
						md.show();
					}
					if (ok) {
						MessageDialog md2 = new MessageDialog(this, "아이디 확인", true, "올바른 정보를 입력하세요");
						md2.show();
					}
				} else {
					t_id = id.getText();
					strSql = "SELECT*FROM user_info WHERE name='" + t_name + "'and tel_number='" + t_tel + "'and id='"
							+ t_id + "';";
					String msg;
					Boolean ok = true;
					ResultSet result = dbst.executeQuery(strSql);
					while (result.next()) {
						ok = false;
						msg = result.getString("passwd");
						MessageDialog md = new MessageDialog(this, "패스워드 확인", true, msg);
						md.show();
					}
					if (ok) {
						MessageDialog md2 = new MessageDialog(this, "아이디 확인", true, "올바른 정보를 입력하세요");
						md2.show();
					}
				}
				dbst.close();
				con.close();
			} catch (SQLException e) {
				System.out.println("SQLException :" + e.getMessage());
			}
		} else if (s == "취소") {
			dispose();
		}

	}
} // ID,PW찾기 마지막

class MakeRegit extends JFrame implements ActionListener // 회원가입 시작
{
	boolean signal = false;
	boolean signal2 = false;
	boolean signal3 = false;
	JTextField id, name, mid_num, last_num;
	JPasswordField passwd;
	JComboBox tel;
	JButton phone_check, check, b1, b2;
	String code[] = { "010", "070", "031", "032" };

	MakeRegit(String title) {
		setTitle(title);
		Container ct = getContentPane();
		ct.setLayout(new BorderLayout(0, 20));
		JPanel top = new JPanel();
		top.setLayout(new GridLayout(4, 1));
		JPanel p1 = new JPanel();
		p1.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel l1 = new JLabel("ID                   :");
		id = new JTextField(8);
		check = new JButton("중복 체크");
		check.addActionListener(this);
		p1.add(l1);
		p1.add(id);
		p1.add(check);
		JPanel p2 = new JPanel();
		p2.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel l2 = new JLabel("PW                :");
		passwd = new JPasswordField(8);
		p2.add(l2);
		p2.add(passwd);
		JPanel p3 = new JPanel();
		p3.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel l3 = new JLabel("이름              :");
		name = new JTextField(8);
		p3.add(l3);
		p3.add(name);
		JPanel p4 = new JPanel();
		p4.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel l4 = new JLabel("전화번호     :");
		mid_num = new JTextField(4);
		tel = new JComboBox(code);
		last_num = new JTextField(4);
		phone_check = new JButton("인증");
		phone_check.addActionListener(this);
		p4.add(l4);
		p4.add(tel);
		p4.add(mid_num);
		p4.add(last_num);
		p4.add(phone_check);

		top.add(p1);
		top.add(p2);
		top.add(p3);
		top.add(p4);
		ct.add(top, BorderLayout.CENTER);

		JPanel bottom = new JPanel();
		b1 = new JButton("가입완료");
		b2 = new JButton("취소");
		b1.addActionListener(this);
		b2.addActionListener(this);
		bottom.add(b1);
		bottom.add(b2);
		ct.add(bottom, BorderLayout.SOUTH);
	}

	public void actionPerformed(ActionEvent ae) {
		String s = ae.getActionCommand();
		String t_id = "", t_passwd = "", t_name = "", t_tel = "";
		if (s == "취소") {
			dispose();
		} else {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.err.println("드라이버 로드에 실패했습니다.");
			}
			try {
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/coinroom?serverTimezone=UTC",
						"root", "java2020");
				Statement dbst = con.createStatement();
				String strSql;
				t_id = id.getText();
				t_passwd = passwd.getText();
				t_name = name.getText();
				t_tel = (String) tel.getSelectedItem() + mid_num.getText() + last_num.getText();
				switch (s) {
				case "중복 체크":
					if (t_id.equals("")) {
						MessageDialog md = new MessageDialog(this, "아이디 확인", true, "ID를 입력하세요");
						md.show();
						break;
					} else {
						strSql = "SELECT*FROM user_info WHERE id='" + t_id + "';";
						boolean ok = true;
						String msg;
						ResultSet result = dbst.executeQuery(strSql);
						while (result.next())
							ok = false;
						if (ok) {
							msg = "사용 가능한 ID입니다.";
							signal = true;
						} else {
							msg = "사용중인 ID입니다.";
							signal = false;
						}
						MessageDialog md = new MessageDialog(this, "아이디 확인", true, msg);
						md.show();
						break;
					}
				case "인증":
					if (t_tel.length() < 11) {
						MessageDialog md2 = new MessageDialog(this, "전화번호 확인", true, "전화번호를 확인해주세요");
						md2.show();
						signal = false;
					} else {
						strSql = "SELECT*FROM user_info WHERE tel_number='" + t_tel + "';";
						boolean ok2 = true;
						String msg2;
						ResultSet result2 = dbst.executeQuery(strSql);
						while (result2.next())
							ok2 = false;
						if (ok2) {
							msg2 = "인증 되었습니다";
							signal2 = true;
						} else {
							msg2 = "가입된 전화번호 입니다.";
							signal2 = false;
						}
						MessageDialog md3 = new MessageDialog(this, "전화번호 확인", true, msg2);
						md3.show();
					}
					break;
				case "가입완료":
					if (t_name.length() == 0) {
						MessageDialog md4 = new MessageDialog(this, "확인", true, "이름을 입력해주세요.");
						md4.show();
						signal3 = false;
						break;
					} else
						signal3 = true;
					if (signal && signal2 && signal3) {
						strSql = "INSERT INTO user_info (id,passwd,second_pwd,name,tel_number) VALUES ('" + t_id + "','"
								+ t_passwd + "','" + "" + "','" + t_name + "','" + t_tel + "');";
						dbst.executeUpdate(strSql);
						MessageDialog md5 = new MessageDialog(this, "확인", true, "회원가입 되었습니다.");
						md5.show();
						strSql = "CREATE TABLE " + t_id
								+ "_staff_info (staff_code int AUTO_INCREMENT primary key,name varchar(8) not null,age date not null,tel_number varchar(11) not null,address varchar(20) not null,intime datetime default null,outtime datetime default null ) default character set = euckr;";
						dbst.execute(strSql);
						strSql = "CREATE TABLE " + t_id
								+ "_customer_info(customer_code int AUTO_INCREMENT primary key ,name varchar(8) not null,tel_number varchar(11) not null,point int default null) default character set = euckr;";
						dbst.execute(strSql);
						this.setVisible(false);
						break;
					} else {
						MessageDialog md6 = new MessageDialog(this, "확인", true, "ID, 전화번호를 확인해주세요.");
						md6.show();
						id.setText("");
						passwd.setText("");
						name.setText("");
						mid_num.setText("");
						last_num.setText("");
						tel.setSelectedItem((Object) code[0]);
						break;
					}
				}
				dbst.close();
				con.close();
			} catch (SQLException e) {
				System.out.println("SQLException :" + e.getMessage());
			}
		}
	}
} // 회원가입 마지막

class TimeCheck extends JFrame implements ActionListener, MouseListener { // 근태관리 화면 시작

	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	int code;
	JTable i_table;
	String t_id;
	String i_t, o_t;
	String te[] = { "010", "070", "031", "032" };
	String con[] = { "이름", "전화번호" };
	String header[] = { "직원번호", "이름", "전화번호", "출근시간", "퇴근시간" };
	Object contents[][] = new Object[0][4];
	JTextField sch, name, mid_num, last_num, intime, outtime, address;
	JComboBox category, tel;
	JButton sch_btn, back_btn, in_btn, out_btn;
	DefaultTableModel mode;
	DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
	LineBorder tb = new LineBorder(Color.BLACK, 1);
	Font f1 = new Font("맑은 고딕", Font.PLAIN | Font.BOLD, 16);

	TimeCheck(String s_id) {
		t_id = s_id;
		Container cp = getContentPane();
		cp.setLayout(null);
		cp.setBackground(new Color(255, 227, 236));
		Font f1 = new Font("맑은 고딕", Font.PLAIN | Font.BOLD, 16);
		mode = new DefaultTableModel(contents, header);
		i_table = new JTable(mode);
		i_table.addMouseListener(this);
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcm = i_table.getColumnModel();

		for (int i = 0; i < tcm.getColumnCount(); i++)
			tcm.getColumn(i).setCellRenderer(dtcr);

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println("드라이버 로드에 실패했습니다.");
		}
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/coinroom?serverTimezone=UTC",
					"root", "java2020");
			Statement dbst = con.createStatement();
			String strSql;
			strSql = "SELECT*FROM " + t_id + "_staff_info ;";
			ResultSet result = dbst.executeQuery(strSql);
			while (result.next()) {
				String d_name = result.getString("name");
				String d_tel = result.getString("tel_number").substring(0, 3) + "-"
						+ result.getString("tel_number").substring(3, 7) + "-"
						+ result.getString("tel_number").substring(7, 11);
				int d_code = result.getInt("staff_code");
				String d_intime = result.getString("intime");
				String d_outtime = result.getString("outtime");
				Object data[] = { d_code, d_name, d_tel, d_intime, d_outtime };
				mode.addRow(data);
			}
			dbst.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("SQLException :" + e.getMessage());
		}

		category = new JComboBox(con);
		category.setBackground(new Color(255, 255, 255));
		sch = new JTextField(20);

		JScrollPane scrollpane = new JScrollPane(i_table);
		scrollpane.setBorder(tb);

		sch_btn = new JButton("조회");
		sch_btn.setBackground(new Color(255, 250, 164));
		sch_btn.addActionListener(this);

		back_btn = new JButton("뒤로가기");
		back_btn.setBackground(new Color(218, 246, 233));
		back_btn.addActionListener(this);

		in_btn = new JButton("출근");
		in_btn.setBackground(new Color(218, 246, 233));
		in_btn.addActionListener(this);
		in_btn.setFont(f1);
		out_btn = new JButton("퇴근");
		out_btn.setBackground(new Color(218, 246, 233));
		out_btn.addActionListener(this);
		out_btn.setFont(f1);

		JLabel i_label = new JLabel("<상세 정보>");
		i_label.setFont(f1);
		JPanel info_panel = new JPanel();
		info_panel.setLayout(new GridLayout(5, 1));
		info_panel.setBorder(tb);
		JPanel p1 = new JPanel();
		p1.setLayout(new FlowLayout(FlowLayout.LEFT));
		p1.setBackground(new Color(255, 255, 255));
		JLabel l1 = new JLabel("이름            :");
		name = new JTextField(8);
		name.setEnabled(false);
		p1.add(l1);
		p1.add(name);
		JPanel p2 = new JPanel();
		p2.setLayout(new FlowLayout(FlowLayout.LEFT));
		p2.setBackground(new Color(255, 255, 255));
		JLabel l2 = new JLabel("전화번호   :");
		tel = new JComboBox(te);
		mid_num = new JTextField(4);
		last_num = new JTextField(4);
		tel.setEnabled(false);
		mid_num.setEnabled(false);
		last_num.setEnabled(false);
		p2.add(l2);
		p2.add(tel);
		p2.add(mid_num);
		p2.add(last_num);
		JPanel p3 = new JPanel();
		p3.setLayout(new FlowLayout(FlowLayout.LEFT));
		p3.setBackground(new Color(255, 255, 255));
		JLabel l3 = new JLabel("주소            :");
		address = new JTextField(20);
		address.setEnabled(false);
		p3.add(l3);
		p3.add(address);
		JPanel p4 = new JPanel();
		p4.setLayout(new FlowLayout(FlowLayout.LEFT));
		p4.setBackground(new Color(255, 255, 255));
		JLabel l4 = new JLabel("출근시간   :");
		intime = new JTextField(16);
		intime.setEnabled(false);
		p4.add(l4);
		p4.add(intime);
		JPanel p5 = new JPanel();
		p5.setLayout(new FlowLayout(FlowLayout.LEFT));
		p5.setBackground(new Color(255, 255, 255));
		JLabel l5 = new JLabel("퇴근시간   :");
		outtime = new JTextField(16);
		outtime.setEnabled(false);
		p5.add(l5);
		p5.add(outtime);
		info_panel.add(p1);
		info_panel.add(p2);
		info_panel.add(p3);
		info_panel.add(p4);
		info_panel.add(p5);

		category.setBounds(50, 25, 80, 30);
		sch.setBounds(140, 25, 300, 30);
		sch_btn.setBounds(450, 25, 70, 30);
		scrollpane.setBounds(50, 80, 600, 600);
		back_btn.setBounds(940, 620, 110, 60);
		i_label.setBounds(825, 80, 100, 30);
		info_panel.setBounds(700, 120, 350, 340);
		in_btn.setBounds(810, 500, 110, 50);
		out_btn.setBounds(930, 500, 110, 50);

		cp.add(category);
		cp.add(sch);
		cp.add(sch_btn);
		cp.add(back_btn);
		cp.add(scrollpane);
		cp.add(i_label);
		cp.add(info_panel);
		cp.add(in_btn);
		cp.add(out_btn);

	}

	public void actionPerformed(ActionEvent ae) {
		String s = ae.getActionCommand();

		if (s == "뒤로가기") {
			MainMenu win = new MainMenu("메인메뉴", t_id);
			win.setSize(600, 550);
			win.setLocation(680, 280);
			win.show();
			win.setResizable(false);
			win.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			dispose();
		} else {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.err.println("드라이버 로드에 실패했습니다.");
			}
			try {
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/coinroom?serverTimezone=UTC",
						"root", "java2020");
				Statement dbst = con.createStatement();
				String strSql;
				switch (s) {
				case "조회":
					mode.setNumRows(0);
					String sch1 = sch.getText();
					if (sch1.equals("")) {
						strSql = "SELECT*FROM " + t_id + "_staff_info;";
						ResultSet result = dbst.executeQuery(strSql);
						while (result.next()) {
							String d_name = result.getString("name");
							String d_tel = result.getString("tel_number").substring(0, 3) + "-"
									+ result.getString("tel_number").substring(3, 7) + "-"
									+ result.getString("tel_number").substring(7, 11);
							int d_code = result.getInt("staff_code");
							String d_intime = result.getString("intime");
							String d_outtime = result.getString("outtime");
							Object data[] = { d_code, d_name, d_tel, d_intime, d_outtime };
							mode.addRow(data);
						}
					} else {
						String ctg = (String) category.getSelectedItem();
						switch (ctg) {
						case "이름":
							strSql = "SELECT*FROM " + t_id + "_staff_info WHERE name='" + sch1 + "';";
							ResultSet result2 = dbst.executeQuery(strSql);
							while (result2.next()) {
								String d_name = result2.getString("name");
								String d_tel = result2.getString("tel_number").substring(0, 3) + "-"
										+ result2.getString("tel_number").substring(3, 7) + "-"
										+ result2.getString("tel_number").substring(7, 11);
								int d_code = result2.getInt("staff_code");
								String d_intime = result2.getString("intime");
								String d_outtime = result2.getString("outtime");
								Object data[] = { d_code, d_name, d_tel, d_intime, d_outtime };
								mode.addRow(data);
							}
							break;
						case "전화번호":
							strSql = "SELECT*FROM " + t_id + "_staff_info WHERE tel_number='" + sch1 + "';";
							ResultSet result3 = dbst.executeQuery(strSql);
							while (result3.next()) {
								String d_name = result3.getString("name");
								String d_tel = result3.getString("tel_number").substring(0, 3) + "-"
										+ result3.getString("tel_number").substring(3, 7) + "-"
										+ result3.getString("tel_number").substring(7, 11);
								int d_code = result3.getInt("staff_code");
								String d_intime = result3.getString("intime");
								String d_outtime = result3.getString("outtime");
								Object data[] = { d_code, d_name, d_tel, d_intime, d_outtime };
								mode.addRow(data);
							}
							break;
						}
					}
					break;
				case "출근":
					mode.setNumRows(0);
					i_t = format.format(new java.util.Date());
					strSql = "UPDATE " + t_id + "_staff_info SET intime='" + i_t + "',outtime=" + null
							+ " WHERE staff_code ='" + code + "';";
					dbst.executeUpdate(strSql);
					strSql = "SELECT*FROM " + t_id + "_staff_info WHERE staff_code='" + code + "';";
					ResultSet result4 = dbst.executeQuery(strSql);
					while (result4.next()) {
						intime.setText(i_t);
						outtime.setText("");
						String d_name = result4.getString("name");
						String d_tel = result4.getString("tel_number").substring(0, 3) + "-"
								+ result4.getString("tel_number").substring(3, 7) + "-"
								+ result4.getString("tel_number").substring(7, 11);
						int d_code = result4.getInt("staff_code");
						String d_intime = result4.getString("intime");
						String d_outtime = result4.getString("outtime");
						Object data[] = { d_code, d_name, d_tel, d_intime, d_outtime };
						mode.addRow(data);
					}
					in_btn.setEnabled(false);
					out_btn.setEnabled(true);
					break;
				case "퇴근":
					mode.setNumRows(0);
					o_t = format.format(new java.util.Date());
					strSql = "UPDATE " + t_id + "_staff_info SET outtime='" + o_t + "' WHERE staff_code ='" + code
							+ "';";
					dbst.executeUpdate(strSql);
					strSql = "SELECT*FROM " + t_id + "_staff_info WHERE staff_code='" + code + "';";
					ResultSet result5 = dbst.executeQuery(strSql);
					while (result5.next()) {
						outtime.setText(o_t);
						intime.setText(i_t);
						String d_name = result5.getString("name");
						String d_tel = result5.getString("tel_number").substring(0, 3) + "-"
								+ result5.getString("tel_number").substring(3, 7) + "-"
								+ result5.getString("tel_number").substring(7, 11);
						int d_code = result5.getInt("staff_code");
						String d_intime = result5.getString("intime");
						String d_outtime = result5.getString("outtime");
						Object data[] = { d_code, d_name, d_tel, d_intime, d_outtime };
						mode.addRow(data);
					}
					in_btn.setEnabled(true);
					out_btn.setEnabled(false);
					break;
				}
				dbst.close();
				con.close();
			} catch (SQLException e) {
				System.out.println("SQLException :" + e.getMessage());
			}
		}
	}

	public void mouseClicked(MouseEvent e) {
		JTable jtable = (JTable) e.getSource();
		name.setEnabled(true);
		tel.setEnabled(true);
		mid_num.setEnabled(true);
		last_num.setEnabled(true);
		address.setEnabled(true);
		intime.setEnabled(true);
		outtime.setEnabled(true);
		int row = jtable.getSelectedRow();
		code = (int) mode.getValueAt(row, 0);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException a) {
			System.err.println("드라이버 로드에 실패했습니다.");
		}
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/coinroom?serverTimezone=UTC",
					"root", "java2020");
			Statement dbst = con.createStatement();
			String strSql;
			strSql = "SELECT*FROM " + t_id + "_staff_info WHERE staff_code='" + code + "';";
			ResultSet result = dbst.executeQuery(strSql);
			while (result.next()) {
				name.setText(result.getString("name"));
				String save = result.getString("tel_number").substring(0, 3);
				tel.setSelectedItem((Object) save);
				save = result.getString("tel_number").substring(3, 7);
				mid_num.setText(save);
				save = result.getString("tel_number").substring(7, 11);
				last_num.setText(save);
				address.setText(result.getString("address"));
				outtime.setText(result.getString("outtime"));
				intime.setText(result.getString("intime"));
			}
			if (intime.getText().equals("")) {
				in_btn.setEnabled(true);
				out_btn.setEnabled(false);
			} else if (outtime.getText().equals("")) {
				out_btn.setEnabled(true);
				in_btn.setEnabled(false);
			} else {
				in_btn.setEnabled(true);
				out_btn.setEnabled(false);
			}
			dbst.close();
			con.close();
		} catch (SQLException a) {
			System.out.println("SQLException :" + a.getMessage());
		}
	}

	public void mouseEntered(java.awt.event.MouseEvent e) {
	}

	public void mouseExited(java.awt.event.MouseEvent e) {
	}

	public void mousePressed(java.awt.event.MouseEvent e) {
	}

	public void mouseReleased(java.awt.event.MouseEvent e) {
	}
} // 근태관리화면 마지막

class UserSet extends JFrame implements ActionListener { // 관리자설정 시작

	JTabbedPane jtp;
	String t_id;
	Container ct = getContentPane();
	JButton my_btn;
	LineBorder tb = new LineBorder(Color.BLACK, 1);
	Font f1 = new Font("맑은 고딕", Font.PLAIN | Font.BOLD, 20);
	Font f2 = new Font("맑은 고딕", Font.PLAIN | Font.BOLD, 16);

	UserSet(String s_id) {
		t_id = s_id;
		UIManager.put("TabbedPane.contentAreaColor", Color.WHITE);
		UIManager.put("TabbedPane.selected", new Color(204, 255, 229));
		ct.setLayout(null);
		ct.setBackground(new Color(255, 227, 236));

		JLabel title = new JLabel("<관리자 설정>");
		title.setFont(f1);
		jtp = new JTabbedPane();

		SalesCheck sc = new SalesCheck(s_id, this);
		StaffInfo si = new StaffInfo(s_id, this);
		jtp.add("직원관리", si);
		jtp.add("매출관리", sc);
		jtp.setFont(f2);

		my_btn = new JButton("MyPage");
		my_btn.setBackground(new Color(204, 255, 229));
		my_btn.addActionListener(this);
		title.setBounds(25, 20, 140, 30);

		jtp.setBounds(20, 60, 1050, 640);
		my_btn.setBounds(960, 20, 80, 30);
		ct.add(title);
		ct.add(jtp);
		ct.add(my_btn);
	}

	public void actionPerformed(ActionEvent ae) {
		String s = ae.getActionCommand();
		if (s == "MyPage") {
			MyPage win = new MyPage(t_id, this);
			win.setSize(360, 350);
			win.setLocation(750, 250);
			win.setTitle("MyPage");
			win.setVisible(true);
			win.setResizable(false);
			win.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
	}
} // 관리자설정 마지막

class StaffInfo extends JPanel implements ActionListener, MouseListener { // 직원정보 탭 시작

	UserSet parent;
	String t_id = "";
	boolean signal = false;
	JPanel p2;
	JTextField name, mid_num, last_num;
	JTextArea address;
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	int code;
	JComboBox year, month, day;
	String year_d[] = new String[100];
	String month_d[] = new String[12];
	JComboBox tel;
	String te[] = { "010", "070", "031", "032" };
	JTable i_table;
	String header[] = { "직원번호", "이름", "생년월일", "전화번호", "주소", "출근시간", "퇴근시간" };
	Object contents[][] = new Object[0][7];
	JButton add_btn, del_btn, rev_btn, back_btn;
	DefaultTableModel mode;
	DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
	LineBorder tb = new LineBorder(Color.BLACK, 1);
	LineBorder tb2 = new LineBorder(new Color(122, 138, 153));
	Font f1 = new Font("맑은 고딕", Font.PLAIN | Font.BOLD, 16);

	StaffInfo(String s_id, UserSet us) {
		parent = us;
		t_id = s_id;
		setBackground(new Color(255, 255, 255));
		setLayout(null);
		mode = new DefaultTableModel(contents, header);
		i_table = new JTable(mode);
		i_table.addMouseListener(this);
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcm = i_table.getColumnModel();

		i_table.getColumn("이름").setPreferredWidth(1);
		i_table.getColumn("직원번호").setPreferredWidth(3);
		i_table.getColumn("생년월일").setPreferredWidth(18);
		i_table.getColumn("전화번호").setPreferredWidth(40);
		for (int i = 0; i < tcm.getColumnCount(); i++)
			tcm.getColumn(i).setCellRenderer(dtcr);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println("드라이버 로드에 실패했습니다.");
		}
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/coinroom?serverTimezone=UTC",
					"root", "java2020");
			Statement dbst = con.createStatement();
			String strSql;
			strSql = "SELECT*FROM " + t_id + "_staff_info;";
			ResultSet result = dbst.executeQuery(strSql);
			while (result.next()) {
				String d_name = result.getString("name");
				Date d_age = result.getDate("age");
				String d_tel = result.getString("tel_number").substring(0, 3) + "-"
						+ result.getString("tel_number").substring(3, 7) + "-"
						+ result.getString("tel_number").substring(7, 11);
				int d_code = result.getInt("staff_code");
				String d_intime = result.getString("intime");
				String d_address = result.getString("address");
				String d_outtime = result.getString("outtime");
				Object data[] = { d_code, d_name, d_age, d_tel, d_address, d_intime, d_outtime };
				mode.addRow(data);
			}
			dbst.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("SQLException :" + e.getMessage());
		}
		JScrollPane scrollpane = new JScrollPane(i_table);
		scrollpane.setBorder(tb);

		add_btn = new JButton("등록");
		add_btn.setBackground(new Color(218, 246, 233));
		add_btn.addActionListener(this);
		add_btn.setFont(f1);

		rev_btn = new JButton("수정");
		rev_btn.setBackground(new Color(218, 246, 233));
		rev_btn.addActionListener(this);
		rev_btn.setFont(f1);

		del_btn = new JButton("삭제");
		del_btn.setBackground(new Color(218, 246, 233));
		del_btn.addActionListener(this);
		del_btn.setFont(f1);

		back_btn = new JButton("뒤로가기");
		back_btn.setBackground(new Color(218, 246, 233));
		back_btn.addActionListener(this);
		back_btn.setFont(f1);

		JLabel i_label = new JLabel("<상세 정보>");
		i_label.setFont(f1);
		JPanel info_panel = new JPanel();
		info_panel.setLayout(new GridLayout(4, 1));
		info_panel.setBorder(tb);
		JPanel p1 = new JPanel();
		p1.setLayout(new FlowLayout(FlowLayout.LEFT));
		p1.setBackground(new Color(255, 255, 255));
		JLabel l1 = new JLabel("이름            :");
		name = new JTextField(8);
		p1.add(l1);
		p1.add(name);
		p2 = new JPanel();
		p2.setLayout(new FlowLayout(FlowLayout.LEFT));
		p2.setBackground(new Color(255, 255, 255));
		JLabel l2 = new JLabel("생년월일   :");
		int j = 0;
		for (int i = 2020; i > 1920; i--) {
			year_d[j] = Integer.toString(i);
			j++;
		}
		for (int i = 1; i < 13; i++) {
			month_d[i - 1] = Integer.toString(i);
		}
		year = new JComboBox(year_d);
		month = new JComboBox(month_d);
		day = new JComboBox();
		for (int i = 1; i < 32; i++) {
			day.addItem(Integer.toString(i));
		}
		month.addActionListener(this);
		p2.add(l2);
		p2.add(year);
		p2.add(month);
		p2.add(day);
		JPanel p3 = new JPanel();
		p3.setLayout(new FlowLayout(FlowLayout.LEFT));
		p3.setBackground(new Color(255, 255, 255));
		JLabel l3 = new JLabel("전화번호   :");
		tel = new JComboBox(te);
		mid_num = new JTextField(4);
		last_num = new JTextField(4);
		p3.add(l3);
		p3.add(tel);
		p3.add(mid_num);
		p3.add(last_num);
		JPanel p4 = new JPanel();
		p4.setLayout(new FlowLayout(FlowLayout.LEFT));
		p4.setBackground(new Color(255, 255, 255));
		JLabel l4 = new JLabel("주소            :");
		address = new JTextArea(2, 19);
		address.setBorder(tb2);
		p4.add(l4);
		p4.add(address);
		info_panel.add(p1);
		info_panel.add(p2);
		info_panel.add(p3);
		info_panel.add(p4);

		scrollpane.setBounds(10, 20, 700, 580);
		info_panel.setBounds(730, 70, 300, 300);
		i_label.setBounds(835, 20, 100, 40);
		add_btn.setBounds(735, 400, 140, 70);
		rev_btn.setBounds(885, 400, 140, 70);
		del_btn.setBounds(735, 490, 140, 70);
		back_btn.setBounds(885, 490, 140, 70);

		add(scrollpane);
		add(i_label);
		add(info_panel);
		add(add_btn);
		add(rev_btn);
		add(del_btn);
		add(back_btn);

	}

	public void actionPerformed(ActionEvent ae) {
		String s = ae.getActionCommand();
		String t_name = name.getText(), t_address = address.getText(),
				t_tel = (String) tel.getSelectedItem() + mid_num.getText() + last_num.getText();
		String t_year = (String) year.getSelectedItem(), t_mon = (String) month.getSelectedItem(),
				t_day = (String) day.getSelectedItem(), t_age = "";
		t_age = t_year + "-" + t_mon + "-" + t_day;

		int index = Integer.parseInt((String) month.getSelectedItem());
		int index2 = Integer.parseInt((String) year.getSelectedItem());

		if (index == 2) {
			day.setVisible(false);
			day = null;
			day = new JComboBox();
			if (index2 % 4 == 0 || (index2 % 100 == 0 && index2 % 400 == 0))
				for (int i = 1; i < 30; i++)
					day.addItem(Integer.toString(i));
			else
				for (int i = 1; i < 29; i++)
					day.addItem(Integer.toString(i));
			p2.add(day);
		} else if (index == 4 || index == 6 || index == 9 || index == 11) {
			day.setVisible(false);
			day = null;
			day = new JComboBox();
			for (int i = 1; i < 31; i++)
				day.addItem(Integer.toString(i));
			p2.add(day);
		} else {
			day.setVisible(false);
			day = null;
			day = new JComboBox();
			for (int i = 1; i < 32; i++)
				day.addItem(Integer.toString(i));
			p2.add(day);
		}
		if (s == "뒤로가기") {
			MainMenu win = new MainMenu("메인메뉴", t_id);
			win.setSize(600, 550);
			win.setLocation(680, 280);
			win.show();
			win.setResizable(false);
			win.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			parent.dispose();
		} else {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.err.println("드라이버 로드에 실패했습니다.");
			}
			try {
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/coinroom?serverTimezone=UTC",
						"root", "java2020");
				Statement dbst = con.createStatement();
				String strSql;
				switch (s) {
				case "등록":
					if (t_tel.length() < 11 && t_name.length() == 0) {
						JOptionPane.showMessageDialog(null, "이름, 전화번호를 정확히 입력해주세요", "확인", JOptionPane.ERROR_MESSAGE);
						break;
					} else if (t_name.length() == 0) {
						JOptionPane.showMessageDialog(null, "이름을 입력해주세요", "이름 확인", JOptionPane.ERROR_MESSAGE);
						break;
					} else if (t_tel.length() < 11) {
						JOptionPane.showMessageDialog(null, "전화번호를 정확히 입력해주세요", "전화번호 확인", JOptionPane.ERROR_MESSAGE);
						break;
					} else {
						strSql = "SELECT*FROM " + t_id + "_staff_info WHERE tel_number='" + t_tel + "';";
						ResultSet rs = dbst.executeQuery(strSql);
						boolean ok = false;
						while (rs.next())
							ok = true;
						if (ok) {
							signal = false;
							JOptionPane.showMessageDialog(null, "중복된 전화번호 입니다", "전화번호 확인", JOptionPane.ERROR_MESSAGE);
						} else
							signal = true;
						if (signal) {
							strSql = "INSERT INTO " + t_id
									+ "_staff_info (staff_code,name,age,tel_number,address,intime,outtime) VALUES (null"
									+ ",'" + t_name + "','" + t_age + "','" + t_tel + "','" + t_address
									+ "',null,null);";
							dbst.executeUpdate(strSql);
							strSql = "ALTER TABLE " + t_id + "_staff_info AUTO_INCREMENT= 1;";
							dbst.execute(strSql);
							strSql = "SET @COUNT = 0;";
							dbst.execute(strSql);
							strSql = "UPDATE " + t_id + "_staff_info SET staff_code = @COUNT:=@COUNT+1;";
							dbst.execute(strSql);
							strSql = "SELECT*FROM " + t_id + "_staff_info ;";
							ResultSet result = dbst.executeQuery(strSql);
							mode.setNumRows(0);
							while (result.next()) {
								String d_name = result.getString("name");
								Date d_age = result.getDate("age");
								String d_tel = result.getString("tel_number").substring(0, 3) + "-"
										+ result.getString("tel_number").substring(3, 7) + "-"
										+ result.getString("tel_number").substring(7, 11);
								int d_code = result.getInt("staff_code");
								String d_intime = result.getString("intime");
								String d_address = result.getString("address");
								String d_outtime = result.getString("outtime");
								Object data[] = { d_code, d_name, d_age, d_tel, d_address, d_intime, d_outtime };
								mode.addRow(data);
							}
						}
						break;
					}
				case "수정":
					if (t_tel.length() < 11 && t_name.length() == 0) {
						JOptionPane.showMessageDialog(null, "이름, 전화번호를 정확히 입력해주세요", "확인", JOptionPane.ERROR_MESSAGE);
						break;
					} else if (t_name.length() == 0) {
						JOptionPane.showMessageDialog(null, "이름을 입력해주세요", "이름 확인", JOptionPane.ERROR_MESSAGE);
						break;
					} else if (t_tel.length() < 11) {
						JOptionPane.showMessageDialog(null, "전화번호를 정확히 입력해주세요", "전화번호 확인", JOptionPane.ERROR_MESSAGE);
						break;
					} else {
						strSql = "SELECT*FROM " + t_id + "_staff_info WHERE tel_number='" + t_tel + "' and staff_code='"
								+ code + "';";
						ResultSet rs = dbst.executeQuery(strSql);
						boolean a = false;
						while (rs.next())
							a = true;
						if (a) {
							strSql = "UPDATE " + t_id + "_staff_info SET name='" + t_name + "',address='" + t_address
									+ "',age='" + t_age + "',tel_number='" + t_tel + "' WHERE staff_code ='" + code
									+ "';";
							dbst.executeUpdate(strSql);
							strSql = "SELECT*FROM " + t_id + "_staff_info ;";
							ResultSet result3 = dbst.executeQuery(strSql);
							mode.setNumRows(0);
							while (result3.next()) {
								String d_name = result3.getString("name");
								Date d_age = result3.getDate("age");
								String d_tel = result3.getString("tel_number").substring(0, 3) + "-"
										+ result3.getString("tel_number").substring(3, 7) + "-"
										+ result3.getString("tel_number").substring(7, 11);
								int d_code = result3.getInt("staff_code");
								String d_intime = result3.getString("intime");
								String d_address = result3.getString("address");
								String d_outtime = result3.getString("outtime");
								Object data[] = { d_code, d_name, d_age, d_tel, d_address, d_intime, d_outtime };
								mode.addRow(data);
							}
							break;
						}
						strSql = "SELECT*FROM " + t_id + "_staff_info WHERE tel_number='" + t_tel + "';";
						ResultSet rs2 = dbst.executeQuery(strSql);
						while (rs2.next()) {
							JOptionPane.showMessageDialog(null, "중복된 전화번호 입니다", "확인", JOptionPane.ERROR_MESSAGE);
							a = true;
						}
						if (!a) {
							strSql = "UPDATE " + t_id + "_staff_info SET name='" + t_name + "',address='" + t_address
									+ "',age='" + t_age + "',tel_number='" + t_tel + "' WHERE staff_code ='" + code
									+ "';";
							dbst.executeUpdate(strSql);
							strSql = "SELECT*FROM " + t_id + "_staff_info;";
							ResultSet result3 = dbst.executeQuery(strSql);
							mode.setNumRows(0);
							while (result3.next()) {
								String d_name = result3.getString("name");
								Date d_age = result3.getDate("age");
								String d_tel = result3.getString("tel_number").substring(0, 3) + "-"
										+ result3.getString("tel_number").substring(3, 7) + "-"
										+ result3.getString("tel_number").substring(7, 11);
								int d_code = result3.getInt("staff_code");
								String d_intime = result3.getString("intime");
								String d_address = result3.getString("address");
								String d_outtime = result3.getString("outtime");
								Object data[] = { d_code, d_name, d_age, d_tel, d_address, d_intime, d_outtime };
								mode.addRow(data);
							}
							break;
						}
						break;
					}
				case "삭제":
					strSql = "DELETE FROM " + t_id + "_staff_info WHERE staff_code='" + code + "';";
					dbst.executeUpdate(strSql);
					strSql = "ALTER TABLE " + t_id + "_staff_info AUTO_INCREMENT= 1;";
					dbst.execute(strSql);
					strSql = "SET @COUNT = 0;";
					dbst.execute(strSql);
					strSql = "UPDATE " + t_id + "_staff_info SET staff_code = @COUNT:=@COUNT+1;";
					dbst.execute(strSql);
					strSql = "SELECT*FROM " + t_id + "_staff_info;";
					ResultSet result2 = dbst.executeQuery(strSql);
					mode.setNumRows(0);
					while (result2.next()) {
						String d_name = result2.getString("name");
						Date d_age = result2.getDate("age");
						String d_tel = result2.getString("tel_number").substring(0, 3) + "-"
								+ result2.getString("tel_number").substring(3, 7) + "-"
								+ result2.getString("tel_number").substring(7, 11);
						int d_code = result2.getInt("staff_code");
						String d_intime = result2.getString("intime");
						String d_address = result2.getString("address");
						String d_outtime = result2.getString("outtime");
						Object data[] = { d_code, d_name, d_age, d_tel, d_address, d_intime, d_outtime };
						mode.addRow(data);
					}
					break;
				}
			} catch (SQLException a) {
				System.out.println("SQLException :" + a.getMessage());
			}
		}
	}

	public void mouseClicked(MouseEvent e) { // 테이블클릭 이벤트
		JTable jtable = (JTable) e.getSource();
		int row = jtable.getSelectedRow();
		code = (int) mode.getValueAt(row, 0);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException a) {
			System.err.println("드라이버 로드에 실패했습니다.");
		}
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/coinroom?serverTimezone=UTC",
					"root", "java2020");
			Statement dbst = con.createStatement();
			String strSql;
			strSql = "SELECT*FROM " + t_id + "_staff_info WHERE staff_code='" + code + "';";
			ResultSet result = dbst.executeQuery(strSql);
			while (result.next()) {
				name.setText(result.getString("name"));
				String save = result.getString("tel_number").substring(0, 3);
				tel.setSelectedItem((Object) save);
				save = result.getString("tel_number").substring(3, 7);
				mid_num.setText(save);
				save = result.getString("tel_number").substring(7, 11);
				last_num.setText(save);
				address.setText(result.getString("address"));
				String d_year, d_mon, d_day, d_age;
				d_age = result.getString("age");
				d_year = d_age.substring(0, 3);
				d_mon = d_age.substring(5, 7);
				d_day = d_age.substring(8, 10);
				int save2 = Integer.parseInt(d_mon);
				d_mon = Integer.toString(save2);
				save2 = Integer.parseInt(d_day);
				d_day = Integer.toString(save2);
				year.setSelectedItem((Object) year);
				month.setSelectedItem((Object) d_mon);
				day.setSelectedItem((Object) d_day);
			}
			day.setEnabled(true);
			dbst.close();
			con.close();
		} catch (SQLException a) {
			System.out.println("SQLException :" + a.getMessage());
		}
	}

	public void mouseEntered(java.awt.event.MouseEvent e) {
	}

	public void mouseExited(java.awt.event.MouseEvent e) {
	}

	public void mousePressed(java.awt.event.MouseEvent e) {
	}

	public void mouseReleased(java.awt.event.MouseEvent e) {
	}
} // 직원정보 탭 마지막

class MyPage extends JFrame implements ActionListener // 마이페이지 시작
{
	UserSet parent;
	boolean signal = true;
	String t_id;
	JTextField id, name, mid_num, last_num;
	JPasswordField passwd2, passwd;
	JComboBox tel;
	JButton phone_check, check, check2, b1, b2;
	String code[] = { "010", "070", "031", "032" };

	MyPage(String s_id, UserSet us) {
		parent = us;
		t_id = s_id;
		setTitle("MyPage");
		Container ct = getContentPane();
		ct.setLayout(new BorderLayout(0, 20));
		JPanel top = new JPanel();
		top.setLayout(new GridLayout(5, 1));
		JPanel p1 = new JPanel();
		p1.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel l1 = new JLabel("ID                   :");
		id = new JTextField(8);
		id.setEnabled(false);
		p1.add(l1);
		p1.add(id);
		JPanel p2 = new JPanel();
		p2.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel l2 = new JLabel("PW                :");
		passwd = new JPasswordField(8);
		check = new JButton("PW변경");
		check.addActionListener(this);
		p2.add(l2);
		p2.add(passwd);
		p2.add(check);
		JPanel p3 = new JPanel();
		p3.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel l3 = new JLabel("PW                :");
		passwd2 = new JPasswordField(4);
		check2 = new JButton("2차PW변경");
		check2.addActionListener(this);
		p3.add(l3);
		p3.add(passwd2);
		p3.add(check2);
		JPanel p4 = new JPanel();
		p4.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel l4 = new JLabel("이름              :");
		name = new JTextField(8);
		p4.add(l4);
		p4.add(name);
		JPanel p5 = new JPanel();
		p5.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel l5 = new JLabel("전화번호     :");
		mid_num = new JTextField(4);
		tel = new JComboBox(code);
		last_num = new JTextField(4);
		phone_check = new JButton("인증");
		phone_check.addActionListener(this);
		p5.add(l5);
		p5.add(tel);
		p5.add(mid_num);
		p5.add(last_num);
		p5.add(phone_check);

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println("드라이버 로드에 실패했습니다.");
		}
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/coinroom?serverTimezone=UTC",
					"root", "java2020");
			Statement dbst = con.createStatement();
			String strSql;
			strSql = "SELECT*FROM user_info WHERE id='" + t_id + "';";
			ResultSet result = dbst.executeQuery(strSql);
			while (result.next()) {
				id.setText(result.getString("id"));
				passwd.setText(result.getString("passwd"));
				passwd2.setText(result.getString("second_pwd"));
				name.setText(result.getString("name"));
				String save = result.getString("tel_number").substring(0, 3);
				tel.setSelectedItem((Object) save);
				save = result.getString("tel_number").substring(3, 7);
				mid_num.setText(save);
				save = result.getString("tel_number").substring(7, 11);
				last_num.setText(save);
			}
			dbst.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("SQLException :" + e.getMessage());
		}

		top.add(p1);
		top.add(p2);
		top.add(p3);
		top.add(p4);
		top.add(p5);
		ct.add(top, BorderLayout.CENTER);

		JPanel bottom = new JPanel();
		b1 = new JButton("수정완료");
		b2 = new JButton("회원탈퇴");
		b1.addActionListener(this);
		b2.addActionListener(this);
		bottom.add(b1);
		bottom.add(b2);
		ct.add(bottom, BorderLayout.SOUTH);
	}

	public void actionPerformed(ActionEvent ae) {
		String s = ae.getActionCommand();
		String t_id = id.getText(), t_name = name.getText(), t_passwd = passwd.getText(),
				t_second_pwd = passwd2.getText(),
				t_tel = (String) tel.getSelectedItem() + mid_num.getText() + last_num.getText();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println("드라이버 로드에 실패했습니다.");
		}
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/coinroom?serverTimezone=UTC",
					"root", "java2020");
			Statement dbst = con.createStatement();
			String strSql;
			switch (s) {
			case "회원탈퇴":
				int exitOption = JOptionPane.showConfirmDialog(null, "회원탈퇴 하시겠습니까?", "회원탈퇴", JOptionPane.YES_NO_OPTION);
				if (exitOption == JOptionPane.YES_OPTION) {
					strSql = "DELETE FROM user_info WHERE id='" + t_id + "';";
					dbst.executeUpdate(strSql);
					strSql = "DROP TABLE " + t_id + "_staff_info;";
					dbst.executeUpdate(strSql);
					strSql = "DROP TABLE " + t_id + "_customer_info;";
					dbst.executeUpdate(strSql);
					dispose();
					parent.dispose();
					NoraebangPos np = new NoraebangPos();
					np.setSize(650, 460);
					np.setLocation(700, 300);
					np.setTitle("다불러 노래방 POS프로그램");
					np.setVisible(true);
					np.setResizable(false);
					np.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				} else
					return;
				break;
			case "PW변경":
				strSql = "UPDATE user_info SET passwd='" + t_passwd + "' WHERE id ='" + t_id + "';";
				dbst.executeUpdate(strSql);
				MessageDialog md = new MessageDialog(this, "PW 확인", true, "변경되었습니다");
				md.show();
				break;
			case "2차PW변경":
				if (t_second_pwd.length() < 4) {
					MessageDialog md1 = new MessageDialog(this, "2차PW 확인", true, "2차비밀번호는 4자리만 가능합니다");
					md1.show();
					break;
				}
				strSql = "UPDATE user_info SET second_pwd='" + t_second_pwd + "' WHERE id ='" + t_id + "';";
				dbst.executeUpdate(strSql);
				MessageDialog md2 = new MessageDialog(this, "2차PW 확인", true, "변경되었습니다");
				md2.show();
				break;
			case "인증":
				if (t_tel.length() < 11) {
					MessageDialog md3 = new MessageDialog(this, "전화번호 확인", true, "전화번호를 입력해주세요");
					md3.show();
					signal = false;
					break;
				}
				strSql = "SELECT*FROM user_info WHERE tel_number='" + t_tel + "';";
				boolean ok = true;
				String msg;
				ResultSet result = dbst.executeQuery(strSql);
				String save = "";
				while (result.next()) {
					ok = false;
					save = result.getString("tel_number");
				}
				if (ok) {
					msg = "인증 되었습니다";
					signal = true;
				} else {
					msg = "중복된 전화번호 입니다.";
					signal = false;
				}
				MessageDialog md3 = new MessageDialog(this, "전화번호 확인", true, msg);
				md3.show();
				break;
			case "수정완료":
				if (t_name.length() == 0) {
					MessageDialog m = new MessageDialog(this, "이름 확인", true, "이름을 입력해주세요");
					m.show();
					break;
				}
				if (signal) {
					strSql = "UPDATE user_info SET name='" + t_name + "',tel_number ='" + t_tel + "' WHERE id ='" + t_id
							+ "';";
					dbst.executeUpdate(strSql);
					MessageDialog md4 = new MessageDialog(this, "수정완료", true, "변경되었습니다");
					md4.show();
					dispose();
				} else {
					MessageDialog md4 = new MessageDialog(this, "확인", true, "전화번호를 확인하세요");
					md4.show();
				}
				break;
			}
		} catch (SQLException e) {
			System.out.println("SQLException :" + e.getMessage());
		}
	}
} // 마이페이지 마지막

class MemberInfo extends JFrame implements ActionListener, MouseListener { // 회원관리 시작
	boolean signal = true;
	int code;
	JTable i_table;
	String t_id;
	String con[] = { "이름", "전화번호" };
	String header[] = { "회원번호", "이름", "전화번호", "적립 포인트" };
	Object contents[][] = new Object[0][4];
	String te[] = { "010", "070", "031", "032" };
	JTextField sch, name, mid_num, last_num, point;
	JComboBox category, tel;
	DefaultTableModel mode;
	DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
	LineBorder tb = new LineBorder(Color.BLACK, 1);
	Font f1 = new Font("맑은 고딕", Font.PLAIN | Font.BOLD, 16);

	MemberInfo(String s_id) {
		t_id = s_id;
		Container cp = getContentPane();
		cp.setLayout(null);
		cp.setBackground(new Color(255, 227, 236));
		mode = new DefaultTableModel(contents, header);
		i_table = new JTable(mode);
		i_table.addMouseListener(this);
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcm = i_table.getColumnModel();

		for (int i = 0; i < tcm.getColumnCount(); i++)
			tcm.getColumn(i).setCellRenderer(dtcr);

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println("드라이버 로드에 실패했습니다.");
		}
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/coinroom?serverTimezone=UTC",
					"root", "java2020");
			Statement dbst = con.createStatement();
			String strSql;
			strSql = "SELECT*FROM " + t_id + "_customer_info;";
			ResultSet result = dbst.executeQuery(strSql);
			while (result.next()) {
				String d_name = result.getString("name");
				String d_tel = result.getString("tel_number").substring(0, 3) + "-"
						+ result.getString("tel_number").substring(3, 7) + "-"
						+ result.getString("tel_number").substring(7, 11);
				int d_code = result.getInt("customer_code");
				int d_point = result.getInt("point");
				Object data[] = { d_code, d_name, d_tel, d_point };
				mode.addRow(data);
			}
			dbst.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("SQLException :" + e.getMessage());
		}
		category = new JComboBox(con);
		category.setBackground(new Color(255, 255, 255));
		sch = new JTextField(20);

		JScrollPane scrollpane = new JScrollPane(i_table);
		scrollpane.setBorder(tb);
		JButton sch_btn, back_btn, add_btn, mdf_btn, del_btn;

		sch_btn = new JButton("조회");
		sch_btn.setBackground(new Color(255, 250, 164));
		sch_btn.addActionListener(this);

		back_btn = new JButton("뒤로가기");
		back_btn.setBackground(new Color(218, 246, 233));
		back_btn.addActionListener(this);

		add_btn = new JButton("등록");
		add_btn.setBackground(new Color(218, 246, 233));
		add_btn.addActionListener(this);
		add_btn.setFont(f1);
		mdf_btn = new JButton("수정");
		mdf_btn.setBackground(new Color(218, 246, 233));
		mdf_btn.addActionListener(this);
		mdf_btn.setFont(f1);
		del_btn = new JButton("삭제");
		del_btn.setBackground(new Color(218, 246, 233));
		del_btn.addActionListener(this);
		del_btn.setFont(f1);

		JLabel i_label = new JLabel("<회원 정보>");
		i_label.setFont(f1);
		JPanel info_panel = new JPanel();
		info_panel.setLayout(new GridLayout(3, 1));
		info_panel.setBorder(tb);

		JPanel p2 = new JPanel();
		p2.setLayout(new FlowLayout(FlowLayout.LEFT));
		p2.setBackground(new Color(255, 255, 255));
		JLabel l1 = new JLabel("이름            :");
		name = new JTextField(8);
		p2.add(l1);
		p2.add(name);
		JPanel p3 = new JPanel();
		p3.setLayout(new FlowLayout(FlowLayout.LEFT));
		p3.setBackground(new Color(255, 255, 255));
		JLabel l2 = new JLabel("전화번호   :");
		tel = new JComboBox(te);
		mid_num = new JTextField(4);
		last_num = new JTextField(4);
		p3.add(l2);
		p3.add(tel);
		p3.add(mid_num);
		p3.add(last_num);
		JPanel p4 = new JPanel();
		p4.setLayout(new FlowLayout(FlowLayout.LEFT));
		p4.setBackground(new Color(255, 255, 255));
		JLabel l3 = new JLabel("포인트       :");
		JLabel l4 = new JLabel("P");
		point = new JTextField(10);
		point.setText("0");
		point.setHorizontalAlignment(JTextField.RIGHT);
		p4.add(l3);
		p4.add(point);
		p4.add(l4);
		info_panel.add(p2);
		info_panel.add(p3);
		info_panel.add(p4);

		category.setBounds(50, 25, 80, 30);
		sch.setBounds(140, 25, 300, 30);
		sch_btn.setBounds(450, 25, 70, 30);
		scrollpane.setBounds(50, 80, 600, 600);
		back_btn.setBounds(940, 620, 110, 60);
		i_label.setBounds(825, 80, 100, 30);
		info_panel.setBounds(700, 120, 350, 250);
		add_btn.setBounds(750, 400, 90, 40);
		mdf_btn.setBounds(850, 400, 90, 40);
		del_btn.setBounds(950, 400, 90, 40);
		cp.add(category);
		cp.add(sch);
		cp.add(sch_btn);
		cp.add(back_btn);
		cp.add(scrollpane);
		cp.add(i_label);
		cp.add(info_panel);
		cp.add(add_btn);
		cp.add(mdf_btn);
		cp.add(del_btn);

	}

	public void actionPerformed(ActionEvent ae) {
		String s = ae.getActionCommand();
		String t_name = name.getText(), t_tel = (String) tel.getSelectedItem() + mid_num.getText() + last_num.getText(),
				t_point = point.getText();

		if (s == "뒤로가기") {
			MainMenu win = new MainMenu("메인메뉴", t_id);
			win.setSize(600, 550);
			win.setLocation(680, 280);
			win.show();
			win.setResizable(false);
			win.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			dispose();
		} else {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.err.println("드라이버 로드에 실패했습니다.");
			}
			try {
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/coinroom?serverTimezone=UTC",
						"root", "java2020");
				Statement dbst = con.createStatement();
				String strSql;
				switch (s) {
				case "조회":
					mode.setNumRows(0);
					String sch1 = sch.getText();
					if (sch1.equals("")) {
						strSql = "SELECT*FROM " + t_id + "_customer_info;";
						ResultSet result = dbst.executeQuery(strSql);
						while (result.next()) {
							String d_name = result.getString("name");
							String d_tel = result.getString("tel_number").substring(0, 3) + "-"
									+ result.getString("tel_number").substring(3, 7) + "-"
									+ result.getString("tel_number").substring(7, 11);
							int d_code = result.getInt("customer_code");
							int d_point = result.getInt("point");
							Object data[] = { d_code, d_name, d_tel, d_point };
							mode.addRow(data);
						}
					} else {
						String ctg = (String) category.getSelectedItem();
						switch (ctg) {
						case "이름":
							strSql = "SELECT*FROM " + t_id + "_customer_info WHERE name='" + sch1 + "';";
							ResultSet result2 = dbst.executeQuery(strSql);
							while (result2.next()) {
								String d_name = result2.getString("name");
								String d_tel = result2.getString("tel_number").substring(0, 3) + "-"
										+ result2.getString("tel_number").substring(3, 7) + "-"
										+ result2.getString("tel_number").substring(7, 11);
								int d_code = result2.getInt("customer_code");
								int d_point = result2.getInt("point");
								Object data[] = { d_code, d_name, d_tel, d_point };
								mode.addRow(data);
							}
							break;
						case "전화번호":
							strSql = "SELECT*FROM " + t_id + "_customer_info WHERE tel_number='" + sch1 + "';";
							ResultSet result3 = dbst.executeQuery(strSql);
							while (result3.next()) {
								String d_name = result3.getString("name");
								String d_tel = result3.getString("tel_number").substring(0, 3) + "-"
										+ result3.getString("tel_number").substring(3, 7) + "-"
										+ result3.getString("tel_number").substring(7, 11);
								int d_code = result3.getInt("customer_code");
								int d_point = result3.getInt("point");
								Object data[] = { d_code, d_name, d_tel, d_point };
								mode.addRow(data);
							}
							break;
						}
					}
					break;
				case "등록":
					if (t_name.length() == 0 && t_tel.length() < 11) {
						MessageDialog md = new MessageDialog(this, "확인", true, "이름, 전화번호를 정확히 입력해주세요");
						md.show();
						break;
					} else if (t_name.length() == 0) {
						MessageDialog md = new MessageDialog(this, "이름 확인", true, "이름을 입력해주세요");
						md.show();
						break;
					} else if (t_tel.length() < 11) {
						MessageDialog md = new MessageDialog(this, "전화번호 확인", true, "전화번호를 정확히 입력해주세요");
						md.show();
						break;
					} else {
						strSql = "SELECT*FROM " + t_id + "_customer_info WHERE tel_number='" + t_tel + "';";
						ResultSet rs = dbst.executeQuery(strSql);
						boolean ok = false;
						while (rs.next())
							ok = true;
						if (ok) {
							MessageDialog md = new MessageDialog(this, "전화번호 확인", true, "중복된 전화번호입니다");
							md.show();
							signal = false;
						} else
							signal = true;
						if (signal) {
							strSql = "INSERT INTO " + t_id
									+ "_customer_info (customer_code,name,tel_number,point) VALUES (null" + ",'"
									+ t_name + "','" + t_tel + "','" + t_point + "');";
							dbst.executeUpdate(strSql);
							strSql = "ALTER TABLE " + t_id + "_customer_info AUTO_INCREMENT= 1;";
							dbst.execute(strSql);
							strSql = "SET @COUNT = 0;";
							dbst.execute(strSql);
							strSql = "UPDATE " + t_id + "_customer_info SET customer_code = @COUNT:=@COUNT+1;";
							dbst.execute(strSql);
							strSql = "SELECT*FROM " + t_id + "_customer_info;";
							ResultSet result4 = dbst.executeQuery(strSql);
							mode.setNumRows(0);
							while (result4.next()) {
								String d_name = result4.getString("name");
								String d_tel = result4.getString("tel_number").substring(0, 3) + "-"
										+ result4.getString("tel_number").substring(3, 7) + "-"
										+ result4.getString("tel_number").substring(7, 11);
								int d_code = result4.getInt("customer_code");
								int d_point = result4.getInt("point");
								Object data[] = { d_code, d_name, d_tel, d_point };
								mode.addRow(data);
							}
						}
						break;
					}
				case "수정":
					if (t_name.length() == 0 && t_tel.length() < 11) {
						MessageDialog md = new MessageDialog(this, "확인", true, "이름, 전화번호를 정확히 입력해주세요");
						md.show();
						break;
					} else if (t_tel.length() < 11) {
						MessageDialog md = new MessageDialog(this, "전화번호 확인", true, "전화번호를 정확히 입력해주세요");
						md.show();
						break;
					} else if (t_name.length() == 0) {
						MessageDialog md = new MessageDialog(this, "이름 확인", true, "이름을 입력해주세요");
						md.show();
						break;
					} else {
						strSql = "SELECT*FROM " + t_id + "_customer_info WHERE tel_number='" + t_tel
								+ "' and customer_code='" + code + "';";
						ResultSet rs = dbst.executeQuery(strSql);
						boolean a = false;
						while (rs.next()) {
							a = true;
						}
						if (a) {
							strSql = "UPDATE " + t_id + "_customer_info SET name='" + t_name + "',tel_number='" + t_tel
									+ "',point='" + t_point + "' WHERE customer_code ='" + code + "';";
							dbst.executeUpdate(strSql);
							strSql = "SELECT*FROM " + t_id + "_customer_info;";
							ResultSet result5 = dbst.executeQuery(strSql);
							mode.setNumRows(0);
							while (result5.next()) {
								String d_name = result5.getString("name");
								String d_tel = result5.getString("tel_number").substring(0, 3) + "-"
										+ result5.getString("tel_number").substring(3, 7) + "-"
										+ result5.getString("tel_number").substring(7, 11);
								int d_code = result5.getInt("customer_code");
								int d_point = result5.getInt("point");
								Object data[] = { d_code, d_name, d_tel, d_point };
								mode.addRow(data);
							}
							break;
						}
						strSql = "SELECT*FROM " + t_id + "_customer_info WHERE tel_number='" + t_tel + "';";
						ResultSet rs2 = dbst.executeQuery(strSql);
						while (rs2.next()) {
							MessageDialog md = new MessageDialog(this, "전화번호 확인", true, "중복된 전화번호입니다");
							md.show();
							a = true;
						}
						if (!a) {
							strSql = "UPDATE " + t_id + "_customer_info SET name='" + t_name + "',tel_number='" + t_tel
									+ "',point='" + t_point + "' WHERE customer_code ='" + code + "';";
							dbst.executeUpdate(strSql);
							strSql = "SELECT*FROM " + t_id + "_customer_info;";
							ResultSet result5 = dbst.executeQuery(strSql);
							mode.setNumRows(0);
							while (result5.next()) {
								String d_name = result5.getString("name");
								String d_tel = result5.getString("tel_number").substring(0, 3) + "-"
										+ result5.getString("tel_number").substring(3, 7) + "-"
										+ result5.getString("tel_number").substring(7, 11);
								int d_code = result5.getInt("customer_code");
								int d_point = result5.getInt("point");
								Object data[] = { d_code, d_name, d_tel, d_point };
								mode.addRow(data);
							}
							break;
						}
						break;
					}
				case "삭제":
					strSql = "DELETE FROM " + t_id + "_customer_info WHERE customer_code='" + code + "';";
					dbst.executeUpdate(strSql);
					strSql = "ALTER TABLE " + t_id + "_customer_info AUTO_INCREMENT= 1;";
					dbst.execute(strSql);
					strSql = "SET @COUNT = 0;";
					dbst.execute(strSql);
					strSql = "UPDATE " + t_id + "_customer_info SET customer_code = @COUNT:=@COUNT+1;";
					dbst.execute(strSql);
					strSql = "SELECT*FROM " + t_id + "_customer_info;";
					ResultSet result6 = dbst.executeQuery(strSql);
					mode.setNumRows(0);
					while (result6.next()) {
						String d_name = result6.getString("name");
						String d_tel = result6.getString("tel_number").substring(0, 3) + "-"
								+ result6.getString("tel_number").substring(3, 7) + "-"
								+ result6.getString("tel_number").substring(7, 11);
						;
						int d_code = result6.getInt("customer_code");
						int d_point = result6.getInt("point");
						Object data[] = { d_code, d_name, d_tel, d_point };
						mode.addRow(data);
					}
					break;
				}
			} catch (SQLException a) {
				System.out.println("SQLException :" + a.getMessage());
			}
		}
	}

	public void mouseClicked(MouseEvent e) {
		JTable jtable = (JTable) e.getSource();
		int row = jtable.getSelectedRow();
		code = (int) mode.getValueAt(row, 0);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException a) {
			System.err.println("드라이버 로드에 실패했습니다.");
		}
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/coinroom?serverTimezone=UTC",
					"root", "java2020");
			Statement dbst = con.createStatement();
			String strSql;
			strSql = "SELECT*FROM " + t_id + "_customer_info WHERE customer_code='" + code + "';";
			ResultSet result = dbst.executeQuery(strSql);
			while (result.next()) {
				name.setText(result.getString("name"));
				String save = result.getString("tel_number").substring(0, 3);
				tel.setSelectedItem((Object) save);
				save = result.getString("tel_number").substring(3, 7);
				mid_num.setText(save);
				save = result.getString("tel_number").substring(7, 11);
				last_num.setText(save);
				point.setText(result.getString("point"));
			}
			dbst.close();
			con.close();
		} catch (SQLException a) {
			System.out.println("SQLException :" + a.getMessage());
		}
	}

	public void mouseEntered(java.awt.event.MouseEvent e) {
	}

	public void mouseExited(java.awt.event.MouseEvent e) {
	}

	public void mousePressed(java.awt.event.MouseEvent e) {
	}

	public void mouseReleased(java.awt.event.MouseEvent e) {
	}
} // 회원관리 마지막