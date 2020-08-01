package com.program.daoimp;

import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.program.dao.AdapterDAO;
import com.program.druidJDBCutil.DruidJDBC;
import com.program.payment.Payment;
import com.program.user.User;

public class PaymentDAOImp extends AdapterDAO{
	JdbcTemplate jt=new JdbcTemplate(DruidJDBC.getDataSource());
	String sql;
	@Override
	public List<?> selectAll() {
		// TODO Auto-generated method stub
		sql="select * from pay";
		List<Payment> list = jt.query(sql, new BeanPropertyRowMapper<Payment>(Payment.class));
		return list;
	}
	// 重载
	public List<?> selectAll(User u) {
		// TODO Auto-generated method stub
		sql="select * from pay where userName=? and isPay='1'";
		List<Payment> list = jt.query(sql, new BeanPropertyRowMapper<Payment>(Payment.class),u.getUserName());
		return list;
	}
	@SuppressWarnings("unchecked")
	@Override
	public void delete(List<?> list) {
		// TODO Auto-generated method stub
		if (list.size()<=0) {
			return;
		}
		sql="delete from pay where ID=?";
		List<Integer> l=(List<Integer>)list;
		for(int id:l) {
			jt.update(sql, id);
		}
	}
	@Override
	public int insertObj(List<?> list) throws SQLException {
		int count=0;
		if(list.size()<=0) {
			return -1;
		}
		@SuppressWarnings("unchecked")
		List<Payment> l=(List<Payment>)list;
		sql="insert into pay(userName,checkEXP,cleanEXP,parkEXP) values(?,?,?,?)";
		for(Payment p:l) {
			count += jt.update(sql,p.getUserName(),p.getCheckEXP(),p.getCleanEXP(),p.getParkEXP());
		}
		return count;
	}
	@Override
	public List<?> selectForNoPay(String userName) {
		sql="select * from pay where userName=? and isPay='0'";
		List<Payment> query = jt.query(sql, new BeanPropertyRowMapper<Payment>(Payment.class),userName);
		return query;
	}
	
}
