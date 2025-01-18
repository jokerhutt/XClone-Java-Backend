package com.twitter.twitter_clone_java;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public class ConversationHandler {
	
	private final ConversationRepository conversationRepository;
	
	public ConversationHandler (ConversationRepository conversationRepository) {
		this.conversationRepository = conversationRepository;
	}
	
	public Optional<Conversation> convoDoubleCheckHandler (Long user1Id, Long user2Id) {
		
		Optional<Conversation> firstCheck = conversationRepository.findConversationByUser1IdAndUser2Id(user1Id, user2Id);
		Optional<Conversation> secondCheck = conversationRepository.findConversationByUser1IdAndUser2Id(user2Id, user1Id);
		
		if(firstCheck.isPresent()) {
			return firstCheck;
		} else if (secondCheck.isPresent()) {
			return secondCheck;
		} else {
			return Optional.empty();
		}
	}
	
	public List<Conversation> fetchUserConvosDoubleCheckHandler (Long user1Id) {
		
		List<Conversation> convoListOne = conversationRepository.findAllConversationsByUser1Id(user1Id);
		List<Conversation> convoListTwo = conversationRepository.findAllConversationsByUser2Id(user1Id);
		
		if (convoListOne == null && convoListTwo == null) {
			return new ArrayList<>();
		} else if (convoListOne == null && convoListTwo != null) {
			return convoListTwo;
		} else if (convoListOne != null && convoListTwo == null) {
			return convoListOne;
		} else if (convoListOne !=null && convoListTwo != null) {
			convoListOne.addAll(convoListTwo);
			return convoListOne;
		} else {
			return new ArrayList<>();
		}
		
	}
	
	public void updateConversationLastMessageId (Long conversationId, Long messageId) {
		
		Optional<Conversation> fetchedConversation = conversationRepository.findConversationById(conversationId);
		
		if (fetchedConversation.isPresent()) {
			Conversation unwrappedConversation = fetchedConversation.get();
			unwrappedConversation.setLastMessageId(messageId);
			conversationRepository.save(unwrappedConversation);
		}
	}
	
	
	
	
	
	
	

}
