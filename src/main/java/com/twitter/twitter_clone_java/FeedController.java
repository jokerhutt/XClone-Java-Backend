package com.twitter.twitter_clone_java;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:5173")


@RestController
@RequestMapping("/api")
public class FeedController {

    private PostRepository postRepository;
    private FollowRepository followRepository;

	public FeedController (FollowRepository followRepository, PostRepository postRepository) {
		this.postRepository = postRepository;
		this.followRepository = followRepository;
	}

	    @GetMapping("/foryoufeed")
	    	public Page<Post> getForYouFeed(
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size
	    	) 	{

		        PageRequest pageRequest = PageRequest.of(page, size);

		        return postRepository.findAllByOrderByCreatedAtDesc(pageRequest);

	    	}

//	    @GetMapping("/followingfeed/{profileUserId}")
//    	public Page<Post> getFollowingFeed(
//    	@PathVariable Long profileUserId,
//        @RequestParam(defaultValue = "0") int page,
//        @RequestParam(defaultValue = "10") int size
//    	) 	{
//	        PageRequest pageRequest = PageRequest.of(page, size);
//
//			List<Follow> userFollowing = followRepository.findAllByFollowingId(profileUserId);
//
//			ArrayList<Long> followingIds = new ArrayList<>();
//
//			for (int i = 0; i < userFollowing.size(); i++) {
//				Follow currentFollow = userFollowing.get(i);
//				Long currentFollowId = currentFollow.getFollowedId();
//				followingIds.add(currentFollowId);
//			}
//
//
//	        Page<Post> postsPage = postRepository.findAllByCreatorIdIn(followingIds, pageRequest);
//
//	        return postsPage;
//    	}






}



