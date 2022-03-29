package BusinessManagementCollection;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.table.*;

public class InventoryControl extends JFrame implements ActionListener, MouseListener {

	String t_id;
	JTextField search, inven_name, inven_code, inven_buyprice, inven_count, inven_saleprice;
	JTable inven_table;
	JComboBox inven_combo;
	JButton inven_addB, inven_newB, inven_changeB, inven_removeB, back;
	DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
	Vector<String> sGroup;
	Vector<String> columnName;
	Vector<Vector<String>> rowData;
	Vector<String> txt;
	DefaultTableModel model = null;
	JScrollPane tableSP;

	int num = 1, row; // ���̺� ������ȣ, ���콺�̺�Ʈ row��ȣ
	String product_name, quantity, saleprice, buyprice, code, group;
	// ��ǰ��, ����, �ǸŰ���, �԰���, ��ǰ�ڵ�, �з�

	Font f1 = new Font("���� ���", Font.PLAIN | Font.BOLD, 16);
	Font f2 = new Font("���� ���", Font.PLAIN | Font.BOLD, 14);

	public InventoryControl(String s_id) {

		t_id = s_id;
		setResizable(false);

		// �з� ������Ʈ ����
		sGroup = new Vector<String>();
		sGroup.add("����");
		sGroup.add("����");

		// ���̺� �̸���
		columnName = new Vector<String>();
		columnName.add("����");
		columnName.add("��ǰ��");
		columnName.add("����");
		columnName.add("�ǸŰ���");
		columnName.add("�԰���");
		columnName.add("��ǰ�ڵ�");
		columnName.add("�з�");

		// ���̺���
		rowData = new Vector<Vector<String>>();
		model = new DefaultTableModel(rowData, columnName) {
			public boolean isCellEditable(int rowIndex, int mColIndex) { // ���̺� ����Ŭ�� ��������
				return false;
			}
		};

		// DB
		Table_DBupdate(); // DB�� �о� JTable �ۼ��ϴ� �޼ҵ�

		// ���̺�
		inven_table = new JTable(model) {
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				// �з��� ���� �� ���� ����
				JComponent component = (JComponent) super.prepareRenderer(renderer, row, column);
				Color color = new Color(204, 255, 255);
				Color color2 = new Color(255, 255, 153);

				if (!isRowSelected(row)) { // �������� �ƴҶ�
					if (rowData.get(row).get(6).equals("����")) // rowData ���̺��� row���� 6��° cell�� "����"�ϰ��
						component.setBackground(color);
					else if (rowData.get(row).get(6).equals("����")) // �����ϰ��
						component.setBackground(color2);
				}
				return component;
			}
		};
		inven_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // ���߼��ñ���
		tableSP = new JScrollPane(inven_table);
		inven_table.getColumnModel().getColumn(0).setPreferredWidth(30);
		inven_table.getColumnModel().getColumn(2).setPreferredWidth(30);
		tableSP.setBounds(10, 10, 700, 580);

		inven_table.addMouseListener(this);

		getContentPane().add(tableSP);

