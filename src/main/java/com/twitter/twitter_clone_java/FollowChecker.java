package com.twitter.twitter_clone_java;

import java.util.Optional;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;

@Component
public class FollowChecker {

	private final FollowRepository followRepository;
	private final NotificationHandler notificationHandler;
	private final UserRepository userRepository;

	public FollowChecker(UserRepository userRepository, FollowRepository followRepository, NotificationHandler notificationHandler) {
		this.followRepository = followRepository;
		this.notificationHandler = notificationHandler;
		this.userRepository = userRepository;
	}

	@Transactional
	public void handleFollowFlag(Long followingId, Long followedId, Notification newNotification) {
		System.out.println("INITIATING FOLLOWCHECKER");
		Optional<Follow> existingFollow = followRepository.findByFollowingUserIdAndFollowedUserId(followingId, followedId);
		if (existingFollow.isPresent()) {
			User existingFollowedUser = existingFollow.get().getFollowedUser();
			existingFollowedUser.getFollowerList().remove(existingFollow.get());
			User existingFollowingUser = existingFollow.get().getFollowingUser();
			existingFollowingUser.getFollowingList().remove(existingFollow.get());

		    userRepository.save(existingFollowedUser);
		    userRepository.save(existingFollowingUser);

			System.out.println("DELETING IMPORTANT STUFF THIS IS GOOD");
			followRepository.delete(existingFollow.get());
			followRepository.flush();
			System.out.println("BEEP BEEP BOOP ATTEMPTED DELETE");
			notificationHandler.handleDeleteNotification(newNotification);
		} else {
			Follow newFollow = new Follow();
			Optional <User> followingUser = userRepository.findById(followingId);
			Optional <User> followedUser = userRepository.findById(followedId);
			if (followingUser.isPresent() && followedUser.isPresent()) {
				User unpackedFollowingUser = followingUser.get();
				User unpackedFollowedUser = followedUser.get();

				newFollow.setFollowingUser(unpackedFollowingUser);
				newFollow.setFollowedUser(unpackedFollowedUser);
			}




			followRepository.save(newFollow);
			notificationHandler.handleNewNotification(newNotification);
		}

	}




}
