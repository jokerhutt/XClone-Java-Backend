package com.twitter.twitter_clone_java;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "replies")
public class Reply {
	
	@Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  @Column(name = "id")
	  private Long id;
	
	  @Column(name = "post_id")
	  private Long replyObjectId;
	  
	  @Column(name = "receiver_id")
	  private Long replyReceiverId;
	  
	  @Column(name = "created_by_user_id")
	  private Long replySenderId;
	  
	  @Column(name = "reply_text")
	  private String replyText;
	  
	  @Column(name = "created_datetime")
	  private String createdAt;
	  
	  public Reply () {};
	  
	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public Long getReplyObjectId() {
	        return replyObjectId;
	    }

	    public void setReplyObjectId(Long replyObjectId) {
	        this.replyObjectId = replyObjectId;
	    }

	    public Long getReplyReceiverId() {
	        return replyReceiverId;
	    }

	    public void setReplyReceiverId(Long replyReceiverId) {
	        this.replyReceiverId = replyReceiverId;
	    }

	    public Long getReplySenderId() {
	        return replySenderId;
	    }

	    public void setReplySenderId(Long replySenderId) {
	        this.replySenderId = replySenderId;
	    }

	    public String getReplyText() {
	        return replyText;
	    }

	    public void setReplyText(String replyText) {
	        this.replyText = replyText;
	    }

	    public String getCreatedAt() {
	        return createdAt;
	    }

	    public void setCreatedAt(String createdAt) {
	        this.createdAt = createdAt;
	    }
	  
	 

}
