package com.twitter.twitter_clone_java;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public class ReplyHandler {
	
	private final ReplyRepository replyRepository;
	private final PostRepository postRepository;
	private final NotificationHandler notificationHandler;
	
	public  ReplyHandler (PostRepository postRepository, ReplyRepository replyRepository, NotificationHandler notificationHandler) {
		this.replyRepository = replyRepository;
		this.notificationHandler = notificationHandler;
		this.postRepository = postRepository;
	}
	
	public Optional<Reply> handleNewReply (Long postId, Long replyReceiverId, Long replySenderId, String replyText) {
		
		Reply newReply = new Reply();
		
		Optional<Post> existingPost = postRepository.findByPostId(postId);
		if (existingPost.isPresent()) {
			newReply.setPost(existingPost.get());
			newReply.setReplyReceiverId(replyReceiverId);
			newReply.setReplySenderId(replySenderId);
			newReply.setReplyText(replyText);
			
			replyRepository.save(newReply);
		}
		
		return replyRepository.findById(newReply.getId());
		
	}
	
	public  List<Reply> fetchRepliesList (Long replyObjectId) {
		List<Reply> fetchedPostReplies = replyRepository.findAllByPostPostId(replyObjectId);
		if (fetchedPostReplies == null) {
			return new ArrayList<>();
		} else {
			return fetchedPostReplies;
		}
		
	}
	
	public  List<Reply> fetchRepliesByUser (Long senderId) {
		List<Reply> fetchedUserReplies = replyRepository.findAllByReplySenderId(senderId);
		if (fetchedUserReplies == null) {
			return new ArrayList<>();
		} else {
			return fetchedUserReplies;
		}
		
	}
	
	public Optional<Reply> fetchReplyById (Long id) {
		Optional<Reply> fetchedReply = replyRepository.findById(id);
			return fetchedReply;
	}

}
