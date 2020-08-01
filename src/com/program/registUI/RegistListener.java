package com.program.registUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.program.check.PhoneCode;
import com.program.user.User;

public class RegistListener extends MouseAdapter implements ActionListener{
	private RegistUI registUI;
	private JTextField nameField;
	private JTextField field;
	private JTextField phoneField;
	private JTextField keyField;
	private JPasswordField passwordField;
	private JTextArea textArea;
	@SuppressWarnings("unused") // 暂时去掉小黄警告
	private JButton submitBtn,sendBtn;
	private JComboBox< String > box;
	private User registUser;
	private static String codeString="";
	String regexCh = "^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9])|(19[0-9]))\\d{8}$";
	Timer timer=new Timer();

	public RegistListener(RegistUI registUI, JTextField nameField,JTextField field, JTextField phoneField, JTextField keyField,
			JPasswordField passwordField, JTextArea textArea, JButton submitBtn,
			JButton sendBtn, JComboBox<String> box) {
		super();
		this.registUI = registUI;
		this.nameField=nameField;
		this.field = field;
		this.phoneField = phoneField;
		this.keyField = keyField;
		this.passwordField = passwordField;
		this.textArea = textArea;
		this.submitBtn = submitBtn;
		this.sendBtn = sendBtn;
		this.box = box;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		String userNameString = field.getText();
		String pwdString = new String (passwordField.getPassword());
		String nameString = nameField.getText();
		String phoneString = phoneField.getText();
		String userTypeString = box.getSelectedItem().toString();
		String infoString = textArea.getText();
		if(userNameString.length()*pwdString.length()*nameString.length()*phoneString.length()*userTypeString.length()==0) {
			JOptionPane.showMessageDialog(null, "请将所有信息完善", "注册失败", JOptionPane.ERROR_MESSAGE);
		}else {
			if(codeString.equals(keyField.getText())&codeString.length()!=0) {
				registUser=new User(userNameString, pwdString, nameString, phoneString, userTypeString, 0, infoString, "0");
				registUI.setRegistUser(registUser);
				//System.out.println(registUI.getRegistUser());
				//验证成功后，验证码失效
				codeString="";
				// 上传数据库
				int loadCount=0;
				try {
					loadCount = registUI.loadToMysql(registUser);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(loadCount!=0&loadCount!=-1) {
					JOptionPane.showMessageDialog(null, "注册成功");
				}else if(loadCount==-1) {
					JOptionPane.showMessageDialog(null, "账户已存在", "警告", JOptionPane.WARNING_MESSAGE);
				}
				registUI.dispose();
			}else {
				JOptionPane.showMessageDialog(null, "验证码为空或者无效", "注册失败", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==sendBtn) {
			// 点击注册按钮验证手机号
			codeCheck();
		}
	}

	private void codeCheck() {
		if(phoneField.getText().matches(regexCh)) {
			timer.schedule(new TimerTask() {
				int i = 60;
				@Override
				public void run() {
					sendBtn.setEnabled(false);
					i--;
					sendBtn.setText(i + "s");
					sendBtn.setForeground(Color.white);
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
				if(e.getSource()==field|e.getSource()==passwordField){
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
