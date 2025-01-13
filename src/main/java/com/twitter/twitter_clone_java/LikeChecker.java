package com.twitter.twitter_clone_java;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public class LikeChecker {
	
	private final LikeRepository likeRepository;
	private final NotificationHandler notificationHandler;
	
	public LikeChecker(LikeRepository likeRepository, NotificationHandler notificationHandler) {
		this.likeRepository = likeRepository;
		this.notificationHandler = notificationHandler;
	}
	
	public void handleLikeFlag(Long postId, Long likerId, Notification newNotification) {
		
		Optional<Like> existingLike = likeRepository.findByPostIdAndLikerId(postId, likerId);
		
		if (existingLike.isPresent()) {
			likeRepository.delete(existingLike.get());
			notificationHandler.handleDeleteNotification(newNotification);
		}
		else {
			Like newLike = new Like();
			newLike.setPostId(postId);
			newLike.setLikerId(likerId);
			likeRepository.save(newLike);
			notificationHandler.handleNewNotification(newNotification);
		}
	}
	
	public List<Like> fetchPostLikes (Long postId) {
		List<Like> refreshedLikes = likeRepository.findByPostId(postId);
	    if (refreshedLikes == null) {
	        return new ArrayList<>();
	    } else {
			return refreshedLikes;
	    }
	}
	
	
	

}
