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

	    if (postMedia == null || postMedia.isEmpty()) {
	        return; // No media to add
	    }

		if (postMedia.size() > 0) {

			Optional <Post> tempPost = postRepository.findByPostId(postId);
			if (tempPost.isPresent()) {
				Post postObjectTempPost = tempPost.get();

				for (int i = 0; i < postMedia.size(); i++) {
					String currentPostMedia = postMedia.get(i);
					int numberPosition = i + 1;
					Long convertedPosition = (long) numberPosition;

					PostMedia newPostMedia = new PostMedia();
					newPostMedia.setMediaFile(currentPostMedia);
					newPostMedia.setPosition(convertedPosition);
					newPostMedia.setPost(postObjectTempPost);

					postMediaRepository.save(newPostMedia);

			}



			}
		}

	}

}
