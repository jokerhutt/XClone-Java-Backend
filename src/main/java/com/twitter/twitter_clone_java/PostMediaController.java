package com.twitter.twitter_clone_java;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.transaction.Transactional;

import java.util.Map;

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
