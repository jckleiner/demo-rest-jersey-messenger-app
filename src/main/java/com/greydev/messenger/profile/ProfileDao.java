package com.greydev.messenger.profile;

import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.greydev.messenger.SessionFactorySingleton;
import com.greydev.messenger.post.Post;
import com.greydev.messenger.post.comment.Comment;

public class ProfileDao {

	private static SessionFactory factory = SessionFactorySingleton.getSessionFactory();

	//saving some dummy posts and profiles to the database
	static {

		Profile profile1 = new Profile("Such profile", "pol", "pia");

		Post post1 = new Post("can", "Such a lovely weather today!", new GregorianCalendar(2015, 11, 11), profile1);
		Post post2 = new Post("jason", "I own a grocery store!", new GregorianCalendar(2011, 04, 04), profile1);

		post1.getComments().add(new Comment("Johny", "First Comment", post1));
		post1.getComments().add(new Comment("Emily", "Grocery store", post1));

		post2.getComments().add(new Comment("Sally", "Hey there", post2));
		post2.getComments().add(new Comment("Sally2", "Hey there2", post2));

		// set bi-directional relationships
		profile1.getPosts().add(post1);
		profile1.getPosts().add(post2);
		post1.setProfile(profile1);
		post2.setProfile(profile1);

		ProfileDao.addProfile(profile1);

	}

	public static List<Profile> getAllProfiles() {
		List<Profile> results = null;
		final Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();

			results = session.createQuery("from Profile").list();

			transaction.commit();
			session.close();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
			results = null;
		}
		return results;

	}

	public static Profile getProfile(String profileName) {
		Profile profile = null;
		final Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();

			profile = session.get(Profile.class, profileName);

			transaction.commit();
			session.close();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
			profile = null;
		}
		return profile;
	}

	public static void addProfile(Profile profile) {
		final Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();

			// TODO handle child entities
			session.save(profile);

			transaction.commit();
			session.close();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}

	}

	public static Profile deleteProfile(String profileName) {
		Profile profileToDelete = null;
		final Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();

			// TODO handle child entities
			// TODO how to handle this properly?
			profileToDelete = getProfile(profileName);
			if (profileToDelete != null) {
				session.delete(profileToDelete);
			}

			transaction.commit();
			session.close();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
			profileToDelete = null;
		}
		return profileToDelete;
	}

	public static Profile updateProfile(Profile profile) {

		final Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();

			// TODO handle child entities
			session.save(profile);

			transaction.commit();
			session.close();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();

		}
		return null;

	}

}
