package com.twitter.twitter_clone_java;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public class RepostChecker {
	
	private final RepostRepository repostRepository;
	
	public RepostChecker(RepostRepository repostRepository) {
		this.repostRepository = repostRepository;
	}
	
	public void handleRepostFlag(Long postId, Long reposterId) {
		
		Optional<Repost> existingRepost = repostRepository.findRepostByPostIdAndReposterId(postId, reposterId);
		
		if (existingRepost.isPresent()) {
			repostRepository.delete(existingRepost.get());
		} else {
			Repost newRepost = new Repost();
			newRepost.setPostId(postId);
			newRepost.setReposterId(reposterId);
			repostRepository.save(newRepost);
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
