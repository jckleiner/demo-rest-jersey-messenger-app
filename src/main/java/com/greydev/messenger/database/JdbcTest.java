package com.greydev.messenger.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcTest {

	public static void main(String[] args) {

		String jdbcUrl = "jdbc:mysql://localhost:3306/TEST?useSSL=false&useUnicode=true"
				+ "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		String userName = "root";
		String password = "root";

		try {
			System.out.println("Connecting to DB");
			Connection myConn = DriverManager.getConnection(jdbcUrl, userName, password);
			System.out.println("success");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
