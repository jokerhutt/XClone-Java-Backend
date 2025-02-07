package com.twitter.twitter_clone_java;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;

@CrossOrigin(origins = "http://localhost:5173")

@RestController
@RequestMapping("/api")
public class NotificationController {

	private NotificationHandler notificationHandler;
	private PostFinder postFinder;
	private NotificationRepository notificationRepository;

	public NotificationController (NotificationHandler notificationHandler, PostFinder postFinder, NotificationRepository notificationRepository) {
		this.notificationHandler = notificationHandler;
		this.postFinder = postFinder;
		this.notificationRepository = notificationRepository;
	}

	@GetMapping("/notifications/{profileUserId}")
	public ResponseEntity<List<Notification>> grabNotificationsForUser(@PathVariable Long profileUserId) {

		List<Notification> fetchedNotifications = notificationHandler.grabUserNotifications(profileUserId);
		return ResponseEntity.ok(fetchedNotifications);
	}

	@GetMapping("/nonmessagenotifications/{profileUserId}")
	public ResponseEntity<List<Notification>> grabNonMessageNotificationsForUser(@PathVariable Long profileUserId) {

		List<Notification> fetchedNotifications = notificationRepository.findAllByReceiverIdAndNotificationTypeNot(profileUserId, "MESSAGE");
		return ResponseEntity.ok(fetchedNotifications);
	}

	@GetMapping("/messagenotifications/{profileUserId}")
		public ResponseEntity<List<Notification>> grabMessageNotificationsForUser(@PathVariable Long profileUserId) {
			List<Notification> fetchedNotifications = notificationRepository.findAllByReceiverIdAndNotificationTypeAndIsRead(profileUserId, "MESSAGE", 0L);
			return ResponseEntity.ok(fetchedNotifications);
		}

	@GetMapping("/grabnotificationpost/{notificationPostId}")
	public ResponseEntity<Post> grabPostNotification(@PathVariable Long notificationPostId) {
		return ResponseEntity.ok(postFinder.findPostById(notificationPostId));
	}

	@Transactional
	@PostMapping("/markasread")
	public ResponseEntity<?> markAsRead(@RequestBody Map<String, Object> data) {

		Long notificationObject = ((Number) data.get("notificationObjectId")).longValue();
		Long receiverId = ((Number) data.get("receiverId")).longValue();
		Long senderId = ((Number) data.get("senderId")).longValue();
		String notificationType = ((String) data.get("notificationType"));

		List<Notification> fetchedNotifications = notificationRepository.findAllByReceiverIdAndSenderIdAndNotificationObjectAndNotificationType(receiverId, senderId, notificationObject, notificationType);

		for (Notification notification : fetchedNotifications) {
		    System.out.println("BEFORE IS: " + notification.getIsRead());
		    notification.setIsRead(1L);
		}
		ArrayList<Long> placeHolderReponse = new ArrayList<>();
		return ResponseEntity.ok(placeHolderReponse);
	}



}
