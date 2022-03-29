package BusinessManagementCollection;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.*;

class MainScreen extends JPanel implements ActionListener { // Home탭
	JPanel leftPanel; // 코인룸
	JPanel rightPanel; // 시간,메뉴,그림공간
	JPanel[] p = new JPanel[12]; // 룸마다 패널
	public static JLabel[] lb = new JLabel[12]; // 룸마다 레이블
	JButton menuB;
	BusinessManagement parent;
	String t_id;

	public MainScreen(String s_id, BusinessManagement bm) {
		t_id = s_id;
		parent = bm;
		setBackground(new Color(255, 255, 246));
		leftPanel = new JPanel();
		rightPanel = new JPanel();

		leftPanel.setLayout(new GridLayout(4, 3, 20, 20));
		for (int i = 0; i < 12; i++) {
			p[i] = new JPanel();
			p[i].setBackground(new Color(252, 238, 245));
			final int idx = i; // 선택된 방의 위치를 저장할 변수
			p[i].addMouseListener(new MouseListener() {
				public void mouseClicked(MouseEvent e) {
					SalesWindow sw = new SalesWindow(idx + 1, t_id);
					sw.setTitle("서비스 창");
					sw.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					sw.setSize(1000, 700);
					sw.setLocation(500, 250);
					sw.setVisible(true);
				}

				public void mousePressed(MouseEvent e) {
				}

				public void mouseReleased(MouseEvent e) {
				}

				public void mouseEntered(MouseEvent e) {
					p[idx].setBorder(new LineBorder(Color.WHITE));
				}

				public void mouseExited(MouseEvent e) {
					p[idx].setBorder(null);
				}
			});
			leftPanel.add(p[i]);
			lb[i] = new JLabel(); // 패널위에 올릴 레이블
			lb[i].setPreferredSize(new Dimension(200, 140));
			lb[i].setHorizontalAlignment(JLabel.CENTER);
			lb[i].setFont(Service.font1);
			p[i].add(lb[i]);

			StringBuilder sb = new StringBuilder();
			sb.append("<html>").append(i + 1).append(")&nbsp;&nbsp;최대인원 ");
			if (i < 7) {
				sb.append(3);
			} else {
				sb.append(6);
			}
			sb.append("명<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;빈방</html>");
			lb[i].setText(sb.toString());
		} // for문

		leftPanel.setPreferredSize(new Dimension(700, 700));
		leftPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
		leftPanel.setBackground(new Color(255, 255, 246));

		rightPanel.setLayout(new BorderLayout(0, 20));
		JLabel timeLabel = new JLabel("접속시간 : " + currentTime());
		timeLabel.setFont(Service.font1);
		menuB = new JButton("M E N U");
		menuB.addActionListener(this); // 클릭 시 (영업관리, 근태관리, 재고관리, 회원관리, 도움말, 관리자 설정 ) 메뉴창으로 가도록
		menuB.setBackground(Service.color1);
		menuB.setPreferredSize(new Dimension(0, 90));
		menuB.setFont(Service.font2);
		ImageIcon image = new ImageIcon("src/BusinessManagementCollection/titleimage.jpg");
		JLabel imgLabel = new JLabel(image);
		rightPanel.add(timeLabel, BorderLayout.NORTH);
		rightPanel.add(menuB, BorderLayout.CENTER);
		rightPanel.add(imgLabel, BorderLayout.SOUTH);
		rightPanel.setBackground(new Color(255, 255, 246));
		rightPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

		add(leftPanel, BorderLayout.CENTER);
		add(rightPanel, BorderLayout.EAST);
	}

	public String currentTime() {
		Date today = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일 E요일  a hh:mm:ss");
		return format.format(today);
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == menuB) {
			MainMenu win = new MainMenu("메인메뉴", t_id);
			win.setSize(600, 550);
			win.setLocation(680, 280);
			win.show();
			win.setResizable(false);
			win.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			parent.dispose();
		}
	}

}

//원하는 셀 만들기 위해 TableCellRenderer
class MyDefaultTableCellRenderer1 extends DefaultTableCellRenderer {
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		if (!table.isColumnSelected(column)) {
			cell.setForeground(Color.blue);
		}
		return cell;
	}
}

//테이블 셀 모두 수정 못하도록
class MyDefaultTableModel extends DefaultTableModel {
	public MyDefaultTableModel(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public boolean isCellEditable(int row, int column) {
		return false;
	}
}

class PaymentHistory extends JPanel implements MouseListener, ActionListener { // 결제내역탭
	JPanel top;
	JPanel center;

	Vector<String> columnName;
	public static Vector<Vector<String>> rowData;
	public static JTable table = null;
	MyDefaultTableModel model = null;

