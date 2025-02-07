package com.twitter.twitter_clone_java;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@CrossOrigin(origins = "http://localhost:5173")


@RestController
@RequestMapping("/api")
public class MessageController implements ApplicationContextAware{

    @PersistenceContext
    private EntityManager entityManager;

	private final ConversationHandler conversationHandler;
	private final MessageChecker messageChecker;
	private final MessageRepository messageRepository;
	private final NotificationHandler notificationHandler;

	private ApplicationContext applicationContext;

	public MessageController (NotificationHandler notificationHandler, MessageRepository messageRepository, ConversationHandler conversationHandler, MessageChecker messageChecker) {
		this.conversationHandler = conversationHandler;
		this.messageChecker = messageChecker;
		this.messageRepository = messageRepository;
		this.notificationHandler = notificationHandler;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}


	@Transactional
	@PostMapping("/newmessage")
	public ResponseEntity<?> message(@RequestBody Map<String, Object> data) {

		WebSocketHandler webSocketHandler = applicationContext.getBean(WebSocketHandler.class);

	    String senderIdString = (String) data.get("senderId");
	    String receiverIdString = (String) data.get("receiverId");

	    if (senderIdString == null || receiverIdString == null) {
	        return ResponseEntity.badRequest().body("Sender or receiver ID cannot be null");
	    }

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
			Long tempConvoId = newMessage.getConversationId();
			Long tempMessageId = newMessage.getId();
			
			messageRepository.flush();
			Optional <Message> updatedMessage = messageRepository.findById(tempMessageId);
			
			if (updatedMessage.isPresent()) {
				
				entityManager.refresh(updatedMessage.get());

				Notification newNotification = new Notification();
				newNotification.setNotificationType((String) data.get("notificationType"));
				newNotification.setNotificationObject(updatedMessage.get().getConversationId());
				newNotification.setReceiverId(Long.valueOf(data.get("receiverId").toString()));
				newNotification.setSenderId(Long.valueOf(data.get("senderId").toString()));
				newNotification.setIsRead(0L);

				notificationHandler.handleNewNotification(newNotification);

				conversationHandler.updateConversationLastMessageId(tempConvoId, tempMessageId);

				List<Message> refreshedMessages = messageChecker.getAllConversationMessages(updatedMessage.get().getConversationId());

			    try {
			    	String messageJson = updatedMessage.get().toString();
			    	webSocketHandler.broadcastMessage(new TextMessage(messageJson));
			    } catch (Exception e) {
			        e.printStackTrace();
			    }

				return ResponseEntity.ok(refreshedMessages);
			} else {
				return ResponseEntity.ok(new ArrayList<>());
			}




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
