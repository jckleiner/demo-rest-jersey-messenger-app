package com.greydev.messenger_demo.service;

import java.util.List;

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
	
	public void deleteMessage(Long id) throws DatabaseOperationException {
		Message response = DatabaseMock.deleteMessage(id);
		if (response == null) {
			throw new DatabaseOperationException();
		}
	}
	
	public void updateMessage(Message message) throws DatabaseOperationException {
		Message response = DatabaseMock.updateMessage(message);
		if (response == null) {
			throw new DatabaseOperationException();
		}
	}
	

	private static long getNextId() {
		return idCount++;
	}

}
