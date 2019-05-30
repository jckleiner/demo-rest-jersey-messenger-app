package com.greydev.messenger.post;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;

import com.greydev.messenger.link.Link;
import com.greydev.messenger.post.comment.Comment;

@Entity
@XmlRootElement
public class Post {

	private GregorianCalendar created;
	@Id
	@GenericGenerator(name = "myCustomIdGenerator", strategy = "com.greydev.messenger.database.UseIdOrGenerate")
	@GeneratedValue(generator = "myCustomIdGenerator")
	@Column(name = "post_id")
	private Long id;
	@Column(name = "text")
	private String text;
	@Column(name = "author")
	private String author;
	@OneToMany(cascade = javax.persistence.CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Comment> comments = new ArrayList<>();
	@Transient
	private List<Link> links = new ArrayList<>();

	public Post() {
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public Post(String author, String text, GregorianCalendar date) {
		this.author = author;
		this.text = text;
		this.created = date;
	}

	public Post(String author, String text) {
		this.author = author;
		this.text = text;
		this.created = new GregorianCalendar();
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

	public void addLink(String url, String rel) {
		links.add(new Link(url, rel));
	}

	//	@XmlTransient
	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

}