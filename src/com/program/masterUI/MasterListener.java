package com.program.masterUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import com.program.dao.AdapterDAO;
import com.program.daoimp.PaymentDAOImp;
import com.program.daoimp.UserDAOImp;
import com.program.loginUI.LoginUI;
import com.program.payment.Payment;
import com.program.user.User;

public class MasterListener implements ActionListener{
	private MasterUI masterUI;
	private JTabbedPane allTabbedPane;
	private JTable payJTable;
	@SuppressWarnings("unused")
	private JPanel headPanel;
	private JButton refleshBtn,addBtn,modBtn,dellBtn;
	private JButton miniBtn,closeBtn;
	private MasterPaneSon1 masterPaneSon1;
	private MasterPaneSon2 masterPaneSon2;
	private int mouseAtX = 0;
	private int mouseAtY = 0;
	
	
	
	public MasterListener(MasterUI masterUI,JTabbedPane allTabbedPane,JTable payJTable,JPanel headPanel,JButton refleshBtn,
			 JButton addBtn, JButton modBtn, JButton dellBtn,MasterPaneSon1 masterPaneSon1,MasterPaneSon2 masterPaneSon2) {
		super();
		
		this.masterUI=masterUI;
		this.allTabbedPane=allTabbedPane;
		this.payJTable=payJTable;
		this.headPanel = headPanel;
		this.refleshBtn = refleshBtn;
		this.addBtn = addBtn;
		this.modBtn = modBtn;
		this.dellBtn = dellBtn;
		this.masterPaneSon1=masterPaneSon1;
		this.masterPaneSon2=masterPaneSon2;
		canDraged(headPanel);
		refleshBtn.addActionListener(this);
		addBtn.addActionListener(this);
		modBtn.addActionListener(this);
		dellBtn.addActionListener(this);
		masterPaneSon1.getYesButton().addActionListener(this);
		masterPaneSon1.getClearButton().addActionListener(this);
		masterPaneSon1.getBoxResfreshBtn().addActionListener(this);
		masterPaneSon1.getField02().addKeyListener(getKeyAdapter());
		masterPaneSon1.getField03().addKeyListener(getKeyAdapter());
		masterPaneSon1.getField04().addKeyListener(getKeyAdapter());
		masterPaneSon2.setMasterPaneSon1(masterPaneSon1);//用于用户选项卡更新时连带业单生成卡的用户数据更新
		masterPaneSon2.getDelButton().addActionListener(this);
		masterPaneSon2.getReflashButton().addActionListener(this);
	}

