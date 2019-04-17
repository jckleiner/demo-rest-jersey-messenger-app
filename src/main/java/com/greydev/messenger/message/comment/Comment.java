package com.greydev.messenger.message.comment;

import java.util.GregorianCalendar;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Comment {
	
	private GregorianCalendar created;
	private Long id;
	private String text;
	private String author;
	
	public Comment() {
		
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
