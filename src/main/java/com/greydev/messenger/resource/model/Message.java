package com.greydev.messenger.resource.model;

import java.util.GregorianCalendar;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Message {

	@XmlElement
	private GregorianCalendar created;
	private Long id;
	private String text;
	private String author;
	
	public Message() {
	}

	public Message(Long id, String author, String text) {
		this.author = author;
		this.id = id;
		this.text = text;
		this.created = new GregorianCalendar();
	}
	
	public Message(Long id, String author, String text, GregorianCalendar date) {
		this.author = author;
		this.id = id;
		this.text = text;
		this.created = date;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public GregorianCalendar getCreated() {
		return created;
	}

	public void setText(String text) {
		this.text = text;
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
	
	public void setCreated(GregorianCalendar date) {
		created = date;
	}
	
}
