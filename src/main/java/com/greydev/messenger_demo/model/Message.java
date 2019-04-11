package com.greydev.messenger_demo.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Message {

	private String author;
	private Long id;
	private String text;
	private Date created;
	
	public Message() {
	}

	public Message(Long id, String author, String text) {
		this.author = author;
		this.id = id;
		this.text = text;
		this.created = new Date();
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getCreated() {
		return created;
	}

	public String getAuthor() {
		return author;
	}

	public Long getId() {
		return id;
	}

	public String getText() {
		return text;
	}

}
