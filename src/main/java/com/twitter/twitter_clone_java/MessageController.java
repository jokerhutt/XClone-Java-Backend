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
public class MessageController {
	
	private final ConversationHandler conversationHandler;
	private final MessageChecker messageChecker;
	private final MessageRepository messageRepository;
	private final NotificationHandler notificationHandler;
	
	public MessageController (NotificationHandler notificationHandler, MessageRepository messageRepository, ConversationHandler conversationHandler, MessageChecker messageChecker) {
		this.conversationHandler = conversationHandler;
		this.messageChecker = messageChecker;
		this.messageRepository = messageRepository;
		this.notificationHandler = notificationHandler;
	}
	
	@Transactional
	@PostMapping("/newmessage")
	public ResponseEntity<?> message(@RequestBody Map<String, Object> data) {
		
		System.out.println("Received message data " + data);
		
		Long senderId = Long.parseLong((String) data.get("senderId"));
		Long receiverId = Long.parseLong((String) data.get("receiverId"));
		String messageText = ((String) data.get("messageText"));
		
		Optional<Conversation> fetchedConversation = conversationHandler.convoDoubleCheckHandler(senderId, receiverId);
		if (fetchedConversation.isEmpty()) {
			 return ResponseEntity.badRequest().body("Failed to create reply");
		} else {
			Conversation unpackedConversation = fetchedConversation.get();
			Long grabbedConvoId = unpackedConversation.getId();
			
			Long conversationId = grabbedConvoId;
			
			Message newMessage = new Message();
			newMessage.setSenderId(senderId);
			newMessage.setReceiverId(receiverId);
			newMessage.setMessageText(messageText);
			newMessage.setConversationId(conversationId);
			
			messageRepository.save(newMessage);
			
			Notification newNotification = new Notification();
			newNotification.setNotificationType((String) data.get("notificationType"));
			newNotification.setNotificationObject(newMessage.getId());
			newNotification.setReceiverId(Long.valueOf(data.get("receiverId").toString()));
			newNotification.setSenderId(Long.valueOf(data.get("senderId").toString()));
			newNotification.setIsRead(false);
			
			notificationHandler.handleNewNotification(newNotification);
			
			Long tempConvoId = newMessage.getConversationId();
			Long tempMessageId = newMessage.getId();
			
			conversationHandler.updateConversationLastMessageId(conversationId, tempMessageId);
			
			List<Message> refreshedMessages = messageChecker.getAllConversationMessages(newMessage.getConversationId());
			return ResponseEntity.ok(refreshedMessages);
		}
	}
	
	@GetMapping("/grabmessagesbyconvoid/{convoId}")
	public ResponseEntity<List<Message>> grabMessagesFromConvo(@PathVariable Long convoId) {
		List<Message> fetchedConvoMessages = messageChecker.getAllConversationMessages(convoId);
		return ResponseEntity.ok(fetchedConvoMessages); 
	}
	
	@GetMapping("/grabmessagebymessageid/{messageId}")
	public ResponseEntity<?> grabMessageFromMessageId(@PathVariable Long messageId) {
		Optional<Message> fetchedMessage = messageRepository.findMessageById(messageId);
		
		if (fetchedMessage.isPresent()) {
			Message unWrappedMessage = fetchedMessage.get();
			return ResponseEntity.ok(unWrappedMessage);
		} else {
			 return ResponseEntity.badRequest().body("No Messages found!");
		}

	}
	
}
