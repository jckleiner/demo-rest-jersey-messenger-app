package com.greydev.messenger.database;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.greydev.messenger.message.Message;
import com.greydev.messenger.profile.Profile;

public class DatabaseMock {

	private static final Map<Long, Message> messageMap = new HashMap<>();
	private static final Map<String, Profile> profileMap = new HashMap<>();

	// saving some dummy messages and profiles to the database
	static {
		Message message1 = new Message(0L, "Can", "Such a lovely weather today!", new GregorianCalendar(2015, 11, 11));
		Message message2 = new Message(1L, "Jason", "I own a grocery store!");
		Profile profile1 = new Profile("jk", "Can", "thx");
		Profile profile2 = new Profile("johny", "jo", "Eyw");

		DatabaseMock.addMessage(message1.getId(), message1);
		DatabaseMock.addMessage(message2.getId(), message2);
		DatabaseMock.addProfile(profile1.getProfileName(), profile1);
		DatabaseMock.addProfile(profile2.getProfileName(), profile2);
	}

	public static List<Message> getAllMessagesAsList() {
		return new ArrayList<Message>(messageMap.values());
	}

	public static Map<Long, Message> getAllMessagesAsMap() {
		return messageMap;
	}

	public static Message getMessage(Long id) {
		return messageMap.get(id);
	}

	public static void addMessage(Long id, Message message) {
		messageMap.put(id, message);
	}

	public static Message deleteMessage(Long id) {
		return messageMap.remove(id);
	}

	public static Message updateMessage(Message message) {
		return messageMap.replace(message.getId(), message);
	}

	public static List<Message> getAllMessagesForYear(int year) {
		List<Message> resultSet = new ArrayList<>();

		for (Message message : getAllMessagesAsList()) {
			if (message.getCreated().get(Calendar.YEAR) == year) {
				resultSet.add(message);
			}
		}
		return resultSet;
	}

	public static List<Profile> getAllProfiles() {
		return new ArrayList<Profile>(profileMap.values());
	}

	public static Profile getProfile(String profileName) {
		return profileMap.get(profileName);
	}

	public static void addProfile(String profileName, Profile profile) {
		profileMap.put(profileName, profile);
	}

	public static Profile deleteProfile(String profileName) {
		return profileMap.remove(profileName);
	}

	public static Profile updateProfile(Profile profile) {
		return profileMap.replace(profile.getProfileName(), profile);
	}

}
