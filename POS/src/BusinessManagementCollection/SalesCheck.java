package BusinessManagementCollection;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class SalesCheck extends JPanel implements ActionListener {

	String t_id;
	private JTable table_inven;
	private JTable table_coin;
	private JTextField salesCoin;
	private JTextField salesCard;
	private JTextField salesSum;
	JComboBox date1Year, date1Mon, date1Day, date2Year, date2Mon, date2Day;
	JButton todayB, searchB, weekB, monthB, spendB, back;
	Vector<String> columnName1;
	Vector<String> columnName2;
	Vector<Vector<String>> rowData1;
	Vector<Vector<String>> rowData2;
	Vector<String> txt;
	JScrollPane tableSP1, tableSP2;
	DefaultTableModel model1 = null, model2 = null;
	UserSet parent;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 날짜포맷
	Calendar cal;
	String datestr;
	String thisDay, thisMon, thisYear; // 오늘 일, 월, 년
	String calDay, calMon, calYear; // 계산된 일, 월, 년

	String[] year, month, day;
	Font f1 = new Font("맑은 고딕", Font.PLAIN | Font.BOLD, 16);

	public SalesCheck(String s_id, UserSet us) {
		t_id = s_id;
		parent = us;
		setBackground(new Color(255, 255, 255));
		setLayout(null);
		// 날짜배열 값 할당
		year = new String[] { "2018", "2019", "2020", "2021", "2022" };
		month = new String[12];
		day = new String[31];
		for (int i = 0; i < 12; i++)
			month[i] = Integer.toString(i + 1);
		for (int i = 0; i < 31; i++)
			day[i] = Integer.toString(i + 1);

		// 벡터
		rowData1 = new Vector<Vector<String>>();
		rowData2 = new Vector<Vector<String>>();

		// 테이블 첫행 벡터
		columnName1 = new Vector<String>();
		columnName2 = new Vector<String>();

		columnName1.add("날짜");
		columnName1.add("상품명");
		columnName1.add("판매수량");
		columnName1.add("매출금액");
		columnName1.add("결재방법");
		columnName1.add("상품코드");

		columnName2.add("날짜");
		columnName2.add("방번호");
		columnName2.add("매출금액");
		columnName2.add("결재방법");

		model1 = new DefaultTableModel(rowData1, columnName1);
		model2 = new DefaultTableModel(rowData2, columnName2);

		// 상품매출테이블
		table_inven = new JTable(model1);
		table_inven.setEnabled(false);
		table_inven.getColumnModel().getColumn(0).setPreferredWidth(115);
		tableSP1 = new JScrollPane(table_inven);
		tableSP1.setBounds(12, 70, 1010, 220);

		// 코인룸매출테이블

		table_coin = new JTable(model2);
		table_coin.setEnabled(false);
		table_coin.getColumnModel().getColumn(0).setPreferredWidth(115);
		tableSP2 = new JScrollPane(table_coin);
		tableSP2.setBounds(12, 40, 555, 240);

		// UI
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.add(tableSP1);
		panel.setBackground(new Color(255, 255, 255));

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.add(tableSP2);
		panel_1.setLayout(null);
		panel_1.setBackground(new Color(255, 255, 255));

		JLabel label = new JLabel("조회기간");
		label.setBounds(12, 15, 53, 15);
		panel.add(label);

		date1Year = new JComboBox(year);
		date1Year.setBounds(67, 13, 64, 19);
		panel.add(date1Year);

		date1Mon = new JComboBox(month);
		date1Mon.setBounds(133, 13, 40, 19);
		panel.add(date1Mon);

		date1Day = new JComboBox(day);
		date1Day.setBounds(175, 13, 40, 19);
		panel.add(date1Day);

		JLabel label_1 = new JLabel("~");
		label_1.setBounds(223, 15, 18, 15);
		panel.add(label_1);

		date2Year = new JComboBox(year);
		date2Year.setBounds(238, 13, 64, 19);
		panel.add(date2Year);

		date2Mon = new JComboBox(month);
		date2Mon.setBounds(304, 13, 40, 19);
		panel.add(date2Mon);

		date2Day = new JComboBox(day);
		date2Day.setBounds(346, 13, 40, 19);
		panel.add(date2Day);

		todayB = new JButton("오늘");
		todayB.setForeground(new Color(83, 78, 82));
		todayB.setBackground(new Color(218, 246, 233));
		todayB.setBounds(394, 11, 74, 23);
		panel.add(todayB);

		searchB = new JButton("조    회");
		searchB.setForeground(new Color(83, 78, 82));
		searchB.setBackground(new Color(255, 250, 164));
		searchB.setBounds(630, 11, 74, 23);
		panel.add(searchB);

		weekB = new JButton("일주일");
		weekB.setForeground(new Color(83, 78, 82));
		weekB.setBackground(new Color(218, 246, 233));
		weekB.setBounds(472, 11, 74, 23);
		panel.add(weekB);

		monthB = new JButton("한달");
		monthB.setForeground(new Color(83, 78, 82));
		monthB.setBackground(new Color(218, 246, 233));
		monthB.setBounds(551, 11, 74, 23);
		panel.add(monthB);

		JLabel label_4 = new JLabel("상품 매출");
		label_4.setFont(new Font("굴림", Font.PLAIN, 15));
		label_4.setBounds(12, 40, 99, 23);
		panel.add(label_4);
		label_4.setFont(f1);

		JLabel label_2 = new JLabel("코인룸 매출");
		label_2.setFont(new Font("굴림", Font.PLAIN, 15));
		label_2.setBounds(12, 9, 99, 23);
		label_2.setFont(f1);
		panel_1.add(label_2);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_2.setBackground(new Color(255, 255, 255));
		panel_2.setLayout(null);

		JLabel label_3 = new JLabel("현금매출");
		label_3.setFont(f1);
		label_3.setBounds(12, 30, 65, 20);
		panel_2.add(label_3);

		JLabel label_5 = new JLabel("카드매출");
		label_5.setBounds(12, 70, 65, 20);
		label_5.setFont(f1);
		panel_2.add(label_5);

		JLabel label_6 = new JLabel("총 매출");
		label_6.setBounds(12, 110, 65, 20);
		label_6.setFont(f1);
		panel_2.add(label_6);

		salesCoin = new JTextField();
		salesCoin.setHorizontalAlignment(SwingConstants.RIGHT);
		salesCoin.setEditable(false);
		salesCoin.setBounds(90, 30, 160, 21);
		panel_2.add(salesCoin);
		salesCoin.setColumns(10);

		salesCard = new JTextField();
		salesCard.setHorizontalAlignment(SwingConstants.RIGHT);
		salesCard.setEditable(false);
		salesCard.setColumns(10);
		salesCard.setBounds(90, 70, 160, 21);
		panel_2.add(salesCard);

		salesSum = new JTextField();
		salesSum.setHorizontalAlignment(SwingConstants.RIGHT);
		salesSum.setEditable(false);
		salesSum.setColumns(10);
		salesSum.setBounds(90, 110, 160, 21);
		panel_2.add(salesSum);

		spendB = new JButton("지출내역 조회");
		spendB.setFont(new Font("맑은 고딕", Font.PLAIN | Font.BOLD, 15));
		spendB.setForeground(new Color(83, 78, 82));
		spendB.setBackground(new Color(218, 246, 233));
		spendB.setBounds(300, 30, 130, 40);
		panel_2.add(spendB);

		back = new JButton("뒤로가기");
		back.setFont(f1);
		back.setForeground(new Color(83, 78, 82));
		back.setBackground(new Color(218, 246, 233));

		panel.setBounds(5, 5, 1035, 300);
		panel_1.setBounds(5, 310, 580, 290);
		panel_2.setBounds(590, 310, 450, 200);
		back.setBounds(920, 540, 120, 50);

		add(panel);
		add(panel_1);
		add(panel_2);
		add(back);

		back.addActionListener(this);
		todayB.addActionListener(this);
		searchB.addActionListener(this);
		weekB.addActionListener(this);
		monthB.addActionListener(this);
		spendB.addActionListener(this);
	}

	public void actionPerformed(ActionEvent ae) {// 액션이벤트
		cal = Calendar.getInstance();
		datestr = sdf.format(cal.getTime());
		thisDay = Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
		thisMon = Integer.toString(cal.get(Calendar.MONTH) + 1);
		thisYear = Integer.toString(cal.get(Calendar.YEAR));
		if (ae.getSource() == back) {
			MainMenu win = new MainMenu("메인메뉴", t_id);
			win.setSize(600, 550);
			win.setLocation(680, 280);
			win.show();
			win.setResizable(false);
			win.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			parent.dispose();
		}

		if (ae.getSource() == todayB) { // 오늘 버튼
			calDay = thisDay;
			calMon = thisMon;
			calYear = thisYear;
			PrintDate(); // 콤보박스 텍스트 변경 메소드
		} else if (ae.getSource() == weekB) { // 일주일 버튼
			cal.add(Calendar.DATE, -6);
			PrintDate();
		} else if (ae.getSource() == monthB) { // 한달 버튼
			cal.add(Calendar.MONTH, -1);
			PrintDate();
		} else if (ae.getSource() == spendB) {
			SpendCheck win = new SpendCheck();
			win.setTitle("지출내역");
			win.setSize(758, 310);
			win.setLocation(400, 400);
			win.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			win.show();
		} else if (ae.getSource() == searchB) { // 조회 버튼
			// 테이블 초기화
			model1.setNumRows(0);
			model2.setNumRows(0);

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

				// 현재 선택된 컴포넌트의 값 저장
				calYear = (String) date1Year.getSelectedItem();
				calMon = (String) date1Mon.getSelectedItem();
				calDay = (String) date1Day.getSelectedItem();
				thisYear = (String) date2Year.getSelectedItem();
				thisMon = (String) date2Mon.getSelectedItem();
				thisDay = (String) date2Day.getSelectedItem();

				// 선택된 컴포넌트 날짜 범위의 상품 판매 데이터 검색
				String sql1 = "SELECT * FROM product_sale, inventory where sale_date between date('" + calYear + "-"
						+ calMon + "-" + calDay + "') and date('" + thisYear + "-" + thisMon + "-" + thisDay
						+ "')+1 AND product_sale.product_code = inventory.product_code;";
				ResultSet result1 = dbSt.executeQuery(sql1);

				// 검색된 데이터 값 상품 판매 테이블 출력
				while (result1.next()) {
					txt = new Vector<String>();
					int buyprice = Integer.parseInt(result1.getString("sale_count"))
							* Integer.parseInt(result1.getString("wear_price"));
					txt.add(result1.getString("sale_date"));
					txt.add(result1.getString("product_name"));
					txt.add(result1.getString("sale_count"));
					txt.add(Integer.toString(buyprice));
					txt.add(result1.getString("pay_method"));
					txt.add(result1.getString("product_code"));
					rowData1.add(txt);
				}

				// 선택된 컴포넌트 날짜 범위의 코인룸 판매 데이터 검색
				String sql2 = "SELECT * FROM coinroom_sale where sale_date between date('" + calYear + "-" + calMon
						+ "-" + calDay + "') and date('" + thisYear + "-" + thisMon + "-" + thisDay + "')+1;";
				ResultSet result2 = dbSt.executeQuery(sql2);

				// 검색된 데이터 값 코인룸 판매 테이블 출력
				while (result2.next()) {
					txt = new Vector<String>();
					txt.add(result2.getString("sale_date"));
					txt.add(result2.getString("room_number"));
					txt.add(result2.getString("pay_price"));
					txt.add(result2.getString("pay_method"));
					rowData2.add(txt);
				}

				// 테이블 업데이트
				table_inven.updateUI();
				table_coin.updateUI();

				dbSt.close();
				con.close();
			} catch (SQLException e) {
				System.out.println("SQLException : " + e.getMessage());
			}

			// 매출계산
			int coin = 0, card = 0;
			for (int i = 0; i < rowData1.size(); i++) // 상품매출 현금,카드 계산
				if (rowData1.get(i).get(4).equals("현금"))
					coin += Integer.parseInt(rowData1.get(i).get(3));
				else
					card += Integer.parseInt(rowData1.get(i).get(3));

			for (int i = 0; i < rowData2.size(); i++) // 코인룸 매출 현금,카드 계산
				if (rowData2.get(i).get(3).equals("현금"))
					coin += Integer.parseInt(rowData2.get(i).get(2));
				else
					card += Integer.parseInt(rowData2.get(i).get(2));

			salesCoin.setText(Integer.toString(coin));
			salesCard.setText(Integer.toString(card));
			salesSum.setText(Integer.toString(coin + card));
		}
	}

	public void PrintDate() { // 콤보박스 텍스트변경
		calDay = Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
		calMon = Integer.toString(cal.get(Calendar.MONTH) + 1);
		calYear = Integer.toString(cal.get(Calendar.YEAR));
		date1Year.setSelectedItem(calYear);
		date1Mon.setSelectedItem(calMon);
		date1Day.setSelectedItem(calDay);
		date2Year.setSelectedItem(thisYear);
		date2Mon.setSelectedItem(thisMon);
		date2Day.setSelectedItem(thisDay);
	}

}
