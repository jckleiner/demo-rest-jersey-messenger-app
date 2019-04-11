package com.greydev.messenger_demo.mockdb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.greydev.messenger_demo.model.Message;

public class DatabaseMock {
	
	private static final Map<Long, Message> messageMap = new HashMap<>();
//	private static final Map<Long, Profile> messageMap = new HashMap<>();
	
	
	public static List<Message> getAllMessages() {
		return new ArrayList<Message>(messageMap.values());
	}
	
	public static Message getMessage(Long id) {
		return messageMap.get(id);
	}
	
	public static Message addMessage(Long id, Message message) {
		return messageMap.put(id, message);
	}
	
	public static Message deleteMessage(Long id) {
		return messageMap.remove(id);
	}
	
	public static Message updateMessage(Message message) {
		return messageMap.replace(message.getId(), message);
	}
	


}
