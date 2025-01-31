package com.twitter.twitter_clone_java;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "posts")
public class Post {

	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  @Column(name = "post_id")
	  private Long postId;

	  @ManyToOne
	  @JoinColumn(name = "creator_id")
	  private User creator;

	  @Column(name = "post_text")
	  private String postText;

	  @Column(name = "created_at")
	  private String createdAt;

	  @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.ALL,  orphanRemoval = true)
	  @JsonManagedReference
	  private List<Like> likeList = new ArrayList<>();

	  @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	  @JsonManagedReference
	  private List<PostMedia> mediaList = new ArrayList<>();

	  @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	  @JsonManagedReference
	  private List<BookMark> bookMarkList = new ArrayList<>();

	  @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	  @JsonManagedReference
	  private List<Repost> repostList = new ArrayList<>();

	  @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	  @JsonManagedReference
	  private List<Reply> replyList = new ArrayList<>();


	  public Post () {}


	  public Long getPostId() {return this.postId;}


	  public List<Like> getLikeList() {return this.likeList;}
	  public List<PostMedia> getMediaList() {return this.mediaList;}
	  public List<BookMark> getBookMarkList() {return this.bookMarkList;}
	  public List<Repost> getRepostList() {return this.repostList;}
	  public List<Reply> getReplyList() {return this.replyList;}

	  public User getCreator() {return this.creator;}
	  public String getPostText() {return this.postText;}
	  public String getCreatedAt() {return this.createdAt;}

	  public void setPostId(Long postId) {this.postId = postId;}

	  public void setLikeList (List<Like> likeList) {this.likeList = likeList;}
	  public void setMediaList (List<PostMedia> mediaList) {this.mediaList = mediaList;}
	  public void setBookMarkList (List<BookMark> bookMarkList) {this.bookMarkList = bookMarkList;}
	  public void setRepostList (List<Repost> repostList) {this.repostList = repostList;}
	  public void setReplyList (List <Reply> replyList) {this.replyList = replyList;}


	  public void setCreator(User creator) {this.creator = creator;}
	  public void setPostText(String postText) {this.postText = postText;}
	  public void setCreatedAt(String createdAt) {this.createdAt = createdAt;}

	    @Override
	    public String toString() {
	        return "{" +
	               "\"postId\": " + postId + "," +
	               "\"creatorId\": " + creator + "," +
	               "\"postText\": \"" + postText + "\"," +
	               "\"postMediaList\": \"" + mediaList.toString() + "\"," +
	               "\"createdAt\": \"" + createdAt + "\"" +
	               "}";
	    }
}
