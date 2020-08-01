package com.program.dao;

import java.sql.SQLException;
import java.util.List;

public class AdapterDAO implements DAO{

	@Override
	public List<?> getRightPost(String userName, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insertObj(List<?> list) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<?> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void delete(List<?> list) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int updateForMaster(int ID, int selectedColumn, String newValue) {
		return 0;
	}

	@Override
	public int updateForUser(int ID, int selectedRow, String newValue) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<?> selectForNoPay(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateForBill(int UID, int PID, int newMoney) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<?> selectOne() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateForForget(String userName, String phone, String newPassword) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
