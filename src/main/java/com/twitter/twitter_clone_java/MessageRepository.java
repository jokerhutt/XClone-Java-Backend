package com.twitter.twitter_clone_java;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

	List<Message> findAllMessagesByConversationId (Long conversationId);
	List<Message> findAllMessagesByConversationIdIn (List<Long> conversationIds);
	Optional<Message> findMessageById (Long id);

}
