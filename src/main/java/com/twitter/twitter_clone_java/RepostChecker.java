package com.twitter.twitter_clone_java;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public class RepostChecker {
	
	private final RepostRepository repostRepository;
	private final NotificationHandler notificationHandler;
	
	public RepostChecker(RepostRepository repostRepository, NotificationHandler notificationHandler) {
		this.repostRepository = repostRepository;
		this.notificationHandler = notificationHandler;
	}
	
	public void handleRepostFlag(Long postId, Long reposterId, Notification newNotification) {
		
		Optional<Repost> existingRepost = repostRepository.findRepostByPostIdAndReposterId(postId, reposterId);
		
		if (existingRepost.isPresent()) {
			repostRepository.delete(existingRepost.get());
			notificationHandler.handleDeleteNotification(newNotification);
		} else {
			Repost newRepost = new Repost();
			newRepost.setPostId(postId);
			newRepost.setReposterId(reposterId);
			repostRepository.save(newRepost);
			notificationHandler.handleNewNotification(newNotification);
		}
		
	}
	
	public List<Repost> fetchPostReposts (Long postId) {
		List <Repost> refreshedReposts = repostRepository.findByPostId(postId);
		if (refreshedReposts == null) {
			return new ArrayList<>();
		} else {
			return refreshedReposts;
		}
	}
	
	
	

}
