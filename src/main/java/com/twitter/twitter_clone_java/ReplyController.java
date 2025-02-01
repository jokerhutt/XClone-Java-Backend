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
public class ReplyController {

	private final NotificationHandler notificationHandler;
	private final ReplyHandler replyHandler;

	public ReplyController(NotificationHandler notificationHandler, ReplyHandler replyHandler) {
		this.notificationHandler = notificationHandler;
		this.replyHandler = replyHandler;
	}

	@Transactional
	@PostMapping("/getallreplypostsbyreplyids")
	public ResponseEntity<List<Post>> grabPostsFromReplyIds(@RequestBody Map<String, List<Long>> requestBody) {
	List<Long> replyIds = requestBody.get("replyIds");
	List<Post> fetchedPosts = replyHandler.getPostsByReplyIds(replyIds);

	if (fetchedPosts == null) {
		return ResponseEntity.ok(new ArrayList<>());
	} else {
		return ResponseEntity.ok(fetchedPosts);
	}

	}

	@Transactional
	@PostMapping("/newreply")
	public ResponseEntity<?> reply(@RequestBody Map<String, Object> data) {

		Long postId = ((Number) data.get("postId")).longValue();
		Long replyReceiverId = ((Number) data.get("replyReceiverId")).longValue();
		Long replySenderId = ((Number) data.get("replySenderId")).longValue();
		String replyText = ((String) data.get("replyText"));

        if (replyText == null || replyText.isEmpty()) {
            return ResponseEntity.badRequest().body("Reply text cannot be empty");
        }

		System.out.println("Reply text is: " +  replyText);
		Optional<Reply> newReplyObject = replyHandler.handleNewReply(postId, replyReceiverId, replySenderId, replyText);

		if (newReplyObject.isEmpty()) {
            return ResponseEntity.badRequest().body("Failed to create reply");
        } else {
        	Reply newReplyThing = (newReplyObject.get());
    		Long replyId = newReplyThing.getId();

    		System.out.println("Reply ID is: " + replyId);

    		Notification newNotification = new Notification();
    		newNotification.setNotificationType((String) data.get("notificationType"));
    		newNotification.setNotificationObject(replyId);
    		newNotification.setReceiverId(Long.valueOf(data.get("receiverId").toString()));
    		newNotification.setSenderId(Long.valueOf(data.get("senderId").toString()));
    		newNotification.setIsRead(0L);

    		notificationHandler.handleNewNotification(newNotification);

    		return ResponseEntity.ok(newReplyThing);
        }
	}

	@GetMapping("replies/{postID}")
	public ResponseEntity<List<Reply>> getAllRepliesByPostId(@PathVariable Long postID) {
		List<Reply> fetchedPostReplies = replyHandler.fetchRepliesList(postID);
		return ResponseEntity.ok(fetchedPostReplies);
	}

	@GetMapping("grabreply/{replyID}")
	public ResponseEntity <?> getReplyByReplyId(@PathVariable Long replyID) {
		System.out.println("reply id is " + replyID);
		Optional<Reply> fetchedReply = replyHandler.fetchReplyById(replyID);

	    if (fetchedReply.isPresent()) {
	        return ResponseEntity.ok(fetchedReply.get());
	    } else {
	        return ResponseEntity.badRequest().body("Reply not found");
	    }
	}

	@GetMapping("grabuserreplies/{profileUserId}")
	public ResponseEntity<List<Reply>> getAllRepliesByUserId(@PathVariable Long profileUserId) {
		List<Reply> fetchedUserReplies = replyHandler.fetchRepliesByUser(profileUserId);
		return ResponseEntity.ok(fetchedUserReplies);
	}

	@GetMapping("grabuserrepliedPosts/{profileUserId}")
	public ResponseEntity <List<Post>> getAllPostsByReplyPostId(@PathVariable Long profileUserId) {
		List<Reply> fetchedUserReplies = replyHandler.fetchRepliesByUser(profileUserId);


		ArrayList<Post> replyPosts = new ArrayList<>();
		for (Reply currentReply : fetchedUserReplies) {
			Post currentPost = currentReply.getPost();
			replyPosts.add(currentPost);
		}

		return ResponseEntity.ok(replyPosts);

	}



}
