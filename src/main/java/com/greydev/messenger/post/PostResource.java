package com.greydev.messenger.post;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.greydev.messenger.exception.DataNotFoundException;
import com.greydev.messenger.exception.InvalidRequestDataException;
import com.greydev.messenger.filter.PostFilterBean;
import com.greydev.messenger.post.comment.CommentResource;

@Path("/posts")
@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public class PostResource {

	private static final Logger LOG = LoggerFactory.getLogger(PostResource.class);
	private static final PostService postService = new PostService();

	@GET
	public List<Post> getAllPosts(@BeanParam PostFilterBean filterBean) {
		int year = filterBean.getYear();
		int start = filterBean.getStart();
		int size = filterBean.getSize();
		LOG.info("GET: getAllPosts");

		// TODO optimize filtering
		if (year != 0) {
			return postService.getAllPostsForYear(year);
		}
		else if (start >= 0 && size > 0) {
			return postService.getAllPostsPaginated(start, size);
		}
		return postService.getAllPosts();
	}

	@GET
	@Path("/{postId}")
	public Post getPost(@PathParam("postId") Long postId)
			throws DataNotFoundException, UnknownHostException {
		LOG.info("GET: getPost(id: {})", postId);
		return postService.getPost(postId); // post always has a unique id, no need for profileName
	}

	@POST
	public Response addPost(@PathParam("profileName") String profileName, @Context UriInfo uriInfo, Post post)
			throws URISyntaxException, InvalidRequestDataException {
		LOG.info("POST: addPost(post: {}, {})", post.getAuthor(), post.getText());
		if (profileName == null) {
			profileName = post.getParentProfileName();
		}

		Post addedPost = postService.addPost(uriInfo, profileName, post);

		String id = Long.toString(addedPost.getId());
		URI locationUri = uriInfo.getAbsolutePathBuilder().path(id).build();
		return Response.created(locationUri).entity(addedPost).build();
	}

	@PUT
	@Path("/{postId}")
	public Post updatePost(@PathParam("profileName") String profileName, @PathParam("postId") Long postId, Post post)
			throws InvalidRequestDataException {
		LOG.info("PUT: updatePost(id: {})", postId);
		System.out.printf("PUT: updatePost(author: %s)\n", post.getAuthor());
		return postService.updatePost(profileName, postId, post);
	}

	@DELETE
	@Path("/{postId}")
	public Post deletePost(@PathParam("postId") Long postId) throws DataNotFoundException {
		LOG.info("DELETE: deletePost(id: {})", postId);
		return postService.deletePost(postId); // post always has a unique id, no need for profileName
	}

	// no parameter means: for all methods.
	// http://localhost:8080/messenger/api/posts/1/comments/2 -> this request will trigger this method.
	// even though the @Path annotation below DOES NOT CONTAIN '1/comments/2', 
	// the whole request will be redirected to CommentResource!
	@Path("{postId}/comments")
	public CommentResource getCommentResource() {
		LOG.info("getAllComments");
		return new CommentResource();
	}
}
