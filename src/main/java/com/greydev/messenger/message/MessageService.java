package com.greydev.messenger.message;

import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.List;

import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.greydev.messenger.database.DatabaseMock;
import com.greydev.messenger.exception.DataNotFoundException;
import com.greydev.messenger.exception.InvalidRequestDataException;
import com.greydev.messenger.message.comment.CommentResource;
import com.greydev.messenger.profile.ProfileResource;

public class MessageService {

	private static final Logger LOG = LoggerFactory.getLogger(MessageService.class);
	private static long idCount = 5;

	public List<Message> getAllMessages() {
		//		return DatabaseMock.getAllMessagesAsList();
		return DatabaseMock.getAllMessagesAsListHibernate();
	}

	public Message getMessage(Long id) throws DataNotFoundException, UnknownHostException {
		Message message = DatabaseMock.getMessage(id);

		if (message == null) {
			throw new DataNotFoundException("GET", "/messages/" + id);
		}
		return message;
	}

	public Message addMessage(UriInfo uriInfo, Message message) throws InvalidRequestDataException {
		//		Message newMessage = new Message(getNextId(), message.getAuthor(), message.getText());
		Message newMessage = new Message(message.getAuthor(), message.getText());

		if (!isMessageValid(newMessage)) {
			throw new InvalidRequestDataException("POST", "/messages");
		}
		//		newMessage.addLink(getUriForSelf(uriInfo, newMessage), "self");
		//		newMessage.addLink(getUriForProfile(uriInfo, newMessage), "profile");
		//		newMessage.addLink(getUriForComments(uriInfo, newMessage), "comments");

		//		DatabaseMock.addMessage(newMessage.getId(), newMessage);
		DatabaseMock.addMessageHibernate(newMessage);
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

	private String getUriForSelf(UriInfo uriInfo, Message message) {
		return uriInfo.getBaseUriBuilder().path(MessageResource.class).path(Long.toString(message.getId())).toString();
	}

	private String getUriForProfile(UriInfo uriInfo, Message message) {
		return uriInfo.getBaseUriBuilder().path(ProfileResource.class).path(message.getAuthor()).toString();
	}

	private String getUriForComments(UriInfo uriInfo, Message message) {
		return uriInfo.getBaseUriBuilder().path(MessageResource.class).path(MessageResource.class, "getCommentResource")
				.path(CommentResource.class).resolveTemplate("messageId", message.getId()).toString();
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
