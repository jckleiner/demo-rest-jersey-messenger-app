package com.greydev.messenger.message;

import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.greydev.messenger.database.DatabaseMock;
import com.greydev.messenger.exception.DataNotFoundException;
import com.greydev.messenger.exception.InvalidRequestDataException;

public class MessageService {

	private static final Logger LOG = LoggerFactory.getLogger(MessageService.class);
	private static long idCount = 5;

	public List<Message> getAllMessages() {
		return DatabaseMock.getAllMessagesAsList();
	}

	public Message getMessage(Long id) throws DataNotFoundException, UnknownHostException {
		Message message = DatabaseMock.getMessage(id);

		if (message == null) {
			throw new DataNotFoundException("GET", "/messages/" + id);
		}
		return message;
	}

	public Message addMessage(Message message) throws InvalidRequestDataException {
		Message newMessage = new Message(getNextId(), message.getAuthor(), message.getText());

		if (!isMessageValid(newMessage)) {
			throw new InvalidRequestDataException("POST", "/messages");
		}
		DatabaseMock.addMessage(newMessage.getId(), newMessage);
		return newMessage;
	}

	public Message updateMessage(Long queryParamMessageId, Message message) throws InvalidRequestDataException {
		// ignore the id inside the received message, use the url param id
		message.setId(queryParamMessageId);

		if (!isMessageValid(message)) {
			throw new InvalidRequestDataException("PUT", "/messages");
		}

		if (doesMessageExist(message.getId())) {
			DatabaseMock.updateMessage(message);
			return message;
		}
		else {
			DatabaseMock.addMessage(message.getId(), message);
			return message;
		}
	}

	public Message deleteMessage(Long id) throws DataNotFoundException {
		Message response = DatabaseMock.deleteMessage(id);

		if (response == null) {
			throw new DataNotFoundException("DELETE", "/messages/" + id);
		}
		return response;
	}

	public List<Message> getAllMessagesForYear(int year) {
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);

		if (year > currentYear) {
			LOG.info("passed year parameter is not valid");
			return null;
		}
		return DatabaseMock.getAllMessagesForYear(year);
	}

	public List<Message> getAllMessagesPaginated(int start, int size) {
		List<Message> messageList = DatabaseMock.getAllMessagesAsList();

		if ((start + size) >= messageList.size()) {
			return messageList.subList(start, messageList.size());
		}
		return messageList.subList(start, start + size);
	}

	// mandatory properties: Author, Text
	public boolean isMessageValid(Message message) {
		return StringUtils.isNoneBlank(message.getAuthor(), message.getText());
	}

	public boolean doesMessageExist(Long id) {
		return (DatabaseMock.getMessage(id) != null);
	}

	private static long getNextId() {
		return idCount++;
	}

}
