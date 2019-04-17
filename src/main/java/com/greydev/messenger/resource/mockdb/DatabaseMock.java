package com.greydev.messenger.resource.mockdb;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.greydev.messenger.resource.model.Message;
import com.greydev.messenger.resource.model.Profile;

public class DatabaseMock {
	
	private static final Map<Long, Message> messageMap = new HashMap<>();
	private static final Map<String, Profile> profileMap = new HashMap<>();
	
	
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
			// TODO change Date to GC
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
