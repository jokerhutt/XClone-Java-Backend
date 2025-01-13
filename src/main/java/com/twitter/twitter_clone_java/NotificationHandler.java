package com.twitter.twitter_clone_java;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public class NotificationHandler {
	
	private NotificationRepository notificationRepository;
	
	public NotificationHandler (NotificationRepository notificationRepository) {
		this.notificationRepository = notificationRepository;
	}
	
	public void handleNewNotification (Notification passedNotification) {
		this.notificationRepository.save(passedNotification);
	}
	
	public void handleDeleteNotification (Notification passedNotification) {
		this.notificationRepository.delete(passedNotification);
	}
	
	public List<Notification>  grabUserNotifications (Long receiverId) {
		return notificationRepository.findAllByReceiverId(receiverId);
	}
	
	
	
//	public void markAsRead (Long id) {
//		
//	}
	


}
