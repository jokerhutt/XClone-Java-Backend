package com.twitter.twitter_clone_java;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public class RepostChecker {

	private final RepostRepository repostRepository;
	private final NotificationHandler notificationHandler;
	private final PostRepository postRepository;

	public RepostChecker(PostRepository postRepository, RepostRepository repostRepository, NotificationHandler notificationHandler) {
		this.repostRepository = repostRepository;
		this.notificationHandler = notificationHandler;
		this.postRepository = postRepository;
	}

	public void handleRepostFlag(Long postId, Long reposterId, Notification newNotification) {

		Optional<Repost> existingRepost = repostRepository.findRepostByPostPostIdAndReposterId(postId, reposterId);

		if (existingRepost.isPresent()) {
			Post existingPost = existingRepost.get().getPost();
			existingPost.getRepostList().remove(existingRepost.get());

			repostRepository.delete(existingRepost.get());
			notificationHandler.handleDeleteNotification(newNotification);
		} else {
			Repost newRepost = new Repost();
			Optional<Post> existingPost = postRepository.findByPostId(postId);
			if (existingPost.isPresent()) {
				newRepost.setPost(existingPost.get());
				newRepost.setReposterId(reposterId);
				repostRepository.save(newRepost);
				notificationHandler.handleNewNotification(newNotification);
			}

		}

	}

	public List<Repost> fetchPostReposts (Long postId) {
		List <Repost> refreshedReposts = repostRepository.findByPostPostId(postId);
		if (refreshedReposts == null) {
			return new ArrayList<>();
		} else {
			return refreshedReposts;
		}
	}

	public List<Repost> fetchPostRepostsByUserId (Long userId) {
		List <Repost> refreshedReposts = repostRepository.findRepostByReposterId(userId);
		if (refreshedReposts == null) {
			return new ArrayList<>();
		} else {
			return refreshedReposts;
		}
	}




}
