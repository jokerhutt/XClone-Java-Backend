package com.twitter.twitter_clone_java;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "postmedia")
public class PostMedia {

	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  @Column(name = "id")
	  private Long id;
	  
	  @Column(name = "post_id")
	  private Long postId;
	  
	  @Column(name = "media_file")
	  private String mediaFile;
	  
	  public PostMedia() {};
	  
	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public Long getPostId() {
	        return postId;
	    }

	    public void setPostId(Long postId) {
	        this.postId = postId;
	    }

	    public String getMediaFile() {
	        return mediaFile;
	    }

	    public void setMediaFile(String mediaFile) {
	        this.mediaFile = mediaFile;
	    }
	
}
