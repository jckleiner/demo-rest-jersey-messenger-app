package com.greydev.messenger.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HibernateTest {

	private static final SessionFactory factory = new Configuration()
			.configure("hibernate.cfg.xml")
			.buildSessionFactory();

	public static void main(String[] args) {

		final Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();

			User user1 = new User("Can", "555", 26);
			User user2 = new User("Ahmet", "222", 28);

			Address address1 = new Address();
			address1.setAddress("Address of Can");
			Address address2 = new Address();
			address2.setAddress("Address of Ahmet");

			user1.getAddress().add(address1);
			user2.getAddress().add(address2);

			session.persist(user1);
			session.persist(user2);

			transaction.commit();
			session.close();

			//			foo();

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			factory.close();
		}
	}

	//	public static void foo() {
	//		System.out.println("starting foo...\n");
	//		final Session session = factory.openSession();
	//		Transaction transaction = null;
	//		try {
	//			transaction = session.beginTransaction();
	//
	//			User u = session.get(User.class, 1);
	//			System.out.printf("user name: %s\n", u.getUserName());
	//
	//			List<User> users = session.createQuery("from User").list();
	//
	//			transaction.commit();
	//			session.close();
	//
	//			User u1 = users.get(0);
	//
	//			List<Vehicle> v = u.getVehicles();
	//			v.size();
	//			users.forEach(user -> {
	//				System.out.println(user.getUserName() + ", " + user.getAddress().getAddress());
	//			});
	//
	//			System.out.println("\nending foo...");
	//		} catch (Exception e) {
	//			e.printStackTrace();
	//			if (transaction != null) {
	//				transaction.rollback();
	//			}
	//		}
	//
	//	}

}
