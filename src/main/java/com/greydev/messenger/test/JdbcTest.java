package com.greydev.messenger.test;

import java.sql.DriverManager;

public class JdbcTest {

	public static void main(String[] args) {

		String jdbcUrl = "jdbc:mysql://localhost:3306/TEST?useSSL=false&useUnicode=true"
				+ "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		String userName = "root";
		String password = "root";

		try {
			System.out.println("Connecting to DB");
			DriverManager.getConnection(jdbcUrl, userName, password);
			System.out.println("success");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
