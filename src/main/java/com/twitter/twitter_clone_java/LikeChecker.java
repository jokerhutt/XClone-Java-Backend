package com.twitter.twitter_clone_java;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public class LikeChecker {
	
	private final LikeRepository likeRepository;
	
	public LikeChecker(LikeRepository likeRepository) {
		this.likeRepository = likeRepository;
	}
	
	public void handleLikeFlag(Long postId, Long likerId) {
		
		Optional<Like> existingLike = likeRepository.findByPostIdAndLikerId(postId, likerId);
		
		if (existingLike.isPresent()) {
			likeRepository.delete(existingLike.get());
		}
		else {
			Like newLike = new Like();
			newLike.setPostId(postId);
			newLike.setLikerId(likerId);
			likeRepository.save(newLike);
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
