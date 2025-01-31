package com.twitter.twitter_clone_java;

import java.time.LocalDateTime;

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
@Table(name = "replies")
public class Reply {

	@Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  @Column(name = "id")
	  private Long id;

	  @ManyToOne
	  @JoinColumn(name = "post_id")
	  @JsonBackReference
	  private Post post;

	  @Column(name = "receiver_id")
	  private Long replyReceiverId;

	  @Column(name = "created_by_user_id")
	  private Long replySenderId;

	  @Column(name = "reply_text")
	  private String replyText;

	  @Column(name = "created_datetime", updatable = false, insertable = false)
	  private LocalDateTime createdAt;

	  public Reply () {}

	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public Post getPost() {
	        return post;
	    }

		public Long getPostId() {
		    return this.post != null ? this.post.getPostId() : null;
		}

	    public void setPost(Post post) {
	        this.post = post;
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

		  public LocalDateTime getCreatedAt() {
			    return createdAt;
			}




}
