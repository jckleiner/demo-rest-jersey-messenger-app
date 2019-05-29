package com.greydev.messenger.message.comment;

import java.util.GregorianCalendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlTransient;

import com.greydev.messenger.message.Message;

@Entity
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "comment_id")
	private Long id;
	@Column(name = "created")
	private GregorianCalendar created;
	@Column(name = "text")
	private String text;
	@Column(name = "author")
	private String author;
	@ManyToOne
	//	@JoinColumn(name = "message_id")
	private Message message;

	public Comment() {

	}

	@XmlTransient
	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public Comment(String author, String text) {
		super();
		this.created = new GregorianCalendar();
		this.text = text;
		this.author = author;
	}

	public Comment(String author, String text, Message message) {
		super();
		this.created = new GregorianCalendar();
		this.text = text;
		this.author = author;
		this.message = message;
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
