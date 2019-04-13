package com.greydev.messenger.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.greydev.messenger.exception.DatabaseOperationException;
import com.greydev.messenger.mockdb.DatabaseMock;
import com.greydev.messenger.model.Message;
import com.greydev.messenger.model.Profile;

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
	
	
	public Profile getProfile(String profileName) {
		return DatabaseMock.getProfile(profileName);
	}
	
	// profileName is required
	public Profile addProfile(Profile profile)  {
		Profile newProfile = new Profile(profile.getProfileName(), profile.getFirstName(), profile.getLastName());
		DatabaseMock.addProfile(profile.getProfileName(), profile);
		return newProfile;
	}
	
	public Profile deleteProfile(Profile profile) throws DatabaseOperationException {
		Profile response = DatabaseMock.deleteProfile(profile.getProfileName());
		// Send back a message 'message with requested id is not found'
		if (response == null) {
			throw new DatabaseOperationException();
		}
		// return the deleted profile
		return response;
	}
	
	public Profile updateProfile(Profile profile) throws DatabaseOperationException {
		DatabaseMock.updateProfile(profile);
		return profile;
	}

	// mandatory properties: Author, Text
	public boolean isProfileValid(Profile profile) {
		return StringUtils.isNoneBlank(profile.getProfileName(), profile.getFirstName(), profile.getLastName());
	}
	
	public boolean doesProfileNameExist(String profileName) {
		return (DatabaseMock.getProfile(profileName) != null);
	}
	
	
	

}
