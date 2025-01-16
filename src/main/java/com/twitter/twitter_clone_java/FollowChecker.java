package com.twitter.twitter_clone_java;

import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public class FollowChecker {
	
	private final FollowRepository followRepository;
	private final NotificationHandler notificationHandler;
	
	public FollowChecker(FollowRepository followRepository, NotificationHandler notificationHandler) {
		this.followRepository = followRepository;
		this.notificationHandler = notificationHandler;
	}
	
	public void handleFollowFlag(Long followingId, Long followedId, Notification newNotification) {
		
		Optional<Follow> existingFollow = followRepository.findByFollowingIdAndFollowedId(followingId, followedId);
		
		if (existingFollow.isPresent()) {
			followRepository.delete(existingFollow.get());
			notificationHandler.handleDeleteNotification(newNotification);
		} else {
			Follow newFollow = new Follow();
			newFollow.setFollowingId(followingId);
			newFollow.setFollowedId(followedId);
			followRepository.save(newFollow);
			notificationHandler.handleNewNotification(newNotification);
		}
		
	}
	
	
	

}
