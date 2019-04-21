package com.greydev.messenger.database;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("api/")
public class MyApp extends ResourceConfig {

	public MyApp() {
		packages("com.greydev.messenger");
	}

}
