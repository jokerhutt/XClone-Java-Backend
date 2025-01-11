package com.twitter.twitter_clone_java;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "posts")
public class Post {
	
	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  @Column(name = "post_id")
	  private Long postId;
	  
	  @Column(name = "creator_id")
	  private Long creatorId;
	  
	  @Column(name = "post_text")
	  private String postText;
	  
	  @Column(name = "created_at")
	  private String createdAt;
	  
	  public Post () {};
	  
	  
	  public Long getPostId() {return this.postId;}
	  public Long getCreatorId() {return this.creatorId;}
	  public String getPostText() {return this.postText;}
	  public String getCreatedAt() {return this.createdAt;}
	  
	  public void setPostId(Long postId) {this.postId = postId;}
	  public void setCreatorId(Long creatorId) {this.creatorId = creatorId;}
	  public void setPostText(String postText) {this.postText = postText;}
	  public void setCreatedAt(String createdAt) {this.createdAt = createdAt;}
	  
	    @Override
	    public String toString() {
	        return "{" +
	               "\"postId\": " + postId + "," +
	               "\"creatorId\": " + creatorId + "," +
	               "\"postText\": \"" + postText + "\"," +
	               "\"createdAt\": \"" + createdAt + "\"" +
	               "}";
	    }
}
