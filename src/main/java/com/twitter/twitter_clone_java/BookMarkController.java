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
public class BookMarkController {
	private final BookMarkRepository bookMarkRepository;
	private final PostRepository postRepository;
	private BookMarkHandler bookMarkHandler;
	
	public BookMarkController (PostRepository postRepository, BookMarkHandler bookMarkHandler, BookMarkRepository bookMarkRepository) {
		this.bookMarkHandler = bookMarkHandler;
		this.bookMarkRepository = bookMarkRepository;
		this.postRepository = postRepository;
	}
	
	@Transactional
	@PostMapping("/newbookmark")
	public ResponseEntity<?> bookmark(@RequestBody Map<String, Object> data) {
		
		Long userId = ((Number) data.get("userId")).longValue();
		Long postId = ((Number) data.get("postId")).longValue();
		
		System.out.println("USERID  is: " +  userId);
		System.out.println("POSTID  is: " +  postId);
		
		bookMarkHandler.handleBookMarkFlag(userId, postId);
		
		List<BookMark> newUserBookMarks =  bookMarkRepository.findBookMarksByUserId(userId);
		
		return ResponseEntity.ok(newUserBookMarks);
	}
	
	@GetMapping("/grabuserbookmarkedposts/{profileUserId}")
	public ResponseEntity<List<Post>> getBookMarkedPostsByUserId(@PathVariable Long profileUserId) {
		
		List<BookMark> userBookMarked = bookMarkRepository.findBookMarksByUserId(profileUserId);
		List<Long> bookMarkedPostIds = new ArrayList<>();
		
		for (BookMark bookmark : userBookMarked) {
			bookMarkedPostIds.add(bookmark.getPostId());
		}
		
		
		
		List<Post> bookMarkedPosts = postRepository.findAllById(bookMarkedPostIds);
		
		return ResponseEntity.ok(bookMarkedPosts);
		
	}
	
	@GetMapping("/grabuserbookmarked/{profileUserId}")
	public ResponseEntity<List<BookMark>> getBookMarksByUserId(@PathVariable Long profileUserId) {
		
		List<BookMark> userBookMarked = bookMarkRepository.findBookMarksByUserId(profileUserId);
		
		return ResponseEntity.ok(userBookMarked);
		
	}
	
	@GetMapping("/grabpostbookmarks/{postID}")
	public ResponseEntity<List<BookMark>> getBookMarksByPostId(@PathVariable Long postID) {
		
		List<BookMark> postBookMarked = bookMarkHandler.fetchPostBookMarks(postID);
		
		return ResponseEntity.ok(postBookMarked);
		
	}

}
