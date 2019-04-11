package com.greydev.messenger_demo;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.greydev.messenger_demo.model.Message;
import com.greydev.messenger_demo.service.MessageService;

@Path("/messages")
public class MessageResource {
	
	private static final MessageService messageService= new MessageService();
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public List<Message> getAllMessages() {
		return messageService.getAllMessages();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_XML)
	public List<Message> addMessage(Message message) {
		return null;
	}

}
