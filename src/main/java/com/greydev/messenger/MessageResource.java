package com.greydev.messenger;

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

import com.greydev.messenger.exception.DatabaseOperationException;
import com.greydev.messenger.model.Message;
import com.greydev.messenger.service.MessageService;

@Path("/messages")
@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public class MessageResource {
	
	private static final MessageService messageService= new MessageService();
	
	@GET
	public List<Message> getAllMessages() {
		System.out.println("GET: getAllMessages");
		return messageService.getAllMessages();
	}
	
	@GET
	@Path("/{messageId}")
	public Message getMessage(@PathParam("messageId") Long messageId) {
		System.out.print("GET: getMessage(id: " + messageId + ")");
		return messageService.getMessage(messageId);
	}
	
	@POST
	public Message addMessage(Message message) throws DatabaseOperationException {
		System.out.println("POST: addMessage(message: " + message.getAuthor() + ", " + message.getText() + ")");
		return messageService.addMessage(message);
	}
	
	@PUT
	@Path("/{messageId}")
	public Message updateMessage(@PathParam("messageId") Long messageId, Message message) throws DatabaseOperationException {
		System.out.println("PUT: updateMessage(id: " + messageId + ")");
		return messageService.updateMessage(messageId, message);
	}
	
	@DELETE
	@Path("/{messageId}")
	public Message deleteMessage(@PathParam("messageId") Long messageId) throws DatabaseOperationException {
		System.out.println("DELETE: deleteMessage(id: " + messageId + ")");
		return messageService.deleteMessage(messageId);
	}

}
