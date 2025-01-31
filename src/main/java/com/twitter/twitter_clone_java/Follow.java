package com.twitter.twitter_clone_java;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "followers")
public class Follow {

    @Id //Always requires, indicated ID is a primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "following_user_id")
	  private Long followingId;

	  @Column(name = "followed_user_id")
	  private Long followedId;

	  public Follow() {}

	  public Long getId() {return this.id;}
	  public Long getFollowingId() {return this.followingId;}
	  public Long getFollowedId() {return this.followedId;}

	  public void setId(Long id) {this.id = id;}
	  public void setFollowingId(Long followingId) {this.followingId = followingId;}
	  public void setFollowedId(Long followedId) { this.followedId = followedId;}





}
