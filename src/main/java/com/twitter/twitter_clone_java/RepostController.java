package com.twitter.twitter_clone_java;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
public class RepostController {

	private final RepostChecker repostChecker;
	private final RepostRepository repostRepository;
	private final NotificationHandler notificationHandler;

	public RepostController (RepostRepository repostRepository, RepostChecker repostChecker, NotificationHandler notificationHandler) {
		this.repostChecker = repostChecker;
		this.notificationHandler = notificationHandler;
		this.repostRepository = repostRepository;
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
		newNotification.setIsRead(0L);

		repostChecker.handleRepostFlag(postId, reposterId, newNotification);

		Optional <Repost> fetchedRepost = repostRepository.findRepostByPostPostIdAndReposterId(postId, reposterId);

		if (fetchedRepost.isPresent()) {
			return ResponseEntity.ok(fetchedRepost.get());
		} else if (fetchedRepost.isEmpty()) {
			return ResponseEntity.ok(new ArrayList<>());
		} else {
			return ResponseEntity.ok(new ArrayList<>());
		}
	}

	@GetMapping("/postreposts/{POSTID}")
	public ResponseEntity<List<Repost>> getRepostsByPostId(@PathVariable Long POSTID) {

		for (int i = 0; i < 20; i++) {
			System.out.println("REPOST POST IS id is: " + POSTID);
		}

		List<Repost> fetchedReposts = repostChecker.fetchPostReposts(POSTID);
		return ResponseEntity.ok(fetchedReposts);
	}

	@GetMapping("/userreposts/{profileUserId}")
	public ResponseEntity<List<Repost>> getRepostsByUserId(@PathVariable Long profileUserId) {

		List<Repost> fetchedReposts = repostChecker.fetchPostRepostsByUserId(profileUserId);
		return ResponseEntity.ok(fetchedReposts);
	}





}
