package com.twitter.twitter_clone_java;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

	List<Notification> findAllByReceiverId(Long receiverId);
	
}
