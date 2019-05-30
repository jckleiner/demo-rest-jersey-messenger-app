package com.greydev.messenger.post.comment;

import java.util.GregorianCalendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.GenericGenerator;

import com.greydev.messenger.post.Post;

@Entity
public class Comment {

	@Id
	@GenericGenerator(name = "myCustomIdGenerator", strategy = "com.greydev.messenger.UseIdOrGenerate")
	@GeneratedValue(generator = "myCustomIdGenerator")
	@Column(name = "comment_id")
	private Long id;
	@Column(name = "created")
	private GregorianCalendar created;
	@Column(name = "text")
	private String text;
	@Column(name = "author")
	private String author;
	@ManyToOne
	private Post post;

	public Comment() {
	}

	@XmlTransient
	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public Comment(String author, String text) {
		super();
		this.created = new GregorianCalendar();
		this.text = text;
		this.author = author;
	}

	public Comment(String author, String text, Post post) {
		super();
		this.created = new GregorianCalendar();
		this.text = text;
		this.author = author;
		this.post = post;
	}

	public Comment(Long id, String author, String text, Post post) {
		super();
		this.created = new GregorianCalendar();
		this.text = text;
		this.author = author;
		this.id = id;
		this.post = post;
	}

	public Comment(Long id, String author, String text) {
		super();
		this.created = new GregorianCalendar();
		this.id = id;
		this.text = text;
		this.author = author;
	}

	public GregorianCalendar getCreated() {
		return created;
	}

	public void setCreated(GregorianCalendar created) {
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
