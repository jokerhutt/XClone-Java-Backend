package com.twitter.twitter_clone_java;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:5173")


@RestController
@RequestMapping("/api")
public class PostMediaController {

	private final PostMediaHandler postMediaHandler;
	private final PostMediaRepository postMediaRepository;

	public PostMediaController (PostMediaHandler postMediaHandler, PostMediaRepository postMediaRepository) {
		this.postMediaHandler = postMediaHandler;
		this.postMediaRepository = postMediaRepository;
	}


//	@GetMapping("grabpostmedia/{postID}")
//	public ResponseEntity<List<PostMedia>> getAllPostMediaByPostId(@PathVariable Long postID) {
//		List<PostMedia> fetchedPostMedia = postMediaRepository.findAllByPostPostId(postID);
//
//		if (fetchedPostMedia == null) {
//			return ResponseEntity.ok(new ArrayList<>());
//		} else {
//			return ResponseEntity.ok(fetchedPostMedia);
//		}
//	}



}
