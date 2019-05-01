package com.greydev.messenger.example;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("date/{dateString}")
public class MyDateResource {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getMyDate(@PathParam("dateString") MyDate date) {
		String str = date.getDay() + ", " + date.getMonth() + ", " + date.getYear();
		return "It works! date: " + str;
	}

}
