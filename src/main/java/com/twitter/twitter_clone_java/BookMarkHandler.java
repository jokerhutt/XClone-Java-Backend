package com.twitter.twitter_clone_java;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public class BookMarkHandler {

	private final BookMarkRepository bookMarkRepository;
	private final PostRepository postRepository;

	public BookMarkHandler (PostRepository postRepository, BookMarkRepository bookMarkRepository) {
		this.bookMarkRepository = bookMarkRepository;
		this.postRepository = postRepository;
	}

	public void handleBookMarkFlag (Long userId, Long postId) {

		Optional<BookMark> existingBookMark = bookMarkRepository.findBookMarkByUserIdAndPostPostId(userId, postId);

		if(existingBookMark.isPresent()) {

			Post existingPost = existingBookMark.get().getPost();
			existingPost.getBookMarkList().remove(existingBookMark.get());

			bookMarkRepository.delete(existingBookMark.get());
		} else {
			BookMark newBookMark = new BookMark();
			Optional<Post> existingPost = postRepository.findByPostId(postId);
			if (existingPost.isPresent()) {
				newBookMark.setPost(existingPost.get());
				newBookMark.setUserId(userId);
				bookMarkRepository.save(newBookMark);
			}

		}
	}

	public List<BookMark> fetchPostBookMarks (Long postId) {
		List<BookMark> refreshedBookMarks = bookMarkRepository.findBookMarksByPostPostId(postId);
	    if (refreshedBookMarks == null) {
	        return new ArrayList<>();
	    } else {
			return refreshedBookMarks;
	    }
	}

}
