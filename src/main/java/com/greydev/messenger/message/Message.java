package com.greydev.messenger.message;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.greydev.messenger.link.Link;
import com.greydev.messenger.message.comment.Comment;

@XmlRootElement
public class Message {

	private GregorianCalendar created;
	private Long id;
	private String text;
	private String author;
	private Map<Long, Comment> comments = new HashMap<>();
	private List<Link> links = new ArrayList<>();

	public Message() {
	}

	public Message(Long id, String author, String text) {
		this.author = author;
		this.id = id;
		this.text = text;
		this.created = new GregorianCalendar();

		// add dummy comments for each message
		this.comments.put(1L, new Comment(1L, author, "First Comment"));
		this.comments.put(2L, new Comment(2L, author, "Grocery store"));
		this.comments.put(3L, new Comment(3L, author, "Hey there"));
	}

	public Message(Long id, String author, String text, GregorianCalendar date) {
		this.author = author;
		this.id = id;
		this.text = text;
		this.created = date;

		// add dummy comments for each message
		this.comments.put(1L, new Comment(1L, author, "First Comment"));
		this.comments.put(2L, new Comment(2L, author, "Grocery store"));
		this.comments.put(3L, new Comment(3L, author, "Hey there"));
	}

	public Message(Long id, String author, String text, GregorianCalendar date, Map<Long, Comment> comments) {
		this.author = author;
		this.id = id;
		this.text = text;
		this.created = date;
		this.comments = comments;
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

	@XmlTransient
	public Map<Long, Comment> getComments() {
		return comments;
	}

//	public void setComments(Map<Long, Comment> comments) {
//		this.comments = comments;
//	}

	public void addLink(String url, String rel) {
		links.add(new Link(url, rel));
	}

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

}
