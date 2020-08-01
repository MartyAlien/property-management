package com.program.mainclass;

import javax.swing.SwingUtilities;

import com.program.loginUI.LoginUI;

public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->new LoginUI());
	}
}
