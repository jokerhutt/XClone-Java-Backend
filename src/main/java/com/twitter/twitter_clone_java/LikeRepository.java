package com.twitter.twitter_clone_java;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

	List<Like> findByPostPostId(Long postId);
	Optional<Like> findByPostPostIdAndLikerId(Long postId, Long likerId);
	List<Like> findByLikerId(Long likerId);
	List<Like> findByPostPostIdIn(List<Long> postIds);
}
