package com.twitter.twitter_clone_java;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;


@CrossOrigin(origins = "http://localhost:5173")

@RestController
@RequestMapping("/api")
public class GrabUser {
	
	private final PostRepository postRepository;
	private final PostFinder postFinder;
	private final UserRepository userRepository;
	private final UserFinder userFinder;
	private final RepostRepository repostRepository;
	
	public GrabUser(PostRepository postRepository, PostFinder postFinder,
			UserRepository userRepository, UserFinder userFinder, RepostRepository repostRepository) {
		this.postRepository = postRepository;
		this.postFinder = postFinder;
		this.userFinder = userFinder;
		this.userRepository = userRepository;
		this.repostRepository = repostRepository;
	}
	
	@GetMapping("/users/{postId}")
	public ResponseEntity<User> getUserByPostId(@PathVariable Long postId) {
		Post tempPost = postFinder.findPostById(postId);
		
		Long tempCreatorId = tempPost.getCreatorId();
		
		User tempUser = userFinder.findUserById(tempCreatorId);
		
		return ResponseEntity.ok(tempUser);
		
	}
	
	@GetMapping("/grabusers/{profileUserId}")
	public ResponseEntity<User> getUserByUserId(@PathVariable Long profileUserId) {
		
		User fetchedProfileUser = userFinder.findUserById(profileUserId);
		return ResponseEntity.ok(fetchedProfileUser);
		
	}
	
	@GetMapping("/grabposts/{profileUserId}")
	public ResponseEntity<List<Post>> getPostByUserId(@PathVariable Long profileUserId) {
		
		for (int i = 0; i < 20; i++) {
			System.out.println("Creator id is: " + profileUserId);
		}
		
		List<Post> fetchedPosts = postRepository.findAllByCreatorId(profileUserId);
		return ResponseEntity.ok(fetchedPosts);
	}
	
	@GetMapping("/grabpostsandreposts/{profileUserId}")
	public ResponseEntity<List<Post>> getPostsAndRepostsByUserId(@PathVariable Long profileUserId) {
		
		List <Post> posts = postRepository.findAllByCreatorId(profileUserId);
		List <Repost> reposts = repostRepository.findRepostByReposterId(profileUserId);
		
		List<Long> repostIds = new ArrayList<>();
		
		if (reposts != null) {
			for (Repost repost : reposts) {
				repostIds.add(repost.getPostId());
			}
		}

		
		List <Post> originalReposts = postRepository.findAllById(repostIds);
		
		if (originalReposts != null) {
				posts.addAll(originalReposts);
		}
		
		posts.sort(Comparator.comparing(Post::getCreatedAt, Comparator.nullsLast(Comparator.naturalOrder())).reversed());
		
		return ResponseEntity.ok(posts);
	}
	
	
	
	

}
