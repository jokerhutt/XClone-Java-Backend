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
	
	public List<BookMark> fetchPostBookMarks (Long postId) {
		List<BookMark> refreshedBookMarks = bookMarkRepository.findAllBookMarksByPostId(postId);
	    if (refreshedBookMarks == null) {
	        return new ArrayList<>();
	    } else {
			return refreshedBookMarks;
	    }
	}

}
