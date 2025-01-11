package com.twitter.twitter_clone_java;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
	
public interface PostRepository extends JpaRepository<Post, Long> {
	Optional<Post> findByPostId(Long postId);
	Optional<Post> findByCreatorId(Long creatorId);
	List<Post> findAllByCreatorId(Long creatorId);
	
}


