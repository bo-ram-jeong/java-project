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

class MainScreen extends JPanel implements ActionListener { // Home��
	JPanel leftPanel; // ���η�
	JPanel rightPanel; // �ð�,�޴�,�׸�����
	JPanel[] p = new JPanel[12]; // �븶�� �г�
	public static JLabel[] lb = new JLabel[12]; // �븶�� ���̺�
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
			final int idx = i; // ���õ� ���� ��ġ�� ������ ����
			p[i].addMouseListener(new MouseListener() {
				public void mouseClicked(MouseEvent e) {
					SalesWindow sw = new SalesWindow(idx + 1, t_id);
					sw.setTitle("���� â");
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
			lb[i] = new JLabel(); // �г����� �ø� ���̺�
			lb[i].setPreferredSize(new Dimension(200, 140));
			lb[i].setHorizontalAlignment(JLabel.CENTER);
			lb[i].setFont(Service.font1);
			p[i].add(lb[i]);

			StringBuilder sb = new StringBuilder();
			sb.append("<html>").append(i + 1).append(")&nbsp;&nbsp;�ִ��ο� ");
			if (i < 7) {
				sb.append(3);
			} else {
				sb.append(6);
			}
			sb.append("��<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;���</html>");
			lb[i].setText(sb.toString());
		} // for��

		leftPanel.setPreferredSize(new Dimension(700, 700));
		leftPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
		leftPanel.setBackground(new Color(255, 255, 246));

		rightPanel.setLayout(new BorderLayout(0, 20));
		JLabel timeLabel = new JLabel("���ӽð� : " + currentTime());
		timeLabel.setFont(Service.font1);
		menuB = new JButton("M E N U");
		menuB.addActionListener(this); // Ŭ�� �� (��������, ���°���, ������, ȸ������, ����, ������ ���� ) �޴�â���� ������
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
		SimpleDateFormat format = new SimpleDateFormat("yyyy�� MM�� dd�� E����  a hh:mm:ss");
		return format.format(today);
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == menuB) {
			MainMenu win = new MainMenu("���θ޴�", t_id);
			win.setSize(600, 550);
			win.setLocation(680, 280);
			win.show();
			win.setResizable(false);
			win.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			parent.dispose();
		}
	}

}

//���ϴ� �� ����� ���� TableCellRenderer
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

//���̺� �� ��� ���� ���ϵ���
class MyDefaultTableModel extends DefaultTableModel {
	public MyDefaultTableModel(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public boolean isCellEditable(int row, int column) {
		return false;
	}
}

class PaymentHistory extends JPanel implements MouseListener, ActionListener { // ����������
	JPanel top;
	JPanel center;

	Vector<String> columnName;
	public static Vector<Vector<String>> rowData;
	public static JTable table = null;
	MyDefaultTableModel model = null;

