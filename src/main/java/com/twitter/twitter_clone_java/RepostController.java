package com.twitter.twitter_clone_java;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.transaction.Transactional;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")


@RestController
@RequestMapping("/api")
public class RepostController {
	
	private final RepostChecker repostChecker;
	private final NotificationHandler notificationHandler;
	
	public RepostController (RepostChecker repostChecker, NotificationHandler notificationHandler) {
		this.repostChecker = repostChecker;
		this.notificationHandler = notificationHandler;
	}
	
	@Transactional
	@PostMapping("/newrepost")
	public ResponseEntity<?> repost(@RequestBody Map<String, Object> data) {
		
		System.out.println("Received like data " + data);
		
		Long postId = ((Number) data.get("postId")).longValue();
		Long reposterId = ((Number) data.get("reposterId")).longValue();
		
		Notification newNotification = new Notification();
		newNotification.setNotificationType((String) data.get("notificationType"));
		newNotification.setNotificationObject(Long.valueOf(data.get("notificationObject").toString()));
		newNotification.setReceiverId(Long.valueOf(data.get("receiverId").toString()));
		newNotification.setSenderId(Long.valueOf(data.get("senderId").toString()));
		newNotification.setIsRead(false);
		
		repostChecker.handleRepostFlag(postId, reposterId, newNotification);
		List <Repost> packagedReposts = repostChecker.fetchPostReposts(postId);
		return ResponseEntity.ok(packagedReposts);	
	}
	
	@GetMapping("/postreposts/{POSTID}")
	public ResponseEntity<List<Repost>> getRepostsByPostId(@PathVariable Long POSTID) {
		
		for (int i = 0; i < 20; i++) {
			System.out.println("REPOST POST IS id is: " + POSTID);
		}
		
		List<Repost> fetchedReposts = repostChecker.fetchPostReposts(POSTID);
		return ResponseEntity.ok(fetchedReposts);
	}
	

	
	

}
