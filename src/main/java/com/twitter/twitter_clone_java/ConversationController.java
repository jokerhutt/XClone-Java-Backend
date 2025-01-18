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
public class ConversationController {
	
	private final ConversationHandler conversationHandler;
	private final ConversationRepository conversationRepository;
	
	public ConversationController(ConversationHandler conversationHandler, ConversationRepository conversationRepository) {
		this.conversationHandler = conversationHandler;
		this.conversationRepository = conversationRepository;
	};
	
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
	
	
	

}
