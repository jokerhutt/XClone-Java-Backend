package com.twitter.twitter_clone_java;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "likes")
public class Like {
	
    @Id //Always requires, indicated ID is a primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Indicates SQL should auto generate
    private Long id;
	
	  @ManyToOne
	  @JoinColumn(name = "post_id")
	  @JsonBackReference
	  private Post post;
	
	@Column(name = "user_id")
	private Long likerId;
	
	public Like () {};
	
	public Post getPost() {return this.post;}
	public Long getLikerId() {return this.likerId;}
	
	public void setPost(Post post) {this.post = post;}
	public void setLikerId(Long likerId) {this.likerId = likerId;}
	
	public Long getPostId() {
	    return this.post != null ? this.post.getPostId() : null;
	}
	

}
