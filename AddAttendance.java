import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AddAttendance {
	Connection con = DB.getConnection();
	DefaultTableModel model = new DefaultTableModel();

	public void addView() throws SQLException {
		JFrame frame = new JFrame();
		Font text = new Font("Times New Roman", Font.PLAIN, 18);
		Font btn = new Font("Times New Roman", Font.BOLD, 20);

		// ------------------------CLOSE---------------------------
		JLabel x = new JLabel("X");
		x.setForeground(Color.decode("#37474F"));
		x.setBounds(965, 10, 100, 20);
		x.setFont(new Font("Times New Roman", Font.BOLD, 20));
		frame.add(x);
		x.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		// ----------------------------------------------------------

		// -----------------------BACK---------------------------------
		JLabel back = new JLabel("< BACK");
		back.setForeground(Color.decode("#37474F"));
		back.setFont(new Font("Times New Roman", Font.BOLD, 17));
		back.setBounds(18, 10, 100, 20);
		frame.add(back);
		back.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
			}
		});
		// --------------------------------------------------------------

		// ------------------Panel----------------------------------
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 1000, 35);
		panel.setBackground(Color.decode("#DEE4E7"));
		frame.add(panel);
		// ---------------------------------------------------------

		// ----------------TABLE---------------------------------
		// @SuppressWarnings("serial")
		JTable table = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		model = (DefaultTableModel) table.getModel();
		model.addColumn("ID");
		model.addColumn("NAME");
		model.addColumn("STATUS");
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(200);
		table.getColumnModel().getColumn(2).setPreferredWidth(200);
		JScrollPane scPane = new JScrollPane(table);
		scPane.setBounds(500, 50, 480, 525);
		frame.add(scPane);
		// ------------------------------------------------------------

		// ---------------------ALREADY ADDED------------------------
		JLabel txt = new JLabel("");
		txt.setFont(text);
		txt.setBounds(125, 525, 350, 20);
		txt.setForeground(Color.red);
		frame.add(txt);
		// -------------------------------------------------------

		// --------------------CLASS---------------------------------
		JLabel classes = new JLabel("CLASS : ");
		classes.setFont(text);
		classes.setBounds(25, 150, 100, 20);
		classes.setForeground(Color.decode("#DEE4E7"));
		frame.add(classes);
		JComboBox<String> clss = new JComboBox<>(classEt());
		clss.setBounds(110, 150, 70, 25);
		frame.add(clss);
		// ------------------------------------------------------

		// -------------------------DATE-------------------------
		JLabel dt = new JLabel("DATE : ");
		dt.setFont(text);
		dt.setBounds(25, 60, 75, 20);
		dt.setForeground(Color.decode("#DEE4E7"));
		frame.add(dt);
		JTextField dtbox = new JTextField();
		dtbox.setBounds(100, 60, 150, 25);
		dtbox.setBackground(Color.decode("#DEE4E7"));
		dtbox.setFont(text);
		dtbox.setForeground(Color.decode("#37474F"));
		String dateInString = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		dtbox.setText(dateInString);
		frame.add(dtbox);
		dtbox.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				try {
					Date currentDate = new Date();
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					Date enteredDate = dateFormat.parse(dtbox.getText());
					if (enteredDate.after(currentDate)) {
						dtbox.setText(dateFormat.format(currentDate));
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		// -------------------------------------------------------------

		// ----------------------VIEWBUTTON-----------------------
		JButton view = new JButton("VIEW");
		view.setBounds(175, 275, 150, 50);
		view.setFont(btn);
		view.setBackground(Color.decode("#DEE4E7"));
		view.setForeground(Color.decode("#37474F"));
		frame.add(view);
		view.addActionListener(e -> {
			try {
				if (check(String.valueOf(clss.getSelectedItem()), dtbox.getText())) {
					txt.setText("Attendance Already marked!!!");
					clearTable();
				} else {
					txt.setText("");
					tblupdt(String.valueOf(clss.getSelectedItem()));
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		// -------------------------------------------------------

		// ----------------------ABSENTBUTTON-----------------------
		JButton ab = new JButton("ABSENT");
		ab.setBounds(75, 365, 150, 50);
		ab.setFont(btn);
		ab.setBackground(Color.decode("#DEE4E7"));
		ab.setForeground(Color.decode("#37474F"));
		frame.add(ab);
		ab.addActionListener(e -> table.setValueAt("Absent", table.getSelectedRow(), 2));
		// -------------------------------------------------------

		// ----------------------PRESENTBUTTON-----------------------
		JButton pre = new JButton("PRESENT");
		pre.setBounds(275, 365, 150, 50);
		pre.setFont(btn);
		pre.setBackground(Color.decode("#DEE4E7"));
		pre.setForeground(Color.decode("#37474F"));
		frame.add(pre);
		pre.addActionListener(e -> {
			System.out.print(table.getSelectedRow());
			table.setValueAt("Present", table.getSelectedRow(), 2);
		});
		// -------------------------------------------------------

		// ----------------------SUBMITBUTTON-----------------------
		JButton sbmt = new JButton("SUBMIT");
		sbmt.setBounds(175, 450, 150, 50);
		sbmt.setFont(btn);
		sbmt.setBackground(Color.decode("#DEE4E7"));
		sbmt.setForeground(Color.decode("#37474F"));
		frame.add(sbmt);
		sbmt.addActionListener(e -> {
			for (int i = 0; i < table.getRowCount(); i++) {
				try {
					addItem(Integer.parseInt(String.valueOf(table.getValueAt(i, 0))),
							String.valueOf(table.getValueAt(i, 2)), dtbox.getText(),
							String.valueOf(clss.getSelectedItem()));
				} catch (NumberFormatException | SQLException e1) {
					e1.printStackTrace();
				}
			}
			for (int i = 0; i < model.getRowCount(); i++) {
				model.removeRow(i);
				model.setRowCount(0);
			}
		});
		// -------------------------------------------------------

		// -------------------------------------------------------
		frame.setSize(1000, 600);
		frame.setResizable(false);
		frame.setLayout(null);
		frame.setUndecorated(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setFocusable(true);
		frame.getContentPane().setBackground(Color.decode("#37474F"));
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		// --------------------------------------------------------------
	}

	public ResultSet dbSearch(String classes) throws SQLException {
		String str1 = "SELECT * from students where class = '" + classes + "'";
		Statement stm = con.createStatement();
		return stm.executeQuery(str1);
	}

	public String[] classEt() throws SQLException {
		String str1 = "SELECT name from class";
		Statement stm = con.createStatement();
		ResultSet rst = stm.executeQuery(str1);
		String[] rt = new String[25];
		int i = 0;
		while (rst.next()) {
			rt[i] = rst.getString("name");
			i++;
		}
		return rt;
	}

	public void tblupdt(String classes) {
		// for (int i = 0; i < model.getRowCount(); i++) {
		// 	model.removeRow(i);
		// 	model.setRowCount(0);
		// }
		clearTable();
		try {
			ResultSet res = dbSearch(classes);
			int rowCount = 0;
			while (res.next()) {
				model.addRow(new Object[0]);
				model.setValueAt(res.getInt("id"), rowCount, 0);
				model.setValueAt(res.getString("name"), rowCount, 1);
				model.setValueAt("Present", rowCount, 2);
				rowCount++;
			}
			res.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	private void clearTable() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
        }
        model.setRowCount(0);
    }

	public void addItem(int id, String status, String date, String classes) throws SQLException {
		String adding = "INSERT INTO attend values(" + id + ", '" + date + "', '" + status + "', '" + classes + "')";
		Statement stm = con.createStatement();
		stm.executeUpdate(adding);
	}

	public boolean check(String classes, String dt) throws SQLException {
		String str1 = "select * from attend where class = '" + classes + "' AND dt = '" + dt + "'";
		Statement stm = con.createStatement();
		ResultSet rst = stm.executeQuery(str1);
		return rst.next();
	}
}