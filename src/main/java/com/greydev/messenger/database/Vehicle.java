package com.greydev.messenger.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Vehicle {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "vehichle_id")
	private int vehicleId;

	@Column(name = "vehicle_name")
	private String vehicleName;

	//	@ManyToMany(mappedBy="vehicleList")
	//	private List<User> userList = new ArrayList<>();

	//@JoinColumn(name="user_Id")

	public int getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getVehicleName() {
		return vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}
	//	public List<User> getUserList() {
	//		return userList;
	//	}
	//	public void setUserList(List<User> userList) {
	//		this.userList = userList;
	//	}

}
