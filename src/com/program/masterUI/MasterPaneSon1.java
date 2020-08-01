package com.program.masterUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.program.daoimp.UserDAOImp;
import com.program.user.User;

public class MasterPaneSon1 extends JPanel{
	private static final long serialVersionUID = -111201L;
	private JButton yesButton,clearButton,boxResfreshBtn;
	private JComboBox<String> payBox;
	private JTextField field01,field02,field03,field04,infoField01,infoField02,infoField03;
	List<User> l;
	Font font1=new Font("微软雅黑",Font.PLAIN,18);
	Font font2=new Font("楷体",Font.PLAIN,16);
	public MasterPaneSon1() {
		super(null);	// 绝对布局
		setBounds(0, 0, 900, 590);
		//setBackground(Color.WHITE);
		JLabel jLabel01=new JLabel("请选择用户:");
		JLabel jLabel02=new JLabel("设备检查费:");
		JLabel jLabel03=new JLabel("清洁费:");
		JLabel jLabel04=new JLabel("停车费:");
		jLabel01.setBounds(250, 100, 100, 40);
		jLabel01.setFont(font1);
		jLabel02.setBounds(250, 150, 100, 40);
		jLabel02.setFont(font1);
		jLabel03.setBounds(250, 200, 100, 40);
		jLabel03.setFont(font1);
		jLabel04.setBounds(250, 250, 100, 40);
		jLabel04.setFont(font1);
		add(jLabel01);
		add(jLabel02);
		add(jLabel03);
		add(jLabel04);
		field01=new JTextField();
		field01.setFont(font2);
		field01.setBounds(600, 100, 200, 36);
		field01.setEditable(false);
		field01.setBorder(null);
		field01.setBackground(Color.white);
		field01.setForeground(new Color(211,82,48));
		this.payBox=initBox();
		payBox.addItemListener(this.getItemListener());
		add(payBox);
		add(field01);
		this.boxResfreshBtn=new JButton();
		boxResfreshBtn.setBounds(548, 100, 32, 32);
		boxResfreshBtn.setFocusPainted(false);
		boxResfreshBtn.setBorder(null);
		boxResfreshBtn.setBackground(Color.white);
		boxResfreshBtn.setToolTipText("列表刷新");
		boxResfreshBtn.setIcon(new ImageIcon("mstUI_img/boxRefresh01.png"));
		boxResfreshBtn.setRolloverIcon(new ImageIcon("mstUI_img/boxRefresh02.png"));
		boxResfreshBtn.setPressedIcon(new ImageIcon("mstUI_img/boxRefresh03.png"));
		add(boxResfreshBtn);
		
		field02=new JTextField();
		field03=new JTextField();
		field04=new JTextField();
		field02.setBounds(400, 150, 180, 36);
		field03.setBounds(400, 200, 180, 36);
		field04.setBounds(400, 250, 180, 36);
		field02.setFont(font2);
		field03.setFont(font2);
		field04.setFont(font2);
		
		infoField01=new JTextField();
		infoField02=new JTextField();
		infoField03=new JTextField();
		infoField01.setBounds(600, 150, 200, 36);
		infoField02.setBounds(600, 200, 200, 36);
		infoField03.setBounds(600, 250, 200, 36);
		infoField01.setFont(font2);
		infoField01.setEditable(false);
		infoField01.setBorder(null);
		infoField01.setBackground(Color.white);
		infoField01.setForeground(new Color(211,82,48));
		infoField02.setFont(font2);
		infoField02.setEditable(false);
		infoField02.setBorder(null);
		infoField02.setBackground(Color.white);
		infoField02.setForeground(new Color(211,82,48));
		infoField03.setFont(font2);
		infoField03.setEditable(false);
		infoField03.setBorder(null);
		infoField03.setBackground(Color.white);
		infoField03.setForeground(new Color(211,82,48));
		
		add(field02);
		add(field03);
		add(field04);
		add(infoField01);
		add(infoField02);
		add(infoField03);
		yesButton=new JButton("生  成");
		clearButton=new JButton("清  空");
		yesButton.setBounds(300, 360, 90, 40);
		clearButton.setBounds(470, 360, 90, 40);
		yesButton.setBorder(null);
		yesButton.setFocusPainted(false);
		yesButton.setForeground(Color.white);
		yesButton.setBackground(new Color(30,180,64));
		yesButton.setFont(font1);
		clearButton.setBorder(null);
		clearButton.setFocusPainted(false);
		clearButton.setForeground(Color.white);
		clearButton.setBackground(new Color(130,100,64));
		clearButton.setFont(font1);
		add(yesButton);
		add(clearButton);
	}
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.drawImage(new ImageIcon("mstUI_img/masterBG.png").getImage(), 0, 0,this.getWidth(), this.getHeight(), this);
	}
	public JComboBox<String> initBox() {
		JComboBox<String> box=new JComboBox<String>();
		box.setBounds(400, 100, 140, 36);
		box.setFont(font2);
		box.setBackground(Color.white);
		box.setBorder(null);
		l = getUserLists();
		box.addItem(" ---请选择--- ");
		for (User user : l) {
			// 可用状态的用户才能用于生成业单
			if(user.getAvailable().equals("1")) {
				box.addItem(user.getUserName());
			}
		}
		return box;
	}
	
	@SuppressWarnings("unchecked")
	public List<User> getUserLists() {
		return (List<User>)new UserDAOImp().selectAll();
	}
	
	public ItemListener getItemListener() {
		@SuppressWarnings("static-access")
		ItemListener itemListener=e -> {
			if(e.getStateChange()==e.SELECTED) {
				if(payBox.getSelectedIndex()==0) {
					field01.setText("");
				}else {
					if(l.size()!=0) {
						for(User u:l) {
							if(u.getUserName().equals(payBox.getSelectedItem())) {
								field01.setText("* 业主姓名："+u.getName());
							}
						}
					}
				}
			}
		};
		return itemListener;
	}
	
	public List<User> getL() {
		return l;
	}
	public void setL(List<User> l) {
		this.l = l;
	}
	public JButton getYesButton() {
		return yesButton;
	}
	public void setYesButton(JButton yesButton) {
		this.yesButton = yesButton;
	}
	public JButton getClearButton() {
		return clearButton;
	}
	public void setClearButton(JButton clearButton) {
		this.clearButton = clearButton;
	}
	public JComboBox<String> getPayBox() {
		return payBox;
	}
	public void setPayBox(JComboBox<String> payBox) {
		this.payBox = payBox;
	}
	public JTextField getField01() {
		return field01;
	}
	public void setField01(JTextField field01) {
		this.field01 = field01;
	}
	public JTextField getField02() {
		return field02;
	}
	public void setField02(JTextField field02) {
		this.field02 = field02;
	}
	public JTextField getField03() {
		return field03;
	}
	public void setField03(JTextField field03) {
		this.field03 = field03;
	}
	public JTextField getField04() {
		return field04;
	}
	public void setField04(JTextField field04) {
		this.field04 = field04;
	}
	public JTextField getInfoField01() {
		return infoField01;
	}
	public void setInfoField01(JTextField infoField01) {
		this.infoField01 = infoField01;
	}
	public JTextField getInfoField02() {
		return infoField02;
	}
	public void setInfoField02(JTextField infoField02) {
		this.infoField02 = infoField02;
	}
	public JTextField getInfoField03() {
		return infoField03;
	}
	public void setInfoField03(JTextField infoField03) {
		this.infoField03 = infoField03;
	}
	public JButton getBoxResfreshBtn() {
		return boxResfreshBtn;
	}
	public void setBoxResfreshBtn(JButton boxResfreshBtn) {
		this.boxResfreshBtn = boxResfreshBtn;
	}
	
}
