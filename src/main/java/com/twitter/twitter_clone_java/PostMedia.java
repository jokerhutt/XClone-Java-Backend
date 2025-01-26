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
@Table(name = "postmedia")
public class PostMedia {

	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  @Column(name = "id")
	  private Long id;
	  
	  @ManyToOne
	  @JoinColumn(name = "post_id")
	  @JsonBackReference
	  private Post post;
	  
	  @Column(name = "media_file")
	  private String mediaFile;
	  
	  @Column(name = "position")
	  private Long position;
	  
	  public PostMedia() {};
	  
	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }
	    
	    public Long getPosition() {
	    	return position;
	    }
	    
	    public void setPosition(Long position) {
	    	this.position = position;
	    }

	    public Post getPost() {
	        return post;
	    }

	    public void setPost(Post post) {
	        this.post = post;
	    }

	    public String getMediaFile() {
	        return mediaFile;
	    }

	    public void setMediaFile(String mediaFile) {
	        this.mediaFile = mediaFile;
	    }
	
}
