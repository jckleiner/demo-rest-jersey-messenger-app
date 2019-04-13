package com.greydev.messenger_demo.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.greydev.messenger_demo.exception.DatabaseOperationException;
import com.greydev.messenger_demo.mockdb.DatabaseMock;
import com.greydev.messenger_demo.model.Message;

public class MessageService {

	private static long idCount = 0;

	// saving some dummy messages to the database
	static {
		Message message1 = new Message(getNextId(), "Can", "Such a lovely weather today!");
		Message message2 = new Message(getNextId(), "Ahmet", "I own a grocery store!");

		DatabaseMock.addMessage(message1.getId(), message1);
		DatabaseMock.addMessage(message2.getId(), message2);
	}

	public List<Message> getAllMessages() {
		return DatabaseMock.getAllMessages();
	}
	
	public Message getMessage(Long id) {
		return DatabaseMock.getMessage(id);
	}
	
	public Message addMessage(Message message)  {
		Message newMessage = new Message(getNextId(), message.getAuthor(), message.getText());
		DatabaseMock.addMessage(newMessage.getId(), newMessage);
		return newMessage;
	}
	
	public Message deleteMessage(Long id) throws DatabaseOperationException {
		Message response = DatabaseMock.deleteMessage(id);
		// Send back a message 'message with requested id is not found'
		if (response == null) {
			throw new DatabaseOperationException();
		}
		return response;
	}
	
	public Message updateMessage(Message message) throws DatabaseOperationException {
		DatabaseMock.updateMessage(message);
		return message;
	}

	// mandatory properties: Author, Text
	public boolean isMessageValid(Message message) {
		return StringUtils.isNoneBlank(message.getAuthor(), message.getText());
	}
	
	public boolean doesIdExist(Long id) {
		return (DatabaseMock.getMessage(id) != null);
	}
	

	private static long getNextId() {
		return idCount++;
	}

}
