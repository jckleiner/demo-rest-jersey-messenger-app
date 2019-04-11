package com.greydev.messenger_demo;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
		return messageService.getAllMessages();
	}
	
	@GET
	@Path("/{messageId}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Message getMessage(@PathParam("messageId") Long messageId) {
		return messageService.getMessage(messageId);
	}
	
	@POST
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Message addMessage(Message message) throws DatabaseOperationException {
		Message newMessage = messageService.addMessage(message);
		return newMessage;
	}

}
