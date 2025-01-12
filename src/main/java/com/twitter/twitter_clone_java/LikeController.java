package com.twitter.twitter_clone_java;

import java.util.ArrayList;
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
	private final LikeRepository likeRepository;
	private final PostRepository postRepository;
	
	public LikeController (LikeChecker likeChecker, LikeRepository likeRepository, PostRepository postRepository) {
		this.likeChecker = likeChecker;
		this.likeRepository = likeRepository;
		this.postRepository = postRepository;
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
	
	@GetMapping("/grabuserlikes/{profileUserId}")
	public ResponseEntity<List<Post>> getLikedPostsByUserId(@PathVariable Long profileUserId) {
		
		for (int i = 0; i < 20; i++) {
			System.out.println("Liker id is: " + profileUserId);
		}
	
		List<Like> userLikedPosts = likeRepository.findByLikerId(profileUserId);
		List<Long> likedPostIds = new ArrayList<>();
		
		for (Like like : userLikedPosts) {
		    likedPostIds.add(like.getPostId());
		}
		
		List<Post> likedPosts = postRepository.findAllById(likedPostIds);
		
	    return ResponseEntity.ok(likedPosts);
		
	}
	
	
	
}