	public MasterListener(MasterUI masterUI, JTabbedPane allTabbedPane, JTable payJTable, JPanel headPanel,
			JButton refleshBtn, JButton addBtn, JButton modBtn, JButton dellBtn, JButton miniBtn, 
			JButton closeBtn,MasterPaneSon1 masterPaneSon1,MasterPaneSon2 masterPaneSon2) {
		this(masterUI, allTabbedPane, payJTable, headPanel, refleshBtn, addBtn, modBtn, dellBtn,masterPaneSon1,masterPaneSon2);
		this.miniBtn = miniBtn;
		this.closeBtn = closeBtn;
		miniBtn.addActionListener(this);
		closeBtn.addActionListener(this);
		this.masterUI.getChangeBtn().addActionListener(this);
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
		              masterUI.setLocation((e.getXOnScreen()-mouseAtX),(e.getYOnScreen()-mouseAtY));//设置拖拽后，窗口的位置
		          }
			};
			p.addMouseListener(frameDrageAdapter);
			p.addMouseMotionListener(motionAdapter);
		}
		public KeyAdapter getKeyAdapter() {
			KeyAdapter adapter=new KeyAdapter() {

				@Override
				public void keyTyped(KeyEvent e) {
					int keyChar=e.getKeyChar();
					if(e.getSource()==masterPaneSon1.getField02()){
						
						if(!(new String(((char)keyChar)+"").matches("^[0-9]+$"))){
							masterPaneSon1.getInfoField01().setText("不能输入非数字");
							e.consume();
						}else {
							masterPaneSon1.getInfoField01().setText("");
						}
					}
					if(e.getSource()==masterPaneSon1.getField03()){
						
						if(!(new String(((char)keyChar)+"").matches("^[0-9]+$"))){
							masterPaneSon1.getInfoField02().setText("不能输入非数字");
							e.consume();
						}else {
							masterPaneSon1.getInfoField02().setText("");
						}
					}
					if(e.getSource()==masterPaneSon1.getField04()){
						
						if(!(new String(((char)keyChar)+"").matches("^[0-9]+$"))){
							masterPaneSon1.getInfoField03().setText("不能输入非数字");
							e.consume();
						}else {
							masterPaneSon1.getInfoField03().setText("");
						}
					}
				}
				
			};
			return adapter;
		}

	@Override
	public void actionPerformed(ActionEvent e) {
		DefaultTableModel tableModel = (DefaultTableModel) payJTable.getModel();
		if(e.getSource()==refleshBtn) {
			refreshForPane01(tableModel);
		}
		if(e.getSource()==masterPaneSon2.getReflashButton()) {
			refreshForPane03();
		}
		if(e.getSource()==addBtn) {
			allTabbedPane.setSelectedIndex(1);	// 跳转选项卡：1 为下发业单
		}
		if(e.getSource()==dellBtn) {
			
			delRows(tableModel,payJTable,new PaymentDAOImp());
		}
		if(e.getSource()==modBtn) {
			//doNothing
		}
		if(e.getSource()==closeBtn) {
			System.exit(0);
		}
		if(e.getSource()==miniBtn) {
			 masterUI.setExtendedState(JFrame.ICONIFIED);
		}
		if(e.getSource()==this.masterUI.getChangeBtn()) {
			int get = JOptionPane.showOptionDialog(null, "是否切换账户？", "切换提示", JOptionPane.YES_NO_OPTION, 
					JOptionPane.QUESTION_MESSAGE, null,new Object[] {"是","否"}, "否");
			if(get==0) {
				masterUI.dispose();
				SwingUtilities.invokeLater(()->new LoginUI());
			}
		}
		if(e.getSource()==masterPaneSon1.getYesButton()) {
			
			String uName = masterPaneSon1.getPayBox().getSelectedItem().toString();
			String checkExp = masterPaneSon1.getField02().getText();
			String cleanExp = masterPaneSon1.getField03().getText();
			String parkExp = masterPaneSon1.getField04().getText();
			if(uName.equals(masterPaneSon1.getPayBox().getItemAt(0).toString())
					|checkExp.length()==0|cleanExp.length()==0|parkExp.length()==0) {
				JOptionPane.showMessageDialog(null, "请选择用户或者必须设置费用(>=0)", "生成错误", JOptionPane.ERROR_MESSAGE);
			}else {
				int get = JOptionPane.showOptionDialog(null, "请确认是否添加业单？", "友情询问", JOptionPane.YES_NO_OPTION, 
						JOptionPane.WARNING_MESSAGE, null, new Object[]{"是","否"}, "否");
				if(get!=1) {
					// 创建订单对象
					List<Payment> insertList=new ArrayList<Payment>();
					Payment payment=new Payment( uName, Integer.parseInt(checkExp), Integer.parseInt(cleanExp), Integer.parseInt(parkExp), null, "0");
					insertList.add(payment);
					try {
						int insertNum = new PaymentDAOImp().insertObj(insertList);
						JOptionPane.showMessageDialog(null, "业单生成成功："+insertNum+"条", "添加成功",JOptionPane.PLAIN_MESSAGE);
						masterPaneSon1.getClearButton().doClick(); // 生成成功触发清空按钮
					} catch (SQLException e1) {
						System.out.println("MasterListener中账单生成的SQL语句错误");
					}
				}
			}
			
		}
		if(e.getSource()==masterPaneSon1.getClearButton()) {
			masterPaneSon1.getPayBox().setSelectedIndex(0);
			masterPaneSon1.getField02().setText("");
			masterPaneSon1.getField03().setText("");
			masterPaneSon1.getField04().setText("");
			masterPaneSon1.getInfoField01().setText("");
			masterPaneSon1.getInfoField02().setText("");
			masterPaneSon1.getInfoField03().setText("");
		}
		if(e.getSource()==masterPaneSon1.getBoxResfreshBtn()) {
			// 重新刷新下拉列表框：从数据库中
			refreshBox();
		}
		if(e.getSource()==masterPaneSon2.getDelButton()) {
			delRows(masterPaneSon2.getDtm(), masterPaneSon2.getUserTable(), new UserDAOImp());
		}
	}

	private void refreshForPane03() {
		TableModelListener[] tableModelListeners = masterPaneSon2.getDtm().getTableModelListeners();
		masterPaneSon2.getDtm().removeTableModelListener(tableModelListeners[0]);  //刷新前先移除自己注册的监听器
		for(int i=masterPaneSon2.getDtm().getRowCount()-1;i>=0;i--) {
			masterPaneSon2.getDtm().removeRow(i); // 先移除原来的数据
		}
		List<User> users = masterPaneSon2.getUsersList();
		for(User u:users) {
			masterPaneSon2.getDtm().addRow(u.toStrArray());
		}
		masterPaneSon2.getDtm().addTableModelListener(tableModelListeners[0]);	//刷新后重新注册
		// 重新刷新下拉列表框：从数据库中
		refreshBox();
	}

	private void refreshBox() {
		masterPaneSon1.getPayBox().setSelectedIndex(0);
		masterPaneSon1.remove(masterPaneSon1.getPayBox());
		masterPaneSon1.setPayBox(masterPaneSon1.initBox());
		masterPaneSon1.add(masterPaneSon1.getPayBox());
		masterPaneSon1.getPayBox().addItemListener(masterPaneSon1.getItemListener());
		masterPaneSon1.getField01().setText("");
	}

	private void refreshForPane01(DefaultTableModel tableModel) {
		for(int i=tableModel.getRowCount()-1;i>=0;i--) {
			tableModel.removeRow(i); // 先移除原来的数据
		}
		List<Payment> payments = masterUI.getPayments();
		for(Payment p:payments) {
			tableModel.addRow(p.toStrArray());
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
					idList.add((Integer.parseInt(table.getValueAt(selectedRows[i], 0)+"")));
					//tableModel.removeRow(selectedRows[i]);
				}
				// 数据库中：先获取选中行的第0列 -> 编号列，通过编号删除数据库中的数据
				// 数据库中相应记录删除完毕后重新绘制表格
				// 调用删除方法
				if(adapterDAO instanceof PaymentDAOImp) {
					PaymentDAOImp p=(PaymentDAOImp)adapterDAO;
					p.delete(idList);
					refreshForPane01(tableModel);
				}else if(adapterDAO instanceof UserDAOImp){
					UserDAOImp u=(UserDAOImp)adapterDAO;
					u.delete(idList);
					refreshForPane03();
				}
			}
		}
	}
}
