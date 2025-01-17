package com.twitter.twitter_clone_java;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public class BookMarkHandler {
	
	private final BookMarkRepository bookMarkRepository;
	
	public BookMarkHandler (BookMarkRepository bookMarkRepository) {
		this.bookMarkRepository = bookMarkRepository;
	}
	
	public void handleBookMarkFlag (Long userId, Long postId) {
		
		Optional<BookMark> existingBookMark = bookMarkRepository.findBookMarkByUserId(userId);
		
		if(existingBookMark.isPresent()) {
			bookMarkRepository.delete(existingBookMark.get());
		} else {
			BookMark newBookMark = new BookMark();
			newBookMark.setPostId(postId);
			newBookMark.setUserId(userId);
			bookMarkRepository.save(newBookMark);
		}
	}

}
