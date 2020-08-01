package com.program.daoimp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.program.dao.AdapterDAO;
import com.program.druidJDBCutil.DruidJDBC;
import com.program.user.User;

public class UserDAOImp extends AdapterDAO{
	DataSource dataSource=DruidJDBC.getDataSource();
	JdbcTemplate jt=new JdbcTemplate(dataSource);
	String sql;
	@Override
	public List<User> getRightPost(String userName, String password){
		// TODO Auto-generated method stub
		sql="select * from user where userName = ? and userPassword = ?";
		List<User> user=null;
		user = jt.query(sql, new BeanPropertyRowMapper<User>(User.class),userName,password);
		return user;
	}
	@Override
	public int insertObj(List<?> list) throws SQLException {
		// TODO Auto-generated method stub
		int count=0;
		if(list.size()==0) {
			return count;
		}
		sql="insert into user(userName,userPassword,name,phone,userType,advDeposit,place,available) values(?,?,?,?,?,?,?,?)";
		try {
			for(Object one:list) {
				User user=(User) one;
				PreparedStatement pS = DruidJDBC.getConnection().prepareStatement("select * from user where userName=? or phone=?");
				pS.setString(1, user.getUserName());
				pS.setString(2, user.getPhone());
				ResultSet rs = pS.executeQuery();
				if(rs.next()==true) {
					return -1;
				}else {
					count+=jt.update(sql, user.getUserName(),user.getUserPassword(),user.getName(),user.getPhone(),
							user.getUserType(),user.getAdvDeposit(),user.getPlace(),user.getAvailable());
				}
			}
		}catch (DuplicateKeyException e) {
			return -1;// 表示表中已存在字段唯一属性的记录 -> 标志用户已存在
		}
		return count;
	}
	@Override
	public List<?> selectAll() {
		sql="select * from user";
		List<User> list = jt.query(sql, new BeanPropertyRowMapper<User>(User.class));
		return list;
	}
	@SuppressWarnings("unchecked")
	@Override
	public void delete(List<?> list) {
		if (list.size()<=0) {
			return;
		}
		sql="delete from user where ID=?";
		List<Integer> l=(List<Integer>)list;
		for(int id:l) {
			jt.update(sql, id);
		}
	}
	@Override
	public int updateForMaster(int ID, int selectedColumn, String newValue) {
		// "业主姓名":2 ,"类型":4 ,"门牌":5 ,"是否可用":6
		if(selectedColumn==2) {
			sql="update user set name=? where ID=?";
		}else if (selectedColumn==4) {
			sql="update user set userType=? where ID=?";
		}else if (selectedColumn==5) {
			sql="update user set place=? where ID=?";
		}else if (selectedColumn==6) {
			sql="update user set available=? where ID=?";
		}else {
			return -1;
		}
		int updateNum = jt.update(sql,newValue,ID);
		return updateNum;
	}
	@Override
	public int updateForUser(int ID, int selectedRow, String newValue) {
		// TODO Auto-generated method stub
		if(selectedRow==1) {
			sql="update user set userPassword=? where ID=?";
		}else if (selectedRow==2) {
			sql="update user set name=? where ID=?";
		}else if (selectedRow==3) {
			sql="update user set phone=? where ID=?";
		}else if (selectedRow==6) {
			sql="update user set place=? where ID=?";
		}else {
			return -1;
		}
		int updateNum = jt.update(sql,newValue,ID);
		return updateNum;
	}
	@Override
	public int updateForBill(int UID, int PID, int newMoney) {
		sql="update user set advDeposit=? where ID=? and available='1'";
		int updateNum = jt.update(sql,newMoney,UID);
		if(updateNum==0) {
			return 0;
		}else {
			String sql2="update pay set isPay='1',payDate=? where ID=?";
			jt.update(sql2,new Timestamp(new Date().getTime()),PID);
		}
		return updateNum;
	}
	@Override
	public List<?> selectOne() {
		// TODO Auto-generated method stub
		return null;
	}
	public List<User> selectOne(String userName,String phone) {
		sql="select * from user where userName=? and phone=?";
		List<User> query = jt.query(sql, new BeanPropertyRowMapper<User>(User.class),userName,phone);
		return query;
	}
	@SuppressWarnings("unchecked")
	public List<?> selectOne(Object obj) {
		User user=(User)obj;
		sql="select * from user where userName=?";
		@SuppressWarnings("rawtypes")
		List<User> query =(List<User>) jt.query(sql, new BeanPropertyRowMapper(User.class),user.getUserName());
		return query;
	}
	@Override
	public int updateForForget(String userName, String phone, String newPassword) {
		sql="update user set userPassword=? where userName=? and phone=?";
		int update = jt.update(sql,newPassword,userName,phone);
		return update;
	}
	public User getRefreshOne(User oldUser) {
		sql="select * from user where ID=?";
		List<User> query =(List<User>) jt.query(sql, new BeanPropertyRowMapper<User>(User.class),
				Integer.parseInt(oldUser.getID()));
		if(query.size()<=0) {
			return null;
		}
		return query.get(0);
	}
}
