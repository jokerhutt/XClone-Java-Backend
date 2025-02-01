package com.twitter.twitter_clone_java;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id //Always requires, indicated ID is a primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Indicates SQL should auto generate
    private Long id;

	@Column(name = "username")
	private String username;

	@Column(name = "email")
	private String email;

	@Column(name = "bio")
	private String bio;

	@Column(name = "profile_pic")
	private String profilePic;

	@Column(name = "display_name")
	private String displayName;

	@Column(name = "back_ground")
	private String backGround;

	@JsonIgnore
	private String password;

	  @OneToMany(mappedBy = "followingUser", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	  @JsonManagedReference
	  private List<Follow> followingList = new ArrayList<>();

	  @OneToMany(mappedBy = "followedUser", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	  @JsonManagedReference
	  private List<Follow> followerList = new ArrayList<>();




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

	public List<Follow> getFollowingList() {
	    return this.followingList;
	}

	public void setFollowingList(List<Follow> followingList) {
	    this.followingList = followingList;
	}

	public List<Follow> getFollowerList() {
	    return this.followerList;
	}

	public void setFollowerList(List<Follow> followerList) {
	    this.followerList = followerList;
	}

}
