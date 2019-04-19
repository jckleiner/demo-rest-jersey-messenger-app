package com.greydev.messenger.profile;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.greydev.messenger.database.DatabaseMock;
import com.greydev.messenger.exception.DataNotFoundException;
import com.greydev.messenger.exception.InvalidRequestDataException;

public class ProfileService {

	private static final Logger LOG = LoggerFactory.getLogger(ProfileService.class);

	public List<Profile> getAllProfiles() {
		return DatabaseMock.getAllProfiles();
	}

	public Profile getProfile(String profileName) throws DataNotFoundException {
		Profile newProfile = DatabaseMock.getProfile(profileName);
		if (newProfile == null) {
			throw new DataNotFoundException("GET", "/profiles/" + profileName);
		}
		return DatabaseMock.getProfile(profileName);
	}

	// profileName is required
	public Profile addProfile(Profile profile) throws InvalidRequestDataException {

		if (!isProfileValid(profile) || doesProfileNameExist(profile.getProfileName())) {
			throw new InvalidRequestDataException("POST", "/profiles");
		}
		Profile newProfile = new Profile(profile.getProfileName(), profile.getFirstName(), profile.getLastName());
		DatabaseMock.addProfile(newProfile.getProfileName(), newProfile);
		return newProfile;
	}

	public Profile updateProfile(String queryParamProfileName, Profile profile)
			throws InvalidRequestDataException, DataNotFoundException {
		// replace the queryParameterName with the profile name if it exist
		profile.setProfileName(queryParamProfileName);

		if (!isProfileValid(profile)) {
			throw new InvalidRequestDataException("PUT", "/profiles");
		}
		else if (!doesProfileNameExist(profile.getProfileName())) {
			throw new DataNotFoundException("PUT", "/profiles/" + profile.getProfileName());
		}
		Profile newProfile = new Profile(profile.getProfileName(), profile.getFirstName(), profile.getLastName());
		DatabaseMock.updateProfile(newProfile);
		return newProfile;
	}

	public Profile deleteProfile(String profileName) throws DataNotFoundException {
		Profile response = DatabaseMock.deleteProfile(profileName);
		if (response == null) {
			throw new DataNotFoundException("DELETE", "/profiles/" + profileName);
		}
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
