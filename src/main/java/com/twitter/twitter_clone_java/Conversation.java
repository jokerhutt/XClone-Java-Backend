package com.twitter.twitter_clone_java;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "conversations")
public class Conversation {

    @Id //Always requires, indicated ID is a primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Indicates SQL should auto generate
    private Long id;
    
	@Column(name = "user1_id")
	private Long user1Id;
	
	@Column(name = "user2_id")
	private Long user2Id;
	
	@Column(name = "last_message_timestamp")
	private String lastMessageTimeStamp;
	
	public Conversation() {};
	
	public Long getId() {
	    return id;
	}

	public void setId(Long id) {
	    this.id = id;
	}

	public Long getUser1Id() {
	    return user1Id;
	}

	public void setUser1Id(Long user1Id) {
	    this.user1Id = user1Id;
	}

	public Long getUser2Id() {
	    return user2Id;
	}

	public void setUser2Id(Long user2Id) {
	    this.user2Id = user2Id;
	}

	public String getLastMessageTimeStamp() {
	    return lastMessageTimeStamp;
	}

	public void setLastMessageTimeStamp(String lastMessageTimeStamp) {
	    this.lastMessageTimeStamp = lastMessageTimeStamp;
	}
	
	
}
