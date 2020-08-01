package com.program.loginUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginUI extends JFrame{
	private static final long serialVersionUID = -1804462002L;
	private JTextField userNameField;
	private JPasswordField passwordField;
	private JComboBox<String> box;
	private JButton logButton,minButton,closeButton;
	private JPanel headPanel ;
	private JPanel centerPanel;
	private JPanel floorJPanel;
	private JButton registButton;
	private JButton forgetButton;
	private LoginListener loginListener;
	

	public LoginUI() throws HeadlessException {
		setIconImage(new ImageIcon("mstUI_img/logTitleIcon.png").getImage());
		setUndecorated(true);
		setSize(600,600);
		setResizable(false);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		getContentPane().setBackground(new Color(190,230,240));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(this.bgHead(),BorderLayout.PAGE_START);
		add(this.floorBtn(),BorderLayout.PAGE_END);
		add(this.cenPanel(),BorderLayout.CENTER);
	    // 实例化自定义监听器
	    loginListener=new LoginListener(this,userNameField, passwordField, box, logButton,
	    		minButton, closeButton, headPanel, registButton, forgetButton);
	    
	    // 注册监听
	    logButton.addActionListener(loginListener);
	    minButton.addActionListener(loginListener);
	    closeButton.addActionListener(loginListener);
	    registButton.addActionListener(loginListener);
	    forgetButton.addActionListener(loginListener);
	    getRootPane().setDefaultButton(logButton);// 设置窗体默认按钮：通过回车直接触发按钮
		setVisible(true); 
	}
	
	private JPanel bgHead() {
		headPanel =new JPanel(null);
		headPanel.setPreferredSize(new Dimension(600, 315));
		
		minButton = new JButton();
		closeButton = new JButton();
		// 设置按钮格式
		minButton.setBounds(488, 0, 54, 50);
		closeButton.setBounds(542, 0, 58, 50);
		minButton.setFocusPainted(false);
		closeButton.setFocusPainted(false);
		minButton.setBorderPainted(false);
		closeButton.setBorderPainted(false);
		minButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		minButton.setIcon(new ImageIcon("login_img/min.png"));
		minButton.setRolloverIcon(new ImageIcon("login_img/min2.png"));
		closeButton.setIcon(new ImageIcon("login_img/close.png"));
		closeButton.setRolloverIcon(new ImageIcon("login_img/close2.png"));
		closeButton.setPressedIcon(new ImageIcon("login_img/close3.png"));
		//设置背景
		JLabel bgImgJLabel=new JLabel(new ImageIcon("login_img/bg.gif"), JLabel.CENTER);
		bgImgJLabel.setBounds(0, 0, 600, 315);
		headPanel.add(minButton);
		headPanel.add(closeButton);
		headPanel.add(bgImgJLabel);
		return headPanel;
	}
	
	private JPanel cenPanel() {
		centerPanel=new JPanel(null);
		centerPanel.setPreferredSize(new Dimension(600,0));
		centerPanel.setBackground(new Color(190,230,240));
		Font font=new Font("微软雅黑",Font.BOLD,24);
		// 账号+输入框
		JLabel jLabel=new JLabel("账  号");
		userNameField=new JTextField();
		jLabel.setBounds(170, 0, 65, 60);
		userNameField.setBounds(238, 10, 220, 40);
		jLabel.setFont(font);
		userNameField.setFont(font);
		centerPanel.add(jLabel);
		centerPanel.add(userNameField);
		
		// 密码+密码框
		JLabel jLabel2=new JLabel("密  码");
		passwordField=new JPasswordField();
		jLabel2.setBounds(170,70,65,60);
		passwordField.setBounds(238, 80, 220, 40);
		jLabel2.setFont(font);
		passwordField.setFont(font);
		centerPanel.add(jLabel2);
		centerPanel.add(passwordField);
		
		// 登录模式
		JLabel jLabel3=new JLabel("登录模式");
		box=new JComboBox<String>();
		box.addItem("居民模式");
		box.addItem("物管模式");
		jLabel3.setBounds(170, 140, 154, 60);
		box.setBounds(300, 150, 124, 40);
		jLabel3.setFont(font);
		box.setFont(new Font("幼圆",Font.PLAIN,17));
		centerPanel.add(jLabel3);
		centerPanel.add(box);
		return centerPanel;
	}
	
	private JPanel floorBtn() {
		floorJPanel=new JPanel(null);
		floorJPanel.setPreferredSize(new Dimension(600,75));
		floorJPanel.setBackground(new Color(190,230,240));
		logButton=new JButton();
		// 设置按钮格式
		logButton.setBounds(216, 3, 168, 68);
		logButton.setToolTipText("Enter可触发登录哦!");
		logButton.setFocusPainted(false);
		logButton.setBorderPainted(false);
		logButton.setBackground(null);
		logButton.setIcon(new ImageIcon("login_img/oldBtn.png"));
		logButton.setPressedIcon(new ImageIcon("login_img/pressedBtn.png"));
		logButton.setRolloverIcon(new ImageIcon("login_img/rolloverBtn.png"));
		floorJPanel.add(logButton);
		
		forgetButton=new JButton();
		forgetButton.setToolTipText("忘记密码,快捷键Alt+F");
		forgetButton.setFocusPainted(false);
		forgetButton.setBorderPainted(false);
		forgetButton.setBackground(null);
		forgetButton.setIcon(new ImageIcon("login_img/forget2.png"));
		forgetButton.setPressedIcon(new ImageIcon("login_img/forget3.png"));
		forgetButton.setRolloverIcon(new ImageIcon("login_img/forget.png"));
		forgetButton.setBounds(10, 20, 50, 50);
		forgetButton.setMnemonic(KeyEvent.VK_F);
		floorJPanel.add(forgetButton);
		
		
		registButton=new JButton();
		registButton.setToolTipText("注册账户,快捷键Alt+R");
		registButton.setFocusPainted(false);
		registButton.setBorderPainted(false);
		registButton.setBackground(null);
		registButton.setIcon(new ImageIcon("login_img/oldRegist.png"));
		registButton.setRolloverIcon(new ImageIcon("login_img/rolloverRegist.png"));
		registButton.setPressedIcon(new ImageIcon("login_img/pressedRegist.png"));
		registButton.setBounds(540, 20, 50, 50);
		registButton.setMnemonic(KeyEvent.VK_R);
		floorJPanel.add(registButton);
		
		return floorJPanel;
	}
}
