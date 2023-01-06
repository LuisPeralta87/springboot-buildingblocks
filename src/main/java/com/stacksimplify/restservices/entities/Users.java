package com.stacksimplify.restservices.entities;

import java.util.List;

import jakarta.persistence.Column;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

//Entity
@Entity
@Table (name= "Users")
public class Users {
	
	@Id
	@GeneratedValue
	private Long Id;
	
	@NotEmpty(message="Username is a mandatory field, please provide one")
	@Column(name = "USER_NAME" , length=50, nullable=false, unique=true)
	private String username;
	
	@Size(min=2, message="FirstName should have atleast 2 characters")
	@Column(name = "FIRST_NAME" , length=50, nullable=false)
	private String firstName;
	
	@Column(name = "LAST_NAME" , length=50, nullable=false)
	private String lastName;
	@Column(name = "EMAIL_ADDRESS" , length=50, nullable=false)
	private String email;
	@Column(name = "ROLE" , length=50, nullable=false)
	private String role;
	@Column(name = "SSN" , length=50, nullable=false, unique=true)
	private String ssn;
	
	@OneToMany(mappedBy="user")
	private List<Order> orders;
	
	//No Arguments Constructor
	public Users() {
		
	}

	//Fileds Constructor
	public Users(Long id, String username, String firstName, String lastName, String email, String role, String ssn) {
		Id = id;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.role = role;
		this.ssn = ssn;
	}

	//Getters and Setters
	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	
	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	//To String
	@Override
	public String toString() {
		return "Users [Id=" + Id + ", username=" + username + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", role=" + role + ", ssn=" + ssn + "]";
	}
		
	
	
	

}
