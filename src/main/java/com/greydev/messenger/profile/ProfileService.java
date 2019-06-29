package com.greydev.messenger.profile;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.greydev.messenger.exception.DataNotFoundException;
import com.greydev.messenger.exception.InvalidRequestDataException;

public class ProfileService {

	private static final Logger LOG = LoggerFactory.getLogger(ProfileService.class);

	public List<Profile> getAllProfiles() {
		return ProfileDao.getAllProfiles();
	}

	public Profile getProfile(String profileName) throws DataNotFoundException {
		Profile newProfile = ProfileDao.getProfile(profileName);
		if (newProfile == null) {
			throw new DataNotFoundException("GET", "/profiles/" + profileName);
		}
		return newProfile;
	}

	// profileName is required
	public Profile addProfile(Profile profile) throws InvalidRequestDataException {

		// Handling bi-directional multichild relationships
		profile.getPosts().forEach(post -> {
			post.setId(null);
			post.setProfile(profile);
			post.getComments().forEach(comment -> {
				comment.setId(null);
				comment.setPost(post);
			});
		});

		if (!isProfileValid(profile) || doesProfileNameExist(profile.getProfileName())) {
			throw new InvalidRequestDataException("POST", "/profiles");
		}
		ProfileDao.addProfile(profile);
		return profile;
	}

	public Profile updateProfile(String queryParamProfileName, Profile profile)
			throws InvalidRequestDataException, DataNotFoundException {

		if (profile == null) {
			throw new InvalidRequestDataException("POST", "profile cant be null");
		}
		if (queryParamProfileName == null) {
			throw new InvalidRequestDataException("POST", "queryParamProfileName cant be null");
		}
		// replace the queryParameterName with the profile name if it exist
		profile.setProfileName(queryParamProfileName);

		if (!isProfileValid(profile)) {
			throw new InvalidRequestDataException("PUT", "/profiles");
		}

		profile.setProfileName(queryParamProfileName); // use the name given in the URL
		profile.getPosts().forEach(post -> { // set bi-directional relations
			post.setProfile(profile);
			post.setId(null);
			post.getComments().forEach(comment -> {
				comment.setPost(post);
				comment.setId(null);
			});
		});

		if (doesProfileNameExist(profile.getProfileName())) {
			ProfileDao.updateProfile(profile);
			return profile;
		}
		ProfileDao.addProfile(profile);
		return profile;
	}

	public Profile deleteProfile(String profileName) throws DataNotFoundException {
		Profile response = ProfileDao.deleteProfile(profileName);
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
		return (ProfileDao.getProfile(profileName) != null);
	}

}
