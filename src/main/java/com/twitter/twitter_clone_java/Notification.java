package com.twitter.twitter_clone_java;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "notifications")


public class Notification {

    @Id //Always requires, indicated ID is a primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "notification_type")
	  private String notificationType;

	  @Column(name = "notification_object_id")
	  private Long notificationObject;

	  @Column(name = "receiver_id")
	  private Long receiverId;

	  @Column(name = "sender_id")
	  private Long senderId;

	  @Column(name = "created_at")
	  private String createdAt;

	  @Column(name = "read")
	  private Long isRead;

	  public Notification () {}

	  public Long getId() {return this.id;}
	  public String getNotificationType() {return this.notificationType;}
	  public Long getNotificationObject() {return this.notificationObject;}
	  public Long getReceiverId() {return this.receiverId;}
	  public Long getSenderId() {return this.senderId;}
	  public String getCreatedAt() {return this.createdAt;}
	  public Long getIsRead() {return this.isRead;}

	  public void setId(Long id) {this.id = id;}
	  public void setNotificationObject(Long notificationObject) {this.notificationObject = notificationObject;}
	  public void setNotificationType(String notificationType) {this.notificationType = notificationType;}
	  public void setReceiverId(Long receiverId) {this.receiverId = receiverId;}
	  public void setSenderId(Long senderId) {this.senderId = senderId;}
	  public void setCreatedAt(String createdAt) {this.createdAt = createdAt;}
	  public void setIsRead(Long isRead) {this.isRead = isRead;}

	    @Override
	    public String toString() {
	        return "{" +
	               "\"notificationType\": " + notificationType + "," +
	               "\"notificationObject\": " + notificationObject + "," +
	               "\"receiverId\": \"" + receiverId + "\"," +
	               "\"senderId\": \"" + senderId + "\"," +
	               "\"createdAt\": \"" + createdAt + "\"," +
	               "\"isRead\": \"" + isRead + "\"" +
	               "}";
	    }


}
