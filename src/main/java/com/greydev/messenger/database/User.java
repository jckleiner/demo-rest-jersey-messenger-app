package com.greydev.messenger.database;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "user_id")
	private int userID;

	@Column(name = "user_name")
	private String userName = "userName123";

	@OneToOne(cascade = CascadeType.PERSIST)
	private Address address = new Address();

	@OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	private List<Vehicle> vehicles = new ArrayList<>();

	public User() {

	}

	public Address getAddress() {
		return address;
	}

	public List<Vehicle> getVehicles() {
		return vehicles;
	}

	public void setVehicles(List<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public User(String userName, String phoneNum, int age) {
		this.userName = userName;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
