package com.twitter.twitter_clone_java;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public class MessageChecker {
	
	private final MessageRepository messageRepository;
	
	public MessageChecker(MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	}
	
	public List<Message> getAllConversationMessages (Long conversationId) {
		List<Message> fetchedConvoMessages = messageRepository.findAllMessagesByConversationId(conversationId);
		if (fetchedConvoMessages == null) {
			return new ArrayList<>();
		} else {
			return fetchedConvoMessages;
		}
		
	}
	
	
	
	

}
