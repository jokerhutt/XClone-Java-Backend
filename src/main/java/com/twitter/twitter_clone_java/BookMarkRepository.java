package com.twitter.twitter_clone_java;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookMarkRepository extends JpaRepository<BookMark, Long>{
	List<BookMark> findAllBookMarksByUserId(Long userId);
	Optional<BookMark> findBookMarkByUserId(Long userId);
	List<BookMark> findAllBookMarksByPostId(Long postId);
}
