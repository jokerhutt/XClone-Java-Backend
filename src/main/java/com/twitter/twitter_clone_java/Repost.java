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
@Table(name = "reposts")
public class Repost {

    @Id //Always requires, indicated ID is a primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Indicates SQL should auto generate
    private Long id;
    
    @ManyToOne
  	@JoinColumn(name = "post_id")
  	@JsonBackReference
  	private Post post;
	
	@Column(name = "user_id")
	private Long reposterId;
	
	public Repost () {};
	
	public Post getPost() {return this.post;}
	public Long getReposterId() {return this.reposterId;}
	
	public void setPost(Post post) {this.post = post;}
	public void setReposterId(Long reposterId) {this.reposterId = reposterId;}
	
	public Long getPostId() {
	    return this.post != null ? this.post.getPostId() : null;
	}
	
}
