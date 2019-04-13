package com.greydev.messenger_demo;

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

import com.greydev.messenger_demo.exception.DatabaseOperationException;
import com.greydev.messenger_demo.model.Profile;
import com.greydev.messenger_demo.service.ProfileService;

@Path("/profiles")
@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public class ProfileResource {

	private static final ProfileService profileService = new ProfileService();

	@GET
	public List<Profile> getAllProfiles() {
		System.out.println("GET: getAllProfiles");
		return profileService.getAllProfiles();
	}

	@GET
	@Path("/{profileId}")
	public Profile getProfile(@PathParam("profileId") Long profileId) {
		System.out.print("GET: getProfile(id: " + profileId + ")");
		return null;
	}

	@POST
	public Profile addProfile(Profile profile) throws DatabaseOperationException {
		System.out.println("POST: addProfile(profile: " + profile.getProfileName() + ", " + profile.getFirstName() + ")");
		return null;
	}

	@PUT
	@Path("/{profileId}")
	public Profile updateProfile(@PathParam("profileId") Long profileId, Profile profile)
			throws DatabaseOperationException {
		System.out.println("PUT: updateProfile(id: " + profileId + ")");
		return null;
	}

	@DELETE
	@Path("/{profileId}")
	public Profile deleteProfile(@PathParam("profileId") Long profileId) throws DatabaseOperationException {
		System.out.println("DELETE: deleteProfile(id: " + profileId + ")");
		return null;
	}

}
