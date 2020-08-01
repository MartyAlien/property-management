package com.program.userUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.program.daoimp.UserDAOImp;
import com.program.user.User;

public class UserPaneSon3 extends JPanel{
	private static final long serialVersionUID = -1804L;
	private User user;
	private JScrollPane jScrollPane;
	private JTable vtable;
	private DefaultTableModel dtm;
	private String[] columnNames = {"资料","详细信息"};
	private Object[][] data;
	private UserUI owner;

	public UserPaneSon3() {
		super(null);
		setBounds(0, 0, 900, 590);
		//setBackground(Color.WHITE);
		add(initScrollPane());
		dtmListener();
	}
	public UserPaneSon3(User user) {
		super(null);
		this.user=user;
		setBounds(0, 0, 900, 590);
		setBackground(Color.WHITE);
		add(initScrollPane());
		dtmListener();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.drawImage(new ImageIcon("mstUI_img/userBG.png").getImage(), 0, 0,this.getWidth(), this.getHeight(), this);
	}
	private JScrollPane initScrollPane() {
		jScrollPane=new JScrollPane();
		//jScrollPane.setBackground(Color.WHITE);
		jScrollPane.getViewport().setOpaque(false);
		jScrollPane.setOpaque(false);
		jScrollPane.setBounds(200, 50, 500, 349);
		JTable viewtable = getViewtable();
		jScrollPane.setViewportView(viewtable);
		return jScrollPane;
	}
	@SuppressWarnings("serial")
	private JTable getViewtable() {
		data=createArr();
		dtm=new DefaultTableModel(data,columnNames);
		vtable=new JTable(dtm) {
			// 重写这个表格的方法：设置不可编辑，但可以选中
			@Override
			public boolean isCellEditable(int row, int column) {
				if(column==1) {
					if(row==1|row==2|row==3|row==6)
						return true;
				}
				return false;
			}
		};
		
		vtable.getTableHeader().setFont(new Font("微软雅黑",Font.BOLD,16));
		vtable.setFont(new Font("幼圆",Font.PLAIN,20));
		vtable.setRowHeight(40);
		vtable.getTableHeader().setReorderingAllowed(false);// 设置列不可拖动
		// 设置部分列宽度
		//payedTable.getColumnModel().getColumn(k).setResizable(false);
		//payedTable.getColumnModel().getColumn(7).setPreferredWidth(200);
		// 设置居中
		DefaultTableCellRenderer renderer=new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(JLabel.CENTER);
		renderer.setOpaque(false);
		vtable.setDefaultRenderer(Object.class, renderer);
		vtable.setOpaque(false);	// 设置表格本身透明
		vtable.getTableHeader().setOpaque(false);	//设置透明
		return vtable;
	}
	private Object[][] createArr(){
		Object[][] arr={{"*用户名",this.user.getUserName()},
				{"用户密码",this.user.getUserPassword()},
				{"业主姓名",this.user.getName()},
				{"手机号码",this.user.getPhone()},
				{"*居住类型",this.user.getUserType()},
				{"*预存款",this.user.getAdvDeposit()},
				{"门牌信息",this.user.getPlace()},
				{"*可用状态",this.user.getAvailable().equals("1")?"是":"否"}};
		return arr;
	}
	
	private void dtmListener() {
		dtm.addTableModelListener(new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent e) {
				// 修改单元格中的数据并推送到数据库
				CodeDialog codeDialog = new CodeDialog(getOwner(),data,vtable,dtm,vtable.getSelectedRow(),vtable.getSelectedColumn());
				int flag = codeDialog.getFlag();
				if(flag==1) {
					String newValue=vtable.getValueAt(vtable.getSelectedRow(), vtable.getSelectedColumn())+"";
					@SuppressWarnings("unused")
					int successNum = new UserDAOImp().updateForUser(Integer.parseInt(user.getID()), vtable.getSelectedRow(), newValue);
					/*User newUser = owner.getNewUser(owner.getUser());
					owner.dispose();
					SwingUtilities.invokeLater(()->new UserUI(newUser));*/
					owner.getRefreshBtn().doClick();// 修改成功后触发刷新按钮
				}
			}
		});
	}
	public UserUI getOwner() {
		return owner;
	}
	public void setOwner(UserUI owner) {
		this.owner = owner;
	}
	public DefaultTableModel getDtm() {
		return dtm;
	}
	public void setDtm(DefaultTableModel dtm) {
		this.dtm = dtm;
	}
	
}
