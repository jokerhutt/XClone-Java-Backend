package com.twitter.twitter_clone_java;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long>{
	List<Reply> findAllByPostPostId (Long postId);
	List<Reply> findAllByReplySenderId (Long replySenderId);
	List<Reply> findAllByIdIn (List<Long> Ids);
	Reply findByPostPostId (Long postId);
	@Override
	public Optional<Reply> findById(Long id);
}
