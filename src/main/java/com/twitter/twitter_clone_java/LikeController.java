package com.twitter.twitter_clone_java;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;

@CrossOrigin(origins = "http://localhost:5173")


@RestController
@RequestMapping("/api")
public class LikeController {

	private final LikeChecker likeChecker;
	private final LikeRepository likeRepository;
	private final PostRepository postRepository;
	private final NotificationHandler notificationHandler;

	public LikeController (NotificationHandler notificationHandler, LikeChecker likeChecker, LikeRepository likeRepository, PostRepository postRepository) {
		this.likeChecker = likeChecker;
		this.likeRepository = likeRepository;
		this.postRepository = postRepository;
		this.notificationHandler = notificationHandler;
	}

	@Transactional
	@PostMapping("/newlike")
	public ResponseEntity<?> like(@RequestBody Map<String, Object> data) {

		System.out.println("Received like data " + data);

		Long postId = ((Number) data.get("postId")).longValue();
		Long likerId = ((Number) data.get("likerId")).longValue();

		Notification newNotification = new Notification();
		newNotification.setNotificationType((String) data.get("notificationType"));
		newNotification.setNotificationObject(Long.valueOf(data.get("notificationObject").toString()));
		newNotification.setReceiverId(Long.valueOf(data.get("receiverId").toString()));
		newNotification.setSenderId(Long.valueOf(data.get("senderId").toString()));
		newNotification.setIsRead(0L);

		likeChecker.handleLikeFlag(postId, likerId, newNotification);

		Optional<Like> fetchedLike = likeRepository.findByPostPostIdAndLikerId(postId, likerId);
		if (fetchedLike.isPresent()) {
			return ResponseEntity.ok(fetchedLike.get());
		} else if (fetchedLike.isEmpty()) {
			return ResponseEntity.ok(new ArrayList<>());
		} else {
			return ResponseEntity.ok(new ArrayList<>());
		}
	}

	@GetMapping("/grabuserlikedpostslist/{profileUserId}")
	public ResponseEntity<List<Like>> getUserLikedPostsList (@PathVariable Long profileUserId) {
		List<Like> userLikedIds = likeRepository.findByLikerId(profileUserId);


		return ResponseEntity.ok(userLikedIds);

	}

//	List<Long> likePostIds = new ArrayList<>();
//
//	for (int i = 0; i < userLikedIds.size(); i++) {
//		Like currentLikedId = userLikedIds.get(i);
//		Long currentPostId = currentLikedId.getPostId();
//		likePostIds.add(currentPostId);
//	}
//
//	List<Like> fetchedLikedPostsLikes = likeRepository.findByPostPostIdIn(likePostIds)



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
			Post tempPost = like.getPost();
		    likedPostIds.add(tempPost.getPostId());
		}
		List<Post> likedPosts = postRepository.findAllById(likedPostIds);
	    return ResponseEntity.ok(likedPosts);
	}





}


