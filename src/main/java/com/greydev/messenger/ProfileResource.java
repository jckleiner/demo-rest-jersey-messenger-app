package com.greydev.messenger;

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

import com.greydev.messenger.exception.DatabaseOperationException;
import com.greydev.messenger.model.Profile;
import com.greydev.messenger.service.ProfileService;

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
	@Path("/{profileName}")
	public Profile getProfile(@PathParam("profileName") String profileName) {
		System.out.print("GET: getProfile(name: " + profileName + ")");
		profileService.getProfile(profileName);
		return null;
	}

	@POST
	public Profile addProfile(Profile profile) throws DatabaseOperationException {
		System.out.println("POST: addProfile(profile: " + profile.getProfileName() + ", " + profile.getFirstName() + ")");
		return null;
	}

	@PUT
	@Path("/{profileName}")
	public Profile updateProfile(@PathParam("profileName") String profileName, Profile profile)
			throws DatabaseOperationException {
		System.out.println("PUT: updateProfile(Name: " + profileName + ")");
		return null;
	}

	@DELETE
	@Path("/{profileName}")
	public Profile deleteProfile(@PathParam("profileName") String profileName) throws DatabaseOperationException {
		System.out.println("DELETE: deleteProfile(Name: " + profileName + ")");
		return null;
	}

}
