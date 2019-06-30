package com.greydev.messenger.example;

import java.util.Calendar;
import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("shortDate/")
public class Test {

	@GET
	@Produces("text/shortdate")
	public Date getMyDate() {
		return Calendar.getInstance().getTime();
	}

}