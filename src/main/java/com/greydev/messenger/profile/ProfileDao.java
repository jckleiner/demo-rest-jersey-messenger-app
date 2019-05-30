package com.greydev.messenger.profile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.greydev.messenger.database.SessionFactorySingleton;

public class ProfileDao {

	private static SessionFactory factory = SessionFactorySingleton.getSessionFactory();
	private static final Map<String, Profile> profileMap = new HashMap<>();

	public static List<Profile> getAllProfiles() {
		return new ArrayList<Profile>(profileMap.values());
	}

	public static Profile getProfile(String profileName) {
		return profileMap.get(profileName);
	}

	public static void addProfile(String profileName, Profile profile) {
		// limit capacity
		if (getAllProfiles().size() >= 200) {
			return;
		}
		profileMap.put(profileName, profile);
	}

	public static Profile deleteProfile(String profileName) {
		return profileMap.remove(profileName);
	}

	public static Profile updateProfile(Profile profile) {
		return profileMap.replace(profile.getProfileName(), profile);
	}

}
