package com.greydev.messenger.post;

import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.List;

import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.greydev.messenger.exception.DataNotFoundException;
import com.greydev.messenger.exception.InvalidRequestDataException;
import com.greydev.messenger.post.comment.CommentResource;
import com.greydev.messenger.profile.ProfileResource;

//TODO writing URI's manually inside exceptions seems bad, fix
public class PostService {

	private static final Logger LOG = LoggerFactory.getLogger(PostService.class);

	public List<Post> getAllPosts() {
		return PostDao.getAllPostsAsList();
	}

	public Post getPost(Long id) throws DataNotFoundException, UnknownHostException {
		Post post = PostDao.getPost(id);

		if (post == null) {
			throw new DataNotFoundException("GET", "/posts/" + id);
		}
		return post;
	}

	public Post addPost(UriInfo uriInfo, Post post) throws InvalidRequestDataException {

		post.setId(null); // don't use the id given by the user, let hibernate generate a new one by setting it to null
		post.getComments().forEach(comment -> {
			System.out.println("setting parent post inside comment...");
			comment.setPost(post); // set the parent relationship
			comment.setId(null); // don't use the id given by the user
		});

		if (!isPostValid(post)) {
			throw new InvalidRequestDataException("POST", "/posts");
		}
		//		newMessage.addLink(getUriForSelf(uriInfo, newMessage), "self");
		//		newMessage.addLink(getUriForProfile(uriInfo, newMessage), "profile");
		//		newMessage.addLink(getUriForComments(uriInfo, newMessage), "comments");

		post.setId(PostDao.addPost(post));
		return post;
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
			PostDao.updatePost(post);
			return post;
		}
		else {
			System.out.println(" * * * post id: " + post.getId());
			System.out.println(" * * * comment0 id: " + post.getComments().get(0).getId());
			post.setId(PostDao.addPost(post));
			System.out.println(" * * * post id: " + post.getId());
			System.out.println(" * * * comment0 id: " + post.getComments().get(0).getId());
			return post;
		}
	}

	public Post deletePost(Long id) throws DataNotFoundException {
		Post response = PostDao.deletePost(id);

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
		return PostDao.getAllPostsForYear(year);
	}

	public List<Post> getAllPostsPaginated(int start, int size) {
		List<Post> postList = PostDao.getAllPostsAsList();

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
		return ObjectUtils.allNotNull(post.getAuthor(), post.getText(), post.getProfile());
	}

	public boolean doesPostExist(Long id) {
		return (PostDao.getPost(id) != null);
	}

}
