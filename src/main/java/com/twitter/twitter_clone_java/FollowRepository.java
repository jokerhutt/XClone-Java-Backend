package com.twitter.twitter_clone_java;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long>{

	Optional<Follow> findByFollowingIdAndFollowedId(Long followingId, Long followedId);
	List<Follow> findAllByFollowingId(Long followingId);
	List<Follow> findAllByFollowedId(Long followedId);

}


