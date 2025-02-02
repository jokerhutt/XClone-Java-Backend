package com.twitter.twitter_clone_java;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;

@Component
public class LikeChecker {

	private final LikeRepository likeRepository;
	private final NotificationHandler notificationHandler;
	private final PostRepository postRepository;

	public LikeChecker(PostRepository postRepository, LikeRepository likeRepository, NotificationHandler notificationHandler) {
		this.likeRepository = likeRepository;
		this.notificationHandler = notificationHandler;
		this.postRepository = postRepository;
	}

	@Transactional
	public void handleLikeFlag(Long postId, Long likerId, Notification newNotification) {

		Optional<Like> existingLike = likeRepository.findByPostPostIdAndLikerId(postId, likerId);


		if (existingLike.isPresent()) {
			Post existingPost = existingLike.get().getPost();
			existingPost.getLikeList().remove(existingLike.get());
			likeRepository.delete(existingLike.get());
			notificationHandler.handleDeleteNotification(newNotification);
		}
		else {
			Like newLike = new Like();
			Optional<Post> existingPost = postRepository.findByPostId(postId);
			if (existingPost.isPresent()) {
				newLike.setPost(existingPost.get());
				newLike.setLikerId(likerId);
				likeRepository.save(newLike);
				notificationHandler.handleNewNotification(newNotification);
			}
		}
	}

	public List<Like> fetchPostLikes (Long postId) {
		List<Like> refreshedLikes = likeRepository.findByPostPostId(postId);
	    if (refreshedLikes == null) {
	        return new ArrayList<>();
	    } else {
			return refreshedLikes;
	    }
	}






}
