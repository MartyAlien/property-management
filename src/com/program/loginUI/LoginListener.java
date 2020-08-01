package com.program.loginUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.program.daoimp.MasterDAOImp;
import com.program.daoimp.UserDAOImp;
import com.program.forgetUI.ForgetUI;
import com.program.master.Master;
import com.program.masterUI.MasterUI;
import com.program.registUI.RegistUI;
import com.program.user.User;
import com.program.userUI.UserUI;

public class LoginListener implements ActionListener{
	private JFrame loginUI;
	private JTextField userNameField;
	private JPasswordField passwordField;
	private JComboBox<String> box;
	private JButton logButton,minButton,closeButton;
	private JPanel headPanel ;
	private JButton registButton;
	private JButton forgetButton;
	private int mouseAtX = 0;
	private int mouseAtY = 0;
	public LoginListener() {
		super();
	}


	public LoginListener(JFrame loginUI,JTextField userNameField, JPasswordField passwordField, JComboBox<String> box,
			JButton logButton, JButton minButton, JButton closeButton, JPanel headPanel, JButton registButton,
			JButton forgetButton) {
		super();
		this.loginUI=loginUI;
		this.userNameField = userNameField;
		this.passwordField = passwordField;
		this.box = box;
		this.logButton = logButton;
		this.minButton = minButton;
		this.closeButton = closeButton;
		this.headPanel = headPanel;
		this.registButton = registButton;
		this.forgetButton = forgetButton;
		this.canDraged(this.headPanel);
	}

	// 窗体可拖拽
	public void canDraged(JPanel p) {
		MouseAdapter frameDrageAdapter=new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
                mouseAtX = e.getPoint().x;
                mouseAtY = e.getPoint().y;
            }
		};
		MouseMotionAdapter motionAdapter=new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
	              loginUI.setLocation((e.getXOnScreen()-mouseAtX),(e.getYOnScreen()-mouseAtY));//设置拖拽后，窗口的位置
	          }
		};
		p.addMouseListener(frameDrageAdapter);
		p.addMouseMotionListener(motionAdapter);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==logButton) {
			isLogin();
		}
		if(e.getSource()==closeButton) {
			System.exit(0);
		}
		if(e.getSource()==minButton) {
			 loginUI.setExtendedState(JFrame.ICONIFIED);
		}
		if(e.getSource()==registButton) {
			// 触发注册事件
			SwingUtilities.invokeLater(()->new RegistUI()); 
		}
		if(e.getSource()==forgetButton) {
			// 触发找回密码事件
			SwingUtilities.invokeLater(()->new ForgetUI());
		}
	}


	private void isLogin() {
		String inputNumString=userNameField.getText();
		String inputPWDString=new String(passwordField.getPassword());
		if(box.getSelectedItem().toString().equals("居民模式")) {
			List<User> list = new UserDAOImp().getRightPost(inputNumString, inputPWDString);
			if(list.size()!=0) {
				User user=list.get(0);
				JOptionPane.showMessageDialog(loginUI, "你好，"+user.getName()+" 欢迎使用本系统！","登录成功",JOptionPane.PLAIN_MESSAGE);
				SwingUtilities.invokeLater(()->new UserUI(user));
				loginUI.dispose();
			}else {
				JOptionPane.showMessageDialog(loginUI, "居民账号或者密码错误", "提示", JOptionPane.ERROR_MESSAGE);
			}
			
		}else {
			List<Master> list = new MasterDAOImp().getRightPost(inputNumString, inputPWDString);
			if(list.size()!=0) {
				Master master=list.get(0);
				JOptionPane.showMessageDialog(loginUI, "你好，"+master.getName()+" 成功进入后台系统！","登录成功",JOptionPane.PLAIN_MESSAGE);
				new MasterUI(master);
				loginUI.dispose();
			}else {
				JOptionPane.showMessageDialog(loginUI, "物业管理员账号或者密码错误", "提示", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
}
