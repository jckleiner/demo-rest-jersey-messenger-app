package com.greydev.messenger;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public final class SessionFactorySingleton {

	private static SessionFactory sessionFactory = new Configuration()
			.configure("hibernate.cfg.xml")
			.buildSessionFactory();

	private SessionFactorySingleton() {
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
