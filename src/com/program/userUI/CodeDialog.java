package com.program.userUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import com.program.check.PhoneCode;

public class CodeDialog {
	JDialog dialog;
	JTextField field01,field02;
	JButton button01,button02,button03;
	MyListener myListener;
	@SuppressWarnings("unused")
	private UserUI owner;
	int flag=0;
	Object[][] data;
	JTable vtable;
	DefaultTableModel dtm;
	int row;
	int column;
	String code=null;
	Timer timer=new Timer();
	public CodeDialog(UserUI owner,Object[][] data,JTable vtable,DefaultTableModel dtm,int row,int column) {
		this.data=data;
		this.vtable=vtable;
		this.dtm=dtm;
		this.row=row;
		this.column=column;
		this.owner=owner;
		dialog=new JDialog(owner, true);
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.setTitle("资料修改验证");
		dialog.setSize(400, 300);
		dialog.setResizable(false);
		dialog.setLocationRelativeTo(null);
		dialog.setLayout(null);
		dialog.getContentPane().setBackground(new Color(255,245,245));
		JLabel jLabel=new JLabel("手机号验证:");
		jLabel.setBounds(75, 70, 100, 40);
		jLabel.setFont(new Font("",Font.PLAIN,16));
		String phone = ((UserUI)owner).getUser().getPhone();
		field01=new JTextField();
		field01.setBounds(185, 70, 140, 40);
		field01.setText(phone);
		field01.setEditable(false);
		field01.setBackground(null);
		field01.setFont(new Font("",Font.PLAIN,16));
		JLabel jLabel2=new JLabel("获取验证码:");
		jLabel2.setBounds(75, 130, 100, 40);
		jLabel2.setFont(new Font("",Font.PLAIN,16));
		field02=new JTextField();
		field02.setBounds(185, 130, 70, 40);
		field02.setBackground(null);
		field02.setFont(new Font("",Font.PLAIN,16));
		
		button01=new JButton("发送");
		button01.setBounds(260, 130, 65, 40);
		button01.setBorder(null);
		button01.setFocusPainted(false);
		button01.setBackground(new Color(133, 162, 190));
		button01.setForeground(Color.white);
		
		button02=new JButton("确 认");
		button02.setBounds(80, 200, 95, 40);
		button02.setBorder(null);
		button02.setFocusPainted(false);
		button02.setBackground(new Color(103, 162, 100));
		button02.setForeground(Color.white);
		
		button03=new JButton("取 消");
		button03.setBounds(220, 200, 95, 40);
		button03.setBorder(null);
		button03.setFocusPainted(false);
		button03.setBackground(new Color(193, 162, 100));
		button03.setForeground(Color.white);
		
		dialog.add(jLabel);
		dialog.add(field01);
		dialog.add(jLabel2);
		dialog.add(field02);
		dialog.add(button01);
		dialog.add(button02);
		dialog.add(button03);
		
		myListener= new MyListener();
		button03.addActionListener(myListener);
		button02.addActionListener(myListener);
		button01.addActionListener(myListener);
		
		dialog.setVisible(true);
		
	}
	
	class MyListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==button03) {
				TableModelListener[] tableModelListeners = dtm.getTableModelListeners();
				dtm.removeTableModelListener(tableModelListeners[0]);
				vtable.setValueAt(data[row][column],vtable.getSelectedRow(), 1);
				dtm.addTableModelListener(tableModelListeners[0]);
				dialog.dispose();
				setFlag(0);
			}
			if(e.getSource()==button02) {
				if(field02.getText().length()<=0) {
					JOptionPane.showMessageDialog(null, "验证码不能为空!","警告",JOptionPane.WARNING_MESSAGE);
				}else {
					if(field02.getText().equals(code)) {
						code="";
						dialog.dispose();
						JOptionPane.showMessageDialog(null, "修改成功！");
						setFlag(1);
					}else {
						JOptionPane.showMessageDialog(null, "验证码错误或者失效", "验证失败", JOptionPane.ERROR_MESSAGE);
						setFlag(0);
					}
				}
			}
			if(e.getSource()==button01) {
				timer.schedule(new TimerTask() {
					int i = 60;
					@Override
					public void run() {
						i--;
						button01.setText(i + "s");
						button01.setForeground(Color.white);
						button01.setBackground(Color.DARK_GRAY);
						button01.setEnabled(false);
						//sendBtn.setHorizontalTextPosition(JButton.CENTER);
						if (i <= 0) {
							//设置按钮可点击 并且停止任务
							button01.setEnabled(true);
							button01.setBackground(new Color(103,172,180));
							button01.setText("发送");
							//sendBtn.setHorizontalTextPosition(JButton.CENTER);
							//timer.cancel();
							this.cancel();
						}
					}
				}, 0, 1000);
				String phonemsg = PhoneCode.getPhonemsg(field01.getText(), 3); //使用资料修改模板
				if(phonemsg.equals("true")) {
					code=PhoneCode.codeNum;
					System.out.println(code);
				}
			}
		}
		
	}
	public JDialog getDialog() {
		return dialog;
	}

	public void setDialog(JDialog dialog) {
		this.dialog = dialog;
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

	public JButton getButton01() {
		return button01;
	}

	public void setButton01(JButton button01) {
		this.button01 = button01;
	}

	public JButton getButton02() {
		return button02;
	}

	public void setButton02(JButton button02) {
		this.button02 = button02;
	}

	public JButton getButton03() {
		return button03;
	}

	public void setButton03(JButton button03) {
		this.button03 = button03;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}
	
}
