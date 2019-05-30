package com.greydev.messenger.post;

import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.List;

import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.greydev.messenger.database.DatabaseMock;
import com.greydev.messenger.exception.DataNotFoundException;
import com.greydev.messenger.exception.InvalidRequestDataException;
import com.greydev.messenger.post.comment.CommentResource;
import com.greydev.messenger.profile.ProfileResource;

public class PostService {

	private static final Logger LOG = LoggerFactory.getLogger(PostService.class);

	public List<Post> getAllPosts() {
		return DatabaseMock.getAllPostsAsListHibernate();
	}

	public Post getPost(Long id) throws DataNotFoundException, UnknownHostException {
		Post post = DatabaseMock.getPostHibernate(id);

		if (post == null) {
			throw new DataNotFoundException("GET", "/posts/" + id);
		}
		return post;
	}

	public Post addPost(UriInfo uriInfo, Post post) throws InvalidRequestDataException {
		Post newPost = new Post(post.getAuthor(), post.getText());
		newPost.setComments(post.getComments());
		newPost.getComments().forEach(comment -> {
			System.out.println("setting parent post inside comment...");
			comment.setPost(newPost);
			comment.setId(null);
		});

		if (!isPostValid(newPost)) {
			throw new InvalidRequestDataException("POST", "/posts");
		}
		//		newMessage.addLink(getUriForSelf(uriInfo, newMessage), "self");
		//		newMessage.addLink(getUriForProfile(uriInfo, newMessage), "profile");
		//		newMessage.addLink(getUriForComments(uriInfo, newMessage), "comments");

		newPost.setId(DatabaseMock.addPostHibernate(newPost));
		return newPost;
	}

	public Post updatePost(Long queryParamPostId, Post post) throws InvalidRequestDataException {
		// ignore the id inside the received post, use the url param id
		post.setId(queryParamPostId);
		post.getComments().forEach(comment -> {
			System.out.println("setting parent post inside comment...");
			comment.setPost(post);
			comment.setId(null);
		});

		if (!isPostValid(post)) {
			throw new InvalidRequestDataException("PUT", "/posts");
		}

		if (doesPostExist(post.getId())) {
			DatabaseMock.updatePostHibernate(post);
			return post;
		}
		else {
			System.out.println(" * * * post id: " + post.getId());
			System.out.println(" * * * comment0 id: " + post.getComments().get(0).getId());
			post.setId(DatabaseMock.addPostHibernate(post));
			System.out.println(" * * * post id: " + post.getId());
			System.out.println(" * * * comment0 id: " + post.getComments().get(0).getId());
			return post;
		}
	}

	public Post deletePost(Long id) throws DataNotFoundException {
		Post response = DatabaseMock.deletePostHibernate(id);

		if (response == null) {
			throw new DataNotFoundException("DELETE", "/posts/" + id);
		}
		return response;
	}

	public List<Post> getAllPostsForYear(int year) {
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);

		if (year > currentYear) {
			LOG.info("passed year parameter is not valid");
			return null;
		}
		return DatabaseMock.getAllPostsForYear(year);
	}

	public List<Post> getAllPostsPaginated(int start, int size) {
		List<Post> postList = DatabaseMock.getAllPostsAsListHibernate();

		if ((start + size) >= postList.size()) {
			return postList.subList(start, postList.size());
		}
		return postList.subList(start, start + size);
	}

	private String getUriForSelf(UriInfo uriInfo, Post post) {
		return uriInfo.getBaseUriBuilder().path(PostResource.class).path(Long.toString(post.getId())).toString();
	}

	private String getUriForProfile(UriInfo uriInfo, Post post) {
		return uriInfo.getBaseUriBuilder().path(ProfileResource.class).path(post.getAuthor()).toString();
	}

	private String getUriForComments(UriInfo uriInfo, Post post) {
		return uriInfo.getBaseUriBuilder().path(PostResource.class).path(PostResource.class, "getCommentResource")
				.path(CommentResource.class).resolveTemplate("postId", post.getId()).toString();
	}

	// mandatory properties: Author, Text
	public boolean isPostValid(Post post) {
		return StringUtils.isNoneBlank(post.getAuthor(), post.getText());
	}

	public boolean doesPostExist(Long id) {
		return (DatabaseMock.getPostHibernate(id) != null);
	}

}
