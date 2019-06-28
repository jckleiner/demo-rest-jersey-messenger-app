package com.greydev.messenger.post;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import com.greydev.messenger.link.Link;
import com.greydev.messenger.post.comment.Comment;
import com.greydev.messenger.profile.Profile;

@Entity
@XmlRootElement
public class Post {

	@Id
	@GenericGenerator(name = "myCustomIdGenerator", strategy = "com.greydev.messenger.UseIdOrGenerate")
	@GeneratedValue(generator = "myCustomIdGenerator")
	@Column(name = "post_id")
	private Long id;
	@Column(name = "text")
	private String text;
	@Column(name = "author")
	private String author;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "post")
	private List<Comment> comments = new ArrayList<>();
	@Transient
	private List<Link> links = new ArrayList<>();
	@ManyToOne
	private Profile profile;
	@Transient
	private String parentProfileName;

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

	public String getParentProfileName() {
		if (this.profile != null) { // is not set when we receive a request with a comment body as a payload
			return this.profile.getProfileName();
		}
		System.out.println("parentProfile is null inside Post...");
		return parentProfileName;
	}

	public void setParentProfileName(String parentProfileName) {
		this.parentProfileName = parentProfileName;
	}

	public Post() {
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public Post(String author, String text, Profile profile) {
		this.author = author;
		this.text = text;
		this.profile = profile;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreated() {
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

	public void setCreated(Date date) {
		created = date;
	}

	public void addLink(String url, String rel) {
		links.add(new Link(url, rel));
	}

	//	@XmlTransient
	public List<Link> getLinks() {
		return links;
	}

	@XmlTransient
	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
		this.parentProfileName = profile.getProfileName();
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

}
