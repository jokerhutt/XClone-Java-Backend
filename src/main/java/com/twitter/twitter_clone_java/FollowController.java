package com.twitter.twitter_clone_java;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.transaction.Transactional;

import java.util.Map;

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
		newNotification.setIsRead(false);
		
		followChecker.handleFollowFlag(followingId, followedId, newNotification);
		
		List<Follow> userFollowing = followRepository.findAllByFollowingId(followingId);	
		
		return ResponseEntity.ok(userFollowing);
	}
	
	@GetMapping("/grabuserfollowing/{profileUserId}")
	public ResponseEntity<List<Follow>> getUserFollowingByFollowingId(@PathVariable Long profileUserId) {
		
		List<Follow> userFollowing = followRepository.findAllByFollowingId(profileUserId);	
		
		if (userFollowing == null) {
			return ResponseEntity.ok(new ArrayList<>());
		} else {
			return ResponseEntity.ok(userFollowing);
		}
	}
	
	@GetMapping("/grabuserfollowers/{profileUserId}")
	public ResponseEntity<List<Follow>> getUserFollowersByFollowingId(@PathVariable Long profileUserId) {
		
		List<Follow> userFollowers = followRepository.findAllByFollowedId(profileUserId);	
		
		if (userFollowers == null) {
			return ResponseEntity.ok(new ArrayList<>());
		} else {
			return ResponseEntity.ok(userFollowers);
		}
	}
	
	
	

}
