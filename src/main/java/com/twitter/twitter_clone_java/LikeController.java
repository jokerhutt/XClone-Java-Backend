package com.twitter.twitter_clone_java;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.transaction.Transactional;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")


@RestController
@RequestMapping("/api")
public class LikeController {
	
	private final LikeChecker likeChecker;
	
	public LikeController (LikeChecker likeChecker) {
		this.likeChecker = likeChecker;
	}
	
	@Transactional
	@PostMapping("/newlike")
	public ResponseEntity<?> like(@RequestBody Map<String, Object> data) {
	
		System.out.println("Received like data " + data);
		
		Long postId = ((Number) data.get("postId")).longValue();
		Long likerId = ((Number) data.get("likerId")).longValue();
		
		likeChecker.handleLikeFlag(postId, likerId);
		
		List<Like> packagedLike = likeChecker.fetchPostLikes(postId);
		
		return ResponseEntity.ok(packagedLike);
		
	}
	
	@GetMapping("/postlikes/{POSTID}")
	public ResponseEntity<List<Like>> getLikesByPostId(@PathVariable Long POSTID) {
		
		for (int i = 0; i < 20; i++) {
			System.out.println("LIKE POST IS id is: " + POSTID);
		}
		
		List<Like> fetchedLikes = likeChecker.fetchPostLikes(POSTID);
		return ResponseEntity.ok(fetchedLikes);
	}
}


