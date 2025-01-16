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
public class ReplyController {
	
	private final NotificationHandler notificationHandler;
	private final ReplyHandler replyHandler;
	
	public ReplyController(NotificationHandler notificationHandler, ReplyHandler replyHandler) {
		this.notificationHandler = notificationHandler;
		this.replyHandler = replyHandler;
	}
	
	@Transactional
	@PostMapping("/newreply")
	public ResponseEntity<?> reply(@RequestBody Map<String, Object> data) {
		
		Long replyObjectId = ((Number) data.get("replyObjectId")).longValue();
		Long replyReceiverId = ((Number) data.get("replyReceiverId")).longValue();
		Long replySenderId = ((Number) data.get("replySenderId")).longValue();
		String replyText = ((String) data.get("replyText"));
		
        if (replyText == null || replyText.isEmpty()) {
            return ResponseEntity.badRequest().body("Reply text cannot be empty");
        }
		
		System.out.println("Reply text is: " +  replyText);
		Reply newReplyObject = replyHandler.handleNewReply(replyObjectId, replyReceiverId, replySenderId, replyText);
        
		if (newReplyObject == null) {
            return ResponseEntity.badRequest().body("Failed to create reply");
        } else {
        	
    		Long replyId = newReplyObject.getId();
    		
    		System.out.println("Reply ID is: " + replyId);
    		
    		Notification newNotification = new Notification();
    		newNotification.setNotificationType((String) data.get("notificationType"));
    		newNotification.setNotificationObject(replyId);
    		newNotification.setReceiverId(Long.valueOf(data.get("receiverId").toString()));
    		newNotification.setSenderId(Long.valueOf(data.get("senderId").toString()));
    		newNotification.setIsRead(false);
    		
    		notificationHandler.handleNewNotification(newNotification);
    		
    		
    		
    		return ResponseEntity.ok("Nice");
        	
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
		        // Return the Reply object inside the ResponseEntity
		        return ResponseEntity.ok(fetchedReply.get());
		    } else {
		        // Return a 404 response if the reply is not found
		        return ResponseEntity.badRequest().body("Reply not found");
		    }
			
		}
	
	
	
}
