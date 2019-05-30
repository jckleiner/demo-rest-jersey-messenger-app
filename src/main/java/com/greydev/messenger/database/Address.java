package com.greydev.messenger.database;

import javax.persistence.Embeddable;

@Embeddable
public class Address {

	private String address = "this is an address...";

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
