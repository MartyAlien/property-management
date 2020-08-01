package com.program.userUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.program.daoimp.PaymentDAOImp;
import com.program.payment.Payment;
import com.program.user.User;

public class UserPaneSon2 extends JPanel{
	private static final long serialVersionUID = -18044L;
	private User user;
	private JScrollPane jScrollPane;
	private JTable payedTable;
	private JButton delBtn;
	private DefaultTableModel dtm;
	private String[] columnNames = {"账单编号","用户名","业主姓名","手机","检查费","清洁费","停车费","缴费日期"};
	@SuppressWarnings("unused")
	private List<Payment> payList=new ArrayList<Payment>(); 
	private Object[][] data;
	
	// 无参构造 局部测试用
	public UserPaneSon2() {
		super(null);
		setBounds(0, 0, 900, 590);
		setBackground(Color.WHITE);
	}
	// 有参构造  总体测试用
	public UserPaneSon2(User user) {
		this();
		this.user=user;
		add(initScrollPane(this.getPayments(this.user)));
		this.delBtn=this.initJButton(delBtn, "删除记录", 750, 520);
		this.delBtn.setToolTipText("Alt+delete可触发按钮哦!");
		this.delBtn.setMnemonic(KeyEvent.VK_DELETE);
		add(this.delBtn);
	}
	private JScrollPane initScrollPane(List<Payment> list) {
		jScrollPane=new JScrollPane();
		jScrollPane.setBackground(Color.WHITE);
		jScrollPane.setBounds(0, 0, 896, 500);
		jScrollPane.setViewportView(getViewtable(list));
		jScrollPane.setOpaque(false);
		jScrollPane.getViewport().setOpaque(false);
		Border js = BorderFactory.createEmptyBorder();
		jScrollPane.setBorder(js);
		return jScrollPane;
	}
	private JTable getViewtable(List<Payment> list) {
		data=new Object[list.size()][];
		int i=0;
		for(Payment p:list) {
			data[i++]=setArrObj(p);
		}
		dtm=new DefaultTableModel(data,columnNames);
		payedTable=new JTable(dtm) {
			private static final long serialVersionUID = -111L;

			// 重写这个表格的方法：设置不可编辑，但可以选中
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		payedTable.getTableHeader().setFont(new Font("微软雅黑",Font.BOLD,16));
		payedTable.setFont(new Font("幼圆",Font.PLAIN,16));
		payedTable.setRowHeight(24);
		payedTable.getTableHeader().setReorderingAllowed(false);// 设置列不可拖动
		// 设置部分列宽度
		//payedTable.getColumnModel().getColumn(k).setResizable(false);
		payedTable.getColumnModel().getColumn(7).setPreferredWidth(200);
		// 设置居中
		DefaultTableCellRenderer renderer=new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(JLabel.CENTER);
		payedTable.setDefaultRenderer(Object.class, renderer);
		return payedTable;
	}
	@SuppressWarnings("unchecked")
	public List<Payment> getPayments(User u) {
		PaymentDAOImp paymentDAOImp = new PaymentDAOImp();
		List<Payment> list = (List<Payment>)paymentDAOImp.selectAll(u);
		return list;
	}
	public Object[] setArrObj(Payment p) {
		// {"账单编号","用户名","业主姓名","手机","检查费","清洁费","停车费","缴费日期"};
		Object[] objs= {p.getID(),user.getUserName(),user.getName(),user.getPhone(),p.getCheckEXP(),
				p.getCleanEXP(),p.getParkEXP(),p.getPayDate()};
		return objs;
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
	public JButton getDelBtn() {
		return delBtn;
	}
	public void setDelBtn(JButton delBtn) {
		this.delBtn = delBtn;
	}
	public DefaultTableModel getDtm() {
		return dtm;
	}
	public void setDtm(DefaultTableModel dtm) {
		this.dtm = dtm;
	}
	public JTable getPayedTable() {
		return payedTable;
	}
	public void setPayedTable(JTable payedTable) {
		this.payedTable = payedTable;
	}
	
}