	JLabel roomNumLb;
	JComboBox roomNumCombo;
	String roomsNum[] = { "��ü", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" };
	JLabel saleDateLb;
	JTextField saleDateTf;
	JButton inquiryB;

	JScrollPane tableSP;
	int row; // ���̺��� ���õ� ���� ��ġ�� ������ ����
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

		roomNumLb = new JLabel("���ȣ");
		roomNumCombo = new JComboBox(roomsNum);
		saleDateLb = new JLabel("�Ǹų�¥");
		saleDateTf = new JTextField(10);
		inquiryB = new JButton("��ȸ");
		inquiryB.setBackground(new Color(255, 250, 164));
		inquiryB.addActionListener(this);

		top.setLayout(new FlowLayout());
		top.add(roomNumLb);
		top.add(roomNumCombo);
		top.add(saleDateLb);
		top.add(saleDateTf);
		top.add(inquiryB);

		columnName = new Vector<String>();
		columnName.add("������ȣ");
		columnName.add("���ȣ");
		columnName.add("�̿�Ÿ��");
		columnName.add("�Ǹų�¥");
		columnName.add("�����ݾ�");
		columnName.add("�������");
		columnName.add("��ȭ��ȣ");
		columnName.add("ȯ��");

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
		table.getColumn("ȯ��").setCellRenderer(renderer1);
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
					isNum = true;// �����ϰ��
					break;
				}
			}
			if (!isNum) {
				Object[] btn = { "YES", "NO" };
				int selected = JOptionPane.showOptionDialog(null, "���� ȯ�� �Ͻðڽ��ϱ�?", "ȯ��", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, btn, btn[0]);
				if (selected == JOptionPane.YES_OPTION) {
					JOptionPane.showMessageDialog(null, "ȯ���� �Ϸ�Ǿ����ϴ�!", "����", JOptionPane.INFORMATION_MESSAGE);
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
					JOptionPane.showMessageDialog(null, "ȯ���� ��ҵǾ����ϴ�!", "����", JOptionPane.INFORMATION_MESSAGE);
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

		if ("��ȸ".equals(s)) {
			roomNumCombo_str = roomNumCombo.getSelectedItem().toString();
			saleDateTf_str = saleDateTf.getText();
			String searchQuery = null;

			if (saleDateTf_str.equals("")) { // �Ǹų�¥�� �������
				if (roomNumCombo_str.equals("��ü")) {
				} else
					searchQuery = "where room_number='" + roomNumCombo_str + "'";
			} else { // �Ѵ� �ԷµǾ�����
				if (roomNumCombo_str.equals("��ü")) {
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

	public static Vector<Vector<String>> getcoinroomSaleList(String searchQuery) { // ��Ȳ������ �˻����Ǿ� ���̱�
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

class BusinessManagement extends JFrame { // ��������â
	String t_id;

	public BusinessManagement(String s_id) {

		t_id = s_id;
		JTabbedPane jtp = new JTabbedPane();
		// ���η����(����)
		MainScreen ms = new MainScreen(t_id, this);
		// ���η����(��������)
		PaymentHistory ph = new PaymentHistory();
		jtp.addTab("HOME", ms);
		jtp.addTab("��������", ph);

		Container ct = getContentPane();
		ct.setBackground(new Color(255, 227, 236));
		ct.add(jtp);
	}
}

class Service extends JPanel implements ActionListener { // ������
	JPanel leftPanel;
	JPanel leftTop;
	JPanel leftCenter;
	JPanel leftBottom;
	JPanel rightPanel;
	JPanel rightTop;

	JLabel orderShowLb; // �ֹ��Ѱ�,�ð������ִ·��̺�
	JLabel songUnitLb; // (��),(��)
	JButton deleteB; // ������ư
	JLabel priceLb; // �����ݾ�
	public static JLabel showPriceLb; // �����ݾ׺����ִ·��̺�
	JButton pointB;
	JButton payB;
	JButton paymentFinB;
	JButton sendMsgB;
	JButton completeB;

	JButton[] b = new JButton[16]; // ����,�ֹ���ư
	String t_id;
	int roomNum;

	// �׼Ǹ����ʿ��� ���� ������
	String tempStr1 = new String(); // �ӽù��ڿ�1
	String tempStr2 = new String(); // �ӽù��ڿ�2
	int actualPrice = 0; // ���������ұݾ�

	public static boolean isPayCompleted = false; // �����Ϸ��Ҷ� �ʿ��� flag�α�
	public static String payMethod = "";

	public static final Font font1 = new Font("���� ���", Font.BOLD, 17);
	public static final Font font2 = new Font("SansSerif", Font.BOLD, 25);
	public static final Font font3 = new Font("���� ���", Font.BOLD, 23);
	public static final Font font4 = new Font("SansSerif", Font.BOLD, 20);
	public static final Color color1 = new Color(241, 227, 255); // ������
	public static final Color color2 = new Color(242, 249, 255); // ���ϴ�
	public static final Color color3 = new Color(222, 239, 255); // �ϴ�

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
		JLabel serviceTitle = new JLabel(roomNum + "����  ��/�ð�");
		orderShowLb = new JLabel();
		songUnitLb = new JLabel("(��)");
		deleteB = new JButton("��");
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
		b[3] = new JButton("<html>��(��)</html>");
		b[4] = new JButton("4");
		b[5] = new JButton("5");
		b[6] = new JButton("6");
		b[7] = new JButton("<html>&nbsp;�ð�<br/>&nbsp;(��)</html>");
		b[8] = new JButton("1");
		b[9] = new JButton("2");
		b[10] = new JButton("3");
		b[11] = new JButton();
		b[12] = new JButton();
		b[13] = new JButton("0");
		b[14] = new JButton();
		b[15] = new JButton("Ȯ��");
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
		priceLb = new JLabel("�����ݾ�");
		showPriceLb = new JLabel("0");
		JLabel wonLb1 = new JLabel("��");
		pointB = new JButton("����Ʈ ���/����");
		payB = new JButton("�����ϱ�");
		paymentFinB = new JButton("�����Ϸ�");
		sendMsgB = new JButton("Send Message >>");
		completeB = new JButton("�ǸſϷ�");

		// �����κ�
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

		// �޼���������
		sendMsgB.setFont(font4);
		sendMsgB.setPreferredSize(new Dimension(0, 50));
		sendMsgB.setBackground(Service.color3);
		sendMsgB.addActionListener(new WindowActionListener(0, t_id));

		// �ǸſϷ�
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
		if (ae.getSource() == this.b[0]) { // ���ڹ�ư
			tempStr1 += "7";
		} else if (ae.getSource() == this.b[1]) {
			tempStr1 += "8";
		} else if (ae.getSource() == this.b[2]) {
			tempStr1 += "9";
		} else if (ae.getSource() == this.b[3]) {
			songUnitLb.setText("(��)");
		} else if (ae.getSource() == this.b[4]) {
			tempStr1 += "4";
		} else if (ae.getSource() == this.b[5]) {
			tempStr1 += "5";
		} else if (ae.getSource() == this.b[6]) {
			tempStr1 += "6";
		} else if (ae.getSource() == this.b[7] || ae.getSource() == this.b[11]) {
			songUnitLb.setText("(��)");
		} else if (ae.getSource() == this.b[8]) {
			tempStr1 += "1";
		} else if (ae.getSource() == this.b[9]) {
			tempStr1 += "2";
		} else if (ae.getSource() == this.b[10]) {
			tempStr1 += "3";
		} else if (ae.getSource() == this.b[13]) {
			tempStr1 += "0";
		}

		if ("��".equals(ac)) { // ������ư
			if (tempStr1.length() > 0) {
				tempStr1 = (String) (tempStr1.subSequence(0, tempStr1.length() - 1));
			}
		} // ������ư��
		orderShowLb.setText(tempStr1);

		if ("Ȯ��".equals(ac)) { // Ȯ�ι�ư
			if ("(��)".equals(songUnitLb.getText())) {
				tempStr1 = orderShowLb.getText();
				actualPrice = Integer.parseInt(tempStr1) * 100;
			} else if ("(��)".equals(songUnitLb.getText())) {
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
		} // Ȯ�ι�ư��

		// ��񿬰�
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

		if ("�����Ϸ�".equals(ac)) {
			String useType;
			int resultPrice = actualPrice - Point_use.usedPoint;
			// �����ƴ��� üũ >> ������� ������ ����
			if (isPayCompleted) { // �����Ǹ�
				if (songUnitLb.getText().equals("(��)")) {
					useType = "����";
					sb.append("<html>").append(roomNum).append(")&nbsp;&nbsp;�ִ��ο� ");
					if (roomNum < 7) {
						sb.append(3);
					} else {
						sb.append(6);
					}
					sb.append("��<br/><br/>����  " + orderShowLb.getText() + "�� <br/><br/>�����ݾ� : " + resultPrice
							+ "</html>");
					MainScreen.lb[roomNum - 1].setText(sb.toString());
				} else {
					useType = "�ð�";
					sb.append("<html>").append(roomNum).append(")&nbsp;&nbsp;�ִ��ο� ");
					if (roomNum < 7) {
						sb.append(3);
					} else {
						sb.append(6);
					}
					sb.append("��<br/><br/>�ð�  " + orderShowLb.getText() + "�� <br/><br/>�����ݾ� : " + resultPrice
							+ "</html>");
					MainScreen.lb[roomNum - 1].setText(sb.toString());
				}
				actualPrice = 0;
			} else {
				JOptionPane.showMessageDialog(this, "������ ���� �ʾҽ��ϴ�. ���� �� �����ϷḦ �����ּ���!", "�����Ϸ�",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			// coinroom_info���� ��뿩�θ� 0(���)���� 1(�����)�� �ٲ�
			String strSql1 = "update coinroom_info set use_whether=1 where room_number='" + roomNum + "';";
			try {
				pstmt = con.prepareStatement(strSql1);
				pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			String phoneNo;
			if (Point_earn.phoneNumTf_str == null && Point_use.phoneNumTf_str == null) {
				phoneNo = "��ȸ��";
			} else if (Point_earn.phoneNumTf_str != null) {
				phoneNo = Point_earn.phoneNumTf_str;
			} else {
				phoneNo = Point_use.phoneNumTf_str;
			}

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String saleTime = format.format(new Date());
			// db���̺� coinroom_sale�� ���� �߰�
			String strSql2 = "insert into coinroom_sale(room_number, use_type, sale_date, pay_price, pay_method, tel_number, refund_price) values('"
					+ roomNum + "', '" + useType + "', '" + saleTime + "', '" + resultPrice + "', '" + Service.payMethod
					+ "', '" + phoneNo + "', 0)";

			try {
				pstmt = con.prepareStatement(strSql2);
				pstmt.executeUpdate();
				JOptionPane.showMessageDialog(this, "������ �Ϸ�Ǿ����ϴ�!", "�����Ϸ�", JOptionPane.INFORMATION_MESSAGE);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			// �������̺�removeAll> �ٽ�db������������
			PaymentHistory.rowData.removeAllElements();
			PaymentHistory.rowData.addAll(PaymentHistory.getcoinroomSaleList(null));
			PaymentHistory.table.updateUI();

			Point_use.usedPoint = 0;
			Service.isPayCompleted = false;
		}

		if ("�ǸſϷ�".equals(ac)) {
			// ��漼��
			sb.append("<html>").append(roomNum).append(")&nbsp;&nbsp;�ִ��ο� ");
			if (roomNum < 7) {
				sb.append(3);
			} else {
				sb.append(6);
			}
			sb.append("��<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;���</html>");
			MainScreen.lb[roomNum - 1].setText(sb.toString());
			// coinroom_info���� ��뿩�θ� 1(�����)���� 0(���)���� �ٲ�
			String strSql3 = "update coinroom_info set use_whether=0 where room_number='" + roomNum + "';";
			try {
				pstmt = con.prepareStatement(strSql3);
				pstmt.executeUpdate();
				JOptionPane.showMessageDialog(this, "���ó���� �Ϸ�Ǿ����ϴ�!", "�ǸſϷ�", JOptionPane.INFORMATION_MESSAGE);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}
}

class WindowActionListener implements ActionListener { // ���������������
	int actualPrice;
	String t_id;

	WindowActionListener(int actualPrice, String s_id) {
		this.actualPrice = actualPrice;
		t_id = s_id;
	}

	public void actionPerformed(ActionEvent ae) {
		String s = ae.getActionCommand();
		if ("����Ʈ ���/����".equals(s)) {
			PointWindow pointWin = new PointWindow(actualPrice, t_id);
			pointWin.setTitle("����Ʈ ���/���� â");
			pointWin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			pointWin.setSize(500, 480);
			pointWin.setLocation(750, 470);
			pointWin.setVisible(true);
		} else if ("�����ϱ�".equals(s)) {
			PaymentWindow paymentWin = new PaymentWindow(actualPrice);
			paymentWin.setTitle("���� â");
			paymentWin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			paymentWin.setSize(500, 480);
			paymentWin.setLocation(750, 470);
			paymentWin.setVisible(true);
		} else if ("Send Message >>".equals(s)) {
			MessageWinow mw = new MessageWinow(t_id);
			mw.setTitle("�޼��� â");
			mw.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			mw.setSize(450, 250);
			mw.setLocation(800, 500);
			mw.setVisible(true);
		} else if ("�����Է�".equals(s)) {
			TextAreaWindow tw = new TextAreaWindow();
			tw.setTitle("�޼��� �Է� â");
			tw.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			tw.setSize(300, 200);
			tw.setLocation(870, 500);
			tw.setVisible(true);
		}

	}
}

class MessageWinow extends JFrame implements ActionListener { // �޼���â
	String[] msg = { "1) ����ʽÿ�~", "2) ���񽺸� �߰��Ͽ����ϴ�!", "3) �ð��� �� �Ǿ����ϴ�~", "4) ���� ����� ���� ���� ���� ��Ź�帳�ϴ�~!",
			"5) ��ſ� �ð��Ǽ̳���?^^*", "6) Ȥ�� ���� ���� ����ǰ�� �����Ű���??", "7) �Ƹ��ٿ� ����� �ӹ��ڸ��� �Ƹ�����ϴ�^^", "8) �ȳ��� ���ʽÿ�~^^" };
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
		send = new JButton("������");
		send.addActionListener(this);
		allSend = new JButton("��ü�߼�");
		allSend.addActionListener(this);
		directInput = new JButton("�����Է�");
		directInput.addActionListener(new WindowActionListener(0, s_id));

		ct.add(sp);
		ct.add(send);
		ct.add(allSend);
		ct.add(directInput);
	}

	public void actionPerformed(ActionEvent e) {
		String ac = e.getActionCommand();
		int[] indices = msgList.getSelectedIndices();
		if ("������".equals(ac)) {
			if (indices.length <= 0)
				JOptionPane.showMessageDialog(this, "���õ� ���� �����ϴ�!", "�޼����߼�", JOptionPane.WARNING_MESSAGE);
			else
				JOptionPane.showMessageDialog(this, "�ش� ���η뿡 �޼����� ���½��ϴ�!", "�޼����߼�", JOptionPane.INFORMATION_MESSAGE);
		} else if ("��ü�߼�".equals(ac)) {
			if (indices.length <= 0)
				JOptionPane.showMessageDialog(this, "���õ� ���� �����ϴ�!", "�޼�����ü�߼�", JOptionPane.WARNING_MESSAGE);
			else
				JOptionPane.showMessageDialog(this, "��ü ���η뿡 �޼����� ���½��ϴ�!", "�޼�����ü�߼�", JOptionPane.INFORMATION_MESSAGE);
		}
	}
}

class TextAreaWindow extends JFrame implements ActionListener { // �����Է¸޼���â
	private JTextArea msg;
	private JButton send;
	private JButton allSend;
	private JButton cancel;

	public TextAreaWindow() {
		Container ct = getContentPane();
		ct.setLayout(new FlowLayout());
		msg = new JTextArea("����ʽÿ�~", 7, 20);
		JScrollPane sp = new JScrollPane(msg);
		sp.setPreferredSize(new Dimension(230, 80));
		send = new JButton("������");
		send.addActionListener(this);
		allSend = new JButton("��ü�߼�");
		allSend.addActionListener(this);
		cancel = new JButton("���");
		cancel.addActionListener(this);

		ct.add(sp);
		ct.add(send);
		ct.add(allSend);
		ct.add(cancel);
	}

	public void actionPerformed(ActionEvent e) {
		String ac = e.getActionCommand();
		if ("������".equals(ac)) {
			if ("".equals(msg.getText())) {
				JOptionPane.showMessageDialog(this, "�Էµȸ޼����� �����ϴ�!", "�޼����߼�", JOptionPane.WARNING_MESSAGE);
			} else
				JOptionPane.showMessageDialog(this, "�ش� ���η뿡 �޼����� ���½��ϴ�!", "�޼����߼�", JOptionPane.INFORMATION_MESSAGE);
		} else if ("��ü�߼�".equals(ac)) {
			if ("".equals(msg.getText())) {
				JOptionPane.showMessageDialog(this, "�Էµȸ޼����� �����ϴ�!", "�޼�����ü�߼�", JOptionPane.WARNING_MESSAGE);
			} else
				JOptionPane.showMessageDialog(this, "��ü ���η뿡 �޼����� ���½��ϴ�!", "�޼�����ü�߼�", JOptionPane.INFORMATION_MESSAGE);
		} else if ("���".equals(ac)) {
			msg.setText("");
		}
	}
}

class Beverage extends JPanel implements ActionListener { // ������
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

	int columnPrice; // �����ǸŰ���
	int actualPrice; // ��������ݾ�
	int num; // �������������
	int num2; // �������������
	int inventoryNumMax; // ��������

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
	int row1; // ���̺��� ���õ� ���� ��ġ�� ������ ����
	int row2;
	int column1;
	int column2;

	public Beverage() {
		leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout(0, 45));
		leftTop = new JPanel();
		leftCenter = new JPanel();
		rightPanel = new JPanel();

		JLabel beverageTitle = new JLabel("����");
		beverageTitle.setFont(Service.font3);

		leftTop.add(beverageTitle);
		leftTop.setBackground(Service.color3);
		leftTop.setPreferredSize(new Dimension(370, 40));

		// ù��°���̺�
		columnName1 = new Vector<String>();
		columnName1.add("�����̸�");
		columnName1.add("����");
		columnName1.add("����");

		rowData1 = new Vector<Vector<String>>();
		// ��񿬵��ؼ� ��� ��������
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
						String beverage = v.get(0); // �����̸�
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
		priceLb = new JLabel("�����ݾ�");
		showPriceLb = new JLabel("0");
		JLabel wonLb1 = new JLabel("��");
		payB = new JButton("�����ϱ�");
		paymentFinB = new JButton("�����Ϸ�");
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
		// �ι�°���̺�
		columnName2 = new Vector<String>();
		columnName2.add("�����̸�");
		columnName2.add("����");
		columnName2.add("���ϱ�");
		columnName2.add("����");

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
					String beverage = v.get(0); // �����̸�
					int inventoryNum = Integer.parseInt(v.get(2)); // ����
					if (rowData2.get(row2).get(0).equals(beverage)) {
						inventoryNumMax = inventoryNum; // �����ִ� ������ inventoryNumMax�� �ִ´�
						break;
					}
				}

				if (rowData2.get(row2).get(0).equals("����") || rowData2.get(row2).get(0).equals("�ݶ�")
						|| rowData2.get(row2).get(0).equals("���̴�") || rowData2.get(row2).get(0).equals("ȯŸ����")
						|| rowData2.get(row2).get(0).equals("ȯŸ������")) {
					columnPrice = 1500;
				} else
					columnPrice = 2000;

				if (column2 == 2) { // +Ŭ��
					// Integer.parseInt(rowData2.get(row2).get(4))
					if (num < inventoryNumMax) {
						table2.setValueAt(Integer.toString(num + 1), row2, 1);
						actualPrice += columnPrice;
					}
					num2 = num + 1; // ���� �ÿ� �ʿ��� ���� �Ѱ��ֱ�
				} else if (column2 == 3) { // -Ŭ��
					if (num > 0) {
						table2.setValueAt(Integer.toString(num - 1), row2, 1);
						actualPrice -= columnPrice;
						num2 = num - 1; // ���� �ÿ� �ʿ��� ���� �Ѱ��ֱ�
					}
				} else
					num2 = num; // ���� �ÿ� �ʿ��� ���� �Ѱ��ֱ�(+,-���� ���� �ƴ� �ٸ��� ���������� ���� ������ �Ѱ��ش�)
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
		table2.getColumn("���ϱ�").setCellRenderer(renderer2);
		table2.getColumn("����").setCellRenderer(renderer2);
		rightBottom.add(tableSP2);

		buttonPanel = new JPanel();
		deleteB = new JButton("����");
		deleteB.setFont(new Font("���� ���", Font.BOLD, 20));
		deleteB.addActionListener(this);
		cancelB = new JButton("���");
		cancelB.setFont(new Font("���� ���", Font.BOLD, 20));
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
		if ("�����ϱ�".equals(ac)) { // �������
			PaymentWindow paymentWin = new PaymentWindow(actualPrice);
			paymentWin.setTitle("���� â");
			paymentWin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			paymentWin.setSize(500, 480);
			paymentWin.setLocation(750, 470);
			paymentWin.setVisible(true);
		}

		// ������ưŬ��
		if ("����".equals(ac)) {
			actualPrice -= columnPrice * num2;
			showPriceLb.setText(Integer.toString(actualPrice));
			model2.removeRow(row2);
		} else if ("���".equals(ac)) { // ��ҹ�ưŬ��
			table2.setValueAt(0, row2, 1);
			if (num2 > 0) {
				actualPrice -= columnPrice * num2;
				num2 = 0;
			}
			showPriceLb.setText(Integer.toString(actualPrice));
		}
		
		// ��񿬰�
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
		
		if ("�����Ϸ�".equals(ac)) {
			// �����ƴ��� üũ >> ��� inventory���̺��� inventory_number�� ����
			if (Service.isPayCompleted) { // �����Ǹ�
				// ��񿬰�
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
				
				// db���̺� product_sale�� ���� �߰�
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				String saleTime = format.format(new Date());
				String productCode = "";
				for (Vector<String> v : rowData2) {
					String beverage = v.get(0); // ����
					int inventory1 = Integer.parseInt(v.get(1)); // ����
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
				
				// rowData2�� ���鼭 �����̸��� ������ŭ �������� ���ֱ�
				for (Vector<String> v : rowData2) {
					String beverage = v.get(0); // ����
					int inventory2 = Integer.parseInt(v.get(1)); // ����
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
				JOptionPane.showMessageDialog(this, "������ �Ϸ�Ǿ����ϴ�!", "�����Ϸ�",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(this, "������ ���� �ʾҽ��ϴ�. ���� �� �����ϷḦ �����ּ���!", "�����Ϸ�",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			// ����â���� ���̺�ٽ� �����;���
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

class SalesWindow extends JFrame { // �Ǹ�â
	public SalesWindow(int roomNum, String s_id) {
		JTabbedPane jtp = new JTabbedPane();
		// �Ǹ�â(����)
		Service s = new Service(roomNum, s_id);
		// �Ǹ�â(����)
		Beverage b = new Beverage();
		jtp.addTab("����", s);
		jtp.addTab("����", b);

		Container ct = getContentPane();
		ct.add(jtp);
	}
}

class Point_use extends JPanel implements ActionListener { // ����Ʈ�����
	JPanel top; // �����г�
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
	public static int resultPrice; // ����Ʈ����İ����ݾ�
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

		phoneNumLb = new JLabel("��ȭ��ȣ");
		phoneNumTf = new JTextField();
		inquiryB = new JButton("��ȸ");
		currentPointLb = new JLabel("���� ����Ʈ");
		showPointLb = new JLabel();
		JLabel pLb1 = new JLabel("P");
		usePointLb = new JLabel("����� ����Ʈ");
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

		fullUse = new JButton("���׻��");
		cancel = new JButton("���");
		ok = new JButton("Ȯ��");
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
		if ("��ȸ".equals(s)) {

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
		} else if ("���".equals(s)) {
			phoneNumTf.setText("");
			showPointLb.setText("");
			writePointTf.setText("");
		} else if ("���׻��".equals(s)) {
			if (actualPrice < Integer.parseInt(showPointLb.getText())) {
				JOptionPane.showMessageDialog(this, "�����ݾ��� ����Ʈ�����ݺ��� ��� �� �� �����ϴ�. ����Ʈ�� ����Ͻ÷��� ����Ʈ�� ���� �Է����ּ���!",
						"����Ʈ���޼���", JOptionPane.WARNING_MESSAGE);
			} else {
				writePointTf.setText(showPointLb.getText());
			}

		} else { // Ȯ��
			if (phoneNumTf.getText().equals("") || showPointLb.getText().equals("")
					|| writePointTf.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "�Էµ��� ���� ������ �ֽ��ϴ�. �ٽ� Ȯ�����ּ���!", "����Ʈ���޼���",
						JOptionPane.WARNING_MESSAGE);
			} else {
				int writePointTf_int = Integer.parseInt(writePointTf.getText());
				if (writePointTf_int > Integer.parseInt(showPointLb.getText()) || actualPrice < writePointTf_int) {
					JOptionPane.showMessageDialog(this, "����Ʈ�Է��� �߸��Ǿ����ϴ�. �ٽ� Ȯ�����ּ���!", "����Ʈ���޼���",
							JOptionPane.WARNING_MESSAGE);
				} else {
					usedPoint = writePointTf_int;
					resultPrice = actualPrice - writePointTf_int;
					resultPoint = Integer.parseInt(showPointLb.getText()) - Integer.parseInt(writePointTf.getText());

					// ��������Ʈ- ���������Ʈ => ����� ����Ʈ�� ����
					String strSql3 = "update " + t_id + "_customer_info set point='" + resultPoint
							+ "' where tel_number='" + phoneNumTf_str + "';";
					try {
						pstmt = con.prepareStatement(strSql3);
						pstmt.executeUpdate();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					JOptionPane.showMessageDialog(this, "����Ʈ����� �Ϸ�Ǿ����ϴ�!", "����Ʈ���޼���", JOptionPane.INFORMATION_MESSAGE);
					Service.showPriceLb.setText(Integer.toString(resultPrice)); // ����â�� �����ݾ��� ����Ʈ������� �� �ݾ����� set
				}
			}
		}
	}
}

class Point_earn extends JPanel implements ActionListener { // ����Ʈ������
	JPanel top; // �����г�
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

		phoneNumLb = new JLabel("��ȭ��ȣ");
		phoneNumTf = new JTextField();
		inquiryB = new JButton("��ȸ");
		currentPointLb = new JLabel("���� ����Ʈ");
		showPointLb = new JLabel();
		JLabel pLb1 = new JLabel("P");
		earnPointLb = new JLabel("���� ����Ʈ");
		showPointLb2 = new JLabel();
		JLabel pLb2 = new JLabel("P");
		applyPointLb = new JLabel("���� �� ����Ʈ");
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

		JButton cancel = new JButton("���");
		JButton ok = new JButton("Ȯ��");
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
		if ("��ȸ".equals(s)) {

			String strSql = "select point from " + t_id + "_customer_info where tel_number='" + phoneNumTf_str + "';";
			try {
				pstmt = con.prepareStatement(strSql);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					showPointLb.setText(rs.getString("point"));
					int showPointLb_int = Integer.parseInt(showPointLb.getText());
					// ������->�����ݾ��� 10���� ����
					int earnPoint = (int) (actualPrice * 0.1); // ��������Ʈ
					int applyPoint = showPointLb_int + earnPoint; // ���� �� ����Ʈ
					showPointLb2.setText(Integer.toString(earnPoint));
					showPointLb3.setText(Integer.toString(applyPoint));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if ("���".equals(s)) {
			phoneNumTf.setText("");
			showPointLb.setText("");
			showPointLb2.setText("");
			showPointLb3.setText("");
		} else { // Ȯ��
			if (phoneNumTf.getText().equals("") || showPointLb.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "�Էµ��� ���� ������ �ֽ��ϴ�. �ٽ� Ȯ�����ּ���!", "����Ʈ�����޼���",
						JOptionPane.WARNING_MESSAGE);
			} else {
				int showPointLb_int = Integer.parseInt(showPointLb.getText());
				// ������->�����ݾ��� 10���� ����
				int earnPoint = (int) (actualPrice * 0.1); // ��������Ʈ
				int applyPoint = showPointLb_int + earnPoint; // ���� �� ����Ʈ
				showPointLb2.setText(Integer.toString(earnPoint));
				showPointLb3.setText(Integer.toString(applyPoint));

				// ��� ����Ʈ �� ����
				String strSql2 = "update " + t_id + "_customer_info set point='" + applyPoint + "' where tel_number='"
						+ phoneNumTf_str + "';";
				try {
					pstmt = con.prepareStatement(strSql2);
					pstmt.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				JOptionPane.showMessageDialog(this, "����Ʈ������ �Ϸ�Ǿ����ϴ�!", "����Ʈ�����޼���", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
}

class PointWindow extends JFrame { // ����Ʈâ
	public PointWindow(int actualPrice, String s_id) {
		JTabbedPane jtp = new JTabbedPane();
		// ����Ʈâ(���)
		Point_use pu = new Point_use(actualPrice, s_id);
		// ����Ʈâ(����)
		Point_earn pe = new Point_earn(actualPrice, s_id);
		jtp.addTab("����Ʈ ���", pu);
		jtp.addTab("����Ʈ ����", pe);

		Container ct = getContentPane();
		ct.add(jtp);
	}
}

class Cash extends JPanel implements ActionListener { // ���ݰ�����
	JPanel top; // �����г�
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

		priceLb = new JLabel("�����ݾ�");
		showPriceLb1 = new JLabel();

		// ����Ʈ����� showPriceLb1�� ������ �ݾ� ���ϱ�
		int tmp = Point_use.usedPoint;
		if (tmp > 0) {
			actualPrice = actualPrice - tmp;
		}

		showPriceLb1.setText(Integer.toString(actualPrice));
		this.actualPrice = actualPrice;

		JLabel wonLb1 = new JLabel("��");
		receivedPriceLb = new JLabel("�����ݾ�");
		receivedPriceTf = new JTextField(10);
		JLabel wonLb2 = new JLabel("��");
		changeLb = new JLabel("�Ž�����");
		showPriceLb2 = new JLabel();
		JLabel wonLb3 = new JLabel("��");

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

		cancel = new JButton("���");
		cancel.addActionListener(this);
		ok = new JButton("Ȯ��");
		ok.addActionListener(this);

		bottom.add(cancel);
		bottom.add(ok);
		bottom.setBorder(new EmptyBorder(25, 25, 25, 25));

		add(top, BorderLayout.CENTER);
		add(bottom, BorderLayout.SOUTH);

	}

	public void actionPerformed(ActionEvent e) {
		String ac = e.getActionCommand();
		if ("���".equals(ac)) {
			receivedPriceTf.setText("");
			showPriceLb2.setText("");
		} else if ("Ȯ��".equals(ac)) { // ����Ȯ��
			if ("".equals(receivedPriceTf.getText()) || "".equals(showPriceLb2.getText()))
				JOptionPane.showMessageDialog(this, "�Էµ��� ���� ������ �ֽ��ϴ�. �ٽ� Ȯ�κ�Ź�帳�ϴ�!", "�����̿Ϸ�",
						JOptionPane.INFORMATION_MESSAGE);
			else {
				JOptionPane.showMessageDialog(this, "������ �Ϸ�Ǿ����ϴ�. ����â���� �����ϷḦ �����ּ���!", "�����Ϸ�",
						JOptionPane.INFORMATION_MESSAGE);
				Service.isPayCompleted = true;
				Service.payMethod = "����";
			}
		} else { // �����ݾ� �Է��ϰ� ���ʹ������� �Ž����� ���̱�
			int showPriceLb2_int = Integer.parseInt(receivedPriceTf.getText()) - actualPrice;
			if (showPriceLb2_int < 0) {
				JOptionPane.showMessageDialog(this, "�����ݾ��� �����մϴ�. �ݾ��� �ٽ� Ȯ�����ּ���!", "���! �ݾ׺���",
						JOptionPane.WARNING_MESSAGE);
			} else
				showPriceLb2.setText(Integer.toString(showPriceLb2_int));
		}
	}
}

class Card extends JPanel implements ActionListener { // ī�������
	JPanel top; // �����г�
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

		priceLb = new JLabel("�����ݾ�");
		showPriceLb = new JLabel(Integer.toString(actualPrice));

		// ����Ʈ����� showPriceLb�� ������ �ݾ� ���ϱ�
		int tmp = Point_use.usedPoint;
		if (tmp > 0) {
			actualPrice = actualPrice - tmp;
		}

		showPriceLb.setText(Integer.toString(actualPrice));
		this.actualPrice = actualPrice;

		JLabel wonLb1 = new JLabel("��");
		cardNumLb = new JLabel("ī���ȣ");
		cardNumTf1 = new JTextField(4);
		cardNumTf2 = new JTextField(4);
		cardNumTf3 = new JTextField(4);
		cardNumTf4 = new JTextField(4);
		ValidityLb = new JLabel("��ȿ�Ⱓ");
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

		cancel = new JButton("���");
		cancel.addActionListener(this);
		ok = new JButton("Ȯ��");
		ok.addActionListener(this);

		bottom.add(cancel);
		bottom.add(ok);
		bottom.setBorder(new EmptyBorder(25, 25, 25, 25));

		add(top, BorderLayout.CENTER);
		add(bottom, BorderLayout.SOUTH);

	}

	public void actionPerformed(ActionEvent e) {
		String ac = e.getActionCommand();
		if ("���".equals(ac)) {
			cardNumTf1.setText("");
			cardNumTf2.setText("");
			cardNumTf3.setText("");
			cardNumTf4.setText("");
			ValidityTf.setText("");
		} else {
			if ("".equals(cardNumTf1.getText()) || "".equals(cardNumTf2.getText()) || "".equals(cardNumTf3.getText())
					|| "".equals(cardNumTf4.getText()) || "".equals(ValidityTf.getText()))
				JOptionPane.showMessageDialog(this, "�Էµ��� ���� ������ �ֽ��ϴ�. �ٽ� Ȯ�κ�Ź�帳�ϴ�!", "�����̿Ϸ�",
						JOptionPane.INFORMATION_MESSAGE);
			else {
				JOptionPane.showMessageDialog(this, "������ �Ϸ�Ǿ����ϴ�. ����â���� �����ϷḦ �����ּ���!", "�����Ϸ�",
						JOptionPane.INFORMATION_MESSAGE);
				Service.isPayCompleted = true;
				Service.payMethod = "ī��";
			}
		}
	}
}

class PaymentWindow extends JFrame { // ����â
	public PaymentWindow(int actualPrice) {
		JTabbedPane jtp = new JTabbedPane();
		// ����â(����)
		Cash cash = new Cash(actualPrice);
		// ����â(ī��)
		Card card = new Card(actualPrice);
		jtp.addTab("����", cash);
		jtp.addTab("ī��", card);

		Container ct = getContentPane();
		ct.add(jtp);

	}
}
