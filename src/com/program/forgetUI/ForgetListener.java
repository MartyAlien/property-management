package com.program.forgetUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.program.check.PhoneCode;
import com.program.daoimp.UserDAOImp;
import com.program.user.User;

public class ForgetListener extends MouseAdapter implements ActionListener{
	private ForgetUI ForgetUI;
	private JTextField field;
	private JTextField phoneField;
	private JTextField keyField;
	private JPasswordField passwordField1;
	private JPasswordField passwordField2;
	private JButton submitBtn,sendBtn;
	private static String codeString="";
	String regexCh = "^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9])|(19[0-9]))\\d{8}$";
	Timer timer=new Timer();
	

	public ForgetListener(ForgetUI forgetUI,JTextField field, JTextField phoneField, JTextField keyField,
			JPasswordField passwordField1, JPasswordField passwordField2, JButton submitBtn,
			JButton sendBtn) {
		super();
		this.ForgetUI = forgetUI;
		this.field = field;
		this.phoneField = phoneField;
		this.keyField = keyField;
		this.passwordField1 = passwordField1;
		this.passwordField2 = passwordField2;
		this.submitBtn = submitBtn;
		this.sendBtn = sendBtn;
	}

	
	@Override
	public void mouseClicked(MouseEvent e) {
		checkRight();
	}


	private void checkRight() {
		String userNameString = field.getText();
		String pwdString1 = new String (passwordField1.getPassword());
		String pwdString2 = new String (passwordField2.getPassword());
		String phoneString = phoneField.getText();  
		String keyString=keyField.getText();
		if(userNameString.length()*pwdString1.length()*pwdString2.length()*phoneString.length()*keyString.length()==0) {
			JOptionPane.showMessageDialog(null, "请将所有信息完善", "找回失败", JOptionPane.ERROR_MESSAGE);
		}else {
			if(pwdString1.equals(pwdString2)) {
				if(codeString.length()!=0) {
					if(codeString.equals(keyString)) {
						List<User> selectOne = new UserDAOImp().selectOne(userNameString, phoneString);
						if(selectOne.size()>0) {
							int flag = new UserDAOImp().updateForForget(userNameString, phoneString, pwdString2);
							if(flag<=0) {
								JOptionPane.showMessageDialog(null, "数据库异常", "找回失败", JOptionPane.ERROR_MESSAGE);
							}else {
								JOptionPane.showMessageDialog(null, "密码找回成功，请尝试重新登录", "成功找回", JOptionPane.PLAIN_MESSAGE);
								ForgetUI.dispose();
							}
						}else {
							JOptionPane.showMessageDialog(null, "用户不存在或者此用户预留手机号码不正确", "找回失败", JOptionPane.ERROR_MESSAGE);
						}
					}else {
						JOptionPane.showMessageDialog(null, "验证码错误或者无效", "找回失败", JOptionPane.ERROR_MESSAGE);
					}
				}else {
					JOptionPane.showMessageDialog(null, "请先获取验证码", "警告", JOptionPane.WARNING_MESSAGE);
				}
			}else {
				JOptionPane.showMessageDialog(null, "两次输入密码不一致", "找回失败", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==sendBtn) {
			codeCheck();
		}
		if(e.getSource()==submitBtn) {
			checkRight();
		}
	}

	private void codeCheck() {
		if(phoneField.getText().matches(regexCh)) {
			timer.schedule(new TimerTask() {
				int i = 60;
				@Override
				public void run() {
					sendBtn.setForeground(Color.white);
					sendBtn.setBackground(Color.DARK_GRAY);
					sendBtn.setEnabled(false);
					i--;
					sendBtn.setText(i + "s");
					//sendBtn.setHorizontalTextPosition(JButton.CENTER);
					if (i <= 0) {
						//设置按钮可点击 并且停止任务
						sendBtn.setEnabled(true);
						sendBtn.setText("验证码");
						//sendBtn.setHorizontalTextPosition(JButton.CENTER);
						timer.cancel();
					}
				}
			}, 0, 1000);
			//验证手机号
			String phonemsg = PhoneCode.getPhonemsg(phoneField.getText(),0);
			codeString=PhoneCode.codeNum;
			if(!phonemsg.equals("true")) {
				JOptionPane.showMessageDialog(null, phonemsg);
			}
		}else {
			JOptionPane.showMessageDialog(null,"电话号码不合法！","警告",JOptionPane.WARNING_MESSAGE);
		}
	}
	
	
	public KeyAdapter getKeyAdapter() {
		KeyAdapter keyAdapter=new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				int keyChar=e.getKeyChar();
				if(e.getSource()==field|e.getSource()==passwordField1){
					if(!(new String(((char)keyChar)+"").matches("^[A-Za-z0-9]+$"))){
						e.consume();
					}
				}
				if(e.getSource()==phoneField|e.getSource()==keyField){
					if(!(new String(((char)keyChar)+"").matches("^[0-9]+$"))){
						e.consume();
					}
				}
				
			}
		};
		return keyAdapter;
	}

}
