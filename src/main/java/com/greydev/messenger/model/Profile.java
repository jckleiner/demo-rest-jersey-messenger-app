package com.greydev.messenger.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Profile {
	
	private String profileName;
	private String firstName;
	private String lastName;
	@XmlElement
	private Date created;

	public Profile() {
	
	}

	public Profile(String profileName, String firstName, String lastName) {
		super();
		this.profileName = profileName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.created = new Date();
	}
	
	public Profile(String profileName, String firstName, String lastName, Date date) {
		super();
		this.profileName = profileName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.created = date;
	}

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getCreated() {
		return created;
	}
	
	public void setCreated(Date date) {
		created = date;
	}

}
