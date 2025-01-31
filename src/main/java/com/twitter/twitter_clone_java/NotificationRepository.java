package com.twitter.twitter_clone_java;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

	List<Notification> findAllByReceiverId(Long receiverId);

	List<Notification> findAllByReceiverIdAndSenderIdAndNotificationObjectAndNotificationType(Long receiverId, Long senderId, Long notificationObject, String notificationType);

	@Modifying
	@Query("UPDATE Notification n SET n.isRead = 1 WHERE n.receiverId = :receiverId AND n.senderId = :senderId AND n.notificationObject = :notificationObject AND n.notificationType = :notificationType")
    void markNotificationsAsRead(Long receiverId, Long senderId, Long notificationObject, String notificationType);

}
