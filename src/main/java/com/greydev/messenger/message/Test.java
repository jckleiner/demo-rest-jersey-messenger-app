package com.greydev.messenger.message;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.greydev.messenger.database.MyDate;

@Path("date/{dateString}")
@Produces(MediaType.TEXT_PLAIN)
public class Test {

	@GET
	public String test(@PathParam("dateString") MyDate date) {
		String str = date.getDay() + ", " + date.getMonth() + ", " + date.getYear();
		return "It works! date: " + str;
	}

}
