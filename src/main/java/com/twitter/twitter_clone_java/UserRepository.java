package com.twitter.twitter_clone_java;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username); // Checks username
    boolean existsByEmail(String email);       // Checks email
    boolean existsByPassword(String password);
    Optional<User> findByUsername(String username);
    Optional<User> findById(int id);
    List<User> findAllByIdIn(List<Long> Ids);


}