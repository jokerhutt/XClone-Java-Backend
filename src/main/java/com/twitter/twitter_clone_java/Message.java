package com.twitter.twitter_clone_java;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "messages")
public class Message {

    @Id //Always requires, indicated ID is a primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Indicates SQL should auto generate
    private Long id;

	@Column(name = "sender_id")
	private Long senderId;

	@Column(name = "receiver_id")
	private Long receiverId;

	@Column(name = "conversation_id")
	private Long conversationId;

	@Column(name = "message_text")
	private String messageText;

	  @Column(name = "created_at", updatable = false, insertable = false)
	  private LocalDateTime createdAt;

	public Message () {}

	public Long getId() {
	    return id;
	}

	public void setId(Long id) {
	    this.id = id;
	}

	public Long getSenderId() {
	    return senderId;
	}

	public void setSenderId(Long senderId) {
	    this.senderId = senderId;
	}

	public Long getReceiverId() {
	    return receiverId;
	}

	public void setReceiverId(Long receiverId) {
	    this.receiverId = receiverId;
	}

	public Long getConversationId() {
	    return conversationId;
	}

	public void setConversationId(Long conversationId) {
	    this.conversationId = conversationId;
	}

	public String getMessageText() {
	    return messageText;
	}

	public void setMessageText(String messageText) {
	    this.messageText = messageText;
	}

	  public LocalDateTime getCreatedAt() {
		    return createdAt;
		}
	  
	  @Override
	  public String toString() {
	      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	      String formattedCreatedAt = (createdAt != null) ? createdAt.format(formatter) : null;

	      return "{" +
	             "\"id\": " + id + "," +
	             "\"senderId\": " + senderId + "," +
	             "\"receiverId\": " + receiverId + "," +
	             "\"conversationId\": " + conversationId + "," +
	             "\"messageText\": \"" + messageText + "\"," +
	             "\"createdAt\": \"" + formattedCreatedAt + "\"" +
	             "}";
	  }

}
