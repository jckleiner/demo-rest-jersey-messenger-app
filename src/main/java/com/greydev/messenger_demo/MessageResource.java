package com.greydev.messenger_demo;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.greydev.messenger_demo.exception.DatabaseOperationException;
import com.greydev.messenger_demo.model.Message;
import com.greydev.messenger_demo.service.MessageService;

@Path("/messages")
public class MessageResource {
	
	private static final MessageService messageService= new MessageService();
	
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<Message> getAllMessages() {
		System.out.println("GET: getAllMessages");
		return messageService.getAllMessages();
	}
	
	@GET
	@Path("/{messageId}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Message getMessage(@PathParam("messageId") Long messageId) {
		System.out.print("GET: getMessage(id: " + messageId + ")");
		Message message = messageService.getMessage(messageId);
		
		//TODO if no message is found, return a proper exception message
		if (message == null) {
			return new Message(888L, "ERROR", "id is not found");
		}
		else {
			System.out.println(" -> returning: " + message.getAuthor() + ", " + message.getText());
		}
		return message;
	}
	
	@POST
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Message addMessage(Message message) throws DatabaseOperationException {
		System.out.println("POST: addMessage(message: " + message.getAuthor() + ", " + message.getText() + ")");
		if (messageService.isMessageValid(message)) {
			return messageService.addMessage(message);
		}
		//TODO if the message is not valid, return a proper exception message
		return new Message(111L, "ERROR", "Invalid message, some properties are missing");
	}
	
	@PUT
	@Path("/{messageId}")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Message updateMessage(@PathParam("messageId") Long messageId, Message message) throws DatabaseOperationException {
		System.out.println("PUT: updateMessage(id: " + messageId + ")");
		message.setId(messageId);
		
		// ignore the id inside the received message, use the url param id
		// TODO proper exception
		if (messageService.isMessageValid(message)) {
			if (messageService.doesIdExist(message.getId())) {
				return messageService.updateMessage(message);
			}
			else {
				return messageService.addMessage(message);
			}
		}
		return new Message(111L, "ERROR", "Invalid message, some properties are missing");
	}
	
	@DELETE
	@Path("/{messageId}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Message deleteMessage(@PathParam("messageId") Long messageId) throws DatabaseOperationException {
		System.out.println("DELETE: deleteMessage(id: " + messageId + ")");
		return messageService.deleteMessage(messageId);
	}

}
