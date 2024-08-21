// package Attendance;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class StudentView {

	Connection con = DB.getConnection();;
	JFrame frame = new JFrame();
	DefaultTableModel model = new DefaultTableModel();

	public void stView(int id) throws SQLException {

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

		// -----------------------LOGOUT-----------------------------
		JLabel back = new JLabel("< LOGOUT");
		back.setForeground(Color.decode("#37474F"));
		back.setFont(new Font("Times New Roman", Font.BOLD, 17));
		back.setBounds(18, 10, 100, 20);
		frame.add(back);
		back.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
				Login login = new Login();
        		login.loginView();
			}
		});
		// ----------------------------------------------------------

		// -----------------------MINIMIZE-----------------------------
		JLabel min = new JLabel("-");
		min.setForeground(Color.decode("#37474F"));
		min.setBounds(935, 8, 100, 20);
		min.setFont(new Font("Times New Roman", Font.BOLD, 25));
		frame.add(min);
		min.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.setState(JFrame.ICONIFIED);
			}
		});
		// -------------------------------------------------------------

		// ------------------Panel----------------------------------
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 1000, 35);
		panel.setBackground(Color.decode("#DEE4E7"));
		frame.add(panel);
		// ---------------------------------------------------------

		// -------------------Welcome---------------------------------
		JLabel welcome = new JLabel("Welcome " + getUser(id) + ",");
		welcome.setForeground(Color.decode("#DEE4E7"));
		welcome.setBounds(10, 50, 350, 20);
		welcome.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		frame.add(welcome);
		// -----------------------------------------------------------

		// ----------------TABLE---------------------------------
		JTable table = new JTable();
		model = (DefaultTableModel) table.getModel();
		model.addColumn("DATE");
		model.addColumn("STATUS");
		JScrollPane scPane = new JScrollPane(table);
		scPane.setBounds(500, 50, 480, 525);
		table.setFont(new Font("Times New Roman", Font.BOLD, 20));
		table.setRowHeight(50);
		frame.add(scPane);
		// ------------------------------------------------------

		// --------------------------INFO------------------------
		JLabel totalclass = new JLabel("TOTAL CLASSES : ");
		totalclass.setBounds(25, 180, 250, 20);
		totalclass.setForeground(Color.decode("#DEE4E7"));
		totalclass.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		frame.add(totalclass);
		JLabel ttbox = new JLabel("");
		ttbox.setBounds(60, 230, 250, 20);
		ttbox.setForeground(Color.decode("#DEE4E7"));
		ttbox.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		frame.add(ttbox);
		JLabel classAtt = new JLabel("CLASSES ATTENDED : ");
		classAtt.setBounds(25, 280, 250, 20);
		classAtt.setForeground(Color.decode("#DEE4E7"));
		classAtt.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		frame.add(classAtt);
		JLabel atbox = new JLabel("");
		atbox.setBounds(60, 330, 250, 20);
		atbox.setForeground(Color.decode("#DEE4E7"));
		atbox.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		frame.add(atbox);
		JLabel classAbs = new JLabel("CLASSES MISSED : ");
		classAbs.setBounds(25, 380, 250, 20);
		classAbs.setForeground(Color.decode("#DEE4E7"));
		classAbs.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		frame.add(classAbs);
		JLabel mtbox = new JLabel("");
		mtbox.setBounds(60, 430, 250, 20);
		mtbox.setForeground(Color.decode("#DEE4E7"));
		mtbox.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		frame.add(mtbox);
		JLabel AttPer = new JLabel("ATTENDANCE PERCENTAGE : ");
		AttPer.setBounds(25, 480, 300, 20);
		AttPer.setForeground(Color.decode("#DEE4E7"));
		AttPer.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		frame.add(AttPer);
		JLabel prbox = new JLabel("");
		prbox.setBounds(60, 530, 250, 20);
		prbox.setForeground(Color.decode("#DEE4E7"));
		prbox.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		frame.add(prbox);
		// ------------------------------------------------------

		// ----------------------SETVALUES---------------------------
		int[] arr = stat(id);
		ttbox.setText(String.valueOf(arr[0]));
		atbox.setText(String.valueOf(arr[1]));
		mtbox.setText(String.valueOf(arr[2]));
		prbox.setText(String.valueOf(arr[3]) + "%");
		// ----------------------------------------------------------

		// -------------------------------------------------------
		frame.setSize(1000, 600);
		frame.setResizable(false);
		frame.setLayout(null);
		frame.setUndecorated(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setFocusable(true);
		frame.getContentPane().setBackground(Color.decode("#37474F"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// --------------------------------------------------------------
	}

	public String getUser(int id) throws SQLException {
		String str = "SELECT name FROM user WHERE id = " + id;
		Statement stm = con.createStatement();
		ResultSet rst = stm.executeQuery(str);
		rst.next();
		return rst.getString("name");
	}

	public void tblupdt(int id) {
		try {
			ResultSet res = dbSearch(id);
			for (int i = 0; res.next(); i++) {
				model.addRow(new Object[0]);
				model.setValueAt(res.getString("dt"), i, 0);
				model.setValueAt(res.getString("status"), i, 1);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	public int[] stat(int id) throws SQLException {
		String str = "SELECT COUNT(*) AS pre FROM attend WHERE stid = " + id + " AND status = 'Present'";
		String str2 = "SELECT COUNT(*) AS abs FROM attend WHERE stid = " + id + " AND status = 'Absent'";
		int[] x = new int[4];
		Statement stm = con.createStatement();
		ResultSet rst = stm.executeQuery(str);
		rst.next();
		x[1] = rst.getInt("pre");
		rst = stm.executeQuery(str2);
		rst.next();
		x[2] = rst.getInt("abs");
		x[0] = x[1] + x[2];
		
		// Check if x[0] is zero before calculating the percentage
		if (x[0] != 0) {
			x[3] = (x[1] * 100) / x[0];
		} else {
			x[3] = 0; // Handle the case where there are no attendance records
		}
		
		tblupdt(id);
		return x;
	}
	

	public ResultSet dbSearch(int id) throws SQLException {
		String str1 = "SELECT * from attend where stid = " + id + " ORDER BY dt asc";
		Statement stm = con.createStatement();
		return stm.executeQuery(str1);			
	}
}