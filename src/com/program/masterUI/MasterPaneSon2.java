package com.program.masterUI;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.program.daoimp.UserDAOImp;
import com.program.user.User;

public class MasterPaneSon2 extends JPanel{
	private static final long serialVersionUID = -1101L;
	private List<User> userList;
	private JTable userTable;
	private String[] columnNames = {"*编号","*用户名","业主姓名","*手机","类型","门牌","是否可用"};
	private JScrollPane scP;
	private JButton delButton;
	private JButton reflashButton;
	private Object[][] data;
	private DefaultTableModel dtm;
	private MasterPaneSon1 masterPaneSon1; 
	
	public MasterPaneSon2() {
		super(null);	// 绝对布局
		setBounds(0, 0, 900, 590);
		setBackground(Color.WHITE);
		this.delButton = initJButton(delButton,"删除用户",750, 520);
		add(delButton);
		this.reflashButton = initJButton(reflashButton,"数据刷新",600, 520);
		add(reflashButton);
		userList=getUsersList();
		add(initScrollPane(userList));
		dtm.addTableModelListener(new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent e) {
				// 修改单元格中的数据并推送到数据库
				int id=Integer.parseInt(userTable.getValueAt(userTable.getSelectedRow(), 0)+"");
				int selectedColumn = userTable.getSelectedColumn();
				String newValue = userTable.getValueAt(userTable.getSelectedRow(), selectedColumn)+"";
				int updateForMaster = new UserDAOImp().updateForMaster(id, selectedColumn, newValue);
				if(updateForMaster==1) {
					JOptionPane.showMessageDialog(null, "修改成功");
					masterPaneSon1.setL(masterPaneSon1.getUserLists()); // 刷新业单中的用户数据
					Object uname = userTable.getValueAt(userTable.getSelectedRow(), 1);
					Object rowName = userTable.getValueAt(userTable.getSelectedRow(), 2);
					if(masterPaneSon1.getPayBox().getSelectedItem().equals(uname)&columnNames[selectedColumn].equals("业主姓名")) {
						masterPaneSon1.getField01().setText("* 业主姓名："+rowName);
					}
				}
			}
		});
	}
	private JButton initJButton(JButton button,String title,int x,int y) {
		button=new JButton(title);
		button.setBounds(x,y,100, 40);
		button.setBorder(null);
		button.setFocusPainted(false);
		button.setBackground(new Color(235,135,112));
		button.setForeground(Color.WHITE);
		button.setFont(new Font("幼圆",Font.PLAIN,16));
		return button;
	}
	public List<User> getUsersList() {
		@SuppressWarnings("unchecked")
		List<User> userAll = (List<User>)new UserDAOImp().selectAll();
		if(userAll.size()<=0) {
			userAll.add(new User());
		}
		return userAll;
	}
	private JScrollPane initScrollPane(List<User> list) {
		scP=new JScrollPane();
		scP.setBackground(Color.WHITE);
		scP.setBounds(0, 0, 896, 500);
		scP.setViewportView(getViewtable(list));
		return scP;
	}
	private JTable getViewtable(List<User> list) {
		data=new Object[list.size()][];
		int i=0;
		for(User u:list) {
			data[i++]=u.toStrArray();
		}
		dtm=new DefaultTableModel(data,columnNames);
		userTable=new JTable(dtm) {
			private static final long serialVersionUID = -021L;

			// 重写这个表格的方法：设置不可编辑，但可以选中
			@Override
			public boolean isCellEditable(int row, int column) {
				if(column==2|column==4|column==5|column==6) { // 2 4 6 列中的值可以修改
					return true;
				}
				return false;
			}
		};
		
		userTable.getTableHeader().setFont(new Font("微软雅黑",Font.BOLD,16));
		userTable.setFont(new Font("幼圆",Font.PLAIN,16));
		userTable.setRowHeight(24);
		userTable.getTableHeader().setReorderingAllowed(false);// 设置列不可拖动
		// 设置部分列宽度
		userTable.getColumnModel().getColumn(0).setPreferredWidth(15);
		userTable.getColumnModel().getColumn(1).setPreferredWidth(20);
		userTable.getColumnModel().getColumn(2).setPreferredWidth(20);
		userTable.getColumnModel().getColumn(3).setPreferredWidth(15);
		userTable.getColumnModel().getColumn(4).setPreferredWidth(15);
		userTable.getColumnModel().getColumn(6).setPreferredWidth(15);
		userTable.getColumnModel().getColumn(0).setResizable(false);
		userTable.getColumnModel().getColumn(2).setResizable(false);
		userTable.getColumnModel().getColumn(3).setResizable(false);
		userTable.getColumnModel().getColumn(4).setResizable(false);
		userTable.getColumnModel().getColumn(6).setResizable(false);
		// 设置居中
		DefaultTableCellRenderer renderer=new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(JLabel.CENTER);
		userTable.setDefaultRenderer(Object.class, renderer);
		return userTable;
	}
	public List<User> getUserList() {
		return userList;
	}
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	public JTable getUserTable() {
		return userTable;
	}
	public void setUserTable(JTable userTable) {
		this.userTable = userTable;
	}
	public String[] getColumnNames() {
		return columnNames;
	}
	public void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames;
	}
	public JScrollPane getScP() {
		return scP;
	}
	public void setScP(JScrollPane scP) {
		this.scP = scP;
	}
	public JButton getDelButton() {
		return delButton;
	}
	public void setDelButton(JButton delButton) {
		this.delButton = delButton;
	}
	public Object[][] getData() {
		return data;
	}
	public void setData(Object[][] data) {
		this.data = data;
	}
	public DefaultTableModel getDtm() {
		return dtm;
	}
	public void setDtm(DefaultTableModel dtm) {
		this.dtm = dtm;
	}
	public JButton getReflashButton() {
		return reflashButton;
	}
	public void setReflashButton(JButton reflashButton) {
		this.reflashButton = reflashButton;
	}
	public MasterPaneSon1 getMasterPaneSon1() {
		return masterPaneSon1;
	}
	public void setMasterPaneSon1(MasterPaneSon1 masterPaneSon1) {
		this.masterPaneSon1 = masterPaneSon1;
	}
	
	
}
