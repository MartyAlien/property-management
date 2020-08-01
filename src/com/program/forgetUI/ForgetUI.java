package com.program.forgetUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.program.daoimp.UserDAOImp;
import com.program.user.User;

public class ForgetUI extends JFrame{
	private static final long serialVersionUID = -12345678L;
	private JPanel jPanel01,jPanel02,jPanel03,jPanel04,jPanel07,jPanel08;
	private JTextField field;    //用戶名       
	private JTextField phoneField;     //手机
	private JTextField keyField;       //验证码      
	private JPasswordField passwordField1;    //新密码1
	private JPasswordField passwordField2;    //新密码2
	private JButton submitBtn,sendBtn;
	private Font font=new Font("微软雅黑",Font.BOLD,15);
	private Color color = new Color(191,230,240);
	private Font fontField=new Font("微软雅黑",Font.PLAIN,20);
	private User forgetUser=null;
	
	public ForgetUI() throws HeadlessException {
		super("找回密码");
		setBounds(400,200,500, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setLayout(null);
		getContentPane().setBackground(color);
		this.setRigistStyle();
		ForgetListener forgetListener = new ForgetListener(this,field,  phoneField, keyField,passwordField1, passwordField2,submitBtn, sendBtn);
		//submitBtn.addActionListener(forgetListener);
		submitBtn.addMouseListener(forgetListener);
		sendBtn.addActionListener(forgetListener);
		field.addKeyListener(forgetListener.getKeyAdapter());
		passwordField1.addKeyListener(forgetListener.getKeyAdapter());
		passwordField2.addKeyListener(forgetListener.getKeyAdapter());
		phoneField.addKeyListener(forgetListener.getKeyAdapter());
		keyField.addKeyListener(forgetListener.getKeyAdapter());
		setVisible(true);
	}
	
	private void setRigistStyle() {
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
		
		//新密码+密码框
		jPanel02=new JPanel();
		jPanel02.setLayout(null);
		jPanel02.setBackground(color);
		JLabel jLabel02=new JLabel("新密码");
		jLabel02.setBounds(100, 20, 60, 40);
		jLabel02.setFont(font);
		passwordField1=new JPasswordField(20);
		passwordField1.setBounds(170, 20, 200, 40);
		passwordField1.setFont(fontField);
		passwordField1.setBorder(null);
		jPanel02.add(jLabel02); 
		jPanel02.add(passwordField1);
		jPanel02.setBounds(0, 120, this.getWidth(), 50);
		this.add(jPanel02);
		
		// 二次输入密码+密码框
		jPanel08=new JPanel();
		jPanel08.setLayout(null);
		jPanel08.setBackground(color);
		JLabel jLabel08=new JLabel("确认密码");
		jLabel08.setBounds(100, 20, 60, 40);
		jLabel08.setFont(font);
		passwordField2=new JPasswordField(20);
		passwordField2.setBounds(170, 20, 200, 40);
		passwordField2.setFont(fontField);
		passwordField2.setBorder(null);
		jPanel08.add(jLabel08); 
		jPanel08.add(passwordField2);
		jPanel08.setBounds(0, 190, this.getWidth(), 50);
		this.add(jPanel08);
		
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
		jPanel03.setBounds(0, 260, this.getWidth(), 50);
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
		jPanel04.setBounds(0,330,this.getWidth(),50);
		this.add(jPanel04);
			
		// 提交按钮
		jPanel07=new JPanel();
		jPanel07.setLayout(null);
		jPanel07.setBackground(color);
		submitBtn=new JButton("完成");
		submitBtn.setFocusPainted(false);
		submitBtn.setBorderPainted(false);
		submitBtn.setBackground(new Color(0,90,171));
		submitBtn.setFont(new Font("幼圆",Font.PLAIN,17));
		submitBtn.setForeground(Color.white);
		submitBtn.setBounds(220, 0, 78, 35);
		jPanel07.add(submitBtn);
		jPanel07.setBounds(0, 410, this.getWidth(), 50);
		this.add(jPanel07);
	}
	
	public User getForgetUser() {
		return forgetUser;
	}

	public void setForgetUser(User forgetUser) {
		this.forgetUser = forgetUser;
	}
	
	public int loadToMysql(User user) throws SQLException {
		List<User> list=new ArrayList<User>();
		list.add(user);
		int insertNum = new UserDAOImp().insertObj(list);
		return insertNum;
	}
}
