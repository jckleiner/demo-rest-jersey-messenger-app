package com.greydev.messenger.profile;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Profile {

	@Id
	@Column(name = "profileName")
	private String profileName;
	@Column(name = "firstName")
	private String firstName;
	@Column(name = "lastName")
	private String lastName;
	@Column(name = "created")
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
