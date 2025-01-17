package com.twitter.twitter_clone_java;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "bookmarks")
public class BookMark {
	
	@Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  @Column(name = "id")
	  private Long id;
	
	  @Column(name = "user_id")
	  private Long userId;
	  
	  @Column(name = "post_id")
	  private Long postId;
	  
	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }
	    
	    public Long getUserId() {
	        return userId;
	    }

	    public void setUserId(Long userId) {
	        this.userId = userId;
	    }

	    public Long getPostId() {
	        return postId;
	    }

	    public void setPostId(Long postId) {
	        this.postId = postId;
	    }

	  
	  
}
