package com.twitter.twitter_clone_java;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.transaction.Transactional;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")


@RestController
@RequestMapping("/api")
public class FeedController {
	
    private PostRepository postRepository;
	
	public FeedController (PostRepository postRepository) {
		this.postRepository = postRepository;
	}
	
	    @GetMapping("/foryoufeed")
	    	public Page<Post> getForYouFeed(
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size
	    	) 	{
	    	
	    		System.out.println("Ohhh yeah i just received a request");
		        PageRequest pageRequest = PageRequest.of(page, size);
	
		        Page<Post> postsPage = postRepository.findAll(pageRequest);
	
		        return postsPage;
		        
	    	}
	    
//	    @GetMapping("/followingfeed/{profileUserId}")
//    	public Page<Post> getFollowingFeed(
//        @RequestParam(defaultValue = "0") int page,
//        @RequestParam(defaultValue = "10") int size
//    	) 	{
//	        PageRequest pageRequest = PageRequest.of(page, size);
//	        
//	        List<User>
//
//	        Page<Post> postsPage = postRepository.findAll(pageRequest);
//
//	        return postsPage;
//    	}
//	
	
	
	
	
	
}


	
