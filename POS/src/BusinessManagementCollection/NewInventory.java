package BusinessManagementCollection;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.util.Vector;

public class NewInventory extends JFrame implements ActionListener {
	private JTextField new_name;
	private JTextField new_code;
	private JTextField new_saleprice;
	private JTextField new_buyprice;
	private JTextField new_count;

	String group, name, code, saleprice, buyprice, count;

	JButton new_close;
	JButton new_submit;
	JComboBox new_group;
	InventoryControl iv;

	public NewInventory(InventoryControl ic) {
		iv = ic;

		setTitle("상품 추가");
		getContentPane().setBackground(new Color(255, 227, 236));
		getContentPane().setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBounds(12, 10, 436, 148);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(12, 10, 410, 101);
		panel_1.add(panel);
		panel.setLayout(null);
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));

		JLabel label = new JLabel("분류");
		label.setBounds(12, 14, 40, 15);
		panel.add(label);

		JLabel label_1 = new JLabel("상품명");
		label_1.setBounds(12, 45, 40, 15);
		panel.add(label_1);

		new_name = new JTextField();
		new_name.setColumns(10);
		new_name.setBounds(78, 39, 116, 21);
		panel.add(new_name);

		JLabel label_2 = new JLabel("상품코드");
		label_2.setBounds(12, 73, 53, 15);
		panel.add(label_2);

		new_code = new JTextField();
		new_code.setColumns(10);
		new_code.setBounds(78, 67, 116, 21);
		panel.add(new_code);

		new_group = new JComboBox(iv.sGroup);
		new_group.setBounds(78, 7, 116, 23);
		panel.add(new_group);

		JLabel label_3 = new JLabel("판매가격");
		label_3.setBounds(216, 13, 53, 15);
		panel.add(label_3);

		new_saleprice = new JTextField();
		new_saleprice.setColumns(10);
		new_saleprice.setBounds(282, 7, 116, 21);
		panel.add(new_saleprice);

		JLabel label_4 = new JLabel("입고가격");
		label_4.setBounds(216, 42, 53, 15);
		panel.add(label_4);

		new_buyprice = new JTextField();
		new_buyprice.setColumns(10);
		new_buyprice.setBounds(282, 36, 116, 21);
		panel.add(new_buyprice);

		JLabel label_5 = new JLabel("수량");
		label_5.setBounds(216, 73, 53, 15);
		panel.add(label_5);

		new_count = new JTextField();
		new_count.setColumns(10);
		new_count.setBounds(282, 67, 116, 21);
		panel.add(new_count);

		new_submit = new JButton("추    가");
		new_submit.setBounds(341, 121, 81, 23);
		panel_1.add(new_submit);
		new_submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		new_submit.setForeground(new Color(83, 78, 82));
		new_submit.setBackground(new Color(218, 246, 233));

		new_close = new JButton("취    소");
		new_close.setBounds(248, 121, 81, 23);
		panel_1.add(new_close);
		new_close.setForeground(new Color(83, 78, 82));
		new_close.setBackground(new Color(218, 246, 233));

		new_close.addActionListener(this);
		new_submit.addActionListener(this);
	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == new_close) { // 취소 버튼
			dispose();
		} else if (ae.getSource() == new_submit) { // 추가 버튼
			try {
				Class.forName("com.mysql.cj.jdbc.Driver"); // mysql의 jdbc Driver 연결
				System.err.println("JDBC-ODBC 드라이버를 정상적으로 로드함");
			} catch (ClassNotFoundException e) {
				System.err.println("드라이버 로드에 실패했습니다.");
			}
			try { // mysql에 만든 student 데이터베이스에 연결하기
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/coinroom?serverTimezone=UTC",
						"root", "java2020");
				Statement dbSt = con.createStatement();

				group = (String) new_group.getSelectedItem();
				name = new_name.getText();
				code = new_code.getText();
				saleprice = new_saleprice.getText();
				buyprice = new_buyprice.getText();
				count = new_count.getText();

				String sql = "insert into inventory values('" + code + "', '" + name + "', '" + group + "', " + count
						+ ", " + saleprice + ", " + buyprice + ");";
				dbSt.executeUpdate(sql);
				System.out.println("새 품목 추가됨");
				iv.Table_DBupdate();
				dispose();

				dbSt.close();
				con.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "올바른 값을 입력하세요", "알림", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
}
