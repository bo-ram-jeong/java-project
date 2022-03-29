package BusinessManagementCollection;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import java.awt.*;
import java.util.Vector;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;

//2015041071 엄준영

public class AddInventory extends JFrame implements ItemListener, ActionListener {
	private JTextField add_buycount;
	private JTable add_table;
	JComboBox add_name;
	JLabel add_code, add_group, add_buyprice, add_count;
	JButton add_submit, add_close;
	InventoryControl iv;
	Vector<String> columnName;
	Vector<Vector<String>> rowData;
	Vector<String> txt;
	String[] inven;
	DefaultTableModel model = null;
	JScrollPane tableSP;
	String product_name, quantity, saleprice, buyprice, code, group, buycount;
	int pricesum = 0;
	int num, sum, price; // 입고수량 입력값, 현재수량, 구입금액
	private JTextField add_pricesum;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 날짜포맷
	Calendar cal;
	String datestr;

	public AddInventory(InventoryControl ic) {
		iv = ic;

		// 테이블 첫행
		columnName = new Vector<String>();
		columnName.add("구입날짜");
		columnName.add("분류");
		columnName.add("상품명");
		columnName.add("입고수량");
		columnName.add("현재수량");
		columnName.add("개당가격");
		columnName.add("구입금액");

		// 상품명 콤보박스에 넣을 상품 목록 배열 생성
		inven = new String[iv.inven_table.getRowCount()]; // 재고테이블의 행의 수 크기의 배열
		for (int i = 0; i < inven.length; i++) { // 재고 이름 목록을 배열 inven에 삽입
			inven[i] = (String) iv.rowData.get(i).get(1);
		}

		// 벡터, 모델
		rowData = new Vector<Vector<String>>();
		model = new DefaultTableModel(rowData, columnName);

		// UI
		setTitle("입고");
		getContentPane().setBackground(new Color(255, 227, 236));
		getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(12, 10, 810, 288);
		getContentPane().add(panel);
		panel.setLayout(null);

		// 테이블
		add_table = new JTable(model) {
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				// 분류에 따른 행 색상 변경
				JComponent component = (JComponent) super.prepareRenderer(renderer, row, column);
				Color color = new Color(204, 255, 255);
				Color color2 = new Color(255, 255, 153);

				if (!isRowSelected(row)) { // 선택중이 아닐때
					if (rowData.get(row).get(1).equals("음료")) // rowData 테이블의 row행의 6번째 cell이 "음료"일경우
						component.setBackground(color);
					else if (rowData.get(row).get(1).equals("과자")) // 과자일경우
						component.setBackground(color2);
				}
				return component;
			}
		};
		add_table.setEnabled(false);
		add_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // 다중선택금지
		tableSP = new JScrollPane(add_table);
		add_table.getColumnModel().getColumn(0).setPreferredWidth(128);
		tableSP.setBounds(231, 34, 567, 211);
		panel.add(tableSP);

		// UI
		JLabel label = new JLabel("상품 선택");
		label.setFont(new Font("굴림", Font.PLAIN, 15));
		label.setBounds(12, 10, 99, 23);
		panel.add(label);

		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBounds(12, 35, 206, 211);
		panel.add(panel_1);

		JLabel label_1 = new JLabel("분류");
		label_1.setBounds(12, 63, 40, 15);
		panel_1.add(label_1);

		JLabel label_2 = new JLabel("상품명");
		label_2.setBounds(12, 14, 40, 15);
		panel_1.add(label_2);

		JLabel label_3 = new JLabel("입고수량");
		label_3.setBounds(12, 143, 53, 15);
		panel_1.add(label_3);

		add_buycount = new JTextField();
		add_buycount.setHorizontalAlignment(SwingConstants.CENTER);
		add_buycount.setColumns(10);
		add_buycount.setBounds(78, 140, 116, 21);
		panel_1.add(add_buycount);

		add_name = new JComboBox(inven);
		add_name.setBounds(78, 10, 116, 23);
		panel_1.add(add_name);

		JLabel label_4 = new JLabel("입고가격");
		label_4.setBounds(12, 88, 53, 15);
		panel_1.add(label_4);

		add_buyprice = new JLabel("0");
		add_buyprice.setHorizontalAlignment(SwingConstants.CENTER);
		add_buyprice.setBounds(78, 88, 116, 15);
		panel_1.add(add_buyprice);

		JLabel label_6 = new JLabel("현재수량");
		label_6.setBounds(12, 113, 53, 15);
		panel_1.add(label_6);

		add_count = new JLabel("0");
		add_count.setHorizontalAlignment(SwingConstants.CENTER);
		add_count.setBounds(78, 113, 116, 15);
		panel_1.add(add_count);

		add_submit = new JButton("입    고");
		add_submit.setForeground(new Color(83, 78, 82));
		add_submit.setBackground(new Color(218, 246, 233));
		add_submit.setBounds(54, 171, 97, 23);
		panel_1.add(add_submit);

		add_group = new JLabel("");
		add_group.setHorizontalAlignment(SwingConstants.CENTER);
		add_group.setBounds(78, 63, 116, 15);
		panel_1.add(add_group);

		JLabel label_7 = new JLabel("상품코드");
		label_7.setBounds(12, 39, 53, 15);
		panel_1.add(label_7);

		add_code = new JLabel("");
		add_code.setHorizontalAlignment(SwingConstants.CENTER);
		add_code.setBounds(78, 39, 116, 15);
		panel_1.add(add_code);

