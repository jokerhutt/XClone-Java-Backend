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
public class FollowController {

	private final FollowChecker followChecker;
	private final FollowRepository followRepository;

	public FollowController (FollowChecker followChecker, FollowRepository followRepository) {
		this.followChecker = followChecker;
		this.followRepository = followRepository;
	}

	@Transactional
	@PostMapping("/newfollow")
	public ResponseEntity<?> follow(@RequestBody Map<String, Object> data) {

		Long followingId = ((Number) data.get("followingId")).longValue();
		Long followedId =((Number) data.get("followedId")).longValue();

		System.out.println("Following ID IS: " + followingId);
		System.out.println("Followed ID IS: " + followedId);

		Notification newNotification = new Notification();
		newNotification.setNotificationType((String) data.get("notificationType"));
		newNotification.setNotificationObject(Long.valueOf(data.get("notificationObject").toString()));
		newNotification.setReceiverId(Long.valueOf(data.get("receiverId").toString()));
		newNotification.setSenderId(Long.valueOf(data.get("senderId").toString()));
		newNotification.setIsRead(0L);

		followChecker.handleFollowFlag(followingId, followedId, newNotification);

		Optional <Follow> foundFollow = followRepository.findByFollowingUserIdAndFollowedUserId(followingId, followedId);
		if (foundFollow.isPresent()) {
			System.out.println("FOUND FOLLOW IS PRESENT " + foundFollow.get().toString());
			return ResponseEntity.ok(foundFollow.get());
		} else {
			return ResponseEntity.ok(new ArrayList<>());
		}
	}

	@GetMapping("/grabuserfollowing/{profileUserId}")
	public ResponseEntity<List<Follow>> getUserFollowingByFollowingId(@PathVariable Long profileUserId) {

		List<Follow> userFollowing = followRepository.findAllByFollowingUserId(profileUserId);

		if (userFollowing == null) {
			return ResponseEntity.ok(new ArrayList<>());
		} else {
			return ResponseEntity.ok(userFollowing);
		}
	}

	@GetMapping("/grabuserfollowers/{profileUserId}")
	public ResponseEntity<List<Follow>> getUserFollowersByFollowingId(@PathVariable Long profileUserId) {

		List<Follow> userFollowers = followRepository.findAllByFollowedUserId(profileUserId);

		if (userFollowers == null) {
			return ResponseEntity.ok(new ArrayList<>());
		} else {
			return ResponseEntity.ok(userFollowers);
		}
	}




}
