package com.twitter.twitter_clone_java;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

	List<Like> findByPostId(Long postId);
	Optional<Like> findByPostIdAndLikerId(Long postId, Long likerId);
	
}
