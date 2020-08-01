package com.program.registUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.program.daoimp.UserDAOImp;
import com.program.user.User;

public class RegistUI extends JFrame{
	private static final long serialVersionUID = -18044620L;
	private JPanel jPanel01,jPanel02,jPanel03,jPanel04,jPanel05,jPanel06,jPanel07,jPanel08;
	private JTextField field;
	private JTextField phoneField;
	private JTextField keyField;
	private JTextField nameField;
	private JPasswordField passwordField;
	private JTextArea textArea;
	private JScrollPane jScrollPane;
	private JButton submitBtn,sendBtn;
	private JComboBox< String > box;
	private Font font=new Font("微软雅黑",Font.BOLD,15);
	private Color color = new Color(191,230,240);
	private Font fontField=new Font("微软雅黑",Font.PLAIN,20);
	private User registUser=null;
	
	
	public RegistUI() throws HeadlessException {
		super("用户注册");
		setBounds(400,200,500, 535);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(null);
		getContentPane().setBackground(color);
		this.setRigistStyle();
		RegistListener registListener = new RegistListener(this, nameField,field, phoneField, keyField, passwordField, textArea, submitBtn, sendBtn, box);
		submitBtn.addMouseListener(registListener);
		sendBtn.addActionListener(registListener);
		field.addKeyListener(registListener.getKeyAdapter());
		passwordField.addKeyListener(registListener.getKeyAdapter());
		phoneField.addKeyListener(registListener.getKeyAdapter());
		keyField.addKeyListener(registListener.getKeyAdapter());
		setVisible(true);
	}
	
