package com.greydev.messenger_demo.mockdb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.greydev.messenger_demo.model.Message;
import com.greydev.messenger_demo.model.Profile;

public class DatabaseMock {
	
	private static final Map<Long, Message> messageMap = new HashMap<>();
	private static final Map<String, Profile> profileMap = new HashMap<>();
	
	
	public static List<Message> getAllMessages() {
		return new ArrayList<Message>(messageMap.values());
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
	
	// **************************************************
	
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
