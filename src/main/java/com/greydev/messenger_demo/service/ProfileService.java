package com.greydev.messenger_demo.service;

import java.util.List;

import com.greydev.messenger_demo.mockdb.DatabaseMock;
import com.greydev.messenger_demo.model.Profile;

public class ProfileService {
	
	//saving some dummy profiles to the database
	static {
		Profile profile1 = new Profile("jk", "Can", "Sagol");
		Profile profile2 = new Profile("ahmo", "Ahmet", "Eyw");

		DatabaseMock.addProfile(profile1.getProfileName(), profile1);
		DatabaseMock.addProfile(profile2.getProfileName(), profile2);
	}

	public List<Profile> getAllProfiles() {
		return DatabaseMock.getAllProfiles();
	}
	

}