	JLabel roomNumLb;
	JComboBox roomNumCombo;
	String roomsNum[] = { "전체", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" };
	JLabel saleDateLb;
	JTextField saleDateTf;
	JButton inquiryB;

	JScrollPane tableSP;
	int row; // 테이블의 선택된 행의 위치를 저장할 변수
	int column;

	public PaymentHistory() {
		setBackground(new Color(255, 255, 246));
		setLayout(new BorderLayout(0, 30));
		top = new JPanel();
		top.setBackground(new Color(255, 255, 246));
		center = new JPanel();
		center.setBackground(new Color(255, 255, 246));
		add(top, BorderLayout.NORTH);
		add(center, BorderLayout.CENTER);

		roomNumLb = new JLabel("방번호");
		roomNumCombo = new JComboBox(roomsNum);
		saleDateLb = new JLabel("판매날짜");
		saleDateTf = new JTextField(10);
		inquiryB = new JButton("조회");
		inquiryB.setBackground(new Color(255, 250, 164));
		inquiryB.addActionListener(this);

		top.setLayout(new FlowLayout());
		top.add(roomNumLb);
		top.add(roomNumCombo);
		top.add(saleDateLb);
		top.add(saleDateTf);
		top.add(inquiryB);

		columnName = new Vector<String>();
		columnName.add("결제번호");
		columnName.add("방번호");
		columnName.add("이용타입");
		columnName.add("판매날짜");
		columnName.add("결제금액");
		columnName.add("결제방법");
		columnName.add("전화번호");
		columnName.add("환불");

		rowData = new Vector<Vector<String>>();
		rowData.addAll(getcoinroomSaleList(null));
		model = new MyDefaultTableModel(rowData, columnName);
		model.fireTableDataChanged();
		table = new JTable(model);
		table.setBackground(Service.color2);
		table.setFont(Service.font1);
		table.setRowHeight(40);
		table.addMouseListener(this);
		tableSP = new JScrollPane(table);
		tableSP.setPreferredSize(new Dimension(1000, 600));
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		table.getColumnModel().getColumn(1).setPreferredWidth(30);
		table.getColumnModel().getColumn(3).setPreferredWidth(180);
		MyDefaultTableCellRenderer1 renderer1 = new MyDefaultTableCellRenderer1();
		table.getColumn("환불").setCellRenderer(renderer1);
		center.add(tableSP);
	}

	public void mouseClicked(MouseEvent e) {
		column = table.getSelectedColumn();
		row = table.getSelectedRow();
		String index_number = table.getValueAt(row, 0).toString();
		String refundPrice_str = table.getValueAt(row, 7).toString();
		boolean isNum = false;
		if (column == 7) {
			for (int i = 0; i < refundPrice_str.length(); i++) {
				char check = refundPrice_str.charAt(i);
				if (check >= 48 && check <= 57) {
					isNum = true;// 숫자일경우
					break;
				}
			}
			if (!isNum) {
				Object[] btn = { "YES", "NO" };
				int selected = JOptionPane.showOptionDialog(null, "정말 환불 하시겠습니까?", "환불", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, btn, btn[0]);
				if (selected == JOptionPane.YES_OPTION) {
					JOptionPane.showMessageDialog(null, "환불이 완료되었습니다!", "응답", JOptionPane.INFORMATION_MESSAGE);
					Connection con = null;
					try {
						Class.forName("com.mysql.cj.jdbc.Driver");
						con = DriverManager.getConnection("jdbc:mysql://localhost:3306/coinroom?serverTimezone=UTC",
								"root", "java2020");
					} catch (ClassNotFoundException ex) {
						ex.printStackTrace();
					} catch (SQLException ex) {
						ex.printStackTrace();
					}

					PreparedStatement pstmt = null;
					String strSql = "update coinroom_sale set refund_price = pay_price where index_number = "
							+ index_number;
					try {
						pstmt = con.prepareStatement(strSql);
						pstmt.executeUpdate();
					} catch (SQLException ex) {
						ex.printStackTrace();
					}

					String strSql2 = "select refund_price from coinroom_sale where index_number = " + index_number;
					ResultSet rs;
					String refundPrice = "";
					try {
						pstmt = con.prepareStatement(strSql2);
						rs = pstmt.executeQuery();
						rs.next();
						refundPrice = rs.getString("refund_price");
					} catch (SQLException ex) {
						ex.printStackTrace();
					}

					table.setValueAt(refundPrice, row, 7);

				} else
					JOptionPane.showMessageDialog(null, "환불이 취소되었습니다!", "응답", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void actionPerformed(ActionEvent ae) {
		String s = ae.getActionCommand();
		String roomNumCombo_str = "";
		String saleDateTf_str = "";

		if ("조회".equals(s)) {
			roomNumCombo_str = roomNumCombo.getSelectedItem().toString();
			saleDateTf_str = saleDateTf.getText();
			String searchQuery = null;

			if (saleDateTf_str.equals("")) { // 판매날짜가 비었을때
				if (roomNumCombo_str.equals("전체")) {
				} else
					searchQuery = "where room_number='" + roomNumCombo_str + "'";
			} else { // 둘다 입력되었을때
				if (roomNumCombo_str.equals("전체")) {
					searchQuery = "where sale_date='" + saleDateTf_str + "'";
				} else {
					searchQuery = "where room_number='" + roomNumCombo_str + "'and sale_date='" + saleDateTf_str + "'";
				}
			}
			rowData.removeAllElements();
			rowData.addAll(getcoinroomSaleList(searchQuery));
			table.updateUI();
		}

	}

	public static Vector<Vector<String>> getcoinroomSaleList(String searchQuery) { // 상황에따라 검색질의어 붙이기
		Vector<Vector<String>> result = new Vector<Vector<String>>();
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/coinroom?serverTimezone=UTC", "root",
					"java2020");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		PreparedStatement pstmt = null;
		ResultSet rs;
		StringBuilder strSql = new StringBuilder("select * from coinroom_sale");
		if (searchQuery != null) {
			strSql.append(" ");
			strSql.append(searchQuery);
		}

		try {
			pstmt = con.prepareStatement(strSql.toString());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Vector<String> v1 = new Vector<String>();
				v1.add(rs.getString("index_number"));
				v1.add(rs.getString("room_number"));
				v1.add(rs.getString("use_type"));
				v1.add(rs.getString("sale_date"));
				v1.add(rs.getString("pay_price"));
				v1.add(rs.getString("pay_method"));
				v1.add(rs.getString("tel_number"));
				if (rs.getInt("refund_price") == 0) {
					v1.add("     [ Click ]");
				} else {
					v1.add(Integer.toString(rs.getInt("refund_price")));
				}
				result.add(v1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}
}

class BusinessManagement extends JFrame { // 영업관리창
	String t_id;

	public BusinessManagement(String s_id) {

		t_id = s_id;
		JTabbedPane jtp = new JTabbedPane();
		// 코인룸관리(메인)
		MainScreen ms = new MainScreen(t_id, this);
		// 코인룸관리(결제내역)
		PaymentHistory ph = new PaymentHistory();
		jtp.addTab("HOME", ms);
		jtp.addTab("결제내역", ph);

		Container ct = getContentPane();
		ct.setBackground(new Color(255, 227, 236));
		ct.add(jtp);
	}
}

class Service extends JPanel implements ActionListener { // 서비스탭
	JPanel leftPanel;
	JPanel leftTop;
	JPanel leftCenter;
	JPanel leftBottom;
	JPanel rightPanel;
	JPanel rightTop;

	JLabel orderShowLb; // 주문한곡,시간보여주는레이블
	JLabel songUnitLb; // (곡),(분)
	JButton deleteB; // 삭제버튼
	JLabel priceLb; // 결제금액
	public static JLabel showPriceLb; // 결제금액보여주는레이블
	JButton pointB;
	JButton payB;
	JButton paymentFinB;
	JButton sendMsgB;
	JButton completeB;

	JButton[] b = new JButton[16]; // 숫자,주문버튼
	String t_id;
	int roomNum;

	// 액션리스너에서 쓰일 변수들
	String tempStr1 = new String(); // 임시문자열1
	String tempStr2 = new String(); // 임시문자열2
	int actualPrice = 0; // 실제결제할금액

	public static boolean isPayCompleted = false; // 결제완료할때 필요한 flag두기
	public static String payMethod = "";

	public static final Font font1 = new Font("맑은 고딕", Font.BOLD, 17);
	public static final Font font2 = new Font("SansSerif", Font.BOLD, 25);
	public static final Font font3 = new Font("맑은 고딕", Font.BOLD, 23);
	public static final Font font4 = new Font("SansSerif", Font.BOLD, 20);
	public static final Color color1 = new Color(241, 227, 255); // 연보라
	public static final Color color2 = new Color(242, 249, 255); // 연하늘
	public static final Color color3 = new Color(222, 239, 255); // 하늘

	public Service(int roomNum, String s_id) {

		t_id = s_id;
		this.roomNum = roomNum;
		leftPanel = new JPanel();
		leftTop = new JPanel();
		leftCenter = new JPanel();
		leftBottom = new JPanel();
		rightPanel = new JPanel();

		leftPanel.setLayout(new BorderLayout());
		leftPanel.setPreferredSize(new Dimension(400, 600));
		JLabel serviceTitle = new JLabel(roomNum + "번방  곡/시간");
		orderShowLb = new JLabel();
		songUnitLb = new JLabel("(곡)");
		deleteB = new JButton("←");
		serviceTitle.setFont(font3);
		orderShowLb.setFont(font3);
		orderShowLb.setBorder(new LineBorder(Color.BLACK));
		orderShowLb.setHorizontalAlignment(JLabel.RIGHT);
		orderShowLb.setPreferredSize(new Dimension(180, 50));
		songUnitLb.setFont(font3);
		deleteB.setFont(font3);
		deleteB.setBorder(new LineBorder(Color.BLACK));
		deleteB.setPreferredSize(new Dimension(50, 50));
		deleteB.addActionListener(this);

		leftTop.setBackground(color3);
		leftBottom.setBackground(color3);
		leftBottom.setPreferredSize(new Dimension(0, 60));

		leftCenter.setLayout(new GridLayout(4, 4));
		b[0] = new JButton("7");
		b[1] = new JButton("8");
		b[2] = new JButton("9");
		b[3] = new JButton("<html>곡(수)</html>");
		b[4] = new JButton("4");
		b[5] = new JButton("5");
		b[6] = new JButton("6");
		b[7] = new JButton("<html>&nbsp;시간<br/>&nbsp;(분)</html>");
		b[8] = new JButton("1");
		b[9] = new JButton("2");
		b[10] = new JButton("3");
		b[11] = new JButton();
		b[12] = new JButton();
		b[13] = new JButton("0");
		b[14] = new JButton();
		b[15] = new JButton("확인");
		for (int i = 0; i < 16; i++) {
			b[i].setBackground(color2);
			b[i].setFont(font1);
			b[i].addActionListener(this);
			leftCenter.add(b[i]);
		}

		leftTop.add(serviceTitle);
		leftBottom.add(orderShowLb);
		leftBottom.add(songUnitLb);
		leftBottom.add(deleteB);

		leftPanel.add(leftTop, BorderLayout.NORTH);
		leftPanel.add(leftCenter, BorderLayout.CENTER);
		leftPanel.add(leftBottom, BorderLayout.SOUTH);
		leftPanel.setBorder(new EmptyBorder(25, 25, 25, 25));

		rightPanel.setLayout(new BorderLayout(0, 35));
		rightTop = new JPanel();
		priceLb = new JLabel("결제금액");
		showPriceLb = new JLabel("0");
		JLabel wonLb1 = new JLabel("원");
		pointB = new JButton("포인트 사용/적립");
		payB = new JButton("결제하기");
		paymentFinB = new JButton("결제완료");
		sendMsgB = new JButton("Send Message >>");
		completeB = new JButton("판매완료");

		// 결제부분
		rightTop.setLayout(null);
		rightTop.setPreferredSize(new Dimension(400, 300));
		rightTop.setBorder(new LineBorder(Color.BLACK));
		rightTop.setBackground(color2);

		priceLb.setBounds(50, 60, 100, 50);
		showPriceLb.setBounds(150, 60, 200, 50);
		wonLb1.setBounds(360, 60, 50, 50);
		pointB.setBounds(110, 140, 200, 50);
		payB.setBounds(80, 220, 120, 40);
		paymentFinB.setBounds(220, 220, 120, 40);

		priceLb.setFont(font1);
		showPriceLb.setFont(font1);
		wonLb1.setFont(font1);
		showPriceLb.setHorizontalAlignment(JLabel.RIGHT);
		showPriceLb.setBorder(new LineBorder(Color.BLACK));
		pointB.setFont(font1);

		payB.setFont(font1);
		paymentFinB.setFont(font1);
		paymentFinB.addActionListener(this);
		completeB.addActionListener(this);

		rightTop.add(priceLb);
		rightTop.add(showPriceLb);
		rightTop.add(wonLb1);
		rightTop.add(pointB);
		rightTop.add(payB);
		rightTop.add(paymentFinB);

		// 메세지보내기
		sendMsgB.setFont(font4);
		sendMsgB.setPreferredSize(new Dimension(0, 50));
		sendMsgB.setBackground(Service.color3);
		sendMsgB.addActionListener(new WindowActionListener(0, t_id));

		// 판매완료
		completeB.setFont(font3);
		completeB.setPreferredSize(new Dimension(0, 80));

		rightPanel.add(rightTop, BorderLayout.NORTH);
		rightPanel.add(sendMsgB, BorderLayout.CENTER);
		rightPanel.add(completeB, BorderLayout.SOUTH);
		rightPanel.setBorder(new EmptyBorder(25, 25, 25, 25));

		add(leftPanel, BorderLayout.WEST);
		add(rightPanel, BorderLayout.EAST);
	}

	public void actionPerformed(ActionEvent ae) {
		String ac = ae.getActionCommand();
		if (ae.getSource() == this.b[0]) { // 숫자버튼
			tempStr1 += "7";
		} else if (ae.getSource() == this.b[1]) {
			tempStr1 += "8";
		} else if (ae.getSource() == this.b[2]) {
			tempStr1 += "9";
		} else if (ae.getSource() == this.b[3]) {
			songUnitLb.setText("(곡)");
		} else if (ae.getSource() == this.b[4]) {
			tempStr1 += "4";
		} else if (ae.getSource() == this.b[5]) {
			tempStr1 += "5";
		} else if (ae.getSource() == this.b[6]) {
			tempStr1 += "6";
		} else if (ae.getSource() == this.b[7] || ae.getSource() == this.b[11]) {
			songUnitLb.setText("(분)");
		} else if (ae.getSource() == this.b[8]) {
			tempStr1 += "1";
		} else if (ae.getSource() == this.b[9]) {
			tempStr1 += "2";
		} else if (ae.getSource() == this.b[10]) {
			tempStr1 += "3";
		} else if (ae.getSource() == this.b[13]) {
			tempStr1 += "0";
		}

		if ("←".equals(ac)) { // 삭제버튼
			if (tempStr1.length() > 0) {
				tempStr1 = (String) (tempStr1.subSequence(0, tempStr1.length() - 1));
			}
		} // 삭제버튼끝
		orderShowLb.setText(tempStr1);

		if ("확인".equals(ac)) { // 확인버튼
			if ("(분)".equals(songUnitLb.getText())) {
				tempStr1 = orderShowLb.getText();
				actualPrice = Integer.parseInt(tempStr1) * 100;
			} else if ("(곡)".equals(songUnitLb.getText())) {
				tempStr2 = orderShowLb.getText();
				int tempStr1_intType = Integer.parseInt(tempStr1);
				if (tempStr1_intType % 2 == 0) {
					tempStr1_intType = tempStr1_intType / 2 * 500;
				} else {
					tempStr1_intType = tempStr1_intType / 2 * 500 + 300;
				}
				actualPrice = tempStr1_intType;
			}
			showPriceLb.setText(Integer.toString(actualPrice));

			for (ActionListener al : payB.getActionListeners()) {
				payB.removeActionListener(al);
			}
			payB.addActionListener(new WindowActionListener(Integer.parseInt(showPriceLb.getText()), t_id));

			for (ActionListener al : pointB.getActionListeners()) {
				pointB.removeActionListener(al);
			}
			pointB.addActionListener(new WindowActionListener(actualPrice, t_id));
		} // 확인버튼끝

		// 디비연결
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/coinroom?serverTimezone=UTC", "root",
					"java2020");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		PreparedStatement pstmt = null;
		ResultSet rs;

		StringBuilder sb = new StringBuilder();

		if ("결제완료".equals(ac)) {
			String useType;
			int resultPrice = actualPrice - Point_use.usedPoint;
			// 결제됐는지 체크 >> 사용중인 방으로 세팅
			if (isPayCompleted) { // 결제되면
				if (songUnitLb.getText().equals("(곡)")) {
					useType = "코인";
					sb.append("<html>").append(roomNum).append(")&nbsp;&nbsp;최대인원 ");
					if (roomNum < 7) {
						sb.append(3);
					} else {
						sb.append(6);
					}
					sb.append("명<br/><br/>코인  " + orderShowLb.getText() + "곡 <br/><br/>결제금액 : " + resultPrice
							+ "</html>");
					MainScreen.lb[roomNum - 1].setText(sb.toString());
				} else {
					useType = "시간";
					sb.append("<html>").append(roomNum).append(")&nbsp;&nbsp;최대인원 ");
					if (roomNum < 7) {
						sb.append(3);
					} else {
						sb.append(6);
					}
					sb.append("명<br/><br/>시간  " + orderShowLb.getText() + "분 <br/><br/>결제금액 : " + resultPrice
							+ "</html>");
					MainScreen.lb[roomNum - 1].setText(sb.toString());
				}
				actualPrice = 0;
			} else {
				JOptionPane.showMessageDialog(this, "결제가 되지 않았습니다. 결제 후 결제완료를 눌러주세요!", "결제완료",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			// coinroom_info에서 사용여부를 0(빈방)에서 1(사용중)로 바꿈
			String strSql1 = "update coinroom_info set use_whether=1 where room_number='" + roomNum + "';";
			try {
				pstmt = con.prepareStatement(strSql1);
				pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			String phoneNo;
			if (Point_earn.phoneNumTf_str == null && Point_use.phoneNumTf_str == null) {
				phoneNo = "비회원";
			} else if (Point_earn.phoneNumTf_str != null) {
				phoneNo = Point_earn.phoneNumTf_str;
			} else {
				phoneNo = Point_use.phoneNumTf_str;
			}

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String saleTime = format.format(new Date());
			// db테이블 coinroom_sale에 내역 추가
			String strSql2 = "insert into coinroom_sale(room_number, use_type, sale_date, pay_price, pay_method, tel_number, refund_price) values('"
					+ roomNum + "', '" + useType + "', '" + saleTime + "', '" + resultPrice + "', '" + Service.payMethod
					+ "', '" + phoneNo + "', 0)";

			try {
				pstmt = con.prepareStatement(strSql2);
				pstmt.executeUpdate();
				JOptionPane.showMessageDialog(this, "결제가 완료되었습니다!", "결제완료", JOptionPane.INFORMATION_MESSAGE);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			// 결제테이블removeAll> 다시db에서가져오기
			PaymentHistory.rowData.removeAllElements();
			PaymentHistory.rowData.addAll(PaymentHistory.getcoinroomSaleList(null));
			PaymentHistory.table.updateUI();

			Point_use.usedPoint = 0;
			Service.isPayCompleted = false;
		}

		if ("판매완료".equals(ac)) {
			// 빈방세팅
			sb.append("<html>").append(roomNum).append(")&nbsp;&nbsp;최대인원 ");
			if (roomNum < 7) {
				sb.append(3);
			} else {
				sb.append(6);
			}
			sb.append("명<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;빈방</html>");
			MainScreen.lb[roomNum - 1].setText(sb.toString());
			// coinroom_info에서 사용여부를 1(사용중)에서 0(빈방)으로 바꿈
			String strSql3 = "update coinroom_info set use_whether=0 where room_number='" + roomNum + "';";
			try {
				pstmt = con.prepareStatement(strSql3);
				pstmt.executeUpdate();
				JOptionPane.showMessageDialog(this, "빈방처리가 완료되었습니다!", "판매완료", JOptionPane.INFORMATION_MESSAGE);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}
}

class WindowActionListener implements ActionListener { // 윈도우생성리스너
	int actualPrice;
	String t_id;

	WindowActionListener(int actualPrice, String s_id) {
		this.actualPrice = actualPrice;
		t_id = s_id;
	}

	public void actionPerformed(ActionEvent ae) {
		String s = ae.getActionCommand();
		if ("포인트 사용/적립".equals(s)) {
			PointWindow pointWin = new PointWindow(actualPrice, t_id);
			pointWin.setTitle("포인트 사용/적립 창");
			pointWin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			pointWin.setSize(500, 480);
			pointWin.setLocation(750, 470);
			pointWin.setVisible(true);
		} else if ("결제하기".equals(s)) {
			PaymentWindow paymentWin = new PaymentWindow(actualPrice);
			paymentWin.setTitle("결제 창");
			paymentWin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			paymentWin.setSize(500, 480);
			paymentWin.setLocation(750, 470);
			paymentWin.setVisible(true);
		} else if ("Send Message >>".equals(s)) {
			MessageWinow mw = new MessageWinow(t_id);
			mw.setTitle("메세지 창");
			mw.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			mw.setSize(450, 250);
			mw.setLocation(800, 500);
			mw.setVisible(true);
		} else if ("직접입력".equals(s)) {
			TextAreaWindow tw = new TextAreaWindow();
			tw.setTitle("메세지 입력 창");
			tw.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			tw.setSize(300, 200);
			tw.setLocation(870, 500);
			tw.setVisible(true);
		}

	}
}

class MessageWinow extends JFrame implements ActionListener { // 메세지창
	String[] msg = { "1) 어서오십시오~", "2) 서비스를 추가하였습니다!", "3) 시간이 다 되었습니다~", "4) 다음 사람을 위해 빠른 선곡 부탁드립니다~!",
			"5) 즐거운 시간되셨나요?^^*", "6) 혹시 놓고 가는 소지품은 없으신가요??", "7) 아름다운 사람은 머문자리도 아름답습니다^^", "8) 안녕히 가십시오~^^" };
	JList msgList;
	JButton send;
	JButton allSend;
	JButton directInput;

	public MessageWinow(String s_id) {
		Container ct = getContentPane();
		ct.setLayout(new FlowLayout());
		msgList = new JList(msg);
		JScrollPane sp = new JScrollPane(msgList);
		sp.setPreferredSize(new Dimension(400, 120));
		send = new JButton("보내기");
		send.addActionListener(this);
		allSend = new JButton("전체발송");
		allSend.addActionListener(this);
		directInput = new JButton("직접입력");
		directInput.addActionListener(new WindowActionListener(0, s_id));

		ct.add(sp);
		ct.add(send);
		ct.add(allSend);
		ct.add(directInput);
	}

	public void actionPerformed(ActionEvent e) {
		String ac = e.getActionCommand();
		int[] indices = msgList.getSelectedIndices();
		if ("보내기".equals(ac)) {
			if (indices.length <= 0)
				JOptionPane.showMessageDialog(this, "선택된 값이 없습니다!", "메세지발송", JOptionPane.WARNING_MESSAGE);
			else
				JOptionPane.showMessageDialog(this, "해당 코인룸에 메세지를 보냈습니다!", "메세지발송", JOptionPane.INFORMATION_MESSAGE);
		} else if ("전체발송".equals(ac)) {
			if (indices.length <= 0)
				JOptionPane.showMessageDialog(this, "선택된 값이 없습니다!", "메세지전체발송", JOptionPane.WARNING_MESSAGE);
			else
				JOptionPane.showMessageDialog(this, "전체 코인룸에 메세지를 보냈습니다!", "메세지전체발송", JOptionPane.INFORMATION_MESSAGE);
		}
	}
}

class TextAreaWindow extends JFrame implements ActionListener { // 직접입력메세지창
	private JTextArea msg;
	private JButton send;
	private JButton allSend;
	private JButton cancel;

	public TextAreaWindow() {
		Container ct = getContentPane();
		ct.setLayout(new FlowLayout());
		msg = new JTextArea("어서오십시오~", 7, 20);
		JScrollPane sp = new JScrollPane(msg);
		sp.setPreferredSize(new Dimension(230, 80));
		send = new JButton("보내기");
		send.addActionListener(this);
		allSend = new JButton("전체발송");
		allSend.addActionListener(this);
		cancel = new JButton("취소");
		cancel.addActionListener(this);

		ct.add(sp);
		ct.add(send);
		ct.add(allSend);
		ct.add(cancel);
	}

	public void actionPerformed(ActionEvent e) {
		String ac = e.getActionCommand();
		if ("보내기".equals(ac)) {
			if ("".equals(msg.getText())) {
				JOptionPane.showMessageDialog(this, "입력된메세지가 없습니다!", "메세지발송", JOptionPane.WARNING_MESSAGE);
			} else
				JOptionPane.showMessageDialog(this, "해당 코인룸에 메세지를 보냈습니다!", "메세지발송", JOptionPane.INFORMATION_MESSAGE);
		} else if ("전체발송".equals(ac)) {
			if ("".equals(msg.getText())) {
				JOptionPane.showMessageDialog(this, "입력된메세지가 없습니다!", "메세지전체발송", JOptionPane.WARNING_MESSAGE);
			} else
				JOptionPane.showMessageDialog(this, "전체 코인룸에 메세지를 보냈습니다!", "메세지전체발송", JOptionPane.INFORMATION_MESSAGE);
		} else if ("취소".equals(ac)) {
			msg.setText("");
		}
	}
}

class Beverage extends JPanel implements ActionListener { // 음료탭
	private JPanel leftPanel;
	private JPanel leftTop;
	private JPanel leftCenter;
	private JPanel rightPanel;
	private JPanel rightCenter;
	private JPanel rightBottom;
	private JPanel buttonPanel;

	JLabel priceLb;
	JLabel showPriceLb;
	JButton payB;
	JButton paymentFinB;

	JButton deleteB;
	JButton cancelB;

	int columnPrice; // 음료판매가격
	int actualPrice; // 음료결제금액
	int num; // 선택한음료수량
	int num2; // 선택한음료수량
	int inventoryNumMax; // 음료재고수

	Vector<String> columnName1;
	Vector<String> columnName2;
	Vector<Vector<String>> rowData1;
	Vector<Vector<String>> rowData2;

	Vector<String> v2;
	JTable table1 = null;
	JTable table2 = null;
	MyDefaultTableModel model1 = null;
	MyDefaultTableModel model2 = null;
	JScrollPane tableSP1;
	JScrollPane tableSP2;
	int row1; // 테이블의 선택된 행의 위치를 저장할 변수
	int row2;
	int column1;
	int column2;

	public Beverage() {
		leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout(0, 45));
		leftTop = new JPanel();
		leftCenter = new JPanel();
		rightPanel = new JPanel();

		JLabel beverageTitle = new JLabel("음료");
		beverageTitle.setFont(Service.font3);

		leftTop.add(beverageTitle);
		leftTop.setBackground(Service.color3);
		leftTop.setPreferredSize(new Dimension(370, 40));

		// 첫번째테이블
		columnName1 = new Vector<String>();
		columnName1.add("음료이름");
		columnName1.add("가격");
		columnName1.add("재고수");

		rowData1 = new Vector<Vector<String>>();
		// 디비연동해서 재고 가져오기
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/coinroom?serverTimezone=UTC", "root",
					"java2020");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		PreparedStatement pstmt = null;
		ResultSet rs;
		String strSql = "select * from inventory";
		try {
			pstmt = con.prepareStatement(strSql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Vector<String> v1 = new Vector<String>();
				v1.add(rs.getString("product_name"));
				v1.add(rs.getString("sale_price"));
				v1.add(rs.getString("inventory_number"));
				rowData1.add(v1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		model1 = new MyDefaultTableModel(rowData1, columnName1);
		table1 = new JTable(model1);
		table1.setBackground(Service.color2);
		table1.setFont(Service.font1);
		table1.setRowHeight(40);
		table1.getColumnModel().getColumn(0).setPreferredWidth(170);
		table1.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				row1 = table1.getSelectedRow();
				boolean isZero = false;
				boolean isAlreadyAdded = false;

				if (rowData1.get(row1).get(2).equals("0")) {
					isZero = true;
				}
				if (!isZero) {
					for (Vector<String> v : rowData2) {
						String beverage = v.get(0); // 음료이름
						if (rowData1.get(row1).get(0).equals(beverage)) {
							isAlreadyAdded = true;
						}
					}
					if (!isAlreadyAdded) {
						Vector<String> v = new Vector<String>();
						v.add(rowData1.get(row1).get(0));
						v.add("0");
						v.add("     +");
						v.add("     -");
						v.add(rowData1.get(row1).get(2));
						model2.addRow(v);
					}
				}
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}
		});
		tableSP1 = new JScrollPane(table1);
		tableSP1.setPreferredSize(new Dimension(370, 400));
		leftCenter.add(tableSP1);

		leftPanel.add(leftTop, BorderLayout.NORTH);
		leftPanel.add(leftCenter, BorderLayout.CENTER);
		leftPanel.setBorder(new EmptyBorder(25, 25, 25, 25));

		rightCenter = new JPanel();
		priceLb = new JLabel("결제금액");
		showPriceLb = new JLabel("0");
		JLabel wonLb1 = new JLabel("원");
		payB = new JButton("결제하기");
		paymentFinB = new JButton("결제완료");
		paymentFinB.addActionListener(this);

		rightPanel.setLayout(new BorderLayout(0, 25));
		rightCenter.setLayout(null);
		rightCenter.setPreferredSize(new Dimension(400, 220));
		rightCenter.setBorder(new LineBorder(Color.BLACK));
		rightCenter.setBackground(Service.color2);

		priceLb.setBounds(60, 50, 100, 50);
		showPriceLb.setBounds(165, 50, 180, 50);
		wonLb1.setBounds(360, 50, 50, 50);
		payB.setBounds(60, 130, 130, 40);
		paymentFinB.setBounds(230, 130, 130, 40);

		priceLb.setFont(Service.font1);
		showPriceLb.setFont(Service.font1);
		showPriceLb.setHorizontalAlignment(JLabel.RIGHT);
		showPriceLb.setBorder(new LineBorder(Color.BLACK));
		wonLb1.setFont(Service.font1);
		payB.setFont(Service.font1);
		payB.addActionListener(this);
		paymentFinB.setFont(Service.font1);

		rightCenter.add(priceLb);
		rightCenter.add(showPriceLb);
		rightCenter.add(wonLb1);
		rightCenter.add(payB);
		rightCenter.add(paymentFinB);

		rightBottom = new JPanel();
		// 두번째테이블
		columnName2 = new Vector<String>();
		columnName2.add("음료이름");
		columnName2.add("수량");
		columnName2.add("더하기");
		columnName2.add("빼기");

		rowData2 = new Vector<Vector<String>>();
		v2 = new Vector<String>();
		model2 = new MyDefaultTableModel(rowData2, columnName2);
		table2 = new JTable(model2);
		table2.setBackground(Service.color2);
		table2.setFont(Service.font1);
		table2.setRowHeight(40);
		table2.getColumnModel().getColumn(0).setPreferredWidth(210);
		table2.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent me) {
				row2 = table2.getSelectedRow();
				column2 = table2.getSelectedColumn();
				num = Integer.parseInt(table2.getValueAt(row2, 1).toString());

				for (Vector<String> v : rowData1) {
					String beverage = v.get(0); // 음료이름
					int inventoryNum = Integer.parseInt(v.get(2)); // 재고수
					if (rowData2.get(row2).get(0).equals(beverage)) {
						inventoryNumMax = inventoryNum; // 남아있는 재고수를 inventoryNumMax에 넣는다
						break;
					}
				}

				if (rowData2.get(row2).get(0).equals("생수") || rowData2.get(row2).get(0).equals("콜라")
						|| rowData2.get(row2).get(0).equals("사이다") || rowData2.get(row2).get(0).equals("환타포도")
						|| rowData2.get(row2).get(0).equals("환타오렌지")) {
					columnPrice = 1500;
				} else
					columnPrice = 2000;

				if (column2 == 2) { // +클릭
					// Integer.parseInt(rowData2.get(row2).get(4))
					if (num < inventoryNumMax) {
						table2.setValueAt(Integer.toString(num + 1), row2, 1);
						actualPrice += columnPrice;
					}
					num2 = num + 1; // 삭제 시에 필요한 수량 넘겨주기
				} else if (column2 == 3) { // -클릭
					if (num > 0) {
						table2.setValueAt(Integer.toString(num - 1), row2, 1);
						actualPrice -= columnPrice;
						num2 = num - 1; // 삭제 시에 필요한 수량 넘겨주기
					}
				} else
					num2 = num; // 삭제 시에 필요한 수량 넘겨주기(+,-누른 행이 아닌 다른행 택했을때는 원래 수량을 넘겨준다)
				showPriceLb.setText(Integer.toString(actualPrice));
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}
		});
		tableSP2 = new JScrollPane(table2);
		tableSP2.setPreferredSize(new Dimension(400, 220));
		MyDefaultTableCellRenderer1 renderer2 = new MyDefaultTableCellRenderer1();
		table2.getColumn("더하기").setCellRenderer(renderer2);
		table2.getColumn("빼기").setCellRenderer(renderer2);
		rightBottom.add(tableSP2);

		buttonPanel = new JPanel();
		deleteB = new JButton("삭제");
		deleteB.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		deleteB.addActionListener(this);
		cancelB = new JButton("취소");
		cancelB.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		cancelB.addActionListener(this);
		buttonPanel.add(deleteB);
		buttonPanel.add(cancelB);

		rightPanel.add(rightCenter, BorderLayout.NORTH);
		rightPanel.add(rightBottom, BorderLayout.CENTER);
		rightPanel.add(buttonPanel, BorderLayout.SOUTH);
		rightPanel.setBorder(new EmptyBorder(23, 23, 23, 23));

		add(leftPanel, BorderLayout.WEST);
		add(rightPanel, BorderLayout.EAST);
	}

	public void actionPerformed(ActionEvent ae) {
		String ac = ae.getActionCommand();
		if ("결제하기".equals(ac)) { // 음료결제
			PaymentWindow paymentWin = new PaymentWindow(actualPrice);
			paymentWin.setTitle("결제 창");
			paymentWin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			paymentWin.setSize(500, 480);
			paymentWin.setLocation(750, 470);
			paymentWin.setVisible(true);
		}

		// 삭제버튼클릭
		if ("삭제".equals(ac)) {
			actualPrice -= columnPrice * num2;
			showPriceLb.setText(Integer.toString(actualPrice));
			model2.removeRow(row2);
		} else if ("취소".equals(ac)) { // 취소버튼클릭
			table2.setValueAt(0, row2, 1);
			if (num2 > 0) {
				actualPrice -= columnPrice * num2;
				num2 = 0;
			}
			showPriceLb.setText(Integer.toString(actualPrice));
		}
		
		// 디비연결
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/coinroom?serverTimezone=UTC", "root",
					"java2020");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		PreparedStatement pstmt = null;
		ResultSet rs;
		
		if ("결제완료".equals(ac)) {
			// 결제됐는지 체크 >> 디비 inventory테이블에서 inventory_number값 수정
			if (Service.isPayCompleted) { // 결제되면
				// 디비연결
				Connection con1 = null;
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/coinroom?serverTimezone=UTC", "root",
							"java2020");
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}

				PreparedStatement pstmt1 = null;
				ResultSet rs1;
				
				// db테이블 product_sale에 내역 추가
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				String saleTime = format.format(new Date());
				String productCode = "";
				for (Vector<String> v : rowData2) {
					String beverage = v.get(0); // 음료
					int inventory1 = Integer.parseInt(v.get(1)); // 재고수
					if(inventory1>0) {
						String strSql1 = "select product_code from product_sale where product_code=(select product_code from inventory where product_name = '"
								+ beverage + "')";
						try {
							pstmt1 = con1.prepareStatement(strSql1);
							rs1=pstmt1.executeQuery();
							while(rs1.next()) {
								productCode=rs1.getString("product_code");
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
						String strSql2 = "insert into product_sale(product_code, sale_date, sale_count, pay_method) values('"
								+ productCode + "', '" + saleTime + "', '" + inventory1 + "', '" + Service.payMethod+ "')";
						try {
							pstmt1 = con1.prepareStatement(strSql2);
							pstmt1.executeUpdate();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
				
				// rowData2를 돌면서 음료이름의 수량만큼 재고수에서 빼주기
				for (Vector<String> v : rowData2) {
					String beverage = v.get(0); // 음료
					int inventory2 = Integer.parseInt(v.get(1)); // 재고수
					if (inventory2 > 0) {
						String strSql2 = "update inventory set inventory_number=inventory_number-" + inventory2
								+ " where product_code = (select product_code from (select product_code inventory where product_name = '"
								+ beverage + "') temp)";
						try {
							pstmt1 = con1.prepareStatement(strSql2);
							pstmt1.executeUpdate();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
				JOptionPane.showMessageDialog(this, "결제가 완료되었습니다!", "결제완료",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(this, "결제가 되지 않았습니다. 결제 후 결제완료를 눌러주세요!", "결제완료",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			// 음료창에서 테이블다시 가져와야함
			Vector<Vector<String>> result = new Vector<Vector<String>>();
			rowData1.removeAllElements();
			rowData2.removeAllElements();
			showPriceLb.setText("0");
			actualPrice = 0;
			String strSql2 = "select * from inventory";
			try {
				pstmt = con.prepareStatement(strSql2);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					Vector<String> v = new Vector<String>();
					v.add(rs.getString("product_name"));
					v.add(rs.getString("sale_price"));
					v.add(rs.getString("inventory_number"));
					result.add(v);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			rowData1.addAll(result);
			table1.updateUI();
			table2.updateUI();

			Service.isPayCompleted = false;
		}
	}

}

class SalesWindow extends JFrame { // 판매창
	public SalesWindow(int roomNum, String s_id) {
		JTabbedPane jtp = new JTabbedPane();
		// 판매창(서비스)
		Service s = new Service(roomNum, s_id);
		// 판매창(음료)
		Beverage b = new Beverage();
		jtp.addTab("서비스", s);
		jtp.addTab("음료", b);

		Container ct = getContentPane();
		ct.add(jtp);
	}
}

class Point_use extends JPanel implements ActionListener { // 포인트사용탭
	JPanel top; // 결제패널
	JPanel bottom;

	JLabel phoneNumLb;
	JTextField phoneNumTf;
	JButton inquiryB;
	JLabel currentPointLb;
	JLabel showPointLb;
	JLabel usePointLb;
	JTextField writePointTf;

	JButton fullUse;
	JButton cancel;
	JButton ok;

	int actualPrice;
	public static int resultPrice; // 포인트사용후결제금액
	int resultPoint;
	public static int usedPoint = 0;
	String t_id;
	public static String phoneNumTf_str = null;

	public Point_use(int actualPrice, String s_id) {
		t_id = s_id;
		this.actualPrice = actualPrice;
		top = new JPanel();
		bottom = new JPanel();
		top.setLayout(null);
		top.setBorder(new LineBorder(Color.BLACK));
		top.setPreferredSize(new Dimension(370, 300));
		top.setBackground(Service.color2);

		phoneNumLb = new JLabel("전화번호");
		phoneNumTf = new JTextField();
		inquiryB = new JButton("조회");
		currentPointLb = new JLabel("현재 포인트");
		showPointLb = new JLabel();
		JLabel pLb1 = new JLabel("P");
		usePointLb = new JLabel("사용할 포인트");
		writePointTf = new JTextField();
		JLabel pLb2 = new JLabel("P");

		phoneNumLb.setBounds(25, 50, 100, 40);
		phoneNumTf.setBounds(120, 50, 150, 40);
		inquiryB.setBounds(290, 50, 60, 40);

		currentPointLb.setBounds(25, 120, 120, 40);
		showPointLb.setBounds(150, 120, 150, 40);
		pLb1.setBounds(310, 120, 50, 40);

		usePointLb.setBounds(25, 190, 120, 40);
		writePointTf.setBounds(150, 190, 150, 40);
		pLb2.setBounds(310, 190, 50, 40);

		phoneNumLb.setFont(Service.font1);
		phoneNumTf.setFont(Service.font1);
		currentPointLb.setFont(Service.font1);
		showPointLb.setFont(Service.font1);
		usePointLb.setFont(Service.font1);
		writePointTf.setFont(Service.font1);

		inquiryB.addActionListener(this);

		phoneNumTf.setHorizontalAlignment(JTextField.RIGHT);
		showPointLb.setHorizontalAlignment(JLabel.RIGHT);
		writePointTf.setHorizontalAlignment(JTextField.RIGHT);
		showPointLb.setBorder(new LineBorder(Color.BLACK));

		top.add(phoneNumLb);
		top.add(phoneNumTf);
		top.add(inquiryB);
		top.add(currentPointLb);
		top.add(showPointLb);
		top.add(pLb1);
		top.add(usePointLb);
		top.add(writePointTf);
		top.add(pLb2);

		fullUse = new JButton("전액사용");
		cancel = new JButton("취소");
		ok = new JButton("확인");
		fullUse.addActionListener(this);
		cancel.addActionListener(this);
		ok.addActionListener(this);

		bottom.add(fullUse);
		bottom.add(cancel);
		bottom.add(ok);
		bottom.setBorder(new EmptyBorder(25, 25, 25, 25));

		add(top, BorderLayout.CENTER);
		add(bottom, BorderLayout.SOUTH);
	}

	public void actionPerformed(ActionEvent ae) {
		String s = ae.getActionCommand();
		phoneNumTf_str = phoneNumTf.getText();

		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/coinroom?serverTimezone=UTC", "root",
					"java2020");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		PreparedStatement pstmt = null;
		ResultSet rs;
		if ("조회".equals(s)) {

			String strSql = "select point from " + t_id + "_customer_info where tel_number='" + phoneNumTf_str + "';";
			try {
				pstmt = con.prepareStatement(strSql);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					showPointLb.setText(rs.getString("point"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if ("취소".equals(s)) {
			phoneNumTf.setText("");
			showPointLb.setText("");
			writePointTf.setText("");
		} else if ("전액사용".equals(s)) {
			if (actualPrice < Integer.parseInt(showPointLb.getText())) {
				JOptionPane.showMessageDialog(this, "결제금액이 포인트적립금보다 적어서 쓸 수 없습니다. 포인트를 사용하시려면 포인트를 직접 입력해주세요!",
						"포인트사용메세지", JOptionPane.WARNING_MESSAGE);
			} else {
				writePointTf.setText(showPointLb.getText());
			}

		} else { // 확인
			if (phoneNumTf.getText().equals("") || showPointLb.getText().equals("")
					|| writePointTf.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "입력되지 않은 정보가 있습니다. 다시 확인해주세요!", "포인트사용메세지",
						JOptionPane.WARNING_MESSAGE);
			} else {
				int writePointTf_int = Integer.parseInt(writePointTf.getText());
				if (writePointTf_int > Integer.parseInt(showPointLb.getText()) || actualPrice < writePointTf_int) {
					JOptionPane.showMessageDialog(this, "포인트입력이 잘못되었습니다. 다시 확인해주세요!", "포인트사용메세지",
							JOptionPane.WARNING_MESSAGE);
				} else {
					usedPoint = writePointTf_int;
					resultPrice = actualPrice - writePointTf_int;
					resultPoint = Integer.parseInt(showPointLb.getText()) - Integer.parseInt(writePointTf.getText());

					// 현재포인트- 사용할포인트 => 디비의 포인트값 변경
					String strSql3 = "update " + t_id + "_customer_info set point='" + resultPoint
							+ "' where tel_number='" + phoneNumTf_str + "';";
					try {
						pstmt = con.prepareStatement(strSql3);
						pstmt.executeUpdate();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					JOptionPane.showMessageDialog(this, "포인트사용이 완료되었습니다!", "포인트사용메세지", JOptionPane.INFORMATION_MESSAGE);
					Service.showPriceLb.setText(Integer.toString(resultPrice)); // 서비스창의 결제금액을 포인트사용적용 후 금액으로 set
				}
			}
		}
	}
}

class Point_earn extends JPanel implements ActionListener { // 포인트적립탭
	JPanel top; // 결제패널
	JPanel bottom;

	JLabel phoneNumLb;
	JTextField phoneNumTf;
	JButton inquiryB;
	JLabel currentPointLb;
	JLabel showPointLb;
	JLabel earnPointLb;
	JLabel showPointLb2;
	JLabel applyPointLb;
	JLabel showPointLb3;

	JButton fullUse;
	JButton cancel;
	JButton ok;

	int actualPrice;
	String t_id;
	public static String phoneNumTf_str = null;

	public Point_earn(int actualPrice, String s_id) {
		this.actualPrice = actualPrice;
		t_id = s_id;
		top = new JPanel();
		bottom = new JPanel();
		top.setLayout(null);
		top.setBorder(new LineBorder(Color.BLACK));
		top.setPreferredSize(new Dimension(370, 300));
		top.setBackground(Service.color2);

		phoneNumLb = new JLabel("전화번호");
		phoneNumTf = new JTextField();
		inquiryB = new JButton("조회");
		currentPointLb = new JLabel("현재 포인트");
		showPointLb = new JLabel();
		JLabel pLb1 = new JLabel("P");
		earnPointLb = new JLabel("적립 포인트");
		showPointLb2 = new JLabel();
		JLabel pLb2 = new JLabel("P");
		applyPointLb = new JLabel("적용 후 포인트");
		showPointLb3 = new JLabel();
		JLabel pLb3 = new JLabel("P");

		phoneNumLb.setBounds(25, 30, 100, 40);
		phoneNumTf.setBounds(120, 30, 150, 40);
		inquiryB.setBounds(290, 30, 60, 40);

		currentPointLb.setBounds(25, 100, 120, 40);
		showPointLb.setBounds(150, 100, 150, 40);
		pLb1.setBounds(310, 100, 50, 40);

		earnPointLb.setBounds(25, 170, 120, 40);
		showPointLb2.setBounds(150, 170, 150, 40);
		pLb2.setBounds(310, 170, 50, 40);

		applyPointLb.setBounds(25, 240, 120, 40);
		showPointLb3.setBounds(150, 240, 150, 40);
		pLb3.setBounds(310, 240, 50, 40);

		phoneNumLb.setFont(Service.font1);
		phoneNumTf.setFont(Service.font1);
		currentPointLb.setFont(Service.font1);
		showPointLb.setFont(Service.font1);
		earnPointLb.setFont(Service.font1);
		showPointLb2.setFont(Service.font1);
		applyPointLb.setFont(Service.font1);
		showPointLb3.setFont(Service.font1);

		phoneNumTf.setHorizontalAlignment(JTextField.RIGHT);
		showPointLb.setHorizontalAlignment(JLabel.RIGHT);
		showPointLb2.setHorizontalAlignment(JTextField.RIGHT);
		showPointLb3.setHorizontalAlignment(JTextField.RIGHT);

		showPointLb.setBorder(new LineBorder(Color.BLACK));
		showPointLb2.setBorder(new LineBorder(Color.BLACK));
		showPointLb3.setBorder(new LineBorder(Color.BLACK));

		top.add(phoneNumLb);
		top.add(phoneNumTf);
		top.add(inquiryB);
		top.add(currentPointLb);
		top.add(showPointLb);
		top.add(pLb1);
		top.add(earnPointLb);
		top.add(showPointLb2);
		top.add(pLb2);
		top.add(applyPointLb);
		top.add(showPointLb3);
		top.add(pLb3);

		JButton cancel = new JButton("취소");
		JButton ok = new JButton("확인");
		inquiryB.addActionListener(this);
		cancel.addActionListener(this);
		ok.addActionListener(this);

		bottom.add(cancel);
		bottom.add(ok);
		bottom.setBorder(new EmptyBorder(25, 25, 25, 25));

		add(top, BorderLayout.CENTER);
		add(bottom, BorderLayout.SOUTH);

	}

	public void actionPerformed(ActionEvent ae) {
		String s = ae.getActionCommand();
		phoneNumTf_str = phoneNumTf.getText();
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/coinroom?serverTimezone=UTC", "root",
					"java2020");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		PreparedStatement pstmt = null;
		ResultSet rs;
		if ("조회".equals(s)) {

			String strSql = "select point from " + t_id + "_customer_info where tel_number='" + phoneNumTf_str + "';";
			try {
				pstmt = con.prepareStatement(strSql);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					showPointLb.setText(rs.getString("point"));
					int showPointLb_int = Integer.parseInt(showPointLb.getText());
					// 적립금->결제금액의 10프로 적립
					int earnPoint = (int) (actualPrice * 0.1); // 적립포인트
					int applyPoint = showPointLb_int + earnPoint; // 적용 후 포인트
					showPointLb2.setText(Integer.toString(earnPoint));
					showPointLb3.setText(Integer.toString(applyPoint));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if ("취소".equals(s)) {
			phoneNumTf.setText("");
			showPointLb.setText("");
			showPointLb2.setText("");
			showPointLb3.setText("");
		} else { // 확인
			if (phoneNumTf.getText().equals("") || showPointLb.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "입력되지 않은 정보가 있습니다. 다시 확인해주세요!", "포인트적립메세지",
						JOptionPane.WARNING_MESSAGE);
			} else {
				int showPointLb_int = Integer.parseInt(showPointLb.getText());
				// 적립금->결제금액의 10프로 적립
				int earnPoint = (int) (actualPrice * 0.1); // 적립포인트
				int applyPoint = showPointLb_int + earnPoint; // 적용 후 포인트
				showPointLb2.setText(Integer.toString(earnPoint));
				showPointLb3.setText(Integer.toString(applyPoint));

				// 디비에 포인트 값 변경
				String strSql2 = "update " + t_id + "_customer_info set point='" + applyPoint + "' where tel_number='"
						+ phoneNumTf_str + "';";
				try {
					pstmt = con.prepareStatement(strSql2);
					pstmt.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				JOptionPane.showMessageDialog(this, "포인트적립이 완료되었습니다!", "포인트적립메세지", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
}

class PointWindow extends JFrame { // 포인트창
	public PointWindow(int actualPrice, String s_id) {
		JTabbedPane jtp = new JTabbedPane();
		// 포인트창(사용)
		Point_use pu = new Point_use(actualPrice, s_id);
		// 포인트창(적립)
		Point_earn pe = new Point_earn(actualPrice, s_id);
		jtp.addTab("포인트 사용", pu);
		jtp.addTab("포인트 적립", pe);

		Container ct = getContentPane();
		ct.add(jtp);
	}
}

class Cash extends JPanel implements ActionListener { // 현금결제탭
	JPanel top; // 결제패널
	JPanel bottom;

	JLabel priceLb;
	JLabel showPriceLb1;
	JLabel receivedPriceLb;
	JTextField receivedPriceTf;
	JLabel changeLb;
	JLabel showPriceLb2;

	int actualPrice;

	JButton cancel;
	JButton ok;

	public Cash(int actualPrice) {

		top = new JPanel();
		bottom = new JPanel();
		top.setLayout(null);
		top.setBorder(new LineBorder(Color.BLACK));
		top.setPreferredSize(new Dimension(370, 300));
		top.setBackground(Service.color2);

		priceLb = new JLabel("결제금액");
		showPriceLb1 = new JLabel();

		// 포인트사용후 showPriceLb1에 보여줄 금액 구하기
		int tmp = Point_use.usedPoint;
		if (tmp > 0) {
			actualPrice = actualPrice - tmp;
		}

		showPriceLb1.setText(Integer.toString(actualPrice));
		this.actualPrice = actualPrice;

		JLabel wonLb1 = new JLabel("원");
		receivedPriceLb = new JLabel("받은금액");
		receivedPriceTf = new JTextField(10);
		JLabel wonLb2 = new JLabel("원");
		changeLb = new JLabel("거스름돈");
		showPriceLb2 = new JLabel();
		JLabel wonLb3 = new JLabel("원");

		priceLb.setBounds(25, 60, 150, 40);
		showPriceLb1.setBounds(130, 60, 170, 40);
		wonLb1.setBounds(320, 60, 50, 40);
		receivedPriceLb.setBounds(25, 120, 150, 40);
		receivedPriceTf.setBounds(130, 120, 170, 40);
		wonLb2.setBounds(320, 120, 50, 40);
		changeLb.setBounds(25, 180, 150, 40);
		showPriceLb2.setBounds(130, 180, 170, 40);
		wonLb3.setBounds(320, 180, 50, 40);

		priceLb.setFont(Service.font1);
		showPriceLb1.setFont(Service.font1);
		showPriceLb1.setHorizontalAlignment(JLabel.RIGHT);
		showPriceLb1.setBorder(new LineBorder(Color.BLACK));
		receivedPriceLb.setFont(Service.font1);
		receivedPriceTf.setFont(Service.font1);
		receivedPriceTf.setHorizontalAlignment(JLabel.RIGHT);
		receivedPriceTf.addActionListener(this);
		changeLb.setFont(Service.font1);
		showPriceLb2.setFont(Service.font1);
		showPriceLb2.setHorizontalAlignment(JLabel.RIGHT);
		showPriceLb2.setBorder(new LineBorder(Color.BLACK));

		top.add(priceLb);
		top.add(showPriceLb1);
		top.add(wonLb1);
		top.add(receivedPriceLb);
		top.add(receivedPriceTf);
		top.add(wonLb2);
		top.add(changeLb);
		top.add(showPriceLb2);
		top.add(wonLb3);

		cancel = new JButton("취소");
		cancel.addActionListener(this);
		ok = new JButton("확인");
		ok.addActionListener(this);

		bottom.add(cancel);
		bottom.add(ok);
		bottom.setBorder(new EmptyBorder(25, 25, 25, 25));

		add(top, BorderLayout.CENTER);
		add(bottom, BorderLayout.SOUTH);

	}

	public void actionPerformed(ActionEvent e) {
		String ac = e.getActionCommand();
		if ("취소".equals(ac)) {
			receivedPriceTf.setText("");
			showPriceLb2.setText("");
		} else if ("확인".equals(ac)) { // 현금확인
			if ("".equals(receivedPriceTf.getText()) || "".equals(showPriceLb2.getText()))
				JOptionPane.showMessageDialog(this, "입력되지 않은 정보가 있습니다. 다시 확인부탁드립니다!", "결제미완료",
						JOptionPane.INFORMATION_MESSAGE);
			else {
				JOptionPane.showMessageDialog(this, "결제가 완료되었습니다. 서비스창에서 결제완료를 눌러주세요!", "결제완료",
						JOptionPane.INFORMATION_MESSAGE);
				Service.isPayCompleted = true;
				Service.payMethod = "현금";
			}
		} else { // 받은금액 입력하고 엔터눌렀을때 거스름돈 보이기
			int showPriceLb2_int = Integer.parseInt(receivedPriceTf.getText()) - actualPrice;
			if (showPriceLb2_int < 0) {
				JOptionPane.showMessageDialog(this, "받은금액이 부족합니다. 금액을 다시 확인해주세요!", "경고! 금액부족",
						JOptionPane.WARNING_MESSAGE);
			} else
				showPriceLb2.setText(Integer.toString(showPriceLb2_int));
		}
	}
}

class Card extends JPanel implements ActionListener { // 카드결제탭
	JPanel top; // 결제패널
	JPanel bottom;

	JLabel priceLb;
	JLabel showPriceLb;
	JLabel cardNumLb;
	JTextField cardNumTf1;
	JTextField cardNumTf2;
	JTextField cardNumTf3;
	JTextField cardNumTf4;
	JLabel ValidityLb;
	JTextField ValidityTf;

	JButton cancel;
	JButton ok;

	int actualPrice;

	public Card(int actualPrice) {
		top = new JPanel();
		bottom = new JPanel();
		top.setLayout(null);
		top.setBorder(new LineBorder(Color.BLACK));
		top.setPreferredSize(new Dimension(370, 300));
		top.setBackground(Service.color2);

		priceLb = new JLabel("결제금액");
		showPriceLb = new JLabel(Integer.toString(actualPrice));

		// 포인트사용후 showPriceLb에 보여줄 금액 구하기
		int tmp = Point_use.usedPoint;
		if (tmp > 0) {
			actualPrice = actualPrice - tmp;
		}

		showPriceLb.setText(Integer.toString(actualPrice));
		this.actualPrice = actualPrice;

		JLabel wonLb1 = new JLabel("원");
		cardNumLb = new JLabel("카드번호");
		cardNumTf1 = new JTextField(4);
		cardNumTf2 = new JTextField(4);
		cardNumTf3 = new JTextField(4);
		cardNumTf4 = new JTextField(4);
		ValidityLb = new JLabel("유효기간");
		ValidityTf = new JTextField(4);

		priceLb.setBounds(25, 60, 150, 40);
		showPriceLb.setBounds(130, 60, 170, 40);
		wonLb1.setBounds(320, 60, 50, 40);
		cardNumLb.setBounds(25, 120, 150, 40);
		cardNumTf1.setBounds(130, 120, 50, 40);
		cardNumTf2.setBounds(190, 120, 50, 40);
		cardNumTf3.setBounds(250, 120, 50, 40);
		cardNumTf4.setBounds(310, 120, 50, 40);
		ValidityLb.setBounds(25, 180, 150, 40);
		ValidityTf.setBounds(130, 180, 120, 40);

		priceLb.setFont(Service.font1);
		showPriceLb.setFont(Service.font1);
		showPriceLb.setHorizontalAlignment(JLabel.RIGHT);
		showPriceLb.setBorder(new LineBorder(Color.BLACK));
		cardNumLb.setFont(Service.font1);
		cardNumTf1.setFont(Service.font1);
		cardNumTf2.setFont(Service.font1);
		cardNumTf3.setFont(Service.font1);
		cardNumTf4.setFont(Service.font1);
		ValidityLb.setFont(Service.font1);
		ValidityTf.setFont(Service.font1);

		top.add(priceLb);
		top.add(showPriceLb);
		top.add(wonLb1);
		top.add(cardNumLb);
		top.add(cardNumTf1);
		top.add(cardNumTf2);
		top.add(cardNumTf3);
		top.add(cardNumTf4);
		top.add(ValidityLb);
		top.add(ValidityTf);

		cancel = new JButton("취소");
		cancel.addActionListener(this);
		ok = new JButton("확인");
		ok.addActionListener(this);

		bottom.add(cancel);
		bottom.add(ok);
		bottom.setBorder(new EmptyBorder(25, 25, 25, 25));

		add(top, BorderLayout.CENTER);
		add(bottom, BorderLayout.SOUTH);

	}

	public void actionPerformed(ActionEvent e) {
		String ac = e.getActionCommand();
		if ("취소".equals(ac)) {
			cardNumTf1.setText("");
			cardNumTf2.setText("");
			cardNumTf3.setText("");
			cardNumTf4.setText("");
			ValidityTf.setText("");
		} else {
			if ("".equals(cardNumTf1.getText()) || "".equals(cardNumTf2.getText()) || "".equals(cardNumTf3.getText())
					|| "".equals(cardNumTf4.getText()) || "".equals(ValidityTf.getText()))
				JOptionPane.showMessageDialog(this, "입력되지 않은 정보가 있습니다. 다시 확인부탁드립니다!", "결제미완료",
						JOptionPane.INFORMATION_MESSAGE);
			else {
				JOptionPane.showMessageDialog(this, "결제가 완료되었습니다. 서비스창에서 결제완료를 눌러주세요!", "결제완료",
						JOptionPane.INFORMATION_MESSAGE);
				Service.isPayCompleted = true;
				Service.payMethod = "카드";
			}
		}
	}
}

class PaymentWindow extends JFrame { // 결제창
	public PaymentWindow(int actualPrice) {
		JTabbedPane jtp = new JTabbedPane();
		// 결제창(현금)
		Cash cash = new Cash(actualPrice);
		// 결제창(카드)
		Card card = new Card(actualPrice);
		jtp.addTab("현금", cash);
		jtp.addTab("카드", card);

		Container ct = getContentPane();
		ct.add(jtp);

	}
}
