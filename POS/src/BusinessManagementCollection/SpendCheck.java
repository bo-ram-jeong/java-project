package BusinessManagementCollection;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class SpendCheck extends JFrame implements ActionListener {
	private JTable table;
	private JTextField salesSum;
	JComboBox date1Year, date1Mon, date1Day, date2Year, date2Mon, date2Day;
	JButton todayB, searchB, weekB, monthB, closeB;
	String[] year, month, day;
	Vector<String> columnName1;
	Vector<Vector<String>> rowData1;
	Vector<String> txt;
	JScrollPane tableSP1;
	DefaultTableModel model1 = null;
	Calendar cal;
	String thisDay, thisMon, thisYear; // ���� ��, ��, ��
	String calDay, calMon, calYear; // ���� ��, ��, ��

	public SpendCheck() {
		setTitle("������ȸ");
		getContentPane().setBackground(new Color(255, 227, 236));
		getContentPane().setLayout(null);

		// ��¥�迭 �� �Ҵ�
		year = new String[] { "2018", "2019", "2020", "2021", "2022" };
		month = new String[12];
		day = new String[31];
		for (int i = 0; i < 12; i++)
			month[i] = Integer.toString(i + 1);
		for (int i = 0; i < 31; i++)
			day[i] = Integer.toString(i + 1);

		// ����
		rowData1 = new Vector<Vector<String>>();

		// ���̺� ù�� ����
		columnName1 = new Vector<String>();

		columnName1.add("��¥");
		columnName1.add("��ǰ��");
		columnName1.add("�ܰ�");
		columnName1.add("��ǰ�ڵ�");
		columnName1.add("�԰����");
		columnName1.add("����ݾ�");

		model1 = new DefaultTableModel(rowData1, columnName1);

		// ���̺�
		table = new JTable(model1);
		table.setEnabled(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(115);
		tableSP1 = new JScrollPane(table);
		tableSP1.setBounds(12, 40, 692, 166);

		// UI
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(12, 10, 716, 249);
		panel.add(tableSP1);
		panel.setBackground(new Color(255, 255, 255));
		getContentPane().add(panel);

		JLabel label = new JLabel("��ȸ�Ⱓ");
		label.setBounds(12, 15, 53, 15);
		panel.add(label);

		date1Year = new JComboBox(year);
		date1Year.setBounds(67, 13, 60, 19);
		panel.add(date1Year);

		date1Mon = new JComboBox(month);
		date1Mon.setBounds(130, 13, 40, 19);
		panel.add(date1Mon);

		date1Day = new JComboBox(day);
		date1Day.setBounds(173, 13, 40, 19);
		panel.add(date1Day);

		JLabel label_1 = new JLabel("~");
		label_1.setBounds(223, 15, 18, 15);
		panel.add(label_1);

		date2Year = new JComboBox(year);
		date2Year.setBounds(238, 13, 60, 19);
		panel.add(date2Year);

		date2Mon = new JComboBox(month);
		date2Mon.setBounds(301, 13, 40, 19);
		panel.add(date2Mon);

		date2Day = new JComboBox(day);
		date2Day.setBounds(344, 13, 40, 19);
		panel.add(date2Day);

		todayB = new JButton("����");
		todayB.setForeground(new Color(83, 78, 82));
		todayB.setBackground(new Color(218, 246, 233));
		todayB.setBounds(394, 11, 74, 23);
		panel.add(todayB);

		searchB = new JButton("��    ȸ");
		searchB.setForeground(new Color(83, 78, 82));
		searchB.setBackground(new Color(255, 250, 164));
		searchB.setBounds(630, 11, 74, 23);
		panel.add(searchB);

		weekB = new JButton("������");
		weekB.setForeground(new Color(83, 78, 82));
		weekB.setBackground(new Color(218, 246, 233));
		weekB.setBounds(472, 11, 74, 23);
		panel.add(weekB);

		monthB = new JButton("�Ѵ�");
		monthB.setForeground(new Color(83, 78, 82));
		monthB.setBackground(new Color(218, 246, 233));
		monthB.setBounds(551, 11, 74, 23);
		panel.add(monthB);

		JLabel label_3 = new JLabel("�� ����");
		label_3.setBounds(12, 219, 53, 15);
		panel.add(label_3);

		salesSum = new JTextField();
		salesSum.setColumns(10);
		salesSum.setBounds(77, 216, 155, 21);
		panel.add(salesSum);

		closeB = new JButton("��    ��");
		closeB.setBounds(630, 216, 74, 23);
		panel.add(closeB);
		closeB.setForeground(new Color(83, 78, 82));
		closeB.setBackground(new Color(218, 246, 233));

		todayB.addActionListener(this);
		searchB.addActionListener(this);
		weekB.addActionListener(this);
		monthB.addActionListener(this);
		closeB.addActionListener(this);
	}

	public void actionPerformed(ActionEvent ae) {// �׼��̺�Ʈ
		cal = Calendar.getInstance();
		thisDay = Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
		thisMon = Integer.toString(cal.get(Calendar.MONTH) + 1);
		thisYear = Integer.toString(cal.get(Calendar.YEAR));

		if (ae.getSource() == todayB) { // ���� ��ư
			calDay = thisDay;
			calMon = thisMon;
			calYear = thisYear;
			PrintDate(); // �޺��ڽ� �ؽ�Ʈ ���� �޼ҵ�
		} else if (ae.getSource() == weekB) { // ������ ��ư
			cal.add(Calendar.DATE, -6);
			PrintDate();
		} else if (ae.getSource() == monthB) { // �Ѵ� ��ư
			cal.add(Calendar.MONTH, -1);
			PrintDate();
		} else if (ae.getSource() == closeB) { // �ݱ� ��ư
			dispose();
		} else if (ae.getSource() == searchB) { // ��ȸ ��ư
			// ���̺� �ʱ�ȭ
			model1.setNumRows(0);

			try {
				Class.forName("com.mysql.cj.jdbc.Driver"); // mysql�� jdbc Driver ����
				System.err.println("JDBC-ODBC ����̹��� ���������� �ε���");
			} catch (ClassNotFoundException e) {
				System.err.println("����̹� �ε忡 �����߽��ϴ�.");
			}
			try { // mysql�� ���� student �����ͺ��̽��� �����ϱ�
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/coinroom?serverTimezone=UTC",
						"root", "java2020");
				Statement dbSt = con.createStatement();

				// ���� ���õ� ������Ʈ�� �� ����
				calYear = (String) date1Year.getSelectedItem();
				calMon = (String) date1Mon.getSelectedItem();
				calDay = (String) date1Day.getSelectedItem();
				thisYear = (String) date2Year.getSelectedItem();
				thisMon = (String) date2Mon.getSelectedItem();
				thisDay = (String) date2Day.getSelectedItem();

				// ���õ� ������Ʈ ��¥ ������ ��ǰ �Ǹ� ������ �˻�
				String sql1 = "SELECT * FROM buy_product,inventory where buy_date between date('" + calYear + "-"
						+ calMon + "-" + calDay + "') and date('" + thisYear + "-" + thisMon + "-" + thisDay
						+ "')+1 AND buy_product.product_code = inventory.product_code;";
				ResultSet result1 = dbSt.executeQuery(sql1);

				// �˻��� ������ �� ��ǰ �Ǹ� ���̺� ���
				while (result1.next()) {
					txt = new Vector<String>();
					int buyprice = Integer.parseInt(result1.getString("wear_price"))
							* Integer.parseInt(result1.getString("buy_count"));
					txt.add(result1.getString("buy_date"));
					txt.add(result1.getString("product_name"));
					txt.add(result1.getString("wear_price"));
					txt.add(result1.getString("product_code"));
					txt.add(result1.getString("buy_count"));
					txt.add(Integer.toString(buyprice));

					rowData1.add(txt);
				}

				// ���̺� ������Ʈ
				table.updateUI();

				dbSt.close();
				con.close();
			} catch (SQLException e) {
				System.out.println("SQLException : " + e.getMessage());
			}

			// ������
			int coin = 0;
			for (int i = 0; i < rowData1.size(); i++) // ����ݾ� �ջ�
				coin += Integer.parseInt(rowData1.get(i).get(5));

			// �� ���� ���
			salesSum.setText(Integer.toString(coin));
		}
	}

	public void PrintDate() { // �޺��ڽ� �ؽ�Ʈ����
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