		add_close = new JButton("닫    기");
		add_close.setForeground(new Color(83, 78, 82));
		add_close.setBackground(new Color(218, 246, 233));
		add_close.setBounds(701, 255, 97, 23);
		panel.add(add_close);

		JLabel label_5 = new JLabel("입고 현황");
		label_5.setFont(new Font("굴림", Font.PLAIN, 15));
		label_5.setBounds(231, 10, 99, 23);
		panel.add(label_5);

		JLabel label_8 = new JLabel("총 구입금액");
		label_8.setFont(new Font("굴림", Font.PLAIN, 14));
		label_8.setBounds(231, 260, 89, 15);
		panel.add(label_8);

		add_pricesum = new JTextField();
		add_pricesum.setHorizontalAlignment(SwingConstants.RIGHT);
		add_pricesum.setEditable(false);
		add_pricesum.setBounds(313, 257, 116, 21);
		panel.add(add_pricesum);
		add_pricesum.setColumns(10);

		// 상품선택 패널 컴포넌트들 초기값
		add_code.setText(iv.rowData.get(0).get(5));
		add_group.setText(iv.rowData.get(0).get(6));
		add_buyprice.setText(iv.rowData.get(0).get(4));
		add_count.setText(iv.rowData.get(0).get(2));

		// 리스너 연결
		add_close.addActionListener(this);
		add_submit.addActionListener(this);
		add_buycount.addActionListener(this);
		add_name.addItemListener(this);

	}// 생성자

	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == add_close) { // 닫기버튼
			dispose(); // 창을닫는다
		} else if (ae.getSource() == add_submit || ae.getSource() == add_buycount) { // 입고버튼, 텍스트필드
			// 현재 시각 문자열 생성
			cal = Calendar.getInstance();
			datestr = sdf.format(cal.getTime());
			System.out.println(datestr);

			// DB연결
			try {
				Class.forName("com.mysql.cj.jdbc.Driver"); // mysql의 jdbc Driver 연결
				System.err.println("JDBC-ODBC 드라이버를 정상적으로 로드함");
			} catch (ClassNotFoundException e) {
				System.err.println("드라이버 로드에 실패했습니다.");
			}
			try { // mysql에 만든 student 데이터베이스에 연결하기

				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/coinroom?serverTimezone=UTC",
						"root", "java2020");
				// System.out.println("DB 연결 완료.");
				Statement dbSt = con.createStatement();
				// System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");

				// 상품선택 패널에 출력된 값들 받아옴
				code = add_code.getText();
				product_name = (String) add_name.getSelectedItem();
				group = add_group.getText();
				quantity = add_count.getText();
				buyprice = add_buyprice.getText();
				buycount = add_buycount.getText();

				// 입력값 숫자여부 검사
				try {
					num = Integer.parseInt(add_buycount.getText());

					// '상품선택' 컴포넌트 처리 및 DB에 추가
					sum = Integer.parseInt(add_count.getText()) + num;
					// 입고한 후의 수량
					price = Integer.parseInt(add_buyprice.getText()) * num;
					// 구입금액
					pricesum += price;

					// 선택된 상품의 재고 수량 업데이트
					String sql = "UPDATE inventory SET inventory_number = '" + sum + "' WHERE product_code = '" + code
							+ "';";
					dbSt.executeUpdate(sql); // DB에 입고한 후의 재고 수량 적용
					add_count.setText(Integer.toString(sum)); // 현재수량 컴포넌트 최신화
					add_buycount.setText("");

					// 입고 테이블 생성
					txt = new Vector<String>();
					txt.add(datestr);
					txt.add(group);
					txt.add(product_name);
					txt.add(buycount);
					txt.add(Integer.toString(sum));
					txt.add(buyprice);
					txt.add(Integer.toString(price));
					rowData.add(txt);
					add_pricesum.setText(Integer.toString(pricesum));
					add_table.updateUI();

					// 입고 정보 DB에 정보 저장
					String sql2 = "insert into buy_product(product_code, buy_date, buy_count) values('" + code + "', '"
							+ datestr + "', " + num + ");";
					dbSt.executeUpdate(sql2);

				} catch (NumberFormatException b) {
					JOptionPane.showMessageDialog(null, "숫자를 입력하세요", "알림", JOptionPane.INFORMATION_MESSAGE);
				}

				// 재고관리창 업데이트
				iv.Table_DBupdate();

				// 텍스트필드 초기화
				add_buycount.setText("");

				dbSt.close();
				con.close();

			} catch (SQLException e) {
				System.out.println("SQLException : " + e.getMessage());
			}
		} // if 입고버튼
	}// 액션이벤트

	public void itemStateChanged(ItemEvent ie) { // 콤보박스 상품 선택시 해당 상품의 정보 출력
		String s = (String) ie.getItem();
		for (int i = 0; i < inven.length; i++) {
			if (iv.rowData.get(i).get(1).equals(s) && ie.getStateChange() == ItemEvent.SELECTED) {
				add_code.setText(iv.rowData.get(i).get(5));
				add_group.setText(iv.rowData.get(i).get(6));
				add_buyprice.setText(iv.rowData.get(i).get(4));
				add_count.setText(iv.rowData.get(i).get(2));
				break;
			}
		}
	}// 콤보박스이벤트
}// 클래스