	private void setRigistStyle() {
		// 姓名+文本框
		jPanel08=new JPanel();
		jPanel08.setLayout(null);
		jPanel08.setBackground(color);
		JLabel jLabel08=new JLabel("姓名");
		jLabel08.setBounds(100, 20, 60, 40);
		jLabel08.setFont(font);
		nameField=new JTextField();
		nameField.setBounds(170, 20, 200, 40);
		nameField.setFont(fontField);
		nameField.setBorder(null);
		jPanel08.add(jLabel08);
		jPanel08.add(nameField);
		jPanel08.setBounds(0, 0, this.getWidth(), 50);
		this.add(jPanel08);
		
		// 用户名+文本框
		jPanel01=new JPanel();
		jPanel01.setLayout(null);
		jPanel01.setBackground(color);
		JLabel jLabel01=new JLabel("用户名");
		jLabel01.setBounds(100, 20, 60, 40);
		jLabel01.setFont(font);
		field=new JTextField(20);
		field.setBounds(170, 20, 200, 40);
		field.setFont(fontField);
		field.setBorder(null);
		jPanel01.add(jLabel01);
		jPanel01.add(field);
		jPanel01.setBounds(0, 50, this.getWidth(), 50);
		this.add(jPanel01);
		
		// 密码+密码框
		jPanel02=new JPanel();
		jPanel02.setLayout(null);
		jPanel02.setBackground(color);
		JLabel jLabel02=new JLabel("密码");
		jLabel02.setBounds(100, 20, 60, 40);
		jLabel02.setFont(font);
		passwordField=new JPasswordField(20);
		passwordField.setBounds(170, 20, 200, 40);
		passwordField.setFont(fontField);
		passwordField.setBorder(null);
		jPanel02.add(jLabel02); 
		jPanel02.add(passwordField);
		jPanel02.setBounds(0, 100, this.getWidth(), 50);
		this.add(jPanel02);
		
		// 手机号+文本框
		jPanel03=new JPanel();
		jPanel03.setLayout(null);
		jPanel03.setBackground(color);
		JLabel jLabel03=new JLabel("手机号");
		jLabel03.setBounds(100, 20, 60, 30);
		jLabel03.setFont(font);
		phoneField=new JTextField();
		phoneField.setBounds(170, 20, 200, 40);
		phoneField.setFont(fontField);
		phoneField.setBorder(null);
		
		jPanel03.add(jLabel03);
		jPanel03.add(phoneField);
		jPanel03.setBounds(0, 150, this.getWidth(), 50);
		this.add(jPanel03);
		
		// 验证码+文本框
		jPanel04=new JPanel();
		jPanel04.setLayout(null);
		jPanel04.setBackground(color);
		JLabel jLabel04=new JLabel("验证码");
		jLabel04.setBounds(100, 15, 60, 40);
		jLabel04.setFont(font);
		keyField=new JTextField();
		keyField.setBounds(170, 15, 100, 40);
		keyField.setFont(fontField);
		keyField.setBorder(null);
		sendBtn=new JButton();
		sendBtn.setText("验证码");
		sendBtn.setHorizontalTextPosition(JButton.CENTER);
		sendBtn.setFocusPainted(false);
		sendBtn.setBorderPainted(false);
		sendBtn.setBackground(new Color(0,90,171));
		sendBtn.setFont(new Font("幼圆",Font.PLAIN,14));
		sendBtn.setForeground(Color.white);
		sendBtn.setBounds(290, 15, 80, 40);
		
		jPanel04.add(jLabel04);
		jPanel04.add(keyField);
		jPanel04.add(sendBtn);
		jPanel04.setBounds(0,200,this.getWidth(),50);
		this.add(jPanel04);
		
		// 学历+下拉列表
		jPanel05=new JPanel(null);
		jPanel05.setBackground(color);
		JLabel jLabel=new JLabel("类型");
		jLabel.setBounds(100, 15, 60, 40);
		jLabel.setFont(font);
		box=new JComboBox<String>();
		box.addItem("常住");
		box.addItem("租户");
		box.setSelectedIndex(0);
		box.setFont(new Font("幼圆", Font.PLAIN, 17));
		box.setBounds(170, 25, 200, 30);
		jPanel05.add(jLabel);
		jPanel05.add(box);
		jPanel05.setBounds(0, 250, this.getWidth(), 50);
		this.add(jPanel05);
		
		// 自我介绍+文本域
		jPanel06=new JPanel();
		jPanel06.setLayout(null);
		jPanel06.setBackground(color);
		JLabel jLabel05=new JLabel("门牌信息");
		jLabel05.setBounds(100, 15, 60, 40);
		jLabel05.setFont(font);
		textArea=new JTextArea();
		textArea.setLineWrap(true);
		textArea.setFont(fontField);
		textArea.setBorder(null);
		jScrollPane=new JScrollPane(textArea);
		jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		jScrollPane.setBounds(170, 25, 200, 80);
		jPanel06.add(jLabel05);
		jPanel06.add(jScrollPane);
		jPanel06.setBounds(0, 300, this.getWidth(), 130);
		this.add(jPanel06);	
		
		// 提交按钮
		jPanel07=new JPanel();
		jPanel07.setLayout(null);
		jPanel07.setBackground(color);
		submitBtn=new JButton("注 册");
		submitBtn.setFocusPainted(false);
		submitBtn.setBorderPainted(false);
		submitBtn.setBackground(new Color(0,90,171));
		submitBtn.setFont(new Font("幼圆",Font.PLAIN,17));
		submitBtn.setForeground(Color.white);
		submitBtn.setBounds(220, 0, 78, 35);
		jPanel07.add(submitBtn);
		jPanel07.setBounds(0, 450, this.getWidth(), 50);
		this.add(jPanel07);
	}
	
	public User getRegistUser() {
		return registUser;
	}

	public void setRegistUser(User registUser) {
		this.registUser = registUser;
	}
	
	public int loadToMysql(User user) throws SQLException {
		List<User> list=new ArrayList<User>();
		list.add(user);
		int insertNum = new UserDAOImp().insertObj(list);
		return insertNum;
	}
}
