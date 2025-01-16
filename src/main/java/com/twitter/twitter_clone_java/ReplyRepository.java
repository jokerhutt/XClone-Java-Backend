package com.twitter.twitter_clone_java;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long>{
	List<Reply> findAllByReplyObjectId (Long replyObjectId);
	Reply findByReplyObjectId (Long replyObjectId);
	public Optional<Reply> findById(Long id);
}
