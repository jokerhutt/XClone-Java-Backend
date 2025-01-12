package com.twitter.twitter_clone_java;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "likes")
public class Like {
	
    @Id //Always requires, indicated ID is a primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Indicates SQL should auto generate
    private Long id;
	
	@Column(name = "post_id")
	private Long postId;
	
	@Column(name = "user_id")
	private Long likerId;
	
	public Like () {};
	
	public Long getPostId() {return this.postId;}
	public Long getLikerId() {return this.likerId;}
	
	public void setPostId(Long postId) {this.postId = postId;}
	public void setLikerId(Long likerId) {this.likerId = likerId;}
	

}
