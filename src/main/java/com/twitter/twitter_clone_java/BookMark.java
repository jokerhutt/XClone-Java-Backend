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
@Table(name = "bookmarks")
public class BookMark {
	
	@Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  @Column(name = "id")
	  private Long id;
	
	  @Column(name = "user_id")
	  private Long userId;
	  
	  @ManyToOne
	  @JoinColumn(name = "post_id")
	  @JsonBackReference
	  private Post post;
	  
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

		public Post getPost() {return this.post;}

		public void setPost(Post post) {this.post = post;}
		
		public Long getPostId() {
		    return this.post != null ? this.post.getPostId() : null;
		}

	  
	  
}