		// UI
		getContentPane().setBackground(new Color(255, 227, 236));
		setTitle("������");
		getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(720, 10, 250, 500);
		panel.setBackground(new Color(255, 255, 255));
		panel.setForeground(Color.WHITE);
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		getContentPane().add(panel);
		panel.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBounds(10, 10, 230, 35);
		panel_1.setBackground(new Color(255, 255, 255));
		panel.add(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel label = new JLabel("��ǰ�˻�");
		label.setFont(f1);
		panel_1.add(label);

		search = new JTextField(13);
		panel_1.add(search);

		JLabel lblNewLabel = new JLabel("��ǰ ����");
		lblNewLabel.setFont(f1);
		lblNewLabel.setBounds(85, 65, 99, 23);
		panel.add(lblNewLabel);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_2.setBounds(10, 100, 230, 120);
		panel_2.setBackground(new Color(255, 255, 255));
		panel.add(panel_2);
		panel_2.setLayout(null);

		JLabel label_1 = new JLabel("�з�");
		label_1.setFont(f2);
		label_1.setBounds(12, 15, 40, 20);
		panel_2.add(label_1);

		JLabel label_2 = new JLabel("��ǰ��");
		label_2.setFont(f2);
		label_2.setBounds(12, 50, 60, 20);
		panel_2.add(label_2);

		inven_name = new JTextField();
		inven_name.setColumns(10);
		inven_name.setBounds(95, 50, 116, 21);
		panel_2.add(inven_name);

		JLabel label_3 = new JLabel("��ǰ�ڵ�");
		label_3.setFont(f2);
		label_3.setBounds(12, 85, 65, 20);
		panel_2.add(label_3);

		inven_code = new JTextField();
		inven_code.setEditable(false);
		inven_code.setColumns(10);
		inven_code.setBounds(95, 85, 116, 21);
		panel_2.add(inven_code);

		inven_combo = new JComboBox(sGroup);
		inven_combo.setBounds(95, 15, 116, 23);
		panel_2.add(inven_combo);

		JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_3.setBackground(new Color(255, 255, 255));
		panel_3.setBounds(10, 258, 230, 120);
		panel.add(panel_3);

		inven_buyprice = new JTextField();
		inven_buyprice.setColumns(10);
		inven_buyprice.setBounds(95, 50, 116, 21);
		panel_3.add(inven_buyprice);

		JLabel label_6 = new JLabel("����");
		label_6.setBounds(12, 85, 40, 20);
		panel_3.add(label_6);

		inven_count = new JTextField();
		inven_count.setEditable(false);
		inven_count.setColumns(10);
		inven_count.setBounds(95, 85, 116, 21);
		panel_3.add(inven_count);

		inven_saleprice = new JTextField();
		inven_saleprice.setColumns(10);
		inven_saleprice.setBounds(95, 15, 116, 21);
		panel_3.add(inven_saleprice);

		JLabel label_4 = new JLabel("�ǸŰ���");
		label_4.setFont(f2);
		label_4.setBounds(12, 15, 65, 20);
		panel_3.add(label_4);

		JLabel label_5 = new JLabel("�԰���");
		label_5.setFont(f2);
		label_5.setBounds(12, 50, 65, 20);
		panel_3.add(label_5);

		JLabel label_7 = new JLabel("���� ����");
		label_7.setFont(f1);
		label_7.setBounds(85, 225, 99, 23);
		panel.add(label_7);

		inven_addB = new JButton("��    ��");
		inven_addB.setForeground(new Color(83, 78, 82));
		inven_addB.setBackground(new Color(218, 246, 233));
		inven_addB.setBounds(10, 390, 110, 40);
		inven_addB.setFont(f1);
		panel.add(inven_addB);

		inven_newB = new JButton("��    ��");
		inven_newB.setForeground(new Color(83, 78, 82));
		inven_newB.setBackground(new Color(218, 246, 233));
		inven_newB.setBounds(130, 390, 110, 40);
		inven_newB.setFont(f1);
		panel.add(inven_newB);

		inven_changeB = new JButton("��    ��");
		inven_changeB.setForeground(new Color(83, 78, 82));
		inven_changeB.setBackground(new Color(218, 246, 233));
		inven_changeB.setBounds(10, 440, 110, 40);
		inven_changeB.setFont(f1);
		panel.add(inven_changeB);

		inven_removeB = new JButton("��    ��");
		inven_removeB.setForeground(new Color(83, 78, 82));
		inven_removeB.setBackground(new Color(218, 246, 233));
		inven_removeB.setBounds(130, 440, 110, 40);
		inven_removeB.setFont(f1);
		panel.add(inven_removeB);

		back = new JButton("�ڷΰ���");
		back.setForeground(new Color(83, 78, 82));
		back.setBackground(new Color(218, 246, 233));
		back.setBounds(860, 550, 110, 40);
		add(back);

		back.addActionListener(this);
		search.addActionListener(this);
		inven_addB.addActionListener(this);
		inven_newB.addActionListener(this);
		inven_changeB.addActionListener(this);
		inven_removeB.addActionListener(this);

	}// ������ ��

	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == back) {
			MainMenu win = new MainMenu("���θ޴�", t_id);
			win.setSize(600, 550);
			win.setLocation(680, 280);
			win.show();
			win.setResizable(false);
			win.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			dispose();
		}
		try { // mysql�� ���� student �����ͺ��̽��� �����ϱ�

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/coinroom?serverTimezone=UTC",
					"root", "java2020");
			System.out.println("DB ���� �Ϸ�.");
			Statement dbSt = con.createStatement();
			System.out.println("JDBC ����̹��� ���������� ����Ǿ����ϴ�.");

			if (ae.getSource() == search) { // ��ǰ�˻�
				String s = search.getText();
				for (int i = 0; i < inven_table.getRowCount(); i++)
					if (rowData.get(i).get(1).equals(s)) {
						inven_name.setText((String) model.getValueAt(i, 1));
						inven_code.setText((String) model.getValueAt(i, 5));
						inven_saleprice.setText((String) model.getValueAt(i, 3));
						inven_buyprice.setText((String) model.getValueAt(i, 4));
						inven_count.setText((String) model.getValueAt(i, 2));
						inven_combo.setSelectedItem((String) model.getValueAt(i, 6));
						inven_table.changeSelection(i, 1, false, false);
						break;
					}
				search.setText("");
			} else if (ae.getSource() == inven_changeB) { // ������ư
				product_name = inven_name.getText(); // �̸�
				quantity = inven_count.getText(); // ����
				saleprice = inven_saleprice.getText(); // �ǸŰ���
				buyprice = inven_buyprice.getText(); // �԰���
				code = inven_code.getText(); // �ڵ�
				group = (String) inven_combo.getSelectedItem(); // �з�

				String sql = "UPDATE inventory SET product_name = '" + product_name + "', classification = '" + group
						+ "', inventory_number = '" + quantity + "', sale_price = '" + saleprice + "', wear_price = '"
						+ buyprice + "' WHERE product_code = '" + code + "';";
				// �Էµ� ������ DB�� ������Ʈ
				dbSt.executeUpdate(sql);
				Table_DBupdate(); // JTable�� �����ۼ��Ѵ�
			} else if (ae.getSource() == inven_addB) { // �԰��ư
				AddInventory win = new AddInventory(this); // â����, ���� Ŭ���� �Ѱ���
				win.setTitle("�԰�");
				win.setSize(850, 350);
				win.setLocation(500, 300);
				win.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				win.show();
			} else if (ae.getSource() == inven_newB) { // �űԹ�ư
				NewInventory win = new NewInventory(this); // â����, ���� Ŭ���� �Ѱ���
				win.setTitle("�ű�");
				win.setSize(477, 208);
				win.setLocation(620, 300);
				win.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				win.show();
			} else if (ae.getSource() == inven_removeB) { // ������ư
				int r = JOptionPane.showConfirmDialog(null, "���� �����Ͻ� �ǰ���?", "���", JOptionPane.YES_NO_OPTION);
				if (r == 0) { // 'Ȯ��'�� ������ ���
					code = inven_code.getText(); // �ڵ�
					String sql = "DELETE FROM inventory WHERE product_code = '" + code + "';";
					dbSt.executeUpdate(sql);
					Table_DBupdate(); // JTable�� �����ۼ��Ѵ�
				}
			}

			dbSt.close();
			con.close();

		} catch (SQLException e) {
			System.out.println("SQLException : " + e.getMessage());
		}

	}

	public void Table_DBupdate() { // DB�� �о JTable�� �ۼ��ϴ� �޼ҵ�
		model.setNumRows(0); // JTable �ʱ�ȭ
		num = 1; // num 1�� �ʱ�ȭ
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

			String sql = "SELECT * FROM inventory;";
			ResultSet result = dbSt.executeQuery(sql);
			while (result.next()) {
				txt = new Vector<String>();
				txt.add(Integer.toString(num++));
				txt.add(result.getString("product_name"));
				txt.add(result.getString("inventory_number"));
				txt.add(result.getString("sale_price"));
				txt.add(result.getString("wear_price"));
				txt.add(result.getString("product_code"));
				txt.add(result.getString("classification"));
				rowData.add(txt);

			}
			dbSt.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("SQLException : " + e.getMessage());
		}
		System.out.println("������ ���̺� �ֽ�ȭ��");
	}

	// ���̺� ���콺 Ŭ�� �̺�Ʈ
	public void mouseReleased(MouseEvent ae) {
		row = inven_table.getSelectedRow();

		inven_name.setText((String) model.getValueAt(row, 1));
		inven_code.setText((String) model.getValueAt(row, 5));
		inven_saleprice.setText((String) model.getValueAt(row, 3));
		inven_buyprice.setText((String) model.getValueAt(row, 4));
		inven_count.setText((String) model.getValueAt(row, 2));
		inven_combo.setSelectedItem((String) model.getValueAt(row, 6));
	}

	public void mouseClicked(MouseEvent ae) {
	}

	public void mousePressed(MouseEvent ae) {
	}

	public void mouseEntered(MouseEvent ae) {
	}

	public void mouseExited(MouseEvent ae) {
	}
}// Ŭ���� ��
