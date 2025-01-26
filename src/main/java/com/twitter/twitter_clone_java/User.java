package com.twitter.twitter_clone_java;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	
    @Id //Always requires, indicated ID is a primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Indicates SQL should auto generate
    private Long id;
	
	private String username;
	
	private String email;
	
	private String bio;
	
	private String profilePic;
	
	private String displayName;
	
	private String backGround;
	  
	@JsonIgnore
	private String password;

	
	public User() {}
	
	public String getUsername() {return this.username;}
	public String getPassword() {return this.password;}
	public String getEmail() {return this.email;}
	public String getBio() {return this.bio;}
	public String getProfilePic() {return this.profilePic;}
	public String getDisplayName() {return this.displayName;}
	public String getBackGround() {return this.backGround;}
	public Long getId() {return this.id;}
	
	public void setUsername(String username) {this.username = username;}
	public void setPassword(String password) {this.password = password;}
	public void setEmail(String email) {this.email = email;}
	public void setBio(String bio) {this.bio = bio;}
	public void setProfilePic(String profilePic) {this.profilePic = profilePic;}
	public void setDisplayName(String displayName) {this.displayName = displayName;}
	public void setBackGround(String backGround) {this.backGround = backGround;}
	public void setId(Long id) {this.id = id;}
	
}
	