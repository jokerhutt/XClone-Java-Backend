package com.twitter.twitter_clone_java;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public class PostMediaHandler {
	
	private final PostMediaRepository postMediaRepository;
	private final PostRepository postRepository;
	
	public PostMediaHandler (PostRepository postRepository, PostMediaRepository postMediaRepository) {
		this.postMediaRepository = postMediaRepository;
		this.postRepository = postRepository;
	}
	
	public void handleAddingPostMedia (List <String> postMedia, Long postId) {
		
		if (postMedia.size() > 0) {
			
			for (int i = 0; i < postMedia.size(); i++) {
				String currentPostMedia = postMedia.get(i);
				PostMedia newPostMedia = new PostMedia();
				newPostMedia.setMediaFile(currentPostMedia);
				newPostMedia.setPostId(postId);
				postMediaRepository.save(newPostMedia);
			}
		}
		
	}

}
