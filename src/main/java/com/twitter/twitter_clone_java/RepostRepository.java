package com.twitter.twitter_clone_java;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepostRepository extends JpaRepository<Repost, Long>{

	List<Repost> findByPostId(Long postId);
	Optional<Repost> findRepostByPostIdAndReposterId(Long postId, Long reposterId);
	List<Repost> findRepostByReposterId(Long reposterId);
}
