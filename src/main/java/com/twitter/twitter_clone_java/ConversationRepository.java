package com.twitter.twitter_clone_java;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {
	Optional<Conversation> findConversationByUser1IdAndUser2Id(Long user1Id, Long user2Id);
	List<Conversation> findAllConversationsByUser1Id(Long user1Id);
	List<Conversation> findAllConversationsByUser2Id(Long user2Id);
}
