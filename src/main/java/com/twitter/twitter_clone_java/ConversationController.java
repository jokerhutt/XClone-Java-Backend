package com.twitter.twitter_clone_java;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:5173")


@RestController
@RequestMapping("/api")
public class ConversationController {

	private final ConversationHandler conversationHandler;
	private final ConversationRepository conversationRepository;
	private final UserRepository userRepository;
	private final MessageRepository messageRepository;

	public ConversationController(MessageRepository messageRepository, UserRepository userRepository, ConversationHandler conversationHandler, ConversationRepository conversationRepository) {
		this.conversationHandler = conversationHandler;
		this.conversationRepository = conversationRepository;
		this.userRepository = userRepository;
		this.messageRepository = messageRepository;
	}

	@GetMapping("/conversations/{userId}/{otherUserId}")
	public ResponseEntity<Conversation> getConversationByParams(@PathVariable Long userId, @PathVariable Long otherUserId) {

		Optional<Conversation> checkedConvo = conversationHandler.convoDoubleCheckHandler(userId, otherUserId);

		if (checkedConvo.isEmpty()) {
			Conversation newConvo = new Conversation();
			newConvo.setUser1Id(userId);
			newConvo.setUser2Id(otherUserId);
			conversationRepository.save(newConvo);
			return ResponseEntity.ok(newConvo);
		} else {
			return ResponseEntity.ok(checkedConvo.get());
		}

	}

	@GetMapping("/userconversations/{userId}")
	public ResponseEntity<List<Conversation>> getUserConvosByUserId(@PathVariable Long userId) {

		List <Conversation> fetchedUserConvos = conversationHandler.fetchUserConvosDoubleCheckHandler(userId);
		return ResponseEntity.ok(fetchedUserConvos);
	}
	
	@GetMapping("/userconversationsotherusers/{userId}")
	public ResponseEntity<List<User>> getUserConvosUsersByUserId(@PathVariable Long userId) {

		List <Conversation> fetchedUserConvos = conversationHandler.fetchUserConvosDoubleCheckHandler(userId);
		ArrayList <Long> otherUserIds = new ArrayList <> ();
		for (int i = 0; i < fetchedUserConvos.size(); i++) {
			Conversation currentConvo = fetchedUserConvos.get(i);
			if (currentConvo.getUser1Id() == userId) {
				otherUserIds.add(currentConvo.getUser2Id());
			} else {
				otherUserIds.add(currentConvo.getUser1Id());
			}
		}
		
		List<User> foundUsersFromConvo = userRepository.findAllByIdIn(otherUserIds);
		
		return ResponseEntity.ok(foundUsersFromConvo);
	}
	
	@GetMapping("/userconversationsmessages/{userId}")
	public ResponseEntity<List<Message>> getUserConvosMessagesByUserId(@PathVariable Long userId) {
		
		List <Conversation> fetchedUserConvos = conversationHandler.fetchUserConvosDoubleCheckHandler(userId);
		ArrayList <Long> messageIds = new ArrayList <> ();
		for (int i = 0; i < fetchedUserConvos.size(); i++) {
			Conversation currentConvo = fetchedUserConvos.get(i);
			messageIds.add(currentConvo.getId());
		}
		List<Message> fetchedAllMessages = messageRepository.findAllMessagesByConversationIdIn(messageIds);
		
		return ResponseEntity.ok(fetchedAllMessages);
	}



}
