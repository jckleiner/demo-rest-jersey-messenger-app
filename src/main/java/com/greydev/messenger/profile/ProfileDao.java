package com.greydev.messenger.profile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.greydev.messenger.SessionFactorySingleton;

public class ProfileDao {

	private static SessionFactory factory = SessionFactorySingleton.getSessionFactory();
	private static final Map<String, Profile> profileMap = new HashMap<>();

	public static List<Profile> getAllProfiles() {
		return new ArrayList<Profile>(profileMap.values());
	}

	public static Profile getProfile(String profileName) {
		return profileMap.get(profileName);
	}

	public static void addProfile(Profile profile) {
		final Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();

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
		return profileMap.remove(profileName);
	}

	public static Profile updateProfile(Profile profile) {
		return profileMap.replace(profile.getProfileName(), profile);
	}

}
