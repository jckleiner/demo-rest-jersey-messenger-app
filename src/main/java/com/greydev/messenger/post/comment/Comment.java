package com.greydev.messenger.post.comment;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import com.greydev.messenger.post.Post;

@Entity
public class Comment {

	@Id
	@GenericGenerator(name = "myCustomIdGenerator", strategy = "com.greydev.messenger.UseIdOrGenerate")
	@GeneratedValue(generator = "myCustomIdGenerator")
	@Column(name = "comment_id")
	private Long id;
	@Column(name = "text")
	private String text;
	@Column(name = "author")
	private String author;
	@ManyToOne
	private Post post;
	@Transient
	private Long parentPostId;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created", updatable = false)
	private Date created;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "lastUpdated")
	private Date lastUpdated;

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public Comment() {
	}

	public Comment(String author, String text, Post post) {
		super();
		this.text = text;
		this.author = author;
		this.post = post;
		this.parentPostId = post.getId();
	}

	public Long getParentPostId() {
		if (post != null) { // is not set when we receive a request with a comment body as a payload
			return post.getId();
		}
		System.out.println("parentPost is null inside Comment...");
		return parentPostId;
	}

	public void setParentPostId(Long parentPostId) {
		this.parentPostId = parentPostId;
	}

	@XmlTransient
	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
		this.parentPostId = post.getId();
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

}
