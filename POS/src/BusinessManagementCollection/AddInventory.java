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

//2015041071 ���ؿ�

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
	int num, sum, price; // �԰���� �Է°�, �������, ���Աݾ�
	private JTextField add_pricesum;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // ��¥����
	Calendar cal;
	String datestr;

	public AddInventory(InventoryControl ic) {
		iv = ic;

		// ���̺� ù��
		columnName = new Vector<String>();
		columnName.add("���Գ�¥");
		columnName.add("�з�");
		columnName.add("��ǰ��");
		columnName.add("�԰����");
		columnName.add("�������");
		columnName.add("���簡��");
		columnName.add("���Աݾ�");

		// ��ǰ�� �޺��ڽ��� ���� ��ǰ ��� �迭 ����
		inven = new String[iv.inven_table.getRowCount()]; // ������̺��� ���� �� ũ���� �迭
		for (int i = 0; i < inven.length; i++) { // ��� �̸� ����� �迭 inven�� ����
			inven[i] = (String) iv.rowData.get(i).get(1);
		}

		// ����, ��
		rowData = new Vector<Vector<String>>();
		model = new DefaultTableModel(rowData, columnName);

		// UI
		setTitle("�԰�");
		getContentPane().setBackground(new Color(255, 227, 236));
		getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(12, 10, 810, 288);
		getContentPane().add(panel);
		panel.setLayout(null);

		// ���̺�
		add_table = new JTable(model) {
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				// �з��� ���� �� ���� ����
				JComponent component = (JComponent) super.prepareRenderer(renderer, row, column);
				Color color = new Color(204, 255, 255);
				Color color2 = new Color(255, 255, 153);

				if (!isRowSelected(row)) { // �������� �ƴҶ�
					if (rowData.get(row).get(1).equals("����")) // rowData ���̺��� row���� 6��° cell�� "����"�ϰ��
						component.setBackground(color);
					else if (rowData.get(row).get(1).equals("����")) // �����ϰ��
						component.setBackground(color2);
				}
				return component;
			}
		};
		add_table.setEnabled(false);
		add_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // ���߼��ñ���
		tableSP = new JScrollPane(add_table);
		add_table.getColumnModel().getColumn(0).setPreferredWidth(128);
		tableSP.setBounds(231, 34, 567, 211);
		panel.add(tableSP);

		// UI
		JLabel label = new JLabel("��ǰ ����");
		label.setFont(new Font("����", Font.PLAIN, 15));
		label.setBounds(12, 10, 99, 23);
		panel.add(label);

		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBounds(12, 35, 206, 211);
		panel.add(panel_1);

		JLabel label_1 = new JLabel("�з�");
		label_1.setBounds(12, 63, 40, 15);
		panel_1.add(label_1);

		JLabel label_2 = new JLabel("��ǰ��");
		label_2.setBounds(12, 14, 40, 15);
		panel_1.add(label_2);

		JLabel label_3 = new JLabel("�԰����");
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

		JLabel label_4 = new JLabel("�԰���");
		label_4.setBounds(12, 88, 53, 15);
		panel_1.add(label_4);

		add_buyprice = new JLabel("0");
		add_buyprice.setHorizontalAlignment(SwingConstants.CENTER);
		add_buyprice.setBounds(78, 88, 116, 15);
		panel_1.add(add_buyprice);

		JLabel label_6 = new JLabel("�������");
		label_6.setBounds(12, 113, 53, 15);
		panel_1.add(label_6);

		add_count = new JLabel("0");
		add_count.setHorizontalAlignment(SwingConstants.CENTER);
		add_count.setBounds(78, 113, 116, 15);
		panel_1.add(add_count);

		add_submit = new JButton("��    ��");
		add_submit.setForeground(new Color(83, 78, 82));
		add_submit.setBackground(new Color(218, 246, 233));
		add_submit.setBounds(54, 171, 97, 23);
		panel_1.add(add_submit);

		add_group = new JLabel("");
		add_group.setHorizontalAlignment(SwingConstants.CENTER);
		add_group.setBounds(78, 63, 116, 15);
		panel_1.add(add_group);

		JLabel label_7 = new JLabel("��ǰ�ڵ�");
		label_7.setBounds(12, 39, 53, 15);
		panel_1.add(label_7);

		add_code = new JLabel("");
		add_code.setHorizontalAlignment(SwingConstants.CENTER);
		add_code.setBounds(78, 39, 116, 15);
		panel_1.add(add_code);

		add_close = new JButton("��    ��");
		add_close.setForeground(new Color(83, 78, 82));
		add_close.setBackground(new Color(218, 246, 233));
		add_close.setBounds(701, 255, 97, 23);
		panel.add(add_close);

		JLabel label_5 = new JLabel("�԰� ��Ȳ");
		label_5.setFont(new Font("����", Font.PLAIN, 15));
		label_5.setBounds(231, 10, 99, 23);
		panel.add(label_5);

		JLabel label_8 = new JLabel("�� ���Աݾ�");
		label_8.setFont(new Font("����", Font.PLAIN, 14));
		label_8.setBounds(231, 260, 89, 15);
		panel.add(label_8);

		add_pricesum = new JTextField();
		add_pricesum.setHorizontalAlignment(SwingConstants.RIGHT);
		add_pricesum.setEditable(false);
		add_pricesum.setBounds(313, 257, 116, 21);
		panel.add(add_pricesum);
		add_pricesum.setColumns(10);

		// ��ǰ���� �г� ������Ʈ�� �ʱⰪ
		add_code.setText(iv.rowData.get(0).get(5));
		add_group.setText(iv.rowData.get(0).get(6));
		add_buyprice.setText(iv.rowData.get(0).get(4));
		add_count.setText(iv.rowData.get(0).get(2));

		// ������ ����
		add_close.addActionListener(this);
		add_submit.addActionListener(this);
		add_buycount.addActionListener(this);
		add_name.addItemListener(this);

	}// ������

	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == add_close) { // �ݱ��ư
			dispose(); // â���ݴ´�
		} else if (ae.getSource() == add_submit || ae.getSource() == add_buycount) { // �԰��ư, �ؽ�Ʈ�ʵ�
			// ���� �ð� ���ڿ� ����
			cal = Calendar.getInstance();
			datestr = sdf.format(cal.getTime());
			System.out.println(datestr);

			// DB����
			try {
				Class.forName("com.mysql.cj.jdbc.Driver"); // mysql�� jdbc Driver ����
				System.err.println("JDBC-ODBC ����̹��� ���������� �ε���");
			} catch (ClassNotFoundException e) {
				System.err.println("����̹� �ε忡 �����߽��ϴ�.");
			}
			try { // mysql�� ���� student �����ͺ��̽��� �����ϱ�

				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/coinroom?serverTimezone=UTC",
						"root", "java2020");
				// System.out.println("DB ���� �Ϸ�.");
				Statement dbSt = con.createStatement();
				// System.out.println("JDBC ����̹��� ���������� ����Ǿ����ϴ�.");

				// ��ǰ���� �гο� ��µ� ���� �޾ƿ�
				code = add_code.getText();
				product_name = (String) add_name.getSelectedItem();
				group = add_group.getText();
				quantity = add_count.getText();
				buyprice = add_buyprice.getText();
				buycount = add_buycount.getText();

				// �Է°� ���ڿ��� �˻�
				try {
					num = Integer.parseInt(add_buycount.getText());

					// '��ǰ����' ������Ʈ ó�� �� DB�� �߰�
					sum = Integer.parseInt(add_count.getText()) + num;
					// �԰��� ���� ����
					price = Integer.parseInt(add_buyprice.getText()) * num;
					// ���Աݾ�
					pricesum += price;

					// ���õ� ��ǰ�� ��� ���� ������Ʈ
					String sql = "UPDATE inventory SET inventory_number = '" + sum + "' WHERE product_code = '" + code
							+ "';";
					dbSt.executeUpdate(sql); // DB�� �԰��� ���� ��� ���� ����
					add_count.setText(Integer.toString(sum)); // ������� ������Ʈ �ֽ�ȭ
					add_buycount.setText("");

					// �԰� ���̺� ����
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

					// �԰� ���� DB�� ���� ����
					String sql2 = "insert into buy_product(product_code, buy_date, buy_count) values('" + code + "', '"
							+ datestr + "', " + num + ");";
					dbSt.executeUpdate(sql2);

				} catch (NumberFormatException b) {
					JOptionPane.showMessageDialog(null, "���ڸ� �Է��ϼ���", "�˸�", JOptionPane.INFORMATION_MESSAGE);
				}

				// ������â ������Ʈ
				iv.Table_DBupdate();

				// �ؽ�Ʈ�ʵ� �ʱ�ȭ
				add_buycount.setText("");

				dbSt.close();
				con.close();

			} catch (SQLException e) {
				System.out.println("SQLException : " + e.getMessage());
			}
		} // if �԰��ư
	}// �׼��̺�Ʈ

	public void itemStateChanged(ItemEvent ie) { // �޺��ڽ� ��ǰ ���ý� �ش� ��ǰ�� ���� ���
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
	}// �޺��ڽ��̺�Ʈ
}// Ŭ����
