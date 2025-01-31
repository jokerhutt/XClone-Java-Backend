package com.twitter.twitter_clone_java;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostMediaRepository extends JpaRepository<PostMedia, Long>{
//	List<PostMedia> findAllByPostPostId (Long postId);
}
