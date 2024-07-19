package com.hello.shared.model;

import java.io.Serializable;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;


@Entity
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	Long id;
	@Index
	private String username;
	@Index
	private String name;
	@Index
	private String address;
	@Index
	private String phoneNumber;

	public User() {
	}

	public User(String name, String address) {
		this.name = name;
		this.address = address;
	}
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User(String username, String name, String address, String phoneNumber) {
		this.username = username;
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
	}

	
	public User(Long id, String username, String name, String address, String phoneNumber) {
		super();
		this.id = id;
		this.username = username;
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", name=" + name + ", address=" + address
				+ ", phoneNumber=" + phoneNumber + "]";
	}
	
//	public User getUser() {
//		User user = new User();
//		user.setName(name);
//		user.setAddress(address);
//		user.setPhoneNumber(phoneNumber);
//		user.setUsername(username);
//		return user;
//	}
	
	
}
