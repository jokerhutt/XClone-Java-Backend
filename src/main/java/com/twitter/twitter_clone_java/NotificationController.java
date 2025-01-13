package com.twitter.twitter_clone_java;

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
	
	public NotificationController (NotificationHandler notificationHandler, PostFinder postFinder) {
		this.notificationHandler = notificationHandler;
		this.postFinder = postFinder;
	}

	@GetMapping("/notifications/{profileUserId}")
	public ResponseEntity<List<Notification>> grabNotificationsForUser(@PathVariable Long profileUserId) {
		
		List<Notification> fetchedNotifications = notificationHandler.grabUserNotifications(profileUserId);
		return ResponseEntity.ok(fetchedNotifications);
	}
	
	@GetMapping("/grabnotificationpost/{notificationPostId}")
	public ResponseEntity<Post> grabPostNotification(@PathVariable Long notificationPostId) {
		return ResponseEntity.ok(postFinder.findPostById(notificationPostId));
	}
	
	
	
}
