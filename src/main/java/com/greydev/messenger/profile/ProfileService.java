package com.greydev.messenger.profile;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.greydev.messenger.database.DatabaseMock;

public class ProfileService {
	
	//saving some dummy profiles to the database
	static {
		Profile profile1 = new Profile("jk", "Can", "thx");
		Profile profile2 = new Profile("johny", "jo", "Eyw");

		DatabaseMock.addProfile(profile1.getProfileName(), profile1);
		DatabaseMock.addProfile(profile2.getProfileName(), profile2);
	}
	
	private static final Logger LOG = LoggerFactory.getLogger(ProfileService.class);

	public List<Profile> getAllProfiles() {
		return DatabaseMock.getAllProfiles();
	}
	
	public Profile getProfile(String profileName) {
		Profile newProfile = DatabaseMock.getProfile(profileName);
		if (newProfile == null) {
			LOG.info("No profile with name '{}' found.", profileName);
			//TODO proper error message response
			return new Profile("ERROR", "profile with '" + profileName + "' does not exist", "");
		}
		return DatabaseMock.getProfile(profileName);
	}
	
	// profileName is required
	public Profile addProfile(Profile profile)  {
		
		if (!isProfileValid(profile)) {
			LOG.info("Not a valid profile, missing properties");
			return new Profile("ERROR", "profile is missing required properties", "");
		} 
		else if (doesProfileNameExist(profile.getProfileName())) {
			LOG.info("Profile name {} already exist", profile.getProfileName());
			return new Profile("ERROR", "Profile name already exist", "");
		}
		Profile newProfile = new Profile(profile.getProfileName(), profile.getFirstName(), profile.getLastName());
		DatabaseMock.addProfile(newProfile.getProfileName(), newProfile);
		return newProfile;
	}

	public Profile updateProfile(String queryParamProfileName, Profile profile) {
		// replace the queryParameterName with the profile name if it exist
		profile.setProfileName(queryParamProfileName);
		
		if (!isProfileValid(profile)) {
			LOG.info("Not a valid profile, missing properties");
			return new Profile("ERROR", "profile is missing required properties", "");
		} 
		else if (!doesProfileNameExist(profile.getProfileName())) {
			LOG.info("Profile with name {} does not exist", profile.getProfileName());
			return new Profile("ERROR", "Profile name does not exist", "");
		}
		Profile newProfile = new Profile(profile.getProfileName(), profile.getFirstName(), profile.getLastName());
		DatabaseMock.updateProfile(newProfile);
		return newProfile;
	}

	public Profile deleteProfile(String profileName) {
		Profile response = DatabaseMock.deleteProfile(profileName);
		// Send back a message 'message with requested id is not found'
		if (response == null) {
			LOG.info("Profile with name {} does not exist", profileName);
			return new Profile("ERROR", "Profile name does not exist", "");
		}
		// return the deleted profile
		LOG.info("Succesfully deleted profile '{}'", profileName);
		return response;
	}
	
	// mandatory properties: Author, Text
	public boolean isProfileValid(Profile profile) {
		return StringUtils.isNoneBlank(profile.getProfileName(), profile.getFirstName(), profile.getLastName());
	}
	
	public boolean doesProfileNameExist(String profileName) {
		return (DatabaseMock.getProfile(profileName) != null);
	}
	
}
