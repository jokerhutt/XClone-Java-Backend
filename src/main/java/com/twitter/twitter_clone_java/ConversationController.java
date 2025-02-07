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
		for (Conversation currentConvo : fetchedUserConvos) {
			if (currentConvo.getUser1Id() == userId) {
				otherUserIds.add(currentConvo.getUser2Id());
			} else {
				otherUserIds.add(currentConvo.getUser1Id());
			}
		}

		List<User> foundUsersFromConvo = userRepository.findAllByIdIn(otherUserIds);

		return ResponseEntity.ok(foundUsersFromConvo);
	}
	
	@Transactional
	@PostMapping("/newconvo")
	public ResponseEntity<?> newConvo(@RequestBody Map<String, Object> data) {

		System.out.println("Received like data " + data);

		Long user1Id = ((Number) data.get("user1Id")).longValue();
		Long user2Id = ((Number) data.get("user2Id")).longValue();


		Conversation newConversation = conversationHandler.handleNewConvo(user1Id, user2Id);
		return ResponseEntity.ok(newConversation);
		
	}
		

	@GetMapping("/userconversationsmessages/{userId}")
	public ResponseEntity<List<Message>> getUserConvosMessagesByUserId(@PathVariable Long userId) {

		List <Conversation> fetchedUserConvos = conversationHandler.fetchUserConvosDoubleCheckHandler(userId);
		ArrayList <Long> messageIds = new ArrayList <> ();
		for (Conversation currentConvo : fetchedUserConvos) {
			messageIds.add(currentConvo.getId());
		}
		List<Message> fetchedAllMessages = messageRepository.findAllMessagesByConversationIdIn(messageIds);

		return ResponseEntity.ok(fetchedAllMessages);
	}



}
