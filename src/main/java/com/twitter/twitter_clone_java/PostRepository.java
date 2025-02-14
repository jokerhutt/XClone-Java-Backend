package com.twitter.twitter_clone_java;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	Optional<Post> findByPostId(Long postId);
	Optional<Post> findByCreatorId(Long creatorId);
	List<Post> findAllByCreatorId(Long creatorId);
	List<Post> findAllByPostIdIn(List<Long> postIds);
	List<Post> findAllByCreatorIdIn(List<Long> creatorIds);
	Page<Post> findAllPostsByCreatorIdIn(List<Long> creatorIds, Pageable pageable);
	@Override
	List<Post> findAll();
	Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);
	Page<Post> findAllByCreatorIdInOrderByCreatedAtDesc(List<Long> creatorIds, PageRequest pageRequest);

}


