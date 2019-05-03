package com.greydev.messenger.example;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@Path("/stealingdata")
public class LeakedInfoProcesser {

	@GET
	public String getData(@QueryParam("data") String userData) {

		// save user data to database

		return "Saved user data: " + userData;
	}

}

//@Path("/")
//public class LeakedInfoProcesser {
//
//	private static final List<String> dataList = new ArrayList<>();
//
//	@GET
//	public String getData(@QueryParam("data") String userData) {
//		String result = "Saved user data: ";
//		if (userData != null) {
//			dataList.add(userData);
//		}
//
//		for (String s : dataList) {
//			result += s + " ";
//		}
//
//		return result;
//	}
//
//}
