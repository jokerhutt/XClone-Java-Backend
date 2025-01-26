package com.twitter.twitter_clone_java;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookMarkRepository extends JpaRepository<BookMark, Long>{
	List<BookMark> findBookMarksByUserId(Long userId);
	Optional<BookMark> findBookMarkByUserId(Long userId);
	List<BookMark> findBookMarksByPostPostId(Long postId);
	Optional<BookMark> findBookMarkByUserIdAndPostPostId(Long userId, Long postId);
}
