package com.program.userUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import com.program.check.PhoneCode;
import com.program.dao.AdapterDAO;
import com.program.daoimp.PaymentDAOImp;
import com.program.daoimp.UserDAOImp;
import com.program.loginUI.LoginUI;
import com.program.user.User;

public class UserListener implements ActionListener{
	private UserUI userUI;
	private UserPaneSon1 sPanel01;
	private UserPaneSon2 sPanel02;
	@SuppressWarnings("unused")
	private UserPaneSon3 sPanel03;
	private int mouseAtX = 0;
	private int mouseAtY = 0;
	Timer timer=new Timer();
	String code="";
	
	public UserListener(UserUI userUI) {
		this.userUI = userUI;
		this.sPanel01=this.userUI.getPaneSon1();
		this.sPanel02=this.userUI.getPaneSon2();
		this.sPanel03=this.userUI.getPaneSon3();
		canDraged(this.userUI.getHeadPanel());
		this.userUI.getCloseBtn().addActionListener(this);
		this.userUI.getMiniBtn().addActionListener(this);
		this.userUI.getChangeBtn().addActionListener(this);
		this.userUI.getRefreshBtn().addActionListener(this);
		this.sPanel02.getDelBtn().addActionListener(this);
		this.sPanel01.getCodeBtn().addActionListener(this);
		this.sPanel01.getPayBtn().addActionListener(this);
	}
	public void canDraged(JPanel p) {
		MouseAdapter frameDrageAdapter=new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
                mouseAtX = e.getPoint().x;
                mouseAtY = e.getPoint().y;
            }
		};
		MouseMotionAdapter motionAdapter=new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
	              userUI.setLocation((e.getXOnScreen()-mouseAtX),(e.getYOnScreen()-mouseAtY));//设置拖拽后，窗口的位置
	          }
		};
		p.addMouseListener(frameDrageAdapter);
		p.addMouseMotionListener(motionAdapter);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		User u = sPanel01.getUser();
		// TODO Auto-generated method stub
		if(e.getSource()==userUI.getCloseBtn()) {
			System.exit(0);
		}
		if(e.getSource()==userUI.getMiniBtn()) {
			 userUI.setExtendedState(JFrame.ICONIFIED);
		}
		if(e.getSource()==userUI.getChangeBtn()) {
			int get = JOptionPane.showOptionDialog(null, "是否切换账户？", "切换提示",JOptionPane.YES_NO_OPTION, 
					JOptionPane.QUESTION_MESSAGE, null,new Object[] {"是","否"}, "否");
			if(get==0) {
				userUI.dispose();
				SwingUtilities.invokeLater(()->new LoginUI());
			}
		}
		if(e.getSource()==userUI.getRefreshBtn()) {
			SwingUtilities.invokeLater(() -> {
				User refreshedUser = new UserDAOImp().getRefreshOne(userUI.getUser());
				UserUI userUI2 = new UserUI(refreshedUser);
				userUI2.setLocation(userUI.getLocation());
				userUI.dispose();
			});
		}
		if(e.getSource()==this.sPanel02.getDelBtn()) {
			delRows(sPanel02.getDtm(), sPanel02.getPayedTable(), new PaymentDAOImp());
		}
		if(e.getSource()==sPanel01.getCodeBtn()){
			User refreshOne = new UserDAOImp().getRefreshOne(userUI.getUser());
			if(refreshOne.getAvailable().equals("1")) {
				// 检测账户是否可用：可用情况下，才能正常获取验证码
				clockCodeBtn(u);
			}else {
				// 不可用的话刷新界面
				JOptionPane.showMessageDialog(null, "经数据库检测，账户不可用，即将刷新界面","操作失败",JOptionPane.ERROR_MESSAGE);
				userUI.getRefreshBtn().doClick();
			}
		}
		if(e.getSource()==sPanel01.getPayBtn()) {
			readyToPay(u);
		}
	}
	private void clockCodeBtn(User u) {
		timer.schedule(new TimerTask() {
			int i=60;
			@Override
			public void run() {
				sPanel01.getCodeBtn().setForeground(Color.white);
				sPanel01.getCodeBtn().setBackground(Color.DARK_GRAY);
				sPanel01.getCodeBtn().setFont(new Font("",Font.PLAIN,14));
				sPanel01.getCodeBtn().setEnabled(false);
				i--;
				sPanel01.getCodeBtn().setText("已发送("+i + "s)");
				//sendBtn.setHorizontalTextPosition(JButton.CENTER);
				if (i <= 0) {
					//设置按钮可点击 并且停止任务
					sPanel01.getCodeBtn().setEnabled(true);
					sPanel01.getCodeBtn().setBackground(new Color(103,172,180));
					sPanel01.getCodeBtn().setFont(new Font("",Font.PLAIN,16));
					sPanel01.getCodeBtn().setText("验证码");
					//sendBtn.setHorizontalTextPosition(JButton.CENTER);
					//timer.cancel(); // 这种会使timer具有一次性
					this.cancel();// 一个任务执行完毕后需要cancel，之后才能继续开始新任务
				}
			}
		}, 1000, 1000);
		//readyToPay();
		String phone=u.getPhone();
		String phonemsg = PhoneCode.getPhonemsg(phone, 2); // 使用缴费模板
		if(phonemsg.equals("true")) {
			code=PhoneCode.codeNum;
			System.out.println(code);
		}
	}
	private void readyToPay(User u) {
		if(sPanel01.getFields()[2].getText().length()>0) {
			if(code.length()>0) {
				if(sPanel01.getFields()[2].getText().equals(code)) {
					String bill = sPanel01.getFields()[4].getText();
					int parseInt = Integer.parseInt(bill.substring(3, bill.length()-1));
					if(u.getAdvDeposit()>=parseInt) {
						// bill形如: 总计:232元
						// 账户存款足以支付费用，调用更新操作
						String s=sPanel01.getWillPayBox().getSelectedItem().toString();
						int payID=Integer.parseInt(s.substring(6, s.length()));
						u.setAdvDeposit(u.getAdvDeposit()-parseInt);
						System.out.println("账户余额"+u.getAdvDeposit());
						int updateForBill = new UserDAOImp().updateForBill(Integer.parseInt(u.getID()), payID,(int)(u.getAdvDeposit()));
						if(updateForBill==1) {
							JOptionPane.showMessageDialog(null, "支付成功，请前往历史账单查询");
							// 支付成功了需要把下拉列表框中的已支付数据移除 -> 通过再次new对象
							/*UserUI refreshed = new UserUI(userUI.getUser());
							refreshed.setLocation(this.userUI.getLocation());
							userUI.dispose();*/
							userUI.getRefreshBtn().doClick();
						}else if(updateForBill==0) {
							JOptionPane.showMessageDialog(null, "支付失败，账户不可用");
							userUI.getRefreshBtn().doClick();
						}
						
						code="";
					}else {
						JOptionPane.showMessageDialog(null, "账户余额不足", "支付失败", JOptionPane.ERROR_MESSAGE);
						code="";
					}
				}else {
					JOptionPane.showMessageDialog(null, "验证码错误或者失效", "认证失败", JOptionPane.ERROR_MESSAGE);
				}
			}else {
				JOptionPane.showMessageDialog(null, "请先获取验证码", "认证失败", JOptionPane.ERROR_MESSAGE);
			}
		}else {
			JOptionPane.showMessageDialog(null, "验证码不能为空","警告", JOptionPane.WARNING_MESSAGE);
		}
	}
	public void delRows(DefaultTableModel tableModel,JTable table,AdapterDAO adapterDAO) {
		List<Integer> idList=new ArrayList<Integer>();
		int selectRows=table.getSelectedRows().length;// 取得用户所选行的行数
		int[] selectedRows = table.getSelectedRows();
		if(selectRows<=0) {
			JOptionPane.showMessageDialog(null, "删除前要选中行", "警告", JOptionPane.WARNING_MESSAGE);
		}else {
			// 确认是否删除
			int get = JOptionPane.showOptionDialog(null, "请确认是否删除？", "友情询问", JOptionPane.YES_NO_OPTION, 
					JOptionPane.WARNING_MESSAGE, null, new Object[]{"是","否"}, "否");
			if(get!=1) {
				// 视觉上：倒着删避免数组下标越界
				for(int i=selectRows-1;i>=0;i--) {
					idList.add((Integer.parseInt(tableModel.getValueAt(selectedRows[i], 0)+"")));
					tableModel.removeRow(selectedRows[i]);
				}
				// 数据库中：先获取选中行的第0列 -> 编号列，通过编号删除数据库中的数据
				// 调用删除方法
				if(adapterDAO instanceof PaymentDAOImp) {
					PaymentDAOImp p=(PaymentDAOImp)adapterDAO;
					p.delete(idList);
				}else if(adapterDAO instanceof UserDAOImp){
					UserDAOImp u=(UserDAOImp)adapterDAO;
					u.delete(idList);
				}
			}
		}
	}
	
}
