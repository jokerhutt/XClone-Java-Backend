package com.twitter.twitter_clone_java;

import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class PostFinder {
	
	private final PostRepository postRepository;
	
	public PostFinder(PostRepository postRepository) {
		this.postRepository = postRepository;
	}
	
	public Post findPostById(Long postId) {
		return postRepository.findByPostId(postId)
				.orElseThrow(() -> new RuntimeException
					("Post not found for post id: " + postId));
	}
	
	public Post findByCreatorId(Long creatorId) {
	    return postRepository.findByCreatorId(creatorId)
	            .orElseThrow(() -> new RuntimeException(
	                "Post not found for creator id: " + creatorId));
	}
	
	

}
