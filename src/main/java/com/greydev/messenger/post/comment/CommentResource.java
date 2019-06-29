package com.greydev.messenger.post.comment;

import java.util.List;

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
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.greydev.messenger.exception.DataNotFoundException;
import com.greydev.messenger.exception.InvalidRequestDataException;

// TODO how to expose comment resource also under .../comments and not just .../messages/x/comments?
@Path("/comments") // this path is ignored if parent calles this resource with a different url
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
//the order is important! If no 'Accept' header is specified, jersey will send the first media type back
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class CommentResource {

	private static final Logger LOG = LoggerFactory.getLogger(CommentResource.class);

	private CommentService commentService = new CommentService();

	@GET
	public List<Comment> getComments(@PathParam("postId") Long postId, @Context UriInfo uriInfo) {
		LOG.info("getCommentsForPost - postId: {}", postId);
		LOG.info("@GET - path: {}", uriInfo.getAbsolutePath());

		// when .../api/comments is called, Client wants to get all the comments.
		if (postId == null) { // @GET - path: http://localhost:8080/messenger/api/comments
			LOG.info(" *** DIRECT ACCESS TO ALL COMMENTS *** ");

			return commentService.getAllCommentsForEveryPost();
		}
		return commentService.getCommentsForPost(postId); // @GET - path: http://localhost:8080/messenger/api/posts/1/comments
	}

	@GET
	@Path("{commentId}")
	public Comment getComment(@PathParam("commentId") Long commentId) {
		return commentService.getComment(commentId); // comment alwasy has a unique id, no need for postId
	}

	@POST
	public Comment addComment(@PathParam("postId") Long postId, Comment comment)
			throws InvalidRequestDataException, DataNotFoundException {

		if (postId == null) { // @POST - path: http://localhost:8080/messenger/api/comments
			LOG.info(" *** DIRECT POST TO COMMENTS *** ");
			postId = comment.getParentPostId();
			LOG.info(" *** POST ID : {} *** ", postId);
			return commentService.addComment(postId, comment);
		}
		return commentService.addComment(postId, comment); // @POST - path: http://localhost:8080/messenger/api/posts/2/comments
	}

	@PUT
	@Path("{commentId}")
	public Comment updateComment(@PathParam("postId") Long postId, @PathParam("commentId") Long commentId,
			Comment comment) throws DataNotFoundException, InvalidRequestDataException {

		if (postId == null) {
			LOG.info(" *** DIRECT POST TO COMMENTS *** ");
			postId = comment.getParentPostId();
			return commentService.updateComment(postId, commentId, comment);
		}

		return commentService.updateComment(postId, commentId, comment);
	}

	@DELETE
	@Path("{commentId}")
	public Comment deleteComment(@PathParam("commentId") Long commentId, Comment comment)
			throws DataNotFoundException {
		return commentService.deleteComment(commentId); // comment alwasy has a unique id, no need for postId
	}

}
