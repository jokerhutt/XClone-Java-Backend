package com.twitter.twitter_clone_java;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "followers")
public class Follow {

    @Id //Always requires, indicated ID is a primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "following_user_id")
    @JsonBackReference
    private User followingUser;

    @ManyToOne
    @JoinColumn(name = "followed_user_id")
    @JsonBackReference
    private User followedUser;

	  public Follow() {}

	  public Long getId() {return this.id;}
	  public User getFollowingUser() { return followingUser; }
	  public User getFollowedUser() { return followedUser; }

	  public void setId(Long id) {this.id = id;}
	  public void setFollowingUser(User followingUser) { this.followingUser = followingUser; }
	  public void setFollowedUser(User followedUser) { this.followedUser = followedUser; }

	    public Long getFollowingId() {
	        return this.followingUser != null ? this.followingUser.getId() : null;
	    }

	    public Long getFollowedId() {
	        return this.followedUser != null ? this.followedUser.getId() : null;
	    }

	    @Override
	    public String toString() {
	        return "Follow{" +
	               "id=" + id +
	               ", followingUserId=" + (followingUser != null ? followingUser.getId() : "null") +
	               ", followedUserId=" + (followedUser != null ? followedUser.getId() : "null") +
	               '}';
	    }



}
