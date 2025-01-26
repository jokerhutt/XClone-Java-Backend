package com.twitter.twitter_clone_java;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;


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
		
		User tempCreatorId = tempPost.getCreator();
		
		User tempUser = userFinder.findUserById(tempCreatorId.getId());
		
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
	
	@GetMapping("/grabrepost/{profileUserId}")
	public ResponseEntity<Post> grabPostByReposterId(@PathVariable Long profileUserId) {
		
		Repost tempRepost = repostRepository.findSingleRepostByReposterId(profileUserId);
		Long originalPostId = tempRepost.getPostId();
		return ResponseEntity.ok(postFinder.findPostById(originalPostId));
		
	}
	
	@GetMapping("/posts/{postId}")
	public ResponseEntity<Post> grabPostByPostId(@PathVariable Long postId) {
		Long originalPostId = postId.longValue();
		return ResponseEntity.ok(postFinder.findPostById(originalPostId));
	}
	
	
	
	@GetMapping("/grabpostbyrepostid/{postID}")
	public ResponseEntity<?> grabPostByRepostId(@PathVariable Long postID) {
		
		Optional<Post> tempPost = postRepository.findByPostId(postID);
		
		if (tempPost.isPresent()) {
			return ResponseEntity.ok(tempPost.get());
		} else {
			return ResponseEntity.badRequest().body("Post not found");
		}
		
	}
	
	
	
	

}
