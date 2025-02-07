package com.twitter.twitter_clone_java;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;

@Component
public class NotificationHandler {

	private NotificationRepository notificationRepository;

	public NotificationHandler (NotificationRepository notificationRepository) {
		this.notificationRepository = notificationRepository;
	}

	public void handleNewNotification (Notification passedNotification) {
		this.notificationRepository.save(passedNotification);
	}

	@Transactional
	public void handleDeleteNotification (Notification passedNotification) {
		System.out.println("PASSED NOTIFICATION IS: " + passedNotification.toString());
		Optional <Notification> findToDelete = notificationRepository.findByReceiverIdAndSenderIdAndNotificationObjectAndNotificationType(passedNotification.getReceiverId(), passedNotification.getSenderId(), passedNotification.getNotificationObject(), passedNotification.getNotificationType());

		if (findToDelete.isPresent()) {
			System.out.println("DELETING NOTIFICATION " + findToDelete.get().toString());
			this.notificationRepository.delete(findToDelete.get());
		}
		System.out.println("DELETING NOTIFICATION ERROR CANT FIND");
	}

	public List<Notification>  grabUserNotifications (Long receiverId) {
		return notificationRepository.findAllByReceiverId(receiverId);
	}



//	public void markAsRead (Long id) {
//
//	}



}
