package com.greydev.messenger.profile;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Path("/profiles")
@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public class ProfileResource {

	private static final Logger LOG = LoggerFactory.getLogger(ProfileResource.class);
	private static final ProfileService profileService = new ProfileService();

	@GET
	public List<Profile> getAllProfiles() {
		LOG.info("GET: getAllProfiles");
		return profileService.getAllProfiles();
	}

	@GET
	@Path("/{profileName}")
	public Profile getProfile(@PathParam("profileName") String profileName) {
		LOG.info("GET: getProfile(name: {})", profileName);
		return profileService.getProfile(profileName);
	}

	@POST
	public Profile addProfile(Profile profile) {
		LOG.info("POST: addProfile(profile: {}, {})", profile.getProfileName(), profile.getFirstName());
		return profileService.addProfile(profile);
	}

	@PUT
	@Path("/{profileName}")
	public Profile updateProfile(@PathParam("profileName") String profileName, Profile profile) {
		LOG.info("PUT: updateProfile(Name: {})", profileName);
		return profileService.updateProfile(profileName, profile);
	}

	@DELETE
	@Path("/{profileName}")
	public Profile deleteProfile(@PathParam("profileName") String profileName) {
		LOG.info("DELETE: deleteProfile(Name: {})", profileName);
		return profileService.deleteProfile(profileName);
	}

}
